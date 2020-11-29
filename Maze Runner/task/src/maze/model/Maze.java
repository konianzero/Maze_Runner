package maze.model;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Maze {
    private static final String LS = "\n";
    private static final String EMPTY = "  ";
    private static final String WALL = "\u2588\u2588";

    private static final int SIZE = 10;

    private int[][] maze = new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 1, 0, 1, 0, 1, 0, 0, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 1, 1},
            {1, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {1, 0, 1, 0, 0, 0, 0, 0, 1, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 0, 1, 0, 1, 0, 0, 0, 1, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(maze)
              .forEach(row -> {
                          IntStream.range(0, SIZE)
                                   .forEach(i -> builder.append(getSymbols(row[i])));
                          builder.append(LS);
                      }
              );
        return builder.toString();
    }

    private String getSymbols(int i) {
        return i == 0 ? EMPTY : WALL;
    }
}
