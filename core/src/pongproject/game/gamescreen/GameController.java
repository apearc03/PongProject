package pongproject.game.gamescreen;

import com.badlogic.gdx.Input.Keys;

import pongproject.game.Constants;
import pongproject.game.Pong;

public class GameController {

	private Pong game;
	private GameScreen screen;
	private PlayerPaddle playerPadd;
	private ComputerPaddle computerPadd;
	private Ball ball;

	private float ballInterSect;
	private float Normalized;
	
	public GameController(Pong pongGame, GameScreen screen) {
		this.game = pongGame;
		this.screen = screen;
		this.ball = new Ball();
		this.playerPadd = new PlayerPaddle(Keys.UP,Keys.DOWN);
		this.computerPadd = new ComputerPaddle(ball);
		
	}
	
	
	
	
	
	public void update() {
		//added for test
		//getComputerPadd().moveCPUPaddle();
		getComputerPadd().movePaddleToBall();
		//--
		getComputerPadd().checkOutOfBounds();
		getPlayerPadd().movePaddle();
		getPlayerPadd().checkOutOfBounds();
		getBall().checkYOutOfBounds();
		getBall().checkXOutOfBounds();
		checkForPlayerCollision();
		checkForCPCollision();
		
		//disabled for testing
		getBall().increaseXVeloverTime();
	
		
		getBall().updatePosition(getBall().getxVelocity(),getBall().getyVelocity());
		getPlayerPadd().updatePosition(getPlayerPadd().getyVelocity());
		
		
		//disabled
		//getComputerPadd().updatePosition(getComputerPadd().getyVelocity());


	}
	
	
	public void startGame() {
		
		getBall().startBallMovement();
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
		getComputerPadd().resetPaddle();
		getPlayerPadd().resetPaddle();

		getBall().setLastHitWasPLayer(false);
		getBall().resetBall();
		
	}
	
	public void zeroPadVelocity() {
		getComputerPadd().setyVelocity(0);
		getPlayerPadd().setyVelocity(0);
	}
	
	
	public void checkForPlayerCollision() {
		
		if(getBall().overlaps(playerPadd)) {
			
				ballInterSect = (getPlayerPadd().getY()+(getPlayerPadd().getHeight()/2))-(getBall().getY()+getBall().getHeight()/2);
				Normalized = (ballInterSect/getPlayerPadd().getHeight())*-1;
		
				//used to fix ball stuck glitch
				if(getBall().getLastHitPlayer()) {

					if(Normalized > 0) {
						getBall().updatePosition(-5, 5);
					}
					else {
						getBall().updatePosition(-5, -5);
					}
					
				}

				
				//working code
				getBall().setVelocity(getBall().getxVelocity()*-1, (Normalized*3.5f)*getBall().getxVelocity()/2);
				
				
				
			
				getBall().setLastHitWasPLayer(true);
				
			
		
				
		}
		
		
	}
	
	
	public void checkForCPCollision() {
		
		if(getBall().overlaps(computerPadd)) {
			
			ballInterSect = (getComputerPadd().getY()+(getComputerPadd().getHeight()/2))-(getBall().getY()+getBall().getHeight()/2);
			Normalized = (ballInterSect/getComputerPadd().getHeight())*-1;
	
			//used to fix ball stuck glitch
			if(!getBall().getLastHitPlayer()) {

				if(Normalized > 0) {
					getBall().updatePosition(5, 5);
				}
				else {
					getBall().updatePosition(5, -5);
				}

			}
			
			
			//working code
			getBall().setVelocity(getBall().getxVelocity()*-1, (Normalized*3.5f)*-getBall().getxVelocity()/2);
			
		
			
			//Computer hit the ball
			getBall().setLastHitWasPLayer(false);
			
		
		}
		System.out.println(ball.getxVelocity());
	}
	
	
	

}
