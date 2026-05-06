

## 🔑 Key Idea (Divisibility Rule for 3)

A number is divisible by 3 **if the sum of its digits is divisible by 3**.
Example:

* $123 → 1+2+3 = 6$ → divisible by 3 ✅
* $457 → 4+5+7 = 16$ → not divisible by 3 ❌

This avoids doing big-number division directly (useful when numbers are **too large** to fit in `int`/`long`).

---

## ✅ Optimized Java Code

```java
public class DivisibleByThree {

    // Optimized approach using digit sum
    public static boolean isDivisibleBy3(String numStr) {
        int sum = 0;

        // Step 1: Compute digit sum
        for (int i = 0; i < numStr.length(); i++) {
            sum += numStr.charAt(i) - '0'; // convert char digit to int
        }

        // Step 2: Check divisibility
        return sum % 3 == 0;
    }

    public static void main(String[] args) {
        String[] testNumbers = {"123", "457", "9999999999999999999999", "81", "102"};

        for (String num : testNumbers) {
            System.out.println("Number: " + num + " → Divisible by 3? " + isDivisibleBy3(num));
        }
    }
}
```
<br/><br/>


```java
public class DivisibleByThreeLong {

    // Optimized approach for long input
    public static boolean isDivisibleBy3(long num) {
        long sum = 0;
        long temp = num;

        // Step 1: Compute sum of digits
        while (temp > 0) {
            sum += temp % 10;  // extract last digit
            temp /= 10;        // remove last digit
        }

        // Step 2: Check divisibility
        return sum % 3 == 0;
    }

    public static void main(String[] args) {
        long[] testNumbers = {123L, 457L, 9999999999999L, 81L, 102L};

        for (long num : testNumbers) {
            System.out.println("Number: " + num + " → Divisible by 3? " + isDivisibleBy3(num));
        }
    }
}
```

---

## 📌 Notes

* Input is taken as a **string** so that we can handle **very large numbers** (like 100+ digits) without overflow.
* Convert each character to integer using `ch - '0'`.
* Add up digits → check if sum % 3 == 0.

---

## ⏱ Complexity

* **Time Complexity**: O(d) → where d = number of digits (we scan each digit once).
* **Space Complexity**: O(1) → only uses a sum variable.

---
