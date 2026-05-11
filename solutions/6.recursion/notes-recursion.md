# Recursion — Questions & Answers (Java)

---

## Table of Contents

- [Level 1: Recursion Basics (Foundation)](#level-1-recursion-basics-foundation)
  - [1. Print 1 to N](#1-print-1-to-n-using-recursion)
  - [2. Print N to 1](#2-print-n-to-1-using-recursion)
  - [3. Sum of First N Numbers](#3-sum-of-first-n-numbers)
  - [4. Factorial of a Number](#4-factorial-of-a-number)
  - [5. Print Even or Odd](#5-print-even-or-odd-using-recursion)
  - [6. Power Function (x^n)](#6-power-function-xn)
- [Level 2: Number & Digit Problems](#level-2-number--digit-problems)
  - [7. Count Digits in a Number](#7-count-digits-in-a-number)
  - [8. Sum of Digits](#8-sum-of-digits-of-a-number)
  - [9. Product of Digits](#9-product-of-digits-of-a-number)
- [Level 3: Fibonacci](#level-3-fibonacci-core-recursion-understanding)
  - [10. Print Fibonacci Series up to N](#10-print-fibonacci-series-up-to-n)
  - [11. Find K-th Fibonacci Number](#11-find-k-th-fibonacci-number)
- [Level 4: Arrays Basics with Recursion](#level-4-arrays-basics-with-recursion)
  - [12. Check if Array is Sorted](#12-check-if-array-is-sorted-ascendingdescending)
  - [13. Find Maximum in Array](#13-find-maximum-in-array)
  - [14. Find Minimum in Array](#14-find-minimum-in-array)
  - [15. Reverse an Array](#15-reverse-an-array)
- [Level 5: Searching (Recursion)](#level-5-searching-recursion)
  - [16. Linear Search](#16-linear-search-using-recursion)
  - [17. First Occurrence in Array](#17-first-occurrence-in-array)
  - [18. Last Occurrence in Array](#18-last-occurrence-in-array)
  - [19. Binary Search](#19-binary-search-using-recursion)
- [Level 6: Strings Recursion](#level-6-strings-recursion)
  - [20. Reverse a String](#20-reverse-a-string)
  - [21. Check Palindrome (String)](#21-check-palindrome-string)
  - [22. Remove Duplicates from String](#22-remove-duplicates-from-string)
  - [23. Move All 'x' to End](#23-move-all-x-to-end-of-string)
- [Level 7: Subsequence Pattern](#level-7-subsequence-pattern-very-important)
  - [24. Generate All Subsequences](#24-generate-all-subsequences)
  - [25. Subsequences with Sum = K](#25-generate-subsequences-with-sum--k)
  - [26. Count Subsequences with Sum = K](#26-count-subsequences-with-sum--k)
- [Level 8: Recursion + Backtracking Basics](#level-8-recursion--backtracking-basics)
  - [27. Generate All Binary Strings (No Consecutive 1s)](#27-generate-all-binary-strings-no-consecutive-1s)
  - [28. Generate Valid Parentheses](#28-generate-valid-parentheses)
- [Level 9: Classic Recursion Problems](#level-9-classic-recursion-problems)
  - [29. Tower of Hanoi](#29-tower-of-hanoi)
- [Level 10: Advanced Backtracking](#level-10-advanced-backtracking)
  - [30. Subsets](#30-subsets)
  - [31. Subsets II](#31-subsets-ii)
  - [32. Permutations](#32-permutations)
  - [33. Combination Sum](#33-combination-sum)
  - [34. Palindrome Partitioning](#34-palindrome-partitioning)
  - [35. N-Queens](#35-n-queens)
  - [36. Sudoku Solver](#36-sudoku-solver)
  - [37. Rat in a Maze](#37-rat-in-a-maze)
  - [38. Knight's Tour](#38-knights-tour)
- [Level 11: Recursion + Divide & Conquer](#level-11-recursion--divide--conquer)
  - [39. Merge Sort](#39-merge-sort)
  - [40. Quick Sort](#40-quick-sort)
  - [41. Count Inversions](#41-count-inversions)

---

## **Level 1: Recursion Basics (Foundation)**

### 1. Print 1 to N using Recursion

```java
void print1ToN(int i, int n) {
    if (i > n) return;
    System.out.print(i + " ");
    print1ToN(i + 1, n);
}
// call: print1ToN(1, n);
```

---

### 2. Print N to 1 using Recursion

```java
void printNTo1(int n) {
    if (n == 0) return;
    System.out.print(n + " ");
    printNTo1(n - 1);
}
```

---

### 3. Sum of First N Numbers

```java
int sum(int n) {
    if (n == 0) return 0;
    return n + sum(n - 1);
}
```

---

### 4. Factorial of a Number

```java
int factorial(int n) {
    if (n == 0 || n == 1) return 1;
    return n * factorial(n - 1);
}

int factorial(int n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);
}
```

---

### 5. Print Even or Odd using Recursion

```java
class Solution {

    static void printEven(int current, int n) {
        if (current > n) return;

        System.out.print(current + " ");
        printEven(current + 2, n);
    }

    static void printOdd(int current, int n) {
        if (current > n) return;

        System.out.print(current + " ");
        printOdd(current + 2, n);
    }

    public static void main(String[] args) {
        int n = 7;

        System.out.print("Even: ");
        printEven(0, n);

        System.out.print("\nOdd: ");
        printOdd(1, n);
    }
}
```

---

### 6. Power Function (x^n)

```java
double power(double x, int n) {
    if (n == 0) return 1;
    if (n < 0) return 1.0 / power(x, -n);
    if (n % 2 == 0) {
        double half = power(x, n / 2);
        return half * half;
    }
    return x * power(x, n - 1);
}
```

---

## **Level 2: Number & Digit Problems**

### 7. Count Digits in a Number

```java
class Solution {
    static int countDigits(int n) {
        n = Math.abs(n);

        if (n < 10) return 1;

        return 1 + countDigits(n / 10);
    }
}


class Solution {
    static int countDigits(int n) {
        n = Math.abs(n);

        if (n == 0) return 1;

        int count = 0;
        while (n > 0) {
            count++;
            n /= 10;
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(countDigits(12345)); // 5
        System.out.println(countDigits(-98));   // 2
        System.out.println(countDigits(0));     // 1
    }
}
```

---

### 8. Sum of Digits of a Number

```java
int sumOfDigits(int n) {
    if (n < 10) return n;
    return (n % 10) + sumOfDigits(n / 10);
}
```

---

### 9. Product of Digits of a Number

```java
int productOfDigits(int n) {
    if (n < 10) return n;
    return (n % 10) * productOfDigits(n / 10);
}
```

---

## **Level 3: Fibonacci (Core Recursion Understanding)**

### 10. Print Fibonacci Series up to N

```java
void printFib(int a, int b, int n) {
    if (a > n) return;
    System.out.print(a + " ");
    printFib(b, a + b, n);
}
// call: printFib(0, 1, n);

class Solution {
    static void printFibUpToN(int n) {
        if (n < 0) return;

        int a = 0, b = 1;

        while (a <= n) {
            System.out.print(a + " ");
            int next = a + b;
            a = b;
            b = next;
        }
    }

    public static void main(String[] args) {
        int n = 10;
        printFibUpToN(n);
    }
}
```

---

### 11. Find K-th Fibonacci Number

```java
int fib(int k) {
    if (k <= 1) return k;
    return fib(k - 1) + fib(k - 2);
}

int fib(int k) {
    
    if (k == 0) return 0;
    if (k == 1) return 1;

    return fib(k - 1) + fib(k - 2);
}
```

---

## **Level 4: Arrays Basics with Recursion**

### 12. Check if Array is Sorted (Ascending/Descending)

```java
class Solution {

    static boolean isSortedAsc(int[] arr) {
        if (arr == null || arr.length <= 1) return true;

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}

class Solution {

    static boolean isSortedDesc(int[] arr) {
        if (arr == null || arr.length <= 1) return true;

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] < arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}

boolean isSortedAsc(int[] arr, int i) {
    if (i == arr.length - 1) return true;
    return arr[i] <= arr[i + 1] && isSortedAsc(arr, i + 1);
}

boolean isSortedDesc(int[] arr, int i) {
    if (i == arr.length - 1) return true;
    return arr[i] >= arr[i + 1] && isSortedDesc(arr, i + 1);
}
// call: isSortedAsc(arr, 0);
```

---

### 13. Find Maximum in Array

```java
int findMax(int[] arr, int i) {
    if (i == arr.length - 1) return arr[i];
    return Math.max(arr[i], findMax(arr, i + 1));
}
// call: findMax(arr, 0);

static int findMax(int[] arr, int i) {
    // base case: reached index 0
    if (i == 0) return arr[0];
    // recursive call: move left
    int maxFromLeft = findMax(arr, i - 1);
    return Math.max(arr[i], maxFromLeft);
}
```

---

### 14. Find Minimum in Array

```java
int findMin(int[] arr, int i) {
    if (i == arr.length - 1) return arr[i];
    return Math.min(arr[i], findMin(arr, i + 1));
}
// call: findMin(arr, 0);
```

---

### 15. Reverse an Array

```java
void reverse(int[] arr, int l, int r) {
    if (l >= r) return;
    int tmp = arr[l]; arr[l] = arr[r]; arr[r] = tmp;
    reverse(arr, l + 1, r - 1);
}
// call: reverse(arr, 0, arr.length - 1);

static void reverse(int[] arr, int l, int r) {
    while (l < r) {
        int tmp = arr[l];
        arr[l] = arr[r];
        arr[r] = tmp;
        l++;
        r--;
    }
}
```

---

## **Level 5: Searching (Recursion)**

### 16. Linear Search using Recursion

```java
int linearSearch(int[] arr, int i, int target) {
    if (i == arr.length) return -1;
    if (arr[i] == target) return i;
    return linearSearch(arr, i + 1, target);
}
// call: linearSearch(arr, 0, target);
```

---

### 17. First Occurrence in Array

```java
int firstOccurrence(int[] arr, int i, int target) {
    if (i == arr.length) return -1;
    if (arr[i] == target) return i;
    return firstOccurrence(arr, i + 1, target);
}
```

---

### 18. Last Occurrence in Array

```java
class Solution {

    static int lastOccurrence(int[] arr, int i, int target) {
        if (i < 0) return -1;

        if (arr[i] == target) return i;

        return lastOccurrence(arr, i - 1, target);
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 2, 4};

        System.out.println(lastOccurrence(arr, arr.length - 1, 2)); // 3
        System.out.println(lastOccurrence(arr, arr.length - 1, 5)); // -1
    }
}


int lastOccurrence(int[] arr, int i, int target) {
    if (i == arr.length) return -1;
    int rest = lastOccurrence(arr, i + 1, target);
    return rest != -1 ? rest : (arr[i] == target ? i : -1);
}
// call: lastOccurrence(arr, 0, target);
```

---

### 19. Binary Search using Recursion

```java
int binarySearch(int[] arr, int lo, int hi, int target) {
    if (lo > hi) return -1;
    int mid = lo + (hi - lo) / 2;
    if (arr[mid] == target) return mid;
    if (arr[mid] > target) return binarySearch(arr, lo, mid - 1, target);
    return binarySearch(arr, mid + 1, hi, target);
}
// call: binarySearch(arr, 0, arr.length - 1, target);
```

---

## **Level 6: Strings Recursion**

### 20. Reverse a String

```java
class Solution {

    // 1. Recursive (your approach - not optimal due to substring)
    static String reverseRecursive(String s) {
        if (s.isEmpty()) return s;
        return reverseRecursive(s.substring(1)) + s.charAt(0);
    }

    // 2. Iterative (Two-pointer - best control)
    static String reverseTwoPointer(String s) {
        char[] arr = s.toCharArray();
        int l = 0, r = arr.length - 1;

        while (l < r) {
            char tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
            l++;
            r--;
        }

        return new String(arr);
    }

    // 3. StringBuilder (built-in reverse)
    static String reverseSB(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    // 4. Manual StringBuilder (interview-friendly)
    static String reverseManualSB(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = s.length() - 1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String input = "hello";

        System.out.println("Input: " + input);

        System.out.println("Recursive       : " + reverseRecursive(input));
        System.out.println("Two Pointer     : " + reverseTwoPointer(input));
        System.out.println("StringBuilder   : " + reverseSB(input));
        System.out.println("Manual SB       : " + reverseManualSB(input));
    }
}
```

---

### 21. Check Palindrome (String)

```java
boolean isPalindrome(String s, int l, int r) {
    if (l >= r) return true;
    if (s.charAt(l) != s.charAt(r)) return false;
    return isPalindrome(s, l + 1, r - 1);
}
// call: isPalindrome(s, 0, s.length() - 1);
    
// 2. Iterative (Two-pointer - best)
static boolean isPalindromeTwoPointer(String s) {
    int l = 0, r = s.length() - 1;
    while (l < r) {
        if (s.charAt(l) != s.charAt(r)) return false;
        l++;
        r--;
    }
    return true;
}
```

---

<!-- ### 22. Remove Duplicates from String

```java
String removeDuplicates(String s, boolean[] visited) {
    if (s.isEmpty()) return "";
    char c = s.charAt(0);
    if (visited[c - 'a']) return removeDuplicates(s.substring(1), visited);
    visited[c - 'a'] = true;
    return c + removeDuplicates(s.substring(1), visited);
}
// call: removeDuplicates(s, new boolean[26]);
``` -->

---

### 23. Move All 'x' to End of String

```java
String moveXToEnd(String s) {
    if (s.isEmpty()) return s;
    String rest = moveXToEnd(s.substring(1));
    return s.charAt(0) == 'x' ? rest + 'x' : s.charAt(0) + rest;
}

static String moveXToEnd(String s) {
    StringBuilder sb = new StringBuilder();
    int countX = 0;
    for (char ch : s.toCharArray()) {
        if (ch == 'x') {
            countX++;
        } else {
            sb.append(ch);
        }
    }
    // append all 'x' at the end
    while (countX-- > 0) {
        sb.append('x');
    }
    return sb.toString();
}
```
---

## **Level 7: Subsequence Pattern (VERY IMPORTANT)**

### 24. Generate All Subsequences
```text id="2zxj2f"
arr = [1,2,3]
```

Output:

```text id="7mdpj7"
[]
[1]
[2]
[3]
[1,2]
[1,3]
[2,3]
[1,2,3]
```
```text id="6drs5x"
                    []
               /          \
           take1         notTake1
            /                \
        [1]                  []
       /   \                /   \
   [1,2] [1]             [2]   []
```
```java
//1
void subsequences(int[] arr, int i, List<Integer> current, List<List<Integer>> result) {
    if (i == arr.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    current.add(arr[i]);
    subsequences(arr, i + 1, current, result);
    current.remove(current.size() - 1);
    subsequences(arr, i + 1, current, result);
}// call: subsequences(arr, 0, new ArrayList<>(), result);

//2
static void subseqRecursive(String s, int i, String current, List<String> result) {
    if (i == s.length()) {
        result.add(current);
        return;
    }

    // pick
    subseqRecursive(s, i + 1, current + s.charAt(i), result);

    // not pick
    subseqRecursive(s, i + 1, current, result);
}

//3
static void subseqSB(String s, int i, StringBuilder sb, List<String> result) {
    if (i == s.length()) {
        result.add(sb.toString());
        return;
    }

    // pick
    sb.append(s.charAt(i));
    subseqSB(s, i + 1, sb, result);

    // backtrack
    sb.deleteCharAt(sb.length() - 1);

    // not pick
    subseqSB(s, i + 1, sb, result);
}
```

---

### 25. Generate Subsequences with Sum = K
```text id="gv4uvv"
arr = [1, 2, 1]
K = 2
```

Output:

```text id="bnixom"
[1, 1]
[2]
```
```text id="d7h4ll"
                         []
                    /          \
                 take1        notTake1
                 sum=1
               /                 \
           take2                notTake2
           sum=3                sum=1
          /    \               /      \
      take1   notTake1     take1    notTake1
      sum=4    sum=3       sum=2      sum=1
                              |
                           VALID
```

```java
void subsWithSumK(int[] arr, int i, int k, List<Integer> current) {
    if (i == arr.length) {
        if (k == 0) System.out.println(current);
        return;
    }
    current.add(arr[i]);
    subsWithSumK(arr, i + 1, k - arr[i], current);
    current.remove(current.size() - 1);
    subsWithSumK(arr, i + 1, k, current);
}

// -------------------------------------------------
// 2. Print ONLY ONE Subsequence (early stop)
// -------------------------------------------------
static boolean subseqOne(int[] arr, int i, int sum, int K, List<Integer> curr) {
    if (i == arr.length) {
        if (sum == K) {
            System.out.println(curr);
            return true;
        }
        return false;
    }

    // pick
    curr.add(arr[i]);
    if (subseqOne(arr, i + 1, sum + arr[i], K, curr)) return true;

    curr.remove(curr.size() - 1);

    // not pick
    if (subseqOne(arr, i + 1, sum, K, curr)) return true;

    return false;
}
```

---

### 26. Count Subsequences with Sum = K

```java
static int subseqCount(int[] arr, int i, int sum, int K) {
    if (i == arr.length) {
        return sum == K ? 1 : 0;
    }

    int pick = subseqCount(arr, i + 1, sum + arr[i], K);
    int notPick = subseqCount(arr, i + 1, sum, K);

    return pick + notPick;
}
```

---

## **Level 8: Recursion + Backtracking Basics**

### 27. Generate All Binary Strings (No Consecutive 1s)

```java
void binaryStrings(char[] arr, int i, int n) {
    if (i == n) {
        System.out.println(new String(arr));
        return;
    }
    arr[i] = '0';
    binaryStrings(arr, i + 1, n);
    if (i == 0 || arr[i - 1] == '0') {
        arr[i] = '1';
        binaryStrings(arr, i + 1, n);
    }
}
// call: binaryStrings(new char[n], 0, n);
```

---

### 28. Generate Valid Parentheses

```java
public class GenerateValidParentheses {

    // Recursive function
    static void validParentheses(int open, int close, int n, String current) {

        // Base case:
        // If current string length becomes 2*n,
        // it means we used all brackets
        if (current.length() == 2 * n) {
            System.out.println(current);
            return;
        }

        // Add opening bracket if open count is less than n
        if (open < n) {
            validParentheses(open + 1, close, n, current + "(");
        }

        // Add closing bracket only if close count is less than open
        // This keeps the parentheses valid
        if (close < open) {
            validParentheses(open, close + 1, n, current + ")");
        }
    }

    public static void main(String[] args) {

        int n = 3;

        System.out.println("Valid Parentheses for n = " + n);

        // Initial call
        validParentheses(0, 0, n, "");
    }
}
```
```
Valid Parentheses for n = 3
((()))
(()())
(())()
()(())
()()()
```

---

## **Level 9: Classic Recursion Problems**

### 29. Tower of Hanoi

```java
void hanoi(int n, char from, char to, char aux) {
    if (n == 0) return;
    hanoi(n - 1, from, aux, to);
    System.out.println("Move disk " + n + " from " + from + " to " + to);
    hanoi(n - 1, aux, to, from);
}
```

---

## **Level 10: Advanced Backtracking**

### 30. Subsets

* **Subset:** choose any elements, order does not matter.
* **Subsequence:** choose elements in original order, skipping allowed.
* Example: `[2,1]` is a valid subset of `[1,2]`, but not a valid subsequence.


```java
static void printSubsets(int[] arr, int i, List<Integer> curr) {
    if (i == arr.length) {
        System.out.println(curr);
        return;
    }

    // pick
    curr.add(arr[i]);
    printSubsets(arr, i + 1, curr);

    // backtrack
    curr.remove(curr.size() - 1);

    // not pick
    printSubsets(arr, i + 1, curr);
}

//2
void subsets(int[] nums, int i, List<Integer> current, List<List<Integer>> result) {
    result.add(new ArrayList<>(current));
    for (int j = i; j < nums.length; j++) {
        current.add(nums[j]);
        subsets(nums, j + 1, current, result);
        current.remove(current.size() - 1);
    }
}
// call: subsets(nums, 0, new ArrayList<>(), result);
```
```
[]
[1]
[1, 2]
[1, 2, 3]
[1, 3]
[2]
[2, 3]
[3]
```

---

### 31. Subsets II
```text id="65xtzi"
[1,2,2]
```
```text id="qkg1jj"
[]
[1]
[2]
[1,2]
[2,2]
[1,2,2]
```
```java
// Given an integer array nums that may contain duplicates, return all possible subsets (the power set).
// The solution set must not contain duplicate subsets. Return the solution in any order.
    public List<List<Integer>> subsetsII(int[] nums) {
        Arrays.sort(nums); // important
        List<List<Integer>> result = new ArrayList<>();
        backtrack(0, nums, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int index, int[] nums, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));

        for (int i = index; i < nums.length; i++) {
            // skip duplicates
            if (i > index && nums[i] == nums[i - 1]) continue;

            current.add(nums[i]);
            backtrack(i + 1, nums, current, result);
            current.remove(current.size() - 1);
        }
    }
// call: Arrays.sort(nums); subsetsII(nums, 0, new ArrayList<>(), result);

    static void subsets(int[] nums, int i,
                        List<Integer> current,
                        Set<List<Integer>> result) {
                        }// will also works but needed sort in interview
```

---

### 32. Permutations

```java
import java.util.*;

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        backtrack(nums, visited, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int[] nums, boolean[] visited, List<Integer> curr, List<List<Integer>> result) {
        if (curr.size() == nums.length) {
            result.add(new ArrayList<>(curr));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) continue;

            visited[i] = true;
            curr.add(nums[i]);

            backtrack(nums, visited, curr, result);

            curr.remove(curr.size() - 1);
            visited[i] = false;
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] nums = {1, 2, 3};
        System.out.println(s.permute(nums));
    }
}
```
#### Approach 2: In-place Swapping (Most Optimal)
```java
import java.util.*;

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(0, nums, result);
        return result;
    }

    private void backtrack(int index, int[] nums, List<List<Integer>> result) {
        if (index == nums.length) {
            List<Integer> list = new ArrayList<>();
            for (int num : nums) list.add(num);
            result.add(list);
            return;
        }

        for (int i = index; i < nums.length; i++) {
            swap(nums, index, i);
            backtrack(index + 1, nums, result);
            swap(nums, index, i); // backtrack
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```
### 2. Permutations of a String

### Approach 1: Using String + Visited

```java
import java.util.*;

class Solution {
    public List<String> permuteString(String s) {
        List<String> result = new ArrayList<>();
        boolean[] visited = new boolean[s.length()];
        backtrack(s, visited, new StringBuilder(), result);
        return result;
    }

    private void backtrack(String s, boolean[] visited, StringBuilder curr, List<String> result) {
        if (curr.length() == s.length()) {
            result.add(curr.toString());
            return;
        }

        for (int i = 0; i < s.length(); i++) {
            if (visited[i]) continue;

            visited[i] = true;
            curr.append(s.charAt(i));

            backtrack(s, visited, curr, result);

            curr.deleteCharAt(curr.length() - 1);
            visited[i] = false;
        }
    }
}
```

---

### Approach 2: In-place Swapping (Best)

```java
import java.util.*;

class Solution {
    public List<String> permute(String s) {
        List<String> result = new ArrayList<>();
        char[] arr = s.toCharArray();
        backtrack(0, arr, result);
        return result;
    }

    private void backtrack(int index, char[] arr, List<String> result) {
        if (index == arr.length) {
            result.add(new String(arr));
            return;
        }

        for (int i = index; i < arr.length; i++) {
            swap(arr, index, i);
            backtrack(index + 1, arr, result);
            swap(arr, index, i);
        }
    }

    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
```

---

### 33. Combination Sum
- Input Example

```text id="j57knk"
candidates = [2,3,6,7]
target = 7
```

- Expected Output

```text id="5kwll4"
[
 [2,2,3],
 [7]
]
```

```java
// Problem: Given an array of candidates and a target,
// return all combinations where numbers sum to target.
// Each element can be used multiple times.

// Approach: Backtracking (pick / not pick)
// - pick: include current element (stay at same index)
// - not pick: move to next index
// - stop when target == 0 (valid) or target < 0 / end reached

void combinationSum(int[] candidates, int i, int target,
                    List<Integer> current, List<List<Integer>> result) {

    if (target == 0) {
        result.add(new ArrayList<>(current));
        return;
    }

    if (i == candidates.length || target < 0) return;

    // pick (reuse same element)
    current.add(candidates[i]);
    combinationSum(candidates, i, target - candidates[i], current, result);

    // backtrack
    current.remove(current.size() - 1);

    // not pick (move to next element)
    combinationSum(candidates, i + 1, target, current, result);
}

// call:
// combinationSum(candidates, 0, target, new ArrayList<>(), result);
```

---

### 34. Palindrome Partitioning

```java
void partition(String s, int start, List<String> current, List<List<String>> result) {
    if (start == s.length()) { result.add(new ArrayList<>(current)); return; }
    for (int end = start + 1; end <= s.length(); end++) {
        String sub = s.substring(start, end);
        if (isPalin(sub)) {
            current.add(sub);
            partition(s, end, current, result);
            current.remove(current.size() - 1);
        }
    }
}

boolean isPalin(String s) {
    int l = 0, r = s.length() - 1;
    while (l < r) if (s.charAt(l++) != s.charAt(r--)) return false;
    return true;
}
```

---

### 35. N-Queens

```java
import java.util.*;

class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];

        for (char[] row : board) Arrays.fill(row, '.');

        solve(0, board, result);
        return result;
    }

    private void solve(int row, char[][] board, List<List<String>> result) {
        if (row == board.length) {
            result.add(construct(board));
            return;
        }

        for (int col = 0; col < board.length; col++) {
            if (isSafe(row, col, board)) {
                board[row][col] = 'Q';
                solve(row + 1, board, result);
                board[row][col] = '.';
            }
        }
    }

    private boolean isSafe(int row, int col, char[][] board) {
        // column
        for (int i = 0; i < row; i++)
            if (board[i][col] == 'Q') return false;

        // upper-left diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 'Q') return false;

        // upper-right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < board.length; i--, j++)
            if (board[i][j] == 'Q') return false;

        return true;
    }

    private List<String> construct(char[][] board) {
        List<String> list = new ArrayList<>();
        for (char[] row : board) {
            list.add(new String(row));
        }
        return list;
    }
}
```

---

### 36. Sudoku Solver

```java
class Solution {

    public void solveSudoku(char[][] board) {
        solve(board);
    }

    private boolean solve(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if (board[i][j] == '.') {

                    for (char c = '1'; c <= '9'; c++) {

                        if (isValid(board, i, j, c)) {

                            board[i][j] = c;

                            if (solve(board)) return true;

                            board[i][j] = '.'; // backtrack
                        }
                    }

                    return false; // no valid number
                }
            }
        }

        return true; // solved
    }

    private boolean isValid(char[][] board, int row, int col, char c) {

        for (int i = 0; i < 9; i++) {

            // check row
            if (board[row][i] == c) return false;

            // check column
            if (board[i][col] == c) return false;

            // check 3x3 box
            int r = 3 * (row / 3) + i / 3;
            int c1 = 3 * (col / 3) + i % 3;

            if (board[r][c1] == c) return false;
        }

        return true;
    }
}
```

---

### 37. Rat in a Maze

<!-- ```java
void ratMaze(int[][] maze, int r, int c, int n, String path, List<String> result, boolean[][] visited) {
    if (r == n - 1 && c == n - 1) { result.add(path); return; }
    int[] dr = {1, 0, 0, -1};
    int[] dc = {0, -1, 1, 0};
    char[] dir = {'D', 'L', 'R', 'U'};
    visited[r][c] = true;
    for (int i = 0; i < 4; i++) {
        int nr = r + dr[i], nc = c + dc[i];
        if (nr >= 0 && nc >= 0 && nr < n && nc < n && !visited[nr][nc] && maze[nr][nc] == 1) {
            ratMaze(maze, nr, nc, n, path + dir[i], result, visited);
        }
    }
    visited[r][c] = false;
}
// call: ratMaze(maze, 0, 0, n, "", result, new boolean[n][n]);
``` -->
```java
import java.util.*;

class Solution {

    public ArrayList<String> findPath(int[][] maze, int n) {
        ArrayList<String> result = new ArrayList<>();

        if (maze[0][0] == 0) return result;

        boolean[][] visited = new boolean[n][n];

        solve(0, 0, maze, n, "", result, visited);

        return result;
    }

    private void solve(int i, int j, int[][] maze, int n,
                       String path, ArrayList<String> result,
                       boolean[][] visited) {

        // destination reached
        if (i == n - 1 && j == n - 1) {
            result.add(path);
            return;
        }

        visited[i][j] = true;

        // Down
        if (isSafe(i + 1, j, maze, n, visited)) {
            solve(i + 1, j, maze, n, path + "D", result, visited);
        }

        // Left
        if (isSafe(i, j - 1, maze, n, visited)) {
            solve(i, j - 1, maze, n, path + "L", result, visited);
        }

        // Right
        if (isSafe(i, j + 1, maze, n, visited)) {
            solve(i, j + 1, maze, n, path + "R", result, visited);
        }

        // Up
        if (isSafe(i - 1, j, maze, n, visited)) {
            solve(i - 1, j, maze, n, path + "U", result, visited);
        }

        // backtrack
        visited[i][j] = false;
    }

    private boolean isSafe(int i, int j, int[][] maze, int n, boolean[][] visited) {
        return (i >= 0 && j >= 0 && i < n && j < n &&
                maze[i][j] == 1 && !visited[i][j]);
    }
}
```
---

### 38. Knight's Tour

```java
boolean knightsTour(int[][] board, int r, int c, int move, int n) {
    if (move == n * n) return true;
    int[] dr = {-2, -1, 1, 2, 2, 1, -1, -2};
    int[] dc = {1, 2, 2, 1, -1, -2, -2, -1};
    for (int i = 0; i < 8; i++) {
        int nr = r + dr[i], nc = c + dc[i];
        if (nr >= 0 && nc >= 0 && nr < n && nc < n && board[nr][nc] == -1) {
            board[nr][nc] = move;
            if (knightsTour(board, nr, nc, move + 1, n)) return true;
            board[nr][nc] = -1;
        }
    }
    return false;
}
// init: board[0][0] = 0; knightsTour(board, 0, 0, 1, n);
```

---

## **Level 11: Recursion + Divide & Conquer**

### 39. Merge Sort

```java
void mergeSort(int[] arr, int l, int r) {
    if (l >= r) return;
    int mid = l + (r - l) / 2;
    mergeSort(arr, l, mid);
    mergeSort(arr, mid + 1, r);
    merge(arr, l, mid, r);
}

void merge(int[] arr, int l, int mid, int r) {
    int[] tmp = new int[r - l + 1];
    int i = l, j = mid + 1, k = 0;
    while (i <= mid && j <= r) tmp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
    while (i <= mid) tmp[k++] = arr[i++];
    while (j <= r) tmp[k++] = arr[j++];
    for (int x = 0; x < tmp.length; x++) arr[l + x] = tmp[x];
}
```

---

### 40. Quick Sort

```java
void quickSort(int[] arr, int l, int r) {
    if (l >= r) return;
    int p = partition(arr, l, r);
    quickSort(arr, l, p - 1);
    quickSort(arr, p + 1, r);
}

int partition(int[] arr, int l, int r) {
    int pivot = arr[r], i = l - 1;
    for (int j = l; j < r; j++) {
        if (arr[j] <= pivot) { i++; int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp; }
    }
    int tmp = arr[i + 1]; arr[i + 1] = arr[r]; arr[r] = tmp;
    return i + 1;
}
```

---

### 41. Count Inversions

#### Problem Statement

Given an array `arr[]`:

```text id="mjlwmq"
Count pairs (i, j)
such that:
```

```text id="thjlwm"
i < j
AND
arr[i] > arr[j]
```
---

#### Example 1

Input:

```text id="0uq3jq"
arr = [2, 4, 1, 3, 5]
```

Inversions:

```text id="tt0pvl"
(2,1)
(4,1)
(4,3)
```

Output:

```text id="a4g8e4"
3
```
#### Java Code — Brute Force

```java id="z9wjlwm"
import java.util.*;

class Solution {

    static int countInversions(int[] arr) {

        int n = arr.length;

        int count = 0;

        for (int i = 0; i < n; i++) {

            for (int j = i + 1; j < n; j++) {

                if (arr[i] > arr[j]) {
                    count++;
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {

        int[] arr = {2, 4, 1, 3, 5};

        System.out.println(countInversions(arr));
    }
}
```

```java
long mergeCount(int[] arr, int l, int r) {
    if (l >= r) return 0;
    int mid = l + (r - l) / 2;
    long count = mergeCount(arr, l, mid) + mergeCount(arr, mid + 1, r);
    int[] tmp = new int[r - l + 1];
    int i = l, j = mid + 1, k = 0;
    while (i <= mid && j <= r) {
        if (arr[i] <= arr[j]) tmp[k++] = arr[i++];
        else { count += (mid - i + 1); tmp[k++] = arr[j++]; }
    }
    while (i <= mid) tmp[k++] = arr[i++];
    while (j <= r) tmp[k++] = arr[j++];
    for (int x = 0; x < tmp.length; x++) arr[l + x] = tmp[x];
    return count;
}
// call: mergeCount(arr, 0, arr.length - 1);
```

---
