/**Assignment#2: GameBoard
 *Name: Frédéric Marchand
 *Student Number: 100817579
 **/
import java.util.ArrayList;

public class GameBoard {
	public static int ROWS = 10;
	public static int COLUMNS = 10;
	private byte[][] stinkies; //keeps locations of all stinkies
	private boolean[][] covered; //keeps covered status of all locations
	private int numStinkies;

	// Constructor for a blank board
	public GameBoard() {
		stinkies = new byte[ROWS][COLUMNS];
		covered = new boolean[ROWS][COLUMNS];
		reset();
	}

	// Place the specified number of stinkies (i.e., num) on the board at
	// random locations.
	public void placeStinkies(int num) {
		double 	random1, random2;
		int		row, col;
		for(int i=0; i<num; i++){
			random1 = Math.random()*ROWS;
			random2 = Math.random()*COLUMNS;
			row = (int)random1;
			col = (int)random2;
			if(stinkies[row][col] == 0){
				stinkies[row][col] = 1;
				numStinkies++;
			}
			else
				i--;
		}
	}

	// Reset the board with no stinkies
	public void reset() {
		for (int r=0; r<ROWS; r++)
			for (int c=0; c<COLUMNS; c++) {
				stinkies[r][c] = 0;
				covered[r][c] = true;
			}
		placeStinkies(10);
	}

	// Determine whether or not there is a stinky at the given location
	public boolean stinkyAt(int r, int c) {
		return stinkies[r][c] == 1;
	}

	// Determine whether or not the given location is still covered
	public boolean coveredAt(int r, int c) {
		return covered[r][c];
	}

	// Uncover the given location and return true if a stinky was there, else false
	public boolean uncover(int r, int c) {
		boolean d = true;
		// If uncovered a stinky ... game over, so uncover all locations
		// and return true.
		// ... FILL IN MISSING CODE ...
		// Uncover this location. If a 0-strength location was
		// uncovered, uncover all adjacent zeroes in the area.
		if(stinkies[r][c] == -1){
			for (int a=0; a<ROWS; a++){
				for (int b=0; b<COLUMNS; b++){
					covered[a][b] = false;
				}
			}
		}
		else if(stinkies[r][c] == 0){
			uncoverZerosFrom(r, c);
			d = false;
		}
		return d;
	}
	// This method is a recursive method (more on this later) that
	// fills in all zeroes connected to the given row and column.
	// Do not spend too much time trying to understand this method.
	private void uncoverZerosFrom(int r, int c) {
		if (!covered[r][c]) return;
		covered[r][c] = false;
		if (smellStrengthAt(r,c) > 0) return;
		if (r != 0) {
			if (stinkies[r-1][c] == 0) uncoverZerosFrom(r-1,c);
			if (c != 0)
				if (stinkies[r-1][c-1] == 0) uncoverZerosFrom(r-1,c-1);
			if (c != COLUMNS-1)
				if (stinkies[r-1][c+1] == 0) uncoverZerosFrom(r-1,c+1);
		}
		if (r != ROWS-1) {
			if (stinkies[r+1][c] == 0) uncoverZerosFrom(r+1,c);
			if (c != 0)
				if (stinkies[r+1][c-1] == 0) uncoverZerosFrom(r+1,c-1);
			if (c != COLUMNS-1)
				if (stinkies[r+1][c+1] == 0) uncoverZerosFrom(r+1,c+1);
		}
		if (c != 0)
			if (stinkies[r][c-1] == 0) uncoverZerosFrom(r,c-1);
		if (c != COLUMNS-1)
			if (stinkies[r][c+1] == 0) uncoverZerosFrom(r,c+1);
	}

	// Return the number of flips (i.e., board locations that need to be uncovered)
	// that need to be made to complete the board.
	public int remainingFlips() {
		int num = 0;
		for (int r=0; r<ROWS; r++){
			for (int c=0; c<COLUMNS; c++){
				if(covered[r][c] == true)
					num++;
			}
		}
		return num;
	}

	// Return the "smell" at the given location. The smell is a number indicating
	// the number of stinkies surrounding (but not including) this location
	public int smellStrengthAt(int r, int c) {
		int strength = 0;
		if (r != 0) {
			strength += stinkies[r-1][c];
			if (c != 0) strength += stinkies[r-1][c-1];
			if (c != COLUMNS-1) strength += stinkies[r-1][c+1];
		}
		if (r != ROWS-1) {
			strength += stinkies[r+1][c];
			if (c != 0) strength += stinkies[r+1][c-1];
			if (c != COLUMNS-1) strength += stinkies[r+1][c+1];
		}
		if (c != 0)
			strength += stinkies[r][c-1];
		if (c != COLUMNS-1)
			strength += stinkies[r][c+1];
		return strength;
	}
}