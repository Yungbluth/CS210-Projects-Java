import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * AUTHOR: Matthew Yungbluth
 * File: Ecosystem.java
 * ASSIGNMENT: Programming Assignment 9 - CrisprGUI
 * COURSE: CSc 210; Section C; Spring 2019
 * PURPOSE: This file creates and manipulates the ecosystem object
 */
public class Ecosystem {
    ArrayList<ArrayList<ArrayList<Animal>>> ecoList;

    // Purpose: Initializes the ecosystem object
    public Ecosystem() {
        this.ecoList = new ArrayList<ArrayList<ArrayList<Animal>>>();
    }
    // Purpose: returns the ecosystem
    public ArrayList<ArrayList<ArrayList<Animal>>> getList() {
        return this.ecoList;
    }
    /**
     * Purpose: Handles the create call, seperating between different animals
     * 
     * @param row:
     *            the row in the ecosystem
     * @param col:
     *            the column in the ecosystem
     * @param spec:
     *            the current species in question
     * @param isFemale:
     *            if the animal is a female
     * @param extra:
     *            any extra information for the animal
     * @param mosqOne:
     *            if mosquito, if parent two has modified genes
     * @param mosqTwo:
     *            if mosquito, if parent one has modified genes
     */
    public void create(int row, int col, String spec, boolean isFemale,
            String extra, boolean mosqOne, boolean mosqTwo) {
        Set<String> mammalAnis = new HashSet<String>(
                Arrays.asList("elephant", "e", "rhinoceros", "r", "lion", "l",
                        "giraffe", "g", "zebra", "z"));
        Set<String> birdAnis = new HashSet<String>(Arrays.asList("thrush", "t",
                "owl", "o", "warbler", "w", "shrike", "s"));
        Set<String> insectAnis = new HashSet<String>(
                Arrays.asList("bee", "b", "fly", "f", "ant", "a"));
        if (mammalAnis.contains(spec)) {
            this.ecoList.get(row).get(col).add(
                    new Mammals(spec, isFemale, extra.equals("right")));
        } else if (birdAnis.contains(spec)) {
            this.ecoList.get(row).get(col)
                    .add(new Birds(spec, isFemale, Integer.parseInt(extra)));
        } else if (insectAnis.contains(spec)) {
            this.ecoList.get(row).get(col).add(
                    new Insects(spec, isFemale, Boolean.parseBoolean(extra), false, false));
        } else if (spec.equals("mosquito") || spec.equals("m")) {
            this.ecoList.get(row).get(col).add(new Insects(spec, isFemale,
                    Boolean.parseBoolean(extra), mosqOne, mosqTwo));
        }
    }
    /**
     * Purpose: Handles mammal movement
     * 
     * @param currentAnimal:
     *            current animal in question
     * @param ija:
     *            current location of animal
     */
    private void mammalMover(Animal currentAnimal, int i, int j, int a) {
        // all furthest nested else is loop
        if (((Mammals) currentAnimal).getAge() == 1
                || ((Mammals) currentAnimal).getFood() == 1) {
            return;
        }
        if (((Mammals) currentAnimal).getRight()) {
            if (((Mammals) currentAnimal).getStep()) {
                // right
                if (j + 1 < ecoList.get(i).size()) {
                    this.ecoList.get(i).get(j + 1).add(currentAnimal);
                } else {
                    this.ecoList.get(i).get(0).add(currentAnimal);
                }
            } else {
                // down
                if (i + 1 < ecoList.size()) {
                    this.ecoList.get(i + 1).get(j).add(currentAnimal);
                } else {
                    this.ecoList.get(0).get(j).add(currentAnimal);
                }
            }
        } else {
            if (((Mammals) currentAnimal).getStep()) {
                // left
                if (j - 1 >= 0) {
                    this.ecoList.get(i).get(j - 1).add(currentAnimal);
                } else {
                    int newj = this.ecoList.get(i).size() - 1;
                    this.ecoList.get(i).get(newj).add(currentAnimal);
                }
            } else {
                // up
                if (i - 1 >= 0) {
                    this.ecoList.get(i - 1).get(j).add(currentAnimal);
                } else {
                    int newi = this.ecoList.size() - 1;
                    this.ecoList.get(newi).get(j).add(currentAnimal);
                }
            }
        }
        ((Mammals) currentAnimal).changeStep();
        ((Mammals) currentAnimal).decreaseAge();
    }
    /**
     * Purpose: handles the bird movement
     * 
     * @param currentAnimal:
     *            current animal in question
     * @param ija:
     *            current location of animal
     */
    private void birdMover(Animal currentAnimal, int i, int j, int a) {
        if (((Birds) currentAnimal).getAge() == 1
                || ((Birds) currentAnimal).getFood() == 1) {
            return;
        }
        switch (((Birds) currentAnimal).getDir()) {
        // all elses are loop
        case "d":
            if (i + 1 < this.ecoList.size()) {
                this.ecoList.get(i + 1).get(j).add(currentAnimal);
            } else {
                this.ecoList.get(0).get(j).add(currentAnimal);
            }
            break;
        case "r":
            if (j + 1 < this.ecoList.get(i).size()) {
                this.ecoList.get(i).get(j + 1).add(currentAnimal);
            } else {
                this.ecoList.get(i).get(0).add(currentAnimal);
            }
            break;
        case "u":
            if (i - 1 >= 0) {
                this.ecoList.get(i - 1).get(j).add(currentAnimal);
            } else {
                int newi = this.ecoList.size() - 1;
                this.ecoList.get(newi).get(j).add(currentAnimal);
            }
            break;
        }
        ((Birds) currentAnimal).decreaseStep();
        ((Birds) currentAnimal).decreaseAge();
    }
    /**
     * Purpose: handles insect movement
     * 
     * @param currentAnimal:
     *            current animal in question
     * @param ija:
     *            current location of animal
     */
    private void insectMover(Animal currentAnimal, int i, int j, int a) {
        if (((Insects) currentAnimal).getAge() == 1) {
            return;
        }
        switch (((Insects) currentAnimal).getDir()) {
        // all elses are loop
        case "u":
            if (i - 1 >= 0) {
                this.ecoList.get(i - 1).get(j).add(currentAnimal);
            } else {
                int newi = this.ecoList.size() - 1;
                this.ecoList.get(newi).get(j).add(currentAnimal);
            }
            break;
        case "r":
            if (j + 1 < this.ecoList.get(i).size()) {
                this.ecoList.get(i).get(j + 1).add(currentAnimal);
            } else {
                this.ecoList.get(i).get(0).add(currentAnimal);
            }
            break;
        case "d":
            if (i + 1 < this.ecoList.size()) {
                this.ecoList.get(i + 1).get(j).add(currentAnimal);
            } else {
                this.ecoList.get(0).get(j).add(currentAnimal);
            }
            break;
        case "l":
            if (j - 1 >= 0) {
                this.ecoList.get(i).get(j - 1).add(currentAnimal);
            } else {
                int newj = this.ecoList.get(i).size() - 1;
                this.ecoList.get(i).get(newj).add(currentAnimal);
            }
            break;
        }
        ((Insects) currentAnimal).decreaseStep();
        ((Insects) currentAnimal).decreaseAge();
    }
    /**
     * Purpose: separates animals based on type, calls correct method to move
     * 
     * @param currentAnimal:
     *            current animal in question
     * @param ija:
     *            current location of animal
     */
    private void mover(Animal currentAnimal, int i, int j, int a) {
        currentAnimal.setMoved(true);
        if (currentAnimal instanceof Mammals) {
            // mammal move
            mammalMover(currentAnimal, i, j, a);
        } else if (currentAnimal instanceof Birds) {
            // bird move
            birdMover(currentAnimal, i, j, a);
        } else if (currentAnimal instanceof Insects) {
            // insect move
            insectMover(currentAnimal, i, j, a);
        }
        this.ecoList.get(i).get(j).remove(a);
    }
    /**
     * Purpose: default move call, moves all animals once
     */
    public void move() {
        HashSet<Animal> allMoved = new HashSet();
        for (int i = 0; i < this.ecoList.size(); i++) {
            for (int j = 0; j < this.ecoList.get(i).size(); j++) {
                if (this.ecoList.get(i).get(j).size() > 0) {
                    HashSet<Integer> indexToRemove = new HashSet();
                    for (int a = 0; a < this.ecoList.get(i).get(j)
                            .size(); a++) {
                        // each animal to move
                        Animal currentAnimal = this.ecoList.get(i).get(j)
                                .get(a);
                        if (!currentAnimal.getMoved()) {
                            allMoved.add(currentAnimal);
                            mover(currentAnimal, i, j, a);
                            a = a - 1;
                        }
                    }
                }
            }
        }
        for (Animal ani : allMoved) {
            ani.setMoved(false);
        }
    }
    /**
     * Purpose: moves all animals at a specific position
     * 
     * @param ij:
     *            position to move animals from
     */
    public void move(int i, int j) {
        HashSet<Animal> allMoved = new HashSet();
        if (i >= 0 && i < this.ecoList.size()) {
            if (j >= 0 && j < this.ecoList.get(i).size()) {
                for (int a = 0; a < this.ecoList.get(i).get(j).size(); a++) {
                    allMoved.add(this.ecoList.get(i).get(j).get(a));
                    mover(this.ecoList.get(i).get(j).get(a), i, j, a);
                    a = a - 1;
                }
            }
        }
        for (Animal ani : allMoved) {
            ani.setMoved(false);
        }
    }
    /**
     * Purpose: moves all animals of a specific type
     * 
     * @param type:
     *            type of animal to move
     */
    public void moveType(String type) throws ClassNotFoundException {
        HashSet<Animal> allMoved = new HashSet();
        for (int i = 0; i < this.ecoList.size(); i++) {
            for (int j = 0; j < this.ecoList.get(i).size(); j++) {
                if (this.ecoList.get(i).get(j).size() > 0) {
                    HashSet<Integer> indexToRemove = new HashSet();
                    for (int a = 0; a < this.ecoList.get(i).get(j)
                            .size(); a++) {
                        // each animal to move
                        Animal currentAnimal = this.ecoList.get(i).get(j)
                                .get(a);
                        if (!currentAnimal.getMoved()) {
                            if ((currentAnimal.getSpec()
                                    .equals(type.substring(0, 1)))) {
                                mover(currentAnimal, i, j, a);
                                allMoved.add(currentAnimal);
                                a = a - 1;
                            }
                        }
                    }
                }
            }
        }
        for (Animal ani : allMoved) {
            ani.setMoved(false);
        }
    }
    /**
     * Purpose: moves all animals of a specific species
     * 
     * @param species:
     *            type of species to move
     */
    public void moveAnimal(String species) {
        HashSet<Animal> allMoved = new HashSet();
        for (int i = 0; i < this.ecoList.size(); i++) {
            for (int j = 0; j < this.ecoList.get(i).size(); j++) {
                if (this.ecoList.get(i).get(j).size() > 0) {
                    HashSet<Integer> indexToRemove = new HashSet();
                    for (int a = 0; a < this.ecoList.get(i).get(j)
                            .size(); a++) {
                        // each animal to move
                        Animal currentAnimal = this.ecoList.get(i).get(j)
                                .get(a);
                        if (!currentAnimal.getMoved()) {
                            int theAt = currentAnimal.toString().indexOf("@");
                            String currentType = currentAnimal.toString()
                                    .substring(0, theAt);
                            if (species.equals(currentType)) {
                                mover(currentAnimal, i, j, a);
                                allMoved.add(currentAnimal);
                                a = a - 1;
                            }
                        }
                    }
                }
            }
        }
        for (Animal ani : allMoved) {
            ani.setMoved(false);
        }
    }
    /**
     * Purpose: reproduces animals on a specific position
     * Animals reproduce if they are the first two positions in a tile, are the
     * same species, one is male and the other is female, and their parents
     * don't both have modified genes
     * 
     * @param ij:
     *            position to reproduce animal
     */
    public void reproducer(int i, int j) {
        if (this.ecoList.get(i).get(j).size() >= 2) {
            Animal curAniOne = this.ecoList.get(i).get(j).get(0);
            Animal curAniTwo = this.ecoList.get(i).get(j).get(1);
            if (curAniOne.getSpec().equals(curAniTwo.getSpec())) {
                if (curAniOne.getFemale() != curAniTwo.getFemale()) {
                    Random genderBool = new Random();
                    boolean female = genderBool.nextBoolean();
                    if (curAniOne instanceof Mammals) {
                        List<String> dirList = new ArrayList<>();
                        dirList.add("right");
                        dirList.add("left");
                        Random rand = new Random();
                        String thisDir = dirList
                                .get(rand.nextInt(dirList.size()));
                        create(i, j, curAniOne.getSpec(), female, thisDir,
                                false,
                                false);
                    } else if (curAniOne instanceof Birds) {
                        create(i, j, curAniOne.getSpec(), female, "5", false,
                                false);
                    } else if (curAniOne instanceof Insects) {
                        List<String> dirList = new ArrayList<>();
                        dirList.add("true");
                        dirList.add("false");
                        Random rand = new Random();
                        String thisDir = dirList
                                .get(rand.nextInt(dirList.size()));
                        if (curAniOne.getSpec().equals("m")) {
                            // mosquito
                            if (((Insects) curAniOne).getMosq()
                                    && ((Insects) curAniTwo).getMosq()) {
                                create(i, j, curAniOne.getSpec(), female,
                                        thisDir,
                                        ((Insects) curAniOne).getMosqChild(),
                                        ((Insects) curAniTwo).getMosqChild());
                            }
                        } else {
                            create(i, j, curAniOne.getSpec(), female, "false",
                                    false, false);
                        }
                    }
                }
            }
        }
    }
    /**
     * Purpose: reproduces all animals if possible
     */
    public void reproduce() {
        for (int i = 0; i < this.ecoList.size(); i++) {
            for (int j = 0; j < this.ecoList.size(); j++) {
                reproducer(i, j);
            }
        }
    }
    /**
     * Purpose: reproduces all animals of a specific type if possible
     * 
     * @param type:
     *            type of animals to reproduce
     */
    public void reproduce(String type) {
        for (int i = 0; i < this.ecoList.size(); i++) {
            for (int j = 0; j < this.ecoList.get(i).size(); j++) {
                if (this.ecoList.get(i).get(j).size() > 1) {
                    if (this.ecoList.get(i).get(j).get(0).getSpec()
                            .equals(type)) {
                        reproducer(i, j);
                    }
                }
            }
        }
    }
    /**
     * Purpose: Performs the eat command on every tile of the grid
     */
    public void eat() {
        for (int i = 0; i < this.ecoList.size(); i++) {
            for (int j = 0; j < this.ecoList.size(); j++) {
                eater(i, j);
            }
        }
    }
    /**
     * Purpose: performs the eat command on a certain type of animal only
     * 
     * @param type:
     *            the type of animal to eat
     */
    public void eat(String type) {
        for (int i = 0; i < this.ecoList.size(); i++) {
            for (int j = 0; j < this.ecoList.get(i).size(); j++) {
                if (this.ecoList.get(i).get(j).size() > 1) {
                    if (this.ecoList.get(i).get(j).get(0).getSpec()
                            .equals(type)
                            || this.ecoList.get(i).get(j).get(1).getSpec()
                            .equals(type)) {
                        eater(i, j);
                    }
                }
            }
        }
    }
    /**
     * Purpose: performs the eat command on the given position in the grid
     */
    public void eater(int i, int j) {
        if (this.ecoList.get(i).get(j).size() >= 2) {
            Animal curAniOne = this.ecoList.get(i).get(j).get(0);
            Animal curAniTwo = this.ecoList.get(i).get(j).get(1);
            if (curAniOne instanceof Birds && curAniTwo instanceof Insects) {
                ((Birds) curAniOne).resetFood();
                this.ecoList.get(i).get(j).remove(1);
            } else if (curAniTwo instanceof Birds
                    && curAniOne instanceof Insects) {
                ((Birds) curAniTwo).resetFood();
                this.ecoList.get(i).get(j).remove(0);
            } else if (curAniOne instanceof Mammals
                    && curAniTwo instanceof Mammals) {
                ((Mammals) curAniOne).resetFood();
                this.ecoList.get(i).get(j).remove(1);
            }
        }
    }
}
