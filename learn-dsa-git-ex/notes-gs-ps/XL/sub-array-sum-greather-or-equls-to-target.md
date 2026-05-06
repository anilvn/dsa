Got it 👍
You want **the smallest subarray whose sum ≥ target** in **Java** with **multiple approaches** (brute force → optimized). I’ll walk you through step by step with code, explanation, and time/space complexity notes.

---

## ✅ Problem Restatement

Given an array of positive integers `arr[]` and an integer `target`, find the length of the **smallest subarray** whose sum is **≥ target**. If no such subarray exists, return `0`.

---

# 🔹 Approach 1: Brute Force (Check All Subarrays)

```java
public class MinSubarraySum {
    // Brute Force O(n^2)
    public static int minSubArrayLenBrute(int target, int[] nums) {
        int n = nums.length;
        int minLen = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += nums[j];
                if (sum >= target) {
                    minLen = Math.min(minLen, j - i + 1);
                    break; // stop inner loop, no need to expand further
                }
            }
        }

        return (minLen == Integer.MAX_VALUE) ? 0 : minLen;
    }

    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 2, 4, 3};
        int target = 7;
        System.out.println(minSubArrayLenBrute(target, arr)); // Output: 2
    }
}
```

### 📌 Notes

* **Time Complexity:** `O(n^2)` (nested loops).
* **Space Complexity:** `O(1)` (only variables).

---

# 🔹 Approach 2: Prefix Sum + Binary Search

👉 Idea:

1. Compute prefix sums.
2. For each start index, binary search for the smallest `end` where sum ≥ target.

```java
import java.util.*;

public class MinSubarraySum {
    // Prefix + Binary Search O(n log n)
    public static int minSubArrayLenPrefix(int target, int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n + 1];
        
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        
        int minLen = Integer.MAX_VALUE;
        
        for (int i = 0; i < n; i++) {
            int toFind = prefix[i] + target;
            int bound = Arrays.binarySearch(prefix, toFind);
            
            if (bound < 0) {
                bound = -bound - 1; // insertion point
            }
            
            if (bound <= n) {
                minLen = Math.min(minLen, bound - i);
            }
        }
        
        return (minLen == Integer.MAX_VALUE) ? 0 : minLen;
    }

    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 2, 4, 3};
        int target = 7;
        System.out.println(minSubArrayLenPrefix(target, arr)); // Output: 2
    }
}
```

### 📌 Notes

* **Time Complexity:** `O(n log n)` (binary search for each index).
* **Space Complexity:** `O(n)` (prefix sum array).

---

# 🔹 Approach 3: Sliding Window (Optimized)

👉 Works because array contains **positive integers**.

* Expand `right` until sum ≥ target.
* Shrink from `left` while still ≥ target.
* Track minimum length.

```java
public class MinSubarraySum {
    // Sliding Window O(n)
    public static int minSubArrayLenSlidingWindow(int target, int[] nums) {
        int n = nums.length;
        int left = 0, sum = 0, minLen = Integer.MAX_VALUE;

        for (int right = 0; right < n; right++) {
            sum += nums[right];

            while (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }

        return (minLen == Integer.MAX_VALUE) ? 0 : minLen;
    }

    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 2, 4, 3};
        int target = 7;
        System.out.println(minSubArrayLenSlidingWindow(target, arr)); // Output: 2
    }
}
```

### 📌 Notes

* **Time Complexity:** `O(n)` (each element visited at most twice).
* **Space Complexity:** `O(1)` (just counters).
* ✅ **Most efficient approach** for positive integers.

---

## 🔎 Summary

| Approach                   | Time Complexity | Space Complexity | Notes                      |
| -------------------------- | --------------- | ---------------- | -------------------------- |
| Brute Force                | O(n²)           | O(1)             | Try all subarrays          |
| Prefix Sum + Binary Search | O(n log n)      | O(n)             | Good for mixed arrays      |
| Sliding Window             | O(n)            | O(1)             | Best for positive integers |

---

👉 Do you also want me to extend this solution for **arrays that can contain negative numbers** (where sliding window won’t work)?
