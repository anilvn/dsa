Perfect choice 👍 This is a classic **sliding window problem**. Let’s solve step by step.

---

# 📝 Problem Statement

Given an array of positive integers `arr[]` and an integer `target`, find the **minimal length of a contiguous subarray** such that the sum ≥ target.
If no such subarray exists, return `0`.

---

# 📥 Input Format

* Integer `target`
* Integer array `arr[]`

# 📤 Output Format

* Minimal length of subarray with sum ≥ target

---

# ✅ Example 1

**Input:**

```
target = 7  
arr = [2, 3, 1, 2, 4, 3]
```

**Output:**

```
2
```

**Explanation:**
The subarray `[4, 3]` has length 2 and sum = 7.

---

# ✅ Example 2

**Input:**

```
target = 15  
arr = [1, 2, 3, 4, 5]
```

**Output:**

```
5
```

**Explanation:**
Whole array sum = 15 → minimal length = 5.

---

# 🛠 Approaches

---

## 1️⃣ Brute Force

### Logic

* Try all possible subarrays using two loops.
* Track minimal length with sum ≥ target.

### Java Code – Brute Force

```java
public class SmallestSubarrayBruteForce {
    public static int minSubArrayLen(int target, int[] arr) {
        int n = arr.length;
        int minLen = Integer.MAX_VALUE;

        // Check all subarrays
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += arr[j];
                if (sum >= target) {
                    minLen = Math.min(minLen, j - i + 1);
                    break; // no need to extend this subarray further
                }
            }
        }

        return (minLen == Integer.MAX_VALUE) ? 0 : minLen;
    }

    public static void main(String[] args) {
        int[] arr1 = {2, 3, 1, 2, 4, 3};
        System.out.println(minSubArrayLen(7, arr1)); // 2

        int[] arr2 = {1, 2, 3, 4, 5};
        System.out.println(minSubArrayLen(15, arr2)); // 5
    }
}
```

### Complexity

* **Time:** `O(n^2)` (all subarrays)
* **Space:** `O(1)`

---

## 2️⃣ Optimized Approach – Sliding Window

### Logic

* Maintain a **window \[left..right]** with running sum.
* Expand `right` to increase sum.
* Shrink `left` while sum ≥ target to minimize length.

### Java Code – Optimized

```java
public class SmallestSubarrayOptimized {
    public static int minSubArrayLen(int target, int[] arr) {
        int n = arr.length;
        int minLen = Integer.MAX_VALUE;
        int sum = 0, left = 0;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            // Shrink window while condition is satisfied
            while (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                sum -= arr[left];
                left++;
            }
        }

        return (minLen == Integer.MAX_VALUE) ? 0 : minLen;
    }

    public static void main(String[] args) {
        int[] arr1 = {2, 3, 1, 2, 4, 3};
        System.out.println(minSubArrayLen(7, arr1)); // 2

        int[] arr2 = {1, 2, 3, 4, 5};
        System.out.println(minSubArrayLen(15, arr2)); // 5

        int[] arr3 = {1, 1, 1, 1, 1};
        System.out.println(minSubArrayLen(11, arr3)); // 0 (not possible)
    }
}
```

### Complexity

* **Time:** `O(n)` (each element visited at most twice).
* **Space:** `O(1)`

---

# 📊 Comparison Table

| Approach    | Time Complexity | Space Complexity | Notes                         |
| ----------- | --------------- | ---------------- | ----------------------------- |
| Brute Force | `O(n^2)`        | `O(1)`           | Try all subarrays             |
| Optimized   | `O(n)`          | `O(1)`           | Sliding window, best approach |

---

👉 Do you want me to also extend this to the case where array can have **negative numbers**? (In that case sliding window won’t always work, and we’d use prefix sums + binary search).
