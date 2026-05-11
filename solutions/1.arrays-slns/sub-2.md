## Minimum Window Size with Sum ≥ K

Input

```text
arr = [2,3,1,2,4,3]
k = 7
```

Output

```text
2
```

Approach

* Variable size sliding window
* Expand right pointer until sum ≥ k
* Shrink from left to get minimum length

```java
int minSubArrayLen(int k, int[] arr) {
    int left = 0, sum = 0;
    int minLen = Integer.MAX_VALUE;

    for (int right = 0; right < arr.length; right++) {
        sum += arr[right];

        // shrink window while valid
        while (sum >= k) {
            minLen = Math.min(minLen, right - left + 1);
            sum -= arr[left++];
        }
    }

    return minLen == Integer.MAX_VALUE ? 0 : minLen;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

## Sliding Window Maximum

Input

```text
arr = [1,3,-1,-3,5,3,6,7]
k = 3
```

Output

```text
[3,3,5,5,6,7]
```

Approach

* Use deque to maintain decreasing order
* Front always stores max element index

```java
int[] maxSlidingWindow(int[] arr, int k) {

    // Stores INDEXES only
    // Maintains DESCENDING order of values
    // Front always contains MAX element index
    Deque<Integer> dq = new ArrayDeque<>();

    int[] res = new int[arr.length - k + 1];

    int idx = 0;

    // Traverse array
    for (int i = 0; i < arr.length; i++) {

        // Remove indexes OUTSIDE current window
        // Remove from FRONT since oldest index stays at front
        if (!dq.isEmpty() && dq.peekFirst() <= i - k) {
            dq.pollFirst();
        }

        // Maintain DESCENDING order
        // Remove smaller/equal elements from BACK
        // Current bigger element makes them useless
        while (!dq.isEmpty() && arr[dq.peekLast()] <= arr[i]) {
            dq.pollLast();
        }

        // Insert current index at BACK
        dq.offerLast(i);

        // Start storing result after first window formed
        if (i >= k - 1) {

            // FRONT contains maximum element index
            // Retrieve actual value using array
            res[idx++] = arr[dq.peekFirst()];
        }
    }

    return res;
}

/*
Time Complexity: O(n)
Space Complexity: O(k)
*/
```
```java
int[] maxSlidingWindow(int[] arr, int k) {

    // MAX HEAP
    // Stores: [value, index]
    //
    // b[0] - a[0]
    // Bigger value gets higher priority
    // Largest value stays at TOP
    PriorityQueue<int[]> pq =
            new PriorityQueue<>((a, b) -> b[0] - a[0]);

    int[] res = new int[arr.length - k + 1];

    int idx = 0;

    // Traverse array
    for (int i = 0; i < arr.length; i++) {

        // Store: [value, index]
        pq.offer(new int[]{arr[i], i});

        // Remove OUTSIDE window indexes
        while (!pq.isEmpty() && pq.peek()[1] <= i - k) {
            pq.poll();
        }

        // First window formed
        if (i >= k - 1) {

            // TOP contains maximum value
            res[idx++] = pq.peek()[0];
        }
    }

    return res;
}
```

---

## Count Distinct Elements in Every Window of Size K

Input

```text
arr = [1,2,1,3,4,2,3]
k = 4
```

Output

```text
[3,4,4,3]
```

Approach

* Use frequency map for current window
* Distinct count = map size

```java
List<Integer> countDistinct(int[] arr, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    List<Integer> ans = new ArrayList<>();

    for (int i = 0; i < arr.length; i++) {

        freq.put(arr[i], freq.getOrDefault(arr[i], 0) + 1);

        // remove left element
        if (i >= k) {
            int leftVal = arr[i - k];

            freq.put(leftVal, freq.get(leftVal) - 1);

            if (freq.get(leftVal) == 0) {
                freq.remove(leftVal);
            }
        }

        if (i >= k - 1) {
            ans.add(freq.size());
        }
    }

    return ans;
}

/*
Time Complexity: O(n)
Space Complexity: O(k)
*/
```

---

## Longest Substring with K Distinct Elements

Input

```text
s = "eceba"
k = 2
```

Output

```text
3
```

Approach

* Variable window
* Maintain frequency map
* Shrink when distinct count > k

```java
int lengthOfLongestSubstringKDistinct(String s, int k) {
    Map<Character, Integer> freq = new HashMap<>();

    int left = 0, maxLen = 0;

    for (int right = 0; right < s.length(); right++) {

        char ch = s.charAt(right);
        freq.put(ch, freq.getOrDefault(ch, 0) + 1);

        // invalid window
        while (freq.size() > k) {
            char leftChar = s.charAt(left);

            freq.put(leftChar, freq.get(leftChar) - 1);

            if (freq.get(leftChar) == 0) {
                freq.remove(leftChar);
            }

            left++;
        }

        maxLen = Math.max(maxLen, right - left + 1);
    }

    return maxLen;
}

/*
Time Complexity: O(n)
Space Complexity: O(k)
*/
```

---

## Subarray Product Less Than K

Input

```text
arr = [10,5,2,6]
k = 100
```

Output

```text
8
```

Approach

* Product-based sliding window
* Shrink while product ≥ k
* Every valid window contributes `(right - left + 1)`

```java
int numSubarrayProductLessThanK(int[] arr, int k) {

    if (k <= 1) return 0;

    int left = 0, count = 0;
    long product = 1;

    for (int right = 0; right < arr.length; right++) {

        product *= arr[right];

        // shrink invalid window
        while (product >= k) {
            product /= arr[left++];
        }

        count += (right - left + 1);
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
/*
Sliding Window Counting Note

right - left + 1

Used for:
- Window length
- Number of elements inside window
- Count of subarrays
- Count of substrings

--------------------------------------------------

Key Intuition

At every iteration:

Count all valid subarrays
ENDING at current right

--------------------------------------------------

Example

left = 1
right = 3

Window:
[5,2,6]

Subarrays ending at index 3:

[6]
[2,6]
[5,2,6]

Count:
3

Formula:

right - left + 1
= 3 - 1 + 1
= 3

--------------------------------------------------

Important Note

Middle subarrays are NOT missed.

They were already counted earlier
when their ending index became right.

Every subarray has:
- one unique ending index
- counted exactly once
*/
```




---

## Count Subarrays with Sum ≤ K

Input

```text
arr = [1,2,1,0,1]
k = 4
```

Output

```text
13
```

Approach

* Valid only for non-negative arrays
* Every valid window contributes `(right - left + 1)`

```java
int countSubarraysAtMostK(int[] arr, int k) {

    int left = 0, sum = 0, count = 0;

    for (int right = 0; right < arr.length; right++) {

        sum += arr[right];

        // shrink invalid window
        while (sum > k) {
            sum -= arr[left++];
        }

        count += (right - left + 1);
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---
## Count Subarrays With Sum Exactly K

## Approach

```java id="n6tkvk"
exact(k) = atMost(k) - atMost(k - 1)
```

Works for:

* Binary arrays
* Positive integers
* Sliding window valid cases

---

## Java Code

```java
class Solution {

    public int numSubarraysWithSum(int[] nums, int goal) {
        return atMost(nums, goal) - atMost(nums, goal - 1);
    }

    private int atMost(int[] nums, int goal) {

        if (goal < 0) {
            return 0;
        }

        int left = 0;
        int sum = 0;
        int count = 0;

        for (int right = 0; right < nums.length; right++) {

            sum += nums[right];

            while (sum > goal) {
                sum -= nums[left++];
            }

            // all subarrays ending at right
            count += right - left + 1;
        }

        return count;
    }
}
```

---

## Key Line

```java
count += right - left + 1;
```

Counts all valid subarrays ending at current `right`.

---

## Time Complexity

```java id="p6kyr3"
O(n)
```

## Space Complexity

```java id="g67pd0"
O(1)
```
---





## Direct Approach (Prefix Sum + HashMap)

Works for:

* Negative numbers
* Positive numbers
* Zeros

---

## Java Code

```java id="0vdm0m"
import java.util.*;

class Solution {

    public int subarraySum(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();

        map.put(0, 1);

        int prefixSum = 0;
        int count = 0;

        for (int num : nums) {

            prefixSum += num;

            // previous prefix needed
            count += map.getOrDefault(prefixSum - k, 0);

            map.put(prefixSum,
                    map.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }
}
```

---

### Core Intuition

If:

```java id="cim6fa"
currentPrefixSum - oldPrefixSum = k
```

then:

```java id="1wv48r"
subarraySum = k
```

So we check:

```java id="t2x4xv"
oldPrefixSum = currentPrefixSum - k
```

---

### Example

```java id="7u8y5p"
nums = [1,2,1]
k = 3
```

Prefix sums:

```text id="njxujn"
1
3
4
```

At prefix `4`:

```java id="nt2s71"
4 - 3 = 1
```

Prefix `1` already exists.

So subarray between them has sum `3`.

---

#### Difference Between Both Approaches

| Approach             | Works With        | Idea              |
| -------------------- | ----------------- | ----------------- |
| Sliding Window       | Positive / Binary | Shrink-expand     |
| Prefix Sum + HashMap | Any integers      | Prefix difference |

---

#### Important

Sliding window cannot directly handle:

```text id="j5m4l7"
negative numbers
```

because window sum becomes non-monotonic.

That’s why:

```java id="q0dht9"
atMost(k) - atMost(k-1)
```

is mainly used in:

* Binary arrays
* Positive arrays
* Exact-K sliding window problems.



## Binary Array Sliding Window (0/1 Flips)

Input

```text
arr = [1,1,1,0,0,0,1,1,1,1,0]
k = 2
```

Output

```text
6
```

Approach

* Track count of zeros
* Shrink when zero count > k

```java
int longestOnes(int[] arr, int k) {

    int left = 0, zeros = 0, maxLen = 0;

    for (int right = 0; right < arr.length; right++) {

        if (arr[right] == 0) zeros++;

        // invalid window
        while (zeros > k) {
            if (arr[left] == 0) zeros--;
            left++;
        }

        maxLen = Math.max(maxLen, right - left + 1);
    }

    return maxLen;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```
