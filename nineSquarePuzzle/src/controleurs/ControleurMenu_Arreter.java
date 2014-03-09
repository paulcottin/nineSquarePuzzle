package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vues.Vue_Menu;
import nineSquarePuzzle.NineSquarePuzzle;
import nineSquarePuzzle.Resolution;

public class ControleurMenu_Arreter implements ActionListener {

	NineSquarePuzzle puzzle;
	Resolution r;
	Vue_Menu menu;
	
	public ControleurMenu_Arreter(NineSquarePuzzle p, Vue_Menu menu) {
		this.puzzle = p;
		this.r = p.getResolution();
		this.menu = menu;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		menu.getArreter().setEnabled(false);
		menu.getLancer().setEnabled(true);
		r.interrupt();
		this.puzzle.setAlgoLance(false);
	}

}
