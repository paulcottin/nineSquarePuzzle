package vues;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nineSquarePuzzle.NineSquarePuzzle;

public class Vue_Instrumentations extends JPanel implements Observer{

	NineSquarePuzzle puzzle;
	
	public Vue_Instrumentations(NineSquarePuzzle p){
		this.puzzle = p;
		
		puzzle.addObserver(this);
		
		JLabel tempsExec = new JLabel("Temps d'execution : "+puzzle.getInstrumentation().tempsEcoule()+"  ---- ");
		JLabel nbAppelRecur = new JLabel("Nombre d'appel recursif : "+String.valueOf(puzzle.getInstrumentation().getNbAppelRecursifs()));
		this.add(tempsExec); 
		this.add(nbAppelRecur);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		this.revalidate();
	}

}