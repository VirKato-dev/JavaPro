package my.concurrency.pool;

import java.util.LinkedList;
import java.util.Optional;

public class MyThreadPool {

    private final int numberOfWorkers;
    private final Thread[] workers;
    private final LinkedList<Runnable> taskQueue = new LinkedList<>();
    private final ThreadLocal<Integer> counter = new ThreadLocal<>();


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
                counter.set(0);
                while (!taskQueue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                    nextTask().ifPresent(r -> {
                        r.run();
                        counter.set(counter.get() + 1);
                    });
                }
                System.out.println(Thread.currentThread().getName() + " finished " + counter.get() + " tasks");
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
