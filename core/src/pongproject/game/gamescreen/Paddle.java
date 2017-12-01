package pongproject.game.gamescreen;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import pongproject.game.Constants;

public abstract class Paddle extends Rectangle{ //possibly extend actor instead and add to stage.

	private Texture paddleImage;
	private Sprite paddleSprite;
	protected String name;
	
	private int score;
	
	
	private float yVelocity;
	
	public Paddle(FileHandle paddle, float xCoordinate) {
		
		this.paddleImage = new Texture(paddle);
		this.paddleSprite = new Sprite(paddleImage);
		this.x = xCoordinate;
		this.y = Constants.VIEWPORT_HEIGHT/2-paddleSprite.getHeight()/2;
		this.paddleSprite.setX(x);
		this.paddleSprite.setY(y);
		//this.width = paddleSprite.getWidth();
		this.width = 10;
		this.height = paddleSprite.getHeight();
		this.yVelocity = 0;
		this.score = 0;
	}
	
	
	public void updatePosition(float yVelocity) {
		
		this.y += yVelocity;
		this.paddleSprite.setY(y);
		
		
	}
	
	public void checkOutOfBounds() {
		
		if(y+paddleSprite.getHeight()>=Constants.VIEWPORT_HEIGHT) {
			setyPosition(Constants.VIEWPORT_HEIGHT-paddleSprite.getHeight());
		}
		
		if(y<=0) {
			setyPosition(0);
		}
	}
	
	
	public void resetPaddle() {
		setyPosition(Constants.VIEWPORT_HEIGHT/2-paddleSprite.getHeight()/2);
		
	}
	
	public void resetScore() {
		score = 0;
	}
	
	public void incrementScore() {
		score +=1;
	}
	
	//getters and setters
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

