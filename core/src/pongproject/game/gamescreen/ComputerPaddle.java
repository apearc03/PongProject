package pongproject.game.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class ComputerPaddle extends Paddle{

	
	private Ball gameBall;
	private float difficulty; //lower the difficulty number to make harder
	
	public ComputerPaddle(Ball ball) {
		super(Gdx.files.internal("paddle.png"), 50);
		this.gameBall = ball;
		this.difficulty =9.5f;
		
		this.name = "the computer";
	}

	
	//Move to ball y with max speed, max speed should change with x velocity.
	
	//make invisible ball 
	
	//maybe add directional movement like player paddle.
	

	
	public void movePaddleToBall() {
		

	
		

	
		
		
					
					if(gameBall.getY()-getHeight()/2+gameBall.height/2>getY()) {
						
						//updatePosition(2);
						updatePosition(((gameBall.getY()-getHeight()/2+gameBall.height/2)-getY())/difficulty);
						
						
					}
					else if(gameBall.getY()-getHeight()/2+gameBall.height/2<getY()) {
						
						//updatePosition(-2);
						updatePosition(((gameBall.getY()-getHeight()/2+gameBall.height/2)-getY())/difficulty);
					}
					
	
		
		
		
		
		//float yDistance = (gameBall.getY()-getHeight()/2+gameBall.height/2)-getY();
		
		
			//setyPosition(gameBall.getY()-this.getHeight()/2+gameBall.height/2);
		
		//float ballposition = gameBall.getY()-getHeight()/2+gameBall.height/2;
		
		
		
		
		
	}
	
	
	//for testing
	
	public void moveCPUPaddle() {
		
		if(Gdx.input.isKeyPressed(Keys.W)) {
				
			setyVelocity(5.0f);

		
		}
		
		if(Gdx.input.isKeyPressed(Keys.S)) {
			
			setyVelocity(-5.0f);
		
		}
		
	}
	
	
	
	public float getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}
	
	

	
}
