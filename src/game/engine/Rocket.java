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
    public int LastXRocket;
    public int LastYRocket;
    private int hart =5;
    private int Score=0;
    private int Strong=3;
    private static BufferedImage image;
    private static BufferedImage image1;
    static {
        try {
            image = ImageIO.read(new File("resources/rocket.png"));
            image1 = ImageIO.read(new File("resources/rocket2.png"));
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }

    public void setHart ( int hart ) {
       this.hart = hart;
    }

    public int getHart () {
        return hart;
    }

    public void setScore ( int score ) {
        Score = score;
    }

    public void setStrong ( int strong ) {
        Strong = strong;
    }

    public int getScore () {
        return Score;
    }

    public int getStrong () {
        return Strong;
    }
    public void decreaseHart(int value){
        hart -=value;
    }
    public Rocket(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setLocation(int x,int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public void paint(Graphics2D g2) {
        BufferedImage bufferedImage = null;
        if(SecondMenu_Setting.TypeOfRocket() == SecondMenu_Setting.TypeOfRocket.RED) {
            bufferedImage = image;

        }
        if(SecondMenu_Setting.TypeOfRocket() == SecondMenu_Setting.TypeOfRocket.GREEN){
            bufferedImage = image1;

        }
        if( ! MainPanel.statePauseMenu ) {
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
