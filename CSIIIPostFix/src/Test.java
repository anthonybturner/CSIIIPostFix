import java.util.Scanner;

public class Test{


/**
*
*Test initialization
**/
Test(){



}


	public static void main(String[]args){


		int x = 8; 
		int  y = 10;

		System.out.println("Enter a number");

		Scanner sInput = new Scanner(System.in);
		x = sInput.nextInt();
		
		System.out.println("Enter another number");
		y = sInput.nextInt();


		System.out.println("Value of x: " + x);
		System.out.println("Value of y: " + y);

		int sum = x+y;

		System.out.println("sum is " + sum);



	}



}
