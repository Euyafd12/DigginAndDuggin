
import java.awt.*;
import java.util.*;

public class Player {

    private int x;
    private int y;
    private int lives;
    private int velX;
    private int velY;
    private final int velocity;
    public ArrayList<Point> path;

    public Player() {

        lives = 2;
        x = 285;
        y = 265;
        velX = 0;
        velY = 0;
        velocity = 3;

        path = new ArrayList<>();
        path.add(new Point(x, y));
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

    public void escapeEnemy() {

        x = 285;
        y = 265;
    }

    public void tickWalk() {

        x += velX;
        y += velY;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public void checkBounds() {

        //Keep's Doug inbounds

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
}

