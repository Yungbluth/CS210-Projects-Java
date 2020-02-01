
public class Animal {

    /**
     * AUTHOR: Matthew Yungbluth
     * File: Animal.java
     * ASSIGNMENT: Programming Assignment 9 - CrisprGUI
     * COURSE: CSc 210; Section C; Spring 2019
     * PURPOSE: This file creates and manipulates the Animal object, a
     * superclass for Mammals.java, Birds.java, and Insects.java
     */
    String spec;
    boolean isFemale;
    String extra;
    boolean hasMoved;

    /**
     * Purpose: initializes the animal object
     * 
     * @param spec:
     *            the species of this animal
     * @param isFemale:
     *            if this animal is female
     */
    public Animal(String spec, boolean isFemale) {
        // Birds, Insects, Mammals
        this.spec = spec;
        this.isFemale = isFemale;
        this.hasMoved = false;
    }

    /**
     * Purpose: returns if this animal is female
     */
    boolean getFemale() {
        return this.isFemale;
    }

    /**
     * Purpose: returns the current species of this animal
     */
    String getSpec() {
        return this.spec.substring(0, 1);
    }

    /**
     * Purpose: returns if this animal has moved this turn
     */
    boolean getMoved() {
        return this.hasMoved;
    }

    /**
     * Purpose: changes the value of hasMoved
     * 
     * @param newMoved:
     *            the value to change hasMoved to
     */
    void setMoved(boolean newMoved) {
        this.hasMoved = newMoved;
    }

}
