import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class GUI extends JPanel implements KeyListener {

    private Graphics2D g2d;
    public int[][] matrix;
    public int xPos, yPos, score, highScore;
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
        score=0;
        highScore=100000;

        path = new ArrayList<>();

        imgBackground = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/background.png"))).getImage();
        imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteRight.png"))).getImage();
        imgICN = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/DigDugIcon.png"))).getImage();
        imgLogo = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Logo.png"))).getImage();
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

        audio("Assets/theme.wav");
    }

    public void paintComponent(Graphics window) {

        super.paintComponent(window);
        g2d = (Graphics2D) window;

        //Make black background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);

        //Dig Dug Logo
        g2d.drawImage(imgLogo, 100, -10, (int) (1072 / 1.5), (int) (478 / 1.5), null);

        //Create dirt
        g2d.drawImage(imgBackground, 0, 300, 181, groundHeight, null);
        g2d.drawImage(imgBackground, 180, 300, 181, groundHeight, null);
        g2d.drawImage(imgBackground, 360, 300, 181, groundHeight, null);
        g2d.drawImage(imgBackground, 470, 300, 181, groundHeight, null);
        g2d.setColor(Color.WHITE);
        g2d.drawString("High Score", 750, 385);
        g2d.drawString(String.valueOf(highScore), 750, 400);
        g2d.drawString("Score", 750, 340);
        g2d.drawString(String.valueOf(score), 750, 355);

        //Make Single black square on top of Doug
        g2d.fillRect(xPos, yPos, 40, 40);

        //Make him move and black path follows
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

    @Override
    public void keyPressed(KeyEvent e) {

        Image dL = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteDownLeft.png"))).getImage();
        Image L = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteLeft.png"))).getImage();
        Image uL = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteUpLeft.png"))).getImage();

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            yPos -= velocity;

            if (imgDigDug.equals(dL) || imgDigDug.equals(L) || imgDigDug.equals(uL)) {
                imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteUpLeft.png"))).getImage();
            }
            else {
                imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteUpRight.png"))).getImage();
            }
        }
        else if (key == KeyEvent.VK_DOWN) {
            yPos += velocity;

            if (imgDigDug.equals(dL) || imgDigDug.equals(L) || imgDigDug.equals(uL)) {
                imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteDownLeft.png"))).getImage();
            }
            else {
                imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteDownRight.png"))).getImage();
            }
        }
        else if (key == KeyEvent.VK_RIGHT) {
            xPos += velocity;
            imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteRight.png"))).getImage();
        }
        else if (key == KeyEvent.VK_LEFT) {
            xPos -= velocity;
            imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteLeft.png"))).getImage();
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