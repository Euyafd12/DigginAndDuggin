
public class gameRunner {

    public static void main(String[] args) throws Exception {

        GUI gui = new GUI();
        gui.display();

        framerateThread frameThread = new framerateThread(gui);
        frameThread.start();

        while (true) {

            //End game when lives hit 0
            System.out.print("");
            if (gui.getPlayer().getLives() < 0) {

                System.out.println("Game Over!");
                gui.saveScore();
                System.exit(999999999);
            }
        }


    }
}
