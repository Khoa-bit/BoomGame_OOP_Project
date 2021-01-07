package com.GUI;

import Sounds.SFX;
import com.GameObjects.Manager;
import com.GameObjects.Bomber;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.BitSet;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PlayGame extends JPanel implements Runnable, ActionListener {
    public static boolean IS_RUNNING = true;
    private MyContainer mContainer;
    private BitSet traceKey = new BitSet();
    private Manager mManager = new Manager();
    private int count = 0;
    private int deadlineBomb = 0;
    private int move = 0;
    private int timeDead = 0;
    private int timeLose = 0;
    private int timeNext = 0;
    private JButton btn_Menu;

    public PlayGame(MyContainer mContainer) {
        this.mContainer = mContainer;
        setBackground(Color.WHITE);
        setLayout(null);
        setFocusable(true);
        addKeyListener(keyAdapter);
        Thread mytheard = new Thread(this);
        mytheard.start();
        innitCompts();
    }

    private void innitCompts() {
        btn_Menu = new JButton();
        btn_Menu.setText("Menu");
        btn_Menu.setBounds(750, 520, 100, 30);
        btn_Menu.addActionListener(this);
        add(btn_Menu);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new java.awt.BasicStroke(2));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Drawing the Gameplay UI
        mManager.drawBackground(g2d);
        mManager.drawAllBomb(g2d);
        mManager.drawAllItem(g2d); // Implements drawAllItem before drawAllBox so that all the boxs will be in
                                   // front of all the items
        mManager.drawAllBox(g2d);
        mManager.drawAllMonster(g2d);
        mManager.getmBomber().drawSelf(g2d);
        mManager.drawAllShawDow(g2d);
        mManager.drawInfo(g2d);
        mManager.drawAllBoss(g2d);
        if (mManager.getStatus() == 1) {
            mManager.drawDialog(g2d, 1);
        }
        if (mManager.getStatus() == 2) {
            mManager.drawDialog(g2d, 2);
        }
        if (mManager.getStatus() == 3) {
            mManager.drawDialog(g2d, 3);
        }
    }

    private KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            traceKey.set(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            traceKey.clear(e.getKeyCode());
        }
    };

    @Override
    public void run() {
        while (IS_RUNNING) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (traceKey.get(KeyEvent.VK_LEFT)) {
                mManager.getmBomber().changeOrient(Bomber.LEFT);
                mManager.getmBomber().move(count, mManager.getArrBomb(), mManager.getArrBox());

            }
            if (traceKey.get(KeyEvent.VK_RIGHT)) {
                mManager.getmBomber().changeOrient(Bomber.RIGHT);
                mManager.getmBomber().move(count, mManager.getArrBomb(), mManager.getArrBox());
            }
            if (traceKey.get(KeyEvent.VK_UP)) {
                mManager.getmBomber().changeOrient(Bomber.UP);
                mManager.getmBomber().move(count, mManager.getArrBomb(), mManager.getArrBox());

            }
            if (traceKey.get(KeyEvent.VK_DOWN)) {
                mManager.getmBomber().changeOrient(Bomber.DOWN);
                mManager.getmBomber().move(count, mManager.getArrBomb(), mManager.getArrBox());
            }
            if (traceKey.get(KeyEvent.VK_SPACE)) {
                mManager.innitBomb();
                mManager.getmBomber().setRunBomb(Bomber.ALLOW_RUN);
            }
            mManager.setRunBomer();
            mManager.deadLineAllBomb();
            mManager.checkDead();
            mManager.checkImpactItem();
            mManager.checkWinAndLose();

            if (mManager.getStatus() == 1) {
                timeLose++;
                if (timeLose == 3000) {
                    mManager.innitManager();
                    mContainer.setShowMenu();
                    timeLose = 0;
                }
            }

            if (mManager.getStatus() == 2) {
                timeNext++;
                if (timeNext == 3000) {
                    mManager.innitManager();
                    timeNext = 0;
                }
            }

            if (mManager.getStatus() == 3) {
                timeNext++;
                if (timeNext == 3000) {
                    mManager.innitManager();
                    mContainer.setShowMenu();
                    timeNext = 0;
                }
            }

            if (mManager.getmBomber().getStatus() == Bomber.DEAD) {
                timeDead++;
                if (timeDead == 3000) {
                    mManager.setNewBomber();
                    timeDead = 0;
                }
            }

            if (move == 0) {
                mManager.changeOrientAll();
                move = 5000;
            }
            if (move > 0) {
                move--;
            }
            mManager.moveAllMonster(count);
            repaint();
            count++;
            if (count == 1000000) {
                count = 0;
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_Menu) {
            SFX.playSound(SFX.click);
            mManager.setRound(1);
            mManager.innitManager();
            mContainer.setShowMenu();
        }

    }
}
