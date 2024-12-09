import java.util.Hashtable;
import java.util.Scanner;

public class Player extends Character {
    private Hashtable<String, Integer> backpack;
    private Place place = Place.GRID;
    private boolean canEnterLibrary = false;
    private boolean alive = true;
    private boolean win = false;
    private Scanner input;

    /**
     * Constructor for Player
     * @param name           the name of the player
     * @param canOnboard     whether the player can board the kayak
     * @param locationRow    the row on the grid the player is in
     * @param locationColumn the column on the grid the player is in
     * @param input          the input of the player
     */

    public Player(String name, boolean canOnboard, int locationRow, int locationColumn, Scanner input) {
        super(name, canOnboard, locationRow, locationColumn);
        this.backpack = new Hashtable<>();
        this.input = input;
    }

    /* Accessors */
    public Hashtable<String, Integer> getBackpack() {
        return this.backpack;
    }

    public Place getPlace() {
        return this.place;
    }

    public boolean getCanEnterLibrary() {
        return this.canEnterLibrary;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public boolean getWin() {
        return this.win;
    }

    /* Setters */
    public void setAlive(boolean status) {
        this.alive = status;
    }

    public void setLocationColumn(int column) {
        this.locationColumn = column;
    }

    public void setLocationRow(int row) {
        this.locationRow = row;
    }

    /**
     * The method that allows the player to examine items
     * 
     * @param item able to be examined
     */
    // ACORN, APPLE, DRESS, RECIPE, BOX, TOOL, LAPTOP, BOOK, OKA //
    public void examine(String input) {
        input = input.toLowerCase();
        int index = locationRow * 10 + locationColumn;
        if (input.contains("acorn") && scMap.hasAcorn(index)) {
            // check on the location
            System.out.println("This is an acorn. squirrels might like it.");
        } else if (input.contains("dress")) {
            System.out.println(
                    "This is a white dress from Ivy Day. It contains valuable memories of Smithies, which might be the key to save Smith");
        } else if (input.contains("laptop")) {
            System.out.println("This is Jordan's laptop. The wallpaper is of his dog, Charlie.");
        } else if (input.contains("apple")) {
            System.out.println("This is an apple from Mountain Day! This might be the key to save Smith.");
        } else if (input.contains("recipe")) {
            System.out.println(
                    "This is a recipe from Julia Child. Keep it safe—Smithies 100 years later will still need it for Julia Child Day!");
        } else if (input.contains("tool")) {
            System.out.println(
                    "It's Cross. A 19the century silver craft originally stored in SCMA. It's 30 cm tall. Hummm...That's pretty long");
        } else if (input.contains("oak")) {
            System.out.println("There is a white dress on the tree. However, you are not tall enough to reach it.");
        } else {
            System.out.println("Invalid! Cannot examine" + input);
        }
    }

    /**
     * The method that allows the player to grab items
     * 
     * @param item able to be grabbed
     */
    public void grab(String input) {
        input = input.toLowerCase();

        // Allow Acorn to be grabbed multiple times
        if (input.equals("acorn")) {
            int currentCount = backpack.getOrDefault("acorn", 0);
            // Increment the count of Acorn by 1
            backpack.put("acorn", currentCount + 1);
            System.out.println("You've grabbed an acorn! Total acorns: " + backpack.get("acorn"));
            return; // Exit early since Acorn has a special rule
        }

        // Check if the item is already in the backpack (avoid grab repetitively)
        if (backpack.containsKey(input)) {
            System.out.println("You already have the " + input + " in your backpack!");
            return; // Exit the method, no further action needed
        }

        int totalNumItem = 0; // Track total items in the backpack
        for (int quantity : backpack.values()) {
            totalNumItem += quantity;
        }

        // Check backpack capacity
        if (totalNumItem < 20) {
            backpack.put(input, 1); // Add the item to the backpack
            System.out.println("You grabbed the " + input + "!");
        } else {
            this.setAlive(false); // Player dies due to overload
            System.out.println("The backpack is overloaded! Your kayak collapsed! You drowned and died.");
        }
    }

    /**
     * The method that allows the player to drop items
     * 
     * @param item able to be dropped
     */
    public void drop(String input) {
        input = input.toLowerCase();
        // Check if the item exists in the backpack and has a count > 0
        if (backpack.containsKey(input) && backpack.get(input) > 0) {
            int currentCount = backpack.get(input);
            if (currentCount > 1) {
                // Decrease the count by 1
                backpack.put(input, currentCount - 1);
            } else {
                // Remove the item entirely if the count is 0 after dropping
                backpack.remove(input);
            }
        } else {
            System.out.println("You don't have that item in your backpack.");
        }
    }
    // once dropped, cannot pick up again

    /**
     * The method that allows the player to move
     * @param input for moving direction
     */
    public void move(String input) {
        if (input.contains("south")) {
            this.setLocationRow(this.locationRow + 1); 
        } else if (input.contains("north")) {
            this.setLocationRow(this.locationRow - 1); 
        } else if (input.contains("west")) {
            this.setLocationColumn(this.locationColumn - 1); 
        } else if (input.contains("east")) {
            this.setLocationColumn(this.locationColumn + 1);
        } else {
            System.out.println("Invalid direction! Please choose from south, north, west, or east.");
        }

        // Handle out-of-bounds scenarios
        if (this.getLocationRow() < 0) {
            handleWaterfall();
        } else if (this.getLocationRow() > 9) {
            this.place = Place.FOREST;
            handleForest();
        } else if (this.getLocationColumn() < 0) {
            if (canEnterLibrary) {
                this.place = Place.LIBRARY;
                handleLibrary();
            } else {
                System.out.println("Sorry, you cannot enter the library yet!");
            }
        } else if (this.getLocationColumn() > 9) {
            this.place = Place.FOREST;
            handleStatue();
        }
    }

    /**
     * The method that allows the player to collect acorns
     */
    public void collectAcorn() {
        int index = locationRow * 10 + locationColumn;
        if (scMap.hasAcorn(index)) { // Check if an acorn exists at the current position
            System.out.println("There is an acorn here. Do you want to collect it? (yes/no)");
            String response = input.nextLine().toLowerCase();

            if (response.equals("yes")) {
                grab("acorn"); // Add the acorn to the backpack
                scMap.removeAcorn(index); // Remove the acorn from the map
                System.out.println("You collected an acorn!");
            } else {
                System.out.println("You chose not to collect the acorn.");
            }
        } else {
            System.out.println("There's no acorn here.");
        }
    }

    /**
     * The method that allows the player to access library
     */
    public void accessLibrary() {
        if (this.canEnterLibrary) {
            System.out.println("You have already helped Squirhold! The library is open for you.");
            return;
        }

        if (this.locationRow == 4 && this.locationColumn == 7) {
            System.out.println(
                    "You saw Squirhold (a family of squirrels). Squirrel Papa says: Can you give us anything to eat? We can't find food because of the flood. If you help us, we will help you too!");
            String response = input.nextLine().toLowerCase();

            if (response.equals("yes")) {
                if (!backpack.containsKey("ACORN")) {
                    System.out.println("Squirrel Mama: Sorry, you don't have the food we want :(");
                } else {
                    int acornCount = backpack.get("ACORN");
                    if (acornCount >= 20) {
                        backpack.put("ACORN", acornCount - 20);
                        this.canEnterLibrary = true;
                        System.out.println(
                                "Squirrel Baby: Thank you so much! We will take 20 acorns! Do you want to go to the library? We will help you with that.");
                        System.out
                                .println("Squirrel Mama runs to the library and says: Now you can enter the library.");
                    } else {
                        System.out.println(
                                "Squirrel Papa: Sorry, we are a big family, and we need more acorns. Please find 20 acorns and then come back to us!");
                    }
                }
            } else {
                System.out.println("Squirhold: Okay...");
            }
        }
    }

    /**
     * The method that handles the waterfall scenario
     */
    private void handleWaterfall() {
        System.out.println("You fell into a waterfall!");
        // Generate a random number between 0 and 1
        if (Math.random() < 0.5) {
            // 50% chance of dying
            this.setAlive(false);
            System.out.println("You didn't survive the waterfall... Game over.");
        } else {
            setLocationColumn(0);
            setLocationRow(0);
            System.out.println("You survived the waterfall! Waterfall is dangerous and unpredictable.");
        }
    }

    /**
     * The method that handles the forest scenario
     */
    private void handleForest() {
        System.out.println("You wandered into the forest. The air feels different here...");
        System.out.println("There looks to be something in the Oak. Do you want to take a closer look at this oak? (yes/no)");

        Scanner scanner = new Scanner(System.in);
        boolean exploring = true; // Keep track of the exploration status

        while (exploring) {
            String command = scanner.nextLine().toLowerCase();

            if (command.equals("yes")) {
                examine("oak"); // Use your existing examine method

                // Check if the player wants to try using a tool
                System.out.println("Do you want to try using a tool from your backpack to grab the dress? (yes/no)");
                String useToolResponse = scanner.nextLine().toLowerCase();

                if (useToolResponse.equals("yes")) {
                    System.out.println("Which tool would you like to use?");
                    String tool = scanner.nextLine();

                    // Check if the tool exists in the player's backpack
                    if (this.backpack.containsKey(tool) && this.backpack.get(tool) > 0) {
                        System.out.println("You used the " + tool + " to grab the white dress!");
                        grab("dress"); // Use the grab method to add the dress to the backpack
                    } else {
                        System.out.println("You don't have that tool in your backpack. You cannot reach the dress.");
                    }
                } else {
                    System.out.println("You decided not to use a tool and left the dress in the tree.");
                }
            } else if (command.equals("leave")) {
                System.out.println("You left the forest.");
                exploring = false; // End the loop
                setLocationColumn(0);
                setLocationRow(0);
            } else if (command.equals("no")) {
                System.out.println("OK. Do you want to leave?");
            } else {
                System.out.println("Unknown command. Type 'leave' if you want to leave.");
            }
        }
        scanner.close();
        //Exception in thread "main" java.util.NoSuchElementException: No line found
        //at java.base/java.util.Scanner.nextLine(Scanner.java:1660)
        //at GameLoop.main(GameLoop.java:40)
        //(base) ReneedeMacBook-Pro:CSC120-FinalProject renee$ 
    }

    /**
     * The method that handles the library scenario
     */
    private void handleLibrary() {
        System.out.println(
                "You entered the library. There are four mysterious boxes here: one is yellow, one is blue, one is white, and one is silver.");

        // Keep track of the exploration status
        boolean exploring = true;

        while (exploring) {
            System.out.println("What would you like to do? (type 'examine [box color]' or 'leave')");
            String command = input.nextLine().toLowerCase();

            // Yellow box with recipe
            if (command.equals("examine yellow box")) {
                System.out.println(
                        "There is a tag with some words on the box, but it was blurred by water. You can faintly make out two words: Julia and cook. Do you want to open it? (yes/no)");
                if (input.nextLine().toLowerCase().equals("yes")) {
                    grab("recipe");
                    System.out.println("You got Julia Child's recipe!");
                } else {
                    System.out.println("OK. Do you want to check other box?");
                }

                // White box with laptop
            } else if (command.equals("examine white box")) {
                System.out.println(
                        "This box looks familiar. You seem to have seen it in a CSC class before. Do you want to open it? (yes/no)");
                if (input.nextLine().toLowerCase().equals("yes")) {
                    grab("laptop");
                    System.out.println("You got Jordan's laptop!");
                } else {
                    System.out.println("OK. Do you want to check other box?");
                }

                // Blue box with book
            } else if (command.equals("examine blue box")) {
                System.out.println(
                        "The box is small and old. You've never seen it before. Do you want to open it? (yes/no)");
                if (input.nextLine().toLowerCase().equals("yes")) {
                    grab("book");
                    System.out.println(
                            "You got a mysterious book! The book says if you want to save Smith, you must collect all the things that's contain smithies' happy memories.");
                } else {
                    System.out.println("OK. Do you want to check other box?");
                }

                // Silver box with tool
            } else if (command.equals("examine silver box")) {
                System.out.println("The box is big and heavy. Do you want to open it? (yes/no)");
                if (input.nextLine().toLowerCase().equals("yes")) {
                    grab("cross");
                    System.out.println("You got a Cross! It is a silver cross from Ethiopia. It is very long.");
                } else {
                    System.out.println("OK. Do you want to check other box?");
                }

                // Leave library
            } else if (command.equals("leave")) {
                System.out.println("You left the library.");
                exploring = false; // End the loop

                // Catch error
            } else {
                System.out.println("Unknown command. Do you want to leave?");
            }
        }
    }

    /**
     * The method that handles the statue scenario
     */
    private void handleStatue() {
        System.out.println(
                "It's the Lanning Fountain in front of the Burton! LOOK!!! The statue is talking and it's glowing.");
        System.out.println(
                "Statue: You must be the one who can save Smith College. I can help you with that. However, I need three things to release my power.");
        System.out.println(
                "A dress from Ivy Day. An apple from Mountain Day. And a recipe from Julia Child. Find them and bring them to me!");
        if (getBackpack().containsKey("APPLE") &&
                getBackpack().containsKey("DRESS") &&
                getBackpack().containsKey("RECIPE")) {
                    this.win = true;
            System.out.println("Yay, you saved the Smith Campus!");
        } else {
            System.out.println(
                    "Statue: Sorry! You don't have all the things I need to save smith! Please collect all of them and find me again.");
                    setLocationColumn(9);
                    setLocationRow(9);
        }
    }


    /**
     * The method that allows the player to interact with raccoon
     * 
     * @param raccoon
     */
    public void interactWithRaccoon(Raccoon raccoon) {
        if (this.getLocationRow() == raccoon.getLocationRow()
                && this.getLocationColumn() == raccoon.getLocationColumn()) {
            System.out.println(
                    "You encounter a rabbit raccoon. It looks at you with pleading face and says, 'I've lost my way to home. Can you take me to the forest?'");

            if (input.nextLine().toLowerCase().equals("yes")) {
                raccoon.onBoard(this); // Raccoon follows the player
                System.out.println("You agreed to help the raccoon. Lead it to the forest!");
            } else {
                System.out.println("The raccoon looks disappointed and stays put.");
            }
        }
    }

    /**
     * The method that allows the raccoon to give the player the apple
     * 
     * @param raccoon
     */
    public void checkLocationForRaccoon(Raccoon raccoon) {
        if (this.getPlace() == Place.FOREST && !raccoon.getAtHome()) {
            raccoon.giveApple(this); // Trigger the raccoon giving the apple
            System.out.println("The raccoon thanks to you and gives you an apple!");
        }
    }

    public static void main(String[] args) {
        Player player = new Player("player", true, 5, 4, null);
        System.out.println(player.getLocationColumn());
        System.out.println(player.getLocationRow());
    }

}