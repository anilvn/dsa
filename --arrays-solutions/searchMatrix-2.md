Here’s the **clean interview-ready solution** for **240. Search a 2D Matrix II** using the **optimal approach**.

---

## Optimal Approach (Top-Right Traversal)

### Idea / Intuition

Start from **top-right corner**:

* Moving **left → smaller values**
* Moving **down → larger values**

So at each step, you can eliminate **one row or one column**.

---

### Algorithm Steps

1. Start at `(row = 0, col = n - 1)`
2. While `row < m` and `col >= 0`:

   * If `matrix[row][col] == target` → return true
   * If `matrix[row][col] > target` → move left (`col--`)
   * Else → move down (`row++`)
3. Return false

---

### Java Code (Minimal + Clean)

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;

        int row = 0;
        int col = n - 1;

        while (row < m && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                col--;
            } else {
                row++;
            }
        }

        return false;
    }
}
```

---

### Complexity

* **Time:** `O(m + n)`
* **Space:** `O(1)`

---

## Why This Works (Interview Insight)

At any position:

* Everything **below** is larger
* Everything **left** is smaller

So each comparison removes:

* One **column** (if too big)
* OR one **row** (if too small)

That’s why it’s linear, not `m*n`.

---

## Edge Cases to Mention in Interview

* Single row / single column
* Target smaller than smallest element
* Target larger than largest element
* Empty matrix (if interviewer includes it)

---

## Alternate Start Point

You can also start from **bottom-left**:

* Up → smaller
* Right → larger

Same complexity, same logic.

---

If you want, I can give:

* Dry run table step-by-step
* Trick to derive this approach in interview (important)
* Compare with “Search a 2D Matrix I” (binary search case)
