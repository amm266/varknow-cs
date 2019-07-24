package game.engine;

import game.Main;
import game.Menu.PauseMenu;
import game.Menu.SecondMenu;
import game.Menu.SecondMenu_Setting;
import game.swing.MainFrame;
import game.swing.MainPanel;

import javax.imageio.ImageIO;
import javax.lang.model.type.NullType;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.io.IOException;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.jar.Manifest;
import java.util.Random;


public class Game implements Animatable {

	public int LastXBomb;
	public int LastYBomb;
	private int width;
	private int height;
	private int j = 0;
	private int f = 0;
	private int a = 0;
	private int b = 0;
	private int velocity;
	private Sound sound;
	private int HeightOfBomb = 3000000;
	private int WidthOfBomb;
	private boolean BombIsExploded = false;
	public BufferedImage bufferedImageHeart;
	public BufferedImage bufferedImagebackground;
	private BufferedImage bufferedImageBomb;
	public static long TimeOfBackgrounddMove = 0;
	private String NumberOfShoot = "" + MainPanel.NumberOfShoot;
	public Rocket rocket;
	public BombShoot bombShoot;
	private BufferedImage bufferedImage;
	private final ArrayList<Tir> tirs = new ArrayList<> ( );
	public static final ArrayList<Chicken> chickens = new ArrayList<> ( );
	private final ArrayList<Egg> eggs = new ArrayList<> ( );
	private final ArrayList<Coin> coins = new ArrayList<> ( );
	private final ArrayList<Stronge> stronges = new ArrayList<> ( );
	static PauseMenu pauseMenu = new PauseMenu ( );
	public static int NumberOfChickensG1 = 40;
	public static int NumberOfChickensG2 = 50;
	public static int NumberOfChickensG3 = 60;
	public static int NumberOfChickensG4 = 70;
	public int G1Transform = 0;
	public FinalEgg finalEgg;
	public Egg egg;
	public int NumberOfEgg;
	private long EggTime = System.currentTimeMillis ( );
	public static BufferedImage SmartEggbufferedImage;
	public static BufferedImage CoinBufferImage;
	public static BufferedImage StrongBufferImage;
	public static BufferedImage bufferedImage_SecondMenu_background;
	public static BufferedImage bufferedImage_SecondMenu_AboutUs;
	public static BufferedImage bufferedImage_SeconfMenu_Setting_background;
	public static BufferedImage bufferedImage_SeconfMenu_Setting_Rocket1;
	public static BufferedImage bufferedImage_SeconfMenu_Setting_Rocket2;
	public static BufferedImage bufferedImage_SeconfMenu_Setting_COS1;
	public static BufferedImage bbufferedImage_SeconfMenu_Setting_COS2;
	public static BufferedImage bufferedImage_Win;

	private String HeartOfRocketStr = "" + Rocket.HeartOfRocket;
	private String ScoreStr = "" + Rocket.Score;
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
	public static Game.LEVEL level = LEVEL.ONE;
	public static Game.GROUP group = GROUP.ONE;


	public Game ( int width , int height ) {
		this.width = width;
		this.height = height;
		rocket = new Rocket ( width / 2 - 50 , height - 200 );
		//int Xrocket = rocket.getX();
		ImagesInit ( );
	}

	@Override
	public void paint ( Graphics2D g2 ) {
		if ( level == LEVEL.Win ) {
			try {
				bufferedImage_Win = ImageIO.read ( new File ( "resources/win.png" ) );

			} catch (IOException ex) {
				ex.printStackTrace ( );
			}
			g2.drawImage ( bufferedImage_Win , 300 , 300 , 500 , 500 , null );

		}

		//moving the background
		if ( MainPanel.statePauseMenu == false ) {

			MoveBackground ( g2 );
		}

		if ( MainPanel.statePauseMenu == true ) {
			g2.drawImage ( bufferedImagebackground , 0 , 0 , 2000 , 1200 , null );
		}


		if ( System.currentTimeMillis ( ) - SecondMenu.TimeOfStartGame <= 2000 ) {
			g2.setFont ( new Font ( "Arial" , 250 , 80 ) );
			g2.drawString ( "Start , good luck!" , 2000 / 2 - 300 , 1100 / 2 );
		} else {
			GameInformation ( g2 );
			BombShooting ( g2 );
		}

		for ( int i = 0;i<tirs.size ();i++ ) {
			Tir tir = tirs.get ( i );
			if ( tir.getX ( ) > 2000 | tir.getY ( ) > 1100 | tir.getX ( ) < 0 | tir.getY ( ) < 0 ){
				tirs.remove ( tir );
				i--;
			}
			if ( ! Objects.isNull ( eggs ) ) {
				for ( Egg egg : eggs ) {
					if ( CheckQuancidence ( egg ) ) {
						eggs.remove ( egg );
						Rocket.HeartOfRocket--;
					}
					if ( egg.getY ( ) > 1100 ) {
						eggs.remove ( egg );
					}
				}
			}


			if ( CheckQuancidence ( tir ) & stage == STAGE.FINAL ) {
				finalEgg.AmountOfLife--;
				tirs.remove ( tir );
			}
			for ( int j=0;j<chickens.size ();j++ ) {
				Chicken chicken = chickens.get ( j );
				if ( CheckQuancidence ( chicken , tir ) ) {
					if ( ! Objects.isNull ( chickens ) ) {
						if ( Random ( 100 ) <= 6 ) {

							Strong ( chicken.getX ( ) , chicken.getY ( ) );
						}
						if ( Random ( 100 ) <= 6 ) {
							Coin ( chicken.getX ( ) , chicken.getY ( ) );
						}


					}
					chickens.remove ( chicken );
					j--;
					tirs.remove ( tir );
				}
			}
		}

		for ( Tir tir : tirs ) {
			Drawtir ( tir , g2 );
		}

		if ( ! Objects.isNull ( coins ) ) {
			for ( int i =0;i<coins.size ();i++ ) {
				Coin coin = coins.get ( i );
				coin.paint ( g2 );
				if ( coin.getY ( ) > 1100 ) {
					coins.remove ( coin );
					i--;
				}
				if ( CheckQuancidence ( coin ) ) {
					Rocket.Score += 2;
					coins.remove ( coin );
					i--;
				}

			}
			//System.out.println(Rocket.Score);
		}

		if ( ! Objects.isNull ( stronges ) ) {
			for ( Stronge stronge : stronges ) {
				stronge.paint ( g2 );
				if ( stronge.getY ( ) > 1100 ) {
					stronges.remove ( stronge );
				}
				if ( CheckQuancidence ( stronge ) ) {
					Rocket.Strong += 1;
					Tir.StrongOfTir++;
					stronges.remove ( stronge );
				}
			}
		}
		if ( ! Objects.isNull ( eggs ) ) {
			for ( Egg egg : eggs ) {
				egg.paint ( g2 );
			}
		}


		if ( ( System.currentTimeMillis ( ) - EggTime ) >= 1000 & ! Objects.isNull ( chickens ) ) {
			for ( Chicken chicken : chickens ) {
				if ( Random ( 50 ) <= 5 ) {
					Egg ( 0 , chicken.getX ( ) , chicken.getY ( ) );
				}
				EggTime = System.currentTimeMillis ( );

			}
		}


		for ( Chicken chicken : chickens ) {
			chicken.paint ( g2 );

			if ( stage == STAGE.FIRST & NumberOfChickensG1 <= 30 & G1Transform < 30 ) {
				chicken.SetX ( 60 * ( G1Transform % 6 ) + 80 );
				chicken.SetY ( 75 * ( G1Transform / 6 ) + 100 );
				G1Transform++;
				chicken.SetMiddelOfChickenX ( 0 );
			}
		}


		if ( stage == STAGE.FIRST ) {
			if ( chickens.size ( ) > 0 ) {
				NumberOfChickensG1 = chickens.size ( );
				NumberOfEgg = NumberOfChickensG1;
			}
			if ( chickens.size ( ) <= 1 ) {
				stage = STAGE.SECOND;
				chickens.clear ( );
				//NumberOfChickensG1=40;
			}
		}
		if ( stage == STAGE.SECOND ) {
			//second stage
			//System.out.println("stage2");
			if ( chickens.size ( ) > 0 ) {
				NumberOfChickensG2 = chickens.size ( );
				NumberOfEgg = NumberOfChickensG2;
			}
			if ( NumberOfChickensG2 <= 1 ) {
				stage = STAGE.THIRD;
				chickens.clear ( );
				//NumberOfChickensG2=40;
			}
		}
		if ( stage == STAGE.THIRD ) {
			//third stage
			//System.out.println("stage3");
			if ( chickens.size ( ) > 0 ) {
				NumberOfChickensG3 = chickens.size ( );
				NumberOfEgg = NumberOfChickensG3;
			}
			if ( chickens.size ( ) <= 1 ) {
				stage = STAGE.FINAL;
				chickens.clear ( );
				//NumberOfChickensG3=40;
			}
		}

		if ( stage == STAGE.FINAL ) {
			//final stage
			System.out.println ( "stagefinal" );

			finalEgg.paint ( g2 );
			if ( finalEgg.AmountOfLife <= 1 ) {
				// stage=STAGE.FIRST;
				if ( level == LEVEL.FOUR ) {
					level = LEVEL.Win;
				}
				if ( level == LEVEL.THREE ) {
					level = LEVEL.FOUR;
				}
				if ( level == LEVEL.TWO ) {
					level = LEVEL.THREE;
				}
				if ( level == LEVEL.ONE ) {
					level = LEVEL.TWO;
				}
				NumberOfBomb++;
				finalEgg.Exictance = false;
			}
		}
		rocket.paint ( g2 );
		if ( MainPanel.statePauseMenu == true ) {
			pauseMenu.paint ( ( Graphics2D ) g2 );
		}
		//////////////////

		System.out.println ( stage );
		System.out.println ( chickens.size ( ) );
	}

	@Override
	public void move () {

		rocket.move ( );
		synchronized (tirs) {
			for ( Tir tir : tirs ) {
				tir.move ( );
			}
		}
		synchronized (chickens) {
			for ( Chicken chicken : chickens ) {
				chicken.move ( );
			}
		}
		if ( ! Objects.isNull ( finalEgg ) )
			finalEgg.move ( );

		if ( ! Objects.isNull ( eggs ) ) {
			synchronized (eggs) {
				for ( Egg egg : eggs ) {
					egg.move ( );
				}
			}
		}

		if ( ! Objects.isNull ( coins ) ) {

			synchronized (coins) {
				for ( Coin coin : coins ) {
					coin.move ( );
				}
			}
		}
		if ( ! Objects.isNull ( stronges ) ) {

			synchronized (stronges) {
				for ( Stronge stronge : stronges ) {
					stronge.move ( );
				}
			}
		}

	}

	public Rocket getRocket () {
		return rocket;
	}


	public void fire () {
		if ( SecondMenu_Setting.typeOfShoot == false ) {
			// System.out.println("multi");
		}
		if ( SecondMenu_Setting.typeOfShoot == true ) {
			synchronized (tirs) {
				int r = 25;
				int v = 5;
				if ( Tir.StrongOfTir <= 1 ) {
					double degree = ( 90 ) / 180.0 * Math.PI;
					tirs.add ( new Tir ( rocket.getX ( ) ,
							rocket.getY ( ) + - r * Math.sin ( degree ) ,
							v * Math.cos ( degree ) ,
							- v * Math.sin ( degree ) ) );
				} else {
					for ( int i = 0 ; i < 5 ; i++ ) {
						double degree = ( 70 + i * 10 ) / 180.0 * Math.PI;

						tirs.add ( new Tir ( rocket.getX ( ) + r * Math.cos ( degree ) ,
								rocket.getY ( ) + - r * Math.sin ( degree ) ,
								5 * Math.cos ( degree ) ,
								- 5 * Math.sin ( degree ) ) );
					}
					// System.out.println("single");

				}
			}
		}
	}

	public void chick () {
		System.out.println ("stage "+stage+" level "+level );
		if ( stage == STAGE.FIRST & chickens.size ( ) == 0 ) {
			if ( level == LEVEL.ONE ) {
				Group1 ( );
				//  group=GROUP.ONE;
			}
			if ( level == LEVEL.TWO ) {
				Group1 ( );
				// group=GROUP.ONE;
			}
			if ( level == LEVEL.THREE ) {
				Group2 ( );
				// group=GROUP.TWO;
			}
			if ( level == LEVEL.FOUR ) {
				Group3 ( );
				//group=GROUP.THREE;
			}
		}
		if ( stage == STAGE.SECOND & chickens.size ( ) == 0 ) {
			//second stage
			if ( level == LEVEL.ONE ) {
				Group1 ( );
				//group=GROUP.ONE;

			}
			if ( level == LEVEL.TWO ) {
				Group2 ( );
				//group=GROUP.TWO;

			}
			if ( level == LEVEL.THREE ) {
				Group3 ( );
				// group=GROUP.THREE;

			}
			if ( level == LEVEL.FOUR ) {
				Group4 ( );
				//group=GROUP.THREE;

			}
		}
		if ( stage == STAGE.THIRD & chickens.size ( ) == 0 ) {
			//third stage
			if ( level == LEVEL.ONE ) {
				Group2 ( );
				// group=GROUP.TWO;
			}
			if ( level == LEVEL.TWO ) {
				Group2 ( );
				// group=GROUP.TWO;

			}
			if ( level == LEVEL.THREE ) {
				Group3 ( );
				//group=GROUP.THREE;

			}
			if ( level == LEVEL.FOUR ) {
				Group4 ( );
				// group=GROUP.FOUR;

			}
		}
		if ( stage == STAGE.FINAL & chickens.size ( ) == 0 & Objects.isNull ( finalEgg ) ) {
			//final stage
			System.out.println ( "final" );
			GroupFinal ( );
			group = GROUP.FINAL;
		}
	}

	public void Strong ( double StrongX , double StrongY ) {
		synchronized (stronges) {
			stronges.add ( new Stronge ( StrongX ,
					StrongY ,
					1 ) );
		}
	}

	public void Coin ( double CoinX , double CoinY ) {
		synchronized (coins) {
			coins.add ( new Coin ( CoinX ,
					CoinY ,
					1 ) );
		}
	}

	public void Egg ( int NumberOfEggs , double Eggx , double Eggy ) {
		synchronized (eggs) {

			int r = 25;
			for ( int i = 0 ; i < NumberOfEggs ; i++ ) {
				eggs.add ( new Egg ( Eggx ,
						Eggy ,
						1 ) );
			}
		}
	}

	public void Group1 () {

		synchronized (chickens) {
			//System.out.println(NumberOfChickensG1);
			int r = 25;
			for ( int i = 0 ; i < 40 ; i++ ) {
				double degree = ( 90 ) / 180.0 * Math.PI;
				chickens.add ( new Chicken ( 60 * ( i % 8 ) + 80 ,
						75 * ( i / 8 ) + 100 ,
						1 * Math.sin ( degree ) ,
						1 * Math.cos ( degree ) ) );
			}
		}
	}

	public void Group2 () {
		double angel1 = 0;
		int radius1 = 300;
		for ( int i = 0 ; i < 20 ; i++ ) {
			chickens.add ( new Chicken ( ( double ) ( Math.cos ( angel1 ) * radius1 ) + center.x , ( double ) ( Math.sin ( angel1 ) * radius1 ) + center.y , 1 , 0 ) );
			angel1 += Math.toRadians ( 18 );
		}
		double angel2 = 0;
		float radius2 = 200;
		for ( int j = 0 ; j < 12 ; j++ ) {
			chickens.add ( new Chicken ( ( int ) ( Math.cos ( angel2 ) * radius2 ) + center.x , ( int ) ( Math.sin ( angel2 ) * radius2 ) + center.y , 1 , 0 ) );
			angel2 += Math.toRadians ( 30 );
		}
		double angel3 = 0;
		float radius3 = 100;
		for ( int j = 0 ; j < 6 ; j++ ) {
			chickens.add ( new Chicken ( ( int ) ( Math.cos ( angel3 ) * radius3 ) + center.x , ( int ) ( Math.sin ( angel3 ) * radius3 ) + center.y , 1 , 0 ) );
			angel3 += Math.toRadians ( 60 );
		}
	}

	public void Group3 () {
		synchronized (chickens) {
			int r = 25;
			for ( int i = 0 ; i < 50 ; i++ ) {
				double degree = ( 90 ) / 180.0 * Math.PI;

				chickens.add ( new Chicken ( 60 * ( i % 8 ) + 80 ,
						75 * ( i / 8 ) + 100 ,
						1 * Math.sin ( degree ) ,
						1 * Math.cos ( degree ) ) );


			}
		}

	}

	public void Group4 () {
		synchronized (chickens) {
			int r = 25;
			for ( int i = 0 ; i < 60 ; i++ ) {
				double degree = ( 90 ) / 180.0 * Math.PI;

				chickens.add ( new Chicken ( 60 * ( i % 8 ) + 80 ,
						75 * ( i / 8 ) + 100 ,
						1 * Math.sin ( degree ) ,
						1 * Math.cos ( degree ) ) );
			}
		}
	}

	public void GroupFinal () {
		if ( level == LEVEL.ONE ) {
			System.out.println ( "groupfinal" );
			finalEgg = new FinalEgg ( 1 );
		}
		if ( level == LEVEL.TWO ) {
			//finalEgg=new FinalEgg(2);
		}
		if ( level == LEVEL.THREE ) {
			// finalEgg=new FinalEgg(3);
		}
		if ( level == LEVEL.FOUR ) {
			//finalEgg=new FinalEgg(4);
		}
	}

	public boolean Coancidence ( Chicken chicken , Tir tir ) {
		if ( ( chicken.getY ( ) >= tir.getY ( ) - 20 & chicken.getY ( ) <= tir.getY ( ) + 20 ) & ( chicken.getX ( ) >= tir.getX ( ) - 20 & chicken.getX ( ) <= tir.getX ( ) + 20 ) ) {
			return true;
		} else {
			return false;
		}
	}

	public void MoveBackground ( Graphics2D g2 ) {

		velocity = 25;
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

	public void GameInformation ( Graphics2D g2 ) {
		g2.setFont ( new Font ( "Arial" , 250 , 20 ) );
		for ( int i = 0 ; i < 20 ; i++ ) {
			g2.setColor ( new Color ( 253 , 255 , 255 ) );
			g2.fillRect ( 15 * i + 150 , 20 , 8 , 17 );
		}
		//System.out.println(MainPanel.Temprature);
		for ( int i = 0 ; i < MainPanel.Temprature / 5 ; i++ ) {
			g2.setColor ( new Color ( 200 , 0 , 0 ) );
			g2.fillRect ( 15 * i + 150 , 20 , 8 , 17 );
		}

		if ( MainPanel.Temprature > 0 & Math.abs ( System.currentTimeMillis ( ) - MainPanel.MainTime ) > 1000 & MainPanel.Temprature < 98 ) {
			MainPanel.Temprature = MainPanel.Temprature - 8;
			MainPanel.MainTime = System.currentTimeMillis ( );
		}
		if ( MainPanel.Temprature >= 98 & MainPanel.WaitForShoot == true ) {
			MainPanel.WaitForShoot = false;
			MainPanel.WaitForShootTime = System.currentTimeMillis ( );

		}
		if ( Math.abs ( System.currentTimeMillis ( ) - MainPanel.WaitForShootTime ) >= 4000 & MainPanel.Temprature >= 98 ) {

			MainPanel.WaitForShoot = true;
			MainPanel.ShootCounter = 0;
			MainPanel.Temprature = 0;
		}
		g2.drawImage ( bufferedImageHeart , 25 , 1000 , 15 , 15 , null );
		g2.setColor ( new Color ( 0 , 200 , 200 ) );
		g2.drawString ( HeartOfRocketStr , 50 , 1000 );


		g2.setColor ( new Color ( 236 , 231 , 57 ) );
		g2.drawString ( NumberOfShoot , 20 , 20 );

		g2.drawImage ( CoinBufferImage , 75 , 1000 , 15 , 15 , null );
		g2.setColor ( new Color ( 0 , 200 , 200 ) );
		g2.drawString ( ScoreStr , 50 , 1000 );

	}

	public void BombShooting ( Graphics2D g2 ) {

		if ( MainPanel.statePauseMenu == false ) {
			if ( MainPanel.BombState & rocket.getY ( ) > 550 ) {
           /* bombShoot=new BombShoot(rocket.getX(), rocket.getY());
            bombShoot.paint(g2);*/
				HeightOfBomb = rocket.getY ( );
				WidthOfBomb = rocket.getX ( );
				MainPanel.BombState = false;
				BombIsExploded = false;
			}
			if ( BombIsExploded == false ) {

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

	public void Drawtir ( Tir tir , Graphics2D g2 ) {
		tir.paint ( g2 );
	}

	public boolean CheckQuancidence ( Chicken chicken , Tir tir ) {

		if ( Coancidence ( chicken , tir ) ) {
			return true;
		} else {
			return false;
		}
	}

	public boolean CheckQuancidence ( Tir tir ) {
		if ( ( tir.getY ( ) <= 575 & tir.getX ( ) <= 1100 & tir.getX ( ) >= 700 ) ) {
			return true;
		} else {
			return false;
		}
	}

	public boolean CheckQuancidence ( Egg egg ) {
		if ( ( egg.getY ( ) >= Rocket.LastYRocket - 20 & egg.getY ( ) <= Rocket.LastYRocket + 20 ) & ( egg.getX ( ) >= Rocket.LastXRocket - 20 & egg.getX ( ) <= Rocket.LastXRocket + 20 ) ) {
			return true;
		} else {
			return false;
		}
	}

	public boolean CheckQuancidence ( Coin coin ) {
		if ( ( coin.getY ( ) >= Rocket.LastYRocket - 20 & coin.getY ( ) <= Rocket.LastYRocket + 20 ) & ( coin.getX ( ) >= Rocket.LastXRocket - 20 & coin.getX ( ) <= Rocket.LastXRocket + 20 ) ) {
			return true;
		} else {
			return false;
		}
	}

	public boolean CheckQuancidence ( Stronge stronge ) {
		if ( ( stronge.getY ( ) >= Rocket.LastYRocket - 20 & stronge.getY ( ) <= Rocket.LastYRocket + 20 ) & ( stronge.getX ( ) >= Rocket.LastXRocket - 20 & stronge.getX ( ) <= Rocket.LastXRocket + 20 ) ) {
			return true;
		} else {
			return false;
		}
	}

	/* public int Random(){

	  random function
   }*/
	public void ImagesInit () {
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
			bbufferedImage_SeconfMenu_Setting_COS2 = ImageIO.read ( new File ( "resources/tir.png" ) );

		} catch (IOException ex) {
			ex.printStackTrace ( );
		}
	}

	public int Random ( int n ) {
		Random rand = new Random ( );

		int RandomNumber = rand.nextInt ( n );
		return RandomNumber;
	}
}
