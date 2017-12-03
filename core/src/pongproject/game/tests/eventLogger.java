package pongproject.game.tests;

public abstract class eventLogger {

	
	private eventLogger() {
		
	}
	
	
	public static void menuScreen() {
		System.out.println("Menu screen");
	}
	public static void loginScreen() {
		System.out.println("login screen");
	}
	public static void highScoreScreen() {
		System.out.println("High Score screen");
	}
	public static void gameScreen() {
		System.out.println("Game screen");
	}
	public static void gameStarted() {
		System.out.println("Game started");
	}
	public static void playerPaddleCollision() {
		System.out.println("Player paddle hit");
	}
	public static void computerPaddleCollision() {
		System.out.println("Computer paddle hit");
	}
	public static void playerScored() {
		System.out.println("Player scored");
	}
	public static void computerScored() {
		System.out.println("Computer scored");	
	}
	public static void playerWon() {
		System.out.println("Player won the game");
	}
	public static void computerWon() {
		System.out.println("Computer won the game");
	}
	public static void newUser() {
		System.out.println("New user account added");
	}
	public static void existingUser() {
		System.out.println("Existing user account");
	}
	public static void loginSuccess() {
		System.out.println("Login Success");
	}
	public static void loginFailed() {
		System.out.println("Login Failed");
	}
	public static void scoreEntered() {
		System.out.println("Score entered into database");
	}
	public static void databaseConnectionMade() {
		System.out.println("Database connection made");
	}
	public static void databaseConnectionFailed() {
		System.out.println("Datbase connection failed");
	}
	public static void usernameExists() {
		System.out.println("User tried to login with a user that already exists. Could be the wrong password or new user.");
	}
	
}
