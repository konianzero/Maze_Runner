package maze.model.algorithm;

import maze.model.graph.Cell;
import maze.model.graph.Edge;
import maze.model.graph.Graph;
import maze.model.graph.Vertex;

import java.util.*;

/**
 * <a href="https://algs4.cs.princeton.edu/41graph/BreadthFirstPaths.java.html">CS Princeton - BreadthFirstPaths.java</a>
 */
public class BFP {
    private static final int INFINITY = Integer.MAX_VALUE;

    private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path
    Queue<Edge> q;             // edges with one endpoint in tree

    public BFP() {

    }

    /**
     * Computes the shortest path between the source vertex {@code s}
     * and every other vertex in the graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public void findPaths(Graph G, int s) {
        marked = new boolean[G.numOfVertices()];
        distTo = new int[G.numOfVertices()];
        edgeTo = new int[G.numOfVertices()];
        q = new ArrayDeque<>();

        bfs(G, s);
    }

    // breadth-first search from a single source
    private void bfs(Graph G, int s) {
        for (int v = 0; v < G.numOfVertices(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[s] = 0;

        scan(G, s);

        while (!q.isEmpty()) {
            Edge e = q.poll();
            int v = e.either(), w = e.other(v);

            if (marked[v] && marked[w]) continue;   // lazy, both v and w already scanned

            if (!marked[v]) scan(G, v);
            if (!marked[w]) scan(G, w);
        }
    }

    private void scan(Graph G, int v) {
        marked[v] = true;
        for (Edge e: G.adjacency(v)) {
            if (!e.isInMST()) continue;

            int w = e.other(v);
            if (!marked[w]) {
                edgeTo[w] = v;
                distTo[w] = distTo[v] + 1;
                q.offer(e);
            }
        }
    }

    private void addToPath(Cell c) {
        c.toShortestPath();
    }

    public Deque<Integer> pathTo(int s, Graph G) {
        Deque<Integer> path = new ArrayDeque<>();
        int x;
        for (x = s; distTo[x] != 0; x = edgeTo[x]) {
            addToPath(G.getVertex(x));
            path.push(x);
        }
        addToPath(G.getVertex(x));
        path.push(x);

        return path;
    }
}
