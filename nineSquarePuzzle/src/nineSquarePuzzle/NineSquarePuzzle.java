package nineSquarePuzzle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import graphique.Fenetre;

public class NineSquarePuzzle {

	private Board board;
	private int[] ordrePlacement = {Board.GAUCHE_HAUT, Board.CENTRE_HAUT, Board.DROITE_HAUT, Board.GAUCHE, Board.CENTRE, Board.DROITE, Board.GAUCHE_BAS, Board.CENTRE_BAS, Board.DROITE_BAS};
	private int iterateur;
	private boolean fini = false;
	private Fenetre fen;
	private Main main;
	
	public NineSquarePuzzle(Board b, Fenetre f, Main main){
		this.board = b;
		this.iterateur = 0;
		this.fen = f;
		this.main = main;
	}
	
	public void affiche(){
		for (int i = board.getPositions().size()-1; i >= 0; i--) {
			board.positionner(board.getPool().getPool().get(i), this.ordrePlacement[i]);
		}
	}
	/*
	public void resoudre(int n) throws InterruptedException{
		int cptTours = 0;
		int cptToursPieceInit = 0;
		ArrayList<Piece> piecesInit = new ArrayList<Piece>();
		int numeroPiece = 0;
		int antiInfnty = 0;
		String nomPremierePieceRetiree = "";boolean premierePieceRetiree = false;
		boolean piecesToutesFaites = false;
		
		System.out.println("Nb pièces non placées : "+nbPiecesNonPlacees()+"\tn : "+n);
		if (nbPiecesNonPlacees() == 0) {//Si le puzzle est résolu
			System.out.println("fini");
			return;
		}
		System.out.println("La piece "+board.getPool().getPool().get(numeroPiece).getNom()+" a été ajoutée au Board en position "+this.ordrePlacement[n]);
		piecesInit.add(board.getPool().getPool().get(numeroPiece));
		board.positionner(board.getPool().getPool().get(numeroPiece), this.ordrePlacement[n]);
		fen.refreshBoard();
		while(!fini){
			cptTours = 0;
			Thread.sleep(00);
			antiInfnty++;
			System.out.println("taille de pool : "+board.getPool().getPool().size());
			System.out.println(board.getPool().getPool().get(numeroPiece));
			System.out.println("La piece "+board.getPool().getPool().get(numeroPiece).getNom()+" a été ajoutée au Board en position "+this.ordrePlacement[n+1]);
			board.positionner(board.getPool().getPool().get(numeroPiece), this.ordrePlacement[n+1]);
			fen.refreshBoard();
			if (bienPlacee(board.getPositions().get(this.ordrePlacement[n+1]))) {
//				resoudre(n+1);
				System.out.println(board.getPositions().get(this.ordrePlacement[n+1]).getNom()+" bien placé !\t cpt de tours : "+cptTours+"\tValeur de n : "+n+"\tDans le while");
				n++;premierePieceRetiree = false;piecesToutesFaites = false;
			}else {
				while (!(bienPlacee(board.getPositions().get(this.ordrePlacement[n+1])) || cptTours > 3)) {
					board.getPositions().get(this.ordrePlacement[n+1]).tourne(1);
					fen.refreshBoard();
					System.out.println(board.getPositions().get(this.ordrePlacement[n+1]).toString()+"\ta tourné\tcompteur de tours : "+cptTours+"\tValeur de n : "+n);
					cptTours++;
				}
				if (bienPlacee(board.getPositions().get(this.ordrePlacement[n+1]))) {
					System.out.println(board.getPositions().get(this.ordrePlacement[n+1]).getNom()+" bien placé !\t cpt de tours : "+cptTours+"\tValeur de n : "+n+"\tDans le while");
					n++;premierePieceRetiree = false;piecesToutesFaites = false;
				}
				if (cptTours == 4) {// => la piece a tourne sans trouver de bonne solutions
					cptTours = 0;
					if (!premierePieceRetiree) {
						nomPremierePieceRetiree = board.getPositions().get(this.ordrePlacement[n+1]).getNom();
						System.out.println("La pièce "+nomPremierePieceRetiree+" est la première à avoir été retirée");
					}
					if (board.getPositions().get(this.ordrePlacement[n+1]).getNom().equals(nomPremierePieceRetiree) && premierePieceRetiree) {
						piecesToutesFaites = true;
					}
					System.out.println("La piece "+board.getPositions().get(this.ordrePlacement[n+1]).getNom()+" ne correspond pas\n"+board.getPositions().get(this.ordrePlacement[n+1]).getNom()+" est enlevée du Board");
					board.retirer(board.getPositions().get(this.ordrePlacement[n+1]));//On l'enlève 
					premierePieceRetiree = true;
					fen.refreshBoard();
				}
//				Si on a fait toutes les pièces on tourne d'un quart de tour la première et on recommence
				if (piecesToutesFaites) {// Si on a fait toutes les pièces sans trouver de solutions
					System.out.println("ON A FAIT TOUTES LES PIECES");
					board.getPositions().get(this.ordrePlacement[n]).tourne(1);
					//Tant qu'on a pas placé une pièce correctement au dessus
					while (bienPlacee(board.getPositions().get(this.ordrePlacement[n]))) {
						cptTours = 0;
						//On fait tourner la pièce n pour voir si il n'existe pas une autre solution
						while (bienPlacee(board.getPositions().get(this.ordrePlacement[n])) || cptTours < 2) {
							board.getPositions().get(this.ordrePlacement[n]).tourne(1);
							fen.refreshBoard();
							System.out.println(board.getPositions().get(this.ordrePlacement[n]).toString()+"\ta tourné\tcompteur de tours : "+cptTours+"\tValeur de n : "+n);
						}
						//Si il y a une autre solution on continue l'algo
						if (bienPlacee(board.getPositions().get(this.ordrePlacement[n]))) {
							System.out.println(board.getPositions().get(this.ordrePlacement[n+1]).getNom()+" bien placé !\t cpt de tours : "+cptTours+"\tValeur de n : "+n+"\tDans le while");
							n++;premierePieceRetiree = false;piecesToutesFaites = false;
						}
						//Sinon on retire la piece et on essaye de trouver une autre solution avec la précédente
						if (cptTours > 2) {//Sinon on enlève la pièce et on essaye avec la précédente
							board.retirer(board.getPositions().get(this.ordrePlacement[n]));
							fen.refreshBoard();
							n--;piecesToutesFaites = false;
						}
					}
					//On retire les pièces du Board
					while (nbPiecesNonPlacees() != 8) {
						System.out.println("La piece "+board.getPositions().get(this.ordrePlacement[n]).getNom()+" a été retirée");
						board.retirer(board.getPositions().get(this.ordrePlacement[n]));
						fen.refreshBoard();
						n--;
					}//Et on fait tourner la pièce en première position
					n = 0;
					cptToursPieceInit++;
					if (cptToursPieceInit == 4) {//Si on a déjà fait tourner 4 fois la première pièce on la remplace par une suivante.
						System.out.println("La pièce "+board.getPositions().get(this.ordrePlacement[n]).getNom()+" a été retirée");
						board.retirer(board.getPositions().get(this.ordrePlacement[n]));
						cptToursPieceInit = 0;int cpt = 0;
						while (piecesInit.contains(board.getPool().getPool().get(numeroPiece)) || cpt < 10) {
							numeroPiece = (numeroPiece +1) % (board.getPool().getPool().size() - 1);
							System.out.println(board.getPool().getPool().get(numeroPiece).getNom()+"\tnuméro pièce : "+numeroPiece+"\tTaille Pool : "+(board.getPool().getPool().size()-1));
							cpt++;
							if (cpt > 10) {
								System.out.println("boucle infinie...");System.exit(0);
							}
						}
						cpt = 0;
						numeroPiece = 0;
						System.out.println("La piece "+board.getPool().getPool().get(numeroPiece).getNom()+" a été ajoutée au Board en position "+this.ordrePlacement[n]);
						piecesInit.add(board.getPool().getPool().get(numeroPiece));
						board.positionner(board.getPool().getPool().get(numeroPiece), this.ordrePlacement[n]);
						fen.refreshBoard();
					}
					premierePieceRetiree = false;piecesToutesFaites = false;
					board.getPositions().get(this.ordrePlacement[n]).tourne(1);
					System.out.println(board.getPositions().get(this.ordrePlacement[n]));
				}
				if (board.getPool().getPool().size() == 3 /*|| antiInfnty > 50) {
					fini = true;
					System.out.println("antiInfnty : "+antiInfnty);
					System.out.println("FIIIIIIIIIIIIIIIIIIIINNNNNNNNNNNNNNNNNNNNNNIIIIIIIIIIIIIIIIIIIIIIIIIIIII !!!!!!!!!!!!!!!!!!! :)");
				}
			}
		}
	}
	*/

	public void resoudre(int n) throws InterruptedException{
		if (board.getPool().getPool().size() == 1) {
			return;
		}
		int nPrecedent = -1;
		int nbTours = 0;
		int nbPiecesTestees = 0;
		boolean avance = true;
		ArrayList<InstanceBoard> boardsFaux = new ArrayList<InstanceBoard>();
		resoudreAide(n, nPrecedent, nbTours, nbPiecesTestees, avance, boardsFaux);
	}
	
	public void resoudreAide(int n, int nPrecedent, int nbTours, int nbPiecesTestees, boolean avance, ArrayList<InstanceBoard> boardsFaux) throws InterruptedException{
		Thread.sleep(1000-fen.getVitesseExec());
		System.out.println("n : "+n+" nPrécédent : "+nPrecedent+" nbTours : "+nbTours+" nbPiecesTestees : "+nbPiecesTestees+"/"+board.getPool().getPool().size()+" avance : "+avance);
		if (n < 0) {
			System.out.println("n < 0");System.exit(0);
		}
		if (board.getPool().getPool().size() == 0) {
			System.out.println("FINI !!!!!!");return ;
		}
		if (aEteUneInstance(new InstanceBoard(board), boardsFaux) && n > nPrecedent/*&& !avance*/) {
			nbPiecesTestees++;
			System.out.println("board contenu !");
			board.retirer(board.getPositions().get(this.ordrePlacement[n]));fen.refreshBoard();
			if (aEteUneInstance(new InstanceBoard(board), boardsFaux)/* && !avance*/) {
				n--;
//				nbPiecesTestees++;
				System.out.println("board contenu !");
				board.retirer(board.getPositions().get(this.ordrePlacement[n]));fen.refreshBoard();
//				if (aEteUneInstance(board.getInstance(), boardsFaux)) {
//					n--;
//					System.out.println("board contenu !");
//					board.retirer(board.getPositions().get(this.ordrePlacement[n]));
					resoudreAide(n - 1, n, 0, nbPiecesTestees, false, boardsFaux);
//				}else {
//					resoudreAide(n - 1, n, 0, 0, true, boardsFaux);
//				}
			}else {
				resoudreAide(n - 1, n, 0, 0, true, boardsFaux);
			}
		}
		if (n > nPrecedent) {
			board.positionner(board.getPool().getPool().get(0), this.ordrePlacement[n]);fen.refreshBoard();
		}
		if (nbPiecesTestees == board.getPool().getPool().size()) {
			board.retirer(board.getPositions().get(this.ordrePlacement[n]));fen.refreshBoard();
			boardsFaux.add(new InstanceBoard(board));System.out.println("Board ajouté !");
			System.out.println(board.toString(board.getInstance()));
			resoudreAide(n - 1, n, nbTours, nbPiecesTestees, false, boardsFaux);
		}
		if (bienPlacee(board.getPositions().get(this.ordrePlacement[n])) && !avance ) {
			if (aUneAutreSolution(board.getPositions().get(this.ordrePlacement[n]))) {
				board.getPositions().get(this.ordrePlacement[n]).tourne(1);fen.refreshBoard();
				resoudreAide(n, n, nbTours + 1, nbPiecesTestees, true, boardsFaux);
			}else {
				board.retirer(board.getPositions().get(this.ordrePlacement[n]));fen.refreshBoard();
				resoudreAide(n-1, n, 0, 0, false, boardsFaux);
			}
			
		}
		if (bienPlacee(board.getPositions().get(this.ordrePlacement[n])) && avance) {
			resoudreAide(n+1, n, 0, 0, true, boardsFaux);
		}
		if (nbTours < 4) {
			board.getPositions().get(this.ordrePlacement[n]).tourne(1);fen.refreshBoard();
			resoudreAide(n, n, nbTours + 1, nbPiecesTestees, avance, boardsFaux);
		}
		if (nbTours >= 4 && avance) {
			boardsFaux.add(new InstanceBoard(board));
			board.retirer(board.getPositions().get(this.ordrePlacement[n]));fen.refreshBoard();
			resoudreAide(n, n-1, 0, nbPiecesTestees + 1, avance, boardsFaux);
		}
		if (nbTours >= 4 && !avance) {
			boardsFaux.add(new InstanceBoard(board));
			board.retirer(board.getPositions().get(this.ordrePlacement[n]));fen.refreshBoard();
			resoudreAide(n - 1, n, 0, nbPiecesTestees + 1, false, boardsFaux);
		}
	}
	
	public int nbPiecesNonPlacees(){
		int nb = 0;
		for (int i = 0; i < board.getPositionOccupees().length; i++) {
			if (!board.getPositionOccupees()[i]) {
				nb++;
			}
		}
		return nb;
	}
	
	public boolean aUneAutreSolution(Piece p){
		for (int i = 0; i < 3; i++) {
			p.tourne(1);
			if (bienPlacee(p)) {
				return true;
			}
		}
		return false;
	}
	
	
	public void nettoieDoublon(ArrayList<InstanceBoard> boardsFaux){
		ArrayList<Integer> indicesANettoyer = new ArrayList<Integer>();
		int cpt = 0;
		for (int i = 0; i < boardsFaux.size(); i++) {
			if (aEteUneInstance(boardsFaux.get(i), boardsFaux)) {
				cpt++;
				indicesANettoyer.add(i);
			}
		}
		if (cpt > 1) {
			for (int i = indicesANettoyer.size(); i > 0; i--) {
				boardsFaux.remove(indicesANettoyer.get(i));
			}
		}
	}
	
	public boolean aEteUneInstance(InstanceBoard instance, ArrayList<InstanceBoard> boardsFaux){
		System.out.println("-----------------------------\n"+boardsFaux.size()+" instances\n|\t"+instance.toString());
		for (InstanceBoard instanceBoard : boardsFaux) {
			System.out.println(instanceBoard.toString());
			if (instance.equals(instanceBoard)) {
				System.out.println("A été une instance !!");
				return true;
			}
		}
		return false;
	}
	
	public boolean match(Piece p, Piece q){
		int indiceP = 0, indiceQ = 0;
		//	Recuperation des indices dans Board.positions des pieces p et q
			for (int i = 0; i < board.getPositions().size(); i++) {
				if (p.equals(board.getPositions().get(i))) {
					indiceP = i;
				}
				if (q.equals(board.getPositions().get(i))) {
					indiceQ = i;
				}
			}
			
			if (indiceP == Board.GAUCHE_BAS && indiceQ == Board.GAUCHE && p.getNorth()+q.getSouth() == 0) {
				return true;
			}
			if (indiceP == Board.GAUCHE_BAS && indiceQ == Board.CENTRE_BAS && p.getEast()+q.getWest() == 0) {
				return true;
			}
			if (indiceP == Board.CENTRE_BAS && indiceQ == Board.DROITE_BAS && p.getEast()+q.getWest() == 0) {
				return true;
			}
			if (indiceP == Board.CENTRE_BAS && indiceQ == Board.GAUCHE_BAS && p.getWest()+q.getEast() == 0) {
				return true;
			}//
			if (indiceP == Board.CENTRE_BAS && indiceQ == Board.CENTRE && p.getNorth()+q.getSouth() == 0) {
				return true;
			}
			if (indiceP == Board.DROITE_BAS && indiceQ == Board.CENTRE_BAS && p.getWest()+q.getEast() == 0) {
				return true;
			}//
			if (indiceP == Board.DROITE_BAS && indiceQ == Board.DROITE && p.getNorth()+q.getSouth() == 0) {
				return true;
			}
			if (indiceP == Board.GAUCHE && indiceQ == Board.GAUCHE_BAS && p.getSouth()+q.getNorth() == 0) {
				return true;
			}//
			if (indiceP == Board.GAUCHE && indiceQ == Board.CENTRE && p.getEast()+q.getWest() == 0) {
				return true;
			}
			if (indiceP == Board.GAUCHE && indiceQ == Board.GAUCHE_HAUT && p.getNorth()+q.getSouth() == 0) {
				return true;
			}
			if (indiceP == Board.CENTRE && indiceQ == Board.GAUCHE && p.getWest()+q.getEast() == 0) {
				return true;
			}//
			if (indiceP == Board.CENTRE && indiceQ == Board.DROITE && p.getEast()+q.getWest() == 0) {
				return true;
			}
			if (indiceP == Board.CENTRE && indiceQ == Board.CENTRE_HAUT && p.getNorth()+q.getSouth() == 0) {
				return true;
			}
			if (indiceP == Board.CENTRE && indiceQ == Board.CENTRE_BAS && p.getSouth()+q.getNorth() == 0) {
				return true;
			}//
			if (indiceP == Board.DROITE && indiceQ == Board.CENTRE && p.getWest()+q.getEast() == 0) {
				return true;
			}
			if (indiceP == Board.DROITE && indiceQ == Board.DROITE_BAS && p.getSouth()+q.getNorth() == 0) {
				return true;
			}//
			if (indiceP == Board.DROITE && indiceQ == Board.DROITE_HAUT && p.getNorth()+q.getSouth() == 0) {
				return true;
			}
			if (indiceP == Board.GAUCHE_HAUT && indiceQ == Board.GAUCHE && p.getSouth()+p.getNorth() == 0){
				return true;
			}
			if (indiceP == Board.GAUCHE_HAUT && indiceQ == Board.CENTRE_HAUT && p.getEast()+q.getWest() == 0) {
				return true;
			}
			if (indiceP == Board.CENTRE_HAUT && indiceQ == Board.GAUCHE_HAUT && p.getWest()+q.getEast() == 0) {
				return true;
			}
			if (indiceP == Board.CENTRE_HAUT && indiceQ == Board.CENTRE && p.getSouth()+q.getNorth() == 0) {
				return true;
			}
			if (indiceP == Board.CENTRE_HAUT && indiceQ == Board.DROITE_HAUT && p.getEast()+q.getWest() == 0) {
				return true;
			}
			if (indiceP == Board.DROITE_HAUT && indiceQ == Board.CENTRE_HAUT && p.getWest()+q.getEast() == 0) {
				return true;
			}
			if (indiceP == Board.DROITE_HAUT && indiceQ == Board.DROITE && p.getSouth()+q.getNorth() == 0) {
				return true;
			}//
		return false;
	}
	
	public boolean bienPlacee(Piece p){
		int indiceP = 0;
		//	Recuperation des indices dans Board.positions des pieces p et q
			for (int i = 0; i < board.getPositions().size(); i++) {
				if (p.equals(board.getPositions().get(i))) {
					indiceP = i;
				}
			}
//			Si la pièce est vide retourner faux
			if (p.getNom().equals("*")) {
				return false;
			}
//			Si la pièce est la première retourner vrai
			if (indiceP == 0) {
				return true;
			}
		if (aPieceAGauche(p)) {
			if(!match(p, board.getPositions().get(indiceP - 1))){
				return false;
			}
		}
		if (aPieceAuDessus(p)) {
			if(!match(p, board.getPositions().get(indiceP - 3))){
				return false;
			}
		}
		return true;
	}
	
	public boolean estAutour(Piece p, Piece q){
		int indiceP = 0, indiceQ = 0;
		//	Recuperation des indices dans Board.positions des pieces p et q
			for (int i = 0; i < board.getPositions().size(); i++) {
				if (p.equals(board.getPositions().get(i))) {
					indiceP = i;
				}
				if (q.equals(board.getPositions().get(i))) {
					indiceQ = i;
				}
			}
			
			if (indiceP == Board.GAUCHE_BAS && (indiceQ == Board.GAUCHE || indiceQ == Board.CENTRE_BAS)) {
				return true;
			}
			if (indiceP == Board.CENTRE_BAS && (indiceQ == Board.GAUCHE || indiceQ == Board.CENTRE || indiceQ == Board.DROITE_BAS)) {
				return true;
			}
			if (indiceP == Board.DROITE_BAS && (indiceQ == Board.CENTRE_BAS || indiceQ == Board.DROITE)) {
				return true;
			}
			if (indiceP == Board.GAUCHE && (indiceQ == Board.GAUCHE_BAS || indiceQ == Board.CENTRE || indiceQ == Board.GAUCHE_HAUT)) {
				return true;
			}
			if (indiceP == Board.CENTRE && (indiceQ == Board.GAUCHE || indiceQ == Board.DROITE || indiceQ == Board.CENTRE_HAUT || indiceQ == Board.CENTRE_BAS)) {
				return true;
			}
			if (indiceP == Board.DROITE &&  (indiceQ == Board.CENTRE || indiceQ == Board.DROITE_BAS || indiceQ == Board.DROITE_HAUT)) {
				return true;
			}
			if (indiceP == Board.GAUCHE_HAUT && (indiceQ == Board.GAUCHE || indiceQ == Board.CENTRE_HAUT)) {
				return true;
			}
			if (indiceP == Board.CENTRE_HAUT && (indiceQ == Board.GAUCHE_HAUT || indiceQ == Board.CENTRE || indiceQ == Board.DROITE_HAUT)) {
				return true;
			}
		return false;
	}
	
	public boolean aPieceAuDessus(Piece p){
		int indiceP = 0;
//	Recuperation des indices dans Board.positions des pieces p et q
		for (int i = 0; i < board.getPositions().size(); i++) {
			if (p.equals(board.getPositions().get(i))) {
				indiceP = i;
			}
		}
		if (indiceP == Board.GAUCHE_HAUT || indiceP == Board.CENTRE_HAUT || indiceP == Board.DROITE_HAUT) {
			return false;
		}
		if ((indiceP == Board.GAUCHE && board.getPositions().get(Board.GAUCHE_HAUT).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.CENTRE && board.getPositions().get(Board.CENTRE_HAUT).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.DROITE && board.getPositions().get(Board.DROITE_HAUT).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.GAUCHE_BAS && board.getPositions().get(Board.GAUCHE).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.CENTRE_BAS && board.getPositions().get(Board.CENTRE).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.DROITE_BAS && board.getPositions().get(Board.DROITE).getNom().equals("*"))) {
			return false;
		}
		return true;
	}
	
	public boolean aPieceEnDesous(Piece p){
		int indiceP = 0;
//	Recuperation des indices dans Board.positions des pieces p et q
		for (int i = 0; i < board.getPositions().size(); i++) {
			if (p.equals(board.getPositions().get(i))) {
				indiceP = i;
			}
		}
		if (indiceP == Board.GAUCHE_BAS || indiceP == Board.CENTRE_BAS || indiceP == Board.DROITE_BAS) {
			return false;
		}
		if ((indiceP == Board.GAUCHE && board.getPositions().get(Board.GAUCHE_BAS).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.CENTRE && board.getPositions().get(Board.CENTRE_BAS).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.DROITE && board.getPositions().get(Board.DROITE_BAS).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.GAUCHE_HAUT && board.getPositions().get(Board.GAUCHE).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.CENTRE_HAUT && board.getPositions().get(Board.CENTRE).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.DROITE_HAUT && board.getPositions().get(Board.DROITE).getNom().equals("*"))) {
			return false;
		}
		return true;
	}
	
	public boolean aPieceAGauche(Piece p){
		int indiceP = 0;
//	Recuperation des indices dans Board.positions des pieces p et q
		for (int i = 0; i < board.getPositions().size(); i++) {
			if (p.equals(board.getPositions().get(i))) {
				indiceP = i;
			}
		}
		if (indiceP == Board.GAUCHE_BAS || indiceP == Board.GAUCHE || indiceP == Board.GAUCHE_HAUT) {
			return false;
		}
		if ((indiceP == Board.CENTRE_BAS && board.getPositions().get(Board.GAUCHE_BAS).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.CENTRE && board.getPositions().get(Board.GAUCHE).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.DROITE_BAS && board.getPositions().get(Board.CENTRE_BAS).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.DROITE && board.getPositions().get(Board.CENTRE).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.DROITE_HAUT && board.getPositions().get(Board.GAUCHE_HAUT).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.CENTRE_HAUT && board.getPositions().get(Board.GAUCHE_HAUT).getNom().equals("*"))) {
			return false;
		}
		return true;
	}
	
	public boolean aPieceADroite(Piece p){
		int indiceP = 0;
//	Recuperation des indices dans Board.positions des pieces p et q
		for (int i = 0; i < board.getPositions().size(); i++) {
			if (p.equals(board.getPositions().get(i))) {
				indiceP = i;
			}
		}
		if (indiceP == Board.DROITE_BAS || indiceP == Board.DROITE|| indiceP == Board.DROITE_HAUT) {
			return false;
		}
		if ((indiceP == Board.CENTRE_BAS && board.getPositions().get(Board.DROITE_BAS).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.CENTRE && board.getPositions().get(Board.DROITE).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.CENTRE_HAUT && board.getPositions().get(Board.DROITE_HAUT).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.GAUCHE_BAS && board.getPositions().get(Board.CENTRE_BAS).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.GAUCHE && board.getPositions().get(Board.CENTRE).getNom().equals("*"))) {
			return false;
		}
		if ((indiceP == Board.GAUCHE_HAUT && board.getPositions().get(Board.GAUCHE_HAUT).getNom().equals("*"))) {
			return false;
		}
		return true;
	}
	
	public boolean isUnder(Piece p, Piece q){
		int indiceP = 0, indiceQ = 0;
//		Recuperation des indices dans Board.positions des pieces p et q
		for (int i = 0; i < board.getPositions().size(); i++) {
			if (p.equals(board.getPositions().get(i))) {
				indiceP = i;
			}
			if (q.equals(board.getPositions().get(i))) {
				indiceQ = i;
			}
		}
//		Comparaison
		if (indiceQ == Board.GAUCHE_BAS || indiceQ == Board.CENTRE_BAS || indiceQ == Board.DROITE_BAS) {
			return false;
		}
		if ((indiceP == Board.GAUCHE_BAS && indiceQ == Board.GAUCHE) ||(indiceP == Board.CENTRE_BAS && indiceQ == Board.CENTRE) || (indiceP == Board.DROITE_BAS && indiceQ == Board.DROITE)) {
			return true;
		}
		if ((indiceP == Board.GAUCHE && indiceQ == Board.GAUCHE_HAUT) ||(indiceP == Board.CENTRE && indiceQ == Board.CENTRE_HAUT) || (indiceP == Board.DROITE && indiceQ == Board.DROITE_HAUT)) {
			return true;
		}
		return false;
	}
	
//	public boolean isUpper(Piece p, Piece q){
//		return !isUnder(p, q);
//	}

	public boolean isAGauche(Piece p, Piece q){
		int indiceP = 0, indiceQ = 0;
//		Recuperation des indices dans Board.positions des pieces p et q
		for (int i = 0; i < board.getPositions().size(); i++) {
			if (p.equals(board.getPositions().get(i))) {
				indiceP = i;
			}
			if (q.equals(board.getPositions().get(i))) {
				indiceQ = i;
			}
		}
		if (indiceQ == Board.GAUCHE_BAS || indiceQ == Board.GAUCHE || indiceQ == Board.GAUCHE_HAUT) {
			return false;
		}
		if ((indiceP == Board.GAUCHE_BAS && indiceQ == Board.CENTRE_BAS) ||(indiceP == Board.GAUCHE && indiceQ == Board.CENTRE) || (indiceP == Board.GAUCHE_HAUT && indiceQ == Board.CENTRE_HAUT)) {
			return true;
		}
		if ((indiceP == Board.CENTRE_BAS && indiceQ == Board.DROITE_BAS) ||(indiceP == Board.CENTRE && indiceQ == Board.DROITE) || (indiceP == Board.CENTRE_HAUT && indiceQ == Board.DROITE_HAUT)) {
			return true;
		}
		return false;
	}
	
//	public boolean isADroite(Piece p, Piece q){
//		return !isAGauche(p,q);
//	}
	
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	class MenuActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource().equals(fen.getLancerAlgo())) {
				try {
					resoudre(0);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
}

	
