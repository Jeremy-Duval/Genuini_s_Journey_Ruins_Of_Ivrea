package genuini.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import genuini.handlers.GameScreenManager;

public class Game implements ApplicationListener {
    public static final String TITLE = "Block Bunny";
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;
	public static final float STEP = 1 / 60f;
	
        private SpriteBatch spriteBatch;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	private GameScreenManager gameScreenManager;
        //public static Content res;
	//Texture img;
	
    @Override
    public void create () {
        spriteBatch = new SpriteBatch();
        cam = new OrthographicCamera();
        cam.setToOrtho(false,V_WIDTH,V_HEIGHT);
        hudCam = new OrthographicCamera();
        hudCam.setToOrtho(false,V_WIDTH,V_HEIGHT);
        gameScreenManager = new GameScreenManager(this);
    }

    @Override
    public void render () {
            Gdx.graphics.setTitle(TITLE + " -- FPS: " + Gdx.graphics.getFramesPerSecond());

            gameScreenManager.update(Gdx.graphics.getDeltaTime());
            gameScreenManager.render();
            gameScreenManager.update(0f);
           
    }

    @Override
    public void dispose () {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public OrthographicCamera getCamera() {
        return cam;
    }

    public OrthographicCamera getHUDCamera() {
        return hudCam;
    }

    
}
