package com.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JFrame {
    public static final int WIDTHJF = 905;
    public static final int HEIGHTJF = 610;
//    15 x 13 grid
    public static final int WIDTHPLAY = 675;
    public static final int HEIGHTPLAY = 585;
    public static final int TILES = 45;

    public GUI() {
        this.setSize(WIDTHJF, HEIGHTJF);
        this.setLayout(new CardLayout());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        MyContainer mContainer = new MyContainer(this);
        add(mContainer);
        WindowAdapter mwindow = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                PlayGame.IS_RUNNING = false;
            }
        };
        addWindowListener(mwindow);
    }

}
