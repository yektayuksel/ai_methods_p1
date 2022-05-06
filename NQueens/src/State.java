
public class State {
	
	int[][] matrix;
	int score;
	int numOfQueens;
	State(int numOfQueens){
		this.numOfQueens = numOfQueens;
		this.matrix = new int[this.numOfQueens][this.numOfQueens];
		this.score=0;
	}
	public int[][] getMatrix() {
		return matrix;
	}
	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public void calculateScore() {
		for(int i = 0; i < this.numOfQueens; i++) {
			for(int j = 0; j < this.numOfQueens; j++) {
				if(this.matrix[i][j] == 1) {
					checkHorizontal(i,j);
					checkVertical(i,j);
					checkDiagonal(i,j);
				}
			}
		}
	}
	
	public void checkHorizontal(int i, int j) { //yatay
		
		for(int k = 0; k < this.numOfQueens; k++) {
			if(k!=j && matrix[i][k] == 1) {
				this.score++;
			}
		}
		
	}
	public void checkVertical(int i, int j) { //dikey
	
		for(int k = 0; k < this.numOfQueens; k++) {
			if(k!=i && matrix[k][i] == 1) {
				this.score++;
			}
		}
	}
	public void checkDiagonal(int i, int j) { //çapraz
		
		
	}

}
