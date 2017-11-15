package pongproject.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import pongproject.game.gamescreen.GameScreen;
import pongproject.game.highscorescreen.HighScoreScreen;
import pongproject.game.menuscreen.MenuScreen;
import pongproject.game.tests.screenFunctionalityTest;

public class Pong extends Game {
	
	private screenFunctionalityTest screenTest; //testing only
	
	
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	
	private Skin skin;
	
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private HighScoreScreen highScoreScreen;
	
	/*to do
	 * 
	 * 
	 * Update written report, see texts on phone.
	 * Removed abstractScreen for now. Didnt have enough shared code between screens to warrant it.
	 * Go to jessops
	 * Work on actual pong game.
	 * possibly test app on phone.
	 * Commit to github
	 * 
	 */
	
	//initialises all necessary variables and sets screen to the menu
	
	@Override
	public void create () {
		
		Gdx.graphics.setTitle(Constants.title);
		
		camera = new OrthographicCamera();
		
		batch = new SpriteBatch();
		
		font = new BitmapFont();
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		highScoreScreen = new HighScoreScreen(this);
		
		screenTest = new screenFunctionalityTest(this); //testing purposes
		
		
		this.setScreen(menuScreen);
	}

	//Disposes of resources on game exit
	@Override
	public void dispose() {
		
		super.dispose();
		batch.dispose();
		font.dispose();
		menuScreen.dispose();
		gameScreen.dispose();
		highScoreScreen.dispose();
	}
	
	// Getter methods
	
	public SpriteBatch getBatch() {
		return batch;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public BitmapFont getFont() {
		return font;
	}
	
	public GameScreen getGameScreen() {
		return gameScreen;
	}
	
	public HighScoreScreen getHighScoreScreen() {
		return highScoreScreen;
	}
	
	public MenuScreen getMenuScreen() {
		return menuScreen;
	}
	
	public screenFunctionalityTest getScreenTest() {
		return screenTest;
	}
	
	public Skin getSkin() {
		return skin;
	}

}
