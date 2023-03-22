import java.awt.*;

public class Player extends GUI {

    private int lives;

    public Player() {

        lives = 2;
    }

    public int getLives() {

        return lives;
    }

    public void setLives(int lives) {

        this.lives = lives;
    }
}
