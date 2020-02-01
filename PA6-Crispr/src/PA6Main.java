import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * AUTHOR: Matthew Yungbluth
 * File: PA6Main.java
 * ASSIGNMENT: Programming Assignment 6 - Crispr
 * COURSE: CSc 210; Section C; Spring 2019
 * PURPOSE: This program reads in a file ecosystem commands and prints the
 * effect those commands have on the ecosystem
 * 
 * USAGE:
 * java PA6Main inputFile
 * where inputFile is a file with commands to execute
 * 
 * Example input:
 * inputFile
 * inputFile:
 * rows: 5
 * cols: 5
 * 
 * CREATE (1,1) bee male true
 * PRINT
 * MOVE (1,1)
 * PRINT
 * 
 * Output for above input:
 * > PRINT
 * .....
 * .b...
 * .....
 * .....
 * .....
 * 
 * > MOVE (1,1)
 * 
 * > PRINT
 * .....
 * b....
 * .....
 * .....
 * .....
 *
 * ALL POSSIBLE COMMAND:
 * CREATE (i, j) type male/female extra
 * PRINT
 * MOVE (i,j)/type/animal
 * REPRODUCE (i,j)/type
 * 
 */

public class PA6Main {
    public static void main(String[] args)
            throws FileNotFoundException, ClassNotFoundException {
        // args[0] = input file
        Scanner inputFile = new Scanner(new File(args[0]));
        int rows = -1;
        int cols = -1;
        boolean hasInit = true;
        Ecosystem myEco = new Ecosystem();
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            String[] command = line.split("\\s+");
            switch (command[0].toLowerCase()) {
            case "rows:":
                rows = Integer.parseInt(command[1]);
                break;
            case "cols:":
                cols = Integer.parseInt(command[1]);
                break;
            case "create":
                createCall(command, myEco);
                break;
            case "print":
                System.out.println("> PRINT");
                myEco.print();
                System.out.println();
                break;
            case "move":
                moveCall(command, myEco);
                break;
            case "reproduce":
                reproduceCall(command, myEco);
                break;
            }
            if (rows != -1 && cols != -1 && hasInit) {
                hasInit = false;
                ArrayList<ArrayList<ArrayList<Animal>>> myList = myEco.getList();
                for (int i = 0; i < rows; i++) {
                    myList.add(new ArrayList<ArrayList<Animal>>());
                    for (int j = 0; j < cols; j++) {
                        myList.get(i).add(new ArrayList<Animal>());
                    }
                }
            }
        }
    }

    /**
     * Purpose: Handles the create call
     * 
     * @param command:
     *            the list of attributes for the create command
     * @param myEco:
     *            the ecosystem being affected
     */
    public static void createCall(String[] command, Ecosystem myEco) {
        String[] createIndexes = command[1].replace("(", "").replace(")", "")
                .split(",");
        if (command.length > 5) {
            // mosquito
            myEco.create(Integer.parseInt(createIndexes[0]),
                    Integer.parseInt(createIndexes[1]), command[2],
                    command[3].equals("female"), command[4],
                    Boolean.parseBoolean(command[5]),
                    Boolean.parseBoolean(command[6]));
        } else {
            myEco.create(Integer.parseInt(createIndexes[0]),
                    Integer.parseInt(createIndexes[1]), command[2],
                    command[3].equals("female"), command[4], false, false);
        }
    }

    /**
     * Purpose: Handles the move call
     * 
     * @param command:
     *            the list of attributes for the move command
     * @param myEco:
     *            the ecosystem being affected
     */
    public static void moveCall(String[] command, Ecosystem myEco)
            throws ClassNotFoundException {
        List<String> speciesList = Arrays.asList("elephant", "rhinoceros",
                "lion", "giraffe", "zebra", "thrush", "owl", "warbler",
                "shrike", "mosquito", "bee", "fly", "ant");
        if (command.length == 1) {
            System.out.println("> MOVE");
            myEco.move();
            System.out.println();
        } else if (command[1].substring(0, 1).equals("(")) {
            // Move (row, column)
            System.out.println("> MOVE " + command[1]);
            myEco.move(Integer.parseInt(command[1].substring(1, 2)),
                    Integer.parseInt(command[1].substring(3, 4)));
            System.out.println();
        } else if (speciesList.contains(command[1])) {
            // type
            System.out.println("> MOVE " + command[1]);
            String lowerCommand = command[1].substring(0, 1).toLowerCase();
            myEco.moveType(lowerCommand);
        } else {
            // animal
            System.out.println("> MOVE " + command[1]);
            String lowerCommand = command[1].substring(0, 1).toUpperCase()
                    + command[1].substring(1).toLowerCase() + "s";
            myEco.moveAnimal(lowerCommand);
            System.out.println();
        }
    }

    /**
     * Purpose: Handles the reproduce call
     * 
     * @param command:
     *            the list of attributes for the reproduce command
     * @param myEco:
     *            the ecosystem being affected
     */
    public static void reproduceCall(String[] command, Ecosystem myEco) {
        List<String> speciesListRepro = Arrays.asList("elephant", "rhinoceros",
                "lion", "giraffe", "zebra", "thrush", "owl", "warbler",
                "shrike", "mosquito", "bee", "fly", "ant");
        if (command.length == 1) {
            System.out.println("> REPRODUCE");
            myEco.reproduce();
            System.out.println();
        } else if (command[1].substring(0, 1).equals("(")) {
            // Move (row, column)
            System.out.println("> REPRODUCE " + command[1]);
            myEco.reproducer(Integer.parseInt(command[1].substring(1, 2)),
                    Integer.parseInt(command[1].substring(3, 4)));
            System.out.println();
        } else if (speciesListRepro.contains(command[1])) {
            // type
            System.out.println("> REPRODUCE " + command[1]);
            String lowerCommand = command[1].substring(0, 1).toLowerCase();
            myEco.reproduce(lowerCommand);
        }
    }
}

