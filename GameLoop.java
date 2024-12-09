import java.util.Scanner;

public class GameLoop {

    public static void main(String[] args) {

        // Create a player instance
        Player myPlayer = new Player("player", true, 5, 4, null);
        scMap map = new scMap();
        Raccoon raccoon = new Raccoon("Raccoon", true, 6, 6);

        // This is a "flag" to let us know when the loop should end
        boolean stillPlaying = true;

        // We'll use this to get input from the user.
        Scanner userInput = new Scanner(System.in);

        // Storage for user's responses
        String userResponse = "";

        // Game opening
        System.out.println("******************");
        System.out.println("WELCOME TO SMITH ADVENTURE");
        System.out.println("******************");
        System.out.println("Enter ready to start, anything else to exit.");

        // Check if the player is ready
        userResponse = userInput.nextLine().toLowerCase();
        if (!userResponse.equalsIgnoreCase("ready")) {
            stillPlaying = false;
            System.out.println("Goodbye! Come back soon!");
        } else {
            System.out.println("You're in the SMITH ADVENTURE.");
        }

        // Main game loop
        while (stillPlaying && myPlayer.isAlive()) {
            System.out.println(
                    "Please enter a command (examine <item>, drop <item>, move <north/south/west/east>, quit):");
            userResponse = userInput.nextLine().toLowerCase();

            if (userResponse.startsWith("examine")) {
                // Create an array to store the separated response
                String[] separatedExamine = userResponse.split(" ");
                if (separatedExamine.length == 2) {
                    // Access the item to be examined
                    myPlayer.examine(separatedExamine[1]);
                } else {
                    System.out.println("You must include 'examine' and a valid object.");
                }
            } else if (userResponse.startsWith("drop")) {
                String[] separatedDrop = userResponse.split(" ");
                if (separatedDrop.length == 2) {
                    // Access the item to be dropped
                    myPlayer.drop(separatedDrop[1]);
                } else {
                    System.out.println("You must include 'drop' and a valid object.");
                }
            } else if (userResponse.startsWith("move")) {
                String[] separatedMove = userResponse.split(" ");
                if (separatedMove.length == 2) {
                    // Access the direciton to move
                    myPlayer.move(separatedMove[1]);
                    System.out.println(myPlayer.getLocationColumn());
                    System.out.println(myPlayer.getLocationRow());
                } else {
                    System.out.println("You must include 'move' and a valid direction.");
                }
            } else if (userResponse.equalsIgnoreCase("quit")) {
                stillPlaying = false;
                System.out.println("Exiting the game. Goodbye!");
            } else {
                System.out.println("Unknown command. Please try again.");
            }
        }

        // Close the sanner
        userInput.close();

        // Exit the game when the player wins
        if (myPlayer.getWin()) {
            stillPlaying = false;
            System.out.println("Well done! Bye~");
        }

    }
}