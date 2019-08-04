package xo;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

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
