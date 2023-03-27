import java.io.FileNotFoundException;

public class gameRunner {

    public static void main(String[] args) throws FileNotFoundException {

        Player Doug = new Player();
        Doug.setDigDug(285, 265);
        Doug.display("Dig Dug");
    }
}
