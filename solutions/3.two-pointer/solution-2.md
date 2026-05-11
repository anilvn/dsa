# Longest Substring Without Repeating Characters

Input
s = "abcabcbb"

Output
3

Approach
Maintain unique characters using sliding window.

```java id="l1x8qp"
// Brute Force
static int lengthOfLongestSubstring(String s) {
    int max = 0;

    for (int i = 0; i < s.length(); i++) {
        Set<Character> set = new HashSet<>();

        for (int j = i; j < s.length(); j++) {

            if (set.contains(s.charAt(j))) {
                break;
            }

            set.add(s.charAt(j));

            max = Math.max(max, j - i + 1);
        }
    }

    return max;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(n)
*/
```

```java id="n2d7we"
// Optimal
static int lengthOfLongestSubstring(String s) {
    Set<Character> set = new HashSet<>();

    int left = 0, max = 0;

    for (int right = 0; right < s.length(); right++) {

        while (set.contains(s.charAt(right))) {
            set.remove(s.charAt(left++));
        }

        set.add(s.charAt(right));

        max = Math.max(max, right - left + 1);
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(n)
*/
```
```java
static int lengthOfLongestSubstring(String s) {

    Map<Character, Integer> map = new HashMap<>();

    int left = 0, max = 0;

    for (int right = 0; right < s.length(); right++) {

        char ch = s.charAt(right);

        // duplicate found inside current window
        if (map.containsKey(ch) && map.get(ch) >= left) {
            left = map.get(ch) + 1;
        }

        map.put(ch, right);

        max = Math.max(max, right - left + 1);
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(256) -> O(1)
*/
```

---

# Minimum Size Subarray Sum

Input
arr = [2,3,1,2,4,3], target = 7

Output
2

Approach
Shrink window while sum >= target.

```java id="k8m1rd"
// Brute Force
static int minSubArrayLen(int target, int[] arr) {
    int min = Integer.MAX_VALUE;

    for (int i = 0; i < arr.length; i++) {
        int sum = 0;

        for (int j = i; j < arr.length; j++) {
            sum += arr[j];

            if (sum >= target) {
                min = Math.min(min, j - i + 1);
                break;
            }
        }
    }

    return min == Integer.MAX_VALUE ? 0 : min;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="y7v9qs"
// Optimal - positive numbers only
static int minSubArrayLen(int target, int[] arr) {
    int left = 0, sum = 0;
    int min = Integer.MAX_VALUE;

    for (int right = 0; right < arr.length; right++) {
        sum += arr[right];

        while (sum >= target) {
            min = Math.min(min, right - left + 1);
            sum -= arr[left++];
        }
    }

    return min == Integer.MAX_VALUE ? 0 : min;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

# Max Consecutive Ones III

Input
arr = [1,1,1,0,0,0,1,1,1,1,0], k = 2

Output
6

Approach
Allow at most k zeros in window.

```java id="u2s5ka"
// Brute Force
static int longestOnes(int[] arr, int k) {
    int max = 0;

    for (int i = 0; i < arr.length; i++) {
        int zeros = 0;

        for (int j = i; j < arr.length; j++) {

            if (arr[j] == 0) {
                zeros++;
            }

            if (zeros > k) {
                break;
            }

            max = Math.max(max, j - i + 1);
        }
    }

    return max;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="d0h7tm"
// Optimal
static int longestOnes(int[] arr, int k) {
    int left = 0, zeros = 0, max = 0;

    for (int right = 0; right < arr.length; right++) {

        if (arr[right] == 0) {
            zeros++;
        }

        while (zeros > k) {

            if (arr[left++] == 0) {
                zeros--;
            }
        }

        max = Math.max(max, right - left + 1);
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

# Binary Array Sliding Window (0/1 Flips)

Input
arr = [0,0,1,1,0,1], k = 2

Output
5

Approach
Same pattern as Max Consecutive Ones III.

```java id="x3e9pv"
// Brute Force
static int maxWindowAfterFlips(int[] arr, int k) {
    int max = 0;

    for (int i = 0; i < arr.length; i++) {
        int zeros = 0;

        for (int j = i; j < arr.length; j++) {

            if (arr[j] == 0) {
                zeros++;
            }

            if (zeros > k) {
                break;
            }

            max = Math.max(max, j - i + 1);
        }
    }

    return max;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="p4f6ln"
// Optimal
static int maxWindowAfterFlips(int[] arr, int k) {
    int left = 0, zeros = 0, max = 0;

    for (int right = 0; right < arr.length; right++) {

        if (arr[right] == 0) {
            zeros++;
        }

        while (zeros > k) {

            if (arr[left++] == 0) {
                zeros--;
            }
        }

        max = Math.max(max, right - left + 1);
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

# Fruits Into Baskets

Input
fruits = [1,2,1,2,3]

Output
4

Approach
Maintain at most 2 distinct fruits.

```java id="r9u3fw"
// Brute Force
static int totalFruit(int[] fruits) {
    int max = 0;

    for (int i = 0; i < fruits.length; i++) {
        Set<Integer> set = new HashSet<>();

        for (int j = i; j < fruits.length; j++) {
            set.add(fruits[j]);

            if (set.size() > 2) {
                break;
            }

            max = Math.max(max, j - i + 1);
        }
    }

    return max;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="v1k6eg"
// Optimal
static int totalFruit(int[] fruits) {
    Map<Integer, Integer> map = new HashMap<>();

    int left = 0, max = 0;

    for (int right = 0; right < fruits.length; right++) {

        map.put(fruits[right],
                map.getOrDefault(fruits[right], 0) + 1);

        while (map.size() > 2) {
            map.put(fruits[left], map.get(fruits[left]) - 1);

            if (map.get(fruits[left]) == 0) {
                map.remove(fruits[left]);
            }

            left++;
        }

        max = Math.max(max, right - left + 1);
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

# Longest Substring With At Most K Distinct Characters

Input
s = "eceba", k = 2

Output
3

Approach
Keep at most k distinct characters in window.

```java id="w7c2ny"
// Brute Force
static int lengthOfLongestSubstringKDistinct(String s, int k) {
    int max = 0;

    for (int i = 0; i < s.length(); i++) {
        Set<Character> set = new HashSet<>();

        for (int j = i; j < s.length(); j++) {
            set.add(s.charAt(j));

            if (set.size() > k) {
                break;
            }

            max = Math.max(max, j - i + 1);
        }
    }

    return max;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(k)
*/
```

```java id="m5r1zt"
// Optimal
static int lengthOfLongestSubstringKDistinct(String s, int k) {
    Map<Character, Integer> map = new HashMap<>();

    int left = 0, max = 0;

    for (int right = 0; right < s.length(); right++) {

        char ch = s.charAt(right);

        map.put(ch, map.getOrDefault(ch, 0) + 1);

        while (map.size() > k) {

            char leftChar = s.charAt(left);

            map.put(leftChar, map.get(leftChar) - 1);

            if (map.get(leftChar) == 0) {
                map.remove(leftChar);
            }

            left++;
        }

        max = Math.max(max, right - left + 1);
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(k)
*/
```

---

# Longest Subarray / Substring With K Distinct Elements

Input
arr = [1,2,1,2,3], k = 2

Output
4
```
arr = [1,2,1,2,3]
k = 2
//output - 4
ans formed-- [1,2,1,2]
```
Approach
Maintain exactly k distinct elements.

```java id="j9d4ru"
// Brute Force
static int longestKDistinct(int[] arr, int k) {
    int max = 0;

    for (int i = 0; i < arr.length; i++) {
        Set<Integer> set = new HashSet<>();

        for (int j = i; j < arr.length; j++) {
            set.add(arr[j]);

            if (set.size() > k) {
                break;
            }

            if (set.size() == k) {
                max = Math.max(max, j - i + 1);
            }
        }
    }

    return max;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(k)
*/
```

```java id="h2o8xc"
// Optimal
static int longestKDistinct(int[] arr, int k) {
    Map<Integer, Integer> map = new HashMap<>();

    int left = 0, max = 0;

    for (int right = 0; right < arr.length; right++) {

        map.put(arr[right],
                map.getOrDefault(arr[right], 0) + 1);

        while (map.size() > k) {
            map.put(arr[left], map.get(arr[left]) - 1);

            if (map.get(arr[left]) == 0) {
                map.remove(arr[left]);
            }

            left++;
        }

        if (map.size() == k) {
            max = Math.max(max, right - left + 1);
        }
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(k)
*/
```

---

# Longest Repeating Character Replacement

Input
s = "AABABBA", k = 1

Output
4

Approach
Keep window where replacements needed <= k.

```java id="f5q8zx"
// Brute Force
static int characterReplacement(String s, int k) {
    int max = 0;

    for (int i = 0; i < s.length(); i++) {
        int[] freq = new int[26];
        int maxFreq = 0;

        for (int j = i; j < s.length(); j++) {

            freq[s.charAt(j) - 'A']++;

            maxFreq = Math.max(maxFreq,
                    freq[s.charAt(j) - 'A']);

            int changes = (j - i + 1) - maxFreq;

            if (changes > k) {
                break;
            }

            max = Math.max(max, j - i + 1);
        }
    }

    return max;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="z4n1yt"
// Optimal
static int characterReplacement(String s, int k) {
    int[] freq = new int[26];

    int left = 0, maxFreq = 0, max = 0;

    for (int right = 0; right < s.length(); right++) {

        freq[s.charAt(right) - 'A']++;

        maxFreq = Math.max(maxFreq,
                freq[s.charAt(right) - 'A']);

        while ((right - left + 1) - maxFreq > k) {
            freq[s.charAt(left) - 'A']--;
            left++;
        }

        max = Math.max(max, right - left + 1);
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```
