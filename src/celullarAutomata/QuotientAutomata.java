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
		boolean[] values = new boolean[endIndex-initIndex];
		
		synchronized(matrix.reading()){
   			matrix.changeReadingStatus(true);	
   		}
				
	   	for(int k=initIndex;k<endIndex;k++){
	   		int i = k%m;
	   		int j = k - i*m;
				   		
			values[k-initIndex] = rc.getNewValue(matrix.getValue(i, j));	
	   	}
	   	
	   	synchronized(matrix.reading()){
   			matrix.changeReadingStatus(false);	
   			matrix.reading().notify();
	   	}
	   	
	   	for(int k=initIndex;k<endIndex;k++){
	   		int i = k%m;
	   		int j = k - i*m;
				
	   		synchronized(matrix.reading()){
	   			if(matrix.reading()){
	   				try {
						matrix.reading().wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	   			}
	   			matrix.changeValue(i,j,values[k-initIndex]);	
	   		}
	   	}
	   	
	   		   	
	}
}

