package game.engine;

import game.Menu.PauseMenu;
import game.Menu.SecondMenu;
import game.swing.MainPanel;

import javax.imageio.ImageIO;
import java.awt.*;

import Box.GameFields;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;

public class Game {
	//forBox
	private Rocket mainRocket;
	public ArrayList<Rocket> rockets = new ArrayList<> ( );
	private ArrayList<Tir> tirs = new ArrayList<> ( );
	public static volatile ArrayList<Chicken> chickens = new ArrayList<> ( );
	private ArrayList<Egg> eggs = new ArrayList<> ( );
	private final ArrayList<Coin> coins = new ArrayList<> ( );
	private final ArrayList<Stronge> stronges = new ArrayList<> ( );
	//local
	private int LastXBomb;
	private int LastYBomb;
	private int j = 0;
	private int f = 0;
	private int a = 0;
	private int b = 0;
	private int HeightOfBomb = 3000000;
	private int WidthOfBomb;
	private boolean BombIsExploded = false;
	public BufferedImage bufferedImageHeart;
	public BufferedImage bufferedImagebackground;
	private BufferedImage bufferedImageBomb;
	private String NumberOfShoot = "" + MainPanel.NumberOfShoot;
	static PauseMenu pauseMenu = new PauseMenu ( );
	public FinalEgg finalEgg;
	public static BufferedImage SmartEggbufferedImage;
	public static BufferedImage CoinBufferImage;
	public static BufferedImage StrongBufferImage;
	public static BufferedImage bufferedImage_SecondMenu_background;
	public static BufferedImage bufferedImage_SecondMenu_AboutUs;
	public static BufferedImage bufferedImage_SeconfMenu_Setting_background;
	public static BufferedImage bufferedImage_SeconfMenu_Setting_Rocket1;
	public static BufferedImage bufferedImage_SeconfMenu_Setting_Rocket2;
	public static BufferedImage bufferedImage_SeconfMenu_Setting_COS1;
	public static BufferedImage bufferedImage_SeconfMenu_Setting_COS2;
	public static BufferedImage bufferedImage_Win;
	private String HeartOfRocketStr = "" + Rocket.getHart ( );
	private String ScoreStr = "" + Rocket.getScore ( );
	public static int NumberOfBomb;
	public static Point center = new Point ( 350 , 350 );

	public static enum STAGE {
		FIRST,
		SECOND,
		THIRD,
		FINAL
	}

	;

	public static enum LEVEL {
		ONE,
		TWO,
		THREE,
		FOUR,
		Win
	}

	;

	public static enum GROUP {
		ONE,
		TWO,
		THREE,
		FOUR,
		FINAL
	}

	;
	public static Game.STAGE stage = STAGE.FIRST;
	private static Game.LEVEL level = LEVEL.ONE;
	static Game.GROUP group = GROUP.ONE;

	public Game ( int width , int height ) {
		mainRocket = new Rocket ( width / 2 - 50 , height - 200 );
		rockets.add ( mainRocket );
		ImagesInit ( );
	}

	public void paint ( Graphics2D g2 ) {
		long t = System.currentTimeMillis ( );
		while ( System.currentTimeMillis ( ) - t < 1 ) ;
		System.out.println ( "in Game" );
		if ( level == LEVEL.Win ) {
			try {
				bufferedImage_Win = ImageIO.read ( new File ( "resources/win.png" ) );
			} catch (IOException ex) {
				ex.printStackTrace ( );
			}
			g2.drawImage ( bufferedImage_Win , 300 , 300 , 500 , 500 , null );
		}
		//moving the background
		if ( ! MainPanel.statePauseMenu ) {

			MoveBackground ( g2 );
		}
		if ( MainPanel.statePauseMenu ) {
			g2.drawImage ( bufferedImagebackground , 0 , 0 , 2000 , 1200 , null );
		}
		if ( System.currentTimeMillis ( ) - SecondMenu.TimeOfStartGame <= 2000 ) {
			g2.setFont ( new Font ( "Arial" , 250 , 80 ) );
			g2.drawString ( "Start , good luck!" , 2000 / 2 - 300 , 1100 / 2 );
		} else {
			GameInformation ( g2 );
			//BombShooting ( g2 );
		}
		for ( Tir tir : tirs ) {
			drawTir ( tir , g2 );
		}
		for ( Coin coin : coins ) {
			coin.paint ( g2 );
		}
		for ( Stronge stronge : stronges ) {
			stronge.paint ( g2 );
		}
		for ( Egg egg : eggs ) {
			egg.paint ( g2 );
		}
		synchronized (chickens) {
			for ( Chicken chicken : chickens ) {
				chicken.paint ( g2 );
			}
		}
		if ( stage == STAGE.FINAL ) {
			//final stage
			System.out.println ( "stageFinal" );
			finalEgg.paint ( g2 );
		}
		for ( Rocket rocket : rockets )
			rocket.paint ( g2 );
		if ( MainPanel.statePauseMenu ) {
			pauseMenu.paint ( g2 );
		}
		System.out.println ( stage );
		System.out.println ( chickens.size ( ) );
	}

	public Rocket getRocket () {
		return mainRocket;
	}

	private void MoveBackground ( Graphics2D g2 ) {
		int velocity = 25;
		if ( a == 0 ) {
			j++;
			g2.drawImage ( bufferedImagebackground , 0 , - 550 + velocity * j , 2000 , 1200 , null );
		}
		if ( ( - 550 + velocity * j ) >= 0 ) {
			b = 1;
			if ( ( - 1200 + velocity * f ) >= 1100 & ( - 550 + velocity * j ) >= 0 ) {
				b = 0;
				f = 0;
			}
			if ( ( - 550 + velocity * j ) >= 1100 & ( - 1200 + velocity * f ) >= 0 ) {
				a = 1;
				j = 0;
			}
		}
		if ( b == 1 ) {
			g2.drawImage ( bufferedImagebackground , 0 , - 1200 + velocity * f , 2000 , 1200 , null );
			f++;
		}
		if ( a == 1 ) {
			g2.drawImage ( bufferedImagebackground , 0 , - 1200 + velocity * j , 2000 , 1200 , null );
			j++;
		}
	}

	private void GameInformation ( Graphics2D g2 ) {
		g2.setFont ( new Font ( "Arial" , 250 , 20 ) );
		for ( int i = 0 ; i < 20 ; i++ ) {
			g2.setColor ( new Color ( 253 , 255 , 255 ) );
			g2.fillRect ( 15 * i + 150 , 20 , 8 , 17 );
		}
		for ( int i = 0 ; i < MainPanel.Temprature / 5 ; i++ ) {
			g2.setColor ( new Color ( 200 , 0 , 0 ) );
			g2.fillRect ( 15 * i + 150 , 20 , 8 , 17 );
		}
		//reload logic
		if ( MainPanel.Temprature > 0 & Math.abs ( System.currentTimeMillis ( ) - MainPanel.MainTime ) > 1000 & MainPanel.Temprature < 98 ) {
			MainPanel.Temprature = MainPanel.Temprature - 8;
			MainPanel.MainTime = System.currentTimeMillis ( );
		}
		if ( MainPanel.Temprature >= 98 && MainPanel.WaitForShoot ) {
			MainPanel.WaitForShoot = false;
			MainPanel.WaitForShootTime = System.currentTimeMillis ( );

		}
		if ( Math.abs ( System.currentTimeMillis ( ) - MainPanel.WaitForShootTime ) >= 4000 & MainPanel.Temprature >= 98 ) {
			MainPanel.WaitForShoot = true;
			MainPanel.ShootCounter = 0;
			MainPanel.Temprature = 0;
		}
		//end reload
		g2.drawImage ( bufferedImageHeart , 25 , 1000 , 15 , 15 , null );
		g2.setColor ( new Color ( 0 , 200 , 200 ) );
		g2.drawString ( HeartOfRocketStr , 50 , 1000 );
		g2.setColor ( new Color ( 236 , 231 , 57 ) );
		g2.drawString ( NumberOfShoot , 20 , 20 );
		g2.drawImage ( CoinBufferImage , 75 , 1000 , 15 , 15 , null );
		g2.setColor ( new Color ( 0 , 200 , 200 ) );
		g2.drawString ( ScoreStr , 50 , 1000 );
	}

	private void BombShooting ( Graphics2D g2 ) {
		if ( ! MainPanel.statePauseMenu ) {
			if ( MainPanel.BombState & mainRocket.getY ( ) > 550 ) {
           /* bombShoot=new BombShoot(rocket.getX(), rocket.getY());
            bombShoot.paint(g2);*/
				HeightOfBomb = mainRocket.getY ( );
				WidthOfBomb = mainRocket.getX ( );
				MainPanel.BombState = false;
				BombIsExploded = false;
			}
			if ( ! BombIsExploded ) {

				g2.drawImage ( bufferedImageBomb , WidthOfBomb , HeightOfBomb , 40 , 40 , null );
				HeightOfBomb = HeightOfBomb - 10;
			}
			if ( HeightOfBomb < 550 ) {
				//System.out.println(HeightOfBomb);
				BombIsExploded = true;
			}
			LastXBomb = WidthOfBomb;
			LastYBomb = HeightOfBomb;
		} else {
			g2.drawImage ( bufferedImageBomb , LastXBomb , LastYBomb , 40 , 40 , null );
		}
	}

	public void getGameFields ( GameFields box ) {
		rockets = box.getRockets ( );
		ArrayList<ChickenForSend> chickenForSends = box.getChickenForSends ( );
		chickens = new ArrayList<> ( );
		synchronized (chickens) {
			for ( ChickenForSend chickenForSend : chickenForSends ) {
				chickens.add ( new Chicken ( chickenForSend ) );
			}
		}
		tirs = box.getTirs ( );
		eggs = box.getEggs ( );
		int a = 1;
	}

	private void drawTir ( Tir tir , Graphics2D g2 ) {
		tir.paint ( g2 );
	}

	private void ImagesInit () {
		try {
			SmartEggbufferedImage = ImageIO.read ( new File ( "resources/Chicken_egg_broken_break-512.png" ) );
			bufferedImageHeart = ImageIO.read ( new File ( "resources/heart.png" ) );
			bufferedImagebackground = ImageIO.read ( new File ( "resources/gamebackground.png" ) );
			bufferedImageBomb = ImageIO.read ( new File ( "resources/bomb.png" ) );
			CoinBufferImage = ImageIO.read ( new File ( "resources/Coin.png" ) );
			StrongBufferImage = ImageIO.read ( new File ( "resources/point.png" ) );
			bufferedImage_SecondMenu_background = ImageIO.read ( new File ( "resources/background.jpg" ) );
			bufferedImage_SecondMenu_AboutUs = ImageIO.read ( new File ( "resources/hossein_khatiri.png" ) );
			bufferedImage_SeconfMenu_Setting_background = ImageIO.read ( new File ( "resources/background_Setting.png" ) );
			bufferedImage_SeconfMenu_Setting_Rocket1 = ImageIO.read ( new File ( "resources/rocket.png" ) );
			bufferedImage_SeconfMenu_Setting_Rocket2 = ImageIO.read ( new File ( "resources/rocket2.png" ) );
			bufferedImage_SeconfMenu_Setting_COS1 = ImageIO.read ( new File ( "resources/shoot2.png" ) );
			bufferedImage_SeconfMenu_Setting_COS2 = ImageIO.read ( new File ( "resources/tir.png" ) );
		} catch (IOException ex) {
			ex.printStackTrace ( );
		}
	}
}
