package game;

import game.Menu.PauseMenu;
import game.engine.Sound;
import game.swing.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PushbackInputStream;
import java.sql.Time;

public class PaintLoop extends Thread {
    private MainPanel mainPanel;
    public boolean runnng = true;
    private long FirstTime ;
    private long LastTime;
    PauseMenu pauseMenu = new PauseMenu();

    public PaintLoop(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
    @Override
    public void run() {
        while (true) {
            //FirstTime= System.currentTimeMillis();
            runnng=mainPanel.state();
               if (runnng & ! MainPanel.statePauseMenu ) {
                 //  mainPanel.moveGame();
               }
                mainPanel.repaint();
            //LastTime=System.currentTimeMillis();
            try {
                long X = (LastTime - FirstTime) + 10;
                Thread.sleep(X);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
