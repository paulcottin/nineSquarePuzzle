package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nineSquarePuzzle.NineSquarePuzzle;

public class ControleurMenu_Tourner implements ActionListener{

	NineSquarePuzzle puzzle;
	
	public ControleurMenu_Tourner(NineSquarePuzzle p){
		this.puzzle = p;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
//		puzzle.tournerSolution(this.puzzle.getSolutions().get(puzzle.getSolutionCourante()).getBoard());
		puzzle.tournerSolutionGraphique(puzzle.getBoard().getPositions());
	}

}
