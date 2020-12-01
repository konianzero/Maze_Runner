package maze.view;

import maze.controller.Controller;

import java.util.Scanner;

public class ConsoleView {
    private static final String MAZE_SIZE = "Please, enter the size of a maze";

    private final Scanner scanner;

    private Controller controller;

    public ConsoleView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        System.out.println(MAZE_SIZE);
        int height = scanner.nextInt();
        int width = scanner.nextInt();

        controller.onStart(height, width);
    }

    public void refresh(String s) {
        System.out.println(s);
    }
}
