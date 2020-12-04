package maze.model.graph;

import maze.model.algorithm.Path;

import java.io.Serializable;

public class Cell extends Path {
    private static int count = 0;

    private CellState state;
    private final int number;

    public Cell() {
        state = CellState.WALL;
        number = count++;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public void toMST() {
        super.toMST();
        state = CellState.EMPTY;
    }

    @Override
    public void toShortestPath() {
        super.toShortestPath();
        state = CellState.PATH;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState cellState) {
        this.state = cellState;
    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)            return true;
        if (!(obj instanceof Cell)) return false;
        Cell other = (Cell) obj;
        return this.number == other.number;
    }
}
