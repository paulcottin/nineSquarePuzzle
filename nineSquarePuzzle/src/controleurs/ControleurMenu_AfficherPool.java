package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import nineSquarePuzzle.NineSquarePuzzle;

public class ControleurMenu_AfficherPool implements ActionListener {

	NineSquarePuzzle puzzle;
	
	public ControleurMenu_AfficherPool(NineSquarePuzzle p){
		this.puzzle = p;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		puzzle.affichePool();
	}

}
