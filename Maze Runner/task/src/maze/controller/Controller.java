package maze.controller;

import maze.model.Maze;
import maze.model.algorithm.PrimMST;
import maze.view.ConsoleView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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

        maze.initMaze(height, width)
            .setMSTAlgorithm(primMST)
            .generate();

        view.refresh(maze.toString());
    }

    public void showMaze() {
        view.refresh(maze.toString());
    }

//    public void loadMaze(String pathToFile) {
//        List<String> lines = null;
//        try {
//            lines = Files.readAllLines(Path.of(pathToFile));
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//        maze.initMaze(lines);
////        view.refresh(maze.toString());
//    }
//
//    public void saveMaze(String pathToFile) {
//        try {
//            Files.writeString(Path.of(pathToFile), maze.toFile());
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//    }

    public void saveMaze(String pathToFile) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathToFile))) {
            oos.writeObject(maze);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void loadMaze(String pathToFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pathToFile))) {
            maze = (Maze) ois.readObject();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }
}
