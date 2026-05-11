This is a **core prefix-sum + hashmap** pattern. The twist is handling **negatives**—which breaks sliding window—so you must rely on prefix sums.

---

# Problem Variants

Given an array (can have +ve / -ve / 0):

1. **Count subarrays with sum = K**
2. **Longest subarray with sum = K**
3. **Smallest subarray with sum = K**

---

# Key Insight (Very Important)

Let:

```text
prefixSum[i] = sum of elements from 0 to i
```

If:

```text
prefixSum[j] - prefixSum[i] = K
```

Then:

```text
prefixSum[i] = prefixSum[j] - K
```

So while iterating, we check:

> “Have I seen (currentSum - K) before?”

---

# 1. Count Subarrays with Sum = K

## Idea

Use a hashmap to store frequency of prefix sums.

---

## Algorithm

1. `map.put(0,1)` → handles subarrays starting at index 0
2. Traverse array
3. Keep running sum
4. If `(sum - K)` exists → add its frequency
5. Update map

---

## Java Code

```java
import java.util.*;

class Solution {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int sum = 0, count = 0;

        for (int num : nums) {
            sum += num;

            if (map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }

            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return count;
    }
}
```

---

## Example

```text
nums = [1,2,3], k=3

Subarrays:
[1,2], [3]

Output = 2
```

---

## Complexity

* Time: O(n)
* Space: O(n)

---

# 2. Longest Subarray with Sum = K

## Idea

Store **first occurrence** of each prefix sum.

---

## Algorithm

1. Keep running sum
2. If `sum == K` → update max length
3. If `(sum - K)` seen before → compute length
4. Store sum only if not already present

---

## Java Code

```java
import java.util.*;

class Solution {
    public int longestSubarray(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        int sum = 0, maxLen = 0;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            if (sum == k) {
                maxLen = i + 1;
            }

            if (map.containsKey(sum - k)) {
                maxLen = Math.max(maxLen, i - map.get(sum - k));
            }

            map.putIfAbsent(sum, i); // store first occurrence
        }

        return maxLen;
    }
}
```

---

## Why First Occurrence?

To maximize length:

```text
length = currentIndex - earliestIndex
```

---

# 3. Smallest Subarray with Sum = K

## Idea

Store **latest occurrence** to minimize length.

---

## Java Code

```java
import java.util.*;

class Solution {
    public int smallestSubarray(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        int sum = 0;
        int minLen = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            if (sum == k) {
                minLen = Math.min(minLen, i + 1);
            }

            if (map.containsKey(sum - k)) {
                minLen = Math.min(minLen, i - map.get(sum - k));
            }

            map.put(sum, i); // overwrite → latest occurrence
        }

        return minLen == Integer.MAX_VALUE ? -1 : minLen;
    }
}
```

---

# Important Note (Sliding Window vs Prefix Sum)

| Case                  | Approach             |
| --------------------- | -------------------- |
| Only positives        | Sliding Window       |
| Positives + Negatives | Prefix Sum + HashMap |

---

# Dry Run Insight

```text
nums = [1,-1,5,-2,3], k=3

prefix sums:
1, 0, 5, 3, 6

At sum=3 → subarray found
```

---

# Common Mistakes

* Forgetting `map.put(0,1)` (count version)
* Overwriting map when finding longest (should not)
* Using sliding window when negatives exist

---

# Interview Strategy

Say this clearly:

> “Since negatives exist, sliding window won’t work. I’ll use prefix sum + hashmap.”

That’s a strong signal.

---

# Pattern Connection

This pattern is used in:

* Subarray sum problems
* Longest/shortest subarray problems
* Equal 0s and 1s
* Binary array transformations

---

If you want next:

* Sliding window version (positive-only arrays)
* Count subarrays with sum divisible by K
* Longest subarray with equal 0s and 1s (very important variant)
<br/>

When the array has **only positive numbers**, you can replace the prefix-sum hashmap with a **true sliding window**.
Why it works: with positives, the sum is **monotonic**—expanding the window increases sum, shrinking decreases it. That gives us a clean two-pointer control.

---

# Problem Variants (Positive-only array)

1. **Count subarrays with sum = K**
2. **Longest subarray with sum = K**
3. **Smallest subarray with sum = K**

---

# Core Sliding Window Template

```text
Expand right → sum increases
If sum > K → shrink from left
If sum == K → update answer
```

---

# 1) Count Subarrays with Sum = K

## Idea

Maintain a window `[left … right]` with running sum.

* Expand `right`
* While `sum > K`, move `left`
* If `sum == K`, increment count

---

## Java Code

```java
class Solution {
    public int countSubarrays(int[] nums, int k) {
        int left = 0, sum = 0, count = 0;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum > k) {
                sum -= nums[left++];
            }

            if (sum == k) {
                count++;
            }
        }

        return count;
    }
}
```

---

## Important Note

This works because all numbers are **positive**.
With negatives → this logic breaks.

---

# 2) Longest Subarray with Sum = K

## Idea

Same window, but maximize length when `sum == K`.

---

## Java Code

```java
class Solution {
    public int longestSubarray(int[] nums, int k) {
        int left = 0, sum = 0, maxLen = 0;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum > k) {
                sum -= nums[left++];
            }

            if (sum == k) {
                maxLen = Math.max(maxLen, right - left + 1);
            }
        }

        return maxLen;
    }
}
```

---

# 3) Smallest Subarray with Sum = K

## Idea

Minimize window length when `sum == K`.

---

## Java Code

```java
class Solution {
    public int smallestSubarray(int[] nums, int k) {
        int left = 0, sum = 0;
        int minLen = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum > k) {
                sum -= nums[left++];
            }

            if (sum == k) {
                minLen = Math.min(minLen, right - left + 1);
            }
        }

        return minLen == Integer.MAX_VALUE ? -1 : minLen;
    }
}
```

---

# Dry Run

```text
nums = [1,2,1,1,1], k = 3

right=0 → sum=1  
right=1 → sum=3 → found  
right=2 → sum=4 → shrink → sum=3 → found  
right=3 → sum=4 → shrink → sum=3 → found  
right=4 → sum=4 → shrink → sum=3 → found
```

---

# Complexity

| Metric | Value |
| ------ | ----- |
| Time   | O(n)  |
| Space  | O(1)  |

---

# Sliding Window vs Prefix Sum

| Condition             | Approach             |
| --------------------- | -------------------- |
| Only positive numbers | Sliding Window       |
| Contains negatives    | Prefix Sum + HashMap |

---

# Common Mistakes

* Using sliding window with negative numbers ❌
* Forgetting to shrink when `sum > K`
* Missing multiple valid windows

---

# Interview Insight

You should explicitly say:

> “Since all numbers are positive, I can use sliding window because the sum grows monotonically.”

That’s the key reasoning interviewers want.

---

# Pattern Connection

This is used in:

* Minimum size subarray sum
* Longest substring problems
* At most / exactly K problems

---

If you want next:

* “Count subarrays with sum ≤ K” (very important trick)
* “Number of subarrays with at most K distinct elements”
* “Binary array sliding window (0/1 flips)”

This is a classic extension of prefix sum + hashmap, but instead of exact sum, we use **modulo arithmetic**.

---

# Problem Statement

Given an integer array and an integer `k`, return the **count of subarrays whose sum is divisible by k**.

---

# Example

```text
Input:  nums = [4,5,0,-2,-3,1], k = 5
Output: 7
```

---

# Key Insight (Very Important)

Let:

```text
prefixSum[i] = sum of elements from 0 to i
```

A subarray `(i+1 → j)` is divisible by `k` if:

```text
(prefixSum[j] - prefixSum[i]) % k == 0
```

Which implies:

```text
prefixSum[j] % k == prefixSum[i] % k
```

---

## Core Idea

> If two prefix sums have the **same remainder when divided by k**, the subarray between them is divisible by k.

---

# Algorithm

1. Use a hashmap to store frequency of remainders
2. Initialize `map.put(0,1)` → handles subarrays starting at index 0
3. Traverse array:

   * Compute running sum
   * Compute remainder: `rem = sum % k`
   * Handle negative remainder: `rem = (rem + k) % k`
   * If remainder already seen → add its frequency to count
   * Update map

---

# Java Code (Optimal)

```java
import java.util.*;

class Solution {
    public int subarraysDivByK(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int sum = 0, count = 0;

        for (int num : nums) {
            sum += num;

            int rem = sum % k;

            // handle negative remainder
            if (rem < 0) rem += k;

            if (map.containsKey(rem)) {
                count += map.get(rem);
            }

            map.put(rem, map.getOrDefault(rem, 0) + 1);
        }

        return count;
    }
}
```

---

# Dry Run

```text
nums = [4,5,0,-2,-3,1], k = 5

prefix sums:
4 → rem=4  
9 → rem=4 → match → count=1  
9 → rem=4 → match → count=2  
7 → rem=2  
4 → rem=4 → match → count=3  
5 → rem=0 → match → count=4 (+ previous zeros)

Final count = 7
```

---

# Why `map.put(0,1)`?

```text
If prefixSum % k == 0 → subarray from index 0 is valid
```

So we pre-store remainder `0`.

---

# Complexity

| Metric | Value        |
| ------ | ------------ |
| Time   | O(n)         |
| Space  | O(k) or O(n) |

---

# Common Mistakes

* Not handling negative remainder
* Forgetting `map.put(0,1)`
* Using sum directly instead of modulo

---

# Interview Insight

Say this clearly:

> “Instead of checking exact sum, I track prefix sum modulo k. Equal remainders mean divisible subarray.”

That’s the key leap.

---

# Pattern Connection

This extends:

* Subarray sum = K
* Prefix sum + hashmap
* Modular arithmetic problems

---

# Related Problems

* Count subarrays with sum = K
* Longest subarray divisible by K
* Continuous subarray sum (LeetCode variant)

---

If you want next:

* Longest subarray with sum divisible by K
* Subarrays with equal 0s and 1s
* Advanced: prefix sum + sliding window hybrid problems
