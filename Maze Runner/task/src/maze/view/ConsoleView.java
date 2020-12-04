package maze.view;

import maze.controller.Controller;

import java.util.Scanner;

public class ConsoleView {
    private static final String MAIN_MENU = "\n=== Menu ===\n" +
                                            "1. Generate a new maze\n" +
                                            "2. Load a maze\n" +
                                            "%s" +
                                            "0. Exit\n";
    private static final String ADD_MENU = "3. Save the maze\n" +
                                           "4. Display the maze\n" +
                                           "5. Find the escape\n";
    private static final String MAZE_SIZE = "Enter the size of a maze";
    private static final String BYE = "Bye!";

    private final Scanner scanner;

    private Controller controller;

    public ConsoleView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        System.out.printf(MAIN_MENU, "");
        switch (scanner.nextInt()) {
            case 0:
                exit();
                break;
            case 1:
                generateMaze();
                break;
            case 2:
                loadMaze();
                break;
            default:
        }
    }

    private void generateMaze() {
        System.out.println(MAZE_SIZE);
        int size = scanner.nextInt();

        controller.onStart(size, size);
        maze();
    }

    private void loadMaze() {
        scanner.nextLine();
        String pathToFile = scanner.nextLine();
        controller.loadMaze(pathToFile);
        maze();
    }

    public void maze() {
        while (true) {
            System.out.printf(MAIN_MENU, ADD_MENU);
            switch (scanner.nextInt()) {
                case 1:
                    generateMaze();
                    break;
                case 2:
                    loadMaze();
                    break;
                case 3:
                    saveMaze();
                    break;
                case 4:
                    displayMaze();
                    break;
                case 5:
                    findEscape();
                    break;
                case 0:
                    exit();
                    break;
                default:
            }
        }
    }

    private void findEscape() {
        controller.findPath();
    }

    private void saveMaze() {
        scanner.nextLine();
        String pathToFile = scanner.nextLine();
        controller.saveMaze(pathToFile);
    }

    private void displayMaze() {
        controller.showMaze();
    }

    public void refresh(String s) {
        System.out.println(s);
    }

    private void exit() {
        System.out.println(BYE);
        System.exit(0);
    }
}
