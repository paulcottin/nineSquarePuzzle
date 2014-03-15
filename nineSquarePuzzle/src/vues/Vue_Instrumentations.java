package vues;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nineSquarePuzzle.NineSquarePuzzle;

/**
 * Affichage du bandeau des annotations statistiques
 * @author Paul
 *
 */
public class Vue_Instrumentations extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	NineSquarePuzzle puzzle;
	JLabel nbAppelRecur;
	JLabel tempsExec;
	
	public Vue_Instrumentations(NineSquarePuzzle p){
		this.puzzle = p;
		
		puzzle.addObserver(this);
		
		tempsExec = new JLabel("Temps d'execution : "+puzzle.getInstrumentation().tempsEcoule()+"  ---- ");
		nbAppelRecur = new JLabel("Nombre d'appel recursif : "+String.valueOf(puzzle.getInstrumentation().getNbAppelRecursifs()));
		this.add(tempsExec); 
		this.add(nbAppelRecur);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (puzzle.isAlgoFini()) {
			tempsExec.setText("Temps d'execution : "+puzzle.getTpsExec()+"  ---- ");
		}else if (puzzle.isAlgoLance()) {
			tempsExec.setText("Temps d'execution : "+puzzle.getInstrumentation().tempsEcoule()+"  ---- ");
		} else {
			tempsExec.setText("Temps d'execution : -1  ---- ");
		}
		nbAppelRecur.setText("Nombre d'appel recursif : "+String.valueOf(puzzle.getNbAppelRecursif()));
		this.revalidate();
	}

}
