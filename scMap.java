import java.util.Random;

public class scMap {
    private Object[][] grid; // The 10x10 grid
    private static boolean[] acorn; // The boolean array
        
        // Constructor to initialize the map and Acorn array
        // object 2D array, acorn 1D array. Index in Acorn = row * 10 + column
    
        public scMap() {
            grid = new Object[10][10];
            acorn = new boolean[100]; // One boolean per grid cell
    
            // Pre-fill the grid with some objects (for demonstration purposes)
            setupGrid();
    
            // Randomly assign 20 true values in the Acorn array
            setupAcorn();
        }
    
        private void setupGrid() {
            grid[1][1] = "Tree";
            grid[2][1] = "Tree";
            grid[5][2] = "Bush";
            grid[5][3] = "Bush";
            grid[4][5] = "Trunk from Cracked Tree";
            grid[5][5] = "Trunk from Cracked Tree";
            grid[6][5] = "Trunk from Cracked Tree";
            grid[7][5] = "Trunk from Cracked Tree";
        }
    
        // Method to set up the Acorn array with 20 true values
        private void setupAcorn() {
            Random random = new Random();
            int count = 0;
    
            while (count < 20) {
                int index = random.nextInt(100); // Random index between 0 and 99
                int row = index / 10;
                int col = index % 10;
                if (grid[row][col] == null && !acorn[index]) { // Only set if it is currently false
                    acorn[index] = true;
                    count++;
                }
            }
        }
    
        public static boolean hasAcorn(int index) {
            return acorn[index];
    }

    public static void removeAcorn(int index) {
        acorn[index] = false;
    }


    // Method to print the grid and the Acorn array (for testing purposes)
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
            if ((i + 1) % 10 == 0) System.out.println(); // New line every 10 values
        }
    }

    public static void main(String[] args) {
        scMap map = new scMap();
        map.displayMap();
    }
}
