package com.GUI;

import java.awt.*;
import javax.swing.JPanel;

public class MyContainer extends JPanel{
    private static final String TAG_MENU = "tag_menu";
    private static final String TAG_PLAYGAME = "tag_playgame";
    private static final String TAG_OPTION = "tag_option";
    private static final String TAG_HIGHTSCORE = "tag_hightscore";
    private CardLayout mCardLayout;
    private GUI gui;
    private Menu mMenu;
    private Option mOption;
    private PlayGame mPlayGame;
    private HightScorePanel mHightScorePanel;

    public MyContainer(GUI mGui){
        this.gui = mGui;
        setBackground(Color.WHITE);
        mCardLayout = new CardLayout();
        setLayout(mCardLayout);
        mMenu = new Menu(this);
        add(mMenu,TAG_MENU);
        mPlayGame = new PlayGame(this);
        add(mPlayGame, TAG_PLAYGAME);
        mOption = new Option(this);
        add(mOption, TAG_OPTION);
        mHightScorePanel = new HightScorePanel(this);
		add(mHightScorePanel, TAG_HIGHTSCORE);

        setShowMenu();
    }

    public GUI getGui() {
        return gui;
    }

    public void setShowMenu(){
        mCardLayout.show(MyContainer.this, TAG_MENU);
        mMenu.requestFocus();
    }

    public void setShowOption(){
        mCardLayout.show(MyContainer.this, TAG_OPTION);
        mOption.requestFocus();
    }

    public void setShowPlay(){
        mCardLayout.show(MyContainer.this, TAG_PLAYGAME);
        mPlayGame.requestFocus();
    }

    public void setShowHightScore(){
		mCardLayout.show(MyContainer.this, TAG_HIGHTSCORE);
		mHightScorePanel.requestFocus();
	}
	
}