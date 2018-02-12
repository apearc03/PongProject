package pongproject.game.loadingscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pongproject.game.Pong;
/**
 * Loading screen class. Primary function is to load a message to the screen whilst assets and database connection load.
 * 
 * @author Alex
 *
 */
public class LoadingScreen implements Screen{

	private Pong pongGame;
	private SpriteBatch batch;
	private BitmapFont font;
	private int i;
	private int loaded;
	
	public LoadingScreen(Pong pong) {
		pongGame = pong;
		batch = new SpriteBatch();
		font = new BitmapFont();
		i = 5;
		loaded =  0;
	}
	
	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.draw(batch, "L O A D I N G ...", 470, 482);
		batch.end();
		i--;
		if(i==loaded) {
			pongGame.init();
		}
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {
		batch.dispose();
		font.dispose();
	}

	@Override
	public void dispose() {}

}
