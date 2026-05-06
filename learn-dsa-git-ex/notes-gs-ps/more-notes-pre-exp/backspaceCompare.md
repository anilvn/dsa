## backspaceCompare


## 🔑 Approaches

### **1. Brute Force (Stack based) – O(n + m) Time, O(n + m) Space**

* Use a stack (or `StringBuilder`) to simulate typing:

  * If the char is a letter → push it.
  * If it’s `#` → pop from stack (if not empty).
* Do this for both strings and compare results.

```java
public class BackspaceCompare {

    // Helper method to build the processed string after applying backspaces
    private String build(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == '#') {
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1); // simulate backspace
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // Compare two strings after processing backspaces
    public boolean backspaceCompare(String str1, String str2) {
        return build(str1).equals(build(str2));
    }

    // Main method to test
    public static void main(String[] args) {
        BackspaceCompare obj = new BackspaceCompare();

        // Test cases
        String str1 = "ab#c";
        String str2 = "ad#c";
        System.out.println(obj.backspaceCompare(str1, str2)); // true

        str1 = "ab##";
        str2 = "c#d#";
        System.out.println(obj.backspaceCompare(str1, str2)); // true

        str1 = "a#c";
        str2 = "b";
        System.out.println(obj.backspaceCompare(str1, str2)); // false

        str1 = "xy#z";
        str2 = "xz";
        System.out.println(obj.backspaceCompare(str1, str2)); // true
    }
}
```

---

### **2. Optimized Two-Pointer (O(1) Extra Space, O(n + m) Time)**

* Start from the **end** of both strings.
* Maintain a **skip counter**:

  * If you see `#`, increment skip count.
  * If you see a letter but skip > 0, decrement skip (skip this letter).
  * Else, this is a valid character.
* Compare characters one by one.

```java
public class BackspaceCompareOptimized {

    // Main method (entry point)
    public static void main(String[] args) {
        BackspaceCompareOptimized obj = new BackspaceCompareOptimized();

        // Example 1
        String str1 = "ab#c";
        String str2 = "ad#c";
        System.out.println(obj.backspaceCompare(str1, str2)); // true

        // Example 2
        str1 = "ab##";
        str2 = "c#d#";
        System.out.println(obj.backspaceCompare(str1, str2)); // true

        // Example 3
        str1 = "a#c";
        str2 = "b";
        System.out.println(obj.backspaceCompare(str1, str2)); // false
    }

    // Method to compare strings after applying backspace
    public boolean backspaceCompare(String s, String t) {
        int i = s.length() - 1;
        int j = t.length() - 1;

        while (i >= 0 || j >= 0) {
            i = getNextValidIndex(s, i);
            j = getNextValidIndex(t, j);

            if (i >= 0 && j >= 0) {
                if (s.charAt(i) != t.charAt(j)) {
                    return false;
                }
            } else if (i >= 0 || j >= 0) {
                return false; // one string ended before the other
            }
            i--;
            j--;
        }
        return true;
    }

    // Helper method to get next valid char index after backspaces
    private int getNextValidIndex(String str, int index) {
        int skip = 0;
        while (index >= 0) {
            if (str.charAt(index) == '#') {
                skip++;
                index--;
            } else if (skip > 0) {
                skip--;
                index--;
            } else {
                break;
            }
        }
        return index;
    }
}
```

---

## ✅ Dry Run (Optimized Approach)

Example:
`s = "ab#c"`, `t = "ad#c"`

```
i = 3 (c), j = 3 (c) → both 'c' match → move left
i = 1 (b), j = 1 (d)
   but index 1 has '#' effect
   skip b → i=0 (a)
   skip d → j=0 (a)
i=0 (a), j=0 (a) → match
Both end → return true
```

---

## ⏱ Complexity

* **Time:** O(n + m)
* **Space:** O(1) in optimized version

---

