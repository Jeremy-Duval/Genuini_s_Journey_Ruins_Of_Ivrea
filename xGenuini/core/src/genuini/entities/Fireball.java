/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import genuini.handlers.PhysicsVariables;
import genuini.main.MainGame;

/**
 *
 * @author Adrien
 */
public class Fireball extends Sprites{

    TextureRegion region;
    int textureWidth;
    int textureHeight ;
    float rotationAngle;
    
    public Fireball(Body body) {
        super(body,MainGame.contentManager.getTexture("fireball"));
        textureWidth = sprite.getTexture().getWidth();
        textureHeight = sprite.getTexture().getHeight();
        rotationAngle = 0;

        region = new TextureRegion(sprite.getTexture(), 0, 0, textureWidth, textureHeight);
    }

    @Override
    public void render(SpriteBatch spriteBatch){
        spriteBatch.begin();
        //spriteBatch.draw(sprite.getTexture(), (int) (body.getPosition().x * PhysicsVariables.PPM - sprite.getWidth() / 2), (int) (body.getPosition().y * PhysicsVariables.PPM - sprite.getHeight() / 2));
        spriteBatch.draw(region, (int) (body.getPosition().x * PhysicsVariables.PPM - sprite.getWidth() / 2), (int) (body.getPosition().y * PhysicsVariables.PPM - sprite.getHeight() / 2), textureWidth / 2f, textureHeight / 2f, textureWidth, textureHeight, 1, 1, rotationAngle, false);
        spriteBatch.end();
        rotationAngle+=45;
    }
    
}
