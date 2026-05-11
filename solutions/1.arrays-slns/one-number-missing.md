This is a must-know problem because it tests both **math insight** and **bit manipulation (XOR)**.

---

# Problem Statement

Given an array of size `n` containing numbers from `0` to `n`, with **one number missing**, find the missing number.

---

# Example

```text
Input:  nums = [3,0,1]
Output: 2
```

```text
Input:  nums = [0,1]
Output: 2
```

---

# Approach 1: Sum Formula (Math)

## Idea

Sum of first `n` natural numbers:

```text
Expected Sum = n * (n + 1) / 2
```

Then subtract actual sum → missing number.

---

## Algorithm

1. Compute expected sum
2. Compute actual sum of array
3. Missing = expected - actual

---

## Java Code

```java
class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;

        int expected = n * (n + 1) / 2;
        int actual = 0;

        for (int num : nums) {
            actual += num;
        }

        return expected - actual;
    }
}
```

---

## Dry Run

```text
nums = [3,0,1], n = 3

Expected = 3 * 4 / 2 = 6
Actual   = 3 + 0 + 1 = 4

Missing = 6 - 4 = 2
```

---

## Complexity

| Metric | Value |
| ------ | ----- |
| Time   | O(n)  |
| Space  | O(1)  |

---

## Limitation

* Risk of **integer overflow** when `n` is large

---

# Approach 2: XOR (Best for Interviews)

## Key Properties of XOR

```text
a ^ a = 0
a ^ 0 = a
XOR is commutative & associative
```

---

## Idea

XOR all numbers from `0 → n` and XOR all array elements.
Duplicate numbers cancel out → remaining is missing number.

---

## Algorithm

1. Initialize `xor = 0`
2. XOR all numbers `0 → n`
3. XOR all elements in array
4. Result = missing number

---

## Java Code

```java
class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;

        int xor = 0;

        // XOR 0 to n
        for (int i = 0; i <= n; i++) {
            xor ^= i;
        }

        // XOR array elements
        for (int num : nums) {
            xor ^= num;
        }

        return xor;
    }
}
```

---

## Optimized XOR (Single Loop)

```java
class Solution {
    public int missingNumber(int[] nums) {
        int xor = nums.length;

        for (int i = 0; i < nums.length; i++) {
            xor ^= i ^ nums[i];
        }

        return xor;
    }
}
```

---

## Dry Run

```text
nums = [3,0,1]

XOR(0..3) = 0 ^ 1 ^ 2 ^ 3
XOR(nums) = 3 ^ 0 ^ 1

Cancel common:
(0^0), (1^1), (3^3)

Remaining = 2
```

---

# Complexity

| Metric | Value |
| ------ | ----- |
| Time   | O(n)  |
| Space  | O(1)  |

---

# Comparison

| Approach    | Pros              | Cons                    |
| ----------- | ----------------- | ----------------------- |
| Sum Formula | Simple            | Overflow risk           |
| XOR         | Safe, no overflow | Slightly less intuitive |

---

# Edge Cases

* Missing `0` → works
* Missing `n` → works
* Single element → works

---

# Interview Insight

If interviewer pushes:

* Start with **sum formula**
* Then say:

  > “To avoid overflow, we can use XOR”

That shows depth.

---

# Pattern Connection

This belongs to:

* Bit manipulation problems
* Missing / repeating number problems
* XOR tricks (very important for interviews)

---

If you want next:

* Find **missing + repeating number**
* Find **single number (others appear twice)**
* XOR-based advanced tricks (very high interview value)
