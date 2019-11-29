package thread;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    public static class CyclicBarrierThread extends Thread {
        private CyclicBarrier cb;
        private int sleepSecond;

        public CyclicBarrierThread(CyclicBarrier cb, int sleepSecond) {
            this.cb = cb;
            this.sleepSecond = sleepSecond;
        }

        public void run() {
            try {
                System.out.println(this.getName() + "运行了");
                Thread.sleep(sleepSecond * 1000);
                System.out.println(this.getName() + "准备等待了, 时间为" + System.currentTimeMillis());
                cb.await();
                System.out.println(this.getName() + "结束等待了, 时间为" + System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println("CyclicBarrier的所有线程await()结束了，我运行了, 时间为" + System.currentTimeMillis());
        //三个屏障
        CyclicBarrier cb = new CyclicBarrier(3, runnable);
        CyclicBarrierThread cbt0 = new CyclicBarrierThread(cb, 3);
        CyclicBarrierThread cbt1 = new CyclicBarrierThread(cb, 6);
        CyclicBarrierThread cbt2 = new CyclicBarrierThread(cb, 9);
        cbt0.start();
        cbt1.start();
        cbt2.start();
        Thread status = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(cbt0.getName() + " status is " + cbt0.getState());
                System.out.println(cbt1.getName() + " status is " + cbt1.getState());
                System.out.println(cbt2.getName() + " status is " + cbt2.getState());
            }
        });
        status.setDaemon(true);
        status.start();
    }
}
