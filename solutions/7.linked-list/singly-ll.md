
<!-- ## FULL REWRITE (BLOCKS WITH COMMENTS + COMPLEXITY) -->

### 1. Node + List Initialization

```java
// Node structure
// Time: O(1)
// Space: O(1)
class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

// List initialization
// Maintains head and size
// Time: O(1)
// Space: O(1)
class SinglyLinkedList {
    private Node head;
    private int size;

    public SinglyLinkedList() {
        head = null;
        size = 0;
    }
```

---

### 2. Build from Array

```java
    // Algorithm:
    // 1. Insert elements sequentially

    // Time: O(n)
    // Space: O(n)

    public static Node buildFromArray(int[] nums) {
        if (nums == null || nums.length == 0) return null;

        Node start = new Node(nums[0]);
        Node current = start;

        for (int index = 1; index < nums.length; index++) {
            Node newNode = new Node(nums[index]);
            current.next = newNode;
            current = newNode;
        }

        return start;
    }
```

---

### 3. Display / Traversal

```java
    // Algorithm:
    // Traverse from head to null

    // Time: O(n)
    // Space: O(1)
    public void display() {
        Node temp = head;

        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println("null");
    }
```

---

### 4. Length

```java
    // Returns size directly

    // Time: O(1)
    // Space: O(1)
    public int getLength() {
        return size;
    }
```

---

### 5. Search

```java
    // Algorithm:
    // Traverse and compare values

    // Time: O(n)
    // Space: O(1)
    public boolean search(int value) {
        Node temp = head;

        while (temp != null) {
            if (temp.data == value) return true;
            temp = temp.next;
        }
        return false;
    }
```

---

### 6. Find Position

```java
    // Returns 1-based index

    // Time: O(n)
    // Space: O(1)
    public int findPosition(int value) {
        Node temp = head;
        int pos = 1;

        while (temp != null) {
            if (temp.data == value) return pos;
            temp = temp.next;
            pos++;
        }
        return -1;
    }
```

---

### 7. Insert at Head

```java
    // Time: O(1)
    // Space: O(1)
    public void insertAtHead(int value) {
        Node newNode = new Node(value);
        newNode.next = head;
        head = newNode;
        size++;
    }
```

---

### 8. Insert at Tail

```java
    // Time: O(n)
    // Space: O(1)
    public void insertAtTail(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }

        size++;
    }
```

---

### 9. Insert at Kth Position

```java
    // Algorithm:
    // 1. Validate k (1 to size+1)
    // 2. Traverse to (k-1)
    // 3. Insert node

    // Time: O(n)
    // Space: O(1)
    public void insertAtKth(int k, int value) {
        if (k < 1 || k > size + 1) return;

        if (k == 1) {
            insertAtHead(value);
            return;
        }

        Node temp = head;
        for (int i = 1; i < k - 1; i++) {
            temp = temp.next;
        }

        Node newNode = new Node(value);
        newNode.next = temp.next;
        temp.next = newNode;
        size++;
    }
```

---

### 10. Insert Before Value

```java
    // Time: O(n)
    // Space: O(1)
    public void insertBeforeValue(int target, int value) {
        if (head == null) return;

        if (head.data == target) {
            insertAtHead(value);
            return;
        }

        Node temp = head;

        while (temp.next != null && temp.next.data != target) {
            temp = temp.next;
        }

        if (temp.next == null) return;

        Node newNode = new Node(value);
        newNode.next = temp.next;
        temp.next = newNode;
        size++;
    }
```

---

### 11. Delete Head

```java
    // Time: O(1)
    // Space: O(1)
    public void deleteHead() {
        if (head == null) return;

        head = head.next;
        size--;
    }
```

---

### 12. Delete Tail

```java
    // Time: O(n)
    // Space: O(1)
    public void deleteTail() {
        if (head == null) return;

        if (head.next == null) {
            head = null;
        } else {
            Node temp = head;
            while (temp.next.next != null) {
                temp = temp.next;
            }
            temp.next = null;
        }

        size--;
    }
```

---

### 13. Delete Kth

```java
// Algorithm:
// 1. Validate k (optional but recommended)
// 2. Traverse to (k-1) using while loop
// 3. Skip kth node

// Time Complexity: O(n)
// Space Complexity: O(1)

public void deleteKth(int k) {
    if (head == null) return;

    if (k == 1) {
        deleteHead();
        return;
    }

    Node current = head;
    int position = 1;

    while (position < k - 1 && current.next != null) {
        current = current.next;
        position++;
    }

    if (current.next == null) return;

    current.next = current.next.next;
}
```

---

### 14. Delete by Value

```java
    // Time: O(n)
    // Space: O(1)
    public void deleteByValue(int value) {
        if (head == null) return;

        if (head.data == value) {
            deleteHead();
            return;
        }

        Node temp = head;

        while (temp.next != null && temp.next.data != value) {
            temp = temp.next;
        }

        if (temp.next == null) return;

        temp.next = temp.next.next;
        size--;
    }
```

---

### 15. Reverse Iterative

```java
    // Time: O(n)
    // Space: O(1)
    public void reverseIterative() {
        Node prev = null;
        Node curr = head;

        while (curr != null) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        head = prev;
    }
```

---

### 16. Reverse Recursive

```java
    // Time: O(n)
    // Space: O(n)
    public void reverseRecursive() {
        head = reverseRec(head);
    }

    private Node reverseRec(Node node) {
        if (node == null || node.next == null) return node;

        Node newHead = reverseRec(node.next);

        node.next.next = node;
        node.next = null;

        return newHead;
    }
}
```

---

## FINAL MERGED CODE (with mimimal comments.)

```java
class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class SinglyLinkedList {
    private Node head;
    private int size;

    public SinglyLinkedList() {
        head = null;
        size = 0;
    }

    public static Node buildFromArray(int[] nums) {
        if (nums == null || nums.length == 0) return null;

        Node start = new Node(nums[0]);
        Node current = start;

        for (int index = 1; index < nums.length; index++) {
            Node newNode = new Node(nums[index]);
            current.next = newNode;
            current = newNode;
        }

        return start;
    }

    public void display() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println("null");
    }

    public int getLength() {
        return size;
    }

    public boolean search(int value) {
        Node temp = head;
        while (temp != null) {
            if (temp.data == value) return true;
            temp = temp.next;
        }
        return false;
    }

    public int findPosition(int value) {
        Node temp = head;
        int pos = 1;
        while (temp != null) {
            if (temp.data == value) return pos;
            temp = temp.next;
            pos++;
        }
        return -1;
    }

    public void insertAtHead(int value) {
        Node newNode = new Node(value);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public void insertAtTail(int value) {
        Node newNode = new Node(value);
        if (head == null) head = newNode;
        else {
            Node temp = head;
            while (temp.next != null) temp = temp.next;
            temp.next = newNode;
        }
        size++;
    }

    public void insertAtKth(int k, int value) {
        if (k < 1 || k > size + 1) return;
        if (k == 1) {
            insertAtHead(value);
            return;
        }
        Node temp = head;
        for (int i = 1; i < k - 1; i++) temp = temp.next;
        Node newNode = new Node(value);
        newNode.next = temp.next;
        temp.next = newNode;
        size++;
    }

    public void insertBeforeValue(int target, int value) {
        if (head == null) return;
        if (head.data == target) {
            insertAtHead(value);
            return;
        }
        Node temp = head;
        while (temp.next != null && temp.next.data != target) temp = temp.next;
        if (temp.next == null) return;
        Node newNode = new Node(value);
        newNode.next = temp.next;
        temp.next = newNode;
        size++;
    }

    public void deleteHead() {
        if (head == null) return;
        head = head.next;
        size--;
    }

    public void deleteTail() {
        if (head == null) return;
        if (head.next == null) head = null;
        else {
            Node temp = head;
            while (temp.next.next != null) temp = temp.next;
            temp.next = null;
        }
        size--;
    }

    public void deleteKth(int k) {
        if (k < 1 || k > size) return;
        if (k == 1) {
            deleteHead();
            return;
        }
        Node temp = head;
        for (int i = 1; i < k - 1; i++) temp = temp.next;
        temp.next = temp.next.next;
        size--;
    }

    public void deleteByValue(int value) {
        if (head == null) return;
        if (head.data == value) {
            deleteHead();
            return;
        }
        Node temp = head;
        while (temp.next != null && temp.next.data != value) temp = temp.next;
        if (temp.next == null) return;
        temp.next = temp.next.next;
        size--;
    }

    public void reverseIterative() {
        Node prev = null;
        Node curr = head;
        while (curr != null) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        head = prev;
    }

    public void reverseRecursive() {
        head = reverseRec(head);
    }

    private Node reverseRec(Node node) {
        if (node == null || node.next == null) return node;
        Node newHead = reverseRec(node.next);
        node.next.next = node;
        node.next = null;
        return newHead;
    }
}
```

---

### FINAL NOTES

* All boundary cases handled: empty list, single node, invalid positions.
* Size tracking avoids repeated traversal.
* All insert/delete operations validate inputs.
* Recursive reverse correctly updates head.
* No memory leaks via dangling references.
* Consistent behavior: invalid operations safely return.

If you want next, I can turn this into a **dry-run visual trace (pointer movement step-by-step)** which is what usually makes everything click.
