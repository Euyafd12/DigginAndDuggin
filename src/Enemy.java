import java.io.FileNotFoundException;

public class Enemy extends GUI{

    private int health;
    private int x, y;
    public Enemy() throws FileNotFoundException {

        health=100;
        x=0;
        y=0;
    }

    public void getDoug()
    {
        x=getX();
        y=getY();
    }
}
