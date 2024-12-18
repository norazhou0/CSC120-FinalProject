import java.util.Scanner;

public class GameLoop {

    public static void main(String[] args) {

        // Create a player instance
        scMap map = new scMap();
        Player myPlayer = new Player("player", true, 5, 4, new Scanner(System.in), map);

        // Flag to control the game loop
        boolean stillPlaying = true;

        // User input scanner
        Scanner userInput = new Scanner(System.in);

        // Game opening
        System.out.println("******************");
        System.out.println("WELCOME TO SMITH ADVENTURE");
        System.out.println("******************");
        System.out.println("Enter 'ready' to start, anything else to exit.");

        // Get user input
        String userResponse = userInput.nextLine().toLowerCase();
        if (!userResponse.equals("ready")) {
            stillPlaying = false;
            System.out.println("Goodbye! Come back soon!");
        } else {
            System.out.println(
                    "You're in the SMITH ADVENTURE. One day, there is a flood disaster happening on a hypothetical Smith Campus. It has covered all major routes, and everyone has disappeared. You are on a kayak able to move around and collect certain items trying to save Smith. You can get to the library, waterfall, statue, and forest. You may also encounter animals that can help you.");
        }

        // Main game loop
        while (stillPlaying && myPlayer.isAlive()) {
            System.out.println("Enter a command (move (north/west/south/east), examine, drop, quit):");
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
                    System.out.println("Player location: (" + myPlayer.getLocationColumn() + ", "
                            + myPlayer.getLocationRow() + ")");

                    // meet Squirhold
                    if (myPlayer.getLocationRow() == 4 && myPlayer.getLocationColumn() == 7) {
                        if (myPlayer.getCanEnterLibrary()) {
                            System.out.println("You have already helped Squirhold! The library is open for you.");
                        } else {
                            System.out.println(
                                    "You saw Squirhold (a family of squirrels). Squirrel Papa says: Can you give us anything to eat? We can't find food because of the flood. If you help us, we will help you too! (yes/no)");
                            String response = userInput.nextLine().toLowerCase();
                            if (response.equals("yes")) {
                                myPlayer.accessLibrary();
                            } else {
                                System.out.println("Squirhold: Okay...");
                            }
                        }
                    }

                    // collect acorn
                    if (myPlayer.getPlace() == Place.GRID) {
                        // convert location coordinates to grid index
                        int index = myPlayer.getLocationRow() * 10 + myPlayer.getLocationColumn();
                        if (map.hasAcorn(index)) { // Use the instance map
                            System.out.println("THERE IS AN ACORN HERE. Do you want to collect it? (yes/no)");
                            String response = userInput.nextLine().toLowerCase();
                            if (response.equals("yes")) {
                                myPlayer.grab("acorn"); // Add the acorn to the backpack
                                map.removeAcorn(index); // Remove the acorn from the map
                                if(myPlayer.isAlive()){
                                    System.out.println("You collected an acorn!");
                                }
                            } else {
                                System.out.println("You chose not to collect the acorn.");
                            }
                        } else {
                            System.out.println("There's no acorn here.");
                        }
                    }

                    // handle forest
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
                                    command = userInput.nextLine().toLowerCase();

                                    if (command.equals("cross") && myPlayer.getBackpack().containsKey("cross")
                                            && myPlayer.getBackpack().get("cross") > 0) {
                                        System.out.println("You used the cross to grab the dress!");
                                        myPlayer.grab("dress");
                                    } else if (command.equals("cross") && !myPlayer.getBackpack().containsKey("cross")) {
                                        System.out.println("Oops. You don't have cross in your packpack");
                                    } else {
                                        System.out.println(command + " cannot be used to grab the dress.");
                                    }
                                } else {
                                    System.out.println("You left the dress in the tree.");
                                }
                            } else if (command.equals("leave")) {
                                System.out.println("You left the forest.");
                                // set the player location
                                myPlayer.setPlace(Place.GRID);
                                myPlayer.setLocationColumn(9);
                                myPlayer.setLocationRow(9);
                                exploring = false;
                            } else {
                                System.out.println("Type 'leave' to exit.");
                                command = userInput.nextLine().toLowerCase();
                            }
                        }

                        // handle waterfall
                    } else if (myPlayer.getPlace() == Place.WATERFALL) {
                        myPlayer.handleWaterfall();

                        // handle statue
                    } else if (myPlayer.getPlace() == Place.STATUE) {
                        myPlayer.handleStatue();
                        if (myPlayer.getWin()) {
                            System.out.println("Congratulations! You've won the game. Goodbye!");
                            stillPlaying = false;
                            break;
                        }

                        // handle library
                    } else if (myPlayer.getPlace() == Place.LIBRARY && myPlayer.getCanEnterLibrary()) {
                        System.out.println(
                                "You entered the library. There are four mysterious boxes here: one is yellow, one is blue, one is white, and one is silver.");
                        boolean exploring = true;

                        while (exploring) {
                            System.out.println("What would you like to do? (type 'examine [box color]' or 'leave')");
                            // Yellow box with recipe
                            String command = userInput.nextLine().toLowerCase();
                            if (command.contains("examine yellow")) {
                                System.out.println(
                                        "There is a tag with some words on the box, but it was blurred by water. You can faintly make out two words: Julia and cook. Do you want to open it? (yes/no)");
                                if (userInput.nextLine().toLowerCase().equals("yes")) {
                                    myPlayer.grab("recipe");
                                    System.out.println("You got Julia Child's recipe!");
                                } else {
                                    System.out.println("OK. Do you want to check other box?");
                                }

                                // White box with laptop
                            } else if (command.contains("examine white")) {
                                System.out.println(
                                        "This box looks familiar. You seem to have seen it in a CSC class before. Do you want to open it? (yes/no)");
                                if (userInput.nextLine().toLowerCase().equals("yes")) {
                                    myPlayer.grab("laptop");
                                    System.out.println("You got Jordan's laptop!");
                                } else {
                                    System.out.println("OK. Do you want to check other box?");
                                }

                                // Blue box with book
                            } else if (command.contains("examine blue")) {
                                System.out.println(
                                        "The box is small and old. You've never seen it before. Do you want to open it? (yes/no)");
                                if (userInput.nextLine().toLowerCase().equals("yes")) {
                                    myPlayer.grab("apple");
                                    System.out.println(
                                            "You got an apple from 1st mountain day. It never corrupts.");
                                } else {
                                    System.out.println("OK. Do you want to check other box?");
                                }

                                // Silver box with tool
                            } else if (command.contains("examine silver")) {
                                System.out.println("The box is big and heavy. Do you want to open it? (yes/no)");
                                if (userInput.nextLine().toLowerCase().equals("yes")) {
                                    myPlayer.grab("cross");
                                    System.out.println(
                                            "You got a Cross! It is a silver cross from Ethiopia. It is very long.");
                                } else {
                                    System.out.println("OK. Do you want to check other box?");
                                }

                                // Leave library
                            } else if (command.equals("leave")) {
                                System.out.println("You left the library.");
                                // Set player location
                                myPlayer.setPlace(Place.GRID);
                                myPlayer.setLocationColumn(0);
                                myPlayer.setLocationRow(0);
                                exploring = false; // End the loop

                                // Catch error
                            } else {
                                System.out.println("Unknown command. Do you want to leave? Type 'leave' to exit.");
                                command = userInput.nextLine().toLowerCase();
                            }
                        }
                    } 
                } else {
                    System.out.println("Invalid command. Please try again.");
                }
            } else if (userResponse.equalsIgnoreCase("quit")) {
                stillPlaying = false;
                System.out.println("Exiting the game. Goodbye!");
            } else {
                System.out.println("Unknown command. Please try again.");
            }
        } userInput.close();
    }
}
