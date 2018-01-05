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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import pongproject.game.Pong;
import pongproject.game.tests.eventLogger;

/**
 * 
 * @author Alex Pearce
 *
 */




public class HighScoreScreen implements Screen{
	
	//Pong and Stage instance
	private Pong pongGame;
	private Stage stage;
	

	//ResultSets to store database query results
	private ResultSet scores;
	private ResultSet playerScores;
	private ResultSet winPercentage;
	private String winPercentageString;
	
	private boolean scoresAssigned;
	private boolean playerScoresAssigned;

	
	//Required textbuttons
	private TextButton menuButton;
	private TextButton orderByPlayerScores; 
	private TextButton allPlayerScores;
	
	
	private TextButton top10Scores;
	private TextButton top25Scores;
	private TextButton top40Scores;
	
	private int scoreYDecrement;
	private int numberOfRankings;

	private boolean showPlayerScores;
		
	//Fonts, titles and backgrounds for screen rendering
	private BitmapFont renderFont;
	
	private final String[] titles = {"High Scores", "Rank", "Player", "Date & Time", "Result", "Score"};
	
	private Texture background;
	private Sprite backgroundSprite;
	
	/**
	 * Constructor initializes all required instances and variables. Adds clickListeners to buttons.
	 * 
	 * @param pong
	 */
	public HighScoreScreen(final Pong pong) {
		pongGame = pong;
		
		stage = new Stage(new StretchViewport(pongGame.getAppWidth(), pongGame.getAppHeight(), pongGame.getCamera()));
		
		
		

		

		background = new Texture(Gdx.files.internal("hsBack2.jpg"));
		backgroundSprite = new Sprite(background);
		backgroundSprite.setSize(pongGame.getAppWidth(), pongGame.getAppHeight());
		
		
		

		top10Scores = new TextButton("Top 10", pongGame.getSkin());
		top10Scores.setPosition(pongGame.getAppWidth()-250, pongGame.getAppHeight()-60);
		stage.addActor(top10Scores);
		
		top25Scores = new TextButton("Top 25", pongGame.getSkin());
		top25Scores.setPosition(top10Scores.getX()+top25Scores.getWidth(), pongGame.getAppHeight()-60);
		stage.addActor(top25Scores);
		
		
		top40Scores = new TextButton("Top 40", pongGame.getSkin());
		top40Scores.setPosition(top25Scores.getX()+top40Scores.getWidth(), pongGame.getAppHeight()-60);
		stage.addActor(top40Scores);
		
		menuButton = new TextButton("Menu", pongGame.getSkin());
		menuButton.setPosition(top40Scores.getX()+top40Scores.getWidth(), pongGame.getAppHeight()-60);
		stage.addActor(menuButton);	
		
		
		orderByPlayerScores = new TextButton("Logged in user", pongGame.getSkin());
		orderByPlayerScores.setPosition(100, pongGame.getAppHeight()-60);
		orderByPlayerScores.setVisible(false);
		stage.addActor(orderByPlayerScores);
		
		
		allPlayerScores = new TextButton("All users", pongGame.getSkin());
		allPlayerScores.setPosition(orderByPlayerScores.getX()+orderByPlayerScores.getWidth(), pongGame.getAppHeight()-60);
		allPlayerScores.setVisible(false);
		stage.addActor(allPlayerScores);
	
		
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			
				super.clicked(event, x, y);
				pongGame.getBackButtonSound().play(pongGame.getGlobalVolume());
				pongGame.setScreen(pongGame.getMenuScreen());
			}
		});
		
		
		//Ranking buttons work by changing the font, Y coodinate spacing and number of rankings.
		
		top10Scores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				super.clicked(event, x, y);
				
				pongGame.getButtonSound().play(pongGame.getGlobalVolume());
				scoreYDecrement = 60;
				numberOfRankings = 10;
				
				
				renderFont = pongGame.getFont16();
			
			}
		});
		
		top25Scores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			
				super.clicked(event, x, y);
				
				pongGame.getButtonSound().play(pongGame.getGlobalVolume());
				scoreYDecrement = 23;
				numberOfRankings = 25;
				
				renderFont = pongGame.getFont14();
			
			}
		});
		
		top40Scores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				super.clicked(event, x, y);
				
				pongGame.getButtonSound().play(pongGame.getGlobalVolume());
				scoreYDecrement = 14;
				numberOfRankings = 40;
			
				renderFont = pongGame.getFont12();
	
				
			}
		});
		
		//Orders by the logged in player or by all scores
		
		orderByPlayerScores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
	
				super.clicked(event, x, y);
				pongGame.getButtonSound().play(pongGame.getGlobalVolume());
				showPlayerScores = true;
			}
		});
		
		
		allPlayerScores.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				super.clicked(event, x, y);
				pongGame.getButtonSound().play(pongGame.getGlobalVolume());
				showPlayerScores = false;
			}
		});
		
		
		
	}

	
	/**
	 * Method called as soon as the screen is set to the current screen.
	 * 
	 */
	@Override
	public void show() {
		
		Gdx.input.setInputProcessor(stage);
		eventLogger.highScoreScreen();
		
			try { //Attemps to get the highScore data.
				scores = pongGame.getData().highScores();
				scoresAssigned = true;
				
					if(pongGame.getLoggedIn()) {  //If a player is logged in then show the extra buttons and win percentage.
						orderByPlayerScores.setVisible(true);
						allPlayerScores.setVisible(true);
						
						playerScores = pongGame.getData().playerScores(pongGame.getData().getAccountUsername());
						
						
						winPercentage = pongGame.getData().winPercentage(pongGame.getData().getAccountUsername());
						
						if(winPercentage.next()) {
							winPercentageString = "Player win percentage: " + String.format("%.0f%%",winPercentage.getFloat(1)*100);

						}
						
						playerScoresAssigned = true;
					}	
					
			} catch (SQLException e) { //If there is an exception then set the screen back to the menu
				
				pongGame.setScreen(pongGame.getMenuScreen());
				
			}
			
	
		
		showPlayerScores = false;
		
		scoreYDecrement = 60;
		numberOfRankings = 10;

		renderFont = pongGame.getFont16();
		
	
	}

	
	/**
	 * Render called every frame. Approximately 58 times per second. Draws all titles, buttons and highscores to the screen. 
	 * 
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
	
		pongGame.getBatch().begin();
		
		backgroundSprite.draw(pongGame.getBatch());
		

		pongGame.getFont20().draw(pongGame.getBatch(), titles[0], 425, pongGame.getAppHeight()-40);
		
		pongGame.getFont20().draw(pongGame.getBatch(), titles[1], 135, 660 );
		pongGame.getFont20().draw(pongGame.getBatch(), titles[2], 280, 660 );
		pongGame.getFont20().draw(pongGame.getBatch(), titles[3], 425, 660 );
		pongGame.getFont20().draw(pongGame.getBatch(), titles[4], 670, 660 );
		pongGame.getFont20().draw(pongGame.getBatch(), titles[5], 815, 660 );
		
		
		
		try {
			
			if(pongGame.getLoggedIn()) {
				pongGame.getFont16().setColor(Color.WHITE);
				pongGame.getFont16().draw(pongGame.getBatch(), winPercentageString, 100, pongGame.getAppHeight()-10);
			}
			
			
			if(showPlayerScores) {
				
				renderScores(numberOfRankings, scoreYDecrement, renderFont, playerScores);
				
			}
			else {
				renderScores(numberOfRankings, scoreYDecrement, renderFont, scores);
		
			}
			
		} catch (SQLException e) {
			
			
			pongGame.setScreen(pongGame.getMenuScreen());
		}
		
		pongGame.getBatch().end();
	
		stage.act(delta);
		stage.draw();

		
	}

	
	/**
	 * Called when the screen is re-sized
	 */
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
		
	}

	//Empty implemented methods
	@Override
	public void pause() {}

	@Override
	public void resume() {}

	/**
	 * Called whenever the screen is hidden
	 */
	@Override
	public void hide() {
		pongGame.getFont16().setColor(Color.WHITE);
		
	}

	/**
	 * Called on application exit to release resources
	 */
	@Override
	public void dispose() {
		stage.dispose();
		background.dispose();
		
		
		try {
			closeResultSets();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
 
	/**
	 * Closes the result set instances.
	 * 
	 * @throws SQLException
	 */
	private void closeResultSets() throws SQLException {
		if(scoresAssigned) {
				scores.close();
		}
		if(playerScoresAssigned) {
				playerScores.close();
				winPercentage.close();
		}
	}
	
	/**
	 * 
	 * Method implemented to be called in the above render method. Loops through the result sets and draws the scores to the screen in the correct position.
	 * 
	 * @param numberOfRanks
	 * @param yDecrement
	 * @param font
	 * @param scoreSet
	 * @throws SQLException
	 */
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
		
			font.draw(pongGame.getBatch(), Integer.toString(rank), 145, yStartHeight );
			font.draw(pongGame.getBatch(), scoreSet.getString(1), 290, yStartHeight );
			font.draw(pongGame.getBatch(), scoreSet.getString(2), 425, yStartHeight );
			font.draw(pongGame.getBatch(), scoreSet.getString(3), 680, yStartHeight );
			font.draw(pongGame.getBatch(), Integer.toString(scoreSet.getInt(4)), 825, yStartHeight );
			
			yStartHeight-= yDecrement;
			rank++;
			iterations--;
			
		}while(iterations>0 && scoreSet.next());
		
	}

	
}
