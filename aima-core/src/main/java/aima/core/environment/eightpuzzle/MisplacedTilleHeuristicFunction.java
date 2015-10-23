package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;

/**
 * @author Ravi Mohan
 * 
 */
public class MisplacedTilleHeuristicFunction implements HeuristicFunction {
    	Object finalState = new EightPuzzleBoard(new int[]{0,1,2,3,4,5,6,7,8});
	public double h(Object actualState) {
		EightPuzzleBoard actualBoard = (EightPuzzleBoard) actualState;
		EightPuzzleBoard finalBoard = (EightPuzzleBoard) finalState;
		return getNumberOfMisplacedTiles(actualBoard, finalBoard);
	}
	
	public void setFinalBoard(Object finalBoard){
	    this.finalState = finalBoard;
	}
	
	public Object getFinalBoard(){
	    return finalState;
	}

	private int getNumberOfMisplacedTiles(EightPuzzleBoard actualBoard, EightPuzzleBoard finalBoard) {
		int numberOfMisplacedTiles = 0;
		if (!(actualBoard.getLocationOf(0).equals(finalBoard.getLocationOf(0)))) {
			numberOfMisplacedTiles++;
		}
		if (!(actualBoard.getLocationOf(1).equals(finalBoard.getLocationOf(1)))) {
			numberOfMisplacedTiles++;
		}
		if (!(actualBoard.getLocationOf(2).equals(finalBoard.getLocationOf(2)))) {
			numberOfMisplacedTiles++;
		}
		if (!(actualBoard.getLocationOf(3).equals(finalBoard.getLocationOf(3)))) {
			numberOfMisplacedTiles++;
		}
		if (!(actualBoard.getLocationOf(4).equals(finalBoard.getLocationOf(4)))) {
			numberOfMisplacedTiles++;
		}
		if (!(actualBoard.getLocationOf(5).equals(finalBoard.getLocationOf(5)))) {
			numberOfMisplacedTiles++;
		}
		if (!(actualBoard.getLocationOf(6).equals(finalBoard.getLocationOf(6)))) {
			numberOfMisplacedTiles++;
		}
		if (!(actualBoard.getLocationOf(7).equals(finalBoard.getLocationOf(7)))) {
			numberOfMisplacedTiles++;
		}
		if (!(actualBoard.getLocationOf(8).equals(finalBoard.getLocationOf(8)))) {
			numberOfMisplacedTiles++;
		}
		// Subtract the gap position from the # of misplaced tiles
		// as its not actually a tile (see issue 73).
		if (numberOfMisplacedTiles > 0) {
			numberOfMisplacedTiles--;
		}
		return numberOfMisplacedTiles;
	}
}