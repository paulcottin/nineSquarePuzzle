package nineSquarePuzzle;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import graphique.FiltreExtension;
/**
 * Classe principale - résolution du puzzle
 * @author Paul
 *
 */
public class NineSquarePuzzle extends Observable implements Runnable{

	private Board board;
	private int[] ordrePlacement = {Board.CENTRE, Board.DROITE, Board.DROITE_HAUT, Board.CENTRE_HAUT, Board.GAUCHE_HAUT, Board.GAUCHE, Board.GAUCHE_BAS, Board.CENTRE_BAS, Board.DROITE_BAS};
	private int nbSolutions = 0, nbSolutionsTrouvees = 0, vitesseExec, solutionCourante;
	private boolean fini = false, algoFini, unique, algoLance;
	private Pool pool;
	private ArrayList<Solution> solutions;
	private ArrayList<InstanceBoard> erreursBoard;
	private String path = Main.path, tpsEcoule;
	private Instrumentation instrumentation;
	private Resolution resolution;

	/**
	 * initialisation des données
	 */
	public NineSquarePuzzle(){
		resolution = new Resolution(this);
		this.vitesseExec = 999;
		this.board = new Board(Main.path);
		this.pool = new Pool(Main.path);
		board.setPool(pool);
		this.algoFini = false; unique = false; algoLance = false;
		this.solutionCourante = 0;
		this.solutions = new ArrayList<Solution>();
		this.erreursBoard = new ArrayList<InstanceBoard>();
		this.instrumentation = new Instrumentation();
	}

	/**
	 * ouvre un fichier de données
	 */
	public void ouvrir() {
		JFileChooser choix = new JFileChooser();
		choix.setCurrentDirectory(new File("data1.txt"));
		choix.setMultiSelectionEnabled(false);
		// Ajout de filtre
		choix.setFileFilter(new FiltreExtension("Texte", ".txt"));
		choix.setAcceptAllFileFilterUsed(false);

		int retour = choix.showOpenDialog(null);
		if (retour == JFileChooser.APPROVE_OPTION) {
			// chemin absolu du fichier choisi
			Main.path = choix.getSelectedFile().getAbsolutePath();
			reinitialisation();
		}
		refresh();
	}

	/**
	 * Réinitialisation du puzzle pour résoudre une autre instance (par ex)
	 */
	public void reinitialisation() {
		board.resetBoard();
		resolution = new Resolution(this);
		this.vitesseExec = 999;
		this.algoFini = false; unique = false; algoLance = false;
		this.solutionCourante = 0;
		this.nbSolutionsTrouvees = 0;
		this.solutions = new ArrayList<Solution>();
		this.pool = new Pool(Main.path);
		board.setPool(pool);
		this.erreursBoard = new ArrayList<InstanceBoard>();
		this.instrumentation = new Instrumentation();
		this.getInstrumentation().setNbAppelRecursifs(0);
	}

	/**
	 * Affiche la solution précédente
	 */
	public void solutionPrecedente(){
		if (solutionCourante == 0) {
			solutionCourante = nbSolutions -1;
			chargeBoard(solutions.get(solutionCourante));
		}
		else if((solutionCourante - 1) <= 0) {
			solutionCourante = 0;
			chargeBoard(solutions.get(solutionCourante));
		}else {
			solutionCourante--;
			chargeBoard(solutions.get(solutionCourante));
		}
		refresh();
	}

	/**
	 * Affiche la solution suivante
	 */
	public void solutionSuivante(){
		if (solutionCourante == nbSolutions -1) { 
			solutionCourante = 0;
			chargeBoard(solutions.get(solutionCourante));
		}
		else if((solutionCourante+1) == solutions.size()) {
			solutionCourante = 0;
			chargeBoard(solutions.get(solutionCourante));
		}else {
			solutionCourante++;
			chargeBoard(solutions.get(solutionCourante));
		}
		refresh();
	}

	/**
	 * Affiche la pool
	 */
	public void affichePool(){
		board.resetBoard();
		for (int i = board.getPositions().size()-1; i >= 0; i--) {
			board.positionner(pool.getPool().get(i), i);
		}
		refresh();
		board.resetBoard();
	}

	/**
	 * permet d'afficher un Board particulier
	 * @param solution : la solution à afficher
	 */
	public void chargeBoard(Solution solution){
		board.resetBoard();
		for (int i = 0; i < solution.getBoard().size(); i++) {
			board.positionner(solution.getBoard().get(i), i);
		}
		refresh();
	}

	/**
	 * la fonction de résolution du puzzle
	 * @param n paramètre de récursion
	 * @throws InterruptedException
	 */
	public void resoudre(int n) throws InterruptedException{
		algoLance = true;
		instrumentation.start();
		algoFini = false;
		nbSolutionsTrouvees = 0;solutionCourante = 0;
		int cptSolutions = 0;
		int premierePiece = 0;

		while (cptSolutions != pool.getNbSolutions()) {
			try {
				resoudreAide(0, 0, 0, false, erreursBoard, false, premierePiece, true, 0);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("interrupted exception !");
			}

			ajouteSolution(board.clone());

			board.resetBoard();refresh();
			fini = false;
			if (cptSolutions != pool.getNbSolutions()) {
				premierePiece = pieceCentraleSuivante(solutions.get(cptSolutions).getBoard());
			}
			if (unique) {
				cptSolutions++;
				nbSolutionsTrouvees++;
			}
		}
		instrumentation.stop();
		algoFini = true;
		JOptionPane.showMessageDialog(null, "Résolution finie !");

		chargeBoard(solutions.get(solutionCourante));
		refresh();
		algoLance = false;
		resolution.interrupt();
	}

	/**
	 * fonction d'aide
	 * @param n : paramètre de récursion
	 * @param orientation : orientation d'une pièce
	 * @param nbPiecesTestees : mémorise le nombre de pièces qui ont été testées pour un emplacement donné
	 * @param aTourne : boolean pour savoir si une pièce à tourner 4 fois
	 * @param boardFaux : arrayList mémorisant les instances ne fonctionnant pas
	 * @param fini : boolean qui sert à sortir de la fonction d'aide
	 * @param premierePiece : int sert à positionner la première pièce
	 * @param premierTour : boolean qui sert à savoir si on est au premier tour de la récursion ou pas
	 * @param nbToursPremierePiece : int
	 * @throws InterruptedException
	 */
	public void resoudreAide(int n, int orientation, int nbPiecesTestees, boolean aTourne, ArrayList<InstanceBoard> boardFaux, boolean fini, int premierePiece, boolean premierTour, int nbToursPremierePiece) throws InterruptedException{
		if (n < 9 && !bienPlacee(board.getPositions().get(Board.DROITE_BAS), n) && !this.fini) {
			while (!bienPlacee(board.getPositions().get(Board.DROITE_BAS), n)) {
				if(n == 0 && aEteUneInstance(new InstanceBoard(this.board), boardFaux) && nbToursPremierePiece >=3){
					board.getPositions().get(this.ordrePlacement[n]).setOrientation(4);refresh();
					board.retirer(board.getPositions().get(this.ordrePlacement[n]));refresh();
					board.positionner(pool.getPool().get(0), this.ordrePlacement[n]);refresh();
				}
				if (nbPiecesTestees > pool.getPool().size()) {
					if (n > 0 || !(nbToursPremierePiece < 3)) {
						board.getPositions().get(this.ordrePlacement[n]).setOrientation(4);refresh();
						board.retirer(board.getPositions().get(this.ordrePlacement[n]));refresh();
						n--;
					}
					while (aEteUneInstance(new InstanceBoard(board), boardFaux)) {
						if (n == 0 && nbToursPremierePiece < 4) {
							board.retirer(board.getPositions().get(this.ordrePlacement[n]));refresh();
						}
						else if (n > 0 && orientation < 4) {
							orientation++;
							board.getPositions().get(this.ordrePlacement[n]).tourne(1);refresh();
						}
						else if (n > 0 && orientation >= 4) {
							board.getPositions().get(this.ordrePlacement[n]).setOrientation(4);refresh();
							board.retirer(board.getPositions().get(this.ordrePlacement[n]));refresh();
							n--;
						}else if (n < 0) {
							n = 0;
							board.getPositions().get(this.ordrePlacement[n]).setOrientation(4);refresh();
							board.retirer(board.getPositions().get(this.ordrePlacement[n]));refresh();
							board.positionner(pool.getPool().get(0), this.ordrePlacement[n]);refresh();
						}
					}
					if (pool.getPool().size() >= 0 && !(n == 0 && !(nbToursPremierePiece > 3))) {
						boardFaux.add(new InstanceBoard(board));
					}
				}
				while (nbPiecesTestees <= pool.getPool().size() && !this.fini) {//Tant qu'on a pas testé toutes les pièces pour une case
					if (n > 0 && aTourne || (n == 0 && !(nbToursPremierePiece < 3) )) {
						board.getPositions().get(this.ordrePlacement[n]).setOrientation(4);refresh();
						board.retirer(board.getPositions().get(this.ordrePlacement[n]));refresh();
						nbPiecesTestees++;
					}
					if (pool.getPool().size() > 0) {
						if (premierTour) {
							board.positionner(pool.getPool().get(premierePiece), this.ordrePlacement[n]);refresh();
							orientation = 0;
							board.getPositions().get(this.ordrePlacement[n]).setOrientation(orientation);refresh();
							Thread.sleep(1000-vitesseExec);
						}
						else if (nbToursPremierePiece < 3 && n == 0) {
							//Ne rien faire, laisser tourner	
						}else {

						}{
							board.positionner(pool.getPool().get(0), this.ordrePlacement[n]);refresh();
							orientation = 0;
							board.getPositions().get(this.ordrePlacement[n]).setOrientation(orientation);refresh();
							Thread.sleep(1000-vitesseExec);
						}
					}else {

					}
					while (orientation < 4 && !this.fini) {
						Thread.sleep(1000-vitesseExec);
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
							if (premierTour) {
								premierTour = false;
							}
							instrumentation.appel();
							resoudreAide(n+1, 0, 0, false, boardFaux, fini, premierePiece, premierTour, nbToursPremierePiece);
							if (bienPlacee(board.getPositions().get(Board.DROITE_BAS), n)) {
								this.fini = true;
								nettoieDoublon(erreursBoard);//Enlève les doublons pour raccourcir les tests
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
				}
			}
		}else {
			this.fini = true;
		}
	}

	/**
	 * ajoute une solution à l'array list de solution
	 * @param b
	 */
	public void ajouteSolution(Board b){
		unique = true;
		if (!solutions.isEmpty()) {
			for (int i = 0; i < solutions.size(); i++) {
				if (((new InstanceBoard(b)).equals(new InstanceBoard(solutions.get(i))) )) {
					unique = false;
				}
			}
			if (unique) {
				solutions.add(new Solution(b.getPositions()));
				erreursBoard.add(new InstanceBoard(b));
				//				Ajoute aux solutions et à erreurBoard les rotation de la solution
				for (int i = 0; i < 3; i++) {
					b.setPositions(tournerSolution(b.getPositions()));
					solutions.add(new Solution(b.getPositions()));
					erreursBoard.add(new InstanceBoard(b));
				}
			}else {
			}
		}else {
			solutions.add(new Solution(b.getPositions()));
			erreursBoard.add(new InstanceBoard(b));
			//			Ajoute aux solutions et à erreurBoard les rotation de la solution
			for (int i = 0; i < 3; i++) {
				b.setPositions(tournerSolution(b.getPositions()));
				solutions.add(new Solution(b.getPositions()));
				erreursBoard.add(new InstanceBoard(b));
			}
		}

		//		Ajoute à erreurBoard l'instance de la pièce centrale seule
		Board bo = new Board();
		bo.getPositions().set(Board.CENTRE, this.board.getPositions().get(Board.CENTRE));
		erreursBoard.add(new InstanceBoard(bo));
	}

	/**
	 * fait tourner de 4 tours une solution pour avoir les 4 solutions relatives
	 * @param b : ArrayLis<Solution>
	 * @return Board (à charger)
	 */
	public ArrayList<Piece> tournerSolution(ArrayList<Piece> b) {
		ArrayList<Piece> board = new ArrayList<Piece>();
		board.add(b.get(Board.GAUCHE_BAS).clone());
		board.add(b.get(Board.GAUCHE).clone());
		board.add(b.get(Board.GAUCHE_HAUT).clone());
		board.add(b.get(Board.CENTRE_BAS).clone());
		board.add(b.get(Board.CENTRE).clone());
		board.add(b.get(Board.CENTRE_HAUT).clone());
		board.add(b.get(Board.DROITE_BAS).clone());
		board.add(b.get(Board.DROITE).clone());
		board.add(b.get(Board.DROITE_HAUT).clone());
		for (int i = 0; i < board.size(); i++) {
			board.get(i).tourne(1);
		}
		refresh();
		return board;
	}

	/**
	 * fait tourner les solutions graphiquement
	 * @param b : ArrayList<Piece>
	 */
	public void tournerSolutionGraphique(ArrayList<Piece> b) {
		if (algoFini) {
			this.board.setPositions(tournerSolution(b));
			chargeBoard(new Solution(board.getPositions()));
		}else {
			JOptionPane.showMessageDialog(null, "L'algo n'est pas fini");
		}
		refresh();
	}

	/**
	 * Détermine si une pièce est parfaite ou pas
	 * @return boolean
	 */
	public boolean isPiecePerfect() {
		Pool p = new Pool(Main.path);
		String[] lettres = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
		String piece = (String)JOptionPane.showInputDialog(null, 
				"La piece suivante est-elle parfaite ?",
				"Piece parfaite",
				JOptionPane.QUESTION_MESSAGE,
				null,
				lettres,
				lettres[0]);
		if (pool.get(piece, p).estParfaite()) {
			JOptionPane.showMessageDialog(null, "La pièce "+piece+" est parfaite");
			return true;
		}else {
			return false;
		}
	}

	/**
	 * détermine si un Pool est parfait ou pas
	 */
	public void isPerfect(){
		if (pool.isPerfect()) {
			JOptionPane.showMessageDialog(null, "Vrai !");
		}else {
			JOptionPane.showMessageDialog(null, "Faux !");
		}
	}

	/**
	 * Calcul quel doit être l'indice de la pièce centrale qui suit pour qu'elles y passent toutes
	 * @param arrayList
	 * @return int
	 */
	public int pieceCentraleSuivante(ArrayList<Piece> arrayList){
		String[] tabLettres = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
		Piece piece = arrayList.get(Board.CENTRE);
		String anciennePieceCentrale = piece.getNom();
		int indiceAnciennePiece = 0;

		for (int i = 0; i < tabLettres.length; i++) {
			if (tabLettres[i].equals(anciennePieceCentrale)) {
				indiceAnciennePiece = i;
			}
		}
		if (indiceAnciennePiece < tabLettres.length -1) {
			for (int i = 0; i < this.pool.getPool().size(); i++) {
				if (this.pool.getPool().get(i).getNom().equals(tabLettres[indiceAnciennePiece+1])) {
					return i;
				}
			}
		}else {
			for (int i = 0; i < this.pool.getPool().size(); i++) {
				if (this.pool.getPool().get(i).getNom().equals(tabLettres[8])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Nettoie les doublons pour raccourcir le temps de test
	 * @param boardFaux ArrayList<InstanceBoard> : Les instances fausses
	 */
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

	/**
	 * Détermine si instance appartient à boardFauc
	 * @param instance : InstanceBoard
	 * @param boardFaux : ArrayList<InstanceBoard>
	 * @return boolean
	 */
	public boolean aEteUneInstance(InstanceBoard instance, ArrayList<InstanceBoard> boardFaux){
		for (InstanceBoard instanceBoard : boardFaux) {
			if (instance.equals(instanceBoard)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * vérifie si deux pièces correspondent
	 * @param p Piece
	 * @param q Piece
	 * @return boolean
	 */
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
		}
		if (indiceP == Board.CENTRE_BAS && indiceQ == Board.CENTRE && p.getNorth()+q.getSouth() == 0) {
			return true;
		}
		if (indiceP == Board.DROITE_BAS && indiceQ == Board.CENTRE_BAS && p.getWest()+q.getEast() == 0) {
			return true;
		}
		if (indiceP == Board.DROITE_BAS && indiceQ == Board.DROITE && p.getNorth()+q.getSouth() == 0) {
			return true;
		}
		if (indiceP == Board.GAUCHE && indiceQ == Board.GAUCHE_BAS && p.getSouth()+q.getNorth() == 0) {
			return true;
		}
		if (indiceP == Board.GAUCHE && indiceQ == Board.CENTRE && p.getEast()+q.getWest() == 0) {
			return true;
		}
		if (indiceP == Board.GAUCHE && indiceQ == Board.GAUCHE_HAUT && p.getNorth()+q.getSouth() == 0) {
			return true;
		}
		if (indiceP == Board.CENTRE && indiceQ == Board.GAUCHE && p.getWest()+q.getEast() == 0) {
			return true;
		}
		if (indiceP == Board.CENTRE && indiceQ == Board.DROITE && p.getEast()+q.getWest() == 0) {
			return true;
		}
		if (indiceP == Board.CENTRE && indiceQ == Board.CENTRE_HAUT && p.getNorth()+q.getSouth() == 0) {
			return true;
		}
		if (indiceP == Board.CENTRE && indiceQ == Board.CENTRE_BAS && p.getSouth()+q.getNorth() == 0) {
			return true;
		}
		if (indiceP == Board.DROITE && indiceQ == Board.CENTRE && p.getWest()+q.getEast() == 0) {
			return true;
		}
		if (indiceP == Board.DROITE && indiceQ == Board.DROITE_BAS && p.getSouth()+q.getNorth() == 0) {
			return true;
		}
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

	/**
	 * détermine si une pièce est bien placée
	 * @param p Piece
	 * @param n int : Rang de la récursion
	 * @return boolean
	 */
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
			if (!(match(p, board.getPositions().get(this.ordrePlacement[n-1])) && match(p, board.getPositions().get(this.ordrePlacement[1])))) {
				return false;
			}
		}
		if (n == 0) {
			return false;
		}
		if (n > 8) {
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

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getNbSolutionsTrouvees() {
		return nbSolutionsTrouvees;
	}

	public void setNbSolutionsTrouvees(int nbSolutionsTrouvees) {
		this.nbSolutionsTrouvees = nbSolutionsTrouvees;
	}

	public String getTpsExec(){
		return instrumentation.tempsEcouleCourant();
	}

	public ArrayList<Solution> getSolutions() {
		return solutions;
	}

	public void setSolutions(ArrayList<Solution> solutions) {
		this.solutions = solutions;
	}

	public void setVitesseExec(int value) {
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
		return this.path;
	}

	public int getSolutionCourante() {
		return solutionCourante;
	}

	public void setSolutionCourante(int solutionCourante) {
		this.solutionCourante = solutionCourante;
	}

	public boolean isAlgoFini() {
		return algoFini;
	}

	public void setAlgoFini(boolean algoFini) {
		this.algoFini = algoFini;
	}

	public Resolution getResolution() {
		return resolution;
	}

	public void refresh(){
		try {
			setChanged();
			notifyObservers();
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}

	public int getNbAppelRecursif(){
		return instrumentation.nbAppelRecursifs;
	}

	public boolean isAlgoLance() {
		return algoLance;
	}

	public void setAlgoLance(boolean algoLance) {
		this.algoLance = algoLance;
		refresh();
	}

	public int getVitesseExec() {
		return vitesseExec;
	}

	public String getTpsEcoule() {
		return tpsEcoule;
	}

	public void setTpsEcoule(String tpsEcoule) {
		this.tpsEcoule = tpsEcoule;
	}

	@Override
	public void run() {
		try {
			resoudre(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

}


