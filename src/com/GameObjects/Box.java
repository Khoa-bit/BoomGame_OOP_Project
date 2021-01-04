package com.GameObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Box {
    private final int x, y, width, height;
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

    public void drawBox(Graphics2D g2d) {
        g2d.drawImage(img, x, y, null);

    }

    public int doesBoxImpactCharacter(Character character) {
        Rectangle rec1 = new Rectangle(x, y, width, height);
        Rectangle rec2 = new Rectangle(character.getX(), character.getY(), character.getWidth(), character.getHeight());
        Rectangle rec3 = new Rectangle();
        if (rec1.intersects(rec2)) {
            Rectangle2D.intersect(rec1, rec2, rec3);
            if (rec3.getHeight() == 1
                    && (character.getOrient() == Character.UP || character.getOrient() == Character.DOWN)) {
                if (character.getX() == rec3.getX()) {
                    return (int) rec3.getWidth();
                } else {
                    return (int) -rec3.getWidth();
                }
            } else {
                if (character.getY() == rec3.getY()) {
                    return (int) rec3.getHeight();
                } else {
                    return (int) -rec3.getHeight();
                }
            }
        }
        return 0;
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
