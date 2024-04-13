package my.concurrency.pool;

import java.util.LinkedList;
import java.util.Optional;

public class MyThreadPool {

    private final int numberOfWorkers;
    private final Thread[] workers;
    private final LinkedList<Runnable> taskQueue = new LinkedList<>();
    private volatile boolean isActive = true;

    private class Worker extends Thread {
        private static int id = 1;
        private int tasksFinished = 0;

        public Worker() {
            setName("Worker-" + id++);
        }

        @Override
        public void run() {
            while (!taskQueue.isEmpty() || isActive) {
                nextTask().ifPresent(r -> {
                    r.run();
                    tasksFinished++;
                });
            }
            System.out.println(getName() + " finished " + tasksFinished + " tasks");
        }
    }


    public MyThreadPool(int numberOfWorkers) {
        this.numberOfWorkers = numberOfWorkers;
        workers = new Thread[numberOfWorkers];
        init();
    }


    public synchronized void execute(Runnable task) {
        if (!isActive) {
            throw new IllegalStateException();
        }
        taskQueue.add(task);
        notifyAll();
    }


    public void shutdown() {
        isActive = false;
    }


    private void init() {
        for (int i = 0; i < numberOfWorkers; i++) {
            workers[i] = new Worker();
            workers[i].start();
        }
    }


    private synchronized Optional<Runnable> nextTask() {
        if (taskQueue.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(taskQueue.remove());
    }
}
