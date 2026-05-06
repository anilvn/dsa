

```java
import java.util.*;

public class JosephusProblem {

    static void Josh(List<Integer> person, int k, int index) {
        // Continue until only one person is left
        while (person.size() > 1) {
            // Find the index of the person to be killed
            index = (index + k) % person.size();

            // Remove that person
            person.remove(index);
        }

        // Print the last remaining person
        System.out.println(person.get(0));
    }

    public static void main(String[] args) {
        int n = 3; // total persons
        int k = 2; // step
        List<Integer> person = new ArrayList<>();
        
        for (int i = 1; i <= n; i++) {
            person.add(i);
        }

        Josh(person, k - 1, 0);  // Note: k-1 to adjust step for 0-based index
    }
}
```