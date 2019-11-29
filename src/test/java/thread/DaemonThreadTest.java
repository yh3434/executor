package thread;

public class DaemonThreadTest {

    public static void main(String[] args) {
//    @Test
//    public void daemonTest() {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 90; i++) {
                try {
                    System.out.println("用户线程" + i);
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

//        thread.setDaemon(true);
        thread.start();
        for (int i = 0; i < 5; i++) {
            System.out.println("主线程" + i);
        }

        System.out.println("主线程执行完毕");

    }
}
