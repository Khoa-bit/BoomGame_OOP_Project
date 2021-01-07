package com.GameObjects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Item extends Immovable {

    public static int Item_Bomb = 1;
    public static int Item_BombSize = 2;
    public static int Item_Shoe = 3;
    private int type, width, height, timeLine;
    private Image img;

    public Item(int x, int y, int type, String image) {
        super(x, y);
        this.type = type;
        this.img = new ImageIcon(getClass().getResource(image)).getImage();
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
        timeLine = 250;
    }

    @Override
    public void drawSelf(Graphics2D g2d) {
        g2d.drawImage(img, x, y, null);
    }

    public int getType() {
        return type;
    }

    public int getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(int timeLine) {
        this.timeLine = timeLine;
    }

    /**
     * @summary This method is to check whether the Bomber impacts any items or not
     * @param bomber
     * @return
     */
    public boolean doesBomberImpactItem(Bomber bomber) {
        // Create 2 rectangle to check intersection
        Rectangle rec1 = new Rectangle(x, y, width, height);
        Rectangle rec2 = new Rectangle(bomber.getX(), bomber.getY(), bomber.getWidth(), bomber.getHeight());
        if (rec1.intersects(rec2))
            System.out.println("isImpactItem: " + rec1.intersects(rec2));
        return rec1.intersects(rec2);
    }

}
