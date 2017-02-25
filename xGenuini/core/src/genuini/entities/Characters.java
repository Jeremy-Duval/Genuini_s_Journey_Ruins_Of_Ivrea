package genuini.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import genuini.screens.GameScreen;

/**
 * The parent class to all moving NPCs
 *
 * @author Adrien Techer
 */
public abstract class Characters extends Sprites {

    //The texture atlas to create textures and animations from
    TextureAtlas atlas;

    /**
     * 
     * @param screen the screen to which the sprite belongs
     */
    public Characters(GameScreen screen) {
        super(screen);
    }

}
