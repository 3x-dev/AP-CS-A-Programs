/**
 *	Describe the scorecard here.
 *
 *	@author	Aryan Singhal
 *	@since	September 29, 2022
 */
public class YahtzeeScoreCard
{
	private int[] catagScores;
	
	public YahtzeeScoreCard()
	{
		catagScores = new int[13];
		for(int i = 0; i < catagScores.length; i++)
			catagScores[i] = -1;
	}
	
	/**
	 *	Get a category score on the score card.
	 *	@param category		the category number (1 to 13)
	 *	@return				the score of that category
	 */
	public int getScore(int category) {
		return catagScores[category - 1];
	}
	
	/**
	 * Set a category score on the score card.
	 * @param category		the category number (1 to 13)
	 * @param score			the score for that category
	 * @return				true if successfully updated, else false
	 */
	public boolean setScore(int category, int score)
	{
		// Optional: check update only if the current score is -1
		
		if(category >= 1 && category <= catagScores.length)
		{
			catagScores[category - 1] = score;
			return true;
		}
		
		return false;
	}
	
	/**
	 *  Print the scorecard header
	 */
	public void printCardHeader() {
		System.out.println("\n");
		System.out.printf("\t\t\t\t\t       3of  4of  Fll Smll Lrg\n");
		System.out.printf("  NAME\t\t  1    2    3    4    5    6   Knd  Knd  Hse " +
						"Strt Strt Chnc Ytz!\n");
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}
	
	/**
	 *  Prints the player's score
	 */
	public void printPlayerScore(YahtzeePlayer player) {
		System.out.printf("| %-12s |", player.getName());
		for (int i = 1; i < 14; i++) {
			if (getScore(i) > -1)
				System.out.printf(" %2d |", getScore(i));
			else System.out.printf("    |");
		}
		System.out.println();
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}

	/**
	 *  Change the scorecard based on the category choice 1-13.
	 *
	 *  @param choice The choice of the player 1 to 13
	 *  @param dg  The DiceGroup to score
	 *  @return  true if change succeeded. Returns false if choice already taken.
	 */
	public boolean changeScore(int choice, DiceGroup dg)
	{
		System.out.println("change scrs false" + getScore(choice));
		
		if(getScore(choice) != -1)
			return false;
		
		if(choice >= 1 && choice <= 6)
			numberScore(choice, dg);
		else if(choice == 7)
			threeOfAKind(dg);
		else if(choice == 8)
			fourOfAKind(dg);
		else if(choice == 9)
			fullHouse(dg);
		else if(choice == 10)
			smallStraight(dg);
		else if(choice == 11)
			largeStraight(dg);
		else if(choice == 12)
			chance(dg);
		else if(choice == 13)
			yahtzeeScore(dg);
		else
			System.out.println("ERROR: Invalid choice" + choice);
		return true;
	}
	
	/**
	 *  Change the scorecard for a number score 1 to 6
	 *
	 *  @param choice The choice of the player 1 to 6
	 *  @param dg  The DiceGroup to score
	 */
	public void numberScore(int choice, DiceGroup dg)
	{
		int sum = 0;
		
		for(int i = 0; i < 5; i++)
		{
			if(dg.getDie(i).getLastRollValue() == choice)
				sum += choice;
		}
		
		setScore(choice, sum);
	} 
	
	/**
	 *	Updates the scorecard for Three Of A Kind choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void threeOfAKind(DiceGroup dg)
	{
		int sum = 0;
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				if(dg.getDie(j).getLastRollValue() == dg.getDie(j + 1).getLastRollValue())
					sum += dg.getDie(j).getLastRollValue();
			}
		}
		
		setScore(7, sum);
	}
	
	/**
	 *	Updates the scorecard for Four Of A Kind choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void fourOfAKind(DiceGroup dg)
	{
		int sum = 0;
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(dg.getDie(j).getLastRollValue() == dg.getDie(j + 1).getLastRollValue())
					if(dg.getDie(j + 1).getLastRollValue() == dg.getDie(j + 2).getLastRollValue())
						sum += dg.getDie(j).getLastRollValue();
			}
		}
		
		setScore(8, sum);
	}
	
	/**
	 *	Updates the scorecard for Full House choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void fullHouse(DiceGroup dg)
	{
		
	}
	
	/**
	 *	Updates the scorecard for Small Straight choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void smallStraight(DiceGroup dg)
	{
		
	}
	
	/**
	 *	Updates the scorecard for Large Straight choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void largeStraight(DiceGroup dg)
	{
		
	}
	
	/**
	 *	Updates the scorecard for Chance choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void chance(DiceGroup dg)
	{
		
	}
	
	/**
	 *	Updates the scorecard for Yahtzee choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void yahtzeeScore(DiceGroup dg)
	{
		
	}

}
