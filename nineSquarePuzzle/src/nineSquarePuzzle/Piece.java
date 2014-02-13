package nineSquarePuzzle;

public class Piece {

	String nom;
	int north, south, east, west;
	String northSigne, southSigne, eastSigne, weastSigne;
//	tableau permettant de stocker l'information : le nombre est positif/neg pour l'affichage
	String[] signes = new String[4];
	
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
	}
	
	public boolean estParfaite(){
		return true;
	}
	
	public String toString(){//Finir de modifier
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
}
