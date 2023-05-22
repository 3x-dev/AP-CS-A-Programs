import java.util.List;
import java.util.ArrayList;

/**
 * AnagramMaker:
 * This program creates anagrams from user-provided phrases.
 * The user can choose how many words they want in each anagram and how many
 * anagrams they want. Any inputs that cause the recursion algorithm to run more
 * than 20 seconds will stop the recursion calls and print the anagrams it
 * has found.
 *
 * Requires the WordUtilities, SortMethods, Prompt, and FileUtils classes
 *
 * @author	Aryan Singhal
 * @since	January 24, 2023
 */
public class AnagramMaker
{
	private final String FILE_NAME = "randomWords.txt";	// file containing all words
	
	private WordUtilities wu;	// the word utilities for building the word
								// database, sorting the database,
								// and finding all words that match
								// a string of characters
	
	// variables for constraining the print output of AnagramMaker
	private int numWords;		// the number of words in a phrase to print
	private int maxPhrases;		// the maximum number of phrases to print
	private int numPhrases;		// the number of phrases that have been printed
	// user input phrase that is cleaned and ready to use
	private String cleanedPhrase;
	private ArrayList <String> anagram; // list of anagrams for a phrase
	private ArrayList <String> allWords; // list of all the words for a phrase
	private long startMillisec; // starting millisecond time
		
	/*	Initialize the database inside WordUtilities
	 *	The database of words does NOT have to be sorted for AnagramMaker to work,
	 *	but the output will appear in order if you DO sort.
	 */
	public AnagramMaker()
	{
		cleanedPhrase = "";
		anagram = new ArrayList <String>();
		allWords = new ArrayList <String>();
		wu = new WordUtilities();
		wu.readWordsFromFile(FILE_NAME);
		wu.sortWords();
	}
	
	public static void main(String[] args)
	{
		AnagramMaker am = new AnagramMaker();
		am.run();
	}
	
	/**	The top routine that prints the introduction and runs the anagram-maker.
	 */
	public void run()
	{
		printIntroduction();
		runAnagramMaker();
		System.out.println("\nThanks for using AnagramMaker!\n");
	}
	
	/**
	 *	Print the introduction to AnagramMaker
	 */
	public void printIntroduction()
	{
		System.out.println("\nWelcome to ANAGRAM MAKER");
		System.out.println("\nProvide a word, name, or phrase and out comes their anagrams.");
		System.out.println("You can choose the number of words in the anagram.");
		System.out.println("You can choose the number of anagrams shown.");
		System.out.println("\nLet's get started!");
	}
	
	/**
	 *	Prompt the user for a phrase of characters, then create anagrams from those
	 *	characters.
	 */
	public void runAnagramMaker()
	{
		numPhrases = 0;
		cleanedPhrase = "";
		allWords.clear();
		anagram.clear();
		
		ArrayList <String> anagram = new ArrayList<String>();
		
		String phrase = Prompt.getString("\nWord(s), name or phrase (q to quit)");
		phrase = phrase.trim();
		phrase = phrase.toLowerCase();
		if(phrase.equals("q"))
			return;
		
		for(int i = 0; i < phrase.length(); i++)
		{
			char c = phrase.charAt(i);
			if((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
				cleanedPhrase += c;
		}
		
		numWords = Prompt.getInt("Number of words in anagram");
		maxPhrases = Prompt.getInt("Maximum number of anagrams to print");
		
		System.out.println();
		startMillisec = System.currentTimeMillis();
		createAnagram(cleanedPhrase);
		System.out.printf("\nStopped at %d anagrams\n", numPhrases);
		runAnagramMaker();
	}
	
	/**
	 * Recursive function for creating anagrams.
	 * @param input		phrase to create anagrams out of
	 */
	public void createAnagram(String input)
	{
		// return if longer than 20 seconds
		if((System.currentTimeMillis() - startMillisec) > 20000)
			return;
					
		String newPhrase = "";
		
		// Optimization
		if(numPhrases >= maxPhrases)
			return;
		
		// Base Case
		if(input.isEmpty())
		{
			printAnagram(anagram);
			return;
		}
		
		if(anagram.size() >= numWords)
			return;
		
		allWords = wu.allWords(input);
		
		for(String word : allWords)
		{
			anagram.add(word);
			newPhrase = wu.removeWord(input, word);
			createAnagram(newPhrase);
			anagram.remove(anagram.size()-1);
		}
	}
	
	/**
	 * Prints valid anagrams.
	 * @param anagramL		Local anagram ArrayList to be printed
	 */
	public void printAnagram(ArrayList <String> anagramL)
	{
		if(numWords != anagramL.size())
			return;
		
		if(numPhrases < maxPhrases)
		{
			for(int i = 0; i < anagramL.size() - 1; i++)
				System.out.printf("%s ", anagram.get(i));
			System.out.println(anagram.get(anagram.size() - 1));
			numPhrases++;
		}
	}
}
