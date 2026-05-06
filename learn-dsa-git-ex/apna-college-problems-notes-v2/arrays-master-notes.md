# Arrays — Master Notes (Zero to Hero)

---

## Table of Contents

1. [Max Subarray Sum — Kadane's Algorithm](#1-max-subarray-sum--kadanes-algorithm)
2. [Majority Element](#2-majority-element)
3. [Product of Array Except Self](#3-product-of-array-except-self)
4. [Container With Most Water](#4-container-with-most-water)
5. [Single Non Duplicate in Sorted Array](#5-single-non-duplicate-in-sorted-array)
6. [Peak Index in Mountain Array](#6-peak-index-in-mountain-array)
7. [Best Time to Buy and Sell Stock](#7-best-time-to-buy-and-sell-stock)
8. [Pow(x, n)](#8-powx-n)
9. [Book Allocation](#9-book-allocation)
10. [Painter Partition](#10-painter-partition)
11. [Aggressive Cows](#11-aggressive-cows)

---

## 1. Max Subarray Sum — Kadane's Algorithm

**Problem**: Given an integer array, find the contiguous subarray with the largest sum and return that sum.

**Key Idea**: At each index, decide — extend the current subarray or start fresh from here. Whichever gives a larger value wins.

---

### Approach 1 — Brute Force `O(n³)`

```java
public static int bruteForce(int[] a) {
    int n = a.length;
    int maxSum = Integer.MIN_VALUE;

    for (int i = 0; i < n; i++) {
        for (int j = i; j < n; j++) {
            int sum = 0;
            for (int k = i; k <= j; k++) {
                sum += a[k];
            }
            maxSum = Math.max(maxSum, sum);
        }
    }
    return maxSum;
}
```

> Triple loop — compute sum of every subarray. Very slow for large inputs.

---

### Approach 2 — Kadane's Algorithm `O(n)`

```java
public class KadaneAlgo {
    public static int maxSubArraySum(int[] nums) {
        int maxSoFar = nums[0];
        int currentMax = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currentMax = Math.max(nums[i], currentMax + nums[i]); // extend or restart
            maxSoFar = Math.max(maxSoFar, currentMax);
        }
        return maxSoFar;
    }

    public static void main(String[] args) {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Maximum Subarray Sum = " + maxSubArraySum(arr)); // 6
    }
}
```

> `currentMax` either extends the previous subarray or starts a new one at `nums[i]`. `maxSoFar` tracks the global best.

---

## 2. Majority Element

**Problem**: Find the element that appears more than `⌊n/2⌋` times in the array.

---

### Approach 1 — Brute Force `O(n²)`

```java
public static int majorityElementBrute(int[] nums) {
    int n = nums.length;

    for (int val : nums) {
        int freq = 0;
        for (int el : nums) {
            if (el == val) freq++;
        }
        if (freq > n / 2) return val;
    }
    return -1;
}
```

> For each element, count its frequency by scanning the whole array.

---

### Approach 2 — Sorting `O(n log n)`

```java
import java.util.Arrays;

public static int majorityElementSort(int[] nums) {
    Arrays.sort(nums);
    return nums[nums.length / 2]; // majority always lands at the middle after sort
}
```

> Since majority element appears > n/2 times, after sorting it will always occupy the middle index.

---

### Approach 3 — Moore's Voting Algorithm `O(n)` · `O(1)` space

```java
public static int majorityElementMoore(int[] nums) {
    int candidate = 0, count = 0;

    for (int num : nums) {
        if (count == 0) {
            candidate = num;
            count = 1;
        } else if (num == candidate) {
            count++;
        } else {
            count--;
        }
    }
    return candidate;
}
```

> Votes cancel out — majority element survives. Works only when a majority element is guaranteed to exist.

---

## 3. Product of Array Except Self

**Problem**: Return array `ans` where `ans[i]` = product of all elements except `nums[i]`. Must be `O(n)`, no division allowed.

---

### Approach 1 — Brute Force `O(n²)`

```java
public int[] productExceptSelfBrute(int[] nums) {
    int n = nums.length;
    int[] ans = new int[n];

    for (int i = 0; i < n; i++) {
        int product = 1;
        for (int j = 0; j < n; j++) {
            if (i != j) product *= nums[j];
        }
        ans[i] = product;
    }
    return ans;
}
```

> For each index, multiply all other elements. Simple but slow.

---

### Approach 2 — Prefix + Suffix Arrays `O(n)` time · `O(n)` space

```java
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] prefix = new int[n];
    int[] suffix = new int[n];
    int[] ans = new int[n];

    prefix[0] = 1;
    for (int i = 1; i < n; i++) prefix[i] = prefix[i - 1] * nums[i - 1];

    suffix[n - 1] = 1;
    for (int i = n - 2; i >= 0; i--) suffix[i] = suffix[i + 1] * nums[i + 1];

    for (int i = 0; i < n; i++) ans[i] = prefix[i] * suffix[i];

    return ans;
}
```

> `prefix[i]` = product of everything left of i. `suffix[i]` = product of everything right of i. Multiply both.

---

### Approach 3 — Optimized `O(n)` time · `O(1)` extra space

```java
public int[] productExceptSelfOptimal(int[] nums) {
    int n = nums.length;
    int[] ans = new int[n];

    ans[0] = 1;
    for (int i = 1; i < n; i++) ans[i] = ans[i - 1] * nums[i - 1]; // prefix pass

    int suffixProduct = 1;
    for (int i = n - 1; i >= 0; i--) {
        ans[i] *= suffixProduct; // multiply running suffix
        suffixProduct *= nums[i];
    }
    return ans;
}
// Input: [1,2,3,4] → Output: [24,12,8,6]
```

> First pass fills prefix products into `ans`. Second pass multiplies a running suffix from the right — no extra array needed.

---

## 4. Container With Most Water

**Problem** (LeetCode #11): Given `height[]`, choose two lines that form a container holding maximum water. Return that max area.

**Key Idea**: `area = min(height[left], height[right]) * (right - left)`. Use two pointers, always move the shorter side inward.

---

### Approach 1 — Brute Force `O(n²)`

```java
public static int maxAreaBrute(int[] height) {
    int maxArea = 0;
    for (int i = 0; i < height.length; i++) {
        for (int j = i + 1; j < height.length; j++) {
            maxArea = Math.max(maxArea, Math.min(height[i], height[j]) * (j - i));
        }
    }
    return maxArea;
}
```

> Check every pair of lines. Correct but O(n²).

---

### Approach 2 — Two Pointer `O(n)`

```java
public class ContainerWithMostWater {
    public static int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int maxArea = 0;

        while (left < right) {
            int area = Math.min(height[left], height[right]) * (right - left);
            maxArea = Math.max(maxArea, area);

            if (height[left] < height[right]) left++;
            else right--;
        }
        return maxArea;
    }

    public static void main(String[] args) {
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("Max Area = " + maxArea(height)); // 49
    }
}
```

> Moving the shorter pointer inward is the only way to possibly find a larger area — moving the taller one can only decrease or maintain area.

---

## 5. Single Non Duplicate in Sorted Array

**Problem** (LeetCode #540): Sorted array where every element appears twice except one. Find that single element.

**Key Idea**: Before the single element, pairs sit at `(even, odd)` indices. After it, pairs shift to `(odd, even)`. Use this to binary search.

---

### Approach 1 — XOR (works on unsorted too) `O(n)`

```java
public static int singleNonDuplicateXOR(int[] nums) {
    int single = 0;
    for (int num : nums) single ^= num; // all pairs cancel out
    return single;
}
```

> XOR of two equal numbers = 0. So all pairs cancel, leaving only the single element.

---

### Approach 2 — Binary Search `O(log n)` · sorted array only

```java
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int low = 0, high = nums.length - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (mid % 2 == 1) mid--; // keep mid even

            if (nums[mid] == nums[mid + 1]) {
                low = mid + 2; // pair intact → single is to the right
            } else {
                high = mid;    // pair broken → single is here or to the left
            }
        }
        return nums[low];
    }
}
// Input: [1,1,2,3,3,4,4,8,8] → Output: 2
```

> Always check from an even index. If `nums[mid] == nums[mid+1]`, the pair is intact and the single element is further right.

---

## 6. Peak Index in Mountain Array

**Problem** (LeetCode #852): Mountain array — strictly increasing then strictly decreasing. Find the peak index.

**Key Idea**: If `arr[mid] < arr[mid+1]`, peak is to the right. Otherwise peak is at mid or to the left.

---

### Approach 1 — Clean Binary Search `O(log n)`

```java
class Solution {
    public int peakIndexInMountainArray(int[] arr) {
        int start = 0, end = arr.length - 1;

        while (start < end) {
            int mid = start + (end - start) / 2;

            if (arr[mid] < arr[mid + 1]) start = mid + 1; // ascending side → go right
            else end = mid;                                // descending side → go left
        }
        return start; // start == end at peak
    }
}
```

---

### Approach 2 — Explicit Peak Check `O(log n)`

```java
public int peakIndexV2(int[] arr) {
    int st = 1, end = arr.length - 2; // peak can't be first or last

    while (st <= end) {
        int mid = st + (end - st) / 2;

        if (arr[mid - 1] < arr[mid] && arr[mid] > arr[mid + 1]) return mid; // peak found
        else if (arr[mid - 1] < arr[mid]) st = mid + 1; // still ascending → go right
        else end = mid - 1;                              // descending → go left
    }
    return -1;
}
// Input: [0,2,4,3,1] → Output: 2 (value 4 is peak)
```

> Approach 1 is cleaner for LeetCode. Approach 2 explicitly checks both neighbors to confirm the peak.

---

## 7. Best Time to Buy and Sell Stock

**Problem** (LeetCode #121): Given prices array, buy once and sell once to maximize profit. Return max profit.

**Key Idea**: Track the minimum price seen so far. At each day, profit = `currentPrice - minPrice`. Update max profit.

---

### Approach 1 — Brute Force `O(n²)`

```java
public static int maxProfitBrute(int[] prices) {
    int maxProfit = 0;
    for (int i = 0; i < prices.length; i++) {
        for (int j = i + 1; j < prices.length; j++) {
            maxProfit = Math.max(maxProfit, prices[j] - prices[i]);
        }
    }
    return maxProfit;
}
```

> Try every buy-sell pair. Correct but slow.

---

### Approach 2 — Single Pass `O(n)`

```java
public static int maxProfit(int[] prices) {
    int minPrice = Integer.MAX_VALUE;
    int maxProfit = 0;

    for (int price : prices) {
        minPrice = Math.min(minPrice, price);           // best buy day so far
        maxProfit = Math.max(maxProfit, price - minPrice); // best profit if sold today
    }
    return maxProfit;
}

public static void main(String[] args) {
    int[] arr = {7, 1, 5, 3, 6, 4};
    System.out.println(maxProfit(arr)); // 5 (buy at 1, sell at 6)
}
```

> One pass — always know the cheapest price seen so far and compute profit at each step.

---

## 8. Pow(x, n)

**Problem** (LeetCode #50): Implement `x^n` efficiently.

**Key Idea**: Binary exponentiation — use the binary representation of `n`. If bit is set, multiply result by current base. Square the base each step.

---

### Approach 1 — Naive `O(n)`

```java
public static double myPowNaive(double x, int n) {
    double result = 1;
    for (int i = 0; i < Math.abs(n); i++) result *= x;
    return n < 0 ? 1 / result : result;
}
```

> Multiply x by itself n times. Too slow for large n.

---

### Approach 2 — Binary Exponentiation `O(log n)`

```java
class Solution {
    public double myPow(double x, int n) {
        long exp = n; // long to handle Integer.MIN_VALUE overflow
        if (exp < 0) { x = 1 / x; exp = -exp; }

        double ans = 1.0;
        while (exp > 0) {
            if ((exp % 2) == 1) ans *= x; // odd exponent → take one x into result
            x *= x;   // square the base
            exp /= 2; // halve the exponent
        }
        return ans;
    }
}
// x=2.0, n=10 → 1024.0
```

> Each iteration halves the exponent → O(log n). Use `long` for `n` to safely handle `Integer.MIN_VALUE` negation.

---

## 9. Book Allocation

**Problem**: `N` books with `A[i]` pages, `M` students. Allocate contiguous books to each student. Minimize the maximum pages any student reads. Return `-1` if `M > N`.

**Key Idea**: Binary search on the answer. Search space = `[max(A), sum(A)]`. Use a greedy check to validate if a given max limit is feasible.

---

```java
import java.util.*;

public class BookAllocation {

    public static boolean isValid(int[] arr, int n, int m, int maxAllowedPages) {
        int students = 1, pages = 0;

        for (int i = 0; i < n; i++) {
            if (arr[i] > maxAllowedPages) return false; // single book exceeds limit

            if (pages + arr[i] <= maxAllowedPages) {
                pages += arr[i];
            } else {
                students++;
                pages = arr[i];
                if (students > m) return false;
            }
        }
        return true;
    }

    public static int allocateBooks(int[] arr, int n, int m) {
        if (m > n) return -1;

        int low = Arrays.stream(arr).max().getAsInt();
        int high = Arrays.stream(arr).sum();
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (isValid(arr, n, m, mid)) {
                result = mid;      // feasible → try smaller max
                high = mid - 1;
            } else {
                low = mid + 1;     // not feasible → increase limit
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] books = {12, 34, 67, 90};
        System.out.println(allocateBooks(books, books.length, 2)); // 113
        // Student1: {12,34,67}=113, Student2: {90}=90 → max=113
    }
}
```

> `isValid` greedily assigns books to students — when adding next book exceeds limit, assign to next student. If students needed > m, limit is too small.

---

## 10. Painter Partition

**Problem**: `N` boards with lengths `A[i]`, `K` painters. Each painter paints contiguous boards. Minimize the maximum time taken by any painter.

**Key Idea**: Identical to Book Allocation — boards = books, painters = students. Binary search on `[max(A), sum(A)]`.

---

```java
import java.util.*;

public class PainterPartition {

    public static boolean isValid(int[] boards, int n, int k, int maxAllowedTime) {
        int painters = 1, time = 0;

        for (int i = 0; i < n; i++) {
            if (boards[i] > maxAllowedTime) return false;

            if (time + boards[i] <= maxAllowedTime) {
                time += boards[i];
            } else {
                painters++;
                time = boards[i];
                if (painters > k) return false;
            }
        }
        return true;
    }

    public static int minTime(int[] boards, int n, int k) {
        if (k > n) return -1;

        int low = Arrays.stream(boards).max().getAsInt();
        int high = Arrays.stream(boards).sum();
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (isValid(boards, n, k, mid)) {
                result = mid;
                high = mid - 1; // try smaller max time
            } else {
                low = mid + 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] boards = {10, 20, 30, 40};
        System.out.println(minTime(boards, boards.length, 2)); // 60
        // Painter1: {10,20,30}=60, Painter2: {40}=40 → max=60
    }
}
```

> Same binary search on answer pattern as Book Allocation. If painting speed is `T` units/time, multiply final answer by `T`.

---

## 11. Aggressive Cows

**Problem**: `N` stalls at positions `arr[i]`, place `C` cows. Maximize the minimum distance between any two cows.

**Key Idea**: Sort stalls. Binary search on the answer (minimum distance). Search space = `[1, max-min]`. Greedily check if `C` cows can be placed with at least `mid` distance apart.

---

```java
import java.util.*;

public class AggressiveCows {

    public static boolean canPlace(int[] stalls, int n, int cows, int minDist) {
        int count = 1;
        int lastPos = stalls[0]; // place first cow at first stall

        for (int i = 1; i < n; i++) {
            if (stalls[i] - lastPos >= minDist) {
                count++;
                lastPos = stalls[i];
                if (count == cows) return true;
            }
        }
        return false;
    }

    public static int largestMinDistance(int[] stalls, int n, int cows) {
        Arrays.sort(stalls);

        int low = 1;
        int high = stalls[n - 1] - stalls[0];
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canPlace(stalls, n, cows, mid)) {
                result = mid;    // feasible → try larger min distance
                low = mid + 1;
            } else {
                high = mid - 1; // not feasible → reduce distance
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] stalls = {1, 2, 8, 4, 9};
        System.out.println(largestMinDistance(stalls, stalls.length, 3)); // 3
        // Cows at positions 1, 4, 8 → min gap = 3
    }
}
```

> Unlike Book Allocation (minimize max), here we maximize the minimum — so when feasible, move `low` up to try a larger distance. Sort first, binary search second.

---
