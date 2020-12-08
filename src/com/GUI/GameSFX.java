package com.GUI;

import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;

public class GameSFX{
    public static int bombBang = 1;
    public static int ded = 2;
    public static int item = 3;
    public static int menu = 4;
    public static int monster_ded = 5;
    public static int new_bomb = 6;
    public static int Victory = 7;

    static ArrayList<Clip> loopClip = new ArrayList<>();

    static String filePath;

    public static void play(int soundType, boolean isLoop) {
        try {
            switch (soundType) {
                case 1 -> filePath = "src/SFX/bomb_bang.wav";
                case 2 -> filePath = "src/SFX/ded.wav";
                case 3 -> filePath = "src/SFX/item.wav";
                case 4 -> filePath = "src/SFX/menu.wav";
                case 5 -> filePath = "src/SFX/monster_ded.wav";
                case 6 -> filePath = "src/SFX/place-bomb.wav";
                case 7 -> filePath = "src/SFX/Victory.wav";
            }
            // create AudioInputStream object
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

            // create clip reference
            Clip clip = AudioSystem.getClip();

            // open audioInputStream to the clip
            clip.open(audioInputStream);

            // loop clip
            if (isLoop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                loopClip.add(clip);
            }

            //start the clip
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public static void stop() {
        for (Clip clip: loopClip) {
            clip.stop();
        }
    }
}
