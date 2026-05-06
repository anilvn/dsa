# Queue -- Implementing from Scratch in Java

## How to Read This Document

Each section covers one implementation approach. Read in order. Code is clean with brief inline comments. Time complexity follows each operation.

---

## Step 1 -- Queue Using Array (Simple)

**Problem:** Implement a basic FIFO queue backed by a static array.

**Weakness:** `remove()` requires shifting all elements left -- O(n).

```java
public class QueueArray {
    static class Queue {
        static int[] arr;
        static int size;
        static int rear;

        Queue(int size) {
            this.size = size;
            arr  = new int[size];
            rear = -1;
        }

        public static boolean isEmpty() { return rear == -1; }

        public static boolean isFull()  { return rear == size - 1; }

        public static void add(int data) {
            if (isFull()) { System.out.println("Overflow"); return; }
            arr[++rear] = data;
        }

        // O(n): shifts all elements left after removal
        public static int remove() {
            if (isEmpty()) { System.out.println("Empty queue"); return -1; }
            int front = arr[0];
            for (int i = 1; i <= rear; i++) arr[i - 1] = arr[i];
            rear--;
            return front;
        }

        public static int peek() {
            if (isEmpty()) { System.out.println("Empty queue"); return -1; }
            return arr[0];
        }
    }
}
```

| Operation | Time Complexity | Notes                                  |
|-----------|-----------------|----------------------------------------|
| add       | O(1)            | Append at rear                         |
| remove    | O(n)            | Shift all elements left                |
| peek      | O(1)            | Read front element                     |

---

## Step 2 -- Circular Queue Using Array

**Problem:** Eliminate the O(n) remove by treating the array as a ring using modulo arithmetic.

```java
public class CircularQueue {
    static class Queue {
        static int[] arr;
        static int size;
        static int front = -1;
        static int rear  = -1;

        Queue(int size) {
            this.size = size;
            arr = new int[size];
        }

        public static boolean isEmpty() { return rear == -1 && front == -1; }

        // full when next position of rear wraps around to front
        public static boolean isFull() { return (rear + 1) % size == front; }

        public static void add(int data) {
            if (isFull()) { System.out.println("Overflow"); return; }
            if (front == -1) front = 0;         // first element
            rear = (rear + 1) % size;           // wrap around
            arr[rear] = data;
        }

        public static int remove() {
            if (isEmpty()) { System.out.println("Empty queue"); return -1; }
            int res = arr[front];
            if (front == rear) {
                front = rear = -1;              // last element removed
            } else {
                front = (front + 1) % size;    // wrap around
            }
            return res;
        }

        public static int peek() {
            if (isEmpty()) { System.out.println("Empty queue"); return -1; }
            return arr[front];
        }
    }
}
```

| Operation | Time Complexity | Notes                                    |
|-----------|-----------------|------------------------------------------|
| add       | O(1)            | Rear advances with modulo                |
| remove    | O(1)            | Front advances with modulo               |
| peek      | O(1)            | Direct array access at front             |

**Key insight:** `(index + 1) % size` wraps the pointer around to 0 once it exceeds the array bounds. This reuses freed slots without shifting.

---

## Step 3 -- Queue Using Linked List

**Problem:** Implement a queue with no fixed capacity using a linked list. Head = front, tail = rear.

```java
public class QueueLinkedList {
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; next = null; }
    }

    static class Queue {
        static Node head = null; // front of queue
        static Node tail = null; // rear of queue

        public static boolean isEmpty() { return head == null && tail == null; }

        public static void add(int data) {
            Node newNode = new Node(data);
            if (isEmpty()) {
                tail = head = newNode;
            } else {
                tail.next = newNode; // append at rear
                tail = newNode;
            }
        }

        public static int remove() {
            if (isEmpty()) { System.out.println("Empty queue"); return -1; }
            int front = head.data;
            if (head == tail) tail = null; // removing last element
            head = head.next;
            return front;
        }

        public static int peek() {
            if (isEmpty()) { System.out.println("Empty queue"); return -1; }
            return head.data;
        }
    }
}
```

| Operation | Time Complexity | Notes                                   |
|-----------|-----------------|-----------------------------------------|
| add       | O(1)            | Append via tail pointer                 |
| remove    | O(1)            | Remove head                             |
| peek      | O(1)            | Read head data                          |

**Advantage:** Dynamically grows and shrinks. No overflow unless memory is exhausted.

---

## Step 4 -- Queue Using Java Collection Framework

**Problem:** Use production-ready Java APIs.

```java
import java.util.*;

public class QueueJCF {
    public static void main(String[] args) {
        Queue<Integer> q = new ArrayDeque<>(); // preferred over LinkedList for queues

        q.add(1); q.add(2); q.add(3); q.add(4); q.add(5);

        while (!q.isEmpty()) {
            System.out.println(q.peek());
            q.remove();
        }
    }
}
```

**Why `ArrayDeque` over `LinkedList`?** `ArrayDeque` uses a resizable array with head and tail pointers -- no node allocation overhead, better cache locality, and no synchronized overhead.

---

## Step 5 -- Queue Using Two Stacks

**Problem:** Simulate a queue using only stack operations. Classic interview problem.

**Approach:** On every `add`, move all elements from `s1` to `s2`, push new element to `s1`, move back. This keeps `s1` in queue order (front at top).

```java
import java.util.*;

public class QueueTwoStacks {
    static class Queue {
        static Stack<Integer> s1 = new Stack<>(); // maintains queue order
        static Stack<Integer> s2 = new Stack<>(); // temp buffer

        public static boolean isEmpty() { return s1.isEmpty(); }

        public static void add(int data) {
            // move s1 -> s2, push new element, move s2 -> s1
            while (!s1.isEmpty()) s2.push(s1.pop());
            s1.push(data);
            while (!s2.isEmpty()) s1.push(s2.pop());
        }

        public static int remove() { return s1.pop(); }  // front is at top of s1

        public static int peek()   { return s1.peek(); }
    }

    public static void main(String[] args) {
        Queue q = new Queue();
        q.add(1); q.add(2); q.add(3);

        while (!q.isEmpty()) {
            System.out.println(q.peek());
            q.remove();
        }
    }
}
```

| Operation | Time Complexity | Notes                                       |
|-----------|-----------------|---------------------------------------------|
| add       | O(n)            | Moves all elements through temp stack twice |
| remove    | O(1)            | Front is always at top of s1                |
| peek      | O(1)            | Read top of s1                              |

---

## Summary -- Comparison Table

| Implementation    | Space | add  | remove | Dynamic Size | Notes                          |
|-------------------|-------|------|--------|--------------|--------------------------------|
| Array (Simple)    | O(n)  | O(1) | O(n)   | No           | Shifting bottleneck on remove  |
| Circular Array    | O(n)  | O(1) | O(1)   | No           | Efficient fixed-size queue     |
| Linked List       | O(n)  | O(1) | O(1)   | Yes          | Best general-purpose choice    |
| Java Collections  | O(n)  | O(1) | O(1)   | Yes          | Use ArrayDeque in production   |
| Two Stacks        | O(n)  | O(n) | O(1)   | Yes          | Interview question -- not practical |

---

## Interview Cheat Sheet

- Queue is FIFO: first in, first out.
- Circular array eliminates O(n) remove by using modulo to advance front and rear.
- Full condition for circular queue: `(rear + 1) % size == front` (one slot left empty as sentinel).
- Linked list queue: `head` = front (dequeue), `tail` = rear (enqueue). Both O(1).
- Two stacks queue: add is O(n), used exclusively to demonstrate stack-to-queue transformation in interviews.
- In production: always use `ArrayDeque`. It is faster than `LinkedList` due to fewer allocations.
