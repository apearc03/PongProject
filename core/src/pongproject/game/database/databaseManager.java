package pongproject.game.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import x.xyz;




public class databaseManager {

	private Connection conn; 

	private String accountUsername; //The name of the user.
	

	
	
	private PreparedStatement insertStatement;
	private String insertQuery;
	
	
	private PreparedStatement checkLoginStatement;
	private String checkLoginQuery;
	private ResultSet checkLoginRS;
	
	
	private boolean check;
	private boolean insert;
	
	
	//Bad practice to include database details in source but here is my attempt at obfuscating login details
	private xyz zxy = new xyz();
	private byte[] z;
	private byte[] x;
	private byte[] y;
	 
	private String i;
	private String o;
	private String u;

	
	public databaseManager() throws SQLException{

		z = Base64.getDecoder().decode(zxy.x());
		 x = Base64.getDecoder().decode(zxy.y());
		 y = Base64.getDecoder().decode(zxy.z());
		 i = new String(y);
		 o = new String(x);
		 u = new String(z);
		 

		
		 
		 check = false;
		 insert = false;
		
		 DriverManager.setLoginTimeout(2);
	}
	
	
	
	
	public void makeConnection() throws SQLException{

		
		conn = DriverManager.getConnection(i, o, u);

		

		    
	}
	
	
	public boolean checkConnection() throws SQLException {
		
		return conn.isValid(1);
	}
	
	


	
	
	public boolean checkLogin(String username, String password) throws SQLException {
		
		
		int pass;
		int passHash = password.hashCode();
		
		
		
		checkLoginQuery = "Select username, password from pong_users where username = ?";
		checkLoginStatement = conn.prepareStatement(checkLoginQuery);
		checkLoginStatement.setString(1, username);

		checkLoginRS = checkLoginStatement.executeQuery();
		
		check = true;
		
		
		if(checkLoginRS.first()) {
			pass = checkLoginRS.getInt("password");
			
			if(passHash == pass) {
				//existing account, both details correct
				System.out.println("Existing user");
				accountUsername = username;
				return true;
			}
			else {
				System.out.println("Username matches but not password, an error message has been returned");
				return false;
			}
		}
		
		
		
		addAccount(username, password);
		System.out.println("New user");
		accountUsername = username;
		return true;
	}
	
	
	
	
	private void addAccount(String username, String password ) throws SQLException {
		
		//If validated insert the username and password into the pong_user database
		insertQuery = "insert into pong_users (username, password) values (?,?)";
		insertStatement = conn.prepareStatement(insertQuery);
		insertStatement.setString(1, username);
		insertStatement.setInt(2, password.hashCode());
		
		insertStatement.executeUpdate();
		System.out.println("Values inserted");
		insert = true;
	}
	
	
	//to do next
	public void enterScore(String userName, String date, String result, int score ) {
		
	}
	
	
	public void closeConnection() throws SQLException {
		if(insert) {
			insertStatement.close();
		}
		if(check) {
			checkLoginStatement.close();
			checkLoginRS.close();
		}
		conn.close();
	}
	
	
	public String getAccountUsername() {
		return accountUsername;
	}
}


