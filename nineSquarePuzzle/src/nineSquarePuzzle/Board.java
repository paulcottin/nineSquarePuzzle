package nineSquarePuzzle;

import java.util.ArrayList;

public class Board {

	ArrayList<Piece> positions;
	ArrayList<Boolean> positionOccupees;
	ArrayList<Integer> inclinaison = new ArrayList<Integer>();
	private Pool pool;
	
	public Board(String path){
		positions = new ArrayList<Piece>();
		positionOccupees = new ArrayList<Boolean>();
		pool = new Pool(path);
	}
	
	public void positionner(int indice, Piece p){
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
