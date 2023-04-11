import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class GUI extends JPanel implements KeyListener {
    private Graphics2D g2d;
    private int highScore, score, flip, add;
    private boolean pausePlay, gameOver, gunOut;
    private Color pauseFade;
    private Image imgDigDug, pauseButton, weapon;
    private String idk;
    private final Player player;
    private final ArrayList<Enemy> enemylist;
    private final int width, height;
    private final Image ICN, plus300, endScreen, imgScore, imgHiScore, watermelon, pineapple, carrot;
    private final Map<String, Image> scoreMap;
    private final ArrayList<Fruit> fruitList;
    private final ArrayList<Rectangle> scoreList;

    public GUI() throws FileNotFoundException {

        g2d = null;

        player = new Player();
        enemylist = new ArrayList<>();
        enemylist.add(new Enemy("Goggles"));
        //Dragon

        pauseFade = new Color(0, 0, 0, 0);
        pausePlay = true;
        gameOver = false;
        gunOut = false;

        width = 915;
        height = 1040;
        flip = 1;
        score = -5;
        add = 0;

        idk = "RIGHT";

        highScore = new Scanner(new File("src/Assets/Scoreboard/highscore.txt")).nextInt();

        scoreMap = new HashMap<>();
        scoreList = new ArrayList<>();
        fruitList = new ArrayList<>();

        Fruit melon = new Fruit();
        Fruit karot = new Fruit();
        Fruit carot = new Fruit();
        Fruit pineP = new Fruit();

        melon.setBounds(500, 625, 48, 27);
        melon.setType("Watermelon");

        karot.setBounds(125, 350, 42, 39);
        karot.setType("Carrot");

        carot.setBounds(457, 850, 42, 39);
        carot.setType("Carrot");

        pineP.setBounds(75, 950, 30, 45);
        pineP.setType("Pineapple");

        fruitList.add(melon);
        fruitList.add(karot);
        fruitList.add(carot);
        fruitList.add(pineP);

        for (int i = 0; i < 10; i++) {
            scoreMap.put(String.valueOf(i), new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Scoreboard/Score" + i + ".png"))).getImage());
        }

        imgDigDug = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougSide.png"))).getImage();
        ICN = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Background/DigDugIcon.jpg"))).getImage();
        pauseButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Empty.png"))).getImage();
        weapon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Empty.png"))).getImage();

        watermelon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Background/Watermelon.png"))).getImage();
        carrot = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Background/Carrot.png"))).getImage();
        pineapple = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Background/Pineapple.png"))).getImage();

        plus300 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Scoreboard/Points300.png"))).getImage();
        endScreen = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Background/Game_Over.png"))).getImage();
        imgScore = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Scoreboard/imgScore.png"))).getImage();
        imgHiScore = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Scoreboard/imgHi-Score.png"))).getImage();
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isPausePlay() {
        return pausePlay;
    }

    public void endGame() {
        gameOver = !gameOver;
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
                saveScore();
            }
        });
        audioPlay();
    }

    public void prepGUI() {

        Image imgBackground = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Background/background.png"))).getImage();
        Image imgLogo = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Background/Logo.png"))).getImage();

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);

        g2d.drawImage(imgLogo, 100, -10, 714, 318, null);
        g2d.drawImage(imgScore, 700, 400, 117, 21, null);
        g2d.drawImage(imgHiScore, 700, 500, 144, 48, null);

        g2d.drawImage(imgBackground, 0, 300, 181, 700, null);
        g2d.drawImage(imgBackground, 180, 300, 181, 700, null);
        g2d.drawImage(imgBackground, 360, 300, 181, 700, null);
        g2d.drawImage(imgBackground, 470, 300, 181, 700, null);

        int tempPos = 675;

        for (int i = 0; i < player.getLives() - 1; i++) {

            g2d.drawImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/DougSide.png"))).getImage(), tempPos, 720, 100, 100, null);
            tempPos += 110;
        }

        g2d.fillRect(460, 612, 128, 40);
        g2d.fillRect(79, 339, 128, 50);
        g2d.fillRect(412, 839, 128, 50);
        g2d.fillRect(28, 937, 128, 58);
    }

    public void paintComponent(Graphics window) {

        super.paintComponent(window);
        g2d = (Graphics2D) window;

        if (gameOver) closeGame();

        else {

            prepGUI();

            int pX = player.getX();
            int pY = player.getY();

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

            g2d.fillRect(pX, pY, 40, 40);
            drawPath();
            drawWeapon();
            drawDigDug();


            for (Enemy enemy : enemylist) {
                enemy.walkTowards(pX, pY);
            }
            drawEnemy();

            collisionCheck();
            drawFruit();

            drawPause();
        }
    }

    public void drawDigDug() {

        g2d.drawImage(imgDigDug, player.getX() + add, player.getY(), 40 * flip, 40, null);
        player.path.add(new Point(player.getX(), player.getY()));
    }

    public void drawWeapon() {

        switch (idk) {

            case "RIGHT", "LEFT" -> g2d.drawImage(weapon, player.getX() + 20, player.getY() + 20, 64 * flip, 10, null);
            case "UP" -> g2d.drawImage(weapon, player.getX() + 20, player.getY() - 40, 10 * flip,64, null);
            case "DOWN" -> g2d.drawImage(weapon, player.getX() + 20, player.getY() + 80, -10 * flip,-64, null);
        }
    }

    public void drawPath() {

        for (Point p : player.path) {
            g2d.fillRect(p.x, p.y, 40, 40);
        }
    }

    public void drawEnemy() {

        Image goggles = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/GogglesRight.png"))).getImage();
        //Image Dino
        for (Enemy enemy : enemylist) {

            if (enemy.getType().equals("Goggles")) {
                g2d.drawImage(goggles, enemy.getX(), enemy.getY(), 40, 40, null);
            }
        }
    }

    public void collisionCheck() {

        Rectangle plyr = new Rectangle(player.getX(), player.getY(), 40, 40);
        Rectangle weap = new Rectangle();

        //Fix Rectangle position
        switch (idk) {
            case "RIGHT","LEFT" -> weap.setBounds((player.getX() + 20), player.getY() + 20, 64 * flip, 10);
            case "UP" -> weap.setBounds(player.getX() + 20, player.getY() - 40, 10 * flip, 64);
            case "DOWN" -> weap.setBounds(player.getX() + 20, player.getY() + 80, -10 * flip, -64);
        }

        for (Enemy enemy : enemylist) {

            Rectangle enmy = new Rectangle(enemy.getX(), enemy.getY(), 40, 40);

            if (plyr.intersects(enmy)) {

                player.dropLives();
                player.escapeEnemy();
                repaint();
                break;
            }

            if (gunOut && weap.intersects(enmy)) {
                enemy.kill();
                score += 1000;
            }
        }


        for (Fruit f : fruitList) {
            if (!f.isEaten() && f.getBounds().intersects(plyr)) {
                f.eat();
                score += 300;
            }
        }
    }

    public void drawFruit() {

        for (Fruit f : fruitList) {

            if (f.isEaten() && f.getAppearCNT() < 40) {
                g2d.drawImage(plus300,f.getBounds().x + (f.getBounds().width / 2) - 24,f.getBounds().y - f.getAppearCNT(), 48, 27, null);
                f.upAppearCNT();
            }

            if (!f.isEaten()) {

                switch (f.getType()) {

                    case "Watermelon" -> g2d.drawImage(watermelon, f.getBounds().x, f.getBounds().y, f.getBounds().width, f.getBounds().height, null);
                    case "Carrot" -> g2d.drawImage(carrot, f.getBounds().x, f.getBounds().y, f.getBounds().width, f.getBounds().height, null);
                    case "Pineapple" -> g2d.drawImage(pineapple, f.getBounds().x, f.getBounds().y, f.getBounds().width, f.getBounds().height, null);
                }
            }
        }
    }

    public void drawPause() {

        g2d.setColor(pauseFade);
        g2d.fillRect(0, 0, width, height);

        g2d.drawImage(pauseButton, width / 2 - 400, (height / 2 - 150), 796, 252, null);
    }

    public void drawScore() {

        String sc = "" + score;
        String hc = "" + highScore;

        int xTemp = 750;

        if (!gameOver) {

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

        else {

            xTemp = ((195 - (sc.length() * 48)) / 2) + 561;

            for (int i = 0; i < sc.length(); i++) {

                g2d.drawImage(scoreMap.get(sc.substring(i, i + 1)), xTemp, 625, 48, 48, null);
                xTemp += 48;
            }

            xTemp = ((230 - (hc.length() * 48)) / 2) + 125;

            for (int i = 0; i < hc.length(); i++) {

                g2d.drawImage(scoreMap.get(hc.substring(i, i + 1)), xTemp, 625, 48, 48, null);
                xTemp += 48;
            }
        }
    }

    public void saveScore() {

        try {
            PrintWriter pw = new PrintWriter("src/Assets/Scoreboard/highscore.txt");
            pw.append(String.valueOf(highScore));
            pw.close();
        } catch (Exception ignored) {}
    }

    public void closeGame() {

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);

        g2d.drawImage(endScreen, 302, 150, 310, 170, null);
        g2d.drawImage(imgHiScore, 125, 500, 230, 80, null);
        g2d.drawImage(imgScore, 561, 545, 195, 35, null);

        drawScore();
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

                idk = "LEFT";
                player.setVelX(-player.getVelocity());
                flip = -1;
                add = 40;
            } else {
                idk = "RIGHT";
                player.setVelX(player.getVelocity());
                flip = 1;
                add = 0;
            }
        }
        else if (player.getVelX() == 0) {

            if (k == KeyEvent.VK_UP) {

                idk = "UP";
                player.setVelY(-player.getVelocity());
                imgDigDug = dgUp;
            }

            if (k == KeyEvent.VK_DOWN) {

                idk = "DOWN";
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
        }
        else if (k == KeyEvent.VK_SPACE) {

            if (idk.equals("UP") || idk.equals("DOWN")) {
                weapon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/weaponUP.png"))).getImage();
            }
            else weapon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Characters/weaponSide.png"))).getImage();

            gunOut = true;
        }

        player.checkBounds();
    }

    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> player.setVelY(0);
            case KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT -> player.setVelX(0);
            case KeyEvent.VK_SPACE -> {
                weapon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/Empty.png"))).getImage();
                gunOut = false;
            }
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

    public void keyTyped(KeyEvent e) {}
}

