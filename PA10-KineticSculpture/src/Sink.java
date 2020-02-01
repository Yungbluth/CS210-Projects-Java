
public class Sink extends Node {

    /**
     * Purpose: Subclass of Node for use in PA10main.java
     */
    private int x;
    private int y;
    private int id;

    public Sink(int x, int y, int id) {
        super(x, y, id);
        this.x = x;
        this.y = y;
        this.id = id;
    }
}