public class Enemy {

    private int x, y, appearCNT, velocity;
    private String movePath;
    private final String type;
    private boolean alive;
    public Enemy(String type, String movePath) {

        x = 0;
        y = 0;
        this.movePath = movePath;
        velocity = 0;
        appearCNT = 0;
        alive = true;
        this.type = type;
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

    public void shiftLeftRight() {

        if (movePath.equals("Left")) {
            x -= velocity;
        }
        else x += velocity;
    }

    public void setPos(int x, int y) {

        this.x = x;
        this.y = y;
    }
    public void setVelocity(int velocity) {
        this.velocity = velocity;
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
    public void die() {
        alive = false;
    }
    public void upAppearCNT() {
        appearCNT++;
    }
}
