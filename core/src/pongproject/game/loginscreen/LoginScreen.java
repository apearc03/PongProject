package pongproject.game.loginscreen;

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

public class LoginScreen implements Screen {

	private Pong pongGame;
	private Label label;
	private Stage stage;
	private TextButton gameButton;
	private boolean isSelected;
	
	public LoginScreen(final Pong pongGame) {
		
		
		
		
		
		
		//Perform database queries here
		//if there are any exceptions, just set the screen back to the menu screen.
		
		
		
		
		
		
		this.pongGame = pongGame;
	
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, pongGame.getCamera()));
		
		
		label = new Label("Enter your login details", pongGame.getSkin());
		label.setPosition(Constants.VIEWPORT_WIDTH/2-(label.getWidth()/2), 300);
		stage.addActor(label);
		
		gameButton = new TextButton("Play", pongGame.getSkin());
		gameButton.setPosition(Constants.VIEWPORT_WIDTH/2-(gameButton.getWidth()/2), 200);
		stage.addActor(gameButton);	
		
		gameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
				pongGame.setScreen(pongGame.getGameScreen());
				
				
			}
		});
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		isSelected = true; //testing purposes
		pongGame.getScreenTest().testScreens();
		
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}

	public boolean isSelected() { //testing
		
		return isSelected;
	}
	
}
