Now we step up from the simple XOR trick.
When elements appear **twice**, XOR works directly.
But when they appear **thrice (or k times)**, we need a different idea.

---

# Problem Statement

Given an array:

* Every element appears **3 times**
* Except **one element appears once**

Find that single element.

---

# Example

```text
Input:  [2,2,3,2]
Output: 3
```

```text
Input:  [0,1,0,1,0,1,99]
Output: 99
```

---

# Why XOR Fails Here

XOR works only for pairs:

```text
a ^ a = 0
```

But:

```text
a ^ a ^ a = a   ❌ (not zero)
```

So duplicates won’t cancel out cleanly.

---

# Optimal Approach: Bit Counting

## Key Insight

Think **bit by bit** (32 bits for integers):

* Count how many times each bit is set
* Since duplicates appear 3 times → their contribution is multiple of 3
* The remaining (mod 3) gives the single number’s bits

---

# Algorithm

1. Initialize `result = 0`
2. For each bit position `0 → 31`

   * Count how many numbers have that bit set
   * If `(count % 3 != 0)` → set that bit in result
3. Return result

---

# Java Code (Clean & Interview Ready)

```java
class Solution {
    public int singleNumber(int[] nums) {
        int result = 0;

        for (int bit = 0; bit < 32; bit++) {
            int count = 0;

            for (int num : nums) {
                if ((num & (1 << bit)) != 0) {
                    count++;
                }
            }

            if (count % 3 != 0) {
                result |= (1 << bit);
            }
        }

        return result;
    }
}
```

---

# Dry Run

```text
nums = [2,2,3,2]

Binary:
2 = 10
3 = 11

Bit 0:
2 → 0
2 → 0
3 → 1
2 → 0
count = 1 → 1 % 3 = 1 → set bit

Bit 1:
2 → 1
2 → 1
3 → 1
2 → 1
count = 4 → 4 % 3 = 1 → set bit

Result = 11 → 3
```

---

# Complexity

| Metric | Value            |
| ------ | ---------------- |
| Time   | O(32 * n) ≈ O(n) |
| Space  | O(1)             |

---

# Advanced Optimal (Bitmask Trick)

This is the **most optimized and interviewer-favorite** version.

## Idea

Use two variables:

* `ones` → stores bits appearing once
* `twos` → stores bits appearing twice

---

## Java Code

```java
class Solution {
    public int singleNumber(int[] nums) {
        int ones = 0, twos = 0;

        for (int num : nums) {
            ones = (ones ^ num) & ~twos;
            twos = (twos ^ num) & ~ones;
        }

        return ones;
    }
}
```

---

## Intuition

Think of it as a **state machine for each bit**:

| Count | ones | twos      |
| ----- | ---- | --------- |
| 0     | 0    | 0         |
| 1     | 1    | 0         |
| 2     | 0    | 1         |
| 3     | 0    | 0 (reset) |

So elements appearing 3 times automatically get removed.

---

# Comparison

| Approach            | Pros               | Cons             |
| ------------------- | ------------------ | ---------------- |
| Bit Counting        | Easy to understand | Slightly verbose |
| Bitmask (ones/twos) | Very efficient     | Harder to derive |

---

# Generalization (Important Insight)

If elements appear **k times**:

* Count bits
* Use `% k`

---

# Edge Cases

* Negative numbers → handled correctly (bit operations work fine)
* Large input → still linear
* Single element → works

---

# Interview Insight

Best way to present:

1. Start with **bit counting (safe explanation)**
2. Then say:

   > “We can optimize using bitmask (ones/twos) to avoid 32 loops”

That shows depth.

---

# Pattern Connection

This belongs to:

* Bit manipulation
* Frequency without extra space
* Finite state machine using bits

---

If you want next:

* “Two single numbers (others appear twice)” → very important XOR split trick
* “Find missing + repeating number” → combination of math + XOR
* “Majority element (n/2, n/3)” → Boyer-Moore Voting Algorithm
