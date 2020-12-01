package maze.model.graph;

public class Vertex extends Cell {
    private final int name;

    public Vertex(int name) {
        this.name = name;
    }

    public int getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%d", name);
    }
}
