### Problem Statement

Given an `m x n` matrix, if any element is `0`, set its entire **row and column** to `0`.
You must do this **in-place** using **O(1) extra space**.

---

### Example

**Input:**

```
matrix = [
 [1, 1, 1],
 [1, 0, 1],
 [1, 1, 1]
]
```

**Output:**

```
[
 [1, 0, 1],
 [0, 0, 0],
 [1, 0, 1]
]
```

---

## Optimal Approach (O(1) Space)

### Idea / Intuition

You don’t actually need extra arrays to track rows and columns.

Instead:

* Use **first row** and **first column** as markers.
* If `matrix[i][j] == 0`, mark:

  * `matrix[i][0] = 0` (row marker)
  * `matrix[0][j] = 0` (column marker)

But there’s a catch:

* First row and first column overlap at `matrix[0][0]`
* So we use an extra variable to track if **first column** needs to be zeroed

---

### Algorithm Steps

1. Initialize:

   * `col0 = 1` → tracks if first column should be zero

2. First pass (marking phase):

   * Traverse matrix
   * If `matrix[i][j] == 0`:

     * Mark row → `matrix[i][0] = 0`
     * Mark column → `matrix[0][j] = 0`
   * If any `matrix[i][0] == 0`, set `col0 = 0`

3. Second pass (apply changes):

   * Traverse from **bottom-right → top-left**
   * If:

     * `matrix[i][0] == 0` OR `matrix[0][j] == 0`
       → set `matrix[i][j] = 0`
   * Handle first column separately using `col0`

---

### Java Code (Clean, Interview Ready)

```java
class Solution {
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int col0 = 1;

        // Step 1: Mark rows and columns
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) col0 = 0;

            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;  // mark row
                    matrix[0][j] = 0;  // mark column
                }
            }
        }

        // Step 2: Traverse in reverse and update
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 1; j--) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }

            if (col0 == 0) {
                matrix[i][0] = 0;
            }
        }
    }
}
```

---

### Dry Run (Key Insight)

Matrix:

```
[1 1 1]
[1 0 1]
[1 1 1]
```

After marking:

```
[1 0 1]
[0 0 1]
[1 1 1]
```

Final result after second pass:

```
[1 0 1]
[0 0 0]
[1 0 1]
```

---

### Complexity

| Type             | Value    |
| ---------------- | -------- |
| Time Complexity  | O(m × n) |
| Space Complexity | O(1)     |

---

### Key Insights (Interview Gold)

* First row & column act as a **hash map replacement**
* Traverse **backwards** to avoid overwriting markers too early
* `col0` is critical to handle first column separately
* This is a classic **in-place optimization trick**

---

If you want, I can also show:

* Brute → Better → Optimal comparison table
* Common mistakes interviewers expect
* Variation questions on this pattern
