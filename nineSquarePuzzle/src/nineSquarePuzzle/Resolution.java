package nineSquarePuzzle;

public class Resolution extends Thread{

	NineSquarePuzzle p;
	
	public Resolution(NineSquarePuzzle p) {
		super();
		this.p = p;
	}
	
	@Override
	public void run() {
		try {
			p.resoudre(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
