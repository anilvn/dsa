Alright 👍 Let’s carefully break this **“longest word from given letters & dictionary”** problem the same way we did for the earlier ones.

---

## **Problem Restatement**

We need to implement:

```java
public static Set<String> longestWord(String letters, Dictionary dict)
```

* **Input**

  * A string of characters: `letters`
  * A dictionary of words (`Dictionary`)

* **Output**

  * The **longest word(s)** from the dictionary that can be formed using only the given letters.

⚠️ Important: A word is valid only if every letter in the word appears in `letters` at least as many times as required.

---

## **Example 1**

Input:

```text
letters = "oet"
dictionary = {"to", "toe", "toes"}
```

Check words:

* `"to"` → ✅ can be formed
* `"toe"` → ✅ can be formed
* `"toes"` → ❌ cannot (missing `s`)

Longest valid word = `"toe"`

**Output:**

```text
{"toe"}
```

---

## **Example 2**

Input:

```text
letters = "ogds"
dictionary = {"to", "toe", "toes", "dog", "dogs"}
```

Check words:

* `"dog"` → ✅ can be formed
* `"dogs"` → ✅ can be formed
* `"to"`, `"toe"`, `"toes"` → ❌ require missing letters

Longest valid word = `"dogs"` (length 4)

**Output:**

```text
{"dogs"}
```

---

## **Analogy (Intuition)**

Imagine you have a **bag of letters** (like Scrabble tiles).

* For each word in the dictionary, check if you can “take out” the needed letters from your bag.
* Keep track of the **longest words** that fit.

👉 It’s like checking which Lego set you can build using only the blocks in your bag.

---

## **Brute Force Approach**

1. For every word in the dictionary:

   * Count characters in both `letters` and the word.
   * Check if word’s characters fit within available letters.
2. Keep track of max length found.
3. Collect all words of that max length.

### **Code**

```java
import java.util.*;

class Dictionary {
    private String[] entries;

    public Dictionary(String[] entries) {
        this.entries = entries;
    }

    public List<String> getEntries() {
        return Arrays.asList(entries);
    }
}

public class Solution {

    /**
     * Brute Force Approach
     * Time Complexity: O(N * W) 
     *   N = number of words in dictionary
     *   W = average length of a word
     * Space Complexity: O(1) extra, aside from result set
     */
    public static Set<String> longestWord(String letters, Dictionary dict) {
        Set<String> result = new HashSet<>();
        Map<Character, Integer> letterCount = getCharCount(letters);

        int maxLen = 0;

        for (String word : dict.getEntries()) {
            if (canForm(word, letterCount)) {
                if (word.length() > maxLen) {
                    result.clear();
                    result.add(word);
                    maxLen = word.length();
                } else if (word.length() == maxLen) {
                    result.add(word);
                }
            }
        }

        return result;
    }

    // helper: count chars in string
    private static Map<Character, Integer> getCharCount(String s) {
        Map<Character, Integer> count = new HashMap<>();
        for (char c : s.toCharArray()) {
            count.put(c, count.getOrDefault(c, 0) + 1);
        }
        return count;
    }

    // helper: check if word can be formed
    private static boolean canForm(String word, Map<Character, Integer> available) {
        Map<Character, Integer> copy = new HashMap<>(available);
        for (char c : word.toCharArray()) {
            if (!copy.containsKey(c) || copy.get(c) == 0) return false;
            copy.put(c, copy.get(c) - 1);
        }
        return true;
    }

    public static boolean pass() {
        Dictionary dict = new Dictionary(
            new String[]{"to", "toe", "toes", "doe", "dog", "god", "dogs", "banana"}
        );
        return new HashSet<>(Arrays.asList("toe")).equals(longestWord("oet", dict));
    }

    public static void main(String[] args) {
        if (pass()) {
            System.out.println("Pass");
        } else {
            System.err.println("Fails");
        }
    }
}
```

---

## **Optimized Approach**

Instead of re-copying the character map for each word:

* Pre-count letters (`lettersCount`).
* For each word, just check against it (without creating new maps repeatedly).

### **Code (Optimized)**

```java
import java.util.*;

class Dictionary {
    private String[] entries;

    public Dictionary(String[] entries) {
        this.entries = entries;
    }

    public List<String> getEntries() {
        return Arrays.asList(entries);
    }
}

public class Solution {

    /**
     * Optimized Approach
     * Time Complexity: O(N * W) 
     *   (cannot avoid checking all words)
     * Space Complexity: O(1) extra, aside from result set
     */
    public static Set<String> longestWord(String letters, Dictionary dict) {
        Set<String> result = new HashSet<>();
        int[] lettersCount = new int[26];
        for (char c : letters.toCharArray()) {
            lettersCount[c - 'a']++;
        }

        int maxLen = 0;

        for (String word : dict.getEntries()) {
            if (canForm(word, lettersCount)) {
                if (word.length() > maxLen) {
                    result.clear();
                    result.add(word);
                    maxLen = word.length();
                } else if (word.length() == maxLen) {
                    result.add(word);
                }
            }
        }

        return result;
    }

    private static boolean canForm(String word, int[] lettersCount) {
        int[] temp = Arrays.copyOf(lettersCount, 26);
        for (char c : word.toCharArray()) {
            if (temp[c - 'a'] == 0) return false;
            temp[c - 'a']--;
        }
        return true;
    }

    public static boolean pass() {
        Dictionary dict = new Dictionary(
            new String[]{"to", "toe", "toes", "doe", "dog", "god", "dogs", "banana"}
        );
        return new HashSet<>(Arrays.asList("toe")).equals(longestWord("oet", dict));
    }

    public static void main(String[] args) {
        if (pass()) {
            System.out.println("Pass");
        } else {
            System.err.println("Fails");
        }
    }
}
```

---

✅ **Brute Force:** Uses hash maps → easy to understand but slightly heavier.
✅ **Optimized:** Uses int array frequency → faster & memory efficient.

---

