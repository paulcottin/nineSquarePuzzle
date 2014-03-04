package nineSquarePuzzle;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class NineSquarePuzzle extends Observable{

	private Board board;
	private int[] ordrePlacement = {Board.CENTRE, Board.DROITE, Board.DROITE_HAUT, Board.CENTRE_HAUT, Board.GAUCHE_HAUT, Board.GAUCHE, Board.GAUCHE_BAS, Board.CENTRE_BAS, Board.DROITE_BAS};
	private int iterateur, nbSolutions = 0, nbSolutionsTrouvees = 0, vitesseExec;
	private boolean fini = false, fichierOuvert = false;
	private JPanel panel;
	private Pool pool;
	private ArrayList<Board> solutions;
	private ArrayList<Integer> premierePiece;
	private ArrayList<InstanceBoard> erreursBoard;
	private String path = Main.path;
	private Instrumentation instrumentation;
	
	public NineSquarePuzzle(){
		this.board = new Board(Main.path);
		this.iterateur = 0;
		this.solutions = new ArrayList<Board>();
		this.pool = board.getPool();
		this.premierePiece = new ArrayList<Integer>();
		this.erreursBoard = new ArrayList<InstanceBoard>();
		this.instrumentation = new Instrumentation();
	}
	
	public void refresh(){
        setChanged();
        notifyObservers();
	}
	
	public void affichePool(){
		for (int i = board.getPositions().size()-1; i >= 0; i--) {
			board.positionner(board.getPool().getPool().get(i), this.ordrePlacement[i]);
		}
	}
	
	public void chargeBoard(Board b){
		for (int i = 0; i < b.getPositions().size(); i++) {
			board.positionner(b.getPositions().get(i), this.ordrePlacement[i]);
		}
	}
	
	public void resoudre(int n) throws InterruptedException{
		System.out.println("Vitesse exec : "+vitesseExec);
		int cptSolutions = 0;
		int premierePiece = 0;
		boolean premierTour = true;
		System.out.println("entré dans resoudre()");
		
		while (cptSolutions != board.getPool().getNbSolutions()) {
			boolean unique = false;
			System.out.println("Algo lancé");
			resoudreAide(0, 0, 0, false, this.erreursBoard, false, premierePiece, true, 0);
			if (!solutions.isEmpty()) {
				unique = true;
				for (int i = 0; i < solutions.size(); i++) {
					if (((new InstanceBoard(this.board.clone())).equals(new InstanceBoard(solutions.get(i))) )) {
						unique = false;
					}
				}
				if (unique) {
					solutions.add(this.board.clone());
					erreursBoard.add(new InstanceBoard(this.board));
					System.out.println("Solution ajoutée (not empty)");
				}else {
					System.out.println("Solution déjà trouvée");
				}
			}else {
				solutions.add(this.board.clone());
				erreursBoard.add(new InstanceBoard(this.board));
				System.out.println("Solution ajoutée (empty)");
			}
			
			this.board.resetBoard();refresh();
			this.fini = false;
			this.pool = new Pool(Main.path);
			System.out.println("cpt de sols : "+(cptSolutions+1)+"\tNb de sols trouvées : "+this.getNbSolutionsTrouvees()+"\tTaille du tabl : "+solutions.size());
			premierePiece = pieceCentraleSuivante(solutions.get(cptSolutions));
			System.out.println("nb total de solutions : "+board.getPool().getNbSolutions());
			premierTour = true;
			if (unique || solutions.size() == 1) {
				cptSolutions++;
				System.out.println("cpt de sol : "+cptSolutions);
			}
		}
		System.out.println("Résolution finie");
		int i = 0;
		for (Board board : solutions) {
			i++;
			System.out.println("board solution n°"+i);
			for (Piece p : board.getPositions()) {
				System.out.println(p.getNom());
			}
		}
	}
	
	public void resoudreAide(int n, int orientation, int nbPiecesTestees, boolean aTourne, ArrayList<InstanceBoard> boardFaux, boolean fini, int premierePiece, boolean premierTour, int nbToursPremierePiece) throws InterruptedException{
		if (n < 9 && !bienPlacee(board.getPositions().get(Board.DROITE_BAS), n) && !this.fini) {
//			System.out.println("derniere p bien placée : "+bienPlacee(board.getPositions().get(Board.DROITE_BAS), n));
//			System.out.println("entre dans résoudreAide()\tn : "+n);
			while (!bienPlacee(board.getPositions().get(Board.DROITE_BAS), n) /*board.getPool().getPool().size() > 0 && */) {
//				System.out.println("fini : "+fini);
				if (nbPiecesTestees > board.getPool().getPool().size()) {
					if (n > 0 || !(nbToursPremierePiece < 3)) {
						board.getPositions().get(this.ordrePlacement[n]).setOrientation(4);refresh();
						board.retirer(board.getPositions().get(this.ordrePlacement[n]));refresh();
						n--;
					}
					while (aEteUneInstance(new InstanceBoard(board), boardFaux)) {
						if (n == 0) {
							//si la première pièce est mal placée on la fait tourner plus tard dans le programme
						}
						else if (n > 0) {
							board.getPositions().get(this.ordrePlacement[n]).setOrientation(4);refresh();
							board.retirer(board.getPositions().get(this.ordrePlacement[n]));refresh();
							n--;
						}else if (n < 0) {
							n = 0;
							board.getPositions().get(this.ordrePlacement[n]).setOrientation(4);refresh();
							board.retirer(board.getPositions().get(this.ordrePlacement[n]));refresh();
							board.positionner(board.getPool().getPool().get(0), this.ordrePlacement[n]);refresh();
						}
					}
					if (board.getPool().getPool().size() >= 0 && !(n == 0 && !(nbToursPremierePiece > 3))/*&& !board.getPositions().get(0).getNom().equals("*")*/) {
						boardFaux.add(new InstanceBoard(board));
					}
				}
				while (nbPiecesTestees <= board.getPool().getPool().size() && !this.fini) {//Tant qu'on a pas testé toutes les pièces pour une case
//					System.out.println("n : "+n+"\ta tourné : "+aTourne+"\tnb de p testées : "+nbPiecesTestees);
					if (n > 0 && aTourne || (n == 0 && !(nbToursPremierePiece < 3) )) {
						board.getPositions().get(this.ordrePlacement[n]).setOrientation(4);refresh();
						board.retirer(board.getPositions().get(this.ordrePlacement[n]));refresh();
						nbPiecesTestees++;
					}
					if (board.getPool().getPool().size() > 0) {
//						System.out.println("n : "+n);
						if (premierTour) {
							board.positionner(board.getPool().getPool().get(premierePiece), this.ordrePlacement[n]);refresh();
							orientation = 0;
							board.getPositions().get(this.ordrePlacement[n]).setOrientation(orientation);refresh();
							Thread.sleep(1000-vitesseExec);
						}
						else if (nbToursPremierePiece < 3 && n == 0) {
						//Ne rien faire, laisser tourner	
						}else {
							
						}{
							board.positionner(board.getPool().getPool().get(0), this.ordrePlacement[n]);refresh();
							orientation = 0;
							board.getPositions().get(this.ordrePlacement[n]).setOrientation(orientation);refresh();
							Thread.sleep(1000-vitesseExec);
						}
					}else {
//						System.out.println("Fini");System.out.println((new InstanceBoard(board).toString()));fini = true;//System.exit(0);
					}
//					System.out.println("coucou");
					while (orientation < 4 && !this.fini) {
						Thread.sleep(1000-vitesseExec);
//						System.out.println("n : "+n+"\t modulo  = 0 : "+(this.ordrePlacement[n] % 2 == 0)+"\t pool size "+board.getPool().getPool().size()+"\tfini : "+fini);
						if (n == 0 && !premierTour) {
							if (!(nbToursPremierePiece < 3)) {//Si la pièce à trop tournée, on fait en sorte qu'elle quitte le while et qu'on la retire après
								aTourne = true;
								orientation = 8;
								nbToursPremierePiece = 0;
							}else {//Sinon on la fait tourner
								board.getPositions().get(this.ordrePlacement[n]).tourne(1);refresh();
								Thread.sleep(1000-vitesseExec);
								nbToursPremierePiece++;
							}
						}
							if (bienPlacee(board.getPositions().get(this.ordrePlacement[n]), n)) {
//								System.out.println("bien placée");
								if (premierTour) {
									premierTour = false;
								}
								resoudreAide(n+1, 0, 0, false, boardFaux, fini, premierePiece, premierTour, nbToursPremierePiece);
//								System.out.println("n : "+n);
//								System.out.println("bien placée : "+bienPlacee(board.getPositions().get(Board.DROITE_BAS), n));
									if (bienPlacee(board.getPositions().get(Board.DROITE_BAS), n)) {
										this.fini = true;
										nettoieDoublon(erreursBoard);//Enlève les doublons pour raccourcir les tests
//										System.out.println("fini");
										orientation = 4;
										return;
									}
									else if (this.fini) {
										return;
									}else {//Sortir de la boucle
										orientation = 4;
									}
							}else {
								orientation++;
								aTourne = true;
								board.getPositions().get(this.ordrePlacement[n]).setOrientation(board.getPositions().get(this.ordrePlacement[n]).getOrientation()+1);
						        refresh();
							}
					}
//					System.out.println("Sorti de orientation < 4");
				}
//				System.out.println("Sorti de nbPiecesTestees <= board.getPool().getPool().size()");
			}
//			System.out.println("Sorti de board.getPool().getPool().size() > 0");
		}else {
			//fen.refreshBoard();
			System.out.println("Solution ajoutée à l'arrayList\tnb solutions trouvees : "+(this.nbSolutionsTrouvees +1));
			this.fini = true;this.nbSolutionsTrouvees++;
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
		String[] tabLettres = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
		Piece piece = board.getPositions().get(Board.CENTRE);
		String anciennePieceCentrale = piece.getNom();
		int indiceAnciennePiece = 0;
		
		for (int i = 0; i < tabLettres.length; i++) {
			if (tabLettres[i].equals(anciennePieceCentrale)) {
				indiceAnciennePiece = i;
			}
		}
		if (indiceAnciennePiece < tabLettres.length -1) {
			for (int i = 0; i < this.board.getPool().getPool().size(); i++) {
				if (this.board.getPool().getPool().get(i).getNom().equals(tabLettres[indiceAnciennePiece+1])) {
					return i;
				}
			}
		}else {
			for (int i = 0; i < this.board.getPool().getPool().size(); i++) {
				if (this.board.getPool().getPool().get(i).getNom().equals(tabLettres[8])) {
					return i;
				}
			}
		}
		
		return -1;
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
			for (int i = indicesANettoyer.size()-1; i >= 0; i--) {
				boardFaux.remove(indicesANettoyer.get(i));
			}
		}
	}
	
	public boolean aEteUneInstance(InstanceBoard instance, ArrayList<InstanceBoard> boardsFaux){
//		System.out.println("-----------------------------\n"+boardsFaux.size()+" instances\n|\t"+instance.toString());
		for (InstanceBoard instanceBoard : boardsFaux) {
//			System.out.println(instanceBoard.toString());
			if (instance.equals(instanceBoard)) {
//				System.out.println("A été une instance !!");
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
//			Si la pièce est vide retourner faux
			if (p.getNom().equals("*")) {
				return false;
			}
//			Si la pièce est la première retourner vrai
			if (indiceP == Board.CENTRE) {
				return true;
			}
//			Si c'est la dernière piece
			if (n == 8) {
//				System.out.println("dernière piece : "+p.getNom()+" doit etre testee avec : "+board.getPositions().get(this.ordrePlacement[n-1]).getNom()+" et "+board.getPositions().get(this.ordrePlacement[1]).getNom());
				if (!(match(p, board.getPositions().get(this.ordrePlacement[n-1])) && match(p, board.getPositions().get(this.ordrePlacement[1])))) {
					return false;
				}
//				for (Piece piece : board.getPositions()) {
//					System.out.println(piece.getNom());
//				}
//				System.exit(0);
			}
			if (n == 0) {//Cas de test
				return false;
			}
			if (n > 8) {//cas de test
				return false;
			}
//			Cas général
			if (this.ordrePlacement[n] % 2 == 0) {
				if (!match(p, board.getPositions().get(this.ordrePlacement[n-1]))) {
					return false;
				}
			}else {
				if (!(match(p, board.getPositions().get(this.ordrePlacement[n-1])) && match(p, board.getPositions().get(Board.CENTRE)))) {
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
	
	public void ouvrir(){
		String pathRecu = JOptionPane.showInputDialog(null, "Path :", "Ouvrir", JOptionPane.QUESTION_MESSAGE);
		Path p = Paths.get(pathRecu);
		if (Files.exists(p)) {
			Main.path = pathRecu;
		}
		fichierOuvert = true;
		board = new Board(Main.path);
        setChanged();
        notifyObservers();
	}
		
	public void solutionPrecedente(){
		
	}
	
	public void solutionSuivante(){
		
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

	public void setVitesseExec(int value) {
		// TODO Auto-generated method stub
		this.vitesseExec = value;
        setChanged();
        notifyObservers();
	}

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	public boolean getFichierOuvert() {
		
		return false;
	}

	public Instrumentation getInstrumentation() {
		return instrumentation;
	}

	public void setInstrumentation(Instrumentation instrumentation) {
		this.instrumentation = instrumentation;
	}

	public String getPath() {
		// TODO Auto-generated method stub
		return this.path;
	}
}

	
