# Detect Cycle in Linked List (Floyd’s Cycle Detection)

### Problem Statement

* Detect whether a linked list contains a cycle or not.

---

### Example

#### Input

```text
1 -> 2 -> 3 -> 4 -> 5
          ^         |
          |_________|
```

#### Output

```text
true
```

---

### Idea / Intuition

* Use slow and fast pointers
* Slow moves 1 step
* Fast moves 2 steps
* If they meet, cycle exists

---

### Optimized Approach

### steps for optimized approach

1. Initialize slow and fast pointers
2. Move slow by 1 step
3. Move fast by 2 steps
4. If both meet, cycle exists
5. If fast reaches null, no cycle

### Java Code

```java
class Solution {

    static boolean hasCycle(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {

            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(1)
*/
```

---

# Find Starting Point of Cycle

### Problem Statement

* Find the node where the cycle begins in a linked list.

---

### Example

#### Input

```text
1 -> 2 -> 3 -> 4 -> 5
          ^         |
          |_________|
```

#### Output

```text
3
```

---

### Idea / Intuition

* First detect cycle using Floyd’s algorithm
* After meeting point is found:

  * move one pointer to head
  * move both one step at a time
* Meeting point becomes cycle start

---

### Optimized Approach

### steps for optimized approach

1. Detect cycle using slow and fast pointers
2. If no cycle, return null
3. Move slow to head
4. Move both pointers one step
5. Return meeting node

### Java Code

```java
class Solution {

    static ListNode detectCycle(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {

            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {

                slow = head;

                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }

                return slow;
            }
        }

        return null;
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(1)
*/
```

---

# Length of Cycle in Linked List

### Problem Statement

* Find the length of cycle present in a linked list.

---

### Example

#### Input

```text
1 -> 2 -> 3 -> 4 -> 5
          ^         |
          |_________|
```

#### Output

```text
3
```

---

### Idea / Intuition

* Detect cycle using slow and fast pointers
* Once they meet:

  * move one pointer until it comes back again
  * count the nodes traversed

---

### Optimized Approach

### steps for optimized approach

1. Detect cycle using Floyd’s algorithm
2. If pointers meet:

   * initialize count as 1
3. Move one pointer until it reaches same node
4. Return count
5. If no cycle, return 0

### Java Code

```java
class Solution {

    static int cycleLength(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {

            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {

                int count = 1; // initial count - for making a valid move -- while loop
                fast = fast.next;

                while (slow != fast) {
                    count++;
                    fast = fast.next;
                }

                return count;
            }
        }

        return 0;
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(1)
*/
```
