This is a classic two-pointer problem—simple on the surface, but very important for interviews because it tests in-place array manipulation.

---

## Problem Statement

Given a **sorted array**, remove duplicates **in-place** such that each element appears only once and return the new length.

* Do **not** use extra space for another array
* Modify the input array directly
* The first `k` elements of the array should contain unique elements

---

## Example

```
Input:  nums = [1,1,2,2,3,3,3,4]

Output: 4
Modified array: [1,2,3,4,_,_,_,_]
```

---

## Key Insight

Because the array is **sorted**, duplicates are always adjacent.
So we only need to compare with the **previous unique element**.

---

## Optimal Approach (Two Pointers)

### Idea

* Use pointer `i` → tracks position of last unique element
* Use pointer `j` → scans the array
* When a new unique element is found, move `i` forward and update

---

### Algorithm

1. If array is empty → return 0
2. Initialize `i = 0`
3. Traverse `j` from `1 → n-1`
4. If `nums[j] != nums[i]`

   * increment `i`
   * set `nums[i] = nums[j]`
5. Return `i + 1`

---

## Java Code (Clean, Interview Ready)

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;

        int i = 0; // last unique index

        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }

        return i + 1;
    }
}
```

---

## Dry Run

```
nums = [1,1,2,2,3]

i = 0

j = 1 → nums[1] == nums[0] → skip  
j = 2 → nums[2] != nums[0] → i=1 → nums[1]=2  
j = 3 → nums[3] == nums[1] → skip  
j = 4 → nums[4] != nums[1] → i=2 → nums[2]=3  

Result: [1,2,3,...], length = 3
```

---

## Complexity

| Metric | Value |
| ------ | ----- |
| Time   | O(n)  |
| Space  | O(1)  |

---

## Edge Cases

* Empty array → return 0
* Single element → return 1
* All elements same → return 1
* No duplicates → return n

---

## Brute Force (Not Allowed but for Understanding)

### Idea

* Use a `Set` to store unique elements
* Copy back to array

### Problem

* Uses extra space → violates constraint

---

## Follow-Up Variations (Important for Interviews)

### 1. Allow at most 2 duplicates

```
Input: [1,1,1,2,2,3]
Output: [1,1,2,2,3]
```

### Trick

Compare with `nums[i-2]` instead of `nums[i]`

---

### Code for Variation

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 2) return nums.length;

        int i = 2;

        for (int j = 2; j < nums.length; j++) {
            if (nums[j] != nums[i - 2]) {
                nums[i++] = nums[j];
            }
        }

        return i;
    }
}
```

---

## Interview Tip

The **real test** here is:

* recognizing sorted property
* thinking in terms of **write pointer vs read pointer**
* doing in-place overwrite safely

---

If you want, I can also give:

* step-by-step visualization
* similar problems (like move zeroes, remove element, etc.)
* tricky edge case tests interviewer might use
