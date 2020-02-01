import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * AUTHOR: Matthew Yungbluth
 * File: PA10Main.java
 * ASSIGNMENT: Programming Assignment 10 - KineticSculpture
 * COURSE: CSc 210; Section C; Spring 2019
 * PURPOSE: This program accepts a user entered file which creates a kinetic
 * sculpture and flows circles from node to node
 * 
 * Example input:
 * PublicTestCases/sculpture.in
 * 
 * delay: 1
 * input: RED, BLUE, YELLOW, GREEN, PURPLE, PINK, BLACK
 * 0: input, (20,20)
 * 1: passthrough, (70,40)
 * 2: passthrough, (80,100)
 * 3: passthrough, (80, 200)
 * 4: passthrough, (140, 100)
 * 5: passthrough, (140, 200)
 * 6: sink, (600,150)
 * 0 -> 1
 * 0 -> 2
 * 0 -> 3
 * 1 -> 5
 * 2 -> 4
 * 3 -> 4
 * 4 -> 6
 * 5 -> 6
 *
 * Where delay is the delay between animations, input is the list of colors for
 * the circles, input is the input node from which the circles are created,
 * passthrough are the nodes that the circles pass through, sink is the final
 * node which stops the circles, input passthrough and sink begin with node id
 * and end with coordinates of node, id -> id creates a link between two node
 * ids
 * 
 * Also allows for changer node, which changes the color of circles
 * id: changer, (x,y) Color
 */
public class PA10Main extends Application {
    // Size of node boxes
    private final static int NODE_SIZE = 30;
    // List of colors
    private List<String> colors = new ArrayList<>();
    // JavaFXView Object to manipulate circles and edges and graphics
    private JavaFXView javaView = new JavaFXView(700, 700);
    // Delay between animations
    private double delay;

    public static void main(String[] args) throws FileNotFoundException {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage)
            throws FileNotFoundException, ClassNotFoundException {
        // inputFile is file to read through
        TextField inputFile = new TextField();
        inputFile.setPrefWidth(200);
        BorderPane p = new BorderPane();
        HBox input_box = new HBox(2);
        // Button to process the TextField
        Button button = new Button("Input File");
        input_box.getChildren().add(inputFile);
        input_box.getChildren().add(button);
        input_box.setSpacing(5);
        p.setCenter(javaView.root);
        p.setBottom(input_box);
        primaryStage.setTitle("Yungbluth Kinetic Sculpture");
        primaryStage.setScene(new Scene(p));
        button.setDefaultButton(true);
        button.setOnAction(new HandleTextInput(inputFile));
        primaryStage.show();
    }
    // Purpose: Creates the animation delay for the graphics
    private void playSculpture() {
        // ----- Set up a timeline with an event that processes sculpture
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        PauseTransition wait = new PauseTransition(Duration.seconds(delay));
        wait.setOnFinished(new CommandHandler(wait));
        wait.play();
    }
    class HandleTextInput implements EventHandler<ActionEvent> {
        private TextField line;
        private Scanner inputFile;

        public HandleTextInput(TextField line) {
            this.line = line;
            this.inputFile = null;
        }
        // Purpose: Handles input file entered from user
        @Override
        public void handle(ActionEvent event) {
            String input = line.getText();
            javaView.root.getChildren().clear();
            Rectangle background = new Rectangle(0, 0, 700, 700);
            background.setFill(Color.AQUA);
            javaView.root.getChildren().add(background);
            javaView.getLines().clear();
            try {
                this.inputFile = new Scanner(new File(input));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            List<Node> nodes = new ArrayList<>();
            while (inputFile.hasNextLine()) {
                String line = inputFile.nextLine();
                String[] command = line.split("\\s+");
                commands(command, nodes);
                }
            playSculpture();
        }
        // Purpose: Handles different commands passed by the input file
        public void commands(String[] command, List<Node> nodes) {
        if (command[0].equals("delay:")) {
            delay = Double.parseDouble(command[1]);
        } else if (command[0].equals("input:")) {
            for (int i = 1; i < command.length; i++) {
                colors.add(command[i].replace(",", ""));
            }
        } else if (command.length > 1) {
            String[] coords = command[2].split(",");
            int x = Integer.parseInt(coords[0].replace("(", ""));
            int y = 0;
            int id = Integer.parseInt(command[0].replace(":", ""));
            if (coords.length == 2) {
                y = Integer.parseInt(coords[1].replace(")", ""));
            } else if (command.length > 3) {
                y = Integer.parseInt(command[3].replace(")", ""));
            }
            if (command[1].equals("passthrough,")) {
                Node newNode = new Pass(x, y, id);
                javaView.initNode(newNode, x, y, NODE_SIZE);
                nodes.add(newNode);
            } else if (command[1].equals("sink,")) {
                Node newNode = new Sink(x, y, id);
                javaView.initNode(newNode, x, y, NODE_SIZE);
                nodes.add(newNode);
            } else if (command[1].equals("input,")) {
                Input inputNode = new Input(x, y, id);
                javaView.initNode(inputNode, x, y, NODE_SIZE);
                nodes.add(inputNode);
            } else if (command[1].equals("->")) {
                    edgeCommand(command, nodes);
                } else if (command[1].equals("changer,")) {
                    String color = command[3];
                    Changer changeNode = new Changer(x, y, id, color);
                    javaView.initNode(changeNode, x, y, NODE_SIZE);
                    nodes.add(changeNode);
                 }
        }
        }
        // Purpose: Handles edge command from input file and creates those edges
        public void edgeCommand(String[] command, List<Node> nodes) {
            Node child = null, parent = null;
            int parentX = 0, parentY = 0, childX = 0, childY = 0;
            for (int i = 0; i < nodes.size(); i++) {
                if (nodes.get(i).getID() == Integer.parseInt(command[0])) {
                    parent = nodes.get(i);
                    parentX = nodes.get(i).getX();
                    parentY = nodes.get(i).getY();
                } else if (nodes.get(i).getID() == Integer
                        .parseInt(command[2])) {
                    child = nodes.get(i);
                    childX = nodes.get(i).getX();
                    childY = nodes.get(i).getY();
                }
            }
            javaView.initEdge(parent, child, parentX + NODE_SIZE,
                    parentY + (NODE_SIZE / 2), childX,
                    childY + (NODE_SIZE / 2));
            parent.addChild(child);

        }
    }
    class CommandHandler implements EventHandler<ActionEvent> {

        private PauseTransition wait;

        CommandHandler(PauseTransition wait) {
            this.wait = wait;
        }
        // Purpose: Executes every animation sequence
        @Override
        public void handle(ActionEvent event) {
            boolean done = false;
            HashMap<Node, Map<Node, Line>> lines = javaView.getLines();
            String curColor = "";
            if (colors.size() > 0) {
                curColor = colors.get(0);
            }
            // Start new color from input node
            if (colors.size() > 0) {
                for (Node nodes : lines.keySet()) {
                    if (nodes instanceof Input) {
                        nodes.setOutputBallColor(curColor);
                    }
                }
                colors.remove(0);
            }
            done = simulate();
            if (!done) {
                wait.playFromStart();
            } else {
                System.out.println("Nothing more to process");
                wait.stop();
            }
        }
        // Handles the Queue for each node text display
        public void queueListDisplay() {
            HashMap<Node, Map<Node, Line>> lines = javaView.getLines();
            for (Node nodes : lines.keySet()) {
                javaView.addText(nodes);
            }
        }
        // Purpose: moves all circles through sculpture
        public boolean simulate() {
            int circlesMoved = 0;
            HashMap<Node, Map<Node, Line>> lines = javaView.getLines();
            // Swaps circles from input to output and clears previous circles
            for (Node nodes : lines.keySet()) {
                if (!(nodes.inputListSize() == 0)
                        && !(nodes instanceof Sink)) {
                    nodes.inputToOutput();
                    if (nodes instanceof Changer) {
                        nodes.setOutputBallColor(
                                ((Changer) nodes).getColorChange());
                    }
                    for (Node parent : lines.keySet()) {
                        if (parent.getChildren().contains(nodes)) {
                            javaView.clearEdge(parent, nodes);
                        }
                    }
                }
                if (nodes instanceof Sink
                        && !(nodes.getInputList().isEmpty())) {
                    for (int i = 0; i < nodes.getInputList().size(); i++) {
                        System.out.println(
                                "sink output = " + nodes.getInputList().get(i));
                    }
                    nodes.setInputList(new ArrayList<>());
                }
            }
            // Moves circles from output of a node to input of all connected
            // children
            for (Node nodes : lines.keySet()) {
                if (!nodes.getOutputBallColor().equals("")) {
                    for (Node children : nodes.getChildren()) {
                        circlesMoved++;
                        String color = nodes.getOutputBallColor();
                        javaView.edgeTransition(nodes, children,
                                color, delay);
                        children.addInput(nodes.getOutputBallColor());
                    }
                    nodes.setOutputBallColor("");
                }
            }
            queueListDisplay();
            return circlesMoved == 0;
        }
    }
}
