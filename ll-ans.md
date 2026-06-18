### 1. Detect if a Linked List Has a Cycle (Floyd's Cycle Detection)

```java
/**
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public boolean hasCycle(ListNode head) {

    // Slow moves 1 step at a time
    ListNode slow = head;

    // Fast moves 2 steps at a time
    ListNode fast = head;

    while (fast != null && fast.next != null) {

        slow = slow.next;
        fast = fast.next.next;

        // If both pointers meet, cycle exists
        if (slow == fast) {
            return true;
        }
    }

    // Fast reached end => no cycle
    return false;
}
```

---

### 2. Find the Starting Node of the Cycle

**Logic:**

* First find meeting point of slow and fast.
* Move one pointer to head.
* Move both one step at a time.
* Where they meet again = start of cycle.

```java
/**
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public ListNode detectCycleStart(ListNode head) {

    ListNode slow = head;
    ListNode fast = head;

    // Step 1: Find meeting point
    while (fast != null && fast.next != null) {

        slow = slow.next;
        fast = fast.next.next;

        if (slow == fast) {

            // Step 2: Move one pointer to head
            ListNode ptr1 = head;
            ListNode ptr2 = slow;

            // Step 3: Move both one step
            while (ptr1 != ptr2) {
                ptr1 = ptr1.next;
                ptr2 = ptr2.next;
            }

            // Start of cycle
            return ptr1;
        }
    }

    // No cycle
    return null;
}
```

---

### 3. Find Length of the Cycle

**Logic:**

* First detect cycle.
* Once slow and fast meet, keep moving one pointer until it comes back to same node.
* Count the steps.

```java
/**
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public int cycleLength(ListNode head) {

    ListNode slow = head;
    ListNode fast = head;

    while (fast != null && fast.next != null) {

        slow = slow.next;
        fast = fast.next.next;

        if (slow == fast) {

            // Cycle found
            int length = 1;

            ListNode current = slow.next;

            while (current != slow) {
                length++;
                current = current.next;
            }

            return length;
        }
    }

    // No cycle
    return 0;
}
```

---

### Interview One-Liner

| Problem         | Key Idea                                                   |
| --------------- | ---------------------------------------------------------- |
| Detect Cycle    | Slow = 1 step, Fast = 2 steps. If they meet → cycle exists |
| Start of Cycle  | After meeting, move one pointer to head and both by 1 step |
| Length of Cycle | From meeting point, make one full round and count nodes    |

**Remember:** Floyd's Algorithm solves all three problems in **O(n) time** and **O(1) space**.
