That’s a much better way to study — clean code + separate notes 👍

---

# ✅ Clean Code (Minimal Comments)

```java
class Solution {
    int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        helper(root);
        return maxSum;
    }

    private int helper(TreeNode node) {
        if (node == null) return 0;

        int left = Math.max(0, helper(node.left));
        int right = Math.max(0, helper(node.right));

        int currentPath = node.val + left + right;
        maxSum = Math.max(maxSum, currentPath);

        return node.val + Math.max(left, right);
    }
}
```

---

# 🧾 Notes (Concept Explanation)

### 1. Two different things happen at every node

**(A) Current Path (for global max)**

```text
node.val + left + right
```

* This forms a **V-shape**
* Can include both children
* Used only to update `maxSum`

---

**(B) Return Path (to parent)**

```text
node.val + max(left, right)
```

* Only **one side allowed**
* Because path cannot split upward

---

### 2. Why ignore negative values?

```text
Math.max(0, child)
```

* If a subtree gives negative sum → it reduces total
* So we **completely discard it**

---

### 3. Global max behavior

* Initially = `-∞`
* Updated at every node
* Can be:

  * a single node
  * a subtree
  * a full V-shaped path

---

### 4. Important Rule

> A valid path is a **continuous line**, not a fork

That’s why:

* We **consider both sides** for max
* But **return only one side**

---

# 🔍 Dry Run Test Cases

---

## ✅ Test Case 1

```
      -10
      /  \
     9   20
        /  \
      -15   7
```

### Step Flow

| Node | Left | Right | Current Path | maxSum | Return |
| ---- | ---- | ----- | ------------ | ------ | ------ |
| -15  | 0    | 0     | -15          | -15    | -15    |
| 7    | 0    | 0     | 7            | 7      | 7      |
| 20   | 0    | 7     | 27           | 27     | 27     |
| 9    | 0    | 0     | 9            | 27     | 9      |
| -10  | 9    | 27    | 26           | 27     | 17     |

👉 **Answer = 27**

---

## ✅ Test Case 2 (All Negative)

```
     -3
    /  \
  -2   -1
```

| Node | Current Path | maxSum |
| ---- | ------------ | ------ |
| -2   | -2           | -2     |
| -1   | -1           | -1     |
| -3   | -3           | -1     |

👉 **Answer = -1** (largest single node)

---

## ✅ Test Case 3 (Best Path NOT through root)

```
      -10
      /
     50
```

| Node | Current Path | maxSum |
| ---- | ------------ | ------ |
| 50   | 50           | 50     |
| -10  | 40           | 50     |

👉 **Answer = 50**

---

## ✅ Test Case 4 (Perfect V-shape)

```
      1
     / \
    2   3
```

| Node | Left | Right | Current Path | maxSum |
| ---- | ---- | ----- | ------------ | ------ |
| 2    | 0    | 0     | 2            | 2      |
| 3    | 0    | 0     | 3            | 3      |
| 1    | 2    | 3     | 6            | 6      |

👉 **Answer = 6**

---

# 🧠 Final Mental Model

Think at each node:

* “What is the best path I can RETURN upward?” → single branch
* “What is the best path I can FORM here?” → both branches

---

If you want next:

* I can show **step-by-step recursion stack visualization**
* Or give a **trick to derive this in interviews without memorizing**
