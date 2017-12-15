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
import pongproject.game.tests.eventLogger;

public class GameScreen implements Screen{


	private Stage stage;
	private TextButton menuButton;
	private TextButton playAgainButton;
	
	private Pong pongGame;

	private float elapsed;
	private boolean gameStarted;
	
	private LabelStyle loadStyle;
	private Label controls;
	private Label play;
	private Action playFadeOut;
	private Action controlsFadeOut;
	

	
	private Label winner;
	private Label scoreStored;
	
	
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
		
		
		
		
		
		
		
		
		
		
		playAgainButton = new TextButton("Play again", pongGame.getSkin());
		playAgainButton.setVisible(false);
		menuButton = new TextButton("Menu", pongGame.getSkin());
		menuButton.setVisible(false);
	
		playAgainButton.setPosition(Constants.VIEWPORT_WIDTH/2-playAgainButton.getWidth()/2, 250);
		stage.addActor(playAgainButton);
		menuButton.setPosition(Constants.VIEWPORT_WIDTH/2-menuButton.getWidth()/2, 200);
		stage.addActor(menuButton);	
		
		
		winner = new Label("", pongGame.getSkin());
		winner.setPosition(Constants.VIEWPORT_WIDTH/2-100, Constants.VIEWPORT_HEIGHT-250);
		winner.setVisible(false);
		stage.addActor(winner);
		
		
		scoreStored = new Label("", pongGame.getSkin());
		
		scoreStored.setVisible(false);
		stage.addActor(scoreStored);
		
		
		playAgainButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				pongGame.setScreen(pongGame.getGameScreen());
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
		eventLogger.gameScreen();
		
		elapsed = 0;
		
		controls.setColor(Color.ORANGE);
		controls.addAction(controlsFadeOut);
		
		play.setColor(Color.ORANGE);
		play.addAction(playFadeOut);
		
	
		
		gameStarted = false;
		
	}

	@Override
	public void render(float delta) {
		
		
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		//gameController.getPaddleLeft().movePaddle();
		gameController.update();
		
		stage.act(delta);
		stage.draw();
		
		
		
	
		pongGame.getBatch().begin();
		
		
		
		if(elapsed >= 60) {
			
			gameController.getBall().getBallSprite().draw(pongGame.getBatch());
			
			
			
			if(!gameStarted) {
				eventLogger.gameStarted();
				gameController.startGame();
				gameStarted = true;
			}
			
			
		}
		else {
			elapsed += 1;
		}
		
		
		gameController.getComputerPadd().getPaddleSprite().draw(pongGame.getBatch());
		gameController.getPlayerPadd().getPaddleSprite().draw(pongGame.getBatch());
		pongGame.getSecondFont().draw(pongGame.getBatch(), "" + gameController.getComputerPadd().getScore(),Constants.VIEWPORT_WIDTH/3,Constants.VIEWPORT_HEIGHT-50);
		pongGame.getSecondFont().draw(pongGame.getBatch(), "" + gameController.getPlayerPadd().getScore(),Constants.VIEWPORT_WIDTH-Constants.VIEWPORT_WIDTH/3,Constants.VIEWPORT_HEIGHT-50);
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
		gameController.resetScores();
		
		
		winner.setVisible(false);
		scoreStored.setVisible(false);
		menuButton.setVisible(false);
		playAgainButton.setVisible(false);
		

		
		controlsFadeOut.reset();

		playFadeOut.reset();
		
	

	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}
	

	
	public void setWinnerText(String winnerText) {
		winner.setText(winnerText);
		winner.setVisible(true);
	}

	
	public void setScoreStored(String score) {
		scoreStored.setText(score);
		scoreStored.setVisible(true);
	}

	public Label getScoreStored() {
		return scoreStored;
	}
	
	public TextButton getPlayAgainButton() {
		return playAgainButton;
	}
	
	public TextButton getMenuButton() {
		return menuButton;
	}
}

