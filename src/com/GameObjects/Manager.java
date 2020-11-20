package com.GameObjects;

//import sound.GameSound;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Manager {
    private Random random = new Random();
    private Bomber mBomber;
    private ArrayList<Box> arrBox;
    private ArrayList<Box> arrShawDow;
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
                mBomber = new Bomber(0, 540, Actor.BOMBER, Actor.DOWN, 5, 5, 5);
                innit();
                nextRound = 0;
                status = 0;
                break;

            default:
                break;
        }

    }

    public void innit() {
        arrBomb = new ArrayList<Bomb>();
        arrBombBang = new ArrayList<BombBang>();
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

    public void deadLineAllBomb() {
        for (int i = 0; i < arrBomb.size(); i++) {
            arrBomb.get(i).deadlineBomb();
            if (arrBomb.get(i).getTimeline() == 250) {
                BombBang bomBang = new BombBang(arrBomb.get(i).getX(), arrBomb.get(i).getY(), arrBomb.get(i).getSize());
                arrBombBang.add(bomBang);
                arrBomb.remove(i);
            }
        }

        for (int i = 0; i < arrBombBang.size(); i++) {
            for (int j = 0; j < arrBomb.size(); j++) {
                if (arrBombBang.get(i).isImpactBombBangvsBomb(arrBomb.get(j))) {
                    BombBang bomBang = new BombBang(arrBomb.get(j).getX(), arrBomb.get(j).getY(), arrBomb.get(j).getSize());
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

