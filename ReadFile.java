import java.io.File;  
import java.io.FileNotFoundException;  

import java.util.*;

public class ReadFile 
{

	
	public String integer_values="0123456789"; //used in code when reading file to check if current index is an integer

	public static ArrayList<Integer[]> subsets=new ArrayList<Integer[]>(); //stores the subset of our set
	public static ArrayList<Integer> elements=new ArrayList<Integer>(); //stores the set of all available elements
	
	
	public static String brute_force_file;
	
	public static String random_file;
	
	public static String ga_file;
	public ReadFile()
	{
		read_files();
	}
	
	public void read_files() //reads the input file and sets up our initial variables
	{
		try 
	    {
			Scanner myReader=new Scanner(System.in);
			System.out.println("Enter input file name");
			String name= myReader.nextLine();
			System.out.println("Enter output file name for the brute force solution");
			brute_force_file=myReader.nextLine();
			System.out.println("Enter output file name for the random fitness values to be printed too");
			random_file=myReader.nextLine();
			System.out.println("Enter output file name for the ga fitness values to be printed too");
			ga_file=myReader.nextLine();

	    	File myObj = new File(name); //C:\\Users\\Studyroom 2\\Documents\\cs381 eclipse\\Hitting set brute force\\src\\small_input.txt
	    	myReader = new Scanner(myObj);
	    	while (myReader.hasNextLine()) 
	    	{
	    		String data = myReader.nextLine();
	    		String data_temp=data;
	    		
	    		if(data.substring(0, 2).equals("{{")) //checks if it's the sets of the subset
	    		{
	    			int element_counter=0;
	    			if(data_temp.substring(0, 1).equals("{")) data_temp=data_temp.substring(1, data_temp.length());
	    			if(data_temp.substring(0, 1).equals("{")) data_temp=data_temp.substring(1, data_temp.length());
	    			//System.out.println(data_temp);
	    		
	    			Integer[] subset= new Integer[data_temp.length()];
	    			
	    			
	    			while(!data_temp.isEmpty()) //runs till we went through every value on the current line we will be checking the first index, then removing for each if statement
	    			{
	    				if(data_temp.substring(0, 1).contentEquals("}") && data_temp.length()>1 && subset!=null) //when a subset closes
	    				{
	    					int counter=0;
	    					for(int i=0; i<subset.length; i++)
	    					{
	    						if(subset[i]==null) break;
	    						counter++;
	    					}
	    					Integer[] temp= new Integer[counter];
	    					for(int i=0; i<counter; i++)
	    					{
	    						temp[i]=subset[i];
	    					}
	    					element_counter=0;
	    					subsets.add(temp);
	    					data_temp=data_temp.substring(1, data_temp.length());  					
	    				}
	    				else if(integer_values.contains(data_temp.substring(0, 1))) //if the current value is a integer
	    				{
	    					
		    				String temp="";
		    				while(integer_values.contains(data_temp.substring(0, 1))) //checks if an integer is more then one digit, if so, gets the full integer to store
		    				{
		    					
		    					temp=temp+data_temp.substring(0, 1);
		    					data_temp=data_temp.substring(1, data_temp.length());
		    				}
		    				
		    				
		    				subset[element_counter++]=Integer.parseInt(temp);
		    				
		    				
	    				}
	    				else if(data_temp.substring(0, 1).equals("{")) //checks if there a new subset
	    				{
	    					data_temp=data_temp.substring(1, data_temp.length());
	    					subset= new Integer[data_temp.length()];
	    				}
	    				else //if empty space or comma, go to next index
	    				{
	    					data_temp=data_temp.substring(1, data_temp.length());
	    				}
	    				
	    			}
	    			for(Integer[] arr : subsets)
	    			{
	    				//System.out.println(Arrays.toString(arr));
	    			}
	    	    	
	    	    	
	    		}
	    		else if(data.substring(0, 1).equals("{") && (!data.substring(1, 2).equals("{")) ) //checks if it's the set of elements that are used in our subsets
	    		{
	    			
	    			for(int i=0; i<data.length(); i++)
	    			{
		    			if(integer_values.contains(data.substring(i, i+1)))
	    				{
	    					
		    				String temp="";
		    				while(integer_values.contains(data.substring(i, i+1)))
		    				{
		    					
		    					temp=temp+data.substring(i, i+1);
		    					i++;
		    				}
		    				i--;
		    				elements.add(Integer.parseInt(temp));
	    				}
	    				
    				}
	    			for(Integer arr : elements)
	    			{
	    				//System.out.println(arr);
	    			}
	    	
	    		}
	    	}

	    	myReader.close();
	   
	    } 
		
		catch (FileNotFoundException e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
	}
}
