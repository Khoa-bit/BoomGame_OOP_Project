package com.GameObjects;

//import sound.GameSound;

import com.GUI.GUI;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Manager {
    private Random random = new Random();
    private Bomber mBomber;
    private ArrayList<Box> arrBox;
    private ArrayList<Box> arrShadow;
    private ArrayList<Bomb> arrBomb;
    private ArrayList<BombBang> arrBombBang;
    private int round=1;
    private int nextRound = 0;
    private int status = 0;

    public Manager() {
        innitManager();
    }

    public void innitManager() {
        switch (round) {
            case 1:
                mBomber = new Bomber(0, 540, Actor.BOMBER, Actor.DOWN, 5, 2, 5);
                innit("src/Map1/BOX.txt", "src/Map1/SHADOW.txt");
                nextRound = 0;
                status = 0;
                break;

            default:
                break;
        }

    }

    public void innit(String pathBox, String pathShadow) {
        arrBox = new ArrayList<>();
        arrShadow = new ArrayList<>();
        arrBomb = new ArrayList<>();
        arrBombBang = new ArrayList<>();

        innitArrBox(pathBox);
        innitArrShadow(pathShadow);
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
                }
                else if (pathImage.equals("/Images/shawdow2.png")) {
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
        Bomb mBomb = new Bomb(x, y, mBomber.getSizeBomb(), 3000);
        arrBomb.add(mBomb);
    }

    public void drawDialog(Graphics2D g2d, int type) {
        g2d.setFont(new Font("Arial", Font.BOLD, 70));
        g2d.setColor(Color.RED);
        if(type==1){
            g2d.drawString("You Lose !!!", 200, 250);
        }else{
            if(type==2){
                g2d.drawString("Round "+round, 200, 250);
            }else{
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

    public void deadLineAllBomb() {
        for (int i = 0; i < arrBomb.size(); i++) {
            arrBomb.get(i).deadlineBomb();
            if (arrBomb.get(i).getTimeline() == 250) {
                BombBang bomBang = new BombBang(arrBomb.get(i).getX(), arrBomb.get(i).getY(), arrBomb.get(i).getSize(), arrBox);
                arrBombBang.add(bomBang);
                arrBomb.remove(i);
            }
        }

        for (int i = 0; i < arrBombBang.size(); i++) {
            for (int j = 0; j < arrBomb.size(); j++) {
                if (arrBombBang.get(i).isImpactBombBangvsBomb(arrBomb.get(j))) {
                    BombBang bomBang = new BombBang(arrBomb.get(j).getX(), arrBomb.get(j).getY(), arrBomb.get(j).getSize(), arrBox);
                    arrBombBang.add(bomBang);
                    arrBomb.remove(j);
                }
            }
        }

        for (int k = 0; k < arrBombBang.size(); k++) {
            arrBombBang.get(k).deadlineBomb();
            if (arrBombBang.get(k).getTimeLine() == 0) {
                arrBombBang.remove(k);
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
    }

    public void moveAllMonster(int count) {
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

