package pongproject.game.highscorescreen;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	private BitmapFont columnNames;

	
	
	
	public HighScoreScreen(final Pong pongGame) {
		this.pongGame = pongGame;
		
		winPercentageFont = new BitmapFont(Gdx.files.internal("calibri100.fnt"));
		winPercentageFont.getData().setScale(0.18f);
		
		columnData = new BitmapFont(Gdx.files.internal("calibri100.fnt"));
		columnData.getData().setScale(0.2f);
		
		columnNames = new BitmapFont(Gdx.files.internal("calibri100.fnt"));
		columnNames.getData().setScale(0.2f);
	    
		
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, pongGame.getCamera()));
		
		
		
		label = new Label("High Score Screen", pongGame.getSkin());
		label.setPosition(Constants.VIEWPORT_WIDTH/2-(label.getWidth()/2), Constants.VIEWPORT_HEIGHT-60);
		stage.addActor(label);
		
		
		
		

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
		allPlayerScores.setPosition(orderByPlayerScores.getX()+allPlayerScores.getWidth()+orderByPlayerScores.getWidth()/2, Constants.VIEWPORT_HEIGHT-60);
		stage.addActor(allPlayerScores);
		allPlayerScores.setVisible(false);
		
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				pongGame.setScreen(pongGame.getMenuScreen());
			}
		});
		
		
		top10Scores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				scoreYDecrement = 55;
				numberOfRankings = 10;
				
				columnData.getData().setScale(0.2f);
				//will assign HighScoreFont to a new font in each button
			}
		});
		
		top25Scores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				scoreYDecrement = 21;
				numberOfRankings = 25;
				columnData.getData().setScale(0.15f);
				//will assign HighScoreFont to a new font in each button
			}
		});
		
		top40Scores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				scoreYDecrement = 13;
				numberOfRankings = 40;
				columnData.getData().setScale(0.12f);
				//will assign HighScoreFont to a new font in each button
			}
		});
		
		
		orderByPlayerScores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
				showPlayerScores = true;
			}
		});
		
		
		allPlayerScores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);

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
							winRatioString = String.format("%.0f%%",winRatio.getFloat(1)*100);

						}
						
						playerScoresAssigned = true;
					}	
					
			} catch (SQLException e) {
				
				pongGame.setScreen(pongGame.getMenuScreen());
				
			}
			
	
		
		showPlayerScores = false;
		
		scoreYDecrement = 55;
		numberOfRankings = 10;

		columnData.getData().setScale(0.2f);	
		
	}

	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		
		stage.act(delta);
		stage.draw();
		
		
		
		
	
	
		pongGame.getBatch().begin();
		pongGame.getFont().draw(pongGame.getBatch(), "FPS: "+ Gdx.graphics.getFramesPerSecond(),20,50);
		
		columnNames.draw(pongGame.getBatch(), "Rank", 120, 560 );
		columnNames.draw(pongGame.getBatch(), "Player", 270, 560 );
		columnNames.draw(pongGame.getBatch(), "Date & Time", 395, 560 );
		columnNames.draw(pongGame.getBatch(), "Result", 620, 560 );
		columnNames.draw(pongGame.getBatch(), "Score", 720, 560 );
		
		try {
			
			if(pongGame.getLoggedIn()) {
				winPercentageFont.draw(pongGame.getBatch(), "Player win percentage: " + winRatioString, 100, Constants.VIEWPORT_HEIGHT-10);
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
		columnNames.dispose();
		
		
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
		int yStartHeight = 530;
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
		
			font.draw(pongGame.getBatch(), Integer.toString(rank), 120, yStartHeight );
			font.draw(pongGame.getBatch(), scoreSet.getString(1), 270, yStartHeight );
			font.draw(pongGame.getBatch(), scoreSet.getString(2), 395, yStartHeight );
			font.draw(pongGame.getBatch(), scoreSet.getString(3), 620, yStartHeight );
			font.draw(pongGame.getBatch(), Integer.toString(scoreSet.getInt(4)), 720, yStartHeight );
			
			yStartHeight-= yDecrement;
			rank++;
			iterations--;
			
		}while(iterations>0 && scoreSet.next());
		
	}

	
}
