package com.GameObjects;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Bomber extends Movable {
    public static final int ALIVE = 1;
    public static final int DEAD = 0;

    protected int sizeBomb, quantityBomb, status, score;


    public Bomber(int x, int y, int type, int orient, int speed, int sizeBomb, int quantityBomb) {
        super(x, y, type, orient, speed);
        this.sizeBomb = sizeBomb;
        this.quantityBomb = quantityBomb;
        this.heart = 3;
        this.score = 0;
        this.status = ALIVE;

        this.img = new ImageIcon(getClass().getResource("/Images/bomber_down.png")).getImage();
        width = img.getWidth(null);
        height = img.getHeight(null) - 20;
    }


    public void setNew(int x, int y) {
        this.x = x;
        this.y = y;
        this.status = ALIVE;
        this.img = new ImageIcon(getClass().getResource("/Images/bomber_down.png")).getImage();
    }

    @Override
    public void drawSelf(Graphics2D g2d) {
        g2d.drawImage(img, x, y - 20, null);
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
        if (quantityBomb > 5) {
            return;
        }
        this.quantityBomb = quantityBomb;
    }

    public void setSizeBomb(int sizeBomb) {
        if (sizeBomb > 3) {
            return;
        }
        this.sizeBomb = sizeBomb;
    }

    public int getSizeBomb() {
        return sizeBomb;
    }

    @Override
    public boolean move(int count, ArrayList<Bomb> arrBomb, ArrayList<Box> arrBox) {
        if (status == DEAD) {
            return false;
        }
        return super.move(count, arrBomb, arrBox);
    }

    public void changeOrient(int orient) {
        if (status == DEAD) {
            return;
        }
        super.changeOrient(orient, "/Images/bomber_");
    }

    public boolean isImpactBomberVsCharacter(Character character) {
        if (status == DEAD) {
            return false;
        }
        Rectangle rec1 = new Rectangle(x, y, width, height);
        Rectangle rec2 = new Rectangle(character.getX(), character.getY(), character.getWidth(), character.getHeight());
        return rec1.intersects(rec2);
    }

}
