package leetcode.linkedlist;

public class TwoSum {
    /*给定 nums = [2, 7, 11, 15], target = 9

    因为 nums[0] + nums[1] = 2 + 7 = 9
    所以返回 [0, 1]*/

    public static void main(String[] args) {
        int[] nums = {3, 2, 4};
        final int[] x = twoSum(nums, 6);
        if (x != null) {
            for (int i : x) {
                System.out.println(i);
            }
            return;
        }
        System.out.println("result is null...");
    }

    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((nums[i] + nums[j]) == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}
