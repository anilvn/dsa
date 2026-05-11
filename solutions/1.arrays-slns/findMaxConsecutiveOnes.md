Another very common linear-scan problem—tests whether you can track a **running streak** and update a global maximum.

---

# Problem Statement

Given a binary array (`0`s and `1`s), find the **maximum number of consecutive 1s**.

---

# Example

```text id="q0b6ch"
Input:  [1,1,0,1,1,1]
Output: 3
```

```text id="x2drgb"
Input:  [1,0,1,1,0,1]
Output: 2
```

---

# Key Insight

You only need:

* `count` → current streak of 1s
* `max` → best streak seen so far

Whenever you see:

* `1` → increase streak
* `0` → reset streak

---

# Optimal Approach (Single Pass)

## Algorithm

1. Initialize `count = 0`, `max = 0`
2. Traverse array
3. If `nums[i] == 1`

   * `count++`
   * update `max = max(max, count)`
4. Else

   * reset `count = 0`

---

## Java Code

```java id="vlzz6c"
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int count = 0;
        int max = 0;

        for (int num : nums) {
            if (num == 1) {
                count++;
                max = Math.max(max, count);
            } else {
                count = 0;
            }
        }

        return max;
    }
}
```

---

# Dry Run

```text id="knu67w"
nums = [1,1,0,1,1,1]

count=0, max=0

1 → count=1 → max=1  
1 → count=2 → max=2  
0 → count=0  
1 → count=1  
1 → count=2  
1 → count=3 → max=3
```

---

# Complexity

| Metric | Value |
| ------ | ----- |
| Time   | O(n)  |
| Space  | O(1)  |

---

# Edge Cases

* All 1s → return n
* All 0s → return 0
* Single element
* Empty array

---

# Follow-Up Variants (Important)

## 1. Max consecutive ones with at most K zeros allowed

→ Sliding window problem

---

## 2. Flip one zero to maximize ones

```text id="pzvtgn"
Input:  [1,0,1,1,0]
Output: 4
```

→ Also sliding window

---

## 3. Longest subarray of 1s after deleting one element

---

# Interview Insight

This problem tests:

* Tracking **local vs global state**
* Reset conditions
* Clean linear traversal

---

# Pattern Connection

This is part of:

* Sliding window basics
* Longest subarray problems
* Kadane-style thinking (continuous segments)

---

If you want next:

* Sliding window version (very important upgrade)
* Maximum subarray sum (Kadane’s Algorithm)
* Longest substring problems (string version of same pattern)
