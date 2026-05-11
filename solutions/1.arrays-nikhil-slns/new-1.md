## Maximum Product After K Increments

---

### Problem Statement

Given an array `nums` and an integer `k`.

In one operation:

* choose any element
* increment it by `1`

Return the maximum possible product after at most `k` operations.

Return answer modulo:

```text id="3p2m8r"
10^9 + 7
```

---

### Example

* Input:

```text id="5m8qpt"
nums = [0,4]
k = 5
```

* Output:

```text id="1x7jql"
20
```

* Explanation:

```text id="8d3kpm"
Increment 0 five times

nums = [5,4]

Product = 5 * 4 = 20
```

---

### Example

* Input:

```text id="6w2mnr"
nums = [6,3,3,2]
k = 2
```

* Output:

```text id="9v1gpk"
216
```

---

### Idea/Intuition

### Key Greedy Observation

To maximize product:

```text id="1r8qmx"
Always increment the smallest number
```

Why?

```text id="4z2kpl"
Increasing smaller values improves
product more efficiently
```

---

### Efficient Approach

Use Min Heap:

* smallest element always available quickly

---

### Steps

1. Insert all numbers into Min Heap
2. Repeat `k` times:

   * remove smallest number
   * increment it
   * insert back
3. Multiply all elements
4. Return modulo result

---

### Java Code

```java id="maxproductk_opt"
import java.util.*;

public class MaximumProductAfterKIncrements {

    public static int maximumProduct(int[] nums, int k) {

        PriorityQueue<Integer> pq =
                new PriorityQueue<>();

        // Min Heap
        for (int num : nums) {
            pq.offer(num);
        }

        // Increment smallest element each time
        while (k-- > 0) {

            int smallest = pq.poll();

            pq.offer(smallest + 1);
        }

        long mod = 1_000_000_007L;

        long product = 1;

        // Calculate product
        while (!pq.isEmpty()) {

            product =
                    (product * pq.poll()) % mod;
        }

        return (int) product;
    }
}
```

---

### Dry Run

```text id="9j2qwl"
nums = [6,3,3,2]
k = 2
```

### Initial Min Heap

```text id="8f7mpk"
[2,3,3,6]
```

---

### Operation 1

```text id="7l3qpx"
Remove 2
Increment -> 3

Heap:
[3,3,3,6]
```

---

### Operation 2

```text id="6v9rkl"
Remove 3
Increment -> 4

Heap:
[3,3,4,6]
```

---

### Product

```text id="5t1qmr"
3 * 3 * 4 * 6 = 216
```

---

### Time Complexity

```text id="0n7mpq"
O(k log n + n log n)
```

---

### Space Complexity

```text id="3g8rpl"
O(n)
```
<br><br><br>

---
## Kth Missing Positive Number

---

### Problem Statement

Given a sorted positive integer array `arr` and an integer `k`, return the `kth` missing positive number.

---

### Example

* Input:

```text id="4p8mql"
arr = [2,3,4,7,11]
k = 5
```

* Output:

```text id="8n2rpk"
9
```

* Explanation:

```text id="1m7qxl"
Missing numbers:
[1,5,6,8,9,10...]

5th missing number = 9
```

---

### Example

* Input:

```text id="5v1kpm"
arr = [1,2,3,4]
k = 2
```

* Output:

```text id="9x4rpl"
6
```

---

### Idea/Intuition

### Key Observation

For index `i`:

```text id="3k9qwr"
Missing numbers till arr[i]

= arr[i] - (i + 1)
```

Why?

```text id="6r2mpl"
Expected number at index i is (i + 1)

Difference gives missing count
```

---

### Binary Search Logic

If:

```text id="2q7mpl"
missing < k
```

move right

Else:

* move left

---

### Steps

1. Apply binary search
2. Calculate missing count at mid
3. Find first index where missing >= k
4. Final answer:

```text id="0m8qpl"
left + k
```

---

### Java Code

```java id="kthmissing_opt"
public class KthMissingPositiveNumber {

    public static int findKthPositive(int[] arr,
                                      int k) {

        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {

            int mid =
                    left + (right - left) / 2;

            // Missing count till mid
            int missing =
                    arr[mid] - (mid + 1);

            if (missing < k) {

                left = mid + 1;

            } else {

                right = mid - 1;
            }
        }

        // kth missing number
        return left + k;
    }
}
```

---

### Dry Run

```text id="7j1rpk"
arr = [2,3,4,7,11]
k = 5
```

| mid | arr[mid] | missing count |
| --- | -------- | ------------- |
| 2   | 4        | 1             |
| 3   | 7        | 3             |
| 4   | 11       | 6             |

Binary search stops:

```text id="9g4mpl"
left = 4
```

Final Answer:

```text id="6x8qrl"
left + k
= 4 + 5
= 9
```

---

### Time Complexity

```text id="4w9mpl"
O(log n)
```

---

### Space Complexity

```text id="1q7rpk"
O(1)
```
