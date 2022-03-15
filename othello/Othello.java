package ca.utoronto.utm.assignment1.othello;

import java.util.Random;

/**
 * Testing out
 * Capture an Othello game. This includes an OthelloBoard as well as knowledge
 * of how many moves have been made, whosTurn is next (OthelloBoard.P1 or
 * OthelloBoard.P2). It knows how to make a move using the board and can tell
 * you statistics about the game, such as how many tokens P1 has and how many
 * tokens P2 has. It knows who the winner of the game is, and when the game is
 * over.
 * 
 * See the following for a short, simple introduction.
 * https://www.youtube.com/watch?v=Ol3Id7xYsY4
 * 
 * @author arnold
 *
 */
public class Othello {
	public static final int DIMENSION = 8; // This is an 8x8 game
	private char whosTurn = OthelloBoard.P1; // P1 moves first!
	private int numMoves = 0;
	protected static OthelloBoard board = new OthelloBoard(DIMENSION);  //try without private 

	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() {   //if its full return empty
		if (board.hasMove() == OthelloBoard.EMPTY){   //i want to use the private method here FIIIIIIXXXXXXXXXXXXXX
			return OthelloBoard.EMPTY;
		}
		return whosTurn; 
	}
	/** 
	 * Attempt to make a move for P1 or P2 (depending on whos turn it is) at
	 * position row, col. A side effect of this method is modification of whos turn
	 * and the move count.
	 * 
	 * @param row
	 * @param col
	 * @return whether the move was successfully made.
	 */
	public boolean move(int row, int col) {  //"fixing" to do here
		if (getWhosTurn()== OthelloBoard.P1) {  
			board.move(row, col, OthelloBoard.P1);
		} else if (getWhosTurn() == OthelloBoard.P2) {
			board.move(row, col, OthelloBoard.P2);
			
		}
		return true;
		  
	/**
	 * 
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
		
	public int static getCount(char player){
		return (board.getCount(player));

	}

	/**
	 * Returns the winner of the game.
	 * 
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
		int count1 = board.getCount(OthelloBoard.P1);
		int count2 = board.getCount(OthelloBoard.P2);
		if ((count1 + count2 != (DIMENSION * DIMENSION)) || (count1 == count2)){    //this is if the game is not finished 
			return OthelloBoard.EMPTY;   
		}
		if (count1 > count2) {
			return OthelloBoard.P1;
		}else if (count1 < count2 ) {
		return OthelloBoard.P2;
		}
	}
	
	/**
	 * 
	 * @return whether the game is over (no player can move next)
	 */
	public boolean isGameOver() {     
		if((OthelloBoard.P1 != board.hasMove()) || (OthelloBoard.P2 != board.hasMove() ||(OthelloBoard.BOTH != board.hasMove()))) {
			return true;
		}
	return false;
	}

	/**
	 * 
	 * @return a string representation of the board.
	 */
	public String getBoardString() {   
		return board.toString();
	}
		
		
	/**
	 * run this to test the current class. We play a completely random game. DO NOT
	 * MODIFY THIS!! See the assignment page for sample outputs from this.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Random rand = new Random();

		Othello o = new Othello();
		System.out.println(o.getBoardString());
		while (!o.isGameOver()) {
			int row = rand.nextInt(8);
			int col = rand.nextInt(8);

			if (o.move(row, col)) {
				System.out.println("makes move (" + row + "," + col + ")");
				System.out.println(o.getBoardString() + o.getWhosTurn() + " moves next");
			}
		}

	}
}