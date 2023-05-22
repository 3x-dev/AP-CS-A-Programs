/**
 *  This program defines the Dice class. A Dice is 6 sides by default, but can
 *  be overridden as n-sided. It keeps track of the number of rolls and
 *  the last roll value.
 *
 *  @author Mr Greenstein
 */

public class Dice
{
	private int sides, rollcount, lastRollValue;
	
	public Dice ( ) {
		sides = 6;
		rollcount = lastRollValue = 0;
		//System.out.println("creating dice");
	}
	
	/**
	 *  @param s The number of side for die
	 */
	public Dice (int s) {
		sides = s;
		rollcount = lastRollValue = 0;
	}
	
	/**
	 *  One roll of the die
	 *
	 *  @return The value of the rolled die
	 */
	public int roll ( ) {
		rollcount++;
		lastRollValue = (int)(Math.random()*sides) + 1;
		return lastRollValue;
	}

	public int numRolls ( ) {
		return rollcount;
	}
	
	public int getLastRollValue ( ) {
		return lastRollValue;
	}
}
