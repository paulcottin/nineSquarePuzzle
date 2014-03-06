package nineSquarePuzzle;


public class InstanceBoard {

	String[] positions = new String[9];
	int[] orientations = new int[9];
	
	public InstanceBoard(Board board){
		for (int i = 0; i < board.getPositions().size(); i++) {
			this.positions[i] = board.getPositions().get(i).getNom();
			this.orientations[i] = board.getPositions().get(i).getOrientation();
		}
	}
	
	public InstanceBoard(Solution sol){
		for (int i = 0; i < sol.getBoard().size(); i++) {
			this.positions[i] = sol.getBoard().get(i).getNom();
			this.orientations[i] = sol.getBoard().get(i).getOrientation();
		}
	}
	
	public boolean equals(InstanceBoard inst){
		for (int i = 0; i < positions.length; i++) {
			if(inst.getPositions()[i] != this.getPositions()[i] || inst.getOrientations()[i] != this.getOrientations()[i]){
				return false;
			}
		}
		return true;
	}
	
	public String toString(){
		String reponse = "instance : ";
		for (int i = 0; i < positions.length -1; i++) {
			reponse = reponse + positions[i]+" ("+orientations[i]+"), ";
		}
		reponse = reponse + positions[positions.length-1]+" ("+orientations[positions.length-1]+"), ";
		return reponse;
	}

	public String[] getPositions() {
		return positions;
	}

	public void setPositions(String[] positions) {
		this.positions = positions;
	}

	public int[] getOrientations() {
		return orientations;
	}

	public void setOrientations(int[] orientations) {
		this.orientations = orientations;
	}
}
