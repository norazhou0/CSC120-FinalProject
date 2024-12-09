
public class Character {
  protected String name;
  protected Boolean canOnboard;
  protected int locationRow;
  protected int locationColumn;

  /**
   * Constructor for Character
   * 
   * @param name           of the character
   * @param canOnboard     whether the character can board the kayak
   * @param locationRow    the row on the grid the character is in
   * @param locationColumn the row on the grid the character is in
   */
  public Character(String name, boolean canOnboard, int locationRow, int locationColumn) {
    this.name = name;
    this.canOnboard = canOnboard;
    this.locationRow = locationRow;
    this.locationColumn = locationColumn;
  }

  /* Accessors */
  public String getName() {
    return this.name;
  }

  public Boolean getCanOnboard() {
    return this.canOnboard;
  }

  public int getLocationRow() {
    return this.locationRow;
  }

  public int getLocationColumn() {
    return this.locationColumn;
  }

  public static void main(String[] args) {
    Character character = new Character("Alice", true, 2, 3);
    System.out.println("Name: " + character.getName());
    System.out.println("Can onboard: " + character.getCanOnboard());
    System.out.println("Location: (" + character.getLocationRow() + ", " + character.getLocationColumn() + ")");
  }

}