
public class gameRunner {

    public static void main(String[] args) throws Exception {

        GUI gui = new GUI();
        gui.display();

        //Use thread to continuously check if character is moving to play theme
        audioThread musicThread = new audioThread(gui);
        musicThread.start();

        framerateThread frameThread = new framerateThread(gui);
        frameThread.start();

        while(true) {

            //End game when lives hit 0
            System.out.print("");
            if (gui.player.getLives() < 0) {
                System.out.println("Game Over!");
                System.exit(999999999);
            }
        }


    }
}
