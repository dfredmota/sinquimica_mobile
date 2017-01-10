package br.com.martinlabs.commons.android;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by gil on 9/29/16.
 */

public abstract class ThreadPool {

    private static Queue<Runnable> queue;
    private static final Thread WORKER_THREAD;
    private static boolean isStarted = false;

    static {
        WORKER_THREAD = new Thread(new Runnable() {
            public void run() {
                process();
            }
        });
    }

    synchronized private static void checkAndInit() {
        if (isStarted) {
            return;
        }
        isStarted = true;
        queue = new LinkedList<Runnable>();
        WORKER_THREAD.setPriority(Thread.MAX_PRIORITY);
        WORKER_THREAD.start();
    }

    private static void process() {
        while (true) {
            while (!queue.isEmpty()) {
                Runnable runnable = queue.poll();
                runnable.run();
            }

            try {
                synchronized (WORKER_THREAD) {
                    WORKER_THREAD.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void queue(Runnable runnable) {
        checkAndInit();
        queue.add(runnable);
        synchronized (WORKER_THREAD) {
            WORKER_THREAD.notifyAll();
        }

    }

}
