package pongproject.game.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Base64;
import x.xyz;




public class databaseManager {

	private Connection conn; 
	
	//Bad practice to include database details in source but here is my attempt at obfuscating login details
	private xyz zxy = new xyz();
	private byte[] z;
	private byte[] x;
	private byte[] y;
	 
	private String i;
	private String o;
	private String u;

	
	public databaseManager() {
		 z = Base64.getDecoder().decode(zxy.x());
		 x = Base64.getDecoder().decode(zxy.y());
		 y = Base64.getDecoder().decode(zxy.z());
		 i = new String(y);
		 o = new String(x);
		 u = new String(z);
		 
		
	}
	
	
	
	public void makeConnection() throws SQLException {


		
		conn = DriverManager.getConnection(i, o, u);
		

		

		    
	}
	
	
	public boolean checkConnection() throws SQLException {
		
		return conn.isValid(1);
	}
	
	
	
	public boolean checkLogin(String userName, String Password) {
		/*
		 * Loops through username column first then password, 
		if username is taken and password matches, return true. "Successful login". Make buttons available.
		If user name is taken and password doesnt match, return error message. "Account name taken". Clear textfields. return false;
		If username is not taken and a password is not empty, return true. "Sucessfully registered".
		If username is empty return false
		
		*/
		
		
		
		return false;
	}

	
	public void enterScore(String userName, int score) {
		
	}
	
	
	public void closeConnection() throws SQLException {
		conn.close();
	}
	
}


