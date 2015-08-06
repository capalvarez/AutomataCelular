package dosD;

import celullarAutomata.Cell;

public class Cell2D implements Cell{
	private int myIndex;
	private Matrix2D matrix;
	private int light;
	
	public Cell2D(boolean init, int index, Matrix2D m){
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
				if(i==0 && j==0){
					continue;
				}
				int i1 = (matrix.getM() + myIndex+i)%matrix.getM();
				int i2 = (matrix.getM() + myIndex+j)%matrix.getM();

				N = N + matrix.getValue(i1,i2).state();
			}
		}
		
		return N;
	}
	
	
}
