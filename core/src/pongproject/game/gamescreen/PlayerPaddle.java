package pongproject.game.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class PlayerPaddle extends Paddle{
	
	//Need to change this direction number based on the movement input
	
	private int keyUp;
	private int keyDown;
	
	private Sound playerScore;
	private Sound playerWin;
	private Sound playerHit;

	
	
	public PlayerPaddle(int kUp, int kDown) {
		
		super(Gdx.files.internal("paddleGreen.png"), 1024-50);
	
		this.keyUp = kUp;
		this.keyDown = kDown;
		this.name = "the player";
		
		this.playerScore = Gdx.audio.newSound(Gdx.files.internal("playerScores.mp3"));
		this.playerWin = Gdx.audio.newSound(Gdx.files.internal("player wins.mp3"));
		this.playerHit = Gdx.audio.newSound(Gdx.files.internal("playerHitsBall.mp3"));
	}
	

	
	public void movePaddle() {
		
		if(Gdx.input.isKeyPressed(keyUp)) {
			
		
			
			setyVelocity(5);
			
		
		
		}
		
		if(Gdx.input.isKeyPressed(keyDown)) {
			
			setyVelocity(-5);
	
			
		}
		
	}



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

	
	//Getters and setters
	
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
