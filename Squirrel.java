public class Squirrel extends Character {
    private int nAcorn = 0;
    private boolean alreadyOpen = false;

    public Squirrel(String name, boolean canOnboard, int locationRow, int locationColumn) {
        super(name, canOnboard, locationRow, locationColumn);
    }

    public int getNAcorn() {
        return nAcorn;
    }

    public boolean getAlreadyOpen() {
        return alreadyOpen;
    }

    public static void main(String[] args) {
        Squirrel squirrelFamily = new Squirrel("squirrel family", false, 4, 7);
        squirrelFamily.getNAcorn();
        squirrelFamily.getAlreadyOpen();
        // Call the methods using the instance and print the results
        // System.out.println("Number of Acorns: " + squirrelFamily.getNAcorn());
        // System.out.println("Already Open: " + squirrelFamily.getAlreadyOpen());
    }
}
