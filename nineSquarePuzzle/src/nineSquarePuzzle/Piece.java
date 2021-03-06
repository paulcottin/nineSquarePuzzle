package nineSquarePuzzle;

public class Piece {

	String nom;
	int north, south, east, west;
	String northSigne, southSigne, eastSigne, weastSigne;
	int orientation = 0;//Sert � faire tourner la pi�ce du bon nb de tours dans l'algo.
//	tableau permettant de stocker l'information : le nombre est positif/neg pour l'affichage
	String[] signes = new String[4];
	
//	Constructeur par default
	public Piece(){
		this.north = 0;	this.south = 0;	this.east = 0; this.west = 0;
		this.northSigne = "-"; this.southSigne = "-"; this.eastSigne = "-"; this.weastSigne = "-";
		this.nom = "*";
	}
	
	public Piece(String nom, int[] valeurs){
		this.nom = nom;
		if (valeurs.length == 4) {
			north = valeurs[0]; east = valeurs[1]; south = valeurs[2]; west = valeurs[3];
		}else {
			System.out.println("Le tableau a pour longueur 4 !");
		}
		if (north>0) {northSigne = " ";}else {northSigne = "-";}
		if (south>0) {southSigne = " ";}else {southSigne = "-";}
		if (west>0) {weastSigne = " ";}else {weastSigne = "-";}
		if (east>0) {eastSigne = " ";}else {eastSigne = "-";}
	}
	
//	Faire tourner la piece, de n quart, dans le sens des aiguilles d'une montre
	public void tourne(int n){
		for (int i = 0; i < n ; i++) {
			tourneUneFois();
		}
	}
	
	public void tourneUneFois(){
		int temp = north;String tempSign = northSigne;
		north = west;northSigne = weastSigne;
		west = south; weastSigne = southSigne;
		south = east; southSigne = eastSigne;
		east = temp; eastSigne = tempSign;
		orientation = orientation++ % 4; //Sert � placer les pi�ces dans le bon ordre dans le Board
	}
	
	public boolean estParfaite(){
		return true;
	}
	
	public boolean equals(Piece p){
		if (p.getNom().equals(this.nom)) {
			return true;
		}else {
			return false;
		}
	}
	
	public Piece clone(){
		return new Piece(this.nom, (new int[]{this.north, this.east, this.south, this.west}));
	}
	
	public String toString(){
		return ("+----------------+\n|\t"+this.northSigne+Math.abs(this.north)+" \t |\n|  "+this.weastSigne+Math.abs(this.west)+"   ["+this.nom+"]   "+this.eastSigne+Math.abs(this.east)+" |\n|\t"+this.southSigne+Math.abs(this.south)+"\t |\n+----------------+");
	}

	public int getNorth() {
		return north;
	}

	public void setNorth(int north) {
		this.north = north;
	}

	public int getSouth() {
		return south;
	}

	public void setSouth(int south) {
		this.south = south;
	}

	public int getEast() {
		return east;
	}

	public void setEast(int east) {
		this.east = east;
	}

	public int getWest() {
		return west;
	}

	public void setWest(int west) {
		this.west = west;
	}

	public String getNorthSigne() {
		return northSigne;
	}

	public void setNorthSigne(String northSigne) {
		this.northSigne = northSigne;
	}

	public String getSouthSigne() {
		return southSigne;
	}

	public void setSouthSigne(String southSigne) {
		this.southSigne = southSigne;
	}

	public String getEastSigne() {
		return eastSigne;
	}

	public void setEastSigne(String eastSigne) {
		this.eastSigne = eastSigne;
	}

	public String getWeastSigne() {
		return weastSigne;
	}

	public void setWeastSigne(String weastSigne) {
		this.weastSigne = weastSigne;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.tourne((orientation-this.orientation) % 4);
		this.orientation = orientation % 4;
//		System.out.println("la pi�ce "+this.nom+" a pr orient : "+this.orientation);
	}
}
