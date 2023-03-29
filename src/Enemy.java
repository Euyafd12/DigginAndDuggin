public class Enemy {

    private int health;
    private int x, y, velocity;
    public Enemy() {

        health = 2;
        x = 200;
        y = 200;
        velocity = 4;
    }

    public void walkTowards(int xPos, int yPos) {

        if (Math.abs(x - xPos) > Math.abs(y - yPos)) {

            if (x - xPos > 0) {
                x -= velocity;
            }
            else x += velocity;
        }
        else {

            if (y - yPos > 0) {
                y -= velocity;
            }
            else y += velocity;
        }
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

    public int getHealth() {
        return health;
    }
}
