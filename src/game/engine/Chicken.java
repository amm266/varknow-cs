package game.engine;

import game.swing.MainFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Chicken implements Animatable {
	private static transient BufferedImage bufferImageChicken;
	{
		try {
			bufferImageChicken = ImageIO.read ( new File ( "resources/rchicken.png" ) );
		} catch (IOException e) {
			e.printStackTrace ( );
		}
	}
	private double x;
	private double y;
	private double vx;
	private double vy;
	public int MiddelOfChickenX;
	//public static boolean ChickenExictance = true;
	private double angel3;
	private double angel2;
	private double angel1;
	public Chicken (ChickenForSend chickenForSend){
		x = chickenForSend.x;
		y = chickenForSend.y;
		vx = chickenForSend.vx;
		vy = chickenForSend.vy;
		MiddelOfChickenX = chickenForSend.MiddelOfChickenX;
		angel3 = chickenForSend.angel3;
		angel2 = chickenForSend.angel2;
		angel1 = chickenForSend.angel1;

	}
	public double getAngel1 () {
		return angel1;
	}

	public double getAngel2 () {
		return angel2;
	}

	public double getAngel3 () {
		return angel3;
	}

	public double getX () {
		return x;
	}

	public double getY () {
		return y;
	}

	public void SetX ( double x ) {
		this.x = x;
	}

	public void SetY ( double y ) {
		this.y = y;
	}

	public void SetMiddelOfChickenX ( int a ) {
		this.MiddelOfChickenX = a;
	}

	public Chicken ( double x , double y , double vx , double vy ) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		//ChickenExictance=true;
	}
	@Override
	public void move () {
		x += 2 * vx;
		y += 2 * vy;
		Game.center.x += 2 * vx;
		MiddelOfChickenX = MiddelOfChickenX + 2 * ( int ) vx;
		if ( ( MiddelOfChickenX > 1100 ) | ( MiddelOfChickenX < - 25 ) ) {
			vx = vx * ( - 1 );
		}
		if ( Game.group == Game.GROUP.ONE ) {
			if ( Math.abs ( ( int ) x - ( int ) Game.center.x ) <= 100 & Math.abs ( ( int ) x - ( int ) Game.center.x ) >= 0 ) {

				angel1 += Math.toRadians ( 6 );
				x = Math.cos ( angel1 ) * 100;
				y = Math.sin ( angel1 ) * 100;
			}
			if ( Math.abs ( ( int ) x - ( int ) Game.center.x ) <= 200 & Math.abs ( ( int ) x - ( int ) Game.center.x ) >= 100 ) {
				angel2 += Math.toRadians ( 10 );
				x = Math.cos ( angel2 ) * 200;
				y = Math.sin ( angel2 ) * 200;
			}
			if ( Math.abs ( ( int ) x - ( int ) Game.center.x ) <= 300 & Math.abs ( ( int ) x - ( int ) Game.center.x ) >= 200 ) {
				angel3 += Math.toRadians ( 20 );
				x = Math.cos ( angel3 ) * 200;
				y = Math.sin ( angel3 ) * 200;
			}
		}
	}
	@Override
	public void paint ( Graphics2D g2 ) {
		g2.drawImage ( bufferImageChicken , ( int ) x , ( int ) y , 60 , 60 , null );
	}
}