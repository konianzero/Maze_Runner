package maze.model;

import maze.model.algorithm.BFP;
import maze.model.algorithm.PrimMST;
import maze.model.graph.*;

import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Maze extends Graph {

    private transient PrimMST mstAlg;
    private transient BFP shortestPathsAlg;

    private Deque<Cell> mst;

    public Maze() {
    }

//    public void initMaze(List<String> lines) {
//        initFromString(lines);
//    }

    public Maze initMaze(int height, int width) {
        initGraph(height, width);
        return this;
    }

    public Maze setMSTAlgorithm(PrimMST mstAlgorithm) {
        this.mstAlg = mstAlgorithm;
        return this;
    }

    public Maze setSPAlgorithm(BFP shortestPathsAlg) {
        this.shortestPathsAlg = shortestPathsAlg;
        return this;
    }

    public void generate() {
        mst = mstAlg.findMST(this);

        RND.setSeed(System.currentTimeMillis());
        addEntrance();
        addExit();
    }

    private void addEntrance() {
        Vertex v = getRandomBorder(0);
        v.setState(CellState.EMPTY);
        mst.offerFirst(v);
    }

    private void addExit() {
        Vertex v = getRandomBorder(width - 1);
        v.setState(CellState.EMPTY);
        mst.offerLast(v);
    }

    private Vertex getRandomBorder(int col) {
        List<Integer> list =IntStream.iterate(1, i -> i < height - 1, i -> i + 2)
                                     .mapToObj(row -> ((Vertex) cells[row][col]).getName())
                                     .collect(Collectors.toList());

        int i = RND.nextInt(list.size());
        return getVertex(list.get(i));
    }

    @Override
    public String toString() {
        return Arrays.stream(cells)
                     .map(row -> Stream.of(row)
                                       .map(c -> c.getState().getSymbol())
                                       .collect(Collectors.joining())
                     )
                     .collect(Collectors.joining("\n"));
    }

    public void findPath() {
        Vertex start = ((Vertex) mst.pollFirst());
        Objects.requireNonNull(start).toShortestPath();
        shortestPathsAlg.findPaths(this, start.getName() + 1);

        Vertex finish = ((Vertex) mst.pollLast());
        Objects.requireNonNull(finish).toShortestPath();
        Deque<Integer> path = shortestPathsAlg.pathTo(finish.getName() - 1,this);


        edges.stream()
             .forEach(e -> {
                 if (!e.isInMST()) return;

                 int v = e.either(), w = e.other(v);
                 if ((path.contains(v) && path.contains(w)) || (v == finish.getName() || w == finish.getName())) {
                     e.toShortestPath();
                 }
             });
    }
}
