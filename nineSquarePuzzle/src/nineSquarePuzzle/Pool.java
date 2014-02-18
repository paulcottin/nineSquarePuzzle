package nineSquarePuzzle;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Pool {

	private ArrayList<String> lignes = new ArrayList<String>();
	private String titre = "Pas de titre";
	private ArrayList<Piece> pool = new ArrayList<Piece>();
	private ArrayList<Piece> utilisee = new ArrayList<Piece>();
	private Color[] couleursPiece = {Color.red, Color.blue, Color.green, Color.gray, Color.pink, Color.cyan, Color.orange, Color.white, Color.black};
	
	public Pool(String path){
		this.charge(path);
		this.titre = lignes.get(0);
		this.creePool();
//		au depart toutes les pieces sont utilisees
		this.utilisee = this.pool;
	}
	
	public Pool(String titre, ArrayList<Piece> array){
		if (titre.length() != 1) {
			System.out.println("Le titre ne doit comporter qu'un caract�re !");
		}else {
			this.titre = titre;
		}
		this.pool = array;
	}
	
	public void creePool(){
		for (int i = 1; i < 10; i++) {
			pool.add(this.creePieces(lignes.get(i)));
		}
	}
	
	public Piece creePieces(String ligne){
		int longueur = ligne.length();
		String titre = ligne.substring(0, 1);
		String chiffre1 = "", chiffre2 = "";
		int[] tab = new int[4];
		int cpt = 0, i = 1;
		while (i < longueur - 1) {
			chiffre1 = ""; chiffre2 = "";
			if (ligne.substring(i, i+1).equals("-")) {
				chiffre1 = "-";
				i++;
			}
			else if (ligne.substring(i, i+1).equals(" ")) {i++;}
			if(ligne.substring(i, i+1).equals("1") || ligne.substring(i, i+1).equals("2") || ligne.substring(i, i+1).equals("3") || ligne.substring(i, i+1).equals("4")){
				chiffre2 = ligne.substring(i, i+1);
				i++;
			}
			String ch = chiffre1.concat(chiffre2);
			if (!ch.equals("")) {
				tab[cpt] = Integer.valueOf(ch);
				cpt++;
			}
		}
		return new Piece(titre, tab);
	}

	public void charge(String path){
		String ligne = "";
		try {
			File f = new File(path);
			BufferedReader buf = new BufferedReader(new FileReader(f));
			while ((ligne = buf.readLine()) != null){
				lignes.add(ligne);
			}
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isPerfect(){
		boolean reponse = true;
		for (Piece p : pool) {
			if (!p.estParfaite()) {
				reponse = false;
			}
		}
		return reponse;
	}
	
//	Verifie qu'une piece existe et si elle est sur le plateau ou pas.
	public boolean isUtilise(Piece p){
		boolean utilise = false, trouve = false;
		for (Piece piece : pool) {
			if (piece.equals(p)) {
				trouve = true;
			}
		}
		if (trouve) {
			for (Piece piece : utilisee) {
				if (piece.equals(p)) {
					utilise = true;
				}
			}
		}else {
			System.out.println("Cette pi�ce n'existe pas !");
		}
		return utilise;
	}
	
//	enleve une piece pour bouger les autres
	public void enlevePiece(Piece p){
		if (this.isUtilise(p)) {
			utilisee.remove(p);
		}
	}
	
//	remet une piece en place
	public void ajoutePiece(Piece p){
		if (!this.isUtilise(p)) {
			utilisee.add(p);
		}
	}
	
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		if (titre.length() != 1) {
			System.out.println("Le nom d'une piece doit etre une lettre !");
		}else {
			this.titre = titre;
		}
	}

	public ArrayList<Piece> getPool() {
		return pool;
	}

	public void setPool(ArrayList<Piece> pool) {
		this.pool = pool;
	}

	public Color[] getCouleursPiece() {
		return couleursPiece;
	}

	public void setCouleursPiece(Color[] couleursPiece) {
		this.couleursPiece = couleursPiece;
	}
}
