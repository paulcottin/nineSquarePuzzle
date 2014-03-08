package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nineSquarePuzzle.NineSquarePuzzle;

public class ControleurMenu_PuzzleParfait implements ActionListener {

	NineSquarePuzzle puzzle;
	
	public ControleurMenu_PuzzleParfait(NineSquarePuzzle p) {
		this.puzzle = p;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		puzzle.isPerfect();
	}

}
