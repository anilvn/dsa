# Stack -- Implementing from Scratch in Java

## How to Read This Document

Each section introduces one implementation approach or problem. Read in order. Code is clean with brief inline comments. Time complexity follows each operation.

---

## Step 1 -- Stack Using ArrayList

**Problem:** Implement a stack backed by a dynamic array.

```java
import java.util.ArrayList;

public class StackAL {
    static class Stack {
        ArrayList<Integer> list = new ArrayList<>();

        public void push(int data) {
            list.add(data);              // end of list is the top
        }

        public boolean isEmpty() {
            return list.size() == 0;
        }

        public int pop() {
            if (isEmpty()) return -1;    // underflow guard
            return list.remove(list.size() - 1); // remove last element
        }

        public int peek() {
            if (isEmpty()) return -1;
            return list.get(list.size() - 1);    // read last element
        }
    }

    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(1); stack.push(2); stack.push(3); stack.push(4);

        while (!stack.isEmpty()) {
            System.out.println(stack.peek());
            stack.pop();
        }
    }
}
```

| Operation | Time Complexity | Notes                                   |
|-----------|-----------------|-----------------------------------------|
| push      | O(1) amortized  | ArrayList resize is rare due to doubling|
| pop       | O(1) amortized  | Remove from end is O(1)                 |
| peek      | O(1)            | Direct index access                     |
| isEmpty   | O(1)            | size check                              |

---

## Step 2 -- Stack Using Java Collection Framework

**Problem:** Use the built-in `Stack` class from `java.util`.

```java
import java.util.*;

public class StackJCF {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1); stack.push(2); stack.push(3); stack.push(4);

        while (!stack.isEmpty()) {
            System.out.println(stack.peek());
            stack.pop();
        }
    }
}
```

**Note:** `java.util.Stack` extends `Vector` (synchronized). For single-threaded use, prefer `ArrayDeque` as a stack: `Deque<Integer> stack = new ArrayDeque<>()`.

---

## Step 3 -- Stack Using Linked List

**Problem:** Implement a stack with a linked list so there is no fixed size limit.

```java
public class StackDS {
    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    static class Stack {
        public static Node head = null; // head == top of stack

        public static void push(int data) {
            Node newNode = new Node(data);
            if (head == null) {
                head = newNode;
                return;
            }
            newNode.next = head; // prepend -- new node becomes the top
            head = newNode;
        }

        public static boolean isEmpty() {
            return head == null;
        }

        public static int pop() {
            if (isEmpty()) return -1;
            Node top = head;
            head = head.next; // move head to next node
            return top.data;
        }

        public static int peek() {
            if (isEmpty()) return -1;
            return head.data;
        }
    }

    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(1); stack.push(2); stack.push(3); stack.push(4);

        while (!stack.isEmpty()) {
            System.out.println(stack.peek());
            stack.pop();
        }
    }
}
```

| Operation | Time Complexity | Space Complexity | Notes                           |
|-----------|-----------------|------------------|---------------------------------|
| push      | O(1)            | O(1) per node    | Prepend to linked list          |
| pop       | O(1)            | O(1)             | Remove head                     |
| peek      | O(1)            | O(1)             | Read head data                  |
| isEmpty   | O(1)            | O(1)             | null check                      |

**Advantage over array-based:** No resize needed, no wasted capacity. Grows and shrinks on demand.

---

## Stack Problem 1 -- Push at Bottom

**Problem:** Insert an element at the bottom of the stack without using any extra data structure.

**Approach:** Recursively pop all elements, push the new element to the now-empty stack, then push all elements back.

```java
import java.util.*;

public class StackProblem1 {
    public static void pushAtBottom(Stack<Integer> s, int data) {
        if (s.isEmpty()) {
            s.push(data); // base case: stack is empty, push here
            return;
        }
        int temp = s.pop();          // temporarily remove top
        pushAtBottom(s, data);       // recurse until stack is empty
        s.push(temp);                // restore top on the way back
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1); stack.push(2); stack.push(3);
        pushAtBottom(stack, 4);

        while (!stack.isEmpty()) {
            System.out.println(stack.pop()); // prints 3, 2, 1, 4
        }
    }
}
```

| Operation    | Time Complexity | Notes                              |
|--------------|-----------------|------------------------------------|
| pushAtBottom | O(n)            | Pops and restores all n elements   |

---

## Stack Problem 2 -- Reverse a Stack

**Problem:** Reverse the order of elements in a stack using only recursion (no extra data structure).

**Approach:** Pop all elements recursively, then use `pushAtBottom` to reinsert each element at the bottom as the recursion unwinds.

```java
import java.util.*;

public class StackProblem2 {
    public static void pushAtBottom(Stack<Integer> s, int data) {
        if (s.isEmpty()) {
            s.push(data);
            return;
        }
        int temp = s.pop();
        pushAtBottom(s, data);
        s.push(temp);
    }

    public static void reverse(Stack<Integer> s) {
        if (s.isEmpty()) return;

        int top = s.pop();     // remove current top
        reverse(s);            // recurse until stack is empty
        pushAtBottom(s, top);  // reinsert at bottom on way back
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1); stack.push(2); stack.push(3);

        reverse(stack);

        while (!stack.isEmpty()) {
            System.out.println(stack.pop()); // prints 1, 2, 3
        }
    }
}
```

| Operation     | Time Complexity | Notes                                              |
|---------------|-----------------|----------------------------------------------------|
| reverse stack | O(n^2)          | reverse calls pushAtBottom (O(n)) for each element |

**Tradeoff:** Clean recursive solution at the cost of O(n^2). If O(n) is required, copy to an array, reverse, push back.

---

## Summary -- Comparison Table

| Implementation   | push    | pop  | peek | Dynamic Size | Notes                              |
|------------------|---------|------|------|----          |------------------------------------|
| ArrayList        | O(1)*   | O(1) | O(1) | Yes          | Simple; amortized O(1) for push    |
| JCF Stack        | O(1)*   | O(1) | O(1) | Yes          | Synchronized; prefer ArrayDeque    |
| Linked List      | O(1)    | O(1) | O(1) | Yes          | No wasted capacity, no resize      |
| Push at Bottom   | O(n)    | --   | --   | --           | Recursive trick; O(n) per call     |
| Reverse Stack    | --      | --   | --   | --           | O(n^2) via pushAtBottom            |

*Amortized O(1) due to occasional resize.

---

## Interview Cheat Sheet

- Stack is LIFO: last pushed is first popped.
- Linked list implementation: head = top of stack. Push = prepend. Pop = remove head.
- ArrayList implementation: last index = top. Push = `add`. Pop = `remove(size - 1)`.
- Push at bottom and reverse are classic recursion problems -- the trick is using the call stack as temporary storage.
- For interviews: if asked to implement a stack, use the linked list approach to show you understand pointers.
