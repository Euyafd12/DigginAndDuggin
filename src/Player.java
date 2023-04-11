import java.awt.*;
import java.util.*;
public class Player {

    private int x, y, lives, velX, velY;
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

        //Keeps Doug inbounds

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

