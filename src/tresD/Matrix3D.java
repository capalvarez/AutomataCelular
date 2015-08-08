package tresD;

import celullarAutomata.RuleComputer;

public class Matrix3D {
	private Cell3D[][][] matrix;
	private boolean reading;
	private int finished;
	private final Object readingObject = new Object();
	private final Object finishObject = new Object();
	private int m;
	private int phases;
	private int threadNum;
	public int[] rules;
	
	public Matrix3D(int m, int p, int N, int[] rules, boolean[] initValues){
		matrix = new Cell3D[m][m][m];	
		this.m = m;
		this.phases = p;
		this.threadNum = N;
		this.rules = rules;
		this.finished = threadNum;
		
		for(int i=0;i<m;i++){
			for(int j=0;j<m;j++){
				for(int k=0;k<m;k++){
					int index = (int) (k*Math.pow(m, 2) + i*m + j);
					matrix[i][j][k] = new Cell3D(initValues[index],index,this);
				}
				
			}
		}
	}
	
	public Cell3D getValue(int i, int j, int k){
		return matrix[i][j][k];
	}
	
	public int getM(){
		return m;
	}
	
	public int getC(){
		return (int)Math.pow(m,3);
	}
	
	public int getPhases(){
		return phases;
	}
	
	public int getN(){
		return threadNum;
	}
	
	public RuleComputer getRules(){
		return new RuleComputer(rules);
	}
	
	public Boolean reading(){
		return reading;
	}
	
	public Object readingLock(){
		return readingObject;
	}
	
	public Integer finished(){
		return finished;
	}
	
	public Object finishLock(){
		return finishObject;
	}
	
	public void nextStep(){
		finished = threadNum;	
	}
	
	public void addWorking(){
		finished++;
	}
	
	public void substractWorking(){
		finished--;
	}
	
	public void changeReadingStatus(boolean status){
		reading = status;
	}
	
	
	public void changeValue(int i, int j, int k, boolean value){
		matrix[i][j][k].changeValue(value);
	}
	
	public void computeMatrixQuotient() throws InterruptedException{
		Thread[] threads = new Thread[threadNum];
		int d = (int)Math.pow(m, 3)/threadNum;
				
		for(int i=0;i<threadNum;i++){
			int start = i*d;
			int end = (i+1)*d;
			
			threads[i] = new Thread(new QuotientAutomata3D(this,start,end)); 
			threads[i].start();
		}	
		
		for(int i=0;i<threadNum;i++){
			threads[i].join();
		}
		
		
	}
	
	public void computeMatrixModule() throws InterruptedException{
		Thread[] threads = new Thread[threadNum];
						
		for(int i=0;i<threadNum;i++){			
			threads[i] = new Thread(new ModuleAutomata3D(this,i)); 
			threads[i].start();
		}
		
		for(int i=0;i<threadNum;i++){
			threads[i].join();
		}
	}
}
