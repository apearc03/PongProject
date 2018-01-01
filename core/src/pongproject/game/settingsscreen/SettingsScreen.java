package pongproject.game.settingsscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import pongproject.game.Pong;
import pongproject.game.tests.eventLogger;



public class SettingsScreen implements Screen {

	
	


	
	
	 //Add background art
	
	private String selectedDifficulty;
	private Float selectedVolume;
	private String selectedResolution;
	
	private Label titleLabel;
	private LabelStyle settingsStyle;
	private Label difficultyLabel;
	private Label volumeLabel;
	private Label resolutionLabel;
	
	private Label controlsUp;
	private Label controlsDown;
	
	private Label controlsLabel;
	
	private Label controlsError;
	
	private String up;
	private TextButton upChange;
	private String down;
	private TextButton downChange;
	

	
	private InputListener upListener;
	private InputListener downListener;
	
	private TextButton menuButton;
	private TextButton applyButton;
	private Stage stage;
	private Pong pongGame;


	private SelectBox<String> difficultyBox;
	private Slider volumeSlider;
	private SelectBox<String> resolutionBox;
	
	private Texture background;
	private Sprite backgroundSprite;
	

	public SettingsScreen(final Pong pong) {

		pongGame = pong;
		
		stage = new Stage(new StretchViewport(pongGame.getAppWidth(), pongGame.getAppHeight(), pongGame.getCamera()));
		
		background = new Texture(Gdx.files.internal("settings4.jpg"));
		backgroundSprite = new Sprite(background);
		backgroundSprite.setSize(pongGame.getAppWidth(), pongGame.getAppHeight());
		
		titleLabel = new Label("Settings", new LabelStyle(pongGame.getFont20(), Color.WHITE));
		titleLabel.setPosition(pongGame.getAppWidth()/2-titleLabel.getWidth()/2, 700);
		stage.addActor(titleLabel);
		
		settingsStyle = new LabelStyle(pongGame.getFont16(), Color.WHITE);
		
		difficultyLabel = new Label("Difficulty", settingsStyle);
		difficultyLabel.setPosition(300, 600);
		stage.addActor(difficultyLabel);
		
		difficultyBox = new SelectBox<String>(pongGame.getSkin());
		difficultyBox.setItems("Easy","Medium","Hard");
		difficultyBox.setSelected("Medium");
		difficultyBox.setPosition(500, 600);
		difficultyBox.setSize(100, 30);
		stage.addActor(difficultyBox);
		
		selectedDifficulty = difficultyBox.getSelected();
		
		volumeLabel = new Label("Volume", settingsStyle);
		volumeLabel.setPosition(300, 525);
		stage.addActor(volumeLabel);
		
		volumeSlider = new Slider(0, 1f, 0.1f, false, pongGame.getSkin());
		volumeSlider.setPosition(500, 525);
		volumeSlider.setValue(pongGame.getButtonVolume());
		stage.addActor(volumeSlider);
		
		selectedVolume = volumeSlider.getValue();
		
		resolutionLabel = new Label("Resolution", settingsStyle);
		resolutionLabel.setPosition(300, 450);
		stage.addActor(resolutionLabel);
		
		resolutionBox = new SelectBox<String>(pongGame.getSkin());
		resolutionBox.setItems("1024 x 768","1440 x 900","1600 x 1050", "Fullscreen");
		resolutionBox.setPosition(500, 450);
		resolutionBox.setSize(150, 30);
		stage.addActor(resolutionBox);
		
		selectedResolution = resolutionBox.getSelected();
		
		
		controlsUp = new Label("Up = " + Keys.toString(pongGame.getGameScreen().getGameController().getPlayerPadd().getKeyUp()) + " Key", settingsStyle);
		controlsUp.setPosition(500, 385);
		stage.addActor(controlsUp);
		
		controlsDown = new Label("Down = " + Keys.toString(pongGame.getGameScreen().getGameController().getPlayerPadd().getKeyDown()) + " Key", settingsStyle);
		controlsDown.setPosition(500, 355);
		stage.addActor(controlsDown);
		
		up = "Change Up key";
		
		upChange = new TextButton(up, pongGame.getSkin());
		upChange.setPosition(650, 385);
		upChange.setSize(140, 30);
		stage.addActor(upChange);
		
		down = "Change Down key";
		
		downChange = new TextButton(down, pongGame.getSkin());
		downChange.setPosition(650, 355);
		downChange.setSize(140, 30);
		stage.addActor(downChange);
		
		
		controlsLabel = new Label("Controls", settingsStyle);
		controlsLabel.setPosition(300, 375);
		stage.addActor(controlsLabel);

		
		controlsError = new Label("Up and Down controls cannot be the same!", new LabelStyle(pongGame.getFont14(), Color.ORANGE));
		controlsError.setPosition(pongGame.getAppWidth()/2-controlsError.getWidth()/2, 300);
		controlsError.setVisible(false);
		stage.addActor(controlsError);
		
		menuButton = new TextButton("Menu", pongGame.getSkin());
		menuButton.setSize(100, 30);
		menuButton.setPosition(pongGame.getAppWidth()/2-menuButton.getWidth()/2, 200);
		stage.addActor(menuButton);
		
		
		applyButton = new TextButton("Apply", pongGame.getSkin());
		applyButton.setSize(100, 30);
		applyButton.setPosition(pongGame.getAppWidth()/2-applyButton.getWidth()/2, 250);
		stage.addActor(applyButton);
		
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				pongGame.getBackButtonSound().play(pongGame.getButtonVolume());
				pongGame.setScreen(pongGame.getMenuScreen());
				
			}
		});
		
		applyButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				//Apply changes to resolution, difficulty etc
				pongGame.getButtonSound().play(pongGame.getButtonVolume());
				
				setDifficulty(difficultyBox.getSelected());
				setVolume(volumeSlider.getValue());
				setResolution(resolutionBox.getSelected());
				
				selectedDifficulty = difficultyBox.getSelected();
				selectedVolume = volumeSlider.getValue();
				selectedResolution = resolutionBox.getSelected();
				resetControlButtons();
			}
		});
		
		
		upListener = new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
	
				if(keycode == pongGame.getGameScreen().getGameController().getPlayerPadd().getKeyDown()) {
					controlsError.setVisible(true);
				}
				else {
					pongGame.getGameScreen().getGameController().getPlayerPadd().setKeyUp(keycode);
					controlsUp.setText("Up = " + Keys.toString(keycode) + " Key");
				}
				stage.removeListener(this);
				upChange.setText(up);
				return super.keyDown(event, keycode);
			}
		};
		
		downListener = new InputListener() {
			
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				
				if(keycode == pongGame.getGameScreen().getGameController().getPlayerPadd().getKeyUp()) {
					controlsError.setVisible(true);
				}
				else {
					pongGame.getGameScreen().getGameController().getPlayerPadd().setKeyDown(keycode);
					controlsDown.setText("Down = " + Keys.toString(keycode) + " Key");
				}
				stage.removeListener(this);
				downChange.setText(down);
				return super.keyDown(event, keycode);
			}
			
		};
		
		upChange.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
					pongGame.getButtonSound().play(pongGame.getButtonVolume());
					upChange.setText("Press a Key");
					downChange.setText(down);
					controlsError.setVisible(false);
					
					stage.addListener(upListener);
					stage.removeListener(downListener);
					
					
					
			}
			
		});
	
		
		downChange.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				pongGame.getButtonSound().play(pongGame.getButtonVolume());
				downChange.setText("Press a key");
				upChange.setText(up);
				controlsError.setVisible(false);
				
				stage.addListener(downListener);
				stage.removeListener(upListener);
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
		

		
		
		pongGame.getBatch().begin();
		backgroundSprite.draw(pongGame.getBatch());
		pongGame.getFont14().draw(pongGame.getBatch(), String.format("%.0f%%",volumeSlider.getValue()*100), 651, 540);
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
		
		difficultyBox.setSelected(selectedDifficulty);
		volumeSlider.setValue(selectedVolume);
		resolutionBox.setSelected(selectedResolution);
	
		resetControlButtons();
	}

	@Override
	public void dispose() {
		background.dispose();
		stage.dispose();
		
	}

	
	private void setDifficulty(String diff) {
		
		if(diff.equals("Easy")) {
			pongGame.getGameScreen().getGameController().getComputerPadd().setDifficulty(12.5f);
		}
		else if(diff.equals("Medium")) {
			pongGame.getGameScreen().getGameController().getComputerPadd().setDifficulty(10.5f);
		}
		else if(diff.equals("Hard")) {
			pongGame.getGameScreen().getGameController().getComputerPadd().setDifficulty(8.5f);
		}
		

	}
	
	private void setVolume(float vol) {
		
		pongGame.setButtonVolume(vol);
		pongGame.getMusic()[0].setVolume(vol);
	}
	
	
	private void setResolution(String res) {
		
		if(res.equals("1024 x 768")) {
			Gdx.graphics.setWindowedMode(1024, 768);
		}
		else if(res.equals("1440 x 900")) {
			Gdx.graphics.setWindowedMode(1440, 900);
		}
		else if(res.equals("1600 x 1050")) {
			Gdx.graphics.setWindowedMode(1600, 1050);
		}
		else if(res.equals("Fullscreen")) {
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		}
		
		
	}
	
	private void resetControlButtons() {
		controlsError.setVisible(false);
		stage.removeListener(upListener);
		stage.removeListener(downListener);
		upChange.setText(up);
		downChange.setText(down);
	}
	
}
