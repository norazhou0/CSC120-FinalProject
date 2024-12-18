import java.util.Random;

/**
 * Represents a 10x10 grid map with boolean flags (Acorn array) indicating specific states
 * in each grid cell. Provides functionality to initialize and manage the grid and Acorn array.
 */
public class scMap {
    /**
     * A 10x10 grid of generic objects representing the map.
     */
    private Object[][] grid;

    /**
     * A boolean array where each index corresponds to a grid cell.
     * True indicates the presence of an acorn, and false indicates its absence.
     * Index calculation: row * 10 + column.
     */
    private static boolean[] acorn;

    /**
     * Constructor to initialize the grid and Acorn array.
     * The grid is a 10x10 array of objects, and the Acorn array is a flat
     * 1D array of booleans with 100 elements. The constructor also calls the
     * setupAcorn() method to randomly place 20 acorns in the Acorn array.
     */
    public scMap() {
        grid = new Object[10][10];
        acorn = new boolean[100]; // One boolean per grid cell

        // Randomly assign 20 true values in the Acorn array
        setupAcorn();
    }

    /**
     * Randomly assigns 20 true values in the Acorn array to represent
     * the presence of acorns in the grid. Ensures that the cell at row 4,
     * column 7 is excluded and that no cell is assigned more than once.
     */
    void setupAcorn() {
        Random random = new Random();
        int count = 0;

        while (count < 20) {
            int index = random.nextInt(100); // Random index between 0 and 99
            int row = index / 10; // Calculate the row from the index
            int col = index % 10; // Calculate the column from the index

            // Skip cell at row 4, column 7
            if (row == 4 && col == 7) {
                continue;
            }

            // Assign true only if the cell is not already assigned
            if (grid[row][col] == null && !acorn[index]) {
                acorn[index] = true;
                count++;
            }
        }
    }

    /**
     * Checks whether an acorn is present at a specific index in the Acorn array.
     *
     * @param index The index to check (0 to 99).
     * @return true if an acorn is present at the specified index,
     *         false otherwise.
     */
    public boolean hasAcorn(int index) {
        return acorn[index];
    }

    /**
     * Removes an acorn from the specified index in the Acorn array.
     *
     * @param index The index from which to remove the acorn (0 to 99).
     */
    public void removeAcorn(int index) {
        acorn[index] = false;
    }

    /**
     * Prints the current state of the grid and the Acorn array.
     * For the grid, displays "." for empty cells and the object in non-empty cells.
     * For the Acorn array, displays "1" for cells with acorns and "0" for empty cells.
     * The Acorn array output is formatted with 10 values per line.
     */
    public void displayMap() {
        System.out.println("Grid:");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print((grid[i][j] == null ? "." : grid[i][j]) + "\t");
            }
            System.out.println();
        }

        System.out.println("\nAcorn Array:");
        for (int i = 0; i < acorn.length; i++) {
            System.out.print(acorn[i] ? "1 " : "0 ");
            if ((i + 1) % 10 == 0) // New line every 10 values
                System.out.println();
        }
    }
}
