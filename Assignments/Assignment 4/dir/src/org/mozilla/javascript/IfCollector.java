package org.mozilla.javascript;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.locks.*;

import org.mozilla.javascript.ast.*;

public class IfCollector {
	private static IfData[] if_data = new IfData[500];
	private static int ifData_counter = -1;
	private static int ifData_ID_counter = 0;
	
	private static AssnData[] assn_data = new AssnData[500];
	private static int assnData_counter = -1;
	private static int assnData_ID_counter = 0;
	
	/*EECE310_NOTE: 
	 * This variable is used to keep track of the number of threads.
	 */
	private static int thread_counter = 0;
	
	/*EECE310_TODO: Declare any synchronization locks or any other
	 * member variables you need to use here. Declare all variables
	 * as static.*/
	
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
			
			IfCollector ifc = new IfCollector();
			/*EECE310_TODO: Visit each child of root (each of 
			 * which is assumed to be a FunctionNode) and collect 
			 * "if" and "assignment" data. Use concurrency to gain 
			 * some speedup.
			 * 
			 * You should use the CollectIfsHelper class
			 * to run a thread. CollectIfs is used to do the actual
			 * stat collection, and you may use it whenever you want
			 * the main thread itself to collect some stats.
			 * 
			 * Increment thread_counter prior to every call to
			 * the following:
			 * 
			 * (ifc.new CollectIfs(fn)).run(); 
			 * 
			 * and decrement thread_counter after every call to the above.
			 * 
			 * */
			
			/*EECE310_TODO: BONUS - If you can get rid of the busy waiting
			 * by replacing it with a yield, you will be given bonus marks
			 */
			while (thread_counter > 0); //wait for all threads to finish
			
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
				
				/*EECE310_TODO: Record the function name and condition
				 * of the IfStatement node, and create a name for this
				 * node using the Tag.newTag() function (you MUST use this
				 * function to generate the tag). See
				 * IfCollectorSequential.java to give you an idea on how
				 * to do this. In particular, a tag MUST be generated using
				 * the following line of code:
				 * 
				 * ifData_ID_counter = Tag.newTag(ifData_ID_counter);
				 * 
				 * Make sure you perform synchronization when accessing
				 * a shared variable. 
				 */
			}
			else if (node instanceof Assignment) {
				Assignment assn_st = (Assignment)node;
				
				/*EECE310_TODO: Record the function name, the right hand side
				 * and the left hand side of the Assignment node, and create 
				 * a name for this node using the Tag.newTag() function (you
				 * MUST use this function to generate the tag). See
				 * IfCollectorSequential.java to give you an idea on how
				 * to do this. In particular, a tag MUST be generated using the
				 * following line of code:
				 * 
				 * assnData_ID_counter = Tag.newTag(assnData_ID_counter);
				 * 
				 * Make sure you perform synchronization when accessing
				 * a shared variable.
				 */
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
				thread_counter++;
				(ifc.new CollectIfs(fn)).run();
				fn = (FunctionNode)fn.getNext();
				thread_counter--;
			}
		}
	}
}