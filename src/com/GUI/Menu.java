package com.GUI;

import SFX.Sound;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JPanel{
    private int padding = 15;
    private GUI mGui;
    private MyContainer mContainer;
    private JLabel lbbackground;
    private JLabel lbPlayGame;
    private JLabel lbOption;
    private JLabel lbExit;
    private JLabel lbHigthScore;
    private ImageIcon backgroundIcon;

    public Menu(MyContainer mContainer){
        this.mContainer = mContainer;
        this.mGui = mContainer.getGui();
        setBackground(Color.BLACK);
        setLayout(null);
        initComps(mGui);
        initbackground();
    }

    public void initComps(GUI mGui){
        lbPlayGame = setLabel((mGui.getWidth()-150)/2-30, (mGui.getHeight()-30)/2-150, "/Images/Play.png");
        lbOption = setLabel(lbPlayGame.getX(),lbPlayGame.getY() + lbPlayGame.getHeight()+padding, "/Images/Option.png");
        lbHigthScore = setLabel(lbOption.getX(),lbOption.getY() + lbPlayGame.getHeight()+padding, "/Images/HightScore.png");
        lbExit = setLabel(lbHigthScore.getX(),lbHigthScore.getY() + lbPlayGame.getHeight()+padding, "/Images/Exit.png");

        lbPlayGame.addMouseListener(mMouseAdapter);
        lbOption.addMouseListener(mMouseAdapter);
        lbHigthScore.addMouseListener(mMouseAdapter);
        lbExit.addMouseListener(mMouseAdapter);
        
        add(lbPlayGame);
        add(lbOption);
        add(lbHigthScore);
        add(lbExit);
    }

    public void initbackground(){
        lbbackground = new JLabel();
        lbbackground.setBounds(0, -10, mGui.getWidth(), mGui.getHeight());
        lbbackground.setBackground(Color.BLACK);
        backgroundIcon = new ImageIcon(getClass().getResource("/Images/background_Menu.png"));
        lbbackground.setIcon(backgroundIcon);
        add(lbbackground);
        
    }

    public JLabel setLabel(int x, int y, String ImageIcon){
        JLabel label = new JLabel();
        ImageIcon Icon = new ImageIcon(getClass().getResource(ImageIcon));
        label.setBounds(x, y, Icon.getIconWidth(), Icon.getIconHeight());
        label.setIcon(Icon);
        return label;
    }

    private MouseAdapter mMouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            if(e.getSource()==lbPlayGame){
                ImageIcon playIcon = new ImageIcon(getClass().getResource("/Images/Play2.png"));
                lbPlayGame.setIcon(playIcon);
            }

            if(e.getSource()==lbOption){
                ImageIcon optionIcon = new ImageIcon(getClass().getResource("/Images/Option2.png"));
                lbOption.setIcon(optionIcon);
            }

            if(e.getSource()==lbHigthScore){
				ImageIcon hightScoreIcon = new ImageIcon(getClass().getResource("/Images/HightScore2.png"));
				lbHigthScore.setIcon(hightScoreIcon);
            }
            
            if(e.getSource()==lbExit){
                ImageIcon exitIcon = new ImageIcon(getClass().getResource("/Images/Exit2.png"));
                lbExit.setIcon(exitIcon);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(e.getSource()==lbPlayGame){
                ImageIcon playIcon = new ImageIcon(getClass().getResource("/Images/Play.png"));
                lbPlayGame.setIcon(playIcon);
            }

            if(e.getSource()==lbOption){
                ImageIcon optionIcon = new ImageIcon(getClass().getResource("/Images/Option.png"));
                lbOption.setIcon(optionIcon);
            }
            
            if(e.getSource()==lbHigthScore){
				ImageIcon hightScoreIcon = new ImageIcon(getClass().getResource("/Images/HightScore.png"));
				lbHigthScore.setIcon(hightScoreIcon);
            }
            
            if(e.getSource()==lbExit){
                ImageIcon exitIcon = new ImageIcon(getClass().getResource("/Images/Exit.png"));
                lbExit.setIcon(exitIcon);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Sound.playSound(Sound.click);
            if(e.getSource()==lbPlayGame){
                mContainer.setShowPlay();
            }

            if(e.getSource()==lbOption){
                mContainer.setShowOption();
            }

            if(e.getSource()==lbExit){
//                GameSFX.stop();
                Sound.stopAllClip();
                Sound.playSound(Sound.exit);
                long end = (new Date()).getTime() + 4000;
                while ((new Date()).getTime() < end) {
                }
                mGui.dispose();
                PlayGame.IS_RUNNING=false;
            }
            if(e.getSource()==lbHigthScore){
                mContainer.setShowHightScore();
            }
        }
    };
}