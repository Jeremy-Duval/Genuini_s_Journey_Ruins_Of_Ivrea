package genuini.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.PPM;

/**
 *The parent class to all static environment components
 * @author Adrien Techer
 */

public abstract class StaticElements extends Sprites{
    private final int ID;
    final Vector2 position; 
    final Filter filter;
    
    /**
     * 
     * @param screen the screen to which the sprite belongs
     * @param body the sprite's body
     * @param ID the ID of the map object
     */
    public StaticElements(GameScreen screen, Body body, int ID) {
        super(screen);
        this.body=body;
        this.ID=ID;
        position = new Vector2(body.getPosition().x * PPM , body.getPosition().y * PPM);
        filter = new Filter();
    }

    @Override
    public void update(float delta){};
    
    /**
     * Creates the filter for the sprite's body
     */
    public abstract void createFilter();
    
    public int getID() {
        return ID;
    }
    
}
