package maze.model.graph;

import maze.model.algorithm.Path;

import java.io.Serializable;

public class Cell extends Path implements Serializable {
    private CellState state;

    public Cell() {
        state = CellState.WALL;
    }

    @Override
    public void toMst() {
        super.toMst();
        if (state != CellState.BORDER) {
            state = CellState.EMPTY;
        }
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState cellState) {
        this.state = cellState;
    }
}
