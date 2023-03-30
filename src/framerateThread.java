public class framerateThread extends Thread {

    private GUI gui;

    public framerateThread(GUI g) {

        gui = g;
    }

    public void run() {

        while (true) {

            //40 FPS
            gui.player.tickWalk();
            gui.player.checkBounds();
            gui.repaint();
            try {
                Thread.sleep(25);
            } catch (Exception ignore) {}
        }
    }


}
