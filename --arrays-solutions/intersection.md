## Problem Statement

Given two **sorted arrays**, return their **intersection**, i.e., elements that are present in both arrays.

* Each element in the result should appear as many times as it appears in both arrays (minimum frequency).
* The result should be in sorted order.

---

## Example

**Input:**
`arr1 = [1, 2, 2, 3, 4]`
`arr2 = [2, 2, 3, 5]`

**Output:**
`[2, 2, 3]`

---

## Approaches

---

### 1. Brute Force (Nested Loops)

#### a) Idea / Intuition

For every element in `arr1`, check if it exists in `arr2`. Mark visited elements to avoid reuse.

#### b) Algorithm Steps

1. Create a boolean array `visited` for `arr2`.
2. For each element in `arr1`:

   * Traverse `arr2`
   * If match found and not visited → add to result, mark visited
3. Return result list.

#### c) Java Code

```java
import java.util.*;

class Solution {
    public List<Integer> intersection(int[] arr1, int[] arr2) {
        List<Integer> res = new ArrayList<>();
        boolean[] visited = new boolean[arr2.length];

        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                if (!visited[j] && arr1[i] == arr2[j]) {
                    res.add(arr1[i]);
                    visited[j] = true;
                    break;
                }
            }
        }
        return res;
    }
}
```

#### d) Time Complexity

O(n * m)

#### e) Space Complexity

O(m)

#### f) Notes

* Works even if arrays are not sorted
* Inefficient for large inputs
* Not preferred in interviews

---

### 2. Better Approach (Using HashMap / Frequency)

#### a) Idea / Intuition

Store frequency of elements from one array, then match with the other.

#### b) Algorithm Steps

1. Store frequency of elements in `arr1` using HashMap
2. Traverse `arr2`:

   * If element exists in map with count > 0 → add to result and decrement count
3. Return result

#### c) Java Code

```java
import java.util.*;

class Solution {
    public List<Integer> intersection(int[] arr1, int[] arr2) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> res = new ArrayList<>();

        for (int num : arr1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (int num : arr2) {
            if (map.getOrDefault(num, 0) > 0) {
                res.add(num);
                map.put(num, map.get(num) - 1);
            }
        }

        return res;
    }
}
```

#### d) Time Complexity

O(n + m)

#### e) Space Complexity

O(n)

#### f) Notes

* Works for unsorted arrays
* Extra space used
* Good when arrays are not sorted

---

### 3. Optimal Approach (Two Pointers)

#### a) Idea / Intuition

Since arrays are sorted, use two pointers to efficiently find common elements.

#### b) Algorithm Steps

1. Initialize `i = 0`, `j = 0`
2. While both pointers are within bounds:

   * If `arr1[i] == arr2[j]` → add to result, increment both
   * If `arr1[i] < arr2[j]` → increment `i`
   * Else → increment `j`
3. Return result

#### c) Java Code

```java
import java.util.*;

class Solution {
    public List<Integer> intersection(int[] arr1, int[] arr2) {
        List<Integer> res = new ArrayList<>();
        int i = 0, j = 0;

        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] == arr2[j]) {
                res.add(arr1[i]);
                i++;
                j++;
            } else if (arr1[i] < arr2[j]) {
                i++;
            } else {
                j++;
            }
        }
        return res;
    }
}
```

#### d) Time Complexity

O(n + m)

#### e) Space Complexity

O(1) (excluding output)

#### f) Notes

* Best approach when arrays are sorted
* No extra space required
* Maintains sorted order automatically
* Most preferred in interviews

---

### 4. Binary Search Approach (Less Common)

#### a) Idea / Intuition

For each element in one array, search in the other using binary search.

#### b) Algorithm Steps

1. Iterate over smaller array
2. For each element, binary search in larger array
3. If found, add to result (handle duplicates carefully)

#### c) Java Code

```java
import java.util.*;

class Solution {
    public List<Integer> intersection(int[] arr1, int[] arr2) {
        List<Integer> res = new ArrayList<>();

        for (int num : arr1) {
            if (binarySearch(arr2, num)) {
                res.add(num);
            }
        }
        return res;
    }

    private boolean binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return true;
            else if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }
}
```

#### d) Time Complexity

O(n log m)

#### e) Space Complexity

O(1)

#### f) Notes

* Useful when one array is much smaller
* Duplicate handling is tricky
* Not as clean as two-pointer approach

---

## Comparison Table

| Approach      | Time Complexity | Space Complexity | Difficulty  | When to Use            |
| ------------- | --------------- | ---------------- | ----------- | ---------------------- |
| Brute Force   | O(n * m)        | O(m)             | Easy        | Small inputs           |
| HashMap       | O(n + m)        | O(n)             | Easy        | Unsorted arrays        |
| Two Pointers  | O(n + m)        | O(1)             | Easy-Medium | Sorted arrays (best)   |
| Binary Search | O(n log m)      | O(1)             | Medium      | One array much smaller |

---

## Key Insight

Since arrays are sorted, the two-pointer technique allows linear traversal while preserving order and efficiently finding common elements.

---

## Dry Run (Two Pointer)

| i | j | arr1[i] | arr2[j] | Action        | Result  |
| - | - | ------- | ------- | ------------- | ------- |
| 0 | 0 | 1       | 2       | i++           | []      |
| 1 | 0 | 2       | 2       | add, i++, j++ | [2]     |
| 2 | 1 | 2       | 2       | add           | [2,2]   |
| 3 | 2 | 3       | 3       | add           | [2,2,3] |

---

## Edge Cases

* One array empty → `[]`
* No common elements → `[]`
* All elements same → full overlap
* Different sizes

---

## Common Mistakes

* Ignoring duplicates handling
* Using two pointers on unsorted arrays
* Forgetting to increment both pointers on match
* Not considering frequency properly
