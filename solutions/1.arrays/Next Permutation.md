## Problem Statement

Given an array representing a permutation of numbers, find the **next lexicographically greater permutation**.
If no such permutation exists (i.e., the array is in descending order), return the **smallest permutation (sorted ascending)**.

---

## Example

**Input:**
`nums = [1, 2, 3]`

**Output:**
`[1, 3, 2]`

**Input:**
`nums = [3, 2, 1]`

**Output:**
`[1, 2, 3]`

---

## Approaches

---

### 1. Brute Force (Generate All Permutations)

#### a) Idea / Intuition

Generate all permutations, sort them, and pick the next permutation.

#### b) Algorithm Steps

1. Generate all permutations of the array
2. Sort them lexicographically
3. Find current permutation
4. Return next permutation or first if none exists

#### c) Java Code

```java
import java.util.*;

class Solution {
    public void nextPermutation(int[] nums) {
        List<List<Integer>> perms = new ArrayList<>();
        generate(nums, 0, perms);

        List<Integer> current = new ArrayList<>();
        for (int num : nums) current.add(num);

        Collections.sort(perms, (a, b) -> {
            for (int i = 0; i < a.size(); i++) {
                if (!a.get(i).equals(b.get(i))) {
                    return a.get(i) - b.get(i);
                }
            }
            return 0;
        });

        for (int i = 0; i < perms.size(); i++) {
            if (perms.get(i).equals(current)) {
                List<Integer> next = perms.get((i + 1) % perms.size());
                for (int j = 0; j < nums.length; j++) {
                    nums[j] = next.get(j);
                }
                return;
            }
        }
    }

    private void generate(int[] nums, int index, List<List<Integer>> perms) {
        if (index == nums.length) {
            List<Integer> temp = new ArrayList<>();
            for (int num : nums) temp.add(num);
            perms.add(temp);
            return;
        }

        for (int i = index; i < nums.length; i++) {
            swap(nums, i, index);
            generate(nums, index + 1, perms);
            swap(nums, i, index);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```

#### d) Time Complexity

O(n! * n)

#### e) Space Complexity

O(n!)

#### f) Notes

* Extremely inefficient
* Only useful for conceptual understanding
* Not acceptable in interviews

---

### 2. Better Approach (Using Next Permutation Logic with Extra List)

#### a) Idea / Intuition

Convert array to list and use built-in next permutation logic (or simulate partially).

#### b) Algorithm Steps

1. Convert array to list
2. Use library or manual logic to compute next permutation
3. Copy back to array

#### c) Java Code

```java
import java.util.*;

class Solution {
    public void nextPermutation(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int num : nums) list.add(num);

        Collections.sort(list);

        List<List<Integer>> perms = new ArrayList<>();
        permute(list, 0, perms);

        for (int i = 0; i < perms.size(); i++) {
            boolean match = true;
            for (int j = 0; j < nums.length; j++) {
                if (!perms.get(i).get(j).equals(nums[j])) {
                    match = false;
                    break;
                }
            }
            if (match) {
                List<Integer> next = perms.get((i + 1) % perms.size());
                for (int j = 0; j < nums.length; j++) {
                    nums[j] = next.get(j);
                }
                return;
            }
        }
    }

    private void permute(List<Integer> list, int index, List<List<Integer>> res) {
        if (index == list.size()) {
            res.add(new ArrayList<>(list));
            return;
        }

        for (int i = index; i < list.size(); i++) {
            Collections.swap(list, i, index);
            permute(list, index + 1, res);
            Collections.swap(list, i, index);
        }
    }
}
```

#### d) Time Complexity

O(n! * n)

#### e) Space Complexity

O(n!)

#### f) Notes

* Slight variation of brute force
* Still impractical
* Avoid in interviews

---

### 3. Optimal Approach (Lexicographical Next Permutation)

#### a) Idea / Intuition

Find the first decreasing element from the right (pivot), then swap with just larger element, and reverse the suffix.

#### b) Algorithm Steps

1. Find index `i` such that `nums[i] < nums[i+1]` from right
2. If no such index → reverse entire array
3. Find index `j` such that `nums[j] > nums[i]` from right
4. Swap `nums[i]` and `nums[j]`
5. Reverse from `i+1` to end

#### c) Java Code

```java
class Solution {
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        int i = n - 2;

        while (i >= 0 && nums[i] >= nums[i + 1]) i--;

        if (i >= 0) {
            int j = n - 1;
            while (nums[j] <= nums[i]) j--;
            swap(nums, i, j);
        }

        reverse(nums, i + 1, n - 1);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left++, right--);
        }
    }
}
```

#### d) Time Complexity

O(n)

#### e) Space Complexity

O(1)

#### f) Notes

* Most important and expected solution
* Works in-place
* Requires understanding of pattern

---

### 4. Using Stack (Less Common Conceptual Approach)

#### a) Idea / Intuition

Use a stack to identify decreasing suffix and find pivot.

#### b) Algorithm Steps

1. Traverse from right and push elements until order breaks
2. Identify pivot
3. Find next greater element
4. Swap and rebuild suffix

#### c) Java Code

```java
import java.util.*;

class Solution {
    public void nextPermutation(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int i = nums.length - 1;

        while (i > 0 && nums[i - 1] >= nums[i]) {
            stack.push(nums[i]);
            i--;
        }

        if (i == 0) {
            Arrays.sort(nums);
            return;
        }

        int pivot = nums[i - 1];
        int j = nums.length - 1;

        while (nums[j] <= pivot) j--;

        nums[i - 1] = nums[j];
        nums[j] = pivot;

        Arrays.sort(nums, i, nums.length);
    }
}
```

#### d) Time Complexity

O(n log n)

#### e) Space Complexity

O(n)

#### f) Notes

* More complex than needed
* Sorting suffix increases cost
* Not preferred over optimal

---

## Comparison Table

| Approach                | Time Complexity | Space Complexity | Difficulty | When to Use   |
| ----------------------- | --------------- | ---------------- | ---------- | ------------- |
| Brute Force             | O(n! * n)       | O(n!)            | Hard       | Learning only |
| Backtracking Variant    | O(n! * n)       | O(n!)            | Hard       | Conceptual    |
| Optimal (Lexicographic) | O(n)            | O(1)             | Medium     | Best choice   |
| Stack-Based             | O(n log n)      | O(n)             | Medium     | Rare cases    |

---

## Key Insight

The next permutation is obtained by making the **smallest possible increase**:

* Find where order breaks from right
* Swap with next greater element
* Reverse suffix to get smallest arrangement

---

## Dry Run

**Input:** `[1, 2, 3]`

| Step              | Action          |
| ----------------- | --------------- |
| Find pivot        | `2 < 3` → i = 1 |
| Find next greater | `3`             |
| Swap              | `[1,3,2]`       |
| Reverse suffix    | `[1,3,2]`       |

---

## Edge Cases

* Descending array → reverse entire array
* Single element
* All elements same
* Large arrays

---

## Common Mistakes

* Not reversing suffix
* Choosing wrong pivot
* Swapping with wrong element
* Forgetting edge case (fully decreasing array)


## Dry Run — Next Permutation for Input: `[2, 4, 8, 8]`

---

### Step 1: Find Pivot (first decreasing element from right)

Scan from right:

| Index | Value | Next | Condition   |
| ----- | ----- | ---- | ----------- |
| 2     | 8     | 8    | 8 < 8 → NO  |
| 1     | 4     | 8    | 4 < 8 → YES |

**Pivot index = 1, Pivot value = 4**

---

### Step 2: Find Next Greater Element (from right)

Find element just greater than `4`:

| Index | Value | Condition   |
| ----- | ----- | ----------- |
| 3     | 8     | 8 > 4 → YES |

**Element to swap = 8 (index 3)**
(Note: we pick the **rightmost greater element**)

---

### Step 3: Swap

Before:
`[2, 4, 8, 8]`

After swapping index 1 and 3:
`[2, 8, 8, 4]`

---

### Step 4: Reverse Suffix (i+1 to end)

Suffix = elements after pivot index → `[8, 4]`

Reverse it → `[4, 8]`

---

### Final Output

`[2, 8, 4, 8]`

---

## Step-by-Step Table

| Step           | Array State   | Action                      |
| -------------- | ------------- | --------------------------- |
| Start          | [2,4,8,8]     | —                           |
| Pivot          | index = 1 (4) | found pivot                 |
| Swap           | [2,8,8,4]     | swap with rightmost greater |
| Reverse suffix | [2,8,4,8]     | reverse [8,4]               |

---

## Key Observation

* Even with duplicates (`8,8`), we always pick the **rightmost element greater than pivot**
* Reversing ensures the suffix becomes the **smallest possible order**

---

If you want, I can also show a **case where pivot is at index 0** or where duplicates create tricky scenarios.
## Optimal Approach (Lexicographical Next Permutation) — Dry Run

### Example 1

**Input:**
`[1, 2, 3]`

---

### Step 1: Find Pivot (first decreasing element from right)

Scan from right:

| Index | Value | Condition   |
| ----- | ----- | ----------- |
| 1     | 2     | 2 < 3 → YES |

**Pivot index = 1, Pivot value = 2**

---

### Step 2: Find Next Greater Element (from right)

| Index | Value | Condition   |
| ----- | ----- | ----------- |
| 2     | 3     | 3 > 2 → YES |

**Element to swap = 3 (index 2)**

---

### Step 3: Swap

Before: `[1, 2, 3]`
After:  `[1, 3, 2]`

---

### Step 4: Reverse Suffix (i+1 to end)

Suffix = `[2]` → already sorted

**Final Output:** `[1, 3, 2]`

---

---

## Example 2 (Important Case)

**Input:**
`[2, 1, 5, 4, 3, 0, 0]`

---

### Step 1: Find Pivot

Scan from right:

| Index | Value | Next | Condition   |
| ----- | ----- | ---- | ----------- |
| 5     | 0     | 0    | 0 < 0 → NO  |
| 4     | 3     | 0    | 3 < 0 → NO  |
| 3     | 4     | 3    | 4 < 3 → NO  |
| 2     | 5     | 4    | 5 < 4 → NO  |
| 1     | 1     | 5    | 1 < 5 → YES |

**Pivot index = 1, Pivot value = 1**

---

### Step 2: Find Next Greater Element (from right)

| Index | Value | Condition   |
| ----- | ----- | ----------- |
| 6     | 0     | 0 > 1 → NO  |
| 5     | 0     | NO          |
| 4     | 3     | 3 > 1 → YES |

**Element to swap = 3 (index 4)**

---

### Step 3: Swap

Before: `[2, 1, 5, 4, 3, 0, 0]`
After:  `[2, 3, 5, 4, 1, 0, 0]`

---

### Step 4: Reverse Suffix

Suffix = `[5, 4, 1, 0, 0]`
Reverse → `[0, 0, 1, 4, 5]`

---

### Final Output

`[2, 3, 0, 0, 1, 4, 5]`

---

---

## Example 3 (Edge Case)

**Input:**
`[3, 2, 1]`

---

### Step 1: Find Pivot

No element satisfies `nums[i] < nums[i+1]`

---

### Step 2: Reverse Entire Array

`[3,2,1] → [1,2,3]`

---

### Final Output

`[1, 2, 3]`

---

## Visual Summary

```
Step 1: Find break point (pivot)
        ↓
   1  2  5  4  3  0  0
      ↑
    pivot

Step 2: Find next greater from right
                     ↑

Step 3: Swap
   2  3  5  4  1  0  0

Step 4: Reverse suffix
   2  3  0  0  1  4  5
```

---

## Key Insight (Reinforced)

* Right side is always **descending** before pivot
* Swap with **just larger element**
* Reverse to get **smallest possible suffix**

---

## Common Mistake Highlight

* Choosing first greater instead of **rightmost greater**
* Forgetting to reverse suffix
* Not handling full decreasing case

---

If you want, I can give you a **step-by-step pointer movement visualization like Dutch Flag** for even deeper clarity.
