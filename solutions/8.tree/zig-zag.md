The **Zig-Zag Traversal** (also called *spiral order traversal*) of a binary tree means visiting nodes level by level, but **alternating direction at each level**:

* Level 0 → Left → Right
* Level 1 → Right → Left
* Level 2 → Left → Right
* and so on…

---

## ✅ Approach 1: Using Queue + Direction Flag (Most Common)

### 🔹 Idea

* Perform **level order traversal (BFS)** using a queue.
* Maintain a boolean flag `leftToRight`.
* For each level:

  * If `leftToRight == true` → insert normally.
  * Else → insert in reverse order.

---

### 💻 Code (Java - Minimal Comments)

```java
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        boolean leftToRight = true;

        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> level = new ArrayList<>(Collections.nCopies(size, 0));

            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();

                int index = leftToRight ? i : size - 1 - i;
                level.set(index, node.val);

                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }

            result.add(level);
            leftToRight = !leftToRight;
        }

        return result;
    }
}
```

---

### 🧠 Notes (Important Understanding)

* We avoid reversing by directly placing elements at correct indices.
* `index = size - 1 - i` handles reverse order efficiently.
* Time Complexity: **O(N)**
* Space Complexity: **O(N)**

---

## ✅ Approach 2: Using Two Stacks

### 🔹 Idea

* Use two stacks:

  * `s1` → Left to Right
  * `s2` → Right to Left
* Alternate pushing children in reverse order.

---

### 💻 Code (Java)

```java
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Stack<TreeNode> s1 = new Stack<>();
        Stack<TreeNode> s2 = new Stack<>();

        s1.push(root);

        while (!s1.isEmpty() || !s2.isEmpty()) {
            List<Integer> level = new ArrayList<>();

            while (!s1.isEmpty()) {
                TreeNode node = s1.pop();
                level.add(node.val);

                if (node.left != null) s2.push(node.left);
                if (node.right != null) s2.push(node.right);
            }

            if (!level.isEmpty()) result.add(level);
            level = new ArrayList<>();

            while (!s2.isEmpty()) {
                TreeNode node = s2.pop();
                level.add(node.val);

                if (node.right != null) s1.push(node.right);
                if (node.left != null) s1.push(node.left);
            }

            if (!level.isEmpty()) result.add(level);
        }

        return result;
    }
}
```

---

### 🧠 Notes

* Order of pushing children is the key trick.
* Slightly less intuitive than BFS method.

---

## ✅ Approach 3: Deque (Best Clean Approach)

### 🔹 Idea

* Use **Deque (Double-ended queue)**.
* Poll from front or back depending on direction.

---

### 💻 Code

```java
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Deque<TreeNode> dq = new LinkedList<>();
        dq.offer(root);
        boolean leftToRight = true;

        while (!dq.isEmpty()) {
            int size = dq.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                if (leftToRight) {
                    TreeNode node = dq.pollFirst();
                    level.add(node.val);

                    if (node.left != null) dq.offerLast(node.left);
                    if (node.right != null) dq.offerLast(node.right);
                } else {
                    TreeNode node = dq.pollLast();
                    level.add(node.val);

                    if (node.right != null) dq.offerFirst(node.right);
                    if (node.left != null) dq.offerFirst(node.left);
                }
            }

            result.add(level);
            leftToRight = !leftToRight;
        }

        return result;
    }
}
```

---

## 🔥 Which One Should You Use?

| Approach     | Best For                   |
| ------------ | -------------------------- |
| Queue + Flag | Interviews (most expected) |
| Two Stacks   | Concept clarity            |
| Deque        | Clean + efficient          |

---

## 🎯 Interview Tip

If asked:

* Start with **BFS + flag**
* Mention: “We can optimize by avoiding reverse using index placement”

---

If you want, I can:

* Do a **dry run step-by-step**
* Show **visual tree traversal**
* Convert this to **recursive version (rare but interesting)**



Here’s a **clear dry run (table format)** for the **Deque (Approach 3)** so you can see exactly how elements move.

---

## 🌳 Example Tree

```
        1
       / \
      2   3
     / \ / \
    4  5 6  7
```

---

## 🔁 Initial State

* Deque: `[1]`
* `leftToRight = true`

---

## 📊 Dry Run Table

| Level  | Direction  | Deque Before  | Operation          | Nodes Added to Level   | Deque After  |
| ------ | ---------- | ------------- | ------------------ | ---------------------- | ------------ |
| 0      | L → R      | [1]           | pollFirst → 1      | [1]                    | [2, 3]       |
|        |            |               | add children (L→R) |                        |              |
| ------ | ---------- | ------------- | ----------         | ---------------------- | ------------ |
| 1      | R → L      | [2, 3]        | pollLast → 3       | [3]                    | [7, 6,2]    |
|        |            |               | add children (R→L) |                        |              |
|        |            | [2, 7, 6]     | pollLast → 2       | [3, 2]                 | [5, 4, 7, 6] |
| ------ | ---------- | ------------- | ----------         | ---------------------- | ------------ |
| 2      | L → R      | [5, 4, 7, 6]  | pollFirst → 5      | [5]                    | [4, 7, 6]    |
|        |            |               | pollFirst → 4      | [5, 4]                 | [7, 6]       |
|        |            |               | pollFirst → 7      | [5, 4, 7]              | [6]          |
|        |            |               | pollFirst → 6      | [5, 4, 7, 6]           | []           |

---

## ✅ Final Output

```
[
 [1],
 [3, 2],
 [5, 4, 7, 6]
]
```

---

## 🧠 Key Observations (Very Important)

* **Left → Right**

  * `pollFirst()`
  * children added: `left → right` using `offerLast()`

* **Right → Left**

  * `pollLast()`
  * children added: `right → left` using `offerFirst()`

---

## ⚡ Mental Shortcut

Think like this:

* 👉 Normal direction → behave like **queue**
* 👉 Reverse direction → behave like **stack from other side**

---

## 🔥 Common Mistake

If you:

* add children in wrong order → traversal breaks
* mix `offerFirst` / `offerLast` incorrectly → wrong zigzag

---

If you want next:

* I can show **step-by-step pointer movement visualization**
* Or give a **1-line memory trick to never forget this**
