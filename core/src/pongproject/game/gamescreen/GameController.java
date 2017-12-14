package pongproject.game.gamescreen;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import com.badlogic.gdx.Input.Keys;

import pongproject.game.Constants;
import pongproject.game.Pong;
import pongproject.game.tests.eventLogger;

public class GameController {

	private Pong game;
	private GameScreen screen;
	private PlayerPaddle playerPadd;
	private ComputerPaddle computerPadd;
	private Ball ball;

	private float ballInterSect;
	private float Normalized;
	
	private int gameScore; //Increment score throughout game somehow, number of paddle hits, score ratio etc. 
	private DateFormat dateFormat;
	private DateFormat timeFormat;
	private Date date;
	
	public GameController(final Pong pongGame, GameScreen gameScreen) {
		game = pongGame;
		screen = gameScreen;
		ball = new Ball();
		playerPadd = new PlayerPaddle(Keys.UP,Keys.DOWN);
		computerPadd = new ComputerPaddle(ball);
		
		dateFormat = DateFormat.getDateInstance(3, Locale.UK);
		timeFormat = DateFormat.getTimeInstance(2, Locale.UK);
		
		gameScore = 1000;
		
	}
	
	
	
	
	
	public void update() {
		
		//added for testing
		//getComputerPadd().moveCPUPaddle();
		
		
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
	
	
	public void startGame() {
		
		ball.startBallMovement();
	}
	
	
	
	public PlayerPaddle getPlayerPadd() {
		return playerPadd;
	}
	
	public ComputerPaddle getComputerPadd() {
		return computerPadd;
	}
	
	public Ball getBall() {
		return ball;
	}
	
	public void resetGame() {
		computerPadd.resetPaddle();
		playerPadd.resetPaddle();

		ball.setLastHitWasPLayer(false);
		ball.resetBall();
		
	}
	
	public void resetScores() {
		computerPadd.resetScore();
		playerPadd.resetScore();
		gameScore = 1000;
	}
	
	public void zeroPadVelocity() {
		computerPadd.setyVelocity(0);
		playerPadd.setyVelocity(0);
	}
	
	
	public void checkForPlayerCollision() {
		
		if(ball.overlaps(playerPadd)) {
			
				ballInterSect = (playerPadd.getY()+(playerPadd.getHeight()/2))-(ball.getY()+ball.getHeight()/2);
				Normalized = (ballInterSect/playerPadd.getHeight())*-1;
		
				//used to fix ball stuck glitch
				if(ball.getLastHitPlayer()) {

					if(Normalized > 0) {
						ball.updatePosition(-5, 5);
					}
					else {
						ball.updatePosition(-5, -5);
					}
					
				}

				eventLogger.playerPaddleCollision();
				//working code
				ball.setVelocity(ball.getxVelocity()*-1, (Normalized*3.5f)*ball.getxVelocity()/2);
				
				
				
				gameScore+=5;
				
				ball.setLastHitWasPLayer(true);
				
			
		
				
		}
		
		
	}
	
	
	public void checkForCPCollision() {
		
		if(ball.overlaps(computerPadd)) {
			
			ballInterSect = (computerPadd.getY()+(computerPadd.getHeight()/2))-(ball.getY()+ball.getHeight()/2);
			
			Normalized = (ballInterSect/computerPadd.getHeight())*-1;
	
			//used to fix ball stuck glitch
			if(!ball.getLastHitPlayer()) {

				if(Normalized > 0) {
					ball.updatePosition(5, 5);
				}
				else {
					ball.updatePosition(5, -5);
				}

			}
			
			eventLogger.computerPaddleCollision();
			//working code
			
			ball.setVelocity(ball.getxVelocity()*-1, (Normalized*3.5f)*-ball.getxVelocity()/2);
			
			//for scoring
			if(gameScore > 10) {
				gameScore -= 10;
				
			}
			
			//Computer hit the ball
			ball.setLastHitWasPLayer(false);
			
		
		}

		
	}
	
	
	
	
	public void checkXOutOfBounds() {
			

			if(ball.getX() <= 0) {
				//ball.setVelocity(ball.getxVelocity()*-1, ball.getyVelocity());
				//player scores
				playerPadd.incrementScore();
				eventLogger.playerScored();
				gameScore += 150;
				
				try {
					checkForWinner(playerPadd);
				} catch (SQLException e) {
					screen.getScoreStored().setPosition(Constants.VIEWPORT_WIDTH/2-230, Constants.VIEWPORT_HEIGHT-270);
					screen.setScoreStored("There is no connection to the database so your score could not be stored");
				}
				
				
			}
			
			

			if(ball.getX() + ball.getBallSprite().getWidth()>= Constants.VIEWPORT_WIDTH) {
				//ball.setVelocity(ball.getxVelocity()*-1, ball.getyVelocity());
				//computer scores
				computerPadd.incrementScore();
				eventLogger.computerScored();
				//for scoring
				if(gameScore > 200) {
					gameScore -= 200;
					
				}
				
				
				try {
					checkForWinner(computerPadd);
				} catch (SQLException e) {
					screen.getScoreStored().setPosition(Constants.VIEWPORT_WIDTH/2-230, Constants.VIEWPORT_HEIGHT-270);
					screen.setScoreStored("There is no connection to the database so your score could not be stored");
					screen.getPlayAgainButton().setVisible(true);
					screen.getMenuButton().setVisible(true);
					
				}
				
				
			}
			
		}
		
		
		
		
		
	public void checkYOutOfBounds() {
			
			
			
			if(ball.getY() < 0) {
				
				ball.updatePosition(0, 5);
				ball.setVelocity(ball.getxVelocity(), ball.getyVelocity()*-1);
			
				
			}
			
			

			if(ball.getY() + ball.getBallSprite().getHeight()> Constants.VIEWPORT_HEIGHT) {
				
				ball.updatePosition(0, -5);
				ball.setVelocity(ball.getxVelocity(), ball.getyVelocity()*-1);
				
				
			}
			
	}
		
		
	public void checkForWinner(Paddle pad) throws SQLException {
			
			if(pad.getScore()==1) {//change to 3 after
						resetGame();
						screen.setWinnerText("The winner is " + pad.getName());
					
					
						
				
					
						
						if(game.getFirstConnection()) {
							
							game.getData().checkConnection();
							screen.getScoreStored().setPosition(Constants.VIEWPORT_WIDTH/2-145, Constants.VIEWPORT_HEIGHT-270);
							screen.setScoreStored("Your score has been successfully stored");
							
							date = new Date();
							
							//Update database tables with indexes
							
									
									if(pad.getClass().equals(playerPadd.getClass())) {
										
										eventLogger.playerWon();
										game.getData().enterScore(game.getData().getAccountUsername(), dateFormat.format(date)+" "+timeFormat.format(date), "Win", gameScore);
									}
									else {
										
										eventLogger.computerWon();
										game.getData().enterScore(game.getData().getAccountUsername(), dateFormat.format(date)+" "+timeFormat.format(date), "Loss", gameScore);
									}
									
						
						}
						else {
							screen.getScoreStored().setPosition(Constants.VIEWPORT_WIDTH/2-230, Constants.VIEWPORT_HEIGHT-270);
							screen.setScoreStored("There is no connection to the database so your score could not be stored");
							screen.getPlayAgainButton().setVisible(true);
							screen.getMenuButton().setVisible(true);
							
						}
						
				
					
				
				
					
					
					screen.getPlayAgainButton().setVisible(true);
					screen.getMenuButton().setVisible(true);
					
					
			
			}
			else {
				resetGame();
				ball.startBallMovement();
			}
			
	}

	
	
}
