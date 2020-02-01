import java.util.HashMap;
import java.util.Map;

import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * List of changes: Added getter method for edgeToLineMap
 * Changed edgeToLineMap and edgeToMarbleMap from mapping ids to mapping Node
 * objects
 * Added method to show text of queued circles above nodes
 */
public class JavaFXView {
    private final static double circle_radius = 10;

    public Group root = new Group();

    private HashMap<Node, Map<Node, Line>> edgeToLineMap = new HashMap<>();
    private HashMap<Node, Map<Node, Circle>> edgeToMarbleMap = new HashMap<>();

    // Set up the background.
    public JavaFXView(int window_width, int window_height) {
        Rectangle background = new Rectangle(0, 0, window_width, window_height);
        background.setFill(Color.AQUA);

        root.getChildren().add(background);
    }

    public HashMap<Node, Map<Node, Line>> getLines() {
        return edgeToLineMap;
    }

    // Create a rectangle for a node.
    public void initNode(Node node_id, int startX, int startY, int size) {
        Rectangle node = new Rectangle(startX, startY, size, size);
        node.setStroke(Color.BLACK);
        node.setStrokeWidth(2);
        node.setFill(Color.WHITE);
        root.getChildren().add(node);

        // Assume this node may have edges coming out of it
        edgeToLineMap.put(node_id, new HashMap<Node, Line>());
        edgeToMarbleMap.put(node_id, new HashMap<Node, Circle>());
    }
    
    // Create an edge.
    public void initEdge(Node source_id, Node target_id, int startX, int startY,
            int endX, int endY) {
        Line edge = new Line(startX, startY, endX, endY);
        root.getChildren().add(edge);
        edgeToLineMap.get(source_id).put(target_id, edge);
    }

    public void edgeTransition(Node source_id, Node target_id,
            String marble_color, double delay) {

        // create a new marble, associate it with edge, and do transition
        Circle marble = new Circle();
        marble.setFill(Color.valueOf(marble_color.trim()));
        marble.setRadius(circle_radius);
        edgeToMarbleMap.get(source_id).put(target_id, marble);
        root.getChildren().add(marble);
        Line path = edgeToLineMap.get(source_id).get(target_id);
        PathTransition trans = new PathTransition(Duration.seconds(delay), path,
                marble);
        trans.play();
    }

    public void clearEdge(Node source_id, Node target_id) {
        // remove the Circle currently associated with this edge
        Circle marble = edgeToMarbleMap.get(source_id).get(target_id);
        root.getChildren().remove(marble);
    }

    private Circle circleClone(Circle toClone) {
        Circle clone = new Circle();
        clone.setFill(toClone.getFill());
        clone.setRadius(toClone.getRadius());
        return clone;
    }

    /*
     * Purpose: Creates and updates text above nodes to show queued circles
     */
    public void addText(Node node) {
        if (root.getChildren().contains(node.getText())) {
            root.getChildren().remove(node.getText());
        }
        Text display = node.getText();
        display.setText("Queued: " + node.getInputList().toString());
        display.setX(node.getX() - 3);
        display.setY(node.getY() - 5);
        display.prefWidth(node.getX() + 6);
        if (node instanceof Sink) {
            display.setText("[]");
        }
        root.getChildren().add(display);
    }
}
