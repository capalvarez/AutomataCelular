package celullarAutomata;

public class RuleComputer {
	private int n1;
	private int n2;
	private int n3;
	private int n4;
		
	public RuleComputer(int[] rules){
		this.n1 = rules[0];
		this.n2 = rules[1];
		this.n3 = rules[2];
		this.n4 = rules[3];
	}
	
	public boolean getNewValue(Cell c){
		int N = c.getNeighbours();
		
		if(c.state()==0){
			if(betweenLight(N)){
				return true;
			}else{
				return false;
			}
		}else{
			if(!betweenKeep(N)){
				return false;
			}else{
				return true;
			}
		}
	}
	
	private boolean betweenLight(int N){
		return n1<=N && N<n2;
	}

	private boolean betweenKeep(int N){
		return n3<=N && N<n4;
	}
}
