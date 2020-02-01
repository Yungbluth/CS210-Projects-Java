import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import bool_exp.ASTNode;
import bool_exp.BoolSatParser;

/**
 * 
 * AUTHOR: Matthew Yungbluth
 * File: PA4BoolSat.java
 * ASSIGNMENT: Programming Assignment 4 - BoolSat
 * COURSE: CSc 210; Section C; Spring 2019
 * PURPOSE: This program reads in a file of a logical NAND operator and returns
 * if this logical expression is true or not
 * 
 * USAGE:
 * java PA4BoolSat inputFile DEBUG(optional)
 * where input file is a file with the NAND expression and DEBUG is used if the
 * debug mode is desired (prints all trials even false)
 * 
 * Example input:
 * inputFile DEBUG
 * inputFile:
 * a NAND a
 * 
 * Output for above input:
 * input: (a NAND a)
 * UNSAT
 * a: false, false
 *
 */
public class PA4BoolSat {
    public static void main(String[] params) throws FileNotFoundException {
        // params[0] = input file
        // params[1] = DEBUG (doesn't exist if no debug)
        //Reads in file
        Scanner readFile = new Scanner(params[0]);
        String fileName = readFile.next();
        Scanner actualFile = new Scanner(new File(fileName));
        String inputLine = actualFile.nextLine();
        //Filled in tree for expression
        ASTNode treeNode = BoolSatParser.parse(inputLine);
        System.out.println("input: " + inputLine);
        Set<String> IDSet = new TreeSet<>();
        //Finds all IDs in expression and puts them into IDSet
        IDFinder(treeNode, IDSet);
        Map<String, Boolean> finalTest = new TreeMap();
        // Assigns false to each ID by default
        IDSet.forEach(i -> finalTest.put(i, false));
        // if DEBUG
        if (params.length == 2) {
            boolTester(finalTest, finalTest.size(), params[1], treeNode);
        } else {
            boolTester(finalTest, finalTest.size(), "", treeNode);
        }
    }

    /**
     * Purpose: returns the result of a NAND
     * 
     * @param boolOne:
     *            the first boolean in the NAND
     * @param boolTwo:
     *            the second boolean in the NAND
     * @return the result of the NAND
     */
    public static boolean NANDEval(boolean boolOne, boolean boolTwo) {
        return !(boolOne == true && boolTwo == true);
    }

    /**
     * Purpose: Finds all IDs and places them in a set
     * 
     * @param treeNode:
     *            the root node of the tree of the expression
     * @param IDSet:
     *            the set of all IDs
     */
    public static void IDFinder(ASTNode treeNode, Set<String> IDSet) {
        if (treeNode.child1 != null && treeNode.child2 != null) {
            IDFinder(treeNode.child1, IDSet);
            IDFinder(treeNode.child2, IDSet);
        } else {
            IDSet.add(treeNode.getId());
        }
    }

    /**
     * Purpose: Creates every permutation of boolean values for each ID and
     * prints them out with their truth value and the truth value of the entire
     * expression
     * 
     * @param finalTest:
     *            the map of IDs assigned to their individual truth values
     * @param n:
     *            the size of finalTest, used to create boolean values
     * @param debug:
     *            empty if no debug, DEBUG if yes debug
     * @param treeNode:
     *            the root node of the tree of the expression
     */
    public static void boolTester(Map<String, Boolean> finalTest, int n,
            String debug, ASTNode treeNode) {
        boolean isSat = false;
        String finalOutput = "";
        // Each instance of the for loop is a permutation of boolean values
        for (int i = 0; i < Math.pow(2, n); i++) {
            // creates a boolean string
            String binary = Integer.toBinaryString(i);
            while (binary.length() < n) {
                binary = "0" + binary;
            }
            boolean[] boolArray = new boolean[n];
            // converts the boolean string into an array of booleans
            for (int j = 0; j < binary.length(); j++) {
                boolArray[j] = binary.substring(j, j + 1).equals("0") ? false
                        : true;
            }
            int iterator = 0;
            // assigns the set of ids with the array of booleans into a treemap
            for (Map.Entry<String, Boolean> entry : finalTest.entrySet()) {
                finalTest.put(entry.getKey(), boolArray[iterator]);
                iterator++;
            }
            // checks if the entire expression is true or false
            boolean sat = isValid(finalTest, treeNode);
            if (sat && !isSat) {
                isSat = true;
                System.out.println("SAT");
            }
            // Adds the text to a final output string
            if (debug.equals("DEBUG")) {
                finalOutput = finalOutput + finalTest.toString()
                        .replace("=", ": ").replace("}", "").replace("{", "")
                        + ", " + sat + "\n";
            } else if (sat) {
                finalOutput = finalOutput + finalTest.toString()
                        .replace("=", ": ").replace("}", "").replace("{", "")
                        + "\n";
            }
        }
        if (!isSat) {
            System.out.println("UNSAT");
        }
        System.out.println(finalOutput);

    }

    /**
     * Purpose: Recursively returns if the entire expression is true or false
     * 
     * @param finalTest:
     *            the mapping of ids to their truth value
     * @param treeNode:
     *            the root node of the tree of the expression
     * @return: a boolean if the entire expression is true or false
     */
    public static boolean isValid(Map<String, Boolean> finalTest,
            ASTNode treeNode) {
        if (treeNode.isNand()) {
            if (treeNode.child1.isNand()) {
                if (treeNode.child2.isNand()) {
                    return (NANDEval(isValid(finalTest, treeNode.child1),
                            isValid(finalTest, treeNode.child2)));
                } else {
                    return (NANDEval(isValid(finalTest, treeNode.child1),
                            finalTest.get(treeNode.child2.getId())));
                }
            } else {
                if (treeNode.child2.isNand()) {
                    return (NANDEval(isValid(finalTest, treeNode.child2),
                            finalTest.get(treeNode.child1.getId())));
                } else {
                    return (NANDEval(finalTest.get(treeNode.child1.getId()),
                            finalTest.get(treeNode.child2.getId())));
                }
            }
        }
        return true;
    }

}
