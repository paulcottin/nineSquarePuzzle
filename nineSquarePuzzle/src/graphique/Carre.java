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
	String[] labelTitres = new String[9]; 
	JLabel[] labels = new JLabel[9];
	Color color;
	int carreX, carreY;
	
//	Carre vide (sert pour afficher le Board au fur et à mesure)
	public Carre(int x, int y) {
		this.carreX = x; this.carreY = y;
		this.setPreferredSize(new Dimension(carreX, carreY));
		this.setVisible(true);
		this.setBackground(Color.gray);
	}
	
	public Carre(int x, int y, Color c, Piece p){
		this.north = p.getNorth(); this.weast = p.getWest(); this.east = p.getEast(); this.south = p.getSouth();this.titre = p.getNom();
		String[] tabValeurs = {String.valueOf(north), String.valueOf(weast), String.valueOf(east), String.valueOf(south)};
		this.color = c;
		this.carreX = x; this.carreY = y;
		this.setPreferredSize(new Dimension(carreX, carreY));
		this.setBackground(color);
		
		JPanel grid = new JPanel(); grid.setLayout(new GridLayout(3, 3, 50, 50));
//		Remplissage des tableaux pour l'affichage des valeurs dans les carrés
		int cpt = 0;
		for (int i = 0; i < labels.length; i++) {
			if (i%2 == 0 && i != 4) {
				labelTitres[i] = " ";
			}
			else if (i == 4) {
				labelTitres[i] = titre;
			}else {
				labelTitres[i] = tabValeurs[cpt++];
			}
			labels[i] = new JLabel(labelTitres[i]);
			grid.add(labels[i]);
		}
		
		grid.setOpaque(false);
		this.add(grid);
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public void setColor(Color c){
		this.color = c;
	}

	public int getCarreX() {
		return carreX;
	}

	public void setCarreX(int x) {
		this.carreX = x;
	}

	public int getCarreY() {
		return carreY;
	}

	public void setCarreY(int y) {
		this.carreY = y;
	}
}
