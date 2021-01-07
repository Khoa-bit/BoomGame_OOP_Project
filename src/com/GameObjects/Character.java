package com.GameObjects;

import com.GUI.GUI;
import java.awt.*;
import java.util.ArrayList;

public class Character {
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    public static final int ALIVE = 1;
    public static final int DEAD = 0;
    public static final int BOMBER = 1;
    public static final int BOMB = 4;
    public static final int MONSTER = 2;
    public static final int BOSS = 3;

    protected int x, y, type, orient, speed, width, height, runBomb;
    protected Image img;

    public void drawCharacter(Graphics2D g2d) {
        switch (type) {
            case BOMBER:
                g2d.drawImage(img, x, y - 20, null);
                break;
            case MONSTER:
                g2d.drawImage(img, x, y - 23, null);
                break;
            case BOMB:
                g2d.drawImage(img, x, y, null);
                break;

            default:
                break;
        }
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
                    int kq = box.doesCharacterImpactBox(this);
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
                    int kq = box.doesCharacterImpactBox(this);
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
                    int kq = box.doesCharacterImpactBox(this);
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
                    int kq = box.doesCharacterImpactBox(this);
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

    public void changeOrient(int orient) {
        this.orient = orient;
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

    public int getOrient() {
        return orient;
    }

    public void setRunBomb(int runBomb) {
        this.runBomb = runBomb;
    }

    public int getRunBomb() {
        return runBomb;
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

    public int getType() {
        return type;
    }
}