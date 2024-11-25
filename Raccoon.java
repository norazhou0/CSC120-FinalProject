public class Raccoon extends Character {
    private boolean haveApple = false;
    private boolean atHome = false;

    public Raccoon(String name, boolean canOnboard, int locationRow, int locationColumn) {
        super(name, canOnboard, locationRow, locationColumn);
    }

    public boolean getHaveApplec() {
        return haveApple;
    }

    public boolean getAtHome() {
        return atHome;
    }

    public void onBoard(Player player) {
        this.locationColumn = player.locationColumn;
        this.locationRow = player.locationRow;
        System.out.println("Raccoon is onboard!");
    }

    public void giveApple(Player player) {
        if (player.getPlace() == Place.FOREST) {
            haveApple = false;
        }
    }



    public static void main(String[] args) {
        Raccoon raccoon = new Raccoon("rabbit raccoon", true, 6, 2);
        raccoon.getHaveApplec();
        raccoon.getAtHome();
    }

}