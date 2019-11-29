package leetcode.string;

import org.junit.Assert;

import java.util.*;

public class LengthOfLongestSubstring {

    public static void main(String[] args) {
//        Assert.assertEquals(lengthOfLongestSubstring3(" "), 1);
//        Assert.assertEquals(lengthOfLongestSubstring3("abcabcbb"), 3);
//        Assert.assertEquals(lengthOfLongestSubstring3("bbbbb"), 1);
//        Assert.assertEquals(lengthOfLongestSubstring3("pwwkew"), 3);
//        Assert.assertEquals(lengthOfLongestSubstring3("aab"), 2);
        Assert.assertEquals(lengthOfLongestSubstring3("abba"), 2);
    }

    public static int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();
        List<Character> result = new ArrayList<>();
        List<Character> temp;
        for (int i = 0; i < chars.length; i++) {
            if (result.size() == (chars.length - i)) {
                break;
            }
            temp = new ArrayList<>();
            for (int j = i; j < chars.length; j++) {
                char e = chars[j];
                if (!temp.contains(e)) {
                    temp.add(e);
                } else {
                    break;
                }
                if (temp.size() > result.size() || result.size() == 0) {
                    result = temp;
                }
            }
        }
        return result.size();
    }

    public static int lengthOfLongestSubstring2(String s) {
        int n = s.length();
        int ans = 0, i = 0, j = 0;
        Set<Character> set = new HashSet<>();
        while (i < n && j < n) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            } else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }

    public static int lengthOfLongestSubstring3(String s) {
        int n = s.length();
        int ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0, j = 0; j < n; j++) {
            i = Math.max(map.getOrDefault(s.charAt(j), i), i);
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }
}
