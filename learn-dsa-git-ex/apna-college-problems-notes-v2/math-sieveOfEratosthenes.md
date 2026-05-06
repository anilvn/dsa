Perfect! Here's a **Java version** that combines both problems in a single file:

```java
import java.util.Scanner;

public class PrimeNumbers {

    // Function to check if a number is prime using i^2 <= n
    public static boolean isPrime(int n) {
        if (n <= 1) return false; // 0 and 1 are not prime
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // Brute force: print primes in range
    public static void printPrimesBruteForce(int l, int r) {
        System.out.print("Primes (Brute Force) between " + l + " and " + r + ": ");
        for (int i = l; i <= r; i++) {
            if (isPrime(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    // Sieve of Eratosthenes: print primes in range
    public static void sieveOfEratosthenes(int l, int r) {
        boolean[] prime = new boolean[r + 1];
        for (int i = 0; i <= r; i++) prime[i] = true;
        prime[0] = prime[1] = false;

        for (int i = 2; i * i <= r; i++) {
            if (prime[i]) {
                for (int j = i * i; j <= r; j += i) {
                    prime[j] = false;
                }
            }
        }

        System.out.print("Primes (Sieve) between " + l + " and " + r + ": ");
        for (int i = l; i <= r; i++) {
            if (prime[i]) System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Check if a number is prime
        System.out.print("Enter a number to check prime: ");
        int n = sc.nextInt();
        if (isPrime(n)) {
            System.out.println(n + " is Prime");
        } else {
            System.out.println(n + " is Not Prime");
        }

        // Print primes in range
        System.out.print("Enter range (l r): ");
        int l = sc.nextInt();
        int r = sc.nextInt();

        printPrimesBruteForce(l, r);
        sieveOfEratosthenes(l, r);

        sc.close();
    }
}
```

---

✅ **Features in this Java program:**

1. Checks if a single number is prime using the $i^2 \le n$ logic.
2. Prints all primes in a range using **brute force**.
3. Prints all primes in a range using **Sieve of Eratosthenes**.

---

If you want, I can **optimize the sieve** to handle ranges like `[1000, 5000]` without building a huge array. That’s useful for large ranges. Do you want me to do that?
