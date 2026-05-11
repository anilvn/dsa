
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> preorder = new ArrayList<>();

        if (root == null) return preorder;

        Stack<TreeNode> st = new Stack<>();
        st.push(root);

        while (!st.isEmpty()) {
            TreeNode node = st.pop();
            preorder.add(node.val);

            // Push right first (so left is processed first)
            if (node.right != null) {
                st.push(node.right);
            }

            if (node.left != null) {
                st.push(node.left);
            }
        }

        return preorder;
    }
}

----------------------------------
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        TreeNode node = root;

        while (true) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                if (stack.isEmpty()) {
                    break;
                }
                node = stack.pop();
                inorder.add(node.val);
                node = node.right;
            }
        }

        return inorder;
    }
}
-----------------------------------------------------

class Solution {

    public List<Integer> postorderTraversal(TreeNode root) {
        Stack<TreeNode> st1 = new Stack<>();
        Stack<TreeNode> st2 = new Stack<>();
        List<Integer> postOrder = new ArrayList<>();

        if (root == null) return postOrder;

        st1.push(root);

        // Step 1: Fill st2
        while (!st1.isEmpty()) {
            TreeNode node = st1.pop();
            st2.push(node);

            if (node.left != null) st1.push(node.left);
            if (node.right != null) st1.push(node.right);
        }

        // Step 2: Extract from st2 (reverse order)
        while (!st2.isEmpty()) {
            postOrder.add(st2.pop().val);
        }

        return postOrder;
    }
}

To check whether a binary tree is **balanced**, we rely on this rule:

👉 A binary tree is **height-balanced** if for every node,
`|height(left subtree) - height(right subtree)| ≤ 1`

---

## ✅ Approach 1: Brute Force (Naive)

### Idea

* For every node:

  * Compute height of left subtree
  * Compute height of right subtree
  * Check difference
  * Recursively check left & right

### ❌ Problem

* Height is recalculated again and again → inefficient

### ⏱ Time Complexity

`O(N^2)`

### Code (Java)

```java
class Solution {

    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;

        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        if (Math.abs(leftHeight - rightHeight) > 1) return false;

        return isBalanced(root.left) && isBalanced(root.right);
    }

    private int height(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }
}
```

---

## ✅ Approach 2: Optimized (Single Traversal) ⭐

### Idea

* While calculating height:

  * If subtree is unbalanced → return `-1`
  * Propagate failure upward immediately

👉 This avoids repeated height calculation

### ⏱ Time Complexity

`O(N)` (Best approach)

### Code (Java)

```java
class Solution {

    public boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }

    private int checkHeight(TreeNode node) {
        if (node == null) return 0;

        int left = checkHeight(node.left);
        if (left == -1) return -1;

        int right = checkHeight(node.right);
        if (right == -1) return -1;

        if (Math.abs(left - right) > 1) return -1;

        return 1 + Math.max(left, right);
    }
}
```

---

## ✅ Approach 3: Using Pair (Height + Balance)

### Idea

* Return two values:

  * height
  * isBalanced

### ⏱ Time Complexity

`O(N)`

### Code (Java)

```java
class Solution {

    static class Pair {
        int height;
        boolean isBalanced;

        Pair(int h, boolean b) {
            height = h;
            isBalanced = b;
        }
    }

    public boolean isBalanced(TreeNode root) {
        return helper(root).isBalanced;
    }

    private Pair helper(TreeNode node) {
        if (node == null) return new Pair(0, true);

        Pair left = helper(node.left);
        Pair right = helper(node.right);

        boolean balanced = left.isBalanced && right.isBalanced &&
                Math.abs(left.height - right.height) <= 1;

        int height = 1 + Math.max(left.height, right.height);

        return new Pair(height, balanced);
    }
}
```

---

## 🧠 Which One Should You Use?

| Approach             | Time  | Space | Use Case                     |
| -------------------- | ----- | ----- | ---------------------------- |
| Brute Force          | O(N²) | O(H)  | Learning/basic understanding |
| Optimized (-1 trick) | O(N)  | O(H)  | ✅ Best for interviews        |
| Pair Method          | O(N)  | O(H)  | Clean + extensible           |

---

## 💡 Interview Tip

If interviewer asks:

> "Can you optimize this?"

👉 Immediately move from brute force → **single traversal approach**

---

If you want, I can:

* Do **dry run step-by-step**
* Show **visual tree example**
* Convert to **iterative solution (stack-based)**




public class Solution {

    public int diameterOfBinaryTree(TreeNode root) {
        int[] diameter = new int[1]; // stores max diameter
        height(root, diameter);
        return diameter[0];
    }

    private int height(TreeNode node, int[] diameter) {
        // base case
        if (node == null) {
            return 0;
        }

        int lh = height(node.left, diameter);
        int rh = height(node.right, diameter);

        // update diameter
        diameter[0] = Math.max(diameter[0], lh + rh);

        // return height of current node
        return 1 + Math.max(lh, rh);
    }
}