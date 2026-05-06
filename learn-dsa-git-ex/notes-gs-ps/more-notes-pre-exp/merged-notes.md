# DSA Notes — Basic to Advanced

---

## 1. Robot Walk

Robot starts at `(0,0)`. Move per char: `U`→y+1, `D`→y-1, `L`→x-1, `R`→x+1. Ignore all other chars.

```java
public static Integer[] walk(String path) {
    int x = 0, y = 0;
    for (char c : path.toCharArray()) {
        switch (c) {
            case 'U': y++; break;
            case 'D': y--; break;
            case 'L': x--; break;
            case 'R': x++; break;
        }
    }
    return new Integer[]{x, y};
}
```

- Time: O(n), Space: O(1)

---

## 2. Prime Factors

Three approaches — pick the optimized one for interviews.

**Approach 1 — Naïve:** Loop 2…n, check divisibility + primality → O(n·√n)

**Approach 2 — Divisor Pairing:** Loop till √n, check both `i` and `n/i` → O(n) practical

**Approach 3 — Optimized (School Method):** Divide out each factor completely, leftover `n > 1` is itself prime → O(√n)

```java
static List<Integer> primeFactors(int n) {
    List<Integer> factors = new ArrayList<>();
    for (int i = 2; i * i <= n; i++) {
        if (n % i == 0) {
            factors.add(i);
            while (n % i == 0) n /= i; // exhaust this factor
        }
    }
    if (n > 1) factors.add(n); // remaining prime
    return factors;
}
```

Dry run `n=780`: divide by 2→390→195, by 3→65, by 5→13; leftover 13 is prime → `[2, 3, 5, 13]`

---

## 3. Longest Uniform Substring

Find the start index and length of the longest run of identical characters.

**Brute Force:** O(n²) — nested expansion per position.

**Optimized — Single Pass:** O(n), O(1)

```java
static int[] longestUniformSubstring(String input) {
    if (input == null || input.isEmpty()) return new int[]{-1, 0};
    int longestStart = 0, longestLength = 1;
    int currentStart = 0, currentLength = 1;

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
```

---

## 4. Get Squares Sorted

Input is a sorted array (may have negatives). Return sorted array of squares.

Key insight: largest square comes from either end. Use two pointers filling result from the back.

```java
public int[] getSquaresSorted(int[] arr) {
    int n = arr.length;
    int[] result = new int[n];
    int left = 0, right = n - 1, pos = n - 1;

    while (left <= right) {
        int leftSq = arr[left] * arr[left];
        int rightSq = arr[right] * arr[right];
        if (leftSq > rightSq) { result[pos--] = leftSq; left++; }
        else                  { result[pos--] = rightSq; right--; }
    }
    return result;
}
```

- Time: O(n), Space: O(n)

---

## 5. Unique Tuples (Sliding Window)

Return all unique substrings of length `len`.

```java
public static HashSet<String> uniqueTuples(String input, int len) {
    HashSet<String> result = new HashSet<>();
    if (input == null || input.length() < len) return result;

    StringBuilder window = new StringBuilder(input.substring(0, len));
    result.add(window.toString());

    for (int i = len; i < input.length(); i++) {
        window.deleteCharAt(0);
        window.append(input.charAt(i));
        result.add(window.toString());
    }
    return result;
}
```

- Time: O(n), Space: O(n)

---

## 6. Longest Common Prefix (Sorted)

Sort the array — the common prefix of the whole array equals the common prefix of the **first** and **last** strings after sorting (most different pair).

```java
public static String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    Arrays.sort(strs);
    String first = strs[0], last = strs[strs.length - 1];
    int i = 0, minLen = Math.min(first.length(), last.length());
    while (i < minLen && first.charAt(i) == last.charAt(i)) i++;
    return first.substring(0, i);
}
```

- Time: O(n log n · m), Space: O(1)

---

## 7. Remove Duplicates with Asterisk

Replace each duplicate char with `'*'`; avoid consecutive `*`s.

```java
public static String removeDuplicatesWithAsterisk(String s) {
    HashSet<Character> seen = new HashSet<>();
    StringBuilder sb = new StringBuilder();
    for (char c : s.toCharArray()) {
        if (!seen.contains(c)) {
            seen.add(c);
            sb.append(c);
        } else if (sb.charAt(sb.length() - 1) != '*') {
            sb.append('*');
        }
    }
    return sb.toString();
}
```

`"ababcababce"` → `"ab*c*e"` — Time: O(n), Space: O(n)

---

## 8. Backspace Compare

Compare two strings after applying `#` as backspace.

**Brute Force — Stack/StringBuilder:** O(n+m) time, O(n+m) space

```java
private String build(String s) {
    StringBuilder sb = new StringBuilder();
    for (char c : s.toCharArray()) {
        if (c == '#') { if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1); }
        else sb.append(c);
    }
    return sb.toString();
}
public boolean backspaceCompare(String s, String t) { return build(s).equals(build(t)); }
```

**Optimized — Two Pointers from end:** O(n+m) time, O(1) space

Traverse both strings from the right, skipping chars consumed by `#`. Compare valid chars one by one.

```java
public boolean backspaceCompare(String s, String t) {
    int i = s.length() - 1, j = t.length() - 1;
    while (i >= 0 || j >= 0) {
        i = nextValid(s, i);
        j = nextValid(t, j);
        if (i >= 0 && j >= 0 && s.charAt(i) != t.charAt(j)) return false;
        if ((i >= 0) != (j >= 0)) return false;
        i--; j--;
    }
    return true;
}

private int nextValid(String str, int idx) {
    int skip = 0;
    while (idx >= 0) {
        if (str.charAt(idx) == '#') { skip++; idx--; }
        else if (skip > 0)          { skip--; idx--; }
        else break;
    }
    return idx;
}
```

---

## 9. Find Top IP Address (Apache Log)

**Brute Force:** O(n²) — for each line, scan all lines to count.

**Optimized — HashMap:** O(n) time, O(n) space

```java
public static String findTopIpaddress(String[] lines) {
    Map<String, Integer> freq = new HashMap<>();
    for (String line : lines)
        freq.merge(line.split(" ")[0], 1, Integer::sum);

    return freq.entrySet().stream()
               .max(Map.Entry.comparingByValue())
               .map(Map.Entry::getKey).orElse("");
}
```

---

## 10. Election / Josephus Problem

`n` people in a circle; every `k`-th person is eliminated. Find the last survivor.

```java
static void josephus(List<Integer> people, int k, int index) {
    while (people.size() > 1) {
        index = (index + k) % people.size();
        people.remove(index);
    }
    System.out.println(people.get(0));
}
// Call with k-1 to adjust for 0-based indexing
```

- Time: O(n²) due to ArrayList remove; O(n log n) with an order-statistic tree.

---

## 11. Dictionary — Longest Word from Letters

Find the longest word(s) in a dictionary that can be formed from the given letters (respecting frequency).

**Optimized — int[26] frequency array:**

```java
public static Set<String> longestWord(String letters, Dictionary dict) {
    Set<String> result = new HashSet<>();
    int[] avail = new int[26];
    for (char c : letters.toCharArray()) avail[c - 'a']++;
    int maxLen = 0;

    for (String word : dict.getEntries()) {
        if (canForm(word, avail)) {
            if (word.length() > maxLen) { result.clear(); maxLen = word.length(); }
            if (word.length() == maxLen) result.add(word);
        }
    }
    return result;
}

private static boolean canForm(String word, int[] avail) {
    int[] temp = Arrays.copyOf(avail, 26);
    for (char c : word.toCharArray()) {
        if (temp[c - 'a']-- == 0) return false;
    }
    return true;
}
```

- Time: O(N·W), Space: O(1) extra

---

## 12. Min Flips to Make Alternate Binary String

Target is either `"010101..."` or `"101010..."`. Count mismatches against both; return the minimum.

```java
public static int minFlips(String s) {
    int flips1 = 0, flips2 = 0;
    for (int i = 0; i < s.length(); i++) {
        char e1 = (i % 2 == 0) ? '0' : '1';
        char e2 = (i % 2 == 0) ? '1' : '0';
        if (s.charAt(i) != e1) flips1++;
        if (s.charAt(i) != e2) flips2++;
    }
    return Math.min(flips1, flips2);
}
```

- Time: O(n), Space: O(1)

---

## 13. Max Consecutive Ones III (Sliding Window)

Given binary array and `K` allowed zero-flips, find the longest subarray of 1s.

**Brute Force:** O(n²) — check all subarrays, count zeros.

**Optimized — Sliding Window:** O(n), O(1)

```java
public static int longestOnes(int[] nums, int K) {
    int left = 0, zeros = 0, maxLen = 0;
    for (int right = 0; right < nums.length; right++) {
        if (nums[right] == 0) zeros++;
        while (zeros > K) { if (nums[left++] == 0) zeros--; }
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

---

## 14. Count Length of Cycle

Array where each element is the next index to jump to. Detect cycle and return its length, or -1.

```java
public static int countLengthOfCycle(int[] arr, int startIndex) {
    Map<Integer, Integer> visited = new HashMap<>(); // index → step
    int steps = 0, current = startIndex;

    while (current >= 0 && current < arr.length) {
        if (visited.containsKey(current))
            return steps - visited.get(current); // cycle length
        visited.put(current, steps++);
        current = arr[current];
    }
    return -1;
}
```

- Time: O(n), Space: O(n)

---

## 15. Daily Temperatures / Next Greater Element

Classic monotonic stack pattern. Traverse right-to-left, maintain a decreasing stack.

**Next Greater Element (value):**

```java
public static int[] nextGreaterElements(int[] arr) {
    int n = arr.length;
    int[] result = new int[n];
    Stack<Integer> stack = new Stack<>(); // stores values

    for (int i = n - 1; i >= 0; i--) {
        while (!stack.isEmpty() && stack.peek() <= arr[i]) stack.pop();
        result[i] = stack.isEmpty() ? -1 : stack.peek();
        stack.push(arr[i]);
    }
    return result;
}
```

**Daily Temperatures (days until warmer):** Same idea but store indices; result is `stack.peek() - i`.

```java
public static int[] dailyTemperatures(int[] arr) {
    int n = arr.length;
    int[] result = new int[n];
    Stack<Integer> stack = new Stack<>(); // stores indices

    for (int i = n - 1; i >= 0; i--) {
        while (!stack.isEmpty() && arr[stack.peek()] <= arr[i]) stack.pop();
        result[i] = stack.isEmpty() ? 0 : stack.peek() - i;
        stack.push(i);
    }
    return result;
}
```

- Time: O(n), Space: O(n)

---

## 16. Snowpack (Trapping Rain Water)

Given elevation array, compute total units of water trapped.

**Two-Pointer Approach:** O(n) time, O(1) space

The side with the smaller max determines how much water can be held at that position.

```java
public static Integer computeSnowpack(Integer[] arr) {
    if (arr == null || arr.length == 0) return 0;
    int left = 0, right = arr.length - 1;
    int leftMax = 0, rightMax = 0, total = 0;

    while (left < right) {
        leftMax  = Math.max(leftMax,  arr[left]);
        rightMax = Math.max(rightMax, arr[right]);
        if (leftMax < rightMax) { total += leftMax  - arr[left++]; }
        else                    { total += rightMax - arr[right--]; }
    }
    return total;
}
```

`{0,1,3,0,1,2,0,4,2,0,3,0}` → 13 units

---

## 17. Min Platforms Required

Sort arrivals and departures separately. Sweep with two pointers: arrival ≤ departure → need one more platform; else free one. Track max.

```java
public static int findPlatform(int[] arr, int[] dep) {
    int n = arr.length;
    Arrays.sort(arr); Arrays.sort(dep);
    int platforms = 0, maxPlatforms = 0, i = 0, j = 0;

    while (i < n) {
        if (arr[i] <= dep[j]) { maxPlatforms = Math.max(maxPlatforms, ++platforms); i++; }
        else                  { platforms--; j++; }
    }
    return maxPlatforms;
}
```

- Time: O(n log n), Space: O(1)

---

## 18. BST Implementation

Insert, search, and in-order traversal (produces sorted output).

```java
class BST {
    private Node root;

    public void put(int value) { root = put(root, value); }
    private Node put(Node node, int value) {
        if (node == null) { Node n = new Node(); n.val = value; return n; }
        if (value < node.val) node.left  = put(node.left,  value);
        else if (value > node.val) node.right = put(node.right, value);
        return node;
    }

    public boolean contains(int value) { return contains(root, value); }
    private boolean contains(Node node, int value) {
        if (node == null) return false;
        if (node.val == value) return true;
        return value < node.val ? contains(node.left, value) : contains(node.right, value);
    }

    public List<Integer> inOrderTraversal() {
        List<Integer> acc = new ArrayList<>();
        inOrder(root, acc);
        return acc;
    }
    private void inOrder(Node node, List<Integer> acc) {
        if (node == null) return;
        inOrder(node.left, acc);
        acc.add(node.val);       // visit between left and right = in-order
        inOrder(node.right, acc);
    }

    private static class Node { int val; Node left, right; }
}
```

- put/contains: O(h) — O(log n) balanced, O(n) skewed
- inOrder: O(n)

---

## 19. 2-Sum / 3-Sum / 4-Sum

General pattern: fix (n-2) elements, solve 2-Sum for the rest.

**2-Sum — HashMap:** O(n) time, O(n) space

```java
public static int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int comp = target - nums[i];
        if (map.containsKey(comp)) return new int[]{map.get(comp), i};
        map.put(nums[i], i);
    }
    return new int[]{};
}
```

**3-Sum — Two Pointers:** O(n²) time, O(1) space. Sort first, fix `i`, use left/right pointers. Skip duplicates.

**4-Sum — Two Pointers:** O(n³) time, O(1) space. Fix `a` and `b`, use left/right pointers for the remaining pair.

```java
public static List<List<Integer>> fourSum(int[] nums, int target) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums);
    int n = nums.length;

    for (int a = 0; a < n - 3; a++) {
        if (a > 0 && nums[a] == nums[a - 1]) continue;
        for (int b = a + 1; b < n - 2; b++) {
            if (b > a + 1 && nums[b] == nums[b - 1]) continue;
            int left = b + 1, right = n - 1;
            while (left < right) {
                long sum = (long) nums[a] + nums[b] + nums[left] + nums[right];
                if (sum == target) {
                    result.add(Arrays.asList(nums[a], nums[b], nums[left], nums[right]));
                    while (left < right && nums[left]  == nums[left  + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++; right--;
                } else if (sum < target) left++;
                else right--;
            }
        }
    }
    return result;
}
```

| Problem | Best Approach | Time  | Space |
|---------|--------------|-------|-------|
| 2-Sum   | HashMap      | O(n)  | O(n)  |
| 3-Sum   | Two Pointers | O(n²) | O(1)  |
| 4-Sum   | Two Pointers | O(n³) | O(1)  |

---

## 20. Median of Stream

Use two heaps: max-heap (`lowers`) for the bottom half, min-heap (`highers`) for the top half. Keep sizes balanced (differ by at most 1).

```java
public class MedianOfStream {
    private PriorityQueue<Integer> lowers  = new PriorityQueue<>(Collections.reverseOrder());
    private PriorityQueue<Integer> highers = new PriorityQueue<>();

    public void addNum(int num) {
        if (lowers.isEmpty() || num <= lowers.peek()) lowers.add(num);
        else highers.add(num);
        rebalance();
    }

    private void rebalance() {
        if (lowers.size() - highers.size() > 1) highers.add(lowers.poll());
        else if (highers.size() - lowers.size() > 1) lowers.add(highers.poll());
    }

    public double findMedian() {
        if (lowers.size() == highers.size())
            return lowers.isEmpty() ? 0 : (lowers.peek() + highers.peek()) / 2.0;
        return lowers.size() > highers.size() ? lowers.peek() : highers.peek();
    }
}
```

- addNum: O(log n), findMedian: O(1), Space: O(n)

Dry run `{5,15,1,3,8,7,9}`:

| Added | lowers (max-heap) | highers (min-heap) | Median |
|-------|-------------------|--------------------|--------|
| 5     | [5]               | []                 | 5      |
| 15    | [5]               | [15]               | 10.0   |
| 1     | [5,1]             | [15]               | 5      |
| 3     | [3,1]             | [5,15]             | 4.0    |
| 8     | [5,1,3]           | [8,15]             | 5      |
| 7     | [5,1,3]           | [7,8,15]           | 6.0    |
| 9     | [7,5,3,1]         | [8,15,9]           | 7      |
