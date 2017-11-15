package pongproject.game.menuscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import pongproject.game.Constants;
import pongproject.game.Pong;

public class MenuScreen implements Screen{

	private Table table;
	private Label label;
	private Stage stage;
	private TextButton gameButton;
	private TextButton highScoreButton;
	private boolean isSelected; //testing purposes
	private Pong pongGame;
	
	//Constructor initialises stage, table, widgets and input for the screen
	public MenuScreen(final Pong pongGame) {
		this.pongGame = pongGame;
		
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, pongGame.getCamera()));
		table = new Table();
		stage.addActor(table);

		table.setFillParent(true);
		table.setWidth(stage.getWidth());
		table.align(Align.center|Align.bottom);
		
		label = new Label("Menu Screen", pongGame.getSkin());
		gameButton = new TextButton("Play", pongGame.getSkin());
		highScoreButton = new TextButton("High Scores", pongGame.getSkin());
		
		table.add(label).padBottom(170);
		table.row();
		table.add(gameButton).padBottom(50);
		table.row();
		table.add(highScoreButton).padBottom(50);
		
		
		gameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
				pongGame.setScreen(pongGame.getGameScreen());
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

	}

	

	//This method is called when the screen is selected
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		isSelected = true; //testing purposes
		pongGame.getScreenTest().testScreens(); //testing
	}
	//Method repeatedly called to render and update screen
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
		isSelected = false; //testing purposes
		
	}
	@Override
	public void dispose() {
		stage.dispose();
		
	}
	public boolean isSelected() { //testing
		
		return isSelected;
	}
}
