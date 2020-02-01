import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * AUTHOR: Matthew Yungbluth
 * File: PA3Main.java
 * ASSIGNMENT: Programming Assignment 3 - Anagrams
 * COURSE: CSc 210; Section C; Spring 2019
 * PURPOSE: This program reads in command line arguments and returns anagrams of
 * the requested word
 * 
 * USAGE:
 * java PA3Main dictFile anagramWord maxLengthofAnagrams(0 for any length)
 * where dictFile is a file with a dictionary of word to compare to, anagramWord
 * is the word given to find anagrams within, and maxLengthofAnagrams is the
 * most amount of words in each anagram phrase
 *
 * Example Input:
 * dictFile:
 * hi
 * hello
 * give
 * me
 * points
 *
 * anagramWord:
 * hellopoints
 *
 * maxLengthofAnagrams:
 * 0
 * 
 * Output for the above input:
 * Phrase to scramble: hellopoints
 * 
 * All words found in hellopoints:
 * [hi, hello, points]
 * 
 * Anagrams for hellopoints:
 * [hello, points]
 * [points, hello]
 *
 *
 */
public class PA3Main {
    public static void main(String[] args) throws FileNotFoundException {
        // args[0] = dictionary of words
        // args[1] = big word to use
        // args[2] = max words for anagram
        System.out.println("Phrase to scramble: " + args[1]);
        // Reads in the file
        Scanner readFile = new Scanner(args[0]);
        String fileName = readFile.next();
        Scanner actualFile = new Scanner(new File(fileName));
        List<String> anaList = new ArrayList<>();
        LetterInventory wordArg = new LetterInventory(args[1]);
        // Fills the list of potential anagrams
        anaList = WordFinder(anaList, wordArg, actualFile);
        System.out.println("");
        System.out.println("All words found in " + args[1] + ":");
        System.out.println(anaList);
        List<String> wordChosen = new ArrayList<>();
        System.out.println("");
        System.out.println("Anagrams for " + args[1] + ":");
        // Prints the anagram groupings
        anaFinder(anaList, wordArg, Integer.parseInt(args[2]), wordChosen);
    }

    /**
     * Purpose: Loops through the dictionary file to return all potential
     * anagrams
     * 
     * @param anaList:
     *            the list of words of potential anagrams
     * @param wordArg:
     *            the original word passed in to find anagrams of
     * @param actualFile:
     *            the dictionary file passed in
     * @return List<String> anaList
     */
    public static List<String> WordFinder(List<String> anaList,
            LetterInventory wordArg, Scanner actualFile) {
        while (actualFile.hasNextLine()) {
            String currentLine = actualFile.nextLine();
            if (wordArg.contains(currentLine)) {
                anaList.add(currentLine);
            }
        }
        return anaList;
    }

    /**
     * Purpose: Recursively travels through all anagram combinations to find all
     * requested anagrams
     * 
     * @param anaList:
     *            the list of words of potential anagrams
     * @param wordArg:
     *            the original word passed in to find anagrams of
     * @param max:
     *            the max amount anagram words to find per grouping
     * @param wordChosen:
     *            the current words already chosen to form the anagram group
     */
    public static void anaFinder(List<String> anaList, LetterInventory wordArg,
            int max, List<String> wordChosen) {
        // base case
        if (wordArg.isEmpty()) {
            if (wordChosen.size() <= max || max == 0) {
                System.out.println(wordChosen);
            }
            return;
        }
        for (int i = 0; i < anaList.size(); i++) {
           String currentWord = anaList.get(i);
           if (wordArg.contains(currentWord)) {
                // choose
                wordArg.subtract(currentWord);
                wordChosen.add(currentWord);
                // recurse
                anaFinder(anaList, wordArg, max, wordChosen);
                // unchoose
                wordChosen.remove(currentWord);
                wordArg.add(currentWord);
           }
        }
    }
}
