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
		Piece p = puzzle.getBoard().getPool().getPool().get(0);
		Piece q = puzzle.getBoard().getPool().getPool().get(5);
		Piece r = puzzle.getBoard().getPool().getPool().get(3);
		Piece s = puzzle.getBoard().getPool().getPool().get(8);
		Piece t = puzzle.getBoard().getPool().getPool().get(6);
		i.start();
		puzzle.getBoard().positionner(q, Board.CENTRE);
		puzzle.getBoard().positionner(p, Board.GAUCHE);
		puzzle.getBoard().positionner(r, Board.CENTRE_HAUT);
		puzzle.getBoard().positionner(s, Board.DROITE);
		puzzle.getBoard().positionner(t, Board.CENTRE_BAS);
		fen.refreshBoard();
		System.out.println(p.getNom()+" est à droite de "+q.getNom()+" : "+puzzle.isADroite(p, q));
		System.out.println(q.getNom()+" est en dessous de "+r.getNom()+" : "+puzzle.isUnder(q, r));
		System.out.println(s.getNom()+" est à droite de "+p.getNom()+" : "+puzzle.isADroite(s, p));
		System.out.println(r.getNom()+" est en dessous de "+t.getNom()+" : "+puzzle.isUnder(r, t));
		System.out.println(q.getNom()+" est au dessus de "+r.getNom()+" : "+puzzle.isUpper(q, r));
		i.stop();
		fen.setInstrumentation();
	}
}
