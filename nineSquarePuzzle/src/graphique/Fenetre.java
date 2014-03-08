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
import javax.xml.stream.events.StartDocument;

import vues.Vue_Board;
import vues.Vue_CoteDroit;
import vues.Vue_Instrumentations;
import vues.Vue_Menu;
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
	public static int WIDTH = 700;
	public static int HEIGHT = 600;
	
	JPanel container = new JPanel();
	JLabel titreProjet = new JLabel("Nine Square Puzzle");
	JMenuBar menu = new JMenuBar();
	JMenu fichier, action, solutions;
	JMenuItem quitter, ouvrir, tourner, lancerAlgo, solutionPrecedente, solutionSuivante;
	JSlider vitesse;
	
	public PoolCarre poolcarre; BoardCarre boardCarre;
	boolean fichierOuvert = false;
	Pool pool;
	Board board;
	Instrumentation instrumentation;
	Main main;
	String path;
	NineSquarePuzzle puzzle;
	
	public Fenetre(){		
		super(Main.path);
		puzzle = new NineSquarePuzzle();
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		
		container.setLayout(new BorderLayout());
		container.setBackground(Color.white);
		
		Vue_Menu vueMenu = new Vue_Menu(this.puzzle);
		container.add(vueMenu, BorderLayout.NORTH);
		
		Vue_Board vueBoard = new Vue_Board(puzzle);
		container.add(vueBoard, BorderLayout.CENTER);
		
		Vue_Instrumentations vueInstrumentation = new Vue_Instrumentations(puzzle);
		container.add(vueInstrumentation, BorderLayout.SOUTH);
		
		Vue_CoteDroit coteDroit = new Vue_CoteDroit(puzzle);
		container.add(coteDroit, BorderLayout.EAST);
		
		this.setContentPane(container);
		this.setVisible(true);
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

	public NineSquarePuzzle getPuzzle() {
		return puzzle;
	}

	public void setPuzzle(NineSquarePuzzle puzzle) {
		this.puzzle = puzzle;
	}

	public static void main(String[] args){
		new Fenetre();
	}
}
