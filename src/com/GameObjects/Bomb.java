package com.GameObjects;

import com.GUI.GUI;

import java.awt.*;

import javax.swing.ImageIcon;

public class Bomb extends Immovable {
    protected int size, orient, timeline;
    private final Image img = new ImageIcon(getClass().getResource("/Images/boom.png")).getImage();

    public Bomb(int x, int y, int size, int timeline) {
        super((x / GUI.TILES) * GUI.TILES, (y / GUI.TILES) * GUI.TILES);
        this.size = size;
        this.orient = 0;
        this.timeline = timeline;
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
    }

    @Override
    public void drawSelf(Graphics2D g2d) {
        g2d.drawImage(img, x, y, null);
    }

    public void deadlineBomb() {
        this.timeline--;
    }

    public int getTimeline() {
        return timeline;
    }

    public int getSize() {
        return size;
    }

    public boolean setRun(Bomber bomber) {
        Rectangle rec2 = new Rectangle(x, y, GUI.TILES, GUI.TILES);
        Rectangle rec3 = new Rectangle(bomber.getX(), bomber.getY(), bomber.getWidth(), bomber.getHeight());
        return rec2.intersects(rec3);
    }

    public void setTimeline(int timeline) {
        this.timeline = timeline;
    }

    public boolean doesImpactNewBomb(int xNewBomb, int yNewBomb) {
        Rectangle rec1 = new Rectangle(x, y, GUI.TILES, GUI.TILES);
        Rectangle rec2 = new Rectangle(xNewBomb, yNewBomb, GUI.TILES, GUI.TILES);
        return rec1.intersects(rec2);
    }

    public int doesBombImpactCharacter(Movable movable) {
        if (movable.getRunBomb() == Movable.ALLOW_RUN) {
            return 0;
        }
        Rectangle rec2 = new Rectangle(x, y, 45, 45);
        Rectangle rec3 = new Rectangle(movable.getX(), movable.getY(), movable.getWidth(), movable.getHeight());
        if (rec2.intersects(rec3)) {
            return 1;
        }
        return 0;
    }
}
