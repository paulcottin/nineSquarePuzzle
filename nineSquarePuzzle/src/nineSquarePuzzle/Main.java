package nineSquarePuzzle;

public class Main {

	public static void main(String[] args){
		int[] tab = {1,-2,-3,4};
		Piece p = new Piece("A", tab);
		System.out.println(p.toString());
		Instrumentation i = new Instrumentation();
		i.start();
		p.tourne(1);
		i.stop();
		System.out.println(p.toString());
		System.out.println(i.afficheInfos());
	}
}
