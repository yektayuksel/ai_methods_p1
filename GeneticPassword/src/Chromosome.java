 import java.util.ArrayList;

public class Chromosome {
	
	ArrayList<Character> genes;
	int fitnessValue;
	Chromosome(){
		this.genes = new ArrayList<Character>();
		this.fitnessValue = 0;
		
	}
	public int getFitnessValue() {
		return fitnessValue;
	}
	public void setFitnessValue(int fitnessValue) {
		this.fitnessValue = fitnessValue;
	}
	
	
	public ArrayList<Character> getGenes() {
		return genes;
	}
	public void setGenes(ArrayList<Character> genes) {
		this.genes = genes;
	}
	public void calculateFitnessVal(String password) {
		int fv = 0;
		for (int i = 0; i < this.genes.size(); i++) {
			if (this.genes.get(i)== password.charAt(i)) {
				fv++;
			}
		}
		this.setFitnessValue(fv);
	}
		
}
