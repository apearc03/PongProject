package pongproject.game.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import pongproject.game.Pong;
import pongproject.game.tests.eventLogger;

/**
 * 
 * @author Alex Pearce
 *
 */
public class GameScreen implements Screen{

	private Pong pongGame;
	private Stage stage;
	
	//Game screen elements declared
	private TextButton menuButton;
	private TextButton playAgainButton;
	


	private float elapsed;
	private boolean gameStarted;
	
	private LabelStyle loadStyle;
	private Label controls;
	private Label play;
	private Action playFadeOut;
	private Action controlsFadeOut;
	

	
	private Label winner;
	private Label scoreStored;
	
	//GameController instance
	private GameController gameController;

	private Texture background;
	private Sprite backgroundSprite;
	
	
	/**
	 * 
	 * GameScreen constructor initializes variables, objects and adds listeners to buttons
	 * 
	 * @param pong
	 */
	public GameScreen(final Pong pong) {
		
		pongGame = pong;
		
		stage = new Stage(new StretchViewport(pongGame.getAppWidth(), pongGame.getAppHeight(), pongGame.getCamera()));
		
		gameController = new GameController(pongGame, this);
		
		background = new Texture(Gdx.files.internal("gameBackground.jpg"));
		backgroundSprite = new Sprite(background);
		backgroundSprite.setSize(pongGame.getAppWidth(), pongGame.getAppHeight());
		
		
		
		
		
		loadStyle = new LabelStyle(pongGame.getFont100(), Color.ORANGE);
		
		
		controlsFadeOut = Actions.fadeOut(5.0f);
		controls = new Label("Use the up and down arrow keys to move", pongGame.getSkin());
		controls.setScale(2.0f);
		controls.setPosition(pongGame.getAppWidth()/2-controls.getWidth()/2, pongGame.getAppHeight()-controls.getHeight());
		stage.addActor(controls);
		
		
		playFadeOut = Actions.fadeOut(1.0f);
		play = new Label("PLAY!", loadStyle);
		play.setPosition(pongGame.getAppWidth()/2-play.getWidth()/2, pongGame.getAppHeight()/2-play.getHeight()/2);
		stage.addActor(play);
		
		
		
		
		
		
		
		
		
		
		playAgainButton = new TextButton("Play again", pongGame.getSkin());
		playAgainButton.setSize(100, 30);
		playAgainButton.setVisible(false);
		playAgainButton.setPosition(pongGame.getAppWidth()/2-playAgainButton.getWidth()/2, 300);
		stage.addActor(playAgainButton);
		
		
		menuButton = new TextButton("Menu", pongGame.getSkin());
		menuButton.setSize(100, 30);
		menuButton.setVisible(false);
		menuButton.setPosition(pongGame.getAppWidth()/2-menuButton.getWidth()/2, 250);
		stage.addActor(menuButton);	
		
		
		winner = new Label("", pongGame.getSkin());
		winner.setPosition(pongGame.getAppWidth()/2-100, pongGame.getAppHeight()-150);
		winner.setVisible(false);
		stage.addActor(winner);
		
		
		scoreStored = new Label("", pongGame.getSkin());
		scoreStored.setVisible(false);
		stage.addActor(scoreStored);
		
		
		playAgainButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				pongGame.getButtonSound().play(pongGame.getGlobalVolume());
				pongGame.setScreen(pongGame.getGameScreen());
			}
		});
		
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				pongGame.getBackButtonSound().play(pongGame.getGlobalVolume());
				pongGame.setScreen(pongGame.getMenuScreen());
				
			}
		});
		
		
	
		
		
	
	}

	
	/**
	 * 
	 * Show method called when this screen is selected. Starts the game music and sets the elements to their starting positions
	 */
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		eventLogger.gameScreen();
		
		
		pongGame.getMusic()[0].pause();
		pongGame.getMusic()[1].play();
		pongGame.getMusic()[1].setLooping(true);
		pongGame.getMusic()[1].setVolume(pongGame.getGlobalVolume());
		
		elapsed = 0;
		
		controls.setText("Use the " + Keys.toString(gameController.getPlayerPadd().getKeyUp()) + " and " + Keys.toString(gameController.getPlayerPadd().getKeyDown())  + " keys to move");
		controls.setColor(Color.ORANGE);
		controls.addAction(controlsFadeOut);
		
		play.setColor(Color.ORANGE);
		play.addAction(playFadeOut);
		
		
		
		gameStarted = false;
		
	}

	/**
	 * 
	 * Called every frame to update the game objects. Their new position and state is drawn to the screen.
	 * 
	 */
	@Override
	public void render(float delta) {
		
		
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
	
		gameController.update();
		
		
		
		
		
	
		pongGame.getBatch().begin();
		
		backgroundSprite.draw(pongGame.getBatch());
		
		if(elapsed >= 60) { //If elapsed is more than 60. Draw the ball and start the game.
			
			gameController.getBall().getBallSprite().draw(pongGame.getBatch());
			
			
			
			if(!gameStarted) {
				eventLogger.gameStarted();
				gameController.startGame();
				gameStarted = true;
			}
			
			
		}
		else {
			elapsed += 1;
		}
		
		
		gameController.getComputerPadd().getPaddleSprite().draw(pongGame.getBatch());
		gameController.getPlayerPadd().getPaddleSprite().draw(pongGame.getBatch());
		pongGame.getFont16().draw(pongGame.getBatch(), Integer.toString(gameController.getComputerPadd().getScore()),pongGame.getAppWidth()/3,pongGame.getAppHeight()-50);
		pongGame.getFont16().draw(pongGame.getBatch(), Integer.toString(gameController.getPlayerPadd().getScore()),pongGame.getAppWidth()-pongGame.getAppWidth()/3,pongGame.getAppHeight()-50);
		pongGame.getBatch().end();
		
		stage.act(delta);
		stage.draw();
		
		gameController.zeroPadVelocity();
		
	}

	/**
	 * Called whenever the screen is re-sized
	 * 
	 */
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
		
	}

	//Empty implemented methods from Screen interface
	
	@Override
	public void pause() {}

	@Override
	public void resume() {}

	
	/**
	 * Called when the screen is hidden. Resets the game state.
	 * 
	 */
	@Override
	public void hide() {
		
		pongGame.getMusic()[0].play();
		pongGame.getMusic()[1].pause();
		
		gameController.resetGame();
		gameController.resetScores();
		
		gameController.getPlayerPadd().victorySound().stop();
		gameController.getComputerPadd().victorySound().stop();
		
		winner.setVisible(false);
		scoreStored.setVisible(false);
		menuButton.setVisible(false);
		playAgainButton.setVisible(false);
		

		
		controlsFadeOut.reset();

		playFadeOut.reset();
		
	

	}

	/**
	 * Disposes of all the game resources
	 * 
	 */
	@Override
	public void dispose() {
		background.dispose();
		gameController.disposeGameSounds();
		stage.dispose();
		
	}
	

	/**
	 * 
	 * Sets the winner text label
	 * 
	 * @param winnerText
	 */
	public void setWinnerText(String winnerText) {
		winner.setText(winnerText);
		winner.setVisible(true);
	}

	/**
	 * 
	 * Sets the score stored text label
	 * 
	 * @param score
	 */
	public void setScoreStored(String score) {
		scoreStored.setText(score);
		scoreStored.setVisible(true);
	}

	//Getter methods
	
	public Label getScoreStored() {
		return scoreStored;
	}
	
	public TextButton getPlayAgainButton() {
		return playAgainButton;
	}
	
	public TextButton getMenuButton() {
		return menuButton;
	}
	
	public GameController getGameController() {
		return gameController;
	}
}

