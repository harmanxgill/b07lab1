public class Polynomial{

	// declaring an array
	double[]  coefficients;
	
	public Polynomial(){
	
		coefficients = new double[1];
		coefficients[0] = 0;
		
		
	}
	
	public Polynomial(double[] values){
	
		coefficients = values;
	
	}
	
	
	public Polynomial add(Polynomial polynomial2){
	
		int length1 = coefficients.length;
		int length2 = polynomial2.coefficients.length;
		
		int max_length = Math.max(length1, length2);
		int min_length = Math.min(length1, length2);

		double[] result_coefficients = new double[max_length];
		
		for (int i = 0; i < min_length; i++){
		
			result_coefficients[i] = coefficients[i] + polynomial2.coefficients[i];
		
		}
		
		int difference = Math.abs(length1 - length2);
		
		if (length1 > length2){
			for (int i = 0; i < difference; i++){
				result_coefficients[min_length + i] = coefficients[min_length + i];
		    }
		}
		else if (length2 > length1){
			for (int i = 0; i < difference; i++) {
				result_coefficients[min_length + i] = polynomial2.coefficients[min_length + i];
			}
		}
		
		return new Polynomial(result_coefficients);
	
	}
	
	public double evaluate(double x){
	
		int length = coefficients.length;
		double result = 0.0;
		
		for (int i = 0; i < length; i++){
		
			result += coefficients[i]*Math.pow(x, i);
		
		}
		
		return result;
	
	}
	
	public boolean hasRoot(double x){
	
		double output = evaluate(x);
		
		return output == 0;
	
	}
	

}
