package Server;

import Box.*;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import game.Connection;
import game.engine.Rocket;
import game.swing.MainPanel;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class Client implements Runnable {
	private static ArrayList<Client> clients = new ArrayList<> ( );
	private Connection connection;
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

		connection = new Connection ( socket );
		try {
			scanner = new Scanner ( socket.getInputStream ( ) );
			formatter = new Formatter ( socket.getOutputStream ( ) );
		} catch (IOException e) {
			e.printStackTrace ( );
		}
		clients.add ( this );
		System.out.println ("New Client Connected!!!" );
	}

	public Connection getConnection () {
		return connection;
	}

	@Override
	public void run () {
		game = new ServerGame ( 2000 , 1100,this );
		while ( true ) {
//			check connection
			if (!connection.isConnect () ){
				disconnect ();
				break;
			}
			//get from client
			Box box = (Box ) connection.get ( );
			int a=1;
			//process
			System.out.println ("start process" );
			BoxFather answer = new BoxFather ( BoxFather.BoxType.simple,false);
			if(box == null){
				int w=1;
			}
			if ( box.getAsk ( ) != null ) {
				switch ( box.getAsk ( ) ) {
					case state:
						box.setStage ( ServerGame.stage );
						break;
					case fire:
						game.fire ( rocket );
						break;
					case setLocation:
						rocket.setLocation ( box.getX ( ) , box.getY ( ) );
						break;
					case startNewGame:
						rocket =  game.newRocket ();
						state = MainPanel.STATE.Game;
						game.start ();
						answer = new Box (  );
						( ( Box ) answer ).setState ( state );
						break;
				}
			}
			System.out.println ("end process" );
			//send to client
			connection.send ( answer );
		}
	}
	private void disconnect(){
		clients.remove ( this );
	}
}
