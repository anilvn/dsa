## Range Sum Query (Prefix Sum)

Input

```text
arr = [2, 4, 1, 6, 3]
query = (1, 3)
```

Output

```text
11
```

Approach

* Build prefix sum array
* Range sum = prefix[r] - prefix[l - 1]

```java
int rangeSum(int[] arr, int l, int r) {
    int n = arr.length;
    int[] prefix = new int[n];

    prefix[0] = arr[0];

    for (int i = 1; i < n; i++) {
        prefix[i] = prefix[i - 1] + arr[i];
    }

    return l == 0 ? prefix[r] : prefix[r] - prefix[l - 1];
}

/*
Time Complexity: O(n)
Space Complexity: O(n)
*/
```

---

## 2D Prefix Sum (Submatrix Queries)

Input

```text
matrix =
[
 [1,2,3],
 [4,5,6],
 [7,8,9]
]

query = (1,1) to (2,2)
```

Output

```text
28
```

Approach

* Build 2D prefix matrix
* Use inclusion-exclusion for submatrix sum

```java
int subMatrixSum(int[][] mat, int r1, int c1, int r2, int c2) {
    int n = mat.length;
    int m = mat[0].length;

    int[][] pre = new int[n + 1][m + 1];

    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
            pre[i][j] = mat[i - 1][j - 1]
                    + pre[i - 1][j]
                    + pre[i][j - 1]
                    - pre[i - 1][j - 1];
        }
    }

    return pre[r2 + 1][c2 + 1]
            - pre[r1][c2 + 1]
            - pre[r2 + 1][c1]
            + pre[r1][c1];
}

/*
Time Complexity: O(n * m)
Space Complexity: O(n * m)
*/
```

---

## Longest Subarray With Sum = 0

Input

```text
arr = [1, -1, 3, 2, -2, -3, 3]
```

Output

```text
6
```

Approach

* Prefix sum + first occurrence map
* Same prefix sum again => sum between them is 0

```java
int longestZeroSum(int[] arr) {
    Map<Integer, Integer> map = new HashMap<>();

    int sum = 0;
    int max = 0;

    map.put(0, -1);

    for (int i = 0; i < arr.length; i++) {
        sum += arr[i];

        if (map.containsKey(sum)) {
            max = Math.max(max, i - map.get(sum));
        } else {
            map.put(sum, i);
        }
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(n)
*/
```

---

## Longest Subarray With Sum = K

Input

```text
arr = [1, 2, 3, -2, 5]
k = 6
```

Output

```text
4
```

Approach

* If prefixSum - k existed before, subarray sum = k

```java
int longestSubarrayK(int[] arr, int k) {
    Map<Integer, Integer> map = new HashMap<>();

    int sum = 0;
    int max = 0;

    map.put(0, -1);

    for (int i = 0; i < arr.length; i++) {
        sum += arr[i];

        if (map.containsKey(sum - k)) {
            max = Math.max(max, i - map.get(sum - k));
        }

        map.putIfAbsent(sum, i);
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(n)
*/
```

---

## Subarray Sum Equals K (Count)

Input

```text
arr = [1, 1, 1]
k = 2
```

Output

```text
2
```

Approach

* Count previous prefix sums equal to sum - k

```java
int countSubarraysK(int[] arr, int k) {
    Map<Integer, Integer> map = new HashMap<>();

    map.put(0, 1);

    int sum = 0;
    int count = 0;

    for (int num : arr) {
        sum += num;

        count += map.getOrDefault(sum - k, 0);

        map.put(sum, map.getOrDefault(sum, 0) + 1);
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(n)
*/
```

---

## Longest Subarray With Sum Divisible By K

Input

```text
arr = [2, 7, 6, 1, 4, 5]
k = 3
```

Output

```text
4
```

Approach

* Same remainder => divisible subarray

```java
int longestDivisibleByK(int[] arr, int k) {
    Map<Integer, Integer> map = new HashMap<>();

    int sum = 0;
    int max = 0;

    map.put(0, -1);

    for (int i = 0; i < arr.length; i++) {
        sum += arr[i];

        int rem = ((sum % k) + k) % k;

        if (map.containsKey(rem)) {
            max = Math.max(max, i - map.get(rem));
        } else {
            map.put(rem, i);
        }
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(n)
*/
```

---

## Count Subarrays With Sum Divisible By K

Input

```text
arr = [4, 5, 0, -2, -3, 1]
k = 5
```

Output

```text
7
```

Approach

* Same remainder pairs form divisible subarrays

```java
int countDivisibleByK(int[] arr, int k) {
    Map<Integer, Integer> map = new HashMap<>();

    map.put(0, 1);

    int sum = 0;
    int count = 0;

    for (int num : arr) {
        sum += num;

        int rem = ((sum % k) + k) % k;

        count += map.getOrDefault(rem, 0);

        map.put(rem, map.getOrDefault(rem, 0) + 1);
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(n)
*/
```

---

## Largest Subarray With Equal 0s And 1s

Input

```text
arr = [0, 1, 0, 1, 1, 1, 0]
```

Output

```text
6
```

Approach

* Convert 0 -> -1
* Then longest zero sum problem

```java
int largestEqual01(int[] arr) {
    Map<Integer, Integer> map = new HashMap<>();

    int sum = 0;
    int max = 0;

    map.put(0, -1);

    for (int i = 0; i < arr.length; i++) {
        sum += (arr[i] == 0) ? -1 : 1;

        if (map.containsKey(sum)) {
            max = Math.max(max, i - map.get(sum));
        } else {
            map.put(sum, i);
        }
    }

    return max;
}

/*
Time Complexity: O(n)
Space Complexity: O(n)
*/
```

---

## Count Subarrays With Equal 0s And 1s

Input

```text
arr = [0, 1, 0, 1]
```

Output

```text
4
```

Approach

* Convert 0 -> -1
* Count zero sum subarrays

```java
int countEqual01(int[] arr) {
    Map<Integer, Integer> map = new HashMap<>();

    map.put(0, 1);

    int sum = 0;
    int count = 0;

    for (int num : arr) {
        sum += (num == 0) ? -1 : 1;

        count += map.getOrDefault(sum, 0);

        map.put(sum, map.getOrDefault(sum, 0) + 1);
    }

    return count;
}

/*
Time Complexity: O(n)
Space Complexity: O(n)
*/
```
