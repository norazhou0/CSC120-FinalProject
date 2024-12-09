import java.util.Hashtable;
import java.util.Scanner;

public class Player extends Character {
    private Hashtable<String, Integer> backpack;
    private Place place = Place.GRID;
    private boolean canEnterLibrary = false;
    private boolean alive = true;
    private Scanner input;

    /**
     * Constructor for Player
     * @param name the name of the player
     * @param canOnboard whether the player can board the kayak
     * @param locationRow the row on the grid the player is in
     * @param locationColumn the column on the grid the player is in
     * @param input the input of the player
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

    /* Setters */
    public void setAlive(boolean status) {
        this.alive = status;
    }


    /** 
     * The method that allows the player to examine items
     * @param item able to be examined
     */
    // ACORN, APPLE, DRESS, RECIPE, BOX, TOOL, LAPTOP, BOOK, OKA //
    public void examine (ItemsExamine item){
        if (item.equals(ItemsExamine.ACORN)) {
            // check on the location
            System.out.println("This is an acorn. squirrels might like it.");
        } else if (item.equals(ItemsExamine.DRESS)) {
            System.out.println("This is a white dress from Ivy Day. It contains valuable memories of Smithies, which might be the key to save Smith");
        } else if (item.equals(ItemsExamine.LAPTOP)) {
            System.out.println("This is Jordan's laptop. The wallpaper is of his dog, Charlie.");
        } else if (item.equals(ItemsExamine.APPLE)) {
            System.out.println("This is an apple from Mountain Day! This might be the key to save Smith.");
        } else if (item.equals(ItemsExamine.RECIPE)) {
            System.out.println("This is a recipe from Julia Child. Keep it safe—Smithies 100 years later will still need it for Julia Child Day!");
        } else if (item.equals(ItemsExamine.TOOL)) {
            System.out.println("It's Cross. A 19the century silver craft originally stored in SCMA. It's 30 cm tall. Hummm...That's pretty long");
        } else if (item.equals(ItemsExamine.OKA)) {
            System.out.println("There is a white dress on the tree. However, you are not tall enough to reach it.");
        } else {
            System.out.println("Cannot examine.");
        }
    }


    /** 
     * The method that allows the player to grab items
     * @param item able to be grabbed
     */
    public void grab(ItemsGrab item) {
        // Get the name of the item
        String itemName = item.name();
    
        // Allow Acorn to be grabbed multiple times
        if (itemName.equalsIgnoreCase("ACORN")) {
            int currentCount = backpack.getOrDefault("ACORN", 0);
            // Increment the count of Acorn by 1
            backpack.put("ACORN", currentCount + 1);
            System.out.println("You've grabbed an acorn! Total acorns: " + backpack.get(itemName));
            return; // Exit early since Acorn has a special rule
        }
    
        // Check if the item is already in the backpack (avoid grab repetitively)
        if (backpack.containsKey(itemName)) {
            System.out.println("You already have the " + itemName + " in your backpack!");
            return; // Exit the method, no further action needed
        }
    
        int totalNumItem = 0; // Track total items in the backpack
        for (int quantity : this.backpack.values()) {
            totalNumItem += quantity;
        }
    
        // Check backpack capacity
        if (totalNumItem < 20) {
            backpack.put(itemName, 1); // Add the item to the backpack
            System.out.println("You grabbed the " + itemName.toLowerCase() + "!");
        } else {
            this.setAlive(false); // Player dies due to overload
            System.out.println("The backpack is overloaded! Your kayak collapsed! You drowned and died.");
        }
    }
    
    /** 
     * The method that allows the player to drop items
     * @param item able to be dropped
     */
    public void drop(ItemsGrab item) {
        String itemName = item.name();
        // Check if the item exists in the backpack and has a count > 0
        if (this.backpack.containsKey(itemName) && this.backpack.get(itemName) > 0) {
            int currentCount = this.backpack.get(itemName);
            if (currentCount > 1) {
                // Decrease the count by 1
                this.backpack.put(itemName, currentCount - 1);
            } else {
                // Remove the item entirely if the count is 0 after dropping
                this.backpack.remove(itemName);
            }
        } else {
            System.out.println("You don't have that item in your backpack.");
        }
    }
    // once dropped, cannot pick up again

    // Check for acorn and handle interaction
    public void collectAcorn(scMap map) {
        int index = locationRow * 10 + locationColumn;
        if (scMap.hasAcorn(index)) { // Check if an acorn exists at the current position
            System.out.println("There is an acorn here. Do you want to collect it? (yes/no)");
            String response = input.nextLine().toLowerCase();

            if (response.equals("yes")) {
                grab(ItemsGrab.ACRON); // Add the acorn to the backpack
                scMap.removeAcorn(index); // Remove the acorn from the map
                System.out.println("You collected an acorn!");
            } else {
                System.out.println("You chose not to collect the acorn.");
            }
        } else {
            System.out.println("There's no acorn here.");
        }
    }

    public void accessLibrary() {
        if (this.canEnterLibrary) {
            System.out.println("You have already helped Squirhold! The library is open for you.");
            return;
        }
    
        if (this.locationRow == 4 && this.locationColumn == 7) {
            System.out.println("You saw Squirhold (a family of squirrels). Squirrel Papa says: Can you give us anything to eat? We can't find food because of the flood. If you help us, we will help you too!");
            String response = input.nextLine().toLowerCase();
    
            if (response.equals("yes")) {
                if (!this.backpack.containsKey("ACORN")) {
                    System.out.println("Squirrel Mama: Sorry, you don't have the food we want :(");
                } else {
                    int acornCount = this.backpack.get("ACORN");
                    if (acornCount >= 20) {
                        this.backpack.put("ACORN", acornCount - 20);
                        this.canEnterLibrary = true;
                        System.out.println("Squirrel Baby: Thank you so much! We will take 20 acorns! Do you want to go to the library? We will help you with that.");
                        System.out.println("Squirrel Mama runs to the library and says: Now you can enter the library.");
                    } else {
                        System.out.println("Squirrel Papa: Sorry, we are a big family, and we need more acorns. Please find 20 acorns and then come back to us!");
                    }
                }
            } else {
                System.out.println("Squirhold: Okay...");
            }
        }
    }
    

    public void setLocationColumn(int column) {
        this.locationColumn = column;
    }

    public void setLocationRow(int row) {
        this.locationColumn = row;
    }

    /** 
     * The method that allows the player to move
     * @param input for moving direction
     */
    public void move(String input) {
        // Ensure case-insensitivity
        input = input.toLowerCase();
        // Check if the input contains "move" and a valid direction
        if (input.contains("move")) {
            if (input.contains("south")) {
                this.setLocationRow(this.getLocationRow() + 1); // Move south
            } else if (input.contains("north")) {
                this.setLocationRow(this.getLocationRow() - 1); // Move north
            } else if (input.contains("west")) {
                this.setLocationColumn(this.getLocationColumn() - 1); // Move west
            } else if (input.contains("east")) {
                this.setLocationColumn(this.getLocationColumn() + 1); // Move east
            } else {
                System.out.println("Invalid direction! Please choose from south, north, west, or east.");
            }
        } else {
            System.out.println("You must include 'move' and a valid direction (e.g., 'move south').");
            return;
        }
    
        // Handle out-of-bounds scenarios
        if (this.getLocationRow() < 0) {
            handleWaterfall();
        } else if (this.getLocationRow() > 9) {
            handleForest();
        } else if (this.getLocationColumn() < 0) {
            if (canEnterLibrary) {
                enterLibrary();
            } else {
                System.out.println("Sorry, you cannot enter the library yet!");
            }
        } else if (this.getLocationColumn() > 9) {
            this.place = Place.STATUE;
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
        this.place = Place.FOREST;
        System.out.println("You wandered into the forest. The air feels different here...");
        System.out.println("There looks to be something in the Oak.");
        boolean exploring = true; // Keep track of the exploration status
        while (exploring) {
            String command = input.nextLine().toLowerCase();
            if (command.equals("examine oak")) {
                examine(ItemsExamine.OKA); // Use existing examine method
                // Check if the player wants to try using a tool
                System.out.println("Do you want to try using a tool from your backpack to grab the dress? (yes/no)");
                if (input.nextLine().equalsIgnoreCase("yes")) {
                    System.out.println("Which tool would you like to use?");
                    String tool = input.nextLine();
                    // Check if the tool exists in the player's backpack
                    if (this.backpack.containsKey(tool)) {
                        System.out.println("You used the " + tool + " to grab the white dress!");
                        grab(ItemsGrab.DRESS); // Use the grab method to add the dress to the backpack
                    } else {
                        System.out.println("You don't have that tool in your backpack. You cannot reach the dress.");
                    }
                } else {
                    System.out.println("You decided not to use a tool and left the dress in the tree.");
                }
            } else if (command.equals("leave")) {
                System.out.println("You left the forest.");
                // End the loop
                exploring = false; 
            } else {
                System.out.println("Unknown command. Do you want to leave?");
            }
        }
    }
    
    /** 
     * The method that handles the library scenario
     */
    private void enterLibrary() {
        System.out.println("You entered the library. There are four mysterious boxes here: one is yellow, one is blue, one is white, and one is silver.");

        // Keep track of the exploration status
        boolean exploring = true; 
        
        while (exploring) {
            System.out.println("What would you like to do? (type 'examine [box color]' or 'leave')");
            String command = input.nextLine().toLowerCase();
    
            // Yellow box with recipe
            if (command.equals("examine yellow box")) {
                System.out.println("There is a tag with some words on the box, but it was blurred by water. You can faintly make out two words: Julia and cook. Do you want to open it? (yes/no)");
                if (input.nextLine().toLowerCase().equals("yes")) {
                    grab(ItemsGrab.RECIPE);
                    System.out.println("You got Julia Child's recipe!");
                } else {
                    System.out.println("OK. Do you want to check other box?");
                }
    
            // White box with laptop
            } else if (command.equals("examine white box")) {
                System.out.println("This box looks familiar. You seem to have seen it in a CSC class before. Do you want to open it? (yes/no)");
                if (input.nextLine().toLowerCase().equals("yes")) {
                    grab(ItemsGrab.LAPTOP);
                    System.out.println("You got Jordan's laptop!");
                } else {
                    System.out.println("OK. Do you want to check other box?");
                }
    
            // Blue box with book
            } else if (command.equals("examine blue box")) {
                System.out.println("The box is small and old. You’ve never seen it before. Do you want to open it? (yes/no)");
                if (input.nextLine().toLowerCase().equals("yes")) {
                    grab(ItemsGrab.BOOK);
                    System.out.println("You got a mysterious book!");
                } else {
                    System.out.println("OK. Do you want to check other box?");
                }
    
            // Silver box with tool
            } else if (command.equals("examine silver box")) {
                System.out.println("The box is big and heavy. Do you want to open it? (yes/no)");
                if (input.nextLine().toLowerCase().equals("yes")) {
                    grab(ItemsGrab.TOOL);
                    System.out.println("You got a Cross tool!");
                } else {
                    System.out.println("OK. Do you want to check other box?");
                }
    
            // Leave library
            } else if (command.equals("leave")) {
                System.out.println("You left the library.");
                exploring = false; // End the loop
    
            // catch error
            } else {
                System.out.println("Unknown command. Do you want to leave?");
            }
        }
    }
    
    
    
    // interact with raccoon
    public void interactWithRaccoon(Raccoon raccoon) {
        if (this.getLocationRow() == raccoon.getLocationRow() && this.getLocationColumn() == raccoon.getLocationColumn()) {
            System.out.println("You encounter a rabbit raccoon. It looks at you with pleading face and says, 'I've lost my way to home. Can you take me to the forest?'");
    
            if (input.nextLine().toLowerCase().equals("yes")) {
                raccoon.onBoard(this); // Raccoon follows the player
                System.out.println("You agreed to help the raccoon. Lead it to the forest!");
            } else {
                System.out.println("The raccoon looks disappointed and stays put.");
            }
        }
    }
    
    public void checkLocationForRaccoon(Raccoon raccoon) {
        if (this.getPlace() == Place.FOREST && !raccoon.getAtHome()) {
            raccoon.giveApple(this); // Trigger the raccoon giving the apple
            System.out.println("The raccoon thanks to you and gives you an apple!");
        }
    }
    
    
    
    
}