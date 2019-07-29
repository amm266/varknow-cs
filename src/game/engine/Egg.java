package game.engine;

import game.Menu.SecondMenu_Setting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Egg implements Animatable {
    private double x;
    private double y;
    private static double vy = 1;
    private static BufferedImage image;
    static {
        try {
            image = ImageIO.read ( new File ( "resources/Chicken_egg_broken_break-512.png" ) );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }

    //public static boolean TirExitance=true;


    public Egg(double x, double y) {
        this.x = x;
        this.y = y;
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
            g2.drawImage(image, (int)x, (int)y,30,30, null);
    }
}
