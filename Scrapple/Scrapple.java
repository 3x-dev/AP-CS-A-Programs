/**
 *  A simple version of the Scrabble game where the user plays against the computer.
 *
 *  @author	Aryan Singhal
 *  @since	October 25, 2022
 * 
 * java -cp Scrapple.jar Scrapple
 */

public class Scrapple
{
	public int [] scores = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10,
					 		1, 1, 1, 1, 4, 4, 8, 4, 10}; // Score table for each letter
	private String tilesRemaining = 
					"AAAAAAAAAABBCCDDDDEEEEEEEEEEEEEFFGGGHHIIIIIIIII" +
					"JKLLLLMMNNNNNNOOOOOOOOPPQRRRRRRSSSSTTTTTTUUUUVVWWXYYZ"; // tile pool
	private final int NUMTILES = 8;			// the number of tiles per player
	private final int MIN_WORD_LENGTH = 4;	// minimum of 4 characters
	
	private ScrapplePlayer userPlayer; // Create a new player for the user
	private ScrapplePlayer compPlayer; // Create a new player for the computer
	private Dictionary dict; // New instance of the dictionary
	
	/**
	 * Constructor initializes field variables
	 */
	public Scrapple()
	{
		userPlayer = new ScrapplePlayer();
		compPlayer = new ScrapplePlayer();
		dict = new Dictionary();
		dict.loadWords();
	}
	
	/**
	 * Main method calls run method
	 */
	public static void main(String [] args)
	{
		Scrapple scrap = new Scrapple();
		scrap.run();
	}
	
	/**
	 * Run method calls all methods and alternates between user and computer's turns.
	 * Checks for conditions to end the game.
	 */
	public void run()
	{
		boolean isGameOver = false;
		boolean userTurn = true;
		
		printIntroduction();
		assignTiles(userPlayer);
	    assignTiles(compPlayer);
		while(tilesRemaining.length() >= 0 && !isGameOver)
		{
			printPool();
			printStats();
			if(userTurn) // Player's turn
			{
				if(userGuess(userPlayer))
				{
					assignTiles(userPlayer);
					String[] validWords = dict.findAllWords(userPlayer.getMatchableTiles());
		
					int numValidCount = 0;
					for(int i = 0; i < validWords.length && validWords[i] != null; i++)
						numValidCount++;
						
				    if (numValidCount == 0)
				    {
						System.out.println("\nNo more words can be created by player.");
						isGameOver = true;
					}
					
				}
				else
					isGameOver = true;
			}
			
			if(!userTurn && !isGameOver) // Computer's turn
			{
				String enter = "";
				enter = Prompt.getString("\nIt's the computer's turn. Hit " +
								"ENTER on the keyboard to continue");
				if(compGuess(compPlayer))
					assignTiles(compPlayer);
				else
					isGameOver = true;
			}
			userTurn = !userTurn;
		}
		
		if(isGameOver)
		{
			printFinalStats();
			System.out.println("\nThank you for playing Scrapple!\n\n");
		}
	}
	
	/**
	 * 
	 */
	public void assignTiles(ScrapplePlayer player)
	{
		int numTiles = player.getTilesNeeded();
		for(int i = 0; i < numTiles; i++)
		{
			char tile = getTileFromPool();
			player.addTile(tile);
		}
	}
	
	public char getTileFromPool()
	{
		if (tilesRemaining.length() <= 0)
			return ' ';
			
		int randI = (int)(tilesRemaining.length() * Math.random());
		char randC = tilesRemaining.charAt(randI);
		tilesRemaining = tilesRemaining.replaceFirst(Character.toString(randC), "");
		
		return randC;
	}
	
	public void printStats()
	{
		System.out.printf("\n\nPlayer Score:%5d", userPlayer.getScore());
		System.out.printf("\nComputer Score:%3d\n", compPlayer.getScore());
		System.out.printf("\n%-39s%-16s", "THE TILES IN YOUR HAND ARE:",
							userPlayer.getPrintableTiles());
		System.out.printf("\n\n%-39s%-16s\n", "THE TILES IN THE COMPUTER HAND ARE:",
							compPlayer.getPrintableTiles());
	}
	
	public void printPool()
	{
		System.out.println("\nHere are the tiles remaining in the pool of " + 
						"letters:");
		for(int i = 0; i < tilesRemaining.length(); i++)
		{
			if(i % 20 == 0)
				System.out.printf("\n ");
			System.out.printf("%-2s", tilesRemaining.charAt(i));
		}
	}
	
	public boolean userGuess(ScrapplePlayer player)
	{
		String playerGuess = Prompt.getString("\nPlease enter a word created "+
								"from your current set of tiles");
		
		boolean isValid = false;
		
		if(player.isValidWord(playerGuess, dict))
		{
			int score = computeScore(playerGuess);
			if(hasDouble(playerGuess))
				System.out.print("BONUS WORD SCORE!!!\n");
			player.addScore(score);
			// remove tiles from player tile (making empty)
			player.removeTiles(playerGuess);
			isValid = true;
		}
		 
		return isValid;
	}
	
	public boolean compGuess(ScrapplePlayer player)
	{
		String[] validWords = dict.findAllWords(player.getMatchableTiles());
		
		int numValidCount = 0;
		for(int i = 0; i < validWords.length && validWords[i] != null; i++)
			numValidCount++;
		
		if(numValidCount == 0)
		{
			// print computer could not find a word
			System.out.println("\nNo more words can be created by computer.");
			return false;
		}
			
		String compWord = bestWord(validWords, scores);
		
		System.out.printf("\nThe computer chose: %s\n", compWord.toUpperCase());
		
		if(hasDouble(compWord))
				System.out.print("BONUS WORD SCORE!!!\n");
		
		int score = computeScore(compWord);
		player.addScore(score);
		// remove tiles from player tile (making empty)
		player.removeTiles(compWord);
		
		return true;
	}
	
	public String bestWord(String[] wordList, int[] scoreTable)
	{
		int wordScore = 0;
		int bestScore = 0;
		String bestWord = "";
		for(int i = 0; i < wordList.length && wordList[i] != null; i++)
		{
			wordScore = computeScore(wordList[i]);
			if(wordScore > bestScore)
			{
				bestScore = wordScore;
				bestWord = wordList[i];
			}
		}
		
		return bestWord;
	}
	
	public int computeScore(String guess)
	{
		int guessScore = 0;
		for(int i = 0; i < guess.length(); i++)
			guessScore += scores[guess.charAt(i) - 'a'];
		
		if(hasDouble(guess))
			guessScore *= 2;
			
		return guessScore;
	}
	
	public boolean hasDouble(String word)
	{
		boolean hasDouble = false;
		for(int i = 0; i < word.length() && !hasDouble; i++)
		{
			if(i < word.length() - 1 && word.charAt(i) == word.charAt(i + 1))
				hasDouble = true;
		}
		
		return hasDouble;
	}
	
	public void printFinalStats()
	{
		System.out.printf("\n%-39s%-16s", "THE TILES IN YOUR HAND ARE:",
							userPlayer.getPrintableTiles());
		System.out.printf("\n\n%-39s%-16s\n", "THE TILES IN THE COMPUTER HAND ARE:",
							compPlayer.getPrintableTiles());
		System.out.printf("\n\nPlayer Score:%5d", userPlayer.getScore());
		System.out.printf("\nComputer Score:%3d\n", compPlayer.getScore());
	}
	
	/**
	 *  Print the introduction screen for Scrapple.
	 */
	public void printIntroduction()
	{
		System.out.print(" _______     _______     ______     ______    ");
		System.out.println(" ______    ______   __          _______");
		System.out.print("/\\   ___\\   /\\  ____\\   /\\  == \\   /\\  __ \\   ");
		System.out.println("/\\  == \\  /\\  == \\ /\\ \\        /\\  ____\\");
		System.out.print("\\ \\___   \\  \\ \\ \\____   \\ \\  __<   \\ \\  __ \\  ");
		System.out.println("\\ \\  _-/  \\ \\  _-/ \\ \\ \\_____  \\ \\  __\\");
		System.out.print(" \\/\\______\\  \\ \\______\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\ ");
		System.out.println(" \\ \\_\\     \\ \\_\\    \\ \\______\\  \\ \\______\\");
		System.out.print("  \\/______/   \\/______/   \\/_/ /_/   \\/_/\\/_/ ");
		System.out.println("  \\/_/      \\/_/     \\/______/   \\/______/ TM");
		System.out.println();
		System.out.print("This game is a modified version of Scrabble. ");
		System.out.println("The game starts with a pool of letter tiles, with");
		System.out.println("the following group of 100 tiles:\n");
		
		for (int i = 0; i < tilesRemaining.length(); i ++)
		{
			System.out.printf("%c ", tilesRemaining.charAt(i));
			if (i == 49) System.out.println();
		}
		System.out.println("\n");
		System.out.printf("The game starts with %d tiles being chosen at ran", NUMTILES);
		System.out.println("dom to fill the player's hand. The player must");
		System.out.printf("then create a valid word, with a length from 4 to %d ", NUMTILES);
		System.out.println("letters, from the tiles in his/her hand. The");
		System.out.print("\"word\" entered by the player is then checked. It is first ");
		System.out.println("checked for length, then checked to make ");
		System.out.print("sure it is made up of letters from the letters in the ");
		System.out.println("current hand, and then it is checked against");
		System.out.print("the word text file. If any of these tests fail, the game");
		System.out.println(" terminates. If the word is valid, points");
		System.out.print("are added to the player's score according to the following table ");
		System.out.println("(These scores are taken from the");
		System.out.println("game of Scrabble):");
		
		// Print line of letter scores
		char c = 'A';
		for (int i = 0; i < 26; i++)
		{
			System.out.printf("%3c", c);
			c = (char)(c + 1);
		}
		System.out.println();
		for (int i = 0; i < scores.length; i++) System.out.printf("%3d", scores[i]);
		System.out.println("\n");
		
		System.out.print("The score is doubled (BONUS) if the word has consecutive double ");
		System.out.println("letters (e.g. ball).\n");
		
		System.out.print("Once the player's score has been updated, more tiles are ");
		System.out.println("chosen at random from the remaining pool");
		System.out.printf("of letters, to fill the player's hand to %d letters. ", NUMTILES);
		System.out.println("The player again creates a word, and the");
		System.out.print("process continues. The game ends when the player enters an ");
		System.out.println("invalid word, or the letters in the");
		System.out.println("pool and player's hand run out. Ready? Let's play!\n");
		
		String enter = "";
		enter = Prompt.getString("HIT ENTER on the keyboard to continue:");
	}
}
