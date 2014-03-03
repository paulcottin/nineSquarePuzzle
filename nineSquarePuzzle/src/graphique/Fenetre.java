package graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nineSquarePuzzle.Board;
import nineSquarePuzzle.Instrumentation;
import nineSquarePuzzle.Main;
import nineSquarePuzzle.NineSquarePuzzle;
import nineSquarePuzzle.Pool;

public class Fenetre extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel container = new JPanel(), grid = new JPanel();
	JLabel titreProjet = new JLabel("Nine Square Puzzle");
	JMenuBar menu = new JMenuBar();
	JMenu fichier, action, solutions;
	JMenuItem quitter, ouvrir, tourner, lancerAlgo, solutionPrecedente, solutionSuivante;
	JSlider vitesse;
	
	int width = 700, height = 600;
	int carreCote = (width - 100)/3;
	int vitesseExec;
	public PoolCarre poolcarre; BoardCarre boardCarre;
	boolean fichierOuvert = false;
	Pool pool;
	Board board;
	Instrumentation instrumentation;
	Main main;
	String path;
	NineSquarePuzzle puzzle;
	
	public Fenetre(boolean donneesChargees){		
		
		this.setTitle(this.path);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		
		grid.setLayout(new GridLayout(3, 3, 5, 5));
		container.setLayout(new BorderLayout());
		container.setBackground(Color.white);
		
		setMenu();
		
		JPanel titreProjetPanel = new JPanel(); titreProjetPanel.setOpaque(false);
		titreProjetPanel.add(titreProjet);
		container.add(menu, BorderLayout.NORTH);
		this.setContentPane(container);
		this.setVisible(true);
	}
	
	public void initialiseDonnees(){
		System.out.println("path : "+path);
		this.board = new Board(this.path);
		this.pool = board.getPool();
		this.instrumentation = new Instrumentation();
		this.poolcarre = new PoolCarre(this.pool, width, height);
		this.boardCarre = new BoardCarre(this.board, width, height);
		this.puzzle = new NineSquarePuzzle(board, this);
		this.initialiseBoard();
		this.setCoteDroit();
		this.revalidate();
		System.out.println(pool.getPool().get(0).toString());
	}
	
//	public Fenetre(Board board, Instrumentation instrumentation, Main m){
//		this.main = m;
//		this.board = board;
//		this.pool = board.getPool();
//		this.instrumentation = instrumentation;
//		this.poolcarre = new PoolCarre(this.pool, width, height);
//		this.boardCarre = new BoardCarre(this.board, width, height);
//		
//		this.setTitle(Main.path);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setSize(width, height);
//		this.setLocationRelativeTo(null);
//		grid.setLayout(new GridLayout(3,3,5,5));
//		
//		container.setLayout(new BorderLayout());
//		container.setBackground(Color.white);
//		
//		initialiseBoard();
//		setMenu();
//		setCoteDroit();
//		
//		JPanel titreProjetPanel = new JPanel(); titreProjetPanel.setOpaque(false);
//		titreProjetPanel.add(titreProjet);
//		container.add(menu, BorderLayout.NORTH);
//		this.setContentPane(container);
//		this.setVisible(true);
//	}
	
	private void setCoteDroit() {
		JPanel cote = new JPanel();
		cote.setBackground(Color.GREEN);
		cote.setPreferredSize(new Dimension(100, height));
		cote.setLayout(new BoxLayout(cote, BoxLayout.PAGE_AXIS));		
		
		Dimension textDimension = new Dimension(100,50);
		JLabel titre = new JLabel(pool.getTitre().substring(0, 9));
		titre.setPreferredSize(textDimension);
		titre.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel nbSolutions;
		if (pool.getNbSolutions() == 1) {
			nbSolutions = new JLabel("Il y a "+pool.getNbSolutions()+" solution");
		}else {
			nbSolutions = new JLabel("Il y a "+pool.getNbSolutions()+" solutions");
		}
		nbSolutions.setPreferredSize(textDimension);
		nbSolutions.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel rapport = new JLabel("Trouvées : "+pool.getNbSolutionsTrouvees()+"/"+pool.getNbSolutions());
		rapport.setPreferredSize(textDimension);
		rapport.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		cote.add(titre);
		cote.add(nbSolutions);
		cote.add(rapport);
		
//		GroupLayout layout = new GroupLayout(cote);
//		cote.setLayout(layout);
//		layout.setAutoCreateGaps(true);
//		layout.setAutoCreateContainerGaps(true);
//		layout.setHorizontalGroup(
//				layout.createSequentialGroup()
//					.addComponent(titre)
//					.addComponent(nbSolutions)
//					.addComponent(rapport)
//		);
		
		JPanel slider = new JPanel();
		JLabel vitesseExec = new JLabel("Vitesse");
		this.vitesse = new JSlider(JSlider.VERTICAL,0, 1000, 500);//A remettre à 500 après les tests
		vitesse.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getSource().equals(vitesse)) {
					Fenetre.this.vitesseExec = vitesse.getValue();
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
		cote.add(slider);
		
		this.vitesseExec = vitesse.getValue();
		
		container.add(cote, BorderLayout.EAST);
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
		JLabel tempsExec = new JLabel("Temps d'execution : "+instrumentation.tempsEcoule()+"  ---- ");
		JLabel nbAppelRecur = new JLabel("Nombre d'appel recursif : "+String.valueOf(instrumentation.getNbAppelRecursifs()));
		inst.add(tempsExec); inst.add(nbAppelRecur);
		container.add(inst, BorderLayout.SOUTH);
		this.refreshBoard();
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
		
		solutions = new JMenu("Solutions");
			solutionPrecedente = new JMenuItem("Solution précédente");
			solutionPrecedente.addActionListener(new menuActionListener());
			solutionSuivante = new JMenuItem("Solution suivante");
			solutionSuivante.addActionListener(new menuActionListener());
		solutions.add(solutionPrecedente);
		solutions.add(solutionSuivante);
		
		menu.add(fichier);
		menu.add(action);
		menu.add(solutions);
	}
	
	public void lancerAlgo() throws InterruptedException{
		puzzle.resoudre(0);
	}
	
	public void videGrid(){
		for (int i = 0; i < 9; i++) {
			grid.remove(i);
		}
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	class menuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource().equals(quitter)) {
				System.exit(0);
			}
			else if (e.getSource().equals(ouvrir)) {
				String pathRecu = JOptionPane.showInputDialog(null, "Path :", "Ouvrir", JOptionPane.QUESTION_MESSAGE);
				Path p = Paths.get(pathRecu);
				if (Files.exists(p)) {
					path = pathRecu;
					System.out.println("fichier ouvert : "+fichierOuvert);
					if (fichierOuvert) {
						videGrid();
						fichierOuvert = false;
					}else {

					}
					path = pathRecu;
					Fenetre.this.initialiseDonnees();
				}else {
					JOptionPane.showMessageDialog(null, "Ce fichier n'existe pas !!", "erreur d'ouverture !", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if (e.getSource().equals(tourner)) {
				String[] nbTours = {"1","2","3"}; 
				String nbString = (String)JOptionPane.showInputDialog(null, "Combien de tours ?", "Faire tourner", JOptionPane.QUESTION_MESSAGE, null, nbTours, nbTours[1]);
				int nb = 0;
				try {
					nb = Integer.valueOf(nbString);
				} catch (NumberFormatException e2) {
					
				}
				pool.getPool().get(0).tourne(nb);
				poolcarre = new PoolCarre(pool, width, height);
				grid.revalidate();
			}
			else if (e.getSource().equals(lancerAlgo)) {
				try {
					lancerAlgo();
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

	public int getVitesseExec() {
		return vitesseExec;
	}

	public void setVitesseExec(int vitesseExec) {
		this.vitesseExec = vitesseExec;
	}

}
