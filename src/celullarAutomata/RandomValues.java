package celullarAutomata;

public class RandomValues {
	boolean[] values; 
	
	public RandomValues(int length){
		values = new boolean[length];
	}
	
	public boolean[] getRandomValues(){
		for(int i=0;i<values.length;i++){
			values[i] = getRandomBoolean();
		}
		
		return values;
	}
	
	 public boolean getRandomBoolean() {
		 return Math.random() < 0.5;
	 }
}
