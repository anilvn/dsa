```java
public class CountFibonacciInRange {

    // Optimized approach
    public static int countFibonacciInRange(long lowerLimit, long upperLimit) {
        if (upperLimit < 0) return 0; // No Fibonacci in negative numbers

        long first = 0, second = 1;
        int count = 0;

        // Generate Fibonacci numbers up to upperLimit
        while (first <= upperLimit) {
            if (first >= lowerLimit) {
                count++;
            }

            long next = first + second;
            first = second;
            second = next;
        }

        return count;
    }

    public static void main(String[] args) {
        long lowerLimit = 10, upperLimit = 200;
        System.out.println("Count in range [" + lowerLimit + ", " + upperLimit + "] = "
                           + countFibonacciInRange(lowerLimit, upperLimit));

        // Additional tests
        System.out.println("Range [1, 10] → " + countFibonacciInRange(1, 10));
        System.out.println("Range [100, 1000] → " + countFibonacciInRange(100, 1000));
        System.out.println("Range [1000, 10000] → " + countFibonacciInRange(1000, 10000));
    }
}
```

---

## 📘 Notes on `countFibonacciInRange`

### 🔹 Time Complexity

* The loop generates Fibonacci numbers **until `upperLimit`**.
* Fibonacci numbers grow **exponentially** (roughly by a factor of φ ≈ 1.618 each step).
* For a `long` in Java, the largest Fibonacci number that fits is `F(92) ≈ 7.5 × 10^18`.
* That means the loop runs at most **92 iterations**, no matter how large `upperLimit` is (within `long`).

👉 So, in **worst case**:

$$
\text{Time Complexity} = O(\log(upperLimit)) \quad \text{(practically ≤ 92 steps for long)}
$$

✅ Effectively **constant time** for `long`.

---

### 🔹 Space Complexity

* Uses only a **few variables**: `first`, `second`, `next`, `count`.
* No extra arrays, lists, or recursion stack.

$$
\text{Space Complexity} = O(1)
$$

---

### ✅ Final Notes

* **Efficient for any range within `long`.**
* Runs in **constant space** and at most \~92 iterations.
* For ranges with very large numbers (like `BigInteger`), we’d need a logarithmic formula approach.

---

