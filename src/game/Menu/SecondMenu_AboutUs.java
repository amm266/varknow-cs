package game.Menu;

import game.engine.Animatable;
import game.engine.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SecondMenu_AboutUs extends JButton implements Animatable {

    private JButton button;
    private boolean State = true;
    private int x;
    private int y;

    @Override
    public void paint(Graphics2D g2) {
        g2.setColor(new Color(0, 200, 0));
        g2.fillRect(0, 0, 1000, 800);


        g2.setColor(new Color(0, 0, 0));
        g2.setFont(new Font("Arial" , 250 , 40));

        g2.setColor(new Color(0, 0, 0, 158));
        g2.setFont(new Font("Arial" , 250 , 20));
        g2.drawString("Ideologist, Positive Thinking,\n" , 650 , 450 );
        g2.drawString("Energetic, Artist, Extrovert\n" , 650 , 490 );
        g2.setColor(new Color(200, 0, 0, 158));
        g2.fillRect(900,700,80,50);
        g2.setColor(new Color(0, 0, 0, 158));
        g2.drawString("Return",915,730);

    }



    public boolean State (int x , int y){
        if ( (x < 980 & x > 900) & ( y <750 & y>700)){
            State = false;
        }
        if (State == false) {
            State = true;
            return false;
        }
        else {
           return true ;
        }
    }

    @Override
    public void move() {

    }
}
