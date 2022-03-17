import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GA 
{
	//the parameters
	static int pop_size=1000; 
	static int generations=10;
	static double fitness_threshold=0.1;
	static int max_length=ReadFile.elements.size();
	static double mutation_rate=0.0;
	static double crossover_rate=0.0;
	
	//information for fitness values
	static ArrayList<Double[]> brute_force_solutions=new ArrayList<Double[]>(); 
	static int correct_size;
	//variable for ga
	static double average_fitness_ga=0;
	static double max_fitness_ga=0; 
	static double min_fitness_ga=-1;
	
	static double[] best_fitness_ga;
	
	public GA()
	{
		
		set_BruteForceSolutions();
		genetic_algo();
		
		
		printFile_ga();
	}


	
	public static void genetic_algo()
	{
		
		ArrayList<double[]> current_population= new ArrayList<double[]>();
		ArrayList<Double> current_fitness= new ArrayList<Double>();
		double 	memetic_fitness=0;
		double [] memetic = null;
		for(int i=0; i<1000; i++) //get the initial population based on randoms
		{
			int current_solution_size= (int)(Math.random()*max_length+1); //to decide the size of current solution
			//System.out.println(current_solution_size);

			double[] temp_solution= new double[current_solution_size];
			for(int k=0; k<current_solution_size; k++) //to create the random elements inside the set
			{
				temp_solution[k]=Math.random();
			}
			current_population.add(temp_solution);
			
			
			double combined_fitness=fitness_function(temp_solution,  current_solution_size);
			current_fitness.add(combined_fitness);
			average_fitness_ga=average_fitness_ga+combined_fitness; //average fitness
			if(max_fitness_ga<combined_fitness) //checking for new max, since initial should always be the case but just incase i have a boolean
			{
				max_fitness_ga=combined_fitness;
				best_fitness_ga=new double[temp_solution.length];
				memetic=new double[temp_solution.length];
				for(int k=0; k<temp_solution.length; k++)
				{
					best_fitness_ga[k]=temp_solution[k];
					memetic[k]=temp_solution[k];

				}
				memetic_fitness=combined_fitness;
			}
			//min should always be -1 but just incase of programming error I have the boolean here also
			if(min_fitness_ga>combined_fitness || min_fitness_ga==-1 )min_fitness_ga=combined_fitness; //checking for new min
			
		}
		
		for(int i=0; i<100; i++) //100 generations
		{
			
			mutation_rate=Math.random(); //mutation rate
			crossover_rate=Math.random(); //cross over rate
			
			ArrayList<Integer> parentpool=new ArrayList<Integer>(); //parents who will be crossover
			                       
			ArrayList<double[]> prev_population= new ArrayList<double[]>(); // to store the prev popluation
			ArrayList<Double> prev_fitness= new ArrayList<Double>(); //to store the prev population fitness
			for(int j=0; j<1000; j++)
			{
				prev_population.add(current_population.get(j));
				prev_fitness.add(current_fitness.get(j));
			}
			current_population.clear();
			current_fitness.clear();
			
			for(int j=0; j<1000; j++)
			{
				if(prev_fitness.get(j)>crossover_rate) //if the fitness is higher then the certain crossover_rate, add as parent
				{
					parentpool.add(j);
				}
			
			}
			if(parentpool.size()<0) //to avoid an empty parent pool
			{
				for(int j=0; j<10; j++) 
				{
					parentpool.add((int)(Math.random()*1000 +1));
				}
				
			}
			for(int j=0; j<1000; j++) //create the new population
			{
				if(j==0)
				{
					current_population.add(memetic); //adds the indiviudal with the greatest fitness to our current population
				}
				else
				{
					//initate two parents
					int parent1=(int)(Math.random()*parentpool.size());
					int parent2=(int)(Math.random()*parentpool.size());
					
					while(parent1==parent2) //parents can not be the same
					{
						parent2=(int)(Math.random()*parentpool.size()+1);	
					}
					//parent 1 is default
					
					double [] child= new double[prev_population.get(parent1).length];
					
					for(int k=0; k<child.length; k++) //make the child equal to parent
					{
						child[k]=prev_population.get(parent1)[k];
					}
					int max_chrom_length= Math.min(prev_population.get(parent1).length, prev_population.get(parent2).length); //the longest value that can be crossed over
					int cross_over_pos= (int)(Math.random()*max_chrom_length); //which values are getting crossed over
					
					child[cross_over_pos]=prev_population.get(parent2)[cross_over_pos]; //crossover
					
					
					//mutation 
					if(Math.random()>mutation_rate)
					{
						//mutation points
						int pos1= (int)(Math.random()*child.length);
						int pos2= (int)(Math.random()*child.length);
						
						double temp=child[pos1];
						child[pos1]=child[pos2];
						child[pos2]=temp; //child mutated
						
					}
					current_population.add(child); // adds to current population
					
					
					
				}
				
				
				
				double combined_fitness=fitness_function(current_population.get(j),  current_population.get(j).length); //gets the fitness
				current_fitness.add(combined_fitness);
				prev_fitness.add(combined_fitness);
				average_fitness_ga=average_fitness_ga+combined_fitness; //average fitness
				if(memetic_fitness<current_fitness.get(j)) //checking for new memetic
				{
					for(int k=0; k<current_population.get(j).length; k++)
					{
						memetic[k]=current_population.get(j)[k];
					}
					memetic_fitness=current_fitness.get(j);
					if(max_fitness_ga<combined_fitness) //checking for new max
					{
						max_fitness_ga=combined_fitness;
						best_fitness_ga=new double[current_population.get(j).length];
						for(int k=0; k<current_population.get(j).length; k++)
						{
							best_fitness_ga[k]=current_population.get(j)[k];
							
						}
					}		
				}
				//min should always be -1 but just incase of programming error I have the boolean here also
				if(min_fitness_ga>combined_fitness || min_fitness_ga==-1 )min_fitness_ga=combined_fitness; //checking for new min

			}
		}
		average_fitness_ga=average_fitness_ga/(double)100000; 
		
		
	
	
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
	
	
	
	public static double return_double(int e) //used to convert int to double, example 255 to .255
	{
		double element=(double)e;
		while(element>1)
		{
			element=element/(double)10;
		}
		return element;
	}
	//Same as the Random fitness function to keep comparison fair
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
//				ArrayList<Integer> used_index=new ArrayList<Integer>();
				fitness_value_elements=0; //resets the current fitness_value_elements to zero if not 1
				for(int w=0; w<brute_force_solutions.get(k).length; w++) //increment for each value in current brute force solution to check if in current random solution
				{
					boolean current_element_found=false;
					for(int x=0; x<temp_solution.length; x++) //check every current random element
					{
						
						if(temp_solution[x]==brute_force_solutions.get(k)[w] )
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
		public void printFile_ga() //prints to output file
		{
			try 
			{

				

				FileWriter writer = new FileWriter(ReadFile.ga_file); //C:\\Users\\Studyroom 2\\Documents\\cs381 eclipse\\Hitting set brute force\\src\\small_output_ga.txt
				writer.write("The fitness values for all the brute force solutions are 2 \n");
				for(Integer[] arr : BruteForce.solutions) //writes all solutions to output file
				{
					//System.out.println(Arrays.toString(arr));
					writer.write(Arrays.toString(arr) + "Fitness Value 2"+ "\n");
				}
				writer.write("\n");
			
				writer.write("The max fitness value for the ga solution is " + max_fitness_ga + "\n");
				writer.write("The set with the max fitness value for the ga solution is ");
				writer.write("[");
				for(double arr : best_fitness_ga) //writes all solutions to output file
				{
					//System.out.println(Arrays.toString(arr));
					writer.write((arr) + ", ");
				
				}
				writer.write("]");
				writer.write("\n");
				writer.write("The min fitness value for the ga solution is " + min_fitness_ga + "\n");
				writer.write("The average fitness value for the ga solution is " + average_fitness_ga + "\n");
				writer.write("\n");
				
				
				writer.close();
				System.out.println("Successfully");
			}
			catch (IOException e) 
			{
				System.out.println("Error");
		    }
		}
		
		
}
