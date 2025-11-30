# CS 366 - PA5: Dynamic Programming Analysis

**Name:** SOLUTION KEY

**Date:** November 2025

---

## Part 2: Written Analysis

### 1. Manual Trace

Trace the following instance of the `dynamicKnapsack` algorithm by filling in the `exist` and `belong` grids:

- **n = 4** (number of items)
- **k = 10** (knapsack capacity)
- **S = [2, 3, 5, 6]** (item sizes, using 1-indexed notation: S[1]=2, S[2]=3, S[3]=5, S[4]=6)

#### exist grid (mark T for true, F for false)

```
exist | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 |
------|---|---|---|---|---|---|---|---|---|---|----|
  0   | T | F | F | F | F | F | F | F | F | F | F  |
  1   | T | F | T | F | F | F | F | F | F | F | F  |
  2   | T | F | T | T | F | T | F | F | F | F | F  |
  3   | T | F | T | T | F | T | F | T | T | F | T  |
  4   | T | F | T | T | F | T | T | T | T | T | T  |
```

**Explanation of exist table construction:**

- **Row 0 (base case):** Only exist[0][0] = T (empty set sums to 0)
- **Row 1 (item 0, size 2):** Can make 0 (don't use) or 2 (use item 0)
- **Row 2 (item 1, size 3):** Can make 0, 2 (from row 1), plus 3 and 5 (using item 1)
- **Row 3 (item 2, size 5):** Can make 0, 2, 3, 5 (from row 2), plus 7 (2+5), 8 (3+5), 10 (5+5 invalid, but 2+3+5=10)
- **Row 4 (item 3, size 6):** Can make all from row 3, plus 6, 9 (3+6), 8 (2+6)

#### belong grid (mark T for true, F for false)

```
belong| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 |
------|---|---|---|---|---|---|---|---|---|---|----|
  0   | F | F | F | F | F | F | F | F | F | F | F  |
  1   | F | F | T | F | F | F | F | F | F | F | F  |
  2   | F | F | F | T | F | T | F | F | F | F | F  |
  3   | F | F | F | F | F | F | F | T | T | F | T  |
  4   | F | F | F | F | F | F | T | F | F | T | F  |
```

**Explanation of belong table:**
- belong[i][j] = T when we use item i-1 to achieve capacity j
- belong[1][2] = T: use item 0 (size 2) to make 2
- belong[2][3] = T: use item 1 (size 3) to make 3
- belong[2][5] = T: use item 1 (size 3) to make 5 (from 2)
- belong[3][7] = T: use item 2 (size 5) to make 7 (from 2)
- belong[3][8] = T: use item 2 (size 5) to make 8 (from 3)
- belong[3][10] = T: use item 2 (size 5) to make 10 (from 5)
- belong[4][6] = T: use item 3 (size 6) to make 6 (from 0)
- belong[4][9] = T: use item 3 (size 6) to make 9 (from 3)

#### Traceback Process

**Instructions:** Show the traceback path through your completed grids by **bolding** or *italicizing* the positions you visit as you trace back from the solution.

**Traceback Steps:**

1. Start at row=4, col=10 (exist[4][10] = T)
2. Check belong[4][10] = F, move up to row=3
3. Check belong[3][10] = T, so **item 2 (size 5) is selected**
   - col = 10 - 5 = 5, row = 2
4. Check belong[2][5] = T, so **item 1 (size 3) is selected**
   - col = 5 - 3 = 2, row = 1
5. Check belong[1][2] = T, so **item 0 (size 2) is selected**
   - col = 2 - 2 = 0, row = 0
6. col = 0, stop

**Positions visited:** *(4,10)* -> **(3,10)** -> **(2,5)** -> **(1,2)** -> (0,0)

**Final Solution:**

- Items in knapsack (by index): **0, 1, 2** (or equivalently: items with sizes 2, 3, 5)
- Total size achieved: **10**
- Wasted space: **0**

---

### 2. Complexity Analysis

#### Time Complexity

**Time complexity in Θ-notation:**

Θ(**n * k**)

**Explanation** (analyze the nested loops in the algorithm):

The `dynamicKnapsack` method contains two main parts:

1. **Base case initialization:** A single loop iterates from j=1 to k, which is O(k)

2. **DP table filling:** Two nested loops:
   - Outer loop: iterates from i=1 to n (n iterations)
   - Inner loop: iterates from j=0 to k (k+1 iterations)
   - Each iteration performs constant-time operations (array accesses, comparisons, assignments)

Total operations: O(k) + O(n * k) = **Θ(n * k)**

The traceback in `findSolution` is O(n) since we visit each row at most once, which is dominated by the DP table construction.

#### Space Complexity

**Space complexity in Θ-notation:**

Θ(**n * k**)

**Explanation:**

The algorithm uses two 2D boolean arrays:
- `exist[n+1][k+1]` - requires (n+1) * (k+1) boolean values
- `belong[n+1][k+1]` - requires (n+1) * (k+1) boolean values

Total space: 2 * (n+1) * (k+1) = **Θ(n * k)**

Additional space for the input array `sizes[n]` is O(n), which is dominated by the 2D arrays.

---

### 3. Algorithm Limitations

#### Question 1: When does this approach become impractical?

For what values of n and k would this dynamic programming approach become impractical?

**Answer:**

The algorithm becomes impractical when the product n * k becomes very large:

1. **Memory constraints:** With n * k cells in each table and two tables, storing billions of boolean values becomes impossible. For example:
   - n = 10,000 items and k = 1,000,000 capacity would require ~20 GB of memory
   - Modern computers typically have 8-64 GB RAM

2. **Time constraints:** Even with O(1) operations per cell, filling n * k cells takes too long when:
   - n * k > 10^9 (takes minutes to hours)
   - n * k > 10^10 (impractical even on fast machines)

**Practical limits:**
- If k is the sum of item sizes (or close to it), and items have large sizes, k can grow exponentially with n
- Typical practical limits: n * k < 10^7 to 10^8 for reasonable performance

#### Question 2: Pseudo-polynomial time

Why is this algorithm considered "pseudo-polynomial" time?

**Answer:**

The algorithm is "pseudo-polynomial" because its time complexity depends on the **numeric value** of the input k, not the **size of the input** (number of bits needed to represent k).

Key distinctions:

1. **Input size:** The capacity k can be represented using log₂(k) bits. For example, k = 1,000,000 requires only 20 bits.

2. **True polynomial time:** A polynomial-time algorithm would have complexity polynomial in the input *size*. If we measure input size properly:
   - n items can be described in O(n log(max_size)) bits
   - k can be described in O(log k) bits

3. **Why pseudo-polynomial:** The algorithm's runtime Θ(n * k) is:
   - Polynomial in the *value* of k
   - **Exponential** in the *number of bits* needed to represent k
   - If k doubles, runtime doubles (linear in value)
   - If we add one bit to k's representation, k doubles, so runtime doubles (exponential in bits)

4. **Practical implication:** The algorithm works well when k is small relative to n, but becomes impractical when k is exponentially large (e.g., when item sizes are large numbers).

This is why the Knapsack problem is NP-complete despite having this "efficient" DP solution - the DP solution is only efficient when k is bounded by a polynomial in n.

---

## Reflection (Optional)

This solution demonstrates the classic trade-off in dynamic programming: we achieve optimal solutions by using additional space to store intermediate results. The algorithm efficiently finds the exact solution but is limited by the capacity value k rather than the number of items n alone.

The distinction between polynomial and pseudo-polynomial time is subtle but important - it explains why NP-complete problems like Knapsack can have "efficient" DP solutions for small input values while remaining computationally hard in general.
