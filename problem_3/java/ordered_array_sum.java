import java.util.HashMap;
import java.util.Map;

/**
 * 问题：有序数组中找到两个数之和的索引位置
 */

public class ordered_array_sum {

    /**
     * 哈希表法(不要求有序数组)
     * @param nums
     * @param target
     * @return
     */
    public static int[] findTwoSumByHash(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    /**
     * 双指针法
     * @param nums
     * @param target
     * @return
     */
    public static int[] findTwoSum(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == target) {
                return new int[] { left, right };
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // int[] result = findTwoSum(new int[] { 1, 2, 3, 4, 5 }, 7);
        int[] result = findTwoSumByHash(new int[] { 1, 2, 3, 4, 5 }, 7);
        // 打印结果
        if (result != null) {
            System.out.println(result[0] + " " + result[1]);
        } else {
            System.out.println("No solution found!");
        }
    }
}