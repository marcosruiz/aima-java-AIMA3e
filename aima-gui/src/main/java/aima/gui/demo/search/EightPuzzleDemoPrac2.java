package aima.gui.demo.search;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;

/**
 * @author Ravi Mohan
 * 
 */

public class EightPuzzleDemoPrac2 {
	static EightPuzzleBoard boardWithThreeMoveSolution = new EightPuzzleBoard(
			new int[] { 1, 2, 5, 3, 4, 0, 6, 7, 8 });;

	static EightPuzzleBoard random1 = new EightPuzzleBoard(new int[] { 1, 4, 2,
			7, 5, 8, 3, 0, 6 });

	static EightPuzzleBoard extreme = new EightPuzzleBoard(new int[] { 0, 8, 7,
			6, 5, 4, 3, 2, 1 });

	public static void main(String[] args) {
		eightPuzzleDemo(new BreadthFirstSearch(new TreeSearch()),boardWithThreeMoveSolution,"breadth first search in Tree (3moves)");
		eightPuzzleDemo(new BreadthFirstSearch(new TreeSearch()),random1,"breadth first search in Tree (random1)");
		//eightPuzzleDemo(new BreadthFirstSearch(new TreeSearch()),extreme,"breadth first search in Tree (extreme)");
		
		eightPuzzleDemo(new BreadthFirstSearch(new GraphSearch()),boardWithThreeMoveSolution,"breadth first search in Graph (3moves)");
		eightPuzzleDemo(new BreadthFirstSearch(new GraphSearch()),random1,"breadth first search in Graph (random1)");
		eightPuzzleDemo(new BreadthFirstSearch(new GraphSearch()),extreme,"breadth first search in Graph (extreme)");
		
		//eightPuzzleDemo(new DepthFirstSearch(new TreeSearch()),boardWithThreeMoveSolution,"depth first search in Tree (3moves)");
		//eightPuzzleDemo(new DepthFirstSearch(new TreeSearch()),random1,"depth first search in Tree (random1)");
		//eightPuzzleDemo(new DepthFirstSearch(new TreeSearch()),extreme,"depth first search in Tree (extreme)");
		
		eightPuzzleDemo(new DepthFirstSearch(new GraphSearch()),boardWithThreeMoveSolution,"depth first search in Graph (3moves)");
		eightPuzzleDemo(new DepthFirstSearch(new GraphSearch()),random1,"depth first search in Graph (random1)");
		eightPuzzleDemo(new DepthFirstSearch(new GraphSearch()),extreme,"depth first search in Graph (extreme)");
		
		eightPuzzleDemo(new DepthLimitedSearch(3),boardWithThreeMoveSolution,"recursive DLS (3)(3moves)");
		eightPuzzleDemo(new DepthLimitedSearch(9),random1,"recursive DLS (9)(random1)");
		//eightPuzzleDemo(new DepthLimitedSearch(30),extreme,"recursive DLS (30)(extreme)");
		
		eightPuzzleDemo(new IterativeDeepeningSearch(),boardWithThreeMoveSolution,"Iterative DLS (3moves)");
		eightPuzzleDemo(new IterativeDeepeningSearch(),random1,"Iterative DLS (random1)");
		//eightPuzzleDemo(new IterativeDeepeningSearch(),extreme,"Iterative DLS (extreme)");
	}
	private static void eightPuzzleDemo(Search search, Object initialState, String message) {
		System.out.println("\nEightPuzzleDemo "+message+" -->");
		try {
			Problem problem = new Problem(initialState, EightPuzzleFunctionFactory
					.getActionsFunction(), EightPuzzleFunctionFactory
					.getResultFunction(), new EightPuzzleGoalTest());
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
		
		Object s = problem.getInitialState();
		System.out.println("Estado inicial:");
		System.out.println(s.toString());
		System.out.println("---");
		ResultFunction rf = new EightPuzzleFunctionFactory().getResultFunction();
		for (int i = 0; i < actions.size(); i++) {
			Action a = actions.get(i);
			String action = a.toString();
			s = rf.result(s, a);
			System.out.println(action);
			System.out.println(s.toString());
			System.out.println("---");
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