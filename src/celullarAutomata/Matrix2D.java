package celullarAutomata;

public class Matrix2D {
	private Cell[][] matrix;
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
		
	public void computeMatrixQuotient(int threadNum){
		
	}
	
	public void computeMatrixModule(int threadNum){
		
	}
	
	
	
	
}
