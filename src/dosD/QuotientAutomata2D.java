package dosD;

import celullarAutomata.RuleComputer;

public class QuotientAutomata2D implements Runnable{
	private int initIndex;
	private int endIndex;
	private RuleComputer rc;
	private Matrix2D matrix;
	private int m;
	
	public QuotientAutomata2D(Matrix2D m, int init, int end){
		matrix = m;
		initIndex = init;
		endIndex = end;
		this.m = matrix.getM();
		this.rc = matrix.getRules();
	}
	
	public void run(){
		for(int e=0;e<matrix.getPhases();e++){
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
	   			matrix.reading().notifyAll();
		   	}
		   	
		   	for(int k=initIndex;k<endIndex;k++){
		   		int i = k%m;
		   		int j = k - i*m;
					
		   		synchronized(matrix.reading()){
		   			if(matrix.reading()){
		   				try {
							matrix.reading().wait();
						} catch (InterruptedException exception) {
							exception.printStackTrace();
						}
		   			}
		   			matrix.changeValue(i,j,values[k-initIndex]);	
		   		}
		   	}
		   	
		   	synchronized(matrix.finished()){
				if(matrix.finished()>1){
					matrix.substractWorking();
					try{
						matrix.finished().wait();
					}catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}else{
					matrix.nextStep();
					matrix.finished().notifyAll();
				}
			}
		   	
		}
	   		   	
	}
}

