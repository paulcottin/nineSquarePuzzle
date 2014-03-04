package nineSquarePuzzle;

import java.util.ArrayList;
   

public class Board {

	public static int GAUCHE_HAUT = 0, CENTRE_HAUT = 1, DROITE_HAUT = 2, GAUCHE = 3, CENTRE = 4, DROITE = 5, GAUCHE_BAS = 6, CENTRE_BAS = 7, DROITE_BAS = 8;
	
	ArrayList<Piece> positions;
	boolean[] positionOccupees = new boolean[9];
	int[] orientation = new int[9];
	private Pool pool;
	String path;
	
	public Board(){
		positions = new ArrayList<Piece>();
		positionOccupees = new boolean[9];
		orientation = new int[9];
	}
	
	public Board(String path){
		/*
		 * Initialiser positions avec des pieces vides (créer un constructeur vide dans Piece)
		 * Changer positions.add(indice, p) par un set
		 * */
		positions = new ArrayList<Piece>();
		for (int i = 0; i < 9; i++) {//Au départ les cases sont occupées par des pièces vides
			positions.add(new Piece());
		}
		for (int i = 0; i < 9; i++) {//Au départ aucune position n'est occupée
			positionOccupees[i] = false;
		}
		for (int i = 0; i < 9; i++) {
			orientation[i] = positions.get(i).getOrientation();
		}
		pool = new Pool(path);
		this.path = path;
	}
	
	public void positionner(Piece p, int indice){
		if (indice < 9) {
			int indiceP = 0;
//			Récupération de l'indice de p
			for (int i = 0; i < this.getPool().getPool().size(); i++) {
				if (this.getPool().getPool().contains(p)) {
					indiceP = i;
				}
			}
			if (!positionOccupees[indice]) {
				while (p.getNom().equals("*")) {
					indiceP = indiceP++ % this.getPool().getPool().size();
					p = this.getPool().getPool().get(indiceP);
				}
				positions.set(indice, p);
				positionOccupees[indice] = true;
				pool.enlevePiece(p);
			}
		}else {
			System.out.println("Indice > 9 !!!");
		}
	}
	
	public void retirer(Piece p){
		for (int i = 0; i < positions.size(); i++) {
			if (p.equals(positions.get(i))) {
				positions.set(i, new Piece());//Au lieu de supprimer on met une pièce vide
				positionOccupees[i] = false;
				pool.ajoutePiece(p);
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

	public Board clone(){
		Board board = new Board();//A changer
		board.setPath(Main.path);
		board.setPool(this.getPool());
//		PositionsOccupees
		for (int i = 0; i < board.positionOccupees.length; i++) {
			board.positionOccupees[i] = this.getPositionOccupees()[i];
		}
		
//		Positions
		for (Piece p : this.positions) {
			board.positions.add(p);
		}

//		Orientations
		for (int i = 0; i < this.orientation.length; i++) {
			board.orientation[i] = this.orientation[i];
		}
		return board;
	}
	
	public void resetBoard(){
		for (Piece p : this.positions) {
			this.retirer(p);
		}
	}
	
	public String[] getInstance() {
		String[] instance = new String[9];
		for (int i = 0; i < this.getPositions().size(); i++) {
			instance[i] = this.getPositions().get(i).getNom();
		}
		return instance;
	}
	
	public String toString(String[] instance){
		return ("instance : "+instance[0]+", "+instance[1]+", "+instance[2]+", "+instance[3]+", "+instance[4]+", "+instance[5]+", "+instance[6]+", "+instance[7]+", "+instance[8]);
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int[] getOrientation() {
		return orientation;
	}

	public void setOrientation(int[] orientation) {
		this.orientation = orientation;
	}
	
	
}
