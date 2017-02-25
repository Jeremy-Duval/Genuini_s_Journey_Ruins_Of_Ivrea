/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import genuini.screens.GameScreen;

/**
 *
 * @author Adrien
 */
public abstract class Characters extends Sprites{
    TextureAtlas atlas;
    public Characters(GameScreen screen) {
        super(screen);
        
    }
    
    
    
    
}
