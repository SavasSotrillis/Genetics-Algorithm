import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Random 
{
	//the parameters
	static int pop_size=1000; 
	static int generations=10;
	static double fitness_threshold=0.1;
	static int max_length=ReadFile.elements.size();
	
	//information for fitness values
	static ArrayList<Double[]> brute_force_solutions=new ArrayList<Double[]>(); 
	static int correct_size;
	
	
	
	
	 //varaibles for java random function
	static double average_fitness_javaRandom=0;
	static double max_fitness_javaRandom=0; 
	static double min_fitness_javaRandom=-1;
	
	static double[] best_fitness_javaRandom;
	
	
	//variables for bbs
	double average_fitness_BBS=0;
	double max_fitness_BBS=0; 
	double min_fitness_BBS=-1;
	
	double[] best_fitness_BBS;
		
	
	public Random()
	{
		set_BruteForceSolutions();
		javaRandom();
		BBS(23, 31, 43);  //23, 31, 43 
		printFile();
	}
	public void set_BruteForceSolutions() 
	{
		for(int i=0; i<BruteForce.solutions.size(); i++) // we are storing the BruteForce.solutions variable to a variable in this class but with a slight difference
		{
			Double[] temp_arrayDoubles=new Double[BruteForce.solutions.get(i).length];
			for(int j=0; j<BruteForce.solutions.get(i).length; j++) //for phase 2 we are using a random between 0 and 1, so I turned the input set into doubles between 0 and 1 in a similar manner like it was described to do for the BBS in the phase 2 requirement word documents
			{
				Double temp=(double)BruteForce.solutions.get(i)[j];
				while(temp>1)
				{
					temp=temp/10;
				}
				temp_arrayDoubles[j]=temp;
			}
			
			brute_force_solutions.add(temp_arrayDoubles);
		}
		correct_size=brute_force_solutions.get(0).length;
		
		for(Double[] arr : brute_force_solutions)
		{
			//System.out.println(Arrays.toString(arr));
		}
	}
	//i broke the fitness function into two parts, if the set produced is the right size, and how correct the elements are since hitting set can have a correct solution that is not the best solution
	public static double fitness_function(double[] temp_solution, int current_solution_size)
	{
		double fitness_value_size=1;
		for(int k=0; k<Math.abs(current_solution_size-correct_size); k++) //to calculate the fitness value of the size of the solution
		{
			fitness_value_size=fitness_value_size-(1/(double)max_length); //each size away from the correct solution size, fitness value loses 1 divide the max possible size of solution based on amount of elements provided
		}
		
		double max_temp_element_fitness=0;
		double fitness_value_elements=0;
		
		//the next few for loops are to get the current fitness value of the elements based on all brute force solutions, max this fitness value can be is 1.
		for(int k=0; k<brute_force_solutions.size(); k++)
		{
			fitness_value_elements=0; //resets the current fitness_value_elements to zero if not 1
			for(int w=0; w<brute_force_solutions.get(k).length; w++) //increment for each value in current brute force solution to check if in current random solution
			{
				boolean current_element_found=false;
				for(int x=0; x<temp_solution.length; x++) //check every current random element
				{
					//System.out.println(temp_solution[x]);
					if(temp_solution[x]==brute_force_solutions.get(k)[w])
					{
						fitness_value_elements=fitness_value_elements+(1/((double)correct_size));
						x=temp_solution.length;
						current_element_found=true;
					}
				}
				if(!current_element_found)
				{
					for(int x=0; x<temp_solution.length; x++) //check every current random element
					{
						//get the first digit and only the first digit of the brute force solutions
						double temp_double_brute_force=brute_force_solutions.get(k)[w];
						temp_double_brute_force=temp_double_brute_force*10;
						temp_double_brute_force=(int)(temp_double_brute_force);
						temp_double_brute_force=temp_double_brute_force/10;
						
						//get the first digit and only the first digit of the randoms
						double temp_random=temp_solution[x];
						temp_random=temp_random*10;
						temp_random=(int)(temp_random);
						temp_random=temp_random/10;
						//we are now only checking the first element, but if the first element of a solution is in the current random elements, we only add half to the fitness value of what we normally would
						if(temp_random==temp_double_brute_force)
						{
							//System.out.println(temp_random);
							fitness_value_elements=fitness_value_elements+((1/((double)correct_size))/2);
							x=temp_solution.length;
							current_element_found=true;
						}

					}
				}
				if(fitness_value_elements==1) w=brute_force_solutions.get(k).length;
			}
			
			if(max_temp_element_fitness<fitness_value_elements) max_temp_element_fitness=fitness_value_elements;
			if(max_temp_element_fitness==1) k=brute_force_solutions.size();					
		}
		return max_temp_element_fitness+fitness_value_size;
	}
	//the fitness for all my brute force solutions will be 2
	//The random outputs will have two fitness values, one based on size of the set being produced and another based the random elements themselves.
	public static void javaRandom()
	{
		double fitness_value_size=1.0; //fitness value based on size of the random set
		double fitness_value_elements=0; //fitness value based on the elements
		
		for(int i=0; i<generations; i++) //loop for the number of max generations
		{
			for(int j=0; j<pop_size; j++) //loop to create the population in current generation
			{

				int current_solution_size= (int)(Math.random()*max_length)+1; //to decide the size of current solution
				//System.out.println(current_solution_size);

				double[] temp_solution= new double[current_solution_size];
				for(int k=0; k<current_solution_size; k++) //to create the random elements inside the set
				{
					temp_solution[k]=Math.random();
				}
					
				double combined_fitness=fitness_function(temp_solution,  current_solution_size);
				/*double average_fitness_javaRandom=0;
				double max_fitness_javaRandom=0; 
				double min_fitness_javaRandom;
				
				double[] best_fitness_javaRandom;*/
				average_fitness_javaRandom=average_fitness_javaRandom+combined_fitness; //average fitness
				if(max_fitness_javaRandom<combined_fitness) //checking for new max
				{
					max_fitness_javaRandom=combined_fitness;
					best_fitness_javaRandom=new double[temp_solution.length];
					for(int k=0; k<temp_solution.length; k++)
					{
						best_fitness_javaRandom[k]=temp_solution[k];
					}
					
				}
				if(min_fitness_javaRandom>combined_fitness || min_fitness_javaRandom==-1 )min_fitness_javaRandom=combined_fitness; //checking for new min

			}
		}
		average_fitness_javaRandom=average_fitness_javaRandom/(double)10000; 
		
		//System.out.println(average_fitness_javaRandom);
	}
	
	
	public static double return_double(int e) //used to convert int to double, example 255 to .255
	{
		double element=(double)e;
		while(element>1)
		{
			element=element/(double)10;
		}
		return element;
	}
	
	public void BBS(int p, int q, int x)
	{
		
		int modular=p*q;
		int size_variable=x;
		for(int i=0; i<generations; i++) //loop for the number of max generations
		{
			for(int j=0; j<pop_size; j++) //loop to create the population in current generation
			{
				//to get the solution size for BBS I decided the most fair way was to just use the BBS algorithm and then mod by the max_length
				size_variable=((size_variable*size_variable)%modular)%max_length+1;  //to decide the size of current solution for current individual
				
				double[] temp_solution= new double[size_variable];
				for(int k=0; k<size_variable; k++) //create current individual
				{
					x=(x*x)%modular;
					
					
					temp_solution[k]=return_double(x);
					//System.out.println(x);
				}
				double combined_fitness=fitness_function(temp_solution, size_variable);
				
				
				average_fitness_BBS=average_fitness_BBS+combined_fitness; //average fitness
				if(max_fitness_BBS<combined_fitness) //checking for new max
				{
					max_fitness_BBS=combined_fitness;
					best_fitness_BBS=new double[temp_solution.length];
					for(int k=0; k<temp_solution.length; k++)
					{
						best_fitness_BBS[k]=temp_solution[k];
					}
					
				}
				if(min_fitness_BBS>combined_fitness || min_fitness_BBS==-1 )min_fitness_BBS=combined_fitness; //checking for new min

			}
		}
		average_fitness_BBS=average_fitness_BBS/(double)10000; 	
	}
	public void printFile() //prints to output file
	{
		try 
		{

			

			FileWriter writer = new FileWriter(ReadFile.random_file); //C:\\Users\\Studyroom 2\\Documents\\cs381 eclipse\\Hitting set brute force\\src\\small_output_brute_force.txt
			writer.write("The fitness values for all the brute force solutions are 2 \n");
			for(Integer[] arr : BruteForce.solutions) //writes all solutions to output file
			{
				//System.out.println(Arrays.toString(arr));
				writer.write(Arrays.toString(arr) + "Fitness Value 2"+ "\n");
			}
			writer.write("\n");
			//print the Math.random to file
			writer.write("The max fitness value for the random solution is " + max_fitness_javaRandom + "\n");
			writer.write("The set with the max fitness value for the random solution is ");
			for(double arr : best_fitness_javaRandom) //writes all solutions to output file
			{
				//System.out.println(Arrays.toString(arr));
				writer.write((arr) + " ");
			}
			writer.write("\n");
			writer.write("The min fitness value for the random solution is " + min_fitness_javaRandom + "\n");
			writer.write("The average fitness value for the random solution is " + average_fitness_javaRandom + "\n");
			writer.write("\n");
			
			
			//print the BBS to file
			writer.write("The max fitness value for the BBS solution is " + max_fitness_BBS + "\n");
			writer.write("The set with the max fitness value for the BBS solution is ");
			for(double arr : best_fitness_BBS) //writes all solutions to output file
			{
				//System.out.println(Arrays.toString(arr));
				writer.write((arr) + " ");
			}
			writer.write("\n");
			writer.write("The min fitness value for the BBS solution is " + min_fitness_BBS + "\n");
			writer.write("The average fitness value for the BBS solution is " + average_fitness_BBS + "\n");
			
			
			writer.close();
			System.out.println("Successfully");
		}
		catch (IOException e) 
		{
			System.out.println("Error");
	    }
	}
	
}
