These are the two most fundamental searching techniques—every interview expects you to know not just code, but **when to use which**.

---

# 1. Linear Search

## Problem

Search for a target element in an array.

---

## Idea

Scan each element one by one until you find the target.

---

## Algorithm

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

## Example

```text
nums = [4,2,7,1,9], target = 7

Check: 4 → 2 → 7 → found at index 2
```

---

## Complexity

| Metric | Value |
| ------ | ----- |
| Time   | O(n)  |
| Space  | O(1)  |

---

## When to Use

* Array is **unsorted**
* Small dataset
* Simplicity matters

---

# 2. Binary Search (Important)

## Condition

Array **must be sorted**

---

## Idea

Divide the search space into halves.

---

## Algorithm

1. Initialize `low = 0`, `high = n-1`
2. While `low <= high`

   * `mid = low + (high - low) / 2`
   * If `nums[mid] == target` → return mid
   * If `target < nums[mid]` → search left
   * Else → search right

---

## Java Code

```java
class Solution {
    public int binarySearch(int[] nums, int target) {
        int low = 0, high = nums.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (target < nums[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }
}
```

---

## Example

```text
nums = [1,3,5,7,9], target = 7

low=0, high=4 → mid=2 → nums[2]=5 → go right  
low=3, high=4 → mid=3 → nums[3]=7 → found
```

---

## Complexity

| Metric | Value    |
| ------ | -------- |
| Time   | O(log n) |
| Space  | O(1)     |

---

# Key Differences

| Feature         | Linear Search         | Binary Search       |
| --------------- | --------------------- | ------------------- |
| Requirement     | No sorting needed     | Must be sorted      |
| Time Complexity | O(n)                  | O(log n)            |
| Approach        | Sequential            | Divide & conquer    |
| Use Case        | Small / unsorted data | Large / sorted data |

---

# Important Variants of Binary Search

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

---

## 3. First and Last Occurrence

Used when duplicates exist.

---

# Common Mistakes

* Forgetting array must be **sorted**
* Infinite loop (`low < high` vs `low <= high`) confusion
* Overflow in mid → use

  ```java
  mid = low + (high - low) / 2;
  ```

---

# Interview Insight

Think like this:

* If **unsorted** → Linear Search
* If **sorted** → Binary Search
* If **search space is monotonic** → Binary Search on answer

---

# Pattern Connection

Binary search is used in:

* Rotated array problems
* Peak element
* Minimum in rotated array
* Search in infinite array
* Binary search on answer (very important)

---

If you want next:

* I can give **Binary Search on Answer (most asked in interviews)**
* Or tricky problems like:

  * Find minimum in rotated array
  * Search in rotated sorted array
  * Allocate books / Painter’s partition
