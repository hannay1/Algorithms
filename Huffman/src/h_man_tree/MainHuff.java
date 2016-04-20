package h_man_tree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class MainHuff {
	
	
	static  List<String> lines; //will hold original lines of file
	static String[] encoded_lines; // will hold encoded lines
	
	
	public static String loadString(String test_f)
	{
		
		String line = "";
		String text = "";
		lines = new ArrayList<String>();
		try
		{
			FileReader fr = new FileReader(test_f); 
	        BufferedReader br =  new BufferedReader(fr);
	        while((line = br.readLine()) != null) 
	        {
	        	lines.add(line);
	        }
  			br.close();  
		}
		catch(IOException e)
		{
			System.err.println("error - file not read: " + e.getMessage());
		}
		
		 for(String s : lines)
	     {
			 text += s;
	     }
		 
		 return text;
	}
	
	public static HashMap<Character, Integer> makeTable(String text)
	{
		HashMap<Character, Integer> frequencyMap = new HashMap<Character, Integer>();
		for(int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			Integer val = frequencyMap.get(new Character(c));
			if(val != null)
			{
				frequencyMap.put(c, val + 1);
			}else
			{
				frequencyMap.put(c, 1);
			}
		}
		return frequencyMap;
	}
	
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String test_file1 = "test.txt"; // test file for proof that this works
		String test_file2 = "lorem.txt"; //lorem ipsum
		String test_file3 = "actual.txt"; //actual text
		String test_file4 = "random.txt"; //random chars with no sequence
			
		String trial1 = loadString(test_file1); //load into line[]
		String trial2 = loadString(test_file2);
		String trial3 = loadString(test_file3);
		String trial4 = loadString(test_file4);
					
		HashMap<Character, Integer> fq1 = new HashMap<Character, Integer>();
		HashMap<Character, Integer> fq2 = new HashMap<Character, Integer>();
		HashMap<Character, Integer> fq3 = new HashMap<Character, Integer>();
		HashMap<Character, Integer> fq4 = new HashMap<Character, Integer>();
		
		fq1 = makeTable(trial1);
		fq2 = makeTable(trial2);
		fq3 = makeTable(trial3);
		fq4 = makeTable(trial4);

		Tree t1 = new Tree(fq1);
		Tree t2 = new Tree(fq2);
		Tree t3 = new Tree(fq3);
		Tree t4 = new Tree(fq4);
	
		HashMap<Character, String> em1 = t1.make_encode_map();
		HashMap<Character, String> em2 = t2.make_encode_map();
		HashMap<Character, String> em3 = t3.make_encode_map();
		HashMap<Character, String> em4 = t4.make_encode_map();
		
		String encodedS1 = t1.encode(trial1, em1);
		int test_e = encodedS1.length();
		String decodedS1 = t1.decode_word(encodedS1);
		int test_d = decodedS1.length() * 2 * 8;
		
		String encodedS2 = t2.encode(trial2, em2);
		int lorem_e = encodedS2.length();
		String decodedS2 = t2.decode_word(encodedS2);
		int lorem_d = decodedS2.length() * 2 * 8;
		
		String encodedS3 = t3.encode(trial3, em3);
		int actual_e = encodedS3.length();
		String decodedS3 = t3.decode_word(encodedS3);
		int actual_d = decodedS3.length() * 2 * 8;
		
		String encodedS4 = t4.encode(trial4, em4);
		int random_e = encodedS4.length();
		String decodedS4 = t4.decode_word(encodedS4);
		int random_d = decodedS4.length() * 2 * 8;
			
		int depth_a = t1.getDepth(t1.root, 'A', 1); //start at level 1. gets the depth of the leaf with a specified char
		int depth_b = t1.getDepth(t1.root, 'B', 1); 
		int depth_c = t1.getDepth(t1.root, 'C', 1); 
		int depth_d = t1.getDepth(t1.root, 'D', 1);
		
		int test_bit_check = t1.bit_check(fq1, t1.root);
		int lorem_bit_check = t2.bit_check(fq2, t2.root);
		int actual_bit_check = t3.bit_check(fq3, t3.root);
		int random_bit_check = t4.bit_check(fq4, t4.root);
		
		
		String sol_random = "randomwords.csv";
		
		System.out.println("test string:" + trial1);
		System.out.println("frequency table: " + fq1);
		System.out.println("file size: " + trial1.length() * 2 * 8 + " bits");
		System.out.println("encoded test file: " + encodedS1);
		System.out.println("decoded test file: " + decodedS1);
		System.out.println("encoded size: " + test_e + " bits");
		System.out.println("verified size of encoded string: " + test_bit_check + " bits");
		System.out.println("decoded size: " + test_d + " bits");
		System.out.println("depth of character 'A': " + depth_a);
		System.out.println("depth of character 'B': " + depth_b);
		System.out.println("depth of character 'C': " + depth_c);
		System.out.println("depth of character 'D': " + depth_d);
		
		try
		{
			FileWriter fw = new FileWriter(sol_random);
	        PrintWriter out = new PrintWriter(fw);
	        
	        out.print("Input");
	        out.print(',');
	        out.print("Encoded size (bits)");
	        out.print(',');
	        out.print("Verified encoded size (bits)");
	        out.print(',');
	        out.print("Decoded/original size (bits)");
	        out.print(',');
	        out.print("Original file size");
	        out.print("\n");
	        
	        out.print("test file (aaabbc)");
	        out.print(',');
	        out.print(test_e);
	        out.print(',');
	        out.print(test_bit_check);
	        out.print(',');
	        out.print(test_d);
	        out.print(',');
	        out.print(trial1.length() * 2 * 8);
	        out.print("\n");
	        
	        out.print("lorem");
	        out.print(',');
	        out.print(lorem_e);
	        out.print(',');
	        out.print(lorem_bit_check);
	        out.print(',');
	        out.print(lorem_d);
	        out.print(',');
	        out.print(trial2.length() * 2 * 8);
	        out.print("\n");
	        
	        out.print("actual text");
	        out.print(',');
	        out.print(actual_e);
	        out.print(',');
	        out.print(actual_bit_check);
	        out.print(',');
	        out.print(actual_d);
	        out.print(',');
	        out.print(trial3.length() * 2 * 8);
	        out.print("\n");
	        
	        out.print("random chars");
	        out.print(',');
	        out.print(random_e);
	        out.print(',');
	        out.print(random_bit_check);
	        out.print(',');
	        out.print(random_d);
	        out.print(',');
	        out.print(trial4.length() * 2 * 8);
	        out.print("\n");
	         
	        out.flush();
	        out.close();
	        fw.close();
	      
	        
		}
		catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    

	}

}
