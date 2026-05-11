This is the classic **Gas Station (Circular Tour)** problem — a very important greedy pattern for interviews.

---

# Problem Understanding

You are given:

* `gas[i]` → fuel available at station `i`
* `cost[i]` → fuel needed to go from `i → i+1`

You need to:

* Find a starting index such that you can complete the full circular tour
* Return `-1` if not possible

---

# Key Insight (Most Important)

Two conditions:

### 1. Total Gas Check (Feasibility)

If:

```
totalGas < totalCost → impossible → return -1
```

### 2. Greedy Observation

If you fail at station `i`, then:

> **Any station between previous start and `i` can NEVER be a valid start**

So:
👉 Move start to `i + 1`

---

# Optimal Approach (Greedy – O(n))

### Idea

* Traverse once
* Track current fuel (`tank`)
* If tank becomes negative → reset start

---

# Algorithm

1. Initialize:

```
total = 0
tank = 0
start = 0
```

2. Loop through stations:

```
diff = gas[i] - cost[i]

total += diff
tank += diff

if (tank < 0):
    start = i + 1
    tank = 0
```

3. Final check:

```
if (total >= 0) return start
else return -1
```

---

# Java Code (Clean, Interview Ready)

```java
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0;
        int tank = 0;
        int start = 0;

        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            
            total += diff;
            tank += diff;

            // If we cannot reach next station
            if (tank < 0) {
                start = i + 1;  // shift start
                tank = 0;       // reset tank
            }
        }

        return total >= 0 ? start : -1;
    }
}
```

---

# Dry Run (Your Example)

```
gas  = [1,2,3,4,5]
cost = [3,4,5,1,2]
```

| i | diff | tank | total | start |
| - | ---- | ---- | ----- | ----- |
| 0 | -2   | -2 ❌ | -2    | 1     |
| 1 | -2   | -2 ❌ | -4    | 2     |
| 2 | -2   | -2 ❌ | -6    | 3     |
| 3 | +3   | 3    | -3    | 3     |
| 4 | +3   | 6    | 0     | 3     |

Final:

```
total = 0 → valid
start = 3
```

---

# Why Greedy Works (Important Interview Explanation)

If you fail at `i`, that means:

```
sum(start → i) < 0
```

So any index between `start` and `i`:

```
sum(that_index → i) will be EVEN smaller
```

Hence:
👉 None of them can be valid starting points

---

# Complexity

| Metric | Value |
| ------ | ----- |
| Time   | O(n)  |
| Space  | O(1)  |

---

# Edge Cases

* All zeros → return 0
* Single station → check `gas[0] >= cost[0]`
* No valid solution → return -1

---

# Interview One-Line Summary

> If total gas ≥ total cost, solution exists. Use greedy: reset start whenever tank goes negative.

---

If you want, I can also give:

* Brute force approach (for understanding)
* Visualization of circular traversal
* Similar problems (truck tour, circular arrays)
