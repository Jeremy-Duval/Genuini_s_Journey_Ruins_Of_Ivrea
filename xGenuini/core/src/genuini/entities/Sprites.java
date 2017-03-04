package genuini.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import genuini.screens.GameScreen;

/**
 *The parent class of all sprites : Characters and StaticElements
 * 
 * @author Adrien Techer
 */ 
public abstract class Sprites{
    protected Body body;
    protected GameScreen screen;
    protected World world;
    protected Sprite sprite;
    public enum Direction {
        LEFT, RIGHT
    };
    
    /**
     * 
     * @param screen the screen to which the sprite belongs
     */
    public Sprites(GameScreen screen) {
            this.screen=screen;
            this.world=screen.getWorld();     
    }
    
    
    public Body getBody() { return body; }

    public Sprite getSprite() {
        return sprite;
    }
    
    public Vector2 getPosition() { return body.getPosition();}
    
    
    public void draw(SpriteBatch spriteBatch) {
            sprite.draw(spriteBatch);
    }

    public abstract void update(float delta);
    
    

}
