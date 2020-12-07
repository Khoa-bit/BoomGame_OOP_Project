package com.GUI;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class gameSFX {
    static int bombBang = 1;
    static int ded = 2;
    static int item = 3;
    static int menu = 4;
    static int monster_ded = 5;
    static int new_bomb = 6;
    static int Victory = 7;

    String filePath;

    Clip clip;

    AudioInputStream audioInputStream;

    gameSFX() {

    }

    public void play(int soundType) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        switch (soundType) {
            case 1 -> filePath = "src/SFX/bomb_bang.wav";
            case 2 -> filePath = "src/SFX/ded.wav";
            case 3 -> filePath = "src/SFX/item.wav";
            case 4 -> filePath = "src/SFX/menu.wav";
            case 5 -> filePath = "src/SFX/monster_ded.wav";
            case 6 -> filePath = "src/SFX/newbomb.wav";
            case 7 -> filePath = "src/SFX/Victory.m4a";
        }
        // create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        //start the clip
        clip.start();
    }
}

class TestMain {
    public static void main(String[] args) {
        try {
            gameSFX audioPlayer = new gameSFX();

            audioPlayer.play(gameSFX.bombBang);
            audioPlayer.play(gameSFX.item);

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }
    }
}
