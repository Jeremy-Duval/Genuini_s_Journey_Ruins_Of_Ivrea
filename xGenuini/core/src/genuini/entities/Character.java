/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import genuini.handlers.PhysicsVariables;
import genuini.main.MainGame;

/**
 *
 * @author Adrien
 */
public class Character extends Sprites{
    
    int textureFrame;
    int minTextureFrame;
    int maxTextureFrame;
    String name;
    String direction;
    int delay;
    final int MAX_DELAY = 3;
    String index;
    
    
    public Character(Body body, String name, int minTextureFrame, int maxTextureFrame,String initialDirection) {
        super(body,MainGame.contentManager.getTexture(name+"_"+initialDirection+"_"+Integer.toString(minTextureFrame)));
        this.name=name; 
        this.minTextureFrame=minTextureFrame;
        this.maxTextureFrame=maxTextureFrame;
        direction=initialDirection;
        textureFrame=minTextureFrame;
        index=Integer.toString(minTextureFrame);
        
    }
    
    
    public void walkLeft(){
        if (delay==MAX_DELAY){
            delay=0;
            if(textureFrame==maxTextureFrame){
                textureFrame=minTextureFrame;
            }else{
                textureFrame++;
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
            if(textureFrame==maxTextureFrame){
                textureFrame=minTextureFrame;
            }else{
                textureFrame++;
            }
        }else{
            delay++;
        }
        direction="right";
        updateTexture(false);
    }
   
    
    public void updateTexture(boolean jump){
        if(jump){
            sprite.setTexture(MainGame.contentManager.getTexture(name+"_"+direction+"_jump"));
        }else{
            index=Integer.toString(textureFrame);
            sprite.setTexture(MainGame.contentManager.getTexture(name+"_"+direction+"_"+index));
        }
        
    }
    
    public void render(SpriteBatch spriteBatch) {
            spriteBatch.begin();
            spriteBatch.draw(sprite.getTexture(), (int) (body.getPosition().x * PhysicsVariables.PPM - sprite.getWidth() / 2), (int) (body.getPosition().y * PhysicsVariables.PPM - sprite.getHeight() / 2));
            spriteBatch.end();
    }


}
