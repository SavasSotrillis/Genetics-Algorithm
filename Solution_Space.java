import java.util.ArrayList;
import java.util.Arrays;

public class Solution_Space 
{
	
	public int size=0;
	static ArrayList<Integer[]> solution_space= new ArrayList<Integer[]>(); //stores all possible solution, even solutions that are incorrect, this is my search space.

	public Solution_Space()
	{
		
		set_size();
		solutions();
		
	}	
	public void set_size() //I realized our largest solution can not be larger then the largest subset.
	{
		for(int i=0; i<ReadFile.subsets.size(); i++)
		{
			if(ReadFile.subsets.get(i).length>size)
			{
				size=ReadFile.subsets.get(i).length;
			}
		}
	}
	public void solutions()
	{
		// was trying to do it with loops since i prefer loops then realized the difficulity, so I switched to a recursive function
		/*for(int i=1; i<=size; i++)//the sizes of our array 
		{
			
				int adder=0;
				for(int k=0; k<reader.elements.size(); k++) //the values being inputted
				{
						int w=k;
						adder++;
						while(w<reader.elements.size()+k)
						{
							Integer temp_solution[]= new Integer[i];
							for(int j=0; j<i; j++) //the input into the array
							{
								temp_solution[j]=reader.elements.get(w%reader.elements.size());
								w=w+adder;
							}
							solution_space.add(temp_solution);
						}	
				}
		}*/
		for(int i=1; i<=size; i++)
		{
			get_combinations(new Integer[i], 0, ReadFile.elements.size(), 0); //recursive function to get all values in our search space
		}
		for(Integer[] arr : solution_space)
		{
			//System.out.println(Arrays.toString(arr));
		}
		
	}
	
	public void get_combinations(Integer arr[], int element_indexs, int end, int array_pos) //I realized that the solution space is just the combination of the elements. 
	{																						
		if(arr.length==array_pos) //if array is full then add to solution sets
		{
			Integer[] final_arr=new Integer[arr.length];
			for(int i=0; i<arr.length; i++)
			{
				final_arr[i]=arr[i]; //needed for a pass by reference issue
			}
			solution_space.add(final_arr);    
			return;
		}
		if(element_indexs>=end) //if we went through all the elements, then return since no more values to go over
		{
			return;
		}
		arr[array_pos]=ReadFile.elements.get(element_indexs);// adds our value into our array
		get_combinations(arr, element_indexs+1, end, array_pos+1);  //we need a case were our current index stays the same
		get_combinations(arr, element_indexs+1, end, array_pos);  //we also need a case were our current index changes next recursion
	}
}
