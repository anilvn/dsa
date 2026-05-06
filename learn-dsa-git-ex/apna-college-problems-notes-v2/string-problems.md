# String Problems — Basic to Advanced

---

## 1. Valid Palindrome

**Concept:** Two Pointers  
LeetCode 125 — check if a string is a palindrome ignoring non-alphanumeric chars and case.

**Approach:** Shrink from both ends, skip non-alphanumeric chars, compare case-insensitively.

```java
public class ValidPalindrome {
    public static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;

        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;

            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right)))
                return false;

            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama")); // true
        System.out.println(isPalindrome("race a car"));                      // false
        System.out.println(isPalindrome("No 'x' in Nixon"));                 // true
    }
}
```

**Complexity:** O(n) time, O(1) space.

---

## 2. Permutation in String

**Concept:** Sliding Window + Frequency Array  
LeetCode 567 — check if any permutation of `s1` exists as a substring of `s2`.

**Approach:**
- A permutation has the same character frequencies → compare freq arrays.
- Slide a fixed window of size `s1.length()` over `s2`, updating counts incrementally.
- `Arrays.equals(count1, count2)` on each step — O(26) = O(1).

```java
import java.util.*;

public class PermutationInString {
    public static boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) return false;

        int[] count1 = new int[26];
        int[] count2 = new int[26];
        int window = s1.length();

        for (char c : s1.toCharArray()) count1[c - 'a']++;

        for (int i = 0; i < s2.length(); i++) {
            count2[s2.charAt(i) - 'a']++;

            // Slide: remove char that just left the window
            if (i >= window)
                count2[s2.charAt(i - window) - 'a']--;

            if (Arrays.equals(count1, count2)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(checkInclusion("ab", "eidbaooo")); // true  ("ba")
        System.out.println(checkInclusion("ab", "eidboaoo")); // false
        System.out.println(checkInclusion("adc", "dcda"));    // true  ("cda")
    }
}
```

**Note:** `Arrays.equals` on size-26 arrays is O(1) in practice. For a stricter O(n) with no repeated comparisons, track a `matches` counter — decrement when a slot goes from mismatched→matched, increment on the reverse.

**Complexity:** O(n) time, O(1) space.
