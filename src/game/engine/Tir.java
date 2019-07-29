package game.engine;

import game.Menu.SecondMenu_Setting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

public class Tir implements Animatable {
	private double x;
	private double y;
	private double vx;
	private double vy;
	private static BufferedImage bufferedImage;
	private static BufferedImage bufferedImage1;
	public static int StrongOfTir = 1;

	static {
		try {
			bufferedImage1 = ImageIO.read ( new File ( "resources/shoot2.png" ) );
			bufferedImage = ImageIO.read ( new File ( "resources/tir.png" ) );
		} catch (Exception e) {
			e.printStackTrace ( );
		}
	}

	public Tir ( double x , double y , double vx , double vy ) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
	}

	public double getX () {
		return x;
	}

	public double getY () {
		return y;
	}

	public void move () {
		x += 2 * vx;
		y += 2 * vy;
	}

	public void paint ( Graphics2D g2 ) {
		// copied from http://www.java2s.com/Code/Java/Advanced-Graphics/RotatingaBufferedImage.htm
		if ( SecondMenu_Setting.ColorOfShoot ( ) ) {
			inPaint ( g2 , bufferedImage1 );
		} else {
			inPaint ( g2 , bufferedImage );
		}
	}

	private void inPaint ( Graphics2D graphics2D , BufferedImage bufferedImage ) {
		AffineTransform tx = new AffineTransform ( );
		tx.rotate ( Math.atan2 ( vy , vx ) , bufferedImage.getWidth ( ) / 2 , bufferedImage.getHeight ( ) / 2 );
		AffineTransformOp op = new AffineTransformOp ( tx ,
				AffineTransformOp.TYPE_BILINEAR );
		bufferedImage = op.filter ( bufferedImage , null );
		graphics2D.drawImage ( bufferedImage , ( int ) x , ( int ) y , 30 , 30 , null );
	}
}
