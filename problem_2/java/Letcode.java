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
            for (int j = 0; j < n; ++j) {
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
            int[] curr = qu.poll(); // 取出队列中的点
            int x = curr[0];
            int y = curr[1];
            // 在四个方向上进行搜索
            for (int i = 0; i < 4; ++i) {
                int nx = x + dirs[i], ny = y + dirs[i + 1];
                // 如果超出边界，则跳过当前方向
                if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
                    continue;
                }
                // *如果当前位置的水高度小于相邻位置的水高度，并且相邻位置的水高度大于相邻位置的地面高度
                if (water[x][y] < water[nx][ny] && water[nx][ny] > heightMap[nx][ny]) {
                    water[nx][ny] = Math.max(water[x][y], heightMap[nx][ny]); // 更新相邻位置的水高度
                    qu.offer(new int[]{nx, ny}); // 将相邻位置加入队列，继续搜索
                }
            }
        }
    
        int res = 0; // 初始化结果变量为0
        // 计算结果，遍历水矩阵和原始高度矩阵，将水矩阵中的高度减去原始高度矩阵中的高度，并累加到结果中
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                res += water[i][j] - heightMap[i][j];
            }
        }
        return res; // 返回结果
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


/**
 * 广度优先搜索 (BFS):
BFS从起始点开始，逐层扩展搜索，先访问离起始点最近的节点，然后逐渐向外扩展。这意味着BFS会优先考虑周围的节点，然后才是更远的节点。
在搜索最短路径或者在图中找到最短距离的情况下，BFS往往更合适，因为它保证了先访问距离起始点更近的节点。
使用队列来实现，先进先出（FIFO）的顺序，可以保证节点按层级顺序被访问。
 * 深度优先搜索 (DFS):
DFS沿着图的深度尽可能远的搜索。它会先访问一个节点的所有邻居，然后再递归地访问每个邻居的邻居，依此类推。
在寻找所有可能的路径或者解决问题的过程中，DFS可能更适用。因为DFS会尽可能地往深处搜索，可以快速找到一条路径或者解决方案。
使用栈来实现，后进先出（LIFO）的顺序，使得搜索沿着一条路径尽可能深入。

比较：
时间复杂度：在最坏情况下，两种算法的时间复杂度都是O(V + E)，其中V是顶点数，E是边数。但是BFS通常在搜索的层数较少的情况下效率更高，而DFS在搜索深度较深的情况下更有效率。
空间复杂度：BFS通常需要更多的内存空间来存储队列，而DFS则需要存储递归调用的栈，因此在空间利用上两者可能有所不同。
应用场景：根据问题的特点选择适合的搜索算法。如果问题要求找到最短路径或者需要层级遍历的特性，通常选择BFS；如果需要找到所有可能的解，或者需要更深的搜索，通常选择DFS。
 */