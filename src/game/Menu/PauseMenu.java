package game.Menu;

import game.Main;
import game.engine.Animatable;
import game.engine.Rocket;
import game.logger.Logger;
import game.swing.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PauseMenu extends JButton implements Animatable {

    public PauseMenu() {


    }

    @Override
    public void paint(Graphics2D g2) {
        super.paintComponent(g2);
        g2.setColor(new Color(242, 243, 43));
        g2.fillRect(400, 290, 200, 100);
        g2.fillRect(400, 410, 200, 100);
        g2.setColor(new Color(32, 243, 35));
        g2.setFont(new Font("Arial",250,50));
        g2.drawString("Resume",410,350);
        g2.drawString("EXIT",445,475);




    }


    @Override

    public void move() {

    }

    public static void ButtonClicked(int x,int y) {

        //this.x=x;
        //this.y=y;
        if ((MainPanel.x < 600 & MainPanel.x > 400) & (MainPanel.y < 390 & MainPanel.y > 290)) {
            MainPanel.statePauseMenu = false;
        }
        if ((MainPanel.x < 600 & MainPanel.x > 400) & (MainPanel.y < 510 & MainPanel.y > 410)) {
            System.exit(0);
        }
    }

}
