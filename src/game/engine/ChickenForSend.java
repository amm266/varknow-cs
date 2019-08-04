package game.engine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChickenForSend {

	double x;
	double y;
	double vx;
	double vy;
	public ChickenForSend(
			 Chicken chicken){
		 x = chicken.getX ();
		 y = chicken.getY ();
	}
	public void paint ( Graphics2D g2 ) {
		g2.drawImage ( Game.bufferImageChicken , ( int ) x , ( int ) y , 60 , 60 , null );
	}
}
