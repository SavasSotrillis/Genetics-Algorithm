import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BruteForce 
{
	static ArrayList<Integer[]> subsets=new ArrayList<Integer[]>();
	static ArrayList<Integer[]> solution_space=new ArrayList<Integer[]>();
	static ArrayList<Integer[]> solutions=new ArrayList<Integer[]>(); 
	
	public BruteForce() //this is just setting up our initial variables using the data we already gathered in Solution_Space and ReadFile Class
	{
		for(int i=0; i<Solution_Space.solution_space.size(); i++)
		{
			solution_space.add(Solution_Space.solution_space.get(i));
		}
		for(int i=0; i<ReadFile.subsets.size(); i++)
		{
			subsets.add(ReadFile.subsets.get(i));
		}
		find_solution();
	}
	public void find_solution() // this function is to find all values in our search space which are solutions to our current problem
	{
		Boolean solution_found=true;
		Boolean subset_match=false;
		int solution_length=-1;
		for(int j=0; j<solution_space.size(); j++) //loop to check every subset in our search space for multiple answers
		{
			if(solution_length!=-1 && solution_space.get(j).length!=solution_length) break; //if solution was found and current subset is larger then previous subset, break
			solution_found=true;
			for(int i=0; i<subsets.size(); i++) //goes through all subsets from the input
			{
				subset_match=false;
				for(int k=0; k<solution_space.get(j).length; k++) //goes over every values in the current subset of the search space 
				{
					for(int w=0; w<subsets.get(i).length; w++) //goes through every value in the current subset of our input file
					{
						if(solution_space.get(j)[k]==subsets.get(i)[w]) //if the current subset of our search space, the current index value  hits current array value of our input subset, set boolean value true
						{
							subset_match=true;
						}
					}
					
				}
				if(subset_match==false) //if no matches for current subset to current subset of search space then go to next subset in our search space.
				{
					i=subsets.size();
					solution_found=false;
				}
			}
			if(solution_found==true) //if solution is found, add to our solutions array list to store it
			{
				solutions.add(solution_space.get(j));
				solution_length=solution_space.get(j).length;
			}
		}
//		System.out.println("All the minimal hitting sets for this input are ");
		for(Integer[] arr : solutions)
		{
			//System.out.println(Arrays.toString(arr));
		}
		printFile();
		
	}
	public void printFile() //prints to output file
	{
		try 
		{


			FileWriter writer = new FileWriter(ReadFile.brute_force_file); //C:\\Users\\Studyroom 2\\Documents\\cs381 eclipse\\Hitting set brute force\\src\\small_output_brute_force.txt
			writer.write("All solutions for the hitting set of minimal length for this input are \n");
			for(Integer[] arr : solutions) //writes all solutions to output file
			{
				//System.out.println(Arrays.toString(arr));
				writer.write(Arrays.toString(arr) + "\n");
			}
			writer.close();
			System.out.println("Successfully");
		}
		catch (IOException e) 
		{
			System.out.println("Error");
	    }
	}
}
