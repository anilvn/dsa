# Stack — Notes

---

## 1. Valid Parentheses

Push the **expected closing bracket** when you see an opener. On a closer, check if it matches the stack top.

```java
public static boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();
    for (char c : s.toCharArray()) {
        if      (c == '(') stack.push(')');
        else if (c == '{') stack.push('}');
        else if (c == '[') stack.push(']');
        else if (stack.isEmpty() || stack.pop() != c) return false;
    }
    return stack.isEmpty();
}
```

- Time: O(n), Space: O(n)

---

## 2. Next Greater Element II (Circular Array)

For each element in a **circular** array, find the next greater element (wrap around if needed). Default `-1` if none exists.

**Key tricks:**
- Traverse `2n-1` down to `0`, use `i % n` for the circular index — simulates two passes without duplicating the array.
- Monotonic decreasing stack of indices; pop when current element is greater than `nums[stack.peek()]`.

```java
public int[] nextGreaterElements(int[] nums) {
    int n = nums.length;
    int[] ans = new int[n];
    Arrays.fill(ans, -1);
    Stack<Integer> stack = new Stack<>(); // stores indices

    for (int i = 2 * n - 1; i >= 0; i--) {
        int idx = i % n;
        while (!stack.isEmpty() && nums[stack.peek()] <= nums[idx])
            stack.pop();
        if (!stack.isEmpty()) ans[idx] = nums[stack.peek()];
        stack.push(idx);
    }
    return ans;
}
```

`[1,2,1]` → `[2,-1,2]` | `[3,8,4,1,2]` → `[8,-1,8,2,3]`

- Time: O(n), Space: O(n)

---

## 3. Largest Rectangle in Histogram

For each bar `i`, the max rectangle it can form extends left until a shorter bar and right until a shorter bar.

**Approach 1 — NSL/NSR (two passes):**

Precompute Nearest Smaller to Left (`left[]`) and Nearest Smaller to Right (`right[]`) using a stack each.  
Then `area[i] = heights[i] * (right[i] - left[i] - 1)`.

```java
public static int largestRectangleArea(int[] heights) {
    int n = heights.length;
    int[] left = new int[n], right = new int[n];
    Stack<Integer> st = new Stack<>();

    // NSL
    for (int i = 0; i < n; i++) {
        while (!st.isEmpty() && heights[st.peek()] >= heights[i]) st.pop();
        left[i] = st.isEmpty() ? -1 : st.peek();
        st.push(i);
    }

    // NSR
    st.clear();
    for (int i = n - 1; i >= 0; i--) {
        while (!st.isEmpty() && heights[st.peek()] >= heights[i]) st.pop();
        right[i] = st.isEmpty() ? n : st.peek();
        st.push(i);
    }

    int maxArea = 0;
    for (int i = 0; i < n; i++)
        maxArea = Math.max(maxArea, heights[i] * (right[i] - left[i] - 1));
    return maxArea;
}
```

**Approach 2 — Single pass (sentinel):**

Push indices in increasing height order. When a shorter bar is found, pop and compute area. A sentinel `0` at the end flushes remaining bars.  
Width = `i - stack.peek() - 1` (distance between the current bar and the new stack top after popping).

```java
public static int largestRectangleArea(int[] heights) {
    Stack<Integer> stack = new Stack<>();
    int maxArea = 0, n = heights.length;

    for (int i = 0; i <= n; i++) {
        int h = (i == n) ? 0 : heights[i]; // sentinel 0 flushes stack
        while (!stack.isEmpty() && h < heights[stack.peek()]) {
            int height = heights[stack.pop()];
            int width  = stack.isEmpty() ? i : i - stack.peek() - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        stack.push(i);
    }
    return maxArea;
}
```

Dry run `[2,1,5,6,2,3]`:
- NSL = `[-1,-1,1,2,1,4]`, NSR = `[1,6,4,4,6,6]`
- Areas = `[2,6,10,6,8,3]` → max = **10**

- Time: O(n), Space: O(n)
