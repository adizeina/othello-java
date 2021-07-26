package ca.utoronto.utm.assignment1.othello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author zeina  
 * this is a class in which takes and sets the human players input and uses error/exeption packages when human input is not valid or
 *  incorrect or out of range. 
 * 
 *
 */
public class PlayerHuman {
	
	private static final String INVALID_INPUT_MESSAGE = "Invalid number, please enter 1-8";
	private static final String IO_ERROR_MESSAGE = "I/O Error";
	private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	private Othello othello;
	private char player;
	
/**
 * @author zeina  
 * this method initializes the Othello class and the player, the human player 
 */
	
	public PlayerHuman(Othello othello, char player) {
		
		this.othello = othello;
		this.player = player;
	}

/**
 * @author zeina 
 * this method inherits from move class and calls on it to produce the row and col in string representation 
 * 
 *
 */
	public Move getMove() {     
		
		int row = getMove("row: ");
		int col = getMove("col: ");
		return new Move(row, col);
	}
/**
 * @author zeina 
 * this method ensures input of user is valid calling on exeptions and "break" if invalid. 
 *
 */
	private int getMove(String message) {
		
		int move, lower = 0, upper = 7;
		while (true) {
			try {
				System.out.print(message);
				String line = PlayerHuman.stdin.readLine();
				move = Integer.parseInt(line);
				if (lower <= move && move <= upper) {
					return move;
				} else {
					System.out.println(INVALID_INPUT_MESSAGE);
				}
			} catch (IOException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
				break;
			} catch (NumberFormatException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
			}
		}
		return -1;
	}
}
