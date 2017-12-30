package pongproject.game.settingsscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;




import pongproject.game.Pong;
import pongproject.game.tests.eventLogger;

public class SettingsScreen implements Screen {

	
	/*try using these for settings

	import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
	import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
	import com.badlogic.gdx.scenes.scene2d.ui.List;
	import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
	
	
	  Set screen size with these
	  
	Gdx.graphics.setWindowedMode(1900, 1080);
	Gdx.graphics.setWindowedMode(1600, 1200);
	Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
	
	*/
	
	private TextButton menuButton;
	private Stage stage;
	private Pong pongGame;

	public SettingsScreen(final Pong pong) {

		pongGame = pong;
		
		stage = new Stage(new StretchViewport(pongGame.getAppWidth(), pongGame.getAppHeight(), pongGame.getCamera()));
		
		
		menuButton = new TextButton("Menu", pongGame.getSkin());
		menuButton.setSize(100, 30);
		menuButton.setPosition(pongGame.getAppWidth()/2-(menuButton.getWidth()/2), 250);
		stage.addActor(menuButton);
		
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				pongGame.getBackButtonSound().play(pongGame.getButtonVolume());
				pongGame.setScreen(pongGame.getMenuScreen());
				
			}
		});
	}
	
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
		eventLogger.settingsScreen();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}

	
	
}
