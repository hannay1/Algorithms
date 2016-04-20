package qmc;

import java.io.*;
import java.util.Random;


public class Queens {
	
	static int node_count = 0;
	static int sol_count = 0;
	static final int num_trials = 1000;
	
	
	
	
	
	public static int[] board_maker(int n)
	{
		int[] board = new int[n];
		return board;
	}
	

	
	
	public static int queen(int i, int[] board)
	{
		int n = board.length;
		node_count++; //number of nodes visited in state space tree
		if(promising(i, board))
		{
			if(i == n -1)
			{
				//System.out.println(Arrays.toString(board));
				sol_count++;
				
			}
			else
			{
				
				for(int j = 1; j< n; j++)
				{
					board[i+1] = j;
					queen(i+1, board);
					
				}
					
			}
		}
		//System.out.println(sol_count);
		return sol_count; //return number of solutions
		
	}
	
	public static boolean promising(int i, int[] board)
	{
		boolean sw;
		int k = 1;
		
		sw = true;
		while(k <i && sw)
		{
			if(board[i] == board[k] || Math.abs((board[i] - board[k])) == i-k)
			{
				sw = false;
				
				
			}
			
			k++;
		}
		return sw;
	}

	public static int estimate_n_queens(int[] board)
	{
		int n = board.length;
		int i = 0;
		int m = 1;
		int j = 0;
		int mprod = 1;
		int numnodes = 1;
		Random gen = new Random();
		
	
		int[] maybe = new int[n];
		while(m != 0 && i != n -1)
		{
			mprod = mprod * m;
			numnodes = numnodes + mprod * n;
			i++;
			m = 0;
			maybe = new int[n];
			int k = 0;
			for(j = 1; j< n; j++)
			{
				board[i] = j;
				if(promising(i, board))
				{
					m++;
					maybe[k] =board[i];
					k++;
				}
			}
			if(m != 0)
			{
				int rand = gen.nextInt(k);
				j = maybe[rand];
				board[i] = j;
			}
		}
		return numnodes;
		

	}
	
	public static double avg_estimate_n(int trials, int[] board)
	{
		queen(0, board);
		int q = 0;
		int tot = 0; //change to long for large # of trials.
		
		for(int i = 0; i <trials; i++)
		{
			q = estimate_n_queens(board);
			tot += q;
		}
		
		double avg = (double)tot / (double)trials;
		
		return avg;
		
		
	}

	
	public static void main(String[] args)
	{
		String csv_file = "monte.csv";
		
		
		int[] sol = new int[7];
		double[] avgs = new double[7];
		int n = 5; // ignore arr[0], so 5 = 4, n = n-1. this is a feature, not a bug.
		for(int i = 0; i <= 6;i++)
		{
			node_count = 0;
			avgs[i] = avg_estimate_n(num_trials, board_maker(n));
			sol[i] = node_count;
			n++;
	
		}
		
		try
		{
			FileWriter fw = new FileWriter(csv_file);
	        PrintWriter out = new PrintWriter(fw);
	        
	        out.print("n");
	        out.print(',');
	        out.print("avg number of nodes");
	        out.print(',');
	        out.print("number of nodes in state space tree");
	        out.print("\n");
	        out.print(4);
	        out.print(',');
	        out.print(avgs[0]);
	        out.print(',');
	        out.print(sol[0]);
	        out.print("\n");
	        out.print(5);
	        out.print(',');
	        out.print(avgs[1]);
	        out.print(',');
	        out.print(sol[1]);
	        out.print("\n");
	        out.print(6);
	        out.print(',');
	        out.print(avgs[2]);
	        out.print(',');
	        out.print(sol[2]);
	        out.print("\n");
	        out.print(7);
	        out.print(',');
	        out.print(avgs[3]);
	        out.print(',');
	        out.print(sol[3]);
	        out.print("\n");
	        out.print(8);
	        out.print(',');
	        out.print(avgs[4]);
	        out.print(',');
	        out.print(sol[4]);
	        out.print("\n");
	        out.print(9);
	        out.print(',');
	        out.print(avgs[5]);
	        out.print(',');
	        out.print(sol[5]);
	        out.print("\n");
	        out.print(10);
	        out.print(',');
	        out.print(avgs[6]);
	        out.print(',');
	        out.print(sol[6]);
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
