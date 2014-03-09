package vues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nineSquarePuzzle.NineSquarePuzzle;
import nineSquarePuzzle.Pool;

/**
 * Affichage des informations relatives à l'état d'avancement de la résolution de l'algorithme
 * @author Paul
 *
 */
public class Vue_CoteDroit extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	NineSquarePuzzle puzzle;
	Pool pool;
	JLabel rapport, titre, nbSolutions;
	
	public Vue_CoteDroit(NineSquarePuzzle p){
		this.puzzle = p;
		this.pool = p.getPool();
		puzzle.addObserver(this);
		pool.addObserver(this);
		
		this.setBackground(Color.GREEN);
		this.setPreferredSize(new Dimension(100, HEIGHT));
		this.setLayout(new BorderLayout());		
		
		JLabel texte = new JLabel();
		texte.setLayout(new GridLayout(3,1));
		
		Dimension textDimension = new Dimension(100,50);
		titre = new JLabel(puzzle.getPool().getTitre().substring(0, 9));
		
		titre.setPreferredSize(textDimension);
		titre.setAlignmentY(Component.LEFT_ALIGNMENT);
		
		if (puzzle.getPool().getNbSolutions() == 1) {
			nbSolutions = new JLabel("Il y a une solution");
		}else {
			nbSolutions = new JLabel("Il y a "+puzzle.getPool().getNbSolutions()+" solutions");
		}
		nbSolutions.setPreferredSize(textDimension);
		nbSolutions.setAlignmentX(Component.LEFT_ALIGNMENT);
		rapport = new JLabel("Trouvées : "+puzzle.getPool().getNbSolutionsTrouvees()+"/"+puzzle.getPool().getNbSolutions());
		rapport.setPreferredSize(textDimension);
		rapport.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		texte.add(titre);
		texte.add(nbSolutions);
		texte.add(rapport);
		
		JPanel slider = new JPanel();
		JLabel vitesseExec = new JLabel("Vitesse");
		final JSlider vitesse = new JSlider(JSlider.VERTICAL,0, 1000, puzzle.getVitesseExec());//A remettre à 500 après les tests
		vitesse.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
					try {
						puzzle.setVitesseExec(vitesse.getValue());
					} catch (Exception e) {
					}
			}
		});
		
		vitesse.setMajorTickSpacing(200);
		vitesse.setPaintTicks(true);
		Hashtable<Integer, JLabel> labelVitesseTable = new Hashtable<Integer, JLabel>();
		labelVitesseTable.put(new Integer(0), new JLabel("min"));
		labelVitesseTable.put(new Integer(1000), new JLabel("max"));
		vitesse.setLabelTable(labelVitesseTable);
		vitesse.setPaintLabels(true);
		slider.add(vitesseExec);
		slider.add(vitesse);
		this.add(texte, BorderLayout.CENTER);
		this.add(slider, BorderLayout.SOUTH);
		
	}
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (puzzle.getPool().getNbSolutions() == 1) {
			nbSolutions.setText("Il y a une solution");
		}else {
			nbSolutions.setText("Il y a "+puzzle.getPool().getNbSolutions()+" solutions");
		}
		titre.setText(puzzle.getPool().getTitre().substring(0, 9));
		rapport.setText("Trouvées : "+puzzle.getNbSolutionsTrouvees()+"/"+puzzle.getPool().getNbSolutions());
		this.repaint();
		this.revalidate();
	}

}
