package tresD;

import celullarAutomata.RuleComputer;

public class QuotientAutomata3D implements Runnable{
	private int initIndex;
	private int endIndex;
	private RuleComputer rc;
	private Matrix3D matrix;
	private int m;
	
	public QuotientAutomata3D(Matrix3D m, int init, int end){
		matrix = m;
		initIndex = init;
		endIndex = end;
		this.m = matrix.getM();
		this.rc = matrix.getRules();
	}
	
	public void run(){
		for(int e=0;e<matrix.getPhases();e++){
			boolean[] values = new boolean[endIndex-initIndex];
			
			synchronized(matrix.readingLock()){
	   			matrix.changeReadingStatus(true);	
	   		}
					
		   	for(int k=initIndex;k<endIndex;k++){
		   		int z = (int)(k/Math.pow(m,2));
		   		int x = (int)((k - z*Math.pow(m,2))/m);
				int y = (int)(k - x*m - z*Math.pow(m, 2));

				values[k-initIndex] = rc.getNewValue(matrix.getValue(x, y, z));	
		   	}
		   	
		   	synchronized(matrix.readingLock()){
		   		matrix.substractWorking();
	   			
		   		if(matrix.finished()<1){   				
	   				matrix.changeReadingStatus(false);	
	   				matrix.nextStep();
	   				
	   				if(matrix.getN()>1){ 
	   					matrix.readingLock().notifyAll();
	   				}	
	   			}
		   	}
		   	
		   	synchronized(matrix.readingLock()){
	   			if(matrix.reading()){
	   				try {
						matrix.readingLock().wait();
					} catch (InterruptedException exception) {
						exception.printStackTrace();
					}
	   			}
		   	}	
		   	
		   	for(int k=initIndex;k<endIndex;k++){
		   		int z = (int)(k/Math.pow(m,2));
		   		int x = (int) ((k - z*Math.pow(m,2))/m);
				int y = (int)(k - x*m - z*Math.pow(m, 2));
		   		
		   		matrix.changeValue(x,y,z,values[k-initIndex]);			
		   	}
		   	
		   	synchronized(matrix.finishLock()){
				if(matrix.finished()>1){
					matrix.substractWorking();
					try{
						matrix.finishLock().wait();
					}catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}else{
					matrix.nextStep();
					
					if(matrix.getN()>1){
						matrix.finishLock().notifyAll();
					}
				}
			}
		   	
		}
	   		   	
	}
}
