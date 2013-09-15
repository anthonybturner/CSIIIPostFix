import java.awt.print.Printable;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PostFixTest{

	int x = 8; 
	int  y = 10;
	private PostFix post_fix;

	/**
	 *
	 *Test initialization
	 **/
	PostFixTest(){

		post_fix = new PostFix();
		
	}

	private void runAllTest() {
		
		print("===================================");
		print("Running all test");
		print("===================================");
		
		
		testAddOperand();
		testEvaluateExpression();
		testReadFile();
		testStart();
		
		
	}

	private void testStart() {
		print("===================================");
		print("Testing start");
		print("===================================");
		
		
	}


	private void print(String msg) {
		
		System.out.println(msg);
		
	}

	private void testReadFile() {
		
		print("===================================");
		print("Testing readFile");
		print("===================================");
		
		
		try {
			
			String file_input = "in_test.dat";
			Scanner scanner = post_fix.readFile(file_input);
			while( scanner.hasNextLine() ){
				
				print("Reading line ");
				print(scanner.nextLine() );
			}
			
		} catch (FileNotFoundException e) {
			
			print("Error reading input file ");
			e.printStackTrace();
		}
		
	}

	private void testEvaluateExpression() {
		
		print("===================================");
		print("Testing evaluateExpression");
		print("===================================");
		
		double value = post_fix.evaluateExpression('+');
		print("Addition " + value );
		
		 value = post_fix.evaluateExpression('-');
		print("Sub " + value );
		
		 value = post_fix.evaluateExpression('*');
		print("Multiply " + value );
		
		 value = post_fix.evaluateExpression('/');
		print("Division " + value );
		
		 value = post_fix.evaluateExpression('^');
		print("Exponent " + value );
		
		 value = post_fix.evaluateExpression('#');
		print("Square root " + value );
		
		 value = post_fix.evaluateExpression('d');
		print("Non operator " + value );
		
		
		
		
	}

	private void testAddOperand() {
		print("===================================");
		print("Testing addOperand");
		print("===================================");
		
		double[]operands = {34.1, 11.0, 3.2,1.9,'^'};
		
		for(Double op: operands){
			
			print("current operand is " + op);
			
			post_fix.addOperand(op);
			
			double top_value = post_fix.checkTopValue();
			print("operand pushed is " + top_value );
			print("");
			//TODO fix because character values are converted to doubles
			if( !op.equals(top_value)){
			
				System.err.println("failed adding operand to stack");
				
			}
					
		}
		
	}

	public static void main(String[]args){

		PostFixTest pft = new PostFixTest();
		pft.runAllTest();

	}


	



}
