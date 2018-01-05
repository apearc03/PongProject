package pongproject.game.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Class designed to represent a computer paddle
 * 
 * @author Alex Pearce
 *
 */
public class ComputerPaddle extends Paddle{

	
	private Ball gameBall;
	//Float variable representing the difficulty
	private float difficulty; 
	//Sound variables for game events
	private Sound cpHit;
	private Sound cpScore;
	private Sound cpWin;
	
	/**
	 * Parameter initializes paddle attributes
	 * 
	 * @param ball
	 */
	public ComputerPaddle(Ball ball) {
		super(Gdx.files.internal("paddleGreenCP.png"), 50);
		this.gameBall = ball;
		this.difficulty = 10.5f;
		
		this.cpHit = Gdx.audio.newSound(Gdx.files.internal("cpHitsBall.mp3"));
		
		this.cpScore = Gdx.audio.newSound(Gdx.files.internal("computerScores.mp3"));
	
		this.cpWin = Gdx.audio.newSound(Gdx.files.internal("player loses.mp3"));
		
		this.name = "the computer";
	}

	
	

	/**
	 * 
	 * Move's the paddle towards the ball position of the Y axis. 
	 * 
	 */
	public void movePaddleToBall() {
		
		
		updatePosition(((gameBall.getY()-getHeight()/2+gameBall.height/2)-getY())/difficulty); 

		
		
	}
	
	

	
	
	//Methods to return game sounds associated with the computer paddle

	@Override
	public Sound scoreSound() {

		return cpScore;
	}


	@Override
	public Sound hitSound() {

		return cpHit;
	}


	@Override
	public Sound victorySound() {
	
		return cpWin;
	}
	
	//Getters and setters
	
	public float getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}


	
}
