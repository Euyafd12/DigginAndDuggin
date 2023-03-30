import javax.sound.sampled.*;
import java.util.*;

public class audioPlayer {

    private AudioInputStream system;
    private Clip sound;
    private long timestamp;
    private boolean playing;

    public audioPlayer() {

        //idk
        try {
            system = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("Assets/theme.wav")));
            sound = AudioSystem.getClip();
            sound.open(system);
        } catch (Exception ignored) {}

        playing = false;
        timestamp = 0;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void play() {

        //Start audio, uses last saved timestamp to save progress
        try {

            if (sound.getMicrosecondLength() <= timestamp) {
                timestamp = 0;
            }

            sound.setMicrosecondPosition(timestamp);
            sound.start();
            sound.loop(Clip.LOOP_CONTINUOUSLY);
            playing = true;
        } catch (Exception ignored) {}
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void pause() {

        //Stop audio, saves timestamp for next startup;
        timestamp = sound.getMicrosecondPosition();
        sound.stop();
    }
}
