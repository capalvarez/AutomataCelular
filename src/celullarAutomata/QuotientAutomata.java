package celullarAutomata;

public class QuotientAutomata implements Runnable{
	private int initIndex;
	private int endIndex;
	private RuleComputer rc;
	private Matrix2D matrix;
	private int m;
	
	public QuotientAutomata(Matrix2D m, int init, int end){
		matrix = m;
		initIndex = init;
		endIndex = end;
		this.m = matrix.getM();
		this.rc = matrix.getRules();
	}
	
	public void run(){
	   	for(int k=initIndex;k<endIndex;k++){
	   		int i = k%m;
	   		int j = k - i*m;
				
			rc.getNewValue(matrix.getValue(i, j));	
		}
	}
}

