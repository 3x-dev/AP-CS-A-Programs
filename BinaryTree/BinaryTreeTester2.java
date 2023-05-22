/**
 *	Binary Tree tester for removing nodes
 *
 *	@author	Mr Greenstein
 */
public class BinaryTreeTester2 {

	BinaryTree<Integer> tree;		// the binary tree
	
	public BinaryTreeTester2() {
		tree = new BinaryTree<Integer>();
	}
	
	public static void main(String[] args) {
		BinaryTreeTester2 btt2 = new BinaryTreeTester2();
		btt2.run();
	}
	
	public void run() {
		// make a tree like this
		//                              47
		//                  23                        74
		//          14              35          59          83
		//      5       19       28         53      66          97
		//                    25    33
		//
		int[] vals = { 47, 23, 14, 5, 19, 35, 28, 25, 33, 74, 59, 53, 66, 83, 97 };
		for (int v : vals) {
			tree.add(v);
		}
		tree.printTree();
		System.out.println("*********************");
		
		// Test 1: Remove leaf node 19
		System.out.println("Test 1: Remove leaf node 19");
		tree.remove(19);
		tree.printTree();
		System.out.println("*********************");

		// Test 2: Remove node 74 whose right subtree root has no left
		System.out.println("Test 2: Remove node 74 whose right subtree root has no left");
		tree.remove(74);
		tree.printTree();
		System.out.println("*********************");
		
		// Test 3: Remove node 23 whose right subtree root has a left
		System.out.println("Test 3: Remove node 23 whose right subtree root has a left");
		tree.remove(23);
		tree.printTree();
		System.out.println("*********************");
		
		// Test 4: Remove the root node
		System.out.println("Test 4: Remove the root node 47");
		tree.remove(47);
		tree.printTree();
		System.out.println("*********************");
		
	}
}