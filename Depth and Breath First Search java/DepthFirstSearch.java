import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class DepthFirstSearch {
	
	public static void dfsPrint(Node start) {
		Stack<Node> stack = new Stack<>();		//create a stack for nodes
		Set<Node> seen = new HashSet<>();		//data structure for marking visited nodes
		stack.push(start);		//push the first node to the stack
		while(!stack.isEmpty()) {		//while the stack is not empty
			Node curr = stack.pop();	//pull a node
			if(!seen.contains(curr)) {		//if we haven't seen the current node...
				seen.add(curr);			//add it to seen hash
				System.out.println(curr);	//display it
			}
			for(Node adjacent: curr.adjacents) {	//iterate adjacent nodes
				if(!seen.contains(adjacent)) {		//if not seen...
					stack.push(adjacent);		//push it to the stack
				}
			}
			
		}
	}


	public static void bfsPrint(Node start) {
		Queue<Node> queue = new LinkedList<>();		//create a stack for nodes
		Set<Node> seen = new HashSet<>();		//data structure for marking visited nodes
		queue.add(start);		//push the first node to the queue
		while(!queue.isEmpty()) {		//while the queue is not empty
			Node curr = queue.poll();	//pull a node
			if(!seen.contains(curr)) {		//if we haven't seen the current node...
				seen.add(curr);			//add it to seen hash
				System.out.println(curr);	//display it
			}
			for(Node adjacent: curr.adjacents) {	//iterate adjacent nodes
				if(!seen.contains(adjacent)) {		//if not seen...
					queue.add(adjacent);		//push it to the queue
				}
			}
			
		}
	}
}
	