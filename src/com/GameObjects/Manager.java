package com.GameObjects;

//import sound.GameSound;

import com.GUI.GUI;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Manager {
    private Random random = new Random();
    private Bomber mBomber;
    private ArrayList<Box> arrBox;
    private ArrayList<Box> arrShadow;
    private ArrayList<Bomb> arrBomb;
    private ArrayList<BombBang> arrBombBang;
    private ArrayList<Monster> arrMonster;
    private ArrayList<HightScore> arrHightScore;
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
                innit("src/Map1/BOX.txt", "src/Map1/SHADOW.txt","src/Map1/MONSTER.txt");
                nextRound = 0;
                status = 0;
                break;

            case 2:
                mBomber.setNew(315, 270);
                innit("src/Map2/BOX.txt", "src/Map2/SHADOW.txt","src/Map2/MONSTER.txt");
                nextRound = 0;
                status = 0;
                break;

            case 3:
                mBomber.setNew(315, 495);
                innit("src/Map3/BOX.txt", "src/Map3/SHADOW.txt","src/Map3/MONSTER.txt");
                nextRound = 0;
                status = 0;
                break;

            default:
                break;
        }

    }

    public void innit(String pathBox, String pathShadow, String pathMonster) {
        arrBox = new ArrayList<>();
        arrShadow = new ArrayList<>();
        arrBomb = new ArrayList<>();
        arrBombBang = new ArrayList<>();
        arrMonster = new ArrayList<Monster>();
        arrHightScore = new ArrayList<HightScore>();

        innitArrBox(pathBox);
        innitArrShadow(pathShadow);
        initarrMonster(pathMonster);
        innitArrHightScore("src/hightscore/HightScore.txt");
        
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
    public void initarrMonster(String path) {
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
				Monster monster = new Monster(x, y, type, orient, speed, heart,
						images);
				arrMonster.add(monster);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public void innitArrHightScore(String path){
		try {
			FileReader file = new FileReader(path);
			BufferedReader input = new BufferedReader(file);
			String line;
			while ((line = input.readLine()) != null) {
				String str[] = line.split(":");
				String name = str[0];
				int score = Integer.parseInt(str[1]);
				HightScore hightScore = new HightScore(name, score);
				arrHightScore.add(hightScore);
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
        }
        else{
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
    public void checkWinAndLose() {
        if (arrMonster.size() == 0) {
            saveScore();
			return;
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
        for (int k = 0; k < arrBombBang.size(); k++) {
			arrBombBang.get(k).deadlineBomb();
			for (int j = 0; j < arrMonster.size(); j++) {
                if (arrBombBang.get(k).isImpactBombBangVsActor(
						arrMonster.get(j))) {
					if(arrMonster.get(j).getHeart()>1){
						arrMonster.get(j).setHeart(arrMonster.get(j).getHeart()-1);
                    }
                    else{
                        mBomber.setScore(mBomber.getScore() + 1);
						arrMonster.remove(j);
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
    public void saveScore(){
		System.out.println();
		if(mBomber.getScore()>arrHightScore.get(arrHightScore.size()-1).getScore()){
			String name = JOptionPane.showInputDialog("Please input Your Name: ");
			HightScore newScore = new HightScore(name, mBomber.getScore());
			arrHightScore.add(newScore);
		}
		Collections.sort(arrHightScore, new Comparator<HightScore>() {

			@Override
			public int compare(HightScore score1, HightScore score2) {
				if(score1.getScore() < score2.getScore()){
					return 1;
				}
				else{
					if(score1.getScore() == score2.getScore()){
						return 0;
					}
					else{
						return -1;
					}
				}
			}
		});
		
		if(arrHightScore.size() > 10){
			arrHightScore.remove(arrHightScore.size()-1);
		}
		
		try {
            FileOutputStream fileOutput = new FileOutputStream("src/hightscore/HightScore.txt");
		    for(int i=0; i<arrHightScore.size(); i++){
				String content = arrHightScore.get(i).getName()+":"+arrHightScore.get(i).getScore()+"\n";
				fileOutput.write(content.getBytes());
			}
        } 
        catch (IOException e ) {
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

