package com.GameObjects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Item {

    public static int Item_Bomb = 1;
    public static int Item_BombSize = 2;
    public static int Item_Shoe = 3;
    private int x, y, type, width, height, timeLine;
    private Image img;

    public Item(int x, int y, int type, String image) {
        super();
        this.x = x;
        this.y = y;
        this.type = type;
        this.img = new ImageIcon(getClass().getResource(image)).getImage();
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
        timeLine = 250;
    }
}
