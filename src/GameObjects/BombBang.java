package GameObjects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class BombBang {
    private int x,y,size,timeLine;
    private Image img_left, img_right, img_up, img_down;

    public BombBang(int x, int y, int size) {
        this.x=x;
        this.y=y;
        this.size=size;
        this.timeLine = 250;
        img_left = new ImageIcon(getClass().getResource("/Images/bombbang_left_1.png")).getImage();
        img_right = new ImageIcon(getClass().getResource("/Images/bombbang_right_1.png")).getImage();
        img_up = new ImageIcon(getClass().getResource("/Images/bombbang_up_1.png")).getImage();
        img_down = new ImageIcon(getClass().getResource("/Images/bombbang_down_1.png")).getImage();
    }

    public void drawBongBang(Graphics2D g2d){
        g2d.drawImage(img_left, x+45-img_left.getWidth(null), y,null);
        g2d.drawImage(img_right, x, y,null);
        g2d.drawImage(img_up, x, y+45-img_up.getHeight(null),null);
        g2d.drawImage(img_down, x, y,null);
    }

    public boolean isImpactBombBangvsBomb(Bomb bomb){
        Rectangle rec1 = new Rectangle(x+45-img_left.getWidth(null), y, img_left.getWidth(null), img_left.getHeight(null));
        Rectangle rec2 = new Rectangle(x, y, img_right.getWidth(null), img_right.getHeight(null));
        Rectangle rec3 = new Rectangle(x, y+45-img_up.getHeight(null), img_up.getWidth(null), img_up.getHeight(null));
        Rectangle rec4 = new Rectangle(x, y, img_down.getWidth(null), img_down.getHeight(null));
        Rectangle rec5 = new Rectangle(bomb.getX(), bomb.getY(), bomb.getWidth(), bomb.getHeight());
        if(rec1.intersects(rec5) || rec2.intersects(rec5) || rec3.intersects(rec5) || rec4.intersects(rec5)){
            return true;
        }
        return false;
    }

    public void deadlineBomb(){
        if(timeLine>0){
            timeLine--;
        }
    }

    public int getTimeLine() {
        return timeLine;
    }
}

