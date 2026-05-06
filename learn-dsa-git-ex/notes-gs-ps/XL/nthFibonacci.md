

```java
public class NthFibonacci {

    // Recursive approach (inefficient for large n)
    public static long fibonacciRecursive(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    // Iterative approach (efficient)
    public static long fibonacciIterative(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;

        long first = 0;
        long second = 1;
        long nth = 0;

        for (int i = 2; i <= n; i++) {
            nth = first + second;
            first = second;
            second = nth;
        }

        return nth;
    }

    // Test cases
    public static void main(String[] args) {
        int n = 10;

        System.out.println("Recursive Fibonacci(" + n + ") = " + fibonacciRecursive(n));
        System.out.println("Iterative Fibonacci(" + n + ") = " + fibonacciIterative(n));
    }
}
```

---

## 📘 Complexity Notes

### 🔹 Recursive Approach

* **Time Complexity**:

  * Each call makes 2 more calls.
  * Exponential growth:

    $$
    O(2^n)
    $$
  * Very slow for large `n` (e.g., `n=50` takes millions of calls).
* **Space Complexity**:

  * Due to recursion stack depth = `n`.
  * $$
    O(n)
    $$

---

### 🔹 Iterative Approach

* **Time Complexity**:

  * Loop runs once per number up to `n`.
  * $$
    O(n)
    $$
* **Space Complexity**:

  * Uses only a few variables (`first`, `second`, `nth`).
  * $$
    O(1)
    $$

---

✅ **Summary:**

* **Recursive:** good for learning, but inefficient (`O(2^n)`).
* **Iterative:** efficient, runs in linear time (`O(n)`) and constant space.

---

