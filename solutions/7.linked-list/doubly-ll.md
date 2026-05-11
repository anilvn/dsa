

### 1. Node + List Initialization

```java
// Node structure — holds data, next, and prev pointers
// Time: O(1)
// Space: O(1)
class Node {
    int data;
    Node next;
    Node prev;

    Node(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

// List maintains head, tail, and size
// Time: O(1)
// Space: O(1)
class DoublyLinkedList {
    private Node head;
    private Node tail;
    private int size;

    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
```

---

### 2. Build from Array

```java
    // Algorithm:
    // 1. Insert elements sequentially using insertAtTail

    // Time: O(n)
    // Space: O(n)
    public static DoublyLinkedList buildFromArray(int[] nums) {
        DoublyLinkedList list = new DoublyLinkedList();
        for (int num : nums) {
            list.insertAtTail(num);
        }
        return list;
    }
```

---

### 3. Display / Traversal

```java
    // Forward traversal: head → null
    // Time: O(n) | Space: O(1)
    public void displayForward() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " <-> ");
            temp = temp.next;
        }
        System.out.println("null");
    }

    // Backward traversal: tail → null
    // Time: O(n) | Space: O(1)
    public void displayBackward() {
        Node temp = tail;
        while (temp != null) {
            System.out.print(temp.data + " <-> ");
            temp = temp.prev;
        }
        System.out.println("null");
    }
```

---

### 4. Length

```java
    // Returns size directly (O(1) because size is maintained)

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
    // Traverse from head and compare values

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
    // Returns 1-based index of value, -1 if not found

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
    // New node's next = old head
    // Old head's prev = new node
    // Update head (and tail if list was empty)

    // Time: O(1)
    // Space: O(1)
    public void insertAtHead(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }
```

---

### 8. Insert at Tail

```java
    // New node's prev = old tail
    // Old tail's next = new node
    // Update tail (and head if list was empty)

    // Time: O(1)  ← O(1) because we maintain a tail pointer!
    // Space: O(1)
    public void insertAtTail(int value) {
        Node newNode = new Node(value);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }
```

---

### 9. Insert at Kth Position

```java
    // Algorithm:
    // 1. Validate k (1 to size+1)
    // 2. Traverse to (k-1)th node
    // 3. Link new node on both sides

    // Time: O(n)
    // Space: O(1)
    public void insertAtKth(int k, int value) {
       if (k < 1 || k > size + 1) return;
    
       if (k == 1) {
           insertAtHead(value);
           return;
       }
    
       if (k == size + 1) {
           insertAtTail(value);
           return;
       }
    
       Node temp = head;
       int count = 1;
    
       while (count < k - 1) {
           temp = temp.next;
           count++;
       }
    
       Node newNode = new Node(value);
    
       newNode.next = temp.next;
       newNode.prev = temp;
    
       temp.next.prev = newNode;  // if (temp.next != null)  may needed.
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
        if (head == null) {
            return;
        }

        if (head.data == target) {
            insertAtHead(value);
            return;
        }

        Node temp = head;

        while (temp != null && temp.data != target) {
            temp = temp.next;
        }

        if (temp == null) {
            return; // target not found
        }

        Node newNode = new Node(value);

        newNode.next = temp;
        newNode.prev = temp.prev;

        temp.prev.next = newNode;
        temp.prev = newNode;

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

        if (head == tail) {        // only one node
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
    }
```

---

### 12. Delete Tail

```java
    // O(1) because we have a tail pointer — no traversal needed!

    // Time: O(1)
    // Space: O(1)
    public void deleteTail() {
        if (tail == null) return;

        if (head == tail) {        // only one node
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
    }
```

---

### 13. Delete Kth

```java
    // Algorithm:
    // 1. Validate k
    // 2. Traverse to kth node
    // 3. Re-link neighbors around it

    // Time: O(n)
    // Space: O(1)
    public void deleteKth(int k) {
        if (head == null || k < 1 || k > size) return;

        if (k == 1)    { deleteHead(); return; }
        if (k == size) { deleteTail(); return; }

        Node current = head;
        for (int i = 1; i < k; i++) {
            current = current.next;
        }

        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
    }
```

---

### 14. Delete by Value

```java
    // Time: O(n)
    // Space: O(1)
    public void deleteByValue(int value) {
        if (head == null) return;
        if (head.data == value) { deleteHead(); return; }
        if (tail.data == value) { deleteTail(); return; }

        Node temp = head;
        while (temp != null && temp.data != value) {
            temp = temp.next;
        }

        if (temp == null) return; // not found

        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        size--;
    }
```

---

### 15. Reverse Iterative

```java
    // Swap next and prev for every node, then swap head and tail

    // Time: O(n)
    // Space: O(1)
    public void reverseIterative() {
        Node current = head;
        Node temp = null;

        while (current != null) {
            temp = current.prev;
            current.prev = current.next;
            current.next = temp;
            current = current.prev; // move forward (was next)
        }

        // swap head and tail
        temp = head;
        head = tail;
        tail = temp;
    }
```

---

### 16. Reverse Recursive

```java
    // Time: O(n)
    // Space: O(n)  ← call stack
    public void reverseRecursive() {
        head = reverseRec(head);
        // fix tail: walk to end
        Node temp = head;
        tail = null;
        while (temp != null) {
            tail = temp;
            temp = temp.next;
        }
    }

    private Node reverseRec(Node node) {
        if (node == null) return null;

        // swap next and prev
        Node temp = node.next;
        node.next = node.prev;
        node.prev = temp;

        if (temp == null) return node; // new head

        return reverseRec(temp);
    }
}
```

---

### Key Differences vs Singly Linked List

| Operation | Singly LL | Doubly LL |
|---|---|---|
| Delete tail | O(n) — must traverse | **O(1)** — tail pointer |
| Insert tail | O(n) without tail ptr | **O(1)** — tail pointer |
| Insert before value | Need to track `prev` manually | Clean — node has `.prev` |
| Reverse traversal | Not possible | O(n) — use `.prev` |
| Space per node | 1 pointer | **2 pointers** (extra memory) |

The main trade-off is **extra memory per node** for `.prev` in exchange for **O(1) tail operations** and **bidirectional traversal**.