

##  Longest Word Finder

**Problem:** Given a string of letters and a dictionary, find the longest word(s) that can be formed using only the given letters (respecting frequency).

**Approach:**
- Count frequency of each letter in the input string.
- For each dictionary word, check if it can be formed using available letter counts.
- Track the maximum length and collect all words of that length.

**Complexity:** O(n * m) — n = dict size, m = avg word length

```java
import java.util.*;

public class LongestWordFinder {

    public static Set<String> longestWord(String letters, Set<String> dict) {
        Set<String> result = new HashSet<>();
        if (letters == null || dict == null) return result;

        int[] letterCount = new int[26];
        for (char c : letters.toCharArray())
            letterCount[c - 'a']++;

        int maxLength = 0;

        for (String word : dict) {
            if (canForm(word, letterCount)) {
                if (word.length() > maxLength) {
                    result.clear();
                    result.add(word);
                    maxLength = word.length();
                } else if (word.length() == maxLength) {
                    result.add(word);
                }
            }
        }

        return result;
    }

    private static boolean canForm(String word, int[] letterCount) {
        int[] temp = new int[26];
        for (char c : word.toCharArray()) {
            temp[c - 'a']++;
            if (temp[c - 'a'] > letterCount[c - 'a']) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Set<String> dict1 = new HashSet<>(Arrays.asList("to", "toe", "toes"));
        System.out.println(longestWord("oet", dict1));   // [toe]

        Set<String> dict2 = new HashSet<>(Arrays.asList("to", "toe", "toes", "dog", "dogs"));
        System.out.println(longestWord("ogds", dict2));  // [dogs]
    }
}
```

---

