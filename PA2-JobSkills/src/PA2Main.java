import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 * AUTHOR: Matthew Yungbluth
 * File: PA2Main.java
 * ASSIGNMENT: Programming Assignment 2 - Job Skills
 * COURSE: CSc 210; Section C; Spring 2019
 * PURPOSE: This program reads in command line arguments and returns information
 * from a file depending on those arguments
 * 
 * USAGE:
 * java PA2Main infile COMMAND optional
 * where infile is the input file, command is the command to look through the
 * input file
 *
 * Example Input:
 * Input File:
 * Company,Title,Category,Location,Responsibilities,Minimum
 * Qualifications,Preferred Qualifications
 * Google,TitleA,CategoryX,Tel Aviv,Everything and the rest,BS,MS
 * Google,TitleB,CategoryX,Tel Aviv,Everything and the rest,BS,MS
 *
 * COMMAND:
 * CATCOUNT will produce the number of positions for each category in the input
 * file for example the above input file will return
 * CategoryX, 2
 *
 * LOCATIONS will produce locations for category, requires optional to be filled
 * with a category for example the above input file will return
 * Tel Aviv, 2
 *
 * optional is required with the LOCATIONS command to provide the category in
 * question
 *
 */
public class PA2Main {
    // Purpose: converts the file name into a file and calls the required method
    // based on the command provided
    public static void main(String[] args) throws FileNotFoundException {
        //args[0] = file name, args[1] = command, args[2] = location if used
        Scanner readFile = new Scanner(args[0]);
        String fileName = readFile.next();
        Scanner actualFile = new Scanner(new File(fileName));
        String[] descriptions = actualFile.next().split(",");
        if (args[1].equals("CATCOUNT")) {
            CatCount(actualFile, descriptions);
        } else if (args[1].equals("LOCATIONS")) {
            Locations(actualFile, descriptions, args[2]);
        } else {
            System.out.println("Invalid Command");
        }
    }

    /**
     * Purpose: carries out the command CATCOUNT
     * 
     * @param actualFile:
     *            the input file being used
     * @param descriptions:
     *            the first line of the file, providing the template for the
     *            rest of the file
     */
    public static void CatCount(Scanner actualFile, String[] descriptions) {
        int indexOfCat = 0;
        // Finds the index of each category
        for (int i = 0; i < descriptions.length; i++) {
            if (descriptions[i].equals("Category")) {
                indexOfCat = i;
            }
        }
        HashMap<String, Integer> categoryCounter = new HashMap<>();
        actualFile.nextLine();
        // Loops through each line of the file
        while (actualFile.hasNextLine()) {
            String thisLine = actualFile.nextLine();
            String[] currentLine = thisLine.split(",");
            // Fills the HashMap categoryCounter
            HashFiller(categoryCounter, currentLine, indexOfCat);
        }
        ArrayList<String> sortedKeys = new ArrayList<String>(
                categoryCounter.keySet());
        Collections.sort(sortedKeys);
        System.out.println("Number of positions for each category");
        System.out.println("-------------------------------------");
        for (String cat : sortedKeys) {
            System.out.println(cat + ", " + categoryCounter.get(cat));
        }
    }

    /**
     * Purpose: carries out the command LOCATIONS
     * 
     * @param actualFile:
     *            the input file being used
     * @param descriptions:
     *            the first line of the file, providing the template for the
     *            rest of the file
     * @param cat:
     *            the category in question from the arguments passed into the
     *            file
     */
    public static void Locations(Scanner actualFile, String[] descriptions,
            String cat) {
        int indexOfCat = 0;
        int indexOfLoc = 0;
        // Finds the index of each category and each location
        for (int i = 0; i < descriptions.length; i++) {
            if (descriptions[i].equals("Category")) {
                indexOfCat = i;
            }
            if (descriptions[i].equals("Location")) {
                indexOfLoc = i;
            }
        }
        HashMap<String, Integer> locationCounter = new HashMap<>();
        actualFile.nextLine();
        // Loops through each line of the file
        while (actualFile.hasNextLine()) {
            String thisLine = actualFile.nextLine();
            String[] currentLine = thisLine.split(",");
            if (currentLine[indexOfCat].equals(cat)) {
                // Fills the HashMap locationCounter
                HashFiller(locationCounter, currentLine, indexOfLoc);
            }
        }
        ArrayList<String> sortedKeys = new ArrayList<String>(
                locationCounter.keySet());
        Collections.sort(sortedKeys);
        System.out.println("LOCATIONS for category: " + cat);
        System.out.println("-------------------------------------");
        for (String loc : sortedKeys) {
            System.out.println(loc + ", " + locationCounter.get(loc));
        }
    }

    /**
     * Purpose: Fills all hashmaps by number of times a string in an index has
     * appeared
     * 
     * @param hashCounter:
     *            the hashmap being manipulated
     * @param currentLine:
     *            an array split from a comma separated string in the input file
     * @param currentIndex:
     *            the index in currentLine that is being looked at
     */
    public static void HashFiller(HashMap<String, Integer> hashCounter,
            String[] currentLine, int currentIndex) {
        if (!hashCounter.isEmpty()
                && hashCounter.containsKey(currentLine[currentIndex])) {
            // 2nd+ instance of this category
            Integer currentNum = hashCounter.get(currentLine[currentIndex]);
            currentNum = currentNum + 1;
            hashCounter.put(currentLine[currentIndex], currentNum);
        } else {
            // First instance of this category
            hashCounter.put(currentLine[currentIndex], 1);
        }
    }

}

