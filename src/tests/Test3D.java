package tests;

import tresD.Matrix3D;
import celullarAutomata.RandomValues;


public class Test3D {
	public static void main(String[] args) throws InterruptedException{
		int m = 3;
		int p = 2;
		int N = 1;
		int[] rules = {1,2,2,4};	
		
		Matrix3D matrix = new Matrix3D(m,p,N,rules,(new RandomValues((int)Math.pow(m,3))).getRandomValues());

		matrix.computeMatrixModule();	
	}
}
