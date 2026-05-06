Perfect 👍 Now let’s handle the **dot product of two arrays of unequal length**.
If the arrays have different sizes, we only compute up to the **smaller length**.

---

# 📝 Problem Statement

Given two arrays `a[]` and `b[]` (possibly unequal lengths), compute the **dot product up to the smaller length**:

$$
\text{DotProduct} = \sum_{i=0}^{\min(n, m)-1} (a[i] \times b[i])
$$

---

# 📥 Input Format

* Two integer arrays `a[]` and `b[]`.

# 📤 Output Format

* A single integer → result of the dot product.

---

# ✅ Example 1

**Input:**

```
a = [1, 2, 3, 4]
b = [5, 6]
```

**Output:**

```
17
```

**Explanation:**

* Take up to length 2 → `1*5 + 2*6 = 5 + 12 = 17`.

---

# ✅ Example 2

**Input:**

```
a = [7, 8]
b = [2, 3, 4, 5]
```

**Output:**

```
38
```

**Explanation:**

* Take up to length 2 → `7*2 + 8*3 = 14 + 24 = 38`.

---

# 🛠️ Approaches

---

## 1️⃣ Brute Force Approach

### Logic

* Find the **minimum length** between the two arrays.
* Traverse till that length, multiply elements, and add.

### Java Code – Brute Force

```java
public class DotProductUnequalBruteForce {

    public static int dotProduct(int[] a, int[] b) {
        int result = 0;
        int minLength = Math.min(a.length, b.length);

        // Traverse only till smaller array length
        for (int i = 0; i < minLength; i++) {
            result += a[i] * b[i];
        }

        return result;
    }

    public static void main(String[] args) {
        int[] a1 = {1, 2, 3, 4};
        int[] b1 = {5, 6};
        System.out.println(dotProduct(a1, b1)); // 17

        int[] a2 = {7, 8};
        int[] b2 = {2, 3, 4, 5};
        System.out.println(dotProduct(a2, b2)); // 38
    }
}
```

### Complexity (Brute Force)

* **Time:** `O(min(n, m))`
* **Space:** `O(1)`

---

## 2️⃣ Optimized Approach – Skip Zeros

### Logic

* Same as brute force, but skip multiplications where either element is `0`.
* Useful for **sparse arrays**.

### Java Code – Optimized

```java
public class DotProductUnequalOptimized {

    public static int dotProduct(int[] a, int[] b) {
        int result = 0;
        int minLength = Math.min(a.length, b.length);

        for (int i = 0; i < minLength; i++) {
            if (a[i] != 0 && b[i] != 0) { // skip zero multiplications
                result += a[i] * b[i];
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] a1 = {1, 2, 3, 4};
        int[] b1 = {5, 6};
        System.out.println(dotProduct(a1, b1)); // 17

        int[] a2 = {7, 8};
        int[] b2 = {2, 3, 4, 5};
        System.out.println(dotProduct(a2, b2)); // 38
    }
}
```

### Complexity (Optimized)

* **Time:** `O(k)` where `k` = number of non-zero multiplications (`k ≤ min(n, m)`)
* **Space:** `O(1)`

---

# 📊 Comparison Table

| Approach               | Time Complexity    | Space Complexity | Notes                                |
| ---------------------- | ------------------ | ---------------- | ------------------------------------ |
| Brute Force            | `O(min(n, m))`     | `O(1)`           | Always processes till smaller array. |
| Optimized (Skip Zeros) | `O(k)` (`k ≤ min`) | `O(1)`           | Faster if arrays are sparse.         |

---




<br/><br/><br/><br/><br/><br/>

---

## 📝 Problem Statement

Given two **2D integer arrays (matrices)** `A` and `B`:

* If dimensions are **equal** → compute element-wise dot product (sum of `A[i][j] * B[i][j]`).
* If dimensions are **unequal** → only compute up to the **common overlapping region** (minimum of rows and columns).

---

## 📥 Input Format

* Two 2D arrays (matrices) `A` and `B`.

## 📤 Output Format

* A single integer → result of the dot product.

---

## ✅ Example 1 – Equal Dimensions

**Input:**

```
A = [[1, 2],
     [3, 4]]

B = [[5, 6],
     [7, 8]]
```

**Output:**

```
70
```

**Explanation:**
`1*5 + 2*6 + 3*7 + 4*8 = 5 + 12 + 21 + 32 = 70`

---

## ✅ Example 2 – Unequal Dimensions

**Input:**

```
A = [[1, 2, 3],
     [4, 5, 6]]

B = [[7, 8],
     [9, 10],
     [11, 12]]
```

**Output:**

```
80
```

**Explanation:**

* Common region = `2 rows × 2 cols`
* Compute:
  `1*7 + 2*8 + 4*9 + 5*10 = 7 + 16 + 36 + 50 = 109`

Oops correction ✅ → That sum = `109`.

---

# 🛠 Approaches

---

## 1️⃣ Brute Force (Traverse Overlap)

### Java Code

```java
public class DotProduct2DBruteForce {

    public static int dotProduct(int[][] A, int[][] B) {
        int rows = Math.min(A.length, B.length);             // min rows
        int cols = Math.min(A[0].length, B[0].length);       // min cols
        int result = 0;

        // Traverse only overlapping region
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result += A[i][j] * B[i][j];
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] A1 = {{1, 2}, {3, 4}};
        int[][] B1 = {{5, 6}, {7, 8}};
        System.out.println(dotProduct(A1, B1)); // 70

        int[][] A2 = {{1, 2, 3}, {4, 5, 6}};
        int[][] B2 = {{7, 8}, {9, 10}, {11, 12}};
        System.out.println(dotProduct(A2, B2)); // 109
    }
}
```

### Complexity

* **Time:** `O(r * c)` where `r = min(rowsA, rowsB)`, `c = min(colsA, colsB)`
* **Space:** `O(1)`

---

## 2️⃣ Optimized Approach – Skip Zeros

### Idea

* If many entries are zero, skip multiplications.
* Useful for sparse matrices.

### Java Code

```java
public class DotProduct2DOptimized {

    public static int dotProduct(int[][] A, int[][] B) {
        int rows = Math.min(A.length, B.length);
        int cols = Math.min(A[0].length, B[0].length);
        int result = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (A[i][j] != 0 && B[i][j] != 0) {
                    result += A[i][j] * B[i][j];
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] A1 = {{1, 0}, {0, 4}};
        int[][] B1 = {{5, 0}, {7, 8}};
        System.out.println(dotProduct(A1, B1)); // 37
    }
}
```

### Complexity

* **Time:** `O(k)` where `k` = number of non-zero overlapping elements.
* **Space:** `O(1)`

---

# 📊 Comparison Table

| Case                   | Time Complexity                | Space Complexity | Notes                   |
| ---------------------- | ------------------------------ | ---------------- | ----------------------- |
| 1D Arrays Equal        | `O(n)`                         | `O(1)`           | Full scan               |
| 1D Arrays Unequal      | `O(min(n, m))`                 | `O(1)`           | Scan common length      |
| 2D Arrays Equal        | `O(r * c)`                     | `O(1)`           | Full matrix dot product |
| 2D Arrays Unequal      | `O(min(rA, rB) * min(cA, cB))` | `O(1)`           | Scan overlapping region |
| Optimized (Skip Zeros) | `O(k)` (`k ≤ overlap size`)    | `O(1)`           | Useful for sparse       |

---

