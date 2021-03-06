package graphique;

import nineSquarePuzzle.Board;

public class BoardCarre {

	Carre[] carres = new Carre[9];
	Board board;
	int carreX, carreY;
	
	public BoardCarre(Board board,int fenetreX, int fenetreY){
		this.board = board;
		this.carreX = fenetreX;
		this.carreY = fenetreY;
		charge();
	}
	
	public void initialise(){
		for (int i = 0; i < 9; i++) {
			carres[i] = new Carre(carreX, carreY);
		}
	}
	
	public void charge(){
		for (int i = 0; i < 9; i++) {
			if (board.getPositions().get(i).getNom().equals("*")) {
				carres[i] = new Carre(carreX, carreY);
			}else{
			carres[i] = new Carre(carreX, carreY, board.getPool().getCouleursPiece()[i], board.getPositions().get(i));
			}
		}
	}

	public Carre[] getCarres() {
		return carres;
	}

	public void setCarres(Carre[] carres) {
		this.carres = carres;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}
