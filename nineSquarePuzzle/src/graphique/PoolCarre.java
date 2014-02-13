package graphique;

import java.awt.Color;

import nineSquarePuzzle.Pool;

public class PoolCarre {
	
	Color[] couleursCarres = {Color.red, Color.blue, Color.green, Color.gray, Color.pink, Color.cyan, Color.orange, Color.white, Color.black};
	Carre[] carresTab = new Carre[9];
	
	public PoolCarre(Pool pool, int fenetreX, int fenetreY){
//		remplissage d'un tableau de carrés dans l'ordre d'apparition dans le fichier texte
		for (int i = 0; i < pool.getPool().size() ; i++) {
			carresTab[i] = new Carre((fenetreX-100)/3, (fenetreY-100)/3, couleursCarres[i], pool.getPool().get(i));
		}
	}

	public Carre[] getCarresTab() {
		return carresTab;
	}
	
	public Carre getCarresTab(int i) {
		if (i<carresTab.length) {
			return carresTab[i];
		}else {
			System.out.println("dépassement de carresTab");
			return null;
		}
		
	}

	public void setCarresTab(Carre[] carresTab) {
		this.carresTab = carresTab;
	}
	
	

}
