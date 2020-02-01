import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * AUTHOR: Matthew Yungbluth
 * File: PA9Main.java
 * ASSIGNMENT: Programming Assignment 9 - CrisprGUI
 * COURSE: CSc 210; Section C; Spring 2019
 * PURPOSE: This program reads in a row and column commands from the command
 * line and then accepts user typed input to affect the ecosystem and shows
 * these changes graphically
 * 
 * USAGE:
 * java PA6Main row column
 * where row and column are integers representing the number of rows and columns
 * of the ecosystem
 * 
 * Example input:
 * 5 5
 * 
 * CREATE (1,1) bee male true
 * MOVE (1,1)
 * 
 * Output for above input:
 * .....
 * .b...
 * .....
 * .....
 * .....
 * 
 * > MOVE (1,1)
 * .....
 * b....
 * .....
 * .....
 * .....
 *
 * ALL POSSIBLE COMMAND:
 * CREATE (i, j) type male/female extra
 * MOVE (i,j)/type/animal
 * REPRODUCE (i,j)/type
 * EAT (i,j)/type
 */

public class PA9Main extends Application {
    // constants for the program
    private final static int TEXT_SIZE = 120;
    private final static int RECT_SIZE = 20;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parameters params = getParameters();
        List<String> args = params.getRaw();
        TextArea command = new TextArea();
        int rows = Integer.parseInt(args.get(0));
        int cols = Integer.parseInt(args.get(1));
        Ecosystem myEco = new Ecosystem();
        ArrayList<ArrayList<ArrayList<Animal>>> myList = myEco.getList();
        // Fills the ecosystem with blank spaces based on the amount of rows and
        // cols
        for (int i = 0; i < rows; i++) {
            myList.add(new ArrayList<ArrayList<Animal>>());
            for (int j = 0; j < cols; j++) {
                myList.get(i).add(new ArrayList<Animal>());
            }
        }
        Button button = new Button("Simulation Step");
        // ===== set up the scene with a text box and button for input
        BorderPane p = new BorderPane();
        TextField cmd_in = new TextField();
        final int num_items = 3;
        HBox input_box = new HBox(num_items);
        Text text = new Text();
        text.setText("  Insert Command Here -->");
        input_box.getChildren().add(text);
        input_box.getChildren().add(cmd_in);
        input_box.getChildren().add(button);
        input_box.setHgrow(cmd_in, Priority.ALWAYS);
        input_box.setSpacing(5);
        // Canvas(pixels across, pixels down)
        Canvas canvas = new Canvas(cols * RECT_SIZE, rows * RECT_SIZE);
        // Command TextArea will hold the commands from the file
        command.setPrefHeight(TEXT_SIZE);
        command.setEditable(false);
        // Place the canvas and command output areas in pane.
        HBox canvas_box = new HBox(canvas);
        canvas_box.setAlignment(Pos.CENTER);
        input_box.setAlignment(Pos.CENTER);
        p.setTop(canvas_box);
        p.setCenter(input_box);
        p.setBottom(command);
        // Title the stage and place the pane into the scene into the stage.
        primaryStage.setTitle("Ecosystem");
        primaryStage.setScene(new Scene(p));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Color c = Color.rgb(0, 255, 0);
        gc.setFill(c);
        // Background box
        gc.fillRect(0, 0, myEco.getList().get(0).size() * RECT_SIZE,
                myEco.getList().size() * RECT_SIZE);
        button.setDefaultButton(true);
        button.setOnAction(new HandleTextInput(cmd_in, gc, myEco, command));
        primaryStage.show();
    }

    /**
     * Purpose: Creates the required animal based on the command specifications
     * 
     * @param command:
     *            The create command line
     * @param myEco:
     *            The ecosystem object
     */
    public static void createCall(String[] command, Ecosystem myEco) {
        String[] createIndexes = command[1].replace("(", "").replace(")", "")
                .split(",");
        if (command.length > 5) {
            // mosquito
            myEco.create(Integer.parseInt(createIndexes[0]),
                    Integer.parseInt(createIndexes[1]), command[2],
                    command[3].equals("female"), command[4],
                    Boolean.parseBoolean(command[5]),
                    Boolean.parseBoolean(command[6]));
        } else {
            myEco.create(Integer.parseInt(createIndexes[0]),
                    Integer.parseInt(createIndexes[1]), command[2],
                    command[3].equals("female"), command[4], false, false);
        }
    }

    /**
     * Purpose: Moves the animals based on the command specification
     * 
     * @param command:
     *            The command line String
     * @param myEco:
     *            The ecosystem object
     */
    public static void moveCall(String[] command, Ecosystem myEco)
            throws ClassNotFoundException {
        List<String> speciesList = Arrays.asList("elephant", "rhinoceros",
                "lion", "giraffe", "zebra", "thrush", "owl", "warbler",
                "shrike", "mosquito", "bee", "fly", "ant");
        if (command.length == 1) {
            myEco.move();
        } else if (command[1].substring(0, 1).equals("(")) {
            // Move (row, column)
            myEco.move(Integer.parseInt(command[1].substring(1, 2)),
                    Integer.parseInt(command[1].substring(3, 4)));
        } else if (speciesList.contains(command[1])) {
            // type
            String lowerCommand = command[1].substring(0, 1).toLowerCase();
            myEco.moveType(lowerCommand);
        } else {
            // animal
            String lowerCommand = command[1].substring(0, 1).toUpperCase()
                    + command[1].substring(1).toLowerCase() + "s";
            myEco.moveAnimal(lowerCommand);
        }
    }

    /**
     * Purpose: Reproduces the animals based on the command specifications
     * 
     * @param command:
     *            the command line String
     * @param myEco:
     *            the Ecosystem object
     */
    public static void reproduceCall(String[] command, Ecosystem myEco) {
        List<String> speciesListRepro = Arrays.asList("elephant", "rhinoceros",
                "lion", "giraffe", "zebra", "thrush", "owl", "warbler",
                "shrike", "mosquito", "bee", "fly", "ant");
        if (command.length == 1) {
            myEco.reproduce();
        } else if (command[1].substring(0, 1).equals("(")) {
            // Reproduce (row, column)
            myEco.reproducer(Integer.parseInt(command[1].substring(1, 2)),
                    Integer.parseInt(command[1].substring(3, 4)));
        } else if (speciesListRepro.contains(command[1])) {
            // type
            String lowerCommand = command[1].substring(0, 1).toLowerCase();
            myEco.reproduce(lowerCommand);
        }
    }

    /**
     * Purpose: Handles the eat command
     * 
     * @param command:
     *            The command line String
     * @param myEco:
     *            the Ecosystem object
     */
    public static void eatCall(String[] command, Ecosystem myEco) {
        List<String> speciesListRepro = Arrays.asList("elephant", "rhinoceros",
                "lion", "giraffe", "zebra", "thrush", "owl", "warbler",
                "shrike", "mosquito", "bee", "fly", "ant");
        if (command.length == 1) {
            myEco.eat();
        } else if (command[1].substring(0, 1).equals("(")) {
            myEco.eater(Integer.parseInt(command[1].substring(1, 2)),
                    Integer.parseInt(command[1].substring(3, 4)));
        } else if (speciesListRepro.contains(command[1])) {
            // type
            String lowerCommand = command[1].substring(0, 1).toLowerCase();
            myEco.eat(lowerCommand);
        }
    }

    class HandleTextInput implements EventHandler<ActionEvent> {
        private GraphicsContext gc;
        private Ecosystem myEco;
        private TextArea command;

        public HandleTextInput(TextField cmd_in, GraphicsContext gc,
                Ecosystem myEco, TextArea command) {
            this.cmd_in = cmd_in;
            this.gc = gc;
            this.myEco = myEco;
            this.command = command;
        }

        @Override
        public void handle(ActionEvent event) {
            String line = cmd_in.getText();
            cmd_in.clear();
            if (!line.equals("")) {
                command.appendText(line + "\n");
            }
            String[] thisCommand = line.split("\\s+");
            Color c = Color.rgb(0, 255, 0);
            gc.setFill(c);
            // Background box
            gc.fillRect(0, 0, myEco.getList().get(0).size() * RECT_SIZE,
                    myEco.getList().size() * RECT_SIZE);
            switch (thisCommand[0].toLowerCase()) {
            case "create":
                createCall(thisCommand, myEco);
                break;
            case "move":
                try {
                    moveCall(thisCommand, myEco);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                break;
            case "reproduce":
                reproduceCall(thisCommand, myEco);
                break;
            case "eat":
                eatCall(thisCommand, myEco);
                break;
            }
            // Loops through the entire grid and assigns a different color
            // to each different species to then create the boxes
            for (int i = 0; i < myEco.getList().size(); i++) {
                for (int j = 0; j < myEco.getList().get(i).size(); j++) {
                    if (myEco.getList().get(i).get(j).size() > 0) {
                        String spec = myEco.getList().get(i).get(j).get(0)
                                .getSpec();
                        // e, r, l, g, z, t, o, w, s, m, b, f, a
                        switch (spec) {
                        case "e":
                            c = Color.rgb(0, 0, 0);
                            break;
                        case "r":
                            c = Color.rgb(100, 0, 0);
                            break;
                        case "l":
                            c = Color.rgb(200, 0, 0);
                            break;
                        case "g":
                            c = Color.rgb(0, 100, 0);
                            break;
                        case "z":
                            c = Color.rgb(0, 200, 0);
                            break;
                        case "t":
                            c = Color.rgb(0, 0, 100);
                            break;
                        case "o":
                            c = Color.rgb(0, 0, 200);
                            break;
                        case "w":
                            c = Color.rgb(100, 100, 0);
                            break;
                        case "s":
                            c = Color.rgb(100, 200, 0);
                            break;
                        case "m":
                            c = Color.rgb(100, 0, 100);
                            break;
                        case "b":
                            c = Color.rgb(100, 0, 200);
                            break;
                        case "f":
                            c = Color.rgb(100, 200, 200);
                            break;
                        case "a":
                            c = Color.rgb(100, 100, 200);
                            break;
                        }
                        drawTileDebug(gc, c, i * RECT_SIZE,
                                j * RECT_SIZE, RECT_SIZE);
                        }
                    }
            }
        }

        private TextField cmd_in;
    }

    // Helper method to draw a rectangular tile and output info in given
    // text area.
    private void drawTileDebug(GraphicsContext gc,
            Color colorname, int y, int x, int size) {
        gc.setFill(colorname);
        gc.fillRect(x, y, size, size);
    }
}
