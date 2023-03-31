
public class Enemy {
    private int x, y;
    private final int velocity;
    public Enemy() {

        x = 125;
        y = 625;
        velocity = 1;
    }

    public void walkTowards(int xPos, int yPos) {

        //Finds longest axis of travel, then moves towards Doug in correct direction

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
}
