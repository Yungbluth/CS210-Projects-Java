
public class Birds extends Animal {

    /**
     * AUTHOR: Matthew Yungbluth
     * File: Birds.java
     * ASSIGNMENT: Programming Assignment 6 - Crispr
     * COURSE: CSc 210; Section C; Spring 2019
     * PURPOSE: This file creates and manipulates the bird species, which is a
     * subclass of Animal.java
     */
    int steps;
    int totalSteps;
    String dir;
    int age;
    int food;

    /**
     * Purpose: initializes the bird object
     * 
     * @param spec:
     *            the species of this bird
     * @param isFemale:
     *            if this bird is a female
     * @param steps:
     *            the total steps in each direction for the bird to move
     */
    public Birds(String spec, boolean isFemale, int steps) {
        // Thrush, Owl, Warbler, Shrike
        super(spec, isFemale);
        this.totalSteps = steps;
        this.steps = steps;
        this.dir = "d";
        this.age = 15;
        this.food = 5;
    }

    int getAge() {
        return this.age;
    }

    int getFood() {
        return this.food;
    }

    void decreaseAge() {
        this.age -= 1;
        this.food -= 1;
    }

    void resetFood() {
        this.food = 5;
    }
    /**
     * Purpose: returns the current remaining steps for the bird to take in a
     * given direction
     */
    int getSteps() {
        return this.steps;
    }

    /**
     * Purpose: returns the current direction the bird is moving in
     */
    String getDir() {
        return this.dir;
    }

    /**
     * Purpose: decreases the current amount of steps by 1, if that brings it to
     * zero, reset the steps to max and change direction
     */
    void decreaseStep() {
        this.steps = this.steps - 1;
        if (this.steps == 0) {
            this.steps = this.totalSteps;
            switch(this.dir) {
            case "d":
                this.dir = "r";
                break;
            case "r":
                this.dir = "u";
                break;
            case "u":
                this.dir = "d";
                break;
            }
        }
    }
}
