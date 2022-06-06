import java.util.Arrays;
public class Board{ 
	private char[][] gameBoard;
	private int col, row;

	public Board(){
		col = 6;
		row = 5;
		gameBoard = new char[row][col];
		setBoard();
	}

	public void setBoard(){
		for (int i = 0; i < row; i++){
			for (int j = 0; j < col; j++){
				gameBoard[i][j] = '.';
			//	System.out.println(gameBoard[i][j]);
			}
			//System.out.println();
		}
	}


	public char getValues(int co, int ro){
		return gameBoard[co][ro];
	}
	
	public void setValue(int co, int ro, char charKey){
		gameBoard[co][ro] = charKey;
	}


	public void paintGreen() {
		
	}
	
	public void paintYellow() {
		
	}
	
	
	public void restart(){
		setBoard();
	}
}