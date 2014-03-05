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
import controleurs.ControleurMenu_Lancer;
import controleurs.ControleurMenu_Ouvrir;
import controleurs.ControleurMenu_Quitter;
import controleurs.ControleurMenu_SolutionPrecedente;
import controleurs.ControleurMenu_SolutionSuivante;

public class Vue_Menu extends JMenuBar implements Observer{

	NineSquarePuzzle puzzle;
	
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
		JMenuItem tourner = new JMenuItem("Tourner");
		tourner.addActionListener(new ControleurMenu_Quitter());
		JMenuItem afficherPool = new JMenuItem("Afficher");
		afficherPool.addActionListener(new ControleurMenu_AfficherPool(this.puzzle));
		JMenuItem lancerAlgo = new JMenuItem("Lancer");
		lancerAlgo.addActionListener(new ControleurMenu_Lancer(this.puzzle));
		lancerAlgo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
	action.add(tourner);
	action.add(afficherPool);
	action.add(lancerAlgo);
	
	JMenu solutions = new JMenu("Solutions");
		JMenuItem solutionPrecedente = new JMenuItem("Solution précédente");
//		if (this.puzzle.isAlgoFini()) {
//			solutionPrecedente.setEnabled(true);
//		}else {
//			solutionPrecedente.setEnabled(false);
//		}
		solutionPrecedente.setEnabled(false);//Pour l'instant
		solutionPrecedente.addActionListener(new ControleurMenu_SolutionPrecedente(this.puzzle));
		solutionPrecedente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ActionEvent.CTRL_MASK));
		JMenuItem solutionSuivante = new JMenuItem("Solution suivante");
//		if (this.puzzle.isAlgoFini()) {
//			solutionSuivante.setEnabled(true);
//		}else {
//			solutionSuivante.setEnabled(false);
//		}
		solutionSuivante.addActionListener(new ControleurMenu_SolutionSuivante(this.puzzle));
		solutionSuivante.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ActionEvent.CTRL_MASK));
	solutions.add(solutionPrecedente);
	solutions.add(solutionSuivante);
	
	this.add(fichier);
	this.add(action);
	this.add(solutions);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		this.revalidate();
	}
}
