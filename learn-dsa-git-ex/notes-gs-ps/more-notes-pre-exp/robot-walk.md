

## **Problem Restatement**

We must implement a function:

```java
public static Integer[] walk(String path)
```

* The robot starts at `(0,0)` (origin).
* Path string contains characters:

  * `'U'` → move **up** (y + 1)
  * `'D'` → move **down** (y - 1)
  * `'L'` → move **left** (x - 1)
  * `'R'` → move **right** (x + 1)
* Ignore all other characters.
* Return final position as an array: `{x, y}`

---

## **Example 1**

Input:

```text
path = "UUU"
```

Step-by-step:

* Start: (0,0)
* U → (0,1)
* U → (0,2)
* U → (0,3)

**Output:**

```text
[0, 3]
```

---

## **Example 2**

Input:

```text
path = "ULDR"
```

Step-by-step:

* Start: (0,0)
* U → (0,1)
* L → (-1,1)
* D → (-1,0)
* R → (0,0)

**Output:**

```text
[0, 0]
```

---

## **Analogy (Intuition)**

Think of this like playing a **video game with a joystick**:

* `U` moves the robot up, `D` down, `L` left, `R` right.
* You keep track of coordinates (x,y).
* Ignore nonsense input (like `"UP LEFT"` words).

👉 It’s like drawing a path on graph paper by following directions.

---

## **Brute Force Approach**

* Loop through every character in the string.
* Update `(x,y)` based on movement.
* Return final coordinates.

### **Code**

```java
import java.util.*;

public class Solution {

    /**
     * Brute Force Approach
     * Time Complexity: O(n) → one pass through the path string
     * Space Complexity: O(1) → only storing x, y
     */
    public static Integer[] walk(String path) {
        int x = 0, y = 0;

        for (char c : path.toCharArray()) {
            if (c == 'U') y++;
            else if (c == 'D') y--;
            else if (c == 'L') x--;
            else if (c == 'R') x++;
            // Ignore everything else
        }

        return new Integer[]{x, y};
    }

    public static boolean isEqual(Integer[] a, Integer[] b) {
        return Arrays.equals(a, b);
    }

    public static boolean pass() {
        boolean res = true;

        {
            String test = "UUU";
            Integer[] result = walk(test);
            res &= isEqual(result, new Integer[]{0, 3});
        }

        {
            String test = "ULDR";
            Integer[] result = walk(test);
            res &= isEqual(result, new Integer[]{0, 0});
        }

        {
            String test = "ULLLDUDUURLRLR";
            Integer[] result = walk(test);
            res &= isEqual(result, new Integer[]{-2, 2});
        }

        {
            String test = "UP LEFT 2xDOWN DOWN RIGHT RIGHT UP UP";
            Integer[] result = walk(test);
            res &= isEqual(result, new Integer[]{1, 1});
        }

        return res;
    }

    public static void main(String[] args) {
        if (pass()) {
            System.out.println("Pass");
        } else {
            System.out.println("Test failures");
        }
    }
}
```

---

## **Optimized Approach**

The brute force is already **O(n)** and efficient since we must read every character.
But optimization can be:

* **Filter only valid moves first** (remove noise).
* Process only `U`, `D`, `L`, `R`.

### **Code (Optimized Filtering)**

```java
import java.util.*;

public class Solution {

    /**
     * Optimized: Pre-filter valid moves
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static Integer[] walk(String path) {
        int x = 0, y = 0;

        for (char c : path.toCharArray()) {
            switch (c) {
                case 'U': y++; break;
                case 'D': y--; break;
                case 'L': x--; break;
                case 'R': x++; break;
                default: break; // ignore
            }
        }

        return new Integer[]{x, y};
    }

    public static boolean isEqual(Integer[] a, Integer[] b) {
        return Arrays.equals(a, b);
    }

    public static boolean pass() {
        boolean res = true;

        {
            String test = "UUU";
            Integer[] result = walk(test);
            res &= isEqual(result, new Integer[]{0, 3});
        }

        {
            String test = "ULDR";
            Integer[] result = walk(test);
            res &= isEqual(result, new Integer[]{0, 0});
        }

        {
            String test = "ULLLDUDUURLRLR";
            Integer[] result = walk(test);
            res &= isEqual(result, new Integer[]{-2, 2});
        }

        {
            String test = "UP LEFT 2xDOWN DOWN RIGHT RIGHT UP UP";
            Integer[] result = walk(test);
            res &= isEqual(result, new Integer[]{1, 1});
        }

        return res;
    }

    public static void main(String[] args) {
        if (pass()) {
            System.out.println("Pass");
        } else {
            System.out.println("Test failures");
        }
    }
}
```

---

✅ **Brute Force:** Simple iteration
✅ **Optimized:** Same time complexity but cleaner with `switch-case` filtering

---
