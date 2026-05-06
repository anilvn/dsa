# Linked List — Notes

---

## 1. Swap Pairs

Swap every two adjacent nodes (no value swapping — pointer manipulation only).

`1->2->3->4->5` → `2->1->4->3->5`

**Recursive:** swap first two, recurse on the rest, connect back.

```java
public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode first = head, second = head.next;
    first.next = swapPairs(second.next); // recurse on tail
    second.next = first;                 // second becomes new head of this pair
    return second;
}
```

- Time: O(n), Space: O(n/2) recursion stack

---

## 2. Flatten Multilevel Linked List

Each node may have a `child` pointer to another list. Flatten into a single list.

**Singly linked list version:**

```java
public Node flatten(Node head) {
    Node curr = head;
    while (curr != null) {
        if (curr.child != null) {
            Node nextTemp = curr.next;
            Node childTail = flatten(curr.child); // flatten child, get its tail
            curr.next = curr.child;
            curr.child = null;
            childTail.next = nextTemp;            // reattach original next
        }
        curr = curr.next;
    }
    return head;
}
```

**Doubly linked list version** — same logic, but also update `prev` pointers:

```java
public Node flatten(Node head) {
    flattenRec(head);
    return head;
}

// Returns tail of the flattened segment
private Node flattenRec(Node head) {
    Node curr = head, tail = head;
    while (curr != null) {
        Node nextTemp = curr.next;
        if (curr.child != null) {
            Node childTail = flattenRec(curr.child);
            curr.next = curr.child;
            curr.child.prev = curr;
            curr.child = null;
            if (nextTemp != null) {
                childTail.next = nextTemp;
                nextTemp.prev = childTail;
            }
            tail = childTail;
        } else {
            tail = curr;
        }
        curr = nextTemp;
    }
    return tail;
}
```

- Time: O(n), Space: O(d) where d = depth of nesting (recursion stack)

---

## 3. Copy List with Random Pointer

Deep copy a linked list where each node has `next` and `random` pointers.

**Approach — HashMap (old node → new node):**
1. First pass: create all new nodes, map old→new.
2. Second pass: assign `next` and `random` using the map.

```java
public Node copyRandomList(Node head) {
    if (head == null) return null;
    HashMap<Node, Node> map = new HashMap<>();

    // Pass 1: clone all nodes
    Node curr = head;
    while (curr != null) {
        map.put(curr, new Node(curr.val));
        curr = curr.next;
    }

    // Pass 2: wire next and random
    curr = head;
    while (curr != null) {
        map.get(curr).next   = map.get(curr.next);
        map.get(curr).random = map.get(curr.random);
        curr = curr.next;
    }
    return map.get(head);
}
```

- Time: O(n), Space: O(n)

---

## 4. LRU Cache

`get(key)` and `put(key, value)` both in O(1).

**Data structures:**
- `HashMap<key, Node>` — O(1) lookup
- Doubly linked list — maintains usage order (MRU at head, LRU at tail)
- Dummy `head` and `tail` sentinels to avoid null checks

**Key insight:** store `key` inside the node so we can remove it from the map on eviction.

```java
class LRUCache {
    class Node {
        int key, value;
        Node prev, next;
        Node(int k, int v) { key = k; value = v; }
    }

    private final int capacity;
    private final HashMap<Integer, Node> map = new HashMap<>();
    private final Node head = new Node(0, 0); // dummy MRU sentinel
    private final Node tail = new Node(0, 0); // dummy LRU sentinel

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        remove(node);
        insertToFront(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) remove(map.get(key));
        if (map.size() == capacity) remove(tail.prev); // evict LRU
        insertToFront(new Node(key, value));
    }

    private void remove(Node node) {
        map.remove(node.key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insertToFront(Node node) {
        map.put(node.key, node);
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}
```

- get/put: O(1), Space: O(capacity)

**Trace (capacity=2):**

| Operation  | Cache state (MRU→LRU) | Returns |
|------------|----------------------|---------|
| put(1,1)   | [1:1]                | —       |
| put(2,2)   | [2:2]→[1:1]          | —       |
| get(1)     | [1:1]→[2:2]          | 1       |
| put(3,3)   | [3:3]→[1:1] (evicts 2)| —      |
| get(2)     | —                    | -1      |
| put(4,4)   | [4:4]→[3:3] (evicts 1)| —      |
| get(3)     | [3:3]→[4:4]          | 3       |
| get(4)     | [4:4]→[3:3]          | 4       |
