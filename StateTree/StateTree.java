import java.util.Scanner;

/**
 * The driver program for handling a binary tree of state information.
 */
public class StateTree
{
	public static void main ( String [] args )
	{
		StateTree treeorder = new StateTree();
		treeorder.mainMenu();
	}
 
	public void mainMenu ()
	{
		BinaryTree tree = new BinaryTree();

		String choice;
		Scanner console = new Scanner(System.in);

		do
		{
			System.out.println("Binary Tree algorithm menu\n");
			System.out.println("(1) Read Data from a file");
			System.out.println("(2) Print the list");
			System.out.println("(3) Search the list");
			System.out.println("(4) Delete node");
			System.out.println("(5) Count nodes");
			System.out.println("(6) Clear the list");
			System.out.println("(7) Print the level");
			System.out.println("(8) Print depth of tree");
			System.out.println("(Q) Quit\n");
			System.out.print("Choice ---> ");
			choice = console.nextLine() + " ";
			System.out.println();

			if ('1' <= choice.charAt(0) && choice.charAt(0) <= '8')
			{
				switch (choice.charAt(0))
				{
					case '1' :	
						tree.loadData();		
						break;
					case '2' :
						System.out.println();
						System.out.println("The tree printed inorder\n");
						tree.printList();
						System.out.println();
						break;
					case '3' :
						tree.testFind();
						break;
					case '4' :
						tree.testDelete();
						break;
					case '5' :
						System.out.println("Number of nodes = " + tree.size());
						System.out.println();
						break;
					case '6' :
						tree.clear();
						break;
					case '7' :
						tree.printLevel();
						break;
					case '8' :
						tree.testDepth();
						break;
				}
			}
		}
		while (choice.charAt(0) != 'Q' && choice.charAt(0) != 'q');
	}
}
