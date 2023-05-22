import java.util.List;
import java.util.ArrayList;

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
 *	@since	January 24, 2022
 */
import java.util.Scanner;

public class WordUtilities
{
	private ArrayList<String> words;	// the dictionary of words
	private int numWordsDict; // Number of words in dictionary
	
	// File containing dictionary of words.
	private String wordFile;
	private final int NUM_WORDS = 50000;
	
	private Scanner wordScan; // File scanner
	
	/* Constructor */
	public WordUtilities()
	{
		words = new ArrayList <String>();
	}
	
	/** Reads all the words from the database and loads them into an ArrayList */
	public void readWordsFromFile(String fileName)
	{
		wordFile = fileName;
		loadWords();
	}
	
	/**	Load all of the dictionary from a file into words array. */
	private void loadWords()
	{
		String word = "";
		int count = 0;
		wordScan = FileUtils.openToRead(wordFile);
		while(wordScan.hasNextLine())
		{
			word = wordScan.nextLine();
			words.add(count, word);
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
			String word = words.get(i);
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
	public void printWords(List <String> wordList)
	{
		System.out.println();
		for(String word : wordList)
		{
			System.out.printf("%s\n", word);
		}
	}

	/**
	 *	Determines if a word's characters match a group of letters
	 *  Is every character in word present in the String of letters.
	 *	@param word		the word to check
	 *	@param letters	the letters
	 *	@return			true if the word's chars match; false otherwise
	 */
	private boolean wordMatch(String word, String letters)
	{
		// if the word is longer than letters return false
		if (word.length() > letters.length()) return false;
		
		// while there are still characters in word, check each word character
		// with letters
		while (word.length() > 0)
		{
			// using the first character in word, find the character's index inside letters
			// and ignore the case
			int index = letters.toLowerCase().indexOf(Character.toLowerCase(word.charAt(0)));
			// if the word character is not in letters, then return false
			if (index < 0) return false;
			
			// remove character from word and letters
			word = word.substring(1);
			letters = letters.substring(0, index) + letters.substring(index + 1);
		}
		// all word letters were found in letters
		return true;
	}
	
	/**
	 *	finds all words that match some or all of a group of alphabetic characters
	 *	Precondition: letters can only contain alphabetic characters a-z and A-Z
	 *	@param letters		group of alphabetic characters
	 *	@return				an ArrayList of all the words that match some or all
	 *						of the characters in letters
	 */
	public ArrayList<String> allWords(String letters)
	{
		ArrayList<String> wordsFound = new ArrayList<String>();
		// check each word in the database with the letters
		for (String word: words)
			if (wordMatch(word, letters))
				wordsFound.add(word);
		return wordsFound;
	}
	
	/**
	 *	Sort the words in the database
	 */
	public void sortWords()
	{
		SortMethods sm = new SortMethods();
		sm.mergeSort(words);
	}
	
	/**
	 * Removes the word passed in from an existing word.
	 * @param orig		original phrase
	 * @param word		word to be removed
	 * @return String	the new word to be checked
	 */
	public String removeWord(String orig, String word)
	{
		String newWord = "";
		boolean[] isFoundArray = new boolean[orig.length()];
		
		for(int i = 0; i < word.length(); i++)
		{
			char c = word.charAt(i);
			boolean isCharFound = false;
			for(int j = 0; j < orig.length() && isCharFound == false; j++)
			{
				if(c == orig.charAt(j) && isFoundArray[j] == false)
				{
					isFoundArray[j] = true;
					isCharFound = true;
				}
			}
			
			if(!isCharFound)
				System.out.println("ERROR: Character invalid");
		}
		
		for(int j = 0; j < orig.length(); j++)
		{
			if(isFoundArray[j] == false)
				newWord += orig.charAt(j);
		}
		
		return newWord;
	}
}
