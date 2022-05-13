import java.util.Random;

public class State {

	int[][] matrix;
	int score;
	int numOfQueens;

	State(int numOfQueens) {
		this.numOfQueens = numOfQueens;
		this.matrix = new int[this.numOfQueens][this.numOfQueens];
		this.score = 0;
	
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		
		for(int i = 0; i < this.numOfQueens; i++) {
			for(int j = 0; j < this.numOfQueens; j++) {
				this.matrix[i][j] = matrix[i][j];
			}
		}
		
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void calculateScore() {
		this.setScore(0);
		for (int i = 0; i < this.numOfQueens; i++) {
			for (int j = 0; j < this.numOfQueens; j++) {
				if (this.matrix[i][j] == 1) {

					checkConflict(i, j);
					
				}
			}
		}
	}

	public void checkConflict(int i, int j) {
		for (int k = 0; k < this.numOfQueens; k++) {
			for (int l = 0; l < this.numOfQueens; l++) {
				if ((k != i || l != j) && this.matrix[k][l] == 1) {
					if (k == i || l == j) {
						this.score++;
					} else if (Math.abs(i - k) == Math.abs(j - l)) {
						this.setScore(this.getScore() + 1);
					}
				}
			}
		}
	}

	public void randomRestart() {
		for (int i = 0; i < this.numOfQueens; i++) {
			for (int j = 0; j < this.numOfQueens; j++) {
				this.matrix[i][j] = 0;
			}
		}


		for(int j = 0; j < this.numOfQueens; j++) {
			int i = new Random().nextInt(this.numOfQueens - 1);
			this.matrix[i][j] = 1;
		}
		this.calculateScore();
	}

	public void calcNextState() {
		
		for(int j = 0; j < this.numOfQueens; j++) {	
			for(int i = 0; i < this.numOfQueens; i++) {
				
				if(this.matrix[i][j] == 1) {
					
					this.setMatrix(moveVerticalAndCheck(i,j).getMatrix());
					break;
				}
			}
		}
		this.calculateScore();
		
	}	
		
	public State moveVerticalAndCheck(int i, int j) {
		State s = new State(this.numOfQueens);
		s.setMatrix(this.getMatrix());
		s.getMatrix()[i][j] = 0;
		for (int k = 0; k < this.numOfQueens; k++) {
		
				s.getMatrix()[k][j] = 1;
				s.calculateScore();
				if (s.getScore() < this.getScore()){
					return s;
				}else{
					s.getMatrix()[k][j] = 0;
				}
		}
		return this;
	}
	public void printState() {
		
		for (int i = 0; i < this.numOfQueens; i++) {
			
			System.out.printf("%9s", " ");
			for (int j = 0; j < this.numOfQueens; j++) {
				System.out.printf("%3d", this.matrix[i][j]);
			}
			System.out.println();
			
		}
	}

}
