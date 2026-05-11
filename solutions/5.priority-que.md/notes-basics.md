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

## Sliding Window Max Heap Code

```java
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
```
