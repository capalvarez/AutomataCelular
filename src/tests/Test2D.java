package tests;

import celullarAutomata.RandomValues;
import dosD.Matrix2D;

public class Test2D {

	public static void main(String[] args) throws InterruptedException{
		int m = 10;
		int p = 2;
		int N = 1;
		int[] rules = {1,2,2,4};	
		
		Matrix2D matrix = new Matrix2D(m,p,N,rules,(new RandomValues((int)Math.pow(m,2))).getRandomValues());

		matrix.computeMatrixQuotient();	
	}
}
