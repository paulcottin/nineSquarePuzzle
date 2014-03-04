package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nineSquarePuzzle.NineSquarePuzzle;

public class ControleurMenu_Ouvrir implements ActionListener {

	NineSquarePuzzle puzzle;
	public ControleurMenu_Ouvrir(NineSquarePuzzle puzzle) {
		this.puzzle = puzzle;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		puzzle.ouvrir();
	}

}
