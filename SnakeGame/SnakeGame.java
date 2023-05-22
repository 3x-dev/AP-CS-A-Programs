/**
 * Snake Game - A simple snake game in Java
 *	
 * SnakeGame.java is a Java program that aims to create a version of the
 * Snake game using a two-dimensional array and a SinglyLinkedList.
 * The objective of the game is for the player-controlled snake to move
 * around on a grid, eat targets, and grow its tail. The game ends if the
 * snake hits the border or itself, or if it becomes trapped with no available
 * moves. The program includes classes such as Coordinate and SnakeBoard to
 * handle the game logic, and SnakeGame class to run the game, handle user
 * commands, and save and restore game progress.
 * 
 *	@author	Aryan Singhal
 *	@since	May 2, 2023
 */

// imports
import java.util.Scanner;
import java.io.PrintWriter;

public class SnakeGame
{
	private Snake snake;		// the snake in the game
	private SnakeBoard board;	// the game board
	private Coordinate target;	// the target for the snake
	private int score;			// the score of the game
	private final String SAVE_FILE = "snakeGameSave.txt"; // Name of file to be saved to
	
	/*	Constructor	*/
	public SnakeGame()
	{
		snake = new Snake(3, 3);
		board = new SnakeBoard(20, 25);
		board.placeSnake(snake);
		target = createTarget();
		score = 0;
	}
	
	/*	Main method	*/
	public static void main(String[] args)
	{
		SnakeGame snakeGame = new SnakeGame();
		snakeGame.run();
	}
	
	/**
	 * Runs and plays the game
	 */
	public void run()
	{
		printIntroduction();
		
		boolean isNotOver = true;
		while(isNotOver) // game isnt over
		{
			board.printBoard(snake, target);
			isNotOver = isMoved();

			if(isGameOver())
			{
				isNotOver = false;
				board.printBoard(snake, target);
				System.out.println("\nGame Over!\n Your score = " + score);
			}
		}
		
		System.out.println("\nThanks for playing SnakeGame!!!\n\n");
	}
	
	/**
	 * Creates a random valid target on the snake game board.
	 * 
	 * Validity for this random coordinate is determined by if the coordinate
	 * exists on the board and does not share a same coordinate as the snake's
	 * coordinates on the board.
	 * 
	 * @return	the coordinate of the randomly generated target
	 */
	public Coordinate createTarget()
	{
		SinglyLinkedList<Coordinate> validCoords = new SinglyLinkedList<Coordinate>();
		
		for(int i = 0; i < board.getHeight(); i++)
		{
			for(int j = 0; j < board.getWidth(); j++)
			{
				if(board.getChar(new Coordinate(i, j)) == ' ')
					validCoords.add(new Coordinate(i, j)); 
			}
		}
		
		return validCoords.get((int)(Math.random() * validCoords.size())).getValue();
	}
	
	/**
	 * Determines the action to be taken when a player playes a move.
	 * When move variable is false, the player played a move that fits the conditions
	 * to end the game. (quit, or snake dies).
	 * 
	 * @return		if the player has played a move
	 */
	public boolean isMoved()
	{
		char c = getSelection();
		boolean move = true;
		
		switch(c)
		{
			case 'w':
				move = moveNorth();
				break;
			case 'd':
				move = moveEast();
				break;
			case 's':
				move = moveSouth();
				break;
			case 'a':
				move = moveWest();
				break;
			case 'q':
				move = !isQuit();
				break;
			case 'f':
				move = !saveGame();
				break;
			case 'r':
				restoreGame();
				break;
			case 'h':
				helpMenu();
				break;
		} 
		
		if(!move)
			System.out.println("\nGame Over!\nYour Score = " + score); 
		
		return move;
	}
	
	/**
	 * Moves the snake up north one coordinate.
	 * 
	 * @return		if the snake was able to move north
	 */
	public boolean moveNorth()
	{
		Coordinate c = new Coordinate(snake.get(0).getValue().getRow() - 1, snake.get(0).getValue().getCol());
		return move(c);
	}	
	
	/**
	 * Moves the snake right east one coordinate.
	 * 
	 * @return		if the snake was able to move east
	 */
	public boolean moveEast()
	{
		Coordinate c = new Coordinate(snake.get(0).getValue().getRow(), snake.get(0).getValue().getCol() + 1);
		return move(c);
	}
	
	/**
	 * Moves the snake down south one coordinate.
	 * 
	 * @return		if the snake was able to move south
	 */
	public boolean moveSouth()
	{
		Coordinate c = new Coordinate(snake.get(0).getValue().getRow() + 1, snake.get(0).getValue().getCol());
		return move(c);
	}
	
	/**
	 * Moves the snake left west one coordinate.
	 * 
	 * @return		if the snake was able to move west
	 */
	public boolean moveWest()
	{
		Coordinate c = new Coordinate(snake.get(0).getValue().getRow(), snake.get(0).getValue().getCol() - 1);
		return move(c);
	}
	
	/**
	 * Moves the snake to the specified coordinate on the board.
	 *
	 * @param coord		The coordinate to which the snake should be moved.
	 * @return true if the move is successful, false otherwise.
	 */
	public boolean move(Coordinate coord)
	{
		if(!board.isValidLoc(coord))
		  return false; 
		
		if(snake.contains(coord))
		  return false; 
		
		snake.add(0, coord);
		
		if(target.equals(coord))
		{
		  score++;
		  target = createTarget();
		  
		  if(target == null)
			return false; 
		}
		else
		  snake.remove(snake.size() - 1);

		return true;
	}
	
	/**
	 * Gets the user's selection for movement or action.
	 *
	 * @return The character representing the user's selection.
	 */
	public char getSelection()
	{
		char c = ' ';
		boolean hasSelect = false;
		
		while(!hasSelect)
		{
			c = Prompt.getChar("Score: " + score + " (w - North, s - South, d - East, a - West, h - Help)");
			
			switch(c)
			{
				case 'w':
					hasSelect = true;
				case 'd':
					hasSelect = true;
				case 's':
					hasSelect = true;
				case 'a':
					hasSelect = true;
				case 'q':
					hasSelect = true;
				case 'f':
					hasSelect = true;
				case 'r':
					hasSelect = true;
				case 'h':
					hasSelect = true;
			} 
		}
		
		return c;
	}
	
	/**
	 * Checks if the game is over.
	 * 
	 * Conditions for the game to end:
	 * 	- If the number of empty spaces on the board is less than 5
	 * 	- If none of the adjacent locations around the head of the snake
	 * 	  are valid or available for movement.
	 *
	 * @return true if the game is over, false otherwise.
	 */
	public boolean isGameOver()
	{
		int numEmpty = 0;
		for(int i = 1; i < board.getHeight(); i++)
		{
			for(int j = 1; j < board.getWidth(); j++)
			{
				if(board.getChar(new Coordinate(i, j)) == ' ')
				  numEmpty++; 
			} 
		} 
		
		if(numEmpty < 5)
		  return true; 
		
		Coordinate c1 = snake.get(0).getValue();
		Coordinate c2 = new Coordinate(c1.getRow() - 1, c1.getCol());
		Coordinate c3 = new Coordinate(c1.getRow() + 1, c1.getCol());
		Coordinate c4 = new Coordinate(c1.getRow(), c1.getCol() - 1);
		Coordinate c5 = new Coordinate(c1.getRow(), c1.getCol() + 1);
		
		if((!board.isValidLoc(c2) || (board.getChar(c2) != ' ' &&
			board.getChar(c2) != '+')) && (!board.isValidLoc(c3) ||
			(board.getChar(c3) != ' ' && board.getChar(c3) != '+')) &&
			(!board.isValidLoc(c4) || (board.getChar(c4) != ' ' &&
			board.getChar(c4) != '+')) && (!board.isValidLoc(c5) ||
			(board.getChar(c5) != ' ' && board.getChar(c5) != '+')))
			return true; 
		
		return false;
	}
	
	/**
	 * If the user wants to quit the current game
	 * 
	 * @return		if user decides to quit their current game
	 */
	public boolean isQuit()
	{
		char c = Prompt.getChar("\nDo you really want to quit? (yes (y) or no (n)");
		
		if(c == 'y')
		  return true; 
		return false;
	}
	
	/**
	 * Saves the current game into a text file
	 * 
	 * @return		if user decides to save their current game
	 */
	public boolean saveGame()
	{
		char c = Prompt.getChar("\nAre you sure you want to save your game? (yes (y) or no (n))");
		
		if(c != 'y')
		  return false; 
		
		PrintWriter pw = FileUtils.openToWrite(SAVE_FILE);
		
		System.out.println("\nSaving your snake game to the file snakeGameSave.txt");
		pw.println("Score " + score);
		pw.printf("Target %d %d\n", target.getRow(), target.getCol());
		pw.println("Snake " + snake.size());
		
		for(int i = 0; i < snake.size(); i++)
		  pw.printf("%d %d\n", snake.get(i).getValue().getRow(), snake.get(i).getValue().getCol()); 
		
		pw.close();
		return true;
	}

	/**	Restores the game file from a text file	*/
	public void restoreGame()
	{
		Scanner scan = FileUtils.openToRead(SAVE_FILE);
		// for target
		int row = 0;
		int col = 0;
		
		scan.next();
		score = scan.nextInt();
		scan.next();
		row = scan.nextInt();
		col = scan.nextInt();
		target = new Coordinate(row, col);
		scan.next();
		int numCoords = scan.nextInt();
		snake.clear();
		
		for(int k = 0; k < numCoords; k++)
		{
			row = scan.nextInt();
			col = scan.nextInt();
		  
			snake.add(new Coordinate(row, col));
		}
		
		scan.close();
	}
	
	/**	Print the game introduction	*/
	public void printIntroduction()
	{
		System.out.println("  _________              __            ________");
		System.out.println(" /   _____/ ____ _____  |  | __ ____  /  _____/_____    _____   ____");
		System.out.println(" \\_____  \\ /    \\\\__  \\ |  |/ // __ \\/   \\  ___\\__  \\  /     \\_/ __ \\");
		System.out.println(" /        \\   |  \\/ __ \\|    <\\  ___/\\    \\_\\  \\/ __ \\|  Y Y  \\  ___/");
		System.out.println("/_______  /___|  (____  /__|_ \\\\___  >\\______  (____  /__|_|  /\\___  >");
		System.out.println("        \\/     \\/     \\/     \\/    \\/        \\/     \\/      \\/     \\/");
		System.out.println("\nWelcome to SnakeGame!");
		System.out.println("\nA snake @****** moves around a board " +
							"eating targets \"+\".");
		System.out.println("Each time the snake eats the target it grows " +
							"another * longer.");
		System.out.println("The objective is to grow the longest it can " +
							"without moving into");
		System.out.println("itself or the wall.");
		System.out.println("\n");
	}
	
	/**	Print help menu	*/
	public void helpMenu()
	{
		System.out.println("\nCommands:\n" +
							"  w - move north\n" +
							"  s - move south\n" +
							"  d - move east\n" +
							"  a - move west\n" +
							"  h - help\n" +
							"  f - save game to file\n" +
							"  r - restore game from file\n" +
							"  q - quit");
		Prompt.getString("\nPress enter to continue");
	}
}
