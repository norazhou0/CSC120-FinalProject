public class Raccoon extends Character {
    private boolean haveApple = true; // Raccoon starts with an apple
    private boolean atHome = false; // Raccoon initially not in the forest


    /**
     * Constructor for Raccoon
     * @param name the name of the raccoon
     * @param canOnboard whether the racoon can board the kayak
     * @param locationRow the row on the grid the raccoon is in
     * @param locationColumn the column on the grid the raccoon is in
     */
    public Raccoon(String name, boolean canOnboard, int locationRow, int locationColumn) {
        super(name, canOnboard, locationRow, locationColumn);
    }

    /* Accessors */
    public boolean getHaveApple() {
        return haveApple;
    }

    public boolean getAtHome() {
        return atHome;
    }
    
    /** 
     * The method that allows the raccoon to board
     * @param player the user
     */
    public void onBoard(Player player) {
        // Set the postion of the raccoon to be equal to the player's
        this.locationRow = player.getLocationRow();
        this.locationColumn = player.getLocationColumn();
        System.out.println("The raccoon is following you!");
    }

    /** 
     * The method that allows the raccoon to give the apple to the player
     * @param player the user
     */
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
