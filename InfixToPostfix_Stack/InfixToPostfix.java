import java.io.*;
import java.util.*;
import java.util.regex.*;

public class InfixToPostfix {

	private static StringBuilder result = new StringBuilder(); //A StringBuilder to build the result String
	private static Stack<Character> operators = new Stack<Character>(); //The list for holding the operators

	public static void main(String[] args) throws IOException {

		String commandLine;
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

		// Break with "quit"
		while (true) {
			
			// Prompt and read what the user entered
			System.out.print("Infix: ");
			commandLine = console.readLine();
			if (commandLine.equals("quit")) {
				break;
			}

			// if the user entered a return, just loop again
			else if (commandLine.equals("")) {
				System.out.println();
				continue;
			}

			// Else, commandLine contains infix string; read parse and evaluate
			else {
				
				/*
				 * We match the input String with the delimiters(operands), break the String
				 * at those points and finally save all the parts including the delimiters 
				 * in a String type list.
				 */
				Pattern regex = Pattern.compile("[\\d]+|[+/*-]|[()]");
				Matcher matcher = regex.matcher(commandLine.toString());

				List<String> matchList = new ArrayList<String>();

				while (matcher.find()) {
					matchList.add(matcher.group(0)); // add the match to the list
				}

				for (String s : matchList) {
					
					char c = s.charAt(0);

					if (Character.isDigit(c))  //If the character is a digit, just append it to the result
						choice(1, s);
					else if (c == '(') { 	  //If its a left bracket, push it to the stack
						operators.push(c);
					} else if (c == ')') {	  //If its a right bracket, pop all the operators till the left bracket
						while(operators.peek() != '('){
							result.append(operators.pop()+" ");
						}
						operators.pop();
					} else		//If its any other operator, just follow the precedence rules and pop accordingly
						choice(2, s);
				}

				Iterator<Character> itr = operators.iterator();	//This part is for any remaining operators on the stack
				while (itr.hasNext()) {
					char temp = itr.next();
					operators.pop();		//popping and appending any remaining operators on the stack
					result.append(temp + " ");
				}

				String finalresult = result.toString();

				System.out.println(
						"Postfix: \" " + finalresult + "\"" + " and it evaluates to " + postfix(finalresult));
				System.out.println();

				result.setLength(0); //Resetting the length of the StringBuilder for usage in further iterations.
			}
		}
	}

	
	/*
	 * A custom method for executing opearations depending on whether
	 * the character is a operator or an operand
	 * 
	 * @param n, the number denoting if its an operator or an operand
	 * @param s, the string containing the chracters to be evaluated
	 * 
	 */
	public static void choice(int n, String s) {
		switch (n) {
		case 1:
			result.append(s + " ");		//Just append to the result
			break;
		case 2:
			if (operators.size() == 0) 	
				operators.push(s.charAt(0));	//push to the stack if its empty
			else {
				Iterator<Character> itr = operators.iterator();
				while (itr.hasNext() && operators.peek()!='(') {
					char temp = itr.next();
					if (precedence(temp) > precedence(s.charAt(0)) || precedence(temp) == precedence(s.charAt(0))) {
									//follow the precedence rules and pop and append accordingly
						operators.pop();
						result.append(temp + " ");
					} else
						break;
				}
				operators.push(s.charAt(0)); 	//Push the operator to the stack after popping the ones with higher or 
								//equal precedence levels
			}
			break;
		}
	}

	
	/*
	 * A method to define the precedence order of the operators
	 * 
	 * @param op, the operator whose precedence level is to be evaluated
	 * 
	 * @return an integer denoting what the precedence level of the character is
	 */
	public static int precedence(char op) {
		switch (op) {
		case '+':
		case '-':
			return 0;
		case '*':			//Just random integers are set with precedence levels 
		case '/':
			return 1;
		default:
			throw new IllegalArgumentException();
		}
	}

	
	/*
	 * A method to evaluate the postfix expression and derive the result.
	 * 
	 * @param s, the String containing the postfix expression to be evaluated
	 * 
	 * @return a integer value with the evaluated result.
	 */
	public static int postfix(String s) {

		String[] temp = s.split(" ");  //Splitting the expression at spaces

		Stack<Integer> operands = new Stack<Integer>(); // another stack to hold the operands this time.

		for (String lol : temp) {

			char temp2 = lol.charAt(0);

			if (Character.isDigit(temp2))		//push to stack if the character is a number
				operands.push(Integer.parseInt(lol));
			else {
				switch (temp2) {

				//If the character is an operator, perform the requisite operation and push the 
				//result to the stack
				case '+':
					int op1 = operands.pop();
					int op2 = operands.pop();
					operands.push(op2 + op1);
					break;

				case '-':
					int opmin1 = operands.pop();
					int opmin2 = operands.pop();
					operands.push(opmin2 - opmin1);
					break;

				case '*':
					int opmul1 = operands.pop();
					int opmul2 = operands.pop();
					operands.push(opmul2 * opmul1);
					break;

				case '/':
					int opdiv1 = operands.pop();
					int opdiv2 = operands.pop();
					operands.push(opdiv2 / opdiv1);
					break;

				}
			}
		}

		return operands.pop(); 	//return the final result
	}
}
