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
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.util.math.Biseccion;

/**
 * @author Ravi Mohan
 * 
 */

public class EightPuzzleDemoPrac2 {
    static double[][] tableOfGeneratedNodes = new double[22][4];
    static double[][] tableOfRamificationFactor = new double[22][4];
    static int[] tableOfDepths = new int[22];

    public static void main(String[] args) {
	int NUM_ITER = 100;
	// Rellenamos la tabla de profundidades
	for (int i = 0; i < tableOfDepths.length; i++) {
	    tableOfDepths[i] = i + 2;
	}

	for (int i = 0; i < 6; i++) {
	    for (int j = 0; j < NUM_ITER; j++) {
		int depth = tableOfDepths[i];
		EightPuzzleBoard initialBoard = GenerateInitialEightPuzzleBoard
			.randomIni();
		EightPuzzleBoard finalBoard = GenerateInitialEightPuzzleBoard
			.random(depth, initialBoard);

		eightPuzzleDemo(new BreadthFirstSearch(), initialBoard,
			finalBoard, i, 0);
		eightPuzzleDemo(new IterativeDeepeningSearch(), initialBoard,
			finalBoard, i, 1);

		MisplacedTilleHeuristicFunction mthf = new MisplacedTilleHeuristicFunction();
		mthf.setFinalBoard(finalBoard);
		eightPuzzleDemo(new AStarSearch(new GraphSearch(), mthf),
			initialBoard, finalBoard, i, 2);

		ManhattanHeuristicFunction mhf = new ManhattanHeuristicFunction();
		mhf.setFinalBoard(finalBoard);
		eightPuzzleDemo(new AStarSearch(new GraphSearch(), mhf),
			initialBoard, finalBoard, i, 3);

	    }
	}
	String result = printResults(tableOfDepths, tableOfGeneratedNodes,
		tableOfRamificationFactor);
	System.out.println(result);

    }

    private static void eightPuzzleDemo(Search search, Object initialState,
	    Object finalState, int row, int column) {
	try {
	    int generatedNodes;
	    double ramificationFactor;
	    EightPuzzleGoalTest epgt = new EightPuzzleGoalTest();
	    epgt.setGoalState((EightPuzzleBoard) finalState);
	    Problem problem = new Problem(initialState,
		    EightPuzzleFunctionFactory.getActionsFunction(),
		    EightPuzzleFunctionFactory.getResultFunction(), epgt);
	    SearchAgent agent = new SearchAgent(problem, search);

	    String generatedNodesString = print(agent.getInstrumentation(),
		    "nodesGenerated");
	    generatedNodes = Integer.parseInt(generatedNodesString);
	    Biseccion biseccion = new Biseccion();
	    biseccion.setGeneratedNodes(generatedNodes);
	    ramificationFactor = biseccion.metodoDeBiseccion(1.0001, 4.0,
		    0.001);
	    // Grabamos resultado en results
	    double generatedNodesDouble = generatedNodes;
	    generatedNodesDouble = generatedNodesDouble / 100;
	    tableOfGeneratedNodes[row][column] += generatedNodes;
	    tableOfRamificationFactor[row][column] += ramificationFactor / 100;

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    private static String printResults(int[] tableOfDepths,
	    double[][] tableOfGeneratedNodes,
	    double[][] tableOfRamificationFactor) {
	String toReturn = "";
	toReturn += "-------------------------------------------------------------------------------------------\n";
	toReturn += "||    ||      Nodos Generados                   ||                  b*                   ||\n";
	toReturn += "-------------------------------------------------------------------------------------------\n";
	toReturn += "||   d||    BFS   |    IDS  | A*h(1)  | A*h(2)  ||    BFS  |    IDS  | A*h(1)  | A*h(2)  ||\n";
	toReturn += "-------------------------------------------------------------------------------------------\n";
	toReturn += "-------------------------------------------------------------------------------------------\n";
	for (int i = 0; i < 6; i++) {
	    toReturn += String.format(
		    "||%4d||%7d   |%7d  |%7d  |%7d  ||%7.2f  |%7.2f  |%7.2f  |%7.2f  ||\n",
		    tableOfDepths[i], tableOfGeneratedNodes[i][0],
		    tableOfGeneratedNodes[i][1], tableOfGeneratedNodes[i][2],
		    tableOfGeneratedNodes[i][3],
		    tableOfRamificationFactor[i][0],
		    tableOfRamificationFactor[i][1],
		    tableOfRamificationFactor[i][2],
		    tableOfRamificationFactor[i][3]);
	}
	toReturn += "-------------------------------------------------------------------------------------------\n";
	return toReturn;
    }

    private static String print(Properties properties, String key) {
	Iterator<Object> keys = properties.keySet().iterator();
	String property = properties.getProperty(key);
	return property;
    }

    private static void printActions(List<Action> actions) {
	for (int i = 0; i < actions.size(); i++) {
	    String action = actions.get(i).toString();
	    System.out.println(action);
	}
    }

}