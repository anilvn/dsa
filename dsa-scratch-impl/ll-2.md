# Best Linked List Problems -- Java

## How to Read This Document

Each problem follows the same structure: problem statement, approach, clean code with inline comments, complexity analysis, and edge cases.

---

## Problem 1 -- Remove Nth Node from End

**Problem:** Given a linked list, remove the nth node from the end and return the head.

**Approach:** Two-pass -- count total nodes, then compute position from start.

**Time Complexity:** O(n) | **Space Complexity:** O(1)

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    // edge case: single node, removal empties the list
    if (head.next == null) return null;

    // pass 1: count total nodes
    int size = 0;
    ListNode temp = head;
    while (temp != null) {
        temp = temp.next;
        size++;
    }

    // removing the head node (nth from end == first from start)
    if (n == size) return head.next;

    // pass 2: find the node just before the target
    int ptf = size - n; // steps from head to predecessor of target
    ListNode prev = head;
    int cp = 1;

    while (cp != ptf) {
        prev = prev.next;
        cp++;
    }

    prev.next = prev.next.next; // unlink the nth node
    return head;
}
```

**Edge cases:**
- Single node: list becomes empty, return null.
- `n == size`: target is the head; return `head.next`.
- No need to handle n > size if input is guaranteed valid.

**Alternative approach:** Single-pass using two pointers -- advance fast pointer by n steps, then move both until fast reaches end. `slow.next` is the target. This eliminates the counting pass.

---

## Problem 2 -- Check if a Linked List is a Palindrome

**Problem:** Return true if the linked list reads the same forwards and backwards.

**Approach:** Find the middle, reverse the second half, compare both halves node by node.

**Time Complexity:** O(n) | **Space Complexity:** O(1)

### Helper -- Find Middle Node (Slow/Fast Pointer)

```java
public ListNode getMiddle(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;

    // fast moves 2x speed; when fast reaches end, slow is at middle
    while (fast.next != null && fast.next.next != null) {
        fast = fast.next.next;
        slow = slow.next;
    }
    return slow;
}
```

### Helper -- Reverse a Linked List

```java
public ListNode reverse(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;

    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
```

### Main -- Palindrome Check

```java
public boolean isPalindrome(ListNode head) {
    if (head == null || head.next == null) return true;

    ListNode firstHalfEnd    = getMiddle(head);
    ListNode secondHalfStart = reverse(firstHalfEnd.next); // reverse second half

    ListNode p1 = head;
    ListNode p2 = secondHalfStart;

    while (p2 != null) { // second half is shorter or equal
        if (p1.val != p2.val) return false;
        p1 = p1.next;
        p2 = p2.next;
    }

    return true;
}
```

**Edge cases:**
- Empty list or single node: trivially a palindrome.
- Odd-length list: middle node is excluded from the second half comparison (handled by `fast.next.next` condition).

**Note:** This solution mutates the original list by reversing the second half. Restore it after comparison if the problem requires the list to remain unchanged.

---

## Problem 3 -- Detect a Cycle in a Linked List

**Problem:** Return true if the linked list contains a cycle.

**Approach:** Floyd's Cycle Detection (Tortoise and Hare). If slow and fast pointers ever meet, a cycle exists.

**Time Complexity:** O(n) | **Space Complexity:** O(1)

```java
public boolean hasCycle(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;

    while (fast != null && fast.next != null) {
        slow = slow.next;       // moves 1 step
        fast = fast.next.next;  // moves 2 steps

        if (fast == slow) return true; // collision -- cycle confirmed
    }
    return false; // fast reached end -- no cycle
}
```

**Why it works:** In a cycle, fast laps slow. The relative speed difference is 1 step per iteration, so they are guaranteed to meet within the cycle. Without a cycle, fast hits null.

**Edge cases:**
- Empty list or single node: `fast.next` is null immediately, returns false.
- Cycle starting at head (self-loop): detected on first or second iteration.

---

## Problem 4 -- Remove a Loop from a Linked List

**Problem:** Detect a cycle and remove it so the list terminates normally.

**Approach:** Use Floyd's algorithm to detect the cycle, then find the loop start, then break the link at the last node of the cycle.

**Time Complexity:** O(n) | **Space Complexity:** O(1)

### Step 1 -- Detect the Cycle

```java
ListNode slow = head;
ListNode fast = head;
boolean hasCycle = false;

while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
    if (slow == fast) {
        hasCycle = true;
        break;
    }
}

if (!hasCycle) return; // no cycle to remove
```

### Step 2 -- Find Loop Start

```java
// Reset one pointer to head; move both one step at a time
// They will meet at the start of the loop
slow = head;
while (slow != fast) {
    slow = slow.next;
    fast = fast.next;
}
// slow == fast == loop start node
ListNode loopStart = slow;
```

**Why this works:** After detection, the distance from head to loop start equals the distance from the meeting point to loop start (a mathematical property of Floyd's algorithm).

### Step 3 -- Break the Loop

```java
// Advance to the last node of the loop (the node whose next points back to loopStart)
ListNode loopEnd = loopStart;
while (loopEnd.next != loopStart) {
    loopEnd = loopEnd.next;
}
loopEnd.next = null; // break the cycle
```

### Complete Method

```java
public void removeLoop(ListNode head) {
    if (head == null) return;

    ListNode slow = head, fast = head;
    boolean hasCycle = false;

    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) { hasCycle = true; break; }
    }

    if (!hasCycle) return;

    slow = head;
    while (slow != fast) {
        slow = slow.next;
        fast = fast.next;
    }

    ListNode loopEnd = slow;
    while (loopEnd.next != slow) {
        loopEnd = loopEnd.next;
    }
    loopEnd.next = null;
}
```

**Edge cases:** Loop that starts at head -- the initial `slow = head` and `fast` (at meeting point) will coincide immediately in the "find loop start" phase.

---

## Summary -- Complexity Table

| Problem              | Time Complexity | Space Complexity | Key Technique                     |
|----------------------|-----------------|------------------|-----------------------------------|
| Remove Nth from End  | O(n)            | O(1)             | Two-pass count + traverse         |
| Palindrome Check     | O(n)            | O(1)             | Slow/fast middle + reverse        |
| Detect Cycle         | O(n)            | O(1)             | Floyd's Tortoise and Hare         |
| Remove Loop          | O(n)            | O(1)             | Floyd's + loop start detection    |

---

## Interview Cheat Sheet

- Slow/fast pointer is the universal tool for: middle node, cycle detection, nth from end.
- Palindrome check: split at middle + reverse second half + compare. Restore if needed.
- Floyd's cycle detection always uses relative speed of 1 vs 2. Meeting guarantees a cycle.
- To find the loop start after detection: reset one pointer to head, move both at speed 1.
- Always check for null and single-node cases first -- they are the most common edge cases.
