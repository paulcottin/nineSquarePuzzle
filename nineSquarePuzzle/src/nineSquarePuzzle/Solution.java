package nineSquarePuzzle;

import java.util.ArrayList;

public class Solution {

	ArrayList<Piece> board = new ArrayList<Piece>();
	
	public Solution(ArrayList<Piece> board){
		this.board = board;
		System.out.println("dans solution : ");
		for (int i = 0; i < board.size(); i++) {
			System.out.println("\t"+this.board.get(i).getNom());
		}
	}

	public ArrayList<Piece> getBoard() {
		return board;
	}

	public void setBoard(ArrayList<Piece> board) {
		this.board = board;
	}
	
}
