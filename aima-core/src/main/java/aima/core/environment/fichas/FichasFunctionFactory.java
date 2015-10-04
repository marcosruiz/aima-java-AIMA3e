package aima.core.environment.fichas;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
public class FichasFunctionFactory {
	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;

	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new EPActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new EPResultFunction();
		}
		return _resultFunction;
	}

	private static class EPActionsFunction implements ActionsFunction {
		public Set<Action> actions(Object state) {
			FichasBoard board = (FichasBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMoveGap(FichasBoard.ONE_LEFT)) {
				actions.add(FichasBoard.ONE_LEFT);
			}
			if (board.canMoveGap(FichasBoard.ONE_RIGHT)) {
				actions.add(FichasBoard.ONE_RIGHT);
			}
			if (board.canMoveGap(FichasBoard.TWO_LEFT)) {
				actions.add(FichasBoard.TWO_LEFT);
			}
			if (board.canMoveGap(FichasBoard.TWO_RIGHT)) {
				actions.add(FichasBoard.TWO_RIGHT);
			}
			if (board.canMoveGap(FichasBoard.THREE_LEFT)) {
				actions.add(FichasBoard.THREE_LEFT);
			}
			if (board.canMoveGap(FichasBoard.THREE_RIGHT)) {
				actions.add(FichasBoard.THREE_RIGHT);
			}

			return actions;
		}
	}

	private static class EPResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			FichasBoard board = (FichasBoard) s;

			if (FichasBoard.ONE_LEFT.equals(a)
					&& board.canMoveGap(FichasBoard.ONE_LEFT)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapLeft(1);
				return newBoard;
			} else if (FichasBoard.ONE_RIGHT.equals(a)
					&& board.canMoveGap(FichasBoard.ONE_RIGHT)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapRight(1);
				return newBoard;
			} else if (FichasBoard.TWO_LEFT.equals(a)
					&& board.canMoveGap(FichasBoard.TWO_LEFT)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapLeft(2);
				return newBoard;
			} else if (FichasBoard.TWO_RIGHT.equals(a)
					&& board.canMoveGap(FichasBoard.TWO_RIGHT)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapRight(2);
				return newBoard;
			} else if (FichasBoard.THREE_LEFT.equals(a)
					&& board.canMoveGap(FichasBoard.THREE_LEFT)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapLeft(3);
				return newBoard;
			} else if (FichasBoard.THREE_RIGHT.equals(a)
					&& board.canMoveGap(FichasBoard.THREE_RIGHT)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapRight(3);
				return newBoard;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}