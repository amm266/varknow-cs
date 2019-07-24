package game.engine;

import game.Main;
import game.Menu.SecondMenu;
import game.Menu.SecondMenu_Setting;
import game.swing.MainPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Rocket implements Animatable {
    private int x;
    private int y;
    public static int LastXRocket;
    public static int LastYRocket;
    public static int HeartOfRocket=5;
    public static int Score=0;
    public static int Strong=3;




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
