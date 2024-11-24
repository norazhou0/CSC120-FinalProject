public class Squirrel extends Character {
    private int nAcorn = 0;
    private boolean alreadyOpen = false;

    public Squirrel(String name, boolean canOnboard, int locationRow, int locationColumn) {
        super(name, canOnboard, locationRow, locationColumn);
    }

    public static void main(String[] args) {
        Squirrel squirrelFamily = new Squirrel("squirrel family", false, 4, 7);
    }
}
