/**
 * The SnakeBoard class knows how to construct the board, place a Snake and
 * target on the board, and print the board to the screen.
 * The SnakeBoard will be stored as a two-dimensional array of char’s.
 * 
 * The SnakeGame class will use the SnakeBoard whenever it needs to display
 * the board to the screen. 
 *
 *	@author	Aryan Singhal
 *	@since	May 2, 2023
 */
public class SnakeBoard
{
	/*	fields	*/
	private int height;
	private int width;
	private char[][] board;			// The 2D array to hold the board
	
	/*	Constructor	*/
	public SnakeBoard(int height, int width)
	{
		this.height = height;
		this.width = width;
		board = new char[this.height + 2][this.width + 2];
		
		createBoard();
	}
	
	/**
	 * Creates/sets up the board for a snake game
	 */
	private void createBoard()
	{
		for(int i = 0; i < board.length; i++)
		  for(int j = 0; j < (board[0]).length; j++)
			this.board[i][j] = ' '; 
		
		for(int i = 0; i < (board[0]).length; i++)
		{
		  board[board.length - 1][i] = '-';
		  board[0][i] = '-';
		} 
		
		for(int i = 0; i < board.length; i++)
		{
		  board[i][(board[0]).length - 1] = '|';
		  board[i][0] = '|';
		} 
		
		board[board.length - 1][(board[0]).length - 1] = '+';
		board[board.length - 1][0] = '+';
		board[0][(board[0]).length - 1] = '+';
		board[0][0] = '+';
	}
	
	/**
	 *	Print the board to the screen.
	 */
	public void printBoard(Snake snake, Coordinate target)
	{
		createBoard();
		placeSnake(snake);
		board[target.getRow()][target.getCol()] = '+';
		
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < (board[0]).length; j++)
				System.out.print(board[i][j] + " "); 
			
			System.out.println();
		}
	}
	
	/* Helper methods go here	*/
	
	/**
	 * Checks if the coordinate passed in is a valid coordinate to move to on the
	 * snake board relative to the snakes current position.
	 * 
	 * @param coord		the coordinate to check for validity
	 * @return		if the coord passed in is a valid location for moving
	 */
	public boolean isValidLoc(Coordinate coord)
	{
		return (coord.getCol() > 0 && coord.getCol() <= width &&
				coord.getRow() > 0 && coord.getRow() <= height);
	}
	
	/**
	 * "Places" draws the snake on the snake game board.
	 * 
	 * @param 	the snake to place.
	 */
	public void placeSnake(Snake snake)
	{
		if(snake.size() < 1)
		  return;
		  
		board[snake.get(0).getValue().getRow()][snake.get(0).getValue().getCol()] = '@';
		
		for(int i = 1; i < snake.size(); i++)
		  board[snake.get(i).getValue().getRow()][snake.get(i).getValue().getCol()] = '*'; 
	}
	
	/*	Accessor methods	*/
	
	// returns the height (number of rows) of the snake board
	public int getHeight()
	{
		return height;
	}
	
	// returns the width (number of columns) of the snake board
	public int getWidth()
	{
		return width;
	}
	
	// Gets and returns the character at the coordinate passed in
	public char getChar(Coordinate coord)
	{
		return board[coord.getRow()][coord.getCol()];
	}
	
	/********************************************************/
	/********************* For Testing **********************/
	/********************************************************/
	
	public static void main(String[] args)
	{
		// Create the board
		int height = 10, width = 15;
		SnakeBoard sb = new SnakeBoard(height, width);
		// Place the snake
		Snake snake = new Snake(3, 3);
		// Place the target
		Coordinate target = new Coordinate(1, 7);
		// Print the board
		sb.printBoard(snake, target);
	}
}
