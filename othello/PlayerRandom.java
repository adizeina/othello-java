package ca.utoronto.utm.assignment1.othello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList; 
import java.util.Random;

/**
 * PlayerRandom makes a move by first determining all possible moves that this
 * player can make, putting them in an ArrayList, and then randomly choosing one
 * of them.
 * 
 * @author arnold
 *
 */
public class PlayerRandom{    
	private OthelloBoard board = new OthelloBoard(DIMENSION);  
	protected static final int DIMENSION = 8; 
	protected Random rand = new Random();  // I am using protected because this allows freedom to user to extend classes
	

	private Othello othello;   
	private char player;

	public PlayerRandom(Othello othello, char player) {
		
		this.othello = othello;
		this.player = player;
	}

	public Move getMove() {    
		ArrayList<Move> moves = new ArrayList<>();
		for (int row = 0; row < DIMENSION; row++) {
			for (int col =0; col < DIMENSION; col++) {
				for (int drow= -1; drow < 2; drow ++) {
					for (int dcol= -1; dcol < 2; dcol ++) {
						if ((board.hasMove()== player) || (board.hasMove()==OthelloBoard.BOTH)) {
							moves.get(row).add(col);      
						}}}}}
	int random = rand.nextInt(moves.size());
	int[] newMove =  moves.get(random);   //saving the move in an array to access it using class Move 
	
	int r = newMove.get(0);   //getting the row from the array for the BIGGEST flip for the player Random at index 0
	int c = newMove.get(1);   //getting the col from the array for the BIGGEST flip for the player Random at index 1
	r = getMove("row: ");      
	c = getMove("col: ");     
	return new Move(r, c);
}
