package aima.core.environment.fichas;

import java.util.ArrayList;
import java.util.List;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.XLocation;

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

	public int getValueAt(XLocation loc) {
		return getValueAt(loc.getXCoOrdinate());
	}

	public XLocation getLocationOf(int val) {
		int absPos = getPositionOf(val);
		return new XLocation(absPos);
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
		if (gapPos >= (0+i)) {
			int valueOnLeft = getValueAt(gapPos - i);
			setValue(gapPos,valueOnLeft);
			setValue(gapPos - i, 0);
		}

	}

	public List<XLocation> getPositions() {
		ArrayList<XLocation> retVal = new ArrayList<XLocation>();
		for (int i = 0; i < 7; i++) {
			int absPos = getPositionOf(i);
			XLocation loc = new XLocation(absPos);
			retVal.add(loc);

		}
		return retVal;
	}

	public void setBoard(List<XLocation> locs) {
		int count = 0;
		for (int i = 0; i < locs.size(); i++) {
			XLocation loc = locs.get(i);
			this.setValue(loc.getXCoOrdinate(), count);
			count = count + 1;
		}
	}

	public boolean canMoveGap(Action where) {
		boolean retVal = true;
		int absPos = getPositionOf(0);
		if (where.equals(ONE_LEFT))
			retVal = (absPos > 0);
		else if (where.equals(ONE_RIGHT))
			retVal = (absPos < 6);
		else if (where.equals(TWO_RIGHT))
			retVal = (absPos > 1);
		else if (where.equals(TWO_RIGHT))
			retVal = (absPos < 5);
		else if (where.equals(THREE_RIGHT))
			retVal = (absPos > 2);
		else if (where.equals(THREE_RIGHT))
			retVal = (absPos < 4);
		return retVal;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		FichasBoard aBoard = (FichasBoard) o;

		for (int i = 0; i < 6; i++) {
			if (this.getPositionOf(i) != aBoard.getPositionOf(i)) {
				return false;
			}
		}
		return true;
	}

	/*@Override
	public int hashCode() {
		int result = 17;
		for (int i = 0; i < 7; i++) {
			int position = this.getPositionOf(i);
			result = 37 * result + position;
		}
		return result;
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

	/**
	 * Note: The graphic representation maps x values on row numbers (x-axis in
	 * vertical direction).
	 */
	/*private int getXCoord(int absPos) {
		return absPos;
	}*/


	private int getAbsPosition(int x) {
		return x;
	}

	private int getValueAt(int x) {
		// refactor this use either case or a div/mod soln
		return state[getAbsPosition(x)];
	}

	private int getGapPosition() {
		return getPositionOf(0);
	}

	private int getPositionOf(int val) {
		int retVal = -1;
		for (int i = 0; i < 7; i++) {
			if (state[i] == val) {
				retVal = i;
			}
		}
		return retVal;
	}

	private void setValue(int x, int val) {
		int absPos = getAbsPosition(x);
		state[absPos] = val;

	}
}