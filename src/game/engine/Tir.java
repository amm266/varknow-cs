package game.engine;

import game.Menu.SecondMenu_Setting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tir implements Animatable {
    private double x;
    private double y;
    private double vx;
    private double vy;
    private BufferedImage bufferedImage;
    private BufferedImage bufferedImage1;
    //public static boolean TirExitance=true;
    public static int StrongOfTir=1;

    public Tir(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
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
        x += 2*vx;
        y += 2*vy;
    }

    public void paint(Graphics2D g2) {
//        g2.setColor(new Color(113, 4, 5));
//        g2.setStroke(new BasicStroke(3));
//
//        double l = 25.0 / Math.sqrt(vx * vx + vy * vy);
//
//        g2.drawLine((int) (x - l * vx), (int) (y - l * vy), (int)x, (int)y);

        try {

            // copied from http://www.java2s.com/Code/Java/Advanced-Graphics/RotatingaBufferedImage.htm

            if(SecondMenu_Setting.ColorOfShoot() == true){

                bufferedImage1 = ImageIO.read(new File("resources/shoot2.png"));

                AffineTransform tx = new AffineTransform();
                tx.rotate(Math.atan2(vy, vx), bufferedImage1.getWidth() / 2, bufferedImage1.getHeight() / 2);

                AffineTransformOp op = new AffineTransformOp(tx,
                        AffineTransformOp.TYPE_BILINEAR);
                bufferedImage1 = op.filter(bufferedImage1, null);

           }
            if(SecondMenu_Setting.ColorOfShoot() == false){

                bufferedImage = ImageIO.read(new File("resources/tir.png"));

                AffineTransform tx = new AffineTransform();
                tx.rotate(Math.atan2(vy, vx), bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);

                AffineTransformOp op = new AffineTransformOp(tx,
                        AffineTransformOp.TYPE_BILINEAR);
                bufferedImage = op.filter(bufferedImage, null);

            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if(SecondMenu_Setting.ColorOfShoot() == true) {
        g2.drawImage(bufferedImage1, (int)x, (int)y, null);

            //System.out.println("redshoot");
        }
        if(SecondMenu_Setting.ColorOfShoot() == false) {
            g2.drawImage(bufferedImage, (int)x, (int)y,30,30, null);

            //System.out.println("greenshoot");
        }


    }
}
