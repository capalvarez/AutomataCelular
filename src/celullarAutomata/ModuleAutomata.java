package celullarAutomata;

public class ModuleAutomata implements Runnable{
	private int initIndex;
	private int endIndex;
	private Matrix2D matrix;
	
	public ModuleAutomata(Matrix2D m, int init, int end){
		matrix = m;
		initIndex = init;
		endIndex = end;
	}
	
	public void run(){
		for(int i=initIndex;i<endIndex;i++){
			for(int j=initIndex;j<endIndex;j++){
				
			}
		}
	}
	
	
}
