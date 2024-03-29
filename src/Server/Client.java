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
	private volatile ServerGame game;
	private Account account = null;
	private Rocket rocket;
	private volatile GameFields.GameState gameState;

	public Client ( Socket socket ) {
		myConnection = new MyConnection ( socket , this );
		new Thread ( myConnection ).start ( );
		clients.add ( this );
		System.out.println ( "New Client Connected!!!" );
	}

	public MyConnection getMyConnection () {
		return myConnection;
	}

	@Override
	public void run () {
		game = new ServerGame ( 2000 , 1100 , this );
		while ( true ) {
		//	game = new ServerGame ( 2000 , 1100 , this );
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
			Box answer = new Box ( false );
			if ( box == null ) {
				int w = 1;
			}
			Box.Ask ask = Box.Ask.chick;
			try {
				ask = box.getAsk ( );
			} catch (Exception e) {
				e.printStackTrace ( );
			}
			switch ( ask ) {
				case createAccount:
					createAccount ( box , answer );
					break;
				case login:
					account = Account.login ( box.getUserName ( ) , box.getPass ( ) );
					if ( account == null ) {
						answer.setSucces ( false );
					} else {
						answer.setSucces ( true );
					}
					break;
				case updateAccount:
					Account.updateAccount ( box.getUserName ( ) , box.getPass ( ) , box.getNewPass ( ) );
					break;
				case deleteAccount:
					Account.deleteAccount ( box.getUserName ( ) , box.getPass ( ) );
				case state:
					box.setStage ( game.stage );
					break;
				case fire:
					game.fire ( rocket );
					int x=2;
					break;
				case setLocation:
					if ( gameState == GameFields.GameState.inGame ) {
						try {
							rocket.setLocation ( box.getX ( ) , box.getY ( ) );
							answer = null;
						} catch (Exception e) {
							e.printStackTrace ( );
						}
					}
					break;
				case startNewGame:
					newGame ( answer );
					break;
				case saveGame:
					save ( );
					break;
				case loadGame:
					load ( );
					System.out.println ("client rocket"+rocket );
					break;
				case chick:
					break;
			}
			System.out.println ( "end process" );
			//send to client
			if ( answer != null ) {
				myConnection.send ( answer );
			}
		}
	}

	public void setGameState ( GameFields.GameState gameState ) {
		this.gameState = gameState;
		int a=1;
	}

	public GameFields.GameState getGameState () {
		return gameState;
	}

	private void createAccount ( Box box , Box answer ) {
		try {
			account = Account.newAccount ( box.getUserName ( ) , box.getPass ( ) , 0 , false );
		} catch (SQLException e) {
			e.printStackTrace ( );
		}
		if ( account == null ) {
			answer.setSucces ( false );
		} else {
			answer.setSucces ( true );
		}
		return;
	}

	private void newGame ( Box answer ) {
		if ( ServerGame.getMainGame ( ) == null ) {
			ServerGame.setMainGame ( game );
			game.start ( );
		} else {
			this.game = ServerGame.getMainGame ( );
			this.setGameState ( GameFields.GameState.inGame );
			synchronized (game.getClients ()) {
				game.getClients ( ).add ( this );
			}
		}
		rocket = game.newRocket ( );
		answer.setSucces ( true );
	}

	private void save () {
//		try {
//			dataBase.saveTirs ( ServerGame.getTirs ( ) , 1 );
//		} catch (SQLException e) {
//			e.printStackTrace ( );
//		}
		try {
			SaveSystem.save ( SaveSystem.SaveMode.game , game , account.getUserName ( ) + "game.json" , rocket );
		} catch (IOException e) {
			e.printStackTrace ( );
		}
	}

	public static ArrayList<Client> getClients () {
		return clients;
	}

	private void load () {
		try {
			finishGame ( );
			game = new ServerGame ( 2000 , 1100 , this );
			GameForSave gameForSave = ( GameForSave ) SaveSystem.load ( SaveSystem.SaveMode.game ,
					account.getUserName ( ) + "game.json" , this );
			if ( gameForSave == null ) {
				System.out.println ( "fosh" );
			} else {
				game.loadGame ( gameForSave );
				game.start ( );
			}
		} catch (IOException e) {
			e.printStackTrace ( );
		}
//		ArrayList<Tir> tirs;
//		try {
//			tirs = dataBase.loadTirs ( );
//			System.out.println ( tirs );
//			System.out.println ( "size" + tirs.size ( ) );
//			ServerGame.setTirs ( tirs );
//		} catch (SQLException e) {
//			e.printStackTrace ( );
//		}
	}

	public Rocket getRocket () {
		return rocket;
	}

	public void setRocket ( Rocket rocket ) {
		this.rocket = rocket;
	}

	private void finishGame () {
		game.stopThread ();
		game = null;
	}

	public void disconnect () {
		clients.remove ( this );
		Account.getLogins ( ).remove ( account );
		System.out.println ( "client disconnected!!!" );
	}
}