package pongproject.game.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;



import x.xyz;




public class databaseManager {

	private Connection conn; 
	//private MysqlDataSource data;
	private String accountUsername;
	private Statement checkLoginStatement;
	private String checkLoginQuery;
	private ResultSet checkLoginRS;
	
	private PreparedStatement insertStatement;
	private String insertQuery;
	
	
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
		
	}
	
	
	
	
	public void makeConnection() throws SQLException {

		
		conn = DriverManager.getConnection(i, o, u);

		

		    
	}
	
	
	public boolean checkConnection() throws SQLException {
		
		return conn.isValid(1);
	}
	
	
	
	public boolean checkLogin(String username, String password) throws SQLException {
		String user;
		int pass;
		
		int passHash = password.hashCode();
		checkLoginStatement = conn.createStatement();
		checkLoginQuery = "Select * from pong_users";
		checkLoginRS = checkLoginStatement.executeQuery(checkLoginQuery);
		check = true;
		
		
		while(checkLoginRS.next()){
			user = checkLoginRS.getString("username");
			pass = checkLoginRS.getInt("password");
			
			if(username.equals(user)) {
				
				if(passHash == pass) {
					System.out.println("Existing user");
					accountUsername = username;
					return true;
				}
				
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
	
	public void enterScore(String userName, int score) {
		
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


