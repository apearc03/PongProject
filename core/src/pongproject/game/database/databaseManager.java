package pongproject.game.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import pongproject.game.tests.eventLogger;
import x.xyz;

/**
 * 
 * Class for all database connectivity
 * 
 * @author Alex Pearce
 *
 */



public class databaseManager {

	//Connection instance declared
	private Connection conn; 

	
	
	private String accountUsername; 
	

	
	//Statements and Strings representing queries 
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
	
	//Booleans to check which database queries have been executed
	
	private boolean check;
	private boolean insert;
	private boolean scoreInsert;
	private boolean highScoresRetrieved;
	private boolean playerScoresRetrieved;
	private boolean winRatioRetrieved;
	
	
	
	
	//Variables to obfuscate database login details
	private xyz zxy = new xyz();
	private byte[] z;
	private byte[] x;
	private byte[] y;
	 
	private String i;
	private String o;
	private String u;
	
	
	
	/**
	 * Constructor initializes required variables
	 * 
	 * @throws SQLException
	 */
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
		
		 DriverManager.setLoginTimeout(2); //Sets the amount of time before the DriverManager stops trying to connect. In this case. Two seconds.
		 
	}
	
	
	
	/**
	 * 
	 * Attempts to make a connection to the database
	 * 
	 * @throws SQLException
	 */
	public void makeConnection() throws SQLException{

		
		conn = DriverManager.getConnection(i, o, u);
		
		    
	}
	
	
	/**
	 * Checks if the connection is still valid. Times out after two seconds
	 * 
	 * @return a boolean representing a valid connection
	 * @throws SQLException
	 */
	public boolean checkConnection() throws SQLException {
		
		return conn.isValid(2);
	}
	
	


	
	/**
	 * 
	 * Checks if the username and password exist within the database
	 * 
	 * @param username
	 * @param password
	 * @return a boolean representing a successful login or not
	 * @throws SQLException
	 */
	public boolean checkLogin(String username, String password) throws SQLException {
		
		
		int pass;
		int passHash = password.hashCode();
		
		
		
		checkLoginQuery = "Select username, password from pong_users where username = ?"; //Query assigned to String 
		checkLoginStatement = conn.prepareStatement(checkLoginQuery);
		checkLoginStatement.setString(1, username);

		checkLoginRS = checkLoginStatement.executeQuery(); //Executes the query
		
		check = true;
		
		
		if(checkLoginRS.first()) {
			pass = checkLoginRS.getInt("password");
			
			if(passHash == pass) { //Existing account, both details are correct
				
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
	
	
	
	/**
	 * 
	 * If the account details entered are not in use then create a new account.
	 * 
	 * @param username
	 * @param password
	 * @throws SQLException
	 */
	private void addAccount(String username, String password ) throws SQLException {
		
		
		insertQuery = "insert into pong_users (username, password) values (?,?)"; //Inserts the new username and password into the database.
		insertStatement = conn.prepareStatement(insertQuery);
		insertStatement.setString(1, username);
		insertStatement.setInt(2, password.hashCode());
		
		insertStatement.executeUpdate();
		
		insert = true;
	}
	
	
	
	/**
	 * 
	 * Method to enter the score into the database. Uses an insert query.
	 * 
	 * @param userName
	 * @param date
	 * @param result
	 * @param score
	 * @throws SQLException
	 */
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
	
	

	
	/**
	 * Method to return the top 50 high scores.
	 * 
	 * @return a ResultSet containing the SQL query records
	 * @throws SQLException
	 */
	public ResultSet highScores() throws SQLException {
		
		
		returnScoresQuery = "select * from pong_scores order by score desc limit 50";
		returnScoresStatement = conn.prepareStatement(returnScoresQuery);


		eventLogger.highScoresLoaded();
		highScoresRetrieved = true;
		
		
		return returnScoresStatement.executeQuery();
		
	
		
	}
	
	/**
	 * Gets all the highScores of the logged in player.
	 * 
	 * @param player
	 * @return a ResultSet containing the player high scores
	 * @throws SQLException
	 */
	public ResultSet playerScores(String player) throws SQLException {
		
		playerScoreQuery = "select * from pong_scores where username = ? order by score desc limit 50";
		playerScoreStatement = conn.prepareStatement(playerScoreQuery);
		playerScoreStatement.setString(1, player);
		
		eventLogger.playerScoresLoaded();
		playerScoresRetrieved = true;
		
		return playerScoreStatement.executeQuery();
	}
	
	/**
	 * 
	 * Gets the logged in player's win percentage
	 * 
	 * @param player
	 * @return A ResultSet containing the player win percentage
	 * @throws SQLException
	 */
	public ResultSet winPercentage(String player) throws SQLException {
		
		winRatioQuery = "select sum(result = 'win')/count(*) from pong_scores where username = ?";
		winRatioStatement = conn.prepareStatement(winRatioQuery);
		winRatioStatement.setString(1, player);
		
		eventLogger.winRatioLoaded();
		winRatioRetrieved = true;
		
		return winRatioStatement.executeQuery();
	}
	
	/**
	 * 
	 * Closes all the statements used though out the application life cycle as well as the connection
	 * 
	 * @throws SQLException
	 */
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
	
	
	
	/**
	 * Getter for the account username
	 */
	public String getAccountUsername() {
		return accountUsername;
	}
}


