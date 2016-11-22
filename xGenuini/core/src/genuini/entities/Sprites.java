/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 *
 * @author Adrien
 */
public class Sprites {
    protected Body body;
    protected int width;
    protected int height;
	
    public Sprites(Body body) {
            this.body = body;
    }

    public Body getBody() { return body; }
    public Vector2 getPosition() { return body.getPosition(); }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
}
