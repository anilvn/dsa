# 1. longestCommonPrefixSorted

### Problem Statement

* Find the longest common prefix among all strings in an array.

---

### Example

#### Input

```text
["flower","flow","flight"]
```

#### Output

```text
"fl"
```

---

### Idea / Intuition

* Sort the array
* Only compare first and last string
* Common part between them is the answer

Because after sorting, most different strings come at ends.

---

### Optimized Approach

### steps for optimized approach

1. Sort the array
2. Take first and last string
3. Compare characters one by one
4. Stop when characters differ

### Java Code

```java
import java.util.*;

class Solution {

    public String longestCommonPrefix(String[] strs) {

        Arrays.sort(strs);

        String first = strs[0];
        String last = strs[strs.length - 1];

        int i = 0;

        while (i < first.length() && i < last.length()) {

            if (first.charAt(i) != last.charAt(i)) {
                break;
            }

            i++;
        }

        return first.substring(0, i);
    }
}

/*
Time Complexity - O(n log n)
Space Complexity - O(1)
*/
```

---

# 2. backspaceCompare

### Problem Statement

* Compare two strings after processing `#` as backspace.

---

### Example

#### Input

```text
s = "ab#c"
t = "ad#c"
```

#### Output

```text
true
```

---

### Idea / Intuition

* Traverse from end
* Skip deleted characters
* Compare valid characters directly

No extra stack needed.

---

### Brute Force Approach (Optional)

### Java Code

```java
import java.util.*;

class Solution {

    private String build(String str) {

        Stack<Character> stack = new Stack<>();

        for (char ch : str.toCharArray()) {

            if (ch == '#') {

                if (!stack.isEmpty()) {
                    stack.pop();
                }

            } else {
                stack.push(ch);
            }
        }

        StringBuilder sb = new StringBuilder();

        for (char ch : stack) {
            sb.append(ch);
        }

        return sb.toString();
    }

    public boolean backspaceCompare(String s, String t) {

        return build(s).equals(build(t));
    }
}

/*
Time Complexity - O(n + m)
Space Complexity - O(n + m)
*/
```

---

### Optimized Approach

### steps for optimized approach

1. Start from end of both strings
2. Skip characters affected by `#`
3. Compare remaining valid characters
4. If mismatch found return false

### Java Code

```java
class Solution {

    public boolean backspaceCompare(String s, String t) {

        int i = s.length() - 1;
        int j = t.length() - 1;

        while (i >= 0 || j >= 0) {

            int skipS = 0;

            while (i >= 0) {

                if (s.charAt(i) == '#') {
                    skipS++;
                    i--;
                } else if (skipS > 0) {
                    skipS--;
                    i--;
                } else {
                    break;
                }
            }

            int skipT = 0;

            while (j >= 0) {

                if (t.charAt(j) == '#') {
                    skipT++;
                    j--;
                } else if (skipT > 0) {
                    skipT--;
                    j--;
                } else {
                    break;
                }
            }

            char ch1 = i >= 0 ? s.charAt(i) : '$';
            char ch2 = j >= 0 ? t.charAt(j) : '$';

            if (ch1 != ch2) {
                return false;
            }

            i--;
            j--;
        }

        return true;
    }
}

/*
Time Complexity - O(n + m)
Space Complexity - O(1)
*/
```

---

# 3. removeDuplicatesWithAsterisk

### Problem Statement

* Remove characters based on `*` operation where `*` deletes previous character.

---

### Example

#### Input

```text
"leet**cod*e"
```

#### Output

```text
"lecoe"
```

---

### Idea / Intuition

* Use stack behavior
* Normal character → add
* `*` → remove previous character

StringBuilder can work like stack.

---

### Optimized Approach

### steps for optimized approach

1. Traverse string
2. Add normal characters
3. Remove last character when `*` found
4. Return final string

### Java Code

```java
class Solution {

    public String removeStars(String s) {

        StringBuilder sb = new StringBuilder();

        for (char ch : s.toCharArray()) {

            if (ch == '*') {
                sb.deleteCharAt(sb.length() - 1);
            } else {
                sb.append(ch);
            }
        }

        return sb.toString();
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(n)
*/
```

---

# 4. minFlipsToMakeAlternate

### Problem Statement

* Find minimum flips needed to make binary string alternating.

---

### Example

#### Input

```text
"0100"
```

#### Output

```text
1
```

---

### Idea / Intuition

* Only 2 possible patterns:

  * 010101...
  * 101010...
* Count mismatch for both
* Return minimum

---

### Optimized Approach

### steps for optimized approach

1. Traverse string
2. Compare with expected char for both patterns
3. Count mismatches
4. Return smaller count

### Java Code
```java
// brute
public class MinFlipsToMakeAlternate {

    public static int minFlipsBruteForce(String s) {
        int n = s.length();
        StringBuilder alt1 = new StringBuilder();
        StringBuilder alt2 = new StringBuilder();

        for (int i = 0; i < n; i++) {
            alt1.append(i % 2 == 0 ? '0' : '1');
            alt2.append(i % 2 == 0 ? '1' : '0');
        }

        int flips1 = 0, flips2 = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != alt1.charAt(i)) flips1++;
            if (s.charAt(i) != alt2.charAt(i)) flips2++;
        }

        return Math.min(flips1, flips2);
    }

    public static void main(String[] args) {
        System.out.println(minFlipsBruteForce("001"));        // 1
        System.out.println(minFlipsBruteForce("0001010111")); // 2
        System.out.println(minFlipsBruteForce("1111"));       // 2
    }
}
```

```java
class Solution {

    public int minOperations(String s) {

        int pattern1 = 0;
        int pattern2 = 0;

        for (int i = 0; i < s.length(); i++) {

            char expected1 = (i % 2 == 0) ? '0' : '1';
            char expected2 = (i % 2 == 0) ? '1' : '0';

            if (s.charAt(i) != expected1) {
                pattern1++;
            }

            if (s.charAt(i) != expected2) {
                pattern2++;
            }
        }

        return Math.min(pattern1, pattern2);
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(1)
*/
```

---

# 5. longestUniformSubstring

### Problem Statement

* Find longest substring containing same repeating character after at most `k` replacements.

---

### Example

#### Input

```text
s = "AABABBA"
k = 1
```

#### Output

```text
4
```

---

### Idea / Intuition

* Use sliding window
* Track most frequent character
* If changes needed > k, shrink window

Key formula:

```text
window size - max frequency <= k
```

---

### Optimized Approach

### steps for optimized approach

1. Expand window using right pointer
2. Store character frequencies
3. Track maximum frequency
4. Shrink window when invalid
5. Store maximum length

### Java Code

```java
class Solution {

    public int characterReplacement(String s, int k) {

        int[] freq = new int[26];

        int left = 0;
        int maxFreq = 0;
        int ans = 0;

        for (int right = 0; right < s.length(); right++) {

            char ch = s.charAt(right);

            freq[ch - 'A']++;

            maxFreq = Math.max(maxFreq, freq[ch - 'A']);

            while ((right - left + 1) - maxFreq > k) {

                freq[s.charAt(left) - 'A']--;
                left++;
            }

            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(1)
*/
```

---

# 6. maxConsecutiveOnesIII

### Problem Statement

* Find maximum consecutive 1s after flipping at most `k` zeros.

---

### Example

#### Input

```text
nums = [1,1,1,0,0,0,1,1,1,1,0]
k = 2
```

#### Output

```text
6
```

---

### Idea / Intuition

* Use sliding window
* Keep count of zeros
* If zeros exceed `k`, shrink window

---

### Optimized Approach

### steps for optimized approach

1. Expand right pointer
2. Count zeros in current window
3. Shrink when zero count exceeds `k`
4. Track maximum window length

### Java Code

```java
class Solution {

    public int longestOnes(int[] nums, int k) {

        int left = 0;
        int zeroCount = 0;
        int ans = 0;

        for (int right = 0; right < nums.length; right++) {

            if (nums[right] == 0) {
                zeroCount++;
            }

            while (zeroCount > k) {

                if (nums[left] == 0) {
                    zeroCount--;
                }

                left++;
            }

            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(1)
*/
```
