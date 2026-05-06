Great question 👍 This is a **classic math + programming** style problem.

We want to **find all pairs `(a, b)`** from a given set of numbers such that:

$$
a^b = b^a
$$

---

## 🔹 Step 1: Understanding the condition

* For **trivial case**: if `a = b`, then `a^b = b^a` always.
* For **non-trivial pairs**, one known family of solutions is:

  * `(2, 4)` and `(4, 2)` → `2^4 = 16`, `4^2 = 16`
  * `(9, 3)` and `(3, 9)` → `9^3 = 729`, `3^9 = 19683` ❌ not equal
    (so not always true)
* In general, the relation is rare.

### How to check?

For integers, the safe way is just **compute powers and compare**.

---

## 🔹 Step 2: Brute Force Approach

Try all pairs `(a, b)` in the set.

```java
import java.util.*;

public class PowerPairs {
    public static void findPairs(int[] nums) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int a = nums[i];
                int b = nums[j];

                // compute using Math.pow (be careful with precision, cast to long)
                double p1 = Math.pow(a, b);
                double p2 = Math.pow(b, a);

                if (Math.abs(p1 - p2) < 1e-9) { // floating comparison
                    System.out.println("(" + a + ", " + b + ")");
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {2, 4, 16, 3, 9};
        findPairs(arr);
    }
}
```

### Output

```
(2, 4)
(4, 2)
```

---

## 🔹 Step 3: Optimized Check

Instead of relying on floating-point, we can:

* Use `BigInteger` for exact integer powers (safe).
* OR precompute logarithms since:

  $$
  a^b = b^a \quad \iff \quad b \cdot \ln(a) = a \cdot \ln(b)
  $$

### Using logarithms (safer than `Math.pow`)

```java
public static void findPairsLog(int[] nums) {
    int n = nums.length;

    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            int a = nums[i], b = nums[j];

            if (a <= 0 || b <= 0) continue; // invalid for logs

            double lhs = b * Math.log(a);
            double rhs = a * Math.log(b);

            if (Math.abs(lhs - rhs) < 1e-9) {
                System.out.println("(" + a + ", " + b + ")");
            }
        }
    }
}
```

---

## 🔹 Complexity

* **Brute Force (both versions)**:

  * **Time Complexity** = `O(n^2)` (check each pair).
  * **Space Complexity** = `O(1)`.

---

✅ Summary:

* Use **nested loops** to check pairs.
* Use either **Math.pow** (risk of floating error) or **logs/BigInteger** for precision.
* Known pairs like `(2,4)` and `(4,2)` will show up correctly.

---

👉 Do you want me to also extend this to **find ALL integer pairs `(a, b)` that satisfy a^b = b^a\` in general (not just from a given set)**? That has some nice math insights.
