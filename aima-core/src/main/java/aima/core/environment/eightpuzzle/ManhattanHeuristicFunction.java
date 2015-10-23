package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * 
 */
public class ManhattanHeuristicFunction implements HeuristicFunction {
    
    	Object finalState = new EightPuzzleBoard(new int[]{0,1,2,3,4,5,6,7,8});

	public double h(Object initialState) {
		EightPuzzleBoard initialBoard = (EightPuzzleBoard) initialState;
		EightPuzzleBoard finalBoard = (EightPuzzleBoard) finalState;
		int retVal = 0;
		for (int i = 1; i < 9; i++) {
			XYLocation locInitial = initialBoard.getLocationOf(i);
			XYLocation locFinal = finalBoard.getLocationOf(i);
			retVal += evaluateManhattanDistanceOf(locInitial, locFinal);
		}
		return retVal;

	}
	
	public void setFinalBoard(Object finalBoard){
	    this.finalState = finalBoard;
	}
	
	public Object getFinalBoard(){
	    return finalState;
	}

	public int evaluateManhattanDistanceOf(XYLocation locInitial, XYLocation locFinal) {
		int retVal = -1;
		int xposInitial = locInitial.getXCoOrdinate();
		int yposInitial = locInitial.getYCoOrdinate();
		
		int xposFinal = locFinal.getXCoOrdinate();
		int yposFinal = locFinal.getYCoOrdinate();
		
		retVal = Math.abs(xposInitial - xposFinal) + Math.abs(yposInitial - yposFinal);

		return retVal;
	}
}