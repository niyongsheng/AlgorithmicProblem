package problem_2.java;

import java.util.LinkedList;
import java.util.Queue;

public class Letcode {
    public int convertToLayeredMatrix(int[][] heightMap) {
        int m = heightMap.length;  // 获取矩阵的行数
        int n = heightMap[0].length;  // 获取矩阵的列数
        int[] dirs = {-1, 0, 1, 0, -1};  // 定义方向数组，用于在四个方向上进行遍历
        int maxHeight = 0;  // 初始化最大高度为0
    
        // 遍历整个矩阵，找到最大高度
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                maxHeight = Math.max(maxHeight, heightMap[i][j]);  // 更新最大高度
            }
        }
    
        // 初始化水矩阵，将水矩阵中的每个元素都设为最大高度
        int[][] water = new int[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j){
                water[i][j] = maxHeight;      
            }
        }
    
        Queue<int[]> qu = new LinkedList<>();  // 定义一个队列用于广度优先搜索
        // 遍历矩阵的边界，将边界上的点加入队列，并将水矩阵中对应位置的值设为边界上的高度
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                    if (water[i][j] > heightMap[i][j]) {
                        water[i][j] = heightMap[i][j];  // 更新水矩阵中边界上的高度
                        qu.offer(new int[]{i, j});  // 将边界上的点加入队列
                    }
                }
            }
        } 
    
        // 广度优先搜索
        while (!qu.isEmpty()) {
            int[] curr = qu.poll();  // 取出队列中的点
            int x = curr[0];
            int y = curr[1];
            // 在四个方向上进行搜索
            for (int i = 0; i < 4; ++i) {
                int nx = x + dirs[i], ny = y + dirs[i + 1];
                // 如果超出边界，则跳过当前方向
                if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
                    continue;
                }
                // 如果当前位置的水高度小于相邻位置的水高度，并且相邻位置的水高度大于相邻位置的地面高度
                if (water[x][y] < water[nx][ny] && water[nx][ny] > heightMap[nx][ny]) {
                    water[nx][ny] = Math.max(water[x][y], heightMap[nx][ny]);  // 更新相邻位置的水高度
                    qu.offer(new int[]{nx, ny});  // 将相邻位置加入队列，继续搜索
                }
            }
        }
    
        int res = 0;  // 初始化结果变量为0
        // 计算结果，遍历水矩阵和原始高度矩阵，将水矩阵中的高度减去原始高度矩阵中的高度，并累加到结果中
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                res += water[i][j] - heightMap[i][j];
            }
        }
        return res;  // 返回结果
    }
    

    public static void main(String[] args) {
        long startTime = System.nanoTime(); // 记录开始时间
        Letcode converter = new Letcode();
        int[][] matrix = {
            {12, 13, 1, 12},
            {13, 4, 13, 12},
            {13, 8888888, 10, 12},
            {12, 13, 12, 12},
            {13, 13, 13, 13}
        };
        int count = converter.convertToLayeredMatrix(matrix);
        System.out.println("Total: " + count);
        long endTime = System.nanoTime(); // 记录结束时间
        long duration = (endTime - startTime) / 1000000; // 计算执行时间（毫秒）
        System.out.println("Execution time: " + duration + " milliseconds");
    }
}
