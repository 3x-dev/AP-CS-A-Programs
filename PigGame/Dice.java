/**
 *  This program defines the Dice class. A Dice is 6 sides by default, but can
 *  be overridden as n-sided. It keeps track of the number of rolls and
 *  the last roll value.
 *
 *  @author Mr Greenstein
 *  @version 2.0
 */

public class Dice {
	private int sides, rollcount, value;
	
	/* Create the seven different line images of a die */
	private String [] line = {
						" _______ ",
						"|       |",
						"| O   O |",
						"|   O   |",
						"|     O |",
						"| O     |",
						"|_______|" };
	
	/**	Constructor for Dice with s sides */
	public Dice (int s) {
		sides = s;
		rollcount = value = 0;
	}
	
	/**	Constructor for default Dice with 6 sides */
	public Dice ( ) {
		this(6);
	}
	
	/**	@return		the rolled value */
	public int roll ( ) {
		rollcount++;
		value = (int)(Math.random() * sides) + 1;
		return value;
	}
	
	/**	@return		number of rolls so far */
	public int numRolls ( ) {
		return rollcount;
	}
	
	/**	@return		the value on the dice */
	public int getValue ( ) {
		return value;
	}
	
	/**
	 *  Prints out the images of the dice
	 */
	public void printDice() {		
		/* Print each line */
		for (int i = 0; i < 6; i++) {
			System.out.print(" ");
			printDiceLine(getValue() + 6 * i);
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 *  @param value The index into the ASCII dice image
	 */
	private void printDiceLine(int value) {
		System.out.print(line[getDiceLine(value)]);
	}
	
	/**
	 *  @param value The value of the die print line
	 *  @return  The index into the ASCII dice image
	 */
	private int getDiceLine(int value) {
		if (value < 7) return 0;
		if (value < 14) return 1;
		switch (value) {
			case 20: case 22: case 25:
				return 1;
			case 16: case 17: case 18: case 24: case 28: case 29: case 30:
				return 2;
			case 19: case 21: case 23:
				return 3;
			case 14: case 15:
				return 4;
			case 26: case 27:
				return 5;
			default:	// value > 30
				return 6;
		}
	}
}






