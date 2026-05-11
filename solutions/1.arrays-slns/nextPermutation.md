### Next Permutation (Lexicographically Next Arrangement)

#### Problem Statement

Given an integer array `nums`, rearrange it into the **next lexicographically greater permutation**.
If such arrangement is not possible (i.e., the array is in descending order), rearrange it into the **lowest possible order (ascending)**.

---

#### Example

```
Input:  [1, 2, 3]
Output: [1, 3, 2]

Input:  [3, 2, 1]
Output: [1, 2, 3]

Input:  [1, 1, 5]
Output: [1, 5, 1]
```

---

## Optimal Approach (Single Pass + Reverse)

### Key Idea

We want the **next just-greater permutation**, not any bigger permutation.

Steps:

1. **Find the first decreasing element from right**

   * Traverse from right and find index `i` such that:

     ```
     nums[i] < nums[i+1]
     ```
   * This is the **breakpoint**

2. **If breakpoint exists**

   * Find element just greater than `nums[i]` from right
   * Swap them

3. **Reverse the suffix**

   * Reverse elements from `i+1` to end (to make it smallest)

---

### Why this works

* Right side after breakpoint is always in **descending order**
* Swapping makes it slightly larger
* Reversing makes it the **smallest possible suffix**

---

## Java Code (Clean, Production-Style)

```java
import java.util.*;

class Solution {

    public void nextPermutation(int[] nums) {
        int n = nums.length;

        // Step 1: Find breakpoint
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        // Step 2: If breakpoint exists, find next greater element and swap
        if (i >= 0) {
            int j = n - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }

        // Step 3: Reverse the suffix
        reverse(nums, i + 1, n - 1);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start++, end--);
        }
    }

    // Test
    public static void main(String[] args) {
        Solution sol = new Solution();

        int[][] tests = {
            {1,2,3},
            {3,2,1},
            {1,1,5},
            {1,3,2},
            {2,3,1}
        };

        for (int[] arr : tests) {
            sol.nextPermutation(arr);
            System.out.println(Arrays.toString(arr));
        }
    }
}
```

---

## Dry Run

### Input: `[1, 3, 2]`

| Step              | Array       | Explanation      |
| ----------------- | ----------- | ---------------- |
| Find breakpoint   | i = 0       | `1 < 3`          |
| Find next greater | j = 2       | `2 > 1`          |
| Swap              | `[2, 3, 1]` | swapped 1 & 2    |
| Reverse suffix    | `[2, 1, 3]` | reverse from i+1 |

---

## Edge Case

### Input: `[3,2,1]`

* No breakpoint
* Entire array reversed → `[1,2,3]`

---

## Complexity

| Metric           | Value |
| ---------------- | ----- |
| Time Complexity  | O(n)  |
| Space Complexity | O(1)  |

---

## Key Insight

* Find the **first place where order breaks**
* Swap with **just larger element**
* Reverse the rest to make it **minimal**

---

If you want, I can also give:

* Step-by-step visualization (like tree/decision flow)
* Brute force (generate all permutations)
* Interview traps & common mistakes (very important for this problem)
