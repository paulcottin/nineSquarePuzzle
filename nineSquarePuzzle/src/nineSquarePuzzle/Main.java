package nineSquarePuzzle;

public class Main {

	public static void main(String[] args){
		int[] tab = {1,-2,-3,4};
		Instrumentation i = new Instrumentation();
		Piece p = new Piece("A", tab);
		i.start();
		System.out.println(p.toString());
		p.tourne(1);
		System.out.println(p.toString());
		i.stop();
		System.out.println(i.afficheInfos());
	}
}
