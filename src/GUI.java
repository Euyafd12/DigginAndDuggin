import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class GUI extends JPanel implements KeyListener {
    private Graphics2D g2d;
    private int highScore, score, flip, add, displayFruit1;
    private boolean pausePlay;
    private Color pauseFade;
    private Image imgDigDug, pauseButton, Watermelon;
    private final Player player;
    private final Enemy enemy;
    private final int width, height;
    private final Image ICN, plus300;
    private final Rectangle waterbox;
    private final Map<String, Image> scoreMap;
    private final ArrayList<Rectangle> scoreList;

    public GUI() throws FileNotFoundException {

        g2d = null;

        player = new Player();
        enemy = new Enemy();

        pauseFade = new Color(0, 0, 0, 0);
        pausePlay = true;

        width = 915;
        height = 1040;
        flip = 1;
        score = 0;
        add = 0;
        displayFruit1 = 0;

        highScore = new Scanner(new File("src/Assets/Scoreboard/highscore.txt")).nextInt();

        scoreMap = new HashMap<>();
        scoreList = new ArrayList<>();

        //Load in map of scoreboard numbers
        for (int i = 0; i < 10; i++) {
            scoreMap.put(String.valueOf(i), new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Scoreboard/Score" + i + ".png"))).getImage());
        }

        imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougSide.png"))).getImage();
        ICN = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Background/DigDugIcon.jpg"))).getImage();
        pauseButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Empty.png"))).getImage();
        Watermelon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Background/Watermelon.png"))).getImage();
        plus300 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Scoreboard/Points300.png"))).getImage();


        waterbox = new Rectangle(500, 625, 48, 27);
    }
    public Player getPlayer() {
        return player;
    }

    public boolean isPausePlay() {
        return pausePlay;
    }

    public void display() {

        JFrame frame = new JFrame("Dig Dug");
        frame.add(this);
        frame.addKeyListener(this);
        frame.setIconImage(ICN);

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

    public void prepGUI() {

        Image imgBackground = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Background/background.png"))).getImage();
        Image imgLogo = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Background/Logo.png"))).getImage();
        Image imgScore = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Scoreboard/imgScore.png"))).getImage();
        Image imgHiScore = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Scoreboard/imgHi-Score.png"))).getImage();

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);

        //Draw each piece of background
        g2d.drawImage(imgLogo, 100, -10, 714, 318, null);
        g2d.drawImage(imgScore, 700, 400, 117, 21, null);
        g2d.drawImage(imgHiScore, 700, 500, 144, 48, null);

        g2d.drawImage(imgBackground, 0, 300, 181, 700, null);
        g2d.drawImage(imgBackground, 180, 300, 181, 700, null);
        g2d.drawImage(imgBackground, 360, 300, 181, 700, null);
        g2d.drawImage(imgBackground, 470, 300, 181, 700, null);

        //Draw lives icon, reduced with each death
        int tempPos = 675;

        for (int i = 0; i < player.getLives(); i++) {

            g2d.drawImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougSide.png"))).getImage(), tempPos, 720, 100, 100, null);
            tempPos += 110;
        }

        g2d.fillRect(460, 610, 128, 40);
        g2d.drawImage(Watermelon, 500, 625, 48, 27, null);
    }

    public void paintComponent(Graphics window) {

        super.paintComponent(window);
        g2d = (Graphics2D) window;

        //Display Background
        prepGUI();

        int pX = player.getX();
        int pY = player.getY();

        //Draw Scoreboard
        Rectangle rec = new Rectangle(pX, pY, 40, 40);

        boolean check = true;
        for (Rectangle rc : scoreList) {

            if (rc.intersects(rec)) {
                check = false;
            }
        }

        if (check) {
            score += 5;
            scoreList.add(rec);
        }

        if (score > highScore) {
            highScore = score;
        }

        drawScore();

        //Display Doug
        g2d.fillRect(pX, pY, 40, 40);
        drawPath();
        drawDigDug();

        //Make enemy move towards Doug and display
        enemy.walkTowards(pX, pY);
        drawEnemy();

        //Check for collision between Doug and enemy
        collisionCheck();

        //Use [Escape] as switch, if on - display pause menu and freeze game
        drawPause();

        if (waterbox.width == 0 && displayFruit1 < 40) {
            g2d.drawImage(plus300, 500, 625 - displayFruit1, 48, 27, null);
            displayFruit1++;
        }
    }

    public void drawScore() {

        //Represent score as string, go through each character and display correct number image

        String sc = "" + score;
        String hc = "" + highScore;

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
        g2d.drawImage(imgDigDug, player.getX() + add, player.getY(), 40 * flip , 40, null);
        player.path.add(new Point(player.getX(), player.getY()));
    }

    public void drawEnemy() {

        Image img = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/GogglesRight.png"))).getImage();
        g2d.drawImage(img, enemy.getX(), enemy.getY(), 40, 40, null);
    }

    public void drawPath() {

        //Draw boxes to represent Doug's dug path
        for (Point p : player.path) {
            g2d.fillRect(p.x, p.y, 40, 40);
        }
    }

    public void drawPause() {

        //Draw pause menu, if [Escape] switch on, color background transparent black and draw pause button

        g2d.setColor(pauseFade);
        g2d.fillRect(0, 0, width, height);

        g2d.drawImage(pauseButton, width / 2 - 400, (height / 2 - 150), 796, 252, null);
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

        if (plyr.intersects(waterbox)) {

            Watermelon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Empty.png"))).getImage();
            score += 300;
            waterbox.setSize(0, 0);
        }

    }

    public void keyPressed(KeyEvent e) {

        Image dgUp = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougUp.png"))).getImage();
        Image dgSide = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougSide.png"))).getImage();
        Image dgDown = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougDown.png"))).getImage();

        player.setVelX(0);
        player.setVelY(0);

        int k = e.getKeyCode();

        if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT) {

            imgDigDug = dgSide;

            if (k == KeyEvent.VK_LEFT) {
                player.setVelX(-player.getVelocity());
                flip = -1;
                add = 40;
            } else {
                player.setVelX(player.getVelocity());
                flip = 1;
                add = 0;
            }
        }

        else if (player.getVelX() == 0) {

            if (k == KeyEvent.VK_UP) {
                player.setVelY(-player.getVelocity());
                imgDigDug = dgUp;
            }

            if (k == KeyEvent.VK_DOWN) {
                player.setVelY(player.getVelocity());
                imgDigDug = dgDown;
            }
        }

        if (k == KeyEvent.VK_ESCAPE) {

            Image pB = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/PauseButton.png"))).getImage();

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

        switch (e.getKeyCode()) {

            case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> player.setVelY(0);
            case KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT -> player.setVelX(0);
        }
    }

    public void saveScore() {

        try {
            PrintWriter pw = new PrintWriter("src/Assets/Scoreboard/highscore.txt");
            pw.append(String.valueOf(highScore));
            pw.close();
        } catch (Exception ignored) {}
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

    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) throws FileNotFoundException {

        new GUI().display();
    }
}

