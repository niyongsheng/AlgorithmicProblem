package problem_1.java;
import java.util.*;

/**
 * 
高度二维矩阵
{
{3, 2, 1, 3, 2, 4}
};
[0, 3, 3, 3, 3, 3]
[4, 4, 4, 4, 4, 0]

{
{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}
};
[0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3]
[3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 1, 0]
 */
public class raindrop_2d {
    // 计算接水量的方法
    public int trapRainWater(int[][] heightMap) {
        // 检查输入矩阵是否为空
        if (heightMap == null || heightMap.length == 0 || heightMap[0].length == 0) {
            return 0;
        }

        int m = heightMap.length; // 行数
        int n = heightMap[0].length; // 列数
        int totalRainWater = 0; // 总接水量

        // 计算每一行的左右边界高度
        int[][] leftBoundaries = new int[m][n];
        int[][] rightBoundaries = new int[m][n];

        // 计算每一行的左边界高度
        for (int i = 0; i < m; i++) {
            leftBoundaries[i] = calculateLeftBoundaries(heightMap[i]);
            System.out.println(Arrays.toString(leftBoundaries[i]));
        }

        // 计算每一行的右边界高度
        for (int i = 0; i < m; i++) {
            rightBoundaries[i] = calculateRightBoundaries(heightMap[i]);
            System.out.println(Arrays.toString(rightBoundaries[i]));
        }


        // 计算每个位置的接水量
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 计算左右边界最低高度
                int boundariesHeight = (j == 0 || j == n - 1) ? 0 : Math.min(leftBoundaries[i][j], rightBoundaries[i][j]);
                // 计算当前位置的接水量并累加到总接水量中
                int rw = Math.max(0, boundariesHeight - heightMap[i][j]);
                totalRainWater += rw;
                System.out.println("Trapped water at position (" + i + ", " + j + "): " + rw);
            }
        }

        return totalRainWater; // 返回总接水量
    }

    private int[] calculateLeftBoundaries(int[] height) {
        int[] leftBoundaries = new int[height.length];
        PriorityQueue<Integer> stack = new PriorityQueue<>(Comparator.reverseOrder()); // 使用栈来记录边界高度
        for (int i = 0; i < height.length; i++) {
            leftBoundaries[i] = stack.isEmpty() ? 0 : stack.peek(); // 更新左边界高度
            stack.offer(height[i]); // 优先入栈
        }
        return leftBoundaries; // 返回每行的左边界高度数组
    }

    private int[] calculateRightBoundaries(int[] height) {
        int[] rightBoundaries = new int[height.length];
        PriorityQueue<Integer> stack = new PriorityQueue<>(Comparator.reverseOrder()); // 使用栈来记录边界高度
        for (int i = height.length - 1; i >= 0; i--) {
            rightBoundaries[i] = stack.isEmpty() ? 0 : stack.peek(); // 更新右边界高度
            stack.offer(height[i]); // 入栈 
        }
        return rightBoundaries; // 返回每行的右边界高度数组
    }

    // 测试主方法
    public static void main(String[] args) {
        raindrop_2d solution = new raindrop_2d(); // 创建解决方案对象
        int[][] heightMap = {{3, 2, 1, 3, 2, 4}}; // 输入高度图
        int result = solution.trapRainWater(heightMap); // 计算接水量
        System.out.println("Total rain water trapped: " + result); // 输出结果
    }
}

