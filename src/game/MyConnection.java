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

public class MyConnection {
	static YaGson yaGson;
	static {
		YaGsonBuilder yaGsonBuilder = new YaGsonBuilder ( );
		yaGson = yaGsonBuilder.create ( );
	}
	Object lock = new Object ();
	private volatile Get get;
	private volatile Send send;
	Socket socket;
	Scanner scanner;
	Formatter formatter;
	volatile BoxFather tmp;
	volatile BoxFather simple;
	private static ArrayList<MyConnection> myConnections =new ArrayList<> (  );
	public MyConnection ( String ip ) {
		try {
			socket = new Socket ( ip , 8888 );
			scanner = new Scanner ( socket.getInputStream ( ) );
			formatter = new Formatter ( socket.getOutputStream ( ) );

		} catch (IOException e) {
			e.printStackTrace ( );
		}
		get = new Get ( this );
		send = new Send ( this );
		Thread thread = new Thread ( get );
		Thread thread1 = new Thread ( send );
	//	thread1.setPriority ( 10 );
		thread.start ();
		thread1.start ();
		myConnections.add ( this );
	}

	public MyConnection ( Socket socket ) {
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
		myConnections.add ( this );
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
		//box = get ( );
		return box;
	}

	public BoxFather get () {
		//System.out.println ("get is started" );
		if ( simple == null ) {
			synchronized (lock){
				try {
					lock.wait ();
				} catch (InterruptedException e) {
					e.printStackTrace ( );
				}
			}
		}
		BoxFather boxFather = simple;
		simple = null;
		//System.out.println ("make simple null" );
		//System.out.println ("get is completed" );
		return boxFather;
	}

	public void send ( BoxFather box ) {
		send.addToQueue ( box );
		synchronized (send.boxFathers) {
			send.getBoxFathers ( ).notifyAll ( );
		}
		//System.out.println ("some box added to send queue!!!" );
	}
}

class Send implements Runnable {
	private MyConnection myConnection;
	Send ( MyConnection myConnection ){
		this.myConnection = myConnection;
	}
	volatile ArrayList<BoxFather> boxFathers = new ArrayList<BoxFather> ( );
	@Override
	public void run () {
		while ( true ) {
		//	System.out.println ("numbers in queue"+boxFathers.size () );
			if ( boxFathers.size ( ) == 0 ) {
				try {
					synchronized (boxFathers) {
						boxFathers.wait ( );
					}
				} catch (InterruptedException e) {
					e.printStackTrace ( );
				}
			}
		//		System.out.println ("send    " );
				send ( boxFathers.get ( 0 ), myConnection );
				boxFathers.remove ( 0 );
		}
	}

	public ArrayList<BoxFather> getBoxFathers () {
		return boxFathers;
	}

	void addToQueue( BoxFather boxFather){
		boxFathers.add ( boxFather );
	}
	private static void send ( BoxFather box, MyConnection myConnection ) {
		myConnection.simple = null;
		//System.out.println ("make simple null" );
		String obj = MyConnection.yaGson.toJson ( box );
		myConnection.formatter.format ( obj + "\n" );
		try {
			synchronized (obj) {
				if ( box.getBoxType ( ) == BoxFather.BoxType.gameField )
					System.out.println ( "data" + obj.toCharArray ( ).length );
			}
		}
		catch (Exception e){
			//e.printStackTrace ();
		}
		System.out.println ("sended" );
		myConnection.formatter.flush ( );
		//System.out.println ("send Box :" + box.getBoxType () );
	}
}

class Get implements Runnable {
	private MyConnection myConnection;
	Get ( MyConnection myConnection ){
		this.myConnection = myConnection;
	}
	@Override
	public void run () {
		while ( true ) {
			String get = "";
			while ( true ) {
				if ( myConnection.scanner.hasNextLine ( ) ) {
					get = myConnection.scanner.nextLine ( );
					break;
				}
			}
			myConnection.tmp = MyConnection.yaGson.fromJson ( get , BoxFather.class );
			try {
				switch ( myConnection.tmp.getBoxType ( ) ) {
					case gameField:
						MainPanel.setGameField ( ( GameFields ) myConnection.tmp );
						System.out.println ( "get fields" );
						myConnection.tmp = null;
						break;
					case simple:
						myConnection.simple = myConnection.tmp;
						synchronized (myConnection.lock){
							myConnection.lock.notifyAll ();
						}
						break;
				}
			}
			catch (Exception e){
			//	e.printStackTrace ();
				System.out.println ( "npe");
			}
		}
	}
}

