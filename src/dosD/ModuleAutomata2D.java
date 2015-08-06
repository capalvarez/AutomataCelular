package dosD;

import celullarAutomata.RuleComputer;

public class ModuleAutomata2D implements Runnable{
	private Matrix2D matrix;
	private int m;
	private int myIndex;
	private RuleComputer rc;
	private int kMax;
	
	public ModuleAutomata2D(Matrix2D m, int index){
		matrix = m;
		this.m = matrix.getM();
		this.rc = matrix.getRules();
		this.kMax = matrix.getC()/matrix.getN();
		this.myIndex = index; 
	}
	
	public void run(){
		for(int e=0;e<matrix.getPhases();e++){		
			boolean[] values = new boolean[kMax];
			
			synchronized(matrix.readingLock()){
	   			matrix.changeReadingStatus(true);	
	   		}
			
			for(int k=0;k<kMax;k++){				
				int l = myIndex + k*matrix.getN();
				int i = (int)(l/m);
				int j = l - i*m;
						   		
				values[k] = rc.getNewValue(matrix.getValue(i, j));	
			}
			   	
			synchronized(matrix.readingLock()){
				matrix.changeReadingStatus(false);	
				
				if(matrix.getN()>1 && matrix.finished()<1){   				
	   				matrix.changeReadingStatus(false);	
	   				matrix.nextStep();
	   				
	   				matrix.readingLock().notifyAll();
	   			}	
			}
			
			synchronized(matrix.readingLock()){
	   			while(matrix.reading()){
	   				try {
						matrix.readingLock().wait();
					} catch (InterruptedException exception) {
						exception.printStackTrace();
					}
	   			}
		   	}
			
			for(int k=0;k<kMax;k++){
				int l = myIndex + k*matrix.getN();
				int i = (int)(k/m);
				int j = l - i*m;
					
		   		matrix.changeValue(i,j,values[k]);
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
					matrix.substractWorking();
					
					if(matrix.getN()>1){
						matrix.finishLock().notifyAll();
		   			}	
				}
			}
		}			
	}
}
