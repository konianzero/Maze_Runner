package maze;

import maze.controller.Controller;
import maze.model.Maze;
import maze.view.ConsoleView;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ConsoleView view = new ConsoleView(scanner);
        Controller controller = new Controller();
        Maze maze = new Maze();

        view.setController(controller);
        controller.setView(view);
        controller.setModel(maze);

        view.start();
        scanner.close();
    }
}
