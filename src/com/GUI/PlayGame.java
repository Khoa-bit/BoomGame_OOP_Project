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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PlayGame extends JPanel implements Runnable, ActionListener {
    public static boolean IS_RUNNING = true;
    private MyContainer mContainer;
    private BitSet traceKey = new BitSet();
    private Manager gameManager = new Manager();
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
        gameManager.drawBackground(g2d);
        gameManager.drawAllBomb(g2d);
        gameManager.drawAllItem(g2d); // Implements drawAllItem before drawAllBox so that all the boxs will be in
                                      // front of all the items
        gameManager.drawAllBox(g2d);
        gameManager.drawAllMonster(g2d);
        gameManager.getmBomber().drawActor(g2d);
        gameManager.drawAllShawDow(g2d);
        gameManager.drawInfo(g2d);
        gameManager.drawBoss(g2d);
        if (gameManager.getStatus() == 1) {
            gameManager.drawDialog(g2d, 1);
        }
        if (gameManager.getStatus() == 2) {
            gameManager.drawDialog(g2d, 2);
        }
        if (gameManager.getStatus() == 3) {
            gameManager.drawDialog(g2d, 3);
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
                gameManager.getmBomber().changeOrient(Bomber.LEFT);
                gameManager.getmBomber().move(count, gameManager.getArrBomb(), gameManager.getArrBox());

            }
            if (traceKey.get(KeyEvent.VK_RIGHT)) {
                gameManager.getmBomber().changeOrient(Bomber.RIGHT);
                gameManager.getmBomber().move(count, gameManager.getArrBomb(), gameManager.getArrBox());
            }
            if (traceKey.get(KeyEvent.VK_UP)) {
                gameManager.getmBomber().changeOrient(Bomber.UP);
                gameManager.getmBomber().move(count, gameManager.getArrBomb(), gameManager.getArrBox());

            }
            if (traceKey.get(KeyEvent.VK_DOWN)) {
                gameManager.getmBomber().changeOrient(Bomber.DOWN);
                gameManager.getmBomber().move(count, gameManager.getArrBomb(), gameManager.getArrBox());
            }
            if (traceKey.get(KeyEvent.VK_SPACE)) {
                gameManager.innitBomb();
                gameManager.getmBomber().setRunBomb(Bomber.ALLOW_RUN);
            }
            gameManager.setRunBomer();
            gameManager.deadLineAllBomb();
            gameManager.checkDead();
            gameManager.checkImpactItem();
            gameManager.checkWinAndLose();

            if (gameManager.getStatus() == 1) {
                timeLose++;
                if (timeLose == 3000) {
                    gameManager.innitManager();
                    mContainer.setShowMenu();
                    timeLose = 0;
                }
            }

            if (gameManager.getStatus() == 2) {
                timeNext++;
                if (timeNext == 3000) {
                    gameManager.innitManager();
                    timeNext = 0;
                }
            }

            if (gameManager.getStatus() == 3) {
                timeNext++;
                if (timeNext == 3000) {
                    gameManager.innitManager();
                    mContainer.setShowMenu();
                    timeNext = 0;
                }
            }

            if (gameManager.getmBomber().getStatus() == Bomber.DEAD) {
                timeDead++;
                if (timeDead == 3000) {
                    gameManager.setNewBomber();
                    timeDead = 0;
                }
            }

            if (move == 0) {
                gameManager.changeOrientAll();
                move = 5000;
            }
            if (move > 0) {
                move--;
            }
            gameManager.moveAllMonster(count);
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
            gameManager.setRound(1);
            gameManager.innitManager();
            mContainer.setShowMenu();
        }

    }
}
