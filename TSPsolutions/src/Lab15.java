import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Lab15 
{


	public static void main (String args[])
	{
		double[][] distances = TSP.ReadArrayFile("C:\\Users\\emman\\Documents\\CS2004\\CS2004 TSP Data (2017-2018)\\TSP_442.txt", " "); //double array list of text
		//TSP_48 is the amount of cities be tested
		/**
		 * We can use the length of the 
		 * minimum spanning tree as an absolute 
		 * lower limit on the fitness of a TSP solution 
		 * TSP_48 Length: 27643.67649888895
		 * TSP_152 Length : 6963.274320208433
		 * TSP_264 Length : 46362.390531654324
		 * **/
		
		// The function  allows for the best tour to be shown
		double MSTLength =0;
		double MSTFitness [][] = MST.PrimsMST(distances);
		for(int i = 0; i < distances.length; i++) {
		for(int j =0; j<i; j++) {
		MSTLength += MSTFitness[i][j];
		
		
		}
		}
		System.out.println("MST Fitness: " + MSTLength);

		//Optimum Tour/Solution and fitness
		ArrayList<Integer> optimumSol = TSP.ReadIntegerFile("C:\\Users\\emman\\Documents\\CS2004\\CS2004 TSP Data (2017-2018)\\TSP_48_OPT.txt");
		TSPSolutions optimumSolution = new TSPSolutions(optimumSol,distances.length); //creating an object
		double optimumFitness = optimumSolution.fitnessFunction(distances);
		
		System.out.println("\nOptimum Solution: " + optimumSolution.getTour());
		System.out.println("Optimum Fitness = " + optimumFitness);
		
		
		System.out.println("-----------");
		
		//Change here
		for(int i = 1; i <= 10; i++) {   //show 10 times for a more accurate set of results
			// for loop for 10 iterations
			TSPSolutions bestRMHC = RMHC(100000,distances,false);// Simple Hill Climbing
			TSPSolutions bestSHC = SHC(100000,distances,false);// Stochastic Hill Climber
			TSPSolutions bestRRHC = RRHC(100000,10,distances,false);// Random Restart Hill climber
			TSPSolutions bestSA = SA(100000,distances,false,0.99,10000);// Simulated Annealing
			//Show preferred TSP solutions 
			System.out.println(bestSA.getFitness());
			
		}
	//Also Change iterations here 
		TSPSolutions bestRMHC = RMHC(100000,distances,false);
		double fitness = bestRMHC.fitnessFunction(distances);
		System.out.println("RMHC");
		System.out.println("\nBest Solution: " + bestRMHC.getTour());
		System.out.println("Best Fitness = " + bestRMHC.getFitness());
		
		System.out.println("-------------");
		System.out.println("SHC");
		System.out.println("--------------");
		
		//Also Change iterations here 
		TSPSolutions bestSHC = SHC(100000,distances,false);
		System.out.println("\nBest Solution: " + bestSHC.getTour());
		System.out.println("Best Fitness = " + bestSHC.getFitness());
		
		
		System.out.println("-----------");
		System.out.println("RRHC");
		System.out.println("-----------");
		
		//Also Change iterations here 
		TSPSolutions bestRRHC = RRHC(100000,10,distances,false);
		System.out.println("\nBest Solution: " + bestRRHC.getTour());
		System.out.println("Best Fitness = " + bestRRHC.getFitness());
		
		System.out.println("-----------");
		System.out.println("SA");
		System.out.println("-----------");
		//Also Change iterations here 
		TSPSolutions bestSA = SA(100000,distances,false,0.99,10000);
		System.out.println("\nBest Solution: " + bestSA.getTour());
		System.out.println("Best Fitness = " + bestSA.getFitness());

	


	}
	
	/**
	 * Random Mutation Hill Climbing
	 * @param iter = Number of Iterations e.g 10, 100, 1000 ect
	 * @param distances = The distances of the cites 
	 * @param print = print method 
	 * @return Best Solution
	 */
	private static TSPSolutions RMHC(int iter, double[][] distances, boolean print)
	{
		int n = distances.length;
		TSPSolutions solution = new TSPSolutions(n);//create random solution
		for (int i = 1; i <= iter; i++) //creating a for loop, iterates for specified number of iterations
		{
			
			
			TSPSolutions Old_solution = new TSPSolutions(solution.getTour(),n); //copies solution into Old_solution
			double f1 = Old_solution.fitnessFunction(distances); //evaluate first fitness within the loop
			
			solution.smallChange(); //makes a small change in sol
			
			TSPSolutions newsol = new TSPSolutions(solution.getTour(),n);
			
			double f2 = newsol.fitnessFunction(distances); //evaluates new fitness to another variable

			//if the new fitness is worse than the old, then keep the old solution
			if (f2 > f1)
			{
				solution=Old_solution;
			}
			if(print) {
				System.out.println("Iteration " + i + ": " +" Fitness: " + f1);
			}
		}
		
		return(solution);//returing current solution
	}
	
	/**
	 * Stochastic Hill Climbing 
	 * @param iter = No of Iteration 10,100,1000, ect.
	 * @param distances = distances for the cities 
	 * @param print = print out information to see process
	 * @return Best solution
	 */
	private static TSPSolutions SHC(int iter, double[][] distances, boolean print)
	{
		int n = distances.length;
		TSPSolutions solution = new TSPSolutions(n);//create random solution
		for (int i = 1; i <= iter; i++) //creating a for loop, iterates for specified number of iterations
		{
			TSPSolutions Old_solution = new TSPSolutions(solution.getTour(),n); //copies sol into Old_solution
			double f1 = Old_solution.fitnessFunction(distances); //evaluate first fitness within the loop
			
			solution.smallChange(); //makes a small change in sol
			
			TSPSolutions newsol = new TSPSolutions(solution.getTour(),n);
			
			double f2 = newsol.fitnessFunction(distances); //evaluates new fitness to another variable

			//if the new fitness is worse than the old, then keep the old solution
			
            /*
            if a random number is > than the probability acceptance then the probability is
            not good enough and we keep the oldFitness
            However probabilty acceptance is 0.6 and random is 0.7 which would mean we would go back to
            old solution and therefore accept the worse solution
            */
			if(CS2004.UR(0, 1) > PRAccept(f2,f1,95)) {
				solution=Old_solution;
			}
			
			if(print) {
				System.out.println("Iteration " + i + ": " +" Fitness: " + f1);
			}
		}
	
		return(solution);//returning current solution
	}
	
	
	/**
//	 * We accept a new solution according to the equation below:
//	 * Pr(accept) = 1/1+e^(f'-f)/T*
//	 * 
//	 * f' = new fitness
//	 * f = old fitness
//	 * T is parameter ( Set to 25) 
//	 * Lecture 9.1 Slide 24
//	 * 
//	 **/
	
	
	private static double PRAccept(double newFitness, double oldFitness, int t) {
		
		return 1.0/(1.0+Math.exp((newFitness-oldFitness)/t));
	}
	
	/**
	 * Random Restart Hill Climbing
	 * @param iter = number of iterations
	 * @param restart = restart from a different section of the search space
	 * @param distances = distances for the cities
	 * @param print = print out information to see process
	 * @return Best Solution
	 */
	
	private static TSPSolutions RRHC (int iter, int restart, double[][] distances, boolean print) {
		ArrayList<TSPSolutions> Solutions = new ArrayList<>();
		for (int i=1; i<=restart; i++){
			Solutions.add(RMHC(iter,distances,print));
		}
		
		return bestSol(Solutions);
	}
	
	private static TSPSolutions bestSol(ArrayList<TSPSolutions> sol) {
		ArrayList<Double> fitness = new ArrayList<>();
		
		for(int i = 0; i < sol.size(); i++) {
			fitness.add(sol.get(i).getFitness());
		}
		
		int min = fitness.indexOf(Collections.min(fitness));
		
		return sol.get(min);
	}
	
	/**
	 * Simulated Annealing 
	 * @param iter
	 * @param distances
	 * @param print
	 * @param coolingrate- cooling values down slowly to get best solution
	 * @param temp
	 * @return Best solution 
	 */
	private static TSPSolutions SA(int iter, double[][] distances, boolean print, double coolingrate, double temp)
	{
		int n = distances.length;
		TSPSolutions sol = new TSPSolutions(n);//create random solution
		
		double currentTemp = temp;
		for (int i = 1; i <= iter; i++) //creating a for loop, iterates for specified number of iterations
		{
			
			
			TSPSolutions Old_solution = new TSPSolutions(sol.getTour(),n); //old solution copied into new solution
			double f1 = Old_solution.fitnessFunction(distances); //evaluate first fitness within the loop
			
			sol.smallChange(); //makes a small change in sol
			
			TSPSolutions newsol = new TSPSolutions(sol.getTour(),n);
			
			double f2 = newsol.fitnessFunction(distances); //evaluates new fitness to another variable

			//This if statement tests the new fitness against the old and if it is worst, the old solution will be returned
			if (f2 > f1)
			{
				double p = PR(f2,f1,currentTemp);
				
				if(p<CS2004.UR(0, 1)) {
					sol=Old_solution;
				}
				else
					sol=newsol;
			}
			if(print) {
				System.out.println("Iteration " + i + ": " +" Fitness: " + f1);
			}
			
			currentTemp *= coolRate(temp, i);
		}
		
		return(sol);//returning current solution
	}
	
	private static double coolRate(double initialTemp, int iter) {
	    return Math.exp((Math.log(Math.pow(10,-100))-Math.log(initialTemp))/iter); //figure out the landar 
    }
	
	
	/*Input:	T0	Starting temperature
	Iter	Number of iterations
	cl 		The cooling rate
	1)	Let x = a random solution
	2)	For i = 0 to Iter-1
	3)		Let f = fitness of x
	4)		Make a small change to x to make x’
	5)		Let f’ = fitness of new point x’
	6)		If f’ is worse than f Then
	7)			Let p = PR(f’,f,Ti)
	8)			If p < UR(0,1) Then
	9)				Reject change (keep x and f)
	10)			Else
	11)				Accept change (keep x’ and f’)
	12)			End If
	13)		Else
	14)			Let x = x’
	15)		End If
	16)		Let Ti+1 = clTi
	17)	End For
	Output:	The solution x*/
	
	
	
	
	
	/**
	 * 
	 * @param newFitness
	 * @param oldFitness
	 * @param currentTemperature
	 * @return PR- probability 
	 * 	 */
	
	private static double PR(double newFitness, double oldFitness, double currentTemperature) {
		
		double changeOfFitness = newFitness - oldFitness;
		
		return Math.exp(-changeOfFitness/currentTemperature);
		
		
	}
}
	


	


