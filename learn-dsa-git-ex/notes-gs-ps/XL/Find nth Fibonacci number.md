Perfect 👌 Let’s do **Fibonacci Series – Find nth Fibonacci number** with **both brute force recursive** and **optimized iterative** approaches.

---

## 🔹 Problem:

Find the **nth Fibonacci number** where:

* F(0) = 0,
* F(1) = 1,
* F(n) = F(n-1) + F(n-2) for n ≥ 2.

---

## 🔹 Input & Output Format

**Input 1:**

```
n = 5
```

**Output 1:**

```
Fibonacci(5) = 5
```

**Input 2:**

```
n = 10
```

**Output 2:**

```
Fibonacci(10) = 55
```

---

## 🔹 Explanation

* Fibonacci sequence starts as: **0, 1, 1, 2, 3, 5, 8, 13, …**
* For **n=5**, sequence = \[0,1,1,2,3,5] → **F(5) = 5**
* For **n=10**, sequence = \[0,1,1,2,3,5,8,13,21,34,55] → **F(10) = 55**

---

## 🔹 Java Solutions

### 1. Brute Force (Recursive)

```java
public class Fibonacci {

    // Brute force recursive method
    public static int fibRecursive(int n) {
        if (n <= 1) return n; // Base case
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }

    public static void main(String[] args) {
        int n1 = 5, n2 = 10;

        System.out.println("Recursive Result:");
        System.out.println("Fibonacci(" + n1 + ") = " + fibRecursive(n1));
        System.out.println("Fibonacci(" + n2 + ") = " + fibRecursive(n2));
    }
}
```

#### 📊 Complexity:

* **Time:** O(2^n) (because of repeated calls)
* **Space:** O(n) (recursion stack depth)

---

### 2. Optimized (Iterative – Dynamic Programming)

```java
public class Fibonacci {

    // Optimized iterative method
    public static int fibIterative(int n) {
        if (n <= 1) return n; // Handle base cases

        int prev2 = 0, prev1 = 1, curr = 0;

        for (int i = 2; i <= n; i++) {
            curr = prev1 + prev2; // F(i) = F(i-1) + F(i-2)
            prev2 = prev1;        // shift window
            prev1 = curr;
        }
        return curr;
    }

    public static void main(String[] args) {
        int n1 = 5, n2 = 10;

        System.out.println("Iterative Result:");
        System.out.println("Fibonacci(" + n1 + ") = " + fibIterative(n1));
        System.out.println("Fibonacci(" + n2 + ") = " + fibIterative(n2));
    }
}
```

#### 📊 Complexity:

* **Time:** O(n) (single loop)
* **Space:** O(1) (just storing last two values)

---

## ✅ Summary

| Approach    | Method       | Time Complexity | Space Complexity | Notes                       |
| ----------- | ------------ | --------------- | ---------------- | --------------------------- |
| Brute Force | Recursion    | O(2^n)          | O(n)             | Very slow, repeated calls   |
| Optimized   | Iterative DP | O(n)            | O(1)             | Efficient, production-ready |

---

