/**
 *  Create a roll of 5 dice and display them.
 *
 *  @author Mr Greenstein and Aryan Singhal
 *  @since September 29, 2022
*/

public class DiceGroup
{
	private Dice[] die;
	
	// Create the seven different line images of a die
	String [] line = {	" _______ ",
						"|       |",
						"| O   O |",
						"|   O   |",
						"|     O |",
						"| O     |",
						"|_______|" };
	
	/**
	 *  Creates a group of 5 dice
	 */
	public DiceGroup()
	{
		die = new Dice[5];
		for(int objCounter = 0; objCounter < die.length; objCounter++)
			die[objCounter] = new Dice();
	}
		
	/**
	 *  Roll all dice.
	 */
	public void rollDice()
	{
		int r = 0;
		for(int dieRolled = 0; dieRolled < die.length; dieRolled++)
			die[dieRolled].roll();
	}
	
	/**
	 *  Roll only the dice not in the string "rawHold".
	 *  e.g. If rawHold="24", only roll dice 1, 3, and 5
	 *  @param rawHold	the numbered dice to hold
	 */
	public void rollDice(String rawHold)
	{
		boolean hold[] = new boolean[5];
		for (int i = 0; i < hold.length; i++)
			hold[i] = false;
			
		for(int i = 0; i < rawHold.length(); i++)
		{
			char c = rawHold.charAt(i);
			if (c >= '1' && c <= '5')
			   hold[(int) (c - '1')] = true;
		}
		
		for(int dieRolled = 0; dieRolled < die.length; dieRolled++)
		{			
			if (!hold[dieRolled])
			{
				//System.out.printf("Rolling die %d \n", dieRolled+1);
				die[dieRolled].roll();
			}
		}
	}
	
	/**
	 *  Dice getter method
	 *  @param i	the index into the die array
	 */
	public Dice getDie(int i) { return die[i]; }
	
	/**
	 *  Computes the sum of the dice group.
	 *
	 *  @return  The integer sum of the dice group.
	 */
	public int getTotal() {
		int sum = 0;
		for (int i = 0; i < die.length; i++) sum += die[i].getLastRollValue();
		return sum;
	}
	
	/**
	 *  Prints out the images of the dice
	 */
	public void printDice() {
		printDiceHeaders();
		for (int i = 0; i < 6; i++) {
			System.out.print(" ");
			for (int j = 0; j < die.length; j++) {
				printDiceLine(die[j].getLastRollValue() + 6 * i);
				System.out.print("     ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 *  Prints the first line of the dice.
	 */
	public void printDiceHeaders() {
		System.out.println();
		for (int i = 1; i < 6; i++) {
			System.out.printf("   # %d        ", i);
		}
		System.out.println();
	}
	
	/**
	 *  Prints one line of the ASCII image of the dice.
	 *
	 *  @param value The index into the ASCII image of the dice.
	 */
	public void printDiceLine(int value) {
		System.out.print(line[getDiceLine(value)]);
	}
	
	/**
	 *  Gets the index number into the ASCII image of the dice.
	 *
	 *  @param value The index into the ASCII image of the dice.
	 *  @return	the index into the line array image of the dice
	 */
	public int getDiceLine(int value) {
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
