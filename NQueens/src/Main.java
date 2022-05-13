import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		int numOfQueens =8;
		double totalMov = 0;
		double totalRestart = 0;
		long totalTime = 0;
		ArrayList<State> solutions =  new ArrayList<State>();
		System.out.printf("%3s %8s %16s %14s\n"," ","Hamle sayýsý","Random Restart", "Geçen süre" );
		for(int i = 0; i < 15; i++) {
			long startTime = System.nanoTime();
			int movement = 0;
			int randomRestrat = 0;
			State s = new State(numOfQueens);
			s.randomRestart();
			while(s.getScore() != 0) {
				int prevScore = s.getScore();
				s.calcNextState();
				movement++;
				if(prevScore == s.getScore()) {
					s.randomRestart();
					randomRestrat++;
				}
			}
			long endTime = System.nanoTime();
			totalMov += movement;
			totalRestart  += randomRestrat;
			totalTime += endTime-startTime;
			
			System.out.println("---------------------------------------------------");
			System.out.printf("%2d. %6d %15d %20d\n",i+1,movement,randomRestrat, endTime - startTime );
			solutions.add(s);
			
			
		}
		
		System.out.println("---------------------------------------------------");
		System.out.printf("%4s %8s %15s %14s\n","Avg:","Hamle sayýsý","Random Restart", "Geçen süre" );
		System.out.printf("%3s %8.2f %15.2f %18d\n"," ",totalMov/15,totalRestart/15, totalTime/15);
		System.out.println("---------------------------------------------------");
		
		for(int i = 0; i < solutions.size(); i++) {
			System.out.printf("%d. çözüm: \n", i+1);
			solutions.get(i).printState();
			System.out.println("---------------------------------------------------");
			
		}

	}
	
	
}
