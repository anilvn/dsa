## Equal Stacks

### Problem Statement

Given 3 stacks of cylinders with different heights, remove cylinders from the top of stacks so that all 3 stacks become equal in height. Return the maximum possible equal height.

---

### Example

#### Sample Input

```text
stack1 = [3,2,1,1,1]
stack2 = [4,3,2]
stack3 = [1,1,4,1]
```

#### Sample Output

```text
5
```

---

### Idea/Intution

* Find total height of all 3 stacks.
* Compare heights:

  * Remove from the tallest stack.
* Continue until:

  * all heights become equal
  * or one stack becomes empty.
* Since each cylinder is removed at most once → efficient solution.

---

### Java code

```java
import java.util.*;

public class EqualStacks {

    public static int equalStacks(List<Integer> h1, List<Integer> h2, List<Integer> h3) {

        int sum1 = h1.stream().mapToInt(Integer::intValue).sum();
        int sum2 = h2.stream().mapToInt(Integer::intValue).sum();
        int sum3 = h3.stream().mapToInt(Integer::intValue).sum();

        int i = 0, j = 0, k = 0;

        while (true) {

            // one stack empty
            if (i == h1.size() || j == h2.size() || k == h3.size()) {
                return 0;
            }

            // equal height found
            if (sum1 == sum2 && sum2 == sum3) {
                return sum1;
            }

            // remove from tallest stack
            if (sum1 >= sum2 && sum1 >= sum3) {
                sum1 -= h1.get(i++);
            } else if (sum2 >= sum1 && sum2 >= sum3) {
                sum2 -= h2.get(j++);
            } else {
                sum3 -= h3.get(k++);
            }
        }
    }

    public static void main(String[] args) {

        List<Integer> h1 = Arrays.asList(3, 2, 1, 1, 1);
        List<Integer> h2 = Arrays.asList(4, 3, 2);
        List<Integer> h3 = Arrays.asList(1, 1, 4, 1);

        System.out.println(equalStacks(h1, h2, h3));
    }
}
```

```text
Time Complexity: O(n1 + n2 + n3)

Space Complexity: O(1)
```

```java
public int equalStacks(int[] h1, int[] h2, int[] h3) {

    int maxHeight = 0;

    Stack<Integer> st1 = new Stack<>();
    Stack<Integer> st2 = new Stack<>();
    Stack<Integer> st3 = new Stack<>();

    fillStacks(st1, h1);
    fillStacks(st2, h2);
    fillStacks(st3, h3);

    // Continue until any stack becomes empty
    while (!st1.isEmpty() && !st2.isEmpty() && !st3.isEmpty()) {

        int stack1Height = st1.peek();
        int stack2Height = st2.peek();
        int stack3Height = st3.peek();

        // Equal height found
        if (stack1Height == stack2Height &&
            stack2Height == stack3Height) {

            maxHeight = stack1Height;
            break;
        }

        // Remove from tallest stack
        if (stack1Height >= stack2Height &&
            stack1Height >= stack3Height) {

            st1.pop();

        } else if (stack2Height >= stack1Height &&
                   stack2Height >= stack3Height) {

            st2.pop();

        } else {
            st3.pop();
        }
    }

    return maxHeight;
}

private void fillStacks(Stack<Integer> stack, int[] arr) {

    int sum = 0;

    // Build cumulative heights from bottom
    for (int i = arr.length - 1; i >= 0; i--) {
        sum += arr[i];
        stack.push(sum);
    }
}
```

<br/><br/><br/>

---

## Find Numbers Missing Between Two Arrays (Considering Frequency)


### Explanation

You are given two integer arrays `A` and `B`.
Each number can appear multiple times.

Task is to find:

* Numbers present in `A` but missing or less frequent in `B`
* Numbers present in `B` but missing or less frequent in `A`

In other words, compare **frequency counts**, not just presence.

---

### Example

**Input:**
A = [1, 2, 2, 3, 4]
B = [2, 3, 3, 5]

**Output:**
Missing from B → [1, 2, 4]
Missing from A → [3, 5]

---

### Approach (Frequency Map Difference)

#### Key Idea:

Use a HashMap to store frequency difference.

#### Steps:

1. Count frequency of elements in `A`
2. Subtract frequency using elements of `B`
3. Elements with positive count → missing from B
4. Elements with negative count → missing from A
5. Convert results into lists

#### Why this works:

* Positive frequency → extra in A
* Negative frequency → extra in B
* Zero → balanced

---

### Code (Java)

```java
import java.util.*;

public class MissingNumbersFrequency {

    public static List<List<Integer>> findMissing(int[] A, int[] B) {
        Map<Integer, Integer> freq = new HashMap<>();

        // Add frequencies from A
        for (int num : A) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        // Subtract frequencies using B
        for (int num : B) {
            freq.put(num, freq.getOrDefault(num, 0) - 1);
        }

        List<Integer> missingFromB = new ArrayList<>();
        List<Integer> missingFromA = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            int key = entry.getKey();
            int count = entry.getValue();

            if (count > 0) {
                missingFromB.add(key);
            } else if (count < 0) {
                missingFromA.add(key);
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        result.add(missingFromB);
        result.add(missingFromA);

        return result;
    }

    public static void main(String[] args) {
        int[] A = {1, 2, 2, 3, 4};
        int[] B = {2, 3, 3, 5};

        List<List<Integer>> result = findMissing(A, B);

        System.out.println("Missing from B: " + result.get(0));
        System.out.println("Missing from A: " + result.get(1));
    }
}
```

---

### Time Complexity:

O(n + m) → where n = size of A, m = size of B

### Space Complexity:

O(k) → number of unique elements

---

If you want, I can also show:

* Sorting-based version (LeetCode style minimal memory)
* XOR-based intuition (when duplicates NOT involved)
* Or interview optimized “one-pass diff trick”






<br/><br/><br/>

---
## Number of Good Pairs

---

### Problem Statement

Given an integer array `nums`, count the number of good pairs.

A pair `(i, j)` is good if:

* `nums[i] == nums[j]`
* `i < j`

---

### Example

* Input:
  nums = [1, 2, 3, 1, 1, 3]

* Output:
  4

* Explanation:
  Good pairs →
  (0,3), (0,4), (3,4), (2,5)

---

### Example

* Input:
  nums = [1,1,1,1]

* Output:
  6

* Explanation:
  Good pairs →
  (0,1), (0,2), (0,3), (1,2), (1,3), (2,3)

---

### Idea/Intuition

* Same numbers can form pairs with previous occurrences
* Use HashMap to store frequency of each number
* When a number appears again:

  * existing frequency = number of new pairs formed

**Steps:**

1. Traverse array
2. Get current frequency of number
3. Add frequency to answer
4. Increase frequency in map

---

### Java Code

#### Brute Force Approach

```java id="goodpairs_bf"
public class GoodPairsBrute {

    public static int numIdenticalPairs(int[] nums) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {

                // Good pair
                if (nums[i] == nums[j]) {
                    count++;
                }
            }
        }

        return count;
    }
}
```

* Time Complexity: **O(n²)**
* Space Complexity: **O(1)**

---

#### Optimized Approach (HashMap)

```java id="goodpairs_opt"
import java.util.*;

public class GoodPairsOptimized {

    public static int numIdenticalPairs(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;

        for (int num : nums) {

            // Existing frequency forms new pairs
            count += map.getOrDefault(num, 0);

            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        return count;
    }
}
```

* Time Complexity: **O(n)**
* Space Complexity: **O(n)**




<br/><br/><br/>

---


## Smaller Numbers Than Current Number

---

### Problem Statement

Given an integer array `nums`, for each element find how many numbers in the array are smaller than it.

Return the result array.

---

### Example

* Input:
  nums = [8,1,2,2,3]

* Output:
  [4,0,1,1,3]

* Explanation:

* 8 → 4 numbers smaller

* 1 → 0 numbers smaller

* 2 → 1 number smaller

* 2 → 1 number smaller

* 3 → 3 numbers smaller

---

### Idea/Intuition

#### Brute Force

* Compare every element with all other elements
* Count smaller values

#### Optimized

* Sort a copy of array
* Store first occurrence index in HashMap
* First index in sorted array = count of smaller numbers

**Steps:**

1. Copy and sort array
2. Store first position of each number
3. Traverse original array
4. Replace with mapped index

---

### Java Code

#### Brute Force Approach

```java id="smaller_bf"
public class SmallerNumbersBrute {

    public static int[] smallerNumbersThanCurrent(int[] nums) {
        int[] result = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            int count = 0;

            for (int j = 0; j < nums.length; j++) {

                // Count smaller elements
                if (nums[j] < nums[i]) {
                    count++;
                }
            }

            result[i] = count;
        }

        return result;
    }
}
```

* Time Complexity: **O(n²)**
* Space Complexity: **O(1)**

---

#### Optimized Approach (Sorting + HashMap)

```java id="smaller_opt"
import java.util.*;

public class SmallerNumbersOptimized {

    public static int[] smallerNumbersThanCurrent(int[] nums) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        Map<Integer, Integer> map = new HashMap<>();

        // First index = smaller count
        for (int i = 0; i < sorted.length; i++) {
            map.putIfAbsent(sorted[i], i);
        }

        int[] result = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            result[i] = map.get(nums[i]);
        }

        return result;
    }
}
```

* Time Complexity: **O(n log n)**
* Space Complexity: **O(n)**




<br/><br/><br/>

---
## Search Insert Position

---

### Problem Statement

Given a sorted array and a target value, return:

* the index if target exists
* otherwise the index where it should be inserted

Array must remain sorted after insertion.

---

### Example

* Input:
  nums = [1,3,5,6], target = 5

* Output:
  2

---

### Example

* Input:
  nums = [1,3,5,6], target = 2

* Output:
  1

---

### Example

* Input:
  nums = [1,3,5,6], target = 7

* Output:
  4

---

### Idea/Intuition

#### Brute Force

* Traverse array linearly
* First element greater than or equal to target = answer

#### Optimized

* Use Binary Search
* If target found → return index
* Otherwise, insertion position will be `left` pointer

**Steps:**

1. Apply binary search
2. Move left/right based on comparison
3. Return `left` after loop ends

---

### Java Code

#### Brute Force Approach

```java id="searchinsert_bf"
public class SearchInsertBrute {

    public static int searchInsert(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {

            // Insert position found
            if (nums[i] >= target) {
                return i;
            }
        }

        return nums.length;
    }
}
```

* Time Complexity: **O(n)**
* Space Complexity: **O(1)**

---

#### Optimized Approach (Binary Search)

```java id="searchinsert_opt"
public class SearchInsertOptimized {

    public static int searchInsert(int[] nums, int target) {

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // Correct insert position
        return left;
    }
}
```

* Time Complexity: **O(log n)**
* Space Complexity: **O(1)**




<br/><br/><br/>

---


## Find Disappeared Numbers

---

### Problem Statement

Given an integer array `nums` of size `n` where numbers are in range `1 to n`, return all numbers that are missing from the array.

Some numbers may appear twice while others are missing.

---

### Example

* Input:
  nums = [4,3,2,7,8,2,3,1]

* Output:
  [5,6]

---

### Idea/Intuition

#### Brute Force

* For every number from `1 to n`
* Check whether it exists in array
* If not present, add to result

#### Optimized

* Use array indices as markers
* For each value `x`, mark index `x-1` as visited by making it negative
* Positive indices after traversal represent missing numbers

**Steps:**

1. Traverse array
2. Mark visited index as negative
3. Traverse again
4. Positive index → missing number

---

### Java Code

#### Brute Force Approach

```java id="findmissing_bf"
import java.util.*;

public class FindDisappearedNumbersBrute {

    public static List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();

        for (int i = 1; i <= nums.length; i++) {

            boolean found = false;

            for (int num : nums) {
                if (num == i) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                result.add(i);
            }
        }

        return result;
    }
}
```

* Time Complexity: **O(n²)**
* Space Complexity: **O(1)**

---

#### Optimized Approach (Index Marking)

```java id="findmissing_opt"
import java.util.*;

public class FindDisappearedNumbersOptimized {

    public static List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {

            int index = Math.abs(nums[i]) - 1;

            // Mark visited
            if (nums[index] > 0) {
                nums[index] = -nums[index];
            }
        }

        for (int i = 0; i < nums.length; i++) {

            // Positive means missing
            if (nums[i] > 0) {
                result.add(i + 1);
            }
        }

        return result;
    }
}
```
```java
        /*
            Example Traversal

            nums = [4,3,2,7,8,2,3,1]

            i=0 -> value=4 -> index=3
            mark nums[3] negative

            [4,3,2,-7,8,2,3,1]

            i=1 -> value=3 -> index=2

            [4,3,-2,-7,8,2,3,1]

            i=2 -> value=-2
            abs(-2)=2 -> index=1

            [4,-3,-2,-7,8,2,3,1]

            Continue...

            Final array:

            [-4,-3,-2,-7,8,2,-3,-1]
        */
```

* Time Complexity: **O(n)**
* Space Complexity: **O(1)** (excluding output array)




<br/><br/><br/>

---

## Top K Frequent Elements

---

### Problem Statement

Given an integer array `nums` and an integer `k`, return the `k` most frequent elements.

Order of output does not matter.

---

### Example

* Input:
  nums = [1,1,1,2,2,3], k = 2

* Output:
  [1,2]

---

### Example

* Input:
  nums = [4,4,4,6,6,1], k = 1

* Output:
  [4]

---

### Idea/Intuition

#### Brute Force

* Count frequency of each number
* Repeatedly find maximum frequency element

#### Optimized

* Use HashMap for frequency count
* Use Max Heap to get highest frequent elements quickly

**Steps:**

1. Store frequencies in HashMap
2. Push entries into Max Heap based on frequency
3. Remove top `k` elements from heap

---

### Java Code

#### Brute Force Approach

```java id="topk_bf"
import java.util.*;

public class TopKFrequentBrute {

    public static int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int[] result = new int[k];

        for (int i = 0; i < k; i++) {

            int maxFreq = 0;
            int element = 0;

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {

                if (entry.getValue() > maxFreq) {
                    maxFreq = entry.getValue();
                    element = entry.getKey();
                }
            }

            result[i] = element;

            // Remove selected element
            map.remove(element);
        }

        return result;
    }
}
```

* Time Complexity: **O(k × n)**
* Space Complexity: **O(n)**

---

#### Optimized Approach (HashMap + Max Heap)

```java id="topk_opt"
import java.util.*;

public class TopKFrequentOptimized {

    static class Pair {
        int num;
        int freq;

        Pair(int num, int freq) {
            this.num = num;
            this.freq = freq;
        }
    }

    public static int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> freqMap = new HashMap<>();

        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // Max Heap based on frequency
        PriorityQueue<Pair> pq =
                new PriorityQueue<>((a, b) -> b.freq - a.freq);

        /*
        PriorityQueue<int[]> pq =
                        new PriorityQueue<>((a, b) -> b[1] - a[1]);
        */

        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {

            // create Pair object and add to heap
            pq.add(new Pair(entry.getKey(), entry.getValue()));

            //  pq.add(new int[]{entry.getKey(), entry.getValue()});
        }

        int[] result = new int[k];

        for (int i = 0; i < k; i++) {

            // poll returns Pair object
            result[i] = pq.poll().num;
        }

        return result;
    }
}


// 2
import java.util.*;

public class TopKFrequentOptimized {

    public static int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> freqMap = new HashMap<>();

        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // Max Heap based on frequency
        PriorityQueue<Integer> pq =
                new PriorityQueue<>((a, b) -> freqMap.get(b) - freqMap.get(a));

        pq.addAll(freqMap.keySet());

        int[] result = new int[k];

        for (int i = 0; i < k; i++) {
            result[i] = pq.poll();
        }

        return result;
    }
}
```

* Time Complexity: **O(n log n)**
* Space Complexity: **O(n)**



<br/><br/><br/>

---


## Squares of a Sorted Array

---

### Problem Statement

Given a sorted integer array `nums` in non-decreasing order, return an array of squares of each number also sorted in non-decreasing order.

---

### Example

* Input:
  nums = [-4,-1,0,3,10]

* Output:
  [0,1,9,16,100]

---

### Example

* Input:
  nums = [-7,-3,2,3,11]

* Output:
  [4,9,9,49,121]

---

### Idea/Intuition

#### Brute Force

* Square every element
* Sort the final array

#### Optimized

* Largest square comes from either:

  * left negative side
  * right positive side
* Use two pointers
* Fill result array from back

**Steps:**

1. Keep pointers at start and end
2. Compare square values
3. Put larger square at end of result
4. Move corresponding pointer

---

### Java Code

#### Brute Force Approach

```java id="squares_bf"
import java.util.*;

public class SortedSquaresBrute {

    public static int[] sortedSquares(int[] nums) {

        int[] result = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            result[i] = nums[i] * nums[i];
        }

        Arrays.sort(result);

        return result;
    }
}
```

* Time Complexity: **O(n log n)**
* Space Complexity: **O(n)**

---

#### Optimized Approach (Two Pointers)

```java id="squares_opt"
public class SortedSquaresOptimized {

    public static int[] sortedSquares(int[] nums) {

        int left = 0;
        int right = nums.length - 1;

        int[] result = new int[nums.length];

        int index = nums.length - 1;

        while (left <= right) {

            int leftSquare = nums[left] * nums[left];
            int rightSquare = nums[right] * nums[right];

            // Larger square goes at end
            if (leftSquare > rightSquare) {
                result[index--] = leftSquare;
                left++;
            } else {
                result[index--] = rightSquare;
                right--;
            }
        }

        return result;
    }
}
```

* Time Complexity: **O(n)**
* Space Complexity: **O(n)**




<br/><br/><br/>

---

## Longest Consecutive Sequence

---

### Problem Statement

Given an unsorted integer array `nums`, return the length of the longest sequence of consecutive elements.

Your algorithm must run in **O(n)** time.

---

### Example

* Input:
  nums = [100,4,200,1,3,2]

* Output:
  4

* Explanation:
  Longest consecutive sequence → [1,2,3,4]

---

### Example

* Input:
  nums = [0,3,7,2,5,8,4,6,0,1]

* Output:
  9

* Explanation:
  Longest consecutive sequence → [0,1,2,3,4,5,6,7,8]

---

### Idea/Intuition

#### Brute Force

* For every element:

  * keep checking next consecutive number
* Very slow because repeated searches happen

#### Optimized

* Store all numbers in HashSet
* Start sequence only if current number is sequence start
* Sequence start means:

```java
num - 1 does not exist
```

* Then expand forward

**Steps:**

1. Insert all numbers into set
2. Check only sequence starting numbers
3. Expand using `num + 1`
4. Track maximum length

---

### Java Code

#### Brute Force Approach

```java id="longestconsecutive_bf"
/*
IMPORTANT:
This problem checks NUMBER continuity,
NOT array position continuity.

WRONG THINKING:
1 should be immediately beside 2 in array

CORRECT THINKING:
1 and 2 can be anywhere in array
*/
public class LongestConsecutiveBrute {

    public static int longestConsecutive(int[] nums) {

        int longest = 0;

        for (int num : nums) {

            int current = num;
            int streak = 1;

            // Search next consecutive numbers
            while (contains(nums, current + 1)) {
                current++;
                streak++;
            }

            longest = Math.max(longest, streak);
        }

        return longest;
    }

    private static boolean contains(int[] nums, int target) {

        for (int num : nums) {
            if (num == target) {
                return true;
            }
        }

        return false;
    }
}
```

* Time Complexity: **O(n²)**
* Space Complexity: **O(1)**

---

#### Optimized Approach (HashSet)

```java id="longestconsecutive_opt"
import java.util.*;

public class LongestConsecutiveOptimized {

    public static int longestConsecutive(int[] nums) {

        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            set.add(num);
        }

        int longest = 0;

        for (int num : set) {

            // Start of sequence
            if (!set.contains(num - 1)) {

                int current = num;
                int streak = 1;

                // Expand sequence
                while (set.contains(current + 1)) {
                    current++;
                    streak++;
                }

                longest = Math.max(longest, streak);
            }
        }

        return longest;
    }
}
```

* Time Complexity: **O(n)**
* Space Complexity: **O(n)**



<br/><br/><br/>

---

## 3Sum Closest

---

### Problem Statement

Given an integer array `nums` and an integer `target`, find three integers whose sum is closest to `target`.

Return the sum of the three integers.

---

### Example

* Input:
  nums = [-1,2,1,-4], target = 1

* Output:
  2

* Explanation:
  Closest triplet sum is:

```text
(-1 + 2 + 1) = 2
```

---

### Idea/Intuition

#### Brute Force

* Generate all possible triplets
* Compute sum
* Track minimum difference from target

#### Optimized

* Sort array
* Fix one element
* Use two pointers for remaining two numbers
* Move pointers based on current sum

**Steps:**

1. Sort array
2. Fix one number
3. Use left/right pointers
4. Update closest sum
5. Move pointers:

   * sum < target → left++
   * sum > target → right--

---

### Java Code

#### Brute Force Approach

```java id="3sumclosest_bf"
public class ThreeSumClosestBrute {

    public static int threeSumClosest(int[] nums, int target) {

        int closestSum = nums[0] + nums[1] + nums[2];

        for (int i = 0; i < nums.length - 2; i++) {

            for (int j = i + 1; j < nums.length - 1; j++) {

                for (int k = j + 1; k < nums.length; k++) {

                    int sum = nums[i] + nums[j] + nums[k];

                    if (Math.abs(target - sum) <
                            Math.abs(target - closestSum)) {

                        closestSum = sum;
                    }
                }
            }
        }

        return closestSum;
    }
}
```

* Time Complexity: **O(n³)**
* Space Complexity: **O(1)**

---

#### Optimized Approach (Sorting + Two Pointers)

```java id="3sumclosest_opt"
import java.util.*;

public class ThreeSumClosestOptimized {

    public static int threeSumClosest(int[] nums, int target) {

        Arrays.sort(nums);

        int closestSum = nums[0] + nums[1] + nums[2];

        for (int i = 0; i < nums.length - 2; i++) {

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {

                int sum = nums[i] + nums[left] + nums[right];

                // Better closer sum found
                if (Math.abs(target - sum) <
                        Math.abs(target - closestSum)) {

                    closestSum = sum;
                }

                if (sum < target) {
                    left++;
                } else if (sum > target) {
                    right--;
                } else {
                    return sum;
                }
            }
        }

        return closestSum;
    }
}
```

* Time Complexity: **O(n²)**
* Space Complexity: **O(1)**




<br/><br/><br/>

---

## Maximum Product Subarray

---

### Problem Statement

Given an integer array `nums`, find the contiguous subarray that has the largest product and return its product.

---

### Example

* Input:
  nums = [2,3,-2,4]

* Output:
  6

* Explanation:

```text id="k9ah6t"
[2,3] => 2 * 3 = 6
```

---

### Example

* Input:
  nums = [-2,0,-1]

* Output:
  0

---

### Idea/Intuition

#### Brute Force

* Generate all subarrays
* Calculate product for each subarray
* Track maximum product

#### Optimized

* Negative number can convert:

  * max product → min product
  * min product → max product
* So track both:

  * currentMax
  * currentMin

**Steps:**

1. Initialize max, min, result with first element
2. Traverse array
3. Swap max/min when number is negative
4. Update current max/min
5. Update final result

---

### Java Code

#### Brute Force Approach

```java id="maxproduct_bf"
public class MaximumProductSubarrayBrute {

    public static int maxProduct(int[] nums) {

        int maxProduct = nums[0];

        for (int i = 0; i < nums.length; i++) {

            int product = 1;

            for (int j = i; j < nums.length; j++) {

                product *= nums[j];

                maxProduct = Math.max(maxProduct, product);
            }
        }

        return maxProduct;
    }
}
```

* Time Complexity: **O(n²)**
* Space Complexity: **O(1)**

---

#### Optimized Approach (Kadane Variant)

```java id="maxproduct_opt"
public class MaximumProductSubarrayOptimized {

    public static int maxProduct(int[] nums) {

        int currentMax = nums[0];
        int currentMin = nums[0];
        int result = nums[0];

        for (int i = 1; i < nums.length; i++) {

            int num = nums[i];

            // Negative flips max/min
            if (num < 0) {
                int temp = currentMax;
                currentMax = currentMin;
                currentMin = temp;
            }

            currentMax = Math.max(num, currentMax * num);

            currentMin = Math.min(num, currentMin * num);

            result = Math.max(result, currentMax);
        }

        return result;
    }
}
```

* Time Complexity: **O(n)**
* Space Complexity: **O(1)**



<br/><br/><br/>

---

## Maximum Average Subarray

---

### Problem Statement

Given an integer array `nums` and an integer `k`, find the contiguous subarray of size `k` that has the maximum average.

Return the maximum average value.

---

### Example

* Input:
  nums = [1,12,-5,-6,50,3], k = 4

* Output:
  12.75

* Explanation:

```text id="8gcn9h"
Subarray [12,-5,-6,50]
Sum = 51
Average = 51 / 4 = 12.75
```

---

### Idea/Intuition

#### Brute Force

* Generate every subarray of size `k`
* Calculate sum and average each time

#### Optimized

* Use Sliding Window
* Maintain current window sum
* Remove left element and add right element

**Steps:**

1. Calculate first window sum
2. Slide window one step at a time
3. Update maximum sum
4. Return maxSum / k

---

### Java Code

#### Brute Force Approach

```java id="maxavg_bf"
public class MaximumAverageSubarrayBrute {

    public static double findMaxAverage(int[] nums, int k) {

        double maxAverage = Integer.MIN_VALUE;

        for (int i = 0; i <= nums.length - k; i++) {

            int sum = 0;

            for (int j = i; j < i + k; j++) {
                sum += nums[j];
            }

            maxAverage = Math.max(maxAverage,
                    (double) sum / k);
        }

        return maxAverage;
    }
}
```

* Time Complexity: **O(n × k)**
* Space Complexity: **O(1)**

---

#### Optimized Approach (Sliding Window)

```java id="maxavg_opt"
public class MaximumAverageSubarrayOptimized {

    public static double findMaxAverage(int[] nums, int k) {

        int windowSum = 0;

        // First window
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }

        int maxSum = windowSum;

        // Slide window
        for (int i = k; i < nums.length; i++) {

            windowSum += nums[i] - nums[i - k];

            maxSum = Math.max(maxSum, windowSum);
        }

        return (double) maxSum / k;
    }
}
```

* Time Complexity: **O(n)**
* Space Complexity: **O(1)**





<br/><br/><br/>

---

## Longest Subarray with 2 Distinct Elements

---

### Problem Statement

Given an integer array `nums`, find the length of the longest contiguous subarray containing at most `2` distinct elements.

---

### Example

* Input:
  nums = [1,2,1]

* Output:
  3

* Explanation:

```text id="mg4iqe"
Entire array has only 2 distinct elements
```

---

### Example

* Input:
  nums = [1,2,3,2,2]

* Output:
  4

* Explanation:

```text id="5yxknd"
Longest subarray = [2,3,2,2]
```

---

### Idea/Intuition

#### Brute Force

* Generate all subarrays
* Count distinct elements
* Keep maximum valid length

#### Optimized

* Use Sliding Window + HashMap
* Store frequency of elements in current window
* Shrink window when distinct count exceeds 2

**Steps:**

1. Expand window using right pointer
2. Add current element frequency
3. If distinct elements > 2:

   * shrink from left
4. Update maximum length

---

### Java Code

#### Brute Force Approach

```java id="2distinct_bf"
import java.util.*;

public class LongestSubarrayTwoDistinctBrute {

    public static int lengthOfLongestSubarray(int[] nums) {

        int maxLength = 0;

        for (int i = 0; i < nums.length; i++) {

            Set<Integer> set = new HashSet<>();

            for (int j = i; j < nums.length; j++) {

                set.add(nums[j]);

                // More than 2 distinct
                if (set.size() > 2) {
                    break;
                }

                maxLength = Math.max(maxLength,
                        j - i + 1);
            }
        }

        return maxLength;
    }
}
```

* Time Complexity: **O(n²)**
* Space Complexity: **O(1)**

---

#### Optimized Approach (Sliding Window)

```java id="2distinct_opt"
import java.util.*;

public class LongestSubarrayTwoDistinctOptimized {

    public static int lengthOfLongestSubarray(int[] nums) {

        Map<Integer, Integer> map = new HashMap<>();

        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < nums.length; right++) {

            map.put(nums[right],
                    map.getOrDefault(nums[right], 0) + 1);

            // Shrink window
            while (map.size() > 2) {

                map.put(nums[left],
                        map.get(nums[left]) - 1);

                if (map.get(nums[left]) == 0) {
                    map.remove(nums[left]);
                }

                left++;
            }

            maxLength = Math.max(maxLength,
                    right - left + 1);
        }

        return maxLength;
    }
}
```

* Time Complexity: **O(n)**
* Space Complexity: **O(1)** (at most 3 distinct elements in map)



<br/><br/><br/>

---

## Maximum Number of Vowels in a Substring of Given Length

---

### Problem Statement

Given a string `s` and an integer `k`, return the maximum number of vowels present in any substring of length `k`.

Vowels are:
`a, e, i, o, u`

---

### Example

* Input:
  s = "abciiidef", k = 3

* Output:
  3

* Explanation:

```text id="4x2o5o"
Substring "iii" contains 3 vowels
```

---

### Example

* Input:
  s = "aeiou", k = 2

* Output:
  2

---

### Idea/Intuition

#### Brute Force

* Generate every substring of size `k`
* Count vowels in each substring

#### Optimized

* Use Sliding Window
* Maintain vowel count for current window
* Add new character and remove old character

**Steps:**

1. Count vowels in first window
2. Slide window one character at a time
3. Add right character vowel count
4. Remove left character vowel count
5. Track maximum vowels

---

### Java Code

#### Brute Force Approach

```java id="maxvowels_bf"
public class MaxVowelsBrute {

    public static int maxVowels(String s, int k) {

        int maxVowels = 0;

        for (int i = 0; i <= s.length() - k; i++) {

            int count = 0;

            for (int j = i; j < i + k; j++) {

                if (isVowel(s.charAt(j))) {
                    count++;
                }
            }

            maxVowels = Math.max(maxVowels, count);
        }

        return maxVowels;
    }

    private static boolean isVowel(char ch) {

        return ch == 'a' || ch == 'e' ||
               ch == 'i' || ch == 'o' ||
               ch == 'u';
    }
}
```

* Time Complexity: **O(n × k)**
* Space Complexity: **O(1)**

---

#### Optimized Approach (Sliding Window)

```java id="maxvowels_opt"
public class MaxVowelsOptimized {

    public static int maxVowels(String s, int k) {

        int count = 0;

        // First window
        for (int i = 0; i < k; i++) {

            if (isVowel(s.charAt(i))) {
                count++;
            }
        }

        int maxVowels = count;

        // Slide window
        for (int i = k; i < s.length(); i++) {

            if (isVowel(s.charAt(i))) {
                count++;
            }

            if (isVowel(s.charAt(i - k))) {
                count--;
            }

            maxVowels = Math.max(maxVowels, count);
        }

        return maxVowels;
    }

    private static boolean isVowel(char ch) {

        return ch == 'a' || ch == 'e' ||
               ch == 'i' || ch == 'o' ||
               ch == 'u';
    }
}
```

* Time Complexity: **O(n)**
* Space Complexity: **O(1)**




<br/><br/><br/>

---

## Boats to Save People

---

### Problem Statement

Given an array `people` where `people[i]` is the weight of a person, and an integer `limit` representing boat capacity.

Each boat can carry at most `2` people at the same time, provided total weight does not exceed `limit`.

Return the minimum number of boats required.

---

### Example

* Input:
  people = [1,2], limit = 3

* Output:
  1

* Explanation:

```text id="lr2dta"
1 + 2 <= 3
Both can travel in one boat
```

---

### Example

* Input:
  people = [3,2,2,1], limit = 3

* Output:
  3

* Explanation:

```text id="0g1pbj"
(1,2), (2), (3)
```

---

### Idea/Intuition

#### Brute Force

* Try pairing every possible person
* Mark used people
* Not efficient

#### Optimized

* Sort array
* Use two pointers:

  * lightest person
  * heaviest person
* If both fit:

  * send together
* Otherwise:

  * heaviest goes alone

**Steps:**

1. Sort weights
2. Keep left/right pointers
3. Check combined weight
4. Move pointers accordingly
5. Count boats

---

### Java Code

#### Brute Force Approach

```java id="boats_bf"
import java.util.*;

public class BoatsToSavePeopleBrute {

    public static int numRescueBoats(int[] people, int limit) {

        boolean[] used = new boolean[people.length];

        int boats = 0;

        for (int i = 0; i < people.length; i++) {

            if (used[i]) {
                continue;
            }

            used[i] = true;

            // Try pairing with another person
            for (int j = people.length - 1; j > i; j--) {

                if (!used[j] &&
                    people[i] + people[j] <= limit) {

                    used[j] = true;
                    break;
                }
            }

            boats++;
        }

        return boats;
    }
}
```

* Time Complexity: **O(n²)**
* Space Complexity: **O(n)**

---

#### Optimized Approach (Sorting + Two Pointers)

```java id="boats_opt"
import java.util.*;

public class BoatsToSavePeopleOptimized {

    public static int numRescueBoats(int[] people, int limit) {

        Arrays.sort(people);

        int left = 0;
        int right = people.length - 1;

        int boats = 0;
        while (left <= right) {

            // Lightest + heaviest fit together
            if (people[left] + people[right] <= limit) {
                left++;
            }

            // Heaviest always boards
            right--;

            boats++;
        }

        return boats;
    }
}

/*
---> keep in mind ---> Each boat can carry at most 2 people at the same time
---> not said about ---> continuous filling of one boat
Thats why below approach will fails.

for (int i =0 ; i < n ; i++){
    if (holidng + arr[i] <= capacity){
        holidng += arr[i];
    }else{
        boaat++;
        holidng = arr[i]+
    }
}
*/
```

* Time Complexity: **O(n log n)**
* Space Complexity: **O(1)**



<br/><br/><br/>

---

## Kth Missing Positive Number

---

### Problem Statement

Given a sorted positive integer array `arr` and an integer `k`, return the `kth` missing positive number.

---

### Example

* Input:
  arr = [2,3,4,7,11], k = 5

* Output:
  9

* Explanation:

```text id="q8k8ry"
Missing numbers = [1,5,6,8,9,10...]
5th missing number = 9
```

---

### Example

* Input:
  arr = [1,2,3,4], k = 2

* Output:
  6

---

### Idea/Intuition

#### Brute Force

* Start from number 1
* Check whether number exists in array
* Count missing numbers until kth missing found

#### Optimized

* Missing numbers before index `i`:

```text id="4c8l5n"
arr[i] - (i + 1)
```

* Use Binary Search to find first index where missing count >= k

**Steps:**

1. Calculate missing count using formula
2. Apply binary search
3. Find position where kth missing lies
4. Return final number

---

### Java Code

#### Brute Force Approach

```java id="kthmissing_bf"
public class KthMissingPositiveBrute {

    public static int findKthPositive(int[] arr, int k) {

        int current = 1;
        int index = 0;

        while (k > 0) {

            // Number exists in array
            if (index < arr.length && arr[index] == current) {                
                index++;
            } else {
                // Missing number found
                k--;

                if (k == 0) {
                    return current;
                }
            }

            current++;
        }

        return -1;
    }
}
```

* Time Complexity: **O(n + k)**
* Space Complexity: **O(1)**

---

#### Optimized Approach (Binary Search)

```java id="kthmissing_opt"
public class KthMissingPositiveOptimized {

    public static int findKthPositive(int[] arr, int k) {

        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            // Missing count till mid
            int missing = arr[mid] - (mid + 1);

            if (missing < k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // Final kth missing number
        return left + k;
    }
}
```

* Time Complexity: **O(log n)**
* Space Complexity: **O(1)**





<br/><br/><br/>

---


## Max Consecutive Ones

---

### Problem Statement

Given a binary array `nums`, return the maximum number of consecutive `1s` in the array.

---

### Example

* Input:
  nums = [1,1,0,1,1,1]

* Output:
  3

* Explanation:

```text id="k8mpk6"
Longest sequence of consecutive 1s = [1,1,1]
```

---

### Example

* Input:
  nums = [1,0,1,1,0,1]

* Output:
  2

---

### Idea/Intuition

#### Brute Force

* Start from every index
* Count consecutive 1s
* Track maximum length

#### Optimized

* Traverse array once
* Maintain current streak of 1s
* Reset count when 0 appears

**Steps:**

1. Traverse array
2. If element is 1:

   * increase streak
3. If element is 0:

   * reset streak
4. Track maximum streak

---

### Java Code

#### Brute Force Approach

```java id="maxones_bf"
public class MaxConsecutiveOnesBrute {

    public static int findMaxConsecutiveOnes(int[] nums) {

        int maxLength = 0;

        for (int i = 0; i < nums.length; i++) {

            int count = 0;

            for (int j = i; j < nums.length; j++) {

                if (nums[j] == 1) {
                    count++;
                    maxLength = Math.max(maxLength, count);
                } else {
                    break;
                }
            }
        }

        return maxLength;
    }
}
```

* Time Complexity: **O(n²)**
* Space Complexity: **O(1)**

---

#### Optimized Approach

```java id="maxones_opt"
public class MaxConsecutiveOnesOptimized {

    public static int findMaxConsecutiveOnes(int[] nums) {

        int count = 0;
        int maxLength = 0;

        for (int num : nums) {

            if (num == 1) {

                count++;

                maxLength = Math.max(maxLength, count);

            } else {

                // Reset streak
                count = 0;
            }
        }

        return maxLength;
    }
}
```

* Time Complexity: **O(n)**
* Space Complexity: **O(1)**


<br/><br/><br/>

---

## Sum of Unique Elements

---

### Problem Statement

Given an integer array `nums`, return the sum of all elements that appear exactly once.

---

### Example

* Input:
  nums = [1,2,3,2]

* Output:
  4

* Explanation:

```text id="7b2m7x"
Unique elements = [1,3]
Sum = 1 + 3 = 4
```

---

### Example

* Input:
  nums = [1,1,1,1,1]

* Output:
  0

---

### Idea/Intuition

#### Brute Force

* For every element:

  * count its occurrences
* Add element if frequency is 1

#### Optimized

* Use HashMap to count frequencies
* Traverse map and add elements with count 1

**Steps:**

1. Store frequencies in map
2. Traverse entries
3. Add keys having frequency 1

---

### Java Code

#### Brute Force Approach

```java id="sumunique_bf"
public class SumOfUniqueElementsBrute {

    public static int sumOfUnique(int[] nums) {

        int sum = 0;

        for (int i = 0; i < nums.length; i++) {

            int count = 0;

            for (int j = 0; j < nums.length; j++) {

                if (nums[i] == nums[j]) {
                    count++;
                }
            }

            // Appears once
            if (count == 1) {
                sum += nums[i];
            }
        }

        return sum;
    }
}
```

* Time Complexity: **O(n²)**
* Space Complexity: **O(1)**

---

#### Optimized Approach (HashMap)

```java id="sumunique_opt"
import java.util.*;

public class SumOfUniqueElementsOptimized {

    public static int sumOfUnique(int[] nums) {

        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int sum = 0;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {

            // Frequency = 1
            if (entry.getValue() == 1) {
                sum += entry.getKey();
            }
        }

        return sum;
    }
}
```

* Time Complexity: **O(n)**
* Space Complexity: **O(n)**
```java
public class SumOfUniqueElements {

    public static int sumOfUnique(int[] nums) {

        // Frequency array
        int[] freq = new int[101];

        // Count frequencies
        for (int num : nums) {
            freq[num]++;
        }

        int sum = 0;

        // Add elements appearing once
        for (int num : nums) {
            if (freq[num] == 1) {
                sum += num;
            }
        }

        return sum;
    }
}
```



<br/><br/><br/>

---

## Matrix Diagonal Sum

---

### Problem Statement

Given a square matrix `mat`, return the sum of:

* primary diagonal
* secondary diagonal

Do not count the center element twice.

---

### Example

* Input:

```text id="5gt6da"
mat = [
 [1,2,3],
 [4,5,6],
 [7,8,9]
]
```

* Output:
  25

* Explanation:

```text id="4lkn5g"
Primary diagonal   = 1 + 5 + 9 = 15
Secondary diagonal = 3 + 5 + 7 = 15

Center element 5 counted twice
Final sum = 15 + 15 - 5 = 25
```

---

### Idea/Intuition

#### Brute Force

* Traverse entire matrix
* Check diagonal conditions

#### Optimized

* Primary diagonal:

```text id="8m30xj"
mat[i][i]
```

* Secondary diagonal:

```text id="ymu7vq"
mat[i][n-1-i]
```

* If matrix size is odd:

  * subtract center once

**Steps:**

1. Traverse rows
2. Add both diagonals
3. Remove duplicate center element if needed

---

### Java Code

#### Brute Force Approach

```java id="matrixdiag_bf"
public class MatrixDiagonalSumBrute {

    public static int diagonalSum(int[][] mat) {

        int n = mat.length;
        int sum = 0;

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                // Primary diagonal
                if (i == j) {
                    sum += mat[i][j];
                }

                // Secondary diagonal
                if (i + j == n - 1) {
                    sum += mat[i][j];
                }
            }
        }

        // Remove duplicate center
        if (n % 2 == 1) {
            sum -= mat[n / 2][n / 2];
        }

        return sum;
    }
}
```

* Time Complexity: **O(n²)**
* Space Complexity: **O(1)**

---

#### Optimized Approach

```java id="matrixdiag_opt"
public class MatrixDiagonalSumOptimized {

    public static int diagonalSum(int[][] mat) {

        int n = mat.length;
        int sum = 0;

        for (int i = 0; i < n; i++) {

            sum += mat[i][i];

            sum += mat[i][n - 1 - i];
        }

        // Remove duplicate center
        if (n % 2 == 1) {
            sum -= mat[n / 2][n / 2];
        }

        return sum;
    }
}
```

* Time Complexity: **O(n)**
* Space Complexity: **O(1)**
```java
public class RectangularMatrixDiagonalSum {

    public static int diagonalSum(int[][] mat) {

        int rows = mat.length;
        int cols = mat[0].length;

        int sum = 0;

        // Primary Diagonal
        // i == j
        for (int i = 0; i < Math.min(rows, cols); i++) {

            sum += mat[i][i];
        }

        // Secondary Diagonal
        // i + j == cols - 1
        for (int i = 0; i < Math.min(rows, cols); i++) {

            int j = cols - 1 - i;

            // Avoid duplicate element
            if (i != j) {
                sum += mat[i][j];
            }
        }

        return sum;
    }

    public static void main(String[] args) {

        int[][] mat = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15}
        };

        System.out.println(diagonalSum(mat));
    }
}
```



<br/><br/><br/>

---

## Last Stone Weight

---

### Problem Statement

Given an array `stones` where each stone has a weight.

On each turn:

* Choose the two heaviest stones
* Smash them together

Rules:

* If both stones are equal → both destroyed
* If different → remaining stone becomes difference of weights

Return the weight of the last remaining stone.
If no stones remain, return `0`.

---

### Example

* Input:
  stones = [2,7,4,1,8,1]

* Output:
  1

* Explanation:

```text id="xjlwm9"
8 - 7 = 1
4 - 2 = 2
2 - 1 = 1
1 - 1 = 0

Last remaining stone = 1
```

---

### Idea/Intuition

#### Brute Force

* Repeatedly sort array
* Pick two largest stones
* Insert remaining difference

#### Optimized

* Use Max Heap
* Always get largest two stones quickly

**Steps:**

1. Insert all stones into max heap
2. Remove largest two stones
3. If unequal:

   * insert difference back
4. Continue until one or zero stones remain

---

### Java Code

#### Brute Force Approach

```java id="laststone_bf"
import java.util.*;

public class LastStoneWeightBrute {

    public static int lastStoneWeight(int[] stones) {

        List<Integer> list = new ArrayList<>();

        for (int stone : stones) {
            list.add(stone);
        }

        while (list.size() > 1) {

            Collections.sort(list);

            int y = list.remove(list.size() - 1);
            int x = list.remove(list.size() - 1);

            // Add remaining difference
            if (y != x) {
                list.add(y - x);
            }
        }

        return list.isEmpty() ? 0 : list.get(0);
    }
}
```

* Time Complexity: **O(n² log n)**
* Space Complexity: **O(n)**

---

#### Optimized Approach (Max Heap)

```java id="laststone_opt"
import java.util.*;

public class LastStoneWeightOptimized {

    public static int lastStoneWeight(int[] stones) {

        // Max Heap
        PriorityQueue<Integer> pq =
                new PriorityQueue<>((a, b) -> b - a);

        for (int stone : stones) {
            pq.offer(stone);
        }

        while (pq.size() > 1) {

            int y = pq.poll(); // largest
            int x = pq.poll(); // second largest

            // Remaining weight
            int z = y - x;

            // Add remaining stone
            if (z > 0) {
                pq.offer(z);
            }
        }

        return pq.isEmpty() ? 0 : pq.poll();
    }
}
```

* Time Complexity: **O(n log n)**
* Space Complexity: **O(n)**





<br/><br/><br/>

---

## Pivot Index

---

### Problem Statement

Given an integer array `nums`, return the pivot index.

Pivot index is the index where:

* sum of left elements = sum of right elements

If no such index exists, return `-1`.

---

### Example

* Input:
  nums = [1,7,3,6,5,6]

* Output:
  3

* Explanation:

```text id="4m3xqm"
Left sum  = 1 + 7 + 3 = 11
Right sum = 5 + 6 = 11
```

---

### Example

* Input:
  nums = [1,2,3]

* Output:
  -1

---

### Idea/Intuition

#### Brute Force

* For every index:

  * calculate left sum
  * calculate right sum
* Compare both sums

#### Optimized

* Calculate total sum once
* Maintain running left sum
* Right sum:

```text id="lm8mvn"
totalSum - leftSum - nums[i]
```

**Steps:**

1. Find total array sum
2. Traverse array
3. Compute right sum
4. Compare left/right sums
5. Update left sum

---

### Java Code

#### Brute Force Approach

```java id="pivot_bf"
public class PivotIndexBrute {

    public static int pivotIndex(int[] nums) {

        for (int i = 0; i < nums.length; i++) {

            int leftSum = 0;
            int rightSum = 0;

            for (int j = 0; j < i; j++) {
                leftSum += nums[j];
            }

            for (int j = i + 1; j < nums.length; j++) {
                rightSum += nums[j];
            }

            if (leftSum == rightSum) {
                return i;
            }
        }

        return -1;
    }
}
```

* Time Complexity: **O(n²)**
* Space Complexity: **O(1)**

---

#### Optimized Approach (Prefix Sum)

```java id="pivot_opt"
public class PivotIndexOptimized {

    public static int pivotIndex(int[] nums) {

        int totalSum = 0;

        for (int num : nums) {
            totalSum += num;
        }

        int leftSum = 0;

        for (int i = 0; i < nums.length; i++) {

            int rightSum =
                    totalSum - leftSum - nums[i];

            if (leftSum == rightSum) {
                return i;
            }

            leftSum += nums[i];
        }

        return -1;
    }
}
```

* Time Complexity: **O(n)**
* Space Complexity: **O(1)**



<br/><br/><br/>

---

## Minimum Increments for Unique Array

---

### Problem Statement

Given an integer array `nums`, increment elements so that all values become unique.

In one move, you can increment any element by `1`.

Return the minimum number of moves required.

---

### Example

* Input:
  nums = [1,2,2]

* Output:
  1

* Explanation:

```text id="hwbk38"
Increment second 2 → 3

Array becomes:
[1,2,3]
```

---

### Example

* Input:
  nums = [3,2,1,2,1,7]

* Output:
  6

---

### Idea/Intuition

#### Brute Force

* For each duplicate:

  * keep incrementing until unique
* Use HashSet to track used values

#### Optimized

* Sort array first
* Every next element should be:

```text id="kktv4r"
greater than previous element
```

* If current value is smaller/equal:

  * increment it to previous + 1

**Steps:**

1. Sort array
2. Traverse from left to right
3. Fix duplicates using previous value
4. Add required increments

---

### Java Code

#### Brute Force Approach

```java id="minincrement_bf"
import java.util.*;

public class MinimumIncrementUniqueBrute {

    public static int minIncrementForUnique(int[] nums) {

        Set<Integer> set = new HashSet<>();

        int moves = 0;

        for (int num : nums) {

            // Increment until unique
            while (set.contains(num)) {
                num++;
                moves++;
            }

            set.add(num);
        }

        return moves;
    }
}
```

* Time Complexity: **O(n²)** (worst case)
* Space Complexity: **O(n)**

---

#### Optimized Approach (Sorting)

```java id="minincrement_opt"
import java.util.*;

public class MinimumIncrementUniqueOptimized {

    public static int minIncrementForUnique(int[] nums) {

        Arrays.sort(nums);

        int moves = 0;

        for (int i = 1; i < nums.length; i++) {

            // Current must be greater than previous
            if (nums[i] <= nums[i - 1]) {

                int required = nums[i - 1] + 1;

                moves += required - nums[i];

                nums[i] = required;
            }
        }

        return moves;
    }
}
```

* Time Complexity: **O(n log n)**
* Space Complexity: **O(1)**




<br/><br/><br/>

---

## Left and Right Sum Differences

---

### Problem Statement

Given an integer array `nums`, for each index `i`:

* `leftSum[i]` = sum of elements left of index `i`
* `rightSum[i]` = sum of elements right of index `i`

Return an array:

```text id="75cnf8"
answer[i] = |leftSum[i] - rightSum[i]|
```

---

### Example

* Input:
  nums = [10,4,8,3]

* Output:
  [15,1,11,22]

* Explanation:

```text id="0xk4g3"
leftSum  = [0,10,14,22]
rightSum = [15,11,3,0]

answer =
[|0-15|, |10-11|, |14-3|, |22-0|]

= [15,1,11,22]
```

---

### Idea/Intuition

#### Brute Force

* For every index:

  * calculate left sum
  * calculate right sum
* Store absolute difference

#### Optimized

* Find total sum first
* Maintain running left sum
* Compute right sum dynamically

```text id="mdj2j8"
rightSum = totalSum - leftSum - nums[i]
```

**Steps:**

1. Calculate total sum
2. Traverse array
3. Compute right sum
4. Store absolute difference
5. Update left sum

---

### Java Code

#### Brute Force Approach

```java id="leftrightdiff_bf"
public class LeftRightDifferenceBrute {

    public static int[] leftRightDifference(int[] nums) {

        int n = nums.length;

        int[] result = new int[n];

        for (int i = 0; i < n; i++) {

            int leftSum = 0;
            int rightSum = 0;

            for (int j = 0; j < i; j++) {
                leftSum += nums[j];
            }

            for (int j = i + 1; j < n; j++) {
                rightSum += nums[j];
            }

            result[i] = Math.abs(leftSum - rightSum);
        }

        return result;
    }
}
```

* Time Complexity: **O(n²)**
* Space Complexity: **O(1)**

---

#### Optimized Approach (Prefix Sum)

```java id="leftrightdiff_opt"
public class LeftRightDifferenceOptimized {

    public static int[] leftRightDifference(int[] nums) {

        int totalSum = 0;

        for (int num : nums) {
            totalSum += num;
        }

        int leftSum = 0;

        int[] result = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {

            int rightSum =
                    totalSum - leftSum - nums[i];

            result[i] =
                    Math.abs(leftSum - rightSum);

            leftSum += nums[i];
        }

        return result;
    }
}
```

* Time Complexity: **O(n)**
* Space Complexity: **O(1)**



<br/><br/><br/>

---


## Number of Islands

---

### Problem Statement

Given a 2D binary grid containing:

* `'1'` → land
* `'0'` → water

Return the number of islands.

An island is formed by connected lands:

* horizontally
* vertically

---

### Example

* Input:

```text id="jyg45n"
grid = [
 ["1","1","1","1","0"],
 ["1","1","0","1","0"],
 ["1","1","0","0","0"],
 ["0","0","0","0","0"]
]
```

* Output:
  1

---

### Example

* Input:

```text id="3jyr9r"
grid = [
 ["1","1","0","0","0"],
 ["1","1","0","0","0"],
 ["0","0","1","0","0"],
 ["0","0","0","1","1"]
]
```

* Output:
  3

---

### Idea/Intuition

#### Brute Force

* Traverse entire grid
* When land found:

  * mark connected lands visited
* Count each DFS/BFS start

#### Optimized

* Use DFS or BFS traversal
* Every unvisited `'1'` starts a new island

**Steps:**

1. Traverse grid
2. If cell is `'1'`:

   * increment island count
   * run DFS/BFS to mark connected land
3. Continue traversal

---

### Java Code

#### DFS Approach (Optimized)

```java id="numislands_opt"
public class NumberOfIslands {

    public static int numIslands(char[][] grid) {

        int islands = 0;

        for (int row = 0; row < grid.length; row++) {

            for (int col = 0; col < grid[0].length; col++) {

                // New island found
                if (grid[row][col] == '1') {

                    islands++;

                    dfs(grid, row, col);
                }
            }
        }

        return islands;
    }

    private static void dfs(char[][] grid,
                            int row,
                            int col) {

        // Boundary or water check
        if (row < 0 ||
            col < 0 ||
            row >= grid.length ||
            col >= grid[0].length ||
            grid[row][col] == '0') {

            return;
        }

        // Mark visited
        grid[row][col] = '0';

        dfs(grid, row + 1, col);
        dfs(grid, row - 1, col);
        dfs(grid, row, col + 1);
        dfs(grid, row, col - 1);
    }
}
```

* Time Complexity: **O(m × n)**
* Space Complexity: **O(m × n)** (DFS recursion stack worst case)




<br/><br/><br/>

---
## Rotting Oranges

---

### Problem Statement

Given a grid where:

* `0` → empty cell
* `1` → fresh orange
* `2` → rotten orange

Every minute:

* rotten oranges spread rot to adjacent fresh oranges
* only up, down, left, right

Return the minimum time required to rot all oranges.
If impossible, return `-1`.

---

### Example

* Input:

```text id="r7n8xg"
grid = [
 [2,1,1],
 [1,1,0],
 [0,1,1]
]
```

* Output:
  4

---

### Example

* Input:

```text id="xz3f3w"
grid = [
 [2,1,1],
 [0,1,1],
 [1,0,1]
]
```

* Output:
  -1

````

- Explanation:  

```text id="7lfk9f"
Some fresh oranges can never rot
````

---

### Idea/Intuition

#### Brute Force

* Simulate minute by minute
* Scan entire grid repeatedly
* Very inefficient

#### Optimized

* Use Multi-Source BFS
* Start BFS from all rotten oranges together
* Spread rot level by level

**Steps:**

1. Add all rotten oranges into queue
2. Count fresh oranges
3. Process BFS level by level
4. Rot adjacent fresh oranges
5. Track minutes
6. If fresh oranges remain:

   * return -1

---

### Java Code

#### Brute Force Approach

```java id="rotting_bf"
public class RottingOrangesBrute {

    public static int orangesRotting(int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;

        int minutes = 0;
        boolean changed = true;

        while (changed) {

            changed = false;

            int[][] temp = new int[rows][cols];

            for (int i = 0; i < rows; i++) {
                temp[i] = grid[i].clone();
            }

            for (int row = 0; row < rows; row++) {

                for (int col = 0; col < cols; col++) {

                    if (grid[row][col] == 2) {

                        int[][] dir = {
                                {1,0},{-1,0},{0,1},{0,-1}
                        };

                        for (int[] d : dir) {

                            int nr = row + d[0];
                            int nc = col + d[1];

                            if (nr >= 0 && nc >= 0 &&
                                nr < rows && nc < cols &&
                                grid[nr][nc] == 1) {

                                temp[nr][nc] = 2;
                                changed = true;
                            }
                        }
                    }
                }
            }

            if (changed) {
                minutes++;
                grid = temp;
            }
        }

        // Check fresh oranges left
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 1) {
                    return -1;
                }
            }
        }

        return minutes;
    }
}
```

* Time Complexity: **O((m × n)²)**
* Space Complexity: **O(m × n)**

---

#### Optimized Approach (Multi-Source BFS)

```java id="rotting_opt"
import java.util.*;

public class RottingOrangesOptimized {

    public static int orangesRotting(int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;

        Queue<int[]> queue = new LinkedList<>();

        int fresh = 0;

        // Collect rotten oranges
        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < cols; col++) {

                if (grid[row][col] == 2) {
                    queue.offer(new int[]{row, col});
                }

                if (grid[row][col] == 1) {
                    fresh++;
                }
            }
        }

        if (fresh == 0) {
            return 0;
        }

        int minutes = 0;

        int[][] dir = {
                {1,0},{-1,0},{0,1},{0,-1}
        };

        while (!queue.isEmpty()) {

            int size = queue.size();

            boolean rottenThisMinute = false;

            for (int i = 0; i < size; i++) {

                int[] cell = queue.poll();

                int row = cell[0];
                int col = cell[1];

                for (int[] d : dir) {

                    int nr = row + d[0];
                    int nc = col + d[1];

                    if (nr >= 0 && nc >= 0 &&
                        nr < rows && nc < cols &&
                        grid[nr][nc] == 1) {

                        grid[nr][nc] = 2;

                        fresh--;

                        queue.offer(new int[]{nr, nc});

                        rottenThisMinute = true;
                    }
                }
            }

            if (rottenThisMinute) {
                minutes++;
            }
        }

        return fresh == 0 ? minutes : -1;
    }
}
```

* Time Complexity: **O(m × n)**
* Space Complexity: **O(m × n)**


## Rotting Oranges (DFS Approach)

```java id="rotting_dfs_clean"
import java.util.*;

public class RottingOrangesDFS {

    public static int orangesRotting(int[][] grid) {

        if (grid == null || grid.length == 0) {
            return -1;
        }

        int rows = grid.length;
        int cols = grid[0].length;

        int[][] time = new int[rows][cols];

        // Initialize with large value
        for (int i = 0; i < rows; i++) {
            Arrays.fill(time[i], Integer.MAX_VALUE);
        }

        // Start DFS from all rotten oranges
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {

                if (grid[i][j] == 2) {
                    dfs(grid, time, i, j, 0);
                }
            }
        }

        int timeRequired = 0;

        // Find maximum time
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {

                if (grid[i][j] == 1) {

                    // Fresh orange never rotted
                    if (time[i][j] == Integer.MAX_VALUE) {
                        return -1;
                    }

                    timeRequired = Math.max(timeRequired,
                                            time[i][j]);
                }
            }
        }

        return timeRequired;
    }

    private static void dfs(int[][] grid,
                            int[][] time,
                            int i,
                            int j,
                            int currentTime) {

        // Invalid cell or slower path
        if (i < 0 || i >= grid.length ||
            j < 0 || j >= grid[0].length ||
            grid[i][j] == 0 ||
            currentTime >= time[i][j]) {

            return;
        }

        time[i][j] = currentTime;

        dfs(grid, time, i - 1, j, currentTime + 1);
        dfs(grid, time, i + 1, j, currentTime + 1);
        dfs(grid, time, i, j - 1, currentTime + 1);
        dfs(grid, time, i, j + 1, currentTime + 1);
    }
}
```
## Time Complexity

```text id="3j9blq"
O(m × n)
```

* Each cell is processed with minimum possible time
* DFS may revisit cells, but only when a shorter time path is found
* Overall bounded by grid size

Where:

* `m` = rows
* `n` = columns

---

## Space Complexity

```text id="f2q4vt"
O(m × n)
```

### Reason:

* `time[][]` matrix stores time for every cell
* DFS recursion stack can go up to all cells in worst case

---

## Breakdown

| Component           | Space    |
| ------------------- | -------- |
| time[][] array      | O(m × n) |
| DFS recursion stack | O(m × n) |
| Total               | O(m × n) |




<br/><br/><br/>

---
## 01 Matrix

---

### Problem Statement

Given a binary matrix `mat`, return a matrix where each cell contains the distance to the nearest `0`.

Distance is calculated using:

* up
* down
* left
* right

---

### Example

* Input:

```text id="g6j1rx"
mat = [
 [0,0,0],
 [0,1,0],
 [0,0,0]
]
```

* Output:

```text id="8bzjlwm"
[
 [0,0,0],
 [0,1,0],
 [0,0,0]
]
```

---

### Example

* Input:

```text id="wd4q6s"
mat = [
 [0,0,0],
 [0,1,0],
 [1,1,1]
]
```

* Output:

```text id="9g7h0l"
[
 [0,0,0],
 [0,1,0],
 [1,2,1]
]
```

---

### Idea/Intuition

#### DFS Approach

* Start DFS from every `0`
* Update shortest distance for reachable cells
* Skip paths that are already shorter

**Steps:**

1. Initialize distance matrix with large values
2. Start DFS from all `0` cells
3. Update distances recursively
4. Return final matrix

---

### Java Code (DFS Approach)

```java id="01matrix_dfs"
import java.util.*;

public class ZeroOneMatrixDFS {

    public static int[][] updateMatrix(int[][] mat) {

        int rows = mat.length;
        int cols = mat[0].length;

        int[][] dist = new int[rows][cols];

        // Initialize with large value
        for (int i = 0; i < rows; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        // Start DFS from all 0 cells
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {

                if (mat[i][j] == 0) {
                    dfs(mat, dist, i, j, 0);
                }
            }
        }

        return dist;
    }

    private static void dfs(int[][] mat,
                            int[][] dist,
                            int row,
                            int col,
                            int currentDist) {

        // Invalid or already shorter path exists
        if (row < 0 || row >= mat.length ||
            col < 0 || col >= mat[0].length ||
            currentDist >= dist[row][col]) {

            return;
        }

        dist[row][col] = currentDist;

        dfs(mat, dist, row + 1, col, currentDist + 1);
        dfs(mat, dist, row - 1, col, currentDist + 1);
        dfs(mat, dist, row, col + 1, currentDist + 1);
        dfs(mat, dist, row, col - 1, currentDist + 1);
    }
}
```

* Time Complexity: **O(m × n)** average
* Space Complexity: **O(m × n)**





<br/><br/><br/>

---


## Gas Station

---

### Problem Statement

There are `n` gas stations arranged in a circular route.

* `gas[i]` → gas available at station `i`
* `cost[i]` → gas needed to travel to next station

Return the starting gas station index if you can travel around the circuit once.
Otherwise return `-1`.

---

### Example

* Input:

```text id="h9l3ps"
gas  = [1,2,3,4,5]
cost = [3,4,5,1,2]
```

* Output:

```text id="7h1lxs"
3
```

* Explanation:

```text id="1rxlgv"
Start at index 3

4-1 = 3
3+5-2 = 6
6+1-3 = 4
4+2-4 = 2
2+3-5 = 0

Complete circuit possible
```

---

### Idea/Intuition

#### Key Observation

If:

```text id="2f0pr4"
totalGas < totalCost
```

then completing circuit is impossible.

---

### Greedy Logic

If current gas becomes negative at index `i`:

```text id="nz2qkr"
No station between previous start and i
can be a valid starting point
```

So:

* reset starting index
* start from `i + 1`

---

### Steps

1. Calculate total gas and total cost
2. If total gas < total cost:

   * return `-1`
3. Traverse stations
4. Track current gas
5. If current gas becomes negative:

   * reset current gas
   * update start index

---

### Java Code

```java id="gasstation_opt"
public class GasStation {

    public static int canCompleteCircuit(int[] gas,
                                         int[] cost) {

        int totalGas = 0;
        int totalCost = 0;

        // Calculate total gas and total cost
        for (int i = 0; i < gas.length; i++) {

            totalGas += gas[i];
            totalCost += cost[i];
        }

        // Impossible to complete circuit
        if (totalGas < totalCost) {
            return -1;
        }

        int currentGas = 0;
        int startIndex = 0;

        // Find valid starting station
        for (int i = 0; i < gas.length; i++) {

            currentGas += gas[i] - cost[i];

            // Cannot move ahead
            if (currentGas < 0) {

                startIndex = i + 1;

                currentGas = 0;
            }
        }

        return startIndex;
    }
}
```

---

### Dry Run

```text id="q3k9sx"
gas  = [1,2,3,4,5]
cost = [3,4,5,1,2]
```

| i | gas[i]-cost[i] | currentGas | startIndex |
| - | -------------- | ---------- | ---------- |
| 0 | -2             | negative   | 1          |
| 1 | -2             | negative   | 2          |
| 2 | -2             | negative   | 3          |
| 3 | +3             | 3          | 3          |
| 4 | +3             | 6          | 3          |

Final Answer:

```text id="2vqpr7"
startIndex = 3
```

---

### Time Complexity

```text id="lch3lk"
O(n)
```

---

### Space Complexity

```text id="g7mxhq"
O(1)
```


<br/><br/><br/>

---

## Contiguous Array

---

### Problem Statement

Given a binary array `nums`, return the length of the longest contiguous subarray with equal number of `0`s and `1`s.

---

### Example

* Input:

```text id="r5y8kb"
nums = [0,1]
```

* Output:

```text id="1zq8lh"
2
```

---

### Example

* Input:

```text id="h4o9pd"
nums = [0,1,0]
```

* Output:

```text id="6nm4rk"
2
```

---

### Idea/Intuition

### Key Observation

Convert:

```text id="5v2g8p"
0 -> -1
1 -> +1
```

Now problem becomes:

```text id="c8j1xt"
Find longest subarray with sum = 0
```

---

### Prefix Sum Logic

If same prefix sum appears again:

```text id="m1y7lb"
Subarray between them has equal 0 and 1
```

Why?

```text id="0b9hqt"
Repeated prefix sum means net sum between
those indices is 0
```

---

### Steps

1. Replace:

   * `0 -> -1`
   * `1 -> +1`
2. Maintain running prefix sum
3. Store first occurrence of prefix sum in HashMap
4. If prefix repeats:

   * calculate subarray length
5. Track maximum length

---

### Java Code

```java id="contiguousarray_opt"
import java.util.*;

public class ContiguousArray {

    public static int findMaxLength(int[] nums) {

        Map<Integer, Integer> map = new HashMap<>();

        // Prefix sum 0 at index -1
        map.put(0, -1);

        int prefixSum = 0;
        int maxLength = 0;

        for (int i = 0; i < nums.length; i++) {

            // Convert 0 -> -1
            if (nums[i] == 0) {
                prefixSum -= 1;
            } else {
                prefixSum += 1;
            }

            // Prefix sum seen before
            if (map.containsKey(prefixSum)) {

                int length = i - map.get(prefixSum);

                maxLength = Math.max(maxLength, length);

            } else {

                // Store first occurrence
                map.put(prefixSum, i);
            }
        }

        return maxLength;
    }
}
```

---

### Dry Run

```text id="9c3zlp"
nums = [0,1,0]
```

| i  | value | converted | prefixSum | map            | maxLength |
| -- | ----- | --------- | --------- | -------------- | --------- |
| -1 | -     | -         | 0         | {0:-1}         | 0         |
| 0  | 0     | -1        | -1        | {-1:0}         | 0         |
| 1  | 1     | +1        | 0         | already exists | 2         |
| 2  | 0     | -1        | -1        | already exists | 2         |

Final Answer:

```text id="j5y1pr"
2
```

---

### Time Complexity

```text id="1yln9r"
O(n)
```

---

### Space Complexity

```text id="w4m0pd"
O(n)
```





<br/><br/><br/>

---
## Subarray Sum Equals K

---

### Problem Statement

Given an integer array `nums` and an integer `k`, return the total number of continuous subarrays whose sum equals `k`.

---

### Example

* Input:

```text id="7r2mql"
nums = [1,1,1]
k = 2
```

* Output:

```text id="9k4qpl"
2
```

* Explanation:

```text id="2l8xqm"
Subarrays:
[1,1]
[1,1]
```

---

### Example

* Input:

```text id="8h3gpr"
nums = [1,2,3]
k = 3
```

* Output:

```text id="x4m8jr"
2
```

* Explanation:

```text id="1v9tpk"
Subarrays:
[1,2]
[3]
```

---

### Idea/Intuition

### Prefix Sum Observation

If:

```text id="6f2nkp"
currentPrefixSum - previousPrefixSum = k
```

then:

```text id="v5r8mw"
Subarray sum between them = k
```

So we check:

```text id="z3m1pt"
previousPrefixSum = currentPrefixSum - k
```

---

### Steps

1. Maintain running prefix sum
2. Store frequency of prefix sums in HashMap
3. For every prefix sum:

   * check if `(prefixSum - k)` exists
4. Add its frequency to answer
5. Store current prefix sum frequency

---

### Java Code

```java id="subarraysumk_opt"
import java.util.*;

public class SubarraySumEqualsK {

    public static int subarraySum(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();

        // Prefix sum 0 occurs once
        map.put(0, 1);

        int prefixSum = 0;
        int count = 0;

        for (int num : nums) {

            prefixSum += num;

            // Required previous prefix sum
            if (map.containsKey(prefixSum - k)) {

                count += map.get(prefixSum - k);
            }

            // Store current prefix sum
            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }
}
```

---

### Dry Run

```text id="5g7rkm"
nums = [1,1,1]
k = 2
```

| num | prefixSum | prefixSum-k | count | map               |
| --- | --------- | ----------- | ----- | ----------------- |
| 1   | 1         | -1          | 0     | {0:1,1:1}         |
| 1   | 2         | 0           | 1     | {0:1,1:1,2:1}     |
| 1   | 3         | 1           | 2     | {0:1,1:1,2:1,3:1} |

Final Answer:

```text id="8v2mnc"
2
```

---

### Time Complexity

```text id="4x7jpk"
O(n)
```

---

### Space Complexity

```text id="6j8qtw"
O(n)
```




<br/><br/><br/>

---





<br/><br/><br/>

---




<br/><br/><br/>

---






<br/><br/><br/>

---




<br/><br/><br/>

---





<br/><br/><br/>

---




<br/><br/><br/>

---






<br/><br/><br/>

---




<br/><br/><br/>

---





<br/><br/><br/>

---




<br/><br/><br/>

---






<br/><br/><br/>

---




<br/><br/><br/>

---





<br/><br/><br/>

---




<br/><br/><br/>

---






<br/><br/><br/>

---




<br/><br/><br/>

---



