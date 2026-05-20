# Reverse Linked List in K-Groups

### Problem Statement

* Reverse nodes of a linked list in groups of size `k`.

---

### Example

#### Input

```text id="z4rj4o"
List = 1 -> 2 -> 3 -> 4 -> 5
k = 2
```

#### Output

```text id="t2n0g8"
2 -> 1 -> 4 -> 3 -> 5
```

---

### Idea / Intuition

* Reverse every group of `k` nodes
* Keep remaining nodes as it is if group size is smaller than `k`
* Connect reversed groups properly

---

### Optimized Approach

### steps for optimized approach

1. Find kth node from current position
2. Reverse current group
3. Connect previous group with reversed group
4. Move to next group
5. Repeat until list ends

### Java Code

```java id="a9b4dp"
class Solution {

    static ListNode reverseKGroup(ListNode head, int k) {

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode groupPrev = dummy;

        while (true) {

            ListNode kthNode = getKthNode(groupPrev, k);

            if (kthNode == null) {
                break;
            }

            ListNode groupNext = kthNode.next;

            // reverse current k-group
            ListNode newGroupHead =
                    reverse(groupPrev.next, groupNext);

            ListNode temp = groupPrev.next;

            groupPrev.next = newGroupHead;

            groupPrev = temp;
        }

        return dummy.next;
    }

    // reverse nodes until groupNext
    static ListNode reverse(ListNode head,
                            ListNode groupNext) {

        ListNode prev = groupNext;
        ListNode curr = head;

        while (curr != groupNext) {

            ListNode nextNode = curr.next;

            curr.next = prev;

            prev = curr;
            curr = nextNode;
        }

        return prev;
    }

    static ListNode getKthNode(ListNode curr, int k) {

        while (curr != null && k > 0) {
            curr = curr.next;
            k--;
        }

        return curr;
    }
}
```

---

# Rotate Linked List by K Places

### Problem Statement

* Rotate a linked list to the right by `k` places.

---

### Example

#### Input

```text id="s1r2jw"
List = 1 -> 2 -> 3 -> 4 -> 5
k = 2
```

#### Output

```text id="v3n1yl"
4 -> 5 -> 1 -> 2 -> 3
```

---

### Idea / Intuition

* Find length of list
* Connect tail to head to make circular list
* Break circle at correct position

---

### Optimized Approach

### steps for optimized approach

1. Find length and tail node
2. Compute `k % length`
3. Make linked list circular
4. Find new tail node
5. Break the circle

### Java Code

```java id="w6v0uk"
class Solution {

    static ListNode rotateRight(ListNode head, int k) {

        if (head == null || head.next == null || k == 0) {
            return head;
        }

        int length = 1;
        ListNode tail = head;

        while (tail.next != null) {
            tail = tail.next;
            length++;
        }

        k = k % length;

        if (k == 0) {
            return head;
        }

        tail.next = head;

        int steps = length - k;

        ListNode newTail = head;

        for (int i = 1; i < steps; i++) {
            newTail = newTail.next;
        }

        ListNode newHead = newTail.next;

        newTail.next = null;

        return newHead;
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(1)
*/
```

---

# Reverse Nodes in Even Length Groups

### Problem Statement

* Reverse nodes in groups having even length.

---

### Example

#### Input

```text id="b0pz8z"
1 -> 2 -> 3 -> 4 -> 5 -> 6
```

#### Output

```text id="k8t3wo"
1 -> 3 -> 2 -> 4 -> 5 -> 6
```

---

### Idea / Intuition

* Group sizes are:

  * 1, 2, 3, 4...
* Reverse only groups with even length
* Keep odd length groups unchanged

---

### Optimized Approach

### steps for optimized approach

1. Traverse group by group
2. Count actual nodes in current group
3. Reverse group if length is even
4. Connect reversed group properly
5. Move to next group

### Java Code

```java id="b7vxpa"
class Solution {

    static ListNode reverseEvenLengthGroups(ListNode head) {

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode prevGroup = dummy;
        ListNode curr = head;

        int groupSize = 1;

        while (curr != null) {

            int count = 0;
            ListNode temp = curr;

            while (temp != null && count < groupSize) {
                temp = temp.next;
                count++;
            }

            if (count % 2 == 0) {

                ListNode prev = temp;
                ListNode node = curr;

                for (int i = 0; i < count; i++) {

                    ListNode nextNode = node.next;

                    node.next = prev;

                    prev = node;
                    node = nextNode;
                }

                ListNode tail = curr;

                prevGroup.next = prev;

                prevGroup = tail;
                curr = temp;

            } else {

                for (int i = 0; i < count; i++) {
                    prevGroup = curr;
                    curr = curr.next;
                }
            }

            groupSize++;
        }

        return dummy.next;
    }
}

/*
Time Complexity - O(n)
Space Complexity - O(1)
*/
```
