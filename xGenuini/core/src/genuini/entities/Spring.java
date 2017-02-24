/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import genuini.main.MainGame;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.PPM;

/**
 *
 * @author Adrien
 */
public class Spring extends Sprites{

    private final Texture springboardDown;
    private final Texture springboardUp;
    private boolean active;
    
    
    public Spring(GameScreen screen, Body body) {
        super(screen);
        this.body=body;
        
        springboardDown = MainGame.contentManager.getTexture("springboardDown");
        springboardUp = MainGame.contentManager.getTexture("springboardUp");
        
        sprite= new Sprite(springboardDown);
    }
    
    @Override
    public void update(float dt){
        Vector2 pos = new Vector2(body.getPosition().x * PPM , body.getPosition().y * PPM);
        sprite.setPosition(pos.x,pos.y);
    }
    
    public void activate(){
        if(!active){
            active=true;
            sprite.setRegion(springboardUp);
            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        active=false;
                        sprite.setRegion(springboardDown);
                    }
                },
                        950
            );
        }
    }
    
}
