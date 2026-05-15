## Count Subarrays With Sum Greater Than K

Input
arr = [1, 2, 3, 4], k = 5

Output
4

Approach
Generate all subarrays and check sum.

```java
// Brute Force
static int countSubarraysGreaterThanK(int[] arr, int k) {
    int count = 0;

    for (int i = 0; i < arr.length; i++) {
        int sum = 0;

        for (int j = i; j < arr.length; j++) {
            sum += arr[j];

            if (sum > k) {
                count++;
            }
        }
    }

    return count;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java
// Optimal - valid only for positive numbers
static int countSubarraysGreaterThanK(int[] arr, int k) {
    int left = 0, sum = 0, count = 0;

    for (int right = 0; right < arr.length; right++) {
        sum += arr[right];

        while (sum > k) {
            count += arr.length - right;
            sum -= arr[left++];
        }
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

## Count Subarrays With Sum Equal To K

Input
arr = [1, 2, 3], k = 3

Output
2

```
//handle negatives
arr = [3, 4, 7, 2, -3, 1, 4, 2]
k = 7
```

Approach
Use prefix sum + hashmap.

```java
// Brute Force
static int countSubarraysEqualToK(int[] arr, int k) {
    int count = 0;

    for (int i = 0; i < arr.length; i++) {
        int sum = 0;

        for (int j = i; j < arr.length; j++) {
            sum += arr[j];

            if (sum == k) {
                count++;
            }
        }
    }

    return count;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java
// Optimal
static int countSubarraysEqualToK(int[] arr, int k) {
    Map<Integer, Integer> map = new HashMap<>();

    map.put(0, 1);

    int sum = 0, count = 0;

    for (int num : arr) {
        sum += num;

        count += map.getOrDefault(sum - k, 0);

        map.put(sum, map.getOrDefault(sum, 0) + 1);
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(n)
*/
```

---

## Count Subarrays With Sum Less Than Or Equal To K

Input
arr = [1, 2, 1, 1], k = 3

Output
7

Approach
Sliding window for positive numbers.

```java
// Brute Force
static int countSubarraysLessThanEqualK(int[] arr, int k) {
    int count = 0;

    for (int i = 0; i < arr.length; i++) {
        int sum = 0;

        for (int j = i; j < arr.length; j++) {
            sum += arr[j];

            if (sum <= k) {
                count++;
            }
        }
    }

    return count;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java
// Optimal - positive numbers only
static int countSubarraysLessThanEqualK(int[] arr, int k) {

    int left = 0;
    int sum = 0;
    int count = 0;

    for (int right = 0; right < arr.length; right++) {

        sum += arr[right];

        // shrink window if sum > k
        // negative condtion shrink
        while (sum > k && left <= right) {
            sum -= arr[left];
            left++;
        }

        // all subarrays ending at right are valid
        count += (right - left + 1);
    }

    return count;
}

// Time Complexity - O(n)
// Space Complexity - O(1)

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

## Sliding Window Maximum

Input
arr = [1,3,-1,-3,5,3,6,7], k = 3

Output
[3,3,5,5,6,7]

Approach
Use deque to maintain decreasing order.

```java
// Brute Force
static int[] maxSlidingWindow(int[] arr, int k) {
    int[] res = new int[arr.length - k + 1];

    for (int i = 0; i <= arr.length - k; i++) {
        int max = arr[i];

        for (int j = i; j < i + k; j++) {
            max = Math.max(max, arr[j]);
        }

        res[i] = max;
    }

    return res;
}

/*
Time Complexity: O(n * k)
Space Complexity: O(1)
*/
```

```java
// Optimal
static int[] maxSlidingWindow(int[] arr, int k) {
    Deque<Integer> dq = new LinkedList<>();
    int[] res = new int[arr.length - k + 1];
    int idx = 0;

    for (int i = 0; i < arr.length; i++) {

        while (!dq.isEmpty() && dq.peekFirst() <= i - k) {
            dq.pollFirst();
        }

        while (!dq.isEmpty() && arr[dq.peekLast()] < arr[i]) {
            dq.pollLast();
        }

        dq.offerLast(i);

        if (i >= k - 1) {
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

---

## Count Distinct Elements In Every Window Of Size K

Input
arr = [1,2,1,3,4,2,3], k = 4

Output
[3,4,4,3]

Approach
Frequency map for current window.

```java
// Brute Force
static List<Integer> countDistinct(int[] arr, int k) {
    List<Integer> res = new ArrayList<>();

    for (int i = 0; i <= arr.length - k; i++) {
        Set<Integer> set = new HashSet<>();

        for (int j = i; j < i + k; j++) {
            set.add(arr[j]);
        }

        res.add(set.size());
    }

    return res;
}

/*
Time Complexity: O(n * k)
Space Complexity: O(k)
*/
```

```java
// Optimal
static List<Integer> countDistinct(int[] arr, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    List<Integer> res = new ArrayList<>();

    for (int i = 0; i < k; i++) {
        map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
    }

    res.add(map.size());

    for (int i = k; i < arr.length; i++) {

        map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);

        map.put(arr[i - k], map.get(arr[i - k]) - 1);

        if (map.get(arr[i - k]) == 0) {
            map.remove(arr[i - k]);
        }

        res.add(map.size());
    }

    return res;
}

/*
Time Complexity: O(n)
Space Complexity: O(k)
*/
```

---

## Maximum Points You Can Obtain From Cards

Input
arr = [1,2,3,4,5,6,1], k = 3

Output
12

Approach
Take total of first k cards, then slide by removing left and adding right.

```java
// Brute Force
static int maxScore(int[] arr, int k) {
    return solve(arr, 0, arr.length - 1, k);
}

static int solve(int[] arr, int left, int right, int k) {

    if (k == 0) {
        return 0;
    }

    int takeLeft = arr[left] + solve(arr, left + 1, right, k - 1);

    int takeRight = arr[right] + solve(arr, left, right - 1, k - 1);

    return Math.max(takeLeft, takeRight);
}

/*
Time Complexity: O(2^k)
Space Complexity: O(k)
*/
```
```java
class Solution {
    public int maxScore(int[] cardPoints, int k) {

        int n = cardPoints.length;

        int leftSum = 0;

        // Initial window -> first k cards
        for (int i = 0; i < k; i++) {
            leftSum += cardPoints[i];
        }

        int max = leftSum;

        int rightSum = 0;
        int right = n - 1;

        // Shift picks from left to right
        for (int left = k - 1; left >= 0; left--) {

            leftSum -= cardPoints[left];
            rightSum += cardPoints[right--];

            max = Math.max(max, leftSum + rightSum);
        }

        return max;
    }
}
```

```java
// Optimal
static int maxScore(int[] arr, int k) {
    int leftSum = 0;

    for (int i = 0; i < k; i++) {
        leftSum += arr[i];
    }

    int max = leftSum;
    int rightSum = 0;

    for (int i = k - 1; i >= 0; i--) {
        leftSum -= arr[i];

        rightSum += arr[arr.length - (k - i)];

        max = Math.max(max, leftSum + rightSum);
    }

    return max;
}

/*
Time Complexity: O(k)
Space Complexity: O(1)
*/
```

<br/>


## Longest Substring Without Repeating Characters

Input
s = "abcabcbb"

Output
3

Approach
Maintain unique characters using sliding window.

```java id="l1x8qp"
// Brute Force
static int lengthOfLongestSubstring(String s) {
    int max = 0;

    for (int i = 0; i < s.length(); i++) {
        Set<Character> set = new HashSet<>();

        for (int j = i; j < s.length(); j++) {

            if (set.contains(s.charAt(j))) {
                break;
            }

            set.add(s.charAt(j));

            max = Math.max(max, j - i + 1);
        }
    }

    return max;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(n)
*/
```

```java id="n2d7we"
// Optimal
static int lengthOfLongestSubstring(String s) {
    Set<Character> set = new HashSet<>();

    int left = 0, max = 0;

    for (int right = 0; right < s.length(); right++) {

        while (set.contains(s.charAt(right))) {
            set.remove(s.charAt(left++));
        }

        set.add(s.charAt(right));

        max = Math.max(max, right - left + 1);
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(n)
*/
```
```java
static int lengthOfLongestSubstring(String s) {

    Map<Character, Integer> map = new HashMap<>();

    int left = 0, max = 0;

    for (int right = 0; right < s.length(); right++) {

        char ch = s.charAt(right);

        // duplicate found inside current window
        if (map.containsKey(ch) && map.get(ch) >= left) {
            left = map.get(ch) + 1;
        }

        map.put(ch, right);

        max = Math.max(max, right - left + 1);
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(256) -> O(1)
*/
```

---

## Minimum Size Subarray Sum

Input
arr = [2,3,1,2,4,3], target = 7

Output
2

Approach
Shrink window while sum >= target.

```java id="k8m1rd"
// Brute Force
static int minSubArrayLen(int target, int[] arr) {
    int min = Integer.MAX_VALUE;

    for (int i = 0; i < arr.length; i++) {
        int sum = 0;

        for (int j = i; j < arr.length; j++) {
            sum += arr[j];

            if (sum >= target) {
                min = Math.min(min, j - i + 1);
                break;
            }
        }
    }

    return min == Integer.MAX_VALUE ? 0 : min;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="y7v9qs"
// Optimal - positive numbers only
static int minSubArrayLen(int target, int[] arr) {
    int left = 0, sum = 0;
    int min = Integer.MAX_VALUE;

    for (int right = 0; right < arr.length; right++) {
        sum += arr[right];

        while (sum >= target) {
            min = Math.min(min, right - left + 1);
            sum -= arr[left++];
        }
    }

    return min == Integer.MAX_VALUE ? 0 : min;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

## Max Consecutive Ones III

Input
arr = [1,1,1,0,0,0,1,1,1,1,0], k = 2

Output
6

Approach
Allow at most k zeros in window.

```java id="u2s5ka"
// Brute Force
static int longestOnes(int[] arr, int k) {
    int max = 0;

    for (int i = 0; i < arr.length; i++) {
        int zeros = 0;

        for (int j = i; j < arr.length; j++) {

            if (arr[j] == 0) {
                zeros++;
            }

            if (zeros > k) {
                break;
            }

            max = Math.max(max, j - i + 1);
        }
    }

    return max;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="d0h7tm"
// Optimal
static int longestOnes(int[] arr, int k) {
    int left = 0, zeros = 0, max = 0;

    for (int right = 0; right < arr.length; right++) {

        if (arr[right] == 0) {
            zeros++;
        }

        while (zeros > k) {

            if (arr[left++] == 0) {
                zeros--;
            }
        }

        max = Math.max(max, right - left + 1);
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```


---
## FRUIT INTO BASKETS

### Problem Statement

* Given an array `fruits`, you start from any index and move right.
* You can pick fruits from at most **2 different types**.
* Return the maximum number of fruits you can collect in a continuous subarray.

---

### Example

#### Input

```text
fruits = [1,2,1]
```

#### Output

```text
3
```

---

### Idea / Intuition

* We need the **longest subarray with at most 2 distinct numbers**.
* Think of it like a sliding window problem.
* Expand window to right.
* If more than 2 types appear, shrink from left.
* Track maximum window size.

---

### Brute Force Approach (Optional)

* Try every starting point.
* Keep adding elements until more than 2 types appear.
* Update maximum length.

---

### Java Code

```java
// brute force code
// Time Complexity - O(n^2)
// Space Complexity - O(1)

import java.util.*;

class Solution {
    static int totalFruit(int[] fruits) {
        int n = fruits.length;
        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            Set<Integer> set = new HashSet<>();

            for (int j = i; j < n; j++) {
                set.add(fruits[j]);

                if (set.size() > 2) break;

                maxLen = Math.max(maxLen, j - i + 1);
            }
        }

        return maxLen;
    }
}
```

---

### Optimized Approach

### steps for optimized approach

* Use sliding window
* Maintain frequency map of fruit types
* Expand right pointer
* If map size > 2, shrink from left
* Keep updating max length

---

### Java Code

```java
// optimized code with minimal comments
// Time Complexity - O(n)
// Space Complexity - O(1) (max 3 keys in map)

import java.util.*;

class Solution {
    static int totalFruit(int[] fruits) {
        Map<Integer, Integer> map = new HashMap<>();
        int left = 0, maxLen = 0;

        for (int right = 0; right < fruits.length; right++) {
            map.put(fruits[right], map.getOrDefault(fruits[right], 0) + 1);

            while (map.size() > 2) {
                int lf = fruits[left];
                map.put(lf, map.get(lf) - 1);

                if (map.get(lf) == 0) {
                    map.remove(lf);
                }
                left++;
            }

            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}
```


---

## Longest Substring With At Most K Distinct Characters

Input
s = "eceba", k = 2

Output
3

Approach
Keep at most k distinct characters in window.

```java id="w7c2ny"
// Brute Force
static int lengthOfLongestSubstringKDistinct(String s, int k) {
    int max = 0;

    for (int i = 0; i < s.length(); i++) {
        Set<Character> set = new HashSet<>();

        for (int j = i; j < s.length(); j++) {
            set.add(s.charAt(j));

            if (set.size() > k) {
                break;
            }

            max = Math.max(max, j - i + 1);
        }
    }

    return max;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(k)
*/
```

```java id="m5r1zt"
// Optimal
static int lengthOfLongestSubstringKDistinct(String s, int k) {
    Map<Character, Integer> map = new HashMap<>();

    int left = 0, max = 0;

    for (int right = 0; right < s.length(); right++) {

        char ch = s.charAt(right);

        map.put(ch, map.getOrDefault(ch, 0) + 1);

        while (map.size() > k) {

            char leftChar = s.charAt(left);

            map.put(leftChar, map.get(leftChar) - 1);

            if (map.get(leftChar) == 0) {
                map.remove(leftChar);
            }

            left++;
        }

        max = Math.max(max, right - left + 1);
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(k)
*/
```

---

## Longest Subarray / Substring With K Distinct Elements

Input
arr = [1,2,1,2,3], k = 2

Output
4
```
arr = [1,2,1,2,3]
k = 2
//output - 4
ans formed-- [1,2,1,2]
```
Approach
Maintain exactly k distinct elements.

```java id="j9d4ru"
// Brute Force
static int longestKDistinct(int[] arr, int k) {
    int max = 0;

    for (int i = 0; i < arr.length; i++) {
        Set<Integer> set = new HashSet<>();

        for (int j = i; j < arr.length; j++) {
            set.add(arr[j]);

            if (set.size() > k) {
                break;
            }

            if (set.size() == k) {
                max = Math.max(max, j - i + 1);
            }
        }
    }

    return max;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(k)
*/
```

```java id="h2o8xc"
// Optimal
static int longestKDistinct(int[] arr, int k) {
    Map<Integer, Integer> map = new HashMap<>();

    int left = 0, max = 0;

    for (int right = 0; right < arr.length; right++) {

        map.put(arr[right],
                map.getOrDefault(arr[right], 0) + 1);

        while (map.size() > k) {
            map.put(arr[left], map.get(arr[left]) - 1);

            if (map.get(arr[left]) == 0) {
                map.remove(arr[left]);
            }

            left++;
        }

        if (map.size() == k) {
            max = Math.max(max, right - left + 1);
        }
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(k)
*/
```

---

## Longest Repeating Character Replacement

Input
s = "AABABBA", k = 1

Output
4

Approach
Keep window where replacements needed <= k.

```java id="f5q8zx"
// Brute Force
static int characterReplacement(String s, int k) {
    int max = 0;

    for (int i = 0; i < s.length(); i++) {
        int[] freq = new int[26];
        int maxFreq = 0;

        for (int j = i; j < s.length(); j++) {

            freq[s.charAt(j) - 'A']++;

            maxFreq = Math.max(maxFreq,
                    freq[s.charAt(j) - 'A']);

            int changes = (j - i + 1) - maxFreq;

            if (changes > k) {
                break;
            }

            max = Math.max(max, j - i + 1);
        }
    }

    return max;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="z4n1yt"
// Optimal
static int characterReplacement(String s, int k) {
    int[] freq = new int[26];

    int left = 0, maxFreq = 0, max = 0;

    for (int right = 0; right < s.length(); right++) {

        freq[s.charAt(right) - 'A']++;

        maxFreq = Math.max(maxFreq,
                freq[s.charAt(right) - 'A']);

        while ((right - left + 1) - maxFreq > k) {
            freq[s.charAt(left) - 'A']--;
            left++;
        }

        max = Math.max(max, right - left + 1);
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```
<br/>


## Subarray Product Less Than K

Input
arr = [10, 5, 2, 6], k = 100

Output
8

Approach
Sliding window, shrink when product ≥ k.

```java id="p1a2bc"
// Brute Force
static int numSubarrayProductLessThanK(int[] arr, int k) {
    int count = 0;

    for (int i = 0; i < arr.length; i++) {
        int prod = 1;

        for (int j = i; j < arr.length; j++) {
            prod *= arr[j];

            if (prod < k) {
                count++;
            } else {
                break;
            }
        }
    }

    return count;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="q9x7kl"
// Optimal
static int numSubarrayProductLessThanK(int[] arr, int k) {
    if (k <= 1) return 0;

    int left = 0, prod = 1, count = 0;

    for (int right = 0; right < arr.length; right++) {
        prod *= arr[right];

        while (prod >= k) {
            prod /= arr[left++];
        }

        count += right - left + 1;
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

## Count Subarrays With Product Less Than K

Input
arr = [1, 2, 3, 4], k = 10

Output
7

Approach
Same as previous (duplicate pattern).

```java id="m8v2pq"
// Brute Force
static int countSubarraysProductLessThanK(int[] arr, int k) {
    int count = 0;

    for (int i = 0; i < arr.length; i++) {
        int prod = 1;

        for (int j = i; j < arr.length; j++) {
            prod *= arr[j];

            if (prod < k) count++;
            else break;
        }
    }

    return count;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="t4n6sd"
// Optimal
static int countSubarraysProductLessThanK(int[] arr, int k) {
    if (k <= 1) return 0;

    int left = 0, prod = 1, count = 0;

    for (int right = 0; right < arr.length; right++) {
        prod *= arr[right];

        while (prod >= k) {
            prod /= arr[left++];
        }

        count += right - left + 1;
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

## Binary Subarrays With Sum

Input
arr = [1,0,1,0,1], goal = 2

Output
4

Approach
Use at most trick.

```java id="b3k8xy"
// Brute Force
static int numSubarraysWithSum(int[] arr, int goal) {
    int count = 0;

    for (int i = 0; i < arr.length; i++) {
        int sum = 0;

        for (int j = i; j < arr.length; j++) {
            sum += arr[j];

            if (sum == goal) count++;
        }
    }

    return count;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="c7v1mn"
// Optimal (AtMost trick)
static int numSubarraysWithSum(int[] arr, int goal) {
    return atMost(arr, goal) - atMost(arr, goal - 1);
}

static int atMost(int[] arr, int k) {
    if (k < 0) return 0;

    int left = 0, sum = 0, count = 0;

    for (int right = 0; right < arr.length; right++) {
        sum += arr[right];

        // Window [left ... right] has sum ≤ k - if goes beyond shrink...
        while (sum > k) {
            sum -= arr[left++];
        }

        count += right - left + 1;
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---
## COUNT NUMBER OF NICE SUBARRAYS

### Problem Statement

* Given an array `nums` and an integer `k`
* A subarray is called **nice** if it contains exactly `k` odd numbers
* Return total number of such subarrays

---

### Example

#### Input

```text
nums = [1,1,2,1,1], k = 3
```

#### Output

```text
2
```

---

### Idea / Intuition

* Convert array into **odd = 1, even = 0**
* Now problem becomes:
  → Count subarrays with sum = k

But direct sliding window is tricky for "exact k"

Better idea:

* Count subarrays with **at most k odds**
* Count subarrays with **at most (k - 1) odds**
* Answer = difference

Why this works:

* atMost(k) includes all subarrays with 0..k odds
* subtract atMost(k-1) → leaves exactly k odds

---

### Brute Force Approach (Optional)

* Try every subarray
* Count odd numbers
* If equal to k → increase answer

---

### Java Code

```java
// brute force code
// Time Complexity - O(n^2)
// Space Complexity - O(1)

class Solution {
    static int numberOfSubarrays(int[] nums, int k) {
        int n = nums.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            int odd = 0;

            for (int j = i; j < n; j++) {
                if (nums[j] % 2 != 0) odd++;

                if (odd == k) count++;
                if (odd > k) break;
            }
        }

        return count;
    }
}
```

---

### Optimized Approach

### steps for optimized approach

* Use sliding window for "at most k odd numbers"
* Maintain left pointer
* Expand right pointer
* If odd count > k → shrink left
* Count valid subarrays ending at each index
* Final answer = atMost(k) - atMost(k - 1)

---

### Java Code

```java
// optimized code with minimal comments
// Time Complexity - O(n)
// Space Complexity - O(1)

class Solution {

    static int numberOfSubarrays(int[] nums, int k) {
        return atMost(nums, k) - atMost(nums, k - 1);
    }

    static int atMost(int[] nums, int k) {
        int left = 0, count = 0, odd = 0;

        for (int right = 0; right < nums.length; right++) {
            if (nums[right] % 2 != 0) odd++;

            while (odd > k) {
                if (nums[left] % 2 != 0) odd--;
                left++;
            }

            count += (right - left + 1);
        }

        return count;
    }
}
```


---

## Subarrays With K Different Integers

Input
arr = [1,2,1,2,3], k = 2

Output
7

Approach
AtMost trick with hashmap.

```java id="u8p3wd"
// Brute Force
static int subarraysWithKDistinct(int[] arr, int k) {
    int count = 0;

    for (int i = 0; i < arr.length; i++) {
        Set<Integer> set = new HashSet<>();

        for (int j = i; j < arr.length; j++) {
            set.add(arr[j]);

            if (set.size() == k) count++;

            if (set.size() > k) break;
        }
    }

    return count;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(k)
*/
```

```java id="w1d7ab"
// Optimal
static int subarraysWithKDistinct(int[] arr, int k) {
    return atMost(arr, k) - atMost(arr, k - 1);
}

static int atMost(int[] arr, int k) {
    Map<Integer, Integer> map = new HashMap<>();

    int left = 0, count = 0;

    for (int right = 0; right < arr.length; right++) {

        map.put(arr[right],
                map.getOrDefault(arr[right], 0) + 1);

        while (map.size() > k) {
            map.put(arr[left], map.get(arr[left]) - 1);

            if (map.get(arr[left]) == 0) {
                map.remove(arr[left]);
            }

            left++;
        }

        count += right - left + 1;
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(k)
*/
```

---

## Important Core Formula

AtMost Trick:

```text
Exact(K) = AtMost(K) - AtMost(K - 1)
```

Used in:

* Binary Subarrays With Sum
* Nice Subarrays
* Subarrays With K Distinct Integers



<br/>

## Number Of Substrings Containing All Three Characters

Input
s = "abcabc"

Output
10

Approach
Use sliding window + frequency count.

```java id="a1b2c3"
// Brute Force
static int numberOfSubstrings(String s) {
    int count = 0;

    for (int i = 0; i < s.length(); i++) {
        int a = 0, b = 0, c = 0;

        for (int j = i; j < s.length(); j++) {

            char ch = s.charAt(j);

            if (ch == 'a') a++;
            if (ch == 'b') b++;
            if (ch == 'c') c++;

            if (a > 0 && b > 0 && c > 0) {
                count++;
            }
        }
    }

    return count;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="d4e5f6"
// Optimal
static int numberOfSubstrings(String s) {
    int[] freq = new int[3];
    int left = 0, count = 0;

    for (int right = 0; right < s.length(); right++) {

        freq[s.charAt(right) - 'a']++;

        while (freq[0] > 0 && freq[1] > 0 && freq[2] > 0) {
            count += s.length() - right;

            freq[s.charAt(left) - 'a']--;
            left++;
        }
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

## Minimum Window Substring

Input
s = "ADOBECODEBANC", t = "ABC"

Output
"BANC"

Approach
Expand until valid, then shrink.

```java id="g7h8i9"
// Brute Force
static String minWindow(String s, String t) {
    int minLen = Integer.MAX_VALUE;
    String ans = "";

    for (int i = 0; i < s.length(); i++) {
        int[] freq = new int[128];

        for (char c : t.toCharArray()) {
            freq[c]++;
        }

        int count = t.length();

        for (int j = i; j < s.length(); j++) {

            if (freq[s.charAt(j)] > 0) {
                count--;
            }

            freq[s.charAt(j)]--;

            if (count == 0) {
                if (j - i + 1 < minLen) {
                    minLen = j - i + 1;
                    ans = s.substring(i, j + 1);
                }
                break;
            }
        }
    }

    return ans;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="j1k2l3"
// Optimal
static String minWindow(String s, String t) {
    if (t.length() > s.length()) return "";

    int[] freq = new int[128];

    for (char c : t.toCharArray()) {
        freq[c]++;
    }

    int required = t.length();
    int left = 0, minLen = Integer.MAX_VALUE, start = 0;

    for (int right = 0; right < s.length(); right++) {

        if (freq[s.charAt(right)] > 0) {
            required--;
        }

        freq[s.charAt(right)]--;

        while (required == 0) {

            // update ans
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                start = left;
            }
            
            // shrink
            freq[s.charAt(left)]++;

            if (freq[s.charAt(left)] > 0) {
                required++;
            }

            left++;
        }
    }

    return minLen == Integer.MAX_VALUE ? "" :
            s.substring(start, start + minLen);
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

## Minimum Size Window (Sum ≥ K)

Input
arr = [2,3,1,2,4,3], k = 7

Output
2

Approach
Shrink window when sum ≥ k.

```java id="m4n5o6"
// Brute Force
static int minWindowSize(int[] arr, int k) {
    int min = Integer.MAX_VALUE;

    for (int i = 0; i < arr.length; i++) {
        int sum = 0;

        for (int j = i; j < arr.length; j++) {
            sum += arr[j];

            if (sum >= k) {
                min = Math.min(min, j - i + 1);
                break;
            }
        }
    }

    return min == Integer.MAX_VALUE ? 0 : min;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="p7q8r9"
// Optimal
static int minWindowSize(int[] arr, int k) {
    int left = 0, sum = 0;
    int min = Integer.MAX_VALUE;

    for (int right = 0; right < arr.length; right++) {

        sum += arr[right];

        while (sum >= k) {
            min = Math.min(min, right - left + 1);
            sum -= arr[left++];
        }
    }

    return min == Integer.MAX_VALUE ? 0 : min;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```



<br/>