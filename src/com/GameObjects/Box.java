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

    public int doesCharacterImpactBox(Character character) {
        Rectangle boxRectangle = new Rectangle(x, y, width, height);
        Rectangle characterRectangle = new Rectangle(character.getX(), character.getY(), character.getWidth(),
                character.getHeight());
        Rectangle intersectRectangle = new Rectangle();

        if (boxRectangle.intersects(characterRectangle)) {
            Rectangle2D.intersect(boxRectangle, characterRectangle, intersectRectangle);
            if (intersectRectangle.getHeight() == 1
                    && (character.getOrient() == Character.UP || character.getOrient() == Character.DOWN)) {
                if (character.getX() == intersectRectangle.getX()) {
                    return (int) intersectRectangle.getWidth();
                } else {
                    return (int) -intersectRectangle.getWidth();
                }
            } else {
                if (character.getY() == intersectRectangle.getY()) {
                    return (int) intersectRectangle.getHeight();
                } else {
                    return (int) -intersectRectangle.getHeight();
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
