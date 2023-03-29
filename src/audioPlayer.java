import javax.sound.sampled.*;
import java.util.*;

public class audioPlayer {

    private AudioInputStream system;
    private Clip sound;
    private long time;
    boolean playing;

    public audioPlayer() {

        try {
            system = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("Assets/theme.wav")));
            sound = AudioSystem.getClip();
        } catch (Exception ignored) {}

        time = 0;
        playing = false;

    }

    public void play() {

        try {
            sound.open(system);
            sound.setMicrosecondPosition(time);
            sound.start();
            sound.loop(Clip.LOOP_CONTINUOUSLY);
            playing = true;
        } catch (Exception ignored) {}
    }

    public void pause() {

        time = sound.getMicrosecondLength();
        sound.stop();
        playing = false;
    }
}
