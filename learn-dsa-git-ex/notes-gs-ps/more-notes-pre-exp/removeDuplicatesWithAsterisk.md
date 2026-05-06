Alright 🚀 Let’s put everything together neatly.

---

# 📝 Problem: Remove Duplicate Characters with `*`

### **Statement**

Given a string of lowercase characters, replace each duplicate character with `'*'`, keeping only the first occurrence of each character.
Additionally, **avoid consecutive `*` characters** — if duplicates are back-to-back, only one `*` should be placed.

---

### **Example**

```
Input:  "ababcababce"
Output: "ab*c*e"
```

---

## ✅ Approach 1: Using `StringBuilder` + `HashSet`

### **Idea**

* Use a `HashSet<Character>` to track which characters have been seen.
* Traverse the string:

  * If character **not seen** → append it to `StringBuilder` and add to set.
  * If character **already seen** → append `'*'` **only if previous char is not `'*'`**.

### **Code**

```java
import java.util.*;

public class RemoveDuplicates {
    // Time Complexity: O(n), Space Complexity: O(n)
    public static String removeDuplicatesWithAsterisk(String s) {
        HashSet<Character> seen = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (!seen.contains(c)) {
                seen.add(c);
                sb.append(c);
            } else {
                if (sb.charAt(sb.length() - 1) != '*') {
                    sb.append('*');
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String input = "ababcababce";
        System.out.println(removeDuplicatesWithAsterisk(input)); // Output: ab*c*e
    }
}
```

---

## ✅ Approach 2: In-place Using Read/Write Pointers

### **Idea**

* Convert string into a `char[]`.
* Maintain:

  * `read` pointer → scans original chars.
  * `write` pointer → writes result into array.
  * `HashSet<Character>` to track seen chars.
* For each char:

  * If **new**, write it.
  * If **duplicate**, write `'*'` only if the last written char is not `'*'`.
* Finally, return substring `[0..write)`.

### **Code**

```java
import java.util.*;

public class RemoveDuplicates {
    // Time Complexity: O(n), Space Complexity: O(n)
    public static String removeDuplicatesInPlace(String s) {
        HashSet<Character> seen = new HashSet<>();
        char[] arr = s.toCharArray();
        int write = 0;

        for (int read = 0; read < arr.length; read++) {
            char c = arr[read];
            if (!seen.contains(c)) {
                seen.add(c);
                arr[write++] = c;
            } else {
                if (write == 0 || arr[write - 1] != '*') {
                    arr[write++] = '*';
                }
            }
        }
        return new String(arr, 0, write);
    }

    public static void main(String[] args) {
        String input = "ababcababce";
        System.out.println(removeDuplicatesInPlace(input)); // Output: ab*c*e
    }
}
```

---

## 🔎 Dry Run Example

Input: `"ababcababce"`

| Read | Char | Seen Before? | Write Action   | Result So Far |
| ---- | ---- | ------------ | -------------- | ------------- |
| 0    | a    | No           | write `a`      | a             |
| 1    | b    | No           | write `b`      | ab            |
| 2    | a    | Yes          | write `*`      | ab\*          |
| 3    | b    | Yes          | skip (prev=\*) | ab\*          |
| 4    | c    | No           | write `c`      | ab\*c         |
| 5    | a    | Yes          | write `*`      | ab*c*         |
| 6    | b    | Yes          | skip (prev=\*) | ab*c*         |
| 7    | a    | Yes          | skip (prev=\*) | ab*c*         |
| 8    | b    | Yes          | skip (prev=\*) | ab*c*         |
| 9    | c    | Yes          | skip (prev=\*) | ab*c*         |
| 10   | e    | No           | write `e`      | ab*c*e        |

✅ Final Output = `"ab*c*e"`

---

## ⚖️ Comparison

| Approach                    | Pros                                                                   | Cons                                 |
| --------------------------- | ---------------------------------------------------------------------- | ------------------------------------ |
| **StringBuilder + HashSet** | Very clean and simple to implement.                                    | Creates a new string (not in-place). |
| **In-place Read/Write**     | Works directly on char array, shows interview-level pointer technique. | Slightly more verbose.               |

---

👉 Would you like me to also add a **Brute Force (no HashSet, O(n²))** approach so you can compare how it evolves from inefficient → optimized?
