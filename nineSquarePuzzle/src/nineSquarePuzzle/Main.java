package nineSquarePuzzle;

import graphique.Fenetre;

public class Main {

	public static void main(String[] args){
//		Main sans GUI (interface graphique)
//		int[] tab = {1,-2,-3,4};
//		Instrumentation i = new Instrumentation();
//		Piece p = new Piece("A", tab);
//		i.start();
//		System.out.println(p.toString());
//		p.tourne(1);
//		System.out.println(p.toString());
//		i.stop();
//		System.out.println(i.afficheInfos());
		
//		Avec GUI
		Instrumentation i = new Instrumentation();
		Pool pool = new Pool("data1.txt");
		Fenetre fen = new Fenetre(pool, i);
		i.start();
		pool.getPool().get(0).tourne(1);
		i.stop();
		fen.setInstrumentation();
	}
}
