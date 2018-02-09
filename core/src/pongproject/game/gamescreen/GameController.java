package pongproject.game.gamescreen;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;

import pongproject.game.Pong;
import pongproject.game.tests.eventLogger;
/**
 * 
 * @author Alex Pearce
 * 
 * Class to control game objects before rendering to the screen.
 *
 */
public class GameController {

	
	//Pong and GameScreen instance.
	private Pong pongGame;
	private GameScreen screen;
	
	//Game objects
	private PlayerPaddle playerPadd;
	private ComputerPaddle computerPadd;
	private Ball ball;

	//Variables used for paddle hits
	private float ballInterSect;
	private float Normalized;
	
	//Variables used for game scoring
	private int gameScore; 
	private DateFormat dateFormat;
	private DateFormat timeFormat;
	private Date date;
	
	//Sound for wall hit event
	private Sound wallHit;
	
	/**
	 * 
	 * Constructor assigns all instance and variables. 
	 * 
	 * @param Game
	 * @param gameScreen
	 */
	public GameController(final Pong Game, GameScreen gameScreen) {
		pongGame = Game;
		screen = gameScreen;
		ball = new Ball();
		playerPadd = new PlayerPaddle(Keys.UP,Keys.DOWN);
		computerPadd = new ComputerPaddle(ball);
		
		dateFormat = DateFormat.getDateInstance(3, Locale.UK);
		timeFormat = DateFormat.getTimeInstance(2, Locale.UK);
		
		gameScore = 2000;

		wallHit = Gdx.audio.newSound(Gdx.files.internal("wallHit.mp3"));
		
	}
	
	
	
	
	/**
	 * Update is called within the render method of the game screen. It checks for all event conditions. Every game object position is then updated
	 */
	public void update() {
		
		
		
		
		computerPadd.checkOutOfBounds();
		playerPadd.movePaddle();
		playerPadd.checkOutOfBounds();
		
		//ball stuff
		
		checkYOutOfBounds();
		checkXOutOfBounds();
		checkForPlayerCollision();
		checkForCPCollision();
		ball.increaseXVeloverTime();
		
		
		ball.updatePosition(getBall().getxVelocity(),getBall().getyVelocity());
		playerPadd.updatePosition(getPlayerPadd().getyVelocity());
		computerPadd.movePaddleToBall();
		
	


	}
	
	/**
	 * Starts the ball movement to begin the game
	 */
	public void startGame() {
		
		ball.startBallMovement();
		
	}
	
	
	/**
	 * Getter method for the player paddle
	 */
	public PlayerPaddle getPlayerPadd() {
		return playerPadd;
	}
	
	/**
	 * Getter method for the computer paddle
	 */
	public ComputerPaddle getComputerPadd() {
		return computerPadd;
	}
	
	/**
	 * Getter method for the ball
	 * 
	 */
	public Ball getBall() {
		return ball;
	}
	
	/**
	 * Resets the game objects.
	 * 
	 */
	public void resetGame() {
		computerPadd.resetPaddle();
		playerPadd.resetPaddle();

		ball.setLastHitWasPLayer(false);
		ball.resetBall();
		
	}
	
	/**
	 * Resets the scores
	 * 
	 */
	public void resetScores() {
		computerPadd.resetScore();
		playerPadd.resetScore();
		
		gameScore = 2000;
	}
	
	/**
	 * 
	 * Sets the Y velocity of the paddles to 0
	 */
	public void zeroPadVelocity() {
		computerPadd.setyVelocity(0);
		playerPadd.setyVelocity(0);
	}
	
	/**
	 * 
	 * Method that checks for a player collision with the ball
	 * 
	 */
	public void checkForPlayerCollision() {
		
		if(ball.overlaps(playerPadd)) { //Uses the overlaps method to check if the two objects share a coordinate
			
				//Formulas to calculate the normalized collision coordinate
				ballInterSect = (playerPadd.getY()+(playerPadd.getHeight()/2))-(ball.getY()+ball.getHeight()/2);
				Normalized = (ballInterSect/playerPadd.getHeight())*-1;					
		
			
				if(ball.getLastHitPlayer()) { //Used to fix bug that prevents the ball from getting stuck in the paddle during a collision with the edges.
					
					if(Normalized > 0) {
						ball.updatePosition(-5, 5);
					}
					else {
						ball.updatePosition(-5, -5);
					}
					
				}

				eventLogger.playerPaddleCollision();

				ball.setVelocity(ball.getxVelocity()*-1, (Normalized*3.5f)*ball.getxVelocity()/2); //Adjust the velocity dependent on the ball collision coordinate
				
				//Plays impact sound and adjusts score
				if(!ball.getLastHitPlayer()) {
					
					playerPadd.hitSound().play(pongGame.getGlobalVolume());
					
					gameScore+=5;
					eventLogger.updateScore(5);
				}
				
				
				ball.setLastHitWasPLayer(true);
				
			
		
				
		}
		
		
	}
	
	/**
	 * Method to check for a computer paddle collision
	 * 
	 */
	public void checkForCPCollision() {
		
		if(ball.overlaps(computerPadd)) {
			
			ballInterSect = (computerPadd.getY()+(computerPadd.getHeight()/2))-(ball.getY()+ball.getHeight()/2);
			
			Normalized = (ballInterSect/computerPadd.getHeight())*-1; //Calculates normalized collision coordinate
	
		
			if(!ball.getLastHitPlayer()) { //Fixes ball glitch on sides of paddle
				
				
				
				if(Normalized > 0) {
					ball.updatePosition(5, 5);
				}
				else {
					ball.updatePosition(5, -5);
				}

			}
			
			eventLogger.computerPaddleCollision();


			
			ball.setVelocity(ball.getxVelocity()*-1, (Normalized*3.5f)*-ball.getxVelocity()/2);
			
			//Plays impact sound and adjusts score
			if(ball.getLastHitPlayer()) {
				
				computerPadd.hitSound().play(pongGame.getGlobalVolume());
				
				if(gameScore > 10) {
					gameScore -= 10;
					eventLogger.updateScore(-10);
				}
			}
			
			
			ball.setLastHitWasPLayer(false);
			
		
		}

		
	}
	
	
	
	/**
	 * 
	 * Checks if the ball is outside of the game screen on the X axis
	 * 
	 */
	public void checkXOutOfBounds() {
			

			if(ball.getX() <= 0) { //Player scores
				
				playerPadd.incrementScore();
				eventLogger.playerScored();
				gameScore += 150;
				eventLogger.updateScore(150);
				
				try { //Checks if the score has reached the winning value
					
					if(!checkForWinner(playerPadd)) {
						playerPadd.scoreSound().play(pongGame.getGlobalVolume());
					}
					
				} catch (SQLException e) {
					screen.getScoreStored().setPosition(pongGame.getAppWidth()/2-230, pongGame.getAppHeight()-270);
					screen.setScoreStored("There is no connection to the database so your score could not be stored");
				}
				
				
			}
			
			

			if(ball.getX() + ball.getBallSprite().getWidth()>= pongGame.getAppWidth()) { //Computer scores
			
				
				computerPadd.incrementScore();
				eventLogger.computerScored();
			
				if(gameScore > 200) {
					gameScore -= 200;
					eventLogger.updateScore(-200);
				}
				
				//Checks if the computer has won
				try {
					
					if(!checkForWinner(computerPadd)) {
						computerPadd.scoreSound().play(pongGame.getGlobalVolume());
					}
					
				} catch (SQLException e) {
					screen.getScoreStored().setPosition(pongGame.getAppWidth()/2-230, pongGame.getAppHeight()-270);
					screen.setScoreStored("There is no connection to the database so your score could not be stored");
					screen.getPlayAgainButton().setVisible(true);
					screen.getMenuButton().setVisible(true);
					
				}
				
				
			}
			
		}
		
		
		
		
	/**
	 * Checks if the ball is out of bounds on the Y axis
	 * 	
	 */
	public void checkYOutOfBounds() {
			
			
			//Checks if the ball has reached the bottom of the screen.
			if(ball.getY() < 0) {
				
				ball.updatePosition(0, 5);
				ball.setVelocity(ball.getxVelocity(), ball.getyVelocity()*-1);
			
				wallHit.play(pongGame.getGlobalVolume());
			}
			
			
			//Checks if the ball has reached the top of the screen.	
			if(ball.getY() + ball.getBallSprite().getHeight()> pongGame.getAppHeight()) {
				
				ball.updatePosition(0, -5);
				ball.setVelocity(ball.getxVelocity(), ball.getyVelocity()*-1);
				
				wallHit.play(pongGame.getGlobalVolume());
			}
			
	}
		
	/**
	 * Called everytime a paddle scores. Checks if their score is 2 and if so executes winning code. 	
	 * @param pad
	 * @return a boolean representing a winner or not
	 * @throws SQLException
	 */
	private boolean checkForWinner(Paddle pad) throws SQLException {
			
			if(pad.getScore()==2) { 
						resetGame(); //Resets the game state
						
						screen.setWinnerText("The winner is " + pad.getName());
					
						pad.victorySound().play(pongGame.getGlobalVolume()); //Plays the winning sound of the winning paddle
						
						
						
						
						if(pongGame.getFirstConnection()) {  //If there was a connection established attempts to add score to the database.
							
							//pongGame.getData().checkConnection(); 
							screen.getScoreStored().setPosition(pongGame.getAppWidth()/2-165, pongGame.getAppHeight()-200);
							
							gameScore -= Math.round(computerPadd.getDifficulty())*50; //Adjusts score dependent on difficulty
							
							screen.setScoreStored("Your score of " + gameScore + " has been successfully stored");
							
							date = new Date();
							
							
									
									//Checks which paddle won to determine result
									if(pad.getClass().equals(playerPadd.getClass())) {
										
										eventLogger.playerWon();
										pongGame.getData().enterScore(pongGame.getData().getAccountUsername(), dateFormat.format(date)+"    "+timeFormat.format(date), "Win", gameScore);
									}
									else {
										
										eventLogger.computerWon();
										pongGame.getData().enterScore(pongGame.getData().getAccountUsername(), dateFormat.format(date)+"    "+timeFormat.format(date), "Loss", gameScore);
									}
									
						
						}
						else {
							screen.getScoreStored().setPosition(pongGame.getAppWidth()/2-230, pongGame.getAppHeight()-200);
							screen.setScoreStored("There is no connection to the database so your score could not be stored");
							screen.getPlayAgainButton().setVisible(true);
							screen.getMenuButton().setVisible(true);
							
						}
						
				
					
				
				
					
					
					screen.getPlayAgainButton().setVisible(true);
					screen.getMenuButton().setVisible(true);
					
					return true;
			
			}
			else {
				resetGame();
				ball.startBallMovement();
				return false;
			}
			
	}

	/**
	 * 
	 * Called when the game exits to release any resources
	 * 
	 */
	public void disposeGameSounds() {
		
		playerPadd.hitSound().dispose();
		playerPadd.scoreSound().dispose();
		playerPadd.victorySound().dispose();
		computerPadd.hitSound().dispose();
		computerPadd.scoreSound().dispose();
		computerPadd.victorySound().dispose();
		wallHit.dispose();
		
	}
	
}
