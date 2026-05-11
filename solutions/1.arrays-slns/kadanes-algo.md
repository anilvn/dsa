This is one of the most important array problems—**Kadane’s Algorithm** is the optimal way to find the **maximum subarray sum** in linear time.

---

# Problem Statement

Given an integer array (can contain negatives), find the **maximum sum of a contiguous subarray**.

---

# Example

```text
Input:  [-2,1,-3,4,-1,2,1,-5,4]
Output: 6
Explanation: [4,-1,2,1] → sum = 6
```

---

# Key Insight

At each index, you have two choices:

```text
1. Extend previous subarray
2. Start new subarray from current element
```

So:

```text
currentSum = max(num, currentSum + num)
```

---

# Optimal Approach (Kadane’s Algorithm)

## Algorithm

1. Initialize:

   * `currentSum = nums[0]`
   * `maxSum = nums[0]`
2. Traverse from index `1 → n-1`
3. At each step:

   * Update `currentSum`
   * Update `maxSum`

---

# Java Code (Clean & Interview Ready)

```java
class Solution {
    public int maxSubArray(int[] nums) {
        int currentSum = nums[0];
        int maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }
}
```

---

# Dry Run

```text
nums = [-2,1,-3,4,-1,2,1,-5,4]

i=0 → current=-2, max=-2  
i=1 → current=max(1, -1)=1 → max=1  
i=2 → current=max(-3, -2)=-2  
i=3 → current=max(4, 2)=4 → max=4  
i=4 → current=3  
i=5 → current=5 → max=5  
i=6 → current=6 → max=6  
i=7 → current=1  
i=8 → current=5  

Final max = 6
```

---

# Complexity

| Metric | Value |
| ------ | ----- |
| Time   | O(n)  |
| Space  | O(1)  |

---

# Important Edge Case

## All Negative Numbers

```text
Input: [-3,-2,-5]
Output: -2
```

Kadane still works because we initialize with `nums[0]`.

---

# Variant: Return Subarray (Indices)

## Idea

Track start/end indices along with sum.

---

## Java Code

```java
class Solution {
    public int[] maxSubArrayWithIndices(int[] nums) {
        int currentSum = nums[0];
        int maxSum = nums[0];

        int start = 0, end = 0, tempStart = 0;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > currentSum + nums[i]) {
                currentSum = nums[i];
                tempStart = i;
            } else {
                currentSum += nums[i];
            }

            if (currentSum > maxSum) {
                maxSum = currentSum;
                start = tempStart;
                end = i;
            }
        }

        return new int[]{maxSum, start, end};
    }
}
```

---

# Why Kadane Works

It avoids recomputation by:

* Dropping negative prefixes
* Keeping only beneficial subarrays

---

# Comparison

| Approach                    | Time  | Space |
| --------------------------- | ----- | ----- |
| Brute Force (all subarrays) | O(n²) | O(1)  |
| Prefix Sum                  | O(n²) | O(1)  |
| Kadane’s                    | O(n)  | O(1)  |

---

# Common Mistakes

* Initializing `currentSum = 0` → fails for all-negative arrays
* Forgetting to update `maxSum`
* Confusing with sliding window

---

# Interview Insight

Say this:

> “At each index, I decide whether to continue the current subarray or start fresh.”

That’s the essence of Kadane.

---

# Pattern Connection

Kadane extends to:

* Maximum product subarray
* Circular subarray sum
* 2D maximum submatrix sum

---

If you want next:

* Maximum product subarray (trickier)
* Kadane on circular array
* 2D Kadane (matrix problems)
