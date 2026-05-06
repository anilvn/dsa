Got it 👍
Let’s solve **"Add two fractions"** in **Java** using both **brute force** and **optimized approaches**, with explanations and complexities.

---

## Problem:

Given two fractions `a/b` and `c/d`, add them and return the result in **simplest form**.

Example:

```
Input: 1/2 + 3/4
Output: 5/4
```

```
Input: 1/3 + 1/6
Output: 1/2
```

---

## 🔹 Brute Force Approach

👉 Find a **common denominator** by multiplying denominators, then add numerators.
👉 Finally, simplify by dividing numerator and denominator by their **GCD**.

```java
public class AddFractionsBruteForce {
    // Function to find GCD
    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    // Brute force addition
    public static String addFractions(int a, int b, int c, int d) {
        // Step 1: Common denominator (cross multiplication)
        int numerator = a * d + b * c;
        int denominator = b * d;

        // Step 2: Reduce fraction
        int g = gcd(numerator, denominator);
        numerator /= g;
        denominator /= g;

        return numerator + "/" + denominator;
    }

    public static void main(String[] args) {
        System.out.println(addFractions(1, 2, 3, 4)); // 5/4
        System.out.println(addFractions(1, 3, 1, 6)); // 1/2
    }
}
```

### ✅ Complexity:

* **Time:** O(log(min(numerator, denominator))) (due to GCD)
* **Space:** O(1)

---

## 🔹 Optimized Approach

👉 Instead of multiplying denominators (which may cause overflow), find **LCM** of denominators using **GCD**.
👉 Then convert fractions to same denominator and add.

```java
public class AddFractionsOptimized {
    // GCD function
    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    // LCM function
    private static int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    // Optimized addition
    public static String addFractions(int a, int b, int c, int d) {
        // Step 1: Find LCM of denominators
        int lcmDen = lcm(b, d);

        // Step 2: Adjust numerators
        int numerator = a * (lcmDen / b) + c * (lcmDen / d);

        // Step 3: Simplify result
        int g = gcd(numerator, lcmDen);
        numerator /= g;
        lcmDen /= g;

        return numerator + "/" + lcmDen;
    }

    public static void main(String[] args) {
        System.out.println(addFractions(1, 2, 3, 4)); // 5/4
        System.out.println(addFractions(1, 3, 1, 6)); // 1/2
    }
}
```

### ✅ Complexity:

* **Time:** O(log(min(b, d))) (for GCD + LCM + simplification)
* **Space:** O(1)
* **Advantage:** Avoids very large denominators (better for big numbers).

---

⚡ In summary:

* **Brute Force:** Cross multiplication (easy but may overflow for large numbers).
* **Optimized:** Use **LCM** of denominators (better for handling larger inputs).

---

Do you also want me to **show a dry run** of both approaches with an example like `1/3 + 1/6`?
