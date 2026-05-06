# AVL Tree — Java

An AVL Tree is a self-balancing BST. Every node stores a **height**, and the tree auto-rebalances after insert/delete using rotations.

---

## Node Structure

```java
class Node {
    int key, height;
    Node left, right;

    Node(int key) {
        this.key = key;
        this.height = 1; // new node starts at height 1
    }
}
```

---

## Height & Balance Factor

```java
public int height(Node node) {
    return (node == null) ? 0 : node.height;
}

public int getBalance(Node node) {
    return (node == null) ? 0 : height(node.left) - height(node.right);
}

public void updateHeight(Node node) {
    if (node != null)
        node.height = 1 + Math.max(height(node.left), height(node.right));
}
```

**Balance Factor (BF) = height(left) − height(right)**

Valid range: `{-1, 0, 1}`. If `|BF| > 1`, a rotation is needed.

---

## Rotations

There are four imbalance cases, each fixed by one or two rotations.

### Right Rotate (fixes LL imbalance)

```
    y                x
   / \              / \
  x   T3    →    T1   y
 / \                  / \
T1  T2              T2  T3
```

```java
private Node rightRotate(Node y) {
    Node x = y.left;
    Node T2 = x.right;

    x.right = y;
    y.left = T2;

    updateHeight(y);
    updateHeight(x);

    return x; // x is the new root
}
```

### Left Rotate (fixes RR imbalance)

```
  x                  y
 / \                / \
T1  y      →      x   T3
   / \            / \
  T2 T3          T1  T2
```

```java
private Node leftRotate(Node x) {
    Node y = x.right;
    Node T2 = y.left;

    y.left = x;
    x.right = T2;

    updateHeight(x);
    updateHeight(y);

    return y; // y is the new root
}
```

### Four Rotation Cases

| Case | Condition | Fix |
|------|-----------|-----|
| LL   | BF > 1 and key < left child key | Right Rotate |
| RR   | BF < −1 and key > right child key | Left Rotate |
| LR   | BF > 1 and key > left child key | Left Rotate on left child, then Right Rotate |
| RL   | BF < −1 and key < right child key | Right Rotate on right child, then Left Rotate |

---

## Insert

```java
public Node insert(Node node, int key) {
    // Step 1: Standard BST insert
    if (node == null) return new Node(key);

    if (key < node.key) node.left = insert(node.left, key);
    else if (key > node.key) node.right = insert(node.right, key);
    else return node; // duplicates not allowed

    // Step 2: Update height
    updateHeight(node);

    // Step 3: Check balance and rotate if needed
    int balance = getBalance(node);

    if (balance > 1 && key < node.left.key) return rightRotate(node);           // LL
    if (balance < -1 && key > node.right.key) return leftRotate(node);          // RR
    if (balance > 1 && key > node.left.key) {                                   // LR
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }
    if (balance < -1 && key < node.right.key) {                                 // RL
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

    return node;
}
```

---

## Delete

```java
public Node delete(Node node, int key) {
    // Step 1: Standard BST delete
    if (node == null) return null;

    if (key < node.key) node.left = delete(node.left, key);
    else if (key > node.key) node.right = delete(node.right, key);
    else {
        if (node.left == null) return node.right;
        if (node.right == null) return node.left;

        // Two children → inorder successor
        Node succ = minNode(node.right);
        node.key = succ.key;
        node.right = delete(node.right, succ.key);
    }

    // Step 2: Update height
    updateHeight(node);

    // Step 3: Rebalance
    int balance = getBalance(node);

    if (balance > 1 && getBalance(node.left) >= 0) return rightRotate(node);    // LL
    if (balance > 1 && getBalance(node.left) < 0) {                             // LR
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }
    if (balance < -1 && getBalance(node.right) <= 0) return leftRotate(node);   // RR
    if (balance < -1 && getBalance(node.right) > 0) {                           // RL
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

    return node;
}

public Node minNode(Node node) {
    if (node.left == null) return node;
    return minNode(node.left);
}
```

> **Note:** In delete, balance case detection uses `getBalance()` on the child (not the inserted key), because we don't know which side caused the imbalance.

---

## Search

Identical to BST search — AVL maintains the BST property.

```java
public boolean search(Node node, int key) {
    if (node == null) return false;
    if (node.key == key) return true;
    return key < node.key ? search(node.left, key) : search(node.right, key);
}
```

---

## Traversals

Same as BST. Inorder still gives sorted order.

```java
public void inorder(Node node) {
    if (node != null) {
        inorder(node.left);
        System.out.print(node.key + " ");
        inorder(node.right);
    }
}
```

Preorder and postorder follow the same pattern.

---

## Validate AVL

```java
public boolean isAVL(Node node) {
    if (node == null) return true;
    if (Math.abs(getBalance(node)) > 1) return false;
    return isAVL(node.left) && isAVL(node.right);
}
```

---

## Time Complexities

| Operation | Complexity |
|-----------|------------|
| Search    | O(log N) — **guaranteed** |
| Insert    | O(log N) — **guaranteed** |
| Delete    | O(log N) — **guaranteed** |
| Space     | O(N) |

---

## BST vs AVL — Key Difference

| | BST | AVL |
|-|-----|-----|
| Balance | Not guaranteed | Always balanced |
| Worst case | O(N) skewed tree | O(log N) always |
| Extra storage | None | Height per node |
| Use when | Data is mostly sorted rarely | Frequent search/insert/delete |

---

## Rotation Quick Reference

```
LL → Right Rotate
RR → Left Rotate
LR → Left Rotate (left child) + Right Rotate
RL → Right Rotate (right child) + Left Rotate
```