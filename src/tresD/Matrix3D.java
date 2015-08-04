package tresD;

import celullarAutomata.RuleComputer;

public class Matrix3D {
	private Cell3D[][][] matrix;
	private boolean reading;
	private int finished;
	private int m;
	private int phases;
	private int threads;
	public int[] rules;
	
	public Matrix3D(int m, int p, int N, int[] rules, boolean[] initValues){
		matrix = new Cell3D[m][m][m];	
		this.m = m;
		this.phases = p;
		this.threads = N;
		this.rules = rules;
		this.finished = threads;
		
		for(int i=0;i<m;i++){
			for(int j=0;j<m;j++){
				for(int k=0;k<m;k++){
					int index = i*m + j*m + k;
					matrix[i][j][k] = new Cell3D(initValues[index],index);
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
		return threads;
	}
	
	public RuleComputer getRules(){
		return new RuleComputer(rules);
	}
	
	public Boolean reading(){
		return reading;
	}
	
	public Integer finished(){
		return finished;
	}
	
	public void nextStep(){
		finished = threads;	
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
	
	public void computeMatrixQuotient(int threadNum){
		Thread[] threads = new Thread[threadNum];
		int d = (int)Math.pow(m, 3)/threadNum;
				
		for(int i=0;i<threadNum;i++){
			int start = i*d;
			int end = (i+1)*d;
			
			threads[i] = new Thread(new QuotientAutomata3D(this,start,end)); 
			threads[i].start();
		}		
	}
	
	public void computeMatrixModule(int threadNum){
		Thread[] threads = new Thread[threadNum];
						
		for(int i=0;i<threadNum;i++){			
			threads[i] = new Thread(new ModuleAutomata3D(this,i)); 
			threads[i].start();
		}	
	}
}
