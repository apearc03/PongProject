package pongproject.game.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * 
 * @author Alex
 *
 */
public class PlayerPaddle extends Paddle{
	
	
	//Variables for input
	private int keyUp;
	private int keyDown;
	
	//Variables for sounds
	private Sound playerScore;
	private Sound playerWin;
	private Sound playerHit;

	
	/**
	 * Constructor initializes controls and sounds associated with this paddle
	 * 
	 * @param kUp
	 * @param kDown
	 */
	public PlayerPaddle(int kUp, int kDown) {
		
		super(Gdx.files.internal("paddleGreen.png"), 1024-50);
	
		this.keyUp = kUp;
		this.keyDown = kDown;
		this.name = "the player";
		
		this.playerScore = Gdx.audio.newSound(Gdx.files.internal("playerScores.mp3"));
		this.playerWin = Gdx.audio.newSound(Gdx.files.internal("player wins.mp3"));
		this.playerHit = Gdx.audio.newSound(Gdx.files.internal("playerHitsBall.mp3"));
	}
	

	
	/**
	 * 
	 * Set's the Y velocity in a direction depedent on the input
	 * 
	 */
	public void movePaddle() {
		
		
			if(Gdx.input.isKeyPressed(keyUp)) {
					
				setyVelocity(4);
	
			}
			
			if(Gdx.input.isKeyPressed(keyDown)) {
				
				setyVelocity(-4);
				
			}
	}

	//Getter and Setter methods
	
	@Override
	public Sound scoreSound() {
	
		return playerScore;
	}



	@Override
	public Sound hitSound() {
		
		return playerHit;
	}



	@Override
	public Sound victorySound() {

		return playerWin;
		
	}
	
	public int getKeyUp() {
		return keyUp;
	}
	
	public void setKeyUp(int keyUp) {
		this.keyUp = keyUp;
	}
	
	
	public int getKeyDown() {
		return keyDown;
	}
	

	public void setKeyDown(int keyDown) {
		this.keyDown = keyDown;
	}
}
