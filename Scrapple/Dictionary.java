/**
 *	Provides utilities for word games:
 *	1. finds all words in the dictionary that match a list of letters
 *	2. prints an array of words to the screen in tabular format
 *	3. finds the word from an array of words with the highest score
 *	4. calculates the score of a word according to a table
 *
 *	Uses the FileUtils and Prompt classes.
 *	
 *	@author Aryan Singhal
 *	@since	October 30, 2022
 */
import java.util.Scanner;

public class Dictionary
{
	private String[] words;	// the dictionary of words
	private int numWordsDict; // Number of words in dictionary
	
	// File containing dictionary of almost 100,000 words.
	private final String WORD_FILE = "wordList.txt";
	
	private Scanner wordScan; // File scanner
	private final int NUM_WORDS = 100000; // Memory allocation of 100000
	
	/* Constructor */
	public Dictionary()
	{
		words = new String[NUM_WORDS];
	}
	
	/**	Load all of the dictionary from a file into words array. */
	public void loadWords()
	{
		String word = "";
		int count = 0;
		wordScan = FileUtils.openToRead(WORD_FILE);
		while(wordScan.hasNextLine())
		{
			word = wordScan.nextLine();
			words[count] = word;
			count++;
		}
		
		numWordsDict = count;
	}
	
	/**
	 * 
	 */
	public boolean hasWord(String word)
	{
		//System.out.println("\nDictionary length:" + numWordsDict + "\n");
		boolean hasFound = false;
		for(int i = 0; i < numWordsDict && !hasFound; i++)
		{
			if(words[i].equals(word))
				hasFound = true;
		}
		return hasFound;
	}
	
	/**	Find all words that can be formed by a list of letters.
	 *  @param letters	string containing list of letters
	 *  @return			array of strings with all words found.
	 */
	public String[] findAllWords (String letters)
	{
		String[] validWordsArray = new String[NUM_WORDS];
		int validCount = 0;
		
		for(int i = 0; i < numWordsDict; i++)
		{
			String word = words[i];
			boolean[] isFoundArray = new boolean[letters.length()];
			boolean isWordMatch = true;
			
			for(int j = 0; j < word.length() && isWordMatch; j++)
			{
				boolean isLetterMatch = false;
				for(int k = 0; k < letters.length() && !isLetterMatch; k++)
				{
					if(!isFoundArray[k] && word.charAt(j) == letters.charAt(k))
					{
						isFoundArray[k] = true;
						isLetterMatch = true;
					}
				}
				
				if(!isLetterMatch)
					isWordMatch = false;
			}
			
			if(isWordMatch && word.length() >= 4 && word.length() <= 8 )
			{
				validWordsArray[validCount] = new String(word);
				validCount++;
			}
		}
		
		return validWordsArray;
	}
	
	/**	Finds the highest scoring word according to a score table.
	 *
	 *  @param word  		An array of words to check
	 *  @param scoreTable	An array of 26 integer scores in letter order
	 *  @return   			The word with the highest score
	 *
	public String bestWord (String [] wordList, int [] scoreTable)
	{
		int wordScore = 0;
		int bestScore = 0;
		String bestWord = "";
		for(int i = 0; i < numWordsDict && wordList[i] != null; i++)
		{
			wordScore = getScore(wordList[i], scoreTable);
			if(wordScore > bestScore)
			{
				bestScore = wordScore;
				bestWord = wordList[i];
			}
		}
		
		return bestWord;
	}
	
	/**	Calculates the score of one word according to a score table.
	 *
	 *  @param word			The word to score
	 *  @param scoreTable	An array of 26 integer scores in letter order
	 *  @return				The integer score of the word
	 *
	public int getScore (String word, int [] scoreTable)
	{
		int wordScore = 0;
		for(int i = 0; i < word.length(); i++)
			wordScore += scoreTable[word.charAt(i) - 'a'];
		
		return wordScore;
	}
	*/
}
