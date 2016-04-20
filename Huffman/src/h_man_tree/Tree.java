package h_man_tree;


import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Tree {
	
	public Node root;
	
	
	public Tree(HashMap<Character, Integer> prob)
	{
		root = encoder(make_pq(prob));
	}
	
	public Node encoder(PriorityQueue<Node> pq)
	{
		
		while(pq.size() > 1)
		{
			Node q = pq.remove();
			Node p = pq.remove();
			Node newNode = new Node();
			newNode.left = p; 
			newNode.right = q;
			int edge_code = q.prob + p.prob;
			newNode.prob = edge_code; 
			pq.add(newNode); 
		}
		root = pq.remove();
		return root;
	}
	
	public PriorityQueue<Node> make_pq(HashMap<Character, Integer> prob)
	{
		PriorityQueue<Node> h_nodes = new PriorityQueue<Node>();
		for(char c : prob.keySet()) 
		{
			Node newNode = new Node();
			newNode.key = c; //key is char
			newNode.prob = prob.get(c); //value corresponds to the key
			h_nodes.add(newNode); //node is now in priority queue
		}
		return h_nodes;	
	}


	public HashMap<Character, String> make_encode_map() 
	{
		HashMap<Character, String> charProbs = new HashMap<Character, String>(); //will hold string k:v paris
		if(!root.leaf())
		{ //if the root is valid, start recursion with an empty path using our map.
			get_enc_map(root,charProbs, ""); //
		}

		return charProbs; //spit back the map for encoding later

	}
	
	public void get_enc_map(Node node,HashMap<Character,String> enc_map, String path)
	{
	
		
			
		if((node.right != null) && (node.left != null))
		{ 
			get_enc_map(node.left,enc_map, path + "0"); //0
			get_enc_map(node.right,enc_map, path + "1"); //1
			
		}
		else
		{
			enc_map.put(node.key, path);
		}
	}

	public String encode(String toEncode,HashMap<Character, String> enc_map)
	{
		String ans = ""; //path 
		for(int i = 0; i< toEncode.length(); i++) //going over the word, 
		{
			 //get the character key at index i-> length
			ans += enc_map.get(toEncode.charAt(i)); //add the corresponding value from the map
		
		}
		
		return ans;
	}

	public String decode_word(String input)
	{
		String ans = ""; //path to leaf 
		Node node = root; //start at root
		for(int i = 0; i< input.length(); i++) // for every character in this string
		{
			char c = input.charAt(i);
			if(c == '1')
			{
				node = node.right; 
				//if the current char (i.e ) of the encoded path 
				//is 1, the go right
			}else
			{ //else state will be 0, go left
				node = node.left;
			}

			if(node.left == null && node.right == null)
			{
			//as a node can have either 0 or 2 children, 
			//need to check for the left/right child is null. 
			//if it is, the node is a leaf

				//concact ans along edges
				ans += node.key;
				node = root;
			}
		}
		return ans;

	}
	
	
	public int getDepth(Node n, char chr, int depth)
	{
		//gets the depth of a node given the character
		
		
		if(n == null)
		{
			return 0;
		}
		
		if(n.key == chr)
		{
			return depth;
		}
		
		int down = getDepth(n.left, chr, depth +1);
		
		if(down != 0)
		{
			return down;
		}
		
		down = getDepth(n.right, chr, depth +1);
		return down;
	}
	
	
	
	public int bit_check(HashMap<Character, Integer> hash, Node n)
	{
		int bits = 0;
		int depth = 0;
		
		//will return the number of bits it takes to encode a file
		
		
		for(Map.Entry<Character, Integer> en : hash.entrySet())
		{
			char c = (char) en.getKey();
			depth = getDepth(n, c, 1);
			depth -= 1;
			int freq = (int) en.getValue();
			int sum = freq * depth;
			bits += sum;
				
		}
		
		
		
		return bits;
		
		
	}
	
}