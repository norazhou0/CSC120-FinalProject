import java.util.Scanner;

public class GameLoop {

    public static void main(String[] args) {

        // Create a player instance
        Player myPlayer = new Player("player", true, 8, 4, null);

        // Flag to control the game loop
        boolean stillPlaying = true;

        // User input scanner
        Scanner userInput = new Scanner(System.in);

        // Game opening
        System.out.println("******************");
        System.out.println("WELCOME TO SMITH ADVENTURE");
        System.out.println("******************");
        System.out.println("Enter 'ready' to start, anything else to exit.");

        String userResponse = userInput.nextLine().toLowerCase();
        if (!userResponse.equalsIgnoreCase("ready")) {
            stillPlaying = false;
            System.out.println("Goodbye! Come back soon!");
        } else {
            System.out.println("You're in the SMITH ADVENTURE.");
        }

        // Main game loop
        while (stillPlaying && myPlayer.isAlive()) {
            System.out.println("Enter a command (examine <item>, drop <item>, move <direction>, quit):");
            userResponse = userInput.nextLine().toLowerCase();

            if (userResponse.startsWith("examine")) {
                String[] parts = userResponse.split(" ");
                if (parts.length == 2) {
                    myPlayer.examine(parts[1]);
                } else {
                    System.out.println("Please provide an item to examine.");
                }
            } else if (userResponse.startsWith("drop")) {
                String[] parts = userResponse.split(" ");
                if (parts.length == 2) {
                    myPlayer.drop(parts[1]);
                } else {
                    System.out.println("Please provide an item to drop.");
                }
            } else if (userResponse.startsWith("move")) {
                String[] parts = userResponse.split(" ");
                if (parts.length == 2) {
                    myPlayer.move(parts[1]);
                    System.out.println("Player location: (" + myPlayer.getLocationRow() + ", " + myPlayer.getLocationColumn() + ")");

                    if (myPlayer.getPlace() == Place.FOREST) {
                        System.out.println("You are in the forest. Explore the oak tree? (yes/no)");
                        String command = userInput.nextLine().toLowerCase();
                        boolean exploring = true;

                        while (exploring) {
                            if (command.equals("yes")) {
                                myPlayer.examine("oak");
                                System.out.println("Use a tool to grab the dress? (yes/no)");
                                command = userInput.nextLine().toLowerCase();

                                if (command.equals("yes")) {
                                    System.out.println("Which tool would you like to use?");
                                    command = userInput.nextLine();

                                    if (myPlayer.getBackpack().containsKey(command) && myPlayer.getBackpack().get(command) > 0) {
                                        System.out.println("You used the " + command + " to grab the dress!");
                                        myPlayer.grab("dress");
                                    } else {
                                        System.out.println("You don't have that tool.");
                                    }
                                } else {
                                    System.out.println("You left the dress in the tree.");
                                }
                            } else if (command.equals("leave")) {
                                System.out.println("You left the forest.");
                                exploring = false;
                                myPlayer.setLocationColumn(9);
                                myPlayer.setLocationRow(9);
                            } else {
                                System.out.println("Type 'leave' to exit.");
                                command = userInput.nextLine().toLowerCase();
                            }
                        }
                    }
                } else {
                    System.out.println("Please specify a direction to move.");
                }
            } else if (userResponse.equalsIgnoreCase("quit")) {
                stillPlaying = false;
                System.out.println("Exiting the game. Goodbye!");
            } else {
                System.out.println("Unknown command. Please try again.");
            }
        }

        // Close scanner
        userInput.close();

        // Win condition
        if (myPlayer.getWin()) {
            System.out.println("Congratulations! You've won the game. Goodbye!");
        }
    }
}
