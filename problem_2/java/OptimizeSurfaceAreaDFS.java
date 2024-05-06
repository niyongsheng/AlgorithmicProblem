package problem_2.java;

public class OptimizeSurfaceAreaDFS {

    public int convertToLayeredMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int maxHeight = getMaxHeight(matrix);
        int total = 0;

        // long startTime = System.nanoTime(); // 记录开始时间
        for (int i = 1; i <= maxHeight; i++) {
            int[][] layer = new int[m][n];
            int temp = 0;
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    if (matrix[j][k] >= i) {
                        layer[j][k] = 1;
                        temp += 1;
                    } else {
                        layer[j][k] = 0;
                    }
                }
            }
            if (temp > 3) {
                total += countEnclosedZeros(layer);
            }
        }
        // long endTime = System.nanoTime(); // 记录结束时间
        // long duration = (endTime - startTime) / 1000000; // 计算执行时间（毫秒）
        // System.out.println("Execution time: " + duration + " milliseconds");
        return total;
    }

    private int getMaxHeight(int[][] matrix) {
        int maxHeight = Integer.MIN_VALUE;
        for (int[] row : matrix) {
            for (int height : row) {
                maxHeight = Math.max(maxHeight, height);
            }
        }
        return maxHeight;
    }

    public int countEnclosedZeros(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;

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
        OptimizeSurfaceAreaDFS converter = new OptimizeSurfaceAreaDFS();
        int[][] matrix = {
            {12, 13, 1, 12},
            {13, 4, 13, 12},
            {13, 8888888, 10, 12},
            {12, 13, 12, 12},
            {13, 13, 13, 13}
        };
        int count = converter.convertToLayeredMatrix(matrix);
        System.out.println("Total: " + count);
    }
}
