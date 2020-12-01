package maze.model.algorithm;

public class Path {
    private boolean mst;

    public void toMst() {
        this.mst = true;
    }

    public boolean isMst() {
        return mst;
    }
}
