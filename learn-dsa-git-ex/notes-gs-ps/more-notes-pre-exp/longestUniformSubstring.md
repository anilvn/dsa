
### 🔹 Brute Force Approach

We check **every possible substring** and see if all characters are the same.

```java
import java.util.*;

public class SolutionBruteForce {

    static int[] longestUniformSubstring(String input) {
        if (input == null || input.isEmpty()) return new int[]{-1, 0};

        int n = input.length();
        int longestStart = -1;
        int longestLength = 0;

        for (int i = 0; i < n; i++) {
            char ch = input.charAt(i);
            int count = 1;

            // expand to the right while characters are the same
            for (int j = i + 1; j < n; j++) {
                if (input.charAt(j) == ch) {
                    count++;
                } else {
                    break;
                }
            }

            if (count > longestLength) {
                longestLength = count;
                longestStart = i;
            }
        }

        return new int[]{longestStart, longestLength};
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(longestUniformSubstring("abbbccda"))); // [1, 3]
    }
}

/*
Time Complexity: O(n^2) 
   - Worst case when string is like "aaaaa..." we expand in nested loops.
Space Complexity: O(1) 
   - Only a few variables.
*/
```

---

### 🔹 Optimized Approach (Single Pass)

We just scan once while **tracking current streak** of repeated characters.

```java
import java.util.*;

public class SolutionBruteForce {

    static int[] longestUniformSubstring(String input) {
        if (input == null || input.isEmpty()) return new int[]{-1, 0};

        int n = input.length();
        int longestStart = -1;
        int longestLength = 0;

     
        int start = 0;
        while(start < n){   
            int end = start+1;
            while (end < n && input.charAt(start) == input.charAt(end) ){    
                if(end-start+1 > longestLength){
                    longestLength = end-start+1 ;
                    longestStart = start;
                }
                end++;
            }
            start = end;
        }

        return new int[]{longestStart, longestLength};
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(longestUniformSubstring("abbbccda"))); // [1, 3]
    }
}
```
<br/>
<br/>

```java
import java.util.*;

public class SolutionOptimized {

    static int[] longestUniformSubstring(String input) {
        if (input == null || input.isEmpty()) return new int[]{-1, 0};

        int longestStart = 0;
        int longestLength = 1;

        int currentStart = 0;
        int currentLength = 1;

        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == input.charAt(i - 1)) {
                currentLength++;
            } else {
                currentStart = i;
                currentLength = 1;
            }

            if (currentLength > longestLength) {
                longestLength = currentLength;
                longestStart = currentStart;
            }
        }

        return new int[]{longestStart, longestLength};
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(longestUniformSubstring("abbbccda"))); // [1, 3]
        System.out.println(Arrays.toString(longestUniformSubstring("10000111"))); // [1, 4]
        System.out.println(Arrays.toString(longestUniformSubstring("aabbbbbCdAA"))); // [2, 5]
    }
}

/*
Time Complexity: O(n) 
   - Single pass through the string.
Space Complexity: O(1) 
   - Constant extra space.
*/
```

