# VALID ANAGRAM

### Problem Statement

* Given two strings, check whether both are anagrams or not.
* Two strings are anagrams if they contain the same characters with the same frequency.

---

### Example

#### Input

```text
str1 = "listen"
str2 = "silent"
```

#### Output

```text
true
```

---

### Idea / Intuition

* Convert both strings to lowercase
* Remove spaces
* Use a frequency array of size 26
* Increase count for characters from first string
* Decrease count for characters from second string
* If all counts become 0, both strings are anagrams

---

### Brute Force Approach (Optional)

### Java Code

```java
import java.util.Arrays;

class Solution {

    public boolean isAnagram(String str1, String str2) {

        str1 = str1.toLowerCase().replace(" ", "");
        str2 = str2.toLowerCase().replace(" ", "");

        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();

        Arrays.sort(arr1);
        Arrays.sort(arr2);

        return Arrays.equals(arr1, arr2);
    }
}

// Time Complexity - O(n log n)
// Space Complexity - O(n)
```

---

### Optimized Approach

### steps for optimized approach

1. Convert both strings to lowercase
2. Remove spaces
3. If lengths are different, return false
4. Create frequency array of size 26
5. Increment count using first string
6. Decrement count using second string
7. If any count is not 0, return false
8. Otherwise return true

### Java Code

```java
class Solution {

    public boolean isAnagram(String str1, String str2) {

        str1 = str1.toLowerCase().replace(" ", "");
        str2 = str2.toLowerCase().replace(" ", "");

        if (str1.length() != str2.length()) {
            return false;
        }

        int[] counts = new int[26];

        for (int i = 0; i < str1.length(); i++) {
            counts[str1.charAt(i) - 'a']++;
            counts[str2.charAt(i) - 'a']--;
        }

        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }
}

// Time Complexity - O(n)
// Space Complexity - O(1)
```
