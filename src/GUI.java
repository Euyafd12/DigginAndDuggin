import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI extends JPanel implements KeyListener {

    private Graphics2D g2d;
    public int[][] matrix;
    public int xPos, yPos;
    public final int width, height, groundHeight;
    private final Image imgBackground, imgDigDug;
    private final Color top, middleTop, middleBottom, bottom;

    public GUI() {

        g2d = null;
        width = 240 * 3;
        height = 320 * 3;
        groundHeight = 235 * 3;
        xPos = 0;
        yPos = 0;

        imgBackground = null;  //imgBackground = new ImageIcon(getClass().getResource("DigDugSprite.png")).getImage();
        imgDigDug = new ImageIcon(getClass().getResource("DigDugSprite1.png")).getImage();
        //imgDigDug = new ImageIcon(getClass().getResource("DigDugSprite2.png")).getImage();

        top = new Color(149, 148, 147, 255);
        middleTop = new Color(183, 151, 75, 255);
        middleBottom = new Color(253, 184, 1, 255);
        bottom = new Color(223, 99, 0, 255);
    }

    public void display(String s) {

        JFrame frame = new JFrame(s);
        frame.add(this);
        frame.addKeyListener(this);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.BLACK);
    }

    public void paintComponent(Graphics window) {

        super.paintComponent(window);
        g2d = (Graphics2D) window;
        createGround();
        moveDigDug();
    }

    public void moveDigDug() {

        g2d.drawImage(imgDigDug, xPos, yPos, 107, 107, null);
    }

    public void setDigDug(int x, int y) {

        xPos = x;
        yPos = y;
    }


    public void createGround() {

        /*Cycle through each pixel available

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
        */
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            yPos -= 5;
            System.out.println("UP");
        }
        else if (key == KeyEvent.VK_DOWN) {
            yPos += 5;
            System.out.println("DOWN");

        }
        else if (key == KeyEvent.VK_RIGHT) {
            xPos += 5;
            System.out.println("RIGHT");

        }
        else if (key == KeyEvent.VK_LEFT) {
            xPos -= 5;
            System.out.println("LEFT");

        }

        if (xPos < 0) xPos = 0;
        if (yPos < 0) yPos = 0;
        if (yPos > width) yPos = 0;
        if (xPos > height) xPos = 0;

        paintComponent(getGraphics());
    }




    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}