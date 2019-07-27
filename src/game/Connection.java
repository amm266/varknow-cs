package game;

import Box.BoxFather;
import Box.GameFields;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import game.swing.MainPanel;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class Connection {
	static YaGson yaGson;
	static {
		YaGsonBuilder yaGsonBuilder = new YaGsonBuilder ( );
		yaGson = yaGsonBuilder.create ( );
	}
	private Get get;
	private Send send;
	Socket socket;
	Scanner scanner;
	Formatter formatter;
	BoxFather tmp;
	BoxFather simple;

	public Connection ( String ip ) {
		try {
			socket = new Socket ( ip , 8888 );
			scanner = new Scanner ( socket.getInputStream ( ) );
			formatter = new Formatter ( socket.getOutputStream ( ) );

		} catch (IOException e) {
			e.printStackTrace ( );
		}
	}

	public Connection ( Socket socket ) {
		try {
			scanner = new Scanner ( socket.getInputStream ( ) );
			formatter = new Formatter ( socket.getOutputStream ( ) );
		} catch (IOException e) {
			e.printStackTrace ( );
		}
	}
	public void disconnect(){

	}
	public boolean isConnect(){
		return socket.isConnected ();
	}
	public BoxFather connection ( BoxFather box ) {
		send ( box );
		box = get ( );
		return box;
	}

	private BoxFather get () {
		while ( simple == null ) ;
		BoxFather boxFather = simple;
		simple = null;
		return boxFather;
	}

	private void send ( BoxFather box ) {
		Send.addToQueue ( box );
	}
}

class Send implements Runnable {
	private Connection connection;
	Send (Connection connection){
		this.connection = connection;
	}
	static ArrayList<BoxFather> boxFathers = new ArrayList<BoxFather> ( );
	@Override
	public void run () {
		while ( true ) {
			if ( boxFathers.size ( ) > 0 ) {
				send ( boxFathers.get ( 0 ),connection );
				boxFathers.remove ( 0 );
			}
		}
	}
	static void addToQueue(BoxFather boxFather){
		boxFathers.add ( boxFather );
	}
	private static void send ( BoxFather box,Connection connection ) {
		connection.simple = null;
		String obj = Connection.yaGson.toJson ( box );
		connection.formatter.format ( obj + "\n" );
		connection.formatter.flush ( );
	}
}

class Get implements Runnable {
	private Connection connection;
	Get (Connection connection){
		this.connection = connection;
	}
	@Override
	public void run () {
		while ( true ) {
			String get = "";
			while ( true ) {
				if ( connection.scanner.hasNextLine ( ) ) {
					get = connection.scanner.nextLine ( );
					break;
				}
			}
			connection.tmp = Connection.yaGson.fromJson ( get , BoxFather.class );
			switch ( connection.tmp.getBoxType ( ) ) {
				case gameField:
					MainPanel.setGameField ( ( GameFields ) connection.tmp );
					connection.tmp = null;
					break;
				case simple:
					connection.simple = connection.tmp;
					break;
			}
		}
	}
}

