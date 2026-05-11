This is a classic linked list problem, and there are **multiple approaches**—each with different trade-offs. Let’s go through them clearly so you can choose the best one in interviews.

---

## ✅ 1. **Brute Force (Two Pass)**

### Idea:

* First pass → find length `L`
* Second pass → go to `(L - n)`th node and delete next

### Steps:

1. Traverse list → count nodes
2. Compute position to delete: `pos = L - n`
3. Traverse again to `pos`
4. Adjust pointers

### Code:

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    int length = 0;
    ListNode temp = head;

    // Step 1: Find length
    while (temp != null) {
        length++;
        temp = temp.next;
    }

    // If deleting head
    if (length == n) {
        return head.next;
    }

    // Step 2: Go to (length - n - 1)
    temp = head;
    for (int i = 1; i < length - n; i++) {
        temp = temp.next;
    }

    // Step 3: Delete node
    temp.next = temp.next.next;

    return head;
}
```

### Complexity:

* Time: **O(N) + O(N) = O(N)**
* Space: **O(1)**

---

## ✅ 2. **Optimal (One Pass – Two Pointers) ⭐ (Most Important)**

### Idea:

Use two pointers with a gap of `n`.

### Steps:

1. Move `fast` pointer `n` steps ahead
2. Move both `slow` and `fast` together
3. When `fast` reaches end → `slow` is before target

### Code:

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;

    ListNode fast = dummy;
    ListNode slow = dummy;

    // Move fast n+1 steps
    for (int i = 0; i <= n; i++) {
        fast = fast.next;
    }

    // Move both
    while (fast != null) {
        fast = fast.next;
        slow = slow.next;
    }

    // Delete node
    slow.next = slow.next.next;

    return dummy.next;
}
```

### Why dummy node?

Handles edge case:

* deleting head (like `n == length`)

### Complexity:

* Time: **O(N)**
* Space: **O(1)**

👉 **This is the BEST approach for interviews**

---

## ✅ 3. **Using Stack**

### Idea:

* Push all nodes into stack
* Pop `n` nodes → reach target
* Adjust previous node

### Code:

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    Stack<ListNode> stack = new Stack<>();
    ListNode temp = head;

    while (temp != null) {
        stack.push(temp);
        temp = temp.next;
    }

    // Pop n nodes
    for (int i = 0; i < n; i++) {
        temp = stack.pop();
    }

    // If deleting head
    if (stack.isEmpty()) {
        return head.next;
    }

    ListNode prev = stack.peek();
    prev.next = prev.next.next;

    return head;
}
```

### Complexity:

* Time: **O(N)**
* Space: **O(N)** ❌ (extra memory)

---

## ✅ 4. **Recursive Approach**

### Idea:

* Traverse till end
* Count nodes while returning
* Delete when count == n

### Code:

```java
int count = 0;

public ListNode removeNthFromEnd(ListNode head, int n) {
    return helper(head, n);
}

private ListNode helper(ListNode node, int n) {
    if (node == null) return null;

    node.next = helper(node.next, n);
    count++;

    if (count == n) {
        return node.next;
    }

    return node;
}
```

### Complexity:

* Time: **O(N)**
* Space: **O(N)** (recursion stack)

---

## 🔥 Summary (Interview View)

| Approach      | Time | Space | Notes                   |
| ------------- | ---- | ----- | ----------------------- |
| Two Pass      | O(N) | O(1)  | Simple but not optimal  |
| Two Pointer ⭐ | O(N) | O(1)  | **Best approach**       |
| Stack         | O(N) | O(N)  | Extra space             |
| Recursion     | O(N) | O(N)  | Elegant but not optimal |

---

## 💡 Interview Tip

If interviewer asks:

> "Can you do it in one pass?"

Immediately go for:
👉 **Two-pointer approach with dummy node**

---

If you want, I can:

* Walk through **dry run step-by-step (visual)**
* Show **edge cases that break naive solutions**
* Or give a **clean template you can memorize**

Just tell me 👍
Nice—this is another **must-know linked list pattern**. Just like before, there are multiple approaches, but only one is truly “interview optimal.”

---

# ✅ Problem: Check if Linked List is Palindrome

---

## ✅ 1. **Brute Force (Convert to Array/List)**

### Idea:

* Copy all node values into an array
* Use two pointers to check palindrome

### Code:

```java
public boolean isPalindrome(ListNode head) {
    List<Integer> list = new ArrayList<>();
    
    ListNode temp = head;
    while (temp != null) {
        list.add(temp.val);
        temp = temp.next;
    }

    int left = 0, right = list.size() - 1;
    while (left < right) {
        if (!list.get(left).equals(list.get(right))) {
            return false;
        }
        left++;
        right--;
    }

    return true;
}
```

### Complexity:

* Time: **O(N)**
* Space: **O(N)** ❌

---

## ✅ 2. **Using Stack**

### Idea:

* Push all elements into stack
* Traverse again and compare

### Code:

```java
public boolean isPalindrome(ListNode head) {
    Stack<Integer> stack = new Stack<>();
    
    ListNode temp = head;
    while (temp != null) {
        stack.push(temp.val);
        temp = temp.next;
    }

    temp = head;
    while (temp != null) {
        if (temp.val != stack.pop()) {
            return false;
        }
        temp = temp.next;
    }

    return true;
}
```

### Complexity:

* Time: **O(N)**
* Space: **O(N)** ❌

---

## ✅ 3. **Recursive Approach**

### Idea:

* Use recursion to go till end
* Compare while coming back

### Code:

```java
ListNode front;

public boolean isPalindrome(ListNode head) {
    front = head;
    return check(head);
}

private boolean check(ListNode node) {
    if (node == null) return true;

    if (!check(node.next)) return false;

    if (node.val != front.val) return false;

    front = front.next;
    return true;
}
```

### Complexity:

* Time: **O(N)**
* Space: **O(N)** (recursion stack)

---

## ✅ 4. **Optimal (Reverse Second Half) ⭐**

### Idea:

1. Find middle of list
2. Reverse second half
3. Compare first half & second half
4. (Optional) Restore list

---

### Code:

```java
public boolean isPalindrome(ListNode head) {
    if (head == null || head.next == null) return true;

    // Step 1: Find middle
    ListNode slow = head, fast = head;
    while (fast.next != null && fast.next.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }

    // Step 2: Reverse second half
    ListNode secondHalf = reverse(slow.next);

    // Step 3: Compare
    ListNode firstHalf = head;
    ListNode temp = secondHalf;

    while (temp != null) {
        if (firstHalf.val != temp.val) return false;
        firstHalf = firstHalf.next;
        temp = temp.next;
    }

    return true;
}

private ListNode reverse(ListNode head) {
    ListNode prev = null;
    while (head != null) {
        ListNode next = head.next;
        head.next = prev;
        prev = head;
        head = next;
    }
    return prev;
}
```

---

### 🔥 Important Detail:

* For **odd length**, middle element is ignored automatically
* For **even length**, halves split evenly

---

### Complexity:

* Time: **O(N)**
* Space: **O(1)** ✅

👉 **This is the BEST approach for interviews**

---

## 🔥 Summary

| Approach       | Time | Space | Notes        |
| -------------- | ---- | ----- | ------------ |
| Array/List     | O(N) | O(N)  | Easiest      |
| Stack          | O(N) | O(N)  | Similar idea |
| Recursion      | O(N) | O(N)  | Elegant      |
| Reverse Half ⭐ | O(N) | O(1)  | **Best**     |

---

## 💡 Interview Insight

If interviewer asks:

> “Can you do it without extra space?”

You must go for:
👉 **Reverse second half approach**

---

## ⚠️ Common Mistakes

* Forgetting to handle **odd length lists**
* Breaking list structure (if not restoring)
* Wrong middle detection condition

---

If you want next level clarity, I can:

* Show **step-by-step dry run (visual pointer movement)**
* Explain **why `fast.next.next` condition works**
* Give **pattern to reuse for multiple LL problems**

Just tell me 👍
