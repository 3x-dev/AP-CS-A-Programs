/**
 *  
 *
 *  @author Aryan Singhal
 *  @since October 30, 2022
*/

public class ScrapplePlayer
{
	private final int NUMTILES = 8;		// the number of tiles per player
	
	private int playerScore;
	private char[] playerTiles;
		
	public ScrapplePlayer()
	{
		playerScore = 0;
		playerTiles= new char[NUMTILES];
		for(int i = 0; i < playerTiles.length; i++)
			playerTiles[i] = ' ';
	}
	
	public int getScore()
	{
		return playerScore;
	}
	
	public String getPrintableTiles()
	{
		String tiles = "";
		for(int i = 0; i < playerTiles.length; i++)
		{
			if(playerTiles[i] != ' ')
			{
				tiles += playerTiles[i];
				tiles += " ";
			}
		}
		return tiles;
	}
	
	public String getMatchableTiles()
	{
		String tiles = "";
		for(int i = 0; i < playerTiles.length; i++)
		{
			if(playerTiles[i] != ' ')
				tiles += playerTiles[i];
		}
		return tiles.toLowerCase();
	}
	
	public int getTilesNeeded()
	{
		int tilesNeeded = 0;
		for(int i = 0; i < playerTiles.length; i++)
		{
			if(playerTiles[i] == ' ')
				tilesNeeded++;
		}
		
		return tilesNeeded;
	}
	
	public void addTile(char c)
	{
		boolean isReplaced = false;
		for(int i = 0; i < playerTiles.length && !isReplaced; i++)
		{
			if(playerTiles[i] == ' ')
			{
				playerTiles[i] = c;
				isReplaced = true;
			}
		}
	}
	
	public int addScore(int score)
	{
		playerScore += score;
		return playerScore;
	}
	
	public boolean isValidWord(String guess, Dictionary dict)
	{
		// Check valid length
		if(guess.length() < 4 || guess.length() > 8)
		{
			return false;
		}
		
		// Ensure every letter in the guess is part of the player's tiles
		String uGuess = guess.toUpperCase();
		boolean[] isFoundArray = new boolean[NUMTILES];
		boolean isGuessMatch = true;

		for(int i = 0; i < uGuess.length() && isGuessMatch; i++)
		{
			boolean isLetterMatch = false;
			for(int j = 0; j < playerTiles.length && !isLetterMatch; j++)
			{
				if(!isFoundArray[j] && uGuess.charAt(i) == playerTiles[j])
				{
					isFoundArray[j] = true;
					isLetterMatch = true;
				}
			}
			
			if(!isLetterMatch)
				isGuessMatch = false;
		}
		if(!isGuessMatch)
			return false;
			
		// Check guess in dictionary
		if(!dict.hasWord(guess.toLowerCase()))
			return false;
			
		return true;
	}
	
	public void removeTiles(String guess)
	{
		guess = guess.toUpperCase();
		for(int i = 0; i < guess.length(); i++)
		{
			boolean isLetterMatch = false;
			for(int j = 0; j < playerTiles.length && !isLetterMatch; j++)
			{
				if(guess.charAt(i) == playerTiles[j])
				{
					isLetterMatch = true;
					playerTiles[j] = ' ';
				}
			}
		}
	}
}
