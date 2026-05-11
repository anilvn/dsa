# Number Of Substrings Containing All Three Characters

Input
s = "abcabc"

Output
10

Approach
Use sliding window + frequency count.

```java id="a1b2c3"
// Brute Force
static int numberOfSubstrings(String s) {
    int count = 0;

    for (int i = 0; i < s.length(); i++) {
        int a = 0, b = 0, c = 0;

        for (int j = i; j < s.length(); j++) {

            char ch = s.charAt(j);

            if (ch == 'a') a++;
            if (ch == 'b') b++;
            if (ch == 'c') c++;

            if (a > 0 && b > 0 && c > 0) {
                count++;
            }
        }
    }

    return count;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="d4e5f6"
// Optimal
static int numberOfSubstrings(String s) {
    int[] freq = new int[3];
    int left = 0, count = 0;

    for (int right = 0; right < s.length(); right++) {

        freq[s.charAt(right) - 'a']++;

        while (freq[0] > 0 && freq[1] > 0 && freq[2] > 0) {
            count += s.length() - right;

            freq[s.charAt(left) - 'a']--;
            left++;
        }
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

# Minimum Window Substring

Input
s = "ADOBECODEBANC", t = "ABC"

Output
"BANC"

Approach
Expand until valid, then shrink.

```java id="g7h8i9"
// Brute Force
static String minWindow(String s, String t) {
    int minLen = Integer.MAX_VALUE;
    String ans = "";

    for (int i = 0; i < s.length(); i++) {
        int[] freq = new int[128];

        for (char c : t.toCharArray()) {
            freq[c]++;
        }

        int count = t.length();

        for (int j = i; j < s.length(); j++) {

            if (freq[s.charAt(j)] > 0) {
                count--;
            }

            freq[s.charAt(j)]--;

            if (count == 0) {
                if (j - i + 1 < minLen) {
                    minLen = j - i + 1;
                    ans = s.substring(i, j + 1);
                }
                break;
            }
        }
    }

    return ans;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="j1k2l3"
// Optimal
static String minWindow(String s, String t) {
    if (t.length() > s.length()) return "";

    int[] freq = new int[128];

    for (char c : t.toCharArray()) {
        freq[c]++;
    }

    int required = t.length();
    int left = 0, minLen = Integer.MAX_VALUE, start = 0;

    for (int right = 0; right < s.length(); right++) {

        if (freq[s.charAt(right)] > 0) {
            required--;
        }

        freq[s.charAt(right)]--;

        while (required == 0) {

            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                start = left;
            }

            freq[s.charAt(left)]++;

            if (freq[s.charAt(left)] > 0) {
                required++;
            }

            left++;
        }
    }

    return minLen == Integer.MAX_VALUE ? "" :
            s.substring(start, start + minLen);
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

# Minimum Size Window (Sum ≥ K)

Input
arr = [2,3,1,2,4,3], k = 7

Output
2

Approach
Shrink window when sum ≥ k.

```java id="m4n5o6"
// Brute Force
static int minWindowSize(int[] arr, int k) {
    int min = Integer.MAX_VALUE;

    for (int i = 0; i < arr.length; i++) {
        int sum = 0;

        for (int j = i; j < arr.length; j++) {
            sum += arr[j];

            if (sum >= k) {
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

```java id="p7q8r9"
// Optimal
static int minWindowSize(int[] arr, int k) {
    int left = 0, sum = 0;
    int min = Integer.MAX_VALUE;

    for (int right = 0; right < arr.length; right++) {

        sum += arr[right];

        while (sum >= k) {
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
