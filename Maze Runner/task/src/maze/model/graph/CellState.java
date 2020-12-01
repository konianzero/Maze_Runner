package maze.model.graph;

public enum CellState {
    EMPTY ("  "),
    WALL ("\u2588\u2588"),
    BORDER (WALL.symbol);

    private String symbol;

    CellState(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
