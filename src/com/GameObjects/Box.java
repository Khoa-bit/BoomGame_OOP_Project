package com.GameObjects;

import javax.swing.*;
import java.awt.*;

public class Box {
    private final int x,y,width,height,type;
    private final Image img;
    public Box(int x, int y, int type,String images) {
        super();
        this.x = x;
        this.y = y;
        this.type = type;
        this.img = new ImageIcon(getClass().getResource(images)).getImage();
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
    }

    public void drawBox(Graphics2D g2d){
        g2d.drawImage(img, x, y, null);

    }

    public int getType() {
        return type;
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
