Great one 👍 Let’s solve **Mean and Median of an Unsorted Array** step by step.

---

# 📝 Problem Statement

Given an **unsorted integer array**, find:

* **Mean** = Average of all numbers.
* **Median** = Middle element when sorted (if even length → average of two middle elements).

---

# 📥 Input Format

* An integer array `arr[]`.

# 📤 Output Format

* Print **mean** and **median** values.

---

# ✅ Example 1

**Input:**

```
arr = [1, 3, 2, 4, 5]
```

**Output:**

```
Mean = 3.0, Median = 3
```

**Explanation:**

* Mean = `(1+3+2+4+5)/5 = 15/5 = 3.0`
* Sorted = `[1, 2, 3, 4, 5]`, Median = middle = `3`.

---

# ✅ Example 2

**Input:**

```
arr = [7, 1, 3, 4]
```

**Output:**

```
Mean = 3.75, Median = 3.5
```

**Explanation:**

* Mean = `(7+1+3+4)/4 = 15/4 = 3.75`
* Sorted = `[1, 3, 4, 7]`, Median = `(3+4)/2 = 3.5`.

---

# 🛠 Approaches

---

## 1️⃣ Brute Force

### Logic

* Compute mean using sum.
* Sort array → pick middle element(s) for median.

### Java Code – Brute Force

```java
import java.util.Arrays;

public class MeanMedianBruteForce {

    public static void meanMedian(int[] arr) {
        int n = arr.length;

        // Mean
        double sum = 0;
        for (int num : arr) {
            sum += num; // accumulate sum
        }
        double mean = sum / n;

        // Median (sort first)
        Arrays.sort(arr); // O(n log n)
        double median;
        if (n % 2 == 0) {
            median = (arr[n / 2 - 1] + arr[n / 2]) / 2.0;
        } else {
            median = arr[n / 2];
        }

        System.out.println("Mean = " + mean + ", Median = " + median);
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 3, 2, 4, 5};
        meanMedian(arr1); // Mean = 3.0, Median = 3

        int[] arr2 = {7, 1, 3, 4};
        meanMedian(arr2); // Mean = 3.75, Median = 3.5
    }
}
```

### Complexity

* **Time:** `O(n log n)` (due to sorting).
* **Space:** `O(1)` (ignoring sorting’s in-place cost).

---

## 2️⃣ Optimized Approach

### Logic

* Mean → always `O(n)` (sum/n).
* Median → can be found in **O(n)** using **Quickselect (Median of Medians algorithm)** instead of full sorting.

### Java Code – Optimized (Quickselect for Median)

```java
import java.util.Random;

public class MeanMedianOptimized {

    // Quickselect to find kth smallest element
    private static int quickSelect(int[] arr, int left, int right, int k) {
        if (left == right) return arr[left];

        int pivotIndex = partition(arr, left, right);

        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            return quickSelect(arr, left, pivotIndex - 1, k);
        } else {
            return quickSelect(arr, pivotIndex + 1, right, k);
        }
    }

    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left;

        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
            }
        }

        int tmp = arr[i];
        arr[i] = arr[right];
        arr[right] = tmp;

        return i;
    }

    public static void meanMedian(int[] arr) {
        int n = arr.length;

        // Mean
        double sum = 0;
        for (int num : arr) sum += num;
        double mean = sum / n;

        // Median (Quickselect O(n))
        double median;
        if (n % 2 == 1) {
            median = quickSelect(arr.clone(), 0, n - 1, n / 2);
        } else {
            int leftMid = quickSelect(arr.clone(), 0, n - 1, n / 2 - 1);
            int rightMid = quickSelect(arr.clone(), 0, n - 1, n / 2);
            median = (leftMid + rightMid) / 2.0;
        }

        System.out.println("Mean = " + mean + ", Median = " + median);
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 3, 2, 4, 5};
        meanMedian(arr1); // Mean = 3.0, Median = 3

        int[] arr2 = {7, 1, 3, 4};
        meanMedian(arr2); // Mean = 3.75, Median = 3.5
    }
}
```

### Complexity

* **Time:**

  * Mean: `O(n)`
  * Median with Quickselect: `O(n)` average, `O(n^2)` worst-case (can be avoided using Median of Medians).
* **Space:** `O(1)`

---

# 📊 Comparison Table

| Approach    | Mean Calculation | Median Calculation   | Time Complexity | Space  |
| ----------- | ---------------- | -------------------- | --------------- | ------ |
| Brute Force | `O(n)`           | `O(n log n)` (sort)  | `O(n log n)`    | `O(1)` |
| Optimized   | `O(n)`           | `O(n)` (quickselect) | `O(n)` average  | `O(1)` |

---

