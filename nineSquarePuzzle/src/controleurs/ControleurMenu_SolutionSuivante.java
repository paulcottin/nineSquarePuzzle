package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nineSquarePuzzle.NineSquarePuzzle;

public class ControleurMenu_SolutionSuivante implements ActionListener{

	NineSquarePuzzle puzzle;
	
	public ControleurMenu_SolutionSuivante(NineSquarePuzzle p){
		this.puzzle = p;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (!puzzle.getSolutions().isEmpty()) {
			this.puzzle.solutionSuivante();
		}
	}
	
	
}
