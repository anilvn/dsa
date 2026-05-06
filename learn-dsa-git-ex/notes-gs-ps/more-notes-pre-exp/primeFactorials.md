

### ✅ Approach 1: Naïve Method (Loop 2…n, check divisor & primality)

```java
import java.util.*;

public class PrimeFactorsNaive {
    // Check if a number is prime
    static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    static List<Integer> primeFactors(int n) {
        List<Integer> factors = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (n % i == 0 && isPrime(i)) {
                factors.add(i);
            }
        }
        return factors;
    }

    public static void main(String[] args) {
        int n = 780;
        System.out.println("Prime factors (Naive): " + primeFactors(n));
    }
}
```

**Complexity**

* Time: `O(n * sqrt(n))` (loop through 2…n, and for each divisor check primality).
* Space: `O(k)` where `k` = number of prime factors (output list only).

---

### ✅ Approach 2: Using Divisor Pairing (loop till √n, check both i and n/i)

```java
import java.util.*;

public class PrimeFactorsDivisors {
    static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    static List<Integer> primeFactors(int n) {
        Set<Integer> factors = new HashSet<>(); // avoid duplicates
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                if (isPrime(i)) factors.add(i);
                int other = n / i;
                if (isPrime(other)) factors.add(other);
            }
        }
        return new ArrayList<>(factors);
    }

    public static void main(String[] args) {
        int n = 780;
        System.out.println("Prime factors (Divisor Pairing): " + primeFactors(n));
    }
}
```

**Complexity**

* Time: `O(√n * √n)` in worst case (outer loop √n, primality check √n) ≈ `O(n)` but practically much less since primality check happens only for divisors.
* Space: `O(k)` for storing prime factors.

---

### ✅ Approach 3: Optimized “School Method” (Repeated Division)

```java
import java.util.*;

public class PrimeFactorsOptimized {
    static List<Integer> primeFactors(int n) {
        List<Integer> factors = new ArrayList<>();

        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                factors.add(i); // i is a prime factor
                while (n % i == 0) {
                    n /= i; // divide as many times as possible
                }
            }
        }

        // If n > 1, then it's a prime number left
        if (n > 1) factors.add(n);

        return factors;
    }

    public static void main(String[] args) {
        int n = 780;
        System.out.println("Prime factors (Optimized): " + primeFactors(n));
    }
}
```

**Complexity**

* Time: `O(√n)` in worst case (when n is prime).
* Average case is even faster because division reduces n significantly.
* Space: `O(k)` (list of prime factors).

---

👉 So in summary:

1. **Naïve** → easy, but very slow `O(n * √n)`.
2. **Divisor Pairing** → better, uses divisor property `≈ O(n)` in practice but faster than naïve.
3. **Optimized School Method** → best, efficient prime factorization in `O(√n)`.

---

Do you also want me to **add a fourth variation** (the final tweak from the video: loop only till √n and check leftover `n > 1`) separately, or should I merge that into Approach 3?

---

### 🔹 Dry Run Table for `n = 780` (Optimized School Method)

| Iteration (i) | Current n | Condition `i * i <= n` | Check `n % i == 0` | Action Taken                  | Factors List |
| ------------- | --------- | ---------------------- | ------------------ | ----------------------------- | ------------ |
| i = 2         | 780       | 2² = 4 ≤ 780 ✅         | 780 % 2 == 0 ✅     | Add `2`; divide → 780→390→195 | \[2]         |
| i = 3         | 195       | 3² = 9 ≤ 195 ✅         | 195 % 3 == 0 ✅     | Add `3`; divide → 195→65      | \[2, 3]      |
| i = 4         | 65        | 4² = 16 ≤ 65 ✅         | 65 % 4 != 0 ❌      | Skip                          | \[2, 3]      |
| i = 5         | 65        | 5² = 25 ≤ 65 ✅         | 65 % 5 == 0 ✅      | Add `5`; divide → 65→13       | \[2, 3, 5]   |
| i = 6         | 13        | 6² = 36 ≤ 13 ❌         | Loop terminates    | —                             | \[2, 3, 5]   |

---

### After Loop Ends

* `n = 13`
* Check `if (n > 1)` → ✅ true → Add `13`

---

### ✅ Final Result

`Prime factors of 780 = [2, 3, 5, 13]`

---

### 🔹 Dry Run Table for `n = 37` (Prime Number)

| Iteration (i) | Current n | Condition `i * i <= n` | Check `n % i == 0` | Action Taken | Factors List |
| ------------- | --------- | ---------------------- | ------------------ | ------------ | ------------ |
| i = 2         | 37        | 2² = 4 ≤ 37 ✅          | 37 % 2 != 0 ❌      | Skip         | \[]          |
| i = 3         | 37        | 3² = 9 ≤ 37 ✅          | 37 % 3 != 0 ❌      | Skip         | \[]          |
| i = 4         | 37        | 4² = 16 ≤ 37 ✅         | 37 % 4 != 0 ❌      | Skip         | \[]          |
| i = 5         | 37        | 5² = 25 ≤ 37 ✅         | 37 % 5 != 0 ❌      | Skip         | \[]          |
| i = 6         | 37        | 6² = 36 ≤ 37 ✅         | 37 % 6 != 0 ❌      | Skip         | \[]          |
| i = 7         | 37        | 7² = 49 ≤ 37 ❌         | Loop terminates    | —            | \[]          |

---

### After Loop Ends

* Current `n = 37` (still unchanged).
* Check: `if (n > 1)` → ✅ true → Add `37`.

---

### ✅ Final Result

`Prime factors of 37 = [37]`

---

👉 Without this **final if-check**, the algorithm would wrongly return **\[] (empty list)** for prime numbers, because no divisor was found inside the loop.

---