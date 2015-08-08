package tresD;

import celullarAutomata.Cell;

public class Cell3D implements Cell{
	private int myIndex;
	private Matrix3D matrix;
	private int light;
	
	public Cell3D(boolean init, int index, Matrix3D m){
		light = init? 1:0;
		myIndex = index;
		matrix = m;
	}
	
	public void changeValue(boolean value){
		light = value? 1:0;
	}
	
	public int state(){
		return light;
	}
	
	public int getNeighbours(){
		int N = 0;
		
		for(int i=-1;i<2;i++){
			for(int j=-1;j<2;j++){
				for(int k=-1;k<2;k++){
					if(i==0 && j==0 && k==0){
						continue;
					}
					
					int i1 = (matrix.getM() + myIndex+i)%matrix.getM();
					int i2 = (matrix.getM() + myIndex+j)%matrix.getM();
					int i3 = (matrix.getM() + myIndex+k)%matrix.getM();
					
					N = N + matrix.getValue(i1,i2,i3).state();
				}	
			}
		}
		
		return N;
	}
}
