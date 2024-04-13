package my.concurrency.pool;

import java.util.LinkedList;
import java.util.Optional;

public class MyThreadPool {

    private final int numberOfWorkers;
    private final Thread[] workers;
    private final LinkedList<Runnable> taskQueue = new LinkedList<>();


    public MyThreadPool(int numberOfWorkers) {
        this.numberOfWorkers = numberOfWorkers;
        workers = new Thread[numberOfWorkers];
        init();
    }


    public synchronized void execute(Runnable task) {
        if (Thread.currentThread().isInterrupted()) {
            throw new IllegalStateException();
        }
        taskQueue.add(task);
        notifyAll();
    }


    public void shutdown() {
        for (Thread worker : workers) {
            worker.interrupt();
        }
    }


    private void init() {
        for (int i = 0; i < numberOfWorkers; i++) {

            workers[i] = new Thread(() -> {
                while (!taskQueue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                    nextTask().ifPresent(Runnable::run);
                }
            });

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
