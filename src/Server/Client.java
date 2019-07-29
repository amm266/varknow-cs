package Server;

import Box.*;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import game.MyConnection;
import game.engine.Rocket;
import game.swing.MainPanel;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class Client implements Runnable {
	private static ArrayList<Client> clients = new ArrayList<> ( );
	private MyConnection myConnection;
	private ServerGame game;
	private static YaGson yaGson;
	private static Scanner scanner;
	private static Formatter formatter;
	private Rocket rocket;
	private MainPanel.STATE state;

	static {
		YaGsonBuilder yaGsonBuilder = new YaGsonBuilder ( );
		yaGson = yaGsonBuilder.create ( );
	}

	public Client ( Socket socket ) {

		myConnection = new MyConnection ( socket );
		try {
			scanner = new Scanner ( socket.getInputStream ( ) );
			formatter = new Formatter ( socket.getOutputStream ( ) );
		} catch (IOException e) {
			e.printStackTrace ( );
		}
		clients.add ( this );
		System.out.println ( "New Client Connected!!!" );
	}

	public MyConnection getMyConnection () {
		return myConnection;
	}

	@Override
	public void run () {
		while ( true ) {
//			check myConnection
			if ( ! myConnection.isConnect ( ) ) {
				disconnect ( );
				break;
			}
			//get from client
			Box box = ( Box ) myConnection.get ( );
			int a = 1;
			//process
			System.out.println ( "start process" );
			BoxFather answer = new BoxFather ( BoxFather.BoxType.simple , false );
			if ( box == null ) {
				int w = 1;
			}
			if ( box.getAsk ( ) != null ) {
				switch ( box.getAsk ( ) ) {
					case state:
						box.setStage ( game.stage );
						break;
					case fire:
						game.fire ( rocket );
						break;
					case setLocation:
						rocket.setLocation ( box.getX ( ) , box.getY ( ) );
						break;
					case startNewGame:
						game = new ServerGame ( 2000 , 1100 , this );
						rocket = game.newRocket ( );
						state = MainPanel.STATE.Game;
						game.start ( );
						answer = new Box ( );
						( ( Box ) answer ).setState ( state );
						break;
					case saveGame:
						try {
							SaveSystem.save ( SaveSystem.SaveMode.game , game , "game1.save" );
						} catch (IOException e) {
							e.printStackTrace ( );
						}
						break;
					case loadGame:
						try {
							finishGame ( );
							game = new ServerGame ( 2000 , 1100 , this );
							GameForSave gameForSave = ( GameForSave ) SaveSystem.load ( SaveSystem.SaveMode.game ,
									"game1.save" ,this);
							if ( gameForSave == null ) {
								System.out.println ( "fosh" );
							} else {
								game.loadGame ( gameForSave );
								game.start ();
							}
						} catch (IOException e) {
							e.printStackTrace ( );
						}
						break;
				}
			}
			System.out.println ( "end process" );
			//send to client
			myConnection.send ( answer );
		}
	}

	public Rocket getRocket () {
		return rocket;
	}

	public void setRocket ( Rocket rocket ) {
		this.rocket = rocket;
	}

	private void finishGame () {
		game.interrupt ( );
		game = null;
	}

	private void disconnect () {
		clients.remove ( this );
	}
}
