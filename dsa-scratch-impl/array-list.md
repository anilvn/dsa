# ArrayList Internals -- Implementing Dynamic Array from Scratch in Java

## How to Read This Document

Each section builds on the previous one. Read in order. Every step introduces one problem and solves it with code. Time complexity and edge cases follow each operation.

---

## Step 1 -- Fixed-Size Array Wrapper (The Foundation)

**Problem:** Raw arrays have no bounds checking, no size tracking, and no abstraction.

```java
public class MyArrayList<T> {
    private Object[] data;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList() {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
}
```

**What this gives us:** A typed wrapper around a plain array with size tracking and index validation.

---

## Step 2 -- Get and Set

**Problem:** Need O(1) random access and in-place update.

```java
@SuppressWarnings("unchecked")
public T get(int index) {
    checkIndex(index);
    return (T) data[index];
}

public void set(int index, T element) {
    checkIndex(index);
    data[index] = element;
}
```

| Operation | Time Complexity | Notes               |
|-----------|-----------------|---------------------|
| get       | O(1)            | Direct index access |
| set       | O(1)            | Direct index write  |

**Edge cases:** Both delegate to `checkIndex` -- negative index and out-of-bounds are caught.

---

## Step 3 -- Resize Logic (The Core of Dynamic Array)

**Problem:** A fixed-capacity array fills up. We need to grow it transparently.

**Strategy:** Double the capacity when full. This keeps the amortized cost of `add` at O(1).

```java
private void resize() {
    int newCapacity = data.length * 2;
    Object[] newData = new Object[newCapacity];
    System.arraycopy(data, 0, newData, 0, size); // O(n) copy
    data = newData;
}
```

**Why doubling?** If we grew by 1 each time, every `add` would cost O(n). Doubling means we copy n elements once every n adds -- amortized O(1) per add.

---

## Step 4 -- Add (Append)

**Problem:** Append an element to the end, growing the array if needed.

```java
public void add(T element) {
    if (size == data.length)
        resize();
    data[size++] = element;
}
```

| Operation | Time Complexity | Notes                              |
|-----------|-----------------|----------------------------------  |
| add (end) | O(1) amortized  | O(n) only when resize is triggered |

**Edge cases:** Resize is triggered before writing, so we never write out of bounds.

---

## Step 5 -- Add at Index

**Problem:** Insert at an arbitrary position, shifting elements right to make room.

```java
public void add(int index, T element) {
    if (index < 0 || index > size) // allow index == size (append)
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    if (size == data.length)
        resize();
    // shift elements right from the end to avoid overwriting
    for (int i = size; i > index; i--)
        data[i] = data[i - 1];
    data[index] = element;
    size++;
}
```

| Operation      | Time Complexity | Notes                                    |
|----------------|-----------------|------------------------------------------|
| add(index, el) | O(n)            | Worst case: insert at index 0, shift all |

**Edge cases:**
- `index == size` is valid (same as append).
- Shift starts from the end to avoid overwriting elements.

---

## Step 6 -- Remove by Index

**Problem:** Delete an element at a given position, shifting elements left to fill the gap.

```java
public T remove(int index) {
    checkIndex(index);
    @SuppressWarnings("unchecked")
    T removed = (T) data[index];
    for (int i = index; i < size - 1; i++)
        data[i] = data[i + 1];
    data[--size] = null; // null out last slot to allow GC
    return removed;
}
```

| Operation     | Time Complexity | Notes                                 |
|---------------|-----------------|---------------------------------------|
| remove(index) | O(n)            | Worst case: remove index 0, shift all |

**Edge cases:**
- Null out the last slot after shrinking `size` -- prevents memory leak (loitering object).
- Returns the removed element (matches Java's `ArrayList` contract).

---

## Step 7 -- Remove by Value

**Problem:** Find and remove the first occurrence of a given value.

```java
public boolean remove(Object value) {
    for (int i = 0; i < size; i++) {
        if (data[i] == null ? value == null : data[i].equals(value)) {
            remove(i); // reuse index-based remove
            return true;
        }
    }
    return false;
}
```

| Operation     | Time Complexity | Notes                                  |
|---------------|-----------------|----------------------------------------|
| remove(value) | O(n)            | Linear scan + O(n) shift in worst case |

**Edge cases:**
- Handles `null` values explicitly to avoid `NullPointerException`.
- Returns `false` if value not found (no exception).

---

## Step 8 -- (Optional) Shrinking Strategy

**Problem:** After many removals, the array wastes memory holding a large empty buffer.

**Strategy:** Shrink to half when size drops below 1/4 of capacity. Never shrink below `DEFAULT_CAPACITY`.

```java
private static final int DEFAULT_CAPACITY = 10;

private void shrinkIfNeeded() {
    if (size > 0 && size <= data.length / 4 && data.length / 2 >= DEFAULT_CAPACITY) {
        Object[] newData = new Object[data.length / 2];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }
}
```

Call `shrinkIfNeeded()` at the end of both `remove` methods.

**Why not shrink at 1/2?** If we shrink at 1/2 and grow at full, an alternating add/remove sequence would trigger resize on every operation -- O(n) per call. The 1/4 threshold creates a buffer zone.

---

## Complete Implementation

```java
public class MyArrayList<T> {
    private Object[] data;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList() {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    public void set(int index, T element) {
        checkIndex(index);
        data[index] = element;
    }

    public void add(T element) {
        if (size == data.length) resize();
        data[size++] = element;
    }

    public void add(int index, T element) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        if (size == data.length) resize();
        for (int i = size; i > index; i--)
            data[i] = data[i - 1];
        data[index] = element;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index);
        T removed = (T) data[index];
        for (int i = index; i < size - 1; i++)
            data[i] = data[i + 1];
        data[--size] = null;
        shrinkIfNeeded();
        return removed;
    }

    public boolean remove(Object value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == null ? value == null : data[i].equals(value)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    private void resize() {
        Object[] newData = new Object[data.length * 2];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    private void shrinkIfNeeded() {
        if (size > 0 && size <= data.length / 4 && data.length / 2 >= DEFAULT_CAPACITY) {
            Object[] newData = new Object[data.length / 2];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }
}
```

---

## Quick Verification

```java
public class Main {
    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>();

        list.add(10); list.add(20); list.add(30); list.add(40);
        System.out.println(list);           // [10, 20, 30, 40]

        list.add(1, 15);
        System.out.println(list);           // [10, 15, 20, 30, 40]

        list.set(0, 5);
        System.out.println(list);           // [5, 15, 20, 30, 40]

        list.remove(2);
        System.out.println(list);           // [5, 15, 30, 40]

        list.remove(Integer.valueOf(15));
        System.out.println(list);           // [5, 30, 40]

        System.out.println("Size: " + list.size());     // 3
        System.out.println("Empty: " + list.isEmpty()); // false
    }
}
```

---

## Summary -- Operation Complexity Table

| Operation       | Time Complexity | Notes                                    |
|-----------------|-----------------|------------------------------------------|
| get(index)      | O(1)            | Direct array access                      |
| set(index, val) | O(1)            | Direct array write                       |
| add(val)        | O(1) amortized  | O(n) on resize, but rare due to doubling |
| add(index, val) | O(n)            | Shift right from index to end            |
| remove(index)   | O(n)            | Shift left from index to end             |
| remove(value)   | O(n)            | Linear scan + shift                      |
| size()          | O(1)            | Tracked field                            |
| isEmpty()       | O(1)            | size == 0 check                          |
| resize (grow)   | O(n)            | Amortized O(1) per add due to doubling   |
| shrink          | O(n)            | Triggered at 1/4 capacity                |

---

## Static Array vs Custom ArrayList vs Java ArrayList

| Aspect            | Static Array      | Custom ArrayList (this impl)     | Java ArrayList                              |
|-------------------|-------------------|----------------------------------|---------------------------------------------|
| Size              | Fixed at creation | Grows/shrinks dynamically        | Grows dynamically (no auto-shrink)          |
| Type safety       | Native generics   | Unchecked cast internally        | Same unchecked cast internally              |
| Null support      | Yes               | Yes (handled explicitly)         | Yes                                         |
| Primitives        | Yes (int[], etc.) | No (autoboxing required)         | No (autoboxing required)                    |
| Shrinking         | N/A               | Manual (1/4 threshold)           | Only via trimToSize()                       |
| Initial capacity  | You decide        | 10 (configurable)                | 10 (configurable via constructor)           |
| Growth factor     | N/A               | 2x                               | 1.5x (newCapacity = old + old >> 1)         |
| Thread safety     | No                | No                               | No (use Vector or synchronizedList)         |
| Fail-fast iter    | No                | Not implemented                  | Yes (modCount tracking)                     |

**Key insight:** Java's `ArrayList` uses a 1.5x growth factor instead of 2x to waste less memory at the cost of slightly more frequent resizes. Everything else is conceptually identical to what we built here.

---

## Interview Cheat Sheet

- Internal structure: `Object[]` + `int size` + `int capacity`
- Resize trigger: `size == capacity`
- Growth strategy: double (or 1.5x in Java's impl)
- Amortized O(1) add: total copy work across n adds = n + n/2 + n/4 + ... = 2n = O(n), so O(1) per add
- Shrink threshold: 1/4 (not 1/2) to prevent add/remove thrashing
- Memory leak prevention: null out removed slots so GC can collect
- `add(index)` and `remove(index)` are O(n) -- this is the main weakness vs LinkedList for middle insertions

---

## Practice Problems

Try solving all array problems with `ArrayList`.

- Reverse a list
- Find max/min
- Remove duplicates
- Merge two lists
- Rotate list elements
