import java.util.ArrayList;
import java.util.Scanner;

public class GameLoop {

    public static void main(String[] args) {

        // Create a player instance
        Player myPlayer = new Player("player", true, 5, 4, null);
        Raccoon raccoon = new Raccoon("Raccoon", true, 6, 2); 

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
        System.out.println("Enter READY to start, anything else to exit.");

        // Check if the player is ready
        userResponse = userInput.nextLine().toUpperCase();
        if (!userResponse.equalsIgnoreCase("READY")) {
            stillPlaying = false;
            System.out.println("Goodbye! Come back soon!");
        } else {System.out.println("You're in the SMITH ADVENTURE.");
        }

        // Main game loop
        while (stillPlaying && myPlayer.isAlive()) {
            System.out.println("Enter a command (examine <item>, grab <item>, drop <item>, move <north/south/west/east>, quit):");
            userResponse = userInput.nextLine().toUpperCase();
            ItemsExamine item;
            if (userResponse.startsWith("EXAMINE")) {
                String[] seperatedResponse = userResponse.split(" ");
                if (seperatedResponse.length <= 1); {
                    
                }
                seperatedResponse[1] = 


                
                
                userResponse.toUpperCase().contains(ItemsExamine.valueOf(null, userResponse));
                myPlayer.examine(item);
                }
            } else if (userResponse.startsWith("GRAB")) {
                ItemsGrab item;
                item = ItemsGrab.valueOf(item);
                myPlayer.grab(item);
                }
            } else if (userResponse.startsWith("DROP")) {
                ItemsGrab item;
                    item = ItemsGrab.valueOf(itemName);
                }
                myPlayer.drop(item);

            } else if (userResponse.startsWith("MOVE")) {
                myPlayer.move(userResponse); 
            } else if (userResponse.equalsIgnoreCase("QUIT")) {
                stillPlaying = false;
                System.out.println("Exiting the game. Goodbye!");
            } else {
                System.out.println("Unknown command. Please try again.");
            }
        }
        

        // Close the sanner
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