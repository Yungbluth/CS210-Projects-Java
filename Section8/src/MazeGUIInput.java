
/**
 * E19MazeOutput reads a file in that is hard coded and
 * turns that file's character representation of a maze
 * into a drawing on a JavaFX canvas.
 * 
 * Adapted from the Section 10 code from CS210 Fall 2018.
 * 
 */
import java.io.File;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MazeGUIInput extends Application {
    // 25 x 25 pixel square
    final int SIZE = 20;
    // Points needed to draw a triangle
    final int TRIANGLE = 3;
    int x;
    int y;
    final int MOVE = 1;
    char[][] maze = readMaze("PublicTestCases/maze_02.txt");
    //Button nextMove;
    GraphicsContext gc;
    Button nextMove;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Process the input file and store it in a 2D array of characters
        primaryStage.setTitle("A-maze-ing!");

        // Canvas to scale to the size of the input file
        Canvas canvas = new Canvas(maze[0].length * SIZE, maze.length * SIZE);

        gc = canvas.getGraphicsContext2D();

        initScreenMaze(gc, maze);
     // Text field to take in maze command
        TextField command = new TextField();

        // Border pane will contain canvas for drawing and text area underneath
        BorderPane p = new BorderPane();

        // Input Area + nextMove Button
        Label cmdLabel = new Label("Type Command in TextField");
        HBox hb = new HBox(3);

        // Input + Text Output
        VBox vb = new VBox(2);

        setupNodes(hb, cmdLabel, vb, command);
        nextMove.setOnAction(new CommandHandler(command));

        p.setCenter(canvas);
        p.setBottom(vb);
        primaryStage.setScene(new Scene(p));
        primaryStage.show();
    }
    
    /*
     * Sets up the TextField, label, and button to be
     * at the bottom
     */
    private void setupNodes(HBox hb, Label cmd, VBox vb, TextField inputCmd) {

        nextMove = new Button("Simulation Step");

        hb.setSpacing(15);
        hb.getChildren().add(cmd);
        hb.getChildren().add(nextMove);

        vb.getChildren().add(hb);
        vb.getChildren().add(inputCmd);
    }

    /*
     * Iterates over the 2D array maze and draws the shape that corresponds
     * to the character.
     */
    public void initScreenMaze(GraphicsContext gc, char[][] maze) {
        // default is yellow because it is a corn maze
        gc.setFill(Color.YELLOW);

        // Two arrays that correspond to the points of the triangle to be drawn
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {

                // * fill in as yellow because it is a corn wall
                if (maze[i][j] == '*') {
                    gc.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);

                } else if (maze[i][j] == 'S') { // Color blue for start position
                                                // in maze
                    gc.setFill(Color.BLUE);
                    double[] yPoints = new double[] {
                            (double) (i * SIZE + SIZE), (double) SIZE * i,
                            (double) (SIZE * i + SIZE) };
                    double[] xPoints = new double[] { (double) j * SIZE,
                            (double) (SIZE * j + (SIZE / 2)),
                            (double) (SIZE * j + SIZE) };
                    // Could just pass arrays straight into fillPolygon
                    gc.fillPolygon(xPoints, yPoints, TRIANGLE);
                    gc.setFill(Color.YELLOW); // Set color back to yellow

                } else if (maze[i][j] == 'E') { // Color square green for end
                    gc.setFill(Color.GREEN);
                    gc.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
                    gc.setFill(Color.YELLOW); // Set color back to yellow
                }

            }
        }

    }

    /*
     * readMaze accepts a string argument that is the file name
     * Process the file and place its contents in the 2D character
     * array Maze. This 2D array is returned so that another function
     * can handle drawing on the Canvas.
     */
    public char[][] readMaze(String filename) {
        char[][] maze = null;

        try {
            Scanner in = new Scanner(new File(filename));
            int width = in.nextInt(), height = in.nextInt();
            in.nextLine();

            // Initialize array to match input file
            maze = new char[height][width];

            // Fill array with input characters
            for (int i = 0; i < height; i++) {
                int j = 0;
                String s = in.nextLine();
                for (char c : s.toCharArray()) {
                	if (c == 'S') {
                		x = j;
                		y = i;
                	}
                    maze[i][j++] = c;
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return maze;
    }
    
    public void parseLine(String line) {
        System.out.println("reading " + line); // Print line for debugging
        if (line.toLowerCase().compareTo("up") == 0 && y - MOVE >= 0
                && maze[y - MOVE][x] != '*') {
            move(y - MOVE, x);
        } else if (line.toLowerCase().compareTo("down") == 0 && y + MOVE <= maze.length
                && maze[y + MOVE][x] != '*') {
        	move(y + MOVE, x);
        } else if (line.toLowerCase().compareTo("left") == 0 && x - MOVE >= 0
                && maze[y][x - MOVE] != '*') {
        	move(y, x - MOVE);
        } else if (line.toLowerCase().compareTo("right") == 0 && x + MOVE <= maze.length
                && maze[y][x + MOVE] != '*') {
        	move(y, x + MOVE);
        }
    }
    
    /**
     * This function 'erases' the old triangle and redraws
     * it in the next move location to make it appear that
     * the triangle is navigating the maze. int newY and 
     * int newX represent the coordinates of the valid move. 
     */
    public void move(int newY, int newX) {
        gc.clearRect(x * SIZE, y * SIZE, SIZE, SIZE);
        double[] yPoints = new double[] { (double) (newY * SIZE + SIZE),
                (double) SIZE * newY, (double) (SIZE * newY + SIZE) };
        double[] xPoints = new double[] { (double) newX * SIZE,
                (double) (SIZE * newX + (SIZE / 2)),
                (double) (SIZE * newX + SIZE) };
        gc.setFill(Color.BLUE);
        gc.fillPolygon(xPoints, yPoints, TRIANGLE);
        x = newX;
        y = newY;
    }
    
	class CommandHandler implements EventHandler<ActionEvent> {
		private TextField command;
	
		CommandHandler(TextField command) {
			this.command = command;
		}
	
		/*
		 * Button EventHandler to take input command
		 * when button is clicked.
		 */
		@Override
		public void handle(ActionEvent event) {
			parseLine(command.getText());
	
		}
	
	}
}