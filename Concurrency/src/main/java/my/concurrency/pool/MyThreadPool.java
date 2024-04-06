package my.concurrency.pool;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;

public class MyThreadPool {

    private final int numberOfWorkers;
    private final Thread[] workers;
    private final LinkedList<Runnable> taskQueue = new LinkedList<>();
    private boolean active = true;


    public MyThreadPool(int numberOfWorkers) {
        this.numberOfWorkers = numberOfWorkers;
        workers = new Thread[numberOfWorkers];
        init();
    }


    public synchronized void execute(Runnable task) {
        if (!active) throw new IllegalStateException();
        taskQueue.add(task);
        notifyAll();
    }


    public void shutdown() {
        active = false;
    }


    private void init() {
        for (int i = 0; i < numberOfWorkers; i++) {

            workers[i] = new Thread(() -> {
                while (!taskQueue.isEmpty() || active) {
                    nextTask().ifPresent(r -> {
                        try {
                            long time = 1000 * (new Random().nextInt(5) + 1);
                            Thread.sleep(time);
//                            System.out.print(Thread.currentThread().getName() + " worked " + time + "ms\t");
                            r.run();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            });

            workers[i].start();
        }
    }


    private synchronized Optional<Runnable> nextTask() {
        if (taskQueue.isEmpty()) return Optional.empty();
        return Optional.of(taskQueue.remove());
    }
}
