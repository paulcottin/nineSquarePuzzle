package nineSquarePuzzle;

import java.util.ArrayList;


public class Board {

	public static int GAUCHE_HAUT = 0, CENTRE_HAUT = 1, DROITE_HAUT = 2, GAUCHE = 3, CENTRE = 4, DROITE = 5, GAUCHE_BAS = 6, CENTRE_BAS = 7, DROITE_BAS = 8;
	
	ArrayList<Piece> positions;
	ArrayList<Boolean> positionOccupees;
	ArrayList<Integer> inclinaison = new ArrayList<Integer>();
	private Pool pool;
	
	public Board(String path){
		positions = new ArrayList<Piece>();
		positionOccupees = new ArrayList<Boolean>();
		pool = new Pool(path);
	}
	
	public void positionner(Piece p, int indice){
		if (!positionOccupees.get(indice)) {
			positions.add(indice, p);
		}
	}

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}
	
	
}
