package game;

import game.engine.Sound;
import game.swing.MainFrame;
import game.swing.MainPanel;

import javax.swing.*;

public class Main extends JFrame {
    public JTextField jTextField = new JTextField();
    public static void main(String[] args) {

        MainFrame mainFrame = new MainFrame();
        MainPanel mainPanel = new MainPanel("localhost");
        mainFrame.add(mainPanel);
        PaintLoop paintLoop=new PaintLoop(mainPanel);
        paintLoop.start();
        Sound sound = new Sound();
        sound.start();
        mainFrame.setVisible(true);

    }
}
