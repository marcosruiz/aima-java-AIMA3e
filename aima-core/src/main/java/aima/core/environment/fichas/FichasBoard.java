package aima.core.environment.fichas;


import java.util.Arrays;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;

/**
 * @author Ravi Mohan
 * @author R. Lunde
 */
public class FichasBoard {

	public static Action ONE_LEFT = new DynamicAction("One to left");

	public static Action ONE_RIGHT = new DynamicAction("One to right");

	public static Action TWO_LEFT = new DynamicAction("Two to left");

	public static Action TWO_RIGHT = new DynamicAction("Two to right");
	
	public static Action THREE_LEFT = new DynamicAction("Three to left");

	public static Action THREE_RIGHT = new DynamicAction("Three to right");

	private int[] state;

	//
	// PUBLIC METHODS
	//

	public FichasBoard() {
		state = new int[] { 1,1,1,0,2,2,2 };
	}

	public FichasBoard(int[] state) {
		this.state = new int[state.length];
		System.arraycopy(state, 0, this.state, 0, state.length);
	}

	public FichasBoard(FichasBoard copyBoard) {
		this(copyBoard.getState());
	}

	public int[] getState() {
		return state;
	}

	public int getValueAt(int loc) {
		return state[loc];
	}

	public void moveGapRight(int i) {
		int gapPos = getGapPosition();
		if (gapPos <= (6-i)) {
			int valueOnRight = getValueAt(gapPos+i);
			setValue(gapPos, valueOnRight);
			setValue(gapPos+i, 0);
		}

	}

	public void moveGapLeft(int i) {
		int gapPos = getGapPosition();
		if (gapPos >= i) {
			int valueOnLeft = getValueAt(gapPos - i);
			setValue(gapPos,valueOnLeft);
			setValue(gapPos - i, 0);
		}

	}

	public boolean canMoveGap(Action where) {
		boolean retVal = true;
		int gapPos = getGapPosition();
		if (where.equals(ONE_LEFT))
			retVal = (gapPos > 0);
		else if (where.equals(ONE_RIGHT))
			retVal = (gapPos < 6);
		else if (where.equals(TWO_LEFT))
			retVal = (gapPos > 1);
		else if (where.equals(TWO_RIGHT))
			retVal = (gapPos < 5);
		else if (where.equals(THREE_LEFT))
			retVal = (gapPos > 2);
		else if (where.equals(THREE_RIGHT))
			retVal = (gapPos < 4);
		return retVal;
	}

	/*@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		FichasBoard aBoard = (FichasBoard) o;

		for (int i = 0; i < 6; i++) {
			if (this.getValueAt(i) != aBoard.getValueAt(i)) {
				return false;
			}
		}
		return true;
	}*/

	@Override
	public String toString() {
		String retVal = state[0] + " " + state[1] + " " + state[2] + " "
				+ state[3] + " " + state[4] + " " + state[5] + " "
				+ state[6];
		return retVal;
	}

	//
	// PRIVATE METHODS
	//

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(state);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FichasBoard other = (FichasBoard) obj;
		if (!Arrays.equals(state, other.state))
			return false;
		return true;
	}


	private int getGapPosition() {
		int valGap = 0;
		int retVal = -1;
		for (int i = 0; i < 7; i++) {
			if (state[i] == valGap) {
				retVal = i;
			}
		}
		return retVal;
	}

	private void setValue(int x, int val) {
		state[x] = val;

	}
}