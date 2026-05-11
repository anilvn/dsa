# Maximum Points You Can Obtain from Cards

## Input

```text
cardPoints = [1,2,3,4,5,6,1]
k = 3
```

## Approach (2 Pointer / Sliding Window)

Take first `k` cards from left as initial sum.

Then:

* remove one card from left side
* add one card from right side
* keep updating max

This checks all combinations of:

* left picks + right picks

---

## Java Code

```java
class Solution {
    public int maxScore(int[] cardPoints, int k) {

        int n = cardPoints.length;

        int leftSum = 0;

        // Initial window -> first k cards
        for (int i = 0; i < k; i++) {
            leftSum += cardPoints[i];
        }

        int max = leftSum;

        int rightSum = 0;
        int right = n - 1;

        // Shift picks from left to right
        for (int left = k - 1; left >= 0; left--) {

            leftSum -= cardPoints[left];
            rightSum += cardPoints[right--];

            max = Math.max(max, leftSum + rightSum);
        }

        return max;
    }
}
```

---

## Dry Run

```text
Initial:
leftSum = 1+2+3 = 6

Step 1:
remove 3
add 1
total = 4

Step 2:
remove 2
add 6
total = 8

Step 3:
remove 1
add 5
total = 12
```

## Output

```text
12
```

## Complexity

```text
Time  : O(k)
Space : O(1)
```
<br/>
<br/>
<br/>
<br/>
<br/>
No. Kadane’s Algorithm is not the correct fit here.

This problem is a **variable-size sliding window + hashmap/frequency count** problem.

---

# Why Kadane does NOT fit

Kadane is used when:

* you need **maximum sum**
* over a **continuous subarray**
* usually with positive/negative values

Example:

* Maximum Subarray Sum
* Maximum Product Subarray (modified)

Kadane works because:

> if current contribution becomes bad, discard it.

---

# But this problem is different

Here the condition is:

> Window can contain at most 2 distinct fruit types.

We are not maximizing:

* sum
* product
* score

We are maximizing:

* **window length under a constraint**

That is classic sliding window.

---

# Real Pattern

This is basically:

> Longest subarray with at most K distinct elements

where:

```text
K = 2
```

---

# Correct Thinking

Maintain:

* left pointer
* right pointer
* frequency map

Expand right:

* add fruits[right]

If distinct fruits > 2:

* shrink from left

Track:

```text
maxLen = max(maxLen, right - left + 1)
```

---

# Java Solution

```java
class Solution {

    public int totalFruit(int[] fruits) {

        Map<Integer, Integer> map = new HashMap<>();

        int left = 0;
        int max = 0;

        for (int right = 0; right < fruits.length; right++) {

            map.put(fruits[right],
                    map.getOrDefault(fruits[right], 0) + 1);

            // Window invalid
            while (map.size() > 2) {

                map.put(fruits[left], map.get(fruits[left]) - 1);

                if (map.get(fruits[left]) == 0) {
                    map.remove(fruits[left]);
                }

                left++;
            }

            max = Math.max(max, right - left + 1);
        }

        return max;
    }
}
```

---

# Complexity

```text
Time  : O(n)
Space : O(1)
```

(technically O(3) map size max)

---

# Important Interview Insight

A common mistake is trying to force Kadane everywhere for subarrays.

Ask:

```text
Am I maximizing SUM?
```

If yes → maybe Kadane.

If:

* longest/shortest window
* distinct characters/elements
* condition satisfaction

→ usually Sliding Window.

<br/>
<br/>
<br/>
<br/>
<br/>
# Longest Substring With At Most K Distinct Characters

## Problem

Given a string `s` and integer `k`, return the length of the longest substring that contains at most `k` distinct characters.

---

## Input

```text id="l1a4oi"
s = "eceba"
k = 2
```

## Output

```text id="fkgqjc"
3
```

## Explanation

```text id="2ic4f4"
"ece" contains at most 2 distinct characters.
```

---

# Pattern

```text id="9i6jlwm"
Variable Size Sliding Window
```

Reason:

* window size is not fixed
* condition = at most k distinct chars

---

# Approach

Use:

* left pointer
* right pointer
* frequency map

Expand right:

* add character

If distinct chars > k:

* shrink from left

Track max window length.

---

# Java Code

```java id="z34f8y"
class Solution {

    public int lengthOfLongestSubstringKDistinct(String s, int k) {

        if (s == null || s.length() == 0 || k == 0) {
            return 0;
        }

        Map<Character, Integer> map = new HashMap<>();

        int left = 0;
        int max = 0;

        for (int right = 0; right < s.length(); right++) {

            char ch = s.charAt(right);

            map.put(ch, map.getOrDefault(ch, 0) + 1);

            // Invalid window
            while (map.size() > k) {

                char leftChar = s.charAt(left);

                map.put(leftChar, map.get(leftChar) - 1);

                if (map.get(leftChar) == 0) {
                    map.remove(leftChar);
                }

                left++;
            }

            max = Math.max(max, right - left + 1);
        }

        return max;
    }
}
```

---

# Dry Run

```text id="jlt9kc"
s = "eceba"
k = 2

Window:
"e"      -> valid
"ec"     -> valid
"ece"    -> valid (max = 3)
"eceb"   -> invalid (3 distinct)

Shrink:
"ceb" -> still invalid
"eb"  -> valid

Continue...
```

---

# Complexity

```text id="wz4cva"
Time  : O(n)
Space : O(k)
```

---

# Core Interview Insight

This template works for:

* Fruits Into Baskets
* Longest Repeating Character Replacement
* Longest Substring Without Repeating Characters
* At Most K Distinct Characters

Only the invalid condition changes.


<br/>
<br/>
<br/>
<br/>
<br/>

# Number of Substrings Containing All Three Characters

## Problem

Given a string `s` consisting only of:

```text id="4cru7o"
'a', 'b', 'c'
```

Return the number of substrings containing at least one occurrence of all three characters.

---

## Example

## Input

```text id="8i9m85"
s = "abcabc"
```

## Output

```text id="t2vs75"
10
```

---

# Pattern

```text id="9u0k6q"
Variable Size Sliding Window
```

We need:

* all 3 chars present
* count total valid substrings

---

# Key Observation

If current window:

```text id="9g5v2e"
[left ... right]
```

contains:

```text id="8hvtcj"
a, b, c
```

Then ALL substrings starting from `left`
and ending from:

```text id="4u9v2p"
right -> n-1
```

are valid.

So add:

```text id="j9mjq2"
n - right
```

---

# Approach

Expand `right`.

When window becomes valid:

* add `n - right`
* shrink from left to find more valid windows

---

# Java Code

```java id="ym9pnr"
class Solution {

    public int numberOfSubstrings(String s) {

        int[] freq = new int[3];

        int left = 0;
        int count = 0;
        int n = s.length();

        for (int right = 0; right < n; right++) {

            freq[s.charAt(right) - 'a']++;

            // Valid window
            while (freq[0] > 0 &&
                   freq[1] > 0 &&
                   freq[2] > 0) {

                count += n - right;

                freq[s.charAt(left) - 'a']--;
                left++;
            }
        }

        return count;
    }
}



class Solution {

    public int numberOfSubstrings(String s) {

        int n = s.length();
        int count = 0;

        for (int i = 0; i < n; i++) {

            boolean hasA = false;
            boolean hasB = false;
            boolean hasC = false;

            for (int j = i; j < n; j++) {

                char ch = s.charAt(j);

                if (ch == 'a') hasA = true;
                else if (ch == 'b') hasB = true;
                else if (ch == 'c') hasC = true;

                if (hasA && hasB && hasC) {
                    count++;
                }
            }
        }

        return count;
    }
}
```

---

# Dry Run

```text id="crjkuv"
s = "abcabc"

right = 2
window = "abc"

Valid:
count += 6 - 2 = 4

Substrings:
"abc"
"abca"
"abcab"
"abcabc"
```

Then shrink left and continue.

---

# Complexity

```text id="9w3z5u"
Time  : O(n)
Space : O(1)
```

---

# Important Insight

This is NOT:

* fixed window
* Kadane

This is:

```text id="0sx8hm"
count all valid subarrays/substrings
```

Sliding window with:

* expand right
* shrink while valid
* count contributions mathematically

Very common advanced sliding-window pattern.

<br/>
<br/>
<br/>
<br/>
<br/>

# Longest Repeating Character Replacement

## Problem

Given a string `s` and integer `k`.

You can replace at most `k` characters.

Return the length of the longest substring containing the same letter after replacement.

---

# Brute Force

## Idea

For every starting index:

* expand substring
* track frequency of chars
* find most frequent char
* replacements needed:

```text id="t44e7h"
windowLength - maxFreq
```

If replacements needed > k:

* stop expanding

---

# Java Code

```java id="l4cfm0"
class Solution {

    public int characterReplacement(String s, int k) {

        int n = s.length();
        int maxLen = 0;

        for (int i = 0; i < n; i++) {

            int[] freq = new int[26];
            int maxFreq = 0;

            for (int j = i; j < n; j++) {

                char ch = s.charAt(j);

                freq[ch - 'A']++;

                maxFreq = Math.max(maxFreq,
                                   freq[ch - 'A']);

                int windowLen = j - i + 1;

                int replacementsNeeded =
                        windowLen - maxFreq;

                if (replacementsNeeded <= k) {
                    maxLen = Math.max(maxLen, windowLen);
                }
            }
        }

        return maxLen;
    }
}
```

---

# Complexity

```text id="2icbmn"
Time  : O(n²)
Space : O(1)
```

---

# Optimal Sliding Window

## Core Observation

To make all chars same in a window:

```text id="a9a7ii"
replace all other chars
```

Needed replacements:

```text id="l6nzw9"
windowSize - maxFreq
```

If this exceeds `k`:

* shrink window

---

# Optimal Java Code

```java id="fmim13"
class Solution {

    public int characterReplacement(String s, int k) {

        int[] freq = new int[26];

        int left = 0;
        int maxFreq = 0;
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {

            char ch = s.charAt(right);

            freq[ch - 'A']++;

            maxFreq = Math.max(maxFreq,
                               freq[ch - 'A']);

            int windowLen = right - left + 1;

            // Invalid window
            while (windowLen - maxFreq > k) {

                freq[s.charAt(left) - 'A']--;
                left++;

                windowLen = right - left + 1;
            }

            maxLen = Math.max(maxLen, windowLen);
        }

        return maxLen;
    }
}
```

---

# Dry Run

## Input

```text id="3wo2hp"
s = "AABABBA"
k = 1
```

Window:

```text id="0e7n4t"
"AABA"
```

freq:

```text id="5hktq4"
A = 3
B = 1
```

window size:

```text id="j2j2ls"
4
```

replacements needed:

```text id="rwfbbh"
4 - 3 = 1
```

Valid.

---

# Complexity

```text id="yovmzg"
Time  : O(n)
Space : O(1)
```

---

# Important Insight

This formula is the heart:

```text id="tw0i4z"
windowSize - maxFreq
```

Meaning:

```text id="6qlg84"
How many chars must be replaced
to make entire window same.
```

This pattern appears in:

* Max Consecutive Ones III
* Binary Subarrays
* Frequency-based sliding window problems

<br/>
<br/>
<br/>
<br/>
<br/>
# Count Number of Nice Subarrays

## Problem

A subarray is called nice if it contains exactly `k` odd numbers.

Return the number of nice subarrays.

---

# Brute Force

## Idea

Generate all subarrays.

For every subarray:

* count odd numbers
* if odd count == k:

  * increment answer

---

# Java Code

```java id="twvxgq"
class Solution {

    public int numberOfSubarrays(int[] nums, int k) {

        int n = nums.length;
        int count = 0;

        for (int i = 0; i < n; i++) {

            int oddCount = 0;

            for (int j = i; j < n; j++) {

                if (nums[j] % 2 != 0) {
                    oddCount++;
                }

                if (oddCount == k) {
                    count++;
                }
            }
        }

        return count;
    }
}
```

---

# Complexity

```text id="z1zcsk"
Time  : O(n²)
Space : O(1)
```

---

# Optimal Approach

## Key Observation

Instead of:

```text id="gqeb6t"
exactly k odds
```

we calculate:

```text id="z5wsnt"
atMost(k) - atMost(k - 1)
```

Because:

```text id="0qqysj"
Exactly K
=
At Most K
-
At Most (K-1)
```

---

# Why This Works

Example:

```text id="4spk8s"
atMost(3) = subarrays with 0,1,2,3 odds
atMost(2) = subarrays with 0,1,2 odds
```

Subtract:

```text id="aj7f8l"
only subarrays with exactly 3 odds remain
```

---

# Optimal Java Code

```java id="0wly1e"
class Solution {

    public int numberOfSubarrays(int[] nums, int k) {

        return atMost(nums, k) - atMost(nums, k - 1);
    }

    private int atMost(int[] nums, int k) {

        int left = 0;
        int count = 0;
        int oddCount = 0;

        for (int right = 0; right < nums.length; right++) {

            if (nums[right] % 2 != 0) {
                oddCount++;
            }

            while (oddCount > k) {

                if (nums[left] % 2 != 0) {
                    oddCount--;
                }

                left++;
            }

            // All subarrays ending at right
            count += right - left + 1;
        }

        return count;
    }
}
```

---

# Dry Run

## Input

```text id="wy2muh"
nums = [1,1,2,1,1]
k = 3
```

Valid subarrays:

```text id="5mj7sm"
[1,1,2,1]
[1,2,1,1]
```

Answer:

```text id="umq0r9"
2
```

---

# Complexity

```text id="y5h0ho"
Time  : O(n)
Space : O(1)
```

---

# Important Sliding Window Insight

This pattern is VERY important:

```text id="3d02iq"
Exactly K
=
At Most K
-
At Most (K-1)
```

Used in:

* Binary Subarrays With Sum
* Nice Subarrays
* Subarrays With K Distinct Integers

This is one of the highest-value sliding window patterns for interviews.


<br/>
<br/>
<br/>
<br/>
<br/>


<br/>
<br/>
<br/>
<br/>
<br/>