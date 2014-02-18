package nineSquarePuzzle;

import graphique.Fenetre;

public class Main {
	
	public static String path = "data1.txt";

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
		NineSquarePuzzle puzzle = new NineSquarePuzzle(path);
		Fenetre fen = new Fenetre(puzzle.getBoard(), i);
//		puzzle.getBoard().positionner(puzzle.getBoard().getPool().getPool().get(0), Board.GAUCHE_HAUT);
//		fen.afficheBoard();
		puzzle.getBoard().positionner(puzzle.getBoard().getPool().getPool().get(1), Board.CENTRE_HAUT);
		fen.afficheBoard();
//		puzzle.getBoard().positionner(puzzle.getBoard().getPool().getPool().get(1), Board.DROITE_HAUT);
//		fen.afficheBoard();
		i.start();
		i.stop();
		fen.setInstrumentation();
	}
}
