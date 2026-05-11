
# ✅ Definition Reminder

Middle = **(⌊n / 2⌋ + 1)** (1-based)

So:

* n = 5 → delete 3rd node
* n = 4 → delete 3rd node

---

# 🧠 Approach 1: Brute Force (Count + Traverse)

### Idea

1. Count total nodes `n`
2. Find middle index = `n/2`
3. Traverse to **(middle - 1)** node
4. Delete next node

### Code

```java
class Solution {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode deleteMiddle(ListNode head) {
        if (head == null || head.next == null) return null;

        // Step 1: Count nodes
        int n = 0;
        ListNode temp = head;
        while (temp != null) {
            n++;
            temp = temp.next;
        }

        // Step 2: Find middle index (0-based: n/2)
        int mid = n / 2;

        // Step 3: Traverse to node before middle
        temp = head;
        for (int i = 0; i < mid - 1; i++) {
            temp = temp.next;
        }

        // Step 4: Delete middle node
        temp.next = temp.next.next;

        return head;
    }
}
```

### Complexity

* Time: **O(n) + O(n) = O(n)**
* Space: **O(1)**

---

# ⚡ Approach 2: Optimal (Slow & Fast Pointer)

### Idea (Best Approach)

* `slow` moves 1 step
* `fast` moves 2 steps
* When `fast` reaches end → `slow` is at middle
* Keep `prev` to delete node

---

### Code

```java
class Solution {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode deleteMiddle(ListNode head) {
        if (head == null || head.next == null) return null;

        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // Delete middle node
        prev.next = slow.next;

        return head;
    }
}
```

---

### 🔥 Why this works

When `fast` jumps 2 steps:

* `slow` reaches **middle exactly**
* No need to count length

---

### Complexity

* Time: **O(n)**
* Space: **O(1)**
  ✅ Best possible

---

# 🧪 Dry Run (Important)

### Input

```
1 → 2 → 3 → 4 → 5
```

| Step  | slow | fast |
| ----- | ---- | ---- |
| start | 1    | 1    |
| 1     | 2    | 3    |
| 2     | 3    | 5    |
| stop  | 3    | null |

👉 `slow = 3` → delete it

---

# ⚠️ Edge Cases

| Case    | Output     |
| ------- | ---------- |
| 1 node  | null       |
| 2 nodes | remove 2nd |
| 3 nodes | remove 2nd |

---

# 🧠 Approach 3: One Pass with Counter Trick (Less Common)

### Idea

* Move fast pointer
* Move slow every 2 steps (manual control)

Not preferred—more complex, less readable.

---

# 🏆 Final Recommendation

Use:

👉 **Slow & Fast Pointer (Approach 2)**
Because:

* Single pass
* Clean logic
* Interview favorite

---

# 💡 Interview Tip

If interviewer asks:

> “Why not counting?”

Say:

> “Counting takes two passes. Fast-slow solves it in one pass with same space.”

---

If you want, I can:

* Show **step-by-step pointer movement animation**
* Convert this into **recursive version**
* Or give **LeetCode-style driver + test cases**

Just tell 👍
