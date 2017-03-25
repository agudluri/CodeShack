import java.util.Scanner;

public class Fibonacci {

	/**
	 * Returns a Fibonacci number from a given point in the sequence.
	 *
	 * @param n - the integer whose Fibonacci number is sought from a given
	 *            point in the sequence.
	 * @param previous- the first Fibonacci number in the sequence.
	 * @param current- the next Fibonacci number in the sequence after previous.
	 *
	 */
	public static long f(int n, long previous, long current) {
		
		final int MAX_N = 92; // because f(92) exceeds the maximum length of long
		final String ERROR_MESSAGE = "\nThe number entered must be " + "greater than 0 and at most " + MAX_N + "."; //the error message in case 
															    //the input is entered
		if (n <= 0 || n > MAX_N)
			throw new IllegalArgumentException(ERROR_MESSAGE); //The illegal argument exception that shows you the error message
		if (n == 1) return previous; //Conditions to proceed with the function
		if (n == 2) return current;
		return f(n-1,current,current+previous); //the recursive call with the reduced n and a single-window shifted 'previous' and 'current'
	} // method f

	public static void main(String[] args) {
		System.out.println("Enter 'n' and the first and second numbers in the sequence seperated by commas"); //Displaying message 
														      //for taking in the input from the user
		Scanner sc = new Scanner(System.in);
		String[] line = sc.nextLine().split(","); //Reading the input and splitting it at the commas
		System.out.println(f(Integer.parseInt(line[0]),Long.parseLong(line[1]),Long.parseLong(line[2]))); //Passing the split inputs to our	
														  //fibonacci function
		sc.close();
	}
}


