package org.mozilla.javascript;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.locks.*;

import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.ast.*;

public class IfCollector {
    private static IfData[] if_data = new IfData[500];
    private static int ifData_counter = -1;
    private static int ifData_ID_counter = 0;

    private static AssnData[] assn_data = new AssnData[500];
    private static int assnData_counter = -1;
    private static int assnData_ID_counter = 0;

    /*EECE310_NOTE:
     * This variable is used to keep track of the number of threads.*/
    //private static int thread_counter = 0;
    private static List<Thread> threads = new ArrayList<Thread>();

    /*EECE310_TODO: Declare any synchronization locks or any other
     * member variables you need to use here. Declare all variables
     * as static.*/
    //public static Object threadCounterLock = new Object();
    public static Object ifDataIdCounterLock = new Object();
    public static Object assnDataIdCounterLock = new Object();

    public static void main(String[] args) {
        int t_counter = 0;

        for (int i = 0; i < 500; i++) {
            if_data[i] = new IfData();
            assn_data[i] = new AssnData();
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter JavaScript File To Parse: ");
            System.out.flush();
            String js_file = br.readLine();

            //Parse the file
            Parser p = new Parser();
            AstRoot root = p.parse(new FileReader(js_file), "", 1);

            long startTime = System.nanoTime();
            long endTime;

            int i = 0;
            IfCollector ifc = new IfCollector();
            for (Node kid : root) {
                FunctionNode fn = (FunctionNode) kid;
                Thread worker = new Thread(ifc.new CollectIfsHelper(fn, 1, ifc));
                worker.start();
                worker.setName(String.valueOf(i++));
                threads.add(worker);
            }

            /*EECE310_TODO: BONUS - If you can get rid of the busy waiting
             * by replacing it with a yield, you will be given bonus marks.*/
            // Instead of using a thread counter, threads automatically join upon completion
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (Exception e) {
                    System.out.println("Error executing threads");
                    System.exit(0);
                }
            }
            //synchronized (threadCounterLock) {
                //while (thread_counter > 0); //wait for all threads to finish
            //}

            endTime = System.nanoTime();
            long duration = endTime - startTime;

            System.out.println("Succeeded");
            System.out.println("Finished in " + duration + " ns");

            printStats();
        } catch (IOException ie) {
            System.out.println("Error reading file");
        }
    }

    public static void printStats() {
        //Print If data first
        int numIfs = ifData_counter + 1;
        System.out.println("Number of ifs: " + numIfs);
        for (int i = 0; i <= ifData_counter; i++) {
            System.out.println(if_data[i].ifName + " " + if_data[i].ifCondition + " " + if_data[i].functionName);
        }

        //Print Assn data
        int numAssns = assnData_counter + 1;
        System.out.println("\nNumber of assignments: " + numAssns);
        for (int i = 0; i <= assnData_counter; i++) {
            System.out.println(assn_data[i].assnName + " " + assn_data[i].assnLeft + " " + assn_data[i].assnRight + " " + assn_data[i].functionName);
        }
    }

    private class CollectIfs implements NodeVisitor {
        private FunctionNode funcNode;

        public CollectIfs(FunctionNode fn) {
            this.funcNode = fn;
        }

        @Override
        public boolean visit(AstNode node) {
            if (node instanceof IfStatement) {
                IfStatement if_st = (IfStatement)node;

                //Get the condition and function name
                String condition = if_st.getCondition().toSource();
                String functionName = if_st.getEnclosingFunction().getName();
                String ifID;

                synchronized (ifDataIdCounterLock) {
                    //Assign an ID to the function
                    ifData_ID_counter = Tag.newTag(ifData_ID_counter);
                    ifID = "if_" + ifData_ID_counter;

                    //Add this to if_data
                    ifData_counter++;
                    if_data[ifData_counter].functionName = functionName;
                    if_data[ifData_counter].ifCondition = condition;
                    if_data[ifData_counter].ifName = ifID;
                }
            }
            else if (node instanceof Assignment) {
                Assignment assn_st = (Assignment)node;

                //Get the left expression, right expression, and function name
                String leftExpr = assn_st.getLeft().toSource();
                String rightExpr = assn_st.getRight().toSource();
                String functionName = assn_st.getEnclosingFunction().getName();
                String assnID;

                synchronized (assnDataIdCounterLock) {
                    //Assign an ID to the assignment
                    assnData_ID_counter = Tag.newTag(assnData_ID_counter);
                    assnID = "assn_" + assnData_ID_counter;

                    //Add this to assn_data
                    assnData_counter++;
                    assn_data[assnData_counter].functionName = functionName;
                    assn_data[assnData_counter].assnLeft = leftExpr;
                    assn_data[assnData_counter].assnRight = rightExpr;
                    assn_data[assnData_counter].assnName = assnID;
                }
            }

            //Visit the child nodes
            return true;
        }

        public void run() {
            funcNode.visit(this);
        }
    }

    private class CollectIfsHelper implements Runnable {
        private FunctionNode fn;
        private int numFuncs;
        private IfCollector ifc;

        public CollectIfsHelper(FunctionNode func, int num, IfCollector if_coll) {
            this.fn = func;
            this.numFuncs = num;
            this.ifc = if_coll;
        }

        @Override
        public void run() {
            for (int i = 0; i < numFuncs; i++) {
                //synchronized (threadCounterLock) {
                    //thread_counter++;
                    (ifc.new CollectIfs(fn)).run();
                    fn = (FunctionNode)fn.getNext();
                    //thread_counter--;
                //}
            }
        }
    }
}
