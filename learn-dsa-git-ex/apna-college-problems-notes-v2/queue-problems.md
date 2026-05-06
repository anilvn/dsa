# Queue Problems — Basic to Advanced

---

## 1. First Unique Character in a String

**Concept:** Queue + HashMap  
LeetCode 387 variant — find the first non-repeating character in a stream.

**Approach:**
- `freqMap` tracks character frequency.
- `queue` preserves insertion order.
- After each update, drain the front of the queue while it holds a repeated char.

```java
import java.util.*;

public class FirstUniqueChar {
    public static char firstUniqueChar(String s) {
        Map<Character, Integer> freqMap = new HashMap<>();
        Queue<Character> queue = new LinkedList<>();

        for (char c : s.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);

            if (freqMap.get(c) == 1) queue.offer(c);

            // Drain front while it's no longer unique
            while (!queue.isEmpty() && freqMap.get(queue.peek()) > 1) {
                queue.poll();
            }
        }

        return queue.isEmpty() ? '_' : queue.peek();
    }

    public static void main(String[] args) {
        System.out.println(firstUniqueChar("swiss"));    // w
        System.out.println(firstUniqueChar("aabbcc"));   // _
        System.out.println(firstUniqueChar("leetcode")); // l
    }
}
```

**Complexity:** O(n) time, O(1) space (at most 26 chars in queue/map).

---

## 2. Sliding Window Maximum

**Concept:** Monotonic Deque  
LeetCode 239 — max of every window of size `k`.

**Approach:**
- Deque stores **indices**, front = max of current window.
- On each step:
  1. Remove front if it's out of window (`index <= i - k`).
  2. Remove from back while `nums[back] < nums[i]` — they can never be the max.
  3. Push current index to back.
  4. Once `i >= k-1`, `nums[deque.front]` is the window max.

```java
import java.util.*;

public class SlidingWindowMaximum {
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        int ri = 0;
        Deque<Integer> deque = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            // Remove out-of-window index
            if (!deque.isEmpty() && deque.peekFirst() <= i - k)
                deque.pollFirst();

            // Maintain decreasing order — remove smaller elements from back
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i])
                deque.pollLast();

            deque.offerLast(i);

            if (i >= k - 1)
                result[ri++] = nums[deque.peekFirst()];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] res = maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3);
        System.out.println(Arrays.toString(res)); // [3, 3, 5, 5, 6, 7]
    }
}
```

**Dry Run** (`nums = [1,3,-1,-3,5,3,6,7]`, `k=3`):

| i | nums[i] | Deque (indices) | Deque (values) | Window Max |
|---|---------|-----------------|----------------|------------|
| 0 | 1       | [0]             | [1]            | —          |
| 1 | 3       | [1]             | [3]            | —          |
| 2 | -1      | [1,2]           | [3,-1]         | 3          |
| 3 | -3      | [1,2,3]         | [3,-1,-3]      | 3          |
| 4 | 5       | [4]             | [5]            | 5          |
| 5 | 3       | [4,5]           | [5,3]          | 5          |
| 6 | 6       | [6]             | [6]            | 6          |
| 7 | 7       | [7]             | [7]            | 7          |

**Complexity:** O(n) time — each index is pushed/popped at most once. O(k) space.

---

## 3. Gas Station

**Concept:** Greedy  
LeetCode 134 — find the starting station to complete a circular route, or return `-1`.

**Key Observations:**
- If `totalGas < totalCost` → impossible, return `-1`.
- If a solution exists, it's **unique**.
- Greedy: whenever `tank` goes negative, the current segment is unreachable from any station before it → reset `start` to `i+1`.

```java
public class GasStation {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalGas = 0, totalCost = 0;
        int tank = 0, start = 0;

        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
            tank += gas[i] - cost[i];

            if (tank < 0) {
                start = i + 1; // can't reach i+1 from anywhere before i+1
                tank = 0;
            }
        }

        return totalGas < totalCost ? -1 : start;
    }

    public static void main(String[] args) {
        GasStation sol = new GasStation();
        System.out.println(sol.canCompleteCircuit(
            new int[]{1,2,3,4,5},
            new int[]{3,4,5,1,2}
        )); // 3
    }
}
```

**Dry Run** (`gas=[1,2,3,4,5]`, `cost=[3,4,5,1,2]`):

```
totalGas=15, totalCost=15 → solution exists

i=0: tank = -2 → reset start=1, tank=0
i=1: tank = -2 → reset start=2, tank=0
i=2: tank = -2 → reset start=3, tank=0
i=3: tank =  3
i=4: tank =  6

Answer: start = 3
```

**Why the greedy reset works:** If you can't reach station `i+1` starting from any station `s` (where `s <= i`), then `s` through `i` are all invalid starts. The only remaining candidate is `i+1`.

**Complexity:** O(n) time, O(1) space.
