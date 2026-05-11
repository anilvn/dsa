
## Problem Statement

Given an array of integers `nums`, return **all possible permutations** of the array.

A permutation is a rearrangement of elements in all possible orders.

---

## Example

Input:
nums = [1, 2, 3]

Output:
[
[1,2,3],
[1,3,2],
[2,1,3],
[2,3,1],
[3,1,2],
[3,2,1]
]

---

## Approach 1: Brute Force (Using Extra List)

### Idea

* Build permutations by picking elements one by one
* Maintain a `current list` and a `visited array`

### Algorithm

* If current list size == n → add to result
* Loop through all elements

  * If not visited → pick it
  * Recurse
  * Backtrack

### Java Code

```java
import java.util.*;

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        backtrack(nums, new ArrayList<>(), ans, used);
        return ans;
    }

    private void backtrack(int[] nums, List<Integer> temp, List<List<Integer>> ans, boolean[] used) {
        if (temp.size() == nums.length) {
            ans.add(new ArrayList<>(temp));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;

            used[i] = true;
            temp.add(nums[i]);

            backtrack(nums, temp, ans, used);

            temp.remove(temp.size() - 1);
            used[i] = false;
        }
    }
}
```

### Complexity

Time: O(n! * n)
Space: O(n) recursion + result

---

## Approach 2: In-Place Swapping (Optimal)

### Idea

* Fix each position by swapping elements
* Avoid extra space for `visited`

### Algorithm

* Start from index `0`
* For each index:

  * Swap current index with i
  * Recurse for next index
  * Backtrack (swap back)

### Java Code

```java
import java.util.*;

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        solve(0, nums, ans);
        return ans;
    }

    private void solve(int index, int[] nums, List<List<Integer>> ans) {
        if (index == nums.length) {
            List<Integer> temp = new ArrayList<>();
            for (int num : nums) temp.add(num);
            ans.add(temp);
            return;
        }

        for (int i = index; i < nums.length; i++) {
            swap(nums, index, i);
            solve(index + 1, nums, ans);
            swap(nums, index, i); // backtrack
        }
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
```

### Complexity

Time: O(n! * n)
Space: O(1) (excluding recursion)

---

## Approach 3: Iterative (Using Queue)

### Idea

* Build permutations level by level
* Similar to BFS

### Algorithm

* Start with empty list in queue
* For each number:

  * Insert it into every position of existing permutations

### Java Code

```java
import java.util.*;

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Queue<List<Integer>> queue = new LinkedList<>();

        queue.add(new ArrayList<>());

        for (int num : nums) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                List<Integer> old = queue.poll();

                for (int j = 0; j <= old.size(); j++) {
                    List<Integer> temp = new ArrayList<>(old);
                    temp.add(j, num);

                    if (temp.size() == nums.length) {
                        result.add(temp);
                    } else {
                        queue.add(temp);
                    }
                }
            }
        }

        return result;
    }
}
```

### Complexity

Time: O(n! * n)
Space: O(n! * n)

---

## Comparison Table

| Approach      | Extra Space | Easy to Understand | Interview Preference |
| ------------- | ----------- | ------------------ | -------------------- |
| Visited Array | O(n)        | Easy               | Good                 |
| In-place Swap | O(1)        | Medium             | Best                 |
| Iterative BFS | High        | Medium             | Rare                 |

---

## Key Insights

### 1. Why permutations are n!

* First position → n choices
* Second → n-1
* Total → n!

### 2. Backtracking Pattern

* Choose
* Explore
* Un-choose

### 3. When to use which

* Need clarity → visited array
* Need optimal → swap
* Need iterative → BFS

---

## Follow-up Variations

### Permutations with duplicates

* Use sorting + skip duplicates

### K-th permutation

* Use factorial math (no recursion)

---

If you want, I can also give:

* **Permutations with duplicates (unique permutations)**
* **Dry run step-by-step tree visualization**
* **Common interview traps + optimizations**
