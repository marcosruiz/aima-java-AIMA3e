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
    static int[][] tableOfGeneratedNodes = new int[22][4];
    static int[] numIter = new int[22];
    static double[][] tableOfRamificationFactor = new double[22][4];
    static int[] tableOfDepths = new int[22];
    static final int NUM_ITER = 100;

    /**
     * Se realizan NUM_ITER pruebas por cada profundidad indicada en
     * tableOfDepths y se muestran por pantalla los resultados. Dichos
     * resultados incluyen la media de los nodos generados y la media del factor
     * de ramificaci�n.
     * 
     * @param args
     */
    public static void main(String[] args) {

	// Rellenamos la tabla de profundidades
	for (int i = 0; i < tableOfDepths.length; i++) {
	    tableOfDepths[i] = i + 2;
	}

	// Inicializamos variables
	int depth;
	EightPuzzleBoard initialBoard;
	EightPuzzleBoard finalBoard;
	boolean good; // indica si profundidad teorica == profundidad real
	MisplacedTilleHeuristicFunction mthf;
	ManhattanHeuristicFunction mhf;

	for (int i = 0; i < tableOfDepths.length; i++) {
	    // Iteramos NUM_ITER pruebas
	    while (numIter[i] < NUM_ITER) {
		depth = tableOfDepths[i];
		// Generamos el tablero inicial y el tablero soluci�n a la
		// profundidad deseada
		initialBoard = GenerateInitialEightPuzzleBoard.randomIni();
		finalBoard = GenerateInitialEightPuzzleBoard.random(depth,
			initialBoard);
		// Prueba BFS
		good = eightPuzzleDemo(new BreadthFirstSearch(), initialBoard,
			finalBoard, i, 0);
		/*
		 * Si la profundidad teorica == profundiad real realizamos las
		 * dem�s b�squedas
		 */
		if (good) {
		    // Prueba IDS
		    if (tableOfDepths[i] <= 10) {
			eightPuzzleDemo(new IterativeDeepeningSearch(),
				initialBoard, finalBoard, i, 1);
		    }
		    // Prueba A* con Heuristica MisplacedTilleHeuristicFunction
		    mthf = new MisplacedTilleHeuristicFunction();
		    mthf.setFinalBoard(finalBoard);
		    eightPuzzleDemo(new AStarSearch(new GraphSearch(), mthf),
			    initialBoard, finalBoard, i, 2);
		    // Prueba A* con Heuristica ManhattanHeuristicFunction
		    mhf = new ManhattanHeuristicFunction();
		    mhf.setFinalBoard(finalBoard);
		    eightPuzzleDemo(new AStarSearch(new GraphSearch(), mhf),
			    initialBoard, finalBoard, i, 3);
		    // Sumamos el n�mero de pruebas
		    numIter[i]++;
		}
	    }
	    // Descomentar si queremos saber a que profundiad estamos
	    // System.out.printf("%d,",i);
	}
	// Imprimimos resultados
	String result = printResults(tableOfDepths, tableOfGeneratedNodes,
		tableOfRamificationFactor);
	System.out.println(result);

    }

    /**
     * M�todo que se encarga de realizar la b�squeda indicada sumando a lo que
     * ya hay en la fila row y columna column indicadas tanto en
     * tableOfGeneratedNodes como en tableOfRamificationFactor los nodos
     * generados y el factor de ramificaci�n respectivamente. Devuelve true si y
     * solo si tableOfDepths[row] es igual a la profundidad a la que se haya la
     * solucion (pathCost)
     * 
     * @param search
     *            indica el algoritmo de b�squeda a usar
     * @param initialState
     *            indica el estado inicial del tablero
     * @param finalState
     *            indica el estado objetivo del tablero
     * @param row
     *            indica la fila d�nde guardar los datos tanto en
     *            tableOfGeneratedNodes como en tableOfRamificationFactor
     * @param column
     *            indica la columna d�nde guardar los datos
     * @return
     */
    private static boolean eightPuzzleDemo(Search search, Object initialState,
	    Object finalState, int row, int column) {
	boolean good = false;
	try {
	    double depth = tableOfDepths[row];
	    int generatedNodes;
	    double ramificationFactor;
	    EightPuzzleGoalTest epgt = new EightPuzzleGoalTest();
	    epgt.setGoalState((EightPuzzleBoard) finalState);
	    Problem problem = new Problem(initialState,
		    EightPuzzleFunctionFactory.getActionsFunction(),
		    EightPuzzleFunctionFactory.getResultFunction(), epgt);
	    SearchAgent agent = new SearchAgent(problem, search);
	    // Leemos nodos generados
	    String generatedNodesString = print(agent.getInstrumentation(),
		    "nodesGenerated");
	    // Comprobamos que pathCost == la profundidad esperada de la
	    // soluci�n
	    String pathCostString = print(agent.getInstrumentation(),
		    "pathCost");
	    double pathCost = Double.parseDouble(pathCostString);
	    good = (pathCost == depth);
	    if (good) {
		generatedNodes = Integer.parseInt(generatedNodesString);
		// Calculamos el factor de ramificaci�n a partir de los nodos
		// generados
		Biseccion biseccion = new Biseccion();
		biseccion.setGeneratedNodes(generatedNodes);
		biseccion.setDepth(tableOfDepths[row]);
		ramificationFactor = biseccion.metodoDeBiseccion(1.0001, 4.0,
			Math.pow(Math.E, -10));
		// Grabamos los resultados en results, sumando a lo que ya hay.
		tableOfGeneratedNodes[row][column] += generatedNodes;
		tableOfRamificationFactor[row][column] += ramificationFactor;
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return good;
    }

    /**
     * Se muestra por salida estandar una tabla con las medias de los resultados
     * obtenidos.
     * 
     * @param tableOfDepths
     * @param tableOfGeneratedNodes
     * @param tableOfRamificationFactor
     * @return
     */
    private static String printResults(int[] tableOfDepths,
	    int[][] tableOfGeneratedNodes,
	    double[][] tableOfRamificationFactor) {
	String toReturn = "";
	toReturn += "-------------------------------------------------------------------------------------------\n";
	toReturn += "||    ||      Nodos Generados                   ||                  b*                   ||\n";
	toReturn += "-------------------------------------------------------------------------------------------\n";
	toReturn += "||   d||    BFS   |    IDS  | A*h(1)  | A*h(2)  ||    BFS  |    IDS  | A*h(1)  | A*h(2)  ||\n";
	toReturn += "-------------------------------------------------------------------------------------------\n";
	toReturn += "-------------------------------------------------------------------------------------------\n";
	for (int i = 0; i < tableOfDepths.length; i++) {
	    toReturn += String.format(
		    "||%4d||%7d   |%7d  |%7d  |%7d  ||%7.2f  |%7.2f  |%7.2f  |%7.2f  ||\n",
		    tableOfDepths[i], tableOfGeneratedNodes[i][0] / numIter[i],
		    tableOfGeneratedNodes[i][1] / numIter[i],
		    tableOfGeneratedNodes[i][2] / numIter[i],
		    tableOfGeneratedNodes[i][3] / numIter[i],
		    tableOfRamificationFactor[i][0] / numIter[i],
		    tableOfRamificationFactor[i][1] / numIter[i],
		    tableOfRamificationFactor[i][2] / numIter[i],
		    tableOfRamificationFactor[i][3] / numIter[i]);
	}
	toReturn += "-------------------------------------------------------------------------------------------\n";
	return toReturn;
    }

    /**
     * Devuelve un string con el valor de la clave key
     * 
     * @param properties
     * @param key
     * @return
     */
    private static String print(Properties properties, String key) {
	Iterator<Object> keys = properties.keySet().iterator();
	String property = properties.getProperty(key);
	return property;
    }

}