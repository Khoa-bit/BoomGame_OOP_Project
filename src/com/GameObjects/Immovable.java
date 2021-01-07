package com.GameObjects;


public abstract class Immovable extends Character {
    public Immovable(int x, int y) {
        super(x, y);
    }

    public Immovable(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

}
