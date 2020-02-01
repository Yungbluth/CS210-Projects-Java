import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Test123 extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}
	
	public void start(Stage stage) {
		// Create the Canvas
        Canvas canvas = new Canvas(400, 400);
        // Get the graphics context of the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Set line width
        gc.setLineWidth(2.0);
        // Set fill color
        Color color = Color.rgb(80, 210, 110);
        gc.setStroke(color);
        Path path = new Path();
        CubicCurveTo cubicTo = new CubicCurveTo();
        cubicTo.setControlX1(150);
        cubicTo.setControlY1(20);
        cubicTo.setControlX2(150);
        cubicTo.setControlY2(150);
        cubicTo.setX(75);
        cubicTo.setY(150);
        path.getElements().addAll(new MoveTo(50, 50), cubicTo, new ClosePath());
        // Draw a rounded Rectangle
         
        // Create the Pane
        Pane root = new Pane();
        // Set the Style-properties of the Pane
        
        // Add the Canvas to the Pane
        root.getChildren().add(path);
        // Create the Scene
        Scene scene = new Scene(root);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title of the Stage
        stage.setTitle("Clearing the Area of a Canvas");
        // Display the Stage
        stage.show();     
		/**
		Stage stage = new Stage();
		Group root = new Group();
        Canvas canvas = new Canvas(300, 300);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.beginPath();
        gc.moveTo(50, 50);
        gc.bezierCurveTo(150, 20, 150, 150, 75, 150);
        gc.closePath();
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();
        */
        

        

	}


}
