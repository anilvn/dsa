This is a fundamental array manipulation problem—very common in interviews because it tests indexing, modular arithmetic, and in-place optimization.

---

# Problem Statement

Given an array, rotate it by **K positions**:

* **Left rotation** → shift elements to the left
* **Right rotation** → shift elements to the right
* Rotation should be **in-place** when possible

---

# Example

### Left Rotate by K = 2

```
Input:  [1,2,3,4,5]
Output: [3,4,5,1,2]
```

### Right Rotate by K = 2

```
Input:  [1,2,3,4,5]
Output: [4,5,1,2,3]
```

---

# Important Insight

Always do:

```
k = k % n
```

Because rotating `n` times results in the same array.

---

# Approach 1: Brute Force (Extra Array)

### Idea

* Copy elements into a new array in rotated positions

---

### Left Rotate Formula

```
new[i] = nums[(i + k) % n]
```

---

### Right Rotate Formula

```
new[(i + k) % n] = nums[i]
```

---

### Java Code

```java
class Solution {
    public void leftRotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;

        int[] temp = new int[n];

        for (int i = 0; i < n; i++) {
            temp[i] = nums[(i + k) % n];
        }

        System.arraycopy(temp, 0, nums, 0, n);
    }
}
```

---

## Complexity

* Time: O(n)
* Space: O(n)

---

# Approach 2: Using Reversal Algorithm (Optimal, In-Place)

This is the **most important approach for interviews**.

---

## Left Rotation

### Steps

1. Reverse first `k` elements
2. Reverse remaining `n-k` elements
3. Reverse entire array

---

### Example

```
[1,2,3,4,5], k=2

Step 1: reverse(0,1) → [2,1,3,4,5]
Step 2: reverse(2,4) → [2,1,5,4,3]
Step 3: reverse(0,4) → [3,4,5,1,2]
```

---

### Java Code

```java
class Solution {
    public void leftRotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;

        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
        reverse(nums, 0, n - 1);
    }

    private void reverse(int[] nums, int l, int r) {
        while (l < r) {
            int temp = nums[l];
            nums[l] = nums[r];
            nums[r] = temp;
            l++;
            r--;
        }
    }
}
```

---

## Right Rotation

### Steps

1. Reverse whole array
2. Reverse first `k` elements
3. Reverse remaining elements

---

### Example

```
[1,2,3,4,5], k=2

Step 1: reverse all → [5,4,3,2,1]
Step 2: reverse(0,1) → [4,5,3,2,1]
Step 3: reverse(2,4) → [4,5,1,2,3]
```

---

### Java Code

```java
class Solution {
    public void rightRotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;

        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }

    private void reverse(int[] nums, int l, int r) {
        while (l < r) {
            int temp = nums[l];
            nums[l] = nums[r];
            nums[r] = temp;
            l++;
            r--;
        }
    }
}
```

---

# Approach 3: One-by-One Rotation

### Idea

Rotate array one step at a time, K times

---

### Java Code

```java
class Solution {
    public void rightRotate(int[] nums, int k) {
        int n = nums.length;

        for (int i = 0; i < k; i++) {
            int last = nums[n - 1];

            for (int j = n - 1; j > 0; j--) {
                nums[j] = nums[j - 1];
            }

            nums[0] = last;
        }
    }
}
```

---

## Complexity

* Time: O(n * k) → worst
* Space: O(1)

---

# Comparison Table

| Approach            | Time   | Space | Best Use            |
| ------------------- | ------ | ----- | ------------------- |
| Brute (extra array) | O(n)   | O(n)  | Easy to write       |
| Reversal (Optimal)  | O(n)   | O(1)  | Best for interviews |
| One-by-one          | O(n*k) | O(1)  | Avoid               |

---

# Edge Cases

* k = 0 → no change
* k > n → use modulo
* n = 1 → no change
* empty array → handle separately

---

# Interview Insight

The interviewer is testing:

* Whether you reduce **k using modulo**
* Whether you know the **reversal trick**
* Ability to do **in-place transformations**

---

If you want next:

* I can give **cyclic replacement approach (harder but important)**
* Or combine this with problems like:

  * Move Zeroes
  * Next Permutation
  * Rotate Matrix (2D version)
