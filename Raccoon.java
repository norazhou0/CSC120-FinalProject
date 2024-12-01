public class Raccoon extends Character {
    private boolean haveApple = true; // Raccoon starts with an apple
    private boolean atHome = false; // Raccoon initially not in the forest

    public Raccoon(String name, boolean canOnboard, int locationRow, int locationColumn) {
        super(name, canOnboard, locationRow, locationColumn);
    }

    public boolean getHaveApple() {
        return haveApple;
    }

    public boolean getAtHome() {
        return atHome;
    }

    public void onBoard(Player player) {
        // Move raccoon to the player's position
        this.locationRow = player.getLocationRow();
        this.locationColumn = player.getLocationColumn();
        System.out.println("The raccoon is following you!");
    }

    public void giveApple(Player player) {
        if (player.getPlace() == Place.FOREST && haveApple) {
            player.grab(ItemsGrab.APPLE); // Add apple to player's backpack
            System.out.println("The raccoon gave you a Mountain Day apple!");
            haveApple = false; // Raccoon no longer has the apple
            atHome = true; // Raccoon has reached its home
        } else if (!haveApple) {
            System.out.println("The raccoon has already given you its apple.");
        }
    }
}
