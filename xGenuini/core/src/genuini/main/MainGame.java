package genuini.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import genuini.handlers.Content;
import genuini.handlers.ScreenEnum;
import genuini.handlers.ScreenManager;

public class MainGame extends Game implements ApplicationListener {
    public static final String TITLE = "Genuini";
    public static final int V_WIDTH = 1280;
    public static final int V_HEIGHT = 720;
    public static final int SCALE = 1;
    public static final float STEP = 1 / 60f;

    public static Content contentManager;
    //Texture img;
	
    @Override
    public void create () {
        
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

        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU );
    }

    @Override
    public void render () {
        super.render();
        Gdx.graphics.setTitle(TITLE + " -- FPS: " + Gdx.graphics.getFramesPerSecond());
    }

    @Override
    public void dispose () {
        contentManager.removeAll();
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
    
}
