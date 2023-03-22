import javax.swing.*;
import java.awt.*;

public class GUI extends JPanel {

    private Graphics2D g2d;
    public int[][] matrix;
    public final int width, height, groundHeight;

    private final Color top, middleTop, middleBottom, bottom;

    public GUI() {

        g2d = null;
        width = 240 * 3;
        height = 320 * 3;
        groundHeight = 235 * 3;

        top = new Color(149, 148, 147, 255);
        middleTop = new Color(183, 151, 75, 255);
        middleBottom = new Color(253, 184, 1, 255);
        bottom = new Color(223, 99, 0, 255);
    }

    public void display(String s) {

        JFrame frame = new JFrame(s);
        frame.add(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.BLACK);
    }

    public void paintComponent(Graphics window) {

        g2d = (Graphics2D) window;
        createGround();
    }

    public void createGround() {

        //Cycle through each pixel available

        for (int r = 0; r < matrix.length; r++) {

            for (int c = 0; c < matrix[r].length; c++) {


                //Match layer of rock (Coloring)

                if (c <= (groundHeight / 4) + (int) (Math.random() * 35)) {
                    g2d.setColor(top);
                } else if (c <= groundHeight / 2 + (int) (Math.random() * 20)) {
                    g2d.setColor(middleTop);
                } else if (c <= (groundHeight / 2 + groundHeight / 4) + (int) (Math.random() * 15)) {
                    g2d.setColor(middleBottom);
                } else {
                    g2d.setColor(bottom);
                }

                g2d.drawRect(r, c + 215, 1, 1);
            }
        }
    }
}