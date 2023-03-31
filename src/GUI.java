
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GUI extends JPanel implements KeyListener {
    private Graphics2D g2d;
    private double highScore, score;
    private Image imgDigDug, pauseButton;
    private Color pauseFade;
    private final Player player;
    private final Enemy enemy;
    private final int width, height, groundHeight;
    private boolean pausePlay;
    private final Image imgBackground;
    private final Image imgICN;
    private final Image imgLogo;
    private final Image imgHiScore;
    private final Image imgScore;
    private Image Watermelon;
    private final Image plus300;
    private final Map<String, Image> scoreMap;

    private Rectangle waterbox;


    public GUI() throws FileNotFoundException {

        player = new Player();
        enemy = new Enemy();

        pauseFade = new Color(0, 0, 0, 0);

        pausePlay = true;

        g2d = null;
        width = 915;
        height = 1040;
        groundHeight = 700;
        score = 0;

        highScore = new Scanner(new File("src/Assets/Scoreboard/highscore.txt")).nextDouble();

        scoreMap = new HashMap<>();

        //Load in map of scoreboard numbers
        for (int i = 0; i < 10; i++) {
            scoreMap.put(String.valueOf(i), new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Scoreboard/Score" + i + ".png"))).getImage());
        }

        imgBackground = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Background/background.png"))).getImage();
        imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougRight.png"))).getImage();
        imgICN = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Background/DigDugIcon.jpg"))).getImage();
        imgLogo = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Background/Logo.png"))).getImage();
        imgScore = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Scoreboard/imgScore.png"))).getImage();
        imgHiScore = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Scoreboard/imgHi-Score.png"))).getImage();
        pauseButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Empty.png"))).getImage();
        Watermelon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Watermelon.png"))).getImage();
        plus300= new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Points300.png"))).getImage();
        waterbox = new Rectangle(500, 625, 16, 9);
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

                //On [X} click, clear highscore.txt, write new highscore and close the file
                saveScore();
            }
        });
        audioPlay();
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isPausePlay() {
        return pausePlay;
    }

    public void prepGUI() {

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);

        //Draw each piece of background
        g2d.drawImage(imgLogo, 100, -10, 714, 318, null);
        g2d.drawImage(imgScore, 700, 400, 117, 21, null);
        g2d.drawImage(imgHiScore, 700, 500, 144, 48, null);
        g2d.drawImage(imgBackground, 0, 300, 181, groundHeight, null);
        g2d.drawImage(imgBackground, 180, 300, 181, groundHeight, null);
        g2d.drawImage(imgBackground, 360, 300, 181, groundHeight, null);
        g2d.drawImage(imgBackground, 470, 300, 181, groundHeight, null);

        int tempPos = 675;

        //Draw lives icon, reduced with each death
        for (int i = 0; i < player.getLives(); i++) {

            g2d.drawImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougRight.png"))).getImage(), tempPos, 720, 100, 100, null);
            tempPos += 110;
        }
        g2d.drawImage(Watermelon, 500, 625, 16, 9, null);
    }

    public void paintComponent(Graphics window) {

        super.paintComponent(window);
        g2d = (Graphics2D) window;

        //Display Background
        prepGUI();

        //Draw Scoreboard
        if (score < 10000) {

            //Prevents Doug from getting points by walking over old path
            if (!player.path.contains(new Point(player.getX(), player.getY()))) {
                score += 0.25;
            }

            if (score > highScore) {
                highScore = score;
            }
        }
        drawScore(score);

        //Display Doug
        g2d.fillRect(player.getX(), player.getY(), 40, 40);
        drawPath();
        drawDigDug();

        //Make enemy move towards Doug and display
        enemy.walkTowards(player.getX(), player.getY());
        drawEnemy();

        //Check for collision between Doug and enemy
        collisionCheck();

        //Use [Escape] as switch, if on - display pause menu and freeze game
        drawPause();
    }


    public void drawScore(double score) {

        //Represent score as string, go through each character and display correct number image

        String sc = "" + (int) score ;
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

        //Draw Doug, add position to existing path list
        g2d.drawImage(imgDigDug, player.getX(), player.getY(), 40, 40, null);

        Point temp = new Point(player.getX(), player.getY());
        if (!player.path.contains(temp)) {
            player.path.add(temp);
        }
    }

    public void drawEnemy() {

        //Draw enemy
        Image img = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/GogglesRight.png"))).getImage();

        g2d.drawImage(img, enemy.getX(), enemy.getY(), 40, 40, null);
    }

    public void drawPath() {

        //Draw boxes to represent Doug's dug path
        for (Point p : player.path) {
            g2d.fillRect(p.x, p.y, 40, 40);
        }
    }

    public void collisionCheck() {

        //See if Doug and Enemy intersect, lose life and teleport Doug away
        Rectangle plyr = new Rectangle(player.getX(), player.getY(), 40, 40);
        Rectangle enmy = new Rectangle(enemy.getX(), enemy.getY(), 40, 40);

        if (plyr.intersects(enmy)) {

            player.dropLives();
            player.escapeEnemy();
            repaint();
        }
        if(plyr.intersects(waterbox))
        {
            Watermelon= new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Empty.png"))).getImage();
        }

    }

    public void drawPause() {

        //Draw pause menu, if [Escape] switch on, color background transparent black and draw pause button

        g2d.setColor(pauseFade);
        g2d.fillRect(0, 0, width, height);

        g2d.drawImage(pauseButton, width / 2 - 400, height / 2 - 150, 398 * 2, 126 * 2, null);
    }


    public void keyPressed(KeyEvent e) {

        //Temporary comparison images for Doug's face movement
        Image dL = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougDownLeft.png"))).getImage();
        Image L = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougLeft.png"))).getImage();
        Image uL = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougUpLeft.png"))).getImage();

        Image pB = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/PauseButton.png"))).getImage();

        int k = e.getKeyCode();

        //Compare Doug's original orientation and depending on direction display next movement
      /*  switch (k) {

            case KeyEvent.VK_UP -> {

                player.setVelY(-player.getVelocity());

                if (imgDigDug.equals(dL) || imgDigDug.equals(L) || imgDigDug.equals(uL)) {
                    imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougUpLeft.png"))).getImage();
                } else {
                    imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougUpRight.png"))).getImage();
                }
            }

            case KeyEvent.VK_DOWN -> {

                player.setVelY(player.getVelocity());

                if (imgDigDug.equals(dL) || imgDigDug.equals(L) || imgDigDug.equals(uL)) {
                    imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougDownLeft.png"))).getImage();
                } else {
                    imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougDownRight.png"))).getImage();
                }
            }

            case KeyEvent.VK_RIGHT -> {
                player.setVelX(player.getVelocity());
                imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougRight.png"))).getImage();
            }

            case KeyEvent.VK_LEFT -> {
                player.setVelX(-player.getVelocity());
                imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougLeft.png"))).getImage();
            }

            case KeyEvent.VK_ESCAPE -> {

                if (!pauseButton.equals(pB)) {
                    pauseButton = pB;
                    pauseFade = new Color(0, 0, 0, 180);
                } else {
                    pauseButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Empty.png"))).getImage();
                    pauseFade = new Color(0, 0, 0, 0);
                }
                pausePlay = !pausePlay;
                repaint();
            }*/
        player.setVelX(0);
        player.setVelY(0);
        if(k==KeyEvent.VK_LEFT)
        {
            player.setVelX(-player.getVelocity());
            imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougLeft.png"))).getImage();
        }
        if(k==KeyEvent.VK_RIGHT)
        {
            player.setVelX(player.getVelocity());
            imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougRight.png"))).getImage();
        }
        if(player.getVelX()==0)
        {
            if(k==KeyEvent.VK_UP)
            {
                player.setVelY(-player.getVelocity());

                if (imgDigDug.equals(dL) || imgDigDug.equals(L) || imgDigDug.equals(uL)) {
                    imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougUpLeft.png"))).getImage();
                } else {
                    imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougUpRight.png"))).getImage();
                }
            }
            if(k==KeyEvent.VK_DOWN)
            {
                player.setVelY(player.getVelocity());

                if (imgDigDug.equals(dL) || imgDigDug.equals(L) || imgDigDug.equals(uL)) {
                    imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougDownLeft.png"))).getImage();
                } else {
                    imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougDownRight.png"))).getImage();
                }
            }

        }
        if(k==KeyEvent.VK_ESCAPE)
        {
            if (!pauseButton.equals(pB)) {
                pauseButton = pB;
                pauseFade = new Color(0, 0, 0, 180);
            } else {
                pauseButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Empty.png"))).getImage();
                pauseFade = new Color(0, 0, 0, 0);
            }
            pausePlay = !pausePlay;
            repaint();
        }


        //Move Doug with keyboard input, lock him inbounds
        player.checkBounds();
    }

    public void keyReleased(KeyEvent e) {

        int c = e.getKeyCode();

        //For AudioThread - Updates track when key up

        if (c != KeyEvent.VK_ESCAPE) {

            switch (c) {

                case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> player.setVelY(0);
                case KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT -> player.setVelX(0);
            }
        }
    }

    public void saveScore()  {


            try{
                PrintWriter pw = new PrintWriter("src/Assets/Scoreboard/highscore.txt");
                pw.append(String.valueOf(highScore));
                pw.close();
            }
            catch(Exception ignored){

            }

    }

    public void audioPlay() {

        try {
            AudioInputStream system = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("Assets/theme.wav")));
            Clip sound = AudioSystem.getClip();
            sound.open(system);

            sound.start();
            sound.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ignored) {}
    }

    public void keyTyped(KeyEvent e) {
    }
}

