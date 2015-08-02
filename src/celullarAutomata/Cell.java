package celullarAutomata;

public class Cell {
	private int myIndex;
	private Matrix2D matrix;
	private int light;
	
	public Cell(boolean init){
		light = init? 1:0;
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
				N = N + matrix.getValue(myIndex+i,myIndex+j).state();
			}
		}
		
		return N;
	}

}
