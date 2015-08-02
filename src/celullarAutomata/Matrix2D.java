package celullarAutomata;

public class Matrix2D {
	private Cell[][] matrix;
	private boolean reading;
	private int m;
	public int[] rules;
	
	public Matrix2D(int m, int[] rules, boolean[] initValues){
		matrix = new Cell[m][m];	
		this.m = m;
		this.rules = rules;
		
		for(int i=0;i<m;i++){
			for(int j=0;j<m;j++){
				matrix[i][j] = new Cell(initValues[i*m + j]);
			}
		}
	}
	
	public Cell getValue(int i, int j){
		return matrix[i][j];
	}
	
	public int getM(){
		return m;
	}
	
	public RuleComputer getRules(){
		return new RuleComputer(rules);
	}
	
	public Boolean reading(){
		return reading;
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
			
			threads[i] = new Thread(new QuotientAutomata(this,start,end)); 
			threads[i].start();
		}		
	}
	
	public void computeMatrixModule(int threadNum){
		
	}
	
	
	
	
}
