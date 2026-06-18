


1. **Sherlock and Valid String**
2. **Two Strings**
3. **Minimum Deletions To Make Two Strings Anagrams**
4. **Repeated String**
5. **Super Reduced String**
6. **Longest Common Prefix**
7. **Partition Labels**
8. **Permutations — Without Duplicates**
9. **Permutations — With Duplicates**
10. **Decode String**
11. **Sorting a Sentence**
12. **Length of Last Word**
13. **Isomorphic Strings**
14. **Jewels & Stones**
15. **Shuffle String**
16. **Check if Sentence is a Pangram**
17. **Re-organize String** (with 2 approaches)
18. **Subsequence Check**
19. **Valid Palindrome - II**
20. **Valid Parentheses**
21. **Reverse Words in a String II**
22. **Reverse Only Letters**
23. **Reverse Prefix of Word**
24. **Maximum Odd Binary Number**
25. **Longest Repeating Character Replacement**
26. **Palindromic Substrings**
27. **Longest Substring Without Repeating Characters**
28. **Ransom Note**
29. **Find All Anagrams in a String** (last one)


<br/>
<br/><br/><br/>

# Sherlock and Valid String

### Problem Statement

Given a string, determine whether it is a valid string.

A string is valid if:

* All characters appear the same number of times
* OR by removing only one character occurrence, the remaining characters have the same frequency

---

### Example

#### Input

```text
aabbcc
aabbccc
aabbcccc
```

#### Output

```text
YES
YES
NO
```

---

### Idea/Intution

1. Count frequency of every character
2. Store all frequencies into an array
3. Sort the frequency array
4. Check 3 cases:

* All frequencies are same
* One character frequency is `1`
  Example:
  `1 2 2 2`
* One character has exactly one extra frequency
  Example:
  `2 2 2 3`

---

### Java code

```java
import java.util.*;

class Solution {

    public static String isValid(String s) {

        Map<Character, Integer> map = new HashMap<>();

        // frequency count
        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        int[] arr = new int[map.size()];
        int idx = 0;

        for (int freq : map.values()) {
            arr[idx++] = freq;
        }

        Arrays.sort(arr);

        // only one unique character
        if (arr.length == 1) {
            return "YES";
        }

        int first = arr[0];
        int second = arr[1];

        int secondLast = arr[arr.length - 2];
        int last = arr[arr.length - 1];

        // all frequencies same
        if (first == last) {
            return "YES";
        }

        // one character frequency is 1
        // example: 1 2 2 2
        if (first == 1 && second == last) {
            return "YES";
        }

        // one character has one extra frequency
        // example: 2 2 2 3
        if (first == second &&
            secondLast == last - 1) {
            return "YES";
        }

        return "NO";
    }

    public static void main(String[] args) {

        System.out.println(isValid("aabbcc"));      // YES
        System.out.println(isValid("aabbccc"));     // YES
        System.out.println(isValid("aabbcccc"));    // NO
        System.out.println(isValid("abc"));         // YES
    }
}
```

#### Time Complexity

```text
O(n + k log k)
```

* `n` → string length
* `k` → unique characters

#### Space Complexity

```text
O(k)
```
```java
import java.util.*;

class Solution {

    static boolean isValid(String s) {

        Map<Character, Integer> map = new HashMap<>();

        // frequency count
        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        // already valid
        if (allSame(map)) {
            return true;
        }

        // try deleting one character
        for (char ch : map.keySet()) {

            int freq = map.get(ch);

            // remove one occurrence
            map.put(ch, freq - 1);

            // remove entry if frequency becomes 0
            if (map.get(ch) == 0) {
                map.remove(ch);
            }

            if (allSame(map)) {
                return true;
            }

            // backtrack
            map.put(ch, freq);
        }

        return false;
    }

    static boolean allSame(Map<Character, Integer> map) {

        int expected = -1;

        for (int freq : map.values()) {

            if (expected == -1) {
                expected = freq;
            } 
            else if (expected != freq) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        System.out.println(isValid("aabbcc"));     // true
        System.out.println(isValid("aabbccc"));    // true
        System.out.println(isValid("aabbcccc"));   // false
    }
}
```


---
<br/><br/><br/><br/>

# Two Strings

### Problem Statement

Given two strings, check whether they share at least one common substring.

If at least one common character exists, return `"YES"`, otherwise return `"NO"`.

---

### Example

#### Input

```text id="93x4i8"
s1 = "cat"
s2 = "rat"

s1 = "study"
s2 = "algo"
```

#### Output

```text id="lt6w9h"
YES
NO
```

---

### Idea/Intution

1. Store all characters of first string in a boolean array
2. Traverse second string
3. If any character already exists in array:

   * common character found
   * return `"YES"`
4. Otherwise return `"NO"`

Using boolean array gives constant time lookup.

---

### Java code

```java id="l1m0ef"
class Solution {

    static String hasCommonSubstring(String s1, String s2) {

        boolean[] seen = new boolean[256];

        // store chars from first string
        for (char ch : s1.toCharArray()) {
            seen[ch] = true;
        }

        // check common character
        for (char ch : s2.toCharArray()) {

            if (seen[ch]) {
                return "YES";
            }
        }

        return "NO";
    }

    public static void main(String[] args) {

        System.out.println(hasCommonSubstring("cat", "rat"));     
        System.out.println(hasCommonSubstring("ar+", "a"));       
        System.out.println(hasCommonSubstring("study", "algo"));  
    }
}
```

#### Time Complexity

```text id="u0v10o"
O(n + m)
```

* `n` → length of first string
* `m` → length of second string

#### Space Complexity

```text id="qg31g5"
O(1)
```

* Fixed size boolean array used (`256`)


---

<br/><br/><br/><br/>

# Minimum Deletions To Make Two Strings Anagrams

### Problem Statement

Given two strings, find the minimum number of character deletions required to make both strings anagrams.

Two strings are anagrams if both contain the same characters with same frequencies.

---

### Example

#### Input

```text id="5kh9xv"
s1 = "cde"
s2 = "abc"
```

#### Output

```text id="f0vq6e"
4
```

#### Explanation

```text id="8kxsl2"
Delete:
c,d from first string
a,b from second string

Remaining:
"e" and "c" are not same

Finally delete both

Total deletions = 4
```

---

### Idea/Intution

1. Count frequency of characters from first string
2. Subtract frequency using second string
3. Remaining frequency difference tells extra characters
4. Sum absolute differences

Frequency difference directly gives required deletions.

---

### Java code

```java id="3vjclh"
class Solution {

    static int minDeletions(String s1, String s2) {

        int[] freq = new int[26];

        // add frequency from first string
        for (char ch : s1.toCharArray()) {
            freq[ch - 'a']++;
        }

        // subtract frequency from second string
        for (char ch : s2.toCharArray()) {
            freq[ch - 'a']--;
        }

        int deletions = 0;

        // count remaining differences
        for (int count : freq) {
            deletions += Math.abs(count);
        }

        return deletions;
    }

    public static void main(String[] args) {

        System.out.println(minDeletions("cde", "abc"));      
        System.out.println(minDeletions("listen", "silent"));
        System.out.println(minDeletions("aabb", "ab"));      
    }
}
```

#### Time Complexity

```text id="ecddrb"
O(n + m)
```

* `n` → length of first string
* `m` → length of second string

#### Space Complexity

```text id="l6kslu"
O(1)
```

* Fixed frequency array of size `26` used


---

<br/><br/><br/><br/>

# Repeated String

### Problem Statement

Given a string `s` repeated infinitely, find how many times character `'a'` appears in the first `n` characters.

---

### Example

#### Input

```text id="2r5h7a"
s = "aba"
n = 10
```

#### Output

```text id="5rx9h0"
7
```

#### Explanation

```text id="g4mt9q"
Infinite string:
abaabaabaa...

First 10 characters:
abaababaab

Count of 'a' = 7
```

---

### Idea/Intution

1. Count total `'a'` in original string
2. Find how many complete repetitions fit inside `n`
3. Multiply:

   * count of `'a'`
   * by full repetitions
4. Process remaining characters separately

Avoid building the actual repeated string.

---

### Java code

```java id="v8r3tk"
class Solution {

    static long repeatedString(String s, long n) {

        long countA = 0;

        // count 'a' in original string
        for (char ch : s.toCharArray()) {

            if (ch == 'a') {
                countA++;
            }
        }

        long fullRepeats = n / s.length();
        long remaining = n % s.length();

        long answer = countA * fullRepeats;

        // count 'a' in remaining part
        for (int i = 0; i < remaining; i++) {

            if (s.charAt(i) == 'a') {
                answer++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {

        System.out.println(repeatedString("abcac", 10)); // 4
        System.out.println(repeatedString("aba", 10));   // 7
    }
}
```

#### Time Complexity

```text id="wy9u3v"
O(s)
```

* `s` → length of original string

#### Space Complexity

```text id="6g2wq8"
O(1)
```


---

<br/><br/><br/><br/>

# Super Reduced String

### Problem Statement

Given a string, repeatedly remove adjacent matching character pairs.

Continue until no more adjacent equal characters exist.

If the final string becomes empty, return:

```text id="t4h0ve"
Empty String
```

---

### Example

#### Input

```text id="f8d2nc"
s = "aabcccdd"
```

#### Output

```text id="k3x7qa"
bc
```

#### Process

```text id="rvb1a4"
aabcccdd
→ bcccdd
→ bcdd
→ bc
```

---

### Idea/Intution

1. Use stack behavior
2. Traverse characters one by one
3. If current character matches top:

   * remove pair
4. Otherwise push character
5. Remaining stack forms answer

Adjacent duplicates automatically collapse.

---

### Java code

```java id="e5l0wj"
class Solution {

    static String superReducedString(String s) {

        StringBuilder stack = new StringBuilder();

        for (char ch : s.toCharArray()) {

            int len = stack.length();

            // remove adjacent duplicate
            if (len > 0 && stack.charAt(len - 1) == ch) {
                stack.deleteCharAt(len - 1);
            } 
            else {
                stack.append(ch);
            }
        }

        return stack.length() == 0
                ? "Empty String"
                : stack.toString();
    }

    public static void main(String[] args) {

        System.out.println(superReducedString("aabcccdd")); // bc
        System.out.println(superReducedString("aa"));       // Empty String
        System.out.println(superReducedString("abba"));     // Empty String
    }
}
```

#### Time Complexity

```text id="f1dy8e"
O(n)
```

* `n` → length of string

#### Space Complexity

```text id="vg9lq2"
O(n)
```

* stack/stringbuilder stores remaining characters
```java
import java.util.*;

class Solution {

    static String superReducedString(String s) {

        Stack<Character> stack = new Stack<>();

        for (char ch : s.toCharArray()) {

            // adjacent duplicate found
            if (!stack.isEmpty() && stack.peek() == ch) {
                stack.pop();
            } 
            else {
                stack.push(ch);
            }
        }

        // build final string
        StringBuilder ans = new StringBuilder();

        for (char ch : stack) {  // st traversal : bottom -> top
            ans.append(ch);
        }

        return ans.length() == 0
                ? "Empty String"
                : ans.toString();
    }

    public static void main(String[] args) {

        System.out.println(superReducedString("aabcccdd")); // bc
        System.out.println(superReducedString("aa"));       // Empty String
        System.out.println(superReducedString("abba"));     // Empty String
    }
}
// O(n)  , O(n)
```

---

<br/><br/><br/><br/>

## Longest Common Prefix

### Problem Statement

Given an array of strings, find the longest common prefix shared among all strings.

If no common prefix exists, return empty string `""`.

---

### Example

#### Input

```text id="f3k8vm"
["flower", "flow", "flight"]
```

#### Output

```text id="d7w1qp"
"fl"
```

---

### Idea/Intution

1. Sort the array
2. After sorting:

   * smallest string comes first
   * largest string comes last
3. Compare only:

   * first string
   * last string
4. Common prefix between them is answer

Why?

If first and last strings share a prefix,
all middle strings will also share it.

---

### Java code

```java id="h8m2yc"
import java.util.*;

class Solution {

    static String longestCommonPrefix(String[] strs) {

        Arrays.sort(strs);

        String first = strs[0];
        String last = strs[strs.length - 1];

        int i = 0;

        // compare first and last string
        while (i < first.length() &&
               i < last.length() &&
               first.charAt(i) == last.charAt(i)) {

            i++;
        }

        return first.substring(0, i);
    }

    public static void main(String[] args) {

        String[] arr1 = {"flower", "flow", "flight"};
        System.out.println(longestCommonPrefix(arr1)); // fl

        String[] arr2 = {"dog", "racecar", "car"};
        System.out.println(longestCommonPrefix(arr2)); // ""
    }
}
```

#### Time Complexity

```text id="u6j2xf"
O(n log n * m)
```

* `n` → number of strings
* `m` → average string length

Sorting dominates complexity.

#### Space Complexity

```text id="r1x9qd"
O(1)
```


---

<br/><br/><br/><br/>

# Partition Labels

### Problem Statement

Given a string, partition it into maximum possible parts such that each character appears in only one partition.

Return the size of each partition.

---

### Example

#### Input

```text id="x2k8qn"
s = "ababcbacadefegdehijhklij"
```

#### Output

```text id="z6v4rt"
[9, 7, 8]
```

#### Partitions

```text id="n3p9yd"
ababcbaca
defegde
hijhklij
```

---

### Idea/Intution

1. Store last occurrence index of every character
2. Traverse string
3. Track:

   * current partition start
   * farthest last occurrence
4. When current index reaches partition end:

   * partition completed
   * store partition size
   * start next partition

Greedy idea:
Expand partition until all characters inside are fully covered.

---

### Java code

```java id="f9m1qx"
import java.util.*;

class Solution {

    static List<Integer> partitionLabels(String s) {

        int[] lastIndex = new int[26];

        // store last occurrence
        for (int i = 0; i < s.length(); i++) {
            lastIndex[s.charAt(i) - 'a'] = i;
        }

        List<Integer> ans = new ArrayList<>();

        int start = 0;
        int end = 0;

        for (int i = 0; i < s.length(); i++) {

            // expand partition end
            end = Math.max(end,
                    lastIndex[s.charAt(i) - 'a']);

            // partition completed
            if (i == end) {

                ans.add(end - start + 1);

                start = i + 1;
            }
        }

        return ans;
    }

    public static void main(String[] args) {

        System.out.println(
            partitionLabels("ababcbacadefegdehijhklij")
        ); // [9, 7, 8]
    }
}
```

#### Time Complexity

```text id="a1q7wn"
O(n)
```

* Single traversal

#### Space Complexity

```text id="d5u2lp"
O(1)
```

* Fixed size array of 26 characters


---

<br/><br/><br/><br/>

## Permutations — Backtracking Notes

---

## 1. Permutations Without Duplicates

### Problem Statement

Given an array of distinct integers, return all possible permutations.

A permutation means arranging elements in every possible order.

---

### Input Format

```text id="q2m5xv"
int[] nums
```

---

### Output Format

```text id="y0k4cf"
List of all permutations
```

---

### Example

### Input

```text id="m8z2lf"
nums = [1, 2, 3]
```

### Output

```text id="e7r4wz"
[
 [1,2,3],
 [1,3,2],
 [2,1,3],
 [2,3,1],
 [3,1,2],
 [3,2,1]
]
```

---

### Core Idea

At every step:

* Pick an unused number
* Add it into current permutation
* Recursively build next positions
* Backtrack by removing it

---

### Backtracking Flow

```text id="q7z8ap"
[]
├── [1]
│   ├── [1,2]
│   │   └── [1,2,3]
│   └── [1,3]
│       └── [1,3,2]
```

---



```java id="z7k1ru"
import java.util.*;

class Solution {

    public List<List<Integer>> permute(int[] nums) {

        List<List<Integer>> resultList = new ArrayList<>();

        backtrack(resultList, new ArrayList<>(), nums);

        return resultList;
    }

    private void backtrack(List<List<Integer>> resultList,
                           ArrayList<Integer> tempList,
                           int[] nums) {

        // One complete permutation formed
        if (tempList.size() == nums.length) {

            resultList.add(new ArrayList<>(tempList));
            return;
        }

        for (int number : nums) {

            // Skip already used number
            if (tempList.contains(number)) {
                continue;
            }

            // Choose
            tempList.add(number);

            // Explore
            backtrack(resultList, tempList, nums);

            // Undo choice
            tempList.remove(tempList.size() - 1);
        }
    }
}
```

---

### Important Observation

```java id="l4d7xa"
tempList.contains(number)
```

checks whether number is already used in current permutation.

This works only when elements are unique.

---

### Time Complexity

[
O(n! * n)
]

Why?

* Total permutations = `n!`
* `contains()` takes `O(n)`

---

### Space Complexity

[
O(n)
]

Recursion stack + temporary list.

---


## 2. Permutations With Duplicates

### Problem Statement

Given an array that may contain duplicates, return all unique permutations.

---

### Input Format

```text id="o1y9wr"
int[] nums
```

---

### Output Format

```text id="y5p2mu"
List of unique permutations
```

---

### Example

### Input

```text id="x4m9vk"
nums = [1, 1, 2]
```

### Output

```text id="j7w3ce"
[
 [1,1,2],
 [1,2,1],
 [2,1,1]
]
```

---

### Problem With Previous Approach

Using:

```java id="m5u0lb"
tempList.contains(number)
```

fails because duplicate values exist.

Example:

```text id="c8w9qx"
[1,1,2]
```

If one `1` is used:

```java id="m4x0yk"
contains(1)
```

becomes true for both `1`s.

So we cannot distinguish indices.

---

### Correct Idea

Use:

```java id="r2b9hj"
boolean[] used
```

Track usage by index instead of value.

---

### Core Idea

At every step:

* Pick unused index
* Mark it used
* Add number
* Explore recursively
* Backtrack

---



```java id="y2w6tp"
import java.util.*;

class Solution {

    public List<List<Integer>> permuteUnique(int[] nums) {

        List<List<Integer>> resultList = new ArrayList<>();

        boolean[] used = new boolean[nums.length];

        backtrack(resultList, new ArrayList<>(), nums, used);

        return resultList;
    }

    private void backtrack(List<List<Integer>> resultList,
                           ArrayList<Integer> tempList,
                           int[] nums,
                           boolean[] used) {

        // One complete permutation formed
        if (tempList.size() == nums.length) {

            // Avoid duplicate permutations
            if (!resultList.contains(tempList)) {

                resultList.add(new ArrayList<>(tempList));
            }

            return;
        }

        for (int i = 0; i < nums.length; i++) {

            // Skip used index
            if (used[i]) {
                continue;
            }

            // Choose
            used[i] = true;
            tempList.add(nums[i]);

            // Explore
            backtrack(resultList, tempList, nums, used);

            // Undo choice
            used[i] = false;
            tempList.remove(tempList.size() - 1);
        }
    }
}
```

---

### Important Observation

This solution works correctly but is NOT optimal.

Reason:

```java id="j0t7fy"
resultList.contains(tempList)
```

is expensive.

Checking duplicates in final answer costs extra time.

---

### Better Optimal Approach

Sort array and skip duplicates during recursion itself.

Condition:

```java id="v0g4tr"
if (i > 0 &&
    nums[i] == nums[i - 1] &&
    !used[i - 1]) {
    continue;
}
```
```java
import java.util.*;

class Solution {

    public List<List<Integer>> permuteUnique(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();

        Arrays.sort(nums);

        boolean[] used = new boolean[nums.length];

        backtrack(result, new ArrayList<>(), nums, used);

        return result;
    }

    private void backtrack(List<List<Integer>> result,
                           List<Integer> temp,
                           int[] nums,
                           boolean[] used) {

        if (temp.size() == nums.length) {

            result.add(new ArrayList<>(temp));
            return;
        }

        for (int i = 0; i < nums.length; i++) {

            if (used[i]) {
                continue;
            }

            // Skip duplicate branch
            if (i > 0 &&
                nums[i] == nums[i - 1] &&
                !used[i - 1]) {

                continue;
            }

            used[i] = true;
            temp.add(nums[i]);

            backtrack(result, temp, nums, used);

            used[i] = false;
            temp.remove(temp.size() - 1);
        }
    }
}
```

This avoids generating duplicate permutations entirely.

---

### Time Complexity

Current approach:

[
O(n! * n!)
]

because duplicate checking in result list is costly.

---

### Space Complexity

[
O(n)
]

For recursion + used array.

---

### Key Difference Between Both Approaches

| Feature                     | Without Duplicates    | With Duplicates                     |
| --------------------------- | --------------------- | ----------------------------------- |
| Tracking                    | `tempList.contains()` | `boolean[] used`                    |
| Duplicate Handling          | Not needed            | Required                            |
| Works for duplicate values? | No                    | Yes                                 |
| Optimal?                    | Acceptable            | No                                  |
| Better approach available?  | Not necessary         | Yes (sorting + skipping duplicates) |









---

<br/><br/><br/><br/>


## Decode String

### Problem Statement

Given an encoded string, return its decoded string.

Encoding format:

```text id="x9k2fa"
k[encoded_string]
```

means:

```text id="b6r1wp"
encoded_string repeated k times
```

---

### Example

#### Input

```text id="m4z8yc"
s = "3[a2[c]]"
```

#### Output

```text id="h7v2qt"
accaccacc
```

---

### Idea/Intution

1. Use two stacks:

   * number stack
   * string stack
2. Build current number when digit found
3. When `[` found:

   * save current string
   * save repeat count
4. When `]` found:

   * repeat current string `k` times
   * attach with previous string
5. Continue until traversal ends

Stacks help handle nested encoding.

---

### Java code

```java id="t3w9kp"
import java.util.*;

class Solution {

    static String decodeString(String s) {

        Stack<Integer> countStack = new Stack<>();
        Stack<StringBuilder> stringStack = new Stack<>();

        StringBuilder current = new StringBuilder();

        int num = 0;

        for (char ch : s.toCharArray()) {

            // build multi digit number
            if (Character.isDigit(ch)) {

                num = num * 10 + (ch - '0');
            }

            // start new encoded part
            else if (ch == '[') {

                countStack.push(num);
                stringStack.push(current);

                num = 0;
                current = new StringBuilder();
            }

            // decode current part
            else if (ch == ']') {

                int repeat = countStack.pop();

                StringBuilder prev = stringStack.pop();

                for (int i = 0; i < repeat; i++) {
                    prev.append(current);
                }

                current = prev;
            }

            // normal character
            else {
                current.append(ch);
            }
        }

        return current.toString();
    }

    public static void main(String[] args) {

        System.out.println(decodeString("3[a]2[bc]"));
        System.out.println(decodeString("3[a2[c]]"));
        System.out.println(decodeString("2[abc]3[cd]ef"));
    }
}
```

#### Time Complexity

```text id="g1y8xr"
O(n)
```

* `n` → length of string

#### Space Complexity

```text id="k5v0pm"
O(n)
```

* stacks and decoded string storage

---

<br/><br/><br/><br/>



---
# Sorting a Sentence

### Problem Statement

You are given a shuffled sentence.

Each word contains a number at the end representing its correct position in the sentence.

Return the original sentence without numbers.

---

### Example

#### Input

```text id="p7k2vx"
"is2 sentence4 This1 a3"
```

#### Output

```text id="m4r9qt"
"This is a sentence"
```

---

### Idea/Intution

1. Split sentence into words
2. Last character of each word is position
3. Place word in correct index
4. Remove number while storing
5. Join all words

Direct indexing avoids sorting.

---

### Java code

```java id="z8n1wc"
class Solution {

    static String sortSentence(String s) {

        String[] words = s.split(" ");

        String[] result = new String[words.length];

        for (String word : words) {

            int len = word.length();

            // extract position
            int pos = word.charAt(len - 1) - '0';

            // store word without number
            result[pos - 1] = word.substring(0, len - 1);
        }

        return String.join(" ", result);
    }

    public static void main(String[] args) {

        System.out.println(
            sortSentence("is2 sentence4 This1 a3")
        );
    }
}
```

#### Time Complexity

```text id="r6v3yn"
O(n)
```

* `n` → total characters in sentence

#### Space Complexity

```text id="f1x8qb"
O(k)
```

* `k` → number of words

<br/><br/><br/><br/>



---
## Length of Last Word

### Problem Statement

Given a string `s` containing words and spaces, return the length of the last word.

A word is a sequence of non-space characters.

---

### Example

#### Input

```text id="q5r8tm"
"Hello World"
```

#### Output

```text id="x2v7pa"
5
```

---

### Idea/Intution

1. Start traversal from end of string
2. Skip trailing spaces
3. Count characters until space appears
4. Count becomes answer

Reverse traversal avoids extra space.

---

### Java code

```java id="v4n1yk"
class Solution {

    static int lengthOfLastWord(String s) {

        int i = s.length() - 1;

        // skip trailing spaces
        while (i >= 0 && s.charAt(i) == ' ') {
            i--;
        }

        int length = 0;

        // count last word length
        while (i >= 0 && s.charAt(i) != ' ') {

            length++;
            i--;
        }

        return length;
    }

    public static void main(String[] args) {

        System.out.println(
            lengthOfLastWord("Hello World")
        ); // 5

        System.out.println(
            lengthOfLastWord(" fly me to the moon ")
        ); // 4
    }
}
```

#### Time Complexity

```text id="t9k2wx"
O(n)
```

* `n` → length of string

#### Space Complexity

```text id="p3m8qv"
O(1)
```

<br/><br/><br/><br/>



---

<br/><br/><br/><br/>



---



## ISOMORPHIC STRINGS

### Problem Statement

Given 2 strings `s` and `t`, check whether they are isomorphic.

Two strings are isomorphic if each character from `s` maps to exactly one character in `t`.

---

### Example

#### Sample Input

```text
s = "egg"
t = "add"
```

#### Sample Output

```text
true
```

---

#### Sample Input

```text
s = "foo"
t = "bar"
```

#### Sample Output

```text
false
```

---

#### Sample Input

```text
s = "paper"
t = "title"
```

#### Sample Output

```text
true
```

---

### Idea/Intution

* Both strings must have same length
* One character should map to only one character
* Reverse mapping should also be unique
* Use 2 hash maps to track both directions

Steps:

1. Traverse both strings together
2. Check existing mappings
3. If mapping breaks, return false
4. Otherwise store mapping
5. Return true at end

---

### Java code

```java
import java.util.HashMap;

class Solution {

    static boolean isIsomorphic(String s, String t) {

        if (s.length() != t.length()) {
            return false;
        }

        HashMap<Character, Character> mapST = new HashMap<>();
        HashMap<Character, Character> mapTS = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {

            char ch1 = s.charAt(i);
            char ch2 = t.charAt(i);

            // validate existing mapping
            if (mapST.containsKey(ch1) && mapST.get(ch1) != ch2) {
                return false;
            }

            if (mapTS.containsKey(ch2) && mapTS.get(ch2) != ch1) {
                return false;
            }

            mapST.put(ch1, ch2);
            mapTS.put(ch2, ch1);
        }

        return true;
    }

    public static void main(String[] args) {

        System.out.println(isIsomorphic("egg", "add"));     // true
        System.out.println(isIsomorphic("foo", "bar"));     // false
        System.out.println(isIsomorphic("paper", "title")); // true
    }
}
```

### Time and Space Complexities

```text
Time Complexity  : O(n)
Space Complexity : O(1)
```

<br/>

## JEWELS & STONES

### Problem Statement

Given 2 strings `jewels` and `stones`, count how many characters in `stones` are present in `jewels`.

---

### Example

#### Sample Input

```text id="f2vk8w"
jewels = "aA"
stones = "aAAbbbb"
```

#### Sample Output

```text id="p8o2zt"
3
```

---

### Idea/Intution

* Store all jewel characters in a set
* Traverse stones
* If current stone exists in set, increase count

Steps:

1. Add all jewels into HashSet
2. Traverse stones string
3. Count matching characters
4. Return count

---

### Java code

```java id="4v2m5l"
import java.util.HashSet;

class Solution {

    static int numJewelsInStones(String jewels, String stones) {

        HashSet<Character> set = new HashSet<>();

        // store jewels
        for (char ch : jewels.toCharArray()) {
            set.add(ch);
        }

        int count = 0;

        // count jewels in stones
        for (char ch : stones.toCharArray()) {
            if (set.contains(ch)) {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {

        String jewels = "aA";
        String stones = "aAAbbbb";

        System.out.println(numJewelsInStones(jewels, stones));
    }
}
```

### Time and Space Complexities

```text id="0ofl9x"
Time Complexity  : O(n + m)
Space Complexity : O(1)
```

<br/><br/><br/>



## SHUFFLE STRING

### Problem Statement

Given a string `s` and an integer array `indices`, restore the string such that each character moves to its target position.

---

### Example

#### Sample Input

```text id="a8gk2n"
s = "rockslife"

indices = {4,5,6,7,8,0,1,2,3}
```

#### Sample Output

```text id="z1wq6m"
liferocks
```

---

### Idea/Intution

* Each index tells where current character should go
* Create a result character array
* Place each character at correct position
* Convert array back to string

Steps:

1. Create result char array
2. Traverse string
3. Put character at target index
4. Return final string

---

### Java code

```java id="q7v2ke"
class Solution {

    static String restoreString(String s, int[] indices) {

        char[] result = new char[s.length()];

        for (int i = 0; i < s.length(); i++) {

            // place character at correct index
            result[indices[i]] = s.charAt(i);
        }

        return new String(result);
    }

    public static void main(String[] args) {

        String s = "rockslife";

        int[] indices = {4,5,6,7,8,0,1,2,3};

        System.out.println(restoreString(s, indices));
    }
}
```

### Time and Space Complexities

```text id="m9t4yr"
Time Complexity  : O(n)
Space Complexity : O(n)
```


<br/><br/><br/>


## CHECK IF SENTENCE IS A PANGRAM

### Problem Statement

Given a string, check whether it contains all 26 English lowercase letters at least once.

---

### Example

#### Sample Input

```text id="g3x7pl"
thequickbrownfoxjumpsoverthelazydog
```

#### Sample Output

```text id="u8k2zm"
true
```

---

### Idea/Intution

* A pangram must contain all 26 letters
* Use boolean array to mark visited characters
* Count unique characters

Steps:

1. Traverse string
2. Mark character as visited
3. Count unique letters
4. If count becomes 26, return true

---

### Java code

```java id="v6n1qa"
class Solution {

    static boolean isPangram(String str) {

        boolean[] visited = new boolean[26];

        int count = 0;

        for (char ch : str.toCharArray()) {

            int index = ch - 'a';

            if (!visited[index]) {
                visited[index] = true;
                count++;
            }
        }

        return count == 26;
    }

    public static void main(String[] args) {

        String str = "thequickbrownfoxjumpsoverthelazydog";

        System.out.println(isPangram(str));
    }
}
```

### Time and Space Complexities

```text id="j2q8wf"
Time Complexity  : O(n)
Space Complexity : O(1)
```


<br/><br/><br/>

## RE-ORGANIZE STRING

### Problem Statement

Given a string, rearrange its characters so that no two adjacent characters are the same.

If it is not possible, return an empty string.

---

### Example

#### Sample Input

```text id="z8q1mw"
s = "aab"
```

#### Sample Output

```text id="d2x9tr"
"aba"
```

---

#### Sample Input

```text id="k7n4pl"
s = "aaab"
```

#### Sample Output

```text id="v5c8hy"
""
```

---

### Idea/Intution

* Always place the character with highest remaining frequency
* Use Max Heap to get highest frequency character
* Keep previous character aside to avoid adjacent duplicates

Steps:

1. Count frequencies
2. Add all characters into max heap
3. Pick highest frequency character
4. Append to answer
5. Reinsert previous character if still remaining
6. If final length != original length, return empty string

---

### Java code

```java id="m4t8qa"
import java.util.HashMap;
import java.util.PriorityQueue;

class Solution {

    static class Pair {

        char ch;
        int freq;

        Pair(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }
    }

    static String reorganizeString(String s) {

        HashMap<Character, Integer> map = new HashMap<>();

        // frequency count
        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        // max heap based on frequency
        PriorityQueue<Pair> maxHeap =
                new PriorityQueue<>((a, b) -> b.freq - a.freq);

        for (char ch : map.keySet()) {
            maxHeap.offer(new Pair(ch, map.get(ch)));
        }

        StringBuilder result = new StringBuilder();

        Pair prev = null;

        while (!maxHeap.isEmpty()) {

            Pair curr = maxHeap.poll();

            result.append(curr.ch);
            curr.freq--;

            // add previous character back
            if (prev != null && prev.freq > 0) {
                maxHeap.offer(prev);
            }

            prev = curr;
        }

        // impossible case
        if (result.length() != s.length()) {
            return "";
        }

        return result.toString();
    }

    public static void main(String[] args) {

        System.out.println(reorganizeString("aab"));   // aba
        System.out.println(reorganizeString("aaab"));  // ""
    }
}
```

### Time and Space Complexities

```text id="p6w2zn"
Time Complexity  : O(n log k)
Space Complexity : O(k)

k = unique characters
```

### Idea/Intution

* Place the most frequent character first
* Fill even indices first: 0, 2, 4...
* Then fill remaining characters
* This keeps same characters apart

Important Observation:

* If any character frequency is greater than `(n + 1) / 2`, answer is impossible

Steps:

1. Count frequencies
2. Find maximum frequency character
3. Check impossible condition
4. Fill even positions with max character
5. Fill remaining characters
6. Return final string

---

### Java code

```java id="c7n2vm"
class Solution {

    static String reorganizeString(String str) {

        int[] hash = new int[26];

        // frequency count
        for (int i = 0; i < str.length(); i++) {
            hash[str.charAt(i) - 'a']++;
        }

        int max = 0;
        int letter = 0;

        // find max frequency character
        for (int i = 0; i < 26; i++) {

            if (hash[i] > max) {
                max = hash[i];
                letter = i;
            }
        }

        // impossible case
        if (max > (str.length() + 1) / 2) {
            return "";
        }

        char[] res = new char[str.length()];

        int idx = 0;

        // fill even positions with max character
        while (hash[letter] > 0) {

            res[idx] = (char) (letter + 'a');

            idx += 2;
            hash[letter]--;
        }

        // fill remaining characters
        for (int i = 0; i < 26; i++) {

            while (hash[i] > 0) {

                if (idx >= res.length) {
                    idx = 1;
                }

                res[idx] = (char) (i + 'a');

                idx += 2;
                hash[i]--;
            }
        }

        return String.valueOf(res);
    }

    public static void main(String[] args) {

        System.out.println(reorganizeString("aab"));   // aba
        System.out.println(reorganizeString("aaab"));  // ""
    }
}
```

### Time and Space Complexities

```text id="h4y8qb"
Time Complexity  : O(n)
Space Complexity : O(n)
```




<br/><br/><br/>

## SUBSEQUENCE CHECK

### Problem Statement

Given 2 strings `str1` and `str2`, check whether `str1` is a subsequence of `str2`.

A subsequence means characters appear in same order, but not necessarily continuously.

---

### Example

#### Sample Input

```text id="q7m2vp"
str1 = "abc"
str2 = "ahbgdc"
```

#### Sample Output

```text id="t4x8kn"
true
```

---

#### Sample Input

```text id="r5p1zc"
str1 = "axc"
str2 = "ahbgdc"
```

#### Sample Output

```text id="n8w3yl"
false
```

---

### Idea/Intution

* Use 2 pointers
* Compare characters from both strings
* If characters match, move both pointers
* Otherwise move only second pointer

Steps:

1. Start both pointers at 0
2. Traverse second string
3. Match characters in order
4. If first string fully traversed, return true

---

### Java code

```java id="m6v9qt"
class Solution {

    static boolean isSubsequence(String str1, String str2) {

        int i = 0;
        int j = 0;

        while (i < str1.length() && j < str2.length()) {

            // character matched
            if (str1.charAt(i) == str2.charAt(j)) {
                i++;
                j++;
            } else {
                j++;
            }
        }

        return i == str1.length();
    }

    public static void main(String[] args) {

        System.out.println(isSubsequence("abc", "ahbgdc")); // true

        System.out.println(isSubsequence("axc", "ahbgdc")); // false
    }
}
```

### Time and Space Complexities

```text id="y3k7rm"
Time Complexity  : O(n)
Space Complexity : O(1)
```




<br/><br/><br/>

## VALID PALINDROME - II

### Problem Statement

Given a string, check if it can become a palindrome after deleting at most one character.

---

### Example

#### Sample Input

```text
"aba"
```

#### Sample Output

```text
true
```

#### Sample Input

```text
"abca"
```

#### Sample Output

```text
true
```

---

### Idea/Intution

* Use 2 pointers:

  * one from start
  * one from end
* If characters match:

  * move both pointers
* If characters do not match:

  * try skipping left character
  * or try skipping right character
* If any one becomes palindrome → return true

---

### Java code

```java
class Solution {

    static boolean validPalindrome(String s) {

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {

            if (s.charAt(left) == s.charAt(right)) {
                left++;
                right--;
            } else {

                return isPalindrome(s, left + 1, right) ||
                       isPalindrome(s, left, right - 1);
            }
        }

        return true;
    }

    static boolean isPalindrome(String s, int left, int right) {

        while (left < right) {

            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}
```
```java
class Solution {

    static boolean validPalindrome(String s) {

        int left = 0;
        int right = s.length() - 1;

        while (left < right && 
                        s.charAt(left) == s.charAt(right) ) {
                left++;
                right--;
        }
         return isPalindrome(s, left + 1, right) ||
                       isPalindrome(s, left, right - 1);
    }

    static boolean isPalindrome(String s, int left, int right) {

        while (left < right) {

            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}

```

### Time and Space Complexities

* Time Complexity: `O(n)`
* Space Complexity: `O(1)`





<br/><br/><br/>


## VALID PARENTHESES

### Problem Statement

Given a string containing brackets `()`, `{}`, and `[]`, check whether the brackets are valid.

A string is valid if:

* every opening bracket has a matching closing bracket
* brackets close in correct order

---

### Example

#### Sample Input

```text id="m0q3fd"
"()"
```

#### Sample Output

```text id="9tv80n"
true
```

#### Sample Input

```text id="q4uz0u"
"{}()]"
```

#### Sample Output

```text id="5b40x8"
false
```

---

### Idea/Intution

* Use a stack
* Push opening brackets into stack
* When closing bracket comes:

  * stack should not be empty
  * top should match current closing bracket
* At the end:

  * stack must be empty

---

### Java code

```java id="cl4j7t"
import java.util.Stack;

class Solution {

    static boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();

        for (char ch : s.toCharArray()) {

            // opening brackets
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } else {

                if (stack.isEmpty()) {
                    return false;
                }

                char top = stack.pop();

                if ((ch == ')' && top != '(') ||
                    (ch == '}' && top != '{') ||
                    (ch == ']' && top != '[')) {

                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
```
```java
import java.util.Stack;

class Solution {

    static boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();

        for (char ch : s.toCharArray()) {
            // opening brackets
            if (ch == '(' ) stack.push(')');
            else if (ch == '{' ) stack.push('}');
            else if (ch == '[' ) stack.push(']');
            else if(stack.empty()){
                 return false;
            }else{
                char top = stack.pop();
                if (top !=ch) return false;
            }
        }

        return stack.isEmpty();
    }
}
```

### Time and Space Complexities

* Time Complexity: `O(n)`
* Space Complexity: `O(n)`


<br/><br/><br/>

## REVERSE WORDS IN A STRING II

### Problem Statement

Given a string, reverse characters of each word while keeping:

* word order same
* spaces same

---

### Example

#### Sample Input

```text id="n7a1qk"
"Welcome to this world!"
```

#### Sample Output

```text id="j9s0dx"
"emocleW ot siht !dlrow"
```

---

### Idea/Intution

* Convert string into character array
* Traverse the array
* Find each word using pointers
* Reverse characters between start and end of word
* Spaces remain unchanged

---

### Java code

```java id="7f3vba"
class Solution {

    static String reverseWords(String s) {

        char[] arr = s.toCharArray();

        int start = 0;

        for (int end = 0; end <= arr.length; end++) {

            // word end found
            if (end == arr.length || arr[end] == ' ') {

                reverse(arr, start, end - 1);

                start = end + 1;
            }
        }

        return new String(arr);
    }

    static void reverse(char[] arr, int left, int right) {

        while (left < right) {

            char temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            left++;
            right--;
        }
    }
}
```

### Time and Space Complexities

* Time Complexity: `O(n)`
* Space Complexity: `O(n)`




<br/><br/><br/>

## REVERSE ONLY LETTERS

### Problem Statement

Given a string, reverse only the English letters while keeping all other characters (like `-`, `=`, `!`) in their original positions.

---

### Example

#### Sample Input

```text id="r1a9qp"
"ab-cd"
```

#### Sample Output

```text id="k2x8mn"
"dc-ba"
```

#### Sample Input

```text id="p7q3ld"
"a-bC=dEf!"
```

#### Sample Output

```text id="t9v4sa"
"f-eD=cBa!"
```

---

### Idea/Intution

* Use two pointers:

  * left → start
  * right → end
* Move pointers inward
* If both are letters → swap
* If not letter → skip that pointer
* Keep non-letters in same position

---

### Java code

```java id="v3k8jd"
class Solution {

    static String reverseOnlyLetters(String s) {

        char[] arr = s.toCharArray();

        int left = 0;
        int right = arr.length - 1;

        while (left < right) {

            if (!isLetter(arr[left])) {
                left++;
            } 
            else if (!isLetter(arr[right])) {
                right--;
            } 
            else {
                char temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;

                left++;
                right--;
            }
        }

        return new String(arr);
    }

    static boolean isLetter(char ch) {
        return (ch >= 'a' && ch <= 'z') ||
               (ch >= 'A' && ch <= 'Z');
    }
}
```

### Time and Space Complexities

* Time Complexity: `O(n)`
* Space Complexity: `O(1)` (ignoring output array)

<br/><br/><br/>

## REVERSE PREFIX OF WORD

### Problem Statement

Given a string `s` and a character `ch`, reverse the prefix of the string starting from index `0` up to the **first occurrence of `ch`**.
If `ch` is not found, return the string as it is.

---

### Example

#### Sample Input

```text id="p1q8aa"
s = "abcdefd", ch = 'd'
```

#### Sample Output

```text id="p1q8ab"
"dcbaefd"
```

#### Sample Input

```text id="p1q8ac"
s = "xyxzxex", ch = 'x'
```

#### Sample Output

```text id="p1q8ad"
"xyxzxex" → first 'x' at index 0 → result = "xyxzxex" (reverse of first char only)
```

---

### Idea/Intution

* Find first index of character `ch`
* If not found → return original string
* Reverse substring from `0` to that index
* Keep rest unchanged

Key point:

* Only prefix is reversed, not full string

---

### Java code

```java id="p1q8ae"
class Solution {

    static String reversePrefix(String s, char ch) {

        char[] arr = s.toCharArray();

        int idx = -1;

        // find first occurrence of ch
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ch) {
                idx = i;
                break;
            }
        }

        // if not found
        if (idx == -1) return s;

        // reverse prefix
        int left = 0, right = idx;

        while (left < right) {

            char temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            left++;
            right--;
        }

        return new String(arr);
    }
}
```

---

### Time and Space Complexities

* Time Complexity: `O(n)`
* Space Complexity: `O(n)` (character array used)



<br/><br/><br/>


## MAXIMUM ODD BINARY NUMBER

### Problem Statement

Given a binary string containing at least one `'1'`, rearrange the characters to form the **maximum possible odd binary number**.

Rules:

* Result must be odd → last bit must be `1`
* Maximize the numeric value

---

### Example

#### Sample Input

```text id="b1q9aa"
"0101"
```

#### Sample Output

```text id="b1q9ab"
"1001"
```

---

### Idea/Intution

* Count number of `1`s and `0`s
* To make number **maximum**:

  * Put all extra `1`s at the left
  * Put all `0`s in middle
  * Keep **one `1` at the end** (to make it odd)
* So final pattern:

  * `(ones - 1)` → left side
  * `zeros` → middle
  * `1` → last

---

### Java code

```java id="b1q9ac"
class Solution {

    static String maximumOddBinaryNumber(String s) {

        int ones = 0;
        int zeros = 0;

        for (char ch : s.toCharArray()) {
            if (ch == '1') ones++;
            else zeros++;
        }

        StringBuilder sb = new StringBuilder();

        // put extra 1s at start
        for (int i = 0; i < ones - 1; i++) {
            sb.append('1');
        }

        // then all 0s
        for (int i = 0; i < zeros; i++) {
            sb.append('0');
        }

        // last must be 1 (to make odd)
        sb.append('1');

        return sb.toString();
    }
}
```

---

### Time and Space Complexities

* Time Complexity: `O(n)`
* Space Complexity: `O(n)`


<br/><br/><br/>

## LONGEST REPEATING CHARACTER REPLACEMENT

### Problem Statement

Given a string `s` and an integer `k`, you can replace at most `k` characters in the string.

Return the length of the **longest substring** where all characters can become the same after at most `k` changes.

---

### Example

#### Sample Input

```text id="l1a9aa"
s = "ABAB", k = 2
```

#### Sample Output

```text id="l1a9ab"
4
```

---

### Idea/Intution

* Use sliding window
* Keep frequency count of characters in window
* Track the most frequent character in current window
* Window is valid if:

  * `(window size - max frequency) <= k`
* If invalid → shrink window from left
* Keep updating max length

Key idea:

* We only care how many characters need change, not exact replacements

---

### Java code

```java id="l1a9ac"
class Solution {

    static int characterReplacement(String s, int k) {

        int[] freq = new int[26];

        int left = 0;
        int maxFreq = 0;
        int result = 0;

        for (int right = 0; right < s.length(); right++) {

            char ch = s.charAt(right);
            freq[ch - 'A']++;

            maxFreq = Math.max(maxFreq, freq[ch - 'A']);

            // shrink window if invalid
            while ((right - left + 1) - maxFreq > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
            }

            result = Math.max(result, right - left + 1);
        }

        return result;
    }
}
```

---

### Time and Space Complexities

* Time Complexity: `O(n)`
* Space Complexity: `O(1)` (fixed size array of 26)




<br/><br/><br/>

## PALINDROMIC SUBSTRINGS

### Problem Statement

Given a string `s`, count how many substrings are palindromes.

A substring is palindromic if it reads the same forward and backward.

---

### Example

#### Sample Input

```text id="p2a9aa"
"abc"
```

#### Sample Output

```text id="p2a9ab"
3
```

#### Sample Input

```text id="p2a9ac"
"aaa"
```

#### Sample Output

```text id="p2a9ad"
6
```

---

### Idea/Intution

* Every palindrome has a center
* Expand around center and count palindromes

Two cases:

* Odd length center (single character)
* Even length center (between two characters)

Steps:

* For every index:

  * expand left and right (odd case)
  * expand left and right (even case)
* Count valid palindromes

This avoids checking all substrings (efficient approach)

---

### Java code

```java id="p2a9ae"
class Solution {

    static int countSubstrings(String s) {

        int count = 0;

        for (int i = 0; i < s.length(); i++) {

            // odd length palindromes
            count += expand(s, i, i);

            // even length palindromes
            count += expand(s, i, i + 1);
        }

        return count;
    }

    static int expand(String s, int left, int right) {

        int count = 0;

        while (left >= 0 && right < s.length()
                && s.charAt(left) == s.charAt(right)) {

            count++;
            left--;
            right++;
        }

        return count;
    }
}
```

---

### Time and Space Complexities

* Time Complexity: `O(n^2)`
* Space Complexity: `O(1)`
```java
public class LongestPalindromicSubstring {

    public static String longestPalindrome(String s) {

        if (s == null || s.length() < 2) {
            return s;
        }

        int start = 0;
        int maxLength = 1;

        for (int i = 0; i < s.length(); i++) {

            // Odd length palindrome
            int len1 = expand(s, i, i);

            // Even length palindrome
            int len2 = expand(s, i, i + 1);

            int currentMax = Math.max(len1, len2);

            if (currentMax > maxLength) {

                maxLength = currentMax;

                start = i - (currentMax - 1) / 2;
            }
        }

        return s.substring(start, start + maxLength);
    }

    private static int expand(String s, int left, int right) {

        while (left >= 0 &&
               right < s.length() &&
               s.charAt(left) == s.charAt(right)) {

            left--;
            right++;
        }

        // Length after crossing boundary
        return right - left - 1;
    }

    public static void main(String[] args) {

        System.out.println(longestPalindrome("babad"));
        System.out.println(longestPalindrome("cbbd"));
        System.out.println(longestPalindrome("racecar"));
        System.out.println(longestPalindrome("abba"));
    }
}
```

<br/><br/><br/>

## LONGEST SUBSTRING WITHOUT REPEATING CHARACTERS 

### Problem Statement

Given a string `s`, find the length of the longest substring without repeating characters.

---

### Example

#### Sample Input

```text id="n3a1aa"
"abcabcbb"
```

#### Sample Output

```text id="n3a1ab"
3
```

---

### Idea/Intution

* Use sliding window technique
* Maintain a set to track unique characters in current window
* Expand window using `right`
* If duplicate found:

  * shrink window from `left` until duplicate is removed
* Update max length at each step

Key idea:

* Window always stays "valid" (no duplicates)

---

### Java code

```java id="n3a1ac"
import java.util.*;

class Solution {

    static int lengthOfLongestSubstring(String s) {

        Set<Character> charSet = new HashSet<>();

        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {

            // remove duplicates
            while (charSet.contains(s.charAt(right))) {
                charSet.remove(s.charAt(left));
                left++;
            }

            charSet.add(s.charAt(right));

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}
```

---

### Time and Space Complexities

* Time Complexity: `O(n)`
  (each character is added and removed at most once)

* Space Complexity: `O(min(n, 26/charset))`
  (set stores at most window characters)

---

### Key Learning

* Sliding window works when:

  * we maintain a "valid condition"
* HashSet is used to quickly detect duplicates
* Two pointers ensure linear traversal

---

If you want, I can also show:

* optimized version using HashMap (faster pointer jumps)
* or interview tricks to explain this in 30 seconds

```java

import java.util.*;

class Solution {

    static int lengthOfLongestSubstring(String s) {

        Map<Character, Integer> map = new HashMap<>();

        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {

            char ch = s.charAt(right);

            // duplicate found
            if (map.containsKey(ch)) {
                // move left only forward
                left = Math.max(left, map.get(ch) + 1);
            }

            map.put(ch, right);

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

}
// time - O(n)
// space - O(min(n, charset))

```

```java
// we can replace
if (map.containsKey(ch)) {
    // move left only forward
    left = Math.max(left, map.get(ch) + 1);
}

// to below code
if (map.containsKey(ch) && map.get(ch) >= left) {
    left = map.get(ch) + 1;
}
```




<br/><br/><br/>


## RANSOM NOTE

### Problem Statement

Given two strings `ransomNote` and `magazine`, check if you can construct `ransomNote` using letters from `magazine`.

Rules:

* Each letter in `magazine` can be used only once
* Return `true` if possible, otherwise `false`

---

### Example

#### Sample Input

```text id="r4a1aa"
ransomNote = "a", magazine = "b"
```

#### Sample Output

```text id="r4a1ab"
false
```

#### Sample Input

```text id="r4a1ac"
ransomNote = "aa", magazine = "ab"
```

#### Sample Output

```text id="r4a1ad"
false
```

---

### Idea/Intution

* Count frequency of each character in `magazine`
* For each character in `ransomNote`:

  * reduce frequency
  * if frequency becomes negative → not possible
* If all characters are satisfied → return true

Key idea:

* It is a simple frequency matching problem

---

### Java code

```java id="r4a1ae"
class Solution {

    static boolean canConstruct(String ransomNote, String magazine) {

        int[] freq = new int[26];

        // count magazine characters
        for (char ch : magazine.toCharArray()) {
            freq[ch - 'a']++;
        }

        // try to build ransomNote
        for (char ch : ransomNote.toCharArray()) {

            freq[ch - 'a']--;

            if (freq[ch - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }
}
```

---

### Time and Space Complexities

* Time Complexity: `O(n + m)`
* Space Complexity: `O(1)` (fixed array of size 26)

---

### Key Learning

* Use frequency array when characters are limited (a–z)
* Greedy reduction works better than searching repeatedly
* Fail early when requirement exceeds availability


<br/><br/><br/>

## FIND ALL ANAGRAMS IN A STRING

### Problem Statement

Given strings `s` and `p`, find all starting indices in `s` where an anagram of `p` exists.

An anagram means:

* same characters
* same frequency
* different order allowed

---

### Example

#### Sample Input

```text id="a1b2c3"
s = "cbaebabacd", p = "abc"
```

#### Sample Output

 ```text id="a1b2c4"
[0, 6]  // starting points only for angaragam.. with those st indeces can form anagram of p in s
```

---

### Idea/Intution

* Use sliding window of size `p.length()`
* Compare frequency of:

  * pattern `p`
  * current window in `s`
* If both frequency arrays match → valid anagram index

Key idea:

* Window size is fixed
* Only update frequencies while sliding

---

### Java code

```java id="a1b2c5"
import java.util.*;

class Solution {

    static List<Integer> findAnagrams(String s, String p) {

        List<Integer> result = new ArrayList<>();

        if (s.length() < p.length()) return result;

        int[] pFreq = new int[26];
        int[] sFreq = new int[26];

        // build frequency for pattern
        for (char ch : p.toCharArray()) {
            pFreq[ch - 'a']++;
        }

        int windowSize = p.length();

        for (int i = 0; i < s.length(); i++) {

            // add current char
            sFreq[s.charAt(i) - 'a']++;

            // remove left side when window exceeds size
            if (i >= windowSize) {
                sFreq[s.charAt(i - windowSize) - 'a']--;
            }

            // compare both frequency arrays
            if (Arrays.equals(pFreq, sFreq)) {
                result.add(i - windowSize + 1);
            }
        }

        return result;
    }
}
```

---

### Time and Space Complexities

* Time Complexity: `O(n * 26)` → effectively `O(n)`
* Space Complexity: `O(1)` (fixed arrays of size 26)

---

### Key Learning

* Fixed-size sliding window is key
* Frequency arrays make anagram check fast
* Avoid sorting every substring (that would be slow `O(n log n)`)



<br/><br/><br/>




<br/><br/><br/>




<br/><br/><br/>



<br/><br/><br/>




<br/><br/><br/>




<br/><br/><br/>

