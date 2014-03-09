package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vues.Vue_Menu;
import nineSquarePuzzle.NineSquarePuzzle;
import nineSquarePuzzle.Resolution;

public class ControleurMenu_Lancer implements ActionListener {

	NineSquarePuzzle puzzle;
	Resolution r;
	Vue_Menu menu;
	
	public ControleurMenu_Lancer(NineSquarePuzzle p, Vue_Menu menu){
		this.puzzle = p;
		this.menu = menu;
		this.r = puzzle.getResolution();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		puzzle.setAlgoLance(true);
		menu.getArreter().setEnabled(true);
		menu.getLancer().setEnabled(false);
		r.run();
	}

	
}
