import java.util.Scanner;
import java.io.*;

public class Polynomial {
	
	double coefficients[];
	int exponents[];
	
	public Polynomial() {
		coefficients = null;
		exponents = null;
	}
	
	public Polynomial(double given_coefficients[], int given_exponents[]) {
		coefficients = given_coefficients;
		exponents = given_exponents;
	}
	
	public Polynomial(File file) throws Exception{
		Scanner myScanner = new Scanner(file);
		if (!myScanner.hasNextLine()) {
			this.coefficients = null;
			this.exponents = null;
		} else {
			String line = myScanner.nextLine();
			line = line.replace("-", "+-");
			String polyArr[] = line.split("+");
			
			this.exponents = new int[polyArr.length];
			this.coefficients = new double[polyArr.length];
			
			for (int i = 0; i < polyArr.length; i++) {
				String subArr[] = polyArr[i].split("x");
				coefficients[i] = Double.parseDouble(subArr[0]);
				
				if (subArr.length > 1) {
					exponents[i] = Integer.parseInt(subArr[1]);
				}
			}
		}
		myScanner.close();
	}
	
	public int find_index(int number, int len, int array[]) {
		int i = 0;
		while (i < len) {
			if (array[i] == number) {
				return i;
			} 
			i += 1;
		}
		return -1;
	}

	public Polynomial add(Polynomial otherPoly) {
		//  Find the length of the final polynomial
			//  Loop through both lists and check the # of common powers
		int counter = 0;
		for (int i = 0; i < this.coefficients.length; i++) {
			for (int j = 0; j < otherPoly.coefficients.length; j++) {
				if (this.exponents[i] == otherPoly.exponents[j]) {
					counter++;
				}
			}
		}

		// 	The length will be the lengthOurPoly + lengthOtherPoly - common
		int length_final = this.coefficients.length + otherPoly.coefficients.length - counter;

		int new_exponents[] = new int[length_final];
		for (int i = 0; i < length_final; i++) {
			new_exponents[i] = -1000;
		}
		
		double new_coeff[] = new double[length_final];

			
		// Add the exponent into this array
		for (int j = 0; j < this.exponents.length; j++) {
			int empty_part = find_index(-1000, length_final, new_exponents);
			if (empty_part == -1) {
				break;
			}
			if (find_index(this.exponents[j], length_final, new_exponents) == -1) {
				new_exponents[empty_part] = this.exponents[j];
			}
		}
		
		for (int i = 0; i < otherPoly.exponents.length; i++) {
			int empty_part = find_index(-1000, length_final, new_exponents);
			if (empty_part == -1) {
				break;
			}
			if (find_index(otherPoly.exponents[i], length_final, new_exponents) == -1) {
				new_exponents[empty_part] = otherPoly.exponents[i];
			}
		}
		
		// Sort the exponent array
		for (int i = 0; i < length_final; i++) {
			for (int j = 0; j < length_final; j++) {
				int temp = 0;
				if (new_exponents[i] < new_exponents[j]) {
					temp = new_exponents[i];
					new_exponents[i] = new_exponents[j];
					new_exponents[j] = temp;
				}
			}
		}
		

		// Loop through the new_exponents array, and for each element, find the index of that in this.exponents and otherPoly.exponents. Add the coefficients for both and set that to the index of new_coeff
		for (int i = 0; i < length_final; i++) {
			int index_our_poly = find_index(new_exponents[i], this.coefficients.length, this.exponents);
			int index_other_poly = find_index(new_exponents[i], otherPoly.coefficients.length, otherPoly.exponents);
			if (index_our_poly == -1) {
				new_coeff[i] = otherPoly.coefficients[index_other_poly];
			} else if (index_other_poly == -1) {
				new_coeff[i] = this.coefficients[index_our_poly];
			} else {
				new_coeff[i] = this.coefficients[index_our_poly] + otherPoly.coefficients[index_other_poly];
			}
		}
		
		// Check if there are any zero entries and if so, remove them
		int num_zeros = 0;
		for (int i = 0; i < length_final; i++) {
			if (new_coeff[i] == 0) {
				num_zeros++;
			}
		}
		
		if (num_zeros != 0) {
			int new_length = length_final - num_zeros;
			int final_exponents[] = new int[new_length];
			double final_coeff[] = new double[new_length];
			
			for (int i = 0; i < length_final; i++) {
				if (new_coeff[i] != 0) {
					final_coeff[i] = new_coeff[i];
					final_exponents[i] = new_exponents[i];
				}
			}
			
			Polynomial p = new Polynomial(final_coeff, final_exponents);
			return p;
		}

		Polynomial p = new Polynomial(new_coeff, new_exponents);
		return p;
	}

	public double evaluate(double x) {
		double sum = coefficients[0];
		for (int i = 1; i < coefficients.length; i++) {
			sum += (coefficients[i] * Math.pow(x, exponents[i]));
		}
		return sum;
	}
	
	public boolean hasRoot(double val) {
		return evaluate(val) == 0;
	}
	
	public Polynomial multiply(Polynomial otherPoly) {		
		double new_coeff[] = new double[0];
		int new_exponents[] = new int[0];
		Polynomial new_poly = new Polynomial(new_coeff, new_exponents);
		
		for (int i = 0; i < this.coefficients.length; i++) {
			double part_coeff[] = new double[this.coefficients.length];
			int part_expon[] = new int[this.exponents.length];
			for (int j = 0; j < otherPoly.coefficients.length; j++) {
				part_coeff[j] = this.coefficients[i] * otherPoly.coefficients[j];
				part_expon[j] = this.exponents[i] + otherPoly.exponents[j];
			}
			Polynomial inner_poly = new Polynomial(part_coeff, part_expon);
			new_poly = new_poly.add(inner_poly);
		}
		
		return new_poly;
	}
	
	
	public void saveToFile(String file) throws Exception {
		
		// Check if both are NULL
		if (this.coefficients == null || this.exponents == null) {
			System.out.println("Coefficients and exponents array are null");
			return;
		}
		
		// Check if both have the same length
		if (this.coefficients.length != this.exponents.length) {
			System.out.println("EXPONENTS AND COEFFICIENTS DON'T HAVE THE SAME LENGTH!!! :(");
			return;
		}
		
		String writeString = "";
		
		for (int i = 0; i < this.coefficients.length; i++) {
			writeString += coefficients[i];
			if (this.exponents[i] != 0) {
				writeString += "x" + this.exponents[i];
			}
			writeString += "+";
		}
		
		if (writeString.endsWith("+")) {
			writeString = writeString.substring(0, writeString.length() - 1);
		}
		
		FileWriter myWriter = new FileWriter(new File(file));
		myWriter.write(writeString);
		myWriter.close();
	}
	
}