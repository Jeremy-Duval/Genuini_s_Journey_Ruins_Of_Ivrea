package genuini.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import genuini.handlers.BoundedCamera;
import genuini.handlers.Content;
import genuini.handlers.GameScreenManager;
import genuini.handlers.Input;
import genuini.handlers.InputProcessor;

public class Game implements ApplicationListener {
    public static final String TITLE = "Genuini";
    public static final int V_WIDTH = 1280;
    public static final int V_HEIGHT = 720;
    public static final int SCALE = 1;
    public static final float STEP = 1 / 60f;

    private SpriteBatch spriteBatch;
    private BoundedCamera cam;
    private OrthographicCamera hudCam;
    private GameScreenManager gameScreenManager;
    public static Content contentManager;
    //Texture img;
    
    
    @Override
    public void create () {
        
        Gdx.input.setInputProcessor(new InputProcessor());
        
        contentManager = new Content();
        
        contentManager.loadTexture("img/bg_shroom.png","background");
        
        
        //load player textures
        for(int i=1;i<12;i++){
            String index=Integer.toString(i);
            if(i<10){
               contentManager.loadTexture("img/Player/p1/left/p1_walk0"+index+".png","p1_left_"+index);
               contentManager.loadTexture("img/Player/p1/right/p1_walk0"+index+".png","p1_right_"+index);
            }else{
                contentManager.loadTexture("img/Player/p1/left/p1_walk"+index+".png","p1_left_"+index);
                contentManager.loadTexture("img/Player/p1/right/p1_walk"+index+".png","p1_right_"+index); 
            } 
        }
        contentManager.loadTexture("img/Player/p1/left/p1_jump.png","p1_left_jump");
        contentManager.loadTexture("img/Player/p1/right/p1_jump.png","p1_right_jump");
        
        
        
        
        
        cam = new BoundedCamera();
        cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
        hudCam = new OrthographicCamera();
        hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
        
        spriteBatch = new SpriteBatch();

        gameScreenManager = new GameScreenManager(this);
    }

    @Override
    public void render () {
        Gdx.graphics.setTitle(TITLE + " -- FPS: " + Gdx.graphics.getFramesPerSecond());

        gameScreenManager.update(Gdx.graphics.getDeltaTime());
        gameScreenManager.render();
        Input.update();
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

    public BoundedCamera getCamera() {
        return cam;
    }

    public OrthographicCamera getHUDCamera() {
        return hudCam;
    }


    
}
