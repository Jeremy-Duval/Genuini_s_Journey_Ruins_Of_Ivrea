/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import genuini.handlers.PhysicsVariables;
import genuini.main.Game;

/**
 *
 * @author Adrien
 */
public class Player extends Sprites{
    Texture texture;
    int i;
    String direction;
    int delay;
    final int MAX_DELAY = 3;
    String index;
    
    public Player(Body body) {
        super(body);
        width=76;
        height=92;
        i=1;
        direction="right";
        index=Integer.toString(i);
        texture = Game.contentManager.getTexture("p1_"+direction+"_"+index);
    }
    
    public void walkLeft(){
        if (delay==MAX_DELAY){
            delay=0;
            if(i==11){
                i=1;
            }else{
                i++;
            }

        }else{
            delay++;
        }
        direction="left";
        updateTexture(false);
    }
    
    public void walkRight(){
        if (delay==MAX_DELAY){
            delay=0;
            if(i==11){
                i=1;
            }else{
                i++;
            }
        }else{
            delay++;
        }
        direction="right";
        updateTexture(false);
    }
   
    
    public void updateTexture(boolean jump){
        if(jump){
            texture=Game.contentManager.getTexture("p1_"+direction+"_jump");
        }else{
            index=Integer.toString(i);
            texture=Game.contentManager.getTexture("p1_"+direction+"_"+index);
        }
        
    }
    
    public void render(SpriteBatch spriteBatch) {
            spriteBatch.begin();
            spriteBatch.draw(texture, (int) (body.getPosition().x * PhysicsVariables.PPM - width / 2), (int) (body.getPosition().y * PhysicsVariables.PPM - height / 2));
            spriteBatch.end();
    }
    
}
