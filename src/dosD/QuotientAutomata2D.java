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
			
			synchronized(matrix.readingLock()){
				matrix.changeReadingStatus(true);	
	   		}
				
		   	for(int k=initIndex;k<endIndex;k++){
		   		int i = (int)(k/m);
		   		int j = k - i*m;
					   		
				values[k-initIndex] = rc.getNewValue(matrix.getValue(i, j));	
		   	}
		   	
		   	synchronized(matrix.readingLock()){
	   			matrix.substractWorking();
	   			   			
	   			if(matrix.getN()>1 && matrix.finished()<1){   				
	   				matrix.changeReadingStatus(false);	
	   				matrix.nextStep();
	   				matrix.readingLock().notify();
	   				System.out.println(Thread.currentThread().getId() + " termino su trabajo");
	   			}	
	   		}
		   	System.out.println("paso por aca?");
		   	
		   	synchronized(matrix.readingLock()){
	   			System.out.println("alguien trata de escribir " + Thread.currentThread().getId());
	   			System.out.println("reading " + matrix.reading());
	   			while(matrix.reading()){
	   				try {
	   					System.out.println("Trato de escribir " + Thread.currentThread().getId());
						matrix.readingLock().wait();
					} catch (InterruptedException exception) {
						exception.printStackTrace();
					}
	   			}
		   	}	
		   	
		   	for(int k=initIndex;k<endIndex;k++){
		   		int i = (int)(k/m);
		   		int j = k - i*m;   		
		   			
		   		System.out.println("escribo? " + Thread.currentThread().getId());
		   		matrix.changeValue(i,j,values[k-initIndex]);	
		   		
		   	}
		   	
		   	synchronized(matrix.finishLock()){
				if(matrix.finished()>0){
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

