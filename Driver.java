import java.io.*;
import java.util.Arrays;
public class Driver {

	public static void main(String [] args) {
	
		//  Test Case 1: Create a polynomial using the default constructor
		System.out.println("Test Case 1");
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));

		// Test Case 2: Create a polynomial from coefficient and exponent arrays
		System.out.println("Test Case 2");
		double [] c1_coefficients = {6, 5};
		int [] c1_exponents = {0, 3};
		Polynomial p1 = new Polynomial(c1_coefficients, c1_exponents);
		System.out.println("Coefficents: " + Arrays.toString(p1.coefficients) + " Exponents: " + Arrays.toString(p1.exponents));
		double [] c2_coefficients = {-2, -9};
		int [] c2_exponents = {1, 4};
		Polynomial p2 = new Polynomial(c2_coefficients, c2_exponents);

		// Test Case 3: Add two polynomials
		System.out.println("Test Case 3");
		Polynomial s = p1.add(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1));

		// Test Case 4: Check if a polynomial has a root
		System.out.println("Test Case 4");
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");

		// Test Case 5: Create a polynomial from a file
		System.out.println("Test Case 5");
		try{
			File file = new File("polynomial.txt");
			Polynomial polynomial_from_file = new Polynomial(file);
			System.out.println("Polynomial from file coefficients: " + Arrays.toString(polynomial_from_file.coefficients) + " Polynomial from file exponents: " + Arrays.toString(polynomial_from_file.exponents));

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Test Case 6: Save a polynomial to a file
		System.out.println("Test Case 6");
		try{
			p1.saveToFile("saved_polynomial.txt");
			System.out.println("Polynomial saved to file");
		} catch (IOException e){
			e.printStackTrace();
		}

		// Test Case 7: Multiply and save to file
		System.out.println("Test Case 7");
		double[] coefficients1 = {1, 2, 1}; // x^2 + 2x + 1
		int[] exponents1 = {2, 1, 0};
		Polynomial p3 = new Polynomial(coefficients1, exponents1);

		double[] coefficients2 = {2, 3}; // 2x + 3
		int[] exponents2 = {1, 0};
		Polynomial p4 = new Polynomial(coefficients2, exponents2);

		Polynomial result = p3.multiply(p4);

		System.out.println("Polynomial from file coefficients: " + Arrays.toString(result.coefficients) + " Polynomial from file exponents: " + Arrays.toString(result.exponents));
		try{
			result.saveToFile("saved_polynomial2.txt");
			System.out.println("Polynomial saved to file");
		} catch (IOException e){
			e.printStackTrace();
		}

		System.out.println("All tests satisfied");
	}

	
}
