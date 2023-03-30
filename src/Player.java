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

    public void escapeEnemy() {

        //If Doug collides with enemy, find random area to teleport to in 600 x 600 rectangle

        while (true) {

            int tempX = (int) ((Math.random() * 400) - 200);
            int tempY = (int) ((Math.random() * 400) - 200);

            if ((x - tempX > -1 && x - tempX < 865) && (y - tempY > -1 && y - tempY < 650)) {
                x -= tempX;
                y -= tempY;
                break;
            }

            if ((x + tempX > -1 && x + tempX < 865) && (y - tempY > -1 && y - tempY < 650)) {
                x -= tempX;
                y -= tempY;
                break;
            }

            if ((x - tempX > -1 && x - tempX < 865) && (y + tempY > -1 && y + tempY < 650)) {
                x -= tempX;
                y -= tempY;
                break;
            }

            if ((x + tempX > -1 && x + tempX < 865) && (y + tempY > -1 && y  + tempY < 650)) {
                x -= tempX;
                y -= tempY;
                break;
            }
        }
    }

    public void walk(int e) {

        //Moves Doug's position depending on keyboard input

        switch (e) {

            case 38 -> y -= velocity;

            case 40 -> y += velocity;

            case 39 -> x += velocity;

            case 37 -> x -= velocity;
        }
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
