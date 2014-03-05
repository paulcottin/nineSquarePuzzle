package vues;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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

public class Vue_Menu extends JMenuBar{

	NineSquarePuzzle puzzle;
	
	public Vue_Menu(NineSquarePuzzle puzzle){
		this.puzzle = puzzle;
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
		solutionPrecedente.addActionListener(new ControleurMenu_SolutionPrecedente(this.puzzle));
		JMenuItem solutionSuivante = new JMenuItem("Solution suivante");
		solutionSuivante.addActionListener(new ControleurMenu_SolutionSuivante(this.puzzle));
	solutions.add(solutionPrecedente);
	solutions.add(solutionSuivante);
	
	this.add(fichier);
	this.add(action);
	this.add(solutions);
	}
}
