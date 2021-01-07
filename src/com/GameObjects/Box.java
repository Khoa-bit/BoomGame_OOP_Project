package com.GameObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Box extends Immovable{
//    private final int width, height;
    private final boolean destructible;
    private final Image img;

    public Box(int x, int y, boolean destructible, String pathImage) {
        super(x, y);
        this.destructible = destructible;
        this.img = new ImageIcon(getClass().getResource(pathImage)).getImage();
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
    }

    @Override
    public void drawSelf(Graphics2D g2d) {
        g2d.drawImage(img, x, y, null);

    }

    public int doesMovableImpactBox(Movable character) {
        Rectangle boxRectangle = new Rectangle(x, y, width, height);
        Rectangle characterRectangle = new Rectangle(character.getX(), character.getY(), character.getWidth(),
                character.getHeight());
        Rectangle intersectRectangle = new Rectangle();

        if (boxRectangle.intersects(characterRectangle)) {
            Rectangle2D.intersect(boxRectangle, characterRectangle, intersectRectangle);
            if (intersectRectangle.getHeight() == 1
                    && (character.getOrient() == Movable.UP || character.getOrient() == Movable.DOWN)) {
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
}
