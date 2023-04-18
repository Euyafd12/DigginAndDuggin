import java.awt.*;
import java.util.*;
public class Player {

    private int x, y, lives, velX, velY, killCNT, flip, add;
    private String direction;
    private final int velocity;
    public ArrayList<Point> path;

    public Player() {

        lives = 3;
        x = 285;
        y = 265;
        velX = 0;
        velY = 0;
        velocity = 4;
        flip = 1;
        add = 0;

        direction = "Right";

        path = new ArrayList<>();
    }
    public void escapeEnemy(ArrayList<Enemy> enemyList) {

        boolean repeat = false;
        int a = 0;
        int b = 0;

        while (!repeat) {

            a = (int) (Math.random() * 611);
            b = (int) (Math.random() * 690) + 270;

            for (Enemy enemy : enemyList) {

                if (Math.sqrt(Math.pow((x - enemy.getX()), 2) + Math.pow((enemy.getY() - b), 2)) < 100) {
                    repeat = true;
                    break;
                }
            }
        }

        x = a;
        y = b;
    }

    public void tickWalk() {

        x += velX;
        y += velY;
    }

    public void checkBounds() {

        if (x < 0) x = 0;
        if (x > 611) x = 611;
        if (y < 270) y = 270;
        if (y > 960) y = 960;
    }

    public void kill() {
        killCNT++;
    }

    public void dropLives() {

        lives--;
    }

    public void setDirection(String direction) {
        this.direction = direction;

        if (direction.equals("Right")) {
            add = 0;
            flip = 1;
        }
        if (direction.equals("Left")) {
            add = 40;
            flip = -1;
        }
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public int getLives() {

        return lives;
    }

    public int getKillCNT() {
        return killCNT;
    }

    public int getVelocity() {
        return velocity;
    }

    public String getDirection() {
        return direction;
    }

    public int getAdd() {
        return add;
    }

    public int getFlip() {
        return flip;
    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    public int getVelX()
    {
        return velX;
    }
}

