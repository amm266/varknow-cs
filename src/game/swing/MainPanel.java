package game.swing;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import game.Menu.FirstMenu;
import game.Menu.PauseMenu;
import game.Menu.SecondMenu;
import game.Menu.SecondMenu_Setting;
import game.engine.*;
import game.logger.Logger;
import game.logger.Read;
import Box.Box;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class MainPanel extends JPanel implements KeyListener {
	private static YaGson yaGson;
	static {
		YaGsonBuilder yaGsonBuilder = new YaGsonBuilder ();
		yaGson = yaGsonBuilder.create ();
	}
	private JTextField jTextField0 = new JTextField ( );
	private JTextField jTextField1 = new JTextField ( );
	private JTextField jTextField2 = new JTextField ( );
	private JTextField jTextField3 = new JTextField ( );
	private boolean GameState = false;
	private boolean MenuState = false;
	private Game game;
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
	private static Socket socket;
	private static Scanner scanner;
	private static Formatter formatter;

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
	private static STATE state = STATE.FIrstMenu;
	public MainPanel ( String ip ) {
		try {
			socket = new Socket ( ip , 8888 );
			scanner = new Scanner ( socket.getInputStream ( ) );
			formatter = new Formatter ( socket.getOutputStream ( ) );
		} catch (IOException e) {
			e.printStackTrace ( );
		}
		setBounds ( 0 , 0 , 2000 , 1100 );
		game = new Game ( 2000 , 1100 );
		if ( state == STATE.FIrstMenu ) {
			Jtextfieldinit ( jTextField0 , 250 , 320 , 480 , 40 , this );
			Jtextfieldinit ( jTextField1 , 250 , 390 , 480 , 40 , this );
			Jtextfieldinit ( jTextField2 , 250 , 440 , 480 , 40 , this );
			Jtextfieldinit ( jTextField3 , 250 , 490 , 480 , 40 , this );
		}
	}
	@Override
	protected void paintComponent ( Graphics g ) {
		super.paintComponent ( g );
		if ( state == STATE.Game ) {
			Box box = gameFields ();
			game.getGameFields ( box );
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
					//game.getRocket ( ).setLocation ( e.getX ( ) , e.getY ( ) );
					connection ( box );
				}
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
						connection ( new Box ( Box.Ask.fire ) );
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
				if ( state == STATE.Game ) {
					statePauseMenu = true;
					Logger.getLogger ( ).LocationOfRocket ( Rocket.LastXRocket , Rocket.LastYRocket );
					Logger.getLogger ( ).TypeOfSpacecraft ( SecondMenu_Setting.LastTypeOfSpacecraft );
					Logger.getLogger ( ).ColorOfShoot ( SecondMenu_Setting.LastColorOfShoot );
					Logger.getLogger ( ).TypeOfShoot ( SecondMenu_Setting.LastTypeOfShoot );
					Logger.getLogger ( ).NameOfPlayer ( FirstMenu.NameOfPlayer );
				}
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
		//jTextField.setText("pleas input your name :)");
		setVisible ( true );
		setLayout ( null );
		Mainpanel.add ( jTextField );
		return jTextField;
	}
	public static Box connection(Box box){
		send ( box );
		return get ();
	}
	private static Box get (){
		String get = "";
		while (true) {
			if (scanner.hasNextLine()) {
				get = scanner.nextLine();
				break;
			}
		}
		return yaGson.fromJson(get, Box.class);
	}

	public static void setState ( STATE state ) {
		MainPanel.state = state;
	}

	private static void send ( Box box ){
		String obj = yaGson.toJson(box);
		formatter.format(obj + "\n");
		formatter.flush();
	}
	public static Box startNewGame(){
		Box box = new Box ( Box.Ask.startNewGame );
		Box answer = connection ( box );
		state = answer.getState ();
		return answer;
	}
	public static Box gameFields(){
		Box box = new Box ( Box.Ask.gameFields );
		return connection ( box );
	}
}