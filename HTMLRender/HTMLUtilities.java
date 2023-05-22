import java.util.ArrayList;

/**
 *	Utilities for handling HTML
 *
 *	@author	Aryan Singhal
 *	@since November 3, 2022
 */

public class HTMLUtilities
{
	// NONE = not nested in a block, COMMENT = inside a comment block 
	// PREFORMAT = inside a pre-format block 
	private enum TokenState { NONE, COMMENT, PREFORMAT }; 
	// the current tokenizer state 
	private TokenState state = TokenState.NONE;;
	
	final String COMMENT_START_TAG = "<!--"; // Tag for where a comment starts
	final String PRE_START_TAG = "<pre>"; // Tag for where preformat starts
	final String PRE_END_TAG = "</pre>"; // Tag for where preformat ends

	/**
	 *	Break the HTML string into tokens. The array returned is
	 *	exactly the size of the number of tokens in the HTML string.
	 *	Example:	HTML string = "Goodnight moon goodnight stars"
	 *				returns { "Goodnight", "moon", "goodnight", "stars" }
	 *	@param str			the HTML string
	 *	@return				the String array of tokens
	 */
	public String[] tokenizeHTMLString(String str)
	{
		// make the size of the array large to start
		String[] result = new String[10000];
		
		int i = 0;
		int t = 0;
		boolean isTokenFound = false;
		while(i < str.length())
		{
			String token = "";
			isTokenFound = false;
			char current = str.charAt(i);
			
			switch(state)
			{
				case NONE:
					if((str.charAt(i) == '<')) // Tokenizing tags
					{
						if((str.substring(i, str.length()).startsWith(COMMENT_START_TAG)))
							state = TokenState.COMMENT;
						
						if(state != TokenState.COMMENT)
						{
							token = str.substring(i, str.indexOf('>', i) + 1);
							i += token.length();
							isTokenFound = true;
							
							if(token.equals(PRE_START_TAG))
								state = TokenState.PREFORMAT;
						}
					}
					else if(isAlpha(current)) // Tokenizing words
					{
						while(i < str.length() && !isTokenFound)
						{
							if(isWordLetter(str.charAt(i)))
								token += str.charAt(i);
							else
								isTokenFound = true;
							if(!isTokenFound)
								i++;
						}
						if(token.length() > 0) // handle end of line
							isTokenFound = true;
					}
					else if(isNumber(current)) // Tokenizing numbers
					{
						token += getNumericToken(str, i);
						i += token.length();
						isTokenFound = true;
					}
					// Tokenizing negative numbers
					else if((current == '-' &&
							i < str.length() - 1 && isNumber(str.charAt(i + 1))))
					{
						token += current;
						i++;
						token += getNumericToken(str, i);
						i += token.length() - 1;
						isTokenFound = true;
					}
					else if(isPunctuation(current)) // Tokenizing punctuation
					{
						token += current;
						isTokenFound = true;
						i++;
					}
					
					if(isTokenFound)
					{
						result[t] = token;
						t++;
					}
					else
						i++;
					break;
				case COMMENT:
					if((str.charAt(i) == '>' && str.charAt(i - 1) == '-' &&
						str.charAt(i - 2) == '-'))
							state = TokenState.NONE;
					i++;
					break;
				case PREFORMAT:
					while(i < str.length() && !isTokenFound)
					{
						if(str.charAt(i) == '<' && str.substring(i, str.length()).startsWith(PRE_END_TAG))
						{
							// Expecting PRE_END_TAG and after as NONE state
							state = TokenState.NONE;
							isTokenFound = true;
						}
						else 
						{
							token += str.charAt(i);
							// Tokenize until end of line
							if(i == str.length() - 1)
								isTokenFound = true;
							i++;
						}
					}
					
					if(isTokenFound)
					{ 
						if (token.length() > 0)
						{
							result[t] = token;
							t++;
						}
					}
					else
						i++;
					break;
					
				default:
					break;
			}
		}
		// return the correctly sized array
		return result;
	}
	
	/**
	 * Check if the current character is part of the alphabet.
	 * 
	 * @param	char c		characrer to be checked
	 * @return	boolean		if character passed in is part of the alphabet
	 */
	public boolean isAlpha(char c)
	{
		return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'));
	}
	
	/**
	 * Check if the current character is a number.
	 * 
	 * @param	char c		characrer to be checked
	 * @return	boolean		if character passed in is a number
	 */
	public boolean isNumber(char c)
	{
		return (c >= '0' && c <= '9');
	}
	
	/**
	 * Gets the token for numbers.
	 * 
	 * @param String str	line to be read
	 * @param int i			current position being checked in the line
	 * @return String		token found in the line
	 */
	private String getNumericToken(String str, int i)
	{
		String token = "";
		boolean isTokenFound = false;
		
		while(i < str.length() && !isTokenFound)
		{
			if(isNumber(str.charAt(i)))
				token += str.charAt(i);
			else if(str.charAt(i) == '.' && i < str.length() - 1 &&
					isNumber(str.charAt(i + 1)))
				token += str.charAt(i);
			else if(str.charAt(i) == 'e')
			{
				if(i < str.length() - 1 && isNumber(str.charAt(i + 1)))
					token += str.charAt(i);
				else if(i < str.length() - 1 && 
					(str.charAt(i + 1) == '-' || str.charAt(i + 1) == '+'))
				{
					if(i < str.length() - 2 && isNumber(str.charAt(i + 2)))
					{
						token += str.charAt(i);
						token += str.charAt(i + 1);
						i++;
					}
				}
			}
			else
				isTokenFound = true;
				
			if(!isTokenFound)
				i++;
		}
		return token;
	}
	
	/**
	 * Checks if the current character is a punctuation character.
	 * 
	 * @param 	char			character to be checked
	 * @return 	boolean			if character passed is punctuation
	 */
	public boolean isPunctuation(char c)
	{
		return c == '.' || c == ',' || c == ';' || c == '(' || c == ')' ||
			   c == '?' || c == '!' || c == '=' || c == '&' || c == '~' ||
			   c == '+' || c == '-' || c == ':';
	}
	
	/**
	 * Checks if the current character is a letter.
	 * 
	 * @param 	char			character to be checked
	 * @return 	boolean			if character passed is a letter
	 */
	private boolean isWordLetter(char c)
	{
		return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '-';
	}
	
	/**
	 *	Print the tokens in the array to the screen
	 *	Precondition: All elements in the array are valid String objects.
	 *				(no nulls)
	 *	@param tokens		an array of String tokens
	 */
	public void printTokens(String[] tokens)
	{
		if(tokens == null)
			return;
		for(int a = 0; a < tokens.length && tokens[a] != null; a++)
		{
			if(a % 5 == 0)
				System.out.print("\n  ");
			System.out.print("[token " + a + "]: " + tokens[a] + " ");
		}
		System.out.println();
	}
}
