package tresD;

import celullarAutomata.RuleComputer;

public class ModuleAutomata3D implements Runnable{
	private Matrix3D matrix;
	private int m;
	private int myIndex;
	private RuleComputer rc;
	private int kMax;
	
	public ModuleAutomata3D(Matrix3D m, int index){
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
				int z = (int)(l/Math.pow(m,2));
		   		int x = (int) ((l - z*Math.pow(m,2))/m);
				int y = (int)(l - x*m - z*Math.pow(m, 2));
						   		
				values[k] = rc.getNewValue(matrix.getValue(x, y, z));	
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
			
			for(int k=0;k<kMax;k++){
				int l = myIndex + k*matrix.getN();
				int z = (int)(l/Math.pow(m,2));
		   		int x = (int) ((l - z*Math.pow(m,2))/m);
		   		int y = (int)(l - x*m - z*Math.pow(m, 2));
					
		   		matrix.changeValue(x,y,z,values[k]);	
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
