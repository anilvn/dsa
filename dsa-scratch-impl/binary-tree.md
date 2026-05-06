# Binary Tree -- Implementing Operations from Scratch in Java

## How to Read This Document

Each section introduces one concept or operation. Read in order. Code is clean and production-quality with brief inline comments. Time complexity follows each operation.

---

## Step 1 -- Node Structure and Tree Building

**Problem:** Represent a binary tree node and construct a tree from a preorder sequence.

```java
static class Node {
    int data;
    Node left;
    Node right;

    Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
```

**Building from preorder array** -- `-1` signals a null (absent) child.

```java
static class BinaryTree {
    static int idx = -1; // global index advances through the array

    public static Node buildTree(int[] nodes) {
        idx++;
        if (nodes[idx] == -1) return null; // null marker -- stop recursion

        Node newNode = new Node(nodes[idx]);
        newNode.left  = buildTree(nodes);  // recurse left subtree
        newNode.right = buildTree(nodes);  // recurse right subtree
        return newNode;
    }
}

// Usage
int[] nodes = {1, 2, 4, -1, -1, 5, -1, -1, 3, -1, 6, -1, -1};
Node root = BinaryTree.buildTree(nodes);
```

**How it works:** Preorder means root is processed first, then left, then right. `-1` entries collapse back immediately, giving the recursive structure its shape.

| Operation | Time Complexity | Notes                          |
|-----------|-----------------|--------------------------------|
| buildTree | O(n)            | Each node visited exactly once |

---

## Step 2 -- Tree Traversals

**Problem:** Visit all nodes in a defined order. Three classic DFS orders + BFS level-order.

### Preorder (Root, Left, Right)

```java
public static void preorder(Node root) {
    if (root == null) return;
    System.out.print(root.data + " "); // process root first
    preorder(root.left);
    preorder(root.right);
}
```

### Inorder (Left, Root, Right)

```java
public static void inorder(Node root) {
    if (root == null) return;
    inorder(root.left);
    System.out.print(root.data + " "); // process root between subtrees
    inorder(root.right);
}
```

**Key insight:** Inorder traversal of a BST produces elements in sorted order.

### Postorder (Left, Right, Root)

```java
public static void postorder(Node root) {
    if (root == null) return;
    postorder(root.left);
    postorder(root.right);
    System.out.print(root.data + " "); // process root last
}
```

### Level Order (BFS)

```java
public static void levelOrder(Node root) {
    if (root == null) return;

    Queue<Node> q = new LinkedList<>();
    q.add(root);
    q.add(null); // null acts as level separator

    while (!q.isEmpty()) {
        Node curr = q.remove();
        if (curr == null) {
            System.out.println(); // newline at end of each level
            if (q.isEmpty()) break;
            q.add(null);          // mark end of next level
        } else {
            System.out.print(curr.data + " ");
            if (curr.left  != null) q.add(curr.left);
            if (curr.right != null) q.add(curr.right);
        }
    }
}
```

| Traversal   | Time Complexity | Space Complexity | Notes                          |
|-------------|-----------------|------------------|--------------------------------|
| Preorder    | O(n)            | O(h)             | h = height of tree (call stack)|
| Inorder     | O(n)            | O(h)             | Sorted output on BST           |
| Postorder   | O(n)            | O(h)             | Used for deletion              |
| Level Order | O(n)            | O(w)             | w = max width of tree          |

---

## Step 3 -- Height of Tree

**Problem:** Find the number of edges (or nodes) on the longest path from root to a leaf.

```java
public static int height(Node root) {
    if (root == null) return 0;
    int leftHeight  = height(root.left);
    int rightHeight = height(root.right);
    return Math.max(leftHeight, rightHeight) + 1; // +1 counts current node
}
```

| Operation | Time Complexity | Space Complexity | Notes                    |
|-----------|-----------------|------------------|--------------------------|
| height    | O(n)            | O(h)             | Must visit every subtree |

**Edge case:** Returns 0 for null (empty tree). A single node returns height 1.

---

## Step 4 -- Count of Nodes

**Problem:** Count total nodes in the tree.

```java
public static int countOfNodes(Node root) {
    if (root == null) return 0;
    int leftNodes  = countOfNodes(root.left);
    int rightNodes = countOfNodes(root.right);
    return leftNodes + rightNodes + 1; // +1 for current node
}
```

| Operation    | Time Complexity | Notes                |
|--------------|-----------------|----------------------|
| countOfNodes | O(n)            | Visits all n nodes   |

---

## Step 5 -- Sum of Nodes

**Problem:** Sum all node values in the tree.

```java
public static int sumOfNodes(Node root) {
    if (root == null) return 0;
    int leftSum  = sumOfNodes(root.left);
    int rightSum = sumOfNodes(root.right);
    return leftSum + rightSum + root.data;
}
```

| Operation  | Time Complexity | Notes              |
|------------|-----------------|--------------------|
| sumOfNodes | O(n)            | Visits all n nodes |

---

## Step 6 -- Diameter of Tree

**Problem:** Find the longest path between any two nodes in the tree (path does not need to pass through root).

### Approach 1 -- O(n^2)

Straightforward but recomputes height at every node.

```java
public static int diameter(Node root) {
    if (root == null) return 0;

    // path through root: sum of left and right heights
    int diam1 = height(root.left) + height(root.right) + 1;

    // path entirely in left or right subtree
    int diam2 = diameter(root.left);
    int diam3 = diameter(root.right);

    return Math.max(diam1, Math.max(diam2, diam3));
}
```

**Why O(n^2)?** `height()` is called for every node in `diameter()`, each costing O(n).

### Approach 2 -- O(n) using TreeInfo

Compute height and diameter together in a single pass.

```java
static class TreeInfo {
    int height;
    int diam;

    TreeInfo(int height, int diam) {
        this.height = height;
        this.diam   = diam;
    }
}

public static TreeInfo diameter(Node root) {
    if (root == null) return new TreeInfo(0, 0);

    TreeInfo leftTI  = diameter(root.left);
    TreeInfo rightTI = diameter(root.right);

    int myHeight = Math.max(leftTI.height, rightTI.height) + 1;

    // diameter candidates: through root, or entirely in a subtree
    int diam1 = leftTI.height + rightTI.height + 1;
    int diam2 = leftTI.diam;
    int diam3 = rightTI.diam;

    int myDiam = Math.max(diam1, Math.max(diam2, diam3));

    return new TreeInfo(myHeight, myDiam);
}
```

**Call:** `diameter(root).diam`

| Approach     | Time Complexity | Space Complexity | Notes                           |
|--------------|-----------------|------------------|---------------------------------|
| O(n^2) naive | O(n^2)          | O(h)             | height() recomputed at each node|
| O(n) TreeInfo| O(n)            | O(h)             | Height and diameter in one pass |

---

## Step 7 -- Subtree Check

**Problem:** Determine whether `subRoot` is a subtree of `root`.

```java
// Check if two trees are structurally identical with same values
public boolean isIdentical(TreeNode root, TreeNode subRoot) {
    if (subRoot == null && root == null) return true;
    if (root == null || subRoot == null) return false;

    if (root.val == subRoot.val) {
        return isIdentical(root.left,  subRoot.left)
            && isIdentical(root.right, subRoot.right);
    }
    return false;
}

// Check if subRoot matches any subtree rooted at any node of root
public boolean isSubtree(TreeNode root, TreeNode subRoot) {
    if (subRoot == null) return true;  // empty tree is always a subtree
    if (root == null)    return false;

    if (isIdentical(root, subRoot)) return true;

    return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
}
```

| Operation  | Time Complexity | Notes                                    |
|------------|-----------------|------------------------------------------|
| isSubtree  | O(m * n)        | m = nodes in root, n = nodes in subRoot  |
| isIdentical| O(n)            | Linear in size of smaller tree           |

**Edge cases:** `subRoot == null` returns true by convention (empty tree is a subtree of everything).

---

## Complete Usage Example

```java
public static void main(String[] args) {
    int[] nodes = {1, 2, 4, -1, -1, 5, -1, -1, 3, -1, 6, -1, -1};
    Node root = BinaryTree.buildTree(nodes);

    System.out.print("Preorder:  "); preorder(root);   System.out.println();
    System.out.print("Inorder:   "); inorder(root);    System.out.println();
    System.out.print("Postorder: "); postorder(root);  System.out.println();
    System.out.println("Level Order:");  levelOrder(root);

    System.out.println("Height:     " + height(root));
    System.out.println("Node Count: " + countOfNodes(root));
    System.out.println("Node Sum:   " + sumOfNodes(root));
    System.out.println("Diameter:   " + diameter(root).diam);
}
```

---

## Summary -- Operation Complexity Table

| Operation       | Time Complexity | Space Complexity | Notes                                  |
|-----------------|-----------------|------------------|----------------------------------------|
| buildTree       | O(n)            | O(n)             | One node created per non-null entry    |
| preorder        | O(n)            | O(h)             | DFS, root first                        |
| inorder         | O(n)            | O(h)             | DFS, sorted on BST                     |
| postorder       | O(n)            | O(h)             | DFS, root last                         |
| levelOrder      | O(n)            | O(w)             | BFS, uses queue                        |
| height          | O(n)            | O(h)             | Visits every node                      |
| countOfNodes    | O(n)            | O(h)             | Visits every node                      |
| sumOfNodes      | O(n)            | O(h)             | Visits every node                      |
| diameter (naive)| O(n^2)          | O(h)             | height() recomputed per node           |
| diameter (O(n)) | O(n)            | O(h)             | TreeInfo carries both height and diam  |
| isSubtree       | O(m * n)        | O(h)             | isIdentical called at each of m nodes  |

h = height of tree, w = max width of tree, m/n = node counts of respective trees

---

## Interview Cheat Sheet

- Preorder/Inorder/Postorder all have O(n) time and O(h) space due to the call stack.
- Height and diameter share the same recursive skeleton -- combine them via a wrapper class to avoid redundant traversals.
- For diameter: the path through a node = leftHeight + rightHeight + 1. The answer is the max over all nodes.
- Inorder of BST is sorted -- a common interview constraint to exploit.
- Level order needs a sentinel (null) or explicit size-per-level counter to separate levels.

---

## Practice Problems

- Find the lowest common ancestor of two nodes
- Check if a binary tree is balanced (height difference <= 1 at every node)
- Print all root-to-leaf paths
- Mirror/invert a binary tree
- Check if two trees are identical
