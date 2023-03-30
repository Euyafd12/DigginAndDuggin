public class audioThread extends Thread {

    private final audioPlayer aP;
    private boolean previous;
    public audioThread(GUI g) {

        aP = g.audioTrack;
        previous = false;
    }

    public void run() {

        //Continuously check if music should be playing - If status changes then start or stop music accordingly
        //Compares older status with current to see if it updates

        while (true) {

            System.out.print("");

            if (aP.isPlaying() != previous) {
                if (aP.isPlaying()) {
                    aP.play();
                }
                else {
                    aP.pause();
                }
                previous = aP.isPlaying();
            }
        }
    }
}
