# Remove Nth Node from End

### Problem Statement

* Remove the nth node from the end of a linked list.

---

### Example

#### Input

```text id="stn8ul"
List = 1 -> 2 -> 3 -> 4 -> 5
n = 2
```

#### Output

```text id="3m8s8m"
1 -> 2 -> 3 -> 5
```

---

### Idea / Intuition

* Use two pointers
* Move fast pointer `n` steps ahead
* Move both pointers together
* Slow reaches node before target node

---

### Optimized Approach

### steps for optimized approach

1. Create dummy node
2. Move fast pointer `n` steps
3. Move slow and fast together
4. Delete target node
5. Return updated head

### Java Code

```java id="m7c35q"
class Solution {

    static ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode slow = dummy;
        ListNode fast = dummy;

        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        while (fast.next != null) {

            slow = slow.next;
            fast = fast.next;
        }

        slow.next = slow.next.next;

        return dummy.next;
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(1)
*/
```

---

# Merge Two Sorted Linked Lists

### Problem Statement

* Merge two sorted linked lists into one sorted linked list.

---

### Example

#### Input

```text id="6jef6m"
List1 = 1 -> 3 -> 5
List2 = 2 -> 4 -> 6
```

#### Output

```text id="qv0l7t"
1 -> 2 -> 3 -> 4 -> 5 -> 6
```

---

### Idea / Intuition

* Compare nodes from both lists
* Attach smaller node to result
* Continue until one list ends
* Attach remaining nodes

---

### Optimized Approach

### steps for optimized approach

1. Create dummy node
2. Compare current nodes
3. Attach smaller node
4. Move pointers forward
5. Attach remaining list

### Java Code

```java id="0glk0o"
class Solution {

    static ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        ListNode dummy = new ListNode(-1);
        ListNode tail = dummy;

        while (list1 != null && list2 != null) {

            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }

            tail = tail.next;
        }

        if (list1 != null) {
            tail.next = list1;
        } else {
            tail.next = list2;
        }

        return dummy.next;
    }
}

/*
Time Complexity - O(n + m)
Space Complexity - O(1)
*/
```

---

# Delete a Given Node (Without Head Pointer)

### Problem Statement

* Delete a node from linked list when only that node is given.

---

### Example

#### Input

```text id="x7r3gi"
1 -> 2 -> 3 -> 4
Delete node = 3
```

#### Output

```text id="k5r10d"
1 -> 2 -> 4
```

---

### Idea / Intuition

* Copy next node value into current node
* Skip next node

---

### Optimized Approach

### steps for optimized approach

1. Copy next node data
2. Change next pointer
3. Remove next node

### Java Code

```java id="8t1xih"
class Solution {

    static void deleteNode(ListNode node) {

        node.val = node.next.val;
        node.next = node.next.next;
    }
}

/*
Time Complexity - O(1)
Space Complexity - O(1)
*/
```

---

# Remove Duplicates from Sorted Linked List

### Problem Statement

* Remove duplicate nodes from a sorted linked list.

---

### Example

#### Input

```text id="i11z8v"
1 -> 1 -> 2 -> 3 -> 3
```

#### Output

```text id="iyv1d2"
1 -> 2 -> 3
```

---

### Idea / Intuition

* Duplicates are adjacent in sorted list
* Compare current node with next node
* Skip duplicate nodes

---

### Optimized Approach

### steps for optimized approach

1. Traverse the list
2. Compare current and next node
3. Remove duplicate node
4. Continue traversal

### Java Code

```java id="4jlwmg"
class Solution {

    static ListNode deleteDuplicates(ListNode head) {

        ListNode curr = head;

        while (curr != null && curr.next != null) {

            if (curr.val == curr.next.val) {
                curr.next = curr.next.next;
            } else {
                curr = curr.next;
            }
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

# Remove Duplicates from Unsorted Linked List

### Problem Statement

* Remove duplicate nodes from an unsorted linked list.

---

### Example

#### Input

```text id="p3a9ek"
1 -> 3 -> 2 -> 3 -> 1 -> 4
```

#### Output

```text id="4myt3e"
1 -> 3 -> 2 -> 4
```

---

### Idea / Intuition

* Use HashSet to track visited values
* Remove node if value already exists
* Otherwise store value in set

---

### Optimized Approach

### steps for optimized approach

1. Create HashSet
2. Traverse linked list
3. Check duplicate value
4. Remove duplicate nodes
5. Return updated list

### Java Code

```java id="op37jv"
import java.util.*;

class Solution {

    static ListNode removeDuplicates(ListNode head) {

        HashSet<Integer> set = new HashSet<>();

        ListNode curr = head;
        ListNode prev = null;

        while (curr != null) {

            if (set.contains(curr.val)) {
                prev.next = curr.next;
            } else {
                set.add(curr.val);
                prev = curr;
            }

            curr = curr.next;
        }

        return head;
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(n)
*/
```

---

# Add 1 to Number Represented by LinkedList

### Problem Statement

* Add 1 to a number represented as a linked list.

---

### Example

#### Input

```text id="a5l5fy"
1 -> 9 -> 9
```

#### Output

```text id="d07vlu"
2 -> 0 -> 0
```

---

### Idea / Intuition

* Reverse the list
* Add 1 like normal addition
* Handle carry
* Reverse the list again

---

### Optimized Approach

### steps for optimized approach

1. Reverse the linked list
2. Add 1 to first node
3. Handle carry while traversing
4. Add new node if carry remains
5. Reverse list again

### Java Code

```java id="g4g8xf"
class Solution {

    static ListNode addOne(ListNode head) {

        head = reverse(head);

        ListNode curr = head;
        int carry = 1;

        while (curr != null) {

            int sum = curr.val + carry;

            curr.val = sum % 10;
            carry = sum / 10;

            if (carry == 0) {
                break;
            }

            if (curr.next == null) {
                curr.next = new ListNode(0);
            }

            curr = curr.next;
        }

        return reverse(head);
    }

    static ListNode reverse(ListNode head) {

        ListNode prev = null;

        while (head != null) {

            ListNode nextNode = head.next;

            head.next = prev;

            prev = head;
            head = nextNode;
        }

        return prev;
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(1)
*/
```
