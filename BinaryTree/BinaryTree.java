/**
 *	Binary Tree of Comparable values.
 *	The tree only has unique values. It does not add duplicate values.
 *	
 *	@author	Aryan Singhal
 *	@since	May 16, 2023
 */

import java.util.List;
import java.util.ArrayList;

public class BinaryTree<E extends Comparable<E>>
{
	private TreeNode<E> root;		// the root of the tree
	
	private final int PRINT_SPACES = 3;	// print spaces between tree levels
										// used by printTree()
	
	/**	constructor for BinaryTree */
	public BinaryTree()
	{
		root = null;
	}
	
	/**	Field accessors and modifiers */
	
	/**
	 * Add a node to the tree
	 *
	 * @param value		the value to put into the tree
	 */
	public void add(E value)
	{
		if(root == null)
		{
			TreeNode<E> tempNode = new TreeNode<E>(value);
			root = tempNode;
		}
		else
		{
			addR(value, root); // Recursive
			//addI(value); // Iterative
		}
	}
	
	/**
     * Recursive helper method to add a node to the tree
     * 
     * @param node the current node being checked
     * @param value the value to add
     */
	public void addR(E value, TreeNode<E> node)
	{
		if(value.compareTo(node.getValue()) > 0)
		{
			if(node.getRight() != null)
				addR(value, node.getRight());
			else
			{
				TreeNode<E> tempNode = new TreeNode<E>(value);
				node.setRight(tempNode);
			}
		}
		else
		{
			if(node.getLeft() != null)
				addR(value, node.getLeft());
			else
			{
				TreeNode<E> tempNode = new TreeNode<E>(value);
				node.setLeft(tempNode);
			}
		}
	}
	
	/**
     * Iterative helper method to print the tree inorder
     *
     * @param node the current node being visited
     */
	public void addI(E value)
	{
		TreeNode<E> newNode = new TreeNode<E>(value);
		TreeNode<E> cur = root;

		while(true)
		{
			if(value.compareTo(current.getValue()) > 0)
			{
				if(cur.getRight() != null)
					cur = cur.getRight();
				else
				{
					cur.setRight(newNode);
					break;
				}
			}
			else
			{
				if(cur.getLeft() != null)
					cur = cur.getLeft();
				else
				{
					cur.setLeft(newNode);
					break;
				}
			}
		}
	}
	
	/**
	 *	Print Binary Tree Inorder
	 */
	public void printInorder()
	{	
		printInRec(root);
        System.out.println();
	}
	
	/**
     * Recursive helper method to print the tree inorder
     * 
     * @param node the current node being visited
     */
	public void printInRec(TreeNode<E> node)
	{
		if(node != null)
		{
            printInRec(node.getLeft());
            System.out.print(node.getValue() + " ");
            printInRec(node.getRight());
        }
	}
	
	/**
     * Print Binary Tree Preorder
     */
    public void printPreorder()
    {
        printPreRec(root);
        System.out.println();
    }

    /**
     * Recursive helper method to print the tree preorder
     *
     * @param node the current node being visited
     */
    private void printPreRec(TreeNode<E> node)
    {
        if(node != null)
        {
            System.out.print(node.getValue() + " ");
            printPreRec(node.getLeft());
            printPreRec(node.getRight());
        }
    }
	
	/**
     * Print Binary Tree Postorder
     */
    public void printPostorder()
    {
        printPostRec(root);
        System.out.println();
    }

    /**
     * Recursive helper method to print the tree postorder
     * 
     * @param node the current node being visited
     */
    private void printPostRec(TreeNode<E> node)
    {
        if(node != null)
        {
            printPostRec(node.getLeft());
            printPostRec(node.getRight());
            System.out.print(node.getValue() + " ");
        }
    }
    
	/** Return a balanced version of this binary tree
     * 
     * @return the balanced tree
     */
    public BinaryTree<E> makeBalancedTree()
    {
        List<E> values = new ArrayList<>();
        storeInorder(root, values);

        BinaryTree<E> balancedTree = new BinaryTree<>();
        balancedTree.root = buildBalancedTree(values, 0, values.size() - 1);

        return balancedTree;
    }

    /**
     * Recursive helper method to store the inorder traversal of the tree
     * 
     * @param node the current node being visited
     * @param values the list to store the values
     */
    private void storeInorder(TreeNode<E> node, List<E> values)
    {
        if (node != null)
        {
            storeInorder(node.getLeft(), values);
            values.add(node.getValue());
            storeInorder(node.getRight(), values);
        }
    }

    /**
     * Recursive helper method to build a balanced tree from a sorted list of values
     * 
     * @param values the sorted list of values
     * @param start the start index of the current sublist
     * @param end the end index of the current sublist
     * @return the root node of the balanced tree
     */
    private TreeNode<E> buildBalancedTree(List<E> values, int start, int end)
    {
        if (start > end)
            return null;

        int mid = (start + end) / 2;
        TreeNode<E> node = new TreeNode<>(values.get(mid));

        node.setLeft(buildBalancedTree(values, start, mid - 1));
        node.setRight(buildBalancedTree(values, mid + 1, end));

        return node;
    }
	
	/**
	 *	Remove value from Binary Tree
	 *	
	 * @param value		the value to remove from the tree
	 *	Precondition: value exists in the tree
	 */
	public void remove(E value)
	{
		root = remove(root, value);
	}
	
	/**
	 *	Remove value from Binary Tree
	 *	
	 * @param node			the root of the subtree
	 * @param value		the value to remove from the subtree
	 * @return				TreeNode that connects to parent
	 */
	public TreeNode<E> remove(TreeNode<E> node, E value)
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
				TreeNode<E> minNode = findMin(node.getRight());
				// Replace the current node's value with the minimum value
				node.setValue(minNode.getValue());
				// Remove the minimum node from the right subtree
				node.setRight(remove(node.getRight(), minNode.getValue()));
			}
		}

		return node;
	}
	
	private TreeNode<E> findMin(TreeNode<E> node)
	{
		if(node.getLeft() == null)
			return node; // Found the minimum value

		return findMin(node.getLeft()); // Continue searching in the left subtree
    }

	/*******************************************************************************/	
	/********************************* Utilities ***********************************/	
	/*******************************************************************************/	
	/**
	 *	Print binary tree
	 *	@param root		root node of binary tree
	 *
	 *	Prints in vertical order, top of output is right-side of tree,
	 *			bottom is left side of tree,
	 *			left side of output is root, right side is deepest leaf
	 *	Example Integer tree:
	 *			  11
	 *			/	 \
	 *		  /		   \
	 *		5			20
	 *				  /	  \
	 *				14	   32
	 *
	 *	would be output as:
	 *
	 *				 32
	 *			20
	 *				 14
	 *		11
	 *			5
	 ***********************************************************************/
	public void printTree()
	{
		printLevel(root, 0);
	}
	
	/**
	 *	Recursive node printing method
	 *	Prints reverse order: right subtree, node, left subtree
	 *	Prints the node spaced to the right by level number
	 *	@param node		root of subtree
	 *	@param level	level down from root (root level = 0)
	 */
	private void printLevel(TreeNode<E> node, int level)
	{
		if (node == null) return;
		// print right subtree
		printLevel(node.getRight(), level + 1);
		// print node: print spaces for level, then print value in node
		for (int a = 0; a < PRINT_SPACES * level; a++) System.out.print(" ");
		System.out.println(node.getValue());
		// print left subtree
		printLevel(node.getLeft(), level + 1);
	}
}
