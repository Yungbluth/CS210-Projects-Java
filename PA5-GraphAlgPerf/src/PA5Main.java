import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * AUTHOR: Matthew Yungbluth
 * File: PA5Main.java
 * ASSIGNMENT: Programming Assignment 5 - GraphAlgPerf
 * COURSE: CSc 210; Section C; Spring 2019
 * PURPOSE: This program reads in a file of a graph and returns the least cost
 * solution with multiple algorithms
 * 
 * USAGE:
 * java PA5Main inputFile ALGORITHM
 * where inputFile is a file with a graph and ALGORITHM is the desired algorithm
 * to be used
 * 
 * Example input:
 * inputFile HEURISTIC
 * inputFile:
 * %%MatrixMarket matrix coordinate real general
 * 3 3 6
 * 1 2 1.0
 * 2 1 2.0
 * 1 3 3.0
 * 3 1 4.0
 * 2 3 5.0
 * 3 2 6.0
 * 
 * Output for above input:
 * cost = 10.0, visitOrder = [1, 2, 3]
 *
 */
public class PA5Main {
    public static void main(String[] args) throws FileNotFoundException {
        // args[0] = input file
        // args[1] = type of algorithm to run
        Scanner inputfile = new Scanner(new File(args[0]));
        DGraph graph = graphCreator(inputfile);
        switch (args[1]) {
        case "HEURISTIC":
            System.out.println(Heuristic(graph).toString(graph));
            break;
        case "BACKTRACK":
            Trip thisTrip = new Trip(graph.getNumNodes());
            Trip minTrip = new Trip(graph.getNumNodes());
            thisTrip.chooseNextCity(1);
            System.out.println(
                    BackTrackRecurse(graph, thisTrip, minTrip).toString(graph));
            break;
        case "MINE":
            Trip myTrip = new Trip(graph.getNumNodes());
            Trip myMinTrip = new Trip(graph.getNumNodes());
            myTrip.chooseNextCity(1);
            System.out.println(Mine(graph, myTrip, myMinTrip).toString(graph));
            break;
        case "TIME":
            Time(graph);
            break;
        }
    }

    /**
     * Purpose: Creates a graph object using the input file
     * 
     * @param inputfile:
     *            The file input by the user
     * @return the DGraph object
     */
    public static DGraph graphCreator(Scanner inputfile) {
        DGraph graph = null;
        int numCity;
        while (inputfile.hasNextLine()) {
            String line = inputfile.nextLine();
            if (line.charAt(0) != '%') {
                String[] graphInput = line.split("\\s+");
                // graphInput[0] = from node
                // graphInput[1] = to node
                // graphInput[2] = weight
                if (graphInput[0].equals(graphInput[1])) {
                    // First line, creates the graph object
                    graph = new DGraph(Integer.parseInt(graphInput[0]));
                } else {
                    graph.addEdge(Integer.parseInt(graphInput[0]),
                            Integer.parseInt(graphInput[1]),
                            Double.parseDouble(graphInput[2]));
                }
            }
        }
        return graph;
    }

    /**
     * Purpose: Solves the least cost algorithm using a recursive backtracking
     * solution
     * 
     * @param graph:
     *            the graph created by the input file
     * @param myTrip:
     *            current trip through the graph
     * @param minTrip:
     *            minimum cost trip through the graph
     * @return the minimum cost trip through the graph (minTrip)
     */
    public static Trip BackTrackRecurse(DGraph graph, Trip myTrip,
            Trip minTrip) {
        if (myTrip.isPossible(graph)) {
            if (!minTrip.isPossible(graph)
                    || myTrip.tripCost(graph) < minTrip.tripCost(graph)) {
                // When a solution is possible and this solution is better than
                // all previous
                minTrip.copyOtherIntoSelf(myTrip);
            }
            return minTrip;
        } else {
            if (myTrip.tripCost(graph) < minTrip.tripCost(graph)) {
                for (int i = 0; i < myTrip.citiesLeft().size(); i++) {
                    // Loop through all remaining cities and recurse over all
                    // possibilities
                    myTrip.chooseNextCity(myTrip.citiesLeft().get(i));
                    BackTrackRecurse(graph, myTrip, minTrip);
                    myTrip.unchooseLastCity();
                }
            }
            return minTrip;
        }
    }

    /**
     * Purpose: Solves the least cost algorithm using a heuristic solution
     * 
     * @param graph:
     *            the graph created by the input file
     * @return the completed trip through the graph
     */
    public static Trip Heuristic(DGraph graph) {
        Trip thisTrip = new Trip(graph.getNumNodes());
        thisTrip.chooseNextCity(1);
        int currentCity = 1;
        int minCity = 0;
        for (int i = 2; i <= graph.getNumNodes(); i++) {
            // Loops through all nodes, starts at i = 2 because 1 is already
            // been used to start
            double localMin = 0;
            for (int j = 0; j < graph.getNeighbors(currentCity).size(); j++) {
                // Loops through all neighbors of current node to find the local
                // minimum weight
                int secondCity = graph.getNeighbors(currentCity).get(j);
                if ((localMin == 0.0
                        || graph.getWeight(currentCity, secondCity) < localMin)
                        && thisTrip.isCityAvailable(secondCity)) {
                    // 0 check for first time through, if its available and
                    // smaller than current min then change current min
                    localMin = graph.getWeight(currentCity, secondCity);
                    minCity = secondCity;
                }
            }
            currentCity = minCity;
            thisTrip.chooseNextCity(minCity);
        }
        return thisTrip;
    }

    /**
     * Purpose: Solves the least cost algorithm using a modified recursive
     * solution in order to increase speed slightly
     * 
     * @param graph:
     *            the graph created by the input file
     * @param myTrip:
     *            the current trip through the graph
     * @param minTrip:
     *            the minimum cost trip through the graph
     * @return the minimum cost trip through the graph (minTrip)
     */
    public static Trip Mine(DGraph graph, Trip myTrip,
            Trip minTrip) {
        // Modified to loop through half of the possibilities available instead
        // of all in order to be faster but less accurate
        if (myTrip.isPossible(graph)) {
            if (!minTrip.isPossible(graph)
                    || myTrip.tripCost(graph) < minTrip.tripCost(graph)) {
                // When a solution is possible and this solution is better than
                // all previous
                minTrip.copyOtherIntoSelf(myTrip);
            }
            return minTrip;
        } else {
            if (myTrip.tripCost(graph) < minTrip.tripCost(graph)) {
                for (int i = 0; i < myTrip.citiesLeft().size(); i += 2) {
                    // Loop through half of remaining cities and recurse over
                    // those possibilities
                    myTrip.chooseNextCity(myTrip.citiesLeft().get(i));
                    BackTrackRecurse(graph, myTrip, minTrip);
                    myTrip.unchooseLastCity();
                }
            }
            return minTrip;
        }
    }

    /**
     * Purpose: Measures and prints the time it takes for all different
     * algorithms, along with their measured costs
     * 
     * @param graph:
     *            the graph created by the input file
     */
    public static void Time(DGraph graph) {
        // Heuristic algorithm
        Trip aTrip = new Trip(graph.getNumNodes());
        aTrip.chooseNextCity(1);
        long startTime = System.nanoTime();
        aTrip = Heuristic(graph);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("heuristic: cost = " + aTrip.tripCost(graph) + ", "
                + duration + " milliseconds");
        // My algorithm
        Trip bTrip = new Trip(graph.getNumNodes());
        Trip cTrip = new Trip(graph.getNumNodes());
        bTrip.chooseNextCity(1);
        startTime = System.nanoTime();
        cTrip = Mine(graph, bTrip, cTrip);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("mine: cost = " + cTrip.tripCost(graph) + ", "
                + duration + " milliseconds");
        // Backtrack algorithm
        Trip dTrip = new Trip(graph.getNumNodes());
        Trip eTrip = new Trip(graph.getNumNodes());
        startTime = System.nanoTime();
        dTrip.chooseNextCity(1);
        dTrip = BackTrackRecurse(graph, dTrip, eTrip);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("backtrack: cost = " + dTrip.tripCost(graph) + ", "
                + duration + " milliseconds");
    }
}
