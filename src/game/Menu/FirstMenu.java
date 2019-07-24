package game.Menu;

import game.Main;
import game.engine.Animatable;
import xo.MainFrame;
import xo.MainPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FirstMenu extends JPanel implements Animatable {
    private BufferedImage bufferedImage_logo;
    private BufferedImage bufferedImage_firstmenubackground;
    private int x;
    private int y;
    private boolean ENTER = false;
    private boolean ADD = false;
    private boolean DELETE = false;
    public static String NameOfPlayer;
    private JTextField jTextFieldInput;
    private JTextField jTextFieldOutput;

    public static enum CounterOfPlayer{

        first,
        empty,
        second,
        third
    };
    public static CounterOfPlayer counterOfPlayer;


    public void FirstMenu() {
    }

    public void buttonClicked(int x ,int y,JTextField jTextFieldInput ,JTextField jTextFieldOutput) {

        this.x=x;
        this.y=y;
        this.jTextFieldInput=jTextFieldInput;
        this.jTextFieldOutput=jTextFieldOutput;
        if ((x > 170 & x < 270) & (y < 640 & y > 600)) {
            // ENTER
            ENTER = true;

        }

        if ((x > 430 & x < 530) & (y < 640 & y > 600)) {
            //DELETE
            DELETE = true;

        }

        if ((x > 700 & x < 800) & (y < 640 & y > 600)) {
            // ADD
            ADD = true;
            NameOfPlayer=jTextFieldInput.getText();
            if(jTextFieldInput != null) {
                counterOfPlayer =CounterOfPlayer.third;
                jTextFieldOutput.setText(NameOfPlayer);
            }
            }
    }


    @Override
    public void paint(Graphics2D g2) {
        super.paintComponent(g2);

        try {
            bufferedImage_logo = ImageIO.read(new File("resources/logo.png"));
            bufferedImage_firstmenubackground = ImageIO.read(new File("resources/firstmenubackground.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g2.setColor(new Color(1, 100, 100));
        //g2.fillRect(0, 0, 1000, 800);
        g2.drawImage(bufferedImage_firstmenubackground,0,0,1000,800,null);
        g2.drawImage(bufferedImage_logo,250,10,500,300,null);

        g2.setColor(new Color(0, 200, 100));
        g2.fillRect(170,600,100,40);
        g2.fillRect(430,600,100,40);
        g2.fillRect(700,600,100,40);
        g2.setFont(new Font("Arial" , 250 , 30));
        g2.setColor(new Color(0,0,0));
        g2.drawString("Enter",180,630);
        g2.drawString("Delete",440,630);
        g2.drawString("Add",710,630);
        g2.setColor(new Color(200,200,200));
        g2.fillRect(250,390,480,105);

    }

    @Override

    public void move() {

    }

    public boolean MenuState(){
        if(ENTER) {
            return true;
        }
        else {
            return false;
        }
    }

    }
