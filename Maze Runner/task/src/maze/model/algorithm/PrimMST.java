package maze.model.algorithm;

import maze.model.graph.Cell;
import maze.model.graph.Edge;
import maze.model.graph.Graph;

import java.util.*;
import java.util.stream.StreamSupport;

/**
 * @see <a href="https://algs4.cs.princeton.edu/43mst/LazyPrimMST.java.html">CS Princeton - LazyPrimMST.java</a>
 */
public class PrimMST {
//    private Deque<Cell> mst;  // Cells in the MST
    private boolean[] marked;   // marked[v] = true iff v on tree
    private Queue<Edge> pq;     // edges with one endpoint in tree

    public PrimMST() {
    }
    /**
     * Compute a minimum spanning tree
     * @param G the graph
     */
    public void computeMST(Graph G) {
//        mst = new ArrayDeque<>();
        pq = new PriorityQueue<>(Edge::compareTo);
        marked = new boolean[G.getNumberOfVertices()];

        // run Prim from all vertices to
        Arrays.stream(G.verticesNames())        // names is just numbers
              .forEach(v -> {
                  if (!marked[v]) prim(G, v);   // get a minimum spanning tree
              });
    }

    // run Prim's algorithm
    private void prim(Graph G, int s) {
        scan(G, s);

        while (!pq.isEmpty()) {
            Edge e = pq.poll();
            int v = e.either(), w = e.other(v);

            if (marked[v] && marked[w]) continue;   // lazy, both v and w already scanned

            addToMST(e);                            // add e to MST

            if (!marked[v]) scan(G, v);             // v becomes part of tree
            if (!marked[w]) scan(G, w);             // w becomes part of tree
        }
    }

    // add all edges e incident to v onto pq if the other endpoint has not yet been scanned
    private void scan(Graph G, int v) {
        addToMST(G.getVertex(v));

        marked[v] = true;
        StreamSupport.stream(G.adjacency(v).spliterator(), false)
                     .forEach(e -> {
                         if (!marked[e.other(v)]) pq.offer(e);
                     });
    }

    private void addToMST(Cell c) {
//        mst.offer(c);
        c.toMst();
    }
}
