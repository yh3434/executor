package thread;

import java.util.concurrent.Semaphore;

/**
 * Created by yuhang on 2016/12/5.
 */
public class SeamPhoreTest {
    public static void main(String[] args) {
        //可以改变参数来控制允许并发的数量
        final Semaphore semaphore = new Semaphore(4);
        Runnable runnable = () -> {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + "获得了信号量 ！");
                long time = System.currentTimeMillis();
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "释放了信号量,时间为" + (System.currentTimeMillis() - time) / 1000 + " 秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        };
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++)
            threads[i] = new Thread(runnable);
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }
}