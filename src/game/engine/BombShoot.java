package game.engine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class BombShoot implements Animatable {
    private double HeightOfBomb;
    private BufferedImage bufferedImageBomb;
    private double x;
    private double y;
    public BombShoot(double x , double y) {
        this.x=x;
        this.y=y;
        System.out.println("bomb");
        try {
            bufferedImageBomb = ImageIO.read(new File("resources/bomb.png"));
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void paint(Graphics2D g2) {
        for(HeightOfBomb = y; HeightOfBomb <400 ; HeightOfBomb--);{
            g2.drawImage(bufferedImageBomb,(int)x,(int)HeightOfBomb,15,15,null);
            System.out.println("paint bomb");
        }
    }

    public void move(){

    }


 }
