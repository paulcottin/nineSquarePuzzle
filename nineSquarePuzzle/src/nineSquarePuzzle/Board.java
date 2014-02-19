package nineSquarePuzzle;

import java.util.ArrayList;


public class Board {

	public static int GAUCHE_HAUT = 0, CENTRE_HAUT = 1, DROITE_HAUT = 2, GAUCHE = 3, CENTRE = 4, DROITE = 5, GAUCHE_BAS = 6, CENTRE_BAS = 7, DROITE_BAS = 8;
	
	ArrayList<Piece> positions;
	boolean[] positionOccupees = new boolean[9];
	int[] orientation = new int[9];
	private Pool pool;
	
	public Board(String path){
		/*
		 * Initialiser positions avec des pieces vides (cr�er un constructeur vide dans Piece)
		 * Changer positions.add(indice, p) par un set
		 * */
		positions = new ArrayList<Piece>();
		for (int i = 0; i < 9; i++) {//Au d�part les cases sont occup�es par des pi�ces vides
			positions.add(new Piece());
		}
		for (int i = 0; i < 9; i++) {//Au d�part aucune position n'est occup�e
			positionOccupees[i] = false;
		}
		for (int i = 0; i < 9; i++) {
			orientation[i] = 0;
		}
		pool = new Pool(path);
	}
	
	public void positionner(Piece p, int indice){
		if (indice < 9) {
			if (!positionOccupees[indice]) {
				positions.set(indice, p);
				positionOccupees[indice] = true;
			}
		}else {
			System.out.println("Indice > 9 !!!");
		}
	}
	
	public void retirer(Piece p){
		for (int i = 0; i < positions.size(); i++) {
			if (p.equals(positions.get(i))) {
				positions.set(i, new Piece());//Au lieu de supprimer on met une pi�ce vide
				positionOccupees[i] = false;
			}
		}
	}
	
	public void resetPieceOrientation(Piece p){
		for (Piece p1 : this.pool.getPool()) {
			if (p1.equals(p)) {
				while (p1.getNorth() != p.getNorth()) {
					p.tourne(1);
				}
				p.setOrientation(0);
			}
		}
	}

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	public ArrayList<Piece> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<Piece> positions) {
		this.positions = positions;
	}

	public boolean[] getPositionOccupees() {
		return positionOccupees;
	}

	public void setPositionOccupees(boolean[] positionOccupees) {
		this.positionOccupees = positionOccupees;
	}
	
	
}
