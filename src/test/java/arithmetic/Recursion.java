package arithmetic;

import org.junit.Test;

public class Recursion {

    @Test
    public void fn() {
        System.out.println(fn(5));
    }

    private int fn(int num) {
        if (num == 1)
            return num;
        return num * fn(num - 1);
    }

    @Test
    public void tailFn() {
        System.out.println(fn(5, 1));
    }

    private int fn(int num, int res) {
        if (num == 1)
            return res;
        return fn(num - 1, num * res);
    }

}
