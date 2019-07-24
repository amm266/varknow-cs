package game.engine;

import game.Menu.SecondMenu_Setting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FinalEggShoot implements Animatable {
    private double x;
    private double y;
    private double vx;
    private double vy;
    private BufferedImage bufferedImage;
    //public static boolean TirExitance=true;

    public FinalEggShoot (double x, double y, double vx, double vy) {
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
        try {
            bufferedImage = ImageIO.read(new File("resources/EggShoot.png"));
            AffineTransform tx = new AffineTransform();
            tx.rotate(Math.atan2(vy, vx), bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);

            AffineTransformOp op = new AffineTransformOp(tx,
                    AffineTransformOp.TYPE_BILINEAR);
            bufferedImage = op.filter(bufferedImage, null);

        }catch (IOException ex) {
            ex.printStackTrace();
        }


            g2.drawImage(bufferedImage, (int)x, (int)y, null);
    }
}
