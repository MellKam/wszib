import java.util.Scanner;

/*
 * Calculator multiline comment
 */
public class Calculator {
	public static void main(String[] args) {
		// Stores two numbers
		double a, b;

		float x = 2.3f;
		double y = -23.8D;

		// Take input from the user
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter the numbers:");

		// Take the inputs
		a = scanner.nextDouble();
		b = scanner.nextDouble();

		System.out.println("Enter the arithmetic operator (+,-,*,/):");

		char op = scanner.next().charAt(0);
		double output = 0;

		switch (op) {
			// case to add two numbers
			case '+':
				output = a + b;
				break;

			// case to subtract two numbers
			case '-':
				output = a - b;
				break;

			// case to multiply two numbers
			case '*':
				output = a * b;
				break;

			// case to divide two numbers
			case '/':
				output = a / b;
				break;

			default:
				System.out.println("You enter wrong input");
		}

		System.out.println("Result:");
		System.out.println(a + " " + op + " " + b + " = " + output);

		scanner.close();
	}
}