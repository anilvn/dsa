# Graphs -- Implementing Algorithms from Scratch in Java

## How to Read This Document

Each section introduces one graph algorithm or concept. Code is clean with brief inline comments. Time and space complexity follow each section.

---

## Step 1 -- Graph Representation

**Problem:** Model a graph where nodes (vertices) are connected by edges, optionally with weights.

### Edge Structure

```java
static class Edge {
    int src;  // source vertex
    int dest; // destination vertex
    int wt;   // weight (0 if unweighted)

    public Edge(int s, int d, int w) {
        this.src  = s;
        this.dest = d;
        this.wt   = w;
    }
}
```

### Adjacency List

Store the graph as an array of `ArrayList<Edge>`. Index = vertex number.

```java
int V = 7; // number of vertices
ArrayList<Edge>[] graph = new ArrayList[V];
for (int i = 0; i < V; i++) {
    graph[i] = new ArrayList<>();
}

// Add undirected edge between u and v with weight w
graph[u].add(new Edge(u, v, w));
graph[v].add(new Edge(v, u, w)); // omit this line for directed graphs
```

### Graph Types

| Type             | Description                          | Edge addition              |
|------------------|--------------------------------------|----------------------------|
| Directed         | Edges have direction (A to B only)   | One-way only               |
| Undirected       | Edges are bidirectional              | Both directions            |
| Weighted         | Edges carry a cost                   | `wt` field non-zero        |
| Unweighted       | All edges have equal cost            | `wt` field ignored         |

---

## Step 2 -- BFS (Breadth-First Search)

**Problem:** Traverse all vertices level by level starting from a source. Finds shortest path in unweighted graphs.

**Time:** O(V + E) | **Space:** O(V)

```java
public static void bfs(ArrayList<Edge>[] graph, int V) {
    boolean[] visited = new boolean[V];
    Queue<Integer> q  = new LinkedList<>();

    q.add(0);           // start from vertex 0
    visited[0] = true;

    while (!q.isEmpty()) {
        int curr = q.remove();
        System.out.print(curr + " ");

        for (Edge e : graph[curr]) {
            if (!visited[e.dest]) {
                visited[e.dest] = true;
                q.add(e.dest);     // enqueue unvisited neighbors
            }
        }
    }
}
```

**Key points:**
- Mark visited before enqueuing (not after dequeuing) to avoid duplicate processing.
- Use BFS when: shortest path in unweighted graphs, level-order traversal, connected components.

---

## Step 3 -- DFS (Depth-First Search)

**Problem:** Traverse by going as deep as possible along each branch before backtracking.

**Time:** O(V + E) | **Space:** O(V)

```java
public static void dfs(ArrayList<Edge>[] graph, int curr, boolean[] visited) {
    if (visited[curr]) return;

    System.out.print(curr + " ");
    visited[curr] = true;

    for (Edge e : graph[curr]) {
        dfs(graph, e.dest, visited); // recurse into each unvisited neighbor
    }
}

// Call from main:
// boolean[] visited = new boolean[V];
// dfs(graph, 0, visited);
```

**Key points:**
- Recursive call stack acts as the traversal stack.
- Use DFS when: topological sorting, cycle detection, path finding.

---

## Step 4 -- All Paths Between Two Nodes

**Problem:** Find and print all possible paths from source to target.

**Time:** O(V^V) worst case (exponential) | **Space:** O(V)

```java
public static void printAllPaths(ArrayList<Edge>[] graph, int src, int tar,
                                 String path, boolean[] vis) {
    if (src == tar) {
        System.out.println(path); // found one complete path
        return;
    }

    for (int i = 0; i < graph[src].size(); i++) {
        Edge e = graph[src].get(i);
        if (!vis[e.dest]) {
            vis[e.dest] = true;
            printAllPaths(graph, e.dest, tar, path + "->" + e.dest, vis);
            vis[e.dest] = false; // backtrack: unmark to allow other paths
        }
    }
}

// Call:
// vis[src] = true;
// printAllPaths(graph, src, tar, "" + src, vis);
```

**Backtracking:** After returning from a recursive call, unmark the vertex so it can be used in other paths.

---

## Step 5 -- Cycle Detection

### Undirected Graph

**Logic:** A cycle exists if we visit a node that is already visited and is not the direct parent of the current node.

```java
public static boolean isCyclicUndirected(ArrayList<Edge>[] graph, boolean[] vis,
                                         int curr, int par) {
    vis[curr] = true;

    for (int i = 0; i < graph[curr].size(); i++) {
        Edge e = graph[curr].get(i);

        if (vis[e.dest] && e.dest != par) {
            return true;              // visited and not parent = back edge = cycle
        } else if (e.dest == par) {
            continue;                 // skip the edge back to parent
        } else if (!vis[e.dest]) {
            if (isCyclicUndirected(graph, vis, e.dest, curr)) return true;
        }
    }
    return false;
}
```

### Directed Graph

**Logic:** Use a recursion stack (`stack[]`). A back edge (edge to a node already in the current DFS path) means a cycle.

```java
public static boolean isCyclicDirected(ArrayList<Edge>[] graph, int curr,
                                        boolean[] vis, boolean[] stack) {
    vis[curr]   = true;
    stack[curr] = true;

    for (int i = 0; i < graph[curr].size(); i++) {
        Edge e = graph[curr].get(i);

        if (stack[e.dest]) {
            return true;              // back edge -- cycle in directed graph
        } else if (!vis[e.dest] && isCyclicDirected(graph, e.dest, vis, stack)) {
            return true;
        }
    }

    stack[curr] = false;              // remove from recursion stack on exit
    return false;
}
```

| Graph Type  | Key Data Structure | Cycle Condition                              |
|-------------|-------------------|----------------------------------------------|
| Undirected  | parent tracking   | visited neighbor != parent                   |
| Directed    | recursion stack   | neighbor is in current DFS stack             |

---

## Step 6 -- Topological Sort

**Problem:** Linear ordering of vertices in a DAG such that for every edge u to v, u appears before v.

**Time:** O(V + E) | **Space:** O(V)

```java
public static void topoSortUtil(ArrayList<Edge>[] graph, int curr,
                                boolean[] vis, Stack<Integer> s) {
    vis[curr] = true;

    for (int i = 0; i < graph[curr].size(); i++) {
        Edge e = graph[curr].get(i);
        if (!vis[e.dest]) {
            topoSortUtil(graph, e.dest, vis, s);
        }
    }

    s.push(curr); // push only after all neighbors are processed
}

public static void topoSort(ArrayList<Edge>[] graph) {
    boolean[] vis = new boolean[graph.length];
    Stack<Integer> s = new Stack<>();

    for (int i = 0; i < graph.length; i++) {
        if (!vis[i]) {
            topoSortUtil(graph, i, vis, s);
        }
    }

    while (!s.isEmpty()) {
        System.out.print(s.pop() + " ");
    }
}
```

**Key insight:** Pushing after processing all neighbors ensures that a vertex appears before all vertices it points to. Popping from the stack gives topological order.

**Use cases:** Course scheduling, build systems, task dependency resolution.

---

## Step 7 -- Dijkstra's Algorithm (Shortest Path, Non-Negative Weights)

**Problem:** Find the shortest distance from a source vertex to all other vertices.

**Time:** O(E log V) | **Space:** O(V)

```java
static class Pair implements Comparable<Pair> {
    int node;
    int dist;

    public Pair(int node, int dist) {
        this.node = node;
        this.dist = dist;
    }

    public int compareTo(Pair p2) {
        return this.dist - p2.dist; // min-heap by distance
    }
}

public static int[] dijkstra(ArrayList<Edge>[] graph, int src) {
    int[] dist      = new int[graph.length];
    boolean[] vis   = new boolean[graph.length];
    PriorityQueue<Pair> pq = new PriorityQueue<>();

    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    pq.add(new Pair(src, 0));

    while (!pq.isEmpty()) {
        Pair curr = pq.remove();

        if (!vis[curr.node]) {
            vis[curr.node] = true;

            for (Edge e : graph[curr.node]) {
                // relax edge: if shorter path found, update and enqueue
                if (!vis[e.dest] && dist[e.src] + e.wt < dist[e.dest]) {
                    dist[e.dest] = dist[e.src] + e.wt;
                    pq.add(new Pair(e.dest, dist[e.dest]));
                }
            }
        }
    }
    return dist;
}
```

**Limitation:** Does not handle negative edge weights. Use Bellman-Ford for negative weights.

---

## Step 8 -- Bellman-Ford Algorithm (Shortest Path, Handles Negative Weights)

**Problem:** Find shortest paths and detect negative weight cycles.

**Time:** O(V * E) | **Space:** O(V)

```java
public static void bellmanFord(ArrayList<Edge>[] graph, int src) {
    int[] dist = new int[graph.length];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;

    // relax all edges V-1 times
    for (int i = 0; i < graph.length - 1; i++) {
        for (int j = 0; j < graph.length; j++) {
            for (Edge e : graph[j]) {
                if (dist[e.src] != Integer.MAX_VALUE && dist[e.src] + e.wt < dist[e.dest]) {
                    dist[e.dest] = dist[e.src] + e.wt;
                }
            }
        }
    }

    // Vth relaxation -- if any update occurs, a negative cycle exists
    for (int j = 0; j < graph.length; j++) {
        for (Edge e : graph[j]) {
            if (dist[e.src] != Integer.MAX_VALUE && dist[e.src] + e.wt < dist[e.dest]) {
                System.out.println("Negative weight cycle exists");
                return;
            }
        }
    }
}
```

**Why V-1 relaxations?** In the worst case, the shortest path visits V-1 edges. After V-1 rounds, all shortest paths (without negative cycles) are finalized.

---

## Step 9 -- Prim's Algorithm (Minimum Spanning Tree)

**Problem:** Find the minimum cost set of edges that connects all vertices in an undirected weighted graph.

**Time:** O(E log E) | **Space:** O(V)

```java
static class Pair implements Comparable<Pair> {
    int vertex;
    int weight;

    public Pair(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public int compareTo(Pair p2) {
        return this.weight - p2.weight; // min-heap by weight
    }
}

public static void primAlgo(ArrayList<Edge>[] graph) {
    boolean[] vis = new boolean[graph.length];
    PriorityQueue<Pair> pq = new PriorityQueue<>();
    pq.add(new Pair(0, 0));
    int totalCost = 0;

    while (!pq.isEmpty()) {
        Pair curr = pq.remove();

        if (!vis[curr.vertex]) {
            vis[curr.vertex] = true;
            totalCost += curr.weight; // include edge cost in MST

            for (Edge e : graph[curr.vertex]) {
                if (!vis[e.dest]) {
                    pq.add(new Pair(e.dest, e.wt)); // add unvisited neighbors
                }
            }
        }
    }

    System.out.println("MST Cost: " + totalCost);
}
```

---

## Step 10 -- Kosaraju's Algorithm (Strongly Connected Components)

**Problem:** Find all strongly connected components (SCCs) in a directed graph. An SCC is a maximal set of vertices where every vertex is reachable from every other.

**Time:** O(V + E) | **Space:** O(V)

**Three steps:**

1. DFS the original graph; push vertices to a stack in finish-time order.
2. Build the transpose graph (reverse all edges).
3. Pop vertices from the stack and DFS on the transpose -- each DFS tree is one SCC.

```java
public static void topSort(ArrayList<Edge>[] graph, int curr,
                           Stack<Integer> s, boolean[] vis) {
    vis[curr] = true;
    for (Edge e : graph[curr]) {
        if (!vis[e.dest]) topSort(graph, e.dest, s, vis);
    }
    s.push(curr); // push after processing all neighbors
}

public static void dfsTranspose(ArrayList<Edge>[] graph, boolean[] vis, int curr) {
    System.out.print(curr + " ");
    vis[curr] = true;
    for (Edge e : graph[curr]) {
        if (!vis[e.dest]) dfsTranspose(graph, vis, e.dest);
    }
}

public static void kosaraju(ArrayList<Edge>[] graph, int V) {
    // Step 1: topological sort on original graph
    Stack<Integer> s = new Stack<>();
    boolean[] vis    = new boolean[V];
    for (int i = 0; i < V; i++) {
        if (!vis[i]) topSort(graph, i, s, vis);
    }

    // Step 2: build transpose graph
    ArrayList<Edge>[] transpose = new ArrayList[V];
    for (int i = 0; i < V; i++) {
        transpose[i] = new ArrayList<>();
        vis[i]       = false;
    }
    for (int i = 0; i < V; i++) {
        for (Edge e : graph[i]) {
            transpose[e.dest].add(new Edge(e.dest, e.src, e.wt));
        }
    }

    // Step 3: DFS on transpose in stack order -- each DFS = one SCC
    while (!s.isEmpty()) {
        int curr = s.pop();
        if (!vis[curr]) {
            System.out.print("SCC: ");
            dfsTranspose(transpose, vis, curr);
            System.out.println();
        }
    }
}
```

---

## Step 11 -- Tarjan's Algorithm

### Bridge Detection

**Problem:** Find edges whose removal increases the number of connected components.

**Key concepts:**
- `dt[v]` (discovery time): timestamp when vertex v is first visited.
- `low[v]` (low link): minimum discovery time reachable from the subtree rooted at v.
- An edge `(u, v)` is a bridge if `dt[u] < low[v]` (subtree of v cannot reach back to u or its ancestors).

### Articulation Points

**Problem:** Find vertices whose removal increases the number of connected components.

**Conditions:**
- Root of DFS tree: articulation point if it has more than 1 DFS tree child.
- Non-root vertex u: articulation point if it has a child v where `low[v] >= dt[u]` (subtree of v cannot bypass u).

---

## Practice Problems

- Rotten Oranges (BFS): minimum time for all oranges to rot; multi-source BFS from all initially rotten cells. Time O(m*n).
- Number of Islands (DFS/BFS): count connected components of '1's; DFS/BFS from each unvisited '1'. Time O(m*n).
- Course Schedule (Cycle Detection): determine if all courses can be finished; detect cycle in directed graph. Time O(V+E).

---

## Summary -- Complexity Table

| Algorithm          | Time Complexity | Space Complexity | Use Case                              |
|--------------------|-----------------|------------------|---------------------------------------|
| BFS                | O(V + E)        | O(V)             | Shortest path (unweighted), levels    |
| DFS                | O(V + E)        | O(V)             | Traversal, cycle detection, topo sort |
| All Paths          | O(V^V)          | O(V)             | Enumerate all paths (backtracking)    |
| Cycle (Undirected) | O(V + E)        | O(V)             | Parent tracking in DFS                |
| Cycle (Directed)   | O(V + E)        | O(V)             | Recursion stack tracking in DFS       |
| Topological Sort   | O(V + E)        | O(V)             | Dependency ordering in DAG            |
| Dijkstra           | O(E log V)      | O(V)             | Shortest path, non-negative weights   |
| Bellman-Ford       | O(V * E)        | O(V)             | Shortest path, negative weights       |
| Prim's MST         | O(E log E)      | O(V)             | Minimum spanning tree                 |
| Kosaraju SCC       | O(V + E)        | O(V)             | Strongly connected components         |
| Tarjan's           | O(V + E)        | O(V)             | Bridges, articulation points          |

---

## Interview Cheat Sheet

- BFS for shortest path in unweighted graphs. DFS for everything else (topo sort, cycle, paths).
- Dijkstra needs a min-heap (PriorityQueue). Fails on negative weights.
- Bellman-Ford relaxes V-1 times. Vth relaxation detects negative cycles.
- Topological sort only works on DAGs. Push to stack after all neighbors are processed.
- Kosaraju: 3 steps -- topo sort, transpose, DFS on transpose in topo order.
- Tarjan: `dt` and `low` arrays. Bridge if `dt[u] < low[v]`. Articulation if `low[v] >= dt[u]` for non-root.
