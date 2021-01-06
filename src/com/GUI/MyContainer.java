package com.GUI;

import Sounds.SFX;

import java.awt.*;
import javax.swing.JPanel;

public class MyContainer extends JPanel {
    private static final String TAG_MENU = "tag_menu";
    private static final String TAG_PLAY_GAME = "tag_play_game";
    private static final String TAG_OPTION = "tag_option";
    private static final String TAG_HIGH_SCORE = "tag_high_score";
    private CardLayout mCardLayout;
    private GUI gui;
    private Menu mMenu;
    private Option mOption;
    private PlayGame mPlayGame;
    private HighScorePanel mHighScorePanel;

    public MyContainer(GUI mGui) {
        this.gui = mGui;
        setBackground(Color.WHITE);
        mCardLayout = new CardLayout();
        setLayout(mCardLayout);
        mMenu = new Menu(this);
        add(mMenu, TAG_MENU);
        mPlayGame = new PlayGame(this);
        add(mPlayGame, TAG_PLAY_GAME);
        mOption = new Option(this);
        add(mOption, TAG_OPTION);
        mHighScorePanel = new HighScorePanel(this);
        add(mHighScorePanel, TAG_HIGH_SCORE);

        setShowMenu();
    }

    public GUI getGui() {
        return gui;
    }

    public void setShowMenu() {
        mCardLayout.show(MyContainer.this, TAG_MENU);
        mMenu.requestFocus();
        mPlayGame = null;
        SFX.stopAllClip();
        SFX.playSoundLoop(SFX.menu);
    }

    public void setShowOption() {
        mCardLayout.show(MyContainer.this, TAG_OPTION);
        mOption.requestFocus();
        SFX.stopAllClip();
        SFX.playSoundLoop(SFX.highScore);
    }

    public void setShowPlay() {
        mPlayGame = new PlayGame(this);
        add(mPlayGame, TAG_PLAY_GAME);
        mCardLayout.show(MyContainer.this, TAG_PLAY_GAME);
        mPlayGame.requestFocus();
        SFX.stopAllClip();
        SFX.playSoundLoop(SFX.background);
    }

    public void setShowHighScore() {
        mCardLayout.show(MyContainer.this, TAG_HIGH_SCORE);
        mHighScorePanel.ReadFileHighScore();
        mHighScorePanel.requestFocus();
        SFX.stopAllClip();
        SFX.playSoundLoop(SFX.highScore);
    }

}