```java
import java.util.*;

public class SolutionBruteForce {

    static int[] longestUniformSubstring(String input) {
        if (input == null || input.isEmpty()) return new int[]{-1, 0};

        int n = input.length();
        int longestStart = -1;
        int longestLength = 0;

     
        int start = 0;
        while(start < n){   
            int end = start+1;
            while (end < n && input.charAt(start) == input.charAt(end) && input.charAt(start) == '1'){    
                if(end-start+1 > longestLength){
                    longestLength = end-start+1 ;
                    longestStart = start;
                }
                end++;
            }
            start = end;
        }

        return new int[]{longestStart, longestLength};
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(longestUniformSubstring("00111110111"))); // [1, 3]
    }
}
```

# 📌 Problem Statement (clear explanation)

You are given:

* A binary array `a[]` (only `0` and `1` values).
* An integer `K` (the maximum number of zeros you can flip to `1`).

👉 Task: Find the **maximum length of a contiguous subarray** containing only `1`s if you are allowed to flip at most `K` zeros.

⚡ Key: You don’t have to flip all zeros, only up to `K`. The subarray must be contiguous.

---

# 📌 Examples

### Example 1

Input:

```
a = [1,0,1,0,1,0,0,0,1,1], K = 2
```

Output:

```
6
```

Explanation: Flip 2 zeros (say at index 1 and 3) → array looks like `[1,1,1,1,1,0,0,0,1,1]`.
Longest streak of `1`s = **6** (indices 0–4 and index 8).

---

### Example 2

Input:

```
a = [1,1,1,0,0,0,1,1,1,1,0], K = 2
```

Output:

```
6
```

Explanation: Flip 2 zeros at indices 3 and 4 → `[1,1,1,1,1,0,1,1,1,1,0]`.
Longest streak = **6** (indices 0–5).

---

### Example 3

Input:

```
a = [0,0,1,1,1,0,0,1,1,1,1], K = 1
```

Output:

```
5
```

Explanation: Flip 1 zero at index 5 → `[0,0,1,1,1,1,0,1,1,1,1]`.
Longest streak = **5** (indices 2–6).

---

# 📌 Approaches

---

## 🔹 Brute Force Approach

**Idea:**
Check all subarrays `[i..j]`. Count zeros.

* If zeros ≤ K → valid subarray.
* Track maximum length.

**Steps:**

1. For each starting index `i`
2. For each ending index `j ≥ i`
3. Count zeros in that subarray
4. If count ≤ K → update answer

**Time Complexity:** `O(n^2)` (because we check all subarrays)
**Space Complexity:** `O(1)`

✅ Works, but slow for large arrays.

---

## 🔹 Optimized Approach (Sliding Window / Two Pointers)

**Idea:**
Use a **window** `[left..right]` that always has at most `K` zeros.

* Expand `right` step by step
* Keep track of number of zeros in window
* If zeros > K → shrink from left until valid
* Keep track of maximum window size

**Time Complexity:** `O(n)` (each element is visited at most twice)
**Space Complexity:** `O(1)`

---

# 📌 Java Implementations

---

### Brute Force Java

```java
public class MaxConsecutiveOnesIII {

    public static int longestOnesBruteForce(int[] nums, int K) {
        int n = nums.length;
        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            int zeros = 0;
            for (int j = i; j < n; j++) {
                if (nums[j] == 0) zeros++;
                if (zeros > K) break; // stop early
                maxLen = Math.max(maxLen, j - i + 1);
            }
        }
        return maxLen;
    }

    public static void main(String[] args) {
        int[] a = {1,0,1,0,1,0,0,0,1,1};
        int K = 2;
        System.out.println("Brute Force Answer = " + longestOnesBruteForce(a, K));
    }
}
```

---

### Optimized Sliding Window Java

```java
public class MaxConsecutiveOnesIII {

    public static int longestOnesOptimized(int[] nums, int K) {
        int left = 0, zeros = 0, maxLen = 0;

        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) zeros++;

            // shrink window if too many zeros
            while (zeros > K) {
                if (nums[left] == 0) zeros--;
                left++;
            }

            // update max window size
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        int[] a = {1,0,1,0,1,0,0,0,1,1};
        int K = 2;
        System.out.println("Optimized Answer = " + longestOnesOptimized(a, K));
    }
}
```

---
