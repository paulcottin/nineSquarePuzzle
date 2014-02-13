package graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import nineSquarePuzzle.Instrumentation;
import nineSquarePuzzle.Main;
import nineSquarePuzzle.Pool;

public class Fenetre extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel container = new JPanel(), grid;
	JLabel titreProjet = new JLabel("Nine Square Puzzle");
	JMenuBar menu = new JMenuBar();
	JMenu fichier, action;
	JMenuItem quitter, ouvrir, tourner;
	
	int width = 600, height = 600;
	int carreCote = (width - 100)/3;
	PoolCarre poolcarre;
	Pool pool;
	Instrumentation instrumentation;
	
	public Fenetre(Pool pool, Instrumentation instrumentation){
		this.pool = pool; this.instrumentation = instrumentation;
		this.poolcarre = new PoolCarre(this.pool, width, height);
		
		this.setTitle(Main.path);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		
		container.setLayout(new BorderLayout());
		container.setBackground(Color.white);
		
		setGridLayout();
		setMenu();
		
		JPanel titreProjetPanel = new JPanel(); titreProjetPanel.setOpaque(false);
		titreProjetPanel.add(titreProjet);
		container.add(menu, BorderLayout.NORTH);
		this.setContentPane(container);
		this.setVisible(true);
	}
	
	public void setGridLayout(){
		grid = new JPanel();
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
	
	public void setMenu(){
		
		fichier = new JMenu("Fichier");
			quitter = new JMenuItem("Quitter");
			quitter.addActionListener(new menuActionListener());
			quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
			ouvrir = new JMenuItem("Ouvrir");
			ouvrir.addActionListener(new menuActionListener());
			ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		fichier.add(ouvrir);
		fichier.add(quitter);
		
		action = new JMenu("Action");
			tourner = new JMenuItem("Tourner");
			tourner.addActionListener(new menuActionListener());
		action.add(tourner);
		
		menu.add(fichier);
		menu.add(action);
	}
	
	public void repaint() { 
//		 repaint le component courant 
		super.repaint(); 
//		repaint tous les components qu'il possède 
		for(int i = 0; i < this.getComponentCount(); i++) 
			this.getComponent(i).repaint(); 
	} 
		
	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	class menuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource().equals(quitter)) {
				System.exit(0);
			}
			else if (e.getSource().equals(ouvrir)) {
				String path = JOptionPane.showInputDialog(null, "Path :", "Ouvrir", JOptionPane.QUESTION_MESSAGE);
				Path p = Paths.get(path);
				if (Files.exists(p)) {
					Main.path = path;
					Fenetre.this.setEnabled(false);Fenetre.this.setVisible(false);
					Main.main(null);
				}else {
					JOptionPane.showMessageDialog(null, "Ce fichier n'existe pas !!", "erreur d'ouverture !", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if (e.getSource().equals(tourner)) {
				String[] nbTours = {"0","1","2","3","4"}; 
				String nbString = (String)JOptionPane.showInputDialog(null, "Combien de tours ?", "Faire tourner", JOptionPane.QUESTION_MESSAGE, null, nbTours, nbTours[1]);
				int nb = Integer.valueOf(nbString);
				pool.getPool().get(0).tourne(nb);
				System.out.println(pool.getPool().get(0).toString());poolcarre = new PoolCarre(pool, width, height);
				grid.removeAll();
				setGridLayout();
				System.out.println("Il devrait se tourner aussi dans la GUI mais je n'y arrive pas... :(");
			}
		}
		
	}

}
