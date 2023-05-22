/**
 * A Coordinate object holds one location on the board. It has a row
 * field and column field, and the constructor initializes those fields.
 * There are also two accessor methods, getRow() and getCol(), and an equals() method.
 * 
 * @author	Aryan Singhal
 * @since	May 13, 2023
 */

public class Coordinate implements Comparable<Coordinate>
{
	private int row; // Row value of coordinate
	private int col; // Column value of coordinate

	public Coordinate(int r, int c)
	{
		row = r;
		col = c;
	}
	
	// returns the row value of the coordinate object
	public int getRow()
	{
		return row;
	}
	
	// returns the column value of the coordinate object
	public int getCol()
	{
		return col;
	}

	@Override
	public boolean equals(Object other)
	{
		if(other != null && other instanceof Coordinate &&
		  (row == ((Coordinate)other).row) &&
		  (col == ((Coordinate)other).col))
			return true;
		
		return false;
	}
	
	/**
	 *	Coordinate is greater when:
	 *	1. x is greater or
	 *	2. x is equal and y is greater
	 *	3. otherwise Coordinates are equal
	 *	@return		negative if less than, 0 if equal, positive if greater than
	 */
	public int compareTo(Coordinate other) {
		if (! (other instanceof Coordinate))
			throw new IllegalArgumentException("compareTo not Coordinate object");
		if (row > ((Coordinate)other).row || row < ((Coordinate)other).row)
			return row - ((Coordinate)other).row;
		if (col > ((Coordinate)other).col || col < ((Coordinate)other).col)
			return col - ((Coordinate)other).col;
		return 0;
	}
	
	public String toString()
	{	return "[ " + row + ", " + col + "]";  }
}
