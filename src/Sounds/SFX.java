package Sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SFX {
    public static String boomBang = "src/Sounds/boom_bang.wav";
    public static String ded = "src/Sounds/ded.wav";
    public static String item = "src/Sounds/item.wav";
    public static String background = "src/Sounds/playgame.wav";
    public static String game3 = "src/Sounds/playgame3.wav";
    public static String menu = "src/Sounds/soundMenu.wav";
    public static String start = "src/Sounds/start.wav";
    public static String exit = "src/Sounds/bye_bye.wav";
    public static String click = "src/Sounds/click.wav";
    public static String monsterDead = "src/Sounds/monster_die.wav";
    public static String placeBoom = "src/Sounds/place-bomb.wav";
    public static String victory = "src/Sounds/victorylol.wav";    
    public static String lose = "src/Sounds/lose.wav";
    public static String win = "src/Sounds/win.wav";
    public static String highScore = "src/Sounds/wait.wav";

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
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
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
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopAllClip() {
        for (Clip clip : continuousClip) {
            clip.stop();
        }
        continuousClip.clear();
    }
}
