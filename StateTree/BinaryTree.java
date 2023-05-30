import java.util.Scanner;

/**
 * Binary Tree for State Objects
 *
 * @author Aryan Singhal
 * @since May 23, 2023
 */
public class BinaryTree
{
	private final String DEFAULT_FILE_NAME = "states2.txt"; // Default input file
	private Scanner keyboard; //  Scanner used for reading text files
	
	private TreeNode root; // root of tree
		
	public BinaryTree()
	{
		root = null;
	}
	
	/**
	 *	Load data from a text file
	 */
	public void loadData()
	{
		Scanner input = FileUtils.openToRead(DEFAULT_FILE_NAME);
        
        while(input.hasNextLine())
        {            
			String name = input.next();
			String abbreviation = input.next();
			int population = input.nextInt();
			int area = input.nextInt();
			int reps = input.nextInt();
			String capital = input.next();
			int month = input.nextInt();
			int day = input.nextInt();
			int year = input.nextInt();
			State state = new State(name, abbreviation, population,
							area, reps, capital, month, day, year);
			
			insert(state);
        }
        
        input.close();
        System.out.println("Loading file " + DEFAULT_FILE_NAME + "\n");
	}
	
	/**
	 * Insert State into tree
	 * @param next  State to insert
	 */
	public void insert(State next)
	{
		if(root == null)
		{
			TreeNode<State> tempNode = new TreeNode<State>(next);
			root = tempNode;
		}
		else
			insertNodeRecursive(root, next);
	}
	
	/**
	 * Insert State into tree recursively
	 * @param value  State to insert recursively
	 */
	private void insertNodeRecursive(TreeNode<State> node, State value)
	{
		if(value.compareTo(node.getValue()) > 0)
		{
			if(node.getRight() != null)
				insertNodeRecursive(node.getRight(), value);
			else
			{
				TreeNode<State> tempNode = new TreeNode<State>(value);
				node.setRight(tempNode);
			}
		}
		else
		{
			if(node.getLeft() != null)
				insertNodeRecursive(node.getLeft(), value);
			else
			{
				TreeNode<State> tempNode = new TreeNode<State>(value);
				node.setLeft(tempNode);
			}
		}
	}

	/**
	 * Prints the tree as a list in ascending order by state name
	 */
	public void printList()
	{
		printInOrder(root);
	}
	
	/**
	 * Prints in order for each node recursively
	 * @param node		current node of tree
	 */
	private void printInOrder(TreeNode<State> node)
	{
		if(node != null)
		{
			printInOrder(node.getLeft());
			System.out.println(node.getValue().toString());
			printInOrder(node.getRight());
		}
	}
	
	/**
	 * Prompts user for State name to find, then starts search
	 */
	public void testFind()
	{
		System.out.println("\n\nTesting search algorithm");
		search();
	}
	
	/**
	 * Searches tree for State given by user.
	 * 
	 * If State given by user not found in the tree,
	 * 		tell user state does not exist in the tree.
	 */
	public void search()
	{
		String stateName = Prompt.getString("\nEnter state name to search for (Q to quit)");
		stateName = stateName.toLowerCase();
		if(stateName.equals("q"))
		{
			System.out.println();
			return;
		}
		
        State state = find(stateName);
        
        if(state != null)
        {
            System.out.println("\n" + state);
            search();
		}
        else
        {
            System.out.println("Name = " + stateName + "  No such state name");
            search();
		}
	}
	
	/**
	 * Finds State node in tree
	 * 
	 * @param name		node to find
	 * @return			the State object if it exists
	 */
	private State find(String name)
	{
        return findNode(root, name);
    }
    
    /**
	 * Finds State node in tree
	 * 
	 * @param name		node to find
	 * @param root		current root traversed in tree
	 * @return			the State object
	 */
	private State findNode(TreeNode<State> node, String name)
	{
		if(node == null)
			return null;		
		
		int compare = name.compareTo(node.getValue().getName().toLowerCase());
		
		if(compare < 0)
			return findNode(node.getLeft(), name);
		else if(compare > 0)
			return findNode(node.getRight(), name);
		else
			return node.getValue();
	}

	/**
	 * Prompts user for State name to delete
	 * OPTIONAL: Not included in your grade!
	 */
	public void testDelete()
	{
		System.out.println("\n\nTesting delete algorithm");
		testDeletePrompt();
	}
	
	public void testDeletePrompt()
	{
		String stateName = Prompt.getString("\nEnter state name to delete for (Q to quit)");
		stateName = stateName.toLowerCase();
		if(stateName.equals("q"))
		{
			System.out.println();
			return;
		}
		
        State state = find(stateName);
        
        if(state != null)
        {
            remove(root, state);
            System.out.println("\nDeleted " + stateName);
            testDeletePrompt();
		}
        else
        {
            System.out.println("\nName = " + stateName + "  No such state name");
            testDeletePrompt();
		}
	}
	
	/**
	 *	Remove value from Binary Tree
	 *	
	 * @param node			the root of the subtree
	 * @param value			the value to remove from the subtree
	 * @return				TreeNode that connects to parent
	 */
	public TreeNode<State> remove(TreeNode<State> node, State value)
	{
		if(node == null)
        return null;

		int compare = value.compareTo(node.getValue());

		if(compare < 0)
			node.setLeft(remove(node.getLeft(), value));
		else if(compare > 0)
			node.setRight(remove(node.getRight(), value));
		else
		{
			if(node.getLeft() == null && node.getRight() == null)
			{
				// Case 1: Node is a leaf node
				node = null;
			}
			else if(node.getLeft() == null)
			{
				// Case 2: Node has only a right child
				node = node.getRight();
			}
			else if(node.getRight() == null)
			{
				// Case 3: Node has only a left child
				node = node.getLeft();
			}
			else
			{
				// Case 4: Node has both left and right children
				
				// Find the minimum value in the right subtree
				TreeNode<State> minNode = findMin(node.getRight());
				// Replace the current node's value with the minimum value
				node.setValue(minNode.getValue());
				// Remove the minimum node from the right subtree
				node.setRight(remove(node.getRight(), minNode.getValue()));
			}
		}

		return node;
	}
	
	private TreeNode<State> findMin(TreeNode<State> node)
	{
		if(node.getLeft() == null)
			return node; // Found the minimum value

		return findMin(node.getLeft()); // Continue searching in the left subtree
    }
	
	/**
	 * Finds the number of nodes starting at the root of the tree
	 * @return  the number of nodes in the tree
	 */
	public int size()
	{
		return countNodes(root);
	}
	
	/**
	 * Counts all the nodes in the tree
	 * @param node		current node
	 */
	private int countNodes(TreeNode<State> node)
	{
		if(node == null)
			return 0;

		int leftCount = countNodes(node.getLeft());
		int rightCount = countNodes(node.getRight());

		return 1 + leftCount + rightCount;
    }
	
	/**
	 * Clears the tree of all nodes
	 */
	public void clear()
	{
		System.out.println("Tree cleared\n");
		root = null;
	}
	
	/**
	 * Prompts user for level of tree to print.
	 * The top level (root node) is level 0.
	 */
	public void printLevel()
	{
		System.out.println("\n\nTesting printLevel algorithm");
		printLevelPrompt();
	}
	
	/**
	 * Prompts user for level of tree to print and prints level.
	 * The top level (root node) is level 0.
	 */
	public void printLevelPrompt()
	{
		int origLevel = Prompt.getInt("\nEnter level value to print (-1 to quit)");
		if(origLevel == -1)
		{
			System.out.println();
			return;
		}
		System.out.println("\nLevel   " + origLevel);
		int curLevel = 0;
		printLevelR(root, origLevel, curLevel);
		System.out.println();
		printLevelPrompt();
	}
	
	/**
	 * Keeps traversing through the tree until desired level is reached.
	 * Done recursively.
	 * 
	 * @param node			current state node of level
	 * @param origLevel		destination level/depth
	 * @param curLevel		current level/depth
	 */
	public void printLevelR(TreeNode<State> node, int origLevel, int curLevel)
	{
		if(node == null)
			return;
		else if(curLevel == origLevel)
		{
			System.out.print(node.getValue().getName() + "   ");
			return;
		}
		else
		{
			printLevelR(node.getLeft(), origLevel, curLevel + 1);
			printLevelR(node.getRight(), origLevel, curLevel + 1);
		}
	}
	
	/**
	* Prints the highest level of the tree (root is level 0),
	* prints "Tree empty" if empty tree
	*/
	public void testDepth()
	{
		if(root == null)
			System.out.println("Tree empty\n");
		else
			System.out.printf("Depth of tree = %d\n\n", getDepth(root) - 1);
	}
	
	/**
	* Gets the current depth of the node given in the tree.
	* 
	* @param node		node to be checked for getting the depth in the tree
	*/
	private int getDepth(TreeNode<State> node)
	{
		if(node == null)
			return 0;

		int leftDepth = getDepth(node.getLeft());
		int rightDepth = getDepth(node.getRight());

		return 1 + Math.max(leftDepth, rightDepth);
	}
}
