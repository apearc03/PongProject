package pongproject.game.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import pongproject.game.Constants;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Ball extends Rectangle {

	private Texture ballImage;
	private Sprite ballSprite;
	private float xVelocity;
	private float yVelocity;

	private boolean lastHitWasPLayer;
	
	

	//make singleton
	
	public Ball() {
		this.ballImage = new Texture(Gdx.files.internal("ball5.png"));
		this.ballSprite = new Sprite(ballImage);
		
		this.x = Constants.VIEWPORT_WIDTH/2-ballSprite.getWidth()/2; 
		
		this.y = Constants.VIEWPORT_HEIGHT/2-ballSprite.getHeight()/2;
	
		this.ballSprite.setX(x);
		this.ballSprite.setY(y);
		this.xVelocity = 0;
		this.yVelocity = 0;
		this.width = ballSprite.getWidth();
		this.height = ballSprite.getHeight();
		this.lastHitWasPLayer = false;
	
	}
	
	
	
	
	public Sprite getBallSprite() {
		return ballSprite;
	}
	
	public void startBallMovement() {
		
		this.xVelocity = 3.5f;
	}
	
	public void increaseXVeloverTime() {
		
		//working code
		
		if(getxVelocity()<Constants.MAX_X_VELOCITY && getxVelocity()>-Constants.MAX_X_VELOCITY) {
			setxVelocity(getxVelocity()*1.0005f);
		}
	

		
	}
	
	
	public float getxVelocity() {
		return xVelocity;
	}
	
	public void setxVelocity(float xVelocity) {
		this.xVelocity = xVelocity;
	}
	
	
	public float getyVelocity() {
		return yVelocity;
	}
	
	
	public void setVelocity(float x, float y) {
		this.xVelocity = x;
		this.yVelocity = y;
	}
	
	public void resetBall() {
		
		this.x = Constants.VIEWPORT_WIDTH/2-ballSprite.getWidth()/2;
		
		this.y = Constants.VIEWPORT_HEIGHT/2-ballSprite.getHeight()/2;

		this.xVelocity = 0;
		this.yVelocity = 0;
		
	}
	
	public void updatePosition(float xVel, float yVel) {
		
		this.x += xVel;
		this.y += yVel;
	
		this.ballSprite.setPosition(x, y);
		
		
	}
	
	public void checkYOutOfBounds() {
		
		
		
		if(y < 0) {
			
			updatePosition(0, 5);
			setVelocity(getxVelocity(), getyVelocity()*-1);
		
			
		}
		
		

		if(y + getBallSprite().getHeight()> Constants.VIEWPORT_HEIGHT) {
			
			updatePosition(0, -5);
			setVelocity(getxVelocity(), getyVelocity()*-1);
			
			
		}
		
	}
	
	

	
	
	
	//When someone scores, return who scores?
	public void checkXOutOfBounds() {
		

		if(x <= 0) {
			setVelocity(getxVelocity()*-1, getyVelocity());
		
		}
		
		

		if(x + getBallSprite().getWidth()>= Constants.VIEWPORT_WIDTH) {
			setVelocity(getxVelocity()*-1, getyVelocity());
			
		}
		
	}
	
	
	public boolean getLastHitPlayer() {
		return lastHitWasPLayer;
	}
	
	public void setLastHitWasPLayer(boolean lastHitWasPLayer) {
		this.lastHitWasPLayer = lastHitWasPLayer;
	}
}
