package nineSquarePuzzle;

import java.util.ArrayList;

public class Solution {

	ArrayList<Piece> board = new ArrayList<Piece>();
	
	public Solution(ArrayList<Piece> board){
		this.board = board;
	}
	
	public String toString(){
		return (this.getBoard().get(Board.GAUCHE_HAUT).toString()+""+this.getBoard().get(Board.CENTRE_HAUT).toString()+""+this.getBoard().get(Board.DROITE_HAUT).toString()+"\n"+this.getBoard().get(Board.GAUCHE).toString()+""+this.getBoard().get(Board.CENTRE).toString()+""+this.getBoard().get(Board.DROITE).toString()+"\n"+this.getBoard().get(Board.GAUCHE_BAS).toString()+""+this.getBoard().get(Board.CENTRE_BAS).toString()+""+this.getBoard().get(Board.DROITE_BAS).toString());
	}

	public ArrayList<Piece> getBoard() {
		return board;
	}

	public void setBoard(ArrayList<Piece> board) {
		this.board = board;
	}
	
}
