package ca.utoronto.utm.assignment1.othello;
import java.util.ArrayList; 

/**
 * Keep track of all of the tokens on the board. This understands some
 * interesting things about an Othello board, what the board looks like at the
 * start of the game, what the players tokens look like ('X' and 'O'), whether
 * given coordinates are on the board, whether either of the players have a move
 * somewhere on the board, what happens when a player makes a move at a specific
 * location (the opposite players tokens are flipped).
 * 
 * Othello makes use of the OthelloBoard.
 * 
 * @author arnold
 *
 */
public class OthelloBoard {
	
	public static final char EMPTY = ' ', P1 = 'X', P2 = 'O', BOTH = 'B';
	private int dim = 8;
	private char[][] board;
	


	public OthelloBoard(int dim) {
		this.dim = dim;
		board = new char[this.dim][this.dim];
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				this.board[row][col] = EMPTY;
			}
		}
		int mid = this.dim / 2;
		this.board[mid - 1][mid - 1] = this.board[mid][mid] = P1;
		this.board[mid][mid - 1] = this.board[mid - 1][mid] = P2;
	}

	public int getDimension() {
		return this.dim;
	}

	/**
	 * 
	 * @param player either P1 or P2
	 * @return P2 or P1, the opposite of player
	 */
	public static char otherPlayer(char player) {
		if (player == P1){ 
			return P2;
		}
			return P1;
		}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return P1,P2 or EMPTY, EMPTY is returned for an invalid (row,col)
	 */
	public char get(int row, int col) {
		if (validCoordinate(row,col)){
			return this.board[row][col];
		}
		return EMPTY;
	
	}
	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return whether (row,col) is a position on the board. Example: (6,12) is not
	 *         a position on the board.
	 */
	private boolean validCoordinate(int row, int col) {
		if (((row>=0) && (row < this.dim)) && ((col>=0) && (col< this.dim))){
			return true;
		}
		return false;  
		}

	

	/**
	 * Check if there is an alternation of P1 next to P2, starting at (row,col) in
	 * direction (drow,dcol). That is, starting at (row,col) and heading in
	 * direction (drow,dcol), you encounter a sequence of at least one P1 followed
	 * by a P2, or at least one P2 followed by a P1. The board is not modified by
	 * this method. Why is this method important? If
	 * alternation(row,col,drow,dcol)==P1, then placing P1 right before (row,col),
	 * assuming that square is EMPTY, is a valid move, resulting in a collection of
	 * P2 being flipped.
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1, if there is an alternation P2 ...P2 P1, or P2 if there is an
	 *         alternation P1 ... P1 P2 in direction (dx,dy), EMPTY if there is no
	 *         alternation
	 */
	
	
	private char alternation(int row, int col, int drow, int dcol) {
		
		if ((get(row,col)!= P1) && (get(row,col)!= P2)){ //check for empty position at start--
				return EMPTY;
		}
		char player = get(row, col);       //what is this supposed to be
		int newrow = row;
		int newcol = col;
		while (validCoordinate(newrow, newcol) && (this.board[newrow][newcol] == player)){
			newrow += drow;
			newcol += dcol;
			return this.board[newrow][newcol];
		}
		return EMPTY;
	}

	/**
	 * flip all other player tokens to player, starting at (row,col) in direction
	 * (drow, dcol). Example: If (drow,dcol)=(0,1) and player==O then XXXO will
	 * result in a flip to OOOO
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow   the row direction, in {-1,0,1}
	 * @param dcol   the col direction, in {-1,0,1}
	 * @param player Either OthelloBoard.P1 or OthelloBoard.P2, the target token to
	 *               flip to.
	 * @return the number of other player tokens actually flipped, -1 if this is not
	 *         a valid move in this one direction, that is, EMPTY or the end of the
	 *         board is reached before seeing a player token.
	 */
	private int flip(int row, int col, int drow, int dcol, char player) { 
		int flips = 0;
		if ((alternation(row, col, drow, dcol)!= EMPTY) && (validCoordinate(row + drow, col+ dcol))) { //which means its not a row of "xxxxx" only 
			int newrow = row;
			int newcol = col;
			while (this.board[newrow][newcol]!= player) {
				this.board[newrow][newcol] = player;
				flips +=1;
				newrow += drow;
				newcol += dcol;
			}
		}else if ((alternation(row, col, drow, dcol)== EMPTY) || (validCoordinate(row + drow, col+ dcol) == false)){
			return -1;
		}
		return flips;
	}


	/**
	 * Return which player has a move (row,col) in direction (drow,dcol).
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1,P2,EMPTY
	 */
	private char hasMove(int row, int col, int drow, int dcol) { 
		if ((this.board[row][col]==EMPTY) && (validCoordinate(row + drow, col + dcol) == true)) {
			return alternation(row + drow, col + dcol, drow, dcol);
		} return EMPTY;
		
	}

	/**
	 * 
	 * @return whether P1,P2 or BOTH have a move somewhere on the board, EMPTY if
	 *         neither do.
	 */
	
	public char hasMove() {
		for (int row = 0; row < this.dim; row++) {        // we want to iterate through the othello board accessing its rows 
			for (int col = 0; col < this.dim; col++) {     // we want to iterate through the othello board accessing its columns 
				for (int drow = -1; drow <2; drow++) {     //this for loop allows us to 
					for (int dcol = - 1; dcol <2; dcol++){
						if (validCoordinate(row+drow, col+ dcol)) {     //if that new coordinate is valid
							if ((((hasMove(row, col, drow, dcol)) == P1) && (hasMove(row, col, drow, dcol)) == P2)){
								return BOTH;			      //if that move can be made for P1 and P2 then return BOTH 
							}else if ((hasMove(row, col, drow, dcol)) == P1){     //but it it can only be made for P1, return P1
								return P1;
							}else if ((hasMove(row, col, drow, dcol)) == P2){
								return P2;		
						}}}}}}
	return EMPTY;     
	}
		
	
	
	/**
	 * Make a move for player at position (row,col) according to Othello rules,
	 * making appropriate modifications to the board. Nothing is changed if this is
	 * not a valid move.
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param player P1 or P2
	 * @return true if player moved successfully at (row,col), false otherwise
	 */
	public boolean move(int row, int col, char player) {
		// HINT: Use some of the above helper methods to get this methods
		// job done!!
		
		if ((!(validCoordinate(row, col)) || (this.board[row][col]!=EMPTY))) {
			return false;
		}if (hasMove() == player || (hasMove()== BOTH)) {
			for (int i = -1; i<2; i++) {
				for (int j = -1; j<2; j++) {
					
					if (this.flip(row +i, col +j, i, j, player)!=-1){
						this.board[row + i][col+j]= player;
						return true;	
					}}}}
		return false;
	}
	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens on the board for player
	 */
	public int getCount(char player) {     
		int count = 0;
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				if (player == get(row, col)){
					count = count + 1;
				}}}
		return count;
	}
	
	/**
	 * @author zeina 
	 * FOR PLAYER GREEDY 
	 * Status: protected so it can extend to other classes -like PlayerGreedy without having errors appear.
	 * 
	 * Return the number of flips without modifying the board like HumanvsHuman where first valid position, with be the move to make
	 * and the tokens will be flipped. 
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @param player (P1,P2 OR EMPTY)
	 * @return number of flips
	 */

	protected int nonModifyingFlip(int row, int col, int drow, int dcol, char player) { 
		int flips = 0;
		if ((alternation(row, col, drow, dcol)!= EMPTY) && (validCoordinate(row + drow, col+ dcol))) { //which means its not a row of "xxxxx" only 
			int newrow = row;
			int newcol = col;
			while (this.board[newrow][newcol]!= player) {
				flips +=1;
				newrow += drow;
				newcol += dcol;
			}
		}else if ((alternation(row, col, drow, dcol)== EMPTY) || (validCoordinate(row + drow, col+ dcol) == false)){
			return -1;
		}
		return flips;
	}
	
	/**
	 * @author zeina 
	 * FOR PLAYER GREEDY 
	 * Status: protected so it can extend to other classes -like PlayerGreedy without having errors appear.
	 * 
	 * Return a nested array, inside each array will represent a move that can be done by the player, inside the array of a move
	 * will be 3 items, the row, the col and the number of flips that move will result in
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param player (P1,P2 OR EMPTY)
	 * @return an array 
	 */

	protected int[] arrayOfFlips(int row, int col, char player) {   //gives us nested array of row and col
		
		ArrayList<ArrayList<Integer>> nest = new ArrayList<>();   
		if ((!(validCoordinate(row, col)) || (this.board[row][col]!=EMPTY))) {
			; // this acts like the python version of "pass", if its not a valid coordinate we want to pass it 
		}
		else if (hasMove() == player || (hasMove()== BOTH)) {
			for (int i = -1; i<2; i++) {       //iterating through the possible moves to make in rows  
				for (int j = -1; j<2; j++) {   //iterating through the possible moves to make in colomns

					while (flip(row +i, col +j, i, j, player)!=-1){    
						for (int m = 0; m < 64; m++) {     // 8*8, max of arrays possible is 60 (4 tokens placed in the beginning)
						    for (int n = 0; n < 3; n++) {  //inside each array is 3 indexes, row -> 0, col->1, flip->2
						    	nest.get(m).add(row+i);   //adding to the current array the 
						    	nest.get(m).add(col+j);
						    	int flips = nonModifyingFlip(row, col, i, j, player);
						    	nest.get(m).add(flips);    	
						    }
						}
					}
				}
			}
		}
		return nest;   // returning the array
	}

/**
 * @author zeina 
 * FOR PLAYER GREEDY 
 * Status: protected so it can extend to other classes -like PlayerGreedy without having errors appear.
 * 
 * Method calls on function arrayOfFlips and will go through to see which move has the greatest number of flips that can be done, 
 * however, if two moves have the same number of max flips, then the method, already having saved the array inside array(row,col,flips)
 * will look at their row and will choose the move with the smallest number of rows. If both moves happen to share the same row, then it
 * call on the array saved and compare their colomns and the smallest col will be returned as the max flip move.
 *  
 *  
 *  Note: I know this is packed as a functio, but I initially seperated them method into 3 -biggestFlipCoord which calls on method 
 *  smallestRow and smallestCol, but that became more inefficent as repetive as I had to keep calling on the function array 
 *  and save the variable and have 2 forloops to iterate through array, that is why I decided to condense it together. 
 * 
 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
 * @param player (P1,P2 OR EMPTY)
 * @return an array with two elements, row and col of the biggest flip
 */	
	
		
	protected static int[] biggestFlipCoord(int row, int col, char player) {  
		int highest = 0; int current = 0;
		int highestArray;  
		ArrayList<Integer> coords = new ArrayList<Integer>();  
		ArrayList<Integer> theArray = new arrayOfFlips(row, col, player);  //calling on function that makes the array 
		
		//now I need to surf through array 
		
		for (int m = 0; m < 64; m++) {     
		    for (int n = 0; n < 3; n++) {
		    	current = theArray.get(m).get(2);    //will access #of flips
		    	int currentArray = m;               //saving current array 
		    	
		    	
		    	if (highest < current){
					highest = current;                   //accessing number of flips
					highestArray = m;                   //if its the biggest it will store "highest" value in Array 	
					
					
				}else if (highest == current) {                                         //if two moves have same biggest number of flip
					if ((theArray.get(highestArray).get(0)) > (theArray.get(currentArray).get(0))){       //checking for smallest row
						coords.add(theArray.get(currentArray).get(0));                     //adding ROW to coords returning array
						coords.add(theArray.get(currentArray).get(1));                    //adding COL to coords returning array
						
						
					}//this else statement is if the highest's row vs current's row
					else if((theArray.get(highestArray).get(0)) < (theArray.get(currentArray).get(0))) {
						coords.add(theArray.get(highestArray).get(0));                     //adding ROW to coords returning array
						coords.add(theArray.get(highestArray).get(1));
						
					}//this is now if the rows are equal, we will compare their colomns, thats why im using index one 
					else if((theArray.get(highestArray).get(0)) == (theArray.get(currentArray).get(0))) { //now call on smallest col if needed 
						if ((theArray.get(highestArray).get(1)) > (theArray.get(currentArray).get(1))) {  //calling on col
							coords.add(theArray.get(currentArray).get(0));
							coords.add(theArray.get(currentArray).get(1));
							
						}else if ((theArray.get(highestArray).get(1)) < (theArray.get(currentArray).get(1))) {
							coords.add(theArray.get(highestArray).get(0));
							coords.add(theArray.get(highestArray).get(1));
							
							
						}}} return coords;  }}}  

	
	/**
	 * @return a string representation of this, just the play area, with no
	 *         additional information. DO NOT MODIFY THIS!!
	 */
	public String toString() {
		/**
		 * See assignment web page for sample output.
		 */
		String s = "";
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';

		s += " +";
		for (int col = 0; col < this.dim; col++) {
			s += "-+";
		}
		s += '\n';

		for (int row = 0; row < this.dim; row++) {
			s += row + "|";
			for (int col = 0; col < this.dim; col++) {
				s += this.board[row][col] + "|";
			}
			s += row + "\n";

			s += " +";
			for (int col = 0; col < this.dim; col++) {
				s += "-+";
			}
			s += '\n';
		}
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';
		return s;
	}

	/**
	 * A quick test of OthelloBoard. Output is on assignment page.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		OthelloBoard ob = new OthelloBoard(8);
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));
		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				ob.board[row][col] = P1;
			}
		}
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));

		// Should all be blank
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				if (row == 0 || col == 0) {
					ob.board[row][col] = P2;
				}
			}
		}
		System.out.println(ob.toString());

		// Should all be P2 (O) except drow=0,dcol=0
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		// Can't move to (4,4) since the square is not empty
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));

		ob.board[4][4] = EMPTY;
		ob.board[2][4] = EMPTY;

		System.out.println(ob.toString());

		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("hasMove at (4,4) in above direction =" + ob.hasMove(4, 4, drow, dcol));
			}
		}
		System.out.println("who has a move=" + ob.hasMove());
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));
		System.out.println(ob.toString());

	}
}
