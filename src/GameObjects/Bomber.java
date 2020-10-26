package GameObjects;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import GUI.GUI;


public class Bomber extends Entity {
    public static int ALLOW_RUN=0;
    public static int DISALLOW_RUN=1;
    protected int sizeBomb,quantityBomb,status,score,heart;



    public Bomber(int x, int y, int type, int orient, int speed, int sizebomb, int quantityBomb) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.runBomb=DISALLOW_RUN;
        this.orient = orient;
        this.speed = speed;
        this.sizeBomb = sizebomb;
        this.quantityBomb = quantityBomb;
        this.heart = 3;
        this.score=0;
        this.status = Entity.ALIVE;
        this.img = new ImageIcon(getClass().getResource("/Images/bomber_down.png")).getImage();
        width = img.getWidth(null);
        height = img.getHeight(null)-20;
    }

    public void setNew(int x,int y) {
        this.x = x;
        this.y = y;
        this.status = ALIVE;
        this.img = new ImageIcon(getClass().getResource("/Images/bomber_down.png")).getImage();
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getQuantityBomb() {
        return quantityBomb;
    }

    public void setQuantityBomb(int quantityBomb) {
        if(quantityBomb>5){
            return;
        }
        this.quantityBomb = quantityBomb;
    }


    public void setSizeBomb(int sizeBomb) {
        if(sizeBomb>3){
            return;
        }
        this.sizeBomb = sizeBomb;
    }

    public int getSizeBomb() {
        return sizeBomb;
    }

    public int getType() {
        return type;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    @Override
    public boolean move(int count) {
        if(status==DEAD){
            return false;
        }
        return super.move(count);
    }

    @Override
    public void changeOrient(int orient) {
        if(this.status==DEAD){
            return;
        }
        super.changeOrient(orient);
        switch (orient) {
            case LEFT:
                img = new ImageIcon(getClass().getResource("/Images/bomber_left.png")).getImage();
                break;
            case RIGHT:
                img = new ImageIcon(getClass().getResource("/Images/bomber_right.png")).getImage();
                break;
            case UP:
                img = new ImageIcon(getClass().getResource("/Images/bomber_up.png")).getImage();
                break;
            case DOWN:
                img = new ImageIcon(getClass().getResource("/Images/bomber_down.png")).getImage();
                break;
            default:
                break;
        }
    }
}

