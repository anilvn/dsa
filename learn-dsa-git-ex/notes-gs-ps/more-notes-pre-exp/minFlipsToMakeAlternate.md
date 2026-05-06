Got it 👍 Let’s do the same structured breakdown for **Problem-1** (“Minimum Flips to Make a Binary String Alternate”).

---

# 📌 Problem Statement (clear explanation)

You are given a **binary string** `s` (only `'0'` and `'1'`).
You want to make it an **alternating string** — i.e.:

* Either pattern `"010101..."`
* Or pattern `"101010..."`

👉 Task: Find the **minimum number of bit flips** required to convert `s` into one of these alternating strings.

---

# 📌 Examples

### Example 1

Input:

```
s = "001"
```

Output:

```
1
```

Explanation:

* Target `"010"` → requires 1 flip (`0 → 1` at position 2).
* Target `"101"` → requires 2 flips.
  ✅ Minimum = **1**

---

### Example 2

Input:

```
s = "0001010111"
```

Output:

```
2
```

Explanation:

* Target `"0101010101"` → 2 flips (2nd and 9th bit).
* Target `"1010101010"` → 6 flips.
  ✅ Minimum = **2**

---

### Example 3

Input:

```
s = "1111"
```

Output:

```
2
```

Explanation:

* Target `"0101"` → requires 2 flips.
* Target `"1010"` → requires 2 flips.
  ✅ Minimum = **2**

---

# 📌 Approaches

---

## 🔹 Brute Force Approach

**Idea:**

1. Construct two possible target strings of same length:

   * `alt1 = "0101..."`
   * `alt2 = "1010..."`
2. Compare input string with both.
3. Count mismatches for each.
4. Answer = `min(mismatches_alt1, mismatches_alt2)`

**Time Complexity:** `O(n)`
**Space Complexity:** `O(n)` (can be optimized to `O(1)` without building strings).

---

## 🔹 Optimized Approach

**Observation:**
You don’t need to build alternate strings. Just check the expected character at each index:

* If index `i` is even → expect `'0'` for `alt1`, `'1'` for `alt2`.
* If index `i` is odd → expect `'1'` for `alt1`, `'0'` for `alt2`.

Count mismatches directly.

**Time Complexity:** `O(n)`
**Space Complexity:** `O(1)`

---

# 📌 Java Implementations

---

### Brute Force Java

```java
public class MinFlipsToMakeAlternate {

    public static int minFlipsBruteForce(String s) {
        int n = s.length();
        StringBuilder alt1 = new StringBuilder();
        StringBuilder alt2 = new StringBuilder();

        for (int i = 0; i < n; i++) {
            alt1.append(i % 2 == 0 ? '0' : '1');
            alt2.append(i % 2 == 0 ? '1' : '0');
        }

        int flips1 = 0, flips2 = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != alt1.charAt(i)) flips1++;
            if (s.charAt(i) != alt2.charAt(i)) flips2++;
        }

        return Math.min(flips1, flips2);
    }

    public static void main(String[] args) {
        System.out.println(minFlipsBruteForce("001"));        // 1
        System.out.println(minFlipsBruteForce("0001010111")); // 2
        System.out.println(minFlipsBruteForce("1111"));       // 2
    }
}
```

---

### Optimized Java

```java
public class MinFlipsToMakeAlternate {

    public static int minFlipsOptimized(String s) {
        int flips1 = 0, flips2 = 0;
        for (int i = 0; i < s.length(); i++) {
            char expected1 = (i % 2 == 0) ? '0' : '1'; // pattern "0101.."
            char expected2 = (i % 2 == 0) ? '1' : '0'; // pattern "1010.."

            if (s.charAt(i) != expected1) flips1++;
            if (s.charAt(i) != expected2) flips2++;
        }
        return Math.min(flips1, flips2);
    }

    public static void main(String[] args) {
        System.out.println(minFlipsOptimized("001"));        // 1
        System.out.println(minFlipsOptimized("0001010111")); // 2
        System.out.println(minFlipsOptimized("1111"));       // 2
    }
}
```

---

# 📌 Dry Run (Example: `"001"`)

| Index | Char | Expected(alt1:010) | Match? | Expected(alt2:101) | Match? |
| ----- | ---- | ------------------ | ------ | ------------------ | ------ |
| 0     | 0    | 0                  | ✅      | 1                  | ❌      |
| 1     | 0    | 1                  | ❌      | 0                  | ✅      |
| 2     | 1    | 0                  | ❌      | 1                  | ✅      |

* Flips for `alt1` = 2 mismatches → 2 flips
* Flips for `alt2` = 1 mismatch → 1 flip
  ✅ Answer = **1**

---
