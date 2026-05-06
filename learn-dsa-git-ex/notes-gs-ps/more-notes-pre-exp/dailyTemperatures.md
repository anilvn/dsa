

## 🔹 Problem Statement

Given an array `arr` of size `n`, for each element find the **Next Greater Element** (NGE) to its right.

* The Next Greater Element of `arr[i]` is the first element to the right of `i` which is greater than `arr[i]`.
* If no such element exists, return `-1` for that index.

---

## 🔹 Example

```
Input:  arr = [4, 5, 2, 25]
Output: [5, 25, 25, -1]

Explanation:
- Next greater for 4 is 5
- Next greater for 5 is 25
- Next greater for 2 is 25
- Next greater for 25 does not exist → -1
```

---

## 🔹 Approach (Monotonic Stack)

1. Use a **stack** to store elements’ indices while traversing from **right to left**.
2. Maintain a **monotonic decreasing stack**:

   * While stack is not empty and current element ≥ stack’s top → pop.
   * If stack is empty after popping → no greater element → `-1`.
   * Else → top of stack is the next greater element.
3. Push current element into stack.

This ensures each element is pushed and popped at most once → **O(n) time** and **O(n) space**.

---

## 🔹 Java Implementation

```java
import java.util.*;

public class NextGreaterElement {
    
    public static int[] nextGreaterElements(int[] arr) {
        int n = arr.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();  // will store elements (not indices)

        // Traverse from right to left
        for (int i = n - 1; i >= 0; i--) {
            // Pop smaller or equal elements
            while (!stack.isEmpty() && stack.peek() <= arr[i]) {
                stack.pop();
            }

            // If stack empty → no greater element
            result[i] = stack.isEmpty() ? -1 : stack.peek();

            // Push current element to stack
            stack.push(arr[i]);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = {4, 5, 2, 25};
        int[] ans = nextGreaterElements(arr);

        System.out.println("Next Greater Elements:");
        System.out.println(Arrays.toString(ans)); 
        // Output: [5, 25, 25, -1]
    }
}
```

---

## 🔹 Complexity

* **Time:** O(n) → each element pushed & popped once.
* **Space:** O(n) → stack + result array.

---

<!-- Do you want me to also cover the **circular version** of Next Greater Element (like LeetCode 503), where the array is considered circular? -->

<br/><br/><br/>

Great twist! 🚀
You now want the **Next Greater Element Index** problem instead of the element itself.

---

## 🔹 Problem Statement

Given an array `arr`, for each element find the **index** of the **Next Greater Element (NGE)** to its right.

* If no such index exists, return `-1`.

---

## 🔹 Example

```
Input:  arr = [4, 5, 2, 25]
Output: [1, 3, 3, -1]

Explanation:
- For arr[0] = 4 → NGE is 5 at index 1
- For arr[1] = 5 → NGE is 25 at index 3
- For arr[2] = 2 → NGE is 25 at index 3
- For arr[3] = 25 → no NGE → -1
```

---

## 🔹 Approach (Monotonic Stack)

Instead of storing **elements** in the stack, we store **indices**.

1. Traverse from **right to left**.
2. Maintain a **monotonic decreasing stack** of indices.
3. While stack not empty and `arr[stack.peek()] <= arr[i]`, pop.
4. If stack empty → no greater index → `-1`.
   Else → `stack.peek()` is the index of NGE.
5. Push current index into stack.

---

## 🔹 Java Code

```java
import java.util.*;

public class NextGreaterElementIndex {

    public static int[] nextGreaterElementIndex(int[] arr) {
        int n = arr.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();  // will store indices

        for (int i = n - 1; i >= 0; i--) {
            // Pop smaller or equal elements
            while (!stack.isEmpty() && arr[stack.peek()] <= arr[i]) {
                stack.pop();
            }

            // Store index of next greater element
            result[i] = stack.isEmpty() ? -1 : stack.peek();

            // Push current index
            stack.push(i);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {4, 5, 2, 25};
        int[] ans = nextGreaterElementIndex(arr);

        System.out.println("Next Greater Element Indexes:");
        System.out.println(Arrays.toString(ans)); 
        // Output: [1, 3, 3, -1]
    }
}
```

---

## 🔹 Complexity

* **Time:** O(n) (push & pop each index once).
* **Space:** O(n) (stack + result).

---
<!-- 
👉 Do you want me to also show how to **convert this index version back into the element version** easily (just mapping `arr[result[i]]` when result\[i] ≠ -1)?
 -->



<br/><br/><br/>

## dailyTemperatures

```java
// class Solution {
//     public int[] dailyTemperatures(int[] temperatures) {
        
//     }
// }


import java.util.*;

public class Solution {

    public static int[] dailyTemperatures(int[] arr) {
        int n = arr.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();  // will store indices

        for (int i = n - 1; i >= 0; i--) {
            // Pop smaller or equal elements
            while (!stack.isEmpty() && arr[stack.peek()] <= arr[i]) {
                stack.pop();
            }

            // Store index of next greater element
            result[i] = stack.isEmpty() ? 0 : stack.peek() - i;

            // Push current index
            stack.push(i);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {4, 5, 2, 25};
        int[] ans = dailyTemperatures(arr);

        System.out.println("Next Greater Element Indexes:");
        System.out.println(Arrays.toString(ans)); 
        // Output: [1, 3, 3, -1]
    }
}
```