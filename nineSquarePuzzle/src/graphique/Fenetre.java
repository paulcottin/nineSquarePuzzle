package graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

import nineSquarePuzzle.Pool;

public class Fenetre extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel container = new JPanel();
	int width = 600, height = 600;
	int carreCote = (width - 100)/3;
	PoolCarre poolcarre;
	Pool pool;
	
//	Carre c1 = new Carre(carreCote, carreCote, Color.red);
//	Carre c2 = new Carre(carreCote, carreCote, Color.blue);
//	Carre c3 = new Carre(carreCote, carreCote, Color.green);
//	Carre c4 = new Carre(carreCote, carreCote, Color.gray);
//	Carre c5 = new Carre(carreCote, carreCote, Color.pink);
//	Carre c6 = new Carre(carreCote, carreCote, Color.cyan);
//	Carre c7 = new Carre(carreCote, carreCote, Color.orange);
//	Carre c8 = new Carre(carreCote, carreCote, Color.white);
//	Carre c9 = new Carre(carreCote, carreCote, Color.black);
	
	public Fenetre(Pool pool){
		this.pool = pool;
		this.poolcarre = new PoolCarre(pool, width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		
		
		
		setGridLayout();
		this.setContentPane(container);
		this.setVisible(true);
	}
	
	public void setGridLayout(){
		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(3, 3, 5, 5));
		for (int i = 0; i < poolcarre.getCarresTab().length; i++) {
			grid.add(poolcarre.getCarresTab(i));
		}
//		grid.add(c1);grid.add(c2);grid.add(c3);grid.add(c4);grid.add(c5);grid.add(c6);grid.add(c7);grid.add(c8);grid.add(c9);
		container.add(grid);
	}

}
