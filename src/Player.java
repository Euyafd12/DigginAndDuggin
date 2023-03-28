public class Player {

    public int xPos, yPos, lives, velocity;


    public Player() {

        lives = 2;
        xPos = 285;
        yPos = 265;
        velocity = 5;
    }

    public void walkUp() {
        yPos -= velocity;
    }
    public void walkDown() {
        yPos += velocity;
    }
    public void walkLeft() {
        xPos -= velocity;
    }
    public void walkRight() {
        xPos += velocity;
    }

    public void checkBounds() {
        if (xPos < 0) xPos = 0;
        if (xPos > 611) xPos = 611;
        if (yPos < 270) yPos = 270;
        if (yPos > 960) yPos = 960;
    }

    public int getLives() {

        return lives;
    }

    public void setLives(int lives) {

        this.lives = lives;
    }

    public void setDigDug(int x, int y) {

        xPos = x;
        yPos = y;
    }

}
