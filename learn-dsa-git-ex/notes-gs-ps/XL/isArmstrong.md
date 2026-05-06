

### Code: Brute Force and Optimized Approaches

```java
public class ArmstrongNumber {

    // Brute Force Approach
    public static boolean isArmstrongBruteForce(int num) {
        int original = num;
        int digits = 0;

        // Step 1: Count digits
        int temp = num;
        while (temp > 0) {
            digits++;
            temp /= 10;
        }

        // Step 2: Compute sum of powers using loops
        int sum = 0;
        temp = num;
        while (temp > 0) {
            int digit = temp % 10;

            // compute digit^digits manually (brute force way)
            int power = 1;
            for (int i = 0; i < digits; i++) {
                power *= digit;
            }

            sum += power;
            temp /= 10;
        }

        return sum == original;
    }

    // Optimized Approach
    public static boolean isArmstrongOptimized(int num) {
        int original = num;
        int digits = (int) Math.log10(num) + 1; // direct way to count digits

        int sum = 0, temp = num;
        while (temp > 0) {
            int digit = temp % 10;
            sum += Math.pow(digit, digits); // use Math.pow instead of manual loop
            temp /= 10;
        }

        return sum == original;
    }

    // Test cases
    public static void main(String[] args) {
        int[] testNumbers = {153, 9474, 123, 9475};

        for (int num : testNumbers) {
            System.out.println("Number: " + num);
            System.out.println("Brute Force: " + isArmstrongBruteForce(num));
            System.out.println("Optimized: " + isArmstrongOptimized(num));
            System.out.println("------");
        }
    }
}
```

---

### Explanation with Example

Take **153**:

* Digits = 3
* $1^3 + 5^3 + 3^3 = 1 + 125 + 27 = 153$ → Armstrong ✅

Take **123**:

* Digits = 3
* $1^3 + 2^3 + 3^3 = 1 + 8 + 27 = 36$ ≠ 123 → Not Armstrong ❌

---

### Time & Space Complexity

#### **Brute Force Approach**

* **Time Complexity**:

  * Counting digits = O(log₁₀n)
  * For each digit, we compute power with a loop up to `digits`. Worst case: O(d \* d), where d = number of digits.
  * So, **O(d²)**.
* **Space Complexity**: O(1) (only variables).

#### **Optimized Approach**

* **Time Complexity**:

  * Counting digits using log = O(1)
  * For each digit, we compute power using `Math.pow` (constant-time in Java).
  * So, **O(d)**.
* **Space Complexity**: O(1).

---
<br/><br/><br/>

```java
int digits = (int) Math.log10(num) + 1; // count digits
sum += Math.pow(digit, digits);         // raise digit to power
```

Let’s break down what happens behind the scenes:

---

## 1. `Math.log10(num)`

* **What it does:** Returns the base-10 logarithm of `num`.

* Formula:

  $$
  \log_{10}(num) = \text{the power to which 10 must be raised to get num}
  $$

* Example:
  $\log_{10}(1000) = 3$ because $10^3 = 1000$.
  $\log_{10}(999) \approx 2.999$.

* Why `+1`?
  Because the **number of digits in a number** `num` is:

  $$
  \text{digits} = \lfloor \log_{10}(num) \rfloor + 1
  $$

* Example:

  * `num = 153`
    $\log_{10}(153) \approx 2.18$ → floor = 2 → +1 = **3 digits**
  * `num = 9474`
    $\log_{10}(9474) \approx 3.97$ → floor = 3 → +1 = **4 digits**

✅ This avoids looping through digits just to count them.

---

## 2. `Math.pow(digit, digits)`

* **What it does:** Raises a number to a power.

  $$
  \text{Math.pow}(a, b) = a^b
  $$

* Example:

  * `Math.pow(5, 3)` = $5^3 = 125$
  * `Math.pow(9, 4)` = $9^4 = 6561$

* **How Java implements it:**
  Internally, Java calls a **native C function** that uses fast exponentiation algorithms (like exponentiation by squaring + floating point arithmetic).
  It is **much faster** than manually looping to multiply the digit `digits` times.

---

## Why This Works for Armstrong Numbers

Armstrong number definition:

$$
\text{num} = \sum (\text{digit}^d) \quad \text{where d = number of digits}
$$

So:

* First line finds **d** (digits count).
* Second line computes **digit^d** efficiently.
* Then we add them up.

---

✅ Example Walkthrough with `num = 9474`

1. `digits = (int) Math.log10(9474) + 1`
   → $ \log_{10}(9474) ≈ 3.97 → 3 + 1 = 4$.

2. Loop through digits:

   * digit = 4 → `Math.pow(4, 4) = 256`
   * digit = 7 → `Math.pow(7, 4) = 2401`
   * digit = 4 → `Math.pow(4, 4) = 256`
   * digit = 9 → `Math.pow(9, 4) = 6561`

3. Sum = 256 + 2401 + 256 + 6561 = 9474 → ✅ Armstrong.

---

⚡ So, in short:

* `Math.log10(num)+1` → smart way to count digits (O(1)).
* `Math.pow(digit, digits)` → efficient power calculation (O(1), internally optimized).

---

👉 Do you want me to also show you how these two operations could be done **without Math.log10 and Math.pow** (using only loops), so you can compare?
