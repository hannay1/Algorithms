package h_man_tree;


public class Node implements Comparable<Node> 
{
	public char key; //key
	public int prob; //value
	public Node left= null; //child nodes
	public Node right = null; 
	
	public Node(){}

	

	public boolean leaf()
	{
		if(this.left == null && this.right == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int compareTo(Node node)
	{
		return prob - node.prob; //natural comparison method for Comparable. 
		
	}
	

	

}
