import java.util.Scanner;

public class GameLoop {

    public static void main(String[] args) {

        // Create a player instance
        Player myPlayer = new Player("Nora", true, 5, 4, null);
        Raccoon raccoon = new Raccoon("Raccoon", true, 6, 2); 

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
        if (myPlayer.getPlace() == Place.STATUE) {
            System.out.println("It's the Lanning Fountain in front of the Burton! LOOK!!! The statue is talking and it's glowing.");
            System.out.println("Statue: You must be the one who can save Smith College. I can help you with that. However, I need three things to release my power.");
            System.out.println("A dress from Ivy Day. An apple from Mountain Day. And a recipe from Julia Child. Find them and bring them to me!");
            if (myPlayer.getBackpack().containsKey("APPLE") && 
            myPlayer.getBackpack().containsKey("DRESS") && 
            myPlayer.getBackpack().containsKey("RECIPE")) {
                System.out.println("Yay, you saved the Smith Campus! Well done!");
            } else {
                System.out.println("Statue: Sorry! You don't have all the things I need to save smith! Please collect all of them and find me again.");
            }
    }
}
}