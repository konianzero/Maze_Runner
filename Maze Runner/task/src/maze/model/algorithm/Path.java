package maze.model.algorithm;

import java.io.Serializable;

public class Path implements Serializable {
    private boolean mst;
    private boolean sp;

    public void toMST() {
        this.mst = true;
    }

    public void toShortestPath() {
        this.sp = true;
    }

    /**
     * Is cell in minimum spanning tree.
     * @return {@code true} if cell in minimum spanning tree;
     *         {@code false} otherwise
     */
    public boolean isInMST() {
        return mst;
    }

    /**
     * Is cell in shortest path.
     * @return {@code true} if cell in shortest path;
     *         {@code false} otherwise
     */
    public boolean isInSP() {
        return sp;
    }
}
