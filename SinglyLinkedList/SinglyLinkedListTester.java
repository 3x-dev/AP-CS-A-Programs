import java.util.NoSuchElementException;

/**
 *	Tester for SinglyLinkedList using Coordinate class as values
 *
 *	@author	Mr Greenstein
 *	@since	March 29, 2019
 */
public class SinglyLinkedListTester
{
	private SinglyLinkedList<Coordinate> sll;
	
	private final int NUM_COORDS = 3;
	
	public SinglyLinkedListTester() { sll = new SinglyLinkedList<Coordinate>(); }
	
	public static void main(String[] args)
	{
		SinglyLinkedListTester sllt = new SinglyLinkedListTester();
		sllt.run();
	}
	
	public void run()
	{
		System.out.println("\nSingly Linked List Tester\n-------------------------");
		testAdd();					// Assignment 1 
		testAddIndex();				// Assignment 2
		testClear();				// Assignment 3
		testSize();					// Assignment 4
		testGet();					// Assignment 5
		testSet();					// Assignment 6
		testRemove();				// Assignment 7
		testIsEmpty();				// Assignment 8 and 11 (copy constructor)
		testContains();				// Assignment 9
		testIndexOf();				// Assignment 10
		System.out.println();
	}
	
	public void testAdd()
	{
		System.out.println("1. Testing add(E value)");
		addCoordinates(sll);
		sll.printList();
		System.out.println();
	}
	
	public void testAddIndex()
	{
		System.out.println("\n2. Testing add(int index, E value)");
		addIndexCoordinates(sll);
		sll.printList();
		System.out.println();
		try
		{
			sll.add(20, new Coordinate(4, 5));
		}
		catch (NoSuchElementException e)
		{	System.err.println("ERROR: no index = 20"); }
	}
	
	public void testClear()
	{
		System.out.println("\n3. Testing clear()");
		sll.clear();
		System.out.println("List after clear():");
		sll.printList();
		System.out.println();
	}
	
	public void testSize()
	{
		System.out.println("\n4. Testing size()");
		addCoordinates(sll);
		sll.printList();
		System.out.println("\nsize = " + sll.size());
	}
	
	public void testGet()
	{
		System.out.println("\n5. Testing E get(int index)");
		System.out.println("Before: ");
		sll.printList();
		if (sll.size() > 0) {
			int index = (int)(Math.random() * sll.size());
			System.out.println("\nindex = " + index + "    Coordinate = "
								+ sll.get(index).getValue());
			index = (int)(Math.random() * sll.size());
			System.out.println("index = " + index + "    Coordinate = "
								+ sll.get(index).getValue());
			try
			{
				sll.get(100);
			}
			catch (NoSuchElementException e)
			{	System.err.println("ERROR: no index = 100"); }
		}
	}
	
	public void testSet()
	{
		System.out.println("\n6. Testing set(int index, E value)");
		System.out.println("Before: ");
		sll.printList();
		int index = (int)(Math.random() * sll.size());
		Coordinate oldCoord = sll.set(index, new Coordinate(111, 222));
		System.out.println("\nindex = " + index + "  oldCoord = " + oldCoord);
		System.out.println("newCoord = " + new Coordinate(111, 222));
		System.out.println("After: ");
		sll.printList();
		System.out.println();
		try
		{
			sll.set(200, new Coordinate(22, 33));
		}
		catch (NoSuchElementException e)
		{	System.err.println("ERROR: no index = 200"); }
	}
	
	public void testRemove()
	{
		System.out.println("\n7. Testing remove(int index)");
		System.out.println("Before:");
		sll.printList();
		int index = (int)(Math.random() * sll.size());
		Coordinate oldCoord = sll.remove(index);
		System.out.println("\nRemove index = " + index + "   Coordinate = "
							+ oldCoord);
		System.out.println("After:");
		sll.printList();
		index = (int)(Math.random() * sll.size());
		oldCoord = sll.remove(index);
		System.out.println("\nRemove index = " + index + "   Coordinate = "
							+ oldCoord);
		System.out.println("After:");
		sll.printList();
		System.out.println();
		try
		{
			sll.remove(250);
		}
		catch (NoSuchElementException e)
		{	System.err.println("ERROR: no index = 250"); }
	}
	
	public void testIsEmpty()
	{
		System.out.println("\n8. Testing isEmpty (and copy constructor) until list is empty");
		SinglyLinkedList<Coordinate> sll2 = new SinglyLinkedList<Coordinate>(sll);
		addCoordinates(sll2);
		if (sll2.size() > 0) {
			System.out.println("Before:");
			sll2.printList();
			System.out.println();
			while(! sll2.isEmpty()) sll2.remove(0);
			System.out.println("After:");
			sll2.printList();
		}
	}
	
	public void testContains()
	{
		System.out.println("\n9. Testing contains");
		System.out.println("Before:");
		sll.printList();
		System.out.println();
		System.out.println("contains(2, 1) = " + sll.contains(new Coordinate(2, 1)));
		System.out.println("contains(2, 0) = " + sll.contains(new Coordinate(2, 0)));
		System.out.println("contains(2, 3) = " + sll.contains(new Coordinate(2, 3)));
	}
	
	public void testIndexOf()
	{
		System.out.println("\n10. Testing indexOf(E value)");
		System.out.println("Before:");
		sll.printList();
		System.out.println("\nsll.indexOf((1, 2)) = " + sll.indexOf(new Coordinate(1, 2)));
		System.out.println("sll.indexOf((3, 2)) = " + sll.indexOf(new Coordinate(3, 2)));
	}
	
	/**********************************************************************/
	/******************* Helper methods for testing ***********************/
	/**********************************************************************/
	public void addCoordinates(SinglyLinkedList<Coordinate> sll)
	{		
		for (int row = 0; row < NUM_COORDS; row++)
			for (int col = 0; col < NUM_COORDS; col++)
				sll.add(new Coordinate(row, col));
	}
	public void addIndexCoordinates(SinglyLinkedList<Coordinate> sll)
	{		
		sll.clear();
		for (int row = 0; row < NUM_COORDS; row++)
			for (int col = 0; col < NUM_COORDS; col++) {
				int index = (int)(Math.random() * sll.size());
				sll.add(index, new Coordinate(row, col));
			}
	}
}