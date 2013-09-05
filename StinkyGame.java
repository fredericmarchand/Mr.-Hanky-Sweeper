/**Assignment#2: StinkyGame
 *Name: Frédéric Marchand
 *Student Number: 100817579
 **/
import java.util.Date;

class StinkyGame{
	private GameBoard board; // represents the Game Board
	private long startTime; // time that user started the board
	private long endTime; // time that user completed the board
	private int bonus = 0; // number of bonus points awarded
	private int score; // total score
	private int flips;//number of remaining flips at the beginning of the game
	private int unflipped;//score so far

	StinkyGame(){
		board = new GameBoard();
	}

	public long getDate(){
		return startTime;
	}

	// Reset the GameBoard and the start time.
	public void startNextBoard() {
		board.reset();//reset the board
		flips = board.remainingFlips();//flips is equal to the number of remaining flips
		startTime = new Date().getTime();
	}

	// Called by the user interface in order to uncover the location
	// at the given row & column in the board
	public void handleSelection(int row, int col) {
		if(board.coveredAt(row, col))//if the board is covered at the location row, col
			board.uncover(row, col);//uncover the location row, col
		unflipped = (flips - board.remainingFlips())*10;//unflipped is equal to the current score
	}

	// Assuming the user just completed a board, return the time (in
	// seconds) that it took for the user to complete that board.
	// Recall that you can get the current time of day as a long number
	// of milliseconds by using new Date().getTime()
	public int getCompletionTime() {
		endTime = (((new Date().getTime()) - (startTime))/1000);
		return((int)endTime);
	}

	// Compute the final score of the game as described in part (1)
	public void finalScore() {
		int p1;
		p1 = board.remainingFlips();//p1 is equal to the number of remaining flips
		score = (((flips - p1)*10) + getBonus());//the score is equal to the number total flips*10 + the bonus
	}

	public int getScore(){ return score; }//get method for the score
	public int getUnflipped(){ return unflipped; }//get method for the score without bonus
	public GameBoard getBoard(){ return board; }//get method for the gameboard
	public int getBonus(){ //get method for the bonus
		if(120-getCompletionTime() > 0)//if 120- the number of seconds it took to complete the game if greater than 0
			bonus = ((120-getCompletionTime())*10);
		return bonus;
	}


}