package dosD;

import celullarAutomata.RuleComputer;

public class Matrix2D{
	private Cell2D[][] matrix;
	private boolean reading;
	private int finished;
	private int m;
	private int phases;
	private int threads;
	public int[] rules;
	
	public Matrix2D(int m, int p, int N, int[] rules, boolean[] initValues){
		matrix = new Cell2D[m][m];	
		this.m = m;
		this.phases = p;
		this.threads = N;
		this.rules = rules;
		this.finished = threads;
		
		for(int i=0;i<m;i++){
			for(int j=0;j<m;j++){
				int index = i*m + j;
				matrix[i][j] = new Cell2D(initValues[index],index);
			}
		}
	}
	
	public Cell2D getValue(int i, int j){
		return matrix[i][j];
	}
	
	public int getM(){
		return m;
	}
	
	public int getC(){
		return (int)Math.pow(m,2);
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
	
	
	public void changeValue(int i, int j, boolean value){
		matrix[i][j].changeValue(value);
	}
	
	public void computeMatrixQuotient(int threadNum){
		Thread[] threads = new Thread[threadNum];
		int d = (int)Math.pow(m, 2)/threadNum;
				
		for(int i=0;i<threadNum;i++){
			int start = i*d;
			int end = (i+1)*d;
			
			threads[i] = new Thread(new QuotientAutomata2D(this,start,end)); 
			threads[i].start();
		}		
	}
	
	public void computeMatrixModule(int threadNum){
		Thread[] threads = new Thread[threadNum];
					
		for(int i=0;i<threadNum;i++){			
			threads[i] = new Thread(new ModuleAutomata2D(this,i)); 
			threads[i].start();
		}	
	}
	
	
	
	
}
