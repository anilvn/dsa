import java.util.*;

/**
 * Complete implementation of Unweighted Undirected Graph
 * All edges have equal weight (assumed to be 1)
 * 
 * Features:
 * - Graph construction and manipulation
 * - BFS and DFS traversal
 * - Shortest path finding
 * - Cycle detection
 * - Connected components
 * - Bridge detection
 * - Articulation points
 * - All paths between vertices
 */
public class UnweightedUndirectedGraph {
    
    // Edge class for representing connections
    static class Edge {
        int src;
        int dest;
        
        public Edge(int s, int d) {
            this.src = s;
            this.dest = d;
        }
        
        @Override
        public String toString() {
            return src + " -- " + dest;
        }
    }
    
    // Graph representation using adjacency list
    private ArrayList<Edge>[] graph;
    private int vertices;
    
    // Constructor
    @SuppressWarnings("unchecked")
    public UnweightedUndirectedGraph(int v) {
        this.vertices = v;
        this.graph = new ArrayList[v];
        
        for (int i = 0; i < v; i++) {
            graph[i] = new ArrayList<>();
        }
    }
    
    // =================== GRAPH CONSTRUCTION METHODS ===================
    
    /**
     * Add an undirected edge between two vertices
     * Time Complexity: O(1)
     */
    public void addEdge(int src, int dest) {
        if (src < 0 || src >= vertices || dest < 0 || dest >= vertices) {
            throw new IllegalArgumentException("Invalid vertex");
        }
        
        // Add edge in both directions for undirected graph
        graph[src].add(new Edge(src, dest));
        graph[dest].add(new Edge(dest, src));
    }
    
    /**
     * Remove an undirected edge between two vertices
     * Time Complexity: O(degree of vertices)
     */
    public boolean removeEdge(int src, int dest) {
        if (src < 0 || src >= vertices || dest < 0 || dest >= vertices) {
            return false;
        }
        
        boolean removed1 = graph[src].removeIf(edge -> edge.dest == dest);
        boolean removed2 = graph[dest].removeIf(edge -> edge.dest == src);
        
        return removed1 && removed2;
    }
    
    /**
     * Check if an edge exists between two vertices
     * Time Complexity: O(degree of src vertex)
     */
    public boolean hasEdge(int src, int dest) {
        if (src < 0 || src >= vertices || dest < 0 || dest >= vertices) {
            return false;
        }
        
        for (Edge e : graph[src]) {
            if (e.dest == dest) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get all neighbors of a vertex
     * Time Complexity: O(degree of vertex)
     */
    public List<Integer> getNeighbors(int vertex) {
        if (vertex < 0 || vertex >= vertices) {
            return new ArrayList<>();
        }
        
        List<Integer> neighbors = new ArrayList<>();
        for (Edge e : graph[vertex]) {
            neighbors.add(e.dest);
        }
        return neighbors;
    }
    
    /**
     * Get degree of a vertex (number of connected edges)
     * Time Complexity: O(1)
     */
    public int getDegree(int vertex) {
        if (vertex < 0 || vertex >= vertices) {
            return -1;
        }
        return graph[vertex].size();
    }
    
    /**
     * Display the graph
     * Time Complexity: O(V + E)
     */
    public void displayGraph() {
        System.out.println("Graph Adjacency List:");
        for (int i = 0; i < vertices; i++) {
            System.out.print("Vertex " + i + ": ");
            for (Edge e : graph[i]) {
                System.out.print(e.dest + " ");
            }
            System.out.println();
        }
    }
    
    // =================== TRAVERSAL METHODS ===================
    
    /**
     * Breadth-First Search traversal
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     */
    public void bfs(int startVertex) {
        if (startVertex < 0 || startVertex >= vertices) {
            System.out.println("Invalid start vertex");
            return;
        }
        
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();
        
        System.out.print("BFS Traversal starting from " + startVertex + ": ");
        
        queue.add(startVertex);
        visited[startVertex] = true;
        
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            System.out.print(curr + " ");
            
            for (Edge e : graph[curr]) {
                if (!visited[e.dest]) {
                    visited[e.dest] = true;
                    queue.add(e.dest);
                }
            }
        }
        System.out.println();
    }
    
    /**
     * Depth-First Search traversal
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     */
    public void dfs(int startVertex) {
        if (startVertex < 0 || startVertex >= vertices) {
            System.out.println("Invalid start vertex");
            return;
        }
        
        boolean[] visited = new boolean[vertices];
        System.out.print("DFS Traversal starting from " + startVertex + ": ");
        dfsUtil(startVertex, visited);
        System.out.println();
    }
    
    private void dfsUtil(int vertex, boolean[] visited) {
        visited[vertex] = true;
        System.out.print(vertex + " ");
        
        for (Edge e : graph[vertex]) {
            if (!visited[e.dest]) {
                dfsUtil(e.dest, visited);
            }
        }
    }
    
    // =================== SHORTEST PATH METHODS ===================
    
    /**
     * Find shortest path between two vertices using BFS
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     */
    public int shortestPath(int src, int dest) {
        if (src < 0 || src >= vertices || dest < 0 || dest >= vertices) {
            return -1;
        }
        
        if (src == dest) return 0;
        
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();
        int[] distance = new int[vertices];
        
        queue.add(src);
        visited[src] = true;
        distance[src] = 0;
        
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            
            for (Edge e : graph[curr]) {
                if (!visited[e.dest]) {
                    visited[e.dest] = true;
                    distance[e.dest] = distance[curr] + 1;
                    queue.add(e.dest);
                    
                    if (e.dest == dest) {
                        return distance[dest];
                    }
                }
            }
        }
        
        return -1; // No path exists
    }
    
    /**
     * Find shortest path with actual path reconstruction
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     */
    public List<Integer> shortestPathWithRoute(int src, int dest) {
        if (src < 0 || src >= vertices || dest < 0 || dest >= vertices) {
            return new ArrayList<>();
        }
        
        if (src == dest) {
            return Arrays.asList(src);
        }
        
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();
        int[] parent = new int[vertices];
        Arrays.fill(parent, -1);
        
        queue.add(src);
        visited[src] = true;
        
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            
            if (curr == dest) {
                // Reconstruct path
                List<Integer> path = new ArrayList<>();
                int temp = dest;
                while (temp != -1) {
                    path.add(0, temp);
                    temp = parent[temp];
                }
                return path;
            }
            
            for (Edge e : graph[curr]) {
                if (!visited[e.dest]) {
                    visited[e.dest] = true;
                    parent[e.dest] = curr;
                    queue.add(e.dest);
                }
            }
        }
        
        return new ArrayList<>(); // No path exists
    }
    
    // =================== CONNECTIVITY METHODS ===================
    
    /**
     * Check if the graph is connected
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     */
    public boolean isConnected() {
        boolean[] visited = new boolean[vertices];
        
        // Find a vertex with non-zero degree to start DFS
        int start = -1;
        for (int i = 0; i < vertices; i++) {
            if (graph[i].size() > 0) {
                start = i;
                break;
            }
        }
        
        if (start == -1) {
            // No edges exist, check if all vertices are isolated
            return vertices <= 1;
        }
        
        // Perform DFS from start vertex
        dfsUtil(start, visited);
        
        // Check if all vertices with edges are visited
        for (int i = 0; i < vertices; i++) {
            if (graph[i].size() > 0 && !visited[i]) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Find all connected components
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     */
    public List<List<Integer>> findConnectedComponents() {
        boolean[] visited = new boolean[vertices];
        List<List<Integer>> components = new ArrayList<>();
        
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                dfsForComponent(i, visited, component);
                components.add(component);
            }
        }
        
        return components;
    }
    
    private void dfsForComponent(int vertex, boolean[] visited, List<Integer> component) {
        visited[vertex] = true;
        component.add(vertex);
        
        for (Edge e : graph[vertex]) {
            if (!visited[e.dest]) {
                dfsForComponent(e.dest, visited, component);
            }
        }
    }
    
    // =================== CYCLE DETECTION ===================
    
    /**
     * Detect if the graph has a cycle
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     */
    public boolean hasCycle() {
        boolean[] visited = new boolean[vertices];
        
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                if (hasCycleUtil(i, visited, -1)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean hasCycleUtil(int curr, boolean[] visited, int parent) {
        visited[curr] = true;
        
        for (Edge e : graph[curr]) {
            if (!visited[e.dest]) {
                if (hasCycleUtil(e.dest, visited, curr)) {
                    return true;
                }
            } else if (e.dest != parent) {
                // Back edge found (cycle detected)
                return true;
            }
        }
        return false;
    }
    
    // =================== BRIDGE DETECTION (TARJAN'S ALGORITHM) ===================
    
    /**
     * Find all bridges in the graph
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     */
    public List<Edge> findBridges() {
        boolean[] visited = new boolean[vertices];
        int[] discoveryTime = new int[vertices];
        int[] low = new int[vertices];
        int[] parent = new int[vertices];
        List<Edge> bridges = new ArrayList<>();
        
        Arrays.fill(parent, -1);
        
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                bridgeUtil(i, visited, discoveryTime, low, parent, bridges, new int[]{0});
            }
        }
        
        return bridges;
    }
    
    private void bridgeUtil(int u, boolean[] visited, int[] disc, int[] low, 
                           int[] parent, List<Edge> bridges, int[] time) {
        visited[u] = true;
        disc[u] = low[u] = ++time[0];
        
        for (Edge e : graph[u]) {
            int v = e.dest;
            if (v == parent[u]) continue;
            
            if (!visited[v]) {
                parent[v] = u;
                bridgeUtil(v, visited, disc, low, parent, bridges, time);
                
                low[u] = Math.min(low[u], low[v]);
                
                if (low[v] > disc[u]) {
                    bridges.add(new Edge(u, v));
                }
            } else {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }
    
    // =================== ARTICULATION POINTS ===================
    
    /**
     * Find all articulation points in the graph
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     */
    public List<Integer> findArticulationPoints() {
        boolean[] visited = new boolean[vertices];
        int[] discoveryTime = new int[vertices];
        int[] low = new int[vertices];
        int[] parent = new int[vertices];
        boolean[] articulationPoints = new boolean[vertices];
        
        Arrays.fill(parent, -1);
        
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                articulationUtil(i, visited, discoveryTime, low, parent, 
                               articulationPoints, new int[]{0});
            }
        }
        
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            if (articulationPoints[i]) {
                result.add(i);
            }
        }
        
        return result;
    }
    
    private void articulationUtil(int u, boolean[] visited, int[] disc, int[] low,
                                 int[] parent, boolean[] ap, int[] time) {
        int children = 0;
        visited[u] = true;
        disc[u] = low[u] = ++time[0];
        
        for (Edge e : graph[u]) {
            int v = e.dest;
            if (v == parent[u]) continue;
            
            if (!visited[v]) {
                children++;
                parent[v] = u;
                articulationUtil(v, visited, disc, low, parent, ap, time);
                
                low[u] = Math.min(low[u], low[v]);
                
                if ((parent[u] == -1 && children > 1) || 
                    (parent[u] != -1 && low[v] >= disc[u])) {
                    ap[u] = true;
                }
            } else {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }
    
    // =================== PATH FINDING ===================
    
    /**
     * Find all paths between two vertices
     * Time Complexity: O(V^V) - exponential
     * Space Complexity: O(V)
     */
    public void findAllPaths(int src, int dest) {
        if (src < 0 || src >= vertices || dest < 0 || dest >= vertices) {
            System.out.println("Invalid vertices");
            return;
        }
        
        boolean[] visited = new boolean[vertices];
        List<Integer> path = new ArrayList<>();
        List<List<Integer>> allPaths = new ArrayList<>();
        
        path.add(src);
        visited[src] = true;
        findAllPathsUtil(src, dest, visited, path, allPaths);
        
        System.out.println("All paths from " + src + " to " + dest + ":");
        for (List<Integer> p : allPaths) {
            System.out.println(p);
        }
    }
    
    private void findAllPathsUtil(int curr, int dest, boolean[] visited,
                                 List<Integer> path, List<List<Integer>> allPaths) {
        if (curr == dest) {
            allPaths.add(new ArrayList<>(path));
            return;
        }
        
        for (Edge e : graph[curr]) {
            if (!visited[e.dest]) {
                visited[e.dest] = true;
                path.add(e.dest);
                
                findAllPathsUtil(e.dest, dest, visited, path, allPaths);
                
                // Backtrack
                path.remove(path.size() - 1);
                visited[e.dest] = false;
            }
        }
    }
    
    // =================== UTILITY METHODS ===================
    
    /**
     * Get total number of edges
     * Time Complexity: O(V)
     */
    public int getEdgeCount() {
        int count = 0;
        for (int i = 0; i < vertices; i++) {
            count += graph[i].size();
        }
        return count / 2; // Divide by 2 for undirected graph
    }
    
    /**
     * Get number of vertices
     * Time Complexity: O(1)
     */
    public int getVertexCount() {
        return vertices;
    }
    
    /**
     * Check if graph is empty
     * Time Complexity: O(V)
     */
    public boolean isEmpty() {
        return getEdgeCount() == 0;
    }
    
    /**
     * Get graph density (ratio of existing edges to possible edges)
     * Time Complexity: O(V)
     */
    public double getDensity() {
        int edges = getEdgeCount();
        int maxEdges = vertices * (vertices - 1) / 2;
        return maxEdges == 0 ? 0 : (double) edges / maxEdges;
    }
    
    // =================== MAIN METHOD FOR TESTING ===================
    
    public static void main(String[] args) {
        // Create a sample graph
        UnweightedUndirectedGraph graph = new UnweightedUndirectedGraph(7);
        
        // Add edges to create the following graph:
        /*
            1 --- 3
           /|     |\
          0 |     | 5 -- 6
           \|     |/
            2 --- 4
        */
        
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);
        graph.addEdge(5, 6);
        
        // Display graph
        graph.displayGraph();
        System.out.println();
        
        // Test traversals
        graph.bfs(0);
        graph.dfs(0);
        System.out.println();
        
        // Test shortest path
        System.out.println("Shortest path from 0 to 6: " + graph.shortestPath(0, 6));
        System.out.println("Route from 0 to 6: " + graph.shortestPathWithRoute(0, 6));
        System.out.println();
        
        // Test connectivity
        System.out.println("Is graph connected? " + graph.isConnected());
        System.out.println("Connected components: " + graph.findConnectedComponents());
        System.out.println();
        
        // Test cycle detection
        System.out.println("Has cycle? " + graph.hasCycle());
        System.out.println();
        
        // Test bridges and articulation points
        System.out.println("Bridges: " + graph.findBridges());
        System.out.println("Articulation points: " + graph.findArticulationPoints());
        System.out.println();
        
        // Test all paths
        graph.findAllPaths(0, 5);
        System.out.println();
        
        // Test utility methods
        System.out.println("Number of vertices: " + graph.getVertexCount());
        System.out.println("Number of edges: " + graph.getEdgeCount());
        System.out.println("Graph density: " + String.format("%.2f", graph.getDensity()));
        System.out.println("Degree of vertex 3: " + graph.getDegree(3));
        System.out.println("Neighbors of vertex 3: " + graph.getNeighbors(3));
    }
}