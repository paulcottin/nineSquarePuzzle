package vues;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import nineSquarePuzzle.NineSquarePuzzle;
import controleurs.ControleurMenu_AfficherPool;
import controleurs.ControleurMenu_Arreter;
import controleurs.ControleurMenu_Lancer;
import controleurs.ControleurMenu_Ouvrir;
import controleurs.ControleurMenu_Parfait;
import controleurs.ControleurMenu_PuzzleParfait;
import controleurs.ControleurMenu_Quitter;
import controleurs.ControleurMenu_SolutionPrecedente;
import controleurs.ControleurMenu_SolutionSuivante;
import controleurs.ControleurMenu_Tourner;

public class Vue_Menu extends JMenuBar implements Observer {

	NineSquarePuzzle puzzle;
	JMenuItem lancer, arreter;
	
	public Vue_Menu(NineSquarePuzzle puzzle){
		this.puzzle = puzzle;
		puzzle.addObserver(this);
		
		JMenu fichier = new JMenu("Fichier");
		JMenuItem quitter = new JMenuItem("Quitter");
		quitter.addActionListener(new ControleurMenu_Quitter());
		quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		JMenuItem ouvrir = new JMenuItem("Ouvrir");
		ouvrir.addActionListener(new ControleurMenu_Ouvrir(puzzle));
		ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
	fichier.add(ouvrir);
	fichier.add(quitter);
	
	JMenu action = new JMenu("Action");
		JMenuItem afficherPool = new JMenuItem("Afficher");
		afficherPool.addActionListener(new ControleurMenu_AfficherPool(this.puzzle));
		JMenuItem pieceParfaite = new JMenuItem("Piece parfaite ?");
		pieceParfaite.addActionListener(new ControleurMenu_Parfait(this.puzzle));
		JMenuItem puzzleParfait = new JMenuItem("Puzzle parfait ?");
		puzzleParfait.addActionListener(new ControleurMenu_PuzzleParfait(this.puzzle));
		lancer= new JMenuItem("Lancer");
		lancer.addActionListener(new ControleurMenu_Lancer(this.puzzle));
		lancer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		arreter = new JMenuItem("Arreter");
		arreter.addActionListener(new ControleurMenu_Arreter(this.puzzle));
		arreter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
	action.add(afficherPool);
	action.add(pieceParfaite);
	action.add(puzzleParfait);
	action.add(lancer);
	action.add(arreter);
		
	
	JMenu solutions = new JMenu("Solutions");
		JMenuItem tourner = new JMenuItem("Tourner");
		tourner.addActionListener(new ControleurMenu_Tourner(this.puzzle));
		JMenuItem solutionPrecedente = new JMenuItem("Solution précédente");
		solutionPrecedente.addActionListener(new ControleurMenu_SolutionPrecedente(this.puzzle));
		solutionPrecedente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ActionEvent.CTRL_MASK));
		JMenuItem solutionSuivante = new JMenuItem("Solution suivante");
		solutionSuivante.addActionListener(new ControleurMenu_SolutionSuivante(this.puzzle));
		solutionSuivante.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ActionEvent.CTRL_MASK));
	solutions.add(tourner);
	solutions.add(solutionPrecedente);
	solutions.add(solutionSuivante);
	
	this.add(fichier);
	this.add(action);
	this.add(solutions);
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}
}

