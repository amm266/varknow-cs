package game;

import game.Menu.PauseMenu;
import game.swing.MainPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PaintLoop extends Thread implements KeyListener {
    private MainPanel mainPanel;
    public boolean running = true;
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
            running =mainPanel.state();
               if ( running & ! MainPanel.statePauseMenu ) {
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
    @Override
    public void keyTyped ( KeyEvent e ) {
        System.out.println ( "save" );
    }

    @Override
    public void keyPressed ( KeyEvent e ) {
        System.out.println ( "save" );
    }

    @Override
    public void keyReleased ( KeyEvent e ) {
        System.out.println ( "save" );
    }
}
