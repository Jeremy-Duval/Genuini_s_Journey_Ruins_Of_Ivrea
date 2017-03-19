package genuini.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import genuini.game.Content;
import genuini.game.ScreenEnum;
import genuini.game.ScreenManager;
import static genuini.screens.AbstractScreen.arduinoInstance;
import static genuini.screens.AbstractScreen.connected;

/**
 * Game initialization class, called by the launcher. Loads all the ressources, renders the screens, defines the viewport's dimension etc...
 * @author Adrien Techer
 */
public class MainGame extends Game implements ApplicationListener {
    public static final String TITLE = "Genuini's Journey : Ruins of Ivrea";
    public static final int V_WIDTH = 1280;
    public static final int V_HEIGHT = 720;
    public static final int SCALE = 1;
    public static final float STEP = 1 / 60f;

    public static Content contentManager;
	
    @Override
    public void create () {
        
        contentManager = new Content();
        
        contentManager.loadTexture("img/bg_shroom.png","background");
        contentManager.loadTexture("img/items/fireball.png","fireball");
        contentManager.loadTexture("img/items/springboardDown.png","springboardDown");
        contentManager.loadTexture("img/items/springboardUp.png","springboardUp");
        contentManager.loadTexture("img/tiles/lock_red.png","turretActive");
        contentManager.loadTexture("img/tiles/lock_yellow.png","turret");
        contentManager.loadTexture("img/items/buttonRed.png","buttonRed");
        contentManager.loadTexture("img/items/buttonRed_pressed.png","buttonRed_pressed");

        
        
        
        contentManager.loadMusic("sounds/Death.mp3","deathMusic");
        contentManager.getMusic("deathMusic").setLooping(true);
        contentManager.getMusic("deathMusic").setVolume(1f);
        
        contentManager.loadMusic("sounds/Earth_From_Sky.mp3","menuMusic");
        contentManager.getMusic("menuMusic").setLooping(true);
        contentManager.getMusic("menuMusic").setVolume(1f);
        
        contentManager.loadMusic("sounds/Land_of_Ivrea.mp3","gameMusic");
        contentManager.getMusic("gameMusic").setLooping(true);
        contentManager.getMusic("gameMusic").setVolume(0.04f);
        
        contentManager.loadSound("sounds/slimeWalk.wav", "slimeWalk");
        contentManager.loadSound("sounds/slimeDeath.wav", "slimeDeath");
        contentManager.loadSound("sounds/snailWalk.wav", "snailWalk");
        
        contentManager.loadSound("sounds/fireball.wav", "fireball");
        contentManager.loadSound("sounds/spring.wav", "spring");
        contentManager.loadSound("sounds/snailAttack.wav", "snailAttack");
        
        contentManager.loadSound("sounds/jump.wav", "jump");
        
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen(ScreenEnum.GAME);
    }

    @Override
    public void render () {
        super.render();
        Gdx.graphics.setTitle(TITLE + " -- FPS: " + Gdx.graphics.getFramesPerSecond());
    }

    @Override
    public void dispose () {
        contentManager.removeAll();
        if(connected){
            arduinoInstance.write("exit;");
            arduinoInstance.close();
        }
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
