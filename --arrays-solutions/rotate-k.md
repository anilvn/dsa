

# 🔹 Problem

Given an array `arr[]` of size `n`, rotate it **left by k positions**.

Example:
`arr = [1,2,3,4,5,6,7], k = 2`
Output → `[3,4,5,6,7,1,2]`

---

# ✅ Approach 1: Brute Force (Rotate one by one)

## 🔹 Idea

Rotate the array left **one step at a time**, `k` times.

## 🔹 Code

```java
class Solution {
    public void leftRotate(int[] arr, int k) {
        int n = arr.length;

        for(int i = 0; i < k; i++) {
            int first = arr[0];

            for(int j = 0; j < n - 1; j++) {
                arr[j] = arr[j + 1];
            }

            arr[n - 1] = first;
        }
    }
}
```

## 🔹 Complexity

* Time: `O(n * k)`
* Space: `O(1)`

## 🔹 Notes

Very slow when `k` is large.

---

# ✅ Approach 2: Using Extra Array

## 🔹 Idea

Store first `k` elements, shift rest, then append stored elements.

## 🔹 Code

```java
class Solution {
    public void leftRotate(int[] arr, int k) {
        int n = arr.length;
        k = k % n;

        int[] temp = new int[k];

        for(int i = 0; i < k; i++) {
            temp[i] = arr[i];
        }

        for(int i = k; i < n; i++) {
            arr[i - k] = arr[i];
        }

        for(int i = 0; i < k; i++) {
            arr[n - k + i] = temp[i];
        }
    }
}
```

## 🔹 Complexity

* Time: `O(n)`
* Space: `O(k)`

---

# ✅ Approach 3: Reversal Algorithm (Optimal ⭐)

## 🔹 Idea

Reverse in 3 steps:

1. Reverse first `k` elements
2. Reverse remaining `n-k` elements
3. Reverse entire array

## 🔹 Code

```java
class Solution {
    public void leftRotate(int[] arr, int k) {
        int n = arr.length;
        k = k % n;

        reverse(arr, 0, k - 1);
        reverse(arr, k, n - 1);
        reverse(arr, 0, n - 1);
    }

    private void reverse(int[] arr, int start, int end) {
        while(start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
}
```

## 🔹 Complexity

* Time: `O(n)`
* Space: `O(1)` ✅ Best

---

# ✅ Approach 4: Juggling Algorithm (Advanced)

## 🔹 Idea

Use **GCD cycles** to rotate elements in groups.

## 🔹 Code

```java
class Solution {
    public void leftRotate(int[] arr, int k) {
        int n = arr.length;
        k = k % n;

        int gcd = gcd(n, k);

        for(int i = 0; i < gcd; i++) {
            int temp = arr[i];
            int j = i;

            while(true) {
                int d = (j + k) % n;
                if(d == i) break;

                arr[j] = arr[d];
                j = d;
            }

            arr[j] = temp;
        }
    }

    private int gcd(int a, int b) {
        if(b == 0) return a;
        return gcd(b, a % b);
    }
}
```

## 🔹 Complexity

* Time: `O(n)`
* Space: `O(1)`

## 🔹 Notes

Harder to understand but efficient.

---

# 🔥 Which One Should You Use?

| Approach    | Time  | Space | When to Use             |
| ----------- | ----- | ----- | ----------------------- |
| Brute Force | O(nk) | O(1)  | Never (only learning)   |
| Extra Array | O(n)  | O(k)  | Easy to write           |
| Reversal ⭐  | O(n)  | O(1)  | **Best for interviews** |
| Juggling    | O(n)  | O(1)  | Advanced optimization   |

---

# 🧠 Key Insight

Left rotation =
👉 Move first `k` elements to the end
👉 Keep order intact

---

If you want, I can give:

* Dry run table (step-by-step)
* Right rotation comparison
* Common mistakes interviewers expect

Just tell me 👍
