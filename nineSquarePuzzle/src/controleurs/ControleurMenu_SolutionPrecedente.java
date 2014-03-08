package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nineSquarePuzzle.NineSquarePuzzle;

public class ControleurMenu_SolutionPrecedente implements ActionListener {

	NineSquarePuzzle puzzle;
	
	public ControleurMenu_SolutionPrecedente(NineSquarePuzzle puzzle){
		this.puzzle = puzzle;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (!puzzle.getSolutions().isEmpty()) {
			this.puzzle.solutionSuivante();
		}
	}

}
