/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import genuini.handlers.PhysicsVariables;

/**
 *
 * @author Adrien
 */
public class Sprites extends Sprite{
    protected Body body;
    protected Sprite sprite;

	
    public Sprites(Body body, Texture texture) {
            this.body = body;
            sprite = new Sprite(texture);
    }
    
    
    public Body getBody() { return body; }
    public Vector2 getPosition() { return body.getPosition(); }

    public void render(SpriteBatch spriteBatch) {
            spriteBatch.begin();
            spriteBatch.draw(sprite.getTexture(), (int) (body.getPosition().x * PhysicsVariables.PPM - sprite.getWidth() / 2), (int) (body.getPosition().y * PhysicsVariables.PPM - sprite.getHeight() / 2));
            spriteBatch.end();
    }

}
