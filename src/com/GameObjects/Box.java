package com.GameObjects;

import javax.swing.*;
import java.awt.*;

public class Box {
    private final int x,y,width,height;
    private final boolean destructible;
    private final Image img;
    public Box(int x, int y, boolean destructible, String pathImage) {
        super();
        this.x = x;
        this.y = y;
        this.destructible = destructible;
        this.img = new ImageIcon(getClass().getResource(pathImage)).getImage();
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
    }

    public void drawBox(Graphics2D g2d){
        g2d.drawImage(img, x, y, null);

    }

    public boolean isDestructible() {
        return destructible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
