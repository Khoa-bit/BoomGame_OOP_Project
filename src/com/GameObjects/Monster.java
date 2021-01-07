package com.GameObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Monster extends Movable {
	public Monster(int x, int y, int type, int orient, int speed, int heart, String images) {
		super(x, y, type, orient, speed);
		this.heart = heart;
		this.img = new ImageIcon(getClass().getResource(images)).getImage();
		width = img.getWidth(null);
		if (type == Movable.MONSTER) {
			height = img.getHeight(null) - 23;
		} else {
			height = img.getHeight(null) - 38;
		}
	}

	@Override
	public void drawSelf(Graphics2D g2d) {
		if (type == Movable.MONSTER) {
			g2d.drawImage(img, x, y - 23, null);
		} else if (type == Movable.BOSS) {
			g2d.drawImage(img, x, y - 38, null);
			g2d.setColor(Color.WHITE);
			g2d.drawRect(x + 13, y - 54, width - 26, 12);
			Image imgHeartBoss = new ImageIcon(getClass().getResource("/Images/heart_boss.png")).getImage();
			int a = 0;
			for (int i = 0; i < (heart - 1) / 250 + 1; i++) {
				g2d.drawImage(imgHeartBoss, x + 18 + a, y - 52, null);
				a = a + 10;
			}
		}
	}

	public void changeOrient(int orient) {
		if (type == Movable.MONSTER) {
			super.changeOrient(orient, "/Images/monster_");
		} else {
			super.changeOrient(orient, "/Images/boss_");
		}
	}

	public int getType() {
		return type;
	}
}
