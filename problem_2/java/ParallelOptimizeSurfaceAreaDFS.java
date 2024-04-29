package problem_2.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelOptimizeSurfaceAreaDFS {

    // private static final int NUM_THREADS =
    // Runtime.getRuntime().availableProcessors(); // 使用可用处理器数目作为线程数
    private static final int NUM_THREADS = 4;

    public int convertToLayeredMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int maxHeight = getMaxHeight(matrix);
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        int[] results = new int[maxHeight]; // 存储每个高度层的结果
        for (int i = 1; i <= maxHeight; i++) {
            final int height = i;
            executor.submit(() -> {
                int[][] layer = new int[m][n];
                for (int j = 0; j < m; j++) {
                    for (int k = 0; k < n; k++) {
                        layer[j][k] = (matrix[j][k] >= height) ? 1 : 0;
                    }
                }
                results[height - 1] = countEnclosedZeros(layer); // 将结果存储到对应高度层的位置
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 汇总所有高度层的结果
        int total = 0;
        for (int result : results) {
            total += result;
        }
        return total;
    }

    private synchronized int getMaxHeight(int[][] matrix) {
        int maxHeight = Integer.MIN_VALUE;
        for (int[] row : matrix) {
            for (int height : row) {
                maxHeight = Math.max(maxHeight, height);
            }
        }
        return maxHeight;
    }

    // 线程安全的版本，保证每个线程的访问不会相互干扰
    private synchronized int countEnclosedZeros(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;

        int sum = 0;
        for (int[] row : grid) {
            for (int value : row) {
                sum += value;
            }
        }
        if (sum <= 3) {
            return 0;
        }

        for (int i = 0; i < rows; i++) {
            dfs(grid, i, 0);
            dfs(grid, i, cols - 1);
        }
        for (int j = 0; j < cols; j++) {
            dfs(grid, 0, j);
            dfs(grid, rows - 1, j);
        }

        int count = 0;
        for (int[] row : grid) {
            for (int value : row) {
                if (value == 0) {
                    count++;
                }
            }
        }

        return count;
    }

    private void dfs(int[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 0) {
            return;
        }
        grid[i][j] = -1;
        dfs(grid, i + 1, j);
        dfs(grid, i - 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i, j - 1);
    }

    public static void main(String[] args) {
        ParallelOptimizeSurfaceAreaDFS converter = new ParallelOptimizeSurfaceAreaDFS();
        int[][] matrix = {
                { 12, 13, 1, 12 },
                { 13, 4, 13, 12 },
                { 13, 8, 10, 12 },
                { 12, 13, 12, 12 },
                { 13, 13, 13, 13 }
        };
        int count = converter.convertToLayeredMatrix(matrix);
        System.out.println("Total: " + count);
    }
}
