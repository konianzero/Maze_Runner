package maze.model;

import maze.model.algorithm.PrimMST;
import maze.model.graph.CellState;
import maze.model.graph.Graph;
import maze.model.graph.Vertex;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Maze extends Graph {

    private transient PrimMST mstAlg;

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

    public Maze generate() {
        mstAlg.computeMST(this);
        addEntrance();
        addExit();
        return this;
    }

    private void addEntrance() {
        openRandomBorder(0);
    }

    private void addExit() {
        openRandomBorder(width - 1);
    }

    private void openRandomBorder(int col) {
        List<Integer> list =IntStream.iterate(1, i -> i < height - 1, i -> i + 2)
                                     .mapToObj(row -> ((Vertex) cells[row][col]).getName())
                                     .collect(Collectors.toList());

        int i = RND.nextInt(list.size());
        getVertex(list.get(i)).setState(CellState.EMPTY);
    }

    @Override
    public String toString() {
        return Arrays.stream(cells)
                     .map(row -> Stream.of(row)
                                  .map(e -> e.getState().getSymbol())
                                  .collect(Collectors.joining())
                     )
                     .collect(Collectors.joining("\n"));
    }

//    public String toFile() {
//        return toString()
//                         .replace(CellState.EMPTY.getSymbol(), "0")
//                         .replace(CellState.WALL.getSymbol(), "1");
//    }
}
