import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;


public class GUI extends JPanel implements KeyListener {

    private final Player player;
    private Graphics2D g2d;
    public final int width, height, groundHeight;
    private double highScore, score;
    private final double tempHiScore;
    private final Image imgBackground, imgICN, imgLogo, imgHiScore, imgScore;
    private Image imgDigDug;
    private final ArrayList<Point> path;

    private final Map<String, Image> scoreMap;

    public GUI() throws FileNotFoundException {

        player = new Player();

        g2d = null;
        width = 915;
        height = 1040;
        groundHeight = 700;
        score = 0;

        highScore = new Scanner(new File("highscore.txt")).nextDouble();
        tempHiScore = highScore;

        path = new ArrayList<>();
        scoreMap = new HashMap<>();


        //Create Map of Scoreboard Number Icons linked to Integers
        for (int i = 0; i < 10; i++) {
            scoreMap.put(String.valueOf(i), new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Score" + i + ".png"))).getImage());
        }

        imgBackground = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/background.png"))).getImage();
        imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteRight.png"))).getImage();
        imgICN = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/DigDugIcon.jpg"))).getImage();
        imgLogo = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Logo.png"))).getImage();
        imgScore = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/imgScore.png"))).getImage();
        imgHiScore = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/imgHi-Score.png"))).getImage();

    }


    public void display() {

        JFrame frame = new JFrame("Dig Dug");
        frame.add(this);
        frame.addKeyListener(this);
        frame.setIconImage(imgICN);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        //When [X] is Pressed, Clear the Highscore.txt Files and Replace is With Current Highscore
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                if (tempHiScore != highScore) {
                    try {
                        PrintWriter pw = new PrintWriter("highscore.txt");
                        pw.append(String.valueOf(highScore));
                        pw.close();
                    } catch (Exception ignored) {
                    }
                }
            }
        });

        //Start Theme
        audio("Assets/theme.wav");
    }

    public void paintComponent(Graphics window) {

        super.paintComponent(window);
        g2d = (Graphics2D) window;

        //Make Black Background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);

        //Draw Dig Dug UI
        g2d.drawImage(imgLogo, 100, -10, 714, 318, null);
        g2d.drawImage(imgScore, 700, 400, 39 * 3, 7 * 3, null);
        g2d.drawImage(imgHiScore, 700, 500, 46 * 3, 16 * 3, null);

        //Create Dirt
        g2d.drawImage(imgBackground, 0, 300, 181, groundHeight, null);
        g2d.drawImage(imgBackground, 180, 300, 181, groundHeight, null);
        g2d.drawImage(imgBackground, 360, 300, 181, groundHeight, null);
        g2d.drawImage(imgBackground, 470, 300, 181, groundHeight, null);

        //Make Single black square on top of Doug
        g2d.fillRect(player.xPos, player.yPos, 40, 40);

        //Scoring System
        if (score < 10000) {

            if (!path.contains(new Point(player.xPos, player.yPos))) {
                score += 0.25;
            }

            if (score > highScore && score == (int) score) {

                highScore = score;
            }
        }
        drawScore(score);

        //Make him move and black path follows
        drawPath();
        drawDigDug();


    }



    public void drawScore(double score) {

        String sc = "" + (int) score;
        String hc = "" + (int) highScore;

        int xTemp = 750;
        int xxTemp = 750;

        //Search Through Map for Matching Character and Draw it

        for (int i = 0; i < sc.length(); i++) {
            g2d.drawImage(scoreMap.get(sc.substring(i, i + 1)), xTemp, 445, 24, 24, null);
            xTemp += 24;
        }

        for (int i = 0; i < hc.length(); i++) {
            g2d.drawImage(scoreMap.get(hc.substring(i, i + 1)), xxTemp, 535 + 48, 24, 24, null);
            xxTemp += 24;
        }
    }


    public void drawDigDug() {

        //Draw Dig Dug Position and Save Existing Position
        g2d.drawImage(imgDigDug, player.xPos, player.yPos, 40, 40, null);

        Point temp = new Point(player.xPos, player.yPos);
        if (!path.contains(temp)) {
            path.add(temp);
        }
    }

    public void drawPath() {

        //Go Through ALl Previous Paths and Draw Black Spot
        for (Point p : path) {
            g2d.fillRect(p.x, p.y, 40, 40);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        //Temp Left positions
        Image dL = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteDownLeft.png"))).getImage();
        Image L = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteLeft.png"))).getImage();
        Image uL = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteUpLeft.png"))).getImage();

        int key = e.getKeyCode();

        //Check What Key is Pressed, and Compare What the Current Sprite Orientation to Left Facing Positions
        if (key == KeyEvent.VK_UP) {

            player.walkUp();

            if (imgDigDug.equals(dL) || imgDigDug.equals(L) || imgDigDug.equals(uL)) {
                imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteUpLeft.png"))).getImage();
            } else {
                imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteUpRight.png"))).getImage();
            }
        } else if (key == KeyEvent.VK_DOWN) {

            player.walkDown();

            if (imgDigDug.equals(dL) || imgDigDug.equals(L) || imgDigDug.equals(uL)) {
                imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteDownLeft.png"))).getImage();
            } else {
                imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteDownRight.png"))).getImage();
            }
        } else if (key == KeyEvent.VK_RIGHT) {

            player.walkRight();
            imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteRight.png"))).getImage();
        } else if (key == KeyEvent.VK_LEFT) {

            player.walkLeft();
            imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteLeft.png"))).getImage();
        }

        //Keep Dig Dug in Bounds
        player.checkBounds();

        repaint();
    }

    public void audio(String a) {

        //Take in Audio File Name and Continuously Play the Associated File
        try {
            AudioInputStream system = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource(a)));
            Clip sound = AudioSystem.getClip();
            sound.open(system);
            sound.start();
            sound.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ignore) {
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}