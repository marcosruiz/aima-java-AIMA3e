package aima.gui.demo.search;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import aima.core.agent.Action;
import aima.core.environment.nqueens.AttackingPairsHeuristic;
import aima.core.environment.nqueens.NQueensBoard;
import aima.core.environment.nqueens.NQueensFunctionFactory;
import aima.core.environment.nqueens.NQueensGoalTest;
import aima.core.search.framework.Problem;
import aima.core.search.framework.SearchAgent;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.HillClimbingSearch.SearchOutcome;
import aima.core.search.local.HillClimbingSearchFlat;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Marcos Ruiz Garcia 648045
 */
public class NQueensTP61 {
    static int pasosAcierto;
    static int pasosFallo;
    static int numAciertos;
    private static final int _boardSize = 8;

    public static void main(String[] args) {
	// EJERCICIO1
	newNQueensDemo();
	// EJERCICIO2
	newNQueensDemoFlat();
	// EJERICIO3
	randomRestartHillClimbing();
    }

    /*
     * EJERCICIO3
     */
    private static void randomRestartHillClimbing() {
	System.out.println();
	System.out.println("EJERCICIO3: REINICIAR HASTA ENCONTRAR LA SOLUCIÓN");
	/*
	 * Busqueda HillClimbing
	 */
	pasosAcierto = 0;
	pasosFallo = 0;
	numAciertos = 0;
	int intentos = 0;
	System.out.println();
	System.out.println("HILL CLIMBING SEARCH:");
	while (numAciertos == 0) {
	    nQueensHillClimbingSearch(true);
	    intentos++;
	}
	System.out.println("Porcentaje de exito: " + 100 / intentos + " %");
	System.out.println("Pasos en acierto: " + pasosAcierto);
	if (intentos > 1) {
	    System.out.println(
		    "Media de pasos en fallo: " + pasosFallo / (intentos - 1));
	} else {
	    System.out.println("No ha habido fallos");
	}
	/*
	 * Busqueda HillClimbingFlat
	 */
	pasosAcierto = 0;
	pasosFallo = 0;
	numAciertos = 0;
	intentos = 0;
	System.out.println();
	System.out.println("HILL CLIMBING SEARCH FLAT:");
	while (numAciertos == 0) {
	    nQueensHillClimbingSearchFlat(true);
	    intentos++;
	}
	System.out.println("Porcentaje de exito: " + 100 / intentos + " %");
	System.out.println("Pasos en acierto: " + pasosAcierto);
	if (intentos > 1) {
	    System.out.println(
		    "Media de pasos en fallo: " + pasosFallo / (intentos - 1));
	} else {
	    System.out.println("No ha habido fallos");
	}
    }

    /*
     * EJERCICIO2
     */
    private static void newNQueensDemoFlat() {
	pasosAcierto = 0;
	pasosFallo = 0;
	numAciertos = 0;
	for (int i = 0; i < 1000; i++) {
	    nQueensHillClimbingSearchFlat(false);
	}
	// System.out.println("pasosAcierto, pasosFallo, numAciertos: " +
	// pasosAcierto + " " + pasosFallo + " " + numAciertos);
	System.out.println();
	System.out.println("EJERCICIO2:");
	System.out.println("HILL CLIMBING SEARCH FLAT:");
	System.out.println("Porcentaje de exito: " + numAciertos / 10 + " %");
	System.out.println(
		"Media de pasos en acierto: " + pasosAcierto / numAciertos);
	System.out.println("Media de pasos en fallo: "
		+ pasosFallo / (1000 - numAciertos));
    }

    /*
     * EJERCICIO1
     */
    private static void newNQueensDemo() {
	pasosAcierto = 0;
	pasosFallo = 0;
	numAciertos = 0;
	for (int i = 0; i < 1000; i++) {
	    nQueensHillClimbingSearch(false);
	}
	// System.out.println("pasosAcierto, pasosFallo, numAciertos: " +
	// pasosAcierto + " " + pasosFallo + " " + numAciertos);
	System.out.println("EJERCICIO1:");
	System.out.println("HILL CLIMBING SEARCH:");
	System.out.println("Porcentaje de exito: " + numAciertos / 10 + " %");
	System.out.println(
		"Media de pasos en acierto: " + pasosAcierto / numAciertos);
	System.out.println("Media de pasos en fallo: "
		+ pasosFallo / (1000 - numAciertos));
    }

    /*
     * boolean ito indica si al encontrar una solucion muestra las estadisticas
     * correspondientes
     */
    private static void nQueensHillClimbingSearch(boolean ito) {
	// System.out.println("\nNQueensDemo HillClimbing -->");
	boolean solucionEncontrada = false;
	try {
	    // Creamos un tablero random con una reina por columna
	    NQueensBoard borigen = new NQueensBoard(_boardSize);
	    Random r = new Random();
	    int j;
	    XYLocation l;
	    for (int i = 0; i < _boardSize; i++) {
		j = r.nextInt(_boardSize - 1);
		l = new XYLocation(i, j);
		borigen.addQueenAt(l);
	    }
	    // Definimos el problema
	    Problem problem = new Problem(borigen,
		    NQueensFunctionFactory.getCActionsFunction(),
		    NQueensFunctionFactory.getResultFunction(),
		    new NQueensGoalTest());
	    HillClimbingSearch search = new HillClimbingSearch(
		    new AttackingPairsHeuristic());
	    SearchAgent agent = new SearchAgent(problem, search);
	    solucionEncontrada = (search
		    .getOutcome() == SearchOutcome.SOLUTION_FOUND);
	    if (solucionEncontrada) {
		if (ito) {
		    printActions(agent.getActions());
		    System.out.println("Search Outcome=" + search.getOutcome());
		    System.out.println(
			    "Final State=\n" + search.getLastSearchState());
		    printInstrumentation(agent.getInstrumentation());
		}
		pasosAcierto += Integer.parseInt(
			agent.getInstrumentation().getProperty("nodesExpanded"))
			- 1;
		numAciertos++;
	    } else {
		pasosFallo += Integer.parseInt(
			agent.getInstrumentation().getProperty("nodesExpanded"))
			- 1;
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /*
     * boolean ito indica si al encontrar una solucion muestra las estadisticas
     * correspondientes
     */
    private static void nQueensHillClimbingSearchFlat(boolean ito) {
	// System.out.println("\nNQueensDemo HillClimbing -->");
	boolean solucionEncontrada = false;
	try {
	    // Creamos un tablero random con una reina por columna
	    NQueensBoard borigen = new NQueensBoard(_boardSize);
	    Random r = new Random();
	    int j;
	    XYLocation l;
	    for (int i = 0; i < _boardSize; i++) {
		j = r.nextInt(_boardSize - 1);
		l = new XYLocation(i, j);
		borigen.addQueenAt(l);
	    }
	    // Definimos el problema
	    Problem problem = new Problem(borigen,
		    NQueensFunctionFactory.getCActionsFunction(),
		    NQueensFunctionFactory.getResultFunction(),
		    new NQueensGoalTest());
	    HillClimbingSearchFlat search = new HillClimbingSearchFlat(
		    new AttackingPairsHeuristic());
	    SearchAgent agent = new SearchAgent(problem, search);
	    solucionEncontrada = (search
		    .getOutcome() == HillClimbingSearchFlat.SearchOutcome.SOLUTION_FOUND);
	    if (solucionEncontrada) {
		if (ito) {
		    printActions(agent.getActions());
		    System.out.println("Search Outcome=" + search.getOutcome());
		    System.out.println(
			    "Final State=\n" + search.getLastSearchState());
		    printInstrumentation(agent.getInstrumentation());
		}
		pasosAcierto += Integer.parseInt(
			agent.getInstrumentation().getProperty("nodesExpanded"))
			- 1;
		numAciertos++;
	    } else {
		pasosFallo += Integer.parseInt(
			agent.getInstrumentation().getProperty("nodesExpanded"))
			- 1;
	    }

	} catch (Exception e) {
	    e.printStackTrace();
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
