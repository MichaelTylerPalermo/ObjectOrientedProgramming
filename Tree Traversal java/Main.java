/*
This code was created to help understand how to implement the 
three types of tree traversal in java 
*/
public class Main {
	
	public static void main(String[] args) {
		Node data = createData();
		preorderTraversal(data);
	}
	
	public static void preorderTraversal(Node n) {
		if(n==null) 
			return;
		System.out.println(n.value+ " ");
		preorderTraversal(n.left);
		preorderTraversal(n.right);
	}
	
	public static void postorderTraversal(Node n) {
		if(n==null) 
			return;
		postorderTraversal(n.left);
		postorderTraversal(n.right);
		System.out.println(n.value+" ");
		
	}
	
	public static void inorderTraversal(Node n) {
		if(n==null) 
			return;
		inorderTraversal(n.left);
		System.out.println(n.value+" ");
		inorderTraversal(n.right);
	}
	
	public static Node createData() {
		Node a = new Node("a");
		Node b = new Node("b");
		Node c = new Node("c");
		Node d = new Node("d");
		Node e = new Node("e");
		Node f = new Node("f");
		Node g = new Node("g");
		
		a.left = b;
		a.right = c;
		c.left = d;
		c.right = e;
		d.right = f;
		f.left = g;
		
		return a;
	}
}
