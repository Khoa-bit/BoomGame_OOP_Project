package com.GameObjects;

import com.GUI.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Movable extends Character {
    public static int ALLOW_RUN = 0;
    public static int DISALLOW_RUN = 1;
    public static final int BOMBER = 1;
    public static final int MONSTER = 2;
    public static final int BOSS = 3;

    protected int type, orient, speed, runBomb, heart;
    protected Image img;

    public Movable(int x, int y, int type, int orient, int speed) {
        super(x, y);
        this.type = type;
        this.orient = orient;
        this.speed = speed;
        this.runBomb = DISALLOW_RUN;
    }

    public boolean move(int count, ArrayList<Bomb> arrBomb, ArrayList<Box> arrBox) {
        if (count % speed != 0) {
            return true;
        }
        switch (orient) {
            case LEFT:
                if (x <= 0) {
                    return false;
                }
                x = x - 1;

                for (Bomb bomb : arrBomb) {
                    if (bomb.doesBombImpactCharacter(this) == 1) {
                        x = x + 1;
                        return false;
                    }
                }

                for (Box box : arrBox) {
                    int kq = box.doesMovableImpactBox(this);
                    if (kq != 0) {
                        if (kq >= -20 && kq <= 20) {
                            if (kq > 0) {
                                y = y + 1;
                            } else {
                                y = y - 1;
                            }
                        }
                        x = x + 1;
                        return false;
                    }
                }

                break;
            case RIGHT:
                if (x > (GUI.WIDTHPLAY - width)) {
                    return false;
                }
                x = x + 1;

                for (Bomb bomb : arrBomb) {
                    if (bomb.doesBombImpactCharacter(this) == 1) {
                        x = x - 1;
                        return false;
                    }
                }

                for (Box box : arrBox) {
                    int kq = box.doesMovableImpactBox(this);
                    if (kq != 0) {
                        if (kq >= -20 && kq <= 20) {
                            if (kq > 0) {
                                y = y + 1;
                            } else {
                                y = y - 1;
                            }
                        }
                        x = x - 1;
                        return false;
                    }
                }

                break;
            case UP:
                if (y <= 0) {
                    return false;
                }
                y = y - 1;

                for (Bomb bomb : arrBomb) {
                    if (bomb.doesBombImpactCharacter(this) == 1) {
                        y = y + 1;
                        return false;
                    }
                }

                for (Box box : arrBox) {
                    int kq = box.doesMovableImpactBox(this);
                    if (kq != 0) {
                        if (kq >= -20 && kq <= 20) {
                            if (kq > 0) {
                                x = x + 1;
                            } else {
                                x = x - 1;
                            }
                        }
                        y = y + 1;
                        return false;
                    }
                }

                break;
            case DOWN:
                if (y >= (GUI.HEIGHTPLAY - height)) {
                    return false;
                }
                y = y + 1;

                for (Bomb bomb : arrBomb) {
                    if (bomb.doesBombImpactCharacter(this) == 1) {
                        y = y - 1;
                        return false;
                    }
                }

                for (Box box : arrBox) {
                    int kq = box.doesMovableImpactBox(this);
                    if (kq != 0) {
                        if (kq >= -20 && kq <= 20) {
                            if (kq > 0) {
                                x = x + 1;
                            } else {
                                x = x - 1;
                            }
                        }
                        y = y - 1;
                        return false;
                    }
                }

                break;

            default:
                break;
        }
        return true;
    }

    public void changeOrient(int orient, String path) {
        this.orient = orient;
        switch (orient) {
            case LEFT:
                img = new ImageIcon(getClass().getResource(path + "left.png")).getImage();
                break;
            case RIGHT:
                img = new ImageIcon(getClass().getResource(path + "right.png")).getImage();
                break;
            case UP:
                img = new ImageIcon(getClass().getResource(path + "up.png")).getImage();
                break;
            case DOWN:
                img = new ImageIcon(getClass().getResource(path + "down.png")).getImage();
                break;
            default:
                break;
        }
    }

    public int getOrient() {
        return orient;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if (speed < 3) {
            return;
        }
        this.speed = speed;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public void setRunBomb(int runBomb) {
        this.runBomb = runBomb;
    }

    public int getRunBomb() {
        return runBomb;
    }
}
