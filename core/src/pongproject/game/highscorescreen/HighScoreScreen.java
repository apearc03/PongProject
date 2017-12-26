package pongproject.game.highscorescreen;

import java.sql.ResultSet;
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

	
	private ResultSet scores;
	private ResultSet playerScores;
	private ResultSet winRatio;
	private String winRatioString;
	
	private boolean scoresAssigned;
	private boolean playerScoresAssigned;

	
	private TextButton orderByPlayerScores; //If pongGame.getLoggedIn = true, show this button. Otherwise set invisible
	private TextButton allPlayerScores;
	
	
	private TextButton top10Scores;
	private TextButton top25Scores;
	private TextButton top40Scores;
	
	private int scoreYDecrement;
	private int numberOfRankings;

	private boolean showPlayerScores;
	
	
	//will assign HighScoreFont to a new font in each button
	private BitmapFont winPercentageFont;
	private BitmapFont columnData;
	private final String[] titles = {"High Scores", "Rank", "Player", "Date & Time", "Result", "Score"};
	
	private Texture Background;
	private Sprite backgroundSprite;
	
	public HighScoreScreen(final Pong pongGame) {
		this.pongGame = pongGame;
		
		
		
		
	    
		
		
		
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, pongGame.getCamera()));
		
		winPercentageFont = new BitmapFont(Gdx.files.internal("arial50.fnt"));
		winPercentageFont.getData().setScale(0.3f);
		
		columnData = new BitmapFont(Gdx.files.internal("arial50.fnt"));

		Background = new Texture(Gdx.files.internal("highScoreBackground.gif"));
		backgroundSprite = new Sprite(Background);
		backgroundSprite.setSize(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		
		
		

		top10Scores = new TextButton("Top 10", pongGame.getSkin());
		top10Scores.setPosition(Constants.VIEWPORT_WIDTH-250, Constants.VIEWPORT_HEIGHT-60);
		stage.addActor(top10Scores);
		
		top25Scores = new TextButton("Top 25", pongGame.getSkin());
		top25Scores.setPosition(top10Scores.getX()+top25Scores.getWidth(), Constants.VIEWPORT_HEIGHT-60);
		stage.addActor(top25Scores);
		
		
		top40Scores = new TextButton("Top 40", pongGame.getSkin());
		top40Scores.setPosition(top25Scores.getX()+top40Scores.getWidth(), Constants.VIEWPORT_HEIGHT-60);
		stage.addActor(top40Scores);
		
		menuButton = new TextButton("Menu", pongGame.getSkin());
		menuButton.setPosition(top40Scores.getX()+top40Scores.getWidth(), Constants.VIEWPORT_HEIGHT-60);
		stage.addActor(menuButton);	
		
		
		orderByPlayerScores = new TextButton("Logged in user", pongGame.getSkin());
		orderByPlayerScores.setPosition(100, Constants.VIEWPORT_HEIGHT-60);
		stage.addActor(orderByPlayerScores);
		orderByPlayerScores.setVisible(false);
		
		allPlayerScores = new TextButton("All users", pongGame.getSkin());
		allPlayerScores.setPosition(orderByPlayerScores.getX()+orderByPlayerScores.getWidth(), Constants.VIEWPORT_HEIGHT-60);
		stage.addActor(allPlayerScores);
		allPlayerScores.setVisible(false);
		
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				pongGame.getBackButtonSound().play(pongGame.getButtonVolume());
				pongGame.setScreen(pongGame.getMenuScreen());
			}
		});
		
		
		top10Scores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
				pongGame.getButtonSound().play(pongGame.getButtonVolume());
				scoreYDecrement = 60;
				numberOfRankings = 10;
				
				
				columnData.getData().setScale(0.34f);
				//will assign HighScoreFont to a new font in each button
			}
		});
		
		top25Scores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
				pongGame.getButtonSound().play(pongGame.getButtonVolume());
				scoreYDecrement = 23;
				numberOfRankings = 25;
				
				columnData.getData().setScale(0.3f);
				//will assign HighScoreFont to a new font in each button
			}
		});
		
		top40Scores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
				pongGame.getButtonSound().play(pongGame.getButtonVolume());
				scoreYDecrement = 14;
				numberOfRankings = 40;
			
				columnData.getData().setScale(0.25f);
				//will assign HighScoreFont to a new font in each button
			}
		});
		
		
		orderByPlayerScores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				pongGame.getButtonSound().play(pongGame.getButtonVolume());
				showPlayerScores = true;
			}
		});
		
		
		allPlayerScores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				pongGame.getButtonSound().play(pongGame.getButtonVolume());
				showPlayerScores = false;
			}
		});
		
		
		
	}

	@Override
	public void show() {
		
		Gdx.input.setInputProcessor(stage);
		eventLogger.highScoreScreen();
		
			try {
				scores = pongGame.getData().highScores();
				scoresAssigned = true;
				
					if(pongGame.getLoggedIn()) {
						orderByPlayerScores.setVisible(true);
						allPlayerScores.setVisible(true);
						
						playerScores = pongGame.getData().playerScores(pongGame.getData().getAccountUsername());
						
						
						winRatio = pongGame.getData().winPercentage(pongGame.getData().getAccountUsername());
						
						if(winRatio.next()) {
							winRatioString = "Player win percentage: " + String.format("%.0f%%",winRatio.getFloat(1)*100);

						}
						
						playerScoresAssigned = true;
					}	
					
			} catch (SQLException e) {
				
				pongGame.setScreen(pongGame.getMenuScreen());
				
			}
			
	
		
		showPlayerScores = false;
		
		scoreYDecrement = 60;
		numberOfRankings = 10;

		columnData.getData().setScale(0.34f);	
		
	}

	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		
		
		
		
		
		
	
	
		pongGame.getBatch().begin();
		//pongGame.getArialPointFour().draw(pongGame.getBatch(), "FPS: "+ Gdx.graphics.getFramesPerSecond(),20,50);
		
		backgroundSprite.draw(pongGame.getBatch());
		
		pongGame.getArialFour().draw(pongGame.getBatch(), titles[0], 425, Constants.VIEWPORT_HEIGHT-40);
		
		pongGame.getArialFour().draw(pongGame.getBatch(), titles[1], 135, 660 );
		pongGame.getArialFour().draw(pongGame.getBatch(), titles[2], 280, 660 );
		pongGame.getArialFour().draw(pongGame.getBatch(), titles[3], 425, 660 );
		pongGame.getArialFour().draw(pongGame.getBatch(), titles[4], 670, 660 );
		pongGame.getArialFour().draw(pongGame.getBatch(), titles[5], 815, 660 );
		
		try {
			
			if(pongGame.getLoggedIn()) {
				winPercentageFont.draw(pongGame.getBatch(), winRatioString, 100, Constants.VIEWPORT_HEIGHT-10);
			}
			
			
			if(showPlayerScores) {
				renderScores(numberOfRankings, scoreYDecrement, columnData, playerScores);
				
			}
			else {
				renderScores(numberOfRankings, scoreYDecrement, columnData, scores);
			}
			
		} catch (SQLException e) {
			
			//do something useful here
			pongGame.setScreen(pongGame.getMenuScreen());
		}
		
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
		winPercentageFont.dispose();
		columnData.dispose();
		Background.dispose();
		
		
		try {
			closeResultSets();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
 
	
	private void closeResultSets() throws SQLException {
		if(scoresAssigned) {
				scores.close();
		}
		if(playerScoresAssigned) {
				playerScores.close();
				winRatio.close();
		}
	}
	
	private void renderScores(int numberOfRanks, int yDecrement, BitmapFont font, ResultSet scoreSet) throws SQLException {
		int yStartHeight = 610;
		int rank = 1;
		int iterations = numberOfRanks;
		boolean grey = false;
		
		
		scoreSet.first();
		
		
		
		
		do {
		
			if(!grey) {
				font.setColor(Color.WHITE);
				grey = true;
			}
			else {
				font.setColor(Color.GRAY);
				grey = false;
			}
		
			font.draw(pongGame.getBatch(), Integer.toString(rank), 135, yStartHeight );
			font.draw(pongGame.getBatch(), scoreSet.getString(1), 280, yStartHeight );
			font.draw(pongGame.getBatch(), scoreSet.getString(2), 425, yStartHeight );
			font.draw(pongGame.getBatch(), scoreSet.getString(3), 670, yStartHeight );
			font.draw(pongGame.getBatch(), Integer.toString(scoreSet.getInt(4)), 815, yStartHeight );
			
			yStartHeight-= yDecrement;
			rank++;
			iterations--;
			
		}while(iterations>0 && scoreSet.next());
		
	}

	
}
