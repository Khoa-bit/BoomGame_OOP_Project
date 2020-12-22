package com.GameObjects;

//import sound.GameSound; // CANNOT IMPORT THIS PACKAGE BECAUSE a package with the same name is created

import Sounds.SFX;

import com.GUI.GUI;

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
    private ArrayList<HighScore> arrHighScore;
    private ArrayList<Item> arrItem;
    private int round = 1;
    private boolean nextRound = true;
    private int status = 0;

    public Manager() {
        innitManager();
    }

    public void innitManager() {
        System.out.print("Manager init");
        switch (round) {
            case 1:
                mBomber = new Bomber(0, 540, Actor.BOMBER, Actor.DOWN, 3, 2, 10);
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
        arrHighScore = new ArrayList<>();
        arrItem = new ArrayList<>();
        innitArrBox(pathBox);
        innitArrShadow(pathShadow);
        initArrMonster(pathMonster);
        innitArrHighScore("src/highScore/HighScore.txt");
        initArrItem(pathItem);
    }

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

    public void innitArrBox(String pathBox) {
        try {
            FileReader file = new FileReader(pathBox);
            BufferedReader input = new BufferedReader(file);
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

    public void initArrMonster(String path) {
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
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void innitBomb() {
        int x = mBomber.getX() + mBomber.getWidth() / 2;
        int y = mBomber.getY() + mBomber.getHeight() / 2;
        for (int i = 0; i < arrBomb.size(); i++) {
            if (arrBomb.get(i).isImpact(x, y)) {
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
            arrBomb.get(i).drawActor(g2d);
        }
        for (int i = 0; i < arrBombBang.size(); i++) {
            arrBombBang.get(i).drawBongBang(g2d);
        }
    }

    public void drawAllMonster(Graphics2D g2d) {
        for (int i = 0; i < arrMonster.size(); i++) {
            arrMonster.get(i).drawActor(g2d);
        }
    }

    public void drawAllBox(Graphics2D g2d) {
        for (Box box : arrBox) {
            box.drawBox(g2d);
        }
    }

    public void drawAllShawDow(Graphics2D g2d) {
        for (Box shadow : arrShadow) {
            shadow.drawBox(g2d);
        }
    }

    public void drawInfo(Graphics2D g2d) {
        g2d.drawString("SCORE : " + mBomber.getScore(), 740, 200);

    }

    public void drawAllItem(Graphics2D g2d) {
        for (int i = 0; i < arrItem.size(); i++) {
            arrItem.get(i).drawItem(g2d);
        }
    }

    public void checkWinAndLose() {
        if (mBomber.getHeart() == 0 && nextRound) {
            round = 1;
            status = 1;
            nextRound = false;
            SFX.stopAllClip();
            SFX.playSound(SFX.lose);
            saveScore();
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
        }
    }

    public void checkImpactItem() {
        for (int i = 0; i < arrItem.size(); i++) {
            if (arrItem.get(i).isImpactItemVsBomber(mBomber)) {
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

    public void deadLineAllBomb() {
        for (int i = 0; i < arrBomb.size(); i++) {
            arrBomb.get(i).deadlineBomb();
            if (arrBomb.get(i).getTimeline() == 250) {
                BombBang bomBang = new BombBang(arrBomb.get(i).getX(), arrBomb.get(i).getY(), arrBomb.get(i).getSize(),
                        arrBox);
                arrBombBang.add(bomBang);
                arrBomb.remove(i);

                // GameSFX.play(GameSFX.bombBang, false);
                SFX.playSound(SFX.boomBang);
            }
        }

        for (int i = 0; i < arrBombBang.size(); i++) {
            for (int j = 0; j < arrBomb.size(); j++) {
                if (arrBombBang.get(i).isImpactBombBangvsBomb(arrBomb.get(j))) {
                    BombBang bomBang = new BombBang(arrBomb.get(j).getX(), arrBomb.get(j).getY(),
                            arrBomb.get(j).getSize(), arrBox);
                    arrBombBang.add(bomBang);
                    arrBomb.remove(j);

                    // GameSFX.play(GameSFX.bombBang, false);
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
                if (arrBombBang.get(k).isImpactBombBangVsActor(arrMonster.get(j))) {
                    if (arrMonster.get(j).getHeart() > 1) {
                        arrMonster.get(j).setHeart(arrMonster.get(j).getHeart() - 1);
                    } else {
                        mBomber.setScore(mBomber.getScore() + 1);
                        arrMonster.remove(j);

                        SFX.playSound(SFX.monsterDed);
                    }
                }
            }
        }
        for (int i = 0; i < arrBombBang.size(); i++) {
            for (int j = 0; j < arrBox.size(); j++) {
                if (arrBombBang.get(i).isImpactBombBangvsBox(arrBox.get(j))) {
                    arrBox.remove(j);
                    arrShadow.remove(j);
                }
            }
        }
    }

    public void setRunBomer() {
        if (arrBomb.size() > 0) {
            if (arrBomb.get(arrBomb.size() - 1).setRun(mBomber) == false) {
                mBomber.setRunBomb(Bomber.DISALLOW_RUN);
            }
        }
    }

    public void changeOrientAll() {
        for (int i = 0; i < arrMonster.size(); i++) {
            int orient = random.nextInt(4) + 1;
            arrMonster.get(i).changeOrient(orient);
        }
    }

    public void moveAllMonster(int count) {
        for (int i = 0; i < arrMonster.size(); i++) {
            if (arrMonster.get(i).move(count, arrBomb, arrBox) == false) {
                int orient = random.nextInt(4) + 1;
                arrMonster.get(i).changeOrient(orient);
            }
        }
    }

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
