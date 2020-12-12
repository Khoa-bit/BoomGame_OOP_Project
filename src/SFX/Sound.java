package SFX;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Sound {
    public static String boomBang = "src/SFX/bomb_bang.wav";
    public static String ded = "src/SFX/ded.wav";
    public static String item = "src/SFX/item.wav";
    public static String background = "src/SFX/soundGame.wav";
    public static String menu = "src/SFX/menu.wav";
    public static String exit = "src/SFX/bye_bye.wav";
    public static String click = "src/SFX/click.wav";
    public static String monsterDed = "src/SFX/monster_ded.wav";
    public static String placeBoom = "src/SFX/place-bomb.wav";
    public static String victory = "src/SFX/Victory.wav";
    public static String lose = "src/SFX/lose.wav";

    public static ArrayList<Clip> continuousClip = new ArrayList<>();

    public static void playSoundLoop(String path) {
        try {
            File music = new File(path);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(-5.0f);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            continuousClip.add(clip);
        }
        catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void playSound(String path) {
        try {
            File music = new File(path);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(-5.0f);
            clip.start();
            clip.loop(0);
        }
        catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopAllClip() {
        for (Clip clip: continuousClip) {
            clip.stop();
        }
        continuousClip.clear();
    }
}
