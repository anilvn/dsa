
#  LongestCommonPrefixSorted
## 🔹 Notes

1. **Idea:**

   * Sort the array of strings lexicographically.
   * The common prefix of the whole array will be the prefix common to the **first** and **last** string in the sorted order.
   * Why? → Because sorting arranges strings so that the ones most different are pushed farthest apart (first and last).

2. **Steps:**

   * Sort the array.
   * Compare characters of first and last string until mismatch.
   * Return the prefix formed.

3. **Complexities:**

   * Sorting: **O(n log n \* m)** (where `n` = number of strings, `m` = average string length).
   * Prefix comparison: **O(m)**.
   * Total: **O(n log n \* m)**.
   * Space: **O(1)** (ignoring sorting overhead in Java’s sort, which is `O(n log n)` for references).

---

## 🔹 Java Code (with complexity notes)

```java
import java.util.*;

public class LongestCommonPrefixSorted {
    
    /**
     * Finds the longest common prefix among strings using sorting.
     *
     * Time Complexity: 
     *   - Sorting: O(n log n * m), where n = number of strings, m = average string length
     *   - Comparing first and last: O(m)
     *   => Overall: O(n log n * m)
     *
     * Space Complexity: 
     *   - O(1) additional (ignoring sorting overhead in Java's Arrays.sort, which uses O(log n) stack space)
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        // Step 1: Sort the array
        Arrays.sort(strs);

        // Step 2: Take first and last string
        String first = strs[0];
        String last = strs[strs.length - 1];

        // Step 3: Compare character by character
        int minLen = Math.min(first.length(), last.length());
        int i = 0;
        while (i < minLen && first.charAt(i) == last.charAt(i)) {
            i++;
        }

        // Step 4: Return prefix
        return first.substring(0, i);
    }

    public static void main(String[] args) {
        String[] words = {"flower", "flow", "flight"};
        System.out.println("Longest Common Prefix: " + longestCommonPrefix(words));
        // Output: "fl"
    }
}
```

---

## 🔹 Example Dry Run

Input:

```
["flower", "flow", "flight"]
```

Sorted → `["flight", "flow", "flower"]`

* Compare `"flight"` and `"flower"` → common `"fl"`
  Result → `"fl"` ✅

---
