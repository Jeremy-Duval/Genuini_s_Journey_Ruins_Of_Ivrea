package genuini.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import genuini.main.MainGame;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.BIT_FIREBALL;
import static genuini.world.PhysicsVariables.BIT_MOB;
import static genuini.world.PhysicsVariables.BIT_PLAYER;
import static genuini.world.PhysicsVariables.BIT_TERRAIN;

/**
 *Class used to create a spring
 * 
 * @author Adrien Techer
 */
public class Spring extends StaticElements{

    private final Texture springboardDown;
    private final Texture springboardUp;
    private boolean active;
    
    /**
     * 
     * @param screen the screen to which the sprite belongs
     * @param body the sprite's body
     * @param ID the ID of the map object
     */
    public Spring(GameScreen screen, Body body, int ID) {
        super(screen, body, ID);
        
        springboardDown = MainGame.contentManager.getTexture("springboardDown");
        springboardUp = MainGame.contentManager.getTexture("springboardUp");
        
        sprite= new Sprite(springboardDown);
        sprite.setPosition(position.x,position.y);
        
        createFilter();
    }
    
    /**
     * Activates the spring : the texture is modified and after a delay goes back to normal
     */
    public void activate(){
        if(!active){
            active=true;
            sprite.setRegion(springboardUp);
            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        active=false;
                        sprite.setRegion(springboardDown);
                    }
                },
                        950
            );
        }
    }
    
    @Override
    /**
     * @inheritDoc
     */
    public final void createFilter() {
        filter.categoryBits = BIT_TERRAIN;
        filter.maskBits = BIT_PLAYER | BIT_FIREBALL | BIT_MOB;
        body.getFixtureList().first().setFilterData(filter);
        body.getFixtureList().first().setUserData("spring");
    }
    
}
