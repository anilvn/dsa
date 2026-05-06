# Complete Guide: 2-Sum, 3-Sum, 4-Sum Problems

## 📋 Problem Overview

| Problem | Description | Goal |
|---------|-------------|------|
| **2-Sum** | Find two numbers that add up to target | `arr[i] + arr[j] = target` |
| **3-Sum** | Find three numbers that add up to target | `arr[i] + arr[j] + arr[k] = target` |
| **4-Sum** | Find four numbers that add up to target | `arr[i] + arr[j] + arr[k] + arr[l] = target` |

---

## 🔢 2-SUM Problem

**Input:** `arr = [2, 7, 11, 15], target = 9`  
**Output:** `[0, 1]` (indices) or `[2, 7]` (values)

### Approach 1: Brute Force
```java
import java.util.*;

public class TwoSumBruteForce {
    public static int[] twoSumBrute(int[] nums, int target) {
        // Time Complexity: O(n²) - nested loops
        // Space Complexity: O(1) - no extra space
        
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j}; // return indices
                }
            }
        }
        return new int[]{}; // no solution found
    }
}
```

### Approach 2: Hash Map (Optimized)
```java
import java.util.*;

public class TwoSumOptimized {
    public static int[] twoSumHash(int[] nums, int target) {
        // Time Complexity: O(n) - single pass
        // Space Complexity: O(n) - hash map storage
        
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{}; // no solution
    }
}
```

---

## 🔢 3-SUM Problem

**Input:** `arr = [1, 2, -1, 0, -2, 1], target = 0`  
**Output:** `[[-2, -1, 1], [-2, 0, 2], [-1, 0, 1]]`

### Approach 1: Brute Force
```java
import java.util.*;

public class ThreeSumBruteForce {
    public static List<List<Integer>> threeSumBrute(int[] nums, int target) {
        // Time Complexity: O(n³) - three nested loops
        // Space Complexity: O(1) - ignoring output space
        
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    }
                }
            }
        }
        return result;
    }
}
```

### Approach 2: Hash Set
```java
import java.util.*;

public class ThreeSumHashing {
    public static List<List<Integer>> threeSumHash(int[] nums, int target) {
        // Time Complexity: O(n²) - fix one, solve 2-sum for rest
        // Space Complexity: O(n) - hash set for seen elements
        
        Set<List<Integer>> result = new HashSet<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            Set<Integer> seen = new HashSet<>();
            for (int j = i + 1; j < n; j++) {
                int complement = target - nums[i] - nums[j];
                if (seen.contains(complement)) {
                    List<Integer> triplet = Arrays.asList(nums[i], nums[j], complement);
                    Collections.sort(triplet); // to handle duplicates
                    result.add(triplet);
                }
                seen.add(nums[j]);
            }
        }
        return new ArrayList<>(result);
    }
}
```

### Approach 3: Two Pointers (Optimized)
```java
import java.util.*;

public class ThreeSumOptimized {
    public static List<List<Integer>> threeSumTwoPointer(int[] nums, int target) {
        // Time Complexity: O(n²) - O(n log n) for sort + O(n²) for main logic
        // Space Complexity: O(1) - ignoring output space, only using pointers
        
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums); // O(n log n)
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            // Skip duplicates for first element
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int left = i + 1, right = n - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    right--;
                    // Skip duplicates for left and right pointers
                    while (left < right && nums[left] == nums[left - 1]) left++;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }
}
```

---

## 🔢 4-SUM Problem

**Input:** `arr = [1, 0, -1, 0, -2, 2], target = 0`  
**Output:** `[[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]`

### Approach 1: Brute Force
```java
import java.util.*;

public class FourSumBruteForce {
    public static List<List<Integer>> fourSumBrute(int[] nums, int target) {
        // Time Complexity: O(n⁴) - four nested loops
        // Space Complexity: O(1) - ignoring output space
        
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {
                        if (nums[i] + nums[j] + nums[k] + nums[l] == target) {
                            result.add(Arrays.asList(nums[i], nums[j], nums[k], nums[l]));
                        }
                    }
                }
            }
        }
        return result;
    }
}
```

### Approach 2: Hash Set
```java
import java.util.*;

public class FourSumHashing {
    public static List<List<Integer>> fourSumHash(int[] nums, int target) {
        // Time Complexity: O(n³) - fix two, solve 2-sum for rest
        // Space Complexity: O(n) - hash set for seen elements
        
        Set<List<Integer>> result = new HashSet<>();
        int n = nums.length;

        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                Set<Integer> seen = new HashSet<>();
                for (int k = j + 1; k < n; k++) {
                    int complement = target - nums[i] - nums[j] - nums[k];
                    if (seen.contains(complement)) {
                        List<Integer> quadruplet = Arrays.asList(nums[i], nums[j], nums[k], complement);
                        Collections.sort(quadruplet);
                        result.add(quadruplet);
                    }
                    seen.add(nums[k]);
                }
            }
        }
        return new ArrayList<>(result);
    }
}
```

### Approach 3: Two Pointers (Optimized)
```java
import java.util.*;

public class FourSumOptimized {
    public static List<List<Integer>> fourSumTwoPointer(int[] nums, int target) {
        // Time Complexity: O(n³) - O(n log n) for sort + O(n³) for main logic
        // Space Complexity: O(1) - ignoring output space, only using pointers
        
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums); // O(n log n)
        int n = nums.length;

        for (int a = 0; a < n - 3; a++) {
            // Skip duplicates for first element
            if (a > 0 && nums[a] == nums[a - 1]) continue;

            for (int b = a + 1; b < n - 2; b++) {
                // Skip duplicates for second element
                if (b > a + 1 && nums[b] == nums[b - 1]) continue;

                int left = b + 1, right = n - 1;
                while (left < right) {
                    long sum = (long)nums[a] + nums[b] + nums[left] + nums[right];

                    if (sum == target) {
                        result.add(Arrays.asList(nums[a], nums[b], nums[left], nums[right]));
                        left++;
                        right--;
                        // Skip duplicates for left and right pointers
                        while (left < right && nums[left] == nums[left - 1]) left++;
                        while (left < right && nums[right] == nums[right + 1]) right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        return result;
    }
}
```

---

## 📊 Complexity Comparison

| Problem | Approach | Time Complexity | Space Complexity | Best For |
|---------|----------|-----------------|------------------|----------|
| **2-Sum** | Brute Force | O(n²) | O(1) | Small arrays |
| **2-Sum** | Hash Map | O(n) | O(n) | **Optimal** |
| **3-Sum** | Brute Force | O(n³) | O(1) | Understanding only |
| **3-Sum** | Hash Set | O(n²) | O(n) | Medium arrays |
| **3-Sum** | Two Pointers | O(n²) | O(1) | **Optimal** |
| **4-Sum** | Brute Force | O(n⁴) | O(1) | Very small arrays |
| **4-Sum** | Hash Set | O(n³) | O(n) | Medium arrays |
| **4-Sum** | Two Pointers | O(n³) | O(1) | **Optimal** |

---

## 🎯 Key Patterns & Tips

### 1. **Two Pointers Technique**
- **When to use:** After sorting, when you need to find pairs/triplets
- **How:** Use `left` and `right` pointers, move based on sum comparison
- **Advantage:** Space efficient O(1)

### 2. **Hashing Technique**
- **When to use:** When you need O(n) or O(n²) solutions
- **How:** Store seen elements, check for complement
- **Advantage:** Better time complexity, but uses extra space

### 3. **Duplicate Handling**
- Always sort array first for two-pointers approach
- Skip duplicates: `if (i > 0 && nums[i] == nums[i-1]) continue;`
- Use `Set<List<Integer>>` if hashing to avoid duplicate results

### 4. **General Pattern**
- **2-Sum:** Fix nothing, find 2 elements
- **3-Sum:** Fix 1 element, solve 2-sum for remaining
- **4-Sum:** Fix 2 elements, solve 2-sum for remaining
- **n-Sum:** Fix (n-2) elements, solve 2-sum for remaining

---

## 🧪 Sample Test Cases

```java
// Test cases for all problems
public static void testAllSums() {
    // 2-Sum
    int[] arr2 = {2, 7, 11, 15};
    System.out.println("2-Sum: " + Arrays.toString(twoSumHash(arr2, 9)));
    
    // 3-Sum  
    int[] arr3 = {-1, 0, 1, 2, -1, -4};
    System.out.println("3-Sum: " + threeSumTwoPointer(arr3, 0));
    
    // 4-Sum
    int[] arr4 = {1, 0, -1, 0, -2, 2};  
    System.out.println("4-Sum: " + fourSumTwoPointer(arr4, 0));
}
```

**Expected Outputs:**
- 2-Sum: `[0, 1]`
- 3-Sum: `[[-1, -1, 2], [-1, 0, 1]]`  
- 4-Sum: `[[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]`