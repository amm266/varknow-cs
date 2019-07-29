package Server;
import java.sql.*;
public class DataBase {
	static String url ="jdbc:mysql://localhost:3306/test1" ;
	static String name = "root";
	static String pass = "admin";
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName ( "com.mysql.jdbc.Driver" );
		Connection connection = DriverManager.getConnection (url,name,pass );
		Statement statement = connection.createStatement ();
	}
}
