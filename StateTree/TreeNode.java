/**
 *	A Tree Node for a binary tree.
 */
public class TreeNode<E extends Comparable<E>>
{
	private E value;
	private TreeNode<E> left;
	private TreeNode<E> right;

	public TreeNode(E initValue)
	{
		value = initValue; 
		left = null;
		right = null; 
	}

	public TreeNode(E initValue, TreeNode<E> initLeft, TreeNode<E> initRight)
	{
		value = initValue; 
		left = initLeft;
		right = initRight; 
	}

	// Accessors and modifiers
	public E getValue() { return value; }

	public TreeNode<E> getLeft() { return left; }

	public TreeNode<E> getRight() { return right; }

	public void setValue(E theNewValue) { value = theNewValue; }

	public void setLeft(TreeNode<E> theNewLeft) { left = theNewLeft; }

	public void setRight(TreeNode<E> theNewRight) { right = theNewRight;}
}