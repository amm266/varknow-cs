package game.engine;

import game.Menu.SecondMenu_Setting;
import game.swing.MainPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Rocket implements Animatable {
    private int x;
    private int y;
    public static int LastXRocket;
    public static int LastYRocket;
    private static int hart =5;
    private static int Score=0;
    private static int Strong=3;


    public static void setHart ( int hart ) {
        Rocket.hart = hart;
    }

    public static int getHart () {
        return hart;
    }

    public static void setScore ( int score ) {
        Score = score;
    }

    public static void setStrong ( int strong ) {
        Strong = strong;
    }

    public static int getScore () {
        return Score;
    }

    public static int getStrong () {
        return Strong;
    }
    public static void decreaseHart(int value){
        hart -=value;
    }
    private BufferedImage bufferedImage;


    public Rocket(int x, int y) {
        this.x = x;
        this.y = y;




    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;

    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void paint(Graphics2D g2) {
//        g2.setColor(new Color(135, 109, 79));
//        g2.fillRect(x - 50, y - 25, 100, 50);
        try {
            if(SecondMenu_Setting.TypeOfRocket() == SecondMenu_Setting.TypeOfRocket.RED) {
                bufferedImage = ImageIO.read(new File("resources/rocket.png"));

            }
            if(SecondMenu_Setting.TypeOfRocket() == SecondMenu_Setting.TypeOfRocket.GREEN){
                bufferedImage = ImageIO.read(new File("resources/rocket2.png"));

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(MainPanel.statePauseMenu == false) {
            if (SecondMenu_Setting.TypeOfRocket() == SecondMenu_Setting.TypeOfRocket.RED) {
                g2.drawImage(bufferedImage, x - bufferedImage.getWidth() / 2, y - bufferedImage.getHeight() / 2, null);
            }
            if (SecondMenu_Setting.TypeOfRocket() == SecondMenu_Setting.TypeOfRocket.GREEN) {
                g2.drawImage(bufferedImage, x - 40, y - 40, 80, 80, null);
            }
            LastXRocket = x;
            LastYRocket = y;
        }
        else {

            g2.drawImage(bufferedImage, LastXRocket - 40, LastYRocket - 40, 80, 80, null);
        }
    }

    @Override
    public void move() {

    }
}
