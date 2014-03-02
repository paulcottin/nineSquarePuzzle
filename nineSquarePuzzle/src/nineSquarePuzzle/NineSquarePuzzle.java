package nineSquarePuzzle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;

import graphique.Fenetre;

public class NineSquarePuzzle {

	private Board board;
	private int[] ordrePlacement = {Board.CENTRE, Board.DROITE, Board.DROITE_HAUT, Board.CENTRE_HAUT, Board.GAUCHE_HAUT, Board.GAUCHE, Board.GAUCHE_BAS, Board.CENTRE_BAS, Board.DROITE_BAS};
	private int iterateur, nbSolutions = 0, nbSolutionsTrouvees = 0;
	private boolean fini = false;
	private Fenetre fen;
	private Main main;
	private Pool pool;
	private ArrayList<Board> solutions;
	
	public NineSquarePuzzle(Board b, Fenetre f, Main main){
		this.board = b;
		this.iterateur = 0;
		this.fen = f;
		this.main = main;
		this.solutions = new ArrayList<Board>();
		this.pool = board.getPool();
	}
	
	public void affichePool(){
		for (int i = board.getPositions().size()-1; i >= 0; i--) {
			board.positionner(board.getPool().getPool().get(i), this.ordrePlacement[i]);
		}
	}
	
	public void resoudre(int n) throws InterruptedException{
		int cptSolutions = 0;
		int premierePiece = 0;
		boolean premierTour = true;
		
		while (cptSolutions < board.getPool().getNbSolutions()) {
			resoudreAide(0, 0, 0, false, new ArrayList<InstanceBoard>(), false, premierePiece, true, 0);
			solutions.add(this.board.clone());
			
			this.board.resetBoard();fen.refreshBoard();
			
			this.pool = new Pool(Main.path);
			System.out.println("cpt de sols : "+cptSolutions+"\tNb de sols trouv�es : "+this.getNbSolutionsTrouvees()+"\tTaille du tabl : "+solutions.size());
			premierePiece = pieceCentraleSuivante(solutions.get(cptSolutions));
			System.out.println("nb total de solutions : "+board.getPool().getNbSolutions());
			premierTour = true;
			cptSolutions++;
		}
		System.out.println("R�solution finie");
		int i = 0;
		for (Board board : solutions) {
			i++;
			System.out.println("board solution n�"+i);
			for (Piece p : board.getPositions()) {
				System.out.println(p.getNom());
			}
		}
	}
	
	public void resoudreAide(int n, int orientation, int nbPiecesTestees, boolean aTourne, ArrayList<InstanceBoard> boardFaux, boolean fini, int premierePiece, boolean premierTour, int nbToursPremierePiece) throws InterruptedException{
		if (n < 8 /*&& !boardParfait(n)*/) {
//			System.out.println("entre dans r�soudreAide()\tn : "+n);
			while (board.getPool().getPool().size() > 0 && !fini) {
				if (nbPiecesTestees > board.getPool().getPool().size()) {
					if (n > 0 || !(nbToursPremierePiece <4)) {
						board.getPositions().get(this.ordrePlacement[n]).setOrientation(4);fen.refreshBoard();
						board.retirer(board.getPositions().get(this.ordrePlacement[n]));fen.refreshBoard();
						n--;
					}
					if (aEteUneInstance(new InstanceBoard(board), boardFaux)) {
						board.getPositions().get(this.ordrePlacement[n]).setOrientation(4);fen.refreshBoard();
						board.retirer(board.getPositions().get(this.ordrePlacement[n]));fen.refreshBoard();
						if (n > 0) {
							n--;
						}else if (n <= 0) {
							n = 0;
							board.getPositions().get(this.ordrePlacement[n]).setOrientation(4);fen.refreshBoard();
							board.retirer(board.getPositions().get(this.ordrePlacement[n]));fen.refreshBoard();
							board.positionner(board.getPool().getPool().get(0), this.ordrePlacement[n]);fen.refreshBoard();
						}
					}
					if (board.getPool().getPool().size() > 0 && !board.getPositions().get(0).getNom().equals("*")) {
						boardFaux.add(new InstanceBoard(board));
					}
				}
				while (nbPiecesTestees <= board.getPool().getPool().size() && !fini) {//Tant qu'on a pas test� toutes les pi�ces pour une case
//					System.out.println("n : "+n+"\ta tourn� : "+aTourne+"\tnb de p test�es : "+nbPiecesTestees);
					if (n > 0 && aTourne || (n == 0 && !(nbToursPremierePiece < 4) )) {
						board.getPositions().get(this.ordrePlacement[n]).setOrientation(4);fen.refreshBoard();
						board.retirer(board.getPositions().get(this.ordrePlacement[n]));fen.refreshBoard();
						nbPiecesTestees++;
					}
					if (board.getPool().getPool().size() > 0) {
//						System.out.println("n : "+n);
						if (premierTour) {
							board.positionner(board.getPool().getPool().get(premierePiece), this.ordrePlacement[n]);fen.refreshBoard();
							orientation = 0;
							board.getPositions().get(this.ordrePlacement[n]).setOrientation(orientation);fen.refreshBoard();
							Thread.sleep(1000-fen.getVitesseExec());
						}
						else if (nbToursPremierePiece < 4 && n == 0) {
						//Ne rien faire, laisser tourner	
						}else {
							
						}{
							board.positionner(board.getPool().getPool().get(0), this.ordrePlacement[n]);fen.refreshBoard();
							orientation = 0;
							board.getPositions().get(this.ordrePlacement[n]).setOrientation(orientation);fen.refreshBoard();
							Thread.sleep(1000-fen.getVitesseExec());
						}
					}else {
//						System.out.println("Fini");System.out.println((new InstanceBoard(board).toString()));fini = true;//System.exit(0);
					}
//					System.out.println("coucou");
					while (orientation < 4 && !fini) {
						Thread.sleep(1000-fen.getVitesseExec());
//						System.out.println("n : "+n+"\t modulo  = 0 : "+(this.ordrePlacement[n] % 2 == 0)+"\t pool size "+board.getPool().getPool().size()+"\tfini : "+fini);
						if (n == 0 && !premierTour) {
							if (!(nbToursPremierePiece < 4)) {//Si la pi�ce � trop tourn�e, on fait en sorte qu'elle quitte le while et qu'on la retire apr�s
								aTourne = true;
								orientation = 8;
								nbToursPremierePiece = 0;
							}else {//Sinon on la fait tourner
								board.getPositions().get(this.ordrePlacement[n]).tourne(1);fen.refreshBoard();Thread.sleep(1000-fen.getVitesseExec());
								nbToursPremierePiece++;
							}
						}
							if (bienPlacee(board.getPositions().get(this.ordrePlacement[n]), n)) {
//								System.out.println("bien plac�e");
								if (premierTour) {
									premierTour = false;
								}
								resoudreAide(n+1, 0, 0, false, boardFaux, fini, premierePiece, premierTour, nbToursPremierePiece);
								System.out.println("n : "+n);
									if (boardParfait(n)) {
										fini = true;
									}else {//Sortir de la boucle
										orientation = 4;
									}
							}else {
								orientation++;
								aTourne = true;
								board.getPositions().get(this.ordrePlacement[n]).setOrientation(board.getPositions().get(this.ordrePlacement[n]).getOrientation()+1);fen.refreshBoard();
							}
					}
//					System.out.println("Sorti de orientation < 4");
				}
//				System.out.println("Sorti de nbPiecesTestees <= board.getPool().getPool().size()");
			}
//			System.out.println("Sorti de board.getPool().getPool().size() > 0");
		}else {
			fen.refreshBoard();
			System.out.println("Solution ajout�e � l'arrayList\tnb solutions trouvees : "+(this.nbSolutionsTrouvees +1));
			fini = true;this.nbSolutionsTrouvees++;
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
	
	public int pieceCentraleSuivante(Board board){
		Piece piece = board.getPositions().get(board.CENTRE);
		int indice = 0;
		for (int i = 0; i < board.getPool().getPool().size(); i++) {
			if (board.getPool().getPool().get(i).equals(piece)) {
				indice = i;
			}
		}
		return (indice+1);
	}
	
	public boolean aUneAutreSolution(Piece p, int n){
		for (int i = 0; i < 3; i++) {
			p.tourne(1);
			if (bienPlacee(p, n)) {
				return true;
			}
		}
		return false;
	}
	
	
	public void nettoieDoublon(ArrayList<InstanceBoard> boardFaux){
		ArrayList<Integer> indicesANettoyer = new ArrayList<Integer>();
		int cpt = 0;
		for (int i = 0; i < boardFaux.size(); i++) {
			if (aEteUneInstance(boardFaux.get(i), boardFaux)) {
				cpt++;
				indicesANettoyer.add(i);
			}
		}
		if (cpt > 1) {
			for (int i = indicesANettoyer.size(); i > 0; i--) {
				boardFaux.remove(indicesANettoyer.get(i));
			}
		}
	}
	
	public boolean aEteUneInstance(InstanceBoard instance, ArrayList<InstanceBoard> boardsFaux){
//		System.out.println("-----------------------------\n"+boardsFaux.size()+" instances\n|\t"+instance.toString());
		for (InstanceBoard instanceBoard : boardsFaux) {
//			System.out.println(instanceBoard.toString());
			if (instance.equals(instanceBoard)) {
//				System.out.println("A �t� une instance !!");
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
			if (indiceP == Board.GAUCHE_HAUT && indiceQ == Board.GAUCHE && p.getSouth()+q.getNorth() == 0){
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
	
	public boolean bienPlacee(Piece p, int n){
		int indiceP = 0;
		//	Recuperation des indices dans Board.positions des pieces p et q
			for (int i = 0; i < board.getPositions().size(); i++) {
				if (p.equals(board.getPositions().get(i))) {
					indiceP = i;
				}
			}
//			Si la pi�ce est vide retourner faux
			if (p.getNom().equals("*")) {
				return false;
			}
//			Si la pi�ce est la premi�re retourner vrai
			if (indiceP == board.CENTRE) {
				return true;
			}
//			Si c'est la derni�re piece
			if (n == 8) {
//				System.out.println("derni�re piece : "+p.getNom()+" doit etre testee avec : "+board.getPositions().get(this.ordrePlacement[n-1]).getNom()+" et "+board.getPositions().get(this.ordrePlacement[1]).getNom());
				if (!(match(p, board.getPositions().get(this.ordrePlacement[n-1])) && match(p, board.getPositions().get(this.ordrePlacement[1])))) {
					return false;
				}
//				for (Piece piece : board.getPositions()) {
//					System.out.println(piece.getNom());
//				}
//				System.exit(0);
			}
//			Cas g�n�ral
			if (this.ordrePlacement[n] % 2 == 0) {
				if (!match(p, board.getPositions().get(this.ordrePlacement[n-1]))) {
					return false;
				}
			}else {
				if (!(match(p, board.getPositions().get(this.ordrePlacement[n-1])) && match(p, board.getPositions().get(board.CENTRE)))) {
					return false;
				}
			}
		return true;
	}
	
	public boolean boardParfait(int n){
		for (Piece p : board.getPositions()) {
			if (!bienPlacee(p, n)) {
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

	public int getNbSolutionsTrouvees() {
		return nbSolutionsTrouvees;
	}

	public void setNbSolutionsTrouvees(int nbSolutionsTrouvees) {
		this.nbSolutionsTrouvees = nbSolutionsTrouvees;
	}

	public ArrayList<Board> getSolutions() {
		return solutions;
	}

	public void setSolutions(ArrayList<Board> solutions) {
		this.solutions = solutions;
	}
}

	
