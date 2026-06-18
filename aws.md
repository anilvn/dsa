## Question Statement

Given an array of integers `nums` containing **n + 1** integers, where each integer is in the range **[1, n]** inclusive.

There is **exactly one repeated number** in the array. Return that repeated number.

### Constraints

* You **cannot modify** the input array.
* You must use **only constant extra space (O(1))**.
* There is only **one duplicate value**, but it may appear more than once.

---

## Input Format

```text
nums = [array elements]
```

### Example

```text
nums = [1,3,4,2,2]
```

---

## Output Format

```text
duplicate number
```

### Example

```text
2
```

---

# Example 1

### Input

```text
nums = [1,3,4,2,2]
```

### Output

```text
2
```

### Explanation

Number `2` appears twice.

```text
1 -> once
3 -> once
4 -> once
2 -> twice
```

So return:

```text
2
```

---

# Example 2

### Input

```text
nums = [3,1,3,4,2]
```

### Output

```text
3
```

### Explanation

```text
3 -> twice
1 -> once
4 -> once
2 -> once
```

Duplicate number is:

```text
3
```

---

# Example 3

### Input

```text
nums = [3,3,3,3,3]
```

### Output

```text
3
```

### Explanation

Every occurrence is `3`.

The repeated number is:

```text
3
```

---

# Understanding the Constraint `n + 1` Numbers in Range `[1, n]`

Suppose:

```text
n = 4
```

Allowed values:

```text
1, 2, 3, 4
```

Only **4 unique numbers** exist.

But array size is:

```text
n + 1 = 5
```

Example:

```text
[1,3,4,2,2]
```

There are:

```text
5 positions
but only 4 possible values
```

By the **Pigeonhole Principle**, at least one value must repeat.

---

# Why HashSet Is Not Accepted?

A simple solution is:

```java
Set<Integer> set = new HashSet<>();

for (int num : nums) {
    if (set.contains(num))
        return num;

    set.add(num);
}
```

This works, but:

```text
Space = O(n)
```

The question explicitly says:

```text
Use only constant extra space.
```

Therefore HashSet is not the expected solution.

---

# Expected Optimal Solution

Use **Floyd's Cycle Detection (Tortoise and Hare)**.

### Complexity

```text
Time  : O(n)
Space : O(1)
```

This satisfies all constraints:

✅ Does not modify array
✅ Constant extra space
✅ Linear time complexity

This is why interviewers often ask this question—to see whether you recognize the cycle-detection pattern rather than immediately using a HashSet.



```java
import java.util.*;

public class GroupAnagrams {

    public static List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {

            char[] arr = str.toCharArray();
            Arrays.sort(arr);

            String key = new String(arr);

            map.computeIfAbsent(key, k -> new ArrayList<>())
               .add(str);
        }

        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {

        String[] strs = {"eat","tea","tan","ate","nat","bat"};

        System.out.println(groupAnagrams(strs));
    }
}
```

```java
public class FindDuplicate {

    public static int findDuplicate(int[] nums) {

        int slow = nums[0];
        int fast = nums[0];

        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        while (slow != fast);

        slow = nums[0];

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }

    public static void main(String[] args) {

        int[] nums = {1,3,4,2,2};

        System.out.println(findDuplicate(nums));
    }
}
```
