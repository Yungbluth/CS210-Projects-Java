import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * AUTHOR: Matthew Yungbluth
 * File: PA1Main.java
 * ASSIGNMENT: Programming Assignment 1 - Gradenator
 * COURSE: CSc 210; Section C; Spring 2019
 * PURPOSE: This program reads in a text file which contains many different
 * grade scores and creates a final grade based on these inputs
 *
 */
public class PA1Main {

    // Purpose: Calls all other functions and controls the flow of the program
    public static void main(String[] args) throws FileNotFoundException {
        Scanner openFile = FileInput();
        Calculations(openFile);
    }

    /**
     * Purpose: Reads in an input from the user and converts it to a Scanner
     * object
     * 
     * @return openFile, which is the scanner object of the file input into the
     *         program
     * @throws FileNotFoundException
     */
    public static Scanner FileInput() throws FileNotFoundException {
        Scanner userInput = new Scanner(System.in);
        System.out.println("File name?");
        String fileName = userInput.next();
        Scanner openFile = new Scanner(new File(fileName));
        return openFile;
    }

    /**
     * Purpose: Controls the bulk of the program, calculates all grades and
     * outputs them
     * to the console.
     * 
     * @param openFile,
     *            which is the Scanner file returned from FileInput()
     */
    public static void Calculations(Scanner openFile) {
        double studentPercent = 0;
        double totalPercent = 0;
        while (openFile.hasNextLine()) {
            String currentLine = openFile.nextLine();
            // splitGrades[0] = percent grades received,
            // splitGrades[1] = category
            // splitGrades[2] = how much of final grade
            String[] splitGrades = currentLine.split(";");
            String[] eachGrade = splitGrades[0].split(" ");
            double totalAdd = 0;
            int whiteSpaces = 0;
            // Each individual grade is added from eachGrade[] into totalAdd
            // whiteSpaces is created to combat an extra space which creates an
            // additional element in eachGrade.length
            for (int i = 0; i < eachGrade.length; i++) {
                if (!eachGrade[i].equals("")) {
                    totalAdd = totalAdd + Double.parseDouble(eachGrade[i]);
                } else {
                    whiteSpaces = whiteSpaces + 1;
                }
            }
            // Converts and calculates all necessary
            totalAdd = totalAdd / (eachGrade.length - whiteSpaces);
            String tempPercent = splitGrades[2].replace("%", "");
            double tempPercentPrint = Double.parseDouble(tempPercent);
            tempPercent = tempPercent.replace(" ", "");
            studentPercent = studentPercent
                    + totalAdd * Double.parseDouble(tempPercent);
            totalPercent = totalPercent + Double.parseDouble(tempPercent);
            // Prints each line according to the output:
            // category; % of total grade; avg=average grade
            System.out.println(
                    splitGrades[1].replaceFirst(" ", "") + "; "
                            + tempPercentPrint
                            + "%; avg=" + String.format("%.1f", totalAdd));
        }
        // Prints the Total percentage grade out of the total percentage
        // obtainable
        System.out.println(
                "TOTAL = "
                        + String.format("%.1f", studentPercent / 100)
                        + "% out of "
                        + totalPercent + "%");
    }
}

