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

import nineSquarePuzzle.Board;
import nineSquarePuzzle.Instrumentation;
import nineSquarePuzzle.Main;
import nineSquarePuzzle.Pool;

public class Fenetre extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel container = new JPanel(), grid = new JPanel();
	JLabel titreProjet = new JLabel("Nine Square Puzzle");
	JMenuBar menu = new JMenuBar();
	JMenu fichier, action;
	JMenuItem quitter, ouvrir, tourner, lancerAlgo;
	
	int width = 600, height = 600;
	int carreCote = (width - 100)/3;
	public PoolCarre poolcarre; BoardCarre boardCarre;
	Pool pool;
	Board board;
	Instrumentation instrumentation;
	Main main;
	
	public Fenetre(Board board, Instrumentation instrumentation, Main m){
		this.main = m;
		this.board = board;
		this.pool = board.getPool();
		this.instrumentation = instrumentation;
		this.poolcarre = new PoolCarre(this.pool, width, height);
		this.boardCarre = new BoardCarre(this.board, width, height);
		
		this.setTitle(Main.path);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		grid.setLayout(new GridLayout(3,3,5,5));
		
		container.setLayout(new BorderLayout());
		container.setBackground(Color.white);
		
		initialiseBoard();
		setMenu();
		
		JPanel titreProjetPanel = new JPanel(); titreProjetPanel.setOpaque(false);
		titreProjetPanel.add(titreProjet);
		container.add(menu, BorderLayout.NORTH);
		this.setContentPane(container);
		this.setVisible(true);
	}
	
	public void initialiseBoard(){
		for (int i = 0; i < 9; i++) {
			grid.add(boardCarre.getCarres()[i]);
		}
		boardCarre.charge();
		container.add(grid);
	}
	
	public void refreshBoard(){
		for (int i = 8; i >= 0; i--) {
			grid.remove(i);
		}
		boardCarre.charge();
		for (int i = 0; i < 9; i++) {
			grid.add(boardCarre.getCarres()[i], i);
		}
		grid.revalidate();
		container.revalidate();
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
			lancerAlgo = new JMenuItem("Lancer");
			lancerAlgo.addActionListener(new menuActionListener());
			lancerAlgo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		action.add(tourner);
		action.add(lancerAlgo);
		
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

	public JMenuItem getLancerAlgo() {
		return lancerAlgo;
	}

	public void setLancerAlgo(JMenuItem lancerAlgo) {
		this.lancerAlgo = lancerAlgo;
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
					try {
						Main.main(null);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Ce fichier n'existe pas !!", "erreur d'ouverture !", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if (e.getSource().equals(tourner)) {
				String[] nbTours = {"1","2","3"}; 
				String nbString = (String)JOptionPane.showInputDialog(null, "Combien de tours ?", "Faire tourner", JOptionPane.QUESTION_MESSAGE, null, nbTours, nbTours[1]);
				int nb = Integer.valueOf(nbString);
				pool.getPool().get(0).tourne(nb);
				poolcarre = new PoolCarre(pool, width, height);
				grid.revalidate();
			}
			else if (e.getSource().equals(lancerAlgo)) {
				try {
					main.puzzle.resoudre(0);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				refreshBoard();
			}
		}
		
	}

	public PoolCarre getPoolcarre() {
		return poolcarre;
	}

	public void setPoolcarre(PoolCarre poolcarre) {
		this.poolcarre = poolcarre;
	}

	public BoardCarre getBoardCarre() {
		return boardCarre;
	}

	public void setBoardCarre(BoardCarre boardCarre) {
		this.boardCarre = boardCarre;
	}

}
