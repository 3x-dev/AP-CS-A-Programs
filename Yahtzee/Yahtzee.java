/**
 *	Introduce the game here
 *
 *	@author	Aryan Singhal
 *	@since September 29, 2022
 */

import java.util.Scanner;

public class Yahtzee
{
	private YahtzeePlayer player1;
	private YahtzeePlayer player2;
	private DiceGroup dg;
	private boolean isPlayer1First;
	
	public Yahtzee()
	{
		dg = new DiceGroup();
		player1 = new YahtzeePlayer();
		player2 = new YahtzeePlayer();
		isPlayer1First = false;
	}
	
	public static void main(String[] args)
	{
		Yahtzee yz = new Yahtzee();
		yz.run();
	}
	
	public void run()
	{
		printHeader();
		getNames();
		chooseFirstTurn();
		for(int countRound = 0; countRound < 13; countRound++)
		{
			printScoreCard();
			System.out.printf("\nRound %d of 13 rounds.\n\n\n", countRound + 1);
			playRound();
		}
	}
	
	public void printHeader()
	{
		System.out.println("\n");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("| WELCOME TO MONTA VISTA YAHTZEE!                                                    |");
		System.out.println("|                                                                                    |");
		System.out.println("| There are 13 rounds in a game of Yahtzee. In each turn, a player can roll his/her  |");
		System.out.println("| dice up to 3 times in order to get the desired combination. On the first roll, the |");
		System.out.println("| player rolls all five of the dice at once. On the second and third rolls, the      |");
		System.out.println("| player can roll any number of dice he/she wants to, including none or all of them, |");
		System.out.println("| trying to get a good combination.                                                  |");
		System.out.println("| The player can choose whether he/she wants to roll once, twice or three times in   |");
		System.out.println("| each turn. After the three rolls in a turn, the player must put his/her score down |");
		System.out.println("| on the scorecard, under any one of the thirteen categories. The score that the     |");
		System.out.println("| player finally gets for that turn depends on the category/box that he/she chooses  |");
		System.out.println("| and the combination that he/she got by rolling the dice. But once a box is chosen  |");
		System.out.println("| on the score card, it can't be chosen again.                                       |");
		System.out.println("|                                                                                    |");
		System.out.println("| LET'S PLAY SOME YAHTZEE!                                                           |");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("\n\n");
	}
	
	public void getNames()
	{
		String player1Name = Prompt.getString("Player 1, please enter your " +
												"first name");
		String player2Name = Prompt.getString("\nPlayer 2, please enter your " +
											"first name");
		player1.setName(player1Name);
		player2.setName(player2Name);
	}
	
	public void chooseFirstTurn()
	{
		int player1Score;
		int player2Score;
		String enter;
		
		enter = Prompt.getString("\nLet's see who will go first. " + player1.getName() + 
									", please hit enter to roll the dice");
		rollAndPrint();
		player1Score = dg.getTotal();
		enter = Prompt.getString(player2.getName() + " it's your turn. Please hit enter "+
										"to roll the dice");
		rollAndPrint();
		player2Score = dg.getTotal();
		
		if(player1Score > player2Score)
		{
			isPlayer1First = true;
			System.out.printf("%s, you rolled a sum of %d, and %s, you rolled a " + 
							"sum of %d.", player1.getName(), player1Score,
							player2.getName(), player2Score);
			System.out.printf("\n%s, since your score was higher, you'll roll " +
							"first." , player1);
		}
		else if(player2Score > player1Score)
		{
			isPlayer1First = false;
			System.out.printf("%s, you rolled a sum of %d, and %s, you rolled a " + 
							"sum of %d.", player1.getName(), player1Score,
							player2.getName(), player2Score);
			System.out.printf("\n%s, since your score was higher, you'll roll " +
							"first." , player2.getName());
		}
		else
		{
			System.out.printf("Whoops, we have a tie (both rolled %d). Looks " + 
							"like we'll have to try that again . . .", player1Score);
			chooseFirstTurn();
		}
	}
	
	public void playRound()
	{
		YahtzeePlayer firstPlayer;
		YahtzeePlayer secondPlayer;
		String enter;
		int holdVals;
		
		if(isPlayer1First)
		{
			firstPlayer = player1;
			secondPlayer = player2;
		}
		else
		{
			firstPlayer = player2;
			secondPlayer = player1;
		}
		
		// first player name, first roll, second name, second roll
		enter = Prompt.getString(firstPlayer.getName() + " it's your turn to play. " +
								"Please hit enter to roll the dice");
		rollAndPrint();
		holdDice(2);
		printScoreCard();
		System.out.print("\n" + firstPlayer.getName() + ", now you need to make a choice. ");
		pickCatag(firstPlayer);
	}
	
	public boolean holdDice(int numHolds)
	{
		String holdVals = Prompt.getString("Which di(c)e would you like to keep? Enter the values " + 
							"you'd like to 'hold' without spaces. For examples, if you'd " + 
							"like to 'hold' die 1, 2, and 5, enter 125 (enter -1 if you'd " +
							"like to end the turn)");
		
		if(holdVals.trim().equals("-1"))
			return true;
		
		rollAndPrint(holdVals);
		numHolds--;
		
		if(numHolds > 0) 
			holdDice(numHolds);
			
		return true;
	}
	
	public void pickCatag(YahtzeePlayer player)
	{
		int catagVal;
		YahtzeeScoreCard ysc = player.getScoreCard();
		
		catagVal = Prompt.getInt("Pick a valid integer from the list above");
		if(catagVal < 1 || catagVal > 13)
			pickCatag(player);
		else
		{
			if(!ysc.changeScore(catagVal, dg))
				pickCatag(player);
		}
	}
	
	public void rollAndPrint()
	{
		dg.rollDice();
		dg.printDice();
	}
	
	public void rollAndPrint(String holdVals)
	{
		dg.rollDice(holdVals);
		dg.printDice();
	}
	
	public void printScoreCard()
	{
		player1.getScoreCard().printCardHeader();
		player1.getScoreCard().printPlayerScore(player1);
		player2.getScoreCard().printPlayerScore(player2);
	}
}
