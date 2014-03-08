package vues;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nineSquarePuzzle.NineSquarePuzzle;

public class ControleurMenu_Arreter implements ActionListener {

	NineSquarePuzzle puzzle;
	
	public ControleurMenu_Arreter(NineSquarePuzzle p) {
		this.puzzle = p;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.puzzle.arreterAlgo();
	}

}
