package game.engine;

import game.Menu.SecondMenu_Setting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Stronge implements Animatable {
    private double x;
    private double y;
    private static double vy;
    private static BufferedImage StrongBufferImage;

    static {
        try {
            StrongBufferImage = ImageIO.read ( new File ( "resources/point.png" ) );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
        vy = 1;

    }
    public Stronge(double x, double y) {
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
        g2.drawImage(StrongBufferImage, (int)x, (int)y,30,30, null);
    }
}
