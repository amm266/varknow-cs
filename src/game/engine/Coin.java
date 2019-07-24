package game.engine;

import game.Menu.SecondMenu_Setting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Coin implements Animatable {
    private double x;
    private double y;
    private double vy;


    //public static boolean TirExitance=true;


    public Coin(double x, double y, double vy) {
        this.x = x;
        this.y = y;
        this.vy = vy;
        //TirExitance=true;



    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void move() {

        y += 2*vy;

    }

    public void paint(Graphics2D g2) {

        g2.drawImage(Game.CoinBufferImage, (int)x, (int)y,30,30, null);
    }
}
