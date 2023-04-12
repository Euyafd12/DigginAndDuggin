public class Enemy {

    private int x, y, appearCNT, velocity;
    private final String type;
    private boolean alive;
    public Enemy(String type) {

        x = 0;
        y = 0;
        velocity = 0;
        appearCNT = 0;
        alive = true;
        this.type = type;
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
    public void kill() {
        alive = false;
    }
    public void upAppearCNT() {
        appearCNT++;
    }
}
