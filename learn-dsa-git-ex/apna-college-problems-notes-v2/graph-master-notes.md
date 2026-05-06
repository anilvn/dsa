# Graph — Master Notes (Zero to Hero)

---

## Table of Contents

1. [Cycle Detection in Undirected Graph](#1-cycle-detection-in-undirected-graph)
2. [Cycle Detection in Directed Graph](#2-cycle-detection-in-directed-graph)
3. [Topological Sort](#3-topological-sort)

---

## 1. Cycle Detection in Undirected Graph

**Problem**: Given an undirected graph, detect if a cycle exists.

**Key Idea**: During DFS, if we reach a node that is already visited AND it is not the parent of the current node → cycle exists. Pass `parent` into DFS to avoid treating the edge we came from as a back edge.

---

```java
import java.util.*;

class CycleUndirected {
    static class Edge {
        int src, dest;
        Edge(int s, int d) { src = s; dest = d; }
    }

    public static void createGraph(ArrayList<Edge>[] graph) {
        for (int i = 0; i < graph.length; i++) graph[i] = new ArrayList<>();

        graph[0].add(new Edge(0, 1)); graph[1].add(new Edge(1, 0));
        graph[0].add(new Edge(0, 2)); graph[2].add(new Edge(2, 0));
        graph[1].add(new Edge(1, 2)); graph[2].add(new Edge(2, 1)); // forms cycle 0-1-2
        graph[2].add(new Edge(2, 3)); graph[3].add(new Edge(3, 2));
    }

    public static boolean isCycle(ArrayList<Edge>[] graph, boolean[] vis, int curr, int par) {
        vis[curr] = true;

        for (Edge e : graph[curr]) {
            if (vis[e.dest] && e.dest != par) return true; // visited + not parent → cycle
            else if (!vis[e.dest]) {
                if (isCycle(graph, vis, e.dest, curr)) return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int V = 4;
        ArrayList<Edge>[] graph = new ArrayList[V];
        createGraph(graph);

        boolean[] vis = new boolean[V];
        for (int i = 0; i < V; i++) {
            if (!vis[i] && isCycle(graph, vis, i, -1)) {
                System.out.println("Cycle exists"); return;
            }
        }
        System.out.println("No cycle");
    }
}
```

> Pass `par = -1` for the starting node. Loop over all vertices in main to handle disconnected graphs. Undirected edges must be added in both directions.

---

## 2. Cycle Detection in Directed Graph

**Problem**: Given a directed graph, detect if a cycle exists.

**Key Idea**: Use two boolean arrays — `vis[]` (visited ever) and `rec[]` (in current recursion stack). If we reach a node where `rec[dest] == true`, a back edge exists → cycle. Reset `rec[curr] = false` on backtrack.

---

```java
import java.util.*;

class CycleDirected {
    static class Edge {
        int src, dest;
        Edge(int s, int d) { src = s; dest = d; }
    }

    public static void createGraph(ArrayList<Edge>[] graph) {
        for (int i = 0; i < graph.length; i++) graph[i] = new ArrayList<>();

        graph[0].add(new Edge(0, 1));
        graph[1].add(new Edge(1, 2));
        graph[2].add(new Edge(2, 3));
        graph[3].add(new Edge(3, 1)); // back edge → cycle: 1→2→3→1
    }

    public static boolean isCycle(ArrayList<Edge>[] graph, boolean[] vis, boolean[] rec, int curr) {
        vis[curr] = true;
        rec[curr] = true; // add to current path

        for (Edge e : graph[curr]) {
            if (rec[e.dest]) return true;          // already in path → cycle
            if (!vis[e.dest]) {
                if (isCycle(graph, vis, rec, e.dest)) return true;
            }
        }

        rec[curr] = false; // backtrack — remove from current path
        return false;
    }

    public static void main(String[] args) {
        int V = 4;
        ArrayList<Edge>[] graph = new ArrayList[V];
        createGraph(graph);

        boolean[] vis = new boolean[V];
        boolean[] rec = new boolean[V];

        for (int i = 0; i < V; i++) {
            if (!vis[i] && isCycle(graph, vis, rec, i)) {
                System.out.println("Cycle exists"); return;
            }
        }
        System.out.println("No cycle");
    }
}
```

> `vis[]` prevents re-running DFS on already-explored nodes. `rec[]` tracks only the active call stack. Backtracking (`rec[curr] = false`) is mandatory — without it, nodes from finished paths would falsely trigger cycle detection.

---

## 3. Topological Sort

**Problem**: Given a DAG (Directed Acyclic Graph), return a linear ordering of vertices such that for every edge `u → v`, `u` appears before `v`.

**Key Idea**: Only valid on DAGs. Two approaches — DFS (push to stack after all neighbors done) and BFS/Kahn's (process nodes with indegree 0 first).

---

### Approach 1 — DFS + Stack `O(V + E)`

```java
import java.util.*;

class TopoSortDFS {
    static class Edge {
        int src, dest;
        Edge(int s, int d) { src = s; dest = d; }
    }

    public static void createGraph(ArrayList<Edge>[] graph) {
        for (int i = 0; i < graph.length; i++) graph[i] = new ArrayList<>();
        graph[0].add(new Edge(0, 2));
        graph[0].add(new Edge(0, 3));
        graph[1].add(new Edge(1, 3));
        graph[2].add(new Edge(2, 4));
        graph[3].add(new Edge(3, 4));
    }

    public static void dfs(ArrayList<Edge>[] graph, int curr, boolean[] vis, Stack<Integer> stack) {
        vis[curr] = true;
        for (Edge e : graph[curr]) {
            if (!vis[e.dest]) dfs(graph, e.dest, vis, stack);
        }
        stack.push(curr); // push only after all neighbors are done
    }

    public static void topoSort(ArrayList<Edge>[] graph) {
        boolean[] vis = new boolean[graph.length];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < graph.length; i++) {
            if (!vis[i]) dfs(graph, i, vis, stack);
        }

        while (!stack.isEmpty()) System.out.print(stack.pop() + " ");
    }

    public static void main(String[] args) {
        int V = 5;
        ArrayList<Edge>[] graph = new ArrayList[V];
        createGraph(graph);
        System.out.println("Topological Sort (DFS):");
        topoSort(graph); // Output: 1 0 3 2 4
    }
}
```

> A node is pushed to the stack only after all its descendants are processed. Popping the stack gives the correct topological order (reverse of finish time).

---

### Approach 2 — BFS / Kahn's Algorithm `O(V + E)`

```java
import java.util.*;

class TopoSortBFS {
    static class Edge {
        int src, dest;
        Edge(int s, int d) { src = s; dest = d; }
    }

    public static void createGraph(ArrayList<Edge>[] graph) {
        for (int i = 0; i < graph.length; i++) graph[i] = new ArrayList<>();
        graph[0].add(new Edge(0, 2));
        graph[0].add(new Edge(0, 3));
        graph[1].add(new Edge(1, 3));
        graph[2].add(new Edge(2, 4));
        graph[3].add(new Edge(3, 4));
    }

    public static void topoSortBFS(ArrayList<Edge>[] graph) {
        int V = graph.length;
        int[] indeg = new int[V];

        for (int i = 0; i < V; i++)
            for (Edge e : graph[i]) indeg[e.dest]++;

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < V; i++)
            if (indeg[i] == 0) q.add(i); // start with nodes that have no dependencies

        while (!q.isEmpty()) {
            int curr = q.remove();
            System.out.print(curr + " ");

            for (Edge e : graph[curr]) {
                indeg[e.dest]--;
                if (indeg[e.dest] == 0) q.add(e.dest); // dependency resolved
            }
        }
    }

    public static void main(String[] args) {
        int V = 5;
        ArrayList<Edge>[] graph = new ArrayList[V];
        createGraph(graph);
        System.out.println("Topological Sort (BFS - Kahn's):");
        topoSortBFS(graph); // Output: 0 1 2 3 4
    }
}
```

> Kahn's processes nodes level by level. If after processing all nodes the count < V, a cycle exists (some nodes never reached indegree 0). Preferred for cycle detection + topo sort together.

---

### Quick Comparison

| | DFS-based | BFS / Kahn's |
|---|---|---|
| Uses | Stack + recursion | Queue + indegree array |
| Cycle detection | Separate `rec[]` needed | If processed count < V → cycle |
| Intuition | Reverse finish time | Process dependencies first |
| Best for | Pure ordering | Scheduling + cycle check |

---
