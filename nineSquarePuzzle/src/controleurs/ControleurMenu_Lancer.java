package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nineSquarePuzzle.NineSquarePuzzle;
import nineSquarePuzzle.Resolution;

public class ControleurMenu_Lancer implements ActionListener {

	NineSquarePuzzle puzzle;
	Resolution r;
	
	public ControleurMenu_Lancer(NineSquarePuzzle p){
		this.puzzle = p;
		this.r = p.getResolution();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		puzzle.setAlgoLance(true);
		r.run();
	}

	
}
