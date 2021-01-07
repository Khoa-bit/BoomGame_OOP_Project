package com.GameObjects;

import com.GUI.GUI;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class BombBang extends Immovable {
    private int size, timeLine;
    private Image img_left, img_right, img_up, img_down;

    public BombBang(int x, int y, int size, ArrayList<Box> arrBox) {
        super(x, y);
        this.size = size;
        this.timeLine = 250; // This timeLine is the speed of motion that the bomb explodes
        img_left = new ImageIcon(getClass().getResource("/Images/bombbang_left_1.png")).getImage();
        img_right = new ImageIcon(getClass().getResource("/Images/bombbang_right_1.png")).getImage();
        img_up = new ImageIcon(getClass().getResource("/Images/bombbang_up_1.png")).getImage();
        img_down = new ImageIcon(getClass().getResource("/Images/bombbang_down_1.png")).getImage();
        for (int i = 1; i < size; i++) {
            int tmp_left = 0, tmp_right = 0, tmp_up = 0, tmp_dow = 0;
            for (Box box : arrBox) {
                if (doesImpactOldBox(x - (i) * 45, y, (i + 1) * 45, 45, box)) {
                    tmp_left = 1;
                }
                if (doesImpactOldBox(x, y, (i + 1) * 45, 45, box)) {
                    tmp_right = 1;
                }
                if (doesImpactOldBox(x, y - (i * 45), 45, (i + 1) * 45, box)) {
                    tmp_up = 1;
                }
                if (doesImpactOldBox(x, y, 45, (i + 1) * 45, box)) {
                    tmp_dow = 1;
                }
            }
            if (tmp_left == 0) {
                setImage(LEFT, i + 1);
            }
            if (tmp_right == 0) {
                setImage(RIGHT, i + 1);
            }
            if (tmp_up == 0) {
                setImage(UP, i + 1);
            }
            if (tmp_dow == 0) {
                setImage(DOWN, i + 1);
            }
        }
    }

    @Override
    public void drawSelf(Graphics2D g2d) {
        g2d.drawImage(img_left, x + GUI.TILES - img_left.getWidth(null), y, null);
        g2d.drawImage(img_right, x, y, null);
        g2d.drawImage(img_up, x, y + GUI.TILES - img_up.getHeight(null), null);
        g2d.drawImage(img_down, x, y, null);
    }

    public void setImage(int orient, int size) {
        // Set maximum bomb size to 2
        switch (orient) {
            case LEFT:
                if (size == 2) {
                    img_left = new ImageIcon(getClass().getResource("/Images/bombbang_left_2.png")).getImage();
                }
                break;
            case RIGHT:
                if (size == 2) {
                    img_right = new ImageIcon(getClass().getResource("/Images/bombbang_right_2.png")).getImage();
                }
                break;
            case UP:
                if (size == 2) {
                    img_up = new ImageIcon(getClass().getResource("/Images/bombbang_up_2.png")).getImage();
                }
                break;
            case DOWN:
                if (size == 2) {
                    img_down = new ImageIcon(getClass().getResource("/Images/bombbang_down_2.png")).getImage();
                }
                break;
            default:
                break;
        }
    }

    public boolean doesBombBangImpactBomb(Bomb bomb) {
        Rectangle bang_left = new Rectangle(x + GUI.TILES - img_left.getWidth(null), y, img_left.getWidth(null),
                img_left.getHeight(null));
        Rectangle bang_right = new Rectangle(x, y, img_right.getWidth(null), img_right.getHeight(null));
        Rectangle bang_up = new Rectangle(x, y + GUI.TILES - img_up.getHeight(null), img_up.getWidth(null),
                img_up.getHeight(null));
        Rectangle bang_down = new Rectangle(x, y, img_down.getWidth(null), img_down.getHeight(null));
        Rectangle BombRectangle = new Rectangle(bomb.getX(), bomb.getY(), bomb.getWidth(), bomb.getHeight());
        if (bang_left.intersects(BombRectangle) || bang_right.intersects(BombRectangle)
                || bang_up.intersects(BombRectangle) || bang_down.intersects(BombRectangle)) {
            return true;
        }
        return false;
    }

    private boolean doesImpactOldBox(int x, int y, int width, int height, Box box) {
        Rectangle oldBomb = new Rectangle(x, y, width, height);
        Rectangle newBomb = new Rectangle(box.getX(), box.getY(), box.getWidth(), box.getHeight());
        return oldBomb.intersects(newBomb);
    }

    public boolean doesBombBangImpactCharacter(Movable movable) {
        Rectangle bang_left = new Rectangle(x + 45 - img_left.getWidth(null) + 5, y + 5, img_left.getWidth(null) - 5,
                img_left.getHeight(null) - 10);
        Rectangle bang_right = new Rectangle(x, y + 5, img_right.getWidth(null) - 5, img_right.getHeight(null) - 10);
        Rectangle bang_up = new Rectangle(x + 5, y + 45 - img_up.getHeight(null) + 5, img_up.getWidth(null) - 5,
                img_up.getHeight(null) - 10);
        Rectangle bang_down = new Rectangle(x + 5, y, img_down.getWidth(null) - 10, img_down.getHeight(null) - 5);

        Rectangle characterRectangle = new Rectangle(movable.getX(), movable.getY(), movable.getWidth(),
                movable.getHeight());
        if (bang_left.intersects(characterRectangle) || bang_right.intersects(characterRectangle)
                || bang_up.intersects(characterRectangle) || bang_down.intersects(characterRectangle)) {
            return true;
        }
        return false;
    }

    public boolean doesBombBangImpactBox(Box box) {
        if (!box.isDestructible()) {
            return false;
        }
        Rectangle bang_left = new Rectangle(x + 45 - img_left.getWidth(null), y, img_left.getWidth(null),
                img_left.getHeight(null));
        Rectangle bang_right = new Rectangle(x, y, img_right.getWidth(null), img_right.getHeight(null));
        Rectangle bang_up = new Rectangle(x, y + 45 - img_up.getHeight(null), img_up.getWidth(null),
                img_up.getHeight(null));
        Rectangle bang_down = new Rectangle(x, y, img_down.getWidth(null), img_down.getHeight(null));

        Rectangle boxRectangle = new Rectangle(box.getX(), box.getY(), box.getWidth(), box.getHeight());

        return bang_left.intersects(boxRectangle) || bang_right.intersects(boxRectangle)
                || bang_up.intersects(boxRectangle) || bang_down.intersects(boxRectangle);
    }

    public void deadlineBomb() {
        if (timeLine > 0) {
            timeLine--;
        }
    }

    public int getTimeLine() {
        return timeLine;
    }
}
