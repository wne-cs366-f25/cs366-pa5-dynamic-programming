# CS 366 - PA5: Dynamic Programming - Knapsack

**Due Date:** November 20, 2025, 11:59 PM

This assignment will help you understand how to solve the simplified knapsack problem using dynamic programming (bottom-up approach).

---

## Background

The **Simplified Knapsack Problem** is a variant where items only have sizes (weights), and the goal is to maximize the total size of items that fit in the knapsack without exceeding its capacity. This is equivalent to minimizing wasted space.

**Formal Problem Statement:**

Given:

- A set of n items with sizes S[0], S[1], ..., S[n-1]
- A knapsack with capacity k

Find:

- A subset of items such that:
  - The total size does not exceed k
  - The total size is maximized (least amount of wasted space)

**Note:** Each item appears exactly once in the array. If there are multiple items of the same size, they appear as separate entries (e.g., [7, 5, 4, 2, 4, 5, 4] has three items of size 4 and two items of size 5).

---

## Assignment Tasks

### Part 1: Java Implementation

Implement the simplified knapsack problem using the **bottom-up dynamic programming** approach covered in class.

**Your program must:**

1. **Read input from two sources:**

   - From **standard input**: Read two integers:
     - `n` = number of items
     - `k` = knapsack capacity
   - From a **file** (filename provided as command-line argument): Read n integers representing item sizes

2. **Implement the `dynamicKnapsack` algorithm** using the `exist` and `belong` arrays:

   - `exist[i][j]` = true if there exists a subset of the first i items whose sizes add up to exactly j
   - `belong[i][j]` = true if item i is included in the solution for subproblem (i, j)

3. **Implement a traceback algorithm** to reconstruct which items were selected:

   - Start at row n, find the largest column j ≤ k where `exist[n][j]` is true
   - Use the `belong` array to determine which items are in the solution
   - Follow the algorithm structure discussed in class (see handout page 5)

4. **Output the following:**
   - List of selected items with their indices and sizes
   - Total size of all selected items
   - Amount of wasted space (k - total size)

**Input Format:**

Standard input:

```
n
k
```

File format (one integer per line):

```
size[0]
size[1]
...
size[n-1]
```

**Output Format:**

```
Dynamic Programming Solution:
Item <index>, size = <size>
Item <index>, size = <size>
...
Total size = <sum>
Wasted space = <k - sum>
```

**Example:**

Input (stdin):

```
5
16
```

Input file (items.txt):

```
2
3
7
5
6
```

Output:

```
Dynamic Programming Solution:
Item 4, size = 6
Item 3, size = 5
Item 2, size = 3
Item 0, size = 2
Total size = 16
Wasted space = 0
```

---

### Part 2: Written Analysis

Answer the following questions in a separate document (ANALYSIS.md, PDF or paper submission):

1. **Manual Trace**

   - Trace the following instance of the `dynamicKnapsack` algorithm by filling the T/F values in two 11x5 grids (with room for column/row labels) for `exist` and `belong`

     - n = 4 (number of items)
     - k = 10 (knapsack capacity)
     - S = [2, 3, 5, 6] (item sizes, using 1-indexed notation: S[1]=2, S[2]=3, S[3]=5, S[4]=6)

   - After completing the grids, show the **traceback process**:
     - Starting position: row = _, column = _\*\*
     - List each step of the traceback showing which items are selected
     - Final solution: Which items (by index) are in the knapsack?

2. **Complexity**

   - Write the space complexity in Θ-notation
   - Write the time complexity of the in Θ-notation as a function of n and k
   - Explain your reasoning by analyzing the nested loops in the algorithm

3. **Algorithm Limitations**

   - For what values of n and k would this dynamic programming approach become impractical?
   - Why is this algorithm considered "pseudo-polynomial" time?

---

## Starter Code

You are provided with:

- `Knapsack.java` - Main program structure with method stubs
- `items1.txt`, `items2.txt`, `items3.txt` - Sample input files
- `ANALYSIS.md` - Template for your written analysis answers
- `QUICKSTART.md` - Guide for building and running your code
- `README.md` - This file

**You must implement:**

- `dynamicKnapsack()` method - The core DP algorithm
- `findSolution()` method - Traceback algorithm to reconstruct the solution

---

## Submission Requirements

1. **Source Code:**

   - `Knapsack.java` (your completed implementation)
   - Include proper documentation:
     - Header comment with your name, date, and assignment title
     - Method comments describing purpose, parameters, and return values
     - Inline comments for non-obvious code sections

2. **Written Analysis:**

   - `ANALYSIS.md` or `ANALYSIS.PDF` or paper submission
   - Answers to all questions in Part 2

## Submission Process

```bash
tar -czf pa5-YOURNAME.zip /workspace
```

Download the file from the container and upload to Kodiak.

## Grading Criteria

- **Submission (33.3%):** Complete submission with all required files and components
- **Completeness (33.3%):** All methods implemented, all analysis problems attempted
- **Correctness (33.3%):** Accurate implementations, passing tests, and sound mathematical analysis

**Due Date:** November 20 by 11:59 PM

## **Late Policy:** 10% per day, maximum 5 days late

---

_Course content developed by Declan Gray-Mullen for WNEU with Claude_
