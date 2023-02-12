import java.util.Random;

/**
 * 
 * Name: Simar
 * Program: This program simulates a forest
 * 
 * t/green = tree
 * b/red = burning
 * x/black = empty
 *
 */

public class Simulator {
	final static int GRID_SIZE = 7; // Size of grid
	final static char[][] grid = new char[GRID_SIZE][GRID_SIZE]; // Displayed grid
	final static char[][] tempGrid = new char[GRID_SIZE][GRID_SIZE]; // Grid that holds changes
	final static int f = 10; // Burning probability 
	final static int p = 20; // Seeding probability 
	final static int fps = 1000; // Frames per second
	
	static final String ANSI_RED_BACKGROUND = "\u001B[41m"; // Red
	static final String ANSI_GREEN_BACKGROUND = "\u001B[42m"; // Green
	static final String ANSI_BLACK_BACKGROUND = "\u001B[40m"; // Black


	public static void main(String[] args) throws InterruptedException { // Exception for Thread.sleep
		driver();
	}
	
	/************************ Driver ************************/
	static void driver() throws InterruptedException {
		generateInitalGrid(); // Starts with a blank grid of x's
		
		while(true) {
            Thread.sleep(fps); // Delay
			displayGrid(); // Display the grid
			checkGrid(); // Add changes to the temporary grid
			updateGrid(); // Convert temporary grid to main grid
		}
	}

	/************************ Check each square ************************/
	static void checkGrid() {
		// Doesn't check the borders
		for (int i = 1; i < GRID_SIZE - 1; i++) {
			for (int j = 1; j < GRID_SIZE - 1; j++) {
				if (grid[i][j] == 'b') tempGrid[i][j] = 'x'; // If square is burning make it empty
				else if (grid[i][j] == 'x') tempGrid[i][j] = seedProbability(); // If square is empty give it a change to grow a tree
				else if (allNeighbourBurning(i, j)) tempGrid[i][j] = 'b'; // If square is a tree and touching a burning square, make tree burn
				else tempGrid[i][j] = burnProbability(); // Any tree can randomly burn
			}
		}
		
	}
	
	/************************ probability of empty space seeding ************************/
	static char seedProbability() {
		Random rand = new Random();
		if (rand.nextInt(100) < p) { // Check to see if random number is less than the probability set, if so make it a tree
			return 't';
		} else {
			return 'x';
		}
	}
	
	
	/************************ probability of empty space burning ************************/
	static char burnProbability() {
		Random rand = new Random();
		if (rand.nextInt(100) < f) { // Check to see if random number is less than the probability set, if so make it burn
			return 'b';
		} else {
			return 't';
		}
	}
	
	/************************ check if any neighbours burning ************************/
	static boolean allNeighbourBurning(int i, int j) {
		
		// Check top left
		if (grid[i - 1][j - 1] == 'b') return true;
		
		// Check top
		if (grid[i - 1][j] == 'b') return true;
		
		// Check top right
		if (grid[i - 1][j + 1] == 'b') return true;		
		
		// Check left
		if (grid[i][j - 1] == 'b') return true;
		
		// Check right
		if (grid[i][j + 1] == 'b') return true;
		
		// Check bottom left
		if (grid[i + 1][j - 1] == 'b') return true;		
		
		// Check bottom
		if (grid[i + 1][j] == 'b') return true;		
		
		// Check bottom right
		if (grid[i + 1][j + 1] == 'b') return true;
		
		return false;
	}
	
	
	/************************ update grid ************************/
	static void updateGrid() {
		for (int i = 1; i < GRID_SIZE - 1; i++) {
			for (int j = 1; j < GRID_SIZE - 1; j++) {
				grid[i][j] = tempGrid[i][j]; // Transfer temporary values to main grid, excluding the borders
			}
		}
	}
	
	
	/************************ display grid ************************/
	static void displayGrid() {
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				if (grid[i][j] == 't') {
					System.out.print(ANSI_GREEN_BACKGROUND + grid[i][j] + " "); // Print green for a tree
				} else if (grid[i][j] == 'b') {
					System.out.print(ANSI_RED_BACKGROUND + grid[i][j] + " "); // Print red for a burning square
				} else {
					System.out.print(ANSI_BLACK_BACKGROUND + grid[i][j] + " "); // Print black for an empty square
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/************************ generate empty grid ************************/
	static void generateInitalGrid() {
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				grid[i][j] = 'x'; // Create an empty grid to start simulation
			}
		}
	}
	
	
}
