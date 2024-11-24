import java.util.Hashtable;


public class Player extends Character {
    private Hashtable<String, Integer> backpack;
    private Place place = Place.GRID;
    private boolean canFly = false;
    private boolean canEnterLibrary = false;
    private boolean alive = true;

    /**
     * Constructor for Player
     * @param name the name of the player
     * @param canOnboard whether the player can board the kayak
     * @param locationRow the row on the grid the player is in
     * @param locationColumn the column on the grid the player is in
     */

    public Player(String name, boolean canOnboard, int locationRow, int locationColumn) {
        super(name, canOnboard, locationRow, locationColumn);
        backpack = new Hashtable<>();
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

    public void examine (ItemsExamine item){
        if (item.equals(ItemsExamine.ACORN)) {
            System.out.println("...");
        } else if (item.equals(ItemsExamine.DRESS)) {
            System.out.println("...");
        } else if (item.equals(ItemsExamine.BOX)) {
            System.out.println("...");
        } else if (item.equals(ItemsExamine.LAPTOP)) {
            System.out.println("...");
        } else if (item.equals(ItemsExamine.STATUE)) {
            System.out.println("...");
        } else if (item.equals(ItemsExamine.LIBRARY)) {
            System.out.println("...");
        } else if (item.equals(ItemsExamine.FOREST)) {
            System.out.println("...");
        } else if (item.equals(ItemsExamine.WATERFALL)) {
            System.out.println("...");
        } else {
            System.out.println("It's just a/an" + item + ".");
        }
    }



    
}
