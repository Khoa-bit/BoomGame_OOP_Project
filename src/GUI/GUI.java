package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JFrame {
        public static final int WIDTHJF = 905;
        public static final int HEIGHTJF = 610;
    
public GUI() {
    setSize(WIDTHJF, HEIGHTJF);
    setLayout(new CardLayout());
    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
    }

}