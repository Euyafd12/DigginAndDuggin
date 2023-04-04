public class framerateThread extends Thread {

    private final GUI gui;

    public framerateThread(GUI g) {
        gui = g;
    }

    public void run() {

        while (true) {

            System.out.print("");

            if (gui.isPausePlay()) {

                //40 FPS
                gui.getPlayer().tickWalk();
                gui.getPlayer().checkBounds();
                gui.repaint();
                try {
                    Thread.sleep(25);
                } catch (Exception ignore) {}
            }
        }
    }


}
