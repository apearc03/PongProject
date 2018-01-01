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

public class GameController {

	private Pong pongGame;
	private GameScreen screen;
	private PlayerPaddle playerPadd;
	private ComputerPaddle computerPadd;
	private Ball ball;

	private float ballInterSect;
	private float Normalized;
	
	private int gameScore; 
	private DateFormat dateFormat;
	private DateFormat timeFormat;
	private Date date;
	
	
	private Sound wallHit;
	
	
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
		
		gameScore = 2000;
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

				ball.setVelocity(ball.getxVelocity()*-1, (Normalized*3.5f)*ball.getxVelocity()/2);
				
				
				if(!ball.getLastHitPlayer()) {
					
					playerPadd.hitSound().play(pongGame.getButtonVolume());
					
					gameScore+=5;
					eventLogger.updateScore(5);
				}
				
				
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
			if(ball.getLastHitPlayer()) {
				
				computerPadd.hitSound().play(pongGame.getButtonVolume());
				
				if(gameScore > 10) {
					gameScore -= 10;
					eventLogger.updateScore(-10);
				}
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
				eventLogger.updateScore(150);
				
				try {
					
					if(!checkForWinner(playerPadd)) {
						playerPadd.scoreSound().play(pongGame.getButtonVolume());
					}
					
				} catch (SQLException e) {
					screen.getScoreStored().setPosition(pongGame.getAppWidth()/2-230, pongGame.getAppHeight()-270);
					screen.setScoreStored("There is no connection to the database so your score could not be stored");
				}
				
				
			}
			
			

			if(ball.getX() + ball.getBallSprite().getWidth()>= pongGame.getAppWidth()) {
				//ball.setVelocity(ball.getxVelocity()*-1, ball.getyVelocity());
				//computer scores
				computerPadd.incrementScore();
				eventLogger.computerScored();
				//for scoring
				if(gameScore > 200) {
					gameScore -= 200;
					eventLogger.updateScore(-200);
				}
				
				
				try {
					
					if(!checkForWinner(computerPadd)) {
						computerPadd.scoreSound().play(pongGame.getButtonVolume());
					}
					
				} catch (SQLException e) {
					screen.getScoreStored().setPosition(pongGame.getAppWidth()/2-230, pongGame.getAppHeight()-270);
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
			
				wallHit.play(0.1f);
			}
			
			

			if(ball.getY() + ball.getBallSprite().getHeight()> pongGame.getAppHeight()) {
				
				ball.updatePosition(0, -5);
				ball.setVelocity(ball.getxVelocity(), ball.getyVelocity()*-1);
				
				wallHit.play(pongGame.getButtonVolume());
			}
			
	}
		
		
	private boolean checkForWinner(Paddle pad) throws SQLException {
			
			if(pad.getScore()==2) {//change to 3 after
						resetGame();
						
						screen.setWinnerText("The winner is " + pad.getName());
					
						pad.victorySound().play(pongGame.getButtonVolume());
						
						
					
						
						if(pongGame.getFirstConnection()) {
							
							pongGame.getData().checkConnection();
							screen.getScoreStored().setPosition(pongGame.getAppWidth()/2-165, pongGame.getAppHeight()-200);
							
							gameScore -= Math.round(computerPadd.getDifficulty())*50; //Adjusts score dependent on difficulty
							
							screen.setScoreStored("Your score of " + gameScore + " has been successfully stored");
							
							date = new Date();
							
							//Update database tables with indexes
							
									
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
