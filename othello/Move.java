package ca.utoronto.utm.assignment1.othello;

/**
 * @author zeina
 * class that returns move for player/AI
 */

public class Move {
	private int row, col; 

	public Move(int row, int col) {
		this.row = row;
		this.col = col;
	}
/**
 * @author zeina
 * @return a row 
 */
	public int getRow() {
		return row;      
	}
	/**
	 * @author zeina
	 * @return a col
	 */
	public int getCol() { 
		return col;        
	}
	/**
	 * @author zeina
	 * @return move coordinate (row, col)
	 */
	public String toString() {
		return "(" + this.row + "," + this.col + ")";
	}
}
