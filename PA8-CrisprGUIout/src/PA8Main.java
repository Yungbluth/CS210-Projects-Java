
/*
 * TODO: Write a file header!
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PA8Main extends Application {
    /**
     * AUTHOR: Matthew Yungbluth
     * File: PA8Main.java
     * ASSIGNMENT: Programming Assignment 8 - CrisprGUIout
     * COURSE: CSc 210; Section C; Spring 2019
     * PURPOSE: This program reads in a file ecosystem commands and prints the
     * effect those commands have on the ecosystem and also provides a graphical
     * output of the ecosystem
     * 
     * USAGE:
     * java PA6Main inputFile
     * where inputFile is a file with commands to execute
     * 
     * Example input:
     * inputFile
     * inputFile:
     * rows: 5
     * cols: 5
     * delay: .5
     * 
     * CREATE (1,1) bee male true
     * PRINT
     * MOVE (1,1)
     * PRINT
     * 
     * Output for above input:
     * > PRINT
     * .....
     * .b...
     * .....
     * .....
     * .....
     * 
     * > MOVE (1,1)
     * 
     * > PRINT
     * .....
     * b....
     * .....
     * .....
     * .....
     *
     * ALL POSSIBLE COMMAND:
     * CREATE (i, j) type male/female extra
     * PRINT
     * MOVE (i,j)/type/animal
     * REPRODUCE (i,j)/type
     * EAT (i,j)/type
     */

    // variables that will be read in from file
    private static double delay = 0;

    // constants for the program
    private final static int TEXT_SIZE = 120;
    private final static int RECT_SIZE = 20;

    public static void main(String[] args) throws FileNotFoundException {
        launch(args);
    }

    /**
     * Purpose: The start of the program
     */
    @Override
    public void start(Stage primaryStage)
            throws FileNotFoundException, ClassNotFoundException {
        Parameters params = getParameters();
        List<String> args = params.getRaw();
        TextArea command = new TextArea();
        int rows = 0;
        int cols = 0;
        Ecosystem myEco = new Ecosystem();
        Scanner inputFile = new Scanner(new File(args.get(0)));
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            String[] thisCommand = line.split("\\s+");
            switch (thisCommand[0].toLowerCase()) {
            case "rows:":
                rows = Integer.parseInt(thisCommand[1]);
                break;
            case "cols:":
                cols = Integer.parseInt(thisCommand[1]);
                break;
            case "delay:":
                delay = Double.parseDouble(thisCommand[1]);
                break;
            }
            if (rows != 0 && cols != 0 && delay != 0.0) {
                ArrayList<ArrayList<ArrayList<Animal>>> myList = myEco
                        .getList();
                for (int i = 0; i < rows; i++) {
                    myList.add(new ArrayList<ArrayList<Animal>>());
                    for (int j = 0; j < cols; j++) {
                        myList.get(i).add(new ArrayList<Animal>());
                    }
                }
                break;
            }
        }
        GraphicsContext gc = setupStage(primaryStage, cols * RECT_SIZE,
                rows * RECT_SIZE,
                command);
        primaryStage.show();
        simulateEcosystem(inputFile, gc, command, args, myEco);
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
            System.out.println("> MOVE");
            myEco.move();
            System.out.println();
        } else if (command[1].substring(0, 1).equals("(")) {
            // Move (row, column)
            System.out.println("> MOVE " + command[1]);
            myEco.move(Integer.parseInt(command[1].substring(1, 2)),
                    Integer.parseInt(command[1].substring(3, 4)));
            System.out.println();
        } else if (speciesList.contains(command[1])) {
            // type
            System.out.println("> MOVE " + command[1]);
            String lowerCommand = command[1].substring(0, 1).toLowerCase();
            myEco.moveType(lowerCommand);
        } else {
            // animal
            System.out.println("> MOVE " + command[1]);
            String lowerCommand = command[1].substring(0, 1).toUpperCase()
                    + command[1].substring(1).toLowerCase() + "s";
            myEco.moveAnimal(lowerCommand);
            System.out.println();
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
            System.out.println("> REPRODUCE");
            myEco.reproduce();
            System.out.println();
        } else if (command[1].substring(0, 1).equals("(")) {
            // Reproduce (row, column)
            System.out.println("> REPRODUCE " + command[1]);
            myEco.reproducer(Integer.parseInt(command[1].substring(1, 2)),
                    Integer.parseInt(command[1].substring(3, 4)));
            System.out.println();
        } else if (speciesListRepro.contains(command[1])) {
            // type
            System.out.println("> REPRODUCE " + command[1]);
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
            System.out.println("> EAT");
            myEco.eat();
            System.out.println();
        } else if (command[1].substring(0, 1).equals("(")) {
            System.out.println("> EAT " + command[1]);
            myEco.eater(Integer.parseInt(command[1].substring(1, 2)),
                    Integer.parseInt(command[1].substring(3, 4)));
            System.out.println();
        } else if (speciesListRepro.contains(command[1])) {
            // type
            System.out.println("> EAT " + command[1]);
            String lowerCommand = command[1].substring(0, 1).toLowerCase();
            myEco.eat(lowerCommand);
        }
    }

    /**
     * Purpose: handles the input file and the waiting for the animations
     * 
     * @param gc
     *            GraphicsContext for drawing ecosystem to.
     * @param command
     *            Reference to text area to show simulation commands.
     */
    private void simulateEcosystem(Scanner inputFile, GraphicsContext gc,
            TextArea command,
            List<String> args, Ecosystem myEco)
            throws FileNotFoundException, ClassNotFoundException {
        // Update GUI based on value of delay(seconds to wait)
        // The below code constructs a PauseTransition and then
        // passes a lambda function into the setOnFinished method.
        // A lambda function is an unnamed function.  Here, the
        // unnamed function takes a parameter it names "e"
        // and then parses the next command out of the file,
        // applies the command to the Ecosystem, and then tells the
        // wait thread to pause for the delay again.
        PauseTransition wait = new PauseTransition(Duration.seconds(delay));
        wait.setOnFinished(
                new CommandHandler(inputFile, gc, command, wait, myEco));
        // Now that the PauseTransition thread is setup, get it going.
        wait.play();
    }

    /**
     * Purpose: Reads in the input file and manipulates the ecosystem
     */
    class CommandHandler implements EventHandler<ActionEvent> {
        private GraphicsContext gc;
        private TextArea command;
        private PauseTransition wait;
        private Ecosystem myEco;
        private Scanner inputFile;

        CommandHandler(Scanner inputFile, GraphicsContext gc,
                TextArea command,
                PauseTransition wait, Ecosystem myEco) {
            this.inputFile = inputFile;
            this.gc = gc;
            this.command = command;
            this.wait = wait;
            this.myEco = myEco;
        }

        @Override
        public void handle(ActionEvent e) {
            if (inputFile.hasNextLine()) {
                String line = inputFile.nextLine();
                if (!line.equals("")) {
                    command.appendText(line + "\n");
                }
                String[] thisCommand = line.split("\\s+");
                Color c = Color.rgb(0, 255, 0);
                gc.setFill(c);
                // Background box
                gc.fillRect(0, 0, myEco.getList().size() * RECT_SIZE,
                        myEco.getList().size() * RECT_SIZE);
                switch (thisCommand[0].toLowerCase()) {
                case "create":
                    createCall(thisCommand, myEco);
                    break;
                case "print":
                    myEco.print();
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
                            drawTileDebug(command, gc, c, i * RECT_SIZE,
                                    j * RECT_SIZE, RECT_SIZE);
                            }
                        }
                    }
                wait.playFromStart();
            } else {
                wait.stop();
                }
        }
    }

    /**
     * Sets up the whole application window and returns the GraphicsContext from
     * the canvas to enable later drawing. Also sets up the TextArea, which
     * should be originally be passed in empty.
     * PA8 Notes: You shouldn't need to modify this method.
     * 
     * @param primaryStage
     *            Reference to the stage passed to start().
     * @param canvas_width
     *            Width to draw the canvas.
     * @param canvas_height
     *            Height to draw the canvas.
     * @param command
     *            Reference to a TextArea that will be setup.
     * @return Reference to a GraphicsContext for drawing on.
     */
    public GraphicsContext setupStage(Stage primaryStage, int canvas_width,
            int canvas_height, TextArea command) {
        // Border pane will contain canvas for drawing and text area underneath
        BorderPane p = new BorderPane();

        // Canvas(pixels across, pixels down)
        // Note this is opposite order of parameters of the Ecosystem in PA6.
        Canvas canvas = new Canvas(canvas_width, canvas_height);

        // Command TextArea will hold the commands from the file
        command.setPrefHeight(TEXT_SIZE);
        command.setEditable(false);

        // Place the canvas and command output areas in pane.
        p.setCenter(canvas);
        p.setBottom(command);

        // Title the stage and place the pane into the scene into the stage.
        primaryStage.setTitle("Ecosystem");
        primaryStage.setScene(new Scene(p));

        return canvas.getGraphicsContext2D();
    }

    // Helper method to draw a rectangular tile and output info in given
    // text area.
    private void drawTileDebug(TextArea command, GraphicsContext gc,
            Color colorname, int x, int y, int size) {
        gc.setFill(colorname);
        gc.fillRect(x, y, size, size);
    }

}
