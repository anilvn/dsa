This is a very common merge-style problem—think of it as a **variation of merge step in merge sort**, but with **uniqueness constraint**.

---

# Problem Statement

Given two **sorted arrays**, return their **union**:

* Result should be **sorted**
* **No duplicates**
* Can return as a list/array

---

# Example

```text
Input:
arr1 = [1,2,2,3,4]
arr2 = [2,3,5]

Output:
[1,2,3,4,5]
```

---

# Key Insight

Since arrays are **already sorted**, we can:

> Use **two pointers** and merge while skipping duplicates.

---

# Optimal Approach (Two Pointers)

## Idea

* `i` → pointer for arr1
* `j` → pointer for arr2
* Compare elements and add smaller one
* Avoid duplicates while adding

---

## Algorithm

1. Initialize `i = 0`, `j = 0`
2. While both arrays have elements:

   * If `arr1[i] < arr2[j]` → add arr1[i], `i++`
   * If `arr1[i] > arr2[j]` → add arr2[j], `j++`
   * If equal → add once, move both
3. Add remaining elements
4. Ensure duplicates are skipped

---

## Java Code (Clean & Interview Ready)

```java
import java.util.*;

class Solution {
    public static List<Integer> union(int[] arr1, int[] arr2) {
        List<Integer> result = new ArrayList<>();

        int i = 0, j = 0;

        while (i < arr1.length && j < arr2.length) {
            int a = arr1[i];
            int b = arr2[j];

            if (a < b) {
                addIfNotDuplicate(result, a);
                i++;
            } else if (a > b) {
                addIfNotDuplicate(result, b);
                j++;
            } else {
                addIfNotDuplicate(result, a);
                i++;
                j++;
            }
        }

        while (i < arr1.length) {
            addIfNotDuplicate(result, arr1[i++]);
        }

        while (j < arr2.length) {
            addIfNotDuplicate(result, arr2[j++]);
        }

        return result;
    }

    private static void addIfNotDuplicate(List<Integer> res, int val) {
        if (res.isEmpty() || res.get(res.size() - 1) != val) {
            res.add(val);
        }
    }
}
```

---

# Dry Run

```text
arr1 = [1,2,2,3]
arr2 = [2,3,4]

i=0, j=0 → 1 < 2 → add 1  
i=1, j=0 → 2 == 2 → add 2  
i=2, j=1 → 2 < 3 → skip duplicate  
i=3, j=1 → 3 == 3 → add 3  
remaining → add 4  

Result: [1,2,3,4]
```

---

# Complexity

| Metric | Value             |
| ------ | ----------------- |
| Time   | O(n + m)          |
| Space  | O(n + m) (output) |

---

# Alternative Approach (Using Set)

## Idea

* Add all elements into a `Set`
* Convert to list and sort

---

## Code

```java
import java.util.*;

class Solution {
    public static List<Integer> union(int[] arr1, int[] arr2) {
        Set<Integer> set = new HashSet<>();

        for (int x : arr1) set.add(x);
        for (int x : arr2) set.add(x);

        List<Integer> result = new ArrayList<>(set);
        Collections.sort(result);

        return result;
    }
}
```

---

## Drawback

* Loses sorted advantage
* Extra sorting → O(n log n)

---

# Edge Cases

* One array empty
* Both arrays empty
* All elements same
* No overlap
* Large duplicates

---

# Interview Insight

What interviewer expects:

* Recognize **sorted property → two pointers**
* Handle **duplicates carefully**
* Avoid unnecessary space or sorting

---

# Related Problems

* Intersection of two sorted arrays
* Merge two sorted arrays
* Remove duplicates from sorted array
* 3-sum / 4-sum (extension of two pointers)

---

If you want next:

* Intersection of two arrays (very similar but trickier)
* Merge without extra space (hard)
* 3-sum (important pattern upgrade)
