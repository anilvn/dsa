# Trie (Prefix Tree) -- Implementing from Scratch in Java

## How to Read This Document

Each section introduces one concept or operation. Read in order. Code is clean with brief inline comments. Time complexity follows each operation.

---

## Step 1 -- Node Structure

**Problem:** Represent a trie node that can branch into up to 26 children (one per lowercase letter) and mark complete words.

```java
static class Node {
    Node[] children = new Node[26]; // index 0 = 'a', 1 = 'b', ..., 25 = 'z'
    boolean eow;                    // end-of-word flag

    public Node() {
        for (int i = 0; i < 26; i++) {
            children[i] = null;
        }
        eow = false;
    }
}
```

**Key design decisions:**
- `children[26]` assumes only lowercase English letters. Index = `character - 'a'`.
- `eow` (end of word) distinguishes a complete stored word from a mere prefix.

---

## Step 2 -- Root Declaration

```java
public static Node root = new Node();
```

The root is always an empty node -- it does not represent any character. All words share this single entry point.

---

## Step 3 -- Insert

**Problem:** Store a word in the trie, creating nodes for characters that do not yet exist.

```java
public static void insert(String word) {
    Node curr = root;
    for (int level = 0; level < word.length(); level++) {
        int idx = word.charAt(level) - 'a'; // map character to index
        if (curr.children[idx] == null) {
            curr.children[idx] = new Node(); // create node only if missing
        }
        curr = curr.children[idx];           // move down the trie
    }
    curr.eow = true; // mark the end of this word
}
```

| Operation | Time Complexity | Space Complexity | Notes                              |
|-----------|-----------------|------------------|------------------------------------|
| insert    | O(n)            | O(n)             | n = length of word; at most n new nodes |

**Edge case:** Inserting a word that is a prefix of an existing word -- only `eow` is set on the shorter word's terminal node; no structural change needed.

---

## Step 4 -- Search

**Problem:** Return true only if the exact word (not just a prefix) is stored in the trie.

```java
public static boolean search(String key) {
    Node curr = root;
    for (int level = 0; level < key.length(); level++) {
        int idx = key.charAt(level) - 'a';
        if (curr.children[idx] == null) {
            return false; // path does not exist
        }
        curr = curr.children[idx];
    }
    return curr.eow; // true only if this node marks a complete word
}
```

| Operation | Time Complexity | Space Complexity | Notes                                        |
|-----------|-----------------|------------------|----------------------------------------------|
| search    | O(n)            | O(1)             | Must check eow -- path alone is not enough   |

**Critical distinction:** If "there" is stored but not "the", then `search("the")` returns false even though the path t-h-e exists. The `eow` check is what separates search from prefix matching.

---

## Step 5 -- Prefix Search (StartsWith)

**Problem:** Return true if any stored word starts with the given prefix.

```java
public static boolean startsWith(String prefix) {
    Node curr = root;
    for (int i = 0; i < prefix.length(); i++) {
        int idx = prefix.charAt(i) - 'a';
        if (curr.children[idx] == null) {
            return false; // prefix path does not exist
        }
        curr = curr.children[idx];
    }
    return true; // prefix path exists; eow is irrelevant here
}
```

| Operation  | Time Complexity | Notes                                   |
|------------|-----------------|-----------------------------------------|
| startsWith | O(m)            | m = length of prefix; no eow check     |

**Difference from search:** `startsWith` does not check `eow` -- it only verifies the path exists.

---

## Complete Usage Example

```java
public static void main(String[] args) {
    String[] words = {"the", "a", "there", "their", "any", "thee"};

    for (String word : words) {
        insert(word);
        System.out.println("inserted " + word);
    }

    System.out.println(search("thee"));      // true
    System.out.println(search("thor"));      // false
    System.out.println(startsWith("the"));   // true
    System.out.println(startsWith("thi"));   // false
}
```

---

## Advanced Problem 1 -- Word Break

**Problem:** Determine if a string can be segmented into a sequence of dictionary words (words already inserted into the trie).

```java
public static boolean wordBreak(String key) {
    if (key.length() == 0) return true; // base case: empty string

    for (int i = 1; i <= key.length(); i++) {
        // if the first i characters form a valid word AND the rest can also be broken
        if (search(key.substring(0, i)) && wordBreak(key.substring(i))) {
            return true;
        }
    }
    return false;
}
```

| Operation | Time Complexity | Notes                                              |
|-----------|-----------------|----------------------------------------------------|
| wordBreak | O(n^3)          | n substrings, each search O(n), plus substring O(n)|

**Optimization:** This recursive approach has overlapping subproblems. Use dynamic programming (boolean `dp[]` array) to reduce to O(n^2).

---

## Advanced Problem 2 -- Longest Word with All Prefixes

**Problem:** Find the longest word in the trie such that all its prefixes are also valid words.

```java
public static String ans = ""; // store result globally

public static void longestWord(Node root, StringBuilder curr) {
    for (int i = 0; i < 26; i++) {
        // only follow paths where every node is a complete word (eow == true)
        if (root.children[i] != null && root.children[i].eow) {
            curr.append((char)(i + 'a'));           // add current character

            if (curr.length() > ans.length()) {
                ans = curr.toString();              // update result
            }

            longestWord(root.children[i], curr);    // recurse deeper

            curr.deleteCharAt(curr.length() - 1);  // backtrack
        }
    }
}

// Call: longestWord(root, new StringBuilder())
// Read: ans
```

| Operation   | Time Complexity          | Notes                                       |
|-------------|--------------------------|---------------------------------------------|
| longestWord | O(26 * n) = O(n)         | DFS with backtracking; visits each node once|

**Why it works:** By only traversing nodes where `eow == true`, we ensure every prefix along any explored path is a valid word.

---

## Advanced Problem 3 -- Count Unique Substrings

**Problem:** Count the number of distinct substrings in a given string.

**Approach:** Build a suffix trie by inserting all suffixes. Each node in the trie represents a unique substring. Total nodes - 1 (exclude root) = count of unique substrings.

```java
public static void buildSuffixTrie(String str) {
    root = new Node();
    for (int i = 0; i < str.length(); i++) {
        insert(str.substring(i)); // insert every suffix
    }
}

public static int countNodes(Node root) {
    if (root == null) return 0;
    int count = 0;
    for (int i = 0; i < 26; i++) {
        if (root.children[i] != null) {
            count += countNodes(root.children[i]);
        }
    }
    return 1 + count; // +1 for current node
}

// unique substrings = countNodes(root) - 1 (subtract root itself)
```

| Operation      | Time Complexity | Space Complexity | Notes                              |
|----------------|-----------------|------------------|------------------------------------|
| buildSuffixTrie| O(n^2)          | O(n^2)           | n suffixes, average length n/2     |
| countNodes     | O(nodes)        | O(h)             | DFS over all trie nodes            |

---

## Summary -- Complexity Table

| Operation         | Time Complexity        | Space Complexity | Notes                               |
|-------------------|------------------------|------------------|-------------------------------------|
| insert            | O(n)                   | O(n)             | n = word length                     |
| search            | O(n)                   | O(1)             | Checks eow at terminal node         |
| startsWith        | O(m)                   | O(1)             | m = prefix length; no eow check     |
| wordBreak         | O(n^3)                 | O(n)             | Optimize with DP to O(n^2)          |
| longestWord       | O(ALPHABET_SIZE * n)   | O(n)             | DFS with backtracking               |
| countSubstrings   | O(n^2)                 | O(n^2)           | Suffix trie approach                |

---

## Interview Cheat Sheet

- Trie is optimal when many strings share common prefixes -- it deduplicates storage.
- `search` vs `startsWith`: search requires `eow == true` at the end; startsWith does not.
- Insert is O(n) time and O(n) space in the worst case (no shared prefix with existing words).
- Each node in a standard trie uses O(26) space for the children array regardless of actual branching.
- Suffix trie stores all O(n^2) substrings; it is memory-intensive but enables O(n) substring queries.

---

## Use Cases

- Autocomplete systems: fast prefix matching with `startsWith`.
- Spell checkers: dictionary lookup and suggestion generation.
- IP routing: longest prefix matching in network routers.
- Word games: Boggle and Scrabble word validation.
- Text processing: pattern matching and string algorithms.
