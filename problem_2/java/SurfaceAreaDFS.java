package problem_2.java;

public class SurfaceAreaDFS {

    public int[][][] convertToLayeredMatrix(int[][] matrix) {
        // 获取矩阵的行数和列数
        int m = matrix.length;
        int n = matrix[0].length;

        // 找到矩阵中的最大高度
        int max_layer = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                max_layer = Math.max(max_layer, matrix[i][j]);
            }
        }

        // 创建分层存储的三维矩阵
        int[][][] layeredMatrix = new int[max_layer][m][n];

        // 将矩阵中的高度值放置到相应的高度层中
        for (int k = 0; k < max_layer; k++) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    int value = matrix[i][j];
                    layeredMatrix[k][i][j] = (value >= k + 1) ? 1 : 0;
                }
            }
        }

        return layeredMatrix;
    }

    // 计算每个图层中封闭区域的大小
    public int[] countClosedRegions(int[][][] layers) {
        
        int[] counts = new int[layers.length];
        for (int i = 0; i < layers.length; i++) {
            System.out.println("DFS layer:" + i);
            counts[i] = countEnclosedZeros(layers[i]);
        }
        return counts;
    }

    public int countEnclosedZeros(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;

        int temp = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int value = grid[i][j];
                temp += value;
            }
        }
        if (temp <= 3) {
            return 0;
        }

        // 使用深度优先搜索找到由1围成的封闭区域
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 从该层的边缘开始深度优先遍历
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                    dfs(grid, i, j);
                }
            }
        }

        // 计算剩余的0的数量
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0) {
                    count++;
                }
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }

        return count;
    }

    // 深度优先
    private void dfs(int[][] grid, int i, int j) {
        // 超出边界或为0值直接返回
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 0) {
            return;
        }

        grid[i][j] = -1; // Mark visited
        // 递归
        dfs(grid, i + 1, j);
        dfs(grid, i - 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i, j - 1);
    }

    public static void main(String[] args) {
        SurfaceAreaDFS converter = new SurfaceAreaDFS();
        int[][] matrix = {
            {1, 4, 3, 1, 3, 2}, 
            {3, 2, 1, 3, 2, 4}, 
            {2, 3, 3, 2, 3, 1}
        };
        int[][][] layeredMatrix = converter.convertToLayeredMatrix(matrix);
        // 打印第一个高度层的矩阵
        for (int l = 0; l < layeredMatrix.length; l++) {
            System.out.println("Layer " + l);
            for (int i = 0; i < layeredMatrix[l].length; i++) {
                for (int j = 0; j < layeredMatrix[l][i].length; j++) {
                    System.out.print(layeredMatrix[l][i][j] + " ");
                }
                System.out.println();
            }
        }
        int[] layerSurfaceArea = converter.countClosedRegions(layeredMatrix);
        for (int i = 0; i < layerSurfaceArea.length; i++) {
            System.out.println("Layer " + i + " surface area: " + layerSurfaceArea[i]);
        }
    }

}
