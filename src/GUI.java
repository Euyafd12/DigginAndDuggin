import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.*;


public class GUI extends JPanel implements KeyListener {

    private Graphics2D g2d;

    public int xPos, yPos, score, highScore;
    public final int width, height, groundHeight, velocity;
    private final ArrayList<Point> path;
    private Image imgBackground, imgDigDug, imgICN, imgLogo;
    private Map<String, Image> scoreMap;
    //private PrintWriter writer;

    public GUI() throws FileNotFoundException {

        velocity = 10;
        g2d = null;
        width = 915;
        height = 1040;
        groundHeight = 700;
        xPos = 0;
        yPos = 0;
        score = 0;
        highScore = 67;

        /*
        writer = new PrintWriter("highscore.txt");
        Scanner s = new Scanner(new File("highscore.txt"));
        System.out.println(s.hasNext());
        highScore = s.nextInt();
        */



        path = new ArrayList<>();
        scoreMap = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            scoreMap.put(String.valueOf(i), new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Score" + i + ".png"))).getImage());
        }

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

        //Make Single black square on top of Doug
        g2d.fillRect(xPos, yPos, 40, 40);

        //Make him move and black path follows
        drawPath();
        moveDigDug();
        score++;
        if (score > highScore) highScore = score;
        //writer.flush();
        //writer.append("" + highScore);
        drawScore(score);
    }

    public void drawScore(int score) {

        String sc = "" + score;
        String hc = "" + highScore;

        int xTemp = 750;
        int xxTemp = 750;

        for (int i = 0; i < sc.length(); i++) {
            g2d.drawImage(scoreMap.get(sc.substring(i, i+1)), xTemp, 385, 24, 24, null);
            xTemp += 24;
        }

        for (int i = 0; i < hc.length(); i++) {
            g2d.drawImage(scoreMap.get(hc.substring(i, i+1)), xxTemp, 385+48, 24, 24, null);
            xxTemp += 24;
        }
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