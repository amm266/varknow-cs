package game.swing;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import game.Connection;
import game.Menu.FirstMenu;
import game.Menu.PauseMenu;
import game.Menu.SecondMenu;
import game.engine.*;
import game.logger.Logger;
import Box.Box;
import Box.BoxFather;
import Box.GameFields;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;

public class MainPanel extends JPanel implements KeyListener {
	private JTextField jTextField0 = new JTextField ( );
	private JTextField jTextField1 = new JTextField ( );
	private JTextField jTextField2 = new JTextField ( );
	private JTextField jTextField3 = new JTextField ( );
	private boolean GameState = false;
	private boolean MenuState = false;
	private static Game game;
	SecondMenu SecondMenu = new SecondMenu ( );
	FirstMenu FirstMenu = new FirstMenu ( );
	public static int x;
	public static int y;
	public static long TimeOfShoot;
	public static int ShootCounter = 0;
	public static boolean WaitForShoot = true;
	public static long WaitForShootTime;
	public static int NumberOfShoot = 10000;
	public static boolean BombState = false;//nounsence
	public static boolean statePauseMenu = false;
	public static Logger log;
	public static int Temprature = 0;
	public static long MainTime = System.currentTimeMillis ( );
	private long TimeOfBombShoot = 0;
	private static Connection connection;

	@Override
	public void keyTyped ( KeyEvent e ) {
	}
	@Override
	public void keyPressed ( KeyEvent e ) {
		if ( e.getKeyCode ( ) == KeyEvent.VK_S ) {
			System.out.println ( "save" );
		}
	}
	@Override
	public void keyReleased ( KeyEvent e ) {
	}
	public enum STATE {
		SEcondMenu,
		FIrstMenu,
		PauseMenu,
		Game
	};

	public static Connection getConnection () {
		return connection;
	}

	private static STATE state = STATE.FIrstMenu;

	public static STATE getState () {
		return state;
	}

	public MainPanel ( String ip ) {
		connection = new Connection ( ip );
		new MoveRocket ().start ();
		setBounds ( 0 , 0 , 2000 , 1100 );
		game = new Game ( 2000 , 1100 );
		if ( state == STATE.FIrstMenu ) {
			Jtextfieldinit ( jTextField0 , 250 , 320 , 480 , 40 , this );
			Jtextfieldinit ( jTextField1 , 250 , 390 , 480 , 40 , this );
			Jtextfieldinit ( jTextField2 , 250 , 440 , 480 , 40 , this );
			Jtextfieldinit ( jTextField3 , 250 , 490 , 480 , 40 , this );
		}
	}
	public static void setGameField(GameFields gameFields){
		game.getGameFields ( gameFields );
	}
	@Override
	protected void paintComponent ( Graphics g ) {
		super.paintComponent ( g );
		if ( state == STATE.Game ) {
			game.paint ( ( Graphics2D ) g );
		}
		if ( state == STATE.FIrstMenu ) {
			FirstMenu.paint ( ( Graphics2D ) g );
			MenuState = FirstMenu.MenuState ( );
			if ( MenuState ) {
				state = STATE.SEcondMenu;
			}
		}
		if ( state == STATE.SEcondMenu ) {
			this.remove ( jTextField0 );
			this.remove ( jTextField1 );
			this.remove ( jTextField2 );
			this.remove ( jTextField3 );
			SecondMenu.paint ( ( Graphics2D ) g );
			GameState = SecondMenu.GameState ( );
			if ( GameState ) {
				state = STATE.Game;
			}
		}
		addKeyListener ( new KeyListener ( ) {
			@Override
			public void keyTyped ( KeyEvent e ) {
				System.out.println ( "pressed" );
			}
			@Override
			public void keyPressed ( KeyEvent e ) {
				if ( e.getKeyCode ( ) == KeyEvent.VK_S ) {
					System.out.println ( "save" );
				}
				System.out.println ( "pressed" );
			}
			@Override
			public void keyReleased ( KeyEvent e ) {
				System.out.println ( "pressed" );
			}
		} );
		addMouseMotionListener ( new MouseMotionListener ( ) {
			@Override
			public void mouseDragged ( MouseEvent e ) {
			}
			@Override
			public void mouseMoved ( MouseEvent e ) {
				x = e.getX ( );
				y = e.getY ( );
				Box box = new Box ( Box.Ask.setLocation  );
				box.setX ( x );
				box.setY ( y );
				if ( state == STATE.Game ) {
				//	connection.connection ( box );
				}
				MoveRocket.setLocation ( x,y );
			}
		} );
		addMouseListener ( new MouseListener ( ) {
			@Override
			public void mouseClicked ( MouseEvent e ) {

				if ( state == STATE.FIrstMenu ) {
					FirstMenu.buttonClicked ( x , y , jTextField0 , jTextField1 );
					System.out.println ( FirstMenu.counterOfPlayer );
				}

				if ( state == STATE.SEcondMenu ) {
					SecondMenu.buttonClicked ( x , y );
				}
				if ( state == STATE.Game && statePauseMenu ) {
					PauseMenu.ButtonClicked ( x , y );
				}

				if ( state == STATE.Game & ! statePauseMenu ) {
					if ( ShootCounter % 2 == 0 ) {
						//todo
						connection.connection ( new Box ( Box.Ask.fire ) );
						NumberOfShoot--;
						TimeOfShoot = System.currentTimeMillis ( );
						ShootCounter++;
						Temprature += 5;
					}
					if ( ( System.currentTimeMillis ( ) - TimeOfShoot ) >= 100 && WaitForShoot ) {
						ShootCounter++;
						Temprature += 5;
					}
				}
			}
			@Override
			public void mousePressed ( MouseEvent e ) {
				if ( SwingUtilities.isRightMouseButton ( e ) ) {
					BombState = true;
					if ( System.currentTimeMillis ( ) - TimeOfBombShoot > 1000 ) {
						if ( Game.stage == Game.STAGE.THIRD ) {
							Game.stage = Game.STAGE.FINAL;
						}
						if ( Game.stage == Game.STAGE.FINAL ) {
							FinalEgg.AmountOfLife -= 50;
						}
						if ( Game.stage == Game.STAGE.SECOND ) {
							Game.stage = Game.STAGE.THIRD;
						}
						if ( Game.stage == Game.STAGE.FIRST ) {
							System.out.println ( "bomb exploded" );
							Game.chickens.clear ( );
						}
						Game.NumberOfBomb--;
						TimeOfBombShoot = System.currentTimeMillis ( );
					}
				}
			}
			@Override
			public void mouseReleased ( MouseEvent e ) {
			}
			@Override
			public void mouseEntered ( MouseEvent e ) {
			}
			@Override
			public void mouseExited ( MouseEvent e ) {
				// TODO: 7/28/2019
//				if ( state == STATE.Game ) {
//					statePauseMenu = true;
//					Logger.getLogger ( ).LocationOfRocket ( Rocket.LastXRocket , Rocket.LastYRocket );
//					Logger.getLogger ( ).TypeOfSpacecraft ( SecondMenu_Setting.LastTypeOfSpacecraft );
//					Logger.getLogger ( ).ColorOfShoot ( SecondMenu_Setting.LastColorOfShoot );
//					Logger.getLogger ( ).TypeOfShoot ( SecondMenu_Setting.LastTypeOfShoot );
//					Logger.getLogger ( ).NameOfPlayer ( FirstMenu.NameOfPlayer );
//				}
			}
		} );
	}
	public boolean state () {
		if ( state == STATE.Game ) {
			return true;
		} else {
			return false;
		}
	}
	public static int ShootCounter () {
		return ShootCounter;
	}
	public JTextField Jtextfieldinit ( JTextField jTextField , int x , int y , int width , int height , MainPanel Mainpanel ) {
		jTextField.setLocation ( x , y );
		jTextField.setSize ( width , height );
		setVisible ( true );
		setLayout ( null );
		Mainpanel.add ( jTextField );
		return jTextField;
	}
	public static void setState ( STATE state ) {
		MainPanel.state = state;
	}
	public static BoxFather startNewGame(){
		System.out.println ("new Game!" );
		Box box = new Box ( Box.Ask.startNewGame );
		BoxFather answer = connection.connection ( box );
		state = STATE.Game;
		System.out.println ("new Game!" );
		return answer;
	}
}
class MoveRocket extends Thread{
	private static int x;
	private static int y;
	private static int waitMilis = 50;
	public static void setLocation(int x,int y){
		MoveRocket.x = x;
		MoveRocket.y = y;
	}
	@Override
	public void run () {
		while ( true ){
			try {
				sleep ( waitMilis );
			} catch (InterruptedException e) {
				e.printStackTrace ( );
			}
			Box box = new Box ( Box.Ask.setLocation  );
			box.setX ( x );
			box.setY ( y );
			if ( MainPanel.getState () == MainPanel.STATE.Game ) {
				MainPanel.getConnection ().connection ( box );
			}
		}
	}
}