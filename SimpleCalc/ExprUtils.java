import java.util.ArrayList;
import java.util.List;

/**
 *	Arithmetic expression utilities
 *	o Methods to tokenize an arithmetic expression.
 *	o Methods to validate the List of expression tokens
 *
 *	@author	Mr Greenstein
 */
public class ExprUtils {
	
	private ArrayStack<String> operatorStack;	// stack for operators
	private ArrayStack<Double> valueStack;		// stack for values
	
	public ExprUtils() {
		operatorStack = new ArrayStack<String>();
		valueStack = new ArrayStack<Double>();
	}
	/*************************************************************************/
	/****************** Expression Tokenizer methods *************************/
	/*************************************************************************/
	/**
	 *	Tokenize a mathematical expression. This can handle the following
	 *	situations:
	 *	1. The unary operator "-" or "+".
	 *	2. An integer or decimal number. The decimal number can start with
	 *		a digit or a decimal (".").
	 *	3. An identifier for a variable which contains only letters.
	 *	4. An operator, like "(", ")", "+", "-", etc.
	 *	
	 *	@param expression	the expression to tokenize
	 *	@return				a List of tokens, each token has a length of one or greater
	 */
	public List<String> tokenizeExpression(String expression) {
		// remove extraneous characters from expression
		expression = cleanExpr(expression);
		
		// the expression tokens
		List<String> result = new ArrayList<String>();
		
		// Keep track of last token processed
		String lastToken = "";
		
		int ind = 0;	// index into String expression
		
		// while there are characters in the expression
		while (ind < expression.length()) {
			char c = expression.charAt(ind++);
			String token = "";
			
			// if character is "-" or "+"
			if (c == '-' || c == '+') {
				// check if there is a "-" or "+" operator preceding this character.
				// This means:
				// 	Expression starts with unary operator like "-4".
				// 	Expression contains two consecutive unary operators "3+-2".
				if (lastToken.length() == 0 || lastToken.equals("=") ||
							lastToken.equals("+") || lastToken.equals("-")) {
					// precede number or variable with unary operator
					token += c;
					// If what follows is a digit, then input number
					if (Character.isDigit(expression.charAt(ind))) {
						// Add number to unary operator, e.g. "-" + "4.3" => "-4.3"
						while (ind < expression.length() && 
							( Character.isDigit(expression.charAt(ind)) ||
								expression.charAt(ind) == '.') )
							token += expression.charAt(ind++);
					}
					// else what follows is a variable, input variable
					else {
						while (ind < expression.length() && 
								Character.isLetter(expression.charAt(ind)))
							token += expression.charAt(ind++);
					}
					result.add(token);
				}
				else {
					token = "" + c;
					result.add(token);
				}
			}
			// if character is digit or decimal, read in number
			else if (Character.isDigit(c) || c == '.') {
				token += c;
				while (ind < expression.length() && 
					( Character.isDigit(expression.charAt(ind)) ||
						expression.charAt(ind) == '.') )
					token += expression.charAt(ind++);
				result.add(token);
			}
			// if character is a letter, read in alpha identifier
			else if (Character.isLetter(c)) {
				token += c;
				while (ind < expression.length() && Character.isLetter(expression.charAt(ind)))
					token += expression.charAt(ind++);
				result.add(token);
			}
			// if character is operator or parentheses (, ), +, -, *, /, or '='
			else if (isOperator(c)) {
				token = "" + c;
				result.add(token);
			}
			
			// if anything else, do nothing
			
			lastToken = token;
		}
		
		return result;
	}
	
	/**
	 *	Remove extraneous characters (like spaces)
	 *	@param expr		the expression String
	 *	@return			the expression with extraneous characters removed
	 */
	private String cleanExpr(String expr) {
		String result = "";
		for (int a = 0; a < expr.length(); a++)
			if (validChar(expr.charAt(a))) result += expr.charAt(a);
		return result;
	}
	
	/**
	 *	Test if the character is valid:
	 *		letter, digit, arithmetic operator, or decimal point
	 *	@param c	character to check
	 *	@return		true if character is valid; false otherwise
	 */
	private boolean validChar(char c) {
		if (Character.isLetterOrDigit(c) || isOperator(c) || c == '.')
			return true;
		return false;
	}
	
	/**	Determine if character is valid arithmetic operator including parentheses
	 *	@param c	the character to check
	 *	@return		true if the character is '+', '-', '*', '/', '^', '=','(', or ')'
	 */
	public boolean isOperator(char c) {
		return isBinaryOperator(c) || c == '(' || c == ')';
	}
	
	/**	Determine if character is valid binary arithmetic operator excluding parentheses
	 *	@param c	the character to check
	 *	@return		true if the character is '+', '-', '*', '/', '^', or '='
	 */
	private boolean isBinaryOperator(char c) {
		switch (c) {
			case '+': case '-': case '*': case '/': 
			case '%': case '=': case '^':
				return true;
		}
		return false;
	}
	
	
	/******************************************************************/
	/************************** For Testing ***************************/
	/******************************************************************/
	public static void main(String[] args) {
		ExprUtils et = new ExprUtils();
		et.run();
	}
	
	public void run() {
		List<String> tokens;
		String expr;
		System.out.println();
		
		expr = "2 + 3 * 5";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");
		
		expr = "xa = 2.1 + 3 * (5 - 4)";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");
		
		expr = "3.456 * 23 / (.5 - 23)";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");
		
		expr = "- 54 + - .12";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");
		
		expr = "4 * (3 + 2) - 18 / (6 * 3)";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");
		
		expr = "- 1 + 1";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");
	}
}
