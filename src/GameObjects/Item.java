package GameObjects;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Item {
    // test the git commit :<<<<
    public static int Item_Bomb = 1;
    public static int Item_BombSize = 2;
    public static int Item_Shoe = 3;
    private int x, y, type, width, height, timeline;

    // Getter and Setter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getTimeline() {
        return timeline;
    }

    public void setTimeline(int timeline) {
        this.timeline = timeline;
    }

    public boolean isImpactItemVsBomber(Bomber bomber) {
        Rectangle rec1 = new Rectangle(x, y, width, height);
        Rectangle rec2 = new Rectangle(bomber.getX(), bomber.getY(), bomber.getWidth(), bomber.getHeight());
        return rec1.intersects(rec2);
    }
}
