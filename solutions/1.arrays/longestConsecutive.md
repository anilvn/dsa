## Problem Statement

Given an unsorted array of integers, find the **length of the longest consecutive elements sequence**.
The sequence must consist of consecutive integers (order in array does not matter).

---

## Example

**Input:**
`nums = [100, 4, 200, 1, 3, 2]`

**Output:**
`4`

**Explanation:**
Longest consecutive sequence is `[1, 2, 3, 4]`

---

## Approaches

---

### 1. Brute Force (Check Every Element)

#### a) Idea / Intuition

For each element, try to find the next consecutive numbers (`x+1, x+2...`) by searching in the array.

#### b) Algorithm Steps

1. Initialize `longest = 0`
2. For each element `num`:

   * Set `current = num`, `count = 1`
   * While `current + 1` exists in array → increment count
3. Update maximum length

#### c) Java Code

```java id="wj2fjh"
class Solution {
    public int longestConsecutive(int[] nums) {
        int longest = 0;

        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            int count = 1;

            while (contains(nums, current + 1)) {
                current++;
                count++;
            }

            longest = Math.max(longest, count);
        }
        return longest;
    }

    private boolean contains(int[] nums, int target) {
        for (int num : nums) {
            if (num == target) return true;
        }
        return false;
    }
}
```

#### d) Time Complexity

O(n²)

#### e) Space Complexity

O(1)

#### f) Notes

* Very slow due to repeated searching
* Simple but inefficient

---

### 2. Better Approach (Sorting)

#### a) Idea / Intuition

Sort the array, then count consecutive sequences.

#### b) Algorithm Steps

1. Sort the array
2. Traverse and track current streak
3. Skip duplicates
4. Update maximum length

#### c) Java Code

```java id="3a3cm3"
import java.util.*;

class Solution {
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;

        Arrays.sort(nums);

        int longest = 1, count = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) continue;

            if (nums[i] == nums[i - 1] + 1) {
                count++;
            } else {
                count = 1;
            }

            longest = Math.max(longest, count);
        }

        return longest;
    }
}
```

#### d) Time Complexity

O(n log n)

#### e) Space Complexity

O(1)

#### f) Notes

* Easy to implement
* Handles duplicates
* Not optimal due to sorting

---

### 3. Optimal Approach (HashSet)

#### a) Idea / Intuition

Only start counting when the number is the **start of a sequence** (`num - 1` not present).

#### b) Algorithm Steps

1. Insert all elements into a HashSet
2. For each element:

   * If `num - 1` not in set → start sequence
   * Count `num, num+1, num+2...`
3. Track maximum length

#### c) Java Code

```java id="9h6e3o"
import java.util.*;

class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int num : nums) set.add(num);

        int longest = 0;

        for (int num : set) {
            if (!set.contains(num - 1)) {
                int current = num;
                int count = 1;

                while (set.contains(current + 1)) {
                    current++;
                    count++;
                }

                longest = Math.max(longest, count);
            }
        }

        return longest;
    }
}
```

#### d) Time Complexity

O(n)

#### e) Space Complexity

O(n)

#### f) Notes

* Most efficient
* Avoids redundant work
* Key trick: start only from sequence beginnings

---

### 4. Union-Find (Advanced / Rare)

#### a) Idea / Intuition

Treat numbers as nodes and union consecutive numbers.

#### b) Algorithm Steps

1. Map each number to index
2. Union `num` with `num+1` if exists
3. Track size of connected components

#### c) Java Code

```java id="0ll0u4"
import java.util.*;

class Solution {
    public int longestConsecutive(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        UnionFind uf = new UnionFind(n);

        for (int i = 0; i < n; i++) {
            if (map.containsKey(nums[i])) continue;

            map.put(nums[i], i);

            if (map.containsKey(nums[i] - 1)) {
                uf.union(i, map.get(nums[i] - 1));
            }
            if (map.containsKey(nums[i] + 1)) {
                uf.union(i, map.get(nums[i] + 1));
            }
        }

        return uf.maxSize();
    }

    class UnionFind {
        int[] parent, size;

        UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int a, int b) {
            int pa = find(a), pb = find(b);
            if (pa == pb) return;

            parent[pb] = pa;
            size[pa] += size[pb];
        }

        int maxSize() {
            int max = 0;
            for (int s : size) max = Math.max(max, s);
            return max;
        }
    }
}
```

#### d) Time Complexity

O(n α(n)) ≈ O(n)

#### e) Space Complexity

O(n)

#### f) Notes

* Overkill for this problem
* Useful in graph-based thinking
* Rarely expected in interviews

---

## Comparison Table

| Approach    | Time Complexity | Space Complexity | Difficulty | When to Use        |
| ----------- | --------------- | ---------------- | ---------- | ------------------ |
| Brute Force | O(n²)           | O(1)             | Easy       | Learning           |
| Sorting     | O(n log n)      | O(1)             | Easy       | Simpler solution   |
| HashSet     | O(n)            | O(n)             | Medium     | Best approach      |
| Union-Find  | O(n)            | O(n)             | Hard       | Advanced scenarios |

---

## Key Insight

The trick is to **avoid redundant work** by only starting sequences from numbers that **do not have a predecessor** (`num - 1` not present). This ensures each sequence is counted exactly once.

---

## Dry Run (Optimal)

**Input:** `[100, 4, 200, 1, 3, 2]`

Set = `{1,2,3,4,100,200}`

| num | start? | sequence | length |
| --- | ------ | -------- | ------ |
| 1   | YES    | 1→2→3→4  | 4      |
| 2   | NO     | skip     | —      |
| 3   | NO     | skip     | —      |
| 4   | NO     | skip     | —      |
| 100 | YES    | 100      | 1      |
| 200 | YES    | 200      | 1      |

Answer = **4**

---

## Edge Cases

* Empty array → `0`
* Single element → `1`
* Duplicates → ignore
* Negative numbers → handled naturally

---

## Common Mistakes

* Starting sequence from every element (causes O(n²))
* Not handling duplicates
* Forgetting to check `num - 1` condition
* Using sorting when optimal is expected

---

If you want, I can connect this with **Longest Increasing Subsequence vs Consecutive Sequence** — a very common confusion in interviews.


<br/>
<br/>
<br/>

## Problem Statement

Understand the difference between:

* **Longest Increasing Subsequence (LIS)**
* **Longest Consecutive Sequence (LCS - consecutive elements problem)**

Both look similar but are fundamentally different in constraints and solutions.

---

## Example

**Input:**
`nums = [100, 4, 200, 1, 3, 2]`

* **Longest Consecutive Sequence:** `[1,2,3,4]` → length = `4`
* **Longest Increasing Subsequence:** `[1,3,4]` or `[1,2,3]` → length = `3`

---

## Core Difference

| Aspect                   | Longest Consecutive            | Longest Increasing Subsequence |
| ------------------------ | ------------------------------ | ------------------------------ |
| Order in array matters?  | No                             | Yes                            |
| Must be adjacent values? | Yes (`+1`)                     | No                             |
| Can skip elements?       | No (must be continuous values) | Yes                            |
| Sorting allowed?         | Conceptually yes (set-based)   | No (breaks subsequence)        |

---

## Approach for Longest Consecutive Sequence

### Idea

Use a **HashSet** and only start sequences when `num - 1` is not present.

### Key Property

* You are forming **value-based chains**, not index-based sequences.

### Code

```java
import java.util.*;

class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);

        int longest = 0;

        for (int num : set) {
            if (!set.contains(num - 1)) {
                int current = num;
                int count = 1;

                while (set.contains(current + 1)) {
                    current++;
                    count++;
                }

                longest = Math.max(longest, count);
            }
        }
        return longest;
    }
}
```

### Time Complexity

O(n)

---

## Approach for Longest Increasing Subsequence

---

### 1. DP Approach (Classic)

#### Idea

For each index, compute LIS ending at that index.

#### Code

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int max = 1;

        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
```

#### Time Complexity

O(n²)

---

### 2. Optimal (Binary Search)

#### Idea

Maintain a list where `tails[i]` = smallest ending of LIS of length `i+1`.

#### Code

```java
import java.util.*;

class Solution {
    public int lengthOfLIS(int[] nums) {
        List<Integer> list = new ArrayList<>();

        for (int num : nums) {
            int idx = Collections.binarySearch(list, num);
            if (idx < 0) idx = -(idx + 1);

            if (idx == list.size()) list.add(num);
            else list.set(idx, num);
        }

        return list.size();
    }
}
```

#### Time Complexity

O(n log n)

---

## Side-by-Side Visualization

### Input

`[10, 9, 2, 5, 3, 7, 101, 18]`

---

### Longest Consecutive

Set = `{2,3,5,7,9,10,18,101}`

Sequences:

* `2 → 3` → length 2
* others isolated

Answer = **2**

---

### Longest Increasing Subsequence

Valid subsequence:

* `[2,3,7,101]` → length = **4**

---

## Key Insight

* **Consecutive Sequence** → Think in terms of **numbers existing or not (HashSet)**
* **LIS** → Think in terms of **order and subsequence (DP / Binary Search)**

---

## Mental Model

| Problem     | Think Like                                                            |
| ----------- | --------------------------------------------------------------------- |
| Consecutive | "Can I build a chain of x, x+1, x+2?"                                 |
| LIS         | "What is the longest increasing pattern I can form respecting order?" |

---

## Common Mistakes

* Applying LIS logic to consecutive problem
* Sorting array for LIS (breaks subsequence)
* Using DP for consecutive (unnecessary)
* Ignoring order requirement in LIS

---

## Quick Interview Trick

If you see:

* `"consecutive"` → use **HashSet**
* `"subsequence"` → use **DP / Binary Search**

---

## Final Summary

* **Consecutive = value continuity → O(n) HashSet**
* **LIS = order-based growth → O(n log n) Binary Search**

---

If you want, I can give you **tricky interview questions where both concepts are mixed** — those are very commonly asked.
