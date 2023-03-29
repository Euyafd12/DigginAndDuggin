import java.awt.*;
import java.util.*;

public class Player {

    private int x, y, lives;
    private final int velocity;
    public ArrayList<Point> path;

    public Player() {

        lives = 2;
        x = 285;
        y = 265;
        velocity = 5;

        path = new ArrayList<>();
        path.add(new Point(x, y));
    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    public void walk(int e) {

        switch (e) {

            case 38 -> y -= velocity;

            case 40 -> y += velocity;

            case 39 -> x += velocity;

            case 37 -> x -= velocity;
        }
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
}
