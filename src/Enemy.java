public class Enemy {

    private int x, y, appearCNT, velocity, add, flip;
    private String movePath, direction;
    private final String type;
    private boolean alive;
    public Enemy(String type, String movePath) {

        x = 0;
        y = 0;
        add = 0;
        flip = 1;
        velocity = 0;
        appearCNT = 0;

        this.type = type;
        this.movePath = movePath;
        direction = movePath;

        alive = true;
    }

    public void move(int xPos, int yPos) {

        if (Math.sqrt(Math.pow((x - xPos), 2) + Math.pow((y - yPos), 2)) > 155) {

            shiftLeftRight();
        }
        else walkTowards(xPos, yPos);

        checkBounds();
    }

    public void checkBounds() {

        if (x < 0) {
            x = 0;
            movePath = "Right";
        }
        if (x > 611) {
            x = 611;
            movePath = "Left";
        }
    }

    public void walkTowards(int xPos, int yPos) {

        if (Math.abs(x - xPos) > Math.abs(y - yPos)) {

            if (x - xPos > 0) {
                x -= velocity;
                flip = -1;
                add = 40;
                direction = "Left";
            }
            else {
                x += velocity;
                flip = 1;
                add = 0;
                direction = "Right";
            }

        }
        else {

            if (y - yPos > 0) {
                y -= velocity;
                direction = "Up";
            }
            else {
                y += velocity;
                direction = "Down";
            }
        }
    }

    public void shiftLeftRight() {

        if (movePath.equals("Left")) {
            x -= velocity;
            flip = -1;
            add = 40;
            direction = "Left";
        }
        else {
            x += velocity;
            flip = 1;
            add = 0;
            direction = "Right";
        }
    }

    public void die() {
        alive = false;
    }

    public void upAppearCNT() {
        appearCNT++;
    }

    public void setPos(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
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

    public int getAppearCNT() {
        return appearCNT;
    }

    public boolean isAlive() {
        return alive;
    }

    public String getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
