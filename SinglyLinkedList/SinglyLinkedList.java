import java.util.NoSuchElementException;

/**
 *	SinglyLinkedList - A class to represent a singly linked list
 *
 *	@author	Aryan Singhal
 *	@since	May 25, 2023
 */
public class SinglyLinkedList<E extends Comparable<E>>
{
	/* Fields */
	private ListNode<E> head, tail;		// head and tail pointers to list
	private int size;					// number of elements in the list
	
	/* No-args Constructors */
	public SinglyLinkedList()
	{
		head = null;
		tail = null;
		size = 0;
	}
	
	/** Copy constructor */
	public SinglyLinkedList(SinglyLinkedList<E> oldList)
	{
		this();
		
		if(oldList != null)
		{
			ListNode<E> curr = oldList.head;
			
			while(curr != null)
			{
				add(curr.getValue());
				curr = curr.getNext();
			}
		}
	}
	
	/**	Clears the list of elements */
	public void clear()
	{
		head = null;
		tail = null;
		size = 0;
	}
	
	/**
	 * 	Add the object to the end of the list
	 *	@param obj		the object to add
	 *	@return			true if successful; false otherwise
	 */
	public boolean add(E obj)
	{
		ListNode<E> newNode = new ListNode<E>(obj);
		
		if(head == null)
		{
			head = newNode;
			tail = newNode;
		}
		else
		{
			tail.setNext(newNode);
			tail = newNode;
		}
		
		size++;
		return true;
	}
	
	/**
	 * 	Add the object at the specified index
	 *	@param index		the index to add the object
	 *	@param obj			the object to add
	 *	@return				true if successful; false otherwise
	 *	@throws NoSuchElementException if index does not exist
	 */
	public boolean add(int index, E obj)
	{
		if(index < 0 || index > size)
			throw new NoSuchElementException();

		ListNode<E> newNode = new ListNode<E>(obj);
		
		if(index == 0)
		{
			newNode.setNext(head);
			head = newNode;
			
			if(tail == null)
				tail = newNode;
		}
		else if(index == size)
		{
			tail.setNext(newNode);
			tail = newNode;
		}
		else
		{
			ListNode<E> prev = head;
			
			for(int i = 0; i < index - 1; i++)
				prev = prev.getNext();
			
			newNode.setNext(prev.getNext());
			prev.setNext(newNode);
		}

		size++;
		return true;
	}
	
	/**	@return the number of elements in this list */
	public int size()
	{
		return size;
	}
	
	/**
	 * 	Return the ListNode at the specified index
	 *	@param index		the index of the ListNode
	 *	@return				the ListNode at the specified index
	 *	@throws NoSuchElementException if index does not exist
	 */
	public ListNode<E> get(int index)
	{
		if(index < 0 || index >= size)
			throw new NoSuchElementException();

		ListNode<E> curr = head;
		
		for(int i = 0; i < index; i++)
			curr = curr.getNext();

		return curr;
	}
	
	/**
	 * 	Replace the object at the specified index
	 *	@param index		the index of the object
	 *	@param obj			the object that will replace the original
	 *	@return				the object that was replaced
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E set(int index, E obj)
	{
		if(index < 0 || index >= size)
			throw new NoSuchElementException();
	
		ListNode<E> curr = head;
		
		for(int i = 0; i < index; i++)
		{
			if(curr == null)
				throw new NoSuchElementException();
			
			curr = curr.getNext();
		}
		
		E og = curr.getValue();
		curr.setValue(obj);
		
		return og;
	}

	/**
	 * 	Remove the element at the specified index
	 *	@param index		the index of the element
	 *	@return				the object in the element that was removed
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E remove(int index)
	{
		if(isEmpty())
			throw new NoSuchElementException();

		if(index < 0 || index >= size)
			throw new NoSuchElementException();

		E val;
		if(index == 0)
		{
			val = head.getValue();
			head = head.getNext();
			
			if(head == null)
				tail = null;
		}
		else
		{
			ListNode<E> prev = head;
			
			for(int i = 0; i < index - 1; i++)
				prev = prev.getNext();

			val = prev.getNext().getValue();
			prev.setNext(prev.getNext().getNext());
			
			if(index == size - 1)
				tail = prev;
		}
		
		size--;
		return val;
	}

	/**
	 *	Replace the object at the specified index
	 *	@param index		the index of the object
	 *	@param obj			the
	 * @return true if the list is empty; false otherwise
	 */
	public boolean isEmpty()
	{
		return size == 0;
	}
  
	/**
	 *	Return true if the list contains the specified object
	 *	@param obj		the object to search for
	 *	@return			true if the list contains the specified object; false otherwise
	 */
	public boolean contains(E value)
	{
		ListNode<E> curr = head;
		
		while(curr != null)
		{
			if(curr.getValue().equals(value))
				return true;

			curr = curr.getNext();
		}
		
		return false;
	}

	/**
	 *	Return the index of the specified object
	 *	@param obj		the object to search for
	 *	@return			the index of the specified object; -1 if not found
	 */
	public int indexOf(E obj)
	{
		ListNode<E> curr = head;
		int index = 0;
		
		while(curr != null)
		{
			if(curr.getValue().equals(obj))
				return index;
			
			curr = curr.getNext();
			index++;
		}
		
		return -1;
	}
	
	/**	Prints the list of elements */
	public void printList()
	{
		ListNode<E> ptr = head;
		while(ptr != null)
		{
			System.out.print(ptr.getValue() + "; ");
			ptr = ptr.getNext();
		}
	}
}
