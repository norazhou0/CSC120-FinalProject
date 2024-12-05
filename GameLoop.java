import java.util.Scanner;

public class GameLoop {

    public static void main(String[] args) {

        // Create a player instance
        Player myPlayer = new Player("Alice", true, 0, 0, "NORTH");

        // This is a "flag" to let us know when the loop should end
        boolean stillPlaying = true;

        // We'll use this to get input from the user.
        Scanner userInput = new Scanner(System.in);

        // Storage for user's responses
        String userResponse = "";

        // This could be replaced with a more interesting opening
        System.out.println("******************");
        System.out.println("WELCOME TO SMITH ADVENTURE");
        System.out.println("******************");

        // Instructions are sometimes helpful
        System.out.println("Enter READY to start, anything else to exit.");

        // The do...while structure means we execute the body of the loop once before checking the stopping condition
        do {
            // ************************************************
            // The stuff that happens in your game will go here
            //  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓
            System.out.println("You are in the SMITH ADVENTURE. Good luck!");
            userResponse = userInput.nextLine().toUpperCase();

            // ***********************************************************************
            // And as the player interacts, you'll check to see if the game should end
            //  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓
            if (!userResponse.equalsIgnoreCase("READY")) {
                stillPlaying = false;
            }
        } while (stillPlaying);

        // Tidy up
        userInput.close();

        // Once you exit the loop, you may need to deal with various possible stopping conditions
        if (myPlayer.getPlace() == Place.STATUE &&
            myPlayer.getBackpack().containsKey("Apple") && 
            myPlayer.getBackpack().containsKey("Dress") && 
            myPlayer.getBackpack().containsKey("Recipe")) {
            System.out.println("Yay, you saved the Smith Campus! Well done!");
        } else { // userResponse.equals("LOSE")
            System.out.println("Hope you see you again soon!");
        }

    }

}