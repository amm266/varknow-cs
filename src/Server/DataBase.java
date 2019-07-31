package Server;
import Account.Account;
import game.engine.Tir;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
	private static String url ="jdbc:mysql://localhost:3306/chickengamesave" ;
	private static String name = "root";
	private static String pass = "admin";
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	public DataBase() throws ClassNotFoundException, SQLException {
		Class.forName ( "com.mysql.cj.jdbc.Driver" );
		connection = DriverManager.getConnection (url,name,pass );
		statement = connection.createStatement ();
	}
	public void executeForInsert ( String exe) throws SQLException {
		statement.executeUpdate ( exe );
//		resultSet.next ();
	}
	public void executeForLoad(String exe) throws SQLException {
		resultSet = statement.executeQuery ( exe );

	}
	public void saveTirs( ArrayList<Tir> tits,int id) throws SQLException {
		String exe = "INSERT INTO Tirs(ID,x,y,vx,vy) VALUES ";
		for ( Tir tir:tits ){
			executeForInsert ( exe+createTir ( tir,id ) );
		}
		System.out.println ("finish save in DB" );
	}
	public ArrayList<Tir> loadTirs() throws SQLException {
		ArrayList<Tir> tirs = new ArrayList<> (  );
		String exe = "SELECT * FROM tirs";
		ResultSet resultSet = statement.executeQuery ( exe );
		while ( resultSet.next () ){
			Tir tir = new Tir ( resultSet.getDouble ( "x" )
					,resultSet.getDouble ( "y" ),
					resultSet.getDouble ( "vx" ),resultSet.getDouble ( "vy" ) );
			tirs.add ( tir );
		}
		return tirs;
	}
	public void updateAccount(Account account,String newPass) throws SQLException {
		String exe = "update accounts\n" +
				"SET password = '"+newPass+"'\n" +
				"WHERE username = '"+account.getUserName ()+"'";
		executeForInsert ( exe );
	}
	private String createTir(Tir tir,int id){
		String out = "(" +id+","+tir.getX ()+","+tir.getY ()+","+tir.getVx ()+","+tir.getVy ()+")";
		return out;
	}
	public void setAccounts( ArrayList<Account> accounts ) throws SQLException {
		String exe = "INSERT INTO accounts(username,password,score) VALUES ";
		for ( Account account:accounts ){
			executeForInsert ( exe+createAccount ( account ) );
		}
		System.out.println ("finish save in DB" );
	}
	public ArrayList<Account> loadAccounts() throws SQLException{
		ArrayList<Account> accounts = new ArrayList<> (  );
		String exe = "SELECT * FROM accounts";
		resultSet = statement.executeQuery ( exe );
		while ( resultSet.next () ){
			Account account = Account.newAccount ( resultSet.getString ( "username" )
					,resultSet.getString ( "password" ),
					resultSet.getInt ( "score" ),true );
			accounts.add ( account );
		}
		return accounts;
	}
	public void delAccount(Account account) throws SQLException {
		String exe = "DELETE FROM accounts WHERE username = '"+account.getUserName ()+"';";
		executeForInsert ( exe );
	}
	private String createAccount(Account account){
		String out = "('" +account.getUserName ()+"','"+account.getPassword ()+"',"+account.getScore ()+")";
		return out;
	}

	public void setAccounts ( Account account ) throws SQLException {
		String exe = "INSERT INTO accounts(username,password,score) VALUES ";
			executeForInsert ( exe+createAccount ( account ) );
		System.out.println ("finish save in DB" );
	}
}
