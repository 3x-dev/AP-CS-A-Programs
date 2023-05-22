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
 *	@since	October 20, 2022
 */
import java.util.Scanner;

public class WordUtils
{
	private String[] words;	// the dictionary of words
	private int numWordsDict; // Number of words in dictionary
	
	// File containing dictionary of almost 100,000 words.
	private final String WORD_FILE = "wordList.txt";
	
	private Scanner wordScan; // File scanner
	private final int NUM_WORDS = 100000; // Memory allocation of 100000
	
	/* Constructor */
	public WordUtils()
	{
		words = new String[NUM_WORDS];
	}
	
	/**	Load all of the dictionary from a file into words array. */
	private void loadWords()
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
	
	/**	Find all words that can be formed by a list of letters.
	 *  @param letters	string containing list of letters
	 *  @return			array of strings with all words found.
	 */
	public String [] findAllWords (String letters)
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
			
			if(isWordMatch)
			{
				validWordsArray[validCount] = new String(word);
				validCount++;
			}
		}
		
		return validWordsArray;
	}
	
	/**	Print the words found to the screen.
	 *  @param words	array containing the words to be printed
	 */
	public void printWords(String [] wordList)
	{
		System.out.println();
		for(int i = 0; i < numWordsDict && wordList[i] != null; i++)
		{
			System.out.printf("%-20s", wordList[i]);
			if(i % 5 == 0)
				System.out.println();
		}
		System.out.println();
	}
	
	/**	Finds the highest scoring word according to a score table.
	 *
	 *  @param word  		An array of words to check
	 *  @param scoreTable	An array of 26 integer scores in letter order
	 *  @return   			The word with the highest score
	 */
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
	 */
	public int getScore (String word, int [] scoreTable)
	{
		int wordScore = 0;
		for(int i = 0; i < word.length(); i++)
			wordScore += scoreTable[word.charAt(i) - 'a'];
		
		return wordScore;
	}
	
	/***************************************************************/
	/************************** Testing ****************************/
	/***************************************************************/
	public static void main (String [] args)
	{
		WordUtils wu = new WordUtils();
		wu.run();
	}
	
	/**
	 * Calls all the necessary methods.
	 */
	public void run()
	{
		String letters = "";
		letters = getLetters();
		loadWords();
		String [] words = findAllWords(letters);
		System.out.println();
		printWords(words);
		
		// Score table in alphabetic order according to Scrabble
		int [] scoreTable = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
		String best = bestWord(words,scoreTable);
		System.out.println("\nHighest scoring word: " + best + "\nScore = " 
							+ getScore(best, scoreTable) + "\n");
	}
	
	/**
	 * Gets the letters from user input and check if the user input is valid.
	 */
	public String getLetters()
	{
		String letters = "";
		letters = Prompt.getString("Please enter a list of letters, from " + 
							"3 to 12 letters long, without spaces".toLowerCase().trim());
		if(letters.length() < 3 || letters.length() > 12)
			getLetters();
		
		return letters;
	}
}