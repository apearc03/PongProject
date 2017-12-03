package pongproject.game.menuscreen;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import pongproject.game.Constants;
import pongproject.game.Pong;
import pongproject.game.loginscreen.LoginScreen;
import pongproject.game.tests.eventLogger;

public class MenuScreen implements Screen{


	private Label label;
	private Stage stage;
	private TextButton gameButton;
	private TextButton highScoreButton;
	private TextButton loginButton;
	private TextButton connectionButton;
	//testing purposes
	private Pong pongGame;
	private Label loggedInAs;
	private Label connectionMessage;
	private LabelStyle connectionStyle;
	
	
	
	
	
	
	
	
	
	
	//Constructor initialises stage, table, widgets and input for the screen
	public MenuScreen(final Pong pongGame) {
		this.pongGame = pongGame;
		
		
		
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, pongGame.getCamera()));
		
		
		label = new Label("Menu Screen", pongGame.getSkin());
		gameButton = new TextButton("Play", pongGame.getSkin());
		highScoreButton = new TextButton("High Scores", pongGame.getSkin());
		connectionButton = new TextButton("Retry Connection", pongGame.getSkin());
		
		connectionButton.setPosition(20, Constants.VIEWPORT_HEIGHT-50);
		connectionButton.setTransform(true);
		connectionButton.setScale(0.5f);
		
		//connectionMessage = "";
		connectionStyle = new LabelStyle(pongGame.getSecondFont(), Color.WHITE);
		connectionMessage = new Label("", connectionStyle);
		connectionMessage.setPosition(20,Constants.VIEWPORT_HEIGHT-20);
		connectionMessage.setFontScale(0.75f);
		stage.addActor(connectionMessage);
		
		loggedInAs = new Label("", pongGame.getSkin());
		loggedInAs.setPosition(20, 20);
		stage.addActor(loggedInAs);
		
		stage.addActor(connectionButton);
		label.setPosition(Constants.VIEWPORT_WIDTH/2-(label.getWidth()/2), 300);
		stage.addActor(label);
		gameButton.setPosition(Constants.VIEWPORT_WIDTH/2-(gameButton.getWidth()/2), 200);
		stage.addActor(gameButton);	
		highScoreButton.setPosition(Constants.VIEWPORT_WIDTH/2-(highScoreButton.getWidth()/2), 100);
		stage.addActor(highScoreButton);
		
		loginButton = new TextButton("Login with a different account", pongGame.getSkin());
		loginButton.setPosition(Constants.VIEWPORT_WIDTH/2-(loginButton.getWidth()/2), 150);
		loginButton.setVisible(false);
		stage.addActor(loginButton);
		
		
		gameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
				try {
						if(pongGame.getFirstConnection()) {
									if(pongGame.getData().checkConnection()) {
										
										if(pongGame.getLoggedIn()) {
											pongGame.setScreen(pongGame.getGameScreen()); 	
										}
										else {
											//pongGame.setScreen(pongGame.getGameScreen()); //added to skip load screen, take out after
											pongGame.setScreen(pongGame.getLoginScreen());
										}
										
									
									}
									else {
										pongGame.setScreen(pongGame.getGameScreen());
									}
						}
						else {
							pongGame.setScreen(pongGame.getGameScreen());
						}
				} catch (SQLException e) {
					
					eventLogger.databaseConnectionFailed();
				}
				//if connected to database, go to login screen. If not go to game screen.
			}
		});
		
		
		highScoreButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				pongGame.setScreen(pongGame.getHighScoreScreen());
			}
		});

		connectionButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				try {
					makeConnection();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					showRetryConnection();
				}
			}
			
			
		});
		
		loginButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				pongGame.setScreen(pongGame.getLoginScreen());
			}
		});
		
		
		try {
			makeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			showRetryConnection();
			pongGame.setFirstConnection(false);
		}
		
		
	}

	

	//This method is called when the screen is selected
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		eventLogger.menuScreen();
		
		
		try {
			if(pongGame.getFirstConnection()) {
				if(!pongGame.getData().checkConnection()) {
					showRetryConnection();
				}
			}	
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		if(pongGame.getLoggedIn()) {
			loginButton.setVisible(true);
		}
	
		
	}
	
	
	
	//Method repeatedly called to render and update screen
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
	
		
		
		pongGame.getBatch().begin();
		//pongGame.getFont().draw(pongGame.getBatch(),connectionMessage,20,Constants.VIEWPORT_HEIGHT-20); //change to label.
		pongGame.getFont().draw(pongGame.getBatch(), "FPS: "+ Gdx.graphics.getFramesPerSecond(),20,50);
		pongGame.getBatch().end();
		
		stage.act(delta);
		stage.draw();
		
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
	
		
	}
	@Override
	public void dispose() {
		stage.dispose();
		try {
			if(pongGame.getFirstConnection()) {
				pongGame.getData().closeConnection();
				}
		}catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	

	
	private void makeConnection() throws SQLException {
		pongGame.getData().makeConnection();
		
		eventLogger.databaseConnectionMade();
		
		connectionMessage.setText("Connection to database successful");
		connectionButton.setVisible(false);
		highScoreButton.setVisible(true);
		pongGame.setFirstConnection(true);
	}
	
	private void showRetryConnection() {
	
		connectionMessage.setText("Connection to database unsuccessful, you can play but your score wont be recorded");
		connectionButton.setVisible(true);
		highScoreButton.setVisible(false);
	}
	
	
	public void setLoggedInAs(String username) {
		loggedInAs.setText("Logged in as " + username);
	}
	
	
}
