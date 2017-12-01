package pongproject.game.gamescreen;

import com.badlogic.gdx.Gdx;

import pongproject.game.Constants;

public class PlayerPaddle extends Paddle{
	
	//Need to change this direction number based on the movement input
	
	private int keyUp;
	private int keyDown;
	
	
	public PlayerPaddle(int kUp, int kDown) {
		super(Gdx.files.internal("paddle.png"), Constants.VIEWPORT_WIDTH-50);
	
		this.keyUp = kUp;
		this.keyDown = kDown;
		this.name = "the player";
	}
	

	
	public void movePaddle() {
		
		if(Gdx.input.isKeyPressed(keyUp)) {
			
		
			
			setyVelocity(5.0f);
			
		
		
		}
		
		if(Gdx.input.isKeyPressed(keyDown)) {
			
			setyVelocity(-5.0f);
	
			
		}
		
	}


}
