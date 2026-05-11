# Subarray Product Less Than K

Input
arr = [10, 5, 2, 6], k = 100

Output
8

Approach
Sliding window, shrink when product ≥ k.

```java id="p1a2bc"
// Brute Force
static int numSubarrayProductLessThanK(int[] arr, int k) {
    int count = 0;

    for (int i = 0; i < arr.length; i++) {
        int prod = 1;

        for (int j = i; j < arr.length; j++) {
            prod *= arr[j];

            if (prod < k) {
                count++;
            } else {
                break;
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

```java id="q9x7kl"
// Optimal
static int numSubarrayProductLessThanK(int[] arr, int k) {
    if (k <= 1) return 0;

    int left = 0, prod = 1, count = 0;

    for (int right = 0; right < arr.length; right++) {
        prod *= arr[right];

        while (prod >= k) {
            prod /= arr[left++];
        }

        count += right - left + 1;
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

# Count Subarrays With Product Less Than K

Input
arr = [1, 2, 3, 4], k = 10

Output
7

Approach
Same as previous (duplicate pattern).

```java id="m8v2pq"
// Brute Force
static int countSubarraysProductLessThanK(int[] arr, int k) {
    int count = 0;

    for (int i = 0; i < arr.length; i++) {
        int prod = 1;

        for (int j = i; j < arr.length; j++) {
            prod *= arr[j];

            if (prod < k) count++;
            else break;
        }
    }

    return count;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="t4n6sd"
// Optimal
static int countSubarraysProductLessThanK(int[] arr, int k) {
    if (k <= 1) return 0;

    int left = 0, prod = 1, count = 0;

    for (int right = 0; right < arr.length; right++) {
        prod *= arr[right];

        while (prod >= k) {
            prod /= arr[left++];
        }

        count += right - left + 1;
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

# Binary Subarrays With Sum

Input
arr = [1,0,1,0,1], goal = 2

Output
4

Approach
Use at most trick.

```java id="b3k8xy"
// Brute Force
static int numSubarraysWithSum(int[] arr, int goal) {
    int count = 0;

    for (int i = 0; i < arr.length; i++) {
        int sum = 0;

        for (int j = i; j < arr.length; j++) {
            sum += arr[j];

            if (sum == goal) count++;
        }
    }

    return count;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="c7v1mn"
// Optimal (AtMost trick)
static int numSubarraysWithSum(int[] arr, int goal) {
    return atMost(arr, goal) - atMost(arr, goal - 1);
}

static int atMost(int[] arr, int k) {
    if (k < 0) return 0;

    int left = 0, sum = 0, count = 0;

    for (int right = 0; right < arr.length; right++) {
        sum += arr[right];

        // Window [left ... right] has sum ≤ k - if goes beyond shrink...
        while (sum > k) {
            sum -= arr[left++];
        }

        count += right - left + 1;
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

# Count Number Of Nice Subarrays

Input
arr = [1,1,2,1,1], k = 3

Output
2

Approach
Convert odd/even → binary idea.

```java id="n2k9qp"
// Brute Force
static int numberOfSubarrays(int[] arr, int k) {
    int count = 0;

    for (int i = 0; i < arr.length; i++) {
        int odd = 0;

        for (int j = i; j < arr.length; j++) {

            if (arr[j] % 2 != 0) {
                odd++;
            }

            if (odd == k) count++;
        }
    }

    return count;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(1)
*/
```

```java id="r6m1zx"
// Optimal
static int numberOfSubarrays(int[] arr, int k) {
    return atMost(arr, k) - atMost(arr, k - 1);
}

static int atMost(int[] arr, int k) {
    if (k < 0) return 0;

    int left = 0, count = 0;

    for (int right = 0; right < arr.length; right++) {

        if (arr[right] % 2 != 0) {
            k--;
        }

        while (k < 0) {
            if (arr[left] % 2 != 0) {
                k++;
            }
            left++;
        }

        count += right - left + 1;
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/
```

---

# Subarrays With K Different Integers

Input
arr = [1,2,1,2,3], k = 2

Output
7

Approach
AtMost trick with hashmap.

```java id="u8p3wd"
// Brute Force
static int subarraysWithKDistinct(int[] arr, int k) {
    int count = 0;

    for (int i = 0; i < arr.length; i++) {
        Set<Integer> set = new HashSet<>();

        for (int j = i; j < arr.length; j++) {
            set.add(arr[j]);

            if (set.size() == k) count++;

            if (set.size() > k) break;
        }
    }

    return count;
}

/*
Time Complexity: O(n^2)
Space Complexity: O(k)
*/
```

```java id="w1d7ab"
// Optimal
static int subarraysWithKDistinct(int[] arr, int k) {
    return atMost(arr, k) - atMost(arr, k - 1);
}

static int atMost(int[] arr, int k) {
    Map<Integer, Integer> map = new HashMap<>();

    int left = 0, count = 0;

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

        count += right - left + 1;
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(k)
*/
```

---

# Important Core Formula

AtMost Trick:

```text
Exact(K) = AtMost(K) - AtMost(K - 1)
```

Used in:

* Binary Subarrays With Sum
* Nice Subarrays
* Subarrays With K Distinct Integers
