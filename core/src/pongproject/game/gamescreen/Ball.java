package pongproject.game.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * 
 * Class representing a ball object for the pong game.
 * 
 * @author Alex Pearce
 *
 */

public class Ball extends Rectangle {

	
	//Ball texture and sprite for rendering
	private Texture ballImage;
	private Sprite ballSprite;
	
	//Variables for ball movement
	private float xVelocity;
	private float yVelocity;
	private final float maxXVelocity;
	
	//Application height and width
	private final float appWidth;
	private final float appHeight;
	
	private boolean lastHitWasPLayer;
	
	

	
	/**
	 * Constructor initializes variables so that the ball is ready for rendering
	 * 
	 */
	public Ball() {
		this.ballImage = new Texture(Gdx.files.internal("ballYellow.png"));
		this.ballSprite = new Sprite(ballImage);
		
		appWidth = 1024;
		appHeight = 768;
		
		this.x = appWidth/2-ballSprite.getWidth()/2; 
		
		this.y = appHeight/2-ballSprite.getHeight()/2;
	
		this.ballSprite.setX(x);
		this.ballSprite.setY(y);
		this.xVelocity = 0;
		this.yVelocity = 0;
		this.width = ballSprite.getWidth();
		this.height = ballSprite.getHeight();
		this.lastHitWasPLayer = false;
		this.maxXVelocity = 7;
	
	}
	
	
	
	/**
	 * Getter method for the sprite
	 * 
	 * @return the Sprite associated with the ball
	 */
	public Sprite getBallSprite() {
		return ballSprite;
	}
	
	/**
	 * Begins the ball movement by setting the X velocity
	 * 
	 */
	public void startBallMovement() {
		
		this.xVelocity = 3f;
	}
	
	
	/**
	 * Increases the X velocity as the game goes on. Prevents games lasting endlessly
	 * 
	 */
	public void increaseXVeloverTime() {
		
	
		
		if(xVelocity<maxXVelocity && xVelocity>-maxXVelocity) {
			setxVelocity(xVelocity*1.0005f);
		}
	

		
	}
	
	/**
	 * Getter for ball X velocity
	 * 
	 * @return a float representing the X velocity
	 */
	public float getxVelocity() {
		return xVelocity;
	}
	
	/**
	 * Setter method for the X velocity
	 * 
	 * @param xVelocity
	 */
	public void setxVelocity(float xVelocity) {
		this.xVelocity = xVelocity;
	}
	
	/**
	 * Getter method for the Y velocity
	 * 
	 * @return A float representing the Y velocity
	 */
	public float getyVelocity() {
		return yVelocity;
	}
	
	/**
	 * Sets the ball velocity
	 * @param x
	 * @param y
	 */
	public void setVelocity(float x, float y) {
		this.xVelocity = x;
		this.yVelocity = y;
	}
	
	/**
	 * Resets the ball to the starting position
	 * 
	 */
	public void resetBall() {
		
		this.x = appWidth/2-ballSprite.getWidth()/2;
		
		this.y = appHeight/2-ballSprite.getHeight()/2;

		this.xVelocity = 0;
		this.yVelocity = 0;
		
	}
	
	/**
	 * Updates the position of the ball determined by the parameters representing the velocity
	 * 
	 * @param xVel
	 * @param yVel
	 */
	public void updatePosition(float xVel, float yVel) {
		
		
		this.x += xVel;
		this.y += yVel;
	
		this.ballSprite.setPosition(x, y);
		
		
	}
	
	
	
	
	/**
	 * Checks if the last paddle hit was the player
	 * 
	 * @return A boolean representing a last hit being the player
	 */
	public boolean getLastHitPlayer() {
		return lastHitWasPLayer;
	}
	
	/**
	 * Sets the last paddle hit
	 * 
	 * @param lastHitWasPLayer
	 */
	public void setLastHitWasPLayer(boolean lastHitWasPLayer) {
		this.lastHitWasPLayer = lastHitWasPLayer;
	}
}
