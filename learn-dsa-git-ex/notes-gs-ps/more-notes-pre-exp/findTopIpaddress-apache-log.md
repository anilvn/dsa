

## 📌 Problem Restatement (in simple words)

You are given a **log file** in the form of an array of strings.
Each log entry begins with an **IP address**, followed by some text (log details).

👉 Your task is to **find the IP address that appears most frequently** in the log file.

If there are multiple with the same highest frequency, you could:

* return any one of them, or
* return all of them (depends on how you want to extend it).

---

## 📝 Input & Output

### Input

* `lines[]` → an array of log entries.
  Each entry looks like:

```
"10.0.0.1 - log entry 1 11"
"10.0.0.1 - log entry 2 213"
"10.0.0.2 - log entry 133132"
```

### Output

* A string representing the IP address that appears **most often**.

### Example

```java
Input:
["10.0.0.1 - log entry 1 11",
 "10.0.0.1 - log entry 2 213",
 "10.0.0.2 - log entry 133132"]

Output:
"10.0.0.1"
```

Because:

* `10.0.0.1` → appears **2 times**
* `10.0.0.2` → appears **1 time**

So, most frequent = `10.0.0.1`.

---

## 🌱 Intuition (with Analogy)

Think of it like **attendance tracking in a class**:

* Each student signs their name (IP address).
* We want to know **which student signed in most times**.

Steps:

1. Record each student’s attendance in a notebook (HashMap → IP vs count).
2. At the end, check who has the **highest count**.

---

## 🚀 Approaches

### 1️⃣ Brute Force

* For each log line, extract the IP.
* Count how many times it appears by scanning the array again.
* Keep track of max frequency.

```java
import java.util.*;

public class Solution {

  // Brute Force Approach
  public static String findTopIpaddress(String[] lines) {
    String topIp = "";
    int maxCount = 0;

    for (int i = 0; i < lines.length; i++) {
      String ip = lines[i].split(" ")[0]; // first token is IP
      int count = 0;

      // count occurrences of this IP in the array
      for (int j = 0; j < lines.length; j++) {
        if (lines[j].startsWith(ip)) {
          count++;
        }
      }

      if (count > maxCount) {
        maxCount = count;
        topIp = ip;
      }
    }
    return topIp;
  }

  public static void main(String[] args) {
    String lines[] = {
        "10.0.0.1 - log entry 1 11",
        "10.0.0.1 - log entry 2 213",
        "10.0.0.2 - log entry 133132"
    };
    System.out.println(findTopIpaddress(lines));
  }
}
```

**Complexity Analysis**

* Time: **O(n²)** (for each IP we scan the whole array again).
* Space: **O(1)** (no extra data structures).

---

### 2️⃣ Optimized (Using HashMap)

* Use a **HashMap\<String, Integer>** to store frequency of each IP.
* Traverse once, fill map.
* Find IP with maximum frequency.

```java
import java.util.*;

public class Solution {

  // Optimized Approach using HashMap
  public static String findTopIpaddress(String[] lines) {
    Map<String, Integer> freqMap = new HashMap<>();

    // Step 1: Count frequency of each IP
    for (String line : lines) {
      String ip = line.split(" ")[0]; // extract first word (IP)
      freqMap.put(ip, freqMap.getOrDefault(ip, 0) + 1);
    }

    // Step 2: Find IP with maximum frequency
    String topIp = "";
    int maxCount = 0;
    for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
      if (entry.getValue() > maxCount) {
        maxCount = entry.getValue();
        topIp = entry.getKey();
      }
    }
    return topIp;
  }

  public static void main(String[] args) {
    String lines[] = {
        "10.0.0.1 - log entry 1 11",
        "10.0.0.1 - log entry 2 213",
        "10.0.0.2 - log entry 133132"
    };
    System.out.println(findTopIpaddress(lines)); // Output: 10.0.0.1
  }
}
```

**Complexity Analysis**

* Time: **O(n)** (one pass for counting, one pass for max).
* Space: **O(n)** (map stores up to `n` distinct IPs).

---

⚡ Analogy Recap:

* Brute Force = asking every student, for each student: *“How many times did you sign attendance?”* → slow.
* Optimized = keeping a **register (HashMap)** → fast lookup, just check once.

---
