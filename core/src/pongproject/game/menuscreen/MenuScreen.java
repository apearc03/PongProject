package pongproject.game.menuscreen;

import java.sql.SQLException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import pongproject.game.Constants;
import pongproject.game.Pong;

public class MenuScreen implements Screen{


	private Label label;
	private Stage stage;
	private TextButton gameButton;
	private TextButton highScoreButton;
	private TextButton connectionButton;
	private boolean isSelected; //testing purposes
	private Pong pongGame;
	
	
	
	private String connectionMessage;
	private boolean firstConnection;
	
	
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
		stage.addActor(connectionButton);
		label.setPosition(Constants.VIEWPORT_WIDTH/2-(label.getWidth()/2), 300);
		stage.addActor(label);
		gameButton.setPosition(Constants.VIEWPORT_WIDTH/2-(gameButton.getWidth()/2), 200);
		stage.addActor(gameButton);	
		highScoreButton.setPosition(Constants.VIEWPORT_WIDTH/2-(highScoreButton.getWidth()/2), 100);
		stage.addActor(highScoreButton);
		
		gameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
				try {
						if(firstConnection) {
									if(pongGame.getData().checkConnection()) {
										pongGame.setScreen(pongGame.getLoginScreen());
									}
									else {
										pongGame.setScreen(pongGame.getGameScreen());
									}
						}
						else {
							pongGame.setScreen(pongGame.getGameScreen());
						}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		
		
		try {
			makeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			showRetryConnection();
			firstConnection = false;
		}
		
		
	}

	

	//This method is called when the screen is selected
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		isSelected = true; //testing purposes
		pongGame.getScreenTest().testScreens(); //testing
		
		
		try {
			if(firstConnection) {
				if(!pongGame.getData().checkConnection()) {
					showRetryConnection();
				}
			}	
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	
		
	}
	
	
	
	//Method repeatedly called to render and update screen
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		
		
		pongGame.getBatch().begin();
		pongGame.getFont().draw(pongGame.getBatch(),connectionMessage,20,Constants.VIEWPORT_HEIGHT-20);
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
		isSelected = false; //testing purposes
		
	}
	@Override
	public void dispose() {
		stage.dispose();
		try {
			if(firstConnection) {
				pongGame.getData().closeConnection();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	public boolean isSelected() { //testing
		
		return isSelected;
	}
	
	

	
	private void makeConnection() throws SQLException {
		pongGame.getData().makeConnection();
		connectionMessage = "Connection to database successful";
		connectionButton.setVisible(false);
		highScoreButton.setVisible(true);
		firstConnection = true;
	}
	
	private void showRetryConnection() {
		connectionMessage = "Connection to database unsuccessful, you can play but your score wont be recorded";
		connectionButton.setVisible(true);
		highScoreButton.setVisible(false);
	}
	
	
	
}
