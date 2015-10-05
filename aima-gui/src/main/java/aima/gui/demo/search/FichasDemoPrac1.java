package aima.gui.demo.search;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.environment.fichas.FichasBoard;
import aima.core.environment.fichas.FichasFunctionFactory;
import aima.core.environment.fichas.FichasGoalTest;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;

/**
 * @author Ravi Mohan
 * 
 */

public class FichasDemoPrac1 {
	static FichasBoard startBoard = new FichasBoard(new int[] { 1,1,1,0,2,2,2 });

	public static void main(String[] args) {
		
		//fichasDemo(new BreadthFirstSearch(new TreeSearch()),startBoard,"breadth first search in Tree");
		
		fichasDemo(new BreadthFirstSearch(new GraphSearch()),startBoard,"breadth first search in Graph");
				
		//fichasDemo(new DepthFirstSearch(new TreeSearch()),startBoard,"depth first search in Tree");
				
		//fichasDemo(new DepthFirstSearch(new GraphSearch()),startBoard,"depth first search in Graph");
				
		//fichasDemo(new DepthLimitedSearch(15),startBoard,"recursive DLS ()");
				
		fichasDemo(new IterativeDeepeningSearch(),startBoard,"Iterative DLS");
	}
	private static void fichasDemo(Search search, Object initialState, String message) {
		System.out.println("\nFichasDemo "+message+" -->");
		try {
			Problem problem = new Problem(initialState, FichasFunctionFactory
					.getActionsFunction(), FichasFunctionFactory
					.getResultFunction(), new FichasGoalTest());
			long ini = System.currentTimeMillis();
			SearchAgent agent = new SearchAgent(problem, search);
			long fin = System.currentTimeMillis();
			//printActions(agent.getActions());
			executeActions(agent.getActions(), problem);
			printInstrumentation(agent.getInstrumentation());
			System.out.println("time : " + (fin-ini));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static void executeActions(List<Action> actions, Problem problem){
		
		Object initialState = problem.getInitialState();
		System.out.println(initialState.toString());
		ResultFunction rf = new FichasFunctionFactory().getResultFunction();
		for (int i = 0; i < actions.size(); i++) {
			Action a = actions.get(i);
			String action = a.toString();
			Object s = rf.result(initialState, a);
			System.out.println(s.toString());
			System.out.println(action);
		}
	}

	private static void printInstrumentation(Properties properties) {
		Iterator<Object> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}

	}

	private static void printActions(List<Action> actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}

}