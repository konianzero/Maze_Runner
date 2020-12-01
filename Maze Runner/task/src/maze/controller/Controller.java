package maze.controller;

import maze.model.Maze;
import maze.model.algorithm.PrimMST;
import maze.view.ConsoleView;

public class Controller {
    private ConsoleView view;
    private Maze maze;

    public void setView(ConsoleView view) {
        this.view = view;
    }

    public void setModel(Maze maze) {
        this.maze = maze;
    }

    public void onStart(int height, int width) {
        PrimMST primMST = new PrimMST();

        maze = new Maze().initMaze(height, width)
                         .setMSTAlgorithm(primMST)
                         .generate();

        view.refresh(maze.toString());
    }
}
