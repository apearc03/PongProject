package pongproject.game.loginscreen;

import java.sql.SQLException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import pongproject.game.Constants;
import pongproject.game.Pong;
import pongproject.game.tests.eventLogger;

public class LoginScreen implements Screen {

	private Pong pongGame;
	private Label label;
	private Label userError;
	private Label passError;
	private LabelStyle errorStyle;
	private Stage stage;
	private TextButton gameButton;
	private TextButton menuButton;
	
	private TextField userField;
	private TextField passField;
	


	
	
	private String username;
	private String password;
	
	public LoginScreen(final Pong pongGame) {
		
		
		
		
		
		//Perform database queries here
		//if there are any exceptions, just set the screen back to the menu screen.
		//constantly check connection status? if it drops, return to menu screen and display prompt saying connection lost.
		
		
		
		
		
		this.pongGame = pongGame;
	
		
		
		
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, pongGame.getCamera()));
		

		
		
	
		
		label = new Label("Enter your login details or register a new account", pongGame.getSkin());
		label.setPosition(Constants.VIEWPORT_WIDTH/2-(label.getWidth()/2), 350);
		stage.addActor(label);
		
		userField = new TextField("", pongGame.getSkin());
		userField.setPosition(Constants.VIEWPORT_WIDTH/2-(userField.getWidth()/2), 250);
		userField.setMessageText("Username");
		stage.addActor(userField);
		
		
	
		
		passField = new TextField("", pongGame.getSkin());
		passField.setPosition(Constants.VIEWPORT_WIDTH/2-(passField.getWidth()/2), 150);
		passField.setPasswordMode(true);
		passField.setPasswordCharacter('*');
		passField.setMessageText("Password");
		stage.addActor(passField);
		
		//added
		errorStyle = new LabelStyle(pongGame.getSecondFont(), Color.RED);
		userError = new Label("", errorStyle);
		passError = new Label("", errorStyle);
		userError.setPosition(Constants.VIEWPORT_WIDTH/2-userField.getWidth()/2,300);
		passError.setPosition(Constants.VIEWPORT_WIDTH/2-passField.getWidth()/2, 200);
		
		stage.addActor(userError);
		stage.addActor(passError);
		
		gameButton = new TextButton("Play", pongGame.getSkin());
		gameButton.setPosition(Constants.VIEWPORT_WIDTH/2-(gameButton.getWidth()/2), 100);
		stage.addActor(gameButton);	
		
		menuButton = new TextButton("Menu", pongGame.getSkin());
		menuButton.setPosition(Constants.VIEWPORT_WIDTH/2-(menuButton.getWidth()/2), 50);
		stage.addActor(menuButton);
		
		gameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				
				
				
				userError.setText("");
				passError.setText("");
				
				
				username = userField.getText();
				password = passField.getText();
				
				
				if(validatePassword(password)&validateUsername(username)) {
							try {
									if(pongGame.getData().checkLogin(username, password)) {
										//In the exception here check the connection, if no connection return to main menu
										pongGame.setLoggedIn(true);
										
										userField.setText("");
										passField.setText("");
										pongGame.getMenuScreen().setLoggedInAs(pongGame.getData().getAccountUsername());
										eventLogger.loginSuccess();
										pongGame.setScreen(pongGame.getGameScreen());
										
									}
									else {
										//userError = "Username is taken or your password is incorrect";
										//passError = "";
										userError.setText("Username is taken or your password is incorrect");
										passError.setText("");
										
									}
								 } catch (SQLException e) {
										eventLogger.loginFailed();
										pongGame.setScreen(pongGame.getMenuScreen());
									 
									 	
								}
				}
				
			}
		});
		
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				pongGame.setScreen(pongGame.getMenuScreen());
			}
		});
		
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	
		eventLogger.loginScreen();
	
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		
		pongGame.getBatch().begin();	
		pongGame.getFont().draw(pongGame.getBatch(), "FPS: "+ Gdx.graphics.getFramesPerSecond(),20,50);
	
		pongGame.getBatch().end();
		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		userError.setText("");
		passError.setText("");
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}

	
	private boolean validateUsername(String username){
		
		if(username.length()>20 || username.length() < 4) {
			
			userError.setText("Username must be between 4-20 characters in length");
			return false;
		}
		
		if(!username.matches("[a-zA-Z0-9]*")) {
		
			userError.setText("Username must only contain letters and numbers");
			return false;
		}
		
		
	
		return true;
	}
	
	private boolean validatePassword(String password) {
		

		if(password.length()>20  || password.length() < 4) {
		
			passError.setText("Password must be between 4-20 characters in length");
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
