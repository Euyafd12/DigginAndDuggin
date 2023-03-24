import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Objects;

public class GUI extends JPanel implements KeyListener {

    private Graphics2D g2d;
    public int[][] matrix;
    public int xPos, yPos;
    public final int width, height, groundHeight, velocity;
    private final ArrayList<Point> path;
    private Image imgBackground, imgDigDug, imgICN, imgLogo;


    public GUI() {

        velocity = 10;
        g2d = null;
        width = 915;
        height = 1040;
        groundHeight = 700;
        xPos = 0;
        yPos = 0;

        path = new ArrayList<>();

        imgBackground = new ImageIcon(Objects.requireNonNull(getClass().getResource("background.png"))).getImage();
        imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("SpriteRight.png"))).getImage();
        imgICN = new ImageIcon(Objects.requireNonNull(getClass().getResource("DigDugIcon.png"))).getImage();
        imgLogo = new ImageIcon(Objects.requireNonNull(getClass().getResource("Logo.png"))).getImage();
    }

    public void display(String s) {

        JFrame frame = new JFrame(s);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setIconImage(imgICN);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        audio("theme.wav");

    }

    public void paintComponent(Graphics window) {

        super.paintComponent(window);
        g2d = (Graphics2D) window;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);

        g2d.drawImage(imgLogo, 100, -10, (int) (1072 / 1.5), (int) (478 / 1.5), null);

        g2d.drawImage(imgBackground, 0, 300, 181, groundHeight, null);
        g2d.drawImage(imgBackground, 180, 300, 181, groundHeight, null);
        g2d.drawImage(imgBackground, 360, 300, 181, groundHeight, null);
        g2d.drawImage(imgBackground, 470, 300, 181, groundHeight, null);

        g2d.fillRect(xPos, yPos, 40, 40);

        drawPath();
        moveDigDug();
    }

    public void moveDigDug() {

        g2d.drawImage(imgDigDug, xPos, yPos, 40, 40, null);
        Point temp = new Point(xPos, yPos);
        path.add(temp);
    }
    public void drawPath() {

        g2d.setColor(Color.BLACK);

        for (Point p : path) {
            g2d.fillRect(p.x, p.y, 40, 40);
        }
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

        Image dL = new ImageIcon(Objects.requireNonNull(getClass().getResource("SpriteDownLeft.png"))).getImage();
        Image L = new ImageIcon(Objects.requireNonNull(getClass().getResource("SpriteLeft.png"))).getImage();
        Image uL = new ImageIcon(Objects.requireNonNull(getClass().getResource("SpriteUpLeft.png"))).getImage();

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            yPos -= velocity;

            if (imgDigDug.equals(dL) || imgDigDug.equals(L) || imgDigDug.equals(uL)) {
                imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("SpriteUpLeft.png"))).getImage();
            }
            else {
                imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("SpriteUpRight.png"))).getImage();
            }
        }
        else if (key == KeyEvent.VK_DOWN) {
            yPos += velocity;

            if (imgDigDug.equals(dL) || imgDigDug.equals(L) || imgDigDug.equals(uL)) {
                imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("SpriteDownLeft.png"))).getImage();
            }
            else {
                imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("SpriteDownRight.png"))).getImage();
            }
        }
        else if (key == KeyEvent.VK_RIGHT) {
            xPos += velocity;
            imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("SpriteRight.png"))).getImage();
        }
        else if (key == KeyEvent.VK_LEFT) {
            xPos -= velocity;
            imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("SpriteLeft.png"))).getImage();
        }

        if (xPos < 0) xPos = 0;
        if (xPos > 611) xPos = 611;
        if (yPos < 270) yPos = 270;
        if (yPos > height - 80) yPos = height - 80;

        repaint();
    }

    public void audio(String a) {

        try {
            AudioInputStream system = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource(a)));
            Clip sound = AudioSystem.getClip();
            sound.open(system);
            sound.start();
            sound.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ignore) {}
    }




    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}