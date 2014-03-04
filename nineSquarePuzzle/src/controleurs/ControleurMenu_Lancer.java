package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nineSquarePuzzle.NineSquarePuzzle;

public class ControleurMenu_Lancer implements ActionListener {

	NineSquarePuzzle puzzle;
	
	public ControleurMenu_Lancer(NineSquarePuzzle p){
		this.puzzle = p;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try {
			puzzle.resoudre(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("erreur dans ControleurMenu_Lancer()");
			e.printStackTrace();
		}
	}

	
}
