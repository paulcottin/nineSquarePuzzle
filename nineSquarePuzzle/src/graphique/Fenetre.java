package graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nineSquarePuzzle.Instrumentation;
import nineSquarePuzzle.Pool;

public class Fenetre extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel container = new JPanel();
	JLabel titreProjet = new JLabel("Nine Square Puzzle");
	int width = 600, height = 600;
	int carreCote = (width - 100)/3;
	PoolCarre poolcarre;
	Pool pool;
	Instrumentation instrumentation;
	
	public Fenetre(Pool pool, Instrumentation instrumentation){
		this.pool = pool; this.instrumentation = instrumentation;
		this.poolcarre = new PoolCarre(pool, width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		
		container.setLayout(new BorderLayout());
		container.setBackground(Color.white);
		
		setGridLayout();
//		setInstrumentation();
		
		JPanel titreProjetPanel = new JPanel(); titreProjetPanel.setOpaque(false);
		titreProjetPanel.add(titreProjet);
		container.add(titreProjetPanel, BorderLayout.NORTH);
		this.setContentPane(container);
		this.setVisible(true);
	}
	
	public void setGridLayout(){
		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(3, 3, 5, 5));
//		Ajout des carrés de couleur au grid layout
		for (int i = 0; i < poolcarre.getCarresTab().length; i++) {
			grid.add(poolcarre.getCarresTab(i));
		}
		container.add(grid, BorderLayout.CENTER);
	}
	
	public void setInstrumentation(){
		JPanel inst = new JPanel(); inst.setOpaque(false);
		JLabel tempsExec = new JLabel("temps d'execution : "+instrumentation.tempsEcoule()+"  ---- ");
		JLabel nbAppelRecur = new JLabel("Nombre d'appel recursif : "+String.valueOf(instrumentation.getNbAppelRecursifs()));
		inst.add(tempsExec); inst.add(nbAppelRecur);
		container.add(inst, BorderLayout.SOUTH);
	}

}
