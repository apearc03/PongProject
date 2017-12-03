package pongproject.game.highscorescreen;

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
import pongproject.game.tests.eventLogger;

public class HighScoreScreen implements Screen{


	private Stage stage;
	private TextButton menuButton;
	private Label label;
	private Pong pongGame;

	
	
	private TextButton orderByPlayerScore; //If pongGame.getLoggedIn = true, show this button. Otherwise set invisible
	
	//Default order should be top 15 scores? 
	//Possible show win ratio of logged in player somehow? Not priority but added feature.			
	

	
	
	
	public HighScoreScreen(final Pong pongGame) {
		this.pongGame = pongGame;
		
		
		
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, pongGame.getCamera()));
		
		
		
		label = new Label("High Score Screen", pongGame.getSkin());
		menuButton = new TextButton("Menu", pongGame.getSkin());
		;

		label.setPosition(Constants.VIEWPORT_WIDTH/2-(label.getWidth()/2), 300);
		stage.addActor(label);
		menuButton.setPosition(Constants.VIEWPORT_WIDTH/2-(menuButton.getWidth()/2), 200);
		stage.addActor(menuButton);	
		
		
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
		

		eventLogger.highScoreScreen();
		
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

	}

	@Override
	public void dispose() {
		stage.dispose();
		
		
	}
 

	
}
