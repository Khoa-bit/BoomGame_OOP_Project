package com.GameObjects;

import com.GUI.GUI;
import java.awt.*;
import java.util.ArrayList;

public abstract class Character implements Drawable {
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;

    protected int x, y, width, height;

    public Character(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 0;
        this.height = 0;
    }

    public Character(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

//    public int getType() {
//        return type;
//    }
}
