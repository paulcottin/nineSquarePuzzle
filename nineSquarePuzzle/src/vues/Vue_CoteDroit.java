package vues;

import graphique.Fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nineSquarePuzzle.NineSquarePuzzle;

public class Vue_CoteDroit extends JPanel implements Observer{

	NineSquarePuzzle puzzle;
	
	public Vue_CoteDroit(NineSquarePuzzle p){
		this.puzzle = p;
		puzzle.addObserver(this);
		
		this.setBackground(Color.GREEN);
		this.setPreferredSize(new Dimension(100, HEIGHT));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));		
		
		Dimension textDimension = new Dimension(100,50);
		JLabel titre = new JLabel(puzzle.getPool().getTitre().substring(0, 9));
		
		titre.setPreferredSize(textDimension);
		titre.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel nbSolutions;
		if (puzzle.getPool().getNbSolutions() == 1) {
			nbSolutions = new JLabel("Il y a "+puzzle.getPool().getNbSolutions()+" solution");
		}else {
			nbSolutions = new JLabel("Il y a "+puzzle.getPool().getNbSolutions()+" solutions");
		}
		nbSolutions.setPreferredSize(textDimension);
		nbSolutions.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel rapport = new JLabel("Trouvées : "+puzzle.getPool().getNbSolutionsTrouvees()+"/"+puzzle.getPool().getNbSolutions());
		rapport.setPreferredSize(textDimension);
		rapport.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		this.add(titre);
		this.add(nbSolutions);
		this.add(rapport);
		
		JPanel slider = new JPanel();
		JLabel vitesseExec = new JLabel("Vitesse");
		final JSlider vitesse = new JSlider(JSlider.VERTICAL,0, 1000, 500);//A remettre à 500 après les tests
		vitesse.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
					try {
						puzzle.setVitesseExec(vitesse.getValue());
					} catch (Exception e) {
						System.out.println("EXCEPTION !!");
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
		this.add(slider);
		
	}
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		this.revalidate();
	}

}
