

## 📒 Notes on This Question

### 1. **Concept**

This is a **Binary Search Tree (BST)** implementation exercise.
You need to:

* Insert elements (`put`)
* Search elements (`contains`)
* Traverse the tree in sorted order (`inOrderTraversal`).

---

### 2. **BST Rules**

* **Insert (`put`)**:

  * If the value is less than the current node → go left.
  * If the value is greater than the current node → go right.
  * If null → insert a new node.

* **Search (`contains`)**:

  * Compare with node’s value.
  * Go left if smaller, right if larger.
  * If found → return true, otherwise false.

* **In-order Traversal**:

  * Traverse left → visit root → traverse right.
  * Produces values in **sorted order**.

---

### 3. **Complexities**

* **`put(value)`**

  * Time: **O(h)** (h = tree height, worst case O(n), best case O(log n) for balanced tree)
  * Space: **O(1)** (ignoring recursion stack)

* **`contains(value)`**

  * Time: **O(h)** (same reason as `put`)
  * Space: **O(1)** (ignoring recursion stack)

* **`inOrderTraversal()`**

  * Time: **O(n)** (visit all nodes once)
  * Space: **O(n)** (result list + recursion stack in skewed case)

---

## ✅ Fixed Code with Complexity Notes

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

  static class BST  {

    private Node root;

    public BST() {
      this.root = null; // start with empty tree
    }

    /**
     * Insert a value into the BST.
     * Time Complexity: O(h) where h = height of tree (O(log n) for balanced, O(n) worst-case skewed)
     * Space Complexity: O(1) iterative, O(h) if recursion stack is considered
     */
    public void put(int value) {
      root = put(root, value);
    }

    private Node put(Node node, int value) {
      if (node == null) {
        Node newNode = new Node();
        newNode.val = value;
        return newNode;
      }
      if (value < node.val) {
        node.left = put(node.left, value);
      } else if (value > node.val) {
        node.right = put(node.right, value);
      }
      return node; // no duplicates inserted
    }

    /**
     * Search if a value exists in the BST.
     * Time Complexity: O(h) where h = height of tree
     * Space Complexity: O(1) iterative, O(h) recursive
     */
    public boolean contains(int value) {
      return contains(root, value);
    }

    private boolean contains(Node node, int value) {
      if (node == null) return false;
      if (node.val == value) return true;
      else if (value < node.val) return contains(node.left, value);
      else return contains(node.right, value);
    }

    /**
     * Perform in-order traversal of the BST (sorted order).
     * Time Complexity: O(n) where n = number of nodes
     * Space Complexity: O(n) for result list + O(h) recursion stack
     */
    public List<Integer> inOrderTraversal() {
      final ArrayList<Integer> acc = new ArrayList<>();
      inOrderTraversal(root, acc);
      return acc;
    }

    private void inOrderTraversal(Node node, List<Integer> acc) {
      if (node == null) return;
      inOrderTraversal(node.left, acc);
      acc.add(node.val);              // ✅ fix: add in between left and right
      inOrderTraversal(node.right, acc);
    }

    private static class Node {
      Integer val;
      Node left;
      Node right;
    }
  }

  public static void main(String[] args) {
    final BST searchTree = new BST();

    searchTree.put(3);
    searchTree.put(1);
    searchTree.put(2);
    searchTree.put(5);

    if(Arrays.asList(1,2,3,5).equals(searchTree.inOrderTraversal())
       && !searchTree.contains(0) 
       && searchTree.contains(1)
       && searchTree.contains(2)
       && searchTree.contains(3)
       && !searchTree.contains(4)
       && searchTree.contains(5)
       && !searchTree.contains(6)) {
      System.out.println("Pass");
    } else {
      System.out.println("Fail");
    }
  }
}
```

---

 Now, if you run this, the output will be:

```
Pass
```

---

