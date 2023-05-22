import java.util.Scanner;
import java.util.ArrayList; 

/**
 *	HTMLRender
 *	This program renders HTML code into a JFrame window.
 *	It requires your HTMLUtilities class and
 *	the SimpleHtmlRenderer and HtmlPrinter classes.
 *
 *	The tags supported:
 *		<html>, </html> - start/end of the HTML file
 *		<body>, </body> - start/end of the HTML code
 *		<p>, </p> - Start/end of a paragraph.
 *					Causes a newline before and a blank line after. Lines are restricted
 *					to 80 characters maximum.
 *		<hr>	- Creates a horizontal rule on the following line.
 *		<br>	- newline (break)
 *		<b>, </b> - Start/end of bold font print
 *		<i>, </i> - Start/end of italic font print
 *		<q>, </q> - Start/end of quotations
 *		<hX>, </hX> - Start/end of heading with size X = 1, 2, 3, 4, 5, 6
 *		<pre>, </pre> - Preformatted text
 *
 *	@author Aryan Singhal
 *	@version November 17, 2022
 */
public class HTMLRender
{
	private HTMLUtilities util;		// HTMLUtilities used in tester
	
	// the array holding all the tokens of the HTML file
	private String [] tokens;
	private final int TOKENS_SIZE = 100000;	// size of array

	// SimpleHtmlRenderer fields
	private SimpleHtmlRenderer render;
	private HtmlPrinter browser;
	
	private ArrayList<String> startFormatTags;
	private ArrayList<String> endFormatTags;
	private ArrayList<String> standAloneTags;
	
	private enum TokenType {UNKNOWN, TAG, WORD, NUMBER, PUNCT}; 
	private enum State {OPEN, BOLD, ITALICS, HEADER, PARA, PRE};
		
	public HTMLRender()
	{
		util = new HTMLUtilities();
		
		// Initialize token array
		tokens = new String[TOKENS_SIZE];
		
		// Initialize Simple Browser
		render = new SimpleHtmlRenderer();
		browser = render.getHtmlPrinter();
		
		startFormatTags = new ArrayList<String>();
		endFormatTags = new ArrayList<String>();
		standAloneTags = new ArrayList<String>();
	}

	public static void main(String[] args)
	{
		HTMLRender hf = new HTMLRender();
		hf.readFile(args);
		hf.startFormatTags();
		hf.endFormatTags();
		hf.standAloneTags();
		hf.run();
	}
	
	public void run()
	{
		ArrayList<String> insideFormatTags = new ArrayList<String>();
		boolean insideFormat = false;
		String endTag = "";
		State state = State.OPEN;
	
		for(int i = 0; i < tokens.length && tokens[i] != null; i++)
		{
			// System.out.println("Found token: " + tokens[i]);
			if(isStartFormatTag(tokens[i].toLowerCase()))
			{
				// insideFormat = true;
				// endTag = getEndFormatTag(tokens[i]);
				switch (state)
				{
					case OPEN:
						if(tokens[i].toLowerCase().equals("<p>"))
						{
							if (insideFormatTags.size() > 0)
							{
								String render = listToString(insideFormatTags);
								System.out.println("Render: " + render);
								browser.print(render);
								browser.println();
								insideFormatTags.clear();
							}
							browser.println();
						}
						else
						{
							if (insideFormatTags.size() > 0)
							{
								String render = listToString(insideFormatTags);
								System.out.println("Render: " + render);
								browser.print(render);
								insideFormatTags.clear();
							}
						}
						break;
					default:
						break;
				}
			}
			else if(isEndFormatTag(tokens[i].toLowerCase()))
			{
				System.out.println("End Format tag: " + tokens[i]);
				switch(state)
				{
					case OPEN:
						if(insideFormatTags.size() > 0)
						{
							String render = listToString(insideFormatTags);
							System.out.println("Render: " + render);
							
							switch(tokens[i].toLowerCase())
							{
								case "</h1>":
									browser.printHeading1(render);
									break;
								case "</h2>":
									browser.printHeading2(render);
									break;
								case "</h3>":
									browser.printHeading3(render);
									break;
								case "</h4>":
									browser.printHeading4(render);
									break;
								case "</h5>":
									browser.printHeading5(render);
									break;
								case "</h6>":
									browser.printHeading6(render);
									break;
								case "</b>":
									browser.printBold(render);
									browser.print(" ");
									break;
								case "</i>":
									browser.printItalic(render);
									break;
								case "</p>":
									browser.print(render);
									browser.println();
									browser.println();
									break;
								case "</pre>":
									browser.printPreformattedText(render);
									break;
								case "</q>":
									browser.print("\"");
									break;
								default:
									break;
							}
						}
						// insideFormat = false;
						insideFormatTags.clear();
						endTag = "";
						break;
					default:  // state
						break;
				}
			}
			else if(isAloneTag(tokens[i].toLowerCase()))
			{
				switch (state)
				{
					case OPEN:
						if(tokens[i].toLowerCase().equals("<hr>"))
						{
							if(insideFormatTags.size() > 0)
							{
								String render = listToString(insideFormatTags);
								System.out.println("Render: " + render);
								browser.print(render);
								browser.println();
								insideFormatTags.clear();
							}
							browser.printHorizontalRule();
						}
						else if(tokens[i].toLowerCase().equals("<br>"))
						{
							if(insideFormatTags.size() > 0)
							{
								String render = listToString(insideFormatTags);
								System.out.println("Render: " + render);
								browser.print(render);
								browser.println();
								insideFormatTags.clear();
							}
							browser.printBreak();
						}
						break;
					default:
						break;
				}
			}
			else
			{
				System.out.println("Adding token: " + tokens[i]);
				insideFormatTags.add(tokens[i]);
			}
			
			/*
			else if(insideFormat && !tokens[i].equalsIgnoreCase(endTag))
				insideFormatTags.add(tokens[i]);
			else if(insideFormat && tokens[i].equalsIgnoreCase(endTag))
			{
				String render = listToString(insideFormatTags);
				
				//System.out.println("Render: " + render);
				
				switch(tokens[i].toLowerCase())
				{
					case "</h1>":
						browser.printHeading1(render);
						break;
					case "</h2>":
						browser.printHeading2(render);
						break;
					case "</h3>":
						browser.printHeading3(render);
						break;
					case "</h4>":
						browser.printHeading4(render);
						break;
					case "</h5>":
						browser.printHeading5(render);
						break;
					case "</h6>":
						browser.printHeading6(render);
						break;
					case "</b>":
						browser.printBold(render);
						browser.print(" ");
						break;
					case "</i>":
						browser.printItalic(render);
						break;
					case "</p>":
						browser.println();
						break;
					case "</pre>":
						browser.printPreformattedText(render);
						break;
					case "</q>":
						browser.print("\"");
						break;
					default:
						break;
				}
				
				insideFormat = false;
				insideFormatTags.clear();
				endTag = "";
			} */
			/*else
			{
				// stand alone tag
				case "<br>":
					
			}*/
		} // end of for loop
		
		/*//Sample renderings from HtmlPrinter class
		// Print plain text without line feed at end
		browser.print("First line");
		
		// Print line feed
		browser.println();
		
		// Print bold words and plain space without line feed at end
		browser.printBold("bold words");
		browser.print(" ");
		
		// Print italic words without line feed at end
		browser.printItalic("italic words");
		
		// Print horizontal rule across window (includes line feed before and after)
		browser.printHorizontalRule();
		
		// Print words, then line feed (printBreak)
		browser.print("A couple of words");
		browser.printBreak();
		browser.printBreak();
		
		// Print a double quote
		browser.print("\"");
		
		// Print Headings 1 through 6 (Largest to smallest)
		browser.printHeading1("Heading1");
		browser.printHeading2("Heading2");
		browser.printHeading3("Heading3");
		browser.printHeading4("Heading4");
		browser.printHeading5("Heading5");
		browser.printHeading6("Heading6");
		
		// Print pre-formatted text (optional)
		browser.printPreformattedText("Preformat Monospace\tfont");
		browser.printBreak();
		browser.print("The end");*/
	}
	
	public String getEndFormatTag(String tag)
	{
		String endTag = "";
		endTag += tag.charAt(0);
		endTag += "/";
		endTag += tag.substring(1, tag.length());
		return endTag;
	}
	
	public String listToString(ArrayList<String> insideFormatTags)
	{
		String converted = "";
		String currentTag = "";
		String nextTag = "";
		
		if(insideFormatTags.size() == 1)
			nextTag = insideFormatTags.get(0);
			
		for(int i = 0; i < insideFormatTags.size() - 1; i++)
		{
			currentTag = insideFormatTags.get(i);
			nextTag = insideFormatTags.get(i + 1);
			// System.out.println("current tag:" + currentTag + ":");
			// System.out.println("next tag:" + nextTag + ":");
			
			TokenType ctype = getType(currentTag);
			TokenType ntype = getType(nextTag);
			
			// Assumes none of the words or numbers start with punctuation
			if(ntype == TokenType.PUNCT && (ctype == TokenType.WORD ||
			   ctype == TokenType.NUMBER || ctype == TokenType.PUNCT))
				converted += currentTag;
			 // else if((ctype == TokenType.PUNCT) && 
			 // (ntype == TokenType.WORD || ntype == TokenType.NUMBER))
			  // converted += currentTag + " ";
			else
				converted += currentTag + " ";
		}
		return converted + nextTag + " ";
	}
	
	public TokenType getType(String token)
	{
		TokenType type = TokenType.UNKNOWN;
		
		if(token.charAt(0) == '<')
			type = TokenType.TAG;
		else if(util.isAlpha(token.charAt(0)))
			type = TokenType.WORD;
		else if(util.isNumber(token.charAt(0)))
			type = TokenType.NUMBER;
		else if(util.isPunctuation(token.charAt(0)))
			type = TokenType.PUNCT;
		return type;
	}
	
	
	public boolean isStartFormatTag(String token)
	{
		boolean match = false;
		
		for(int i = 0; i < startFormatTags.size(); i++)
		{
			if(startFormatTags.get(i).equalsIgnoreCase(token))
				match = true;
		}
		return match;
	}
	
	public boolean isEndFormatTag(String token)
	{
		boolean match = false;
		
		for(int i = 0; i < endFormatTags.size(); i++)
		{
			if(endFormatTags.get(i).equalsIgnoreCase(token))
				match = true;
		}
		return match;
	}
	
	public boolean isAloneTag(String token)
	{
		boolean match = false;
		
		for(int i = 0; i < standAloneTags.size(); i++)
		{
			if(standAloneTags.get(i).equalsIgnoreCase(token))
				match = true;
		}
		return match;
	}
	
	public void startFormatTags()
	{
		startFormatTags.add("<h1>");
		startFormatTags.add("<h2>");
		startFormatTags.add("<h3>");
		startFormatTags.add("<h4>");
		startFormatTags.add("<h5>");
		startFormatTags.add("<h6>");
		startFormatTags.add("<b>");
		startFormatTags.add("<i>");
		startFormatTags.add("<p>");
		startFormatTags.add("<pre>");
		startFormatTags.add("<q>");
		startFormatTags.add("<html>");
		startFormatTags.add("<body>");
	}
	
	public void endFormatTags()
	{
		endFormatTags.add("</h1>");
		endFormatTags.add("</h2>");
		endFormatTags.add("</h3>");
		endFormatTags.add("</h4>");
		endFormatTags.add("</h5>");
		endFormatTags.add("</h6>");
		endFormatTags.add("</b>");
		endFormatTags.add("</i>");
		endFormatTags.add("</p>");
		endFormatTags.add("</pre>");
		endFormatTags.add("</q>");
		endFormatTags.add("</html>");
		endFormatTags.add("</body>");
	}
	
	public void standAloneTags()
	{
		standAloneTags.add("<br>");
		standAloneTags.add("<hr>");
	}
	
	/**
	 *	Opens the HTML file specified on the command line
	 *	then inputs each line and prints out the line and the
	 *	tokens produced by HTMLUtilities.
	 *	@param args		the String array holding the command line arguments
	 */
	public void readFile(String[] args)
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
		
		int numTokens = 0;
		// Open the HTML file
		input = FileUtils.openToRead(fileName);
		
		// Read each line of the HTML file, tokenize, then print tokens
		while(input.hasNext())
		{
			String line = input.nextLine();
			String[] lineTokens = util.tokenizeHTMLString(line);
			
			int numLineTokens = 0;
			for(int i = 0; i < lineTokens.length && lineTokens[i] != null; i++)
			{
				tokens[i + numTokens] = lineTokens[i];
				numLineTokens++;
			}
			numTokens += numLineTokens;
		}
		input.close();
	}
}
