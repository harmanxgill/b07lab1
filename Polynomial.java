import java.io.*;
import java.util.Scanner;

public class Polynomial{

	// declaring an array
	public double[]  coefficients;
	public int[] exponents;
	
	public Polynomial(){
	
		this.coefficients = new double[1];
		this.coefficients[0] = 0;
		this.exponents = new int[1];
		this.exponents[0] = 0;
		
	}
	
	public Polynomial(double[] coefficient_values, int[] exponent_values){
	
		this.coefficients = coefficient_values;
		this.exponents = exponent_values;
	
	}

	public Polynomial(File file) throws IOException{

		Scanner newScanner = new Scanner(file);
		String equation = newScanner.nextLine();
		
		equation = equation.replace("-", "+-");

		if(equation.charAt(0) == '+'){
            equation = equation.substring(1, equation.length());

        }

		String[] split_terms = equation.split("\\+");
		double[] coefficients = new double[split_terms.length];
		int[] exponents = new int[split_terms.length];
		String[] split_exponents = null;

		for (int i = 0; i < split_terms.length; i++){
			split_exponents = split_terms[i].split("x");
			coefficients[i] = Double.parseDouble(split_exponents[0]);
			if (split_exponents.length == 1){
				exponents[i] = 0;
			}
			else{
				exponents[i] = Integer.parseInt(split_exponents[split_exponents.length-1]);
			}
		}

		this.coefficients = coefficients;
		this.exponents = exponents;

		newScanner.close();
	}
	
	
	public Polynomial add(Polynomial polynomial2){

		if (exponents == null && polynomial2.exponents == null){	
			return new Polynomial();
		}

		int max_exponent = Math.max(get_max_exponent(exponents), get_max_exponent(polynomial2.exponents)) + 1;

		double[] temp_coefficients = new double[max_exponent];
		for (int i = 0; i < temp_coefficients.length; i++){
			temp_coefficients[i] = 0;
		}

		if (exponents != null){
			for (int i = 0; i < exponents.length; i++){
				temp_coefficients[exponents[i]] += coefficients[i]; 
			}
		}

		if (polynomial2.exponents != null){
			for (int i = 0; i < polynomial2.exponents.length; i++){
				temp_coefficients[polynomial2.exponents[i]] += polynomial2.coefficients[i]; 
			}
		}

		int non_zero_terms = count_non_zero_terms(temp_coefficients);

		double[] result_coefficients = new double[non_zero_terms];
		int[] result_exponents = new int[non_zero_terms];
		int index = 0;

		for (int i = 0; i < temp_coefficients.length; i++){
			if (temp_coefficients[i] != 0){
				result_coefficients[index] = temp_coefficients[i];
				result_exponents[index] = i;
				index+=1;
			}
		}

		return new Polynomial(result_coefficients, result_exponents);
	
	}
	
	public double evaluate(double x){
	
		double result = 0.0;
		
		for (int i = 0; i < coefficients.length; i++){
		
			result += coefficients[i]*Math.pow(x, exponents[i]);
		
		}
		
		return result;
	
	}
	
	public boolean hasRoot(double x){
	
		double output = evaluate(x);
		
		return output == 0;
	
	}

	public Polynomial multiply(Polynomial polynomial2){

		if (exponents == null && polynomial2.exponents == null){		// if polynomial1 and polynomial2 are null, then return a new empty polynomial
			return new Polynomial();
		}

		int max_exponent = -1;
		for (int i = 0; i < this.exponents.length; i++){
            for (int j = 0; j < polynomial2.exponents.length; j++){
                max_exponent = Math.max(max_exponent, this.exponents[i] + polynomial2.exponents[j]);
            }
        }

		max_exponent+=1;
		double[] temp_coefficients = new double[max_exponent];
		for (int i = 0; i < max_exponent; i++){
			temp_coefficients[i] = 0;
		}


		for (int i = 0; i < exponents.length; i++){
			for (int j = 0; j < polynomial2.exponents.length; j++){
				temp_coefficients[this.exponents[i] + polynomial2.exponents[j]] += this.coefficients[i] * polynomial2.coefficients[j];
			}
		}

		int non_zero_terms = count_non_zero_terms(temp_coefficients);

		double[] result_coefficients = new double[non_zero_terms];
		int[] result_exponents = new int[non_zero_terms];
		int index = 0;

		for (int i = 0; i < temp_coefficients.length; i++){
			if (temp_coefficients[i] != 0){
				result_coefficients[index] = temp_coefficients[i];
				result_exponents[index] = i;
				index+=1;
			}
		}

		return new Polynomial(result_coefficients, result_exponents);


	}

	private int get_max_exponent(int[] exponents){

		if (exponents == null || exponents.length == 0) {
			return -1;
		}

		int max_exponent = -1;

		for (int i = 0; i < exponents.length; i++){
			max_exponent = Math.max(max_exponent, exponents[i]);
		}

		return max_exponent;

	}

	private int count_non_zero_terms(double[] coefficients){

		int count = 0;

		for (int i = 0; i < coefficients.length; i++){
			if (coefficients[i] != 0){
				count+=1;
			}
		}

		return count;

	}

	public void saveToFile(String filepath) throws IOException{

		FileWriter newWriter = new FileWriter(filepath);

		String result = "";

		if (this.exponents == null && this.coefficients == null){
			System.out.println("Received null arrays for coefficients and exponents");
			newWriter.close();
			return;
		}

		if (this.exponents.length != this.coefficients.length){
			System.out.println("Received arrays with different lengths for coefficients and exponents");
			newWriter.close();
			return;
		}

		if (this.exponents != null){
			for (int i = 0; i < this.exponents.length; i++){
				if (this.coefficients[i] > 0 && i > 0){
					result += '+';
				}
				
				result += Double.toString(this.coefficients[i]);
				if (this.exponents[i] != 0){
					result += "x";
					if (this.exponents[i] > 1){
						result += Integer.toString(this.exponents[i]);
					}
				}

			}

			newWriter.write(result);
			newWriter.close();

		}

	




	}
	

}
