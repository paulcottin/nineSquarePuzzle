package vues;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nineSquarePuzzle.NineSquarePuzzle;

public class ControleurMenu_Parfait implements ActionListener {

	NineSquarePuzzle puzzle;
	
	public ControleurMenu_Parfait(NineSquarePuzzle p) {
		this.puzzle = p;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		puzzle.isPiecePerfect();
	}

}
