## Binary Search Notes


## Binary Search (Iterative)

Question:
Search an element in a sorted array.

Condition:

* Array must be sorted.

Core Idea:

* Compare middle element with target.
* Eliminate half of the search space every iteration.

Flow:

```text id="owdz6l"
target == arr[mid] -> return mid
target < arr[mid]  -> move left
target > arr[mid]  -> move right
```

Template:

```java
int binarySearch(int[] arr, int target) {
    int left = 0;
    int right = arr.length - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;

        if (arr[mid] == target) {
            return mid;
        } else if (target < arr[mid]) {
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }

    return -1;
}
```

Time Complexity:

```text id="g3r2it"
O(log n)
```

Space Complexity:

```text id="6l8e4i"
O(1)
```

---

## Binary Search (Recursive)

Core Idea:

* Same logic as iterative.
* Problem divided recursively.

Template:

```java
int binarySearch(int[] arr, int left, int right, int target) {

    if (left > right) {
        return -1;
    }

    int mid = left + (right - left) / 2;

    if (arr[mid] == target) {
        return mid;
    }

    if (target < arr[mid]) {
        return binarySearch(arr, left, mid - 1, target);
    }

    return binarySearch(arr, mid + 1, right, target);
}
```

Time Complexity:

```text id="2lbh2v"
O(log n)
```

Space Complexity:

```text id="qqt8sq"
O(log n)
```

Reason:

```text id="qk3mkt"
Recursive call stack
```

---

## Overflow Cases in Binary Search

Wrong:

```java
int mid = (left + right) / 2;
```

Problem:

```text id="1iq11q"
left + right can overflow integer range
```

Safe Formula:

```java
int mid = left + (right - left) / 2;
```

Why Safe:

```text id="pbpd85"
(right - left) never exceeds integer range
```

Example Overflow Case:

```text id="m8pbdq"
left  = 2_000_000_000
right = 2_100_000_000
```

```text id="xx6u0m"
left + right -> overflow
```

Important Note:

```text id="nnpsrr"
Always use:
left + (right - left) / 2
```

---




## Linear Search

### Problem

Search for a target element in an array.

---

### Idea

Scan each element one by one until you find the target.

---

### Algorithm

1. Traverse from `i = 0 → n-1`
2. If `nums[i] == target` → return index
3. If not found → return `-1`

---

## Java Code

```java
class Solution {
    public int linearSearch(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }
}
```

---

### Example

```text
nums = [4,2,7,1,9], target = 7

Check: 4 → 2 → 7 → found at index 2
```

---

### Complexity

| Metric | Value |
| ------ | ----- |
| Time   | O(n)  |
| Space  | O(1)  |

---

### When to Use

* Array is **unsorted**
* Small dataset
* Simplicity matters

---


## Important Variants of Binary Search

Interviewers rarely stop at basic binary search—they go into variants:

---

## 1. Lower Bound (First ≥ target)

```java
int lowerBound(int[] nums, int target) {
    int low = 0, high = nums.length;

    while (low < high) {
        int mid = low + (high - low) / 2;

        if (nums[mid] >= target) {
            high = mid;        // keep mid (candidate)
        } else {
            low = mid + 1;     // discard mid
        }
    }
    return low;
}
```

---

## 2. Upper Bound (First > target)

```java
int upperBound(int[] nums, int target) {
    int low = 0, high = nums.length;

    while (low < high) {
        int mid = low + (high - low) / 2;

        if (nums[mid] > target) {
            high = mid;        // keep mid
        } else {
            low = mid + 1;
        }
    }
    return low;
}
```

<br/>
## Search Insert Position

### Explanation

Question:
Given a sorted array and a target value, return:

* index if target exists
* otherwise the position where it should be inserted

Core Idea:

* Use Binary Search
* If target not found, `left` will point to correct insert position

Example:

```text id="w8gux8"
arr = [1, 3, 5, 6]
target = 2

Output = 1
```

Why:

```text id="8hrrji"
2 should be inserted before 3
```

### Code

```java id="9n9evn"
int searchInsert(int[] arr, int target) {
    int left = 0;
    int right = arr.length - 1;

    while (left <= right) {

        int mid = left + (right - left) / 2;

        if (arr[mid] == target) {
            return mid;
        }

        if (target < arr[mid]) {
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }

    return left;
}
```

---

## Floor of a Number (largest element ≤ x)

### Explanation

Question:
Find the largest element less than or equal to target.

Core Idea:

* Store possible answer
* Move right to find larger valid floor

Example:

```text id="n4rrr5"
arr = [1, 2, 4, 6, 10]
target = 5

Output = 4
```

Observation:

```text id="y38vgj"
4 is largest number <= 5
```

### Code

```java id="5k9uk5"
int floorIndex(int[] arr, int target) {

    int low = 0;
    int high = arr.length - 1;

    int ans = -1;

    while (low <= high) {

        int mid = low + (high - low) / 2;

        if (arr[mid] <= target) {
            ans = mid;          // valid candidate
            low = mid + 1;      // try finding larger valid element
        } else {
            high = mid - 1;
        }
    }

    return ans;
}
```

---

## Ceil of a Number (smallest element ≥ x)

### Explanation

Question:
Find the smallest element greater than or equal to target.

Core Idea:

* Store possible answer
* Move left to find smaller valid ceil

Example:

```text id="4m1t1u"
arr = [1, 2, 4, 6, 10]
target = 5

Output = 6
```

Observation:

```text id="m4h1xm"
6 is smallest number >= 5
```

### Code

```java id="stt1g4"
int ceil(int[] arr, int target) {

    int left = 0;
    int right = arr.length - 1;

    int ans = -1;

    while(left <= right) {

        int mid = left + (right - left) / 2;

        if(arr[mid] >= target) {
            ans = arr[mid];
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }

    return ans;
}
```

---

## First Occurrence in Array

### Explanation

Question:
Find first occurrence index of target in sorted array.

Core Idea:

* When target found:

  * store answer
  * continue searching left side

Observation:

```text id="bh5vzg"
Need leftmost occurrence
```

Example:

```text id="mbd1os"
arr = [1,2,2,2,3,4]
target = 2

Output = 1
```

### Code

```java id="x2zvpi"
int firstOccurrence(int[] arr, int target) {

    int left = 0;
    int right = arr.length - 1;

    int ans = -1;

    while (left <= right) {

        int mid = left + (right - left) / 2;

        if (arr[mid] == target) {
            ans = mid;
            right = mid - 1;
        }

        else if (arr[mid] < target) {
            left = mid + 1;
        }

        else {
            right = mid - 1;
        }
    }

    return ans;
}
```

---

## Last Occurrence in Array

### Explanation

Question:
Find last occurrence index of target in sorted array.

Core Idea:

* When target found:

  * store answer
  * continue searching right side

Observation:

```text id="f7iwbm"
Need rightmost occurrence
```

Example:

```text id="4g7sm8"
arr = [1,2,2,2,3,4]
target = 2

Output = 3
```

### Code

```java id="yo3v5w"
int lastOccurrence(int[] arr, int target) {

    int left = 0;
    int right = arr.length - 1;

    int ans = -1;

    while (left <= right) {

        int mid = left + (right - left) / 2;

        if (arr[mid] == target) {
            ans = mid;
            left = mid + 1;
        }

        else if (arr[mid] < target) {
            left = mid + 1;
        }

        else {
            right = mid - 1;
        }
    }

    return ans;
}
```

---

## Count Occurrences of Element

### Explanation

Question:
Count total occurrences of target in sorted array.

Core Idea:

```text id="brh5sy"
count = lastOccurrence - firstOccurrence + 1
```

Important:

```text id="5t9l1q"
If element not found,
return 0
```

Example:

```text id="nh0my8"
arr = [1,2,2,2,3,4]
target = 2

Output = 3
```

### Code

```java id="q7s7kh"
int countOccurrences(int[] arr, int target) {

    int first = firstOccurrence(arr, target);

    if (first == -1) {
        return 0;
    }

    int last = lastOccurrence(arr, target);

    return last - first + 1;
}
```

<br/><br/>

---

## Find Pivot Index in Rotated Sorted Array

### Explanation

Definition:

```text id="mjlwm0"
Pivot index = index of minimum element
```

Observation:

```text id="4m2qv0"
Minimum element is the only element
smaller than its previous element.
```

Important:

```text id="o5u5pb"
Number of rotations = pivot index
```

Example:

```text id="k0v6s7"
arr = [4,5,6,7,0,1,2]

Pivot Index = 4
Minimum Element = 0
```

---

## Core Logic

### If Current Search Space Already Sorted

```text id="x4q2ut"
arr[low] <= arr[high]

Then arr[low] is minimum.
```

### If Left Half Sorted

```text id="b7mr36"
Minimum must exist in right half.
```

Move:

```text id="ykskhy"
low = mid + 1
```

### Else

```text id="g0kq7x"
Minimum exists in left half including mid.
```

Move:

```text id="6l0n6j"
high = mid - 1
```

---

## Code

```java id="msd8wa"
int findPivot(int[] arr) {

    int left = 0;
    int right = arr.length - 1;

    while(left < right) {

        int mid = left + (right - left) / 2;

        // right half sorted -> min on left incl mid
        if(arr[mid] <= arr[right]) {
            right = mid;
        }

        // min in right half
        else {
            left = mid + 1;
        }
    }

    return left;
}
```

---
<br/><br/>
## Search in Rotated Sorted Array (No duplicates)

### explanation

At least one half is always sorted.

Check:

* which half sorted
* whether target lies inside that half

---

### code

```java id="9gvfd7"
int search(int[] arr, int target) {

    int left = 0;
    int right = arr.length - 1;

    while(left <= right) {

        int mid = left + (right - left) / 2;

        if(arr[mid] == target) {
            return mid;
        }

        // left half sorted
        if(arr[left] <= arr[mid]) {

            if(target >= arr[left] && target < arr[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        // right half sorted
        else {

            if(target > arr[mid] && target <= arr[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }

    return -1;
}
```

---

## Search in Rotated Sorted Array (With duplicates)

### explanation

Duplicates can break sorted-half detection.

Example:

```text id="k3lbjlwm"
[2,2,2,3,4,2]
```

When:

```java id="1skq1y"
arr[left] == arr[mid] == arr[right]
```

shrink both sides.

---

### code

```java id="k1y9wr"
boolean search(int[] arr, int target) {

    int left = 0;
    int right = arr.length - 1;

    while(left <= right) {

        int mid = left + (right - left) / 2;

        if(arr[mid] == target) {
            return true;
        }

        // duplicates
        if(arr[left] == arr[mid] && arr[mid] == arr[right]) {
            left++;
            right--;
        }

        // left sorted
        else if(arr[left] <= arr[mid]) {

            if(target >= arr[left] && target < arr[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        // right sorted
        else {

            if(target > arr[mid] && target <= arr[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }

    return false;
}
```

---

## Find Minimum in Rotated Sorted Array

### explanation

If:

```java id="uxiyz7"
arr[mid] <= arr[right]
```

right half sorted.
Minimum on LEFT incl mid.

Else:
minimum on RIGHT.

---

### code

```java id="y4jz0g"
int findMin(int[] arr) {

    int left = 0;
    int right = arr.length - 1;

    while(left < right) {

        int mid = left + (right - left) / 2;

        if(arr[mid] <= arr[right]) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }

    return arr[left];
}
```

---

## Find Maximum in Rotated Sorted Array

### explanation

Maximum element lies just before minimum.

Or directly binary search.

If:

```java id="jlwmc5"
arr[mid] > arr[right]
```

maximum may be on RIGHT incl mid.

Else:
maximum on LEFT.

---

### code

```java id="xgvs50"
int findMax(int[] arr) {

    int left = 0;
    int right = arr.length - 1;

    while(left < right) {

        int mid = left + (right - left + 1) / 2;

        if(arr[mid] > arr[right]) {
            left = mid;
        } else {
            right = mid - 1;
        }
    }

    return arr[left];
}
```

---

## Number of Rotations in Array

### explanation

Rotation count = pivot idx.

Pivot idx = minimum element index.

---

### code

```java id="jbjlwm"
int rotationCount(int[] arr) {

    int left = 0;
    int right = arr.length - 1;

    while(left < right) {

        int mid = left + (right - left) / 2;

        if(arr[mid] <= arr[right]) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }

    return left;
}
```

---

## minimal notes-very imp

| Problem         | Core Idea               |
| --------------- | ----------------------- |
| Search Rotated  | one half always sorted  |
| With Duplicates | shrink equal boundaries |
| Find Min        | compare with right      |
| Find Max        | just before minimum     |
| Rotation Count  | minimum index           |

---

## Time Complexity

```text id="jlwm3l"
O(log n)
```

With duplicates worst case:

```text id="jlwm2r"
O(n)
```

---

## Space Complexity

```text id="jlwm8f"
O(1)
```

---
<br/>
## Single Element in Sorted Array

### Explanation

Observation:

```text id="v5e0v0"
Before single element:
even index -> first occurrence

After single element:
pattern breaks
```

### Code

```java id="2xob4g"
int singleNonDuplicate(int[] arr) {

    int low = 0;
    int high = arr.length - 1;

    while (low < high) {

        int mid = low + (high - low) / 2;

        // Make mid even
        if (mid % 2 == 1) {
            mid--;
        }

        if (arr[mid] == arr[mid + 1]) {
            low = mid + 2;
        } else {
            high = mid;
        }
    }

    return arr[low];
}
```

---

## Find Peak Element

### Explanation

Peak Element:

```text id="10qbgd"
Element greater than neighbors
```

Observation:

```text id="krd21u"
If mid < mid + 1
peak exists on right side
```

### Code

```java id="w6zzl1"
int findPeakElement(int[] arr) {

    int low = 0;
    int high = arr.length - 1;

    while (low < high) {

        int mid = low + (high - low) / 2;

        if (arr[mid] < arr[mid + 1]) {
            low = mid + 1;
        } else {
            high = mid;
        }
    }

    return low;
}
```

---

## K-th Missing Positive Number

### Explanation

Missing numbers till index:

```text id="lq4sga"
arr[mid] - (mid + 1)
```

Observation:

```text id="ngb0jz"
If missing < k
move right
else move left
```

### Code

```java id="0ohk54"
int findKthPositive(int[] arr, int k) {

    int low = 0;
    int high = arr.length - 1;

    while (low <= high) {

        int mid = low + (high - low) / 2;

        int missing = arr[mid] - (mid + 1);

        if (missing < k) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }

    return low + k;
}
```
<br/>
## SQRT OF A NUMBER

### Problem Statement (Short clear statement)

Find the square root of a non-negative integer (floor value if not perfect square).

---

### Example

* input: x = 17
* output: 4

---

### Idea/Intution

* Use binary search from 1 to x
* Check mid * mid
* Move left/right accordingly
* Track best answer

---

### Java Code

**Brute Force**

```java
int sqrtBrute(int x) {
    int ans = 0;
    for (int i = 1; i <= x; i++) {
        if (i * i <= x) ans = i;
        else break;
    }
    return ans;
}
// Time: O(n), Space: O(1)
```

**Optimized (Binary Search)**

```java
int sqrt(int x) {
    int l = 1, r = x, ans = 0;

    while (l <= r) {
        int mid = l + (r - l) / 2;

        if ((long) mid * mid <= x) {
            ans = mid;
            l = mid + 1;
        } else {
            r = mid - 1;
        }
    }
    return ans;
}
// Time: O(log n), Space: O(1)
```

---

## NTH ROOT OF A NUMBER

### Problem Statement (Short clear statement)

Find integer nth root of m if exists, else return -1.

---

### Example

* input: n = 3, m = 27
* output: 3

---

### Idea/Intution

* Binary search on answer space
* Check mid^n == m using power function (early stop if exceeds)

---

### Java Code

**Brute Force**

```java
int nthRootBrute(int n, int m) {
    for (int i = 1; i <= m; i++) {
        long val = 1;
        for (int j = 1; j <= n; j++) val *= i;
        if (val == m) return i;
        if (val > m) break;
    }
    return -1;
}
// Time: O(m*n), Space: O(1)
```

**Optimized (Binary Search)**

```java
int multiply(int mid, int n, int m) {
    long res = 1;
    for (int i = 1; i <= n; i++) {
        res *= mid;
        if (res > m) return 2;
    }
    return res == m ? 1 : 0;
}

int nthRoot(int n, int m) {
    int l = 1, r = m;

    while (l <= r) {
        int mid = l + (r - l) / 2;

        int val = multiply(mid, n, m);

        if (val == 1) return mid;
        if (val == 0) l = mid + 1;
        else r = mid - 1;
    }
    return -1;
}
// Time: O(n log m), Space: O(1)
```

---

## KOKO EATING BANANAS

### Problem Statement (Short clear statement)

Find minimum eating speed k such that all bananas are eaten within h hours.

---

### Example

* input: piles = [3,6,7,11], h = 8
* output: 4

---

### Idea/Intution

* Binary search on speed
* Check total hours required at mid speed

---

### Java Code

**Brute Force**

```java
int minSpeedBrute(int[] piles, int h) {
    for (int k = 1; k <= 1e9; k++) {
        long time = 0;
        for (int p : piles) {
            time += (p + k - 1) / k;
        }
        if (time <= h) return k;
    }
    return -1;
}
// Time: O(maxPile * n), Space: O(1)
```

**Optimized**

```java
int hours(int[] piles, int k) {
    long total = 0;
    for (int p : piles) {
        total += (p + k - 1) / k;
    }
    return (int) total;
}

int minSpeed(int[] piles, int h) {
    int l = 1, r = (int) 1e9, ans = r;

    while (l <= r) {
        int mid = l + (r - l) / 2;

        if (hours(piles, mid) <= h) {
            ans = mid;
            r = mid - 1;
        } else {
            l = mid + 1;
        }
    }
    return ans;
}
// Time: O(n log maxPile), Space: O(1)
```

---

## MINIMUM DAYS TO MAKE M BOUQUETS

### Problem Statement (Short clear statement)

Find minimum days required to make m bouquets using k adjacent flowers.

---

### Example

* input: bloomDay = [1,10,3,10,2], m = 3, k = 1
* output: 3

---

### Idea/Intution

* Binary search on days
* Check if bouquets can be formed by given day

---

### Java Code

**Brute Force**

```java
int minDaysBrute(int[] bloom, int m, int k) {
    int n = bloom.length;

    for (int d = 1; d <= 1e9; d++) {
        int count = 0, flowers = 0;

        for (int i = 0; i < n; i++) {
            if (bloom[i] <= d) {
                flowers++;
                if (flowers == k) {
                    count++;
                    flowers = 0;
                }
            } else {
                flowers = 0;
            }
        }
        if (count >= m) return d;
    }
    return -1;
}
// Time: O(n * maxDay), Space: O(1)
```

**Optimized**

```java
boolean canMake(int[] bloom, int m, int k, int day) {
    int count = 0, flowers = 0;

    for (int b : bloom) {
        if (b <= day) {
            flowers++;
            if (flowers == k) {
                count++;
                flowers = 0;
            }
        } else flowers = 0;
    }
    return count >= m;
}

int minDays(int[] bloom, int m, int k) {
    if ((long) m * k > bloom.length) return -1;

    int l = 1, r = (int) 1e9, ans = -1;

    while (l <= r) {
        int mid = l + (r - l) / 2;

        if (canMake(bloom, m, k, mid)) {
            ans = mid;
            r = mid - 1;
        } else {
            l = mid + 1;
        }
    }
    return ans;
}
// Time: O(n log maxDay), Space: O(1)
```

---

## SMALLEST DIVISOR GIVEN THRESHOLD

### Problem Statement (Short clear statement)

Find smallest divisor such that sum of ceil division is <= threshold.

---

### Example

* input: nums = [1,2,5,9], threshold = 6
* output: 5

---

### Idea/Intution

* Binary search on divisor
* Compute sum using ceil division

---

### Java Code

**Brute Force**

```java
int smallestDivisorBrute(int[] nums, int threshold) {
    for (int d = 1; d <= 1e6; d++) {
        int sum = 0;
        for (int n : nums) {
            sum += (n + d - 1) / d;
        }
        if (sum <= threshold) return d;
    }
    return -1;
}
// Time: O(maxVal * n), Space: O(1)
```

**Optimized**

```java
int compute(int[] nums, int d) {
    int sum = 0;
    for (int n : nums) {
        sum += (n + d - 1) / d;
    }
    return sum;
}

int smallestDivisor(int[] nums, int threshold) {
    int l = 1, r = (int) 1e6, ans = r;

    while (l <= r) {
        int mid = l + (r - l) / 2;

        if (compute(nums, mid) <= threshold) {
            ans = mid;
            r = mid - 1;
        } else {
            l = mid + 1;
        }
    }
    return ans;
}
// Time: O(n log maxVal), Space: O(1)
```

---

## CAPACITY TO SHIP PACKAGES WITHIN D DAYS

### Problem Statement (Short clear statement)

Find minimum ship capacity to ship all packages within D days.

---

### Example

* input: weights = [1,2,3,4,5,6,7,8,9,10], D = 5
* output: 15

---

### Idea/Intution

* Binary search on capacity
* Simulate shipping per capacity

---

### Java Code

**Brute Force**

```java
int shipBrute(int[] w, int D) {
    int max = 0, sum = 0;
    for (int x : w) {
        max = Math.max(max, x);
        sum += x;
    }

    for (int cap = max; cap <= sum; cap++) {
        int days = 1, load = 0;

        for (int x : w) {
            if (load + x > cap) {
                days++;
                load = 0;
            }
            load += x;
        }

        if (days <= D) return cap;
    }
    return -1;
}
// Time: O(n * sum), Space: O(1)
```

**Optimized**

```java
int daysNeeded(int[] w, int cap) {
    int days = 1, load = 0;

    for (int x : w) {
        if (load + x > cap) {
            days++;
            load = 0;
        }
        load += x;
    }
    return days;
}

int shipWithinDays(int[] w, int D) {
    int l = 0, r = 0;

    for (int x : w) {
        l = Math.max(l, x);
        r += x;
    }

    int ans = r;

    while (l <= r) {
        int mid = l + (r - l) / 2;

        if (daysNeeded(w, mid) <= D) {
            ans = mid;
            r = mid - 1;
        } else {
            l = mid + 1;
        }
    }
    return ans;
}
// Time: O(n log sum), Space: O(1)
```
<br/>

---
## AGGRESSIVE COWS

### Problem Statement (Short clear statement)

Place cows in stalls such that minimum distance between any two cows is maximized.

---

### Example

* input: stalls = [1,2,8,4,9], cows = 3
* output: 3

---

### Idea/Intution

* Sort stalls
* Binary search on minimum distance
* Check if cows can be placed with given distance

---

### Java code

**Brute Force**

```java id="ac1"
boolean canPlace(int[] a, int cows, int dist) {
    int count = 1, last = a[0];

    for (int i = 1; i < a.length; i++) {
        if (a[i] - last >= dist) {
            count++;
            last = a[i];
        }
    }
    return count >= cows;
}

int aggressiveCowsBrute(int[] a, int cows) {
    Arrays.sort(a);

    int max = a[a.length - 1] - a[0];

    for (int d = 1; d <= max; d++) {
        if (!canPlace(a, cows, d - 1)) return d - 1;
    }
    return max;
}
// Time: O(n * maxDist), Space: O(1)
```

**Optimized**

```java id="ac2"
boolean canPlace(int[] a, int cows, int dist) {
    int count = 1, last = a[0];

    for (int i = 1; i < a.length; i++) {
        if (a[i] - last >= dist) {
            count++;
            last = a[i];
        }
    }
    return count >= cows;
}

int aggressiveCows(int[] a, int cows) {
    Arrays.sort(a);

    int l = 1, r = a[a.length - 1] - a[0], ans = 0;

    while (l <= r) {
        int mid = l + (r - l) / 2;

        if (canPlace(a, cows, mid)) {
            ans = mid;
            l = mid + 1;
        } else {
            r = mid - 1;
        }
    }
    return ans;
}
// Time: O(n log maxDist), Space: O(1)
```

---
## ALLOCATE MINIMUM NUMBER OF PAGES

### Problem Statement (Short clear statement)

Given an array where `arr[i]` is pages in ith book and `m` students, allocate books in contiguous manner such that maximum pages assigned to any student is minimized. Return -1 if not possible.

---

### Example

* input: arr = [12, 34, 67, 90], m = 2
* output: 113

---

### Idea/Intution

* Each student gets contiguous books only
* We search answer using binary search on “maximum pages a student can get”
* For a given limit, greedily assign books
* If students needed > m → invalid

---

### Java Code

**Brute Force**

```java id="ab_brute"
boolean isValidAllocation(int[] pages, int students, int maxPagesAllowed) {
    int studentCount = 1;
    int currentPages = 0;

    for (int page : pages) {

        if (page > maxPagesAllowed) return false;

        if (currentPages + page > maxPagesAllowed) {
            studentCount++;
            currentPages = page;
        } else {
            currentPages += page;
        }
    }

    return studentCount <= students;
}

int allocateBooksBrute(int[] pages, int students) {
    int total = 0;

    for (int page : pages) total += page;

    for (int maxPages = 0; maxPages <= total; maxPages++) {
        if (isValidAllocation(pages, students, maxPages)) {
            return maxPages;
        }
    }

    return -1;
}
// Time: O(n * sum), Space: O(1)
```

---

**Optimized (Binary Search)**

```java id="ab_opt"
boolean isValidAllocation(int[] pages, int students, int maxPagesAllowed) {
    int studentCount = 1;
    int currentPages = 0;

    for (int page : pages) {

        if (page > maxPagesAllowed) return false;

        if (currentPages + page > maxPagesAllowed) {
            studentCount++;
            currentPages = page;
        } else {
            currentPages += page;
        }
    }

    return studentCount <= students;
}

int allocateMinimumPages(int[] pages, int students) {

    if (students > pages.length) return -1;

    int low = 0, high = 0;

    for (int page : pages) {
        low = Math.max(low, page);
        high += page;
    }

    int answer = -1;

    while (low <= high) {
        int mid = low + (high - low) / 2;

        if (isValidAllocation(pages, students, mid)) {
            answer = mid;
            high = mid - 1;
        } else {
            low = mid + 1;
        }
    }

    return answer;
}
// Time: O(n log(sum of pages)), Space: O(1)
```

---

## PAINTER’S PARTITION

### Problem Statement (Short clear statement)

Minimize time to paint boards using k painters, where each painter paints continuous boards.

---

### Example

* input: boards = [10,20,30,40], k = 2
* output: 60

---

### Idea/Intution

* Same as Allocate Books
* Binary search on max workload

---

### Java code

**Brute Force**

```java id="pp1"
boolean canPaint(int[] a, int k, int limit) {
    int painters = 1, sum = 0;

    for (int x : a) {
        if (x > limit) return false;

        if (sum + x > limit) {
            painters++;
            sum = x;
        } else {
            sum += x;
        }
    }
    return painters <= k;
}

int painterBrute(int[] a, int k) {
    int sum = 0;
    for (int x : a) sum += x;

    for (int cap = 0; cap <= sum; cap++) {
        if (canPaint(a, k, cap)) return cap;
    }
    return -1;
}
// Time: O(n * sum), Space: O(1)
```

**Optimized**

```java id="pp2"
boolean canPaint(int[] a, int k, int limit) {
    int painters = 1, sum = 0;

    for (int x : a) {
        if (x > limit) return false;

        if (sum + x > limit) {
            painters++;
            sum = x;
        } else {
            sum += x;
        }
    }
    return painters <= k;
}

int painterPartition(int[] a, int k) {
    int l = 0, r = 0;

    for (int x : a) {
        l = Math.max(l, x);
        r += x;
    }

    int ans = r;

    while (l <= r) {
        int mid = l + (r - l) / 2;

        if (canPaint(a, k, mid)) {
            ans = mid;
            r = mid - 1;
        } else {
            l = mid + 1;
        }
    }
    return ans;
}
// Time: O(n log sum), Space: O(1)
```

---

## SPLIT ARRAY LARGEST SUM

### Problem Statement (Short clear statement)

Split array into k subarrays such that maximum subarray sum is minimized.

---

### Example

* input: nums = [7,2,5,10,8], k = 2
* output: 18

---

### Idea/Intution

* Binary search on answer (max subarray sum)
* Check if splits ≤ k

---

### Java code

**Brute Force**

```java id="sa1"
boolean canSplit(int[] a, int k, int limit) {
    int count = 1, sum = 0;

    for (int x : a) {
        if (x > limit) return false;

        if (sum + x > limit) {
            count++;
            sum = x;
        } else {
            sum += x;
        }
    }
    return count <= k;
}

int splitBrute(int[] a, int k) {
    int sum = 0;
    for (int x : a) sum += x;

    for (int cap = 0; cap <= sum; cap++) {
        if (canSplit(a, k, cap)) return cap;
    }
    return -1;
}
// Time: O(n * sum), Space: O(1)
```

**Optimized**

```java id="sa2"
boolean canSplit(int[] a, int k, int limit) {
    int count = 1, sum = 0;

    for (int x : a) {
        if (x > limit) return false;

        if (sum + x > limit) {
            count++;
            sum = x;
        } else {
            sum += x;
        }
    }
    return count <= k;
}

int splitArray(int[] a, int k) {
    int l = 0, r = 0;

    for (int x : a) {
        l = Math.max(l, x);
        r += x;
    }

    int ans = r;

    while (l <= r) {
        int mid = l + (r - l) / 2;

        if (canSplit(a, k, mid)) {
            ans = mid;
            r = mid - 1;
        } else {
            l = mid + 1;
        }
    }
    return ans;
}
// Time: O(n log sum), Space: O(1)
```
