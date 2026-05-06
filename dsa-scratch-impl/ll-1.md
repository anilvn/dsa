# Linked List -- Implementing from Scratch in Java

## How to Read This Document

Each section introduces one concept or operation. Read in order. Code is clean and production-quality with brief inline comments. Time complexity follows each operation.

---

## Step 1 -- Node Structure and Basic Setup

**Problem:** Represent a singly linked list where each node holds data and a pointer to the next node.

```java
class LL {
    Node head;       // entry point into the list
    private int size;

    LL() {
        size = 0;
    }

    public class Node {
        String data;
        Node next;

        Node(String data) {
            this.data = data;
            this.next = null;
            size++;         // increment on node creation
        }
    }
}
```

**What this gives us:** A self-tracking list where `size` is maintained automatically on insertion.

---

## Step 2 -- Add at Beginning and End

**Problem:** Insert nodes at the head (O(1)) and at the tail (O(n)).

```java
// O(1): redirect head pointer
public void addFirst(String data) {
    Node newNode = new Node(data);
    newNode.next = head;
    head = newNode;
}

// O(n): traverse to last node
public void addLast(String data) {
    Node newNode = new Node(data);
    if (head == null) {
        head = newNode;
        return;
    }
    Node lastNode = head;
    while (lastNode.next != null) {
        lastNode = lastNode.next;
    }
    lastNode.next = newNode;
}
```

| Operation | Time Complexity | Notes                                |
|-----------|-----------------|--------------------------------------|
| addFirst  | O(1)            | Pointer redirect only                |
| addLast   | O(n)            | Must traverse to find last node      |

**Tradeoff:** Use a `tail` pointer if addLast must be O(1) (done in Queue implementation).

---

## Step 3 -- Print the List

**Problem:** Traverse and display all nodes.

```java
public void printList() {
    Node currNode = head;
    while (currNode != null) {
        System.out.print(currNode.data + " -> ");
        currNode = currNode.next;
    }
    System.out.println("null");
}
```

| Operation | Time Complexity | Notes              |
|-----------|-----------------|--------------------|
| printList | O(n)            | One full traversal |

---

## Step 4 -- Remove First and Last

**Problem:** Delete from the head (O(1)) and from the tail (O(n)).

```java
// O(1): move head forward
public void removeFirst() {
    if (head == null) {
        System.out.println("Empty List, nothing to delete");
        return;
    }
    head = head.next;
    size--;
}

// O(n): need pointer to second-to-last node
public void removeLast() {
    if (head == null) {
        System.out.println("Empty List, nothing to delete");
        return;
    }
    size--;
    if (head.next == null) { // single element
        head = null;
        return;
    }
    Node currNode = head;
    Node lastNode = head.next;
    while (lastNode.next != null) {
        currNode = currNode.next;
        lastNode = lastNode.next;
    }
    currNode.next = null; // detach last node
}
```

| Operation   | Time Complexity | Notes                                   |
|-------------|-----------------|-----------------------------------------|
| removeFirst | O(1)            | Pointer redirect                        |
| removeLast  | O(n)            | Needs two-pointer scan to find tail-1   |

**Edge cases:** Empty list guard. Single-node case handled separately in `removeLast`.

---

## Step 5 -- Insert at Index

**Problem:** Insert at an arbitrary position in the middle of the list.

```java
public void addInMiddle(int index, String data) {
    if (index > size || index < 0) {
        System.out.println("Invalid Index value");
        return;
    }
    size++;
    Node newNode = new Node(data);

    // inserting at head
    if (head == null || index == 0) {
        newNode.next = head;
        head = newNode;
        return;
    }

    Node currNode = head;
    for (int i = 1; i < index; i++) {
        currNode = currNode.next;
    }
    // wire in the new node
    Node nextNode = currNode.next;
    currNode.next = newNode;
    newNode.next  = nextNode;
}
```

| Operation   | Time Complexity | Notes                                 |
|-------------|-----------------|---------------------------------------|
| addInMiddle | O(n)            | Must traverse to position index - 1   |

**Edge cases:** `index == 0` redirects to head insertion. `index > size` is rejected.

---

## Step 6 -- Size

```java
public int getSize() {
    return size;
}
```

| Operation | Time Complexity | Notes                    |
|-----------|-----------------|--------------------------|
| getSize   | O(1)            | Size tracked on mutations|

---

## Complete Usage Example

```java
public static void main(String[] args) {
    LL list = new LL();
    list.addLast("is");
    list.addLast("a");
    list.addLast("list");
    list.printList(); // is -> a -> list -> null

    list.addFirst("this");
    list.printList(); // this -> is -> a -> list -> null
    System.out.println(list.getSize()); // 4

    list.removeFirst();
    list.printList(); // is -> a -> list -> null

    list.removeLast();
    list.printList(); // is -> a -> null
}
```

---

## Using Java's Built-in LinkedList (Quick Reference)

For production code, Java's `LinkedList` from the Collections Framework is preferred.

```java
import java.util.*;

class LL {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();

        list.add("is");
        list.add("a");
        list.addLast("list");     // adds at end
        list.addFirst("this");    // adds at beginning
        list.add(3, "linked");    // adds at index 3

        System.out.println(list); // [this, is, a, linked, list]

        System.out.println(list.get(0));  // "this"
        System.out.println(list.size());  // 5

        list.remove(3);        // remove at index 3
        list.removeFirst();
        list.removeLast();

        System.out.println(list); // [is, a]
    }
}
```

---

## Reversing a Linked List

### Method 1 -- Iterative (Recommended)

Uses three pointers: `prevNode`, `currNode`, `nextNode`. One pass, O(1) space.

```java
public void reverseList() {
    if (head == null || head.next == null) return;

    Node prevNode = head;
    Node currNode = head.next;

    while (currNode != null) {
        Node nextNode  = currNode.next; // save next
        currNode.next  = prevNode;      // reverse the link
        prevNode       = currNode;      // advance prev
        currNode       = nextNode;      // advance curr
    }

    head.next = null; // original head is now the tail
    head = prevNode;  // new head is the old tail
}
```

| Operation     | Time Complexity | Space Complexity | Notes                    |
|---------------|-----------------|------------------|--------------------------|
| reverseList   | O(n)            | O(1)             | In-place pointer reversal|

### Method 2 -- Recursive

```java
public Node reverseListRecursive(Node head) {
    if (head == null || head.next == null) return head; // base case

    Node newHead = reverseListRecursive(head.next); // recurse to end

    head.next.next = head; // reverse the link on way back
    head.next = null;      // break old forward link

    return newHead;
}
```

| Operation              | Time Complexity | Space Complexity | Notes                          |
|------------------------|-----------------|------------------|--------------------------------|
| reverseListRecursive   | O(n)            | O(n)             | Call stack grows with list size|

**Caution:** Space complexity is O(n) due to the recursive call stack, not O(1).

### Method 3 -- Using Collections Utility

Only applicable when using Java's built-in `LinkedList`.

```java
import java.util.*;

LinkedList<Integer> list = new LinkedList<>();
list.add(1); list.add(2); list.add(3);
Collections.reverse(list);
System.out.println(list); // [3, 2, 1]
```

| Method       | Time | Space | Preferred When                      |
|--------------|------|-------|-------------------------------------|
| Iterative    | O(n) | O(1)  | Always -- interviews and production |
| Recursive    | O(n) | O(n)  | Conceptual clarity only             |
| Collections  | O(n) | O(1)  | Using Java's LinkedList in projects |

---

## Summary -- Operation Complexity Table

| Operation   | Time Complexity | Notes                                     |
|-------------|-----------------|-------------------------------------------|
| addFirst    | O(1)            | Redirect head pointer                     |
| addLast     | O(n)            | Traverse to tail; O(1) with tail pointer  |
| addInMiddle | O(n)            | Traverse to index - 1                     |
| removeFirst | O(1)            | Redirect head pointer                     |
| removeLast  | O(n)            | Traverse to second-to-last node           |
| getSize     | O(1)            | Tracked field                             |
| printList   | O(n)            | One full traversal                        |
| reverse     | O(n)            | O(1) space iterative; O(n) recursive      |

---

## Interview Cheat Sheet

- Linked list has O(1) insert/delete at head; O(n) everywhere else without a tail pointer.
- Array vs LinkedList: arrays win on random access (O(1)); linked lists win on head insert/delete.
- Reversal: always prefer iterative for interviews -- clean, O(1) space, no stack overflow risk.
- Two-pointer (slow/fast) is the go-to technique for: middle node, cycle detection, nth from end.

---

## Practice Problems

- Swap nodes in pairs (LeetCode 24)
- Remove nth node from end of list (LeetCode 19)
- Reverse linked list II -- reverse a subrange (LeetCode 92)
- Make a linked list with elements (1, 5, 7, 3, 8, 2, 3), search for 7 and display its index
- Take elements 1-50 from user input, delete all nodes with values greater than 25
