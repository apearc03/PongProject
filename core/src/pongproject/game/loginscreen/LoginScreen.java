package pongproject.game.loginscreen;

import java.sql.SQLException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import pongproject.game.Pong;
import pongproject.game.tests.eventLogger;

/**
 * 
 * @author Alex Pearce
 *
 */

public class LoginScreen implements Screen {

	
	//Game and Stage created
	private Pong pongGame;
	private Stage stage;
	
	//Required Labels and TextFields
	private Label titleLabel;
	private Label userError;
	private Label passError;

	private TextButton gameButton;
	private TextButton menuButton;
	private TextField userField;
	private TextField passField;
	
	
	//Strings and background declaration
	private String title;
	
	private String username;
	private String password;
	
	private Texture background;
	private Sprite backgroundSprite;
	
	
	/**
	 * Constructor used to initialize stage elements.
	 * 
	 * @param pong
	 */
	public LoginScreen(final Pong pong) {
		
		
		
		pongGame = pong;
	
		
		
		stage = new Stage(new StretchViewport(pongGame.getAppWidth(), pongGame.getAppHeight(), pongGame.getCamera()));
		
		background = new Texture(Gdx.files.internal("loginBackground.jpg"));
		backgroundSprite = new Sprite(background);
		backgroundSprite.setSize(pongGame.getAppWidth(), pongGame.getAppHeight());
		
		title =  "Enter your login or register a new username";
		titleLabel = new Label(title, new LabelStyle(pongGame.getFont20(),Color.WHITE));
		titleLabel.setPosition(pongGame.getAppWidth()/2-titleLabel.getWidth()/2, 600);
		stage.addActor(titleLabel);
		
		
		
		userField = new TextField("", pongGame.getSkin());
		userField.setPosition(pongGame.getAppWidth()/2-userField.getWidth()/2, 450);
		userField.setMessageText("Username");
		stage.addActor(userField);
		
		
	
		
		passField = new TextField("", pongGame.getSkin());
		passField.setPosition(pongGame.getAppWidth()/2-passField.getWidth()/2, 375);
		passField.setPasswordMode(true);
		passField.setPasswordCharacter('*');
		passField.setMessageText("Password");
		stage.addActor(passField);
		
		
		
		userError = new Label("", pongGame.getSkin());
		userError.setColor(Color.ORANGE);
		userError.setPosition(pongGame.getAppWidth()/2-userField.getWidth()/2,500);
		stage.addActor(userError);
		
		passError = new Label("", pongGame.getSkin());
		passError.setColor(Color.ORANGE);
		passError.setPosition(pongGame.getAppWidth()/2-passField.getWidth()/2, 425);
		stage.addActor(passError);
		
	
		
		gameButton = new TextButton("Play", pongGame.getSkin());
		gameButton.setSize(100, 30);
		gameButton.setPosition(pongGame.getAppWidth()/2-gameButton.getWidth()/2, 300);
		stage.addActor(gameButton);	
		
		menuButton = new TextButton("Menu", pongGame.getSkin());
		menuButton.setSize(100, 30);
		menuButton.setPosition(pongGame.getAppWidth()/2-menuButton.getWidth()/2, 250);
		stage.addActor(menuButton);
		
		//Button listeners added
		
		gameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				
				
				
				userError.setText("");
				passError.setText("");
				
				
				username = userField.getText();
				password = passField.getText();
				
				
				if(validatePassword(password)&validateUsername(username)) { //If the password and username values are valid return true
							
							try {
									if(pongGame.getData().checkLogin(username, password)) { //Checks the database for a username and password match
										
										pongGame.setLoggedIn(true);
										
										userField.setText("");
										passField.setText("");
										pongGame.getMenuScreen().setLoggedInAs(pongGame.getData().getAccountUsername());
										eventLogger.loginSuccess();
										pongGame.getButtonSound().play(pongGame.getGlobalVolume());
										pongGame.setScreen(pongGame.getGameScreen());
										
									}
									else { //Username is taken or the password is wrong
									
										pongGame.getButtonErrorSound().play(pongGame.getGlobalVolume());
										userError.setText("Username is taken or your password is incorrect");
										passError.setText("");
										
									}
								 } catch (SQLException e) {
										eventLogger.loginFailed();
										pongGame.setScreen(pongGame.getMenuScreen());
									 
									 	
								}
				}
				else {
					pongGame.getButtonErrorSound().play(pongGame.getGlobalVolume());
				}
				
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
	 * Called every time the screen is shown. Sets the screen as the input processor.
	 */
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	
		eventLogger.loginScreen();
	
	}

	/**
	 * Called every frame. Renders Stage actors and background art
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		pongGame.getBatch().begin();
		
		backgroundSprite.draw(pongGame.getBatch());
		
		pongGame.getBatch().end();
		
		stage.act(delta);
		stage.draw();
	}

	/**
	 * Called when the window is re-sized
	 */
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
		
	}

	//Methods that are required to be implemented by the Screen interface but are unused.
	@Override
	public void pause() {}

	@Override
	public void resume() {}

	/**
	 * Called when the screen is navigated away from. Removes errors
	 */
	@Override
	public void hide() {
		userError.setText("");
		passError.setText("");
		
	}

	/**
	 * Method called when the application exits to release resources.
	 */
	@Override
	public void dispose() {
		background.dispose();
		stage.dispose();
		
	}

	/**
	 * 
	 * Method designed to validate the username against certain conditions
	 * 
	 * @param username
	 * @return a boolean that represents a valid username or not
	 */
	private boolean validateUsername(String username){
		
		if(username.length()>10 || username.length() < 4) {
			
			userError.setText("Username must be between 4-10 characters in length");
			return false;
		}
		
		if(!username.matches("[a-zA-Z0-9]*")) {
		
			userError.setText("Username must only contain letters and numbers");
			return false;
		}
		
		
	
		return true;
	}
	
	/**
	 * Method designed to validate the password against certain conditions
	 * 
	 * @param password
	 * @return a boolean representing a valid password or not
	 */
	private boolean validatePassword(String password) {
		

		if(password.length()>10  || password.length() < 4) {
		
			passError.setText("Password must be between 4-10 characters in length");
			return false;
		}

		
		if(!password.matches("[a-zA-Z0-9]*")) {
			
			passError.setText("Password must only contain letters and numbers");
			return false;
		}
		
		if(password.equals(username)) {
			
			passError.setText("Password cannot be the same as your username");
			return false;
		}
		
		return true;
	
	}
	
	

}
