package pongproject.game.gamescreen;

import pongproject.game.Pong;

public class GameController {

	private Pong game;
	
	public GameController(Pong game) {
		this.game = game;
	}
	
	/* private void updateBallMovement(float deltaTime) {
	        if (!(ball == null)) {
	            ball.moveX(deltaTime);
	            checkForPaddleCollision();
	            checkForBallOutOfBounds();
	            ball.moveY(deltaTime);
	            checkForWallCollision();
	        }
	    }
	    
	  */
}
