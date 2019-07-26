package Server;

import Box.Box;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import game.engine.Rocket;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class Client implements Runnable{
	private static ArrayList<Client> clients = new ArrayList<>();
	private Socket socket;
	private Mudle mudle;
	private static YaGson yaGson;
	private Scanner scanner;
	private Formatter formatter;
	private Rocket rocket;
	static {
		YaGsonBuilder yaGsonBuilder = new YaGsonBuilder ();
		yaGson = yaGsonBuilder.create ();
	}
	public Client(Socket socket) {
		this.socket = socket;
		clients.add(this);
		try {
			scanner = new Scanner(socket.getInputStream());
			formatter = new Formatter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run () {
		clients.add ( this );
		while ( true ){
			//get from client
			Box box = recieve ();
			//process
			Box answer = new Box ();
			switch ( box.getAsk ()){
				case state:
					box.setStage ( Mudle.stage );
					break;
				case move:
					mudle.move ();
					break;
				case fire:
					mudle.fire ( rocket );
					break;
				case chick:
					mudle.chick ();
					break;
				case setLocation:
					rocket.setLocation ( box.getX (),box.getY ());
					break;
			}
			//send to client
			send ( box );
		}
	}
	private void send( Box box ){
		String out = yaGson.toJson ( box );
		formatter.format(out + "\n");
		formatter.flush();
	}
	public Box recieve() {
		String get = "";
		while (true) {
			if (scanner.hasNextLine()) {
				get = scanner.nextLine();
				break;
			}
		}
		Box box = yaGson.fromJson(get, Box.class);
		return box;
	}
}
