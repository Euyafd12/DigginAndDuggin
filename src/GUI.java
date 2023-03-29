import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GUI extends JPanel implements KeyListener {

    public final Player player;
    private Graphics2D g2d;
    public final int width, height, groundHeight;
    private double highScore, score;
    private final double tempHiScore;
    private final Image imgBackground, imgICN, imgLogo, imgHiScore, imgScore;
    private Image imgDigDug, pauseButton;
    private final Map<String, Image> scoreMap;
    private final audioPlayer audioTrack;

    public GUI() throws FileNotFoundException {

        player = new Player();

        g2d = null;
        width = 915;
        height = 1040;
        groundHeight = 700;
        score = 0;

        highScore = new Scanner(new File("highscore.txt")).nextDouble();
        tempHiScore = highScore;

        audioTrack = new audioPlayer();
        scoreMap = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            scoreMap.put(String.valueOf(i), new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Score" + i + ".png"))).getImage());
        }

        imgBackground = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/background.png"))).getImage();
        imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteRight.png"))).getImage();
        imgICN = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/DigDugIcon.jpg"))).getImage();
        imgLogo = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Logo.png"))).getImage();
        imgScore = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/imgScore.png"))).getImage();
        imgHiScore = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/imgHi-Score.png"))).getImage();
        pauseButton =  new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Empty.png"))).getImage();
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
        audioTrack.play();
    }

    public void prepGUI() {

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);

        g2d.drawImage(imgLogo, 100, -10, 714, 318, null);
        g2d.drawImage(imgScore, 700, 400, 117, 21, null);
        g2d.drawImage(imgHiScore, 700, 500, 144, 48, null);
        g2d.drawImage(imgBackground, 0, 300, 181, groundHeight, null);
        g2d.drawImage(imgBackground, 180, 300, 181, groundHeight, null);
        g2d.drawImage(imgBackground, 360, 300, 181, groundHeight, null);
        g2d.drawImage(imgBackground, 470, 300, 181, groundHeight, null);

        int tempPos = 675;

        for (int i = 0; i < player.getLives(); i++) {

            g2d.drawImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteRight.png"))).getImage(), tempPos, 720, 100, 100, null);
            tempPos += 110;
        }
    }

    public void paintComponent(Graphics window) {

        super.paintComponent(window);
        g2d = (Graphics2D) window;

        prepGUI();

        if (score < 10000) {

            if (!player.path.contains(new Point(player.getX(), player.getY()))) {
                score += 0.25;
            }

            if (score > highScore && score == (int) score) {

                highScore = score;
            }
        }
        drawScore(score);

        g2d.fillRect(player.getX(), player.getY(), 40, 40);
        drawPath();
        drawDigDug();
        drawPause();
        pauseButton =  new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Empty.png"))).getImage();
    }


    public void drawScore(double score) {

        String sc = "" + (int) score;
        String hc = "" + (int) highScore;

        int xTemp = 750;

        for (int i = 0; i < sc.length(); i++) {
            g2d.drawImage(scoreMap.get(sc.substring(i, i + 1)), xTemp, 445, 24, 24, null);
            xTemp += 24;
        }

        xTemp = 750;

        for (int i = 0; i < hc.length(); i++) {
            g2d.drawImage(scoreMap.get(hc.substring(i, i + 1)), xTemp, 583, 24, 24, null);
            xTemp += 24;
        }
    }


    public void drawDigDug() {

        g2d.drawImage(imgDigDug, player.getX(), player.getY(), 40, 40, null);

        Point temp = new Point(player.getX(), player.getY());
        if (!player.path.contains(temp)) {
            player.path.add(temp);
        }
    }

    public void drawPath() {

        for (Point p : player.path) {
            g2d.fillRect(p.x, p.y, 40, 40);
        }
    }

    public void drawPause()
    {
        g2d.drawImage(pauseButton, 300, 300, 200, 200, null);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        Image dL = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteDownLeft.png"))).getImage();
        Image L = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteLeft.png"))).getImage();
        Image uL = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteUpLeft.png"))).getImage();

        int k = e.getKeyCode();

        switch (k) {

            case KeyEvent.VK_UP -> {

                if (imgDigDug.equals(dL) || imgDigDug.equals(L) || imgDigDug.equals(uL)) {
                    imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteUpLeft.png"))).getImage();
                } else {
                    imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteUpRight.png"))).getImage();
                }
            }
            case KeyEvent.VK_DOWN -> {

                if (imgDigDug.equals(dL) || imgDigDug.equals(L) || imgDigDug.equals(uL)) {
                    imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteDownLeft.png"))).getImage();
                } else {
                    imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteDownRight.png"))).getImage();
                }
            }
            case KeyEvent.VK_RIGHT -> imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteRight.png"))).getImage();
            case KeyEvent.VK_LEFT -> imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/SpriteLeft.png"))).getImage();
            case KeyEvent.VK_ESCAPE -> {
                pauseButton =new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/PauseButton.png"))).getImage();
            }
        }

        player.walk(k);
        player.checkBounds();

        repaint();

        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

    }


    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}