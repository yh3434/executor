package threadpool;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;

public class TestScheduledThreadPoolExecutor {

    @Test
    public void testScheduledExecuteService() throws InterruptedException {
        // 创建大小为5的线程池
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);

        for (int i = 0; i < 3; i++) {
            Task worker = new Task("task-" + i);
            // 只执行一次
            scheduledThreadPool.schedule(worker, 5, TimeUnit.SECONDS);
            // 周期性执行，每5秒执行一次
//            scheduledThreadPool.scheduleAtFixedRate(worker, 0, 5, TimeUnit.SECONDS);
        }

//        Thread.sleep(10000);

//        System.out.println("Shutting down executor...");
        // 关闭线程池
        scheduledThreadPool.shutdown();
        boolean isDone;
        // 等待线程池终止
        do {
            isDone = scheduledThreadPool.awaitTermination(1, TimeUnit.DAYS);
            System.out.println("awaitTermination...");
        } while (!isDone);

        System.out.println("Finished all threads");
    }

    @Test
    public void testExecutorService() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 3; i++) {
            Task worker = new Task("task-" + i);
            threadPool.submit(worker);
        }
        threadPool.shutdown();
        boolean isDone;
        // 等待线程池终止
        do {
            isDone = threadPool.awaitTermination(1, TimeUnit.DAYS);
            System.out.println("awaitTermination...");
        } while (!isDone);

        System.out.println("Finished all threads");
    }

    @Test
    public void testCustomThreadPoolExecutor() throws InterruptedException {
        //创建等待队列
        BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<>(50);
        //自定义拒绝策略
        RejectedExecutionHandler rejectedExecutionHandler = (r, e) ->
                System.out.println(("Task " + r.toString() + " rejected from " + e.toString()));
        //创建线程池，池中保存的线程数为3，允许的最大线程数为5
        ExecutorService threadPool = new ThreadPoolExecutor(3, 5, 50, TimeUnit.MILLISECONDS, bqueue, rejectedExecutionHandler);
        for (int i = 0; i < 100; i++) {
            ExcptionTask worker = new ExcptionTask("task-" + i);
            threadPool.execute(worker);
        }
        Thread.sleep(6000);
        for (int i = 100; i < 110; i++) {
            ExcptionTask worker = new ExcptionTask("task-" + i);
            threadPool.execute(worker);
        }
        threadPool.shutdown();
        boolean isDone;
        // 等待线程池终止
        do {
            isDone = threadPool.awaitTermination(1, TimeUnit.DAYS);
            System.out.println("awaitTermination...");
        } while (!isDone);

        System.out.println("Finished all threads");
    }
}

class Task implements Runnable {

    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("name = " + name + ", startTime = " + new Date());
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("name = " + name + ", endTime = " + new Date());
    }

}

class ExcptionTask implements Runnable {

    private String name;

    public ExcptionTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("name = " + name + ", endTime = " + new Date());
    }

}


class Hello {
    Runnable r1 = () -> System.out.println(this);
    Runnable r2 = () -> System.out.println(toString());

    public static void main(String[] args) {
        new Hello().r1.run();
        new Hello().r2.run();
    }

    public String toString() {
        return "Hello Hoolee";
    }
}