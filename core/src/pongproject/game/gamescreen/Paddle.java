package pongproject.game.gamescreen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * 
 * Abstact paddle class representing a game Paddle
 * 
 * @author Alex Pearce
 *
 */
public abstract class Paddle extends Rectangle{ 

	//All types of paddles have the following attributes
	private Texture paddleImage;
	private Sprite paddleSprite;
	protected String name;
	private final float appHeight;
	
	private int score;
	
	private float yVelocity;
	
	/**
	 * Paddle constructor initializes all paddle attributes and sets the score to 0
	 * 
	 * @param paddle
	 * @param xCoordinate
	 */
	public Paddle(FileHandle paddle, float xCoordinate) {
		
		this.paddleImage = new Texture(paddle);
		this.paddleSprite = new Sprite(paddleImage);
		this.appHeight = 768;
		this.x = xCoordinate;
		this.y = appHeight/2-paddleSprite.getHeight()/2;
		this.paddleSprite.setX(x);
		this.paddleSprite.setY(y);
		//this.width = paddleSprite.getWidth();
		this.width = 10;
		this.height = paddleSprite.getHeight();
		this.yVelocity = 0;
		this.score = 0;
	}
	
	/**
	 * Updates the paddle position on the Y axis
	 * 
	 * @param yVelocity
	 */
	public void updatePosition(float yVelocity) {
		
		this.y += yVelocity;
		this.paddleSprite.setY(y);
		
		
	}
	
	/**
	 * 
	 * Checks if the paddle is out of bounds on the Y axis.
	 * If so the paddle position is set back to the closest possible position to that boundry 
	 * 
	 */
	public void checkOutOfBounds() {
		
		if(y+paddleSprite.getHeight()>=appHeight) {
			setyPosition(appHeight-paddleSprite.getHeight());
		}
		
		if(y<=0) {
			setyPosition(0);
		}
	}
	
	/**
	 * 
	 * Resets the paddle position
	 * 
	 */
	public void resetPaddle() {
		setyPosition(appHeight/2-paddleSprite.getHeight()/2);
		
	}
	
	/**
	 * Resets the paddle score
	 * 
	 */
	public void resetScore() {
		score = 0;
	}
	
	/**
	 * 
	 * Increments the paddle score
	 * 
	 */
	public void incrementScore() {
		score +=1;
	}
	
	//Abstract methods to be implemented in child classes
	public abstract Sound scoreSound();
	
	public abstract Sound hitSound();
	
	public abstract Sound victorySound();
	
	
	//Getter and Setter methods
	public int getScore() {
		return score;
	}
	

	public Sprite getPaddleSprite() {
		return paddleSprite;
	}
	
	public float getyVelocity() {
		return yVelocity;
	}
	
	public void setyVelocity(float yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	public float getyPosition() {
		return y;
	}
	
	public void setyPosition(float yPosition) {
		this.y = yPosition;
		this.paddleSprite.setY(yPosition);
	}
	
	public String getName() {
		return name;
	}
}

