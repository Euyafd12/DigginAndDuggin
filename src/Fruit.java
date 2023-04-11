import java.awt.*;
public class Fruit {
    private boolean eaten;
    private int appearCNT;
    private final Rectangle edge;
    private String type;

    public Fruit() {

        type = "";
        eaten = false;
        appearCNT = 0;
        edge = new Rectangle(0, 0, 0, 0);
    }

    public String getType() {
        return type;
    }

    public boolean isEaten() {
        return eaten;
    }

    public int getAppearCNT() {
        return appearCNT;
    }

    public Rectangle getBounds() {
        return edge;
    }

    public void upAppearCNT() {
       appearCNT++;
    }

    public void eat() {
        eaten = true;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBounds(int a, int b, int c, int d) {
        edge.setBounds(a, b, c, d);
    }
}
