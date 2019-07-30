package Server;

import Account.Account;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
	public static void main(String[] args){
		Account.DBTest ();
		new Thread(() -> {
			try {
				ServerSocket serverSocket = new ServerSocket(8888);
				while (true) {
					Client client = new Client(serverSocket.accept());
					new Thread(client).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
