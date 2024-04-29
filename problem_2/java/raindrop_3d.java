package problem_2.java;

import java.util.*;

/**
 * 
 * 高度二维矩阵(3d情况下无法由2d递推完成！！！)
 * {
 * {1, 4, 3, 1, 3, 2},
 * {3, 2, 1, 3, 2, 4},
 * {2, 3, 3, 2, 3, 1}
 * };
 * 
 * [0, 1, 4, 4, 4, 4]
 * [0, 3, 3, 3, 3, 3]
 * [0, 2, 3, 3, 3, 3]
 * 
 * [4, 3, 3, 3, 2, 0]
 * [4, 4, 4, 4, 4, 0]
 * [3, 3, 3, 3, 1, 0]
 * 
 * [0, 2, 3]
 * [0, 3, 3]
 * [0, 3, 3]
 * [0, 2, 3]
 * [0, 3, 3]
 * [0, 1, 4]
 * 
 * [3, 1, 0]
 * [4, 4, 0]
 * [3, 3, 0]
 * [3, 1, 0]
 * [3, 3, 0]
 * [4, 2, 0]
 */
public class raindrop_3d {
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

        // 计算每一列的前后边界高度
        int[][] frontBoundaries = new int[n][m];
        int[][] backBoundaries = new int[n][m];

        // 计算每一列的前边界高度
        for (int i = 0; i < n; i++) {
            frontBoundaries[i] = calculateFrontBoundaries(rotateMatrix(heightMap)[i]);
            System.out.println(Arrays.toString(frontBoundaries[i]));
        }

        // 计算每一列的后边界高度
        for (int i = 0; i < n; i++) {
            backBoundaries[i] = calculateBackBoundaries(rotateMatrix(heightMap)[i]);
            System.out.println(Arrays.toString(backBoundaries[i]));
        }

        // 计算每一列的前后边界高度
        int[][] minusPiFrontBoundaries = rotateMatrixCounterClockwise(frontBoundaries);
        int[][] minusPiBackBoundaries = rotateMatrixCounterClockwise(backBoundaries);

        // 计算每个位置的接水量
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 计算左右边界最低高度
                int boundariesLRHeight = Math.min(leftBoundaries[i][j], rightBoundaries[i][j]);
                // 计算前后边界最低高度
                int boundariesFBHeight = Math.min(minusPiFrontBoundaries[i][j], minusPiBackBoundaries[i][j]);
                // 计算当前位置的接水量并累加到总接水量中
                int rw = Math.max(0, Math.min(boundariesLRHeight, boundariesFBHeight) - heightMap[i][j]);
                totalRainWater += rw;
                System.out.println("Trapped water at position (" + i + ", " + j + "): " + rw);
            }
        }

        return totalRainWater; // 返回总接水量
    }

    // 顺时针旋转二维数组
    private int[][] rotateMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // 创建一个新的二维数组来存储旋转后的结果
        int[][] rotatedMatrix = new int[cols][rows];

        // 顺时针旋转90度
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedMatrix[j][rows - 1 - i] = matrix[i][j];
            }
        }

        return rotatedMatrix;
    }

    // 逆时针时针旋转二维数组
    private int[][] rotateMatrixCounterClockwise(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // 创建一个新的二维数组来存储旋转后的结果
        int[][] rotatedMatrix = new int[cols][rows];

        // 逆时针旋转90度
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedMatrix[cols - 1 - j][i] = matrix[i][j];
            }
        }

        return rotatedMatrix;
    }

    private int[] calculateLeftBoundaries(int[] height) {
        int[] leftBoundaries = new int[height.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > stack.peek()) {
                stack.pop();
            }
            leftBoundaries[i] = stack.isEmpty() ? 0 : stack.peek();
            stack.push(height[i]);
        }
        return leftBoundaries; // 返回每行的左边界高度数组
    }

    private int[] calculateRightBoundaries(int[] height) {
        int[] rightBoundaries = new int[height.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = height.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && height[i] > stack.peek()) {
                stack.pop();
            }
            rightBoundaries[i] = stack.isEmpty() ? 0 : stack.peek();
            stack.push(height[i]);
        }
        return rightBoundaries; // 返回每行的右边界高度数组
    }

    private int[] calculateFrontBoundaries(int[] height) {
        int[] frontBoundaries = new int[height.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > stack.peek()) {
                stack.pop();
            }
            frontBoundaries[i] = stack.isEmpty() ? 0 : stack.peek();
            stack.push(height[i]);
        }
        return frontBoundaries; // 返回每列的前边界高度数组
    }

    private int[] calculateBackBoundaries(int[] height) {
        int[] backBoundaries = new int[height.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = height.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && height[i] > stack.peek()) {
                stack.pop();
            }
            backBoundaries[i] = stack.isEmpty() ? 0 : stack.peek();
            stack.push(height[i]);
        }
        return backBoundaries; // 返回每列的后边界高度数组
    }

    // 测试主方法
    public static void main(String[] args) {
        raindrop_3d solution = new raindrop_3d(); // 创建解决方案对象
        int[][] heightMap = {
                { 1, 4, 3, 1, 3, 2 },
                { 3, 2, 1, 3, 2, 4 },
                { 2, 3, 3, 2, 3, 1 }
        }; // 输入高度图
        int result = solution.trapRainWater(heightMap); // 计算接水量
        System.out.println("Total rain water trapped: " + result); // 输出结果
    }

}