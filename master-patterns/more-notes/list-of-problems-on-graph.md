# Standard Graphs DSA Patterns & Problems

## 1. Graph Representation Patterns
### Basic Representations
- **Adjacency Matrix** representation
- **Adjacency List** representation  
- **Edge List** representation
- **Incidence Matrix** representation
- **Convert between different representations**

### Weighted Graph Representations
- **Weighted Adjacency Matrix**
- **Weighted Adjacency List** (using pairs/objects)
- **Edge List with Weights**

## 2. Graph Traversal Patterns
### Depth First Search (DFS)
- **DFS Recursive Implementation**
- **DFS Iterative Implementation** (using stack)
- **DFS on Undirected Graph**
- **DFS on Directed Graph**
- **DFS with Visited Array**
- **DFS Path Tracking**
- **DFS Time Stamps** (Discovery/Finish times)

### Breadth First Search (BFS)
- **BFS Implementation** (using queue)
- **BFS Level Order Traversal**
- **BFS Shortest Path** (unweighted)
- **Multi-source BFS**
- **BFS on Matrix/Grid**
- **Bidirectional BFS**

## 3. Connectivity Patterns
### Basic Connectivity
- **Check if Graph is Connected**
- **Count Connected Components**
- **Find All Connected Components**
- **Check if Two Nodes are Connected**
- **Bridge Detection** (edges whose removal increases components)
- **Articulation Points** (vertices whose removal increases components)

### Strong Connectivity (Directed Graphs)
- **Check if Directed Graph is Strongly Connected**
- **Find Strongly Connected Components** (Kosaraju's Algorithm)
- **Find Strongly Connected Components** (Tarjan's Algorithm)
- **Condensation Graph** from SCCs

## 4. Cycle Detection Patterns
### Undirected Graphs
- **Cycle Detection using DFS**
- **Cycle Detection using Union-Find**
- **Find All Cycles in Undirected Graph**

### Directed Graphs
- **Cycle Detection using DFS** (Back edge detection)
- **Cycle Detection using Topological Sort**
- **Find All Cycles in Directed Graph**
- **Detect Negative Cycle** (Bellman-Ford)

## 5. Shortest Path Algorithms
### Single Source Shortest Path
- **Dijkstra's Algorithm** (non-negative weights)
- **Bellman-Ford Algorithm** (handles negative weights)
- **Single Source Shortest Path in DAG**
- **BFS Shortest Path** (unweighted graphs)

### All Pairs Shortest Path
- **Floyd-Warshall Algorithm**
- **Johnson's Algorithm**
- **All Pairs Shortest Path using Dijkstra**

### Specialized Shortest Path
- **Shortest Path with K edges**
- **Shortest Path avoiding certain nodes**
- **A* Search Algorithm**
- **0-1 BFS** (weights 0 or 1)

## 6. Topological Sorting Patterns
- **Topological Sort using DFS**
- **Topological Sort using Kahn's Algorithm** (BFS)
- **All Possible Topological Orders**
- **Check if Topological Order Exists**
- **Minimum Time to Complete Tasks**
- **Course Schedule Problems**

## 7. Minimum Spanning Tree (MST) Patterns
- **Kruskal's Algorithm** (Union-Find based)
- **Prim's Algorithm** (Priority queue based)
- **Borůvka's Algorithm**
- **Find MST Weight**
- **Count MSTs in Graph**
- **MST with additional constraints**

## 8. Union-Find (Disjoint Set Union) Patterns
### Basic Operations
- **Union-Find Basic Implementation**
- **Union by Rank optimization**
- **Path Compression optimization**
- **Find with Path Compression**

### Applications
- **Dynamic Connectivity**
- **Kruskal's MST using Union-Find**
- **Connected Components using Union-Find**
- **Cycle Detection using Union-Find**
- **Number of Islands** (Union-Find approach)

## 9. Bipartite Graph Patterns
- **Check if Graph is Bipartite** (2-coloring)
- **Find Bipartition of Graph**
- **Maximum Matching in Bipartite Graph**
- **Minimum Vertex Cover in Bipartite Graph**
- **Maximum Independent Set in Bipartite Graph**
- **Perfect Matching in Bipartite Graph**

## 10. Graph Coloring Patterns
- **Graph Coloring using Backtracking**
- **Check if Graph can be colored with K colors**
- **Minimum Colors needed** (Chromatic number approximation)
- **Greedy Graph Coloring**
- **Edge Coloring**

## 11. Network Flow Patterns
### Maximum Flow
- **Ford-Fulkerson Algorithm**
- **Edmonds-Karp Algorithm** (BFS-based Ford-Fulkerson)
- **Dinic's Algorithm**
- **Push-Relabel Algorithm**

### Minimum Cut
- **Min-Cut Max-Flow Theorem**
- **Find Minimum Cut**
- **All Minimum Cuts**

### Specialized Flows
- **Maximum Bipartite Matching** (as max flow)
- **Multi-source Multi-sink Flow**
- **Minimum Cost Maximum Flow**

## 12. Tree Algorithms on Graphs
### Spanning Trees
- **Find All Spanning Trees**
- **Count Spanning Trees** (Matrix-Tree theorem)
- **Minimum/Maximum Spanning Tree**

### Tree Properties in Graphs
- **Check if Graph is a Tree**
- **Find Tree Edges in Graph**
- **LCA in Tree** (when graph is tree)
- **Tree Diameter**

## 13. Advanced Graph Algorithms
### Eulerian Paths and Cycles
- **Check if Eulerian Path exists**
- **Find Eulerian Path**
- **Check if Eulerian Cycle exists**
- **Find Eulerian Cycle**
- **Hierholzer's Algorithm**

### Hamiltonian Paths and Cycles
- **Check if Hamiltonian Path exists**
- **Find Hamiltonian Path** (Backtracking)
- **Traveling Salesman Problem** (TSP)
- **TSP using Dynamic Programming**

## 14. Graph Matching Patterns
- **Maximum Matching in General Graph**
- **Perfect Matching**
- **Stable Marriage Problem**
- **Hungarian Algorithm** (Assignment problem)
- **Blossom Algorithm** (General graph matching)

## 15. Special Graph Types & Patterns

### Directed Acyclic Graph (DAG)
- **Check if Graph is DAG**
- **Longest Path in DAG**
- **Count Paths in DAG**
- **Topological Sort variations**

### Grid/Matrix Graph Problems
- **Number of Islands**
- **Surrounded Regions**
- **Word Search in Grid**
- **Shortest Path in Grid**
- **Rotten Oranges** (Multi-source BFS)
- **Pacific Atlantic Water Flow**

### Tree-like Graphs
- **Tree Traversals on Graph**
- **Root a Tree**
- **Tree Isomorphism**
- **Tree Center Finding**

## 16. Dynamic Programming on Graphs
- **Longest Path in DAG**
- **Count Paths with Conditions**
- **Maximum/Minimum Path Problems**
- **Traveling Salesman using DP**
- **Steiner Tree Problem**

## 17. String/Pattern Matching on Graphs
- **Pattern Matching in Graph**
- **Subgraph Isomorphism**
- **Graph Edit Distance**
- **Common Subgraph Problems**

## Graph Types & Classifications

### By Edge Direction
- **Undirected Graph**: Edges have no direction
- **Directed Graph (Digraph)**: Edges have direction
- **Mixed Graph**: Contains both directed and undirected edges

### By Edge Weights
- **Unweighted Graph**: All edges have equal weight (or weight 1)
- **Weighted Graph**: Edges have associated weights/costs
- **Negative Weight Graph**: Some edges have negative weights

### By Connectivity
- **Connected Graph**: Path exists between any two vertices
- **Disconnected Graph**: Some vertices unreachable from others
- **Strongly Connected**: Every vertex reachable from every other (directed)
- **Weakly Connected**: Connected when treating as undirected

### By Special Properties
- **Simple Graph**: No self-loops, no multiple edges
- **Multigraph**: Multiple edges between same vertices allowed
- **Complete Graph**: Edge between every pair of vertices
- **Bipartite Graph**: Vertices can be colored with 2 colors
- **Planar Graph**: Can be drawn without edge crossings
- **Regular Graph**: All vertices have same degree

### By Structure
- **Sparse Graph**: Few edges relative to vertices (E << V²)
- **Dense Graph**: Many edges relative to vertices (E ≈ V²)
- **Tree**: Connected acyclic graph (E = V-1)
- **Forest**: Collection of trees
- **Cycle Graph**: Forms a single cycle
- **Star Graph**: One central vertex connected to all others

### By Application Domain
- **Social Network Graph**: Represents relationships
- **Transportation Network**: Roads, flights, etc.
- **Computer Network Graph**: Network topology
- **Dependency Graph**: Task dependencies
- **Flow Network**: Capacity constraints on edges

## Advanced Graph Data Structures

### Graph Representations for Specific Use Cases
- **Compressed Sparse Row (CSR)** for sparse graphs
- **Coordinate Format (COO)** for sparse matrices
- **Link-Cut Trees** for dynamic connectivity
- **Heavy-Light Decomposition** for tree queries

### Specialized Graph Structures
- **Suffix Graph/Tree** for string processing
- **De Bruijn Graph** for sequence assembly
- **Trie as Graph** for string matching
- **Automata as Graphs** for pattern recognition

## Problem Complexity by Graph Type

### Easy Level
- Basic traversals (DFS/BFS), simple connectivity, cycle detection

### Medium Level  
- Shortest paths, topological sort, MST, bipartite graphs

### Hard Level
- Network flows, advanced matching, Hamiltonian problems, graph DP

## Key Algorithmic Techniques
1. **DFS & BFS** - Foundation of most graph algorithms
2. **Dynamic Programming** - For optimization problems on graphs
3. **Greedy Approach** - For MST and shortest paths
4. **Divide & Conquer** - For some graph decomposition problems
5. **Backtracking** - For enumeration problems (Hamiltonian paths)
6. **Two Pointers** - For some specialized graph problems
7. **Binary Search** - For optimization problems with monotonic property

## Common Graph Problem Categories
- **Reachability**: Can we reach from A to B?
- **Connectivity**: How connected is the graph?
- **Optimization**: Find best path/matching/coloring
- **Counting**: How many paths/cycles/components?
- **Decision**: Does property X exist?
- **Construction**: Build graph with certain properties