package xo;

import javax.swing.*;

public class Main extends JFrame{
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        MainPanel mainPanel = new MainPanel();
        //mainFrame.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        //setlayout(null), set bounds(x,y)
        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);


    }
}
