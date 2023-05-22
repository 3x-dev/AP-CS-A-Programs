/**
 * Identifier class that defines and handles identifiers.
 * 
 * @author Aryan Singhal
 * @since March 6, 2023
*/
public class Identifier
{
	private String name;	// Identifier name
	private double value;	// Identifier value
	
	// Constructor
	public Identifier(String name, double value)
	{
		this.name = name;
		this.value = value;
	}
	
	/**
	 * Checks if the name of the identifier is a valid name for an identifier
	 * 
	 * @return		If of identifier is a valid name (only letters)
	 */
	public boolean isValidName()
	{
		for(int i = 0; i < name.length(); i++)
		{
			if(!((name.charAt(i) >= 'A' && name.charAt(i) <= 'Z') || 
				(name.charAt(i) >= 'a' && name.charAt(i) <= 'z')))
				return false;
		}
		
		return true;
	}
	
	// Gets the identifier name
	public String getName()
	{
		return name;
	}
	
	// Gets the identifier value
	public double getValue()
	{
		return value;
	}
	
	/**
	 * Sets the identifier value passed in to value field variable
	 * 
	 * @param v		Value to set to value field variable
	 */
	public void setValue(double v)
	{
		value = v;
	}
}
