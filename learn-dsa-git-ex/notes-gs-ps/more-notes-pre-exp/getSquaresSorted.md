


## getSquaresSorted

### 🔑 Idea:

* The array is **already sorted**, but negatives will mess up order when squared.
* The **largest square** will come from either the **leftmost (largest negative)** or the **rightmost (largest positive)** element.
* Use **two pointers**:

  * `left = 0`
  * `right = arr.length - 1`
* Compare `abs(arr[left])` and `abs(arr[right])`.
* Place the larger square at the **end** of the result array and move the pointer.

---

### ✅ O(n) Two-Pointer Solution

```java
public int[] getSquaresSorted(int[] arr) {
    int n = arr.length;
    int[] result = new int[n];
    int left = 0, right = n - 1;
    int pos = n - 1; // fill from the end
    
    while (left <= right) {
        int leftSq = arr[left] * arr[left];
        int rightSq = arr[right] * arr[right];
        
        if (leftSq > rightSq) {
            result[pos--] = leftSq;
            left++;
        } else {
            result[pos--] = rightSq;
            right--;
        }
    }
    return result;
}
```

---

### 🏃 Dry Run

Input: `[-3, -2, -1, 1, 2, 4, 6]`

| Left   | Right | Left² | Right² | Pick | Result\[] (from end)           |
| ------ | ----- | ----- | ------ | ---- | ------------------------------ |
| -3     | 6     | 9     | 36     | 36   | \[ \_, \_, \_, \_, \_, \_, 36] |
| -3     | 4     | 9     | 16     | 16   | \[ \_, \_, \_, \_, \_, 16, 36] |
| -3     | 2     | 9     | 4      | 9    | \[ \_, \_, \_, \_, 9, 16, 36]  |
| -2     | 2     | 4     | 4      | 4    | \[ \_, \_, \_, 4, 9, 16, 36]   |
| -1     | 2     | 1     | 4      | 4    | \[ \_, \_, 4, 4, 9, 16, 36]    |
| -1     | 1     | 1     | 1      | 1    | \[ \_, 1, 4, 4, 9, 16, 36]     |
| (done) |       |       |        |      | \[1, 1, 4, 9, 16, 36]          |

Output ✅: `[1, 1, 4, 9, 16, 36]`

---

⏱ **Time Complexity**: O(n)
📦 **Space Complexity**: O(n) (extra array for results)

---
