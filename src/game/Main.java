package game;

import game.engine.Sound;
import game.swing.MainFrame;
import game.swing.MainPanel;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main extends JFrame {
    public JTextField jTextField = new JTextField();
    public static void main(String[] args) {
        String ip;
        FileReader fileReader = null;
        try {
            fileReader = new FileReader ( "setupConnection.txt" );
        } catch (FileNotFoundException e) {
            e.printStackTrace ( );
        }
        Scanner scanner = new Scanner ( fileReader );
        ip = scanner.nextLine ();
        MainFrame mainFrame = new MainFrame();
        MainPanel mainPanel = new MainPanel(ip);
        mainFrame.add(mainPanel);
        PaintLoop paintLoop=new PaintLoop(mainPanel);
        paintLoop.start();
        Sound sound = new Sound();
        sound.start();
        mainFrame.setVisible(true);

    }
}
