package Server;

import Account.Account;
import Box.*;
import game.MyConnection;
import game.engine.Rocket;
import game.engine.Tir;
import game.swing.MainPanel;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Client implements Runnable {
	private static ArrayList<Client> clients = new ArrayList<> ( );
	private MyConnection myConnection;
	private ServerGame game;
	private Account account = null;
	private Rocket rocket;
	private MainPanel.STATE state;
	private DataBase dataBase;

	public Client ( Socket socket ) {
		try {
			dataBase = new DataBase ( );
		} catch (ClassNotFoundException e) {
			e.printStackTrace ( );
		} catch (SQLException e) {
			e.printStackTrace ( );
		}
		myConnection = new MyConnection ( socket );
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
			game = new ServerGame ( 2000 , 1100 , this );
			//get from client
			Box box = ( Box ) myConnection.get ( );
			int a = 1;
			//process
			System.out.println ( "start process" );
			Box answer = new Box ( false);
			if ( box == null ) {
				int w = 1;
			}
			if ( box.getAsk ( ) != null ) {
				switch ( box.getAsk ( ) ) {
					case createAccount:
						try {
							account = Account.newAccount ( box.getUserName ( ) , box.getPass ( ),0 ,false);
						} catch (SQLException e) {
							e.printStackTrace ( );
						}
						if ( account == null ) {
							answer.setSucces ( false );
						} else {
							answer.setSucces ( true );
						}
						break;
					case login:
						account = Account.login ( box.getUserName (),box.getPass () );
						if ( account == null ) {
							answer.setSucces ( false );
						} else {
							answer.setSucces ( true );
						}
						break;
					case state:
						box.setStage ( game.stage );
						break;
					case fire:
						game.chickens.clear ( );
						game.fire ( rocket );
						break;
					case setLocation:
						rocket.setLocation ( box.getX ( ) , box.getY ( ) );
						answer = null;
						break;
					case startNewGame:
						rocket = game.newRocket ( );
						state = MainPanel.STATE.Game;
						game.start ( );
						answer = null;
						break;
					case saveGame:
						try {
							dataBase.saveTirs ( ServerGame.getTirs ( ) , 1 );
						} catch (SQLException e) {
							e.printStackTrace ( );
						}
						try {
							SaveSystem.save ( SaveSystem.SaveMode.game , game , account.getUserName ( ) + "game.json" , rocket );
						} catch (IOException e) {
							e.printStackTrace ( );
						}
						break;
					case loadGame:
						//try {
						//	finishGame ( );
						//game = new ServerGame ( 2000 , 1100 , this );
						//	GameForSave gameForSave = ( GameForSave ) SaveSystem.load ( SaveSystem.SaveMode.game ,
						//	account.getUserName ()+"game.json" ,this);
						//	if ( gameForSave == null ) {
						//		System.out.println ( "fosh" );
						//	} else {
						//		game.loadGame ( gameForSave );
						//	game.start ();
						//	}
						//	} catch (IOException e) {
						//	e.printStackTrace ( );
						//}
						game.chickens.clear ( );

						ArrayList<Tir> tirs;
						try {
							tirs = dataBase.loadTirs ( );
							System.out.println ( tirs );
							System.out.println ( "size" + tirs.size ( ) );
							ServerGame.setTirs ( tirs );
						} catch (SQLException e) {
							e.printStackTrace ( );
						}
						break;
				}
			}
			System.out.println ( "end process" );
			//send to client
			if ( answer != null ) {
				myConnection.send ( answer );
			}
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