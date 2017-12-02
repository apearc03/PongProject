package pongproject.game;

import java.sql.SQLException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import pongproject.game.database.databaseManager;
import pongproject.game.gamescreen.GameScreen;
import pongproject.game.highscorescreen.HighScoreScreen;
import pongproject.game.loginscreen.LoginScreen;
import pongproject.game.menuscreen.MenuScreen;
import pongproject.game.tests.screenFunctionalityTest;

public class Pong extends Game {
	
	private screenFunctionalityTest screenTest; //testing only
	
	
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private BitmapFont secondFont;
	private BitmapFont loadFont;
	
	
	private Skin skin;
	
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private HighScoreScreen highScoreScreen;
	private LoginScreen loginScreen;
	
	private  databaseManager data;
	
	private boolean firstConnection;
	private boolean loggedIn;
	
	
	/*to do
	 * 
	 *
	 * Next do the highscore screen. Add index to the necessary columns in pong_scores. Might be username and scores so far. Username to get the players scores only. Score to get the highest scores? 
	 * 
	 * Might need to change name of paddle velocity variable. I think its speed not velocity since x remains the same.
	 * Menu and buttons are placed on screen Y coordinate with magic number, need to place according to screen size
	 * 
	 * Tested with delta a lot, seemed to make things worse
	 * 
	 * 
	 *
	 * 
	 * 
	 * Idea for later on, allow paddle to move in confined area left and right
	 * Remove Batch.draw in render methods when game is done.
	 * Re do all text with hiero font creator.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * After that work on gamescreen.
	 * Start with some sort of countdown. Might need to do this in a newscreen. Try not to tho. Could be a method called in the show method.
	 * Then position assets and move ball.
	 * Use vectors for position and velocity. Rectangles for ball and paddles.
	 * Vector for ball, position and velocity. Float for y position and float for paddles y velocity since it remains on same x coordinate.
	 * When ball hits paddle. Create calculation from ball and paddles velocity.
	 * .
	 * 
	 * 
	 * 
	 */
	
	//initialises all necessary variables and sets screen to the menu
	
	@Override
	public void create () {
		
		
		
	
			try {
				data = new databaseManager();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		
			
		
		Gdx.graphics.setTitle(Constants.title);
		
		camera = new OrthographicCamera();
		
		batch = new SpriteBatch();
		
		loadFont = new BitmapFont(Gdx.files.internal("arial150.fnt"));
		
		
		font = new BitmapFont();
		font.getData().setScale(0.7f);
		
		secondFont = new BitmapFont();
		secondFont.getData().setScale(1f);
		//errorFont.setColor(Color.RED);
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		highScoreScreen = new HighScoreScreen(this);
		loginScreen = new LoginScreen(this);
		
		screenTest = new screenFunctionalityTest(this); //testing purposes
		
		loggedIn = false;
		
		this.setScreen(menuScreen);
	}

	//Disposes of resources on game exit
	@Override
	public void dispose() {
		
		super.dispose();
		font.dispose();
		batch.dispose();
		menuScreen.dispose();
		gameScreen.dispose();
		highScoreScreen.dispose();
		loginScreen.dispose();
	
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
	
	public BitmapFont getSecondFont() {
		return secondFont;
	}
	
	public BitmapFont getLoadFont() {
		return loadFont;
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
	
	public LoginScreen getLoginScreen() {
		return loginScreen;
	}
	
	public screenFunctionalityTest getScreenTest() {
		return screenTest;
	}
	
	public Skin getSkin() {
		return skin;
	}

	public databaseManager getData() {
		return data;
	}
	
	public boolean getLoggedIn(){
		return loggedIn;
	}
	
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public boolean getFirstConnection(){
		return firstConnection;
	}
	
	public void setFirstConnection(boolean firstConnection) {
		this.firstConnection = firstConnection;
	}
}
