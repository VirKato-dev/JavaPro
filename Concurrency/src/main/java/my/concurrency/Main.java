package my.concurrency;

import my.concurrency.pool.MyThreadPool;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        MyThreadPool threadPool = new MyThreadPool(3);

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "  work with 'task" + finalI + "'");
            });
        }
        threadPool.shutdown();
    }
}