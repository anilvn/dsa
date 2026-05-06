Nice — let's build a few clean, interview-ready Java solutions to **find the winner of an election** (given a list/array of votes where each vote is a candidate name).
I’ll provide:

* Problem statement & assumptions
* 3 approaches: **brute-force (count + sort)**, **hashmap (optimal)**, **hashmap + first-occurrence tie-break**
* Inline comments, complexity notes, and examples

# Problem

Input: an array/list of strings `votes`, each string is the name of a candidate.
Output: the name of the candidate who wins (most votes).
Tie handling — there are two common policies; I'll implement both and label them:

1. **Lexicographic tie-break**: when multiple candidates have same max votes, choose the lexicographically smallest name. (HackerRank-style)
2. **First-occurrence tie-break**: when tie, the candidate who reached that count first (appeared earlier in input) wins.

---

# 1) Brute-force / Sort approach (simple, clearer but extra log factor)

Sort unique candidates by (-count, name) then pick first. Clean but uses sorting.

```java
import java.util.*;

public class ElectionWinner {

    // Brute-force: build frequency map, then sort entries by (count desc, name asc)
    public static String winnerBySort(List<String> votes) {
        if (votes == null || votes.isEmpty()) return "";

        Map<String, Integer> freq = new HashMap<>();
        for (String v : votes) {
            freq.put(v, freq.getOrDefault(v, 0) + 1);
        }

        // Create a list of map entries and sort by count desc, name asc
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(freq.entrySet());
        entries.sort((a, b) -> {
            int cmp = Integer.compare(b.getValue(), a.getValue()); // descending count
            if (cmp != 0) return cmp;
            return a.getKey().compareTo(b.getKey()); // tie-break: lexicographically smallest
        });

        return entries.get(0).getKey();
    }

    // Simple test
    public static void main(String[] args) {
        List<String> votes = Arrays.asList("alex","michael","michael","ram","michael","alex","ram","ram");
        System.out.println("Winner (bySort): " + winnerBySort(votes)); // prints "michael"
    }
}
```

Complexity: building map O(n), sorting O(k log k) where k = number of distinct candidates (≤ n). Total \~ O(n + k log k). Space O(k).

---

# 2) Optimal: Single pass using HashMap (choose lexicographically smallest on ties)

No global sort — track counts and best candidate while counting.

```java
import java.util.*;

public class ElectionWinner {

    // Optimal single-pass count. Tie-break: lexicographically smallest name.
    public static String winnerByMapLexicographic(List<String> votes) {
        if (votes == null || votes.isEmpty()) return "";

        Map<String, Integer> freq = new HashMap<>();
        String best = "";
        int bestCount = 0;

        for (String v : votes) {
            int count = freq.getOrDefault(v, 0) + 1;
            freq.put(v, count);

            // Update best:
            // - if this candidate now has more votes, it's the new best.
            // - if same votes, choose lexicographically smaller name.
            if (count > bestCount || (count == bestCount && v.compareTo(best) < 0)) {
                best = v;
                bestCount = count;
            }
        }
        return best;
    }

    // Quick demo
    public static void main(String[] args) {
        List<String> votes = Arrays.asList("alex","michael","michael","ram","michael","alex","ram","ram");
        System.out.println("Winner (map lexicographic): " + winnerByMapLexicographic(votes)); // "michael"
    }
}
```

Complexity: O(n) time, O(k) space. This is generally the recommended solution.

---

# 3) Single-pass with First-occurrence tie-break

If the tie rule is: *candidate who appears earlier in the list wins when counts tie*, we must remember first occurrence order.

```java
import java.util.*;

public class ElectionWinner {

    // Single-pass using HashMap, tie-break: earliest appearance in votes wins.
    public static String winnerByFirstOccurrence(List<String> votes) {
        if (votes == null || votes.isEmpty()) return "";

        Map<String, Integer> freq = new HashMap<>();
        Map<String, Integer> firstIndex = new HashMap<>(); // record first index seen
        String best = null;
        int bestCount = 0;
        int bestFirstIndex = Integer.MAX_VALUE;

        for (int i = 0; i < votes.size(); i++) {
            String v = votes.get(i);
            freq.put(v, freq.getOrDefault(v, 0) + 1);
            firstIndex.putIfAbsent(v, i);

            int count = freq.get(v);
            int idx = firstIndex.get(v);

            // Update best:
            // - more votes -> replace
            // - same votes -> earlier first occurrence -> replace
            if (best == null
                || count > bestCount
                || (count == bestCount && idx < bestFirstIndex)) {
                best = v;
                bestCount = count;
                bestFirstIndex = idx;
            }
        }
        return best;
    }

    // Demo
    public static void main(String[] args) {
        List<String> votes = Arrays.asList("joe", "alex", "joe", "alex"); 
        // both joe and alex have 2 votes. joe appeared earlier -> joe wins
        System.out.println("Winner (first-occurrence): " + winnerByFirstOccurrence(votes)); // "joe"
    }
}
```

Complexity: O(n) time, O(k) space.

---

# Edge cases & notes

* Empty or null input → return empty string `""` (could throw exception instead if required).
* Case sensitivity: current code treats `"Alex"` and `"alex"` as different. Convert to `toLowerCase()` first if case-insensitive counting is desired.
* Large inputs: all solutions are linear in the number of votes (except the sort-based extra log factor).
* Tie-break policy must be clarified in problem statement — I implemented the two most common ones.

---

# Which to use in interviews?

* If asked simply “who has the most votes?” — implement **winnerByMapLexicographic** (or **winnerByFirstOccurrence** if specified). Explain tie rules.
* Mention complexity: O(n) time, O(k) space.

---

Would you like:

* a full file combining all three methods and unit-like test cases?
* a version that reads votes from STDIN (for competitive programming)?
* the same implementations in Python or C++?
