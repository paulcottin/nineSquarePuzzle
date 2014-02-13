package graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nineSquarePuzzle.Piece;

public class Carre extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int north, weast, east, south;
	String titre;
	Color color;
	int x, y;
	
	JLabel c1 = new JLabel(" ");
	JLabel c2 = new JLabel(String.valueOf(north));
	JLabel c3 = new JLabel(" ");
	JLabel c4 = new JLabel(String.valueOf(weast));
	JLabel c5 = new JLabel(titre);
	JLabel c6 = new JLabel(String.valueOf(east));
	JLabel c7 = new JLabel(" ");
	JLabel c8 = new JLabel(String.valueOf(south));
	JLabel c9 = new JLabel(" ");
	
	public Carre() {
	}
	
	public Carre(int x, int y, Color c, Piece p){
		this.north = p.getNorth(); this.weast = p.getWest(); this.east = p.getEast(); this.south = p.getSouth();this.titre = p.getNom();
		this.color = c;
		this.x = x; this.y = y;
		this.setPreferredSize(new Dimension(x, y));
		this.setBackground(c);
		JPanel grid = new JPanel(); this.setLayout(new GridLayout(3, 3, 50, 50));
		this.add(c1);  this.add(c2); this.add(c3); this.add(c4);this.add(c5);  this.add(c6); this.add(c7); this.add(c8);this.add(c9);
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public void setColor(Color c){
		this.color = c;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
