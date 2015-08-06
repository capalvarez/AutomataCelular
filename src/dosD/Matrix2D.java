package dosD;

import celullarAutomata.RuleComputer;

public class Matrix2D{
	private Cell2D[][] matrix;
	private boolean reading;
	private final Object readingObject = new Object();
	private final Object finishObject = new Object();
	private int finished;
	private int m;
	private int phases;
	private int threadNum;
	public int[] rules;
	
	public Matrix2D(int m, int p, int N, int[] rules, boolean[] initValues){
		matrix = new Cell2D[m][m];	
		this.m = m;
		this.phases = p;
		this.threadNum = N;
		this.rules = rules;
		this.finished = threadNum;
		
		for(int i=0;i<m;i++){
			for(int j=0;j<m;j++){
				int index = i*m + j;
				matrix[i][j] = new Cell2D(initValues[index],index,this);
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
		return threadNum;
	}
	
	public RuleComputer getRules(){
		return new RuleComputer(rules);
	}
	
	public Object readingLock(){
		return readingObject;
	}
	
	public boolean reading(){
		return reading;
	}
	
	public Object finishLock(){
		return finishObject;
	}
	
	public Integer finished(){
		return finished;
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
	
	public void changeValue(int i, int j, boolean value){
		matrix[i][j].changeValue(value);
	}
	
	public void computeMatrixQuotient() throws InterruptedException{
		Thread[] threads = new Thread[threadNum];
		int d = (int)Math.pow(m, 2)/threadNum;
				
		for(int i=0;i<threadNum;i++){
			int start = i*d;
			int end = (i+1)*d;
			
			threads[i] = new Thread(new QuotientAutomata2D(this,start,end)); 
			threads[i].start();
		}
		
		System.out.println("Done!");
		
		for(int i=0;i<threadNum;i++){
			threads[i].join();
		}
		
		//printMatrix();
	}
	
	public void computeMatrixModule(){
		Thread[] threads = new Thread[threadNum];
					
		for(int i=0;i<threadNum;i++){			
			threads[i] = new Thread(new ModuleAutomata2D(this,i)); 
			threads[i].start();
		}
		
		System.out.print("Done!");
		printMatrix();
		
	}
	
	public void printMatrix(){
		for (int i = 0; i<m; i++) {
		    for (int j = 0;j<m; j++) {
		        System.out.print(matrix[i][j].state() + " ");
		    }
		    System.out.print("\n");
		}
	}
	
	
	
	
}
