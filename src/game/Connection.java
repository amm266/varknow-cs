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
	volatile BoxFather tmp;
	volatile BoxFather simple;
	private static ArrayList<Connection> connections =new ArrayList<> (  );
	public Connection ( String ip ) {
		try {
			socket = new Socket ( ip , 8888 );
			scanner = new Scanner ( socket.getInputStream ( ) );
			formatter = new Formatter ( socket.getOutputStream ( ) );

		} catch (IOException e) {
			e.printStackTrace ( );
		}
		get = new Get ( this );
		send = new Send ( this );
		new Thread ( send ).start ();
		new Thread ( get ).start ();
		connections.add ( this );
	}

	public Connection ( Socket socket ) {
		try {
			scanner = new Scanner ( socket.getInputStream ( ) );
			formatter = new Formatter ( socket.getOutputStream ( ) );
		} catch (IOException e) {
			e.printStackTrace ( );
		}
		get = new Get ( this );
		send = new Send ( this );
		new Thread ( send ).start ();
		new Thread ( get ).start ();
		connections.add ( this );
	}
	public void disconnect(){

	}
	public boolean isConnect(){
		if ( socket == null )
			return true;
		return socket.isConnected ();
	}
	public BoxFather connection ( BoxFather box ) {
		send ( box );
		box = get ( );
		return box;
	}

	public BoxFather get () {
		//System.out.println ("get is started" );
		while ( simple == null ) {

		}
		BoxFather boxFather = simple;
		simple = null;
		//System.out.println ("make simple null" );
		//System.out.println ("get is completed" );
		return boxFather;
	}

	public void send ( BoxFather box ) {
		send.addToQueue ( box );
		//System.out.println ("some box added to send queue!!!" );
	}
}

class Send implements Runnable {
	private Connection connection;
	Send (Connection connection){
		this.connection = connection;
	}
	ArrayList<BoxFather> boxFathers = new ArrayList<BoxFather> ( );
	@Override
	public void run () {
		while ( true ) {
			try {
				Thread.sleep ( 1 );
			} catch (InterruptedException e) {
				e.printStackTrace ( );
			}
			if ( boxFathers.size ( ) > 0 ) {
		//		System.out.println ("send    " );
				send ( boxFathers.get ( 0 ),connection );
				boxFathers.remove ( 0 );
			}
		}
	}
	void addToQueue(BoxFather boxFather){
		boxFathers.add ( boxFather );
	}
	private static void send ( BoxFather box,Connection connection ) {
		connection.simple = null;
		//System.out.println ("make simple null" );
		String obj = Connection.yaGson.toJson ( box );
		connection.formatter.format ( obj + "\n" );
		if ( box.getBoxType () == BoxFather.BoxType.gameField )
			System.out.println ("data"+obj.toCharArray ().length );
		connection.formatter.flush ( );
		//System.out.println ("send Box :" + box.getBoxType () );
	}
}

class Get implements Runnable {
	private Connection connection;
	Get (Connection connection){
		this.connection = connection;
	}
	@Override
	public void run () {
		try {
			Thread.sleep ( 1 );
		} catch (InterruptedException e) {
			e.printStackTrace ( );
		}
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

