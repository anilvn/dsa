## Problem Statement

Given an array of integers, move all `0`s to the end while maintaining the relative order of non-zero elements. The operation should ideally be done **in-place**.

---

## Example

**Input:**
`nums = [0, 1, 0, 3, 12]`

**Output:**
`[1, 3, 12, 0, 0]`

---

## Approaches

---

### 1. Brute Force (Using Extra Array)

#### a) Idea / Intuition

Create a new array, first store all non-zero elements, then append zeros at the end.

#### b) Algorithm Steps

1. Create a new array `temp` of same size.
2. Traverse original array and copy all non-zero elements to `temp`.
3. Count zeros and fill remaining positions with `0`.
4. Copy `temp` back to original array.

#### c) Java Code

```java
class Solution {
    public void moveZeroes(int[] nums) {
        int n = nums.length;
        int[] temp = new int[n];
        int index = 0;

        for (int num : nums) {
            if (num != 0) {
                temp[index++] = num;
            }
        }

        while (index < n) {
            temp[index++] = 0;
        }

        for (int i = 0; i < n; i++) {
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

* Simple and intuitive
* Not in-place (extra space used)
* Not preferred in interviews when in-place is required

---

### 2. Better Approach (Two Pass In-Place)

#### a) Idea / Intuition

Shift all non-zero elements forward, then fill remaining positions with zeros.

#### b) Algorithm Steps

1. Maintain index `k = 0`
2. Traverse array, place non-zero elements at `nums[k++]`
3. Fill remaining indices with `0`

#### c) Java Code

```java
class Solution {
    public void moveZeroes(int[] nums) {
        int k = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[k++] = nums[i];
            }
        }

        while (k < nums.length) {
            nums[k++] = 0;
        }
    }
}
```

#### d) Time Complexity

O(n)

#### e) Space Complexity

O(1)

#### f) Notes

* In-place solution
* Maintains order
* Slightly inefficient due to second pass

---

### 3. Optimal Approach (Two Pointers - Single Pass)

#### a) Idea / Intuition

Use two pointers:

* `i` scans array
* `j` tracks position to place next non-zero

Swap when needed to reduce writes.

#### b) Algorithm Steps

1. Initialize `j = 0`
2. Traverse `i` from 0 to n-1
3. If `nums[i] != 0`, swap `nums[i]` with `nums[j]`
4. Increment `j`

#### c) Java Code

```java
class Solution {
    public void moveZeroes(int[] nums) {
        int j = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                j++;
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

* Best and most asked approach
* Minimizes unnecessary writes
* Maintains relative order
* Preferred in interviews

---

### 4. Snowball Approach (Less Common Optimization)

#### a) Idea / Intuition

Track number of zeros seen so far (`snowball size`). Shift elements left by that count.

#### b) Algorithm Steps

1. Initialize `snowball = 0`
2. Traverse array
3. If element is `0`, increment snowball
4. If non-zero and snowball > 0, shift element left by snowball and put zero at current index

#### c) Java Code

```java
class Solution {
    public void moveZeroes(int[] nums) {
        int snowball = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                snowball++;
            } else if (snowball > 0) {
                nums[i - snowball] = nums[i];
                nums[i] = 0;
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

* Fewer swaps than two-pointer method
* Useful when minimizing writes is important
* Slightly harder to think of in interviews

---

## Comparison Table

| Approach          | Time Complexity | Space Complexity | Difficulty | When to Use                     |
| ----------------- | --------------- | ---------------- | ---------- | ------------------------------- |
| Brute Force       | O(n)            | O(n)             | Easy       | When space is not a concern     |
| Two Pass In-Place | O(n)            | O(1)             | Easy       | Simple in-place solution        |
| Two Pointer Swap  | O(n)            | O(1)             | Medium     | Best general interview solution |
| Snowball Approach | O(n)            | O(1)             | Medium     | When minimizing writes          |

---

## Key Insight

The core idea is to maintain the relative order of non-zero elements while pushing zeros to the end. This can be efficiently achieved using two pointers or tracking the count of zeros encountered so far.

---

## Dry Run (Optimal Approach)

| i | nums[i] | j | Array State  |
| - | ------- | - | ------------ |
| 0 | 0       | 0 | [0,1,0,3,12] |
| 1 | 1       | 0 | [1,0,0,3,12] |
| 2 | 0       | 1 | [1,0,0,3,12] |
| 3 | 3       | 1 | [1,3,0,0,12] |
| 4 | 12      | 2 | [1,3,12,0,0] |

---

## Edge Cases

* All zeros → `[0,0,0]`
* No zeros → `[1,2,3]`
* Single element → `[0]`, `[1]`
* Already sorted with zeros at end

---

## Common Mistakes

* Not maintaining order of non-zero elements
* Using extra space when in-place is required
* Incorrect swap logic leading to data loss
* Forgetting to handle edge cases like empty array
