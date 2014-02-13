package graphique;

import java.awt.Point;

import nineSquarePuzzle.Instrumentation;
import nineSquarePuzzle.NineSquarePuzzle;
import nineSquarePuzzle.Pool;

public class Test {

	public static void main(String[] args){
		Instrumentation i = new Instrumentation();
		Pool pool = new Pool("data1.txt");
		Fenetre fen = new Fenetre(pool, i);
		i.start();
		pool.getPool().get(0).tourne(1);
		i.stop();
		fen.setInstrumentation();
	}
}
