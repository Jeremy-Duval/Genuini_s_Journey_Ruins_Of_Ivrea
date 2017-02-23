/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import genuini.screens.GameScreen;

/**
 *
 * @author Adrien
 */
public abstract class Character extends Sprites{
    public Character(GameScreen screen) {
        super(screen);        
    }

    
}
