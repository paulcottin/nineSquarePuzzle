package nineSquarePuzzle;

import graphique.Fenetre;

public class Main {
	
	public static String path = "data4.txt";

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
		
		Fenetre fen = new Fenetre();
//		System.out.println(fen.getPuzzle().getBoard().toString());
//		fen.getPuzzle().resoudre(0);
//		Board b = new Board(Main.path);
//		b.resetBoard();
//		Board c = new Board(Main.path);
//		c.resetBoard();
		
//		Piece p = b.getPool().getPool().get(3);
//		Piece q = b.getPool().getPool().get(4);
//		Piece r = c.getPool().getPool().get(3);
//		Piece s = c.getPool().getPool().get(8);
//		Piece t = c.getPool().getPool().get(6);
		
//		b.positionner(p, 0);
//		b.positionner(q, 0);
		
//		fen.getPuzzle().getBoard().resetBoard();
//		
//		System.out.println("size of b.getpositions() : "+fen.getPuzzle().getBoard().getPositions().size());
//		for (int i = 0; i < fen.getPuzzle().getBoard().getPositions().size(); i++) {
//			fen.getPuzzle().getBoard().positionner(new Piece(), i);
//		}
//		
//		fen.getPuzzle().affichePool();
		
//		c.positionner(r, 0);
//		c.positionner(s, 1);
//		c.positionner(t, 2);
		
//		fen.getPuzzle().getSolutions().add(b);
//		fen.getPuzzle().getSolutions().add(c);
//		System.out.println(fen.getPuzzle().getSolutions().get(0).getPositions().get(0).toString());
		
//		fen.getPuzzle().chargeBoard(fen.getPuzzle().getSolutions().get(1));
//		fen.getPuzzle().refresh();
		
//		fen.getPuzzle().getBoard().positionner(p, 0);
//		fen.getPuzzle().getBoard().positionner(q, Board.DROITE_HAUT);
//		fen.getPuzzle().getBoard().positionner(r, Board.GAUCHE_BAS);
//		fen.getPuzzle().getBoard().positionner(s, Board.DROITE);
//		fen.getPuzzle().getBoard().positionner(t, Board.CENTRE_BAS);
		
//		System.out.println(fen.getPuzzle().getSolutions().contains(b));
//		System.out.println(fen.getPuzzle().getSolutions().indexOf(b));
//		fen.getPuzzle().chargeBoard(fen.getPuzzle().getSolutions().get(0));
//		fen.getPuzzle().refresh();
		
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
