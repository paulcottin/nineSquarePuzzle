package nineSquarePuzzle;

import graphique.Fenetre;

public class Main {
	
	public static String path = "data1.txt";

	public static void main(String[] args) throws InterruptedException{
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
//		Board board = new Board(path);
//		Instrumentation i = new Instrumentation();
//		fen = new Fenetre(board, i, main);
//		puzzle = new NineSquarePuzzle(board, fen, main);
		
		
//		Piece p = puzzle.getBoard().getPool().getPool().get(3);
//		Piece q = puzzle.getBoard().getPool().getPool().get(4);
//		Piece r = puzzle.getBoard().getPool().getPool().get(3);
//		Piece s = puzzle.getBoard().getPool().getPool().get(8);
//		Piece t = puzzle.getBoard().getPool().getPool().get(6);
		
		Fenetre fen = new Fenetre(false);
		
//		puzzle.getBoard().positionner(p, 0);
//		puzzle.getBoard().positionner(q, Board.DROITE_HAUT);
//		puzzle.getBoard().positionner(r, Board.GAUCHE_BAS);
//		puzzle.getBoard().positionner(s, Board.DROITE);
//		puzzle.getBoard().positionner(t, Board.CENTRE_BAS);
		
//		i.start();
//		puzzle.resoudre(0);
//		i.stop();
		
//		Test fonction chargeBoard(Board b)
//		Board b = new Board();
//		b.getPositions().add(p);
//		b.getPositions().add(q);
//		b.getPositions().add(r);
//		b.getPositions().add(s);
//		b.getPositions().add(t);
//		board.resetBoard();
//		puzzle.chargeBoard(b);
		
		
//		puzzle.affichePool();fen.refreshBoard();
//		Board b = puzzle.getBoard().clone();
//		System.out.println("taille de b.positions : "+b.getPositions().size());
//		for (Piece piece : b.getPositions()) {
//			System.out.println(piece.toString());
//		}
		
////		Vérification fonction tourne();
//		System.out.println(puzzle.getBoard().getPool().getPool().get(2));
//		puzzle.getBoard().getPool().getPool().get(2).tourne(3);
//		System.out.println(puzzle.getBoard().getPool().getPool().get(2));
//		p.tourne(1);
//		fen.refreshBoard();
		
		
//		System.out.println(t.getNom()+" a qquun a droite : "+puzzle.aPieceADroite(t));
//		i.start();
//		System.out.println(r.getNom()+" correspond à "+t.getNom()+" : "+puzzle.match(r,t));
//		i.stop();
//		System.out.println(p.getNom()+" correspond à "+q.getNom()+" : "+puzzle.match(p,q));
//		System.out.println(p.getNom()+" est à droite de "+q.getNom()+" : "+puzzle.isADroite(p, q));
//		System.out.println(q.getNom()+" est en dessous de "+r.getNom()+" : "+puzzle.isUnder(q, r));
//		System.out.println(s.getNom()+" est à droite de "+p.getNom()+" : "+puzzle.isADroite(s, p));
//		System.out.println(r.getNom()+" est en dessous de "+t.getNom()+" : "+puzzle.isUnder(r, t));
//		System.out.println(q.getNom()+" est au dessus de "+r.getNom()+" : "+puzzle.isUpper(q, r));
		
//		fen.setInstrumentation();

	}
}
