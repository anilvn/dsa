# Binary Search Tree (BST) — Java

## Node Structure

```java
class Node {
    int key;
    Node left, right;

    Node(int key) {
        this.key = key;
    }
}
```

---

## Insert

Recursive. Returns the updated subtree root.

```java
public Node insert(Node node, int key) {
    if (node == null) return new Node(key);

    if (key < node.key) node.left = insert(node.left, key);
    else if (key > node.key) node.right = insert(node.right, key);
    // duplicate keys are ignored

    return node;
}
```

**Logic:**
- Reached null → place new node here.
- `key < node.key` → recurse left.
- `key > node.key` → recurse right.

---

## Search

```java
public boolean search(Node node, int key) {
    if (node == null) return false;
    if (node.key == key) return true;
    return key < node.key ? search(node.left, key) : search(node.right, key);
}
```

**Time:** O(H) — O(log N) balanced, O(N) skewed.

---

## Delete

Three cases:

```java
public Node delete(Node node, int key) {
    if (node == null) return null;

    if (key < node.key) node.left = delete(node.left, key);
    else if (key > node.key) node.right = delete(node.right, key);
    else {
        if (node.left == null && node.right == null) return null;  // leaf
        if (node.left == null) return node.right;                  // one child
        if (node.right == null) return node.left;                  // one child

        // two children → replace with inorder successor
        Node succ = minNode(node.right);
        node.key = succ.key;
        node.right = delete(node.right, succ.key);
    }
    return node;
}
```

**Cases:**
1. **Leaf** → return null.
2. **One child** → return that child.
3. **Two children** → find inorder successor (leftmost node in right subtree), copy its key, delete the successor.

---

## Min & Max

```java
public Node minNode(Node node) {
    if (node.left == null) return node;
    return minNode(node.left);
}

public Node maxNode(Node node) {
    if (node.right == null) return node;
    return maxNode(node.right);
}
```

---

## Height

```java
public int height(Node node) {
    if (node == null) return 0;
    return 1 + Math.max(height(node.left), height(node.right));
}
```

---

## Traversals

All three are standard recursive DFS. In BST, **inorder always gives sorted output**.

```java
public void inorder(Node node) {      // Left → Root → Right  →  sorted order
    if (node != null) {
        inorder(node.left);
        System.out.print(node.key + " ");
        inorder(node.right);
    }
}

public void preorder(Node node) {     // Root → Left → Right
    if (node != null) {
        System.out.print(node.key + " ");
        preorder(node.left);
        preorder(node.right);
    }
}

public void postorder(Node node) {    // Left → Right → Root
    if (node != null) {
        postorder(node.left);
        postorder(node.right);
        System.out.print(node.key + " ");
    }
}
```

---

## Print In Range

Prints all keys between X and Y (inclusive).

```java
public void printInRange(Node node, int X, int Y) {
    if (node == null) return;

    if (node.key >= X && node.key <= Y) {
        printInRange(node.left, X, Y);
        System.out.print(node.key + " ");
        printInRange(node.right, X, Y);
    } else if (node.key > Y) {
        printInRange(node.left, X, Y);
    } else {
        printInRange(node.right, X, Y);
    }
}
```

---

## Validate BST

Uses min/max bounds passed down the recursion.

```java
public boolean isValidBST(Node node, Node min, Node max) {
    if (node == null) return true;
    if (min != null && node.key <= min.key) return false;
    if (max != null && node.key >= max.key) return false;
    return isValidBST(node.left, min, node) && isValidBST(node.right, node, max);
}

// Initial call
isValidBST(root, null, null);
```

---

## Convert Skewed BST to Balanced BST

```java
public void getInorder(Node node, ArrayList<Integer> list) {
    if (node == null) return;
    getInorder(node.left, list);
    list.add(node.key);
    getInorder(node.right, list);
}

public Node buildBalanced(ArrayList<Integer> list, int st, int end) {
    if (st > end) return null;
    int mid = (st + end) / 2;
    Node node = new Node(list.get(mid));
    node.left = buildBalanced(list, st, mid - 1);
    node.right = buildBalanced(list, mid + 1, end);
    return node;
}

public Node balanceBST(Node root) {
    ArrayList<Integer> inorder = new ArrayList<>();
    getInorder(root, inorder);
    return buildBalanced(inorder, 0, inorder.size() - 1);
}
```

**Idea:** Inorder gives sorted array → pick mid as root → recurse on halves.

---

## Time Complexities

| Operation | Balanced BST | Skewed BST |
|-----------|-------------|------------|
| Search    | O(log N)    | O(N)       |
| Insert    | O(log N)    | O(N)       |
| Delete    | O(log N)    | O(N)       |
| Inorder   | O(N)        | O(N)       |

---

## Key Properties

- Left subtree keys < node key < right subtree keys.
- No duplicate keys (standard BST).
- Inorder traversal → sorted sequence.
- Performance degrades to O(N) on skewed trees → use AVL or Red-Black Tree when balance matters.