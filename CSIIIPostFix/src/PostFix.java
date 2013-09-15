import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * 
 * @author Anthony turner<br><br>
 * 
 * <b>Description:</b> <br> 
 * 	Programming Assignment 1
 *  Postfix Expression Calculator
 *
 */

public class PostFix{

	//Constants
	private static final String INPUT_FILE = "in.dat";
	private static final String INPUT_FILE_TEST = "in_test.dat";
	private static final char MULTIPLICATION = '*';
	private static final char DIVISION = '/';
	private static final char SUBTRACTION = '-';
	private static final char ADDITION = '+';
	private static final char EXPONENT = '^';
	private static final char NEGATION = '_';
	private static final char SQUARE_ROOT = '#';
	
	// These patterns are from Appendix B of Data Structures and Other Objects.
	   // They may be used in hasNext and findInLine to read certain patterns
	   // from a Scanner.
	   public static final Pattern CHARACTER =
	     Pattern.compile("\\S.*?");  
	   public static final Pattern UNSIGNED_DOUBLE =
	     Pattern.compile("((\\d+\\.?\\d*)|(\\.\\d+))([Ee][-+]?\\d+)?.*?");
	   
	//Member variables
	private Stack<Double>operands;//Holds a stack of operands	

	public PostFix(){

		//Initializations
		operands = new Stack<Double>();
	}


	/**
	 * Starts the program's execution
	 **/
	public void start(){

		printWelcome();

		try{

			Scanner scanner = readFile(INPUT_FILE);

			readExpressions(scanner);


		}catch(FileNotFoundException e){

			System.out.println("Can't read from input file." );	

		}
		
		printGoodbye();
	}
	


	/**
	 * Reads in an internal file for the post fix operations
	 * @return Scanner with a read-in file
	 * @throws FileNotFoundException if the file can't be found
	 */
	public Scanner readFile(String input) throws FileNotFoundException{

		File file_input = new File(input);
		return new Scanner(file_input);
	}

	private void readExpressions(Scanner scanner) {
		
		while(scanner.hasNext()){//Read-in each expression

			String expression = scanner.nextLine();
			printExpression(expression);//Display the expression to output
			
			try{
				evaluateTokens(expression);
			}
			catch(IllegalArgumentException e){
				
				System.out.println(e.toString());	
			}
		}
		
	}


	/**
	 * Evaluates one single expression and its tokens for operands and operators
	 * @param expression the string expression to evaluate
	 * @throws IllegalArgumentException if there is an invalid expression
	 */
	public void evaluateTokens(String expression) {
		
		Scanner expressions_reader = new Scanner(expression);
		String nextToken = "";
		
		try{

			while(expressions_reader.hasNext() ){//read each token in expression
				
				//Potential operand/double value
				if( expressions_reader.hasNext(UNSIGNED_DOUBLE)){
					
					nextToken = expressions_reader.findInLine(UNSIGNED_DOUBLE);
					operands.push( new Double(nextToken));//Add the double value operand to the stack
					
				}else{//Potential operator
					
					nextToken = expressions_reader.findInLine(CHARACTER);
					try{
					
						double result =	evaluateExpression(nextToken.charAt(0)); //get token at the first index, this should be an operator 
						//Here I could make sure that the result is not equal to -1
						operands.push(result);//Add the evaluated result to the stack
						
					}catch(IllegalArgumentException e){
						
						System.out.println( e.toString() );
						//e.printStackTrace();
						
					}
				}
			}//End of the single expression
			
			if( operands.size() == 1){//One operand means we should have our final result on the stack.			
				
				displayOut("-->", operands.pop());//Display the result to screen	
				
			}else{
				//This call would normally prevent the scanner from being closed, hence the finally clause.
				throw new IllegalArgumentException(expression + " illegal input expression" );
			}
			
		}finally{//Finally is here so that the scanner can be closed otherwise a resource leak occurs.
			
			expressions_reader.close();
		}
	}
	
	/**
	 * Evaluates the last two (or one if unary) operands added to the stack 
	 * with the given argument operator.
	 * @param operator the operator to use on the operand(s).
	 * @return returns the value of the evaluated expression
	 * @throws IllegalArgumentException if the supposed operator is not a valid operator
	 */
	public double evaluateExpression(char operator){
		
		//At least one operand must be in the stack, and if it is an Unary operator only pop one operand.
		if(operands.size() >=1 && isUnaryOperator(operator)){
			
			double top_operand = operands.pop();

			switch(operator){
			
				case NEGATION: return -top_operand;//Unary operation - First put back the bottom operand
			
				case SQUARE_ROOT: return Math.sqrt(top_operand);//Unary operation - First put back the bottom operand
			
			}
			
		}else if( operands.size() >=2 ){//Binary operation 

			double top_operand = operands.pop();
			double bottom_operand = operands.pop();

			switch(operator){

					case MULTIPLICATION : return top_operand * bottom_operand ;
						
					case DIVISION: return bottom_operand / top_operand;
						
					case SUBTRACTION: return bottom_operand - top_operand;
						
					case ADDITION: return bottom_operand + top_operand ;
						
					case EXPONENT: return Math.pow(bottom_operand, top_operand);
				
					default: throw new IllegalArgumentException(operator + " is an invalid character");
			}
		}else{
			//If neither an operator nor an operand then something went wrong
			throw new IllegalArgumentException(" invalid operation");
		}
		return -1;//This should never be reached
		
	}
	
	
	/*
	 * Determines if the given operator is an Unary operator.
	 */
	private boolean isUnaryOperator(char operator) {
		
		switch(operator){

			case NEGATION: 
			case SQUARE_ROOT: return true;
			
			default: return false;
		}
	}


	/**
	 * Looks at top operand on stack
	 * @return a copy of the top operand on the stack
	 */
	public double checkTopValue() {
		
		return operands.peek();
	}
	
	//Determines if the given token is an operator
/*	public boolean isOperator(char operator){

		
		//An arraylist could be used which contains all the constants in this switch statement,
		//Then, contains can be called on that list to determine if it is an operator.
		//That method is easier because new values are simply put in the list (future expansion).
		switch(operator){

			case MULTIPLICATION : ;
			case DIVISION: ;
			case SUBTRACTION: ;
			case ADDITION: ;
			case EXPONENT: ;
			case SQUARE_ROOT: ;
			case NEGATION: ;
			return true;

			default: return false;

		}
	 } */

	/**
	 * Adds the given operand for later evaluation
	 * @param operand the double operand to add
	 */
	public void addOperand(double operand) {

		operands.push(operand);

	}

	/*=========================================================================\
	 * 
	 * 						Display to output method calls
	 * 
	 *=========================================================================*/
	
	
	public void printExpression(String expression) {

		System.out.println("The value of " + expression + " is ");

	}

	public void displayOut(String msg, double d){

		System.out.println(msg + d);
	}

	public void displayOut(String msg, String d){

		System.out.println(msg + d);
	}

	public void printWelcome(){


		System.out.println("==============================");
		System.out.println("Assignment: PostFix Evaluation\nAuthor: Anthony Turner");
		System.out.println("==============================");


	}

	public void printGoodbye(){


		System.out.println("==============================");
		System.out.println("Bye bye!");
		System.out.println("==============================");


	}
	

	public static void main(String[]args){


		new PostFix().start();

	}


}
