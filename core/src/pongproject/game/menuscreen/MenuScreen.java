package pongproject.game.menuscreen;

import java.awt.DisplayMode;
import java.sql.SQLException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import pongproject.game.Pong;
import pongproject.game.tests.eventLogger;

public class MenuScreen implements Screen{



	private Stage stage;
	private TextButton gameButton;
	private TextButton highScoreButton;
	private TextButton loginButton;
	private TextButton connectionButton;
	private TextButton settingsButton;
	
	private Pong pongGame;
	private Label loggedInAs;
	private Label connectionMessage;
	private LabelStyle messageStyle;

	private final String title; 
	private final String author;
	
	private Label titleLabel;
	private Label authorLabel;
	
	private Texture background;
	
	private Sprite backgroundSprite;
	
	
	//Constructor initialises stage, table, widgets and input for the screen
	public MenuScreen(final Pong pong) {
		this.pongGame = pong;
		
		
		stage = new Stage(new StretchViewport(pongGame.getAppWidth(), pongGame.getAppHeight(), pongGame.getCamera()));
		
		
		
		
		
		background = new Texture(Gdx.files.internal("menu.jpg"));
		
		
		backgroundSprite = new Sprite(background);
		backgroundSprite.setSize(pongGame.getAppWidth(), pongGame.getAppHeight());
		
		title = "Pong";
		author = "By Alex Pearce";
		
		
	
		

		
		
		
		authorLabel = new Label(author, new LabelStyle(pongGame.getFont20(),Color.WHITE));
		authorLabel.setPosition(pongGame.getAppWidth()/2-authorLabel.getWidth()/2, 500);
		stage.addActor(authorLabel);
		
		titleLabel = new Label(title, new LabelStyle(pongGame.getFont100(),Color.WHITE));
		titleLabel.setPosition(pongGame.getAppWidth()/2-titleLabel.getWidth()/2, 550);
		stage.addActor(titleLabel);
		
		
		gameButton = new TextButton("Play", pongGame.getSkin());
		gameButton.setSize(100, 30);
		gameButton.setPosition(pongGame.getAppWidth()/2-(gameButton.getWidth()/2), 300);
		stage.addActor(gameButton);	
		
		settingsButton = new TextButton("Settings", pongGame.getSkin());
		settingsButton.setSize(100, 30);
		settingsButton.setPosition(pongGame.getAppWidth()/2-(settingsButton.getWidth()/2), 250);
		stage.addActor(settingsButton);
	
		
		
		highScoreButton = new TextButton("High Scores", pongGame.getSkin());
		highScoreButton.setSize(100, 30);
		highScoreButton.setPosition(pongGame.getAppWidth()/2-(highScoreButton.getWidth()/2), 200);
		stage.addActor(highScoreButton);
		
		
		
		connectionButton = new TextButton("Retry Connection", pongGame.getSkin());
		connectionButton.setPosition(20, pongGame.getAppHeight()-55);
		connectionButton.setTransform(true);
		connectionButton.setScale(0.75f);
		stage.addActor(connectionButton);
		
		
		messageStyle = new LabelStyle(pongGame.getFont14(), Color.WHITE);
		
		connectionMessage = new Label("", messageStyle);
		connectionMessage.setPosition(20,pongGame.getAppHeight()-20);
		stage.addActor(connectionMessage);
		
		loggedInAs = new Label("", messageStyle);
		loggedInAs.setPosition(20, 20);
		stage.addActor(loggedInAs);
		
		
		

		
		
	
		
		loginButton = new TextButton("Change User", pongGame.getSkin());
		loginButton.setSize(100, 30);
		loginButton.setPosition(pongGame.getAppWidth()/2-(loginButton.getWidth()/2), 150);
		loginButton.setVisible(false);
		stage.addActor(loginButton);
		
		
		gameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
				pongGame.getButtonSound().play(pongGame.getButtonVolume());
				
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
		
		
		settingsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				pongGame.getButtonSound().play(pongGame.getButtonVolume());
				pongGame.setScreen(pongGame.getSettingsScreen());
			}
		});
		
		
		highScoreButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
				pongGame.getButtonSound().play(pongGame.getButtonVolume());
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
				pongGame.getButtonSound().play(pongGame.getButtonVolume());
				pongGame.setScreen(pongGame.getLoginScreen());
			}
		});
		
		
		
		//makes the connection to the database
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
		
		//pongGame.getBatch().draw(test, 0, 0);
		
		backgroundSprite.draw(pongGame.getBatch());
	
		
		//pongGame.getArialPointFour().draw(pongGame.getBatch(), "FPS: "+ Gdx.graphics.getFramesPerSecond(),20,50);
		
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
		background.dispose();
		

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
	
		connectionMessage.setText("Connection to database unsuccessful, your score wont be recorded");
		connectionButton.setVisible(true);
		highScoreButton.setVisible(false);
	}
	
	
	public void setLoggedInAs(String username) {
		loggedInAs.setText("Logged in as " + username);
	}
	
	
}
