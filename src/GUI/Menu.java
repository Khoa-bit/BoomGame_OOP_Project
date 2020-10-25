package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Menu extends JPanel{
    private int padding = 15;
    private GUI mGui;
    private MyContainer mContainer;
    private JLabel lbbackground;
    private JLabel lbPlayGame;
    private JLabel lbExit;

    public Menu(MyContainer mContainer){
        this.mContainer = mContainer;
        this.mGui = mContainer.getGui();
        setBackground(Color.BLACK);
        setLayout(null);
        initComps(mGui);
    }

    public void initComps(GUI mGui){
        lbPlayGame = setLabel((mGui.getWidth()-150)/2-30, (mGui.getHeight()-30)/2-150, "/Images/Play.png");
        lbExit = setLabel(lbPlayGame.getX(),lbPlayGame.getY() + lbPlayGame.getHeight()+padding, "/Images/Exit.png");
        lbPlayGame.addMouseListener(mMouseAdapter);
        lbExit.addMouseListener(mMouseAdapter);
        add(lbPlayGame);
        add(lbExit);
    }

    public void initbackground(){
        lbbackground = new JLabel();
        lbbackground.setBounds(0, -10, mGui.getWidth(), mGui.getHeight());
        lbbackground.setBackground(Color.BLACK);
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
            if(e.getSource()==lbExit){
                ImageIcon exitIcon = new ImageIcon(getClass().getResource("/Images/Exit.png"));
                lbExit.setIcon(exitIcon);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getSource()==lbExit){
                mGui.dispose();
                PlayGame.IS_RUNNING=false;
            }
            if(e.getSource()==lbPlayGame){
                mContainer.setShowPlay();
            }
        }
    };
}