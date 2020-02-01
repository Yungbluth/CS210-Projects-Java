public class Insects extends Animal {

    /**
     * AUTHOR: Matthew Yungbluth
     * File: Insects.java
     * ASSIGNMENT: Programming Assignment 9 - CrisprGUI
     * COURSE: CSc 210; Section C; Spring 2019
     * PURPOSE: This file creates and manipulates the insect species, which is a
     * subclass of Animal.java
     */

    boolean clock;
    int totalSteps;
    String dir;
    int steps;
    boolean mosqOne;
    boolean mosqTwo;
    int age;

    /**
     * Purpose: initializes the insect object
     * 
     * @param spec:
     *            the species of this insect
     * @param isFemale:
     *            if this insect is female
     * @param clock:
     *            if this insect is moving clockwise (if false:
     *            counterclockwise)
     * @param mosqOne:
     *            for mosquitos: if parent one has modified genes
     * @param mosqTwo:
     *            for mosquitos: if parent two has modified genes
     */
    public Insects(String spec, boolean isFemale, boolean clock,
            boolean mosqOne, boolean mosqTwo) {
        // Mosquito, Bee, Fly, Ant
        super(spec, isFemale);
        this.clock = clock;
        this.totalSteps = 1;
        this.dir = "l";
        this.steps = 1;
        this.mosqOne = mosqOne;
        this.mosqTwo = mosqTwo;
        this.age = 10;
    }

    /**
     * Purpose: returns the current age of the Insect
     */
    int getAge() {
        return this.age;
    }

    /**
     * Purpose: reduces the current age of the insect, for use after moving
     */
    void decreaseAge() {
        this.age -= 1;
    }

    /**
     * Purpose: returns if the child inherits modified genes
     */
    boolean getMosqChild() {
        return this.mosqOne || this.mosqTwo;
    }

    /**
     * Purpose: returns if this mosquito is able to reproduce
     */
    boolean getMosq() {
        return !this.mosqOne || !this.mosqTwo;
    }

    /**
     * Purpose: returns the current direction of this insect
     */
    String getDir() {
        return this.dir;
    }

    /**
     * Purpose: decreases the current remaining steps of the insect by 1, if the
     * remaining steps become 0, reset the steps to the max and change
     * direction, if a full circle has been made then increase the max steps by
     * 1
     */
    void decreaseStep() {
        this.steps = this.steps - 1;
        if (this.steps == 0) {
            switch (this.dir) {
            case "u":
                if (this.clock) {
                    this.dir = "r";
                } else {
                    this.dir = "l";
                    this.totalSteps = this.totalSteps + 1;
                }
                break;
            case "r":
                if (this.clock) {
                    this.dir = "d";
                } else {
                    this.dir = "u";
                }
                break;
            case "d":
                if (this.clock) {
                    this.dir = "l";
                    this.totalSteps = this.totalSteps + 1;
                } else {
                    this.dir = "r";
                }
                break;
            case "l":
                if (this.clock) {
                    this.dir = "u";
                } else {
                    this.dir = "d";
                }
                break;
            }
            this.steps = this.totalSteps;
        }
    }
}
