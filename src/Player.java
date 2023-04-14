import java.awt.*;
import java.util.*;
public class Player {

    private int x, y, lives, velX, velY, killCNT;
    private final int velocity;
    public ArrayList<Point> path;

    public Player() {

        lives = 3;
        x = 285;
        y = 265;
        velX = 0;
        velY = 0;
        velocity = 4;

        path = new ArrayList<>();
    }

    public int getKillCNT() {
        return killCNT;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
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

    public void kill() {
        killCNT++;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public void checkBounds() {

        if (x < 0) x = 0;
        if (x > 611) x = 611;
        if (y < 270) y = 270;
        if (y > 960) y = 960;
    }

    public int getLives() {

        return lives;
    }

    public void dropLives() {

        lives--;
    }

    public int getVelX()
    {
        return velX;
    }
}

