## Problem Statement

Sort an array containing only `0`, `1`, and `2` in-place such that all `0`s come first, followed by `1`s, then `2`s.

---

## Example

**Input:**
`[2, 0, 2, 1, 1, 0]`

**Output:**
`[0, 0, 1, 1, 2, 2]`

---

## Optimal Approach (Dutch National Flag Algorithm)

### a) Idea / Intuition

Maintain three regions using pointers:

* `low` → next position for `0`
* `mid` → current element
* `high` → next position for `2`

**Regions during execution:**

* `[0 … low-1]` → all `0`s
* `[low … mid-1]` → all `1`s
* `[mid … high]` → unknown
* `[high+1 … n-1]` → all `2`s

---

### b) Algorithm Steps

1. Initialize `low = 0`, `mid = 0`, `high = n - 1`
2. While `mid <= high`:

   * If `nums[mid] == 0`:

     * Swap `low` and `mid`
     * `low++`, `mid++`
   * Else if `nums[mid] == 1`:

     * `mid++`
   * Else (`nums[mid] == 2`):

     * Swap `mid` and `high`
     * `high--`
3. End when `mid > high`

---

### c) Java Code

```java
class Solution {
    public void sortColors(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                int temp = nums[low];
                nums[low] = nums[mid];
                nums[mid] = temp;
                low++;
                mid++;
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                int temp = nums[mid];
                nums[mid] = nums[high];
                nums[high] = temp;
                high--;
            }
        }
    }
}
```

---

### d) Time Complexity

O(n)

### e) Space Complexity

O(1)

---

### f) Notes

* Single pass solution
* In-place sorting
* Most optimal and interview-preferred
* Key trick: **do not increment `mid` when swapping with `high`**

---

## Visualization (Step-by-Step Dry Run)

**Array:** `[2, 0, 2, 1, 1, 0]`

| Step | low | mid | high | Array State   | Action         |
| ---- | --- | --- | ---- | ------------- | -------------- |
| 1    | 0   | 0   | 5    | [2,0,2,1,1,0] | swap(mid,high) |
| 2    | 0   | 0   | 4    | [0,0,2,1,1,2] | swap(low,mid)  |
| 3    | 1   | 1   | 4    | [0,0,2,1,1,2] | swap(low,mid)  |
| 4    | 2   | 2   | 4    | [0,0,2,1,1,2] | swap(mid,high) |
| 5    | 2   | 2   | 3    | [0,0,1,1,2,2] | mid++          |
| 6    | 2   | 3   | 3    | [0,0,1,1,2,2] | mid++          |

Final: `[0,0,1,1,2,2]`

---

## Visual Zones Understanding

At any step:

```
[ 0-zone ][ 1-zone ][ unknown ][ 2-zone ]
   low        mid              high
```

Example mid-step:

```
[0,0 | 1 | 2,1 | 2,2]
       ↑     ↑
      mid   high
```

---

## Key Insight

You are not sorting — you are **partitioning into 3 groups in one pass**.
The trick is handling `2`s carefully by rechecking swapped elements.

---

## Common Mistakes

* Incrementing `mid` after swapping with `high`
* Not understanding the 4 regions
* Treating it like normal sorting instead of partitioning

---

## Quick Mental Model

Think of it as:

* Push `0` to left
* Push `2` to right
* Let `1` stay in middle

---

If you want, I can animate it step-by-step for another example you give.

<br/>
<br/>

## Problem Statement

Sort an array containing only `0`, `1`, and `2` in-place so that all `0`s come first, followed by `1`s, then `2`s.

---

## Example

**Input:**
`[2, 0, 2, 1, 1, 0]`

**Output:**
`[0, 0, 1, 1, 2, 2]`

---

## Approaches

---

### 1. Brute Force (Sorting)

#### a) Idea / Intuition

Use a standard sorting algorithm to sort the array.

#### b) Algorithm Steps

1. Call built-in sort on the array
2. Return sorted array

#### c) Java Code

```java id="c3p8yd"
import java.util.*;

class Solution {
    public void sortColors(int[] nums) {
        Arrays.sort(nums);
    }
}
```

#### d) Time Complexity

O(n log n)

#### e) Space Complexity

O(1) or O(log n) (depends on sort implementation)

#### f) Notes

* Simplest approach
* Ignores problem constraints
* Not preferred in interviews

---

### 2. Better Approach (Counting Sort)

#### a) Idea / Intuition

Count number of `0`s, `1`s, and `2`s, then overwrite array.

#### b) Algorithm Steps

1. Initialize `count0`, `count1`, `count2`
2. Traverse array and count occurrences
3. Fill array with `0`s, then `1`s, then `2`s

#### c) Java Code

```java id="l0s2cw"
class Solution {
    public void sortColors(int[] nums) {
        int count0 = 0, count1 = 0, count2 = 0;

        for (int num : nums) {
            if (num == 0) count0++;
            else if (num == 1) count1++;
            else count2++;
        }

        int index = 0;

        while (count0-- > 0) nums[index++] = 0;
        while (count1-- > 0) nums[index++] = 1;
        while (count2-- > 0) nums[index++] = 2;
    }
}
```

#### d) Time Complexity

O(n)

#### e) Space Complexity

O(1)

#### f) Notes

* Two-pass solution
* Very easy to implement
* Does not preserve original positions (not stable concern here)
* Good fallback if DNF not remembered

---

### 3. Better Approach (Using Extra Array)

#### a) Idea / Intuition

Create a new array and place elements in sorted order using counts.

#### b) Algorithm Steps

1. Count occurrences of 0,1,2
2. Create new array
3. Fill values in sorted order
4. Copy back

#### c) Java Code

```java id="7uj0xq"
class Solution {
    public void sortColors(int[] nums) {
        int[] count = new int[3];

        for (int num : nums) {
            count[num]++;
        }

        int[] temp = new int[nums.length];
        int index = 0;

        for (int i = 0; i < 3; i++) {
            while (count[i]-- > 0) {
                temp[index++] = i;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            nums[i] = temp[i];
        }
    }
}
```

#### d) Time Complexity

O(n)

#### e) Space Complexity

O(n)

#### f) Notes

* Easier to visualize
* Not in-place
* Rarely preferred

---

### 4. Optimal Approach (Dutch National Flag)

#### a) Idea / Intuition

Partition into 3 regions using 3 pointers.

#### b) Algorithm Steps

1. `low = 0`, `mid = 0`, `high = n-1`
2. Process based on value at `mid`
3. Expand regions dynamically

#### c) Java Code

```java id="l6uhya"
class Solution {
    public void sortColors(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                int temp = nums[low];
                nums[low++] = nums[mid];
                nums[mid++] = temp;
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                int temp = nums[mid];
                nums[mid] = nums[high];
                nums[high--] = temp;
            }
        }
    }
}
```

#### d) Time Complexity

O(n)

#### e) Space Complexity

O(1)

#### f) Notes

* Single pass
* Most optimal
* Slightly tricky logic

---

### 5. Two-Pass Partition (0s then 1s)

#### a) Idea / Intuition

First move all `0`s to front, then move all `1`s after them.

#### b) Algorithm Steps

1. First pass: move all `0`s to front
2. Second pass: move all `1`s after zeros
3. Remaining will be `2`s

#### c) Java Code

```java id="j9rm5z"
class Solution {
    public void sortColors(int[] nums) {
        int index = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                int temp = nums[i];
                nums[i] = nums[index];
                nums[index++] = temp;
            }
        }

        for (int i = index; i < nums.length; i++) {
            if (nums[i] == 1) {
                int temp = nums[i];
                nums[i] = nums[index];
                nums[index++] = temp;
            }
        }
    }
}
```

#### d) Time Complexity

O(n)

#### e) Space Complexity

O(1)

#### f) Notes

* Easier than DNF
* Two passes required
* Slightly more swaps

---

## Comparison Table

| Approach            | Time Complexity | Space Complexity | Difficulty  | When to Use               |
| ------------------- | --------------- | ---------------- | ----------- | ------------------------- |
| Built-in Sort       | O(n log n)      | O(1)             | Easy        | Quick solution            |
| Counting Sort       | O(n)            | O(1)             | Easy        | Simple logic              |
| Extra Array         | O(n)            | O(n)             | Easy        | Visualization             |
| Dutch National Flag | O(n)            | O(1)             | Medium      | Best solution             |
| Two-Pass Partition  | O(n)            | O(1)             | Easy-Medium | Easier alternative to DNF |

---

## Key Insight

This problem is about **partitioning**, not sorting.
The optimal solution (DNF) efficiently divides the array into three regions in a single traversal.

---

## Edge Cases

* All same elements → `[0,0,0]`, `[1,1]`
* Already sorted
* Reverse sorted
* Single element

---

## Common Mistakes

* Treating it like general sorting
* Wrong pointer updates in DNF
* Extra swaps in two-pass approaches
* Forgetting in-place requirement

---

If you want, I can compare **Move Zeroes vs Dutch Flag** since both use similar two-pointer thinking — that’s a common interview twist.
