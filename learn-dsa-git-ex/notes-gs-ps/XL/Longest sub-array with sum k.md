Sure! Let’s go **step by step** and explore **all approaches** for the problem: **Longest subarray with sum = k** in Java. I’ll cover **brute force, better, and optimal** approaches with **logic, complexity, and code**.

---

## **Problem Statement**

Given an array `arr[]` and an integer `k`, find the length of the **longest contiguous subarray** whose sum equals `k`.

**Example:**

```java
Input: arr = [1, 2, 3, 4, 5], k = 9
Output: 3
Explanation: Subarray [2, 3, 4] sums to 9
```

---

## **1️⃣ Brute Force Approach**

### **Logic:**

* Generate all possible subarrays using two nested loops.
* Calculate the sum of each subarray.
* If sum equals `k`, update max length.

### **Code:**

```java
public static int longestSubarrayBruteForce(int[] arr, int k) {
    int n = arr.length;
    int maxLen = 0;

    for (int i = 0; i < n; i++) {
        int sum = 0;
        for (int j = i; j < n; j++) {
            sum += arr[j];
            if (sum == k) {
                maxLen = Math.max(maxLen, j - i + 1);
            }
        }
    }

    return maxLen;
}
```

### **Complexity:**

* Time: `O(n^2)`
* Space: `O(1)`

✅ **Simple but slow for large arrays.**

---

## **2️⃣ Prefix Sum Approach (with extra array)**

### **Logic:**

* Calculate prefix sums in an array `prefix[i] = sum(arr[0..i])`.
* For every pair `(i, j)`, check if `prefix[j] - prefix[i-1] == k`.
* Avoid recalculating sum for subarrays.

### **Code:**

```java
public static int longestSubarrayPrefixSum(int[] arr, int k) {
    int n = arr.length;
    int[] prefix = new int[n];
    prefix[0] = arr[0];
    for (int i = 1; i < n; i++) {
        prefix[i] = prefix[i - 1] + arr[i];
    }

    int maxLen = 0;
    for (int i = 0; i < n; i++) {
        for (int j = i; j < n; j++) {
            int sum = prefix[j] - (i > 0 ? prefix[i - 1] : 0);
            if (sum == k) {
                maxLen = Math.max(maxLen, j - i + 1);
            }
        }
    }

    return maxLen;
}
```

### **Complexity:**

* Time: `O(n^2)`
* Space: `O(n)`

✅ **Slightly better readability, but same time complexity.**

---

## **3️⃣ Optimal Approach using HashMap (Prefix Sum + Hashing)**

### **Logic:**

1. Maintain a running sum `sum`.
2. Keep a HashMap `prefixSumIndex` to store first occurrence of every sum.
3. At index `i`, check if `(sum - k)` exists in the map:

   * If yes, subarray from `prefixSumIndex.get(sum - k) + 1` to `i` sums to `k`.
4. Update `maxLen` accordingly.
5. Only store first occurrence of sum to maximize subarray length.

**Works for arrays with negative numbers too.**

### **Code:**

```java
public static int longestSubarrayOptimal(int[] arr, int k) {
    Map<Integer, Integer> prefixSumIndex = new HashMap<>();
    int sum = 0;
    int maxLen = 0;

    for (int i = 0; i < arr.length; i++) {
        sum += arr[i];

        if (sum == k) {
            maxLen = i + 1; // subarray from start
        }

        if (!prefixSumIndex.containsKey(sum)) {
            prefixSumIndex.put(sum, i);
        }

        if (prefixSumIndex.containsKey(sum - k)) {
            maxLen = Math.max(maxLen, i - prefixSumIndex.get(sum - k));
        }
    }

    return maxLen;
}
```

### **Complexity:**

* Time: `O(n)` ✅
* Space: `O(n)` ✅

⚡ **Optimal and handles negatives.**

---

## **Comparison Table**

| Approach                       | Time Complexity | Space Complexity | Handles Negative Numbers | Notes                            |
| ------------------------------ | --------------- | ---------------- | ------------------------ | -------------------------------- |
| Brute Force                    | O(n²)           | O(1)             | Yes                      | Easy to implement                |
| Prefix Sum                     | O(n²)           | O(n)             | Yes                      | Slightly better structure        |
| Prefix Sum + HashMap (Optimal) | O(n)            | O(n)             | Yes                      | Best approach, handles negatives |

---

<br/><br/><br/>


Got it! Let’s **dry run** this piece of code step by step so it makes sense. We'll take an example:

---

### **Example:**

```java
arr = [1, 2, 3, 4, 5], k = 9
```

### **Step 1: Compute Prefix Array**

```java
prefix[0] = arr[0] = 1
prefix[1] = prefix[0] + arr[1] = 1 + 2 = 3
prefix[2] = prefix[1] + arr[2] = 3 + 3 = 6
prefix[3] = prefix[2] + arr[3] = 6 + 4 = 10
prefix[4] = prefix[3] + arr[4] = 10 + 5 = 15
```

So,

```
prefix = [1, 3, 6, 10, 15]
```

---

### **Step 2: Nested Loops Logic**

```java
for (int i = 0; i < n; i++) {
    for (int j = i; j < n; j++) {
        int sum = prefix[j] - (i > 0 ? prefix[i - 1] : 0);
        if (sum == k) {
            maxLen = Math.max(maxLen, j - i + 1);
        }
    }
}
```

* **Outer loop (`i`)** → start index of subarray
* **Inner loop (`j`)** → end index of subarray
* **Subarray sum** → `prefix[j] - prefix[i-1]` (for `i > 0`), otherwise `prefix[j]` if starting at 0

---

### **Step 3: Dry Run**

| i | j | prefix\[j] | prefix\[i-1] | sum = prefix\[j] - prefix\[i-1] | sum == k? | maxLen      |
| - | - | ---------- | ------------ | ------------------------------- | --------- | ----------- |
| 0 | 0 | 1          | -            | 1                               | ❌         | 0           |
| 0 | 1 | 3          | -            | 3                               | ❌         | 0           |
| 0 | 2 | 6          | -            | 6                               | ❌         | 0           |
| 0 | 3 | 10         | -            | 10                              | ❌         | 0           |
| 0 | 4 | 15         | -            | 15                              | ❌         | 0           |
| 1 | 1 | 3          | 1            | 3 - 1 = 2                       | ❌         | 0           |
| 1 | 2 | 6          | 1            | 6 - 1 = 5                       | ❌         | 0           |
| 1 | 3 | 10         | 1            | 10 - 1 = 9                      | ✅         | 3           |
| 1 | 4 | 15         | 1            | 15 - 1 = 14                     | ❌         | 3           |
| 2 | 2 | 6          | 3            | 6 - 3 = 3                       | ❌         | 3           |
| 2 | 3 | 10         | 3            | 10 - 3 = 7                      | ❌         | 3           |
| 2 | 4 | 15         | 3            | 15 - 3 = 12                     | ❌         | 3           |
| 3 | 3 | 10         | 6            | 10 - 6 = 4                      | ❌         | 3           |
| 3 | 4 | 15         | 6            | 15 - 6 = 9                      | ✅         | 3 → stays 3 |
| 4 | 4 | 15         | 10           | 15 - 10 = 5                     | ❌         | 3           |

---

### **Step 4: Explanation**

1. When `i = 0, j = 3`, sum = 10 → ❌
2. When `i = 1, j = 3`, sum = 10 - 1 = 9 → ✅ → subarray `[2,3,4]` length = 3 → `maxLen = 3`
3. When `i = 3, j = 4`, sum = 15 - 6 = 9 → ✅ → subarray `[4,5]` length = 2 → `maxLen = 3` (no change)

✅ **Final result: `maxLen = 3`** → `[2,3,4]`

---

<br/><br/><br/>
Perfect! Let’s dive **deeply** into this problem and cover everything you asked, including **negative numbers, duplicates, edge cases, variations, and related questions**.

---

## **1️⃣ What happens with negative numbers & duplicates?**

* **Brute force / Prefix sum approach** works **even if numbers are negative** because it checks all subarrays explicitly.
* **Optimal HashMap approach** also works **with negatives** as long as you track the **first occurrence of each prefix sum**.

**Example:**

```java
arr = [3, -1, 2, 1, 2, -1], k = 3
```

* Subarrays that sum to 3: `[3]`, `[3,-1,2,-1]`, `[2,1]`, `[1,2]`, `[3,-1,2,-1]` (duplicates possible)
* HashMap handles this correctly: prefix sums with negatives can repeat, but we always store **first occurrence** to maximize length.

---

## **2️⃣ Important Edge Cases**

1. **Array is empty:**

   * Return `0` (no subarray exists).

2. **Array has all negative numbers:**

   * Works fine with HashMap and brute force.

3. **Array has all zeros:**

   * Example: `[0, 0, 0]`, `k = 0` → max length = 3

4. **Single element equal to k:**

   * Example: `[5]`, `k = 5` → max length = 1

5. **No subarray sums to k:**

   * Return `0`

6. **Multiple subarrays with same max length:**

   * Only the first occurrence is returned by default in the optimal approach.

---

## **3️⃣ Variations & Similar Problems**

Here are **similar variations** you may encounter in interviews or coding challenges:

| Variation                            | Description                                                    |
| ------------------------------------ | -------------------------------------------------------------- |
| **Print the subarray itself**        | Instead of length, return or print the subarray that sums to k |
| **Print all subarrays equal to k**   | Brute force or prefix + HashMap with lists                     |
| **Unique subarrays equal to k**      | Use a `Set` of subarrays to remove duplicates                  |
| **Count total subarrays equal to k** | Count number of occurrences instead of max length              |
| **Longest subarray with sum ≤ k**    | Sliding window (works for positives)                           |
| **Subarray with sum closest to k**   | Prefix sum + binary search / TreeMap                           |

---

## **4️⃣ Printing the Subarrays Equal to k**

**Brute force approach**:

```java
public static void printSubarraysWithSumK(int[] arr, int k) {
    int n = arr.length;
    for (int i = 0; i < n; i++) {
        int sum = 0;
        for (int j = i; j < n; j++) {
            sum += arr[j];
            if (sum == k) {
                System.out.print("Subarray: [");
                for (int x = i; x <= j; x++) {
                    System.out.print(arr[x] + (x < j ? ", " : ""));
                }
                System.out.println("]");
            }
        }
    }
}
```

---

## **5️⃣ Optimal approach with negative numbers (printing one max-length subarray)**

```java
public static void printMaxLengthSubarray(int[] arr, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    int sum = 0, maxLen = 0, start = -1, end = -1;

    for (int i = 0; i < arr.length; i++) {
        sum += arr[i];

        if (sum == k) {
            maxLen = i + 1;
            start = 0;
            end = i;
        }

        if (!map.containsKey(sum)) {
            map.put(sum, i);
        }

        if (map.containsKey(sum - k)) {
            if (i - map.get(sum - k) > maxLen) {
                maxLen = i - map.get(sum - k);
                start = map.get(sum - k) + 1;
                end = i;
            }
        }
    }

    if (maxLen > 0) {
        System.out.print("Max length subarray: [");
        for (int i = start; i <= end; i++) {
            System.out.print(arr[i] + (i < end ? ", " : ""));
        }
        System.out.println("]");
    } else {
        System.out.println("No subarray sums to k");
    }
}
```

---

## **6️⃣ Printing All Unique Subarrays Equal to k**

```java
public static void printAllUniqueSubarrays(int[] arr, int k) {
    Set<String> uniqueSubarrays = new HashSet<>();
    int n = arr.length;

    for (int i = 0; i < n; i++) {
        int sum = 0;
        for (int j = i; j < n; j++) {
            sum += arr[j];
            if (sum == k) {
                StringBuilder sb = new StringBuilder();
                for (int x = i; x <= j; x++) sb.append(arr[x]).append(",");
                uniqueSubarrays.add(sb.toString());
            }
        }
    }

    for (String sub : uniqueSubarrays) {
        String[] nums = sub.split(",");
        System.out.print("[");
        for (int i = 0; i < nums.length; i++) {
            if (!nums[i].isEmpty())
                System.out.print(nums[i] + (i < nums.length - 1 ? ", " : ""));
        }
        System.out.println("]");
    }
}
```

---

## **7️⃣ Key Points to Remember**

* Brute force always works but slow.
* Prefix sum + HashMap is **O(n)** and works with **negatives and duplicates**.
* Always **store first occurrence** in HashMap to get **max length**.
* Edge cases:

  * Empty array
  * Negative numbers
  * Single element equal to k
  * All zeros
  * Multiple max-length subarrays

---

If you want, I can **draw a single visual diagram** showing **all the subarrays, prefix sums, HashMap states, and max-length logic**, including negative numbers and duplicates — it’s like a **complete cheat sheet for this problem**.

Do you want me to make that visual?

