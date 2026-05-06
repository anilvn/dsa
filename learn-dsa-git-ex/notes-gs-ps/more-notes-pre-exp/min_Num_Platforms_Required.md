# Problem — in plain English

You’re given arrival and departure times for a set of trains that stop at a station.
You must compute the **minimum number of platforms** the station needs so that **no two trains that are at the station at the same time** have to wait.

A train is considered to occupy a platform from its arrival time up to its departure time. If two trains’ occupied time intervals overlap, they cannot share the same platform at that overlapping period.

---

# Core idea (one-liner)

Sort arrival times and departure times separately, then sweep through them with two pointers — whenever the next event is an arrival you need one more platform, whenever the next event is a departure you free a platform. Track the maximum platforms used at any time.

---

# Step-by-step approach

1. **Sort** the `arr[]` and `dep[]` arrays independently. This gives a chronological order of arrival events and departure events.
2. Use two indices `i` (for arrivals) and `j` (for departures), both starting at `0`.
3. Maintain `platform_needed = 0` and `maxPlatforms = 0`.
4. Compare `arr[i]` and `dep[j]`:

   * If `arr[i] <= dep[j]` → a train arrives before the earliest departure, so increment `platform_needed`, move `i++`, and update `maxPlatforms = max(maxPlatforms, platform_needed)`.
   * Else (`arr[i] > dep[j]`) → a train departs before the next arrival, so decrement `platform_needed`, move `j++`.
5. Continue until you have processed all arrivals (or both pointers reach end).
6. `maxPlatforms` is the minimum number of platforms required.

> **Tie rule:** The `<=` comparison treats an arrival at the same minute as a departure as overlapping (i.e., needs another platform). If your interpretation is that a train arriving exactly at the departure time can immediately use the same platform, change the check to `arr[i] < dep[j]`. (Use whichever interpretation matches the problem statement you have.)

---

# Time & Space Complexity

* Sorting: `O(n log n)`.
* Single sweep with two pointers: `O(n)`.
* **Overall:** `O(n log n)` time.
* **Extra space:** `O(1)` (ignoring input storage).

---

# Examples

**Example 1**
Input:
`arr = {900, 940, 950, 1100, 1500, 1800}`
`dep = {910, 1200, 1120, 1130, 1900, 2000}`
Output: `3`
Explanation: Between 9:40 and 11:20 there are at most 3 trains simultaneously, so 3 platforms required.

**Example 2**
Input:
`arr = {900, 940}`
`dep = {910, 1200}`
Output: `1`
Explanation: The first train departs at 9:10, so only one platform is needed overall.

---

# Java solution

Below is a complete Java implementation that follows the sorted sweep approach and uses the `<=` tie rule (arriving at the same time as a departure counts as an overlap). It uses the skeleton you provided and prints the test result.

```java
import java.util.Arrays;

public class Min_Num_Platforms_Required {
    
    public static int findPlatform(int arr[], int dep[]) {
        if (arr == null || dep == null) return 0;
        int n = arr.length;
        if (n == 0) return 0;
        if (dep.length != n) {
            // If lengths differ, use the minimum length to avoid out-of-bounds.
            n = Math.min(n, dep.length);
        }

        Arrays.sort(arr, 0, n);
        Arrays.sort(dep, 0, n);

        int platform_needed = 0;
        int maxPlatforms = 0;

        int i = 0; // index for arr
        int j = 0; // index for dep

        while (i < n && j < n) {
            // If next event is arrival (<= means arrival at same time as departure counts as overlap)
            if (arr[i] <= dep[j]) {
                platform_needed++;
                // if (platform_needed > maxPlatforms) {
                //     maxPlatforms = platform_needed;
                // }
                maxPlatforms = Math.max(maxPlatforms,platform_needed);
                i++;
            } else {
                // Next event is departure
                platform_needed--;
                j++;
            }
        }

        return maxPlatforms;
    }
    
    // Driver Code
    public static void main(String[] args)
    {
        int arr[]  = { 900, 940, 950, 1100, 1500, 1800 };
        int dep[]  = { 910, 1200, 1120, 1130, 1900, 2000 };
        
        int arr1[] = { 900, 940 };
        int dep1[] = { 910, 1200 }; 

        if (findPlatform(arr, dep) == 3 && findPlatform(arr1, dep1) == 1)
            System.out.println("All Tests Pass");
        else
            System.out.println("There are test failures");
    }
}
```

---

# Notes & edge cases

* If arrival and departure arrays have different lengths, you should clarify the problem before proceeding — above code handles that conservatively by using `min(lengths)`.
* If you want arrivals that happen exactly when a train departs **not** to count as overlapping, change the condition `if (arr[i] <= dep[j])` to `if (arr[i] < dep[j])`.
* Handle the case `n == 0` (no trains) by returning `0`.

---
<!-- 
If you want, I can:

* show a trace of the two-pointer sweep for Example 1 step-by-step, or
* provide a min-heap based solution (useful when arrivals are not all known up-front or you want a different style). Which would you like? -->


Perfect 👍 Let’s do a **dry run in tabular format** for both examples.
We’ll track these variables:

* `i` → pointer for **arrivals**
* `j` → pointer for **departures**
* `arr[i]` → next arrival time
* `dep[j]` → next departure time
* `platform_needed` → currently occupied platforms
* `maxPlatforms` → maximum platforms seen so far

---

## Example 1

Input:
`arr = {900, 940, 950, 1100, 1500, 1800}`
`dep = {910, 1120, 1130, 1200, 1900, 2000}`
(Notice I sorted `dep[]` → {910, 1120, 1130, 1200, 1900, 2000})

| Step | i | j | arr\[i] | dep\[j] | Action                           | platform\_needed | maxPlatforms |
| ---- | - | - | ------- | ------- | -------------------------------- | ---------------- | ------------ |
| 1    | 0 | 0 | 900     | 910     | Arrival ≤ Departure → +1         | 1                | 1            |
| 2    | 1 | 0 | 940     | 910     | Arrival > Departure → one leaves | 0                | 1            |
| 3    | 1 | 1 | 940     | 1120    | Arrival ≤ Departure → +1         | 1                | 1            |
| 4    | 2 | 1 | 950     | 1120    | Arrival ≤ Departure → +1         | 2                | 2            |
| 5    | 3 | 1 | 1100    | 1120    | Arrival ≤ Departure → +1         | 3                | 3            |
| 6    | 4 | 1 | 1500    | 1120    | Arrival > Departure → one leaves | 2                | 3            |
| 7    | 4 | 2 | 1500    | 1130    | Arrival > Departure → one leaves | 1                | 3            |
| 8    | 4 | 3 | 1500    | 1200    | Arrival > Departure → one leaves | 0                | 3            |
| 9    | 4 | 4 | 1500    | 1900    | Arrival ≤ Departure → +1         | 1                | 3            |
| 10   | 5 | 4 | 1800    | 1900    | Arrival ≤ Departure → +1         | 2                | 3            |
| 11   | - | - | end     |         | Finish                           | —                | **3**        |

✅ Answer = **3 platforms**

---

## Example 2

Input:
`arr = {900, 940}`
`dep = {910, 1200}` (already sorted)

| Step | i | j | arr\[i] | dep\[j] | Action                           | platform\_needed | maxPlatforms |
| ---- | - | - | ------- | ------- | -------------------------------- | ---------------- | ------------ |
| 1    | 0 | 0 | 900     | 910     | Arrival ≤ Departure → +1         | 1                | 1            |
| 2    | 1 | 0 | 940     | 910     | Arrival > Departure → one leaves | 0                | 1            |
| 3    | 1 | 1 | 940     | 1200    | Arrival ≤ Departure → +1         | 1                | 1            |
| 4    | - | - | end     |         | Finish                           | —                | **1**        |

✅ Answer = **1 platform**

---


