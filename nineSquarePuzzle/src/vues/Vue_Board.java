package vues;

import graphique.BoardCarre;
import graphique.Fenetre;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import nineSquarePuzzle.NineSquarePuzzle;

/**
 * Affichage du Board (affichage principal)
 * @author Paul
 *
 */
public class Vue_Board extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	NineSquarePuzzle puzzle;
	BoardCarre boardCarre;
	
	public Vue_Board(NineSquarePuzzle p){
		super();
		 this.puzzle = p;
		 boardCarre = new BoardCarre(puzzle.getBoard(), Fenetre.WIDTH, Fenetre.HEIGHT);
		 
		 puzzle.addObserver(this);
		 
		 this.setLayout(new GridLayout(3, 3, 5, 5));
		 
		 this.initialiseBoard();
	}
	
	public void initialiseBoard(){
		for (int i = 0; i < 9; i++) {
			this.add(boardCarre.getCarres()[i]);
		}
		boardCarre.charge();
	}
	
	public void refreshBoard(){
		for (int i = 8; i >= 0; i--) {
			this.remove(i);
		}
		boardCarre.charge();
		for (int i = 0; i < 9; i++) {
			this.add(boardCarre.getCarres()[i], i);
		}
		this.revalidate();
	}

	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		this.refreshBoard();
	}

}
