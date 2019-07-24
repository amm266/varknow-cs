package game.swing;

import java.awt.event.KeyEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class KeyListener implements java.awt.event.KeyListener {

    public KeyListener() {
    }
        @Override
        public void keyTyped (KeyEvent e){

        }

        @Override
        public void keyPressed (KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_P) {
                State();
            }

        }

        @Override
        public void keyReleased (KeyEvent e){

        }


    public boolean State (){
        return false;
    }

}
