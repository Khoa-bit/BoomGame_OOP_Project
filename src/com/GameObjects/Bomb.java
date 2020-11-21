package com.GameObjects;

import com.GUI.GUI;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Bomb extends Actor{
    protected int size, timeline;

    public Bomb(int x, int y, int size, int timeline){
        x=(x/ GUI.TILES)*GUI.TILES;
        y=(y/GUI.TILES)*GUI.TILES;
        this.x=x;
        this.y=y;
        this.size=size;
        this.orient=0;
        this.timeline = timeline;
        this.type =Actor.BOMB;
        img= new ImageIcon(getClass().getResource("/Images/bomb.png")).getImage();
        this.width= img.getWidth(null);
        this.height= img.getHeight(null);
    }

    public Bomb(int x, int y,int orient, int speed, int size, int timeline){
        x=(x/GUI.TILES)*GUI.TILES;
        y=(y/GUI.TILES)*GUI.TILES;
        this.x=x;
        this.y=y;
        this.orient = orient;
        this.speed = 5;
        this.size=size;
        this.timeline = timeline;
        this.type =Actor.BOMB;
        img= new ImageIcon(getClass().getResource("/Images/bomb.png")).getImage();
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
    }

    public void deadlineBomb(){
        this.timeline--;
    }

    public int getTimeline() {
        return timeline;
    }

    public int getSize() {
        return size;
    }

    public boolean setRun(Bomber bomber){
        Rectangle rec2 = new Rectangle(x, y, GUI.TILES, GUI.TILES);
        Rectangle rec3 = new Rectangle(bomber.getX(), bomber.getY(), bomber.getWidth(), bomber.getHeight());
        return rec2.intersects(rec3);
    }

    public void setTimeline(int timeline) {
        this.timeline = timeline;
    }


    public boolean isImpact(int xNewBomb, int yNewBomb){
        Rectangle rec1 = new Rectangle(x, y, GUI.TILES, GUI.TILES);
        Rectangle rec2 = new Rectangle(xNewBomb, yNewBomb, GUI.TILES, GUI.TILES);
        return rec1.intersects(rec2);
    }
}
