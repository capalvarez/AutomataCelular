package dosD;

import celullarAutomata.Cell;


public class Cell2D implements Cell{
	private int myIndex;
	private Matrix2D matrix;
	private int light;
	
	public Cell2D(boolean init, int index){
		light = init? 1:0;
		myIndex = index;
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
			for(int j=-1;j<2;i++){
				if(i==0 && j==0){
					continue;
				}
				N = N + matrix.getValue(myIndex+i,myIndex+j).state();
			}
		}
		
		return N;
	}
}
