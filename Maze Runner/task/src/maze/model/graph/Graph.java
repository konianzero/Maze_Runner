package maze.model.graph;

import java.io.Serializable;
import java.util.*;
import java.util.stream.IntStream;

import static maze.model.graph.CellState.*;

public class Graph implements Serializable {
    private static final int MAX_WEIGHT = 10;
    protected static final Random RND = new Random();

    protected int width;
    protected int height;
    protected int V;
    protected int E;

    protected List<Vertex> vertices;
    protected List<Edge> edges;
    protected Deque<Edge>[] adjacency;
    protected Cell[][] cells;

    public Graph() {
    }

    public int numOfVertices() {
        return V;
    }

    public int size() { return width * height; }

    public Vertex[] vertices() {
        return vertices.toArray(Vertex[]::new);
    }

    public Iterable<Edge> adjacency(int v) {
        return adjacency[v];
    }

    public Vertex getVertex(int n) {
        return vertices.stream()
                       .filter(v -> v.getName() == n)
                       .findFirst().get();
    }

    protected void initGraph(int height, int width) {
        V = 0;
        E = 0;

        this.height = height;
        this.width = width;

        adjacency = new ArrayDeque[this.width * this.height];
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        cells = new Cell[this.height][this.width];

        initBoard();
    }

    private void initBoard() {
        RND.setSeed(System.currentTimeMillis());
        createVertices();
        createEdges();
    }

    /**
     * Create vertices.
     *
     * <p>
     * Example for 6 x 6 maze:
     * <pre>
     * B - BORDER (Vertex with state = BORDER)
     * V - VERTEX
     * . - Empty
     *
     * maze:
     *     0 1 2 3 4 5
     *   -------------
     * 0 | B B B B B B
     * 1 | B V . V . B
     * 2 | B . . . . B
     * 3 | B V . V . B
     * 4 | B . . . . B
     * 5 | B B B B B B
     * </pre>
     */
    private void createVertices() {
        IntStream.range(0, height)
                 .forEach(row -> IntStream.range(0, width)
                                          .forEach(col -> addVertex(row, col))
                 );
    }

    private void addVertex(int row, int col) {
        if (isHorizontalBorder(col) || isVerticalBorder(row)) {
            createVertex(row, col).setState(BORDER);
//            addCell(row, col);
        } else if (isOdd(col)) {
            if (isOdd(row)) {
                createVertex(row, col).setState(WALL);
            }
        }
    }

    private boolean isHorizontalBorder(int colNum) {
        return colNum == 0 || colNum == width - 1;
    }

    private boolean isVerticalBorder(int rowNum) {
        return rowNum == 0 || rowNum == height - 1;
    }

    protected boolean isOdd(int n) {
        return n % 2 != 0;
    }

    /**
     * Create vertex, add to cell board and initialize adjacency list.
     *
     * @param row cell y coordinate
     * @param col cell x coordinate
     */
    private Vertex createVertex(int row, int col) {
        Vertex v = new Vertex(V);

        cells[row][col] = v;
        vertices.add(v);
        adjacency[V] = new ArrayDeque<>();
        V++;
        return v;
    }

    private Cell createCell(int row, int col) {
        Cell c = new Cell();

        cells[row][col] = c;
        return c;
    }

    /**
     * Create edges.
     *
     * <p>
     * Example for 6 x 6 maze:
     * <pre>
     * B - BORDER (Vertex with state = BORDER)
     * V - VERTEX
     * E - EDGE
     * C - CELL (not in use)
     *
     * maze:
     *     0 1 2 3 4 5
     *   -------------
     * 0 | B B B B B B
     * 1 | B V E V E B
     * 2 | B E C E C B
     * 3 | B V E V E B
     * 4 | B E C E C B
     * 5 | B B B B B B
     * </pre>
     */
    private void createEdges() {
        IntStream.range(1, height - 1)
                 .forEach(row -> IntStream.range(1, width - 1)
                                          .forEach( col -> addEdge(row, col))
                 );
    }

    private void addEdge(int row, int col) {
        if (isOdd(col)) {
            if (isOdd(row)) return;

            int from = ((Vertex) cells[row - 1][col]).getName();
            int to = ((Vertex) cells[row + 1][col]).getName();
            createEdge(from, to, row, col);
        } else { // is Even column
            if (isOdd(row)) {
                int from = ((Vertex) cells[row][col - 1]).getName();
                int to = ((Vertex) cells[row][col + 1]).getName();
                createEdge(from, to, row, col);
            } else {
                createCell(row, col);
            }
        }

    }

    /**
     * Create edge and add to adjacency list and cell board.
     *
     * @param from from vertex
     * @param to to vertex
     * @param row cell y coordinate
     * @param col cell x coordinate
     */
    private Edge createEdge(int from, int to, int row, int col) {
        Edge e = new Edge(from, to, RND.nextInt(MAX_WEIGHT));

        cells[row][col] = e;
        edges.add(e);
        adjacency[from].offer(e);
        adjacency[to].offer(e);
        E++;
        return e;
    }
}
