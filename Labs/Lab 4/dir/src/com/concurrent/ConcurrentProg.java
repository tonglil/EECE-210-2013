package com.concurrent;

public class ConcurrentProg {
    public static int tagCounter = 0;
    public static Object tagCounterLock = new Object();

    public static void main(String[] args) {
        ConcurrentProg cp = new ConcurrentProg();
        for (int i = 0; i < 4; i++) {
            new Thread(cp.new PrintMsg()).start();
        }
    }

    private static int incBy100000() {
        for (int i = 0; i < 100000; i++) {
            tagCounter++;
        }

        return tagCounter;
    }

    private class PrintMsg implements Runnable {
        @Override
        public void run() {
            int tag;
            synchronized (tagCounterLock) {
                tag = incBy100000();
            }
            System.out.println("This is thread " + tag);
        }
    }
}
