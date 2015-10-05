package aima.core.environment.fichas;

import aima.core.search.framework.GoalTest;

/**
 * @author Ravi Mohan
 * 
 */
public class FichasGoalTest implements GoalTest {
	static FichasBoard goal = new FichasBoard(new int[] { 2,2,2,0,1,1,1 });

	public boolean isGoalState(Object state) {
		FichasBoard board = (FichasBoard) state;
		return board.equals(goal);
	}
	
	public static Object getGoalState() {
		return goal;
	}
}