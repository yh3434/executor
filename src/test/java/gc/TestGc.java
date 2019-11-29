package gc;

import java.util.ArrayList;
import java.util.List;

//请写一段程序，让其运行时的表现为触发5次ygc，然后3次fgc，然后3次ygc，然后1次fgc，请给出代码以及启动参数。
public class TestGc {
    //-Xms41m -Xmx41m -Xmn10m -XX:+UseParallelGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
    private static final int _1MB = 104_8576;

    public static void main(String[] args) {
        System.out.println("0.---");
        List caches = new ArrayList();
        for (int i = 0; i < 11; i++) {
            caches.add(new byte[3 * _1MB]);
        }
        System.out.println("1.---");
        caches.add(new byte[3 * _1MB]);
        caches.remove(0);
        caches.add(new byte[3 * _1MB]);
        for (int i = 0; i < 8; i++) {
            caches.remove(0);
        }
        caches.add(new byte[3 * _1MB]);
        System.out.println("2.---");
        for (int i = 0; i < 7; i++) {
            caches.add(new byte[3 * _1MB]);
        }
    }
}
