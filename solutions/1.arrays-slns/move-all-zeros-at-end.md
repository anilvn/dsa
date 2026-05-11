Another classic two-pointer problem—very similar thinking to “remove duplicates”, but with a twist: you must **preserve the relative order of non-zero elements**.

---

# Problem Statement

Given an array, move all `0`s to the end while maintaining the order of non-zero elements.

* Do it **in-place**
* Minimize operations

---

# Example

```text
Input:  [0,1,0,3,12]
Output: [1,3,12,0,0]
```

---

# Key Insight

We don’t care about zeros’ positions—we care about:

> placing all **non-zero elements forward in order**, then filling remaining with zeros.

---

# Optimal Approach (Two Pointers)

## Idea

* `i` → position to place next non-zero
* `j` → scans the array
* Whenever you see a non-zero → move it to index `i`

---

## Algorithm

1. Initialize `i = 0`
2. Traverse `j = 0 → n-1`
3. If `nums[j] != 0`

   * swap `nums[i]` and `nums[j]`
   * increment `i`

---

## Java Code (Best – In-Place Swap)

```java
class Solution {
    public void moveZeroes(int[] nums) {
        int i = 0;

        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != 0) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                i++;
            }
        }
    }
}
```

---

## Dry Run

```text
nums = [0,1,0,3,12]

i=0

j=0 → 0 → skip  
j=1 → 1 → swap(0,1) → [1,0,0,3,12], i=1  
j=2 → 0 → skip  
j=3 → 3 → swap(1,3) → [1,3,0,0,12], i=2  
j=4 → 12 → swap(2,4) → [1,3,12,0,0], i=3
```

---

# Alternative Approach (Less Swaps)

## Idea

* First pass → copy all non-zero elements
* Second pass → fill zeros

---

## Java Code

```java
class Solution {
    public void moveZeroes(int[] nums) {
        int i = 0;

        // place non-zero elements
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != 0) {
                nums[i++] = nums[j];
            }
        }

        // fill zeros
        while (i < nums.length) {
            nums[i++] = 0;
        }
    }
}
```

---

## Why This is Better Sometimes

* Fewer swaps
* Better when writes are cheaper than swaps

---

# Complexity

| Metric | Value |
| ------ | ----- |
| Time   | O(n)  |
| Space  | O(1)  |

---

# Edge Cases

* All zeros → unchanged
* No zeros → unchanged
* Single element → unchanged
* Large array → still linear

---

# Common Mistakes

* Using extra array (not in-place)
* Not maintaining order
* Swapping unnecessarily

---

# Interview Insight

This problem tests:

* Two-pointer pattern mastery
* In-place overwrite vs swap decision
* Stability (order preservation)

---

# Pattern Connection

This belongs to the same family as:

* Remove duplicates from sorted array
* Remove element
* Partition array problems

---

If you want, I can next give:

* “Move all negatives to one side”
* “Sort 0s, 1s, 2s (Dutch National Flag)”
* Harder variant: “Partition array around pivot”
