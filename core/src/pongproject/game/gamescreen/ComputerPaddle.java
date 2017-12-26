package pongproject.game.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class ComputerPaddle extends Paddle{

	
	private Ball gameBall;
	private float difficulty; //lower the difficulty number to make harder
	
	public ComputerPaddle(Ball ball) {
		super(Gdx.files.internal("paddleGreen.png"), 50);
		this.gameBall = ball;
		this.difficulty = 10.5f;
		
		this.name = "the computer";
	}

	
	//Move to ball y with max speed, max speed should change with x velocity.
	
	//make invisible ball 
	
	//maybe add directional movement like player paddle.
	

	
	public void movePaddleToBall() {
		

	
		
		updatePosition(((gameBall.getY()-getHeight()/2+gameBall.height/2)-getY())/difficulty);
		
		
		
		
		
	}
	
	
	//for testing
	
	public void moveCPUPaddle() {
		
		if(Gdx.input.isKeyPressed(Keys.W)) {
				
			setyVelocity(5);

		
		}
		
		if(Gdx.input.isKeyPressed(Keys.S)) {
			
			setyVelocity(-5);
		
		}
		
	}
	
	
	
	public float getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}
	
	

	
}
