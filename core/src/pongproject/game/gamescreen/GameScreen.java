package pongproject.game.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import pongproject.game.Constants;
import pongproject.game.Pong;

public class GameScreen implements Screen{


	private Stage stage;
	private TextButton menuButton;
	private Label label;
	private Pong pongGame;

	private float elapsed;
	private boolean gameStarted;
	
	private LabelStyle loadStyle;
	private Label controls;
	private Label play;
	private Action playFadeOut;
	private Action controlsFadeOut;
	
	private boolean isSelected; //testing purposes
	
	
	
	
	private GameController gameController;

	
	
	public GameScreen(final Pong pongGame) {
		this.pongGame = pongGame;
		
		gameController = new GameController(pongGame, this);
		
	
		
		
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, pongGame.getCamera()));
		
		
		
		loadStyle = new LabelStyle(pongGame.getLoadFont(), Color.ORANGE);
		
		
		controlsFadeOut = Actions.fadeOut(5.0f);
		controls = new Label("Use the up and down arrow keys to move", pongGame.getSkin());
		controls.setScale(2.0f);
		controls.setPosition(Constants.VIEWPORT_WIDTH/2-controls.getWidth()/2, Constants.VIEWPORT_HEIGHT-controls.getHeight());
		
		stage.addActor(controls);
		
		
		playFadeOut = Actions.fadeOut(1.0f);
		play = new Label("PLAY!", loadStyle);
		play.setPosition(Constants.VIEWPORT_WIDTH/2-play.getWidth()/2, Constants.VIEWPORT_HEIGHT/2-play.getHeight()/2);
		
		
		stage.addActor(play);
		
		
		
		
		
		
		
		
		
		
		label = new Label("Game Screen", pongGame.getSkin());
		menuButton = new TextButton("Menu", pongGame.getSkin());

		
		
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
	
		elapsed = 0;
		
		controls.setColor(Color.ORANGE);
		controls.addAction(controlsFadeOut);
		
		play.setColor(Color.ORANGE);
		play.addAction(playFadeOut);
		
		isSelected = true; //testing purposes
		
		
		pongGame.getScreenTest().testScreens(); //testing
		
		gameStarted = false;
		
	}

	@Override
	public void render(float delta) {
		
		elapsed += 1;
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		//gameController.getPaddleLeft().movePaddle();
		gameController.update();
		
		stage.act(delta);
		stage.draw();
		
		
		
	
		pongGame.getBatch().begin();
		
		
		if(elapsed >= 60) {
			
			gameController.getBall().getBallSprite().draw(pongGame.getBatch());
			
			if(!gameStarted) {
				gameController.startGame();
				gameStarted = true;
			}
			
			
		}
		
		gameController.getComputerPadd().getPaddleSprite().draw(pongGame.getBatch());
		gameController.getPlayerPadd().getPaddleSprite().draw(pongGame.getBatch());
		pongGame.getFont().draw(pongGame.getBatch(), "FPS: "+ Gdx.graphics.getFramesPerSecond(),20,50);
		pongGame.getBatch().end();
		
		gameController.zeroPadVelocity();
		
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
		
		gameController.resetGame();
		
		isSelected = false;//testing purposes
		
		controlsFadeOut.reset();

		playFadeOut.reset();

	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}
	
	public boolean isSelected() { //testing
		
		return isSelected;
	}

}
