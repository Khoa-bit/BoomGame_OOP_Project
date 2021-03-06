package com.GameObjects;

//import sound.GameSound; // CANNOT IMPORT THIS PACKAGE BECAUSE a package with the same name is created

import Sounds.SFX;

import com.GUI.GUI;
import com.GUI.PlayGame;

import java.awt.Image;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Manager {
    private Random random = new Random();
    private Bomber mBomber;
    private ArrayList<Box> arrBox;
    private ArrayList<Box> arrShadow;
    private ArrayList<Bomb> arrBomb;
    private ArrayList<BombBang> arrBombBang;
    private ArrayList<Monster> arrMonster;
    private ArrayList<Monster> arrBoss;
    private ArrayList<HighScore> arrHighScore;
    private ArrayList<Item> arrItem;
    private int round = 1;
    private boolean nextRound = true;
    private int status = 0;
    private String Background;
    private static Manager uniqueManager;
    int count = 0;

    private Manager() {
        mBomber = new Bomber(0, 540, Movable.BOMBER, Movable.DOWN, 3, 5, 5);
        innitManager();
    }

    public static synchronized Manager getInstance() {
        if (uniqueManager == null) {
            uniqueManager = new Manager();
        }
        return uniqueManager;
    }

    public void innitManager() {
        // System.out.print("Manager init");
        switch (round) {
            case 1:
                mBomber.setNew(0, 540);
                innit("src/Map1/BOX.txt", "src/Map1/SHADOW.txt", "src/Map1/MONSTER.txt", "src/Map1/ITEM.txt");
                nextRound = true;
                status = 0;
                break;

            case 2:
                mBomber.setNew(315, 270);
                innit("src/Map2/BOX.txt", "src/Map2/SHADOW.txt", "src/Map2/MONSTER.txt", "src/Map2/ITEM.txt");
                nextRound = true;
                status = 0;
                break;

            case 3:
                mBomber.setNew(315, 495);
                innit("src/Map3/BOX.txt", "src/Map3/SHADOW.txt", "src/Map3/MONSTER.txt", "src/Map3/ITEM.txt");
                nextRound = true;
                status = 0;
                SFX.stopAllClip();
                SFX.playSoundLoop(SFX.game3);
                break;

            default:
                break;
        }

    }

    public void innit(String pathBox, String pathShadow, String pathMonster, String pathItem) {
        arrBox = new ArrayList<>();
        arrShadow = new ArrayList<>();
        arrBomb = new ArrayList<>();
        arrBombBang = new ArrayList<>();
        arrMonster = new ArrayList<>();
        arrBoss = new ArrayList<>();
        arrHighScore = new ArrayList<>();
        arrItem = new ArrayList<>();
        innitArrBox(pathBox);
        innitArrShadow(pathShadow);
        initArrMonster(pathMonster);
        innitArrHighScore("src/highScore/HighScore.txt");
        initArrItem(pathItem);
    }

    /**
     * Load items to game map
     *
     * @param pathItem the path to item map's directory
     */
    public void initArrItem(String pathItem) {
        FileReader itemFile = null;
        BufferedReader bufferedString = null;
        try {
            itemFile = new FileReader(pathItem);
            bufferedString = new BufferedReader(itemFile);
            String eachLine;
            while ((eachLine = bufferedString.readLine()) != null) {
                String str[] = eachLine.split(":");
                int coordinateX = Integer.parseInt(str[0]);
                int coordinateY = Integer.parseInt(str[1]);
                int type = Integer.parseInt(str[2]);
                String images = str[3];
                Item item = new Item(coordinateX, coordinateY, type, images);
                arrItem.add(item);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Load box to game map
     *
     * @param pathBox the path to box map's directory
     */
    public void innitArrBox(String pathBox) {
        try {
            FileReader file = new FileReader(pathBox);
            BufferedReader input = new BufferedReader(file);
            Background = input.readLine();
            String line;
            while ((line = input.readLine()) != null) {
                String[] str = line.split(":");
                int x = Integer.parseInt(str[0]) * GUI.TILES;
                int y = Integer.parseInt(str[1]) * GUI.TILES;
                boolean destructible = Boolean.parseBoolean(str[2]);
                String pathImage = str[3];
                Box box = new Box(x, y, destructible, pathImage);
                arrBox.add(box);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load all monster to game map
     *
     * @param path the path to monster map's directory
     */
    public void initArrMonster(String path) {
        // For this to work effectively Boss must be last on MONSTER.txt
        try {
            FileReader file = new FileReader(path);
            BufferedReader input = new BufferedReader(file);
            String line;
            while ((line = input.readLine()) != null) {
                String str[] = line.split(":");
                int x = Integer.parseInt(str[0]);
                int y = Integer.parseInt(str[1]);
                int type = Integer.parseInt(str[2]);
                int orient = Integer.parseInt(str[3]);
                int speed = Integer.parseInt(str[4]);
                int heart = Integer.parseInt(str[5]);
                String images = str[6];
                Monster monster = new Monster(x, y, type, orient, speed, heart, images);
                arrMonster.add(monster);
                if (type == Movable.BOSS) {
                    arrBoss.add(monster);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load high score
     *
     * @param path the path to high score's directory
     */
    public void innitArrHighScore(String path) {
        try {
            FileReader file = new FileReader(path);
            BufferedReader input = new BufferedReader(file);
            String line;
            while ((line = input.readLine()) != null) {
                String str[] = line.split(":");
                String name = str[0];
                int score = Integer.parseInt(str[1]);
                HighScore highScore = new HighScore(name, score);
                arrHighScore.add(highScore);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load box's shadow to game map
     *
     * @param pathShadow the path to shadow map's directory
     */
    public void innitArrShadow(String pathShadow) {
        try {
            FileReader file = new FileReader(pathShadow);
            BufferedReader input = new BufferedReader(file);
            String line;
            while ((line = input.readLine()) != null) {
                String[] str = line.split(":");
                int x = Integer.parseInt(str[0]) * GUI.TILES;
                int y = Integer.parseInt(str[1]) * GUI.TILES;
                boolean destructible = Boolean.parseBoolean(str[2]);
                String pathImage = str[3];
                if (pathImage.equals("/Images/shawdow1.png")) {
                    y += 23;
                } else if (pathImage.equals("/Images/shawdow2.png")) {
                    y += 38;
                }
                Box shadow = new Box(x, y, destructible, pathImage);
                arrShadow.add(shadow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Innit bomb and check Bomber's bombs quantity.
     */
    public void innitBomb() {
        int x = mBomber.getX() + mBomber.getWidth() / 2;
        int y = mBomber.getY() + mBomber.getHeight() / 2;
        for (int i = 0; i < arrBomb.size(); i++) {
            if (arrBomb.get(i).doesImpactNewBomb(x, y)) {
                return;
            }
        }

        if (arrBomb.size() >= mBomber.getQuantityBomb()) {
            return;
        }
        SFX.playSound(SFX.placeBoom);

        Bomb mBomb = new Bomb(x, y, mBomber.getSizeBomb(), 3000);
        arrBomb.add(mBomb);
    }

    /**
     * Sets Bomber location when it dies or starts a round.
     */
    public void setNewBomber() {
        switch (round) {
            case 1:
                mBomber.setNew(0, 540);
                break;
            case 2:
                mBomber.setNew(315, 270);
                break;
            case 3:
                mBomber.setNew(315, 495);
                break;

            default:
                break;
        }
    }

    /**
     * Draw dialog on screen.
     *
     * @param g2d  the Graphics2D
     * @param type the type of dialogs
     */
    public void drawDialog(Graphics2D g2d, int type) {
        g2d.setFont(new Font("Arial", Font.BOLD, 70));
        g2d.setColor(Color.RED);
        if (type == 1) {
            g2d.drawString("You Lose !!!", 200, 250);
        } else {
            if (type == 2) {
                g2d.drawString("Round " + round, 200, 250);
            } else {
                g2d.drawString("You Win !!!", 200, 250);
            }
        }
    }

    public void drawAllBomb(Graphics2D g2d) {
        for (int i = 0; i < arrBomb.size(); i++) {
            arrBomb.get(i).drawSelf(g2d);
        }
        for (int i = 0; i < arrBombBang.size(); i++) {
            arrBombBang.get(i).drawSelf(g2d);
        }
    }

    public void drawAllMonster(Graphics2D g2d) {
        for (int i = 0; i < arrMonster.size() - arrBoss.size(); i++) {
            arrMonster.get(i).drawSelf(g2d);
        }
    }

    public void drawAllBoss(Graphics2D g2d) {
        for (int i = 0; i < arrBoss.size(); i++) {
            arrBoss.get(i).drawSelf(g2d);
        }
    }

    public void drawAllBox(Graphics2D g2d) {
        for (Box box : arrBox) {
            box.drawSelf(g2d);
        }
    }

    public void drawAllShawDow(Graphics2D g2d) {
        for (Box shadow : arrShadow) {
            shadow.drawSelf(g2d);
        }
    }

    public void drawBackground(Graphics2D g2d) {
        Image imgBackground = new ImageIcon(getClass().getResource("/Images/background2.png")).getImage();
        g2d.drawImage(imgBackground, 0, 0, null);
    }

    /**
     * @summary This method is to draw the right layouts of the PlayGame GUI which
     *          displays the current SCORE,HEART
     * @param g2d
     */
    public void drawInfo(Graphics2D g2d) {
        Image imgInfor = new ImageIcon(getClass().getResource("/Images/background_Info.png")).getImage();
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(Color.RED);
        g2d.drawImage(imgInfor, 675, 0, null);
        g2d.drawString("HEART", 755, 100);
        Image heart = new ImageIcon(getClass().getResource("/Images/heart_1.png")).getImage();
        if (mBomber.getHeart() == 3) {
            g2d.drawImage(heart, 750, 120, null);
            g2d.drawImage(heart, 775, 120, null);
            g2d.drawImage(heart, 800, 120, null);
        }
        if (mBomber.getHeart() == 2) {
            g2d.drawImage(heart, 760, 120, null);
            g2d.drawImage(heart, 790, 120, null);
        }
        if (mBomber.getHeart() == 1) {
            g2d.drawImage(heart, 775, 120, null);
        }

        g2d.drawString("SCORE : " + mBomber.getScore(), 740, 200);

    }

    public void drawAllItem(Graphics2D g2d) {
        for (int i = 0; i < arrItem.size(); i++) {
            arrItem.get(i).drawSelf(g2d);
        }
    }

    /**
     * Check if bomber gets hit by Monster or Bomb Bang.
     */
    public void checkDead() {
        if (PlayGame.GOD_MODE) {
            return;
        }

        for (int i = 0; i < arrBombBang.size(); i++) {
            if (arrBombBang.get(i).doesBombBangImpactCharacter(mBomber) && mBomber.getStatus() == Bomber.ALIVE) {
                Image icon = new ImageIcon(getClass().getResource("/Images/bomber_dead.png")).getImage();
                mBomber.setImg(icon);
                if (mBomber.getStatus() == Bomber.DEAD) {
                    return;
                }
                mBomber.setHeart(mBomber.getHeart() - 1);
                mBomber.setStatus(Bomber.DEAD);
                SFX.playSound(SFX.ded);
            }
        }
        for (int i = 0; i < arrMonster.size(); i++) {
            if (mBomber.isImpactBomberVsCharacter(arrMonster.get(i))) {
                Image icon = new ImageIcon(getClass().getResource("/Images/ghost.png")).getImage();
                mBomber.setImg(icon);
                if (mBomber.getStatus() == Bomber.DEAD) {
                    return;
                }
                mBomber.setHeart(mBomber.getHeart() - 1);
                mBomber.setStatus(Bomber.DEAD);
                SFX.playSound(SFX.ded);
            }
        }
    }

    /**
     * Check Bomber's lives to end game and monsters count to start new round
     */
    public void checkWinAndLose() {
        if (mBomber.getHeart() == 0 && nextRound) {
            round = 1;
            status = 1;
            nextRound = false;
            SFX.stopAllClip();
            SFX.playSound(SFX.lose);
            saveScore();

            // Disable this function
            mBomber.setHeart(-1);
        }
        if (arrMonster.size() == 0 && nextRound) {
            if (round == 3) {
                status = 3;
                nextRound = false;
                SFX.stopAllClip();
                SFX.playSound(SFX.victory);
                saveScore();
                round = 1;
                return;
            }

            round = round + 1;
            nextRound = false;
            status = 2;
            SFX.playSound(SFX.win);
        }
    }

    /**
     * This method is to check whether the bomber has get the new items.
     */
    public void checkImpactItem() {
        for (int i = 0; i < arrItem.size(); i++) {
            if (arrItem.get(i).doesBomberImpactItem(mBomber)) {
                SFX.playSound(SFX.item);
                if (arrItem.get(i).getType() == Item.Item_Bomb) {
                    mBomber.setQuantityBomb(mBomber.getQuantityBomb() + 1);
                    arrItem.remove(i);
                    break;
                }
                if (arrItem.get(i).getType() == Item.Item_BombSize) {
                    mBomber.setSizeBomb(mBomber.getSizeBomb() + 1);
                    arrItem.remove(i);
                    break;
                }
                if (arrItem.get(i).getType() == Item.Item_Shoe) {
                    mBomber.setSpeed(mBomber.getSpeed() - 1);
                    arrItem.remove(i);
                    break;
                }

            }
        }
    }

    /**
     * Count down bombs and bomb bangs Generate Bomb explosion Check collision with
     * all Character
     */
    public void deadLineAllBomb() {
        for (int i = 0; i < arrBomb.size(); i++) {
            arrBomb.get(i).deadlineBomb();
            if (arrBomb.get(i).getTimeline() == 250) {
                BombBang bomBang = new BombBang(arrBomb.get(i).getX(), arrBomb.get(i).getY(), arrBomb.get(i).getSize(),
                        arrBox);
                arrBombBang.add(bomBang);
                arrBomb.remove(i);
                SFX.playSound(SFX.boomBang);
            }
        }

        for (int i = 0; i < arrBombBang.size(); i++) {
            for (int j = 0; j < arrBomb.size(); j++) {
                if (arrBombBang.get(i).doesBombBangImpactBomb(arrBomb.get(j))) {
                    BombBang bomBang = new BombBang(arrBomb.get(j).getX(), arrBomb.get(j).getY(),
                            arrBomb.get(j).getSize(), arrBox);
                    arrBombBang.add(bomBang);
                    arrBomb.remove(j);
                    SFX.playSound(SFX.boomBang);
                }
            }
        }

        for (int k = 0; k < arrBombBang.size(); k++) {
            arrBombBang.get(k).deadlineBomb();
            if (arrBombBang.get(k).getTimeLine() == 0) {
                arrBombBang.remove(k);
            }
        }
        for (int k = 0; k < arrBombBang.size(); k++) {
            arrBombBang.get(k).deadlineBomb();
            for (int j = 0; j < arrMonster.size(); j++) {
                if (arrBombBang.get(k).doesBombBangImpactCharacter(arrMonster.get(j))) {
                    if (arrMonster.get(j).getHeart() > 1) {
                        arrMonster.get(j).setHeart(arrMonster.get(j).getHeart() - 1);
                    } else {
                        if (arrMonster.get(j).getType() == Movable.BOSS) {
                            mBomber.setScore(mBomber.getScore() + 10);
                        } else {
                            mBomber.setScore(mBomber.getScore() + 1);
                        }
                        SFX.playSound(SFX.monsterDead);
                        arrMonster.remove(j);
                    }
                }
            }
        }

        for (int i = 0; i < arrBombBang.size(); i++) {
            for (int j = 0; j < arrBox.size(); j++) {
                if (arrBombBang.get(i).doesBombBangImpactBox(arrBox.get(j))) {
                    arrBox.remove(j);
                    arrShadow.remove(j);
                }
            }

        }
    }

    /**
     * Disable Bomber ability to go through bombs.
     */
    public void setRunBomer() {
        if (arrBomb.size() > 0) {
            if (arrBomb.get(arrBomb.size() - 1).setRun(mBomber) == false) {
                mBomber.setRunBomb(Bomber.DISALLOW_RUN);
            }
        }
    }

    /**
     * Randomly change all Monsters' orientations.
     */
    public void changeOrientAll() {
        for (int i = 0; i < arrMonster.size(); i++) {
            int orient = random.nextInt(4) + 1;
            arrMonster.get(i).changeOrient(orient);
        }
    }

    /**
     * Move all monsters.
     *
     * @param count the speed of monsters.
     */
    public void moveAllMonster(int count) {
        for (int i = 0; i < arrMonster.size(); i++) {
            if (arrMonster.get(i).move(count, arrBomb, arrBox) == false) {
                int orient = random.nextInt(4) + 1;
                arrMonster.get(i).changeOrient(orient);
            }
        }
    }

    /**
     * Save score to a text file.
     */
    public void saveScore() {
        System.out.println();
        // if(mBomber.getScore() > arrHighScore.get(arrHighScore.size()-1).getScore()){
        String name = JOptionPane.showInputDialog("Please input Your Name: ");
        HighScore newScore = new HighScore(name, mBomber.getScore());
        arrHighScore.add(newScore);
        // }
        Collections.sort(arrHighScore, new Comparator<HighScore>() {

            @Override
            public int compare(HighScore score1, HighScore score2) {
                if (score1.getScore() < score2.getScore()) {
                    return 1;
                } else {
                    if (score1.getScore() == score2.getScore()) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            }
        });

        if (arrHighScore.size() > 10) {
            arrHighScore.remove(arrHighScore.size() - 1);
        }

        try {
            FileOutputStream fileOutput = new FileOutputStream("src/highScore/HighScore.txt");
            for (int i = 0; i < arrHighScore.size(); i++) {
                String content = arrHighScore.get(i).getName() + ":" + arrHighScore.get(i).getScore() + "\n";
                fileOutput.write(content.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Box> getArrBox() {
        return arrBox;
    }

    public ArrayList<Bomb> getArrBomb() {
        return arrBomb;
    }

    public Bomber getmBomber() {
        return mBomber;
    }

    public int getStatus() {
        return status;
    }

    public void setRound(int round) {
        this.round = 1;
    }

}
