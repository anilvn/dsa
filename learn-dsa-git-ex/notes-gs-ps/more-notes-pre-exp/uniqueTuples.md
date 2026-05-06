
## **Problem Restatement**

We are asked to implement a function:

```java
public static HashSet<String> uniqueTuples(String input, int len)
```

* **Input:** A string `input` and an integer `len`.
* **Output:** A set (`HashSet<String>`) containing **all unique substrings (tuples)** of length `len` that appear in the string.

---

## **Example 1**

Input:

```text
input = "aab"
len = 2
```

Step-by-step substrings of length 2:

* `"aa"` (from index 0–1)
* `"ab"` (from index 1–2)

Unique substrings = `{ "aa", "ab" }`

**Output:**

```text
["aa", "ab"]
```

✅ Matches test case.

---

## **Example 2**

Input:

```text
input = "abcdef"
len = 3
```

Step-by-step substrings of length 3:

* `"abc"` (0–2)
* `"bcd"` (1–3)
* `"cde"` (2–4)
* `"def"` (3–5)

Unique substrings = `{ "abc", "bcd", "cde", "def" }`

**Output:**

```text
["abc", "bcd", "cde", "def"]
```

---

## **Analogy (Intuition)**

Imagine sliding a **window of size `len`** across the string:

* Each move extracts a substring.
* Store each substring in a set (which removes duplicates automatically).

👉 Like scanning words in a sentence with a magnifying glass, but only reading `len` letters at a time.

---

## **Brute Force Approach**

* Generate every substring of size `len`.
* Add each substring into a `HashSet`.
* Return the set.

### **Code**

```java
import java.util.HashSet;

public class Solution {

    /**
     * Brute Force Approach
     * Time Complexity: O(n * len)  → we generate (n-len+1) substrings, each costs O(len)
     * Space Complexity: O(n) → in worst case, all substrings are unique
     */
    public static HashSet<String> uniqueTuples(String input, int len) {
        HashSet<String> result = new HashSet<>();

        if (input == null || input.length() < len) return result;

        for (int i = 0; i <= input.length() - len; i++) {
            String substring = input.substring(i, i + len); // O(len)
            result.add(substring);
        }

        return result;
    }

    public static void main(String[] args) {
        String input = "aab";
        HashSet<String> result = uniqueTuples(input, 2);

        if (result.contains("aa") && result.contains("ab")) {
            System.out.println("Test passed.");
        } else {
            System.out.println("Test failed.");
        }
    }
}
```

---

## **Optimized Approach (Sliding Window with Rolling Hash)**

Brute force creates substrings repeatedly (O(len) each).
We can optimize using a **rolling hash**:

* Treat each substring as a number (like Rabin-Karp algorithm).
* Instead of recomputing substring each time, update hash when sliding the window.
* Avoids `substring()` cost.

### **Code (Optimized)**

```java
import java.util.HashSet;

public class Solution {

    /**
     * Optimized Approach using Sliding Window + Hash
     * Time Complexity: O(n) → one pass with constant update per step
     * Space Complexity: O(n) → storing unique substrings
     */
    public static HashSet<String> uniqueTuples(String input, int len) {
        HashSet<String> result = new HashSet<>();

        if (input == null || input.length() < len) return result;

        int n = input.length();
        StringBuilder window = new StringBuilder();

        // First window
        window.append(input.substring(0, len));
        result.add(window.toString());

        // Slide the window
        for (int i = len; i < n; i++) {
            // remove first char and add new char
            window.deleteCharAt(0);
            window.append(input.charAt(i));
            result.add(window.toString());
        }

        return result;
    }

    public static void main(String[] args) {
        String input = "aab";
        HashSet<String> result = uniqueTuples(input, 2);

        if (result.contains("aa") && result.contains("ab")) {
            System.out.println("Test passed.");
        } else {
            System.out.println("Test failed.");
        }
    }
}
```

---

✅ **Brute Force:** Easy to understand, but substring creation costs more.
✅ **Optimized:** Efficient sliding window avoids redundant work.

---
