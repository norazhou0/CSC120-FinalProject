import java.util.Hashtable;
import java.util.Scanner;

public class Player extends Character {
    private Hashtable<String, Integer> backpack; //String: item name; Integer: item count
    private Place place = Place.GRID;
    private boolean canEnterLibrary = false;
    private boolean alive = true;
    private boolean win = false;
    private Scanner input;
    private scMap map;

    /**
     * Constructor for Player
     * 
     * @param name           the name of the player
     * @param canOnboard     whether the player can board the kayak
     * @param locationRow    the row on the grid the player is in
     * @param locationColumn the column on the grid the player is in
     * @param input          the input of the player
     * @param map            the map the player is on
     */

    public Player(String name, boolean canOnboard, int locationRow, int locationColumn, Scanner input, scMap map) {
        super(name, canOnboard, locationRow, locationColumn);
        this.map = map; // Assign the map
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

    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * The method that allows the player to examine items
     * 
     * @param input for examining items
     */
    public void examine(String input) {
        if (input.contains("acorn")) {
            System.out.println("An acorn. Squirrels might like it.");
        } else if (input.contains("dress")) {
            System.out.println(
                    "A white dress from Ivy Day. It contains valuable memories of Smithies, which might be the key to save Smith");
        } else if (input.contains("laptop")) {
            System.out.println("Jordan's laptop. The wallpaper is of his dog, Charlie.");
        } else if (input.contains("apple")) {
            System.out.println("An apple from Mountain Day. This might be the key to save Smith.");
        } else if (input.contains("recipe")) {
            System.out.println(
                    "A recipe from Julia Child. Keep it safe — Smithies 100 years later will still need it for Julia Child Day!");
        } else if (input.contains("tool")) {
            System.out.println(
                    "Cross. A 19th century silver craft originally stored in SCMA. It's 30 cm tall. Hummm...That's pretty long!");
        } else if (input.contains("oak")) {
            System.out.println("There is a white dress on the tree. However, you are not tall enough to reach it.");
        } else {
            System.out.println("Invalid! Cannot examine " + input);
        }
    }

    /**
     * The method that allows the player to grab items
     * 
     * @param input for grabbing items
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

        // Check if the item is already in the backpack (avoid grabbing repetitively)
        if (backpack.containsKey(input)) {
            System.out.println("You already have the " + input + " in your backpack!");
            return; // Exit the method, no further action needed
        }

        int totalNumItem = 0; // Track total items in the backpack
        for (int quantity : backpack.values()) {
            totalNumItem += quantity;
        }

        // Check backpack capacity
        if (totalNumItem < 10) {
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
     * @param input for dropping items
     */
    public void drop(String input) {
        input = input.toLowerCase();
        // Check if the item exists in the backpack and has a count greater than 0
        if (backpack.containsKey(input) && backpack.get(input) > 0) {
            if (backpack.get(input) > 1) {
                // Decrease the count by 1
                backpack.put(input, backpack.get(input) - 1);
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
     * 
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
            this.place = Place.WATERFALL;
        } else if (this.getLocationRow() > 9) {
            this.place = Place.FOREST;
        } else if (this.getLocationColumn() < 0) {
            if (canEnterLibrary) {
                this.place = Place.LIBRARY;
            } else {
                this.place = Place.LIBRARY;
                System.out.println("Sorry, you cannot enter the library yet!");
            }
        } else if (this.getLocationColumn() > 9) {
            this.place = Place.STATUE;
        }
    }

    /**
     * The method that allows the player to access library
     */
    public void accessLibrary() {
        if (!backpack.containsKey("acorn")) {
            System.out.println("Squirrel Mama: Sorry, you don't have the food we want :(");
        } else {
            if (backpack.get("acorn") >= 5) {
                backpack.put("acorn", backpack.get("acorn") - 5);
                this.canEnterLibrary = true;
                System.out.println(
                        "Squirrel Baby: Thank you so much! We will take 5 acorns! Do you want to go to the library? We will help you with that.");
                System.out.println(
                        "Squirrel Mama runs to the library and says: Now you can enter the library.");
            } else {
                System.out.println(
                        "Squirrel Papa: Sorry, we are a big family, and we need more acorns. Please find 5 acorns and then come back to us!");
            }
        }
    }

    /**
     * The method that handles the waterfall scenario
     */
    public void handleWaterfall() {
        System.out.println("You fell into a waterfall!");
        // Generate a random number between 0 and 1
        if (Math.random() < 0.5) {
            // 50% chance of dying
            this.setAlive(false);
            System.out.println("You didn't survive the waterfall... Game over.");
        } else {
            // Set player location
            setLocationColumn(0);
            setLocationRow(0);
            System.out.println("You survived the waterfall! Waterfall is dangerous and unpredictable.");
            this.place = Place.GRID;
        }
    }

    /**
     * The method that handles the statue scenario
     */
    public void handleStatue() {
        System.out.println(
                "It's the Lanning Fountain in front of the Burton! LOOK!!! The statue is talking and it's glowing.");
        System.out.println(
                "Statue: You must be the one who can save Smith College. I can help you with that. However, I need three things to release my power.");
        System.out.println(
                "A dress from Ivy Day. An apple from Mountain Day. And a recipe from Julia Child. Find them and bring them to me!");
        if (getBackpack().containsKey("dress") &&
                getBackpack().containsKey("apple") &&
                getBackpack().containsKey("recipe")) {
            this.win = true;
            System.out.println("Yay, you saved the Smith Campus!");
        } else {
            System.out.println(
                    "Statue: Sorry! You don't have all the things I need to save Smith! Please collect all of them and find me again.");
            // Set player location
            setLocationColumn(9);
            setLocationRow(9);
            this.place = Place.GRID;
        }
    }

}