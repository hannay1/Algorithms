package sorts;
import java.io.*;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sorter {
  
  static int count = 0;
  
  static int p = 0;
    static int counter = 0;

    public static int[] switcher(int[] arr1, int[] arr2)
    {
        //this method simply transfrs the same value from 
        //one array to the other, needed for preserving the same
        //array
        for(int i = 0; i < arr2.length; i++)
        {
            arr2[i] = arr1[i];
        }

        return arr2;
    }

	public static double exchangeSort(int[] arr, int rand_point)
	{
		int n = arr.length;
		int countr = 0;

		if(rand_point > 0)
		{
			n = rand_point;
		}

		/*for n- 1 iterations(arr[n] will already be sorted after n-1 passes),
		compare number in ith slot with number in [j = i + 1]th slot from j to n 
		and exchange if arr[i] > arr[j]*/


		long start = System.nanoTime();
		for(int i = 0; i < n- 1; i++)
		{
			for(int j = i + 1; j < n; j++)
			{
				if(arr[j] < arr[i])
				{
					//exchange arr[i] and arr[j]
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
					countr++; //counts number of swaps = (n(n-1))/2 in worst case
				}
			}
		}
		long end = System.nanoTime();
		long time = end - start;
		double sec = (double) time / 1000000000.0;
		return sec;
		

	}


      public static double mergeSort(int[] arr, int left , int right)
    {


        long start = System.nanoTime();
        if(left < right)
        {
            int mid =  (right + left) / 2; //Step 1
            mergeSort(arr, left, mid);
            mergeSort(arr, mid +1, right);
            merge(arr, left, mid, right);

        }
        long end = System.nanoTime();
        double sec = (double) (end - start) / 1000000000.0;
        return sec;   
    }

    public static void merge(int[] arr, int left, int mid, int right)
    {
        int[] temp = new int[arr.length];
        temp = switcher(arr, temp); //temporary array containing elements in original 
        int i = left; //pointer to first in temporary array
        int j = mid +1; //pointer to middle of temporary array
        int k = left; //pointer to the index in original array

        while(i <= mid && j <= right) //while i is before middle and end is greater than one up from middle
        {
            if(temp[j] > temp[i]) //if the first index in the first part is less than the first in the second,
            {
                arr[k++] = temp[i++]; //fill the original array with the first in the FIRST part of temp
                //count++;
            }else
            {
                arr[k++] = temp[j++]; //or, if the first in the second is greater than the first of the first part, 
                //fill the original with the first in the second
                //count++;
            }  
        }
        //now copy temporary arrays back into original array
        for(int x = i; x <= mid; x++)
        { //copy all from temp array until the mid point into the original array
            arr[k++] = temp[x];
        }

        
        for(int y = j; y <= right; y++)
        { //copy all from j until the end of the temp into the original array
            arr[k++] = temp[y];
        }    
    }


    public static int[] arrayMaker(int arr_size, boolean sorted)
  {
    int[] arr = new int[arr_size];
    Random gen = new Random();

    for(int i = 0; i <arr_size; i++)
    {
      arr[i] = i;
    }

    for(int j : arr)
    {
      int rand = gen.nextInt(arr_size);
      int temp = arr[j];
      arr[j] = arr[rand];
      arr[rand] = temp;
    }

        if(sorted)
            arr = exchangeSort(arr,0); 
    return arr;

  }

    public static int[] rev_exchangeSort(int[] arr)
    {
        //used to make a reverse order list
        int n = arr.length;
        for(int i = 0; i < n- 1; i++)
        {
            for(int j = i + 1; j < n; j++)
            {
                
                if(arr[j] > arr[i])
                {
                    //exchange arr[i] and arr[j]
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
        

    }

    


    public static double quickSort(int[] arr, int left, int right) 
    {
        long start = System.nanoTime();

        if(right > left)
        {

            part(arr,left, right);
            quickSort(arr, left, p -1);
            quickSort(arr, p + 1, right);
        }

        long end = System.nanoTime();
        double sec = (double) (end - start) / 1000000000.0;

        //System.out.println(Arrays.toString(arr));
        return sec;

    }
    
    
    public static void part(int[] arr, int left, int right)
    {
        int pi = arr[left]; //pivotpoint
        int i, j;
        j = left;
        int temp;
        for(i = left +1; i<=right;i++)
        {
            
            if(arr[i] < pi)
            {
                
                j++;
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                
            } 
        }
        p = j;
            temp = arr[left];
            arr[left] = arr[p];
            arr[p] = temp;
            
           
    }
    


    public static void main(String[] args)
    {

        String merge_exchange = "merge_exchange.csv";
       
        int[] arr1000 = arrayMaker(10,true);
        //arr1000 = rev_exchangeSort(arr1000);
        //System.out.println("exchange sort: " + Arrays.toString(arr1000));

        

        //this is why i installed 16gb of ram.
        int[] m_temp_0 = new int[arr1000.length];
        int[] q_temp_0 = new int[arr1000.length];
        int[] e_temp_0 = new int[arr1000.length];
        
        m_temp_0 = switcher(arr1000, m_temp_0);
        q_temp_0 = switcher(arr1000, q_temp_0);
        e_temp_0 = switcher(arr1000, e_temp_0);
       

        double m_res_0 = mergeSort(m_temp_0, 0 , m_temp_0.length -1);
        double q_res_0 = quickSort(q_temp_0, 0 , q_temp_0.length -1);
        double e_res_0 = exchangeSort(e_temp_0, 0);
      
       
       
       
        try
        {
           FileWriter fw = new FileWriter(merge_exchange);
           PrintWriter out = new PrintWriter(fw);
           out.print("threshold value");
           out.print(',');
           out.print("Exchange_(secs)");
           out.print(',');
           out.print("Quick time (secs)");
           out.print("\n");
           out.print(0);
           out.print(',');
           out.print(merg_res_0);
           out.print(',');
           out.print(q_res_0);
           out.print("\n");
           out.print(10);
           out.print(',');
           out.print(merg_res_10);
           out.print(',');
           out.print(q_res_10);
           out.print("\n");
           out.print(20);
           out.print(',');
           out.print(merg_res_20);
           out.print(',');
           out.print(q_res_20);
           out.print("\n");
           out.print(50);
           out.print(',');
           out.print(merg_res_50);
           out.print(',');
           out.print(q_res_50);
           out.print("\n");
           out.print(100);
           out.print(',');
           out.print(merg_res_100);
           out.print(',');
           out.print(q_res_100);
           out.print("\n");
           out.print(200);
           out.print(',');
           out.print(merg_res_200);
           out.print(',');
           out.print(q_res_200);
           out.print("\n");
           out.print(500);
           out.print(',');
           out.print(merg_res_500);
           out.print(',');
           out.print(q_res_500);
           out.print("\n");
           out.print(1000);
           out.print(',');
           out.print(merg_res_1000);
           out.print(',');
           out.print(q_res_1000);
           out.print("\n");
           
        

           out.flush();
           out.close();
           fw.close();
       

        }catch (IOException e)
        {
            e.printStackTrace();
        }

        
        
    }
}