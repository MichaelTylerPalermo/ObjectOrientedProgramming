
public class Node {
	String value;
	Node left;
	Node right;
	Node adjacents;
	
	public Node(String value) {
		this.value = value;
		this.adjacents = null;
	}
}
