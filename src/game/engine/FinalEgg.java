package game.engine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class FinalEgg implements Animatable {

    public int life;
    public double HeightOfEgg = -300;
    public static int AmountOfLife;
    public boolean Exictance;
    public ArrayList<FinalEggShoot> finalEggShoots = new ArrayList<>();
    private long TimeOfFinalEggShoot = System.currentTimeMillis();


    public FinalEgg(int life) {
        this.life = life;

        AmountOfLife = 250 * life;
        Exictance = true;
    }

    @Override
    public void paint(Graphics2D g2) {
        if (HeightOfEgg < 75) {
            g2.drawImage(Game.SmartEggbufferedImage, 650, (int) HeightOfEgg
                    , 500, 500, null);
        } else {
            if (AmountOfLife <= 1)
                Exictance = false;

            if (Exictance) {
                g2.drawImage(Game.SmartEggbufferedImage, 700, (int) HeightOfEgg
                        , 500, 500, null);

            }
        }
        if(Exictance) {
            if (System.currentTimeMillis() - TimeOfFinalEggShoot >= 500) {
                shoot();
                TimeOfFinalEggShoot = System.currentTimeMillis();
            }
            if (!Objects.isNull(finalEggShoots)) {
                for (FinalEggShoot finalEggShoot : finalEggShoots) {

                        finalEggShoot.paint(g2);
                        finalEggShoot.move();


                    if(Math.abs(finalEggShoot.getX()- (Rocket.LastXRocket+5))<5 & Math.abs(finalEggShoot.getY()- (Rocket.LastYRocket+5))<5){
                        Rocket.HeartOfRocket--;
                        finalEggShoots.remove(finalEggShoot);
                    }
                    if(finalEggShoot.getX() < 0 | finalEggShoot.getX()>2000 | finalEggShoot.getY()<0 | finalEggShoot.getY()>1100)
                        finalEggShoots.remove(finalEggShoot);
                }
            }
        }
    }

    @Override
    public void move() {
        if (HeightOfEgg < 75)
            HeightOfEgg += 1;


    }

    public void shoot() {
        synchronized (finalEggShoots) {
            int r = 25;
            for (int i = 0; i < 8; i++) {
                double degree = ( i * 45) / 180.0 * Math.PI;
                //if(randomNumber==1)//0 or 1 or 2 or 3
                if(Random(8)<=2) {
                    finalEggShoots.add(new FinalEggShoot(950 + r * Math.cos(degree),
                            325 + -r * Math.sin(degree),
                            5 * Math.cos(degree),
                            -5 * Math.sin(degree)));
                }
            }
        }
    }


    public int Random (int n) {
        Random rand = new Random();

        int RandomNumber = rand.nextInt(n);
        return RandomNumber;
    }
}
