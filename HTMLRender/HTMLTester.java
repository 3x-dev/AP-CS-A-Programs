import java.util.Scanner;

/**
 *	Test class for HTMLUtilities
 *	This class can be run with the following command:
 *
 *		java HTMLTester <htmlFileName>
 *
 *	Requires the HTMLUtilities and FileUtils classes.
 *
 *	@author Mr Greenstein
 */

public class HTMLTester
{
	private HTMLUtilities util;			// HTMLUtilities used in tester
	
	public HTMLTester()
	{
		util = new HTMLUtilities();
	}
	
	public static void main(String[] args)
	{
		HTMLTester ht = new HTMLTester();
		ht.run(args);
	}
	
	/**
	 *	Opens the HTML file specified on the command line
	 *	then inputs each line and prints out the line and the
	 *	tokens produced by HTMLUtilities.
	 *	@param args		the String array holding the command line arguments
	 */
	public void run(String[] args)
	{
		Scanner input = null;
		String fileName = "";
		// if the command line contains the file name, then store it
		if(args.length > 0)
			fileName = args[0];
		// otherwise print out usage message
		else
		{
			System.out.println("Usage: java HTMLTester <htmlFileName>");
			System.exit(0);
		}
		
		// Open the HTML file
		input = FileUtils.openToRead(fileName);
		
		// Read each line of the HTML file, tokenize, then print tokens
		while(input.hasNext())
		{
			String line = input.nextLine();
			System.out.println("\n" + line);
			String [] tokens = util.tokenizeHTMLString(line);
			util.printTokens(tokens);
		}
		input.close();
	}
}
