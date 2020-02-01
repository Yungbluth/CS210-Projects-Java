
public class Mammals extends Animal {

    /**
     * AUTHOR: Matthew Yungbluth
     * File: Mammals.java
     * ASSIGNMENT: Programming Assignment 9 - CrisprGUI
     * COURSE: CSc 210; Section C; Spring 2019
     * PURPOSE: This file creates and manipulates the mammal species, which is a
     * subclass of Animal.java
     */
    boolean isRight;
    boolean moveStep;
    int reproLeft;
    int age;
    int food;

    /**
     * Purpose: Initializes the mammal object
     * 
     * @param spec:
     *            the species of this mammal
     * @param isFemale:
     *            if this mammal is a female
     * @param isRight:
     *            the movement pattern of this mammal
     */
    public Mammals(String spec, boolean isFemale, boolean isRight) {
        // Elephant, Rhinoceros, Lion, Giraffe, Zebra
        super(spec, isFemale);
        this.isRight = isRight;
        this.moveStep = false;
        this.reproLeft = 5;
        this.age = 25;
        this.food = 10;
    }

    /**
     * Purpose: returns the current age number, animal dead when this reaches
     * zero
     */
    int getAge() {
        return this.age;
    }

    /**
     * Purpose: returns the current food number, animal dead when this reaches
     * zero
     */
    int getFood() {
        return this.food;
    }

    /**
     * Purpose: Decreases age and food, to be used after a move command
     */
    void decreaseAge() {
        this.age -= 1;
        this.food -= 1;
    }

    /**
     * Purpose: resets the food number to the max, to be used after eat command
     */
    void resetFood() {
        this.food = 10;
    }

    /**
     * Purpose: returns the current movement of the mammal (horizontal vs
     * vertical)
     */
    boolean getStep() {
        return this.moveStep;
    }

    /**
     * Purpose: inverts the movestep, to be used after every movement
     */
    void changeStep() {
        this.moveStep = !this.moveStep;
    }

    /**
     * Purpose: returns if this mammals moves to the right or the left
     */
    public boolean getRight() {
        return this.isRight;
    }

}
