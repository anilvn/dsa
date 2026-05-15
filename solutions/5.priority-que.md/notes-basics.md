## PriorityQueue Notes

```java
// PriorityQueue by default = MIN HEAP
// Smallest element stays at TOP

PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// TOP element
minHeap.peek();
```

```java
// MIN HEAP
// Smaller element gets HIGHER priority

PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> a - b);

// a - b
// Smaller value comes first
// Smallest element stays at TOP
```

```java
// MAX HEAP
// Bigger element gets HIGHER priority

PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

// b - a
// Bigger value comes first
// Largest element stays at TOP
```





---


## PriorityQueue<int[]>

```java
// Min Heap
// Stores: [value, index]
//
// a[0] -> first array value
// b[0] -> second array value
//
// Smaller value gets HIGHER priority
// Smallest element stays at TOP
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
```

```java
// Max Heap
// Bigger value gets HIGHER priority
// Largest element stays at TOP
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
```
```java
// Stores arrays
// Format: [value, index]

pq.offer(new int[]{arr[i], i});

// Example stored element
[10, 4]
// 10 -> value
// 4  -> index
```

---

## Accessing Array Values

```java
int[] top = pq.peek();

// index 0 -> value
int value = top[0];

// index 1 -> index
int index = top[1];
```

---

## Mnemonic

```java
[ value , index ]
    0       1
```

```java
top[0] -> actual number
top[1] -> position/index
```
---
<br/><br/><br/><br/>

---
## SLIDING WINDOW MAXIMUM

### Problem Statement

* Given an array and a window size `k`, find the maximum element in every sliding window of size `k`.

---

### Example

#### Input

```text
arr = [1,3,-1,-3,5,3,6,7]
k = 3
```

#### Output

```text
[3,3,5,5,6,7]
```

---

### Idea / Intuition

* Use a Max Heap to always keep the largest element at the top
* Store both value and index inside heap
* Remove elements which go outside the current window
* Once window size becomes `k`, store the maximum

---

### Brute Force Approach (Optional)

### Java Code

```java
class Solution {

    int[] maxSlidingWindow(int[] arr, int k) {

        int n = arr.length;

        int[] res = new int[n - k + 1];

        int idx = 0;

        for (int i = 0; i <= n - k; i++) {

            int max = arr[i];

            for (int j = i; j < i + k; j++) {
                max = Math.max(max, arr[j]);
            }

            res[idx++] = max;
        }

        return res;
    }
}

// Time Complexity - O(n * k)
// Space Complexity - O(1)
```

---

### Optimized Approach

### steps for optimized approach

1. Create Max Heap storing `[value, index]`
2. Traverse the array
3. Insert current element into heap
4. Remove indexes outside current window
5. When first window is formed, store heap top as answer
6. Continue for all windows

### Java Code

```java
import java.util.PriorityQueue;

class Solution {

    int[] maxSlidingWindow(int[] arr, int k) {

        // MAX HEAP
        // Stores: [value, index]
        //
        // b[0] - a[0]
        // Bigger value gets higher priority
        // Largest value stays at TOP
        PriorityQueue<int[]> pq =
                new PriorityQueue<>((a, b) -> b[0] - a[0]);

        int[] res = new int[arr.length - k + 1];

        int idx = 0;

        // Traverse array
        for (int i = 0; i < arr.length; i++) {

            // Store: [value, index]
            pq.offer(new int[]{arr[i], i});

            // Remove OUTSIDE window indexes
            while (!pq.isEmpty() && pq.peek()[1] <= i - k) {
                pq.poll();
            }

            // First window formed
            if (i >= k - 1) {

                // TOP contains maximum value
                res[idx++] = pq.peek()[0];
            }
        }

        return res;
    }
}

// Time Complexity - O(n log n)
// Space Complexity - O(n)
```

