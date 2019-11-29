package thread;

import java.util.concurrent.CountDownLatch;

/**
 * Created by yuhang on 2016/12/5.
 */
public class CountDownLatchTest extends Thread {
    private CountDownLatch cdl;
    private int sleepSecond;

    public CountDownLatchTest(String name, CountDownLatch cdl, int sleepSecond) {
        super(name);
        this.cdl = cdl;
        this.sleepSecond = sleepSecond;
    }

    public void run() {
        try {
            System.out.println(this.getName() + "启动了 !");
            long time = System.currentTimeMillis();
            Thread.sleep(sleepSecond * 1000);
            cdl.countDown();
            System.out.println(this.getName() + "执行完了，时间为" + (System.currentTimeMillis() - time) / 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class DoneThread extends Thread {
        private CountDownLatch cdl;

        public DoneThread(String name, CountDownLatch cdl) {
            super(name);
            this.cdl = cdl;
        }

        public void run() {
            try {
                System.out.println(this.getName() + "要等待了！");
                long time = System.currentTimeMillis();
                cdl.await();//等待countDown()
                System.out.println(this.getName() + "等待完了, 等待时间为：" + (System.currentTimeMillis() - time) / 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //改变参数来体验被唤醒的情况
        CountDownLatch cdl = new CountDownLatch(3);
        DoneThread dt0 = new DoneThread("DoneThread1", cdl);
        DoneThread dt1 = new DoneThread("DoneThread2", cdl);
        dt0.start();
        dt1.start();
        CountDownLatchTest wt0 = new CountDownLatchTest("WorkThread1", cdl, 2);
        CountDownLatchTest wt1 = new CountDownLatchTest("WorkThread2", cdl, 3);
        CountDownLatchTest wt2 = new CountDownLatchTest("WorkThread3", cdl, 4);
        wt0.start();
        wt1.start();
        wt2.start();
    }
}
