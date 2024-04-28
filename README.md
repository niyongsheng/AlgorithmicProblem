# AlgorithmicProblem
![(logo)](./logo.png)
===
![Pages](https://img.shields.io/badge/Coding-v0.0.1-brightblue.svg?style=flat-square)
===
> 记录些有意思的算法问题。<br>
> Some interesting algorithm problems.

## [problem_1](/problem_1/)
<a>2D接雨滴</a>
<p>给定n个非负整数表示每个宽度单位为1的方块的高度图，计算按此排列的方块，下雨之后能接多少雨水。</p>
<img src="./problem_1/raindrop_2d.jpg" width="260" style="border-radius: 10px;">

<details open>
<summary>代码实现</summary>

#### [`raindrop_2d.c`](/problem_1/c/raindrop_2d.c)
#### [`raindrop_2d.java`](/problem_1/java/raindrop_2d.java)
  * 解法：最小堆
  * 时间复杂度：O(n)
  * 空间复杂度：O(n)
  
</details>


## [problem_2](/problem_2/)
<a>3D接雨滴</a>

<img src="./problem_2/raindrop_3d.png" width="260" style="border-radius: 10px;">

<details open>
<summary>代码实现</summary>

#### [`raindrop_3d.c`](/problem_2/c/raindrop_3d.c)
#### [`raindrop_3d.java`](/problem_2/java/raindrop_3d.java)
  * 解法：最小堆
  * 时间复杂度：O(MN)
  * 空间复杂度：O(MN)
    - 使用优先队列作为辅助数据结构，其空间复杂度取决于输入的大小和队列中元素的数量，但由于最多只存储一行或一列的元素，因此可以忽略不计。
  
</details>



## Contact Me
* E-mail: niyongsheng@Outlook.com
* Weibo: [@Ni永胜](https://weibo.com/u/7317805089)
