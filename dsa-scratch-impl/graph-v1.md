# Graphs in Java – Complete Notes with Code & Explanations
>https://claude.ai/chat/dcac1784-7393-46ad-a6d3-c6b438715f61

## 📑 Contents
1. [Graph Representation](#1-graph-representation)
2. [BFS (Breadth-First Search)](#2-bfs-breadth-first-search)
3. [DFS (Depth-First Search)](#3-dfs-depth-first-search)
4. [All Paths between two nodes](#4-all-paths-between-two-nodes)
5. [Cycle Detection](#5-cycle-detection)
6. [Topological Sort](#6-topological-sort)
7. [Dijkstra's Algorithm](#7-dijkstras-algorithm)
8. [Bellman-Ford Algorithm](#8-bellman-ford-algorithm)
9. [Prim's Algorithm (MST)](#9-prims-algorithm-mst)
10. [Kosaraju's Algorithm (SCC)](#10-kosarajus-algorithm-scc)
11. [Tarjan's Algorithm](#11-tarjans-algorithm)
12. [Practice Problems](#12-practice-problems)
13. [Complexity Summary](#13-complexity-summary)

---

## 1. Graph Representation

### What is a Graph?
A graph is a collection of nodes (vertices) connected by edges. It's used to represent relationships between different entities.

### Basic Graph Structure
```java
static class Edge {
    int src;    // source vertex
    int dest;   // destination vertex
    int wt;     // weight (optional)
    
    public Edge(int s, int d, int w) {
        this.src = s;
        this.dest = d;
        this.wt = w;
    }
}
```

### Graph Types:
- **Directed Graph**: Edges have direction (A → B)
- **Undirected Graph**: Edges are bidirectional (A ↔ B)
- **Weighted Graph**: Edges have weights/costs
- **Unweighted Graph**: All edges have equal weight

---

## 2. BFS (Breadth-First Search)

### 🎯 Purpose: 
Explore all neighbors at current depth before moving to next depth level.

### 📊 Time Complexity: O(V + E)
### 💾 Space Complexity: O(V)

### 🔄 Algorithm Steps:
1. Start from source vertex
2. Add to queue and mark visited
3. Process all neighbors of current vertex
4. Add unvisited neighbors to queue
5. Repeat until queue is empty

### 💻 Code:
```java
public static void bfs(ArrayList<Edge> graph[], int V) {
    boolean visited[] = new boolean[V];
    Queue<Integer> q = new LinkedList<>();
    
    q.add(0); // Start from vertex 0
    
    while (!q.isEmpty()) {
        int curr = q.remove();
        
        if (!visited[curr]) {
            System.out.print(curr + " ");
            visited[curr] = true;
            
            // Add all neighbors to queue
            for (int i = 0; i < graph[curr].size(); i++) {
                Edge e = graph[curr].get(i);
                q.add(e.dest);
            }
        }
    }
}
```

### 🎯 Use Cases:
- Shortest path in unweighted graphs
- Level-order traversal
- Finding connected components

---

## 3. DFS (Depth-First Search)

### 🎯 Purpose: 
Explore as far as possible along each branch before backtracking.

### 📊 Time Complexity: O(V + E)
### 💾 Space Complexity: O(V)

### 🔄 Algorithm Steps:
1. Start from source vertex
2. Mark current vertex as visited
3. Recursively visit all unvisited neighbors
4. Backtrack when no unvisited neighbors remain

### 💻 Code:
```java
public static void dfs(ArrayList<Edge> graph[], int curr, boolean visited[]) {
    if (visited[curr]) {
        return;
    }
    
    System.out.print(curr + " ");
    visited[curr] = true;

    // Recursively visit all neighbors using foreach
    for (Edge e : graph[curr]) {
        dfs(graph, e.dest, visited);
    }
    
    /*
    // Recursively visit all neighbors
    for (int i = 0; i < graph[curr].size(); i++) {
        Edge e = graph[curr].get(i);
        dfs(graph, e.dest, visited);
    }
    */
}
```

### 🎯 Use Cases:
- Topological sorting
- Cycle detection
- Path finding
- Connected components

---

## 4. All Paths Between Two Nodes

### 🎯 Purpose: 
Find all possible paths from source to target vertex.

### 📊 Time Complexity: O(V^V) (exponential)
### 💾 Space Complexity: O(V)

### 💻 Code:
```java
public static void printAllPaths(ArrayList<Edge> graph[], int src, int tar, 
                                String path, boolean vis[]) {
    if (src == tar) {
        System.out.println(path);
        return;
    }
    
    for (int i = 0; i < graph[src].size(); i++) {
        Edge e = graph[src].get(i);
        if (!vis[e.dest]) {
            vis[e.dest] = true;
            printAllPaths(graph, e.dest, tar, path + "->" + e.dest, vis);
            vis[e.dest] = false; // Backtrack
        }
    }
}
```

### 🎯 Key Points:
- Uses backtracking to explore all paths
- Mark vertices as visited during recursion
- Unmark when backtracking to allow other paths

---

## 5. Cycle Detection

### 5.1 Undirected Graph Cycle Detection

### 🔄 Algorithm Logic:
If we encounter a visited vertex that is not the parent, a cycle exists.

### 💻 Code:
```java
public static boolean isCyclicUtil(ArrayList<Edge>[] graph, boolean vis[], 
                                 int curr, int par) {
    vis[curr] = true;
    
    for (int i = 0; i < graph[curr].size(); i++) {
        Edge e = graph[curr].get(i);
        
        // Case 1: Neighbor is visited and not parent
        if (vis[e.dest] && e.dest != par) {
            return true;
        }
        // Case 2: Neighbor is parent - skip
        else if (e.dest == par) {
            continue;
        }
        // Case 3: Neighbor is unvisited - recurse
        else {
            if (isCyclicUtil(graph, vis, e.dest, curr)) {
                return true;
            }
        }
    }
    return false;
}
```

### 5.2 Directed Graph Cycle Detection

### 🔄 Algorithm Logic:
Use recursion stack to detect back edges (cycle).

### 💻 Code:
```java
public static boolean isCyclicUtil(ArrayList<Edge>[] graph, int curr, 
                                 boolean vis[], boolean stack[]) {
    vis[curr] = true;
    stack[curr] = true;
    
    for (int i = 0; i < graph[curr].size(); i++) {
        Edge e = graph[curr].get(i);
        
        if (stack[e.dest]) { // Back edge found
            return true;
        }
        else if (!vis[e.dest] && 
                 isCyclicUtil(graph, e.dest, vis, stack)) {
            return true;
        }
    }
    
    stack[curr] = false; // Remove from recursion stack
    return false;
}
```

---

## 6. Topological Sort

### 🎯 Purpose: 
Linear ordering of vertices in a directed acyclic graph (DAG).

### 📊 Time Complexity: O(V + E)
### 💾 Space Complexity: O(V)

### 🔄 Algorithm Steps:
1. Perform DFS from all unvisited vertices
2. After processing all neighbors, push vertex to stack
3. Pop elements from stack to get topological order

### 💻 Code:
```java
public static void topoSortUtil(ArrayList<Edge> graph[], int curr, 
                               boolean vis[], Stack<Integer> s) {
    vis[curr] = true;
    
    for (int i = 0; i < graph[curr].size(); i++) {
        Edge e = graph[curr].get(i);
        if (!vis[e.dest]) {
            topoSortUtil(graph, e.dest, vis, s);
        }
    }
    
    s.push(curr); // Add to stack after processing neighbors
}

public static void topoSort(ArrayList<Edge> graph[]) {
    boolean vis[] = new boolean[graph.length];
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

### 🎯 Use Cases:
- Course scheduling
- Build systems
- Task dependencies

---

## 7. Dijkstra's Algorithm

### 🎯 Purpose: 
Find shortest path from source to all vertices (non-negative weights only).

### 📊 Time Complexity: O(E log V)
### 💾 Space Complexity: O(V)

### 🔄 Algorithm Steps:
1. Initialize distances: source = 0, others = ∞
2. Use priority queue (min-heap) to get minimum distance vertex
3. Update distances of all neighbors
4. Repeat until all vertices processed

### 💻 Code:
```java
static class Pair implements Comparable<Pair> {
    int n;
    int path;
    
    public Pair(int n, int path) {
        this.n = n;
        this.path = path;
    }
    
    @Override
    public int compareTo(Pair p2) {
        return this.path - p2.path; // Min heap
    }
}

public static int[] dijkstra(ArrayList<Edge> graph[], int src) {
    PriorityQueue<Pair> pq = new PriorityQueue<>();
    int dist[] = new int[graph.length];
    boolean vis[] = new boolean[graph.length];
    
    // Initialize distances
    for (int i = 0; i < dist.length; i++) {
        if (i != src) {
            dist[i] = Integer.MAX_VALUE;
        }
    }
    
    pq.add(new Pair(src, 0));
    
    while (!pq.isEmpty()) {
        Pair curr = pq.remove();
        
        if (!vis[curr.n]) {
            vis[curr.n] = true;
            
            for (int i = 0; i < graph[curr.n].size(); i++) {
                Edge e = graph[curr.n].get(i);
                int u = e.src;
                int v = e.dest;
                
                if (!vis[v] && dist[u] + e.wt < dist[v]) {
                    dist[v] = dist[u] + e.wt;
                    pq.add(new Pair(v, dist[v]));
                }
            }
        }
    }
    
    return dist;
}
```

### ⚠️ Limitations:
- Cannot handle negative weights
- Use Bellman-Ford for negative weights

---

## 8. Bellman-Ford Algorithm

### 🎯 Purpose: 
Find shortest path with negative weight detection.

### 📊 Time Complexity: O(V × E)
### 💾 Space Complexity: O(V)

### 🔄 Algorithm Steps:
1. Initialize distances: source = 0, others = ∞
2. Relax all edges V-1 times
3. Check for negative weight cycles in Vth iteration

### 💻 Code:
```java
public static void bellmanFord(ArrayList<Edge> graph[], int src) {
    int dist[] = new int[graph.length];
    
    // Initialize distances
    for (int i = 0; i < dist.length; i++) {
        if (i != src)
            dist[i] = Integer.MAX_VALUE;
    }
    
    // Relax edges V-1 times
    for (int i = 0; i < graph.length - 1; i++) {
        for (int j = 0; j < graph.length; j++) {
            for (int k = 0; k < graph[j].size(); k++) {
                Edge e = graph[j].get(k);
                int u = e.src;
                int v = e.dest;
                int wt = e.wt;
                
                if (dist[u] != Integer.MAX_VALUE && 
                    dist[u] + wt < dist[v]) {
                    dist[v] = dist[u] + wt;
                }
            }
        }
    }
    
    // Check for negative weight cycle
    for (int j = 0; j < graph.length; j++) {
        for (int k = 0; k < graph[j].size(); k++) {
            Edge e = graph[j].get(k);
            int u = e.src;
            int v = e.dest;
            int wt = e.wt;
            
            if (dist[u] != Integer.MAX_VALUE && 
                dist[u] + wt < dist[v]) {
                System.out.println("Negative weight cycle exists");
                return;
            }
        }
    }
}
```

---

## 9. Prim's Algorithm (MST)

### 🎯 Purpose: 
Find Minimum Spanning Tree (MST) for weighted undirected graphs.

### 📊 Time Complexity: O(E log E)
### 💾 Space Complexity: O(V)

### 🔄 Algorithm Steps:
1. Start with any vertex
2. Add minimum weight edge connecting tree to non-tree vertex
3. Repeat until all vertices included

### 💻 Code:
```java
static class Pair implements Comparable<Pair> {
    int v;
    int wt;
    
    public Pair(int v, int wt) {
        this.v = v;
        this.wt = wt;
    }
    
    @Override
    public int compareTo(Pair p2) {
        return this.wt - p2.wt;
    }
}

public static void primAlgo(ArrayList<Edge> graph[]) {
    boolean vis[] = new boolean[graph.length];
    PriorityQueue<Pair> pq = new PriorityQueue<>();
    pq.add(new Pair(0, 0));
    int cost = 0;
    
    while (!pq.isEmpty()) {
        Pair curr = pq.remove();
        
        if (!vis[curr.v]) {
            vis[curr.v] = true;
            cost += curr.wt;
            
            for (int i = 0; i < graph[curr.v].size(); i++) {
                Edge e = graph[curr.v].get(i);
                if (!vis[e.dest]) {
                    pq.add(new Pair(e.dest, e.wt));
                }
            }
        }
    }
    
    System.out.println("MST Cost: " + cost);
}
```

---

## 10. Kosaraju's Algorithm (SCC)

### 🎯 Purpose: 
Find Strongly Connected Components in directed graphs.

### 📊 Time Complexity: O(V + E)
### 💾 Space Complexity: O(V)

### 🔄 Algorithm Steps:
1. Perform DFS and store vertices in stack by finish time
2. Create transpose graph (reverse all edges)
3. Perform DFS on transpose graph in stack order

### 💻 Code:
```java
public static void kosaraju(ArrayList<Edge> graph[], int V) {
    // Step 1: Topological sort
    Stack<Integer> s = new Stack<>();
    boolean vis[] = new boolean[V];
    
    for (int i = 0; i < V; i++) {
        if (!vis[i]) {
            topSort(graph, i, s, vis);
        }
    }
    
    // Step 2: Create transpose graph
    ArrayList<Edge> transpose[] = new ArrayList[V];
    for (int i = 0; i < V; i++) {
        transpose[i] = new ArrayList<Edge>();
        vis[i] = false;
    }
    
    for (int i = 0; i < V; i++) {
        for (int j = 0; j < graph[i].size(); j++) {
            Edge e = graph[i].get(j);
            transpose[e.dest].add(new Edge(e.dest, e.src));
        }
    }
    
    // Step 3: DFS on transpose graph
    while (!s.isEmpty()) {
        int curr = s.pop();
        if (!vis[curr]) {
            System.out.print("SCC: ");
            dfs(transpose, vis, curr);
            System.out.println();
        }
    }
}
```

---

## 11. Tarjan's Algorithm

### 11.1 Bridge Detection

### 🎯 Purpose: 
Find bridges (critical edges) whose removal increases connected components.

### 💻 Key Concepts:
- **Discovery Time (dt)**: When vertex is first visited
- **Low Link (low)**: Lowest discovery time reachable from subtree
- **Bridge Condition**: dt[u] < low[v] where (u,v) is an edge

### 11.2 Articulation Points

### 🎯 Purpose: 
Find vertices whose removal increases connected components.

### 💻 Key Conditions:
1. **Root vertex**: More than 1 child in DFS tree
2. **Non-root vertex**: dt[u] ≤ low[v] for child v

---

## 12. Practice Problems

### 🍊 Rotten Oranges (BFS Application)
- **Problem**: Find minimum time for all oranges to rot
- **Approach**: Multi-source BFS from all rotten oranges
- **Time**: O(m × n), **Space**: O(m × n)

### 🏝️ Number of Islands (DFS/BFS Application)
- **Problem**: Count connected components of '1's
- **Approach**: DFS/BFS from each unvisited '1'
- **Time**: O(m × n), **Space**: O(m × n)

### 📚 Course Schedule (Cycle Detection)
- **Problem**: Check if all courses can be completed
- **Approach**: Detect cycle in directed graph
- **Time**: O(V + E), **Space**: O(V)

---

## 13. Complexity Summary

| Algorithm | Time Complexity | Space Complexity | Use Case |
|-----------|----------------|------------------|----------|
| BFS | O(V + E) | O(V) | Shortest path (unweighted) |
| DFS | O(V + E) | O(V) | Traversal, cycle detection |
| Dijkstra | O(E log V) | O(V) | Shortest path (non-negative) |
| Bellman-Ford | O(V × E) | O(V) | Shortest path (negative weights) |
| Topological Sort | O(V + E) | O(V) | Dependency resolution |
| Prim's MST | O(E log E) | O(V) | Minimum spanning tree |
| Kosaraju SCC | O(V + E) | O(V) | Strongly connected components |
| Tarjan's | O(V + E) | O(V) | Bridges, articulation points |

---

## 🎯 Key Takeaways

1. **Choose the right algorithm** based on graph properties (directed/undirected, weighted/unweighted)
2. **BFS for shortest paths** in unweighted graphs
3. **Dijkstra for weighted graphs** with non-negative weights
4. **Bellman-Ford for negative weights** and cycle detection
5. **DFS for topological sorting** and cycle detection
6. **Tarjan's for bridge/articulation point** detection

---

## 📚 Additional Resources

- Practice problems on LeetCode, HackerRank, GeeksforGeeks
- Visualize algorithms using graph visualization tools
- Implement variations and optimizations
- Study real-world applications (social networks, GPS, etc.)

**Happy Coding! 🚀**