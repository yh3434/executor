package string;

import org.junit.Test;

public class StringTest {

    @Test
    public void testNew() {
        String s1 = new String("aaa");
        String s2 = "aaa";
        String s3 = new String("aaa").intern();
        String s4 = "a" + "aa";
        String s5 = "a" + "aa";
        System.out.println(s1 == s2);
        System.out.println(s2 == s3);
        System.out.println(s2 == s4);
        System.out.println(s4 == s5);
    }

}
