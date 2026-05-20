# Reverse a Linked List (Iterative)

### Problem Statement

* Reverse a singly linked list using an iterative approach.

---

### Example

#### Input

```text
1 -> 2 -> 3 -> 4 -> 5
```

#### Output

```text
5 -> 4 -> 3 -> 2 -> 1
```

---

### Idea / Intuition

* Keep track of previous, current, and next nodes
* Reverse the link one by one
* Move forward until list ends

---

### Optimized Approach

### steps for optimized approach

1. Initialize `prev = null`
2. Traverse the list using `curr`
3. Store next node
4. Reverse current node link
5. Move pointers forward
6. Return `prev`

### Java Code

```java
class Solution {

    static ListNode reverseList(ListNode head) {

        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {

            ListNode nextNode = curr.next;

            curr.next = prev;

            prev = curr;
            curr = nextNode;
        }

        return prev;
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(1)
*/
```

---

# Reverse a Linked List (Recursive)

### Problem Statement

* Reverse a singly linked list using recursion.

---

### Example

#### Input

```text
1 -> 2 -> 3 -> 4 -> 5
```

#### Output

```text
5 -> 4 -> 3 -> 2 -> 1
```

---

### Idea / Intuition

* Go till last node using recursion
* While returning back, reverse links
* Make current node next point back to current node

---

### Optimized Approach

### steps for optimized approach

1. Base case:

   * if list is empty or only one node exists
2. Recursively reverse remaining list
3. Reverse current node connection
4. Return new head

### Java Code

```java
class Solution {

    static ListNode reverseList(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = reverseList(head.next);

        head.next.next = head;
        head.next = null;

        return newHead;
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(n)
*/
```

---

# Reverse a Doubly LinkedList

### Problem Statement

* Reverse a doubly linked list.

---

### Example

#### Input

```text
1 <-> 2 <-> 3 <-> 4
```

#### Output

```text
4 <-> 3 <-> 2 <-> 1
```

---

### Idea / Intuition

* Swap `next` and `prev` pointers for every node
* Move using previous pointer after swapping
* Last processed node becomes new head

---

### Optimized Approach

### steps for optimized approach

1. Traverse the list
2. Swap `prev` and `next`
3. Move to next node
4. Return new head

### Java Code

```java
class Solution {

    static Node reverseDLL(Node head) {

        Node curr = head;
        Node temp = null;

        while (curr != null) {

            temp = curr.prev;
            curr.prev = curr.next;
            curr.next = temp;

            curr = curr.prev;
        }

        if (temp != null) {
            head = temp.prev;
        }

        return head;
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(1)
*/
```

---

# Find Middle of Linked List

### Problem Statement

* Find the middle node of a linked list.

---

### Example

#### Input

```text
1 -> 2 -> 3 -> 4 -> 5
```

#### Output

```text
3
```

---

### Idea / Intuition

* Use slow and fast pointers
* Slow moves 1 step
* Fast moves 2 steps
* When fast reaches end, slow is at middle

---

### Optimized Approach

### steps for optimized approach

1. Initialize slow and fast pointers
2. Move slow by 1 step
3. Move fast by 2 steps
4. Return slow pointer

### Java Code

```java
class Solution {

    static ListNode middleNode(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {

            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(1)
*/
```

---

# Delete Middle Node of Linked List

### Problem Statement

* Delete the middle node of a linked list.

---

### Example

#### Input

```text
1 -> 2 -> 3 -> 4 -> 5
```

#### Output

```text
1 -> 2 -> 4 -> 5
```

---

### Idea / Intuition

* Use slow and fast pointers to find middle
* Keep previous node of slow
* Remove middle node by changing links

---

### Optimized Approach

### steps for optimized approach

1. Handle single node case
2. Use slow and fast pointers
3. Track previous node of slow
4. Delete middle node
5. Return head

### Java Code

```java
class Solution {

    static ListNode deleteMiddle(ListNode head) {

        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;

        while (fast != null && fast.next != null) {

            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        prev.next = slow.next;

        return head;
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(1)
*/
class Solution {

    static ListNode deleteMiddle(ListNode head) {

        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;
        ListNode middle = null;

        while (fast != null && fast.next != null) {

            middle = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        middle.next = middle.next.next;

        return head;
    }
}
```
