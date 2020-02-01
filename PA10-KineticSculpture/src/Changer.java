
public class Changer extends Node {

    private int x;
    private int y;
    private int id;
    private String color;

    public Changer(int x, int y, int id, String color) {
        super(x, y, id);
        this.x = x;
        this.y = y;
        this.id = id;
        this.color = color;
    }

    public String getColorChange() {
        return this.color;
    }

}
