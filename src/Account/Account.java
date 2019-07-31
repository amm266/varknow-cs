package Account;

import Server.DataBase;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Account {
	private static ArrayList<Account> accounts = new ArrayList<Account> ( );
	private static ArrayList<Account> logins = new ArrayList<Account> ( );
	private String userName;
	private String password;
	private int score;
	private static DataBase dataBase;


	public static void DBTest () {
		try {
			dataBase = new DataBase ( );
			accounts = dataBase.loadAccounts ( );
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace ( );
		}
	}

	public static Account newAccount ( String userName , String password , int score , boolean load ) throws SQLException {
		for ( Account account : accounts ) {
			if ( account.userName.equals ( userName ) )
				return null;
		}
		Account account = new Account ( userName , password , score );
		if ( ! load ) {
			dataBase.setAccounts ( account );
			logins.add ( account );
		}
		accounts.add ( account );
		return account;
	}

	private Account ( String userName , String password , int score ) {
		this.userName = userName;
		this.password = password;
		this.score = score;
	}

	public String getPassword () {
		return password;
	}

	public int getScore () {
		return score;
	}

	public static Account login ( String userName , String password ) {
		for ( Account account : accounts ) {
			if ( account.userName.equals ( userName ) ) {
				if ( logins.contains ( account ) ){
					return null;
				}
				if ( account.password.equals ( password ) ) {
					logins.add ( account );
					return account;
				} else {
					return null;
				}
			}
		}
		return null;
	}

	public String getUserName () {
		return userName;
	}

	public static ArrayList<Account> getLogins () {
		return logins;
	}

	public static void deleteAccount ( String userName , String password ) {
		Account account = login ( userName , password );
		if ( account != null ) {
			try {
				dataBase.delAccount ( account );
			} catch (SQLException e) {
				e.printStackTrace ( );
			}
		}
	}

	public static void updateAccount ( String userName , String password , String newPass ) {
		Account account = login ( userName , password );
		if ( account != null ) {
			try {
				dataBase.updateAccount ( account , newPass );
			} catch (SQLException e) {
				e.printStackTrace ( );
			}
		}
	}

	public void increaseScore ( int add ) {
		score += add;
	}
}
