/**
 *	The object to store US state information.
 *
 *	@author	Aryan Singhal
 *	@since	May 23, 2023
 */
public class State implements Comparable<State>
{
	private String name;
	private String abbreviation;
	private int population;
	private int area;
	private int reps;
	private String capital;
	private int month;
	private int day;
	private int year;
	
	public State(String n, String a, int p, int ar, int r, String c, int m, int d, int y) 
	{
		name = n;
        abbreviation = a;
        population = p;
        area = ar;
        reps = r;
        capital = c;
        month = m;
        day = d;
        year = y;
	}
	
	public int compareTo(State other) 
	{
		return name.compareTo(other.name);
	}
	
	public String getName()
	{
		return name;
	}
	
	public String toString()
	{
		return String.format("%-21s%-7s%-11s%-11s%-5s%-21s%-3s%-3s%-4s", name, abbreviation,
					population, area, reps, capital, month, day, year);
	}
}
