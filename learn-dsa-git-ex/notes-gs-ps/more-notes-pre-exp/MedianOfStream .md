Nice — median of a data stream is a classic. Here’s a clean, efficient Java solution using two heaps (max-heap for the lower half, min-heap for the upper half). This gives O(log n) insertion and O(1) median retrieval.

```java
import java.util.PriorityQueue;
import java.util.Collections;

public class MedianOfStream {
    // Max-heap for the lower half
    private PriorityQueue<Integer> lowers = new PriorityQueue<>(Collections.reverseOrder());
    // Min-heap for the upper half
    private PriorityQueue<Integer> highers = new PriorityQueue<>();

    // Add number to the data stream
    public void addNum(int num) {
        // Always add to lowers first (or highers depending on value)
        if (lowers.isEmpty() || num <= lowers.peek()) {
            lowers.add(num);
        } else {
            highers.add(num);
        }
        rebalance();
    }

    // Rebalance so sizes differ by at most 1
    private void rebalance() {
        if (lowers.size() - highers.size() > 1) {
            highers.add(lowers.poll());
        } else if (highers.size() - lowers.size() > 1) {
            lowers.add(highers.poll());
        }
    }

    // Return median of current stream
    public double findMedian() {
        if (lowers.size() == highers.size()) {
            if (lowers.isEmpty()) return 0; // no elements
            // average of two middle values
            return (lowers.peek() + highers.peek()) / 2.0;
        } else if (lowers.size() > highers.size()) {
            return lowers.peek();
        } else {
            return highers.peek();
        }
    }

    // Example usage
    public static void main(String[] args) {
        MedianOfStream ms = new MedianOfStream();
        int[] stream = {5, 15, 1, 3, 8, 7, 9};

        for (int x : stream) {
            ms.addNum(x);
            System.out.printf("Added %d → median = %.1f%n", x, ms.findMedian());
        }
    }
}
```

Notes:

* Time: `addNum` is O(log n) due to heap ops; `findMedian` is O(1).
* Space: O(n) total for the two heaps.
* Handles even/odd counts and negative numbers fine.
* If you want to support `long` values or avoid overflow when averaging large ints, compute the average using `((long)low + (long)high) / 2.0`. Want that change?

---

Perfect 👍 — let’s dry run the **Median of Stream** code with

`stream = {5, 15, 1, 3, 8, 7, 9}`

We’ll keep track of:

* Step number
* New element added
* Contents of **lowers (max-heap)** and **highers (min-heap)**
* Median after rebalancing

---

### Dry Run Table

| Step | Added | Lowers (max-heap) | Highers (min-heap) | Median          |
| ---- | ----- | ----------------- | ------------------ | --------------- |
| 1    | 5     | \[5]              | \[]                | 5               |
| 2    | 15    | \[5]              | \[15]              | (5+15)/2 = 10.0 |
| 3    | 1     | \[5, 1]           | \[15]              | 5               |
| 4    | 3     | \[3, 1]           | \[5, 15]           | (3+5)/2 = 4.0   |
| 5    | 8     | \[5, 1, 3]        | \[8, 15]           | 5               |
| 6    | 7     | \[5, 1, 3]        | \[7, 15, 8]        | (5+7)/2 = 6.0   |
| 7    | 9     | \[7, 5, 3, 1]     | \[8, 15, 9]        | 7               |

---

### Explanation Highlights

* **Step 1:** Add `5`. Only one element → median = 5.
* **Step 2:** Add `15`. Heaps balanced (`[5]`, `[15]`) → median = 10.
* **Step 3:** Add `1`. Goes into lowers → `[5,1]`, `[15]` → median = 5.
* **Step 4:** Add `3`. Rebalance → lowers `[3,1]`, highers `[5,15]` → median = 4.
* **Step 5:** Add `8`. Goes to highers, then rebalance → median = 5.
* **Step 6:** Add `7`. Highers bigger, balance restored → median = 6.
* **Step 7:** Add `9`. Rebalance again → lowers `[7,5,3,1]`, highers `[8,15,9]` → median = 7.

---
