package thread;


import java.time.LocalDateTime;

public class ThreadMethodTest {

    public static void main(String[] args) throws InterruptedException {
//    @Test
//    public void yieldTest() throws InterruptedException {
        Runnable r = () -> {
            for (int i = 0; i < 20; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + " in loop..." + LocalDateTime.now());
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread1 = new Thread(r);
        Thread thread2 = new Thread(r);
        thread1.start();
        thread2.start();
        System.out.println("主线程开始" + LocalDateTime.now());
//        thread1.yield();
//        thread1.join();
//        thread1.join(1000);
        thread1.interrupt();
        thread2.interrupt();
        System.out.println("主线程结束" + LocalDateTime.now());
    }


}
