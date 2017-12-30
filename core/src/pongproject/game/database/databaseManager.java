package pongproject.game.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import pongproject.game.tests.eventLogger;
import x.xyz;




public class databaseManager {

	private Connection conn; 

	private String accountUsername; //The name of the user.
	

	
	
	private PreparedStatement insertStatement;
	private String insertQuery;
	
	
	private PreparedStatement checkLoginStatement;
	private String checkLoginQuery;
	private ResultSet checkLoginRS;
	
	private PreparedStatement insertScoreStatement;
	private String insertScoreQuery;
	
	
	private PreparedStatement returnScoresStatement;
	private String returnScoresQuery;
	
	
	private PreparedStatement playerScoreStatement;
	private String playerScoreQuery;
	
	private PreparedStatement winRatioStatement;
	private String winRatioQuery;
	
	
	private boolean check;
	private boolean insert;
	private boolean scoreInsert;
	private boolean highScoresRetrieved;
	private boolean playerScoresRetrieved;
	private boolean winRatioRetrieved;
	
	
	
	
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
		 scoreInsert = false;
		 highScoresRetrieved = false;
		 playerScoresRetrieved = false;
		 winRatioRetrieved = false;
		
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
				eventLogger.existingUser();
				accountUsername = username;
				return true;
			}
			else {
				eventLogger.usernameExists();
				return false;
			}
		}
		
		
		eventLogger.newUser();
		addAccount(username, password);
		
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
		
		insert = true;
	}
	
	
	//have to change database to match this now, maybe use indexing
	
	public void enterScore(String userName, String date, String result, int score ) throws SQLException {
		
		insertScoreQuery = "insert into pong_scores (username, date, result, score) values (?,?,?,?)";
		insertScoreStatement = conn.prepareStatement(insertScoreQuery);
		insertScoreStatement.setString(1, userName);
		insertScoreStatement.setString(2, date);
		insertScoreStatement.setString(3,result);
		insertScoreStatement.setInt(4, score);
		
		insertScoreStatement.executeUpdate();
		eventLogger.scoreEntered();
		scoreInsert = true;
	}
	
	

	
	
	public ResultSet highScores() throws SQLException {
		
		
		returnScoresQuery = "select * from pong_scores order by score desc limit 50";
		returnScoresStatement = conn.prepareStatement(returnScoresQuery);
	
		
		

		eventLogger.highScoresLoaded();
		highScoresRetrieved = true;
		
		
		return returnScoresStatement.executeQuery();
		
	
		
	}
	
	
	public ResultSet playerScores(String player) throws SQLException {
		
		playerScoreQuery = "select * from pong_scores where username = ? order by score desc limit 50";
		playerScoreStatement = conn.prepareStatement(playerScoreQuery);
		playerScoreStatement.setString(1, player);
		
		eventLogger.playerScoresLoaded();
		playerScoresRetrieved = true;
		
		return playerScoreStatement.executeQuery();
	}
	
	public ResultSet winPercentage(String player) throws SQLException {
		
		winRatioQuery = "select sum(result = 'win')/count(*) from pong_scores where username = ?";
		winRatioStatement = conn.prepareStatement(winRatioQuery);
		winRatioStatement.setString(1, player);
		
		
		
		eventLogger.winRatioLoaded();
		winRatioRetrieved = true;
		
		return winRatioStatement.executeQuery();
	}
	
	
	public void closeConnection() throws SQLException {
		if(insert) {
			insertStatement.close();
		}
		if(check) {
			checkLoginStatement.close();
			checkLoginRS.close();
		}
		if(scoreInsert) {
			insertScoreStatement.close();
		}
		if(highScoresRetrieved) {
			returnScoresStatement.close();
		}
		if(playerScoresRetrieved) {
			playerScoreStatement.close();
		}
		if(winRatioRetrieved) {
			winRatioStatement.close();
		}
		conn.close();
	}
	
	
	
	
	public String getAccountUsername() {
		return accountUsername;
	}
}


