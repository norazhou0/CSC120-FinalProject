import java.util.Hashtable;
import java.util.Scanner;

public class Player extends Character {
    private Hashtable<String, Integer> backpack;
    private Place place = Place.GRID;
    private boolean canFly = false;
    private boolean canEnterLibrary = false;
    private boolean alive = true;
    String direction;
    Scanner input;

    /**
     * Constructor for Player
     * @param name the name of the player
     * @param canOnboard whether the player can board the kayak
     * @param locationRow the row on the grid the player is in
     * @param locationColumn the column on the grid the player is in
     */

    public Player(String name, boolean canOnboard, int locationRow, int locationColumn, String move) {
        super(name, canOnboard, locationRow, locationColumn);
        backpack = new Hashtable<>();
        this.input = new Scanner(System.in);
    }

    /* Accessors */
    public Hashtable<String, Integer> getBackpack() {
        return this.backpack;
    }

    public Place getPlace() {
        return this.place;
    }

    public boolean getCanFly() {
        return this.canFly;
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

    /* Methods */
    /* ACORN, APPLE, DRESS, RECIPE, BOX, TOOL, LAPTOP, BOOK, OKA */
    public void examine (ItemsExamine item){
        if (item.equals(ItemsExamine.ACORN)) {
            System.out.println("...");
        } else if (item.equals(ItemsExamine.DRESS)) {
            System.out.println("...");
        } else if (item.equals(ItemsExamine.BOX)) {
            System.out.println("...");
        } else if (item.equals(ItemsExamine.LAPTOP)) {
            System.out.println("...");
        } else if (item.equals(ItemsExamine.APPLE)) {
            System.out.println("...");
        } else if (item.equals(ItemsExamine.RECIPE)) {
            System.out.println("...");
        } else if (item.equals(ItemsExamine.TOOL)) {
            System.out.println("It's Cross. A 19the century silver craft originally stored in SCMA. It's 30 cm tall. Hummm...That's pretty long");
        } else if (item.equals(ItemsExamine.OKA)) {
            System.out.println("There is a white dress on the tree.However, you are not tall enough to reach it.");
        } else {
            System.out.println("It's just a/an" + item + ".");
        }
    }

    
    public void grab (ItemsGrab item) {
        int totalNumItem = 0;
        for (int quantity : this.backpack.values()) {
            totalNumItem += quantity;
        }
        if (totalNumItem < 20) {
            String itemName = item.name();
            backpack.put(itemName, 1);
        } else {
            this.setAlive(false);
            System.out.println("The backpack is overloaded!");
        }
    }

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
            System.out.println("Cannot drop the item.");
        }
    }

    public void setLocationColumn(int column) {
        this.locationColumn = column;
    }

    public void setLocationRow(int row) {
        this.locationColumn = row;
    }

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
            if (canEnterLibrary == true) { // Use '==' for comparison
                enterLibrary();
            } else {
                System.out.println("Sorry, you cannot enter the library yet!");
            }
        } else if (this.getLocationColumn() > 9) {
            interactWithStatue();
        }        
    }

    private void handleWaterfall() {
        System.out.println("You fell into a waterfall!");
        double chance = Math.random(); // Generate a random number between 0 and 1
        if (chance < 0.5) {
            this.setAlive(false); // 50% chance of dying
            System.out.println("You didn't survive the waterfall... Game over.");
        } else {
            this.canFly = true; // 50% chance of learning to fly
            System.out.println("You survived the waterfall and learned how to fly!");
        }
    }
    
    private void handleForest() {
        System.out.println("You wandered into the forest. The air feels different here...");
        System.out.println("There looks to be something in the Oak.");
    
        Scanner scanner = new Scanner(System.in);
        boolean exploring = true; // Keep track of the exploration status
    
        while (exploring) {
            System.out.println("What would you like to do? (type 'examine oak' to inspect, 'leave' to exit)");
            String command = scanner.nextLine().toLowerCase();
    
            if (command.equals("examine oak")) {
                examine(ItemsExamine.OKA); // Use your existing examine method
    
                // Check if the player wants to try using a tool
                System.out.println("Do you want to try using a tool from your backpack to grab the dress? (yes/no)");
                String useToolResponse = scanner.nextLine().toLowerCase();
    
                if (useToolResponse.equals("yes")) {
                    System.out.println("Which tool would you like to use?");
                    String tool = scanner.nextLine();
    
                    // Check if the tool exists in the player's backpack
                    if (this.backpack.containsKey(tool) && this.backpack.get(tool) > 0) {
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
                exploring = false; // End the loop
            } else {
                System.out.println("Unknown command. Do you want to leave?");
            }
        }
    }
    

    // private void enterLibrary() {
    //     System.out.println("You entered the library. There are four mysterious boxes here.");
    //     Scanner scanner = new Scanner(System.in);
    
    //     for (ItemsGrab item : ItemsGrab.values()) {
    //         System.out.println("You see a " + item.name().toLowerCase() + ". Do you want to grab it? (yes/no)");
    //         String response = scanner.nextLine().toLowerCase();
    //         if (response.equals("yes")) {
    //             grab(item);
    //             System.out.println("You grabbed the " + item.name().toLowerCase() + "!");
    //         }
    //     }
    // }
    
    private void interactWithStatue() {
        System.out.println("You encountered a statue! It asks for a specific item from your backpack.");
        Scanner scanner = new Scanner(System.in);
        System.out.println("What will you give to the statue?");
        String itemToGive = scanner.nextLine();
    
        if (this.backpack.containsKey(itemToGive) && this.backpack.get(itemToGive) > 0) {
            drop(ItemsGrab.valueOf(itemToGive.toUpperCase())); // Give the item to the statue
            System.out.println("The statue accepted your offering of " + itemToGive + " and seems pleased."); //write different response when recieving different things
        } else {
            System.out.println("You don't have the item the statue is asking for!");
        }
    }
    

    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Player player = new Player("Jordan", true, 5, 4, "");
    
        while (player.isAlive()) {
            System.out.println("What's your next move? (e.g., 'move south')");
            String userInput = input.nextLine().toLowerCase();
    
            player.move(userInput);
    
            // Show the player's updated position
            System.out.println("You are now at row " + player.getLocationRow() +
                               ", column " + player.getLocationColumn());
        }
    
        System.out.println("Game over!");
        input.close();
    }
    
    
    
}