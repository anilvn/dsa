## Sliding Window Maximum

Problem:

Given an array `nums[]` and an integer `k`, find the maximum element in every contiguous subarray (window) of size `k`.

Example:

```text
nums = [1,3,-1,-3,5,3,6,7]
k = 3

Windows:
[1 3 -1] -> 3
[3 -1 -3] -> 3
[-1 -3 5] -> 5
[-3 5 3] -> 5
[5 3 6] -> 6
[3 6 7] -> 7

Output:
[3,3,5,5,6,7]
```

---

## 1. Optimal Approach — Deque

Time Complexity: `O(n)`
Space Complexity: `O(k)`

---

### Core Idea

Maintain a deque that stores indices of useful elements.

Rules:

1. Remove indices outside the current window.
2. Remove smaller elements from the back because they can never become maximum.
3. Front of deque always contains the maximum element index.

The deque maintains elements in decreasing order.

---

### Visualization

For:

```text
nums = [1,3,-1,-3,5,3,6,7]
k = 3
```

Deque stores indices.

---

### Step-by-step Dry Run

| i | nums[i] | Remove Outdated | Remove Smaller | Deque (indices) | Window Max |
| - | ------- | --------------- | -------------- | --------------- | ---------- |
| 0 | 1       | -               | -              | [0]             | -          |
| 1 | 3       | -               | remove 0       | [1]             | -          |
| 2 | -1      | -               | -              | [1,2]           | 3          |
| 3 | -3      | -               | -              | [1,2,3]         | 3          |
| 4 | 5       | remove 1        | remove 3,2     | [4]             | 5          |
| 5 | 3       | -               | -              | [4,5]           | 5          |
| 6 | 6       | -               | remove 5,4     | [6]             | 6          |
| 7 | 7       | -               | remove 6       | [7]             | 7          |

---

### Java Code — Deque Approach

```java
import java.util.*;

class Solution {

    public int[] maxSlidingWindow(int[] nums, int k) {

        int n = nums.length;

        if (n == 0 || k == 0)
            return new int[0];

        int[] ans = new int[n - k + 1];

        Deque<Integer> dq = new ArrayDeque<>();

        int idx = 0;

        for (int i = 0; i < n; i++) {

            // Remove elements outside window
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) {
                dq.pollFirst();
            }

            // Remove smaller elements
            while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) {
                dq.pollLast();
            }

            // Add current index
            dq.offerLast(i);

            // Store answer
            if (i >= k - 1) {
                ans[idx++] = nums[dq.peekFirst()];
            }
        }

        return ans;
    }

    public static void main(String[] args) {

        Solution s = new Solution();

        int[] nums = {1,3,-1,-3,5,3,6,7};

        int[] ans = s.maxSlidingWindow(nums, 3);

        System.out.println(Arrays.toString(ans));
    }
}
```

---

## Why Deque Works

Suppose deque contains:

```text
[9, 7, 5]
```

If new element = `10`

Then:

```text
9,7,5 can never become max again
```

because `10` is larger and will stay longer in future windows.

So remove them.

That is why each element enters and leaves deque only once.

Hence:

```text
O(n)
```

---

## 2. Heap (Priority Queue) Approach

Time Complexity: `O(n log n)`
Space Complexity: `O(n)`

---

### Core Idea

Use a max heap to always get the largest element.

Store:

```text
(value, index)
```

Why index?

Because heap may contain elements outside current window.

We remove outdated elements using index.

---

### Algorithm

For each element:

1. Insert `(value, index)` into heap
2. Remove elements outside window
3. Heap top becomes answer

---

### Java Code — Heap Approach

```java
import java.util.*;

class Solution {

    public int[] maxSlidingWindow(int[] nums, int k) {

        int n = nums.length;

        int[] ans = new int[n - k + 1];

        PriorityQueue<int[]> pq =
                new PriorityQueue<>((a, b) -> b[0] - a[0]);

        int idx = 0;

        for (int i = 0; i < n; i++) {

            // Add current element
            pq.offer(new int[]{nums[i], i});

            // Remove outdated elements
            while (pq.peek()[1] <= i - k) {
                pq.poll();
            }

            // Window formed
            if (i >= k - 1) {
                ans[idx++] = pq.peek()[0];
            }
        }

        return ans;
    }

    public static void main(String[] args) {

        Solution s = new Solution();

        int[] nums = {1,3,-1,-3,5,3,6,7};

        int[] ans = s.maxSlidingWindow(nums, 3);

        System.out.println(Arrays.toString(ans));
    }
}
```

---

## Dry Run — Heap

Example:

```text
nums = [1,3,-1,-3,5]
k = 3
```

Heap stores:

```text
(value,index)
```

---

### i = 0

Add `(1,0)`

Heap top = 1

---

### i = 1

Add `(3,1)`

Heap top = 3

---

### i = 2

Add `(-1,2)`

Window formed.

Heap top = 3

Answer = 3

---

### i = 3

Add `(-3,3)`

Remove outdated:

```text
index <= 0
```

Heap top still `(3,1)`

Answer = 3

---

### i = 4

Add `(5,4)`

Heap top = 5

Answer = 5

---

## Deque vs Heap

| Feature             | Deque  | Heap       |
| ------------------- | ------ | ---------- |
| Time Complexity     | O(n)   | O(n log n) |
| Space Complexity    | O(k)   | O(n)       |
| Faster              | Yes    | No         |
| Easy to Understand  | Medium | Easy       |
| Best for Interviews | Yes    | Sometimes  |
| Maintains Order     | Yes    | No         |

---

## Key Insight

Deque removes useless elements immediately.

Heap keeps all elements and lazily removes outdated ones.

That is why:

```text
Deque = Optimal
Heap = Simpler but slower
```
