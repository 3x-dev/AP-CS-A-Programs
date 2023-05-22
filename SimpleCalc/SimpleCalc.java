import java.util.ArrayList;
import java.util.List;

/**
 *	Creates a simple arithmetic calculator
 *
 *	@author	Aryan Singhal
 *	@since	Feburary 28, 2023
 */
public class SimpleCalc
{
	private ExprUtils utils;	// expression utilities
	
	private ArrayStack<Double> valueStack;		// value stack
	private ArrayStack<String> operatorStack;	// operator stack
	
	private List<String> tokens;	// List of String tokens
	private List<Identifier> identifiers;	// List of identifiers

	// constructor
	public SimpleCalc()
	{
		utils = new ExprUtils();
		valueStack = new ArrayStack<Double>();
		operatorStack = new ArrayStack<String>();
		
		identifiers = new ArrayList<Identifier>();
	}
	
	// Main method
	public static void main(String[] args)
	{
		SimpleCalc sc = new SimpleCalc();
		sc.run();
	}
	
	// Runs all the methods
	public void run()
	{
		System.out.println("\nWelcome to SimpleCalc!!!");
		prepCalc();
		runCalc();
		System.out.println("\nThanks for using SimpleCalc! Goodbye.\n");
	}
	
	/**
	 *	Prompt the user for expressions, run the expression evaluator,
	 *	and display the answer.
	 */
	public void runCalc()
	{		
		String input = Prompt.getString(" ");
		if(input.equals(""))
			runCalc();
		else if(input.equals("q") || input.equals("Q"))
			return;
		else if(input.equals("h") || input.equals("H"))
		{
			printHelp();
			runCalc();
		}
		else if(input.equals("l") || input.equals("L"))
		{
			printVariables();
			runCalc();
		}
		else
		{
			String expr = input;
			tokens = utils.tokenizeExpression(expr);
						
			if(isInputAssignment(tokens))
			{				
				List<String> assignExpr = tokens.subList(2, tokens.size());

				if(!isStringIdentifier(tokens.get(0)))
					System.out.println("Error in expression");
				else if(!isValidExpression(assignExpr))
					System.out.println("Error in expression");
				else
				{
				
					String idName = tokens.get(0);
					double idValue = evaluateExpression(assignExpr);
					Identifier id = new Identifier(idName, idValue);
					
					if(id.isValidName())
					{
						if(!isIdReplaced(id))
							identifiers.add(id);
						
						System.out.printf("   %-10s = %.15f\n", idName, idValue);
					}
				}
			}
			else if(isVariable(tokens))
			{
				String token = tokens.get(0);
				if(isStringOperand(token))
					System.out.println(token);
				else
					System.out.println(getIdValue(token));
			}
			else
			{
				if(!isValidExpression(tokens))
					System.out.println("Error in expression");
				else
					System.out.printf("%f\n", evaluateExpression(tokens));
			}
			runCalc();
		}
	}
	
	/**
	 *	Evaluate expression and return the value
	 * 
	 *	@param tokens	a List of String tokens making up an arithmetic expression
	 *	@return			a double value of the evaluated expression
	 */
	public double evaluateExpression(List<String> tokens)
	{
		double value = 0;
		
		for(String token:tokens)
		{
			if(isStringOperand(token))
			{
				double num = Double.parseDouble(token);
				valueStack.push(num);
			}
			else if(isStringIdentifier(token))
			{
				double num = getIdValue(token);
				valueStack.push(num);
			}
			else if(isStringOperator(token))
			{
				if(operatorStack.isEmpty() || token.equals("("))
					operatorStack.push(token);
				else if(token.equals(")")) // closing parentheses
				{
					String topOp = operatorStack.peek();
					while(!topOp.equals("("))
					{
						evaluate();
						topOp = operatorStack.peek();
					}
					operatorStack.pop();
				}
				else // normal non-parenthesis operator
				{
					String topOp = operatorStack.peek();
					
					// while(topOp >= token)
					while(!operatorStack.isEmpty() && hasPrecedence(token, topOp))
					{
						evaluate();
						
						if(!operatorStack.isEmpty())
							topOp = operatorStack.peek();
					}
				    operatorStack.push(token);
				}
			}
		}
		
		while(!operatorStack.isEmpty())
			evaluate();
		
		// Calculated value
		value = valueStack.pop();
		
		return value;
	}
	
	/**
	 * Evaluates an expression by popping one operator and two operands
	 * and pushes the result into the value stack.
	 */
	public void evaluate()
	{
		String operator = operatorStack.pop();
		
		double right = valueStack.pop();
		double left = valueStack.pop();
		double value = 0;
		
		switch(operator)
		{
			case "^":
				value = Math.pow(left, right);
				break;
				
			case "*":
				value = left * right;
				break;
				
			case "/":
				value = left / right;
				break;
				
			case "%":
				value = left % right;
				break;
				
			case "+":
				value = left + right;
				break;
			
			case "-":
				value = left - right;
				break;
		}
		valueStack.push(value);
	}
	
	/**
	 * This method takes a String parameter idName and searches for an Identifier
	 * object with the same name from a list of Identifier objects called identifiers.
	 * If a matching Identifier object is found, it retrieves its value and
	 * assigns it to a double variable value. If no match is found, value remains 0. 
	 * The method then returns the value. 
	 *
	 * @param idName 		a String representing the name of the Identifier object to retrieve the value from
	 * @return a double 	representing the value of the Identifier object with the specified name, 
	 *         or 0 if no matching Identifier object is found
	 */
	public double getIdValue(String idName)
	{
		double value = 0;
		
		for(Identifier eId:identifiers)
		{
			if(idName.equals(eId.getName()))
				value = eId.getValue();
		}
		
		return value;
	}
	
	/**
	 * Checks if the tokens in the input are part of a valid expression.
	 * Precondition: Must have a '=' sign
	 * 
	 * @param tokens	List of string tokens to be checked
	 * @return			If the tokens create a valid assignment expression
	 */
	public boolean isValidExpression(List<String> tokens)
	{
		boolean isValid = true;
		
		if(tokens.size() == 0)
			isValid = false;
		
		for(String token:tokens)
		{
			if(!(isStringOperator(token) || isStringOperand(token)
					|| isStringIdentifier(token)))
				isValid = false;
			
			if(token.equals("="))
				isValid = false;
		}
		
		return isValid;
	}
	
	/**
	 * Checks if the input is just an identifier if the length of tokens is 1.
	 * 
	 * @param tokens		List of tokens to be checked
	 * @return				If their is only one token in the list
	 */
	public boolean isVariable(List<String> tokens)
	{
		return tokens.size() == 1;
	}
	
	/**
	 * This method takes an Identifier object called newId as a parameter and
	 * searches for an Identifier object with the same name from a list of Identifier
	 * objects called identifiers. If a matching Identifier object is found,
	 * it updates its value to the value of newId, and the method returns true. If no matching 
	 * Identifier object is found, the method returns false. 
	 *
	 * @param newId 	the Identifier object to search for and replace its value if found
	 * @return 			true if the value of the matching Identifier object was updated,
	 *					false if no matching Identifier object was found
	 */
	public boolean isIdReplaced(Identifier newId)
	{
		for(Identifier eId:identifiers)
		{
			if(newId.getName().equals(eId.getName()))
			{
				eId.setValue(newId.getValue());
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * This method takes a List of String objects called tokens as a parameter
	 * and checks if the second token in the list is an assignment operator (=).
	 * If it is, the method returns true, otherwise it returns false. 
	 *
	 * @param tokens a List of String objects representing the tokens in a statement
	 * @return true if the second token is an assignment operator, false otherwise
	 */
	public boolean isInputAssignment(List<String> tokens)
	{
		if(tokens.size() >= 2 && tokens.get(1).equals("="))
			return true;
		
		return false;
	}
	
	/**
	 * This method takes a String object called token as a parameter and checks
	 * if it is a single character operator (i.e., +, -, *, /, or %).
	 * If it is, the method returns true, otherwise it returns false. 
	 *
	 * @param token a String object representing a single character operator
	 * @return true if the token is a single character operator, false otherwise
	 */
	public boolean isStringOperator(String token)
	{
		if(token.length() == 1 && utils.isOperator(token.charAt(0)))
			return true;
		
		return false;
	}
	
	/**
	 * This method takes a String object called token as a parameter and
	 * checks if it contains at least one digit. If it does, the method returns true,
	 * otherwise it returns false. 
	 *
	 * @param token a String object representing a potential operand
	 * @return true if the token contains at least one digit, false otherwise
	 */
	public boolean isStringOperand(String token)
	{
		for(int i = 0; i < token.length(); i++)
		{
			char c = token.charAt(i);		
			if(Character.isDigit(c))
				return true;
		}
		
		return false;
	}
	
	/**
	 * This method takes a String object called token as a parameter and checks
	 * if it consists of only letters (either uppercase or lowercase).
	 * If it does, the method returns true, otherwise it returns false. 
	 *
	 * @param token a String object representing a potential identifier
	 * @return true if the token consists of only letters (uppercase or lowercase), false otherwise
	 */
	public boolean isStringIdentifier(String token)
	{
		for(int i = 0; i < token.length(); i++)
		{
			if(!((token.charAt(i) >= 'A' && token.charAt(i) <= 'Z') || 
				(token.charAt(i) >= 'a' && token.charAt(i) <= 'z')))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Prepares the calculator by adding 'e' and 'pi' to the list of identifiers
	 */
	public void prepCalc()
	{
		identifiers.add(new Identifier("e", Math.E));
		identifiers.add(new Identifier("pi", Math.PI));
	}
	
	/**	Print variables */
	public void printVariables()
	{
		System.out.println("\nVariables:");
		for(Identifier eId:identifiers)
		{
			System.out.printf("    %-15s=%15.2f\n", eId.getName(), eId.getValue());
		}
		System.out.println();
	}
	
	/**	Print help */
	public void printHelp()
	{
		System.out.println("\nHelp:");
		System.out.println("  h - this message\n  q - quit\n  l - list variables\n");
		System.out.println("Expressions can contain:");
		System.out.println("  integers or decimal numbers");
		System.out.println("  arithmetic operators +, -, *, /, %, ^");
		System.out.println("  parentheses '(' and ')'");
		System.out.println("  simple assignments, eg. x = <expression>\n");
	}
	
	/**
	 *	Precedence of operators.
	 * 	Returns true if op2 (top of stack) >= op1 (token)
	 * 	Usage: if top of operatorstack >= token, evaluate
	 *	@param op1	operator 1
	 *	@param op2	operator 2
	 *	@return		true if op2 has higher or same precedence as op1; false otherwise
	 *	Algorithm:
	 *		if op1 is exponent, then false
	 *		if op2 is either left or right parenthesis, then false
	 *		if op1 is multiplication or division or modulus and 
	 *				op2 is addition or subtraction, then false
	 *		otherwise true
	 */
	private boolean hasPrecedence(String op1, String op2)
	{
		if (op1.equals("^")) return false;
		if (op2.equals("(") || op2.equals(")")) return false;
		if ((op1.equals("*") || op1.equals("/") || op1.equals("%")) 
				&& (op2.equals("+") || op2.equals("-")))
			return false;
		return true;
	}
}
