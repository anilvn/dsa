Perfect 👍 — let’s solve

👉 **Last Index of Character in String – Find last index of `x` in a string**

---

# 📝 Problem Statement

Given a string `s` and a character `x`, return the **last index** of `x` in the string.
If `x` is not found, return `-1`.

---

# 📥 Input Format

* A string `s`.
* A character `x` to search for.

# 📤 Output Format

* An integer → the **last index** (0-based) of `x` in `s`.
* If not present, output `-1`.

---

# ✅ Example 1

**Input:**

```
s = "programming"
x = 'm'
```

**Output:**

```
7
```

**Explanation:**

* `"programming"` → `'m'` occurs at indices `6` and `7`.
* Last occurrence = index `7`.

---

# ✅ Example 2

**Input:**

```
s = "hello world"
x = 'z'
```

**Output:**

```
-1
```

**Explanation:**

* `'z'` is not present in `"hello world"`.
* Return `-1`.

---

# 🛠️ Approaches

---

## 1️⃣ Brute Force Approach

### Logic

* Start from **left to right**.
* Keep updating `lastIndex` whenever `x` is found.
* At the end, return `lastIndex`.

### Java Code – Brute Force

```java
public class LastIndexBruteForce {

    public static int findLastIndex(String s, char x) {
        int lastIndex = -1;

        // Traverse entire string
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == x) {
                lastIndex = i; // update whenever found
            }
        }

        return lastIndex; // -1 if never found
    }

    public static void main(String[] args) {
        System.out.println(findLastIndex("programming", 'm')); // 7
        System.out.println(findLastIndex("hello world", 'z')); // -1
    }
}
```

### Complexity (Brute Force)

* **Time:** `O(n)` (scan entire string).
* **Space:** `O(1)` (constant storage).

---

## 2️⃣ Optimized Approach – Reverse Traversal

### Logic

* Start from **end of string**.
* The **first match** we find is the last occurrence.
* Return immediately.

### Java Code – Optimized

```java
public class LastIndexOptimized {

    public static int findLastIndex(String s, char x) {
        // Start from the end of string
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == x) {
                return i; // first match from right = last occurrence
            }
        }
        return -1; // not found
    }

    public static void main(String[] args) {
        System.out.println(findLastIndex("programming", 'm')); // 7
        System.out.println(findLastIndex("hello world", 'z')); // -1
    }
}
```

### Complexity (Optimized)

* **Time:** `O(n)` worst case (if not found), but can stop early once found.
* **Space:** `O(1)` (constant storage).

---

# 📊 Comparison Table

| Approach            | Time Complexity              | Space Complexity | Notes                                            |
| ------------------- | ---------------------------- | ---------------- | ------------------------------------------------ |
| Brute Force         | `O(n)`                       | `O(1)`           | Always traverses full string.                    |
| Optimized (Reverse) | `O(n)` worst, but early exit | `O(1)`           | Stops immediately once last occurrence is found. |

---

✅ With this, you now have:

* **Input/Output format**
* **2 explained examples**
* **Brute force + Optimized Java code with inline notes**
* **Time/Space complexities & comparison**

---

👉 Do you also want me to include a **built-in solution using `lastIndexOf()`** (Java API) as a 3rd quick approach for completeness?
