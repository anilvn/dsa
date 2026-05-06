```java
/*
** Instructions to candidate.
**  1) Given an array of non-negative integers representing the elevations
**     from the vertical cross section of a range of hills, determine how
**     many units of snow could be captured between the hills. 
**
**     See the example array and elevation map below.
**                                 ___
**             ___                |   |        ___
**            |   |        ___    |   |___    |   |
**         ___|   |    ___|   |   |   |   |   |   |
**     ___|___|___|___|___|___|___|___|___|___|___|___
**     {0,  1,  3,  0,  1,  2,  0,  4,  2,  0,  3,  0}
**                                 ___
**             ___                |   |        ___
**            |   | *   *  _*_  * |   |_*_  * |   |
**         ___|   | *  _*_|   | * |   |   | * |   |
**     ___|___|___|_*_|___|___|_*_|___|___|_*_|___|___
**     {0,  1,  3,  0,  1,  2,  0,  4,  2,  0,  3,  0}
**
**     Solution: In this example 13 units of snow (*) could be captured.
**  
**  2) Consider adding some additional tests in doTestsPass().
**  3) Implement computeSnowpack() correctly.
*/
```
<br/>

```java
import java.util.*;

class Solution {

    /**
     * Optimized Two-Pointer Approach
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static Integer computeSnowpack(Integer[] arr) {
        if (arr == null || arr.length == 0) return 0;

        int left = 0, right = arr.length - 1;
        int leftMax = 0, rightMax = 0;
        int total = 0;

        while (left < right) {
            leftMax = Math.max(leftMax,arr[left]);
            rightMax = Math.max(rightMax,arr[right]);
            
            if (leftMax < rightMax) {
                total += leftMax - arr[left];
                left++;
            } else {
                total += rightMax - arr[right];
                right--;
            }
        }

        return total;
    }

    public static boolean doTestsPass() {
        boolean result = true;
        result &= computeSnowpack(new Integer[]{0,1,3,0,1,2,0,4,2,0,3,0}) == 13;
        result &= computeSnowpack(new Integer[]{2,0,2}) == 2; // simple case
        result &= computeSnowpack(new Integer[]{3,0,0,2,0,4}) == 10; // multiple pits
        result &= computeSnowpack(new Integer[]{0,0,0}) == 0; // no trapping
        return result;
    }

    public static void main(String[] args) {
        if (doTestsPass()) {
            System.out.println("All tests pass");
        } else {
            System.out.println("Tests fail.");
        }
    }
}
```