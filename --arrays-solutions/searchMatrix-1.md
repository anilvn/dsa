## Problem Statement

Given an `m x n` matrix where:

* Each row is sorted in non-decreasing order
* The first element of each row is greater than the last element of the previous row

Return `true` if `target` exists in the matrix, otherwise return `false`.
Required time complexity: `O(log(m * n))`.

---

## Example

**Input:**

```
matrix = [
  [1, 3, 5, 7],
  [10,11,16,20],
  [23,30,34,60]
]
target = 3
```

**Output:**

```
true
```

---

## Approaches

---

### 1) Brute Force

#### a) Idea / Intuition

Check every element in the matrix.

#### b) Algorithm Steps

1. Traverse each row
2. Traverse each column
3. If element equals target → return true
4. After full traversal → return false

#### c) Java Code

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == target) return true;
            }
        }
        return false;
    }
}
```

#### d) Time Complexity

O(m * n)

#### e) Space Complexity

O(1)

#### f) Notes

* Simple but inefficient
* Not suitable for large inputs
* Ignores sorted structure

---

### 2) Row-wise Binary Search

#### a) Idea / Intuition

Each row is sorted → apply binary search on every row.

#### b) Algorithm Steps

1. For each row:

   * Apply binary search
2. If found → return true
3. Else return false

#### c) Java Code

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        for (int[] row : matrix) {
            int left = 0, right = row.length - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;

                if (row[mid] == target) return true;
                else if (row[mid] < target) left = mid + 1;
                else right = mid - 1;
            }
        }
        return false;
    }
}
```

#### d) Time Complexity

O(m * log n)

#### e) Space Complexity

O(1)

#### f) Notes

* Uses row sorting only
* Still not optimal
* Does not use global ordering property

---

### 3) Two-Phase Binary Search (Better)

#### a) Idea / Intuition

First find the correct row using binary search, then search inside that row.

#### b) Algorithm Steps

1. Binary search on rows:

   * Find row where `target` could exist
2. Apply binary search on that row

#### c) Java Code

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;

        int top = 0, bottom = m - 1;

        while (top <= bottom) {
            int mid = top + (bottom - top) / 2;

            if (target > matrix[mid][n - 1]) {
                top = mid + 1;
            } else if (target < matrix[mid][0]) {
                bottom = mid - 1;
            } else {
                return binarySearch(matrix[mid], target);
            }
        }

        return false;
    }

    private boolean binarySearch(int[] row, int target) {
        int left = 0, right = row.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (row[mid] == target) return true;
            else if (row[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }
}
```

#### d) Time Complexity

O(log m + log n)

#### e) Space Complexity

O(1)

#### f) Notes

* Efficient and intuitive
* Separates concerns (row + column search)
* Equivalent to optimal complexity

---

### 4) Optimal Approach (Single Binary Search on Virtual Array)

#### a) Idea / Intuition

Treat matrix as a flattened sorted array using index mapping.

#### b) Algorithm Steps

1. Set `low = 0`, `high = m*n - 1`
2. While `low <= high`:

   * Compute `mid`
   * Map to 2D:

     * `row = mid / n`
     * `col = mid % n`
   * Compare value
3. Return result

#### c) Java Code

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;

        int low = 0, high = m * n - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            int row = mid / n;
            int col = mid % n;

            int val = matrix[row][col];

            if (val == target) return true;
            else if (val < target) low = mid + 1;
            else high = mid - 1;
        }

        return false;
    }
}
```

#### d) Time Complexity

O(log(m * n))

#### e) Space Complexity

O(1)

#### f) Notes

* Best and most elegant solution
* Uses full sorted property
* Avoids extra binary search step

---

## Comparison Table

| Approach                    | Time Complexity  | Space Complexity | Difficulty | When to Use                    |
| --------------------------- | ---------------- | ---------------- | ---------- | ------------------------------ |
| Brute Force                 | O(m * n)         | O(1)             | Easy       | Small inputs                   |
| Row-wise Binary Search      | O(m log n)       | O(1)             | Easy       | When only row sorting is known |
| Two-Phase Binary Search     | O(log m + log n) | O(1)             | Medium     | Clear logical separation       |
| Virtual Array Binary Search | O(log(m * n))    | O(1)             | Medium     | Best choice for this problem   |

---

## Key Insight

* The matrix behaves like a **fully sorted 1D array**.
* This allows applying **binary search directly using index mapping**.
* The trick is converting 1D index → 2D coordinates efficiently.

---

## Edge Cases

* Single row or single column
* Target smaller than first element
* Target larger than last element
* Matrix with one element

---

## Common Mistakes

* Using approach from problem 240 (wrong pattern)
* Forgetting index mapping (`mid / n`, `mid % n`)
* Incorrect row selection in two-phase approach
