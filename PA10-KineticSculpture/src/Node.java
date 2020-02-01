import java.util.ArrayList;
import java.util.List;

import javafx.scene.text.Text;

/**
 * AUTHOR: Matthew Yungbluth
 * File: Node.java
 * ASSIGNMENT: Programming Assignment 10 - KineticSculpture
 * COURSE: CSc 210; Section C; Spring 2019
 * PURPOSE: This file creates an object of Nodes for use in PA10Main.java
 */
public class Node {

    private int x;
    private int y;
    private List<Node> children;
    private int id;
    private String inputBallColor;
    private String outputBallColor;
    private ArrayList<String> inputList;
    private Text text;

    public Node(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.children = new ArrayList<>();
        this.id = id;
        this.inputBallColor = "";
        this.outputBallColor = "";
        this.inputList = new ArrayList<>();
        this.text = new Text();
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Text getText() {
        return this.text;
    }

    public void setInputList(ArrayList newList) {
        this.inputList = newList;
    }

    public int inputListSize() {
        return this.inputList.size();
    }

    public ArrayList<String> getInputList() {
        return this.inputList;
    }

    public void addInput(String color) {
        this.inputList.add(color);
    }

    public void inputToOutput() {
        if (this.inputList.size() > 0) {
            this.outputBallColor = this.inputList.get(0);
            this.inputList.remove(0);
        }
    }

    public void setInputBallColor(String color) {
        this.inputBallColor = color;
    }

    public String getInputBallColor() {
        return this.inputBallColor;
    }

    public void setOutputBallColor(String color) {
        this.outputBallColor = color;
    }

    public String getOutputBallColor() {
        return this.outputBallColor;
    }

    public int getID() {
        return this.id;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public List<Node> getChildren() {
        return this.children;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }
}
