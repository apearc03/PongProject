package pongproject.game;

import java.sql.SQLException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import pongproject.game.database.databaseManager;
import pongproject.game.gamescreen.GameScreen;
import pongproject.game.highscorescreen.HighScoreScreen;
import pongproject.game.loginscreen.LoginScreen;
import pongproject.game.menuscreen.MenuScreen;
import pongproject.game.settingsscreen.SettingsScreen;

public class Pong extends Game {
	

	
	
	
	private OrthographicCamera camera;
	private SpriteBatch batch;



	
	
	private Skin skin;
	
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private HighScoreScreen highScoreScreen;
	private LoginScreen loginScreen;
	private SettingsScreen settingsScreen;
	
	private databaseManager data;
	
	private Music music[] = new Music[2];

	
	private boolean firstConnection;
	private boolean loggedIn;
	
	private Sound buttonSound;
	private Sound backButtonSound;
	private Sound buttonErrorSound;
	private float buttonVolume;
	
	private int appHeight;
	private int appWidth;
	
	private FreeTypeFontGenerator generator;
	
	private BitmapFont font16;
	private FreeTypeFontParameter parameter16;
	private BitmapFont font14;
	private FreeTypeFontParameter parameter14;
	private BitmapFont font12;
	private FreeTypeFontParameter parameter12;
	
	private BitmapFont font20; 
	private FreeTypeFontParameter parameter20;
	
	private BitmapFont font100;
	private FreeTypeFontParameter parameter100;
	/*to do
	 * 
	 * 

	 * Change all fonts to open sans ttf if possible.
	 * 
	 * Next functionality should be an options/settings screen or popup. Will include sound volume, music mute. Resolution options. Difficulty setting. Have controls shown. Picture of up/down key?

	 * 
	 * 
	 * Then remake project for different platforms.
	 * 
	 * When remaking project for deployment, remove eventLogger class for all. Remove database functionality for android. Remove necessary functionality for html.
	 * 
	 * 
	 * Need to redo all fonts, have separate bitMapFonts for each ranking.
	 * Do fonts for titles of screens.
	 * Remember to close all fonts in dispose methods after they are sorted.
	 * 
	 * 
	 * 
	 * In all the exceptions where I have just returned to the menuScreen. 
	 * Maybe put a line of code after to set a label or similar on the menuScreen to show that there has been a loss of connection to database.
	 * 
	 * 
	 * 
	 * Remember to clear all scores from database before deployment, add dummy scores suitable for game mode of first to 2 or 3.
	 * 
	 * 
	 * 
	 * 
	 * Might need to change name of paddle velocity variable. I think its speed not velocity since x remains the same.
	 * Menu and buttons are placed on screen Y coordinate with magic number, need to place according to screen size
	 * 
	 * Tested with delta a lot, seemed to make things worse
	 * 
	 * 
	 * 
	 * Add tab function to buttons?
	 * 

	 * 
	 * 
	 * Idea for later on, allow paddle to move in confined area left and right
	 * Re do all text with hiero font creator.
	 * 
	 * 
	 * Remove empty pause, resume implementations if possible
	 * 
	 * 
	 * 
	 * If randomly spawned powerups for length of paddle are used. Make use of libgdx timer to set duration of powerup. Random java class to set positioning on the middle x axis.
	 * 
	 * If you do 2 player, try to just use gameScreen class but with a new twoPlayerGameController class. Show player controls differently
	 * 
	 * 
	 * Tweak difficulty and max ball x velocity at the end
	 * 
	 * 
	 * On export "Extract required libraries into generated JAR"
		Remove unused libraries from build path? Controllers etc
	 * Being creating final Jar. Make a new project with only the required libraries and platform launchers.
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
		
			
		
			
		
		Gdx.graphics.setTitle("Pong");
		
		camera = new OrthographicCamera();
		
		
		batch = new SpriteBatch();

		appWidth = 1024;
		appHeight = 768;
		
		
		
		
		music[0] = Gdx.audio.newMusic(Gdx.files.internal("music2.ogg"));
		music[1] = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
		
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("openSans.ttf"));
		
		
		parameter16 = new FreeTypeFontParameter();
		parameter16.size = 16;
		font16 = generator.generateFont(parameter16);
		font16.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		
		parameter14 = new FreeTypeFontParameter();
		parameter14.size = 14;
		font14 = generator.generateFont(parameter14);
		font14.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		
		parameter12 = new FreeTypeFontParameter();
		parameter12.size = 12;
		font12 = generator.generateFont(parameter12);
		font12.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		parameter20 = new FreeTypeFontParameter();
		parameter20.size = 20;
		font20 = generator.generateFont(parameter20);
		font20.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		
		parameter100 = new FreeTypeFontParameter();
		parameter100.size = 100;
		font100 = generator.generateFont(parameter100);
		font100.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		
		buttonSound = Gdx.audio.newSound(Gdx.files.internal("buttonSound.mp3"));	
		backButtonSound = Gdx.audio.newSound(Gdx.files.internal("backButtonSound.mp3"));
		buttonErrorSound = Gdx.audio.newSound(Gdx.files.internal("buttonError.mp3"));
		buttonVolume = 0.2f;
		
	
		
		//errorFont.setColor(Color.RED);
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		highScoreScreen = new HighScoreScreen(this);
		loginScreen = new LoginScreen(this);
		settingsScreen = new SettingsScreen(this);
		
	 //testing purposes
		
		loggedIn = false;
		
		//this.setScreen(menuScreen);
		this.setScreen(menuScreen);
		
		
		music[0].play();
		music[0].setVolume(buttonVolume);
		music[0].setLooping(true);
	}

	//Disposes of resources on game exit
	@Override
	public void dispose() {
		
		super.dispose();
		generator.dispose();
		font100.dispose();
		font20.dispose();
		font16.dispose();
		font14.dispose();
		font12.dispose();
		music[0].dispose();
		music[1].dispose();
		
		buttonSound.dispose();
		backButtonSound.dispose();
		buttonErrorSound.dispose();
		batch.dispose();
		menuScreen.dispose();
		gameScreen.dispose();
		highScoreScreen.dispose();
		settingsScreen.dispose();
		loginScreen.dispose();
	
	}
	
	// Getter methods
	
	public SpriteBatch getBatch() {
		return batch;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public int getAppWidth() {
		return appWidth;
	}
	
	public int getAppHeight() {
		return appHeight;
	}
	

	
	public BitmapFont getFont100() {
		return font100;
	}
	
	public BitmapFont getFont20() {
		return font20;
	}
	
	public BitmapFont getFont16() {
		return font16;
	}
	
	public BitmapFont getFont14() {
		return font14;
	}
	
	public BitmapFont getFont12() {
		return font12;
	}
	
	public Sound getButtonSound() {
		return buttonSound;
	}
	
	public Sound getBackButtonSound() {
		return backButtonSound;
	}
	
	public Sound getButtonErrorSound() {
		return buttonErrorSound;
	}
	
	public GameScreen getGameScreen() {
		return gameScreen;
	}
	
	public float getButtonVolume() {
		return buttonVolume;
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
	
	public SettingsScreen getSettingsScreen() {
		return settingsScreen;
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
	
	public Music[] getMusic() {
		return music;
	}

}
