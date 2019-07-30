package Server;
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
		Class.forName ( "com.mysql.jdbc.Driver" );
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
		resultSet = statement.executeQuery ( exe );
		while ( resultSet.next () ){
			Tir tir = new Tir ( resultSet.getDouble ( "x" )
					,resultSet.getDouble ( "y" ),
					resultSet.getDouble ( "vx" ),resultSet.getDouble ( "vy" ) );
			tirs.add ( tir );
		}
		return tirs;
	}
	private String createTir(Tir tir,int id){
		String out = "(" +id+","+tir.getX ()+","+tir.getY ()+","+tir.getVx ()+","+tir.getVy ()+")";
		return out;
	}
}
