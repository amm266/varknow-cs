package Server;

import Box.Box;
import game.Menu.PauseMenu;
import game.Menu.SecondMenu_Setting;
import game.engine.*;
import game.swing.MainPanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class ServerGame extends Thread {
	public int LastXBomb;
	public int LastYBomb;
	private int width;
	private int height;
	private int HeightOfBomb = 3000000;
	private int WidthOfBomb;
	private boolean BombIsExploded = false;
	public BufferedImage bufferedImageHeart;
	public BufferedImage bufferedImagebackground;
	private BufferedImage bufferedImageBomb;
	private String NumberOfShoot = "" + MainPanel.NumberOfShoot;
	public ArrayList<Rocket> rockets = new ArrayList<> ( );
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
	public int NumberOfEgg;
	private long EggTime = System.currentTimeMillis ( );
	private static ServerGame mainGame;
	private String HeartOfRocketStr = "" + Rocket.getHart ( );
	private String ScoreStr = "" + Rocket.getScore ( );
	public static int NumberOfBomb;
	public static Point center = new Point ( 350 , 350 );

	@Override
	public void run () {
		while ( true ) {
			Thread thread = this;
			try {
				thread.sleep ( 30 );
			} catch (InterruptedException e) {
				e.printStackTrace ( );
			}
			this.chick ( );
			this.move ( );
			int a = 1;
		}
	}

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
	public static game.engine.Game.STAGE stage = game.engine.Game.STAGE.FIRST;
	public static game.engine.Game.LEVEL level = game.engine.Game.LEVEL.ONE;
	public static game.engine.Game.GROUP group = game.engine.Game.GROUP.ONE;


	public ServerGame ( int width , int height ) {
		this.width = width;
		this.height = height;
	}

	public Rocket newRocket () {
		Rocket rocket = new Rocket ( width / 2 - 50 , height - 200 );
		rockets.add ( rocket );
		return rocket;
	}
	public Box setGameFields( Box box ){
		box.setGameFields ( new ArrayList<Chicken> (  ),tirs,eggs,rockets );
		return box;
	}
	public void paint () {
		if ( Rocket.getHart ( ) <= 0 ) {
			System.out.println ( "loooooooooooose" );
		}
		//moving the background
		for ( int k = 0 ; k < eggs.size ( ) ; k++ ) {
			Egg egg = eggs.get ( k );
			if ( CheckQuancidence ( egg ) ) {
				System.out.println ( "fosh bad" );
				eggs.remove ( egg );
				Rocket.decreaseHart ( 1 );
				System.out.println ( "harts of rocket  " + Rocket.getHart ( ) );
				k--;
			}
			if ( egg.getY ( ) > 1100 ) {
				eggs.remove ( egg );
				k--;
			}
		}
		for ( int i = 0 ; i < tirs.size ( ) ; i++ ) {
			Tir tir = tirs.get ( i );
			if ( tir.getX ( ) > 2000 | tir.getY ( ) > 1100 | tir.getX ( ) < 0 | tir.getY ( ) < 0 ) {
				tirs.remove ( tir );
				i--;
			}
			if ( CheckQuancidence ( tir ) & stage == game.engine.Game.STAGE.FINAL ) {
				finalEgg.AmountOfLife--;
				tirs.remove ( tir );
			}
			for ( int j = 0 ; j < chickens.size ( ) ; j++ ) {
				Chicken chicken = chickens.get ( j );
				if ( CheckQuancidence ( chicken , tir ) ) {
					if ( Random ( 100 ) <= 6 ) {

						Strong ( chicken.getX ( ) , chicken.getY ( ) );
					}
					if ( Random ( 100 ) <= 6 ) {
						Coin ( chicken.getX ( ) , chicken.getY ( ) );
					}


					chickens.remove ( chicken );
					j--;
					tirs.remove ( tir );
				}
			}
		}
		for ( int i = 0 ; i < coins.size ( ) ; i++ ) {
			Coin coin = coins.get ( i );
			if ( coin.getY ( ) > 1100 ) {
				coins.remove ( coin );
				i--;
			}
			if ( CheckQuancidence ( coin ) ) {
				Rocket.setScore ( Rocket.getScore ( ) + 2 );
				coins.remove ( coin );
				i--;
				System.out.println ( "score:  " + Rocket.getScore ( ) );
			}

		}
		//System.out.println(Rocket.Score);

		for ( int i = 0 ; i < stronges.size ( ) ; i++ ) {
			Stronge stronge = stronges.get ( i );
			if ( stronge.getY ( ) > 1100 ) {
				stronges.remove ( stronge );
				i--;
			}
			if ( CheckQuancidence ( stronge ) ) {
				Rocket.setStrong ( Rocket.getStrong ( ) + 1 );
				Tir.StrongOfTir++;
				stronges.remove ( stronge );
				i--;
			}
		}
		if ( ( System.currentTimeMillis ( ) - EggTime ) >= 1000 ) {
			for ( Chicken chicken : chickens ) {
				if ( Random ( 50 ) <= 5 ) {
					Egg ( 1 , chicken.getX ( ) , chicken.getY ( ) );
				}
				EggTime = System.currentTimeMillis ( );
			}
		}
		for ( Chicken chicken : chickens ) {
			if ( stage == game.engine.Game.STAGE.FIRST & NumberOfChickensG1 <= 30 & G1Transform < 30 ) {
				chicken.SetX ( 60 * ( G1Transform % 6 ) + 80 );
				chicken.SetY ( 75 * ( G1Transform / 6 ) + 100 );
				G1Transform++;
				chicken.SetMiddelOfChickenX ( 0 );
			}
		}
		if ( stage == game.engine.Game.STAGE.FIRST ) {
			if ( chickens.size ( ) > 0 ) {
				NumberOfChickensG1 = chickens.size ( );
				NumberOfEgg = NumberOfChickensG1;
			}
			if ( chickens.size ( ) <= 1 ) {
				stage = game.engine.Game.STAGE.SECOND;
				chickens.clear ( );
				//NumberOfChickensG1=40;
			}
		}
		if ( stage == game.engine.Game.STAGE.SECOND ) {
			//second stage
			//System.out.println("stage2");
			if ( chickens.size ( ) > 0 ) {
				NumberOfChickensG2 = chickens.size ( );
				NumberOfEgg = NumberOfChickensG2;
			}
			if ( NumberOfChickensG2 <= 1 ) {
				stage = game.engine.Game.STAGE.THIRD;
				chickens.clear ( );
				//NumberOfChickensG2=40;
			}
		}
		if ( stage == game.engine.Game.STAGE.THIRD ) {
			//third stage
			//System.out.println("stage3");
			if ( chickens.size ( ) > 0 ) {
				NumberOfChickensG3 = chickens.size ( );
				NumberOfEgg = NumberOfChickensG3;
			}
			if ( chickens.size ( ) <= 1 ) {
				stage = game.engine.Game.STAGE.FINAL;
				chickens.clear ( );
				//NumberOfChickensG3=40;
			}
		}

		if ( stage == game.engine.Game.STAGE.FINAL ) {
			//final stage
			System.out.println ( "stagefinal" );
			if ( finalEgg.AmountOfLife <= 1 ) {
				// stage=STAGE.FIRST;
				if ( level == game.engine.Game.LEVEL.FOUR ) {
					level = game.engine.Game.LEVEL.Win;
				}
				if ( level == game.engine.Game.LEVEL.THREE ) {
					level = game.engine.Game.LEVEL.FOUR;
				}
				if ( level == game.engine.Game.LEVEL.TWO ) {
					level = game.engine.Game.LEVEL.THREE;
				}
				if ( level == game.engine.Game.LEVEL.ONE ) {
					level = game.engine.Game.LEVEL.TWO;
				}
				NumberOfBomb++;
				finalEgg.Exictance = false;
			}
		}
		System.out.println ( stage );
		System.out.println ( chickens.size ( ) );
	}

	public void move () {
		for ( Rocket rocket : rockets ) {
			rocket.move ( );
		}
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
		synchronized (eggs) {
			for ( Egg egg : eggs ) {
				egg.move ( );
			}
		}
		synchronized (coins) {
			for ( Coin coin : coins ) {
				coin.move ( );
			}
		}
		synchronized (stronges) {
			for ( Stronge stronge : stronges ) {
				stronge.move ( );
			}
		}
	}

	public void fire ( Rocket rocket ) {
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
		System.out.println ( "stage " + stage + " level " + level );
		if ( stage == game.engine.Game.STAGE.FIRST & chickens.size ( ) == 0 ) {
			if ( level == game.engine.Game.LEVEL.ONE ) {
				Group1 ( );
				//  group=GROUP.ONE;
			}
			if ( level == game.engine.Game.LEVEL.TWO ) {
				Group1 ( );
				// group=GROUP.ONE;
			}
			if ( level == game.engine.Game.LEVEL.THREE ) {
				Group2 ( );
				// group=GROUP.TWO;
			}
			if ( level == game.engine.Game.LEVEL.FOUR ) {
				Group3 ( );
				//group=GROUP.THREE;
			}
		}
		if ( stage == game.engine.Game.STAGE.SECOND & chickens.size ( ) == 0 ) {
			//second stage
			if ( level == game.engine.Game.LEVEL.ONE ) {
				Group1 ( );
				//group=GROUP.ONE;

			}
			if ( level == game.engine.Game.LEVEL.TWO ) {
				Group2 ( );
				//group=GROUP.TWO;

			}
			if ( level == game.engine.Game.LEVEL.THREE ) {
				Group3 ( );
				// group=GROUP.THREE;

			}
			if ( level == game.engine.Game.LEVEL.FOUR ) {
				Group4 ( );
				//group=GROUP.THREE;

			}
		}
		if ( stage == game.engine.Game.STAGE.THIRD & chickens.size ( ) == 0 ) {
			//third stage
			if ( level == game.engine.Game.LEVEL.ONE ) {
				Group2 ( );
				// group=GROUP.TWO;
			}
			if ( level == game.engine.Game.LEVEL.TWO ) {
				Group2 ( );
				// group=GROUP.TWO;

			}
			if ( level == game.engine.Game.LEVEL.THREE ) {
				Group3 ( );
				//group=GROUP.THREE;

			}
			if ( level == game.engine.Game.LEVEL.FOUR ) {
				Group4 ( );
				// group=GROUP.FOUR;

			}
		}
		if ( stage == game.engine.Game.STAGE.FINAL & chickens.size ( ) == 0 & Objects.isNull ( finalEgg ) ) {
			//final stage
			System.out.println ( "final" );
			GroupFinal ( );
			group = game.engine.Game.GROUP.FINAL;
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
		if ( level == game.engine.Game.LEVEL.ONE ) {
			System.out.println ( "groupfinal" );
			finalEgg = new FinalEgg ( 1 );
		}
		if ( level == game.engine.Game.LEVEL.TWO ) {
			//finalEgg=new FinalEgg(2);
		}
		if ( level == game.engine.Game.LEVEL.THREE ) {
			// finalEgg=new FinalEgg(3);
		}
		if ( level == game.engine.Game.LEVEL.FOUR ) {
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

	public boolean CheckQuancidence ( Chicken chicken , Tir tir ) {

		return Coancidence ( chicken , tir );
	}

	public boolean CheckQuancidence ( Tir tir ) {
		return ( tir.getY ( ) <= 575 & tir.getX ( ) <= 1100 & tir.getX ( ) >= 700 );
	}

	public boolean CheckQuancidence ( Egg egg ) {
		return CheckQuancidence ( egg.getY ( ) , egg.getX ( ) );
	}

	public boolean CheckQuancidence ( Coin coin ) {
		return CheckQuancidence ( coin.getY ( ) , coin.getX ( ) );
	}

	public boolean CheckQuancidence ( Stronge stronge ) {
		return CheckQuancidence ( stronge.getY ( ) , stronge.getX ( ) );
	}

	public boolean CheckQuancidence ( double y , double x ) {
		return ( y >= Rocket.LastYRocket - 20 & y <= Rocket.LastYRocket + 20 ) & ( x >= Rocket.LastXRocket - 20 & x <= Rocket.LastXRocket + 20 );
	}

	public int Random ( int n ) {
		Random rand = new Random ( );

		int RandomNumber = rand.nextInt ( n );
		return RandomNumber;
	}

}
